package es.uclm.library.business.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import es.uclm.library.business.entity.Usuario;

public class UsuarioDAO extends EntityDAO<Usuario> {

    public UsuarioDAO(GestorBaseDatos gestorBaseDatos) {
        super(gestorBaseDatos);
    }

    // Método para autenticar un usuario con idUsuario y password
    public Usuario autenticarUsuario(String idUsuario, String password) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = '" + idUsuario + "' AND pass = '" + password + "'";
        ResultSet resultSet = gestorBaseDatos.select(sql);
        try {
            if (resultSet != null && resultSet.next()) {
                return mapResultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected String generateInsertSQL(Usuario usuario) {
        return "INSERT INTO usuarios (id_usuario, pass, attribute) VALUES ('" +
                usuario.getIdUsuario() + "', '" + usuario.getPass() + "', " + usuario.getAttribute() + ")";
    }

    @Override
    protected String generateUpdateSQL(Usuario usuario) {
        return "UPDATE usuarios SET pass = '" + usuario.getPass() + "', attribute = " + usuario.getAttribute() +
                " WHERE id_usuario = '" + usuario.getIdUsuario() + "'";
    }

    @Override
    protected String generateDeleteSQL(String id) {
        return "DELETE FROM usuarios WHERE id_usuario = '" + id + "'";
    }

    @Override
    protected String generateSelectSQL(String id) {
        return "SELECT * FROM usuarios WHERE id_usuario = '" + id + "'";
    }

    @Override
    protected Usuario mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultSet.getString("id_usuario"));
        usuario.setPass(resultSet.getString("pass"));
        usuario.setAttribute(resultSet.getInt("attribute"));
        return usuario;
    }
}
