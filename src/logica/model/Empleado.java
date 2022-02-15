package logica.model;

public class Empleado {
	
	private long dni;
	private long costoPorHora;
	private boolean libre = true;
	
	public Empleado(long dni, long costoPorHora) {
		this.dni = dni;
		this.costoPorHora = costoPorHora;
	}
	
	public long costoPorHora(int horas) {
		return this.costoPorHora * horas;
	}
	
	public boolean estaLibre() {
		return libre;
	}

	public void pasarALibre() {
		this.libre = true;
	}
	
	public void pasarAOcupado() {
		this.libre = false;
	}
	
	public long getDni() {
		return dni;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", costoPorHora=" + costoPorHora + ", libre=" + libre + "]";
	}
	
	
	
}
