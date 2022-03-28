package gui.tarea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.sun.javafx.tk.Toolkit;

import gui.BotoneraCrud;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Estado;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;
import persistencia.dao.EstadoDAOH2Impl;
import persistencia.dao.TareaDAOH2Impl;

public class FrmListadoTareas extends JFrame implements ActionListener {
	
	GenericService<Tarea> tareaService = new GenericService<Tarea>(new TareaDAOH2Impl());
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	private JTable tabla;
	private TareaTableModel modelo;
	private JScrollPane scrollPaneParaTabla;
	private JLabel LblTitulo;
	private BotoneraCrud botoneraCrud = new BotoneraCrud();
	
	public FrmListadoTareas() {
		
		// setea titulo ventana
		this.setTitle("Tarea");

		// se cierra la ventana al hacer click en el boton X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setear panel de la ventana
		this.setContentPane(GetPanelPrincipal());

		// compacta las componentes de la ventana
		this.pack();
		
		// alilnea la ventana en el medio de la pantalla
		this.setLocationRelativeTo(null);

		// muestra la ventana
		this.setVisible(true);
		
	}
	
	private JPanel GetPanelPrincipal() {
		JPanel panel = new JPanel(new BorderLayout());
		
		LblTitulo = new JLabel("Listado de tareas", SwingConstants.CENTER);
		panel.add(LblTitulo, BorderLayout.NORTH);
		
		modelo = new TareaTableModel();
		tabla = new JTable(modelo);
		cargarTabla();
		scrollPaneParaTabla = new JScrollPane(tabla);
		panel.add(scrollPaneParaTabla, BorderLayout.CENTER);
		
		panel.add(botoneraCrud.GetPanelBotones(this), BorderLayout.SOUTH);
		
		return panel;
	}
	
	public void cargarTabla() {
		List<Tarea> lista;
		try {
			lista = tareaService.listar();
			modelo.setFilas(lista);
			modelo.fireTableDataChanged();	
		} catch (ServicioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					"Cargar Tabla",JOptionPane.ERROR_MESSAGE);
		}
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
				JOptionPane.showMessageDialog(this, "No selecciono ninguna tarea",
												"Modificar",JOptionPane.ERROR_MESSAGE);
			}	
		} else if (e.getSource() == botoneraCrud.botonBorrar) {
			try {
				if(this.tabla.getSelectedRow() != -1) {
					int fila = this.tabla.getSelectedRow();
					long id = (long)this.tabla.getValueAt(fila, 0);
					Tarea t = new Tarea();
					t.setId(id);
					Empleado emp = tareaService.getById(id).getEmpleado();
					emp.setLibre(true);
					empleadoService.modificar(emp);
					tareaService.borrar(t);
					cargarTabla();		
				} else {
					JOptionPane.showMessageDialog(this, "No selecciono ninguna tarea", "Borrar",
					        JOptionPane.ERROR_MESSAGE);
				}
			}catch(ServicioException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(),
						"Borrar",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
