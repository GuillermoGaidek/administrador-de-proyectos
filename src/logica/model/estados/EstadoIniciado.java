package logica.model.estados;

import logica.model.Empleado;

public class EstadoIniciado extends Estado{
	
	public final static String TABLA = "ESTADO_INICIADO";
	public final static String DESC = "La tarea ha iniciado";
	
	public EstadoIniciado(long id,Empleado responsable) {
		super(id,responsable);
	}

	@Override
	public String toString() {
		return "EstadoIniciado [id=" + id + ", responsable=" + responsable + "]";
	}
	
}
