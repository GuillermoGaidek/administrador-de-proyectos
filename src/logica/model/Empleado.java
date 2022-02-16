package logica.model;

public class Empleado {
	
	private final long dni;
	private long costoPorHora;
	private boolean libre = true;
	private Proyecto proyecto;
	
	public Empleado(long dni, long costoPorHora) {
		this.dni = dni;
		this.costoPorHora = costoPorHora;
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
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", costoPorHora=" + costoPorHora + ", libre=" + libre + ", proyecto=" + proyecto
				+ "]";
	}	
	
}
