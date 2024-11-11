package es.uclm.library.business.persistence;

import es.uclm.library.business.entity.Repartidor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO extends EntityDAO<Repartidor> {

    public RepartidorDAO(GestorBaseDatos gestorBaseDatos) {
        super(gestorBaseDatos);
    }

    // Método para obtener todos los repartos realizados por un repartidor específico
    public List<String> obtenerHistorialDeEntregas(String repartidorId) {
        String sql = "SELECT id_entrega FROM servicio_entregas WHERE repartidor_id = '" + repartidorId + "'";
        ResultSet resultSet = gestorBaseDatos.select(sql);
        List<String> entregas = new ArrayList<>();
        try {
            while (resultSet != null && resultSet.next()) {
                entregas.add(resultSet.getString("id_entrega"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial de entregas: " + e.getMessage());
        }
        return entregas;
    }

    // Método para actualizar la disponibilidad de un repartidor
    public int actualizarDisponibilidad(String repartidorId, boolean disponible) {
        String sql = "UPDATE repartidores SET disponible = " + (disponible ? "1" : "0") +
                " WHERE id_repartidor = '" + repartidorId + "'";
        return gestorBaseDatos.update(sql);
    }

    @Override
    protected String generateInsertSQL(Repartidor repartidor) {
        return "INSERT INTO repartidores (id_repartidor, nombre, apellidos, nif, eficiencia) VALUES ('" +
                repartidor.getIdUsuario() + "', '" + repartidor.getNombre() + "', '" +
                repartidor.getApellidos() + "', '" + repartidor.getNif() + "', " +
                repartidor.getEficiencia() + ")";
    }

    @Override
    protected String generateUpdateSQL(Repartidor repartidor) {
        return "UPDATE repartidores SET nombre = '" + repartidor.getNombre() + "', apellidos = '" +
                repartidor.getApellidos() + "', nif = '" + repartidor.getNif() +
                "', eficiencia = " + repartidor.getEficiencia() +
                " WHERE id_repartidor = '" + repartidor.getIdUsuario() + "'";
    }

    @Override
    protected String generateDeleteSQL(String id) {
        return "DELETE FROM repartidores WHERE id_repartidor = '" + id + "'";
    }

    @Override
    protected String generateSelectSQL(String id) {
        return "SELECT * FROM repartidores WHERE id_repartidor = '" + id + "'";
    }

    @Override
    protected Repartidor mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Repartidor repartidor = new Repartidor();
        repartidor.setIdUsuario(resultSet.getString("id_repartidor"));
        repartidor.setNombre(resultSet.getString("nombre"));
        repartidor.setApellidos(resultSet.getString("apellidos"));
        repartidor.setNif(resultSet.getString("nif"));
        repartidor.setEficiencia(resultSet.getInt("eficiencia"));
        // Puedes mapear otros atributos si los hay
        return repartidor;
    }
}
