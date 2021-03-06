package persistencia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import logica.excepciones.DAOException;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Estado;
import logica.model.Proyecto;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.jdbc.DBManager;

public class EstadoDAOH2Impl implements DAO<Estado> {
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	
	@Override
	public void crear(Estado estado) throws DAOException {
		
		Object dniResponsable = estado.getResponsable() == null ? null : estado.getResponsable().getDni();
		
		String sql = "INSERT INTO ESTADO (ID_EMPLEADO,INICIADO,EN_CURSO,FINALIZADO,ID_TAREA,FECHA_MODIFICACION) VALUES " +
						"(" + dniResponsable + ", " + estado.estaIniciado() + ", " +
						estado.estaEnCurso() + ", " + estado.estaFinalizado() + ", " +
						estado.getIdTarea() + ", '" + estado.getFechaModificacion() + "')";
		
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
	public void borrar(Estado estado) throws DAOException {
		String sql = "DELETE FROM ESTADO WHERE ID= " + estado.getId() ;
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
	public void modificar(Estado estado) throws DAOException {
		
		Object dniResponsable = estado.getResponsable() == null ? null : estado.getResponsable().getDni();
		
		String sql = "UPDATE ESTADO SET " + "ID_EMPLEADO='" + dniResponsable + "',INICIADO='" + 
					estado.estaIniciado() + "',EN_CURSO=" + estado.estaEnCurso() + 
					",FINALIZADO=" + estado.estaFinalizado() + ",ID_TAREA=" + estado.getIdTarea() +
					",FECHA_MODIFICACION='" + estado.getFechaModificacion() + "' WHERE ID=" + estado.getId();
		
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
	public List<Estado> listar() throws DAOException {
		List<Estado> lista = new ArrayList<Estado>();
		String sql = "SELECT * FROM ESTADO ORDER BY ID DESC";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				ZonedDateTime fechaModificacion = ZonedDateTime.ofInstant(rs.getTimestamp("FECHA_MODIFICACION").toInstant(),
																		  ZoneId.systemDefault());
				
				Empleado empleado = rs.getLong("ID_EMPLEADO") == 0 ? null : empleadoService.getById(rs.getLong("ID_EMPLEADO"));
				Estado estado = new Estado(rs.getLong("ID"),empleado,
											rs.getBoolean("INICIADO"),rs.getBoolean("EN_CURSO"),rs.getBoolean("FINALIZADO"),
											rs.getLong("ID_TAREA"),fechaModificacion);
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
	public List<Estado> listarById(long idTarea) throws DAOException {
		List<Estado> lista = new ArrayList<Estado>();
		String sql = "SELECT * FROM ESTADO WHERE ID_TAREA =" + idTarea;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				ZonedDateTime fechaModificacion = ZonedDateTime.ofInstant(rs.getTimestamp("FECHA_MODIFICACION").toInstant(),
																		  ZoneId.systemDefault());
				Empleado empleado = rs.getLong("ID_EMPLEADO") == 0 ? null : empleadoService.getById(rs.getLong("ID_EMPLEADO"));
				Estado estado = new Estado(rs.getLong("ID"),empleado,
											rs.getBoolean("INICIADO"),rs.getBoolean("EN_CURSO"),rs.getBoolean("FINALIZADO"),
											rs.getLong("ID_TAREA"),fechaModificacion);
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
	public Estado getById(long id) throws DAOException {
		Estado estado = null;
		String sql = "SELECT * FROM ESTADO WHERE ID=" + id;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				
				ZonedDateTime fechaModificacion = ZonedDateTime.ofInstant(rs.getTimestamp("FECHA_MODIFICACION").toInstant(),
						  ZoneId.systemDefault());
				Empleado empleado = rs.getLong("ID_EMPLEADO") == 0 ? null : empleadoService.getById(rs.getLong("ID_EMPLEADO"));
				estado = new Estado(rs.getLong("ID"),empleado,
						rs.getBoolean("INICIADO"),rs.getBoolean("EN_CURSO"),rs.getBoolean("FINALIZADO"),
						rs.getLong("ID_TAREA"),fechaModificacion);
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
	
	@Override
	public long getLastId() {return 0;}
}
