package logica.model.estados;

import logica.model.Empleado;

public class EstadoEnCurso extends Estado{
	
	public final static String TABLA = "ESTADO_EN_CURSO";
	public final static String DESC = "La tarea se encuentra en curso";
	
	public EstadoEnCurso(long id,Empleado responsable) {
		super(id,responsable);
	}

	@Override
	public String toString() {
		return "EstadoEnCurso [id=" + id + ", responsable=" + responsable + "]";
	}
	
}
