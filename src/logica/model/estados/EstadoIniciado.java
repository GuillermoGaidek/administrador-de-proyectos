package logica.model.estados;

import logica.model.Empleado;

public class EstadoIniciado extends Estado{
	
	public final static String DESC = "La tarea ha iniciado";
	
	public EstadoIniciado(long id,Empleado responsable) {
		super(id);
		this.responsable = responsable;
		
	}
	
	@Override
	public String toString() {
		return "EstadoIniciado [responsable=" + responsable + "]";
	}
	
}
