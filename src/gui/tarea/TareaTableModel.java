package gui.tarea;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import logica.model.Tarea;

public class TareaTableModel extends AbstractTableModel {
	
	//INDICES DE LAS COLUMNAS
	private static final int COLUMNA_ID = 0;
	private static final int COLUMNA_TITULO = 1;
	private static final int COLUMNA_DESCRIPCION = 2;
	private static final int COLUMNA_HORAS_ESTIMADAS = 3;
	private static final int COLUMNA_HORAS_REALES = 4;
	private static final int COLUMNA_ID_EMPLEADO = 5;
	private static final int COLUMNA_ID_ESTADO = 6;
	private static final int COLUMNA_ID_PROYECTO = 7;
	
	private List<Tarea> filas;
	
	private String[] nombresColumnas = {"Id","Titulo", "Descripcion", "Horas Estimadas", "Horas Reales",
										"Id Empleado","Id Estado","Id Proyecto"};
	
	private Class[] tiposColumnas = {Integer.class, String.class, String.class, Integer.class, Integer.class,
									 Integer.class,Integer.class,Integer.class};
	
	public TareaTableModel() {
		filas = new ArrayList<Tarea>();
	}
	
	//CONSTRUCTOR CON CONTENIDO INICIAL
	public TareaTableModel(List<Tarea> contenidoInicial) {
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
		
		Tarea T = filas.get(rowIndex);
		
		Object result = null;
		switch(columnIndex) {
		case COLUMNA_ID:
			result = T.getId();
			break;
		case COLUMNA_TITULO:
			result = T.getTitulo();
			break;
		case COLUMNA_DESCRIPCION:
			result = T.getDescripcion();
			break;
		case COLUMNA_HORAS_ESTIMADAS:
			result = T.getHorasEstimadas();
			break;
		case COLUMNA_HORAS_REALES:
			result = T.getHorasReales();
			break;
		case COLUMNA_ID_EMPLEADO:
			result = T.getEmpleado();
			break;
		case COLUMNA_ID_ESTADO:
			result = T.getEstado();
			break;
		case COLUMNA_ID_PROYECTO:
			result = T.getProyecto();
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

    public List<Tarea> getFilas() {
    	return filas;
    }

    public void setFilas(List<Tarea> filas) {
    	this.filas = filas;
    }
}
