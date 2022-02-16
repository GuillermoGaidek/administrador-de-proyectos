package persistencia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import logica.excepciones.DAOException;
import logica.excepciones.EmpleadoNoDisponibleException;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Estado;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.jdbc.DBManager;

public class TareaDAOH2Impl implements DAO<Tarea> {
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Estado> estadoService = new GenericService<Estado>(new EstadoDAOH2Impl());
	
	
	@Override
	public void crear(Tarea t) throws DAOException {
		
		String sql = "INSERT INTO TAREA (TITULO,DESCRIPCION,HORAS_ESTIMADAS,HORAS_REALES,ID_EMPLEADO,ID_ESTADO,ID_PROYECTO) VALUES " +
		"('" + t.getTitulo() + "', '" + t.getDescripcion() + "', " + t.getHorasEstimadas() + ", " + t.getHorasReales() +
		t.getEmpleado().getDni() + "," + t.getEstado().getId() + "," + t.getProyecto().getId() + ")";
		
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
	public void borrar(Tarea t) throws DAOException {
		String sql = "DELETE FROM TAREA WHERE ID= " + t.getId() ;
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
	public void modificar(Tarea t) throws DAOException {
		
		String sql = "UPDATE TAREA SET " + "TITULO='" + t.getTitulo() + "',DESCRIPCION='" + t.getDescripcion() + 
						"',HORAS_ESTIMADAS=" + t.getHorasEstimadas() + ",HORAS_REALES=" + t.getHorasReales() + 
						",ID_EMPLEADO=" + t.getEmpleado().getDni() + ",ID_ESTADO=" + t.getEstado().getId() +
						" WHERE ID=" + t.getId();
		
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
	public List<Tarea> listar() throws DAOException {
		List<Tarea> lista = new ArrayList<Tarea>();
		String sql = "SELECT * FROM TAREA ORDER BY ID";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				
				Empleado empleado = empleadoService.getById(rs.getLong("ID_EMPLEADO"));
				empleado.setLibre(true);
				
				Estado estado = estadoService.getById(rs.getLong("ID"));
				
				Tarea a = new Tarea(rs.getInt("ID"), rs.getString("TITULO"), rs.getString("DESCRIPCION"), 
									rs.getInt("HORAS_ESTIMADAS"),rs.getInt("HORAS_REALES"),
									empleado,estado);
				lista.add(a);
			}
		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener lista de tareas de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener lista de tareas de la BD, rollback no realizado", e1);
			}
		} catch (ServicioException se) {
			throw new DAOException("Error en el servicio al obtener lista de Tareas de la BD", se);
		} catch (EmpleadoNoDisponibleException se) {
			throw new DAOException("Error al obtener lista de Tareas de la BD", se);
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
	public Tarea getById(long id) throws DAOException {
		Tarea tarea = new Tarea();
		String sql = "SELECT * FROM TAREA WHERE ID=" + id;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				
				Empleado empleado = empleadoService.getById(rs.getLong("ID_EMPLEADO"));
				empleado.setLibre(true);
				
				Estado estado = estadoService.getById(rs.getLong("ID"));
				
				tarea = new Tarea(rs.getInt("ID"), rs.getString("TITULO"), rs.getString("DESCRIPCION"), 
						rs.getInt("HORAS_ESTIMADAS"),rs.getInt("HORAS_REALES"),
						empleado,estado);
			}

		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener tarea de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener tarea de la BD, rollback no realizado", e1);
			}
		} catch (ServicioException se) {
			throw new DAOException("Error en el servicio al obtener lista de Tareas de la BD", se);
		} catch (EmpleadoNoDisponibleException se) {
			throw new DAOException("Error al obtener lista de Tareas de la BD", se);
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
		return tarea;
	}
}
