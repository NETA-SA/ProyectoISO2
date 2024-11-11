package es.uclm.library.business.persistence;

import es.uclm.library.business.entity.ServicioEntrega;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicioEntregaDAO extends EntityDAO<ServicioEntrega> {

    public ServicioEntregaDAO(GestorBaseDatos gestorBaseDatos) {
        super(gestorBaseDatos);
    }

    @Override
    protected String generateInsertSQL(ServicioEntrega servicioEntrega) {
        return "INSERT INTO servicio_entregas (id_servicio, pedido_id, direccion, repartidor_id, fecha_recepcion, fecha_entrega) VALUES ('" +
                servicioEntrega.getId() + "', '" + servicioEntrega.getPedido().getId() + "', '" +
                servicioEntrega.getDireccion() + "', '" + servicioEntrega.getRepartidor().getIdUsuario() + "', '" +
                servicioEntrega.getFechaRecepcion() + "', '" + servicioEntrega.getFechaEntrega() + "')";
    }

    @Override
    protected String generateUpdateSQL(ServicioEntrega servicioEntrega) {
        return "UPDATE servicio_entregas SET pedido_id = '" + servicioEntrega.getPedido().getId() +
                "', direccion = '" + servicioEntrega.getDireccion() +
                "', repartidor_id = '" + servicioEntrega.getRepartidor().getIdUsuario() +
                "', fecha_recepcion = '" + servicioEntrega.getFechaRecepcion() +
                "', fecha_entrega = '" + servicioEntrega.getFechaEntrega() +
                "' WHERE id_servicio = '" + servicioEntrega.getId() + "'";
    }

    @Override
    protected String generateDeleteSQL(String id) {
        return "DELETE FROM servicio_entregas WHERE id_servicio = '" + id + "'";
    }

    @Override
    protected String generateSelectSQL(String id) {
        return "SELECT * FROM servicio_entregas WHERE id_servicio = '" + id + "'";
    }

    @Override
    protected ServicioEntrega mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        ServicioEntrega servicioEntrega = new ServicioEntrega();
        servicioEntrega.setId(resultSet.getString("id_servicio"));
        // Aquí es necesario asignar el pedido y repartidor (dependiendo de su implementación)
        servicioEntrega.setDireccion(resultSet.getString("direccion"));
        servicioEntrega.setFechaRecepcion(resultSet.getTimestamp("fecha_recepcion").toLocalDateTime());
        servicioEntrega.setFechaEntrega(resultSet.getTimestamp("fecha_entrega").toLocalDateTime());
        return servicioEntrega;
    }
}
