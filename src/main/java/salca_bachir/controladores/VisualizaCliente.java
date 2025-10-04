package salca_bachir.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import salca_bachir.modelos.Persona;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VisualizaCliente {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private DatePicker birthDateField;
    @FXML private TableView<Persona> tableView;
    @FXML private TableColumn<Persona, Integer> idColumn;
    @FXML private TableColumn<Persona, String> firstNameColumn;
    @FXML private TableColumn<Persona, String> lastNameColumn;
    @FXML private TableColumn<Persona, LocalDate> birthDateColumn;

    // LISTA OBSERVABLE COMO CAMPO DE LA CLASE (clave para que "Add" funcione)
    private ObservableList<Persona> personas = FXCollections.observableArrayList();
    private ObservableList<Persona> originalData;
    private int nextId = 6;

    @FXML
    public void initialize() {
        // Vincular columnas con propiedades de Persona
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        // Formatear fecha en la columna
        birthDateColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? "" : date.format(DateTimeFormatter.ISO_LOCAL_DATE));
            }
        });

        // Asignar la lista observable a la tabla
        tableView.setItems(personas);

        // Cargar datos iniciales
        loadInitialData();
    }

    private void loadInitialData() {
        personas.setAll(
                new Persona(1, "Ashwin", "Sharan", LocalDate.of(2012, 10, 11)),
                new Persona(2, "Advik", "Sharan", LocalDate.of(2012, 10, 11)),
                new Persona(3, "Layne", "Estes", LocalDate.of(2011, 12, 16)),
                new Persona(4, "Mason", "Boyd", LocalDate.of(2003, 4, 20)),
                new Persona(5, "Babalu", "Sharan", LocalDate.of(1980, 1, 10))
        );
        originalData = FXCollections.observableArrayList(personas);
    }

    @FXML
    private void handleAdd() {
        String nombre = firstNameField.getText().trim();
        String apellido = lastNameField.getText().trim();
        LocalDate fecha = birthDateField.getValue();

        if (nombre.isEmpty() || apellido.isEmpty() || fecha == null) {
            showAlert("Error", "Todos los campos son obligatorios.");
            return;
        }

        // 🔥 AÑADIR A LA LISTA OBSERVABLE (no a tableView.getItems())
        personas.add(new Persona(nextId++, nombre, apellido, fecha));
        clearFields();
    }

    @FXML
    private void handleDeleteSelected() {
        var selected = tableView.getSelectionModel().getSelectedItems();
        if (selected.isEmpty()) {
            showAlert("Info", "Selecciona al menos una fila.");
            return;
        }
        personas.removeAll(selected);
    }

    @FXML
    private void handleRestore() {
        personas.setAll(originalData);
        nextId = originalData.size() + 1;
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        birthDateField.setValue(null);
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}