package persistencia.dao;

import java.util.List;

import logica.excepciones.DAOException;
import logica.model.Tarea;

public interface TareaDAO {
    public void crear(Tarea a) throws DAOException;
    public void borrar(Tarea a) throws DAOException;
    public void modificar(Tarea a) throws DAOException;
    public List<Tarea> listar() throws DAOException;
    public Tarea getTarea(int id) throws DAOException;
}
