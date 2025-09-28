// src/main/java/salca_bachir/modelos/Persona.java
package salca_bachir.modelos;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Representa a una persona con identificador, nombre, apellido y fecha de nacimiento.
 * Utiliza propiedades JavaFX para permitir enlace de datos con controles de interfaz.
 */
public class Persona {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellido = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> fechaNacimiento = new SimpleObjectProperty<>();

    /**
     * Constructor completo.
     *
     * @param id identificador único
     * @param nombre nombre de la persona
     * @param apellido apellido de la persona
     * @param fechaNacimiento fecha de nacimiento
     */
    public Persona(int id, String nombre, String apellido, LocalDate fechaNacimiento) {
        setId(id);
        setNombre(nombre);
        setApellido(apellido);
        setFechaNacimiento(fechaNacimiento);
    }

    // Getters, setters y propiedades (con Javadoc breve)
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    public String getApellido() { return apellido.get(); }
    public void setApellido(String apellido) { this.apellido.set(apellido); }
    public StringProperty apellidoProperty() { return apellido; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento.get(); }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento.set(fechaNacimiento); }
    public ObjectProperty<LocalDate> fechaNacimientoProperty() { return fechaNacimiento; }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id.get() +
                ", nombre='" + nombre.get() + '\'' +
                ", apellido='" + apellido.get() + '\'' +
                ", fechaNacimiento=" + fechaNacimiento.get() +
                '}';
    }
}
