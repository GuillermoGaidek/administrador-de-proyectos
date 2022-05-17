package gui.empleado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import gui.BotoneraCrud;
import gui.tarea.FrmTarea;
import gui.tarea.TareaTableModel;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;
import persistencia.dao.TareaDAOH2Impl;

public class FrmListadoEmpleados extends JFrame implements ActionListener{
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	private JTable tabla;
	private EmpleadoTableModel modelo;
	private JScrollPane scrollPaneParaTabla;
	private JLabel LblTitulo;
	private BotoneraCrud botoneraCrud = new BotoneraCrud();
	private long idProyecto;
	boolean allEmpleados = true;
	
	public FrmListadoEmpleados(long idProyecto,boolean allEmpleados) {
		this.idProyecto = idProyecto;
		this.allEmpleados = allEmpleados;
	}
	
	public FrmListadoEmpleados() {
		// setea titulo ventana
		this.setTitle("Empleados");

		// se cierra la ventana al hacer click en el boton X
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// setear panel de la ventana
		this.setContentPane(GetPanelPrincipal());

		// compacta las componentes de la ventana
		this.pack();
		
		// alilnea la ventana en el medio de la pantalla
		this.setLocationRelativeTo(null);

		// muestra la ventana
		this.setVisible(true);
		
	}
	
	public JPanel GetPanelPrincipal() {
		JPanel panel = new JPanel(new BorderLayout());
		
		String t = allEmpleados ? "Listado de Empleados" : null;
		
		LblTitulo = new JLabel(t, SwingConstants.CENTER);
		panel.add(LblTitulo, BorderLayout.NORTH);
		
		modelo = new EmpleadoTableModel();
		tabla = new JTable(modelo);
		cargarTabla();
		scrollPaneParaTabla = new JScrollPane(tabla);
		panel.add(scrollPaneParaTabla, BorderLayout.CENTER);
		
		if(allEmpleados) {
			panel.add(botoneraCrud.GetPanelBotones(this), BorderLayout.SOUTH);
		}else{
			panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2, 2,
			        2, 2, Color.black), "EMPLEADOS", TitledBorder.LEFT, TitledBorder.TOP));
		}
		panel.setPreferredSize(new Dimension(540, 240));
		
		return panel;
	}
	
	public void cargarTabla() {
		List<Empleado> lista;
		try {
			if(allEmpleados) {
				lista = empleadoService.listar();
			}else {
				lista = empleadoService.listarById(idProyecto);
			}
			modelo.setFilas(lista);
			modelo.fireTableDataChanged();	
		} catch (ServicioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					"Cargar Tabla",JOptionPane.ERROR_MESSAGE);
		}
	}	

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == botoneraCrud.botonAgregar) {
			new FrmEmpleado(-1, this);
		} else if (e.getSource() == botoneraCrud.botonModificar) {
			if(this.tabla.getSelectedRow() != -1) {
				int fila = this.tabla.getSelectedRow();
				long idTarea = (long)this.tabla.getValueAt(fila, 0);
				new FrmEmpleado(idTarea, this);	
			} else {
				JOptionPane.showMessageDialog(this, "No selecciono ningun empleado",
												"Modificar",JOptionPane.ERROR_MESSAGE);
			}	
		} else if (e.getSource() == botoneraCrud.botonBorrar) {
			try {
				if(this.tabla.getSelectedRow() != -1) {
					int fila = this.tabla.getSelectedRow();
					long dni = (long)this.tabla.getValueAt(fila, 0);
					Empleado emp = new Empleado();
					emp.setDni(dni);
					empleadoService.borrar(emp);
					cargarTabla();		
				} else {
					JOptionPane.showMessageDialog(this, "No selecciono ningun empleado", "Borrar",
					        JOptionPane.ERROR_MESSAGE);
				}
			}catch(ServicioException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(),
						"Borrar",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
}
