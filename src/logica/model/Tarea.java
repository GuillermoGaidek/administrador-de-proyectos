package logica.model;

import logica.excepciones.EmpleadoNoDisponibleException;
import logica.model.estados.Estado;

public class Tarea {
	private int id;
	private String titulo;
	private String descripcion;
	private int horasEstimadas;
	private int horasReales;
	private Empleado empleado;
	private Estado estado;
	
	public Tarea() {
	}
	
	public Tarea(int id, String titulo, String descripcion, int horasEstimadas,int horasReales,
				 Empleado empleado,Estado estado) throws EmpleadoNoDisponibleException {
		asignarEmpleado(empleado);
		this.estado = estado;
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.horasEstimadas = horasEstimadas;
		this.horasReales = horasReales;
	}

	public void asignarEmpleado(Empleado empleado) throws EmpleadoNoDisponibleException {
		if (!empleado.estaLibre()) throw new EmpleadoNoDisponibleException("El empleado ya esta ocupado");
		this.empleado = empleado;
		this.empleado.setLibre(false);
	}
	
	public Empleado getEmpleado() {
		return this.empleado;
	}

	public Estado cambiarEstadoA(Estado nuevoEstado) {
		return nuevoEstado;
	}
	
	public Estado getEstado() {
		return this.estado;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	@Override
	public String toString() {
		return "Tarea [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", horasEstimadas="
				+ horasEstimadas + ", horasReales=" + horasReales + ", empleado=" + empleado + ", estado=" + estado
				+ "]";
	}

}
