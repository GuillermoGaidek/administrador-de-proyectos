package logica.model.estados;

import logica.model.Empleado;

public class EstadoIniciado extends Estado{
	
	public EstadoIniciado(Empleado responsable) {
		this.responsable = responsable;
	}

	@Override
	public String toString() {
		return "EstadoIniciado [responsable=" + responsable + "]";
	}
	
}
