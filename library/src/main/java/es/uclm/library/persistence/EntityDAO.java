package es.uclm.library.business.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class EntityDAO<T> {

	protected GestorBaseDatos gestorBaseDatos;

	// Constructor para inicializar el gestor de base de datos
	public EntityDAO(GestorBaseDatos gestorBaseDatos) {
		this.gestorBaseDatos = gestorBaseDatos;
	}

	// Método para insertar una entidad
	public int insert(T entity) {
		String sql = generateInsertSQL(entity);
		return gestorBaseDatos.insert(sql);
	}

	// Método para actualizar una entidad
	public int update(T entity) {
		String sql = generateUpdateSQL(entity);
		return gestorBaseDatos.update(sql);
	}

	// Método para eliminar una entidad por su ID
	public int delete(String id) {
		String sql = generateDeleteSQL(id);
		return gestorBaseDatos.delete(sql);
	}

	// Método para seleccionar una entidad por su ID
	public T select(String id) {
		String sql = generateSelectSQL(id);
		ResultSet resultSet = gestorBaseDatos.select(sql);
		try {
			if (resultSet != null && resultSet.next()) {
				return mapResultSetToEntity(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("Error al procesar el resultado: " + e.getMessage());
		}
		return null;
	}

	// Método abstracto para generar SQL de inserción, implementación en subclases
	protected abstract String generateInsertSQL(T entity);

	// Método abstracto para generar SQL de actualización, implementación en subclases
	protected abstract String generateUpdateSQL(T entity);

	// Método abstracto para generar SQL de eliminación, implementación en subclases
	protected abstract String generateDeleteSQL(String id);

	// Método abstracto para generar SQL de selección, implementación en subclases
	protected abstract String generateSelectSQL(String id);

	// Método abstracto para mapear ResultSet a la entidad, implementación en subclases
	protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

}
