package gui.estado;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import logica.excepciones.ServicioException;
import logica.model.Estado;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.dao.TareaDAOH2Impl;

public class EstadoTableModel extends AbstractTableModel {
	
	//INDICES DE LAS COLUMNAS
	private static final int COLUMNA_ID = 0;
	private static final int COLUMNA_ID_EMPLEADO = 1;
	private static final int COLUMNA_ESTADO = 2;
	private static final int COLUMNA_TAREA = 3;
	private static final int COLUMNA_FECHA_MODIFICACION = 4;
	
	GenericService<Tarea> tareaService = new GenericService<Tarea>(new TareaDAOH2Impl());
	private List<Estado> filas;
	
	private String[] nombresColumnas = {"Id","Responsable", "Estado", "Tarea", "Fecha modificacion"};
	
	private Class[] tiposColumnas = {Long.class, String.class, String.class, Long.class, ZonedDateTime.class};
	
	public EstadoTableModel() {
		filas = new ArrayList<Estado>();
	}
	
	//CONSTRUCTOR CON CONTENIDO INICIAL
	public EstadoTableModel(List<Estado> contenidoInicial) {
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
		
		Estado estado = filas.get(rowIndex);
		
		Object result = null;
		switch(columnIndex) {
		case COLUMNA_ID:
			result = estado.getId();
			break;
		case COLUMNA_ID_EMPLEADO:
			result = estado.getResponsable() == null ? "-" : estado.getResponsable().getDni();
			break;
		case COLUMNA_ESTADO:
			result = estado.stringifyEstado(estado);
			break;
		case COLUMNA_TAREA:
			try {
				result = tareaService.getById(estado.getIdTarea()).getTitulo();
			} catch (ServicioException e) {
				JOptionPane.showMessageDialog(new JFrame() , e.getMessage(),
						"Estados",JOptionPane.ERROR_MESSAGE);
			}
			break;
		case COLUMNA_FECHA_MODIFICACION:
			result = estado.getFechaModificacion().toString();
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

    public List<Estado> getFilas() {
    	return filas;
    }

    public void setFilas(List<Estado> filas) {
    	this.filas = filas;
    }
}
