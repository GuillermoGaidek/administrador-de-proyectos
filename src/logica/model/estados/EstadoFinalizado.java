package logica.model.estados;

import logica.model.Empleado;

public class EstadoFinalizado extends Estado{
	
	public final static String DESC = "La tarea ha finalizado";

	
	public EstadoFinalizado(long id, Empleado responsable) {
		super(id);
		this.responsable = responsable;
	}
	
	@Override
	public String toString() {
		return "EstadoFinalizado [responsable=" + responsable + "]";
	}
	
	
}
