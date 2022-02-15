package logica.model.estados;

import logica.model.Empleado;

public class EstadoFinalizado extends Estado{
	
	public EstadoFinalizado(Empleado responsable) {
		this.responsable = responsable;
	}

	@Override
	public String toString() {
		return "EstadoFinalizado [responsable=" + responsable + "]";
	}
	
	
}
