package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Tarea;

public class TareaTableModel extends AbstractTableModel {
	
	//INDICES DE MIS COLUMNAS
	private static final int COLUMNA_ID= 0;
	private static final int COLUMNA_TITULO= 1;
	private static final int COLUMNA_DESCRIPCION = 2;
	private static final int COLUMNA_HORAS_ESTIMADAS = 3;
	private static final int COLUMNA_HORAS_REALES = 4;
	
	
	// NOMBRES DE LOS ENCABEZADOS
	private String[] nombresColumnas = {"Id","Título", "Descripción", "Horas Estimadas", "Horas Reales"};
	
	//TIPOS DE CADA COLUMNA (EN EL MISMO ORDEN DE LOS ENCABEZADOS)
	private Class[] tiposColumnas = {Integer.class, String.class, String.class, Integer.class, Integer.class};


	private List<Tarea> filas;
	
	// CONSTRUCTOR VACIO
	public TareaTableModel() {
		filas = new ArrayList<Tarea>();
	}
	
	//CONSTRUCTOR CON CONTENIDO INICIAL
	public TareaTableModel(List<Tarea> contenidoInicial) {
		filas = contenidoInicial;
	}

	
	//METODO QUE HAY QUE PISAR
	public int getColumnCount() {
		return nombresColumnas.length;
		}

	
	//OTRO METODO QUE HAY QUE PISAR
	public int getRowCount() {
	return filas.size();
	}

	
	// METODO QUE HAY QUE PISAR
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		super.setValueAt(value, rowIndex, columnIndex);
	}
	

	//METODO QUE HAY QUE PISAR
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
		default:
			result = new String("");
		}
		
		return result;
	}

	//METODO QUE HAY QUE PISAR
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
