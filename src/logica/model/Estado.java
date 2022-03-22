package logica.model;

public class Estado {
	
	private boolean iniciado;
	private boolean enCurso;
	private boolean finalizado;
	private long id;
	private Empleado modificadoPor;
	
	public Estado() {}
	
	public Estado(long id,Empleado modificadoPor,boolean iniciado,boolean enCurso,boolean finalizado) {
		this.id = id;
		this.modificadoPor = modificadoPor;
		this.iniciado = iniciado; 
		this.enCurso = enCurso;
		this.finalizado = finalizado;
	}
	
	public Estado(Empleado modificadoPor,boolean iniciado,boolean enCurso,boolean finalizado) {
		this.modificadoPor = modificadoPor;
		this.iniciado = iniciado; 
		this.enCurso = enCurso;
		this.finalizado = finalizado;
	}
	
	public long getId() {
		return id;
	}
	
	public Empleado getModificadoPor() {
		return modificadoPor;
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
