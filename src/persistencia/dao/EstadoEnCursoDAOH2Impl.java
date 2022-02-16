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
import logica.model.estados.EstadoEnCurso;
import logica.service.GenericService;
import persistencia.jdbc.DBManager;

public class EstadoEnCursoDAOH2Impl implements DAO<EstadoEnCurso> {
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	
	@Override
	public void crear(EstadoEnCurso enCurso) throws DAOException {
		
		String sql = "INSERT INTO ESTADO_EN_CURSO (DESCRIPCION,ID_EMPLEADO) VALUES " +
					"('" + EstadoEnCurso.DESC + "', " + enCurso.getResponsable().getDni() + ")";
		
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
	public void borrar(EstadoEnCurso enCurso) throws DAOException {
		String sql = "DELETE FROM ESTADO_EN_CURSO WHERE ID_EMPLEADO= " + enCurso.getId() ;
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
	public void modificar(EstadoEnCurso enCurso) throws DAOException {
		
		String sql = "UPDATE ESTADO_EN_CURSO SET ID_EMPLEADO=" + 
						enCurso.getResponsable().getDni() +
						" WHERE ID=" + enCurso.getId();
					
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
	public List<EstadoEnCurso> listar() throws DAOException {
		List<EstadoEnCurso> lista = new ArrayList<EstadoEnCurso>();
		String sql = "SELECT * FROM ESTADO_EN_CURSO;";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				EstadoEnCurso enCurso = new EstadoEnCurso(rs.getLong("ID"),
											empleadoService.getById(rs.getLong("ID_EMPLEADO")));
				lista.add(enCurso);
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
	public EstadoEnCurso getById(long id) throws DAOException {
		EstadoEnCurso enCurso = null;
		String sql = "SELECT * FROM ESTADO_EN_CURSO WHERE ID=" + id;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				enCurso = new EstadoEnCurso(rs.getLong("ID"),
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
		return enCurso;
	}
}
