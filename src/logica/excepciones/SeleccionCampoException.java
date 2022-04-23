package logica.excepciones;

public class SeleccionCampoException extends Exception{
	
	public SeleccionCampoException() {
    }

    public SeleccionCampoException(String message) {
        super(message);
    }

    public SeleccionCampoException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeleccionCampoException(Throwable cause) {
        super(cause);
    }
}
