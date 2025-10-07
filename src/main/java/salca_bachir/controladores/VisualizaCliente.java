package salca_bachir.controladores;

// Importaciones necesarias para JavaFX, concurrencia, acceso a datos y logging
import javafx.application.Platform;
import javafx.concurrent.Task;
import salca_bachir.dao.PersonasDao;
import salca_bachir.modelos.Persona;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador de la vista "tabla.fxml".
 * Esta clase actúa como intermediario entre la interfaz gráfica (vista)
 * y la capa de acceso a datos (DAO).

 * Contiene toda la lógica para:
 *  - Cargar los datos desde la base de datos.
 *  - Agregar, eliminar y restaurar registros.
 *  - Controlar eventos de botones y menús.
 *  - Mostrar mensajes y alertas al usuario.
 */
public class VisualizaCliente {

    /** Logger de la clase para registrar eventos e incidencias */
    private static final Logger LOGGER = LoggerFactory.getLogger(VisualizaCliente.class);

    // ==== Referencias a los elementos definidos en el archivo FXML ====
    @FXML private TextField firstNameField;  // Campo de texto para el nombre
    @FXML private TextField lastNameField;   // Campo de texto para el apellido
    @FXML private DatePicker birthDateField; // Selector de fecha de nacimiento

    @FXML private TableView<Persona> tableView; // Tabla que muestra los datos
    @FXML private TableColumn<Persona, Integer> idColumn; // Columna del ID
    @FXML private TableColumn<Persona, String> firstNameColumn; // Columna del nombre
    @FXML private TableColumn<Persona, String> lastNameColumn;  // Columna del apellido
    @FXML private TableColumn<Persona, LocalDate> birthDateColumn; // Columna de fecha de nacimiento

    /** Instancia del DAO para acceder a la base de datos */
    private final PersonasDao dao = new PersonasDao();


    // ================================================================
    // ============ MÉTODOS PRINCIPALES DEL CONTROLADOR ===============
    // ================================================================

    /**
     * Se ejecuta automáticamente cuando se carga la vista FXML.
     * Configura las columnas de la tabla y llama al metodo  de carga de datos.
     */
    @FXML
    public void initialize() {
        // Se vinculan las columnas con las propiedades del modelo Persona.
        idColumn.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        firstNameColumn.setCellValueFactory(c -> c.getValue().nombreProperty());
        lastNameColumn.setCellValueFactory(c -> c.getValue().apellidoProperty());
        birthDateColumn.setCellValueFactory(c -> c.getValue().fechaNacimientoProperty());

        // Carga inicial de los datos desde la base de datos de manera asíncrona.
        loadDataAsync();
    }

    /**Método encargado de cargar los datos desde la base de datos de manera asíncrona.
     * Se ejecuta en un hilo secundario para evitar que la interfaz se congele.
     */
    private void loadDataAsync() {
        // Se crea una tarea en segundo plano (Task) que devuelve una lista de personas.
        Task<List<Persona>> task = new Task<>() {
            @Override
            protected List<Persona> call() {
                LOGGER.info("Cargando datos desde la base de datos...");
                return dao.getAllPersonas(); // Llama al DAO para obtener los registros.
            }
        };

        // Cuando la tarea termina correctamente:
        task.setOnSucceeded(e -> {
            // Se actualiza la tabla con los datos obtenidos.
            tableView.setItems(FXCollections.observableArrayList(task.getValue()));
            LOGGER.info("Datos cargados correctamente");
        });

        // En caso de error durante la ejecución:
        task.setOnFailed(e -> {
            LOGGER.error("Error al cargar datos", task.getException());
            showAlert(Alert.AlertType.ERROR, "Error al cargar datos",
                    "No se pudieron cargar los datos de la base de datos.");
        });

        // Se lanza la tarea en un nuevo hilo independiente.
        new Thread(task).start();
    }


    // ================================================================
    // =================== EVENTO: AÑADIR PERSONA =====================
    // ================================================================

    /**
     * Añade una nueva persona a la base de datos y la muestra en la tabla.
     * Se ejecuta cuando el usuario pulsa el botón “Añadir”.
     */
    @FXML
    private void handleAdd() {
        // Obtiene los valores introducidos en los campos de texto.
        String nombre = firstNameField.getText();
        String apellido = lastNameField.getText();
        LocalDate fechaNac = birthDateField.getValue();

        // Verifica que los campos no estén vacíos o nulos.
        if (nombre == null || nombre.isBlank() ||
                apellido == null || apellido.isBlank() || fechaNac == null) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos",
                    "Por favor, completa todos los campos.");
            return; // Se cancela la operación si falta algún dato.
        }

        // Crea un nuevo objeto Persona con los datos ingresados.
        Persona nueva = new Persona(0, nombre, apellido, fechaNac);

        // Tarea asíncrona para insertar la persona en la base de datos.
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                dao.addPersona(nueva); // Inserta la persona mediante el DAO.
                return null;
            }
        };

        // Si la inserción fue exitosa:
        task.setOnSucceeded(e -> Platform.runLater(() -> {
            // Se actualiza la tabla y se limpian los campos del formulario.
            tableView.getItems().add(nueva);
            firstNameField.clear();
            lastNameField.clear();
            birthDateField.setValue(null);
            LOGGER.info("Persona agregada correctamente: {}", nueva);
        }));

        // Si ocurre un error:
        task.setOnFailed(e -> {
            LOGGER.error("Error al agregar persona", task.getException());
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar la persona.");
        });

        // Se inicia la tarea en un hilo aparte.
        new Thread(task).start();
    }


    // ================================================================
    // =================== EVENTO: ELIMINAR PERSONA ===================
    // ================================================================

    /**
     * Elimina la persona seleccionada en la tabla.
     * Muestra un mensaje de error si no se selecciona ninguna fila.
     */
    @FXML
    private void handleDeleteSelected() {
        // Se obtiene el elemento seleccionado en la tabla.
        Persona selected = tableView.getSelectionModel().getSelectedItem();

        // Si no hay selección, se muestra un aviso.
        if (selected == null) {
            showAlert(Alert.AlertType.INFORMATION, "Ninguna selección",
                    "Selecciona una fila para eliminar.");
            return;
        }

        // Tarea para eliminar la persona en la base de datos.
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                dao.deletePersona(selected.getId());
                return null;
            }
        };

        // Si la eliminación fue exitosa:
        task.setOnSucceeded(e -> Platform.runLater(() -> {
            tableView.getItems().remove(selected);
            LOGGER.info("Persona eliminada: {}", selected);
        }));

        // En caso de error:
        task.setOnFailed(e -> {
            LOGGER.error("Error al eliminar persona", task.getException());
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la persona.");
        });

        // Se inicia la tarea.
        new Thread(task).start();
    }


    // ================================================================
    // =================== EVENTO: RESTAURAR DATOS ====================
    // ================================================================

    /**
     * Recarga los datos desde la base de datos.
     * Se utiliza para refrescar la tabla después de cambios.
     */
    @FXML
    private void handleRestore() {
        loadDataAsync(); // Reutiliza el método de carga asíncrona.
    }


    // ================================================================
    // ==================== EVENTO: SALIR =============================
    // ================================================================

    /**
     * Muestra una ventana de confirmación antes de cerrar la aplicación.
     */
    @FXML
    private void handleExit() {
        // Se crea un cuadro de diálogo de confirmación.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("¿Estás seguro de que deseas salir?");
        alert.setContentText("Los cambios no guardados se perderán.");

        // Si el usuario confirma, se cierra la aplicación.
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            LOGGER.info("Aplicación cerrada por el usuario");
            Platform.exit(); // Cierra JavaFX
        }
    }


    // ================================================================
    // =================== EVENTO: ACERCA DE ==========================
    // ================================================================

    /**
     * Muestra información sobre la aplicación y el autor.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("Tabla de Clientes - Versión 1.0 (Asíncrona)");
        alert.setContentText("""
                Aplicación de gestión de personas.
                Conectada a MariaDB vía Docker.
                Carga asíncrona con JavaFX Tasks.
                Desarrollado por: SALCA BACHIR SALEH D
                © 2025
                """);
        alert.showAndWait();
    }



    //  MÉTODO AUXILIAR: MOSTRAR ALERTAS


    /**
     * Método reutilizable para mostrar diferentes tipos de alertas
     * (error, advertencia, información, confirmación).
     *
     * @param type    Tipo de alerta (ERROR, WARNING, INFO, etc.)
     * @param title   Título de la ventana de alerta
     * @param message Mensaje mostrado al usuario
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Platform.runLater(() -> { // Asegura que se ejecute en el hilo de la UI
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
