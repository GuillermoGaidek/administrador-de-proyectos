package logica.model;

public class Estado {
	
	private boolean iniciado;
	private boolean enCurso;
	private boolean finalizado;
	private long id;
	private Empleado empleado;
	
	public Estado(long id,Empleado empleado,boolean iniciado,boolean enCurso,boolean finalizado) {
		this.id = id;
		this.empleado = empleado;
		this.iniciado = iniciado; 
		this.enCurso = enCurso;
		this.finalizado = finalizado;
	}
	
	public long getId() {
		return id;
	}
	
	public Empleado getEmpleado() {
		return empleado;
	}

	public boolean estaIniciado() {
		return iniciado;
	}

	public boolean estaEnCurso() {
		return enCurso;
	}

	public boolean estaFinalizado() {
		return finalizado;
	}
	
}
