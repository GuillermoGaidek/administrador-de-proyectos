package persistencia.dao;

import java.util.List;

import logica.excepciones.DAOException;
import logica.excepciones.EmpleadoYaAsignadoException;
import logica.model.Tarea;

public interface DAO<T> {
    public void crear(T a) throws DAOException;
    public void borrar(T a) throws DAOException;
    public void modificar(T a) throws DAOException;
    public List<T> listar() throws DAOException;
    public List<T> listarById(long id) throws DAOException;
    public T getById(long id) throws DAOException;
    public long getLastId() throws DAOException;
}
