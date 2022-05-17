package persistencia.dao;

import logica.model.Tarea;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import logica.excepciones.DAOException;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Estado;
import logica.model.Proyecto;
import logica.service.GenericService;
import persistencia.jdbc.DBManager;

public class ProyectoDAOH2Impl implements DAO<Proyecto> {
	
	@Override
	public void crear(Proyecto p) throws DAOException {
		
		String sql = "INSERT INTO PROYECTO (TITULO) VALUES " +
						"('" + p.getTitulo() + "'" + ")";
		
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
	public void borrar(Proyecto p) throws DAOException {
		String sql = "DELETE FROM PROYECTO WHERE ID= " + p.getId() ;
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
	public void modificar(Proyecto p) throws DAOException {
		
		String sql = "UPDATE PROYECTO SET TITULO = '" + p.getTitulo()  +
						"' WHERE ID = " + p.getId();
		
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
	public List<Proyecto> listar() throws DAOException {
		List<Proyecto> lista = new ArrayList<Proyecto>();
		String sql = "SELECT * FROM PROYECTO ORDER BY ID";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				Proyecto p = new Proyecto(rs.getLong("ID"), rs.getString("TITULO"));
				lista.add(p);
			}
		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener lista de tareas de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener lista de tareas de la BD, rollback no realizado", e1);
			}
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
	public List<Proyecto> listarById(long id) {return new ArrayList();}

	@Override
	public Proyecto getById(long id) throws DAOException {
		Proyecto proyecto = new Proyecto();
		String sql = "SELECT * FROM PROYECTO WHERE ID=" + id;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				proyecto = new Proyecto(rs.getLong("ID"), rs.getString("TITULO"));
			}

		} catch (SQLException e) {
			try {
				c.rollback();
				throw new DAOException("Error al obtener tarea de la BD, rollback realizado", e);
			} catch (SQLException e1) {
				throw new DAOException("Error al obtener tarea de la BD, rollback no realizado", e1);
			}
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
		return proyecto;
	}
		
	@Override
	public long getLastId() throws DAOException {
		String sql = "SELECT MAX(ID) AS ID FROM PROYECTO";
		long lastId = 0;
		Connection c = DBManager.connect();
		
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			if (rs.next()) lastId = rs.getLong("ID");
			
		} catch(SQLException e) {
				throw new DAOException("Error al consultar la BD", e);
		} finally {
			try {
				DBManager.close();
			} catch (SQLException e) {
				throw new DAOException("Error al cerrar la conexion de la BD", e);
			}
		}
		
		return lastId;
	}
	
}
