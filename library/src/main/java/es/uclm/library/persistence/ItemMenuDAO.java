package es.uclm.library.business.persistence;

import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.TipoItemMenu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMenuDAO extends EntityDAO<ItemMenu> {

    public ItemMenuDAO(GestorBaseDatos gestorBaseDatos) {
        super(gestorBaseDatos);
    }

    // Método para obtener todos los ítems de menú de un restaurante específico
    public List<ItemMenu> obtenerItemsPorRestaurante(String restauranteId) {
        String sql = "SELECT * FROM items_menu WHERE restaurante_id = '" + restauranteId + "'";
        ResultSet resultSet = gestorBaseDatos.select(sql);
        List<ItemMenu> items = new ArrayList<>();
        try {
            while (resultSet != null && resultSet.next()) {
                items.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ítems de menú: " + e.getMessage());
        }
        return items;
    }

    @Override
    protected String generateInsertSQL(ItemMenu item) {
        return "INSERT INTO items_menu (id_item, nombre, precio, tipo, restaurante_id) VALUES ('" +
                item.getId() + "', '" + item.getNombre() + "', " + item.getPrecio() + ", '" +
                item.getTipo().name() + "', '" + item.getRestauranteId() + "')";
    }

    @Override
    protected String generateUpdateSQL(ItemMenu item) {
        return "UPDATE items_menu SET nombre = '" + item.getNombre() + "', precio = " + item.getPrecio() +
                ", tipo = '" + item.getTipo().name() + "' WHERE id_item = '" + item.getId() + "'";
    }

    @Override
    protected String generateDeleteSQL(String id) {
        return "DELETE FROM items_menu WHERE id_item = '" + id + "'";
    }

    @Override
    protected String generateSelectSQL(String id) {
        return "SELECT * FROM items_menu WHERE id_item = '" + id + "'";
    }

    @Override
    protected ItemMenu mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        ItemMenu item = new ItemMenu();
        item.setId(resultSet.getString("id_item"));
        item.setNombre(resultSet.getString("nombre"));
        item.setPrecio(resultSet.getDouble("precio"));
        item.setTipo(TipoItemMenu.valueOf(resultSet.getString("tipo")));
        item.setRestauranteId(resultSet.getString("restaurante_id"));
        return item;
    }
}
