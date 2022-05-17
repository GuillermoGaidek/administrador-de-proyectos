package logica.model;

import java.time.ZonedDateTime;

import gui.Combo;

public class Estado {
	
	private boolean iniciado;
	private boolean enCurso;
	private boolean finalizado;
	private long id;
	private Empleado responsable;
	private long idTarea;
	private ZonedDateTime fechaModificacion;
	
	public Estado() {}
	
	public Estado(long id,Empleado responsable,boolean iniciado,boolean enCurso,boolean finalizado,long idTarea,ZonedDateTime fechaModificacion) {
		this.id = id;
		this.responsable = responsable;
		this.iniciado = iniciado; 
		this.enCurso = enCurso;
		this.finalizado = finalizado;
		this.idTarea = idTarea;
		this.fechaModificacion = fechaModificacion;
	}
	
	public Estado(Empleado responsable,boolean iniciado,boolean enCurso,boolean finalizado,long idTarea,ZonedDateTime fechaModificacion) {
		this.responsable = responsable;
		this.iniciado = iniciado; 
		this.enCurso = enCurso;
		this.finalizado = finalizado;
		this.idTarea = idTarea;
		this.fechaModificacion = fechaModificacion;
	}
	
	public String stringifyEstado(Estado estado) {
			
		String estadoString = null;
		
		if(estado.estaIniciado()) {
			estadoString = "INICIADO";
		} else if(estado.estaEnCurso()) {
			estadoString = "EN CURSO";
		} else if(estado.estaFinalizado()) {
			estadoString = "FINALIZADO";
		}
		
		return estadoString;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Empleado getResponsable() {
		return responsable;
	}
	
	public long getIdTarea() {
		return idTarea;
	}
	
	public void setIdTarea(long idTarea) {
		this.idTarea = idTarea;
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

	public ZonedDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(ZonedDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
}
