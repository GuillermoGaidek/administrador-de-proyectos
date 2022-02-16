package logica.model.estados;

import logica.model.Empleado;

public class Estado {
	
	protected long id;
	protected Empleado responsable;
	
	public Estado(long id,Empleado responsable) {
		this.id = id;
		this.responsable = responsable;
	}
	
	public long getId() {
		return id;
	}
	
	public Empleado getResponsable() {
		return responsable;
	}
}
