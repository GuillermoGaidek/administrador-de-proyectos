package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptions.DAOException;
import jdbc.DBManager;
import model.Tarea;

public class TareaDAOImpl implements TareaDAO {
	@Override
	public void crear(Tarea t) throws DAOException {
		String sql = "INSERT INTO TAREAS (TITULO,DESCRIPCION,HORASESTIMADAS,HORASREALES) VALUES " +
		"('" + t.getTitulo() + "', '" + t.getDescripcion() + "', " + t.getHorasEstimadas() + ", " + t.getHorasReales() + ")";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			s.executeUpdate(sql);
			c.commit();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				c.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e);
			}
		} finally {
			try {
				c.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
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
				e.printStackTrace();
				c.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e);
			}
		} finally {
			try {
				c.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void modificar(Tarea t) throws DAOException {
		String sql = "UPDATE TAREAS SET " +
		"TITULO='" + t.getTitulo() + "',DESCRIPCION='" + t.getDescripcion() + "',HORASESTIMADAS=" + t.getHorasEstimadas() + ",HORASREALES=" + t.getHorasReales()
		+ " WHERE ID=" + t.getId();
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			s.executeUpdate(sql);
			c.commit();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				c.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e);
			}
		} finally {
			try {
				c.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public List<Tarea> listar() throws DAOException {
		List<Tarea> lista = new ArrayList<Tarea>();
		String sql = "SELECT * FROM TAREAS ORDER BY TITULO";
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				Tarea a = new Tarea(rs.getInt("ID"), rs.getString("TITULO"), rs.getString("DESCRIPCION"), rs.getInt("HORASESTIMADAS"),
						rs.getInt("HORASREALES"));
				lista.add(a);
			}
		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				c.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return lista;
	}

	@Override
	public Tarea getTarea(int id) throws DAOException {
		Tarea resultado = new Tarea();
		String sql = "SELECT * FROM TAREAS WHERE ID=" + id;
		Connection c = DBManager.connect();
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				resultado = new Tarea(rs.getInt("ID"), rs.getString("TITULO"), rs.getString("DESCRIPCION"), rs.getInt("HORASESTIMADAS"),
						rs.getInt("HORASREALES"));
			}

		} catch (SQLException e) {
			try {
				c.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				c.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return resultado;
	}
}
