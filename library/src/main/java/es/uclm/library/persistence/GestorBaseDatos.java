package es.uclm.library.business.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorBaseDatos {

    private Connection connection;

    // Método para establecer la conexión a la base de datos
    public boolean conectar() {
        try {
            // Configura la conexión (ajusta la URL, usuario y contraseña según tu base de datos)
            String url = "jdbc:mysql://localhost:3306/tu_base_de_datos";
            String user = "tu_usuario";
            String password = "tu_contraseña";

            connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            return false;
        }
    }

    // Método para cerrar la conexión a la base de datos
    public boolean desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al desconectar de la base de datos: " + e.getMessage());
        }
        return false;
    }

    // Método para ejecutar una consulta de inserción
    public int insert(String sql) {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar insert: " + e.getMessage());
            return 0;
        }
    }

    // Método para ejecutar una consulta de actualización
    public int update(String sql) {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar update: " + e.getMessage());
            return 0;
        }
    }

    // Método para ejecutar una consulta de eliminación
    public int delete(String sql) {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar delete: " + e.getMessage());
            return 0;
        }
    }

    // Método para ejecutar una consulta de selección
    public ResultSet select(String sql) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar select: " + e.getMessage());
            return null;
        }
    }
}
