package gui.tarea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.sun.javafx.tk.Toolkit;

import gui.BotoneraCrud;
import gui.estado.ListadoEstados;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Estado;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;
import persistencia.dao.EstadoDAOH2Impl;
import persistencia.dao.TareaDAOH2Impl;

public class FrmListadoTareas implements ActionListener {
	
	GenericService<Tarea> tareaService = new GenericService<Tarea>(new TareaDAOH2Impl());
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Estado> estadoService = new GenericService<Estado>(new EstadoDAOH2Impl());
	
	private JTable tabla;
	private TareaTableModel modelo;
	private JScrollPane scrollPaneParaTabla;
	private JLabel LblTitulo;
	private JButton BtnEstado;
	private BotoneraCrud botoneraCrud = new BotoneraCrud();
	private long idProyecto;

	public FrmListadoTareas(long idProyecto) {
		// asigna el proyecto seleccionado
		this.idProyecto = idProyecto;
	}
	
	public JPanel GetPanelPrincipal() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		JPanel panelSouth = new JPanel(new FlowLayout());
		
		modelo = new TareaTableModel();
		tabla = new JTable(modelo);
		cargarTabla();
		scrollPaneParaTabla = new JScrollPane(tabla);
		panelPrincipal.add(scrollPaneParaTabla, BorderLayout.CENTER);
		
		panelSouth.add(botoneraCrud.GetPanelBotones(this));
		BtnEstado = new JButton("Historial Estados");
		BtnEstado.addActionListener(this);
		panelSouth.add(BtnEstado);
		
		panelPrincipal.add(panelSouth,BorderLayout.SOUTH);
		panelPrincipal.setPreferredSize(new Dimension(340, 240));
		panelPrincipal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2, 2,
		        2, 2, Color.black), "TAREAS", TitledBorder.LEFT, TitledBorder.TOP));
		
		return panelPrincipal;
	}
	
	public void cargarTabla() {
		List<Tarea> lista;
		try {
			lista = tareaService.listarById(idProyecto);
			modelo.setFilas(lista);
			modelo.fireTableDataChanged();	
		} catch (ServicioException e) {
			JOptionPane.showMessageDialog(new JFrame() , e.getMessage(),
					"Cargar Tarea",JOptionPane.ERROR_MESSAGE);
			
		}
	}	
	
	public long getIdProyecto() {
		return idProyecto;
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == botoneraCrud.botonAgregar) {
			new FrmTarea(-1, this);
		} else if (e.getSource() == botoneraCrud.botonModificar) {
			if(this.tabla.getSelectedRow() != -1) {
				int fila = this.tabla.getSelectedRow();
				long idTarea = (long)this.tabla.getValueAt(fila, 0);
				new FrmTarea(idTarea, this);	
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "No selecciono ninguna tarea",
												"Modificar",JOptionPane.ERROR_MESSAGE);
			}	
		} else if (e.getSource() == botoneraCrud.botonBorrar) {
			try {
				if(this.tabla.getSelectedRow() != -1) {
					int fila = this.tabla.getSelectedRow();
					long id = (long)this.tabla.getValueAt(fila, 0);
					Tarea t = tareaService.getById(id);
					borrarTareasEnCascada(t);	
					cargarTabla();		
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "No selecciono ninguna tarea", "Borrar",
					        JOptionPane.ERROR_MESSAGE);
				}
			}catch(ServicioException ex) {
				JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(),
						"Borrar",JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == BtnEstado) {
			if(this.tabla.getSelectedRow() != -1) {
				int fila = this.tabla.getSelectedRow();
				long idTarea = (long)this.tabla.getValueAt(fila, 0);
				new ListadoEstados(idTarea);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "No selecciono ninguna tarea",
						"Historial Estados",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void borrarTareasEnCascada(Tarea t) throws ServicioException {
		List<Estado> listaEstados = estadoService.listarById(t.getId());
		for(Estado e : listaEstados) {
			estadoService.borrar(e);
		}
		tareaService.borrar(t);
	}
	
}
