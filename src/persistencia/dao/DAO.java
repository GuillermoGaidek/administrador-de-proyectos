package persistencia.dao;

import java.util.List;

import logica.excepciones.DAOException;
import logica.model.Tarea;

public interface DAO<T> {
    public void crear(T a) throws DAOException;
    public void borrar(T a) throws DAOException;
    public void modificar(T a) throws DAOException;
    public List<T> listar() throws DAOException;
    public T getTarea(int id) throws DAOException;
}
