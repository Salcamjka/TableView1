// ==========================================================
// Archivo: src/main/java/salca_bachir/modelos/Persona.java
// ==========================================================

package salca_bachir.modelos;

// Importaciones de JavaFX para propiedades reactivas
import javafx.beans.property.*;
import java.time.LocalDate;

// Importación opcional de logger (para integrar con Logback)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase modelo que representa a una persona dentro del sistema.

 * Esta clase sigue el patrón de **JavaFX Properties**, lo que permite:
 *  - Enlazar sus atributos directamente con componentes visuales (TableView, TextField, etc.)
 *  - Detectar cambios automáticamente sin necesidad de observadores manuales.

 * Campos principales:
 *  - id             → Identificador único (entero)
 *  - nombre         → Nombre de la persona
 *  - apellido       → Apellido de la persona
 *  - fechaNacimiento → Fecha de nacimiento
 */
public class Persona {

    // ==============================
    // Logger (para uso con Logback)
    // ==============================
    private static final Logger LOGGER = LoggerFactory.getLogger(Persona.class);

    // ==============================
    // Propiedades JavaFX del modelo
    // ==============================
    // Estas propiedades son "observables" por JavaFX.
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellido = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> fechaNacimiento = new SimpleObjectProperty<>();

    // ==============================
    // Constructor
    // ==============================

    /**
     * Constructor completo que inicializa una instancia de Persona.
     *
     * @param id identificador único (autogenerado normalmente por la BD)
     * @param nombre nombre de la persona
     * @param apellido apellido de la persona
     * @param fechaNacimiento fecha de nacimiento de la persona
     */
    public Persona(int id, String nombre, String apellido, LocalDate fechaNacimiento) {
        setId(id);
        setNombre(nombre);
        setApellido(apellido);
        setFechaNacimiento(fechaNacimiento);

        // Mensaje de depuración al crear una nueva persona
        LOGGER.debug("Nueva Persona creada: {} {} (ID: {})", nombre, apellido, id);
    }

    // ==============================
    // Getters, setters y propiedades
    // ==============================

    /** @return el identificador único de la persona */
    public int getId() { return id.get(); }

    /** Establece el identificador único de la persona */
    public void setId(int id) {
        this.id.set(id);
        LOGGER.trace("ID asignado: {}", id);  // nivel TRACE → para seguimiento detallado
    }

    /** @return propiedad observable del ID */
    public IntegerProperty idProperty() { return id; }

    /** @return el nombre de la persona */
    public String getNombre() { return nombre.get(); }

    /** Establece el nombre de la persona */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
        LOGGER.trace("Nombre asignado: {}", nombre);
    }

    /** @return propiedad observable del nombre */
    public StringProperty nombreProperty() { return nombre; }

    /** @return el apellido de la persona */
    public String getApellido() { return apellido.get(); }

    /** Establece el apellido de la persona */
    public void setApellido(String apellido) {
        this.apellido.set(apellido);
        LOGGER.trace("Apellido asignado: {}", apellido);
    }

    /** @return propiedad observable del apellido */
    public StringProperty apellidoProperty() { return apellido; }

    /** @return la fecha de nacimiento */
    public LocalDate getFechaNacimiento() { return fechaNacimiento.get(); }

    /** Establece la fecha de nacimiento */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
        LOGGER.trace("Fecha de nacimiento asignada: {}", fechaNacimiento);
    }

    /** @return propiedad observable de la fecha de nacimiento */
    public ObjectProperty<LocalDate> fechaNacimientoProperty() { return fechaNacimiento; }

    // ==============================
    // Representación textual
    // ==============================

    /**
     * Devuelve una representación de texto útil para depuración o logs.
     */
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

