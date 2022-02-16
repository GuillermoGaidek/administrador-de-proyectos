package logica.main;

import logica.excepciones.ServicioException;
import gui.tarea.FrmListadoTareas;
import logica.model.Empleado;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;

public class Main {

	public static void main(String[] args) {
		new FrmListadoTareas();
		
	}
	
}



/*GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
try {
	empleadoService.crear(new Empleado(93930313,10));
} catch (ServicioException e) {
	// TODO Auto-generated catch block
	System.out.println(e.getMessage());
	e.printStackTrace();
}*/