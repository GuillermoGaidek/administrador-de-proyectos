package persistencia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import logica.excepciones.DAOException;
import logica.excepciones.EmpleadoYaAsignadoException;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Estado;
import logica.model.Proyecto;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.jdbc.DBManager;

public class EmpleadoDAOH2Impl implements DAO<Empleado> {
	
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	
	@Override
	public void crear(Empleado empleado) throws DAOException {
		
		Object idProyecto = empleado.getProyecto() == null ? null : empleado.getProyecto().getId();
		
		String sql = "INSERT INTO EMPLEADO (DNI,COSTO_POR_HORA,LIBRE,ID_PROYECTO) VALUES " +
					"(" + empleado.getDni() + ", " + empleado.getCostoPorHora() + ", " + 
					empleado.estaLibre() + ", " + idProyecto + ")";
		
		Connection c = DBManager.connect();
		
		try {
			Statement s = c.createStatement();
			s.executeUpdate(sql);
			c.commit();
		} catch(JdbcSQLIntegrityConstraintViolationException e) {
			throw new DAOException("Empleado ya existe con ese DNI. Rollback realizado", e);
		} catch(SQLException e) {
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
	public void borrar(Empleado empleado) throws DAOException {
		String sql = "DELETE FROM EMPLEADO WHERE DNI= " + empleado.getDni() ;
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
	public void modificar(Empleado empleado) throws DAOException {
		
		Object idProyecto = empleado.getProyecto() == null ? null : empleado.getProyecto().getId();
		
		String sql = "UPDATE EMPLEADO SET " +
					"DNI=" + empleado.getDni() + ",COSTO_POR_HORA=" + empleado.getCostoPorHora() + 
					",LIBRE=" + empleado.estaLibre() + ",ID_PROYECTO=" + idProyecto + 
					" WHERE DNI=" + empleado.getDni();
		
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			s.executeUpdate(sql);
			c.commit();
		} catch(JdbcSQLIntegrityConstraintViolationException e) {
			throw new DAOException("Empleado ya existe con ese DNI. Rollback realizado", e);
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
	public List<Empleado> listar() throws DAOException {
		List<Empleado> lista = new ArrayList<Empleado>();
		String sql = "SELECT * FROM EMPLEADO;";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				Proyecto proyecto = rs.getLong("ID_PROYECTO") == 0 ? null : proyectoService.getById(rs.getLong("ID_PROYECTO"));
				Empleado empleado = new Empleado(rs.getLong("DNI"), rs.getInt("COSTO_POR_HORA"),proyecto);
				lista.add(empleado);
			}
		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener lista de empleados de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener lista de empleados de la BD, rollback no realizado", e1);
			}
		} catch (ServicioException se) {
			throw new DAOException("Error en el servicio al obtener lista de empleados de la BD", se);
		} catch (EmpleadoYaAsignadoException empYaAsigEx) {
			throw new DAOException("Empleado ya asignado.", empYaAsigEx);
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
	public List<Empleado> listarById(long idProyecto) throws DAOException {
		List<Empleado> lista = new ArrayList<Empleado>();
		String sql = "SELECT * FROM EMPLEADO WHERE ID_PROYECTO = " + idProyecto + ";";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				Proyecto proyecto = rs.getLong("ID_PROYECTO") == 0 ? null : proyectoService.getById(rs.getLong("ID_PROYECTO"));
				Empleado empleado = new Empleado(rs.getLong("DNI"), rs.getInt("COSTO_POR_HORA"),proyecto);
				lista.add(empleado);
			}
		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener lista de empleados de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener lista de empleados de la BD, rollback no realizado", e1);
			}
		} catch (ServicioException se) {
			throw new DAOException("Error en el servicio al obtener lista de empleados de la BD", se);
		} catch (EmpleadoYaAsignadoException empYaAsigEx) {
			throw new DAOException("Empleado ya asignado.", empYaAsigEx);
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
	public Empleado getById(long dni) throws DAOException {
		Empleado empleado = null;
		String sql = "SELECT * FROM EMPLEADO WHERE DNI=" + dni;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				Proyecto proyecto = rs.getLong("ID_PROYECTO") == 0 ? null : proyectoService.getById(rs.getLong("ID_PROYECTO"));
				empleado = new Empleado(rs.getLong("DNI"), rs.getInt("COSTO_POR_HORA"),proyecto);
			}

		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener registro de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener registro de la BD, rollback no realizado", e1);
			}
		} catch (ServicioException se) {
			throw new DAOException("Error en el servicio al obtener lista de empleados de la BD", se);
		} catch (EmpleadoYaAsignadoException empYaAsigEx) {
			throw new DAOException("Empleado ya asignado.", empYaAsigEx);
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
		return empleado;
	}
	
	@Override
	public long getLastId() {return 0;}
	
	
}


