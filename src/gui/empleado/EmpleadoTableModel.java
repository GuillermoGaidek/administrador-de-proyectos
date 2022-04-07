package gui.empleado;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import logica.model.Empleado;
import logica.model.Proyecto;
import logica.model.Tarea;

public class EmpleadoTableModel extends AbstractTableModel{
	
	//INDICES DE LAS COLUMNAS
	private static final int COLUMNA_DNI = 0;
	private static final int COLUMNA_COSTO_POR_HORA = 1;
	private static final int COLUMNA_LIBRE = 2;
	private static final int COLUMNA_ID_PROYECTO = 3;
	
	private List<Empleado> filas;
	
	private String[] nombresColumnas = {"DNI","Costo por hora (AR$)", "Libre", "Proyecto"};
	
	private Class[] tiposColumnas = {Long.class, Integer.class, String.class, Long.class};
	
	public EmpleadoTableModel() {
		filas = new ArrayList<Empleado>();
	}
	
	//CONSTRUCTOR CON CONTENIDO INICIAL
	public EmpleadoTableModel(List<Empleado> contenidoInicial) {
		filas = contenidoInicial;
	}
	
	@Override
	public int getColumnCount() {
		return nombresColumnas.length;
		}
	
	@Override
	public int getRowCount() {
		return filas.size();
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		super.setValueAt(value, rowIndex, columnIndex);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Empleado E = filas.get(rowIndex);
		
		Object result = null;
		switch(columnIndex) {
			case COLUMNA_DNI:
				result = E.getDni();
				break;
			case COLUMNA_COSTO_POR_HORA:
				result = E.getCostoPorHora();
				break;
			case COLUMNA_LIBRE:
				result = E.estaLibre()? "SI": "NO";
				break;
			case COLUMNA_ID_PROYECTO:
				result = E.getProyecto() == null ? "-" : E.getProyecto().getTitulo();
				break;
			default:
				result = new String("");
		}
				
		return result;
	}

	@Override
	public String getColumnName(int col) {
		return nombresColumnas[col];
    }

    public List<Empleado> getFilas() {
    	return filas;
    }

    public void setFilas(List<Empleado> filas) {
    	this.filas = filas;
    }

}
