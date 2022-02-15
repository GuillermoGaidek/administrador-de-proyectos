package logica.model.estados;

import logica.model.Empleado;

public class EstadoEnCurso extends Estado{
	
	public EstadoEnCurso(Empleado responsable) {
		this.responsable = responsable;
	}

	@Override
	public String toString() {
		return "EstadoEnCurso [responsable=" + responsable + "]";
	}
	
	
}
