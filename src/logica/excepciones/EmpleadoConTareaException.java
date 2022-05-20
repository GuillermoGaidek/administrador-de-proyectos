package logica.excepciones;

public class EmpleadoConTareaException extends Exception{
	
	 public EmpleadoConTareaException() {
	    }

	    public EmpleadoConTareaException(String message) {
	        super(message);
	    }

	    public EmpleadoConTareaException(String message, Throwable cause) {
	        super(message, cause);
	    }

	    public EmpleadoConTareaException(Throwable cause) {
	        super(cause);
	    }
	    
}
