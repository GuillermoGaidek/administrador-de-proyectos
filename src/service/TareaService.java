package service;

import java.util.List;

import dao.TareaDAOImpl;
import exceptions.DAOException;
import exceptions.ServicioException;
import model.Tarea;

public class TareaService {
	TareaDAOImpl dao;

	public TareaService() {
		dao = new TareaDAOImpl();
	}

    public void crear(Tarea t) throws ServicioException {
        try {
            dao.crear(t);
        } catch (DAOException e) {
            throw new ServicioException(e);
        }
    }

    public void borrar(Tarea t) {
        try {
			dao.borrar(t);
		} catch (DAOException e) {
			e.printStackTrace();
		}
    }

    public void modificar(Tarea t) {
        try {
			dao.modificar(t);
		} catch (DAOException e) {
			e.printStackTrace();
		}
    }

    public List<Tarea> listar() {
    	List<Tarea> tareas = null;
        try {
        	tareas = dao.listar();
		} catch (DAOException e) {
			e.printStackTrace();
		}
        return tareas;
    }

    public Tarea getTarea(int id) {
    	Tarea tarea = null;
    	try {
    		tarea = dao.getTarea(id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
    	return tarea;
    }
}
