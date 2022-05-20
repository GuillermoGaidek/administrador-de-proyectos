package logica.model;

import logica.excepciones.EmpleadoYaAsignadoException;
import logica.excepciones.ServicioException;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;

public class Empleado {
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	
	private long dni;
	private long costoPorHora;
	private boolean libre = true; //Se maneja automaticamente con setProyecto
	private Proyecto proyecto = null;
	
	public Empleado() {}
	
	public Empleado(long dni, long costoPorHora,Proyecto proyecto) throws EmpleadoYaAsignadoException {
		this.dni = dni;
		this.costoPorHora = costoPorHora;
		setProyecto(proyecto);
	}
	
	public void setProyecto(Proyecto proyecto) throws EmpleadoYaAsignadoException {
		if(proyecto != null && !this.libre) throw new EmpleadoYaAsignadoException("Empleado ya asignado.");
		if(proyecto != null && this.libre) this.libre = false;
		if(proyecto == null && !this.libre) this.libre = true;
		this.proyecto = proyecto;
	}
	
	public long getCostoPorHora() {
		return costoPorHora;
	}

	public void setCostoPorHora(long costoPorHora) {
		this.costoPorHora = costoPorHora;
	}

	public boolean estaLibre() {
		return libre;
	}

	public void setLibre(boolean l) {
		this.libre = l;
	}
	
	public long getDni() {
		return dni;
	}
	
	public void setDni(long dni) {
		this.dni = dni;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Empleado emp = (Empleado)obj;
		if (this.dni == emp.dni) return true;
	    return false;
	}
	
	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", costoPorHora=" + costoPorHora + ", libre=" + libre + ", proyecto=" + proyecto
				+ "]";
	}	
	
}
