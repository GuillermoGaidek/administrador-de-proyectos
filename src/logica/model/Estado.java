package logica.model;

public class Estado {
	
	private boolean iniciado;
	private boolean enCurso;
	private boolean finalizado;
	private long id;
	private Empleado modificadoPor;
	private long idTarea;
	
	public Estado() {}
	
	public Estado(long id,Empleado modificadoPor,boolean iniciado,boolean enCurso,boolean finalizado,long idTarea) {
		this.id = id;
		this.modificadoPor = modificadoPor;
		this.iniciado = iniciado; 
		this.enCurso = enCurso;
		this.finalizado = finalizado;
		this.idTarea = idTarea;
	}
	
	public Estado(Empleado modificadoPor,boolean iniciado,boolean enCurso,boolean finalizado,long idTarea) {
		this.modificadoPor = modificadoPor;
		this.iniciado = iniciado; 
		this.enCurso = enCurso;
		this.finalizado = finalizado;
		this.idTarea = idTarea;
	}
	
	public String stringifyEstado(Tarea t) {
			
		String estado = null;
		
		if(t.getEstado().estaIniciado()) {
			estado = "Iniciado";
		} else if(t.getEstado().estaEnCurso()) {
			estado = "En curso";
		} else if(t.getEstado().estaFinalizado()) {
			estado = "Finalizado";
		}
		
		return estado;
	}
	
	public long getId() {
		return id;
	}
	
	public Empleado getModificadoPor() {
		return modificadoPor;
	}
	
	public long getIdTarea() {
		return idTarea;
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
