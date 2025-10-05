package salca_bachir.dao;

import salca_bachir.modelos.Persona;
import salca_bachir.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PersonasDao {

    public ObservableList<Persona> getAllPersonas() {
        ObservableList<Persona> lista = FXCollections.observableArrayList();
        String sql = "SELECT id, nombre, apellido, fecha_nacimiento FROM personas ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                LocalDate fechaNac = rs.getDate("fecha_nacimiento").toLocalDate();

                lista.add(new Persona(id, nombre, apellido, fechaNac));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void addPersona(Persona persona) {
        String sql = "INSERT INTO personas (nombre, apellido, fecha_nacimiento) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setDate(3, java.sql.Date.valueOf(persona.getFechaNacimiento()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePersona(int id) {
        String sql = "DELETE FROM personas WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}