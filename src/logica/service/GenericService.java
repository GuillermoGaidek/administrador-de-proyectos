package logica.service;

import java.util.List;
import logica.excepciones.DAOException;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Tarea;
import persistencia.dao.DAO;
import persistencia.dao.EmpleadoDAOH2Impl;

public class GenericService<T> {
	DAO dao;

	public GenericService(DAO dao) {
		this.dao = dao;
	}

    public void crear(T objetoDao) throws ServicioException {
        try {
            dao.crear(objetoDao);
        } catch (DAOException e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new ServicioException("Hubo un error en la BD al guardar el registro", e);
        }
    }

    public void borrar(T objetoDao) throws ServicioException {
        try {
			dao.borrar(objetoDao);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al borrar el registro", e);
		}
    }

    public void modificar(T objetoDao) throws ServicioException {
        try {
			dao.modificar(objetoDao);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al modificar el registro", e);
		}
    }

    public List<T> listar() throws ServicioException {
    	List<T> objetoDao = null;
        try {
        	objetoDao = dao.listar();
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al obtener los datos", e);
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
			throw new ServicioException("Hubo un error en la BD al obtener el registro", e);
		}
    	return objetoDao;
    }
}