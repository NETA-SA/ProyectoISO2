package es.uclm.library.business.persistence;

import es.uclm.library.business.entity.Restaurante;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestauranteDAO extends EntityDAO<Restaurante> {

	public RestauranteDAO(GestorBaseDatos gestorBaseDatos) {
		super(gestorBaseDatos);
	}

	@Override
	protected String generateInsertSQL(Restaurante restaurante) {
		return "INSERT INTO restaurantes (id_restaurante, nombre, cif, direccion, codigo_postal) VALUES ('" +
				restaurante.getIdUsuario() + "', '" + restaurante.getNombre() + "', '" +
				restaurante.getCif() + "', '" + restaurante.getDireccion() + "', '" + restaurante.getCodigoPostal() + "')";
	}

	@Override
	protected String generateUpdateSQL(Restaurante restaurante) {
		return "UPDATE restaurantes SET nombre = '" + restaurante.getNombre() + "', cif = '" +
				restaurante.getCif() + "', direccion = '" + restaurante.getDireccion() +
				"', codigo_postal = '" + restaurante.getCodigoPostal() + "' WHERE id_restaurante = '" + restaurante.getIdUsuario() + "'";
	}

	@Override
	protected String generateDeleteSQL(String id) {
		return "DELETE FROM restaurantes WHERE id_restaurante = '" + id + "'";
	}

	@Override
	protected String generateSelectSQL(String id) {
		return "SELECT * FROM restaurantes WHERE id_restaurante = '" + id + "'";
	}

	@Override
	protected Restaurante mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Restaurante restaurante = new Restaurante();
		restaurante.setIdUsuario(resultSet.getString("id_restaurante"));
		restaurante.setNombre(resultSet.getString("nombre"));
		restaurante.setCif(resultSet.getString("cif"));
		restaurante.setDireccion(resultSet.getString("direccion"));
		restaurante.setCodigoPostal(resultSet.getString("codigo_postal"));
		return restaurante;
	}
}
