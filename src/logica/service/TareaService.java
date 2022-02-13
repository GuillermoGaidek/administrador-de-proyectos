package logica.service;

import java.util.List;

import logica.excepciones.DAOException;
import logica.excepciones.ServicioException;
import logica.model.Tarea;
import persistencia.dao.DAO;
import persistencia.dao.TareaDAOH2Impl;

public class TareaService {
	DAO<Tarea> dao;

	public TareaService() {
		dao = new TareaDAOH2Impl();
	}

    public void crear(Tarea t) throws ServicioException {
        try {
            dao.crear(t);
        } catch (DAOException e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new ServicioException("Hubo un error en la BD al crear la tarea", e);
        }
    }

    public void borrar(Tarea t) throws ServicioException {
        try {
			dao.borrar(t);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al borrar la tarea", e);
		}
    }

    public void modificar(Tarea t) throws ServicioException {
        try {
			dao.modificar(t);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al modificar la tarea", e);
		}
    }

    public List<Tarea> listar() throws ServicioException {
    	List<Tarea> tareas = null;
        try {
        	tareas = dao.listar();
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al obtener las tareas", e);
		}
        return tareas;
    }

    public Tarea getTarea(int id) throws ServicioException {
    	Tarea tarea = null;
    	try {
    		tarea = dao.getTarea(id);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
        	e.printStackTrace();
			throw new ServicioException("Hubo un error en la BD al obtener la tarea", e);
		}
    	return tarea;
    }
}
