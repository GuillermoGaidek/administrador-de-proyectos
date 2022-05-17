package gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import logica.model.Empleado;

public class Combo{
		
	public static final String ESTADO_FINALIZADO = "FINALIZADO";
	public static final String ESTADO_EN_CURSO = "EN CURSO";
	public static final String ESTADO_INICIADO = "INICIADO";
	
	
	public static JComboBox getComboEmpleadosLibres(List<Empleado> empleados,Long idProyecto) {
		JComboBox ComboConEmpleadosLibres = new JComboBox(filtrarEmpleadosLibres(empleados,idProyecto));
		ComboConEmpleadosLibres.setEditable(false);
		return ComboConEmpleadosLibres;
	}
	
	public static JComboBox getComboEstados() {
		String[] estados = {ESTADO_FINALIZADO, ESTADO_EN_CURSO, ESTADO_INICIADO}; 
		JComboBox comboEstados = new JComboBox(estados);
		comboEstados.setEditable(false);
		return comboEstados;
	}
	
	public static JComboBox getComboEmpleadosDelProyecto(List<Empleado> empleados,Long idProyecto) {
		JComboBox ComboConEmpleadosLibres = new JComboBox(filtrarEmpleadosProyecto(empleados,idProyecto));
		ComboConEmpleadosLibres.setEditable(false);
		return ComboConEmpleadosLibres;
	}

	public static Object[] filtrarEmpleadosLibres(List<Empleado> empleados,Long idProyecto) {
		List<Long> empleadosFiltrados = new ArrayList<Long>();
		if (empleados.isEmpty()) {
			empleadosFiltrados.add((long) 0);
			return empleadosFiltrados.toArray();
		}
		for(Empleado emp : empleados) {
			if(emp.getProyecto() == null || emp.getProyecto().getId() == idProyecto) {
				empleadosFiltrados.add((long)emp.getDni());
			}
		}
		if (empleadosFiltrados.isEmpty()) empleadosFiltrados.add((long) 0);
		
		return empleadosFiltrados.toArray();
	}
	
	private static Object[] filtrarEmpleadosProyecto(List<Empleado> empleados,Long idProyecto) {
		List<Long> empleadosFiltrados = new ArrayList<Long>();
		if (empleados.isEmpty()) {
			empleadosFiltrados.add((long) 0);
			return empleadosFiltrados.toArray();
		} 
		for(Empleado emp : empleados) {
			if(emp.getProyecto() != null && emp.getProyecto().getId() == idProyecto) {
				empleadosFiltrados.add((long)emp.getDni());
			}
		}
		if (empleadosFiltrados.isEmpty()) empleadosFiltrados.add((long) 0);
		return empleadosFiltrados.toArray();
	}
	
}
