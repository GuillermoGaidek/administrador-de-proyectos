package logica.service;

import java.util.List;
import logica.excepciones.DAOException;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Tarea;
import persistencia.dao.DAO;
import persistencia.dao.EmpleadoDAOH2Impl;

public class GenericService<T> {
	DAO<T> dao;

	public GenericService(DAO<T> dao) {
		this.dao = dao;
	}

    public void crear(T objetoDao) throws ServicioException {
        try {
            dao.crear(objetoDao);
        } catch (DAOException e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new ServicioException("Hubo un error en la BD al guardar el registro: " + e.getMessage(), e);
        }
    }

    public void borrar(T objetoDao) throws ServicioException {
        try {
			dao.borrar(objetoDao);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al guardar el registro: " + e.getMessage(), e);
		}
    }

    public void modificar(T objetoDao) throws ServicioException {
        try {
			dao.modificar(objetoDao);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al guardar el registro: " + e.getMessage(), e);
		}
    }

    public List<T> listar() throws ServicioException {
    	List<T> objetoDao = null;
        try {
        	objetoDao = dao.listar();
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al guardar el registro: " + e.getMessage(), e);
		}
        return objetoDao;
    }
    
    public List<T> listarById(long idProyecto) throws ServicioException {
    	List<T> objetoDao = null;
        try {
        	objetoDao = dao.listarById(idProyecto);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al guardar el registro: " + e.getMessage(), e);
		}
        return objetoDao;
    }

    public T getById(long id) throws ServicioException {
    	T objetoDao = null;
    	try {
    		objetoDao = (T) dao.getById(id);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al guardar el registro: " + e.getMessage(), e);
		}
    	return objetoDao;
    }
    
    public long getLastId() throws ServicioException {
    	long lastId = 0;
    	try {
    		lastId = dao.getLastId();
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al guardar el registro: " + e.getMessage(), e);
		}
    	return lastId;
    }
    
    
}
