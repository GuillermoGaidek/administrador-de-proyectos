package logica.model;

import java.util.List;

public class Proyecto {
	
	private int id;
	private String titulo;
	private List<Empleado> empleados;
	private List<Tarea> tareas;
	
	public Proyecto() {
		
	}

	public Proyecto(int id,String titulo,List<Empleado> empleados,List<Tarea> tareas) {
		this.id = id;
		this.titulo = titulo;
		this.empleados = empleados;
		this.tareas = tareas;
		
	}
	
	public void agregarTarea(Tarea t) {
		this.tareas.add(t);
	}
	
	public void eliminarTarea(Tarea t) {
		this.tareas.remove(t);
	}
	
	public List<Tarea> getTareas() {
		return tareas;
	}
	
	public void agregarEmpleado(Empleado e) {
		this.empleados.add(e);
	}
	
	public void eliminarEmpleado(Empleado e) {
		this.empleados.remove(e);
	}
	
	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Proyecto [id=" + id + ", nombre=" + titulo + ", empleados=" + empleados + ", tareas=" + tareas + "]";
	}
	
	

	
}
