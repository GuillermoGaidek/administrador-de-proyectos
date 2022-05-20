package logica.model;

import java.util.List;

public class Proyecto {
	
	private long id;
	private String titulo;
	private List<Empleado> empleados;
	private List<Tarea> tareas;
	
	public Proyecto() {
		
	}
	
	public Proyecto(String titulo) {
		this.titulo = titulo;
	}
	
	public Proyecto(long id,String titulo) {
		this.id = id;
		this.titulo = titulo;
	}
	
	public void agregarEmpleado(Empleado e) {
		this.empleados.add(e);
	}
	
	public void eliminarEmpleado(Empleado e) {
		this.empleados.remove(e);
	}
	
	public List<Empleado> getListaEmpleados() {
		return empleados;
	}
	
	public void setListaEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}
	
	public void agregarTarea(Tarea t) {
		this.tareas.add(t);
	}
	
	public void eliminarTarea(Tarea t) {
		this.tareas.remove(t);
	}
	
	public List<Tarea> getListaTareas() {
		return tareas;
	}
	
	public void setListaTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Proyecto [id=" + id + ", nombre=" + titulo + ", empleados=" + empleados + ", tareas=" + tareas + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Proyecto proy = (Proyecto)obj;
		if (this.id == proy.id) return true;
	    return false;
	}

	
}
