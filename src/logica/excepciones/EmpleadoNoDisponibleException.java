package logica.excepciones;

public class EmpleadoNoDisponibleException extends Exception{
	
	public EmpleadoNoDisponibleException() {
    }

    public EmpleadoNoDisponibleException(String message) {
        super(message);
    }

    public EmpleadoNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmpleadoNoDisponibleException(Throwable cause) {
        super(cause);
    }
}
