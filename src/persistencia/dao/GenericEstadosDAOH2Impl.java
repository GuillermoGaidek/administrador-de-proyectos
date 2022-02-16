package persistencia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import logica.excepciones.DAOException;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.estados.Estado;
import logica.model.estados.EstadoEnCurso;
import logica.service.GenericService;
import persistencia.jdbc.DBManager;

public class GenericEstadosDAOH2Impl<T extends Estado> implements DAO<T> {
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	String tablaEstado;
	
	public GenericEstadosDAOH2Impl(String tablaEstado) {
		this.tablaEstado = tablaEstado;
	}
	
	@Override
	public void crear(T estado) throws DAOException {
		
		String sql = "INSERT INTO" + tablaEstado + "(DESCRIPCION,ID_EMPLEADO) VALUES " +
					"('" + EstadoEnCurso.DESC + "', " + estado.getResponsable().getDni() + ")";
		
		Connection c = DBManager.connect();
		
		try {
			Statement s = c.createStatement();
			s.executeUpdate(sql);
			c.commit();
		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al guardar en la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al guardar en la BD y rollback no realizado", e1);
			}
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
	}

	@Override
	public void borrar(T estado) throws DAOException {
		String sql = "DELETE FROM" + tablaEstado + "WHERE ID_EMPLEADO= " + estado.getId() ;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			s.executeUpdate(sql);
			c.commit();
		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al borrar registro de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al borrar de la BD y rollback no realizado", e1);
			}
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
	}

	@Override
	public void modificar(T estado) throws DAOException {
		
		String sql = "UPDATE" + tablaEstado + "SET ID_EMPLEADO=" + 
						estado.getResponsable().getDni() +
						" WHERE ID=" + estado.getId();
					
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			s.executeUpdate(sql);
			c.commit();
		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al modificar registro de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al modificar registro de la BD, rollback no realizado", e1);
			}
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
		
	}

	@Override
	public List<T> listar() throws DAOException {
		List<T> lista = new ArrayList<T>();
		String sql = "SELECT * FROM" + tablaEstado;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				T estado = (T) new Estado(rs.getLong("ID"),
											empleadoService.getById(rs.getLong("ID_EMPLEADO")));
				lista.add(estado);
			}
		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener datos de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener datos de la BD, rollback no realizado", e1);
			}
		} catch (ServicioException se) {
			throw new DAOException("Error en el servicio al obtener lista de Estados de la BD", se);
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
		return lista;
	}

	@Override
	public T getById(long id) throws DAOException {
		T estado = null;
		String sql = "SELECT * FROM" + tablaEstado + "WHERE ID=" + id;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				estado = (T) new Estado(rs.getLong("ID"),
						empleadoService.getById(rs.getLong("ID_EMPLEADO")));
			}

		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener el Estado de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener el Estado de la BD, rollback no realizado", e1);
			}
		} catch (ServicioException se) {
			throw new DAOException("Error en el servicio al obtener lista de Estados de la BD", se);
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
		return estado;
	}
}
