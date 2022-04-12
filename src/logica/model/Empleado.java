package logica.model;

import logica.excepciones.EmpleadoNoDisponibleException;
import logica.excepciones.ServicioException;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;

public class Empleado {
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	
	private long dni;
	private long costoPorHora;
	private boolean libre = true; //se manjea automaticamente
	private Proyecto proyecto = null;
	
	public Empleado() {}
	
	public Empleado(long dni, long costoPorHora,Proyecto proyecto,boolean persistir) throws EmpleadoNoDisponibleException, ServicioException {
		if(proyecto != null) asignarProyectoNuevo(proyecto,persistir);
		this.dni = dni;
		this.costoPorHora = costoPorHora;
	}
	
	public void asignarProyectoNuevo(Proyecto proyecto,boolean persistir) throws EmpleadoNoDisponibleException, ServicioException {
		if (!this.libre) throw new EmpleadoNoDisponibleException("El empleado ya esta ocupado");
		this.libre = false;
		this.proyecto = proyecto;
		if(persistir)empleadoService.modificar(this);
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

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
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
