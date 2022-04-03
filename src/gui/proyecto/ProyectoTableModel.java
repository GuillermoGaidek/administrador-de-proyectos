package gui.proyecto;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import logica.model.Proyecto;
import logica.model.Tarea;

public class ProyectoTableModel extends AbstractTableModel {

	//INDICES DE LAS COLUMNAS
	private static final int COLUMNA_ID = 0;
	private static final int COLUMNA_TITULO = 1;
	
	private List<Proyecto> filas;
	
	private String[] nombresColumnas = {"Id","Titulo"};
	
	private Class[] tiposColumnas = {Long.class, String.class};
	
	public ProyectoTableModel() {
		filas = new ArrayList<Proyecto>();
	}
	
	//CONSTRUCTOR CON CONTENIDO INICIAL
	public ProyectoTableModel(List<Proyecto> contenidoInicial) {
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
		
		Proyecto P = filas.get(rowIndex);
		
		Object result = null;
		switch(columnIndex) {
		case COLUMNA_ID:
			result = P.getId();
			break;
		case COLUMNA_TITULO:
			result = P.getTitulo();
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

    public List<Proyecto> getFilas() {
    	return filas;
    }

    public void setFilas(List<Proyecto> filas) {
    	this.filas = filas;
    }
    
    
}
