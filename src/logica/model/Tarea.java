package logica.model;

import logica.excepciones.EmpleadoNoDisponibleException;
import logica.excepciones.ServicioException;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;

public class Tarea {
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	private long id;
	private String titulo;
	private String descripcion;
	private int horasEstimadas;
	private int horasReales;
	private Empleado empleado;
	private Estado estado;
	private Proyecto proyecto;
	
	public Tarea() {}
	
	public Tarea(long id, String titulo, String descripcion, int horasEstimadas,int horasReales,
				 Empleado empleado,Estado estado,Proyecto proyecto) throws EmpleadoNoDisponibleException, ServicioException {
		asignarEmpleado(empleado);
		this.estado = estado;
		this.proyecto = proyecto;
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.horasEstimadas = horasEstimadas;
		this.horasReales = horasReales;
	}

	public void asignarEmpleado(Empleado empleado) throws EmpleadoNoDisponibleException, ServicioException {
		if (!empleado.estaLibre()) throw new EmpleadoNoDisponibleException("El empleado ya esta ocupado");
		this.empleado = empleado;
		this.empleado.setLibre(false);
		empleadoService.modificar(this.empleado);
	}
	
	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void cambiarEstadoA(Estado nuevoEstado) {
		this.estado = nuevoEstado;
	}
	
	public Estado getEstado() {
		return this.estado;
	}
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getHorasEstimadas() {
		return this.horasEstimadas;
	}

	public void setHorasEstimadas(int horasEstimadas) {
		this.horasEstimadas = horasEstimadas;
	}

	public int getHorasReales() {
		return this.horasReales;
	}

	public void setHorasReales(int horasReales) {
		this.horasReales = horasReales;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Override
	public String toString() {
		return "Tarea [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", horasEstimadas="
				+ horasEstimadas + ", horasReales=" + horasReales + ", empleado=" + empleado + ", estado=" + estado
				+ ", proyecto=" + proyecto + "]";
	}

}
