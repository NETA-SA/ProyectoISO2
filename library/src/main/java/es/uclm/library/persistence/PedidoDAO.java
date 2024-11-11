package es.uclm.library.business.persistence;

import es.uclm.library.business.entity.Pedido;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO extends EntityDAO<Pedido> {

    public PedidoDAO(GestorBaseDatos gestorBaseDatos) {
        super(gestorBaseDatos);
    }

    // Método para obtener todos los pedidos de un cliente
    public List<Pedido> obtenerPedidosPorCliente(String clienteId) {
        String sql = "SELECT * FROM pedidos WHERE cliente_id = '" + clienteId + "'";
        ResultSet resultSet = gestorBaseDatos.select(sql);
        List<Pedido> pedidos = new ArrayList<>();
        try {
            while (resultSet != null && resultSet.next()) {
                pedidos.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedidos del cliente: " + e.getMessage());
        }
        return pedidos;
    }

    @Override
    protected String generateInsertSQL(Pedido pedido) {
        return "INSERT INTO pedidos (id_pedido, cliente_id, restaurante_id, estado, fecha) VALUES ('" +
                pedido.getId() + "', '" + pedido.getCliente().getIdUsuario() + "', '" +
                pedido.getRestaurante().getIdUsuario() + "', '" + pedido.getEstado().name() + "', '" +
                pedido.getFecha() + "')";
    }

    @Override
    protected String generateUpdateSQL(Pedido pedido) {
        return "UPDATE pedidos SET cliente_id = '" + pedido.getCliente().getIdUsuario() + "', restaurante_id = '" +
                pedido.getRestaurante().getIdUsuario() + "', estado = '" + pedido.getEstado().name() +
                "', fecha = '" + pedido.getFecha() + "' WHERE id_pedido = '" + pedido.getId() + "'";
    }

    @Override
    protected String generateDeleteSQL(String id) {
        return "DELETE FROM pedidos WHERE id_pedido = '" + id + "'";
    }

    @Override
    protected String generateSelectSQL(String id) {
        return "SELECT * FROM pedidos WHERE id_pedido = '" + id + "'";
    }

    @Override
    protected Pedido mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(resultSet.getString("id_pedido"));
        // Asigna los atributos de cliente, restaurante y otros según sea necesario
        pedido.setEstado(Pedido.Estado.valueOf(resultSet.getString("estado")));
        pedido.setFecha(resultSet.getDate("fecha"));
        // Mapea otros detalles de la entidad Pedido, como los items del pedido si corresponde
        return pedido;
    }
}
