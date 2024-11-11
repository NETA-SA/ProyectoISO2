package es.uclm.library.business.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import es.uclm.library.business.entity.Cliente;

public class ClienteDAO extends EntityDAO<Cliente> {

    public ClienteDAO(GestorBaseDatos gestorBaseDatos) {
        super(gestorBaseDatos);
    }



    @Override
    protected String generateInsertSQL(Cliente cliente) {
        return "INSERT INTO clientes (id_cliente, nombre, apellidos, dni) VALUES ('" +
                cliente.getIdUsuario() + "', '" + cliente.getNombre() + "', '" + cliente.getApellidos() + "', '" +
                cliente.getDni() + "')";
    }

    @Override
    protected String generateUpdateSQL(Cliente cliente) {
        return "UPDATE clientes SET nombre = '" + cliente.getNombre() + "', apellidos = '" + cliente.getApellidos() +
                "', dni = '" + cliente.getDni() + "' WHERE id_cliente = '" + cliente.getIdUsuario() + "'";
    }

    @Override
    protected String generateDeleteSQL(String id) {
        return "DELETE FROM clientes WHERE id_cliente = '" + id + "'";
    }

    @Override
    protected String generateSelectSQL(String id) {
        return "SELECT * FROM clientes WHERE id_cliente = '" + id + "'";
    }

    @Override
    protected Cliente mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdUsuario(resultSet.getString("id_cliente"));
        cliente.setNombre(resultSet.getString("nombre"));
        cliente.setApellidos(resultSet.getString("apellidos"));
        cliente.setDni(resultSet.getString("dni"));
        // Asigna otros atributos específicos de Cliente según sea necesario
        return cliente;
    }
}
