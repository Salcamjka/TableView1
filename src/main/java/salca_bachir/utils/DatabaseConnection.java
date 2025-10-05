package salca_bachir.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3307/bd_tablev";
    private static final String USER = "usuario";
    private static final String PASSWORD = "clave";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver de MariaDB no encontrado", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
