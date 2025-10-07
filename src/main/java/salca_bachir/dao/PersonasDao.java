package salca_bachir.dao;

// Importaciones necesarias: modelo, conexión y utilidades de JavaFX
import salca_bachir.modelos.Persona;
import salca_bachir.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) encargada de gestionar el acceso
 * y manipulación de los datos en la tabla "personas" de la base de datos.

 * Ofrece métodos CRUD (Create, Read, Delete) para la entidad Persona:
 *   - Obtener todas las personas
 *   - Insertar una persona nueva
 *   - Eliminar una persona por ID

 * Esta clase mantiene separada la lógica de acceso a datos del resto
 * de la aplicación, promoviendo una arquitectura limpia (MVC).
 */
public class PersonasDao {

    // MÉTODO: OBTENER TODAS LAS PERSONAS


    /**
     * Recupera todos los registros de la tabla "personas" ordenados por ID.
     *
     * @return Lista de objetos Persona obtenidos desde la base de datos.
     */
    public List<Persona> getAllPersonas() {
        // Se crea una lista vacía para almacenar las personas recuperadas
        List<Persona> lista = new ArrayList<>();

        // Consulta SQL que selecciona todos los campos relevantes
        String sql = "SELECT id, nombre, apellido, fecha_nacimiento FROM personas ORDER BY id";

        // Bloque try-with-resources para cerrar automáticamente los recursos
        try (Connection conn = DatabaseConnection.getConnection();   // Conexión a la BD
             PreparedStatement stmt = conn.prepareStatement(sql);    // Sentencia preparada
             ResultSet rs = stmt.executeQuery()) {                   // Resultado de la consulta

            // Se recorre cada fila del resultado
            while (rs.next()) {
                int id = rs.getInt("id");                             // Campo ID
                String nombre = rs.getString("nombre");               // Campo nombre
                String apellido = rs.getString("apellido");           // Campo apellido
                LocalDate fechaNac = rs.getDate("fecha_nacimiento")   // Campo fecha
                        .toLocalDate();

                // Se crea un nuevo objeto Persona con los datos obtenidos
                lista.add(new Persona(id, nombre, apellido, fechaNac));
            }

        } catch (SQLException e) {
            // Si ocurre un error de SQL, se imprime la traza de error
            // (Recomendación: usar un LOGGER aquí)
            e.printStackTrace();
        }

        // Devuelve la lista con todas las personas encontradas
        return lista;
    }


    // MÉTODO: AÑADIR PERSONA


    /**
     * Inserta un nuevo registro en la tabla "personas".
     *
     * @param persona Objeto Persona que contiene los datos a insertar.
     */
    public void addPersona(Persona persona) {
        // Consulta SQL con parámetros (para evitar inyecciones SQL)
        String sql = "INSERT INTO personas (nombre, apellido, fecha_nacimiento) VALUES (?, ?, ?)";

        // Bloque try-with-resources para asegurar el cierre de recursos
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Se asignan los valores a los parámetros de la sentencia SQL
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setDate(3, java.sql.Date.valueOf(persona.getFechaNacimiento()));

            // Se ejecuta la sentencia (INSERT)
            stmt.executeUpdate();

        } catch (SQLException e) {
            // En caso de error, se muestra la traza
            e.printStackTrace();
        }
    }



    //  MÉTODO: ELIMINAR PERSONA


    /**
     * Elimina un registro de la tabla "personas" según su ID.
     *
     * @param id Identificador único de la persona a eliminar.
     */
    public void deletePersona(int id) {
        // Consulta SQL parametrizada para eliminar por ID
        String sql = "DELETE FROM personas WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Se establece el valor del parámetro
            stmt.setInt(1, id);

            // Se ejecuta la eliminación
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
