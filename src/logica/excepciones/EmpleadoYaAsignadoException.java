package logica.excepciones;

public class EmpleadoYaAsignadoException extends Exception{
	
	 public EmpleadoYaAsignadoException() {
	    }

	    public EmpleadoYaAsignadoException(String message) {
	        super(message);
	    }

	    public EmpleadoYaAsignadoException(String message, Throwable cause) {
	        super(message, cause);
	    }

	    public EmpleadoYaAsignadoException(Throwable cause) {
	        super(cause);
	    }
	    
}
