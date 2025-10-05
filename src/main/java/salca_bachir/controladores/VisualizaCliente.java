package salca_bachir.controladores;

import salca_bachir.dao.PersonasDao;
import salca_bachir.modelos.Persona;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import java.time.LocalDate;

/**
 * Controlador de la vista tabla.fxml.
 * Gestiona la interacción entre la interfaz gráfica y la lógica de negocio (DAO).
 */
public class VisualizaCliente {

    // Referencias a los componentes del FXML
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker birthDateField;

    @FXML
    private TableView<Persona> tableView;

    @FXML
    private TableColumn<Persona, Integer> idColumn;

    @FXML
    private TableColumn<Persona, String> firstNameColumn;

    @FXML
    private TableColumn<Persona, String> lastNameColumn;

    @FXML
    private TableColumn<Persona, LocalDate> birthDateColumn;

    // Instancia del DAO para operaciones con la base de datos
    private final PersonasDao dao = new PersonasDao();

    /**
     * Método llamado automáticamente al cargar el FXML.
     * Configura las columnas de la tabla y carga los datos iniciales desde la base de datos.
     */
    @FXML
    public void initialize() {
        // Enlazar columnas con las propiedades del modelo Persona
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        birthDateColumn.setCellValueFactory(cellData -> cellData.getValue().fechaNacimientoProperty());

        // Cargar datos desde la base de datos
        tableView.setItems(dao.getAllPersonas());
    }

    /**
     * Maneja el evento del botón "Add" y del menú "Editar → Agregar".
     * Valida los campos, crea una nueva persona y la guarda en la base de datos.
     */
    @FXML
    private void handleAdd() {
        String nombre = firstNameField.getText();
        String apellido = lastNameField.getText();
        LocalDate fechaNac = birthDateField.getValue();

        if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                fechaNac == null) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, completa todos los campos.");
            return;
        }

        // Crear nueva persona (el ID será asignado por la base de datos)
        Persona nueva = new Persona(0, nombre, apellido, fechaNac);
        dao.addPersona(nueva);

        // Actualizar la vista
        tableView.getItems().add(nueva);

        // Limpiar campos
        firstNameField.clear();
        lastNameField.clear();
        birthDateField.setValue(null);
    }

    /**
     * Maneja el evento del botón "Eliminar Filas Seleccionadas" y del menú "Editar → Eliminar Seleccionado".
     * Elimina la fila seleccionada de la base de datos y de la tabla.
     */
    @FXML
    private void handleDeleteSelected() {
        Persona selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dao.deletePersona(selected.getId());
            tableView.getItems().remove(selected);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Ninguna selección", "Selecciona una fila para eliminar.");
        }
    }

    /**
     * Maneja el evento del botón "Restaurar Filas" y del menú "Editar → Restaurar Filas".
     * Recarga todos los datos desde la base de datos.
     */
    @FXML
    private void handleRestore() {
        tableView.setItems(dao.getAllPersonas());
    }

    /**
     * Maneja el evento del menú "Archivo → Salir".
     */
    @FXML
    private void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("¿Estás seguro de que deseas salir?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            javafx.application.Platform.exit();
        }
    }

    /**
     * Maneja el evento del menú "Ayuda → Acerca de...".
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("Tabla de Clientes - Versión 1.0");
        alert.setContentText(
                "Aplicación de gestión de personas.\n" +
                        "Conectada a MariaDB vía Docker.\n" +
                        "Desarrollado por: SALCA BACHIR SALEH D\n" +
                        "© 2025"
        );
        alert.showAndWait();
    }

    /**
     * Muestra un cuadro de diálogo de alerta.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}