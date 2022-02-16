package persistencia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logica.excepciones.DAOException;
import logica.model.Tarea;
import persistencia.jdbc.DBManager;

public class TareaDAOH2Impl implements DAO<Tarea> {
	
	@Override
	public void crear(Tarea t) throws DAOException {
		
		String sql = "INSERT INTO TAREA (TITULO,DESCRIPCION,HORAS_ESTIMADAS,HORAS_REALES,EMPLEADO,ESTADO) VALUES " +
		"('" + t.getTitulo() + "', '" + t.getDescripcion() + "', " + t.getHorasEstimadas() + ", " + t.getHorasReales() +
		t.getEmpleado().getDni() + "," + t.getEstado().getId() + ")";
		
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
		String sql = "DELETE FROM TAREAS WHERE ID= " + t.getId() ;
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
		
		String sql = "UPDATE TAREA SET " +
		"TITULO='" + t.getTitulo() + "',DESCRIPCION='" + t.getDescripcion() + 
		"',HORAS_ESTIMADAS=" + t.getHorasEstimadas() + ",HORAS_REALES=" + t.getHorasReales() + 
		",EMPLEADO=" + t.getEmpleado() + ",ESTADO=" + t.getEstado().getId() +
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
		String sql = "SELECT * FROM TAREA ORDER BY TITULO";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				Tarea a = new Tarea(rs.getInt("ID"), rs.getString("TITULO"), rs.getString("DESCRIPCION"), rs.getInt("HORAS_ESTIMADAS"),
						rs.getInt("HORAS_REALES"));
				lista.add(a);
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
	public Tarea getById(int id) throws DAOException {
		Tarea resultado = new Tarea();
		String sql = "SELECT * FROM TAREA WHERE ID=" + id;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				resultado = new Tarea(rs.getInt("ID"), rs.getString("TITULO"), rs.getString("DESCRIPCION"), rs.getInt("HORAS_ESTIMADAS"),
						rs.getInt("HORAS_REALES"));
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
		return resultado;
	}
}
