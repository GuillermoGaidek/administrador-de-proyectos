package gui.proyecto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.AsignacionEmpleados;
import gui.empleado.EmpleadoTableModel;
import gui.tarea.FrmListadoTareas;
import logica.excepciones.EmpleadoNoDisponibleException;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Estado;
import logica.model.Proyecto;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;
import persistencia.dao.EstadoDAOH2Impl;
import persistencia.dao.ProyectoDAOH2Impl;
import persistencia.dao.TareaDAOH2Impl;

public class FrmProyecto  extends JFrame implements ActionListener {

	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	GenericService<Estado> estadoService = new GenericService<Estado>(new EstadoDAOH2Impl());
		
	private JLabel LblTituloVentana;
	private JLabel LblTitulo;	
	private JLabel LblTituloEmpleados;
	private JLabel LblEmpleado;
	private JTextField TxtTitulo;
	private JTextField TxtEmpleado;
	private JTable tabla;
	private EmpleadoTableModel modelo;
	private JScrollPane scrollPaneParaTabla;
	private JButton BtnGuardar;
	private JButton BtnAsignacionEmpleados;
	private FrmListadoProyectos frm;
	private long idProyecto;
	Proyecto proyecto;
	List<Empleado> ListaEmpleados = new ArrayList<Empleado>();
	
	public FrmProyecto(long idProyecto, FrmListadoProyectos frm) {

		this.idProyecto = idProyecto;
		this.frm = frm;
		
		// setea titulo ventana
		this.setTitle("Proyecto");

		// cerrar programa
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// setear panel de la ventana
		this.setContentPane(GetPanelPrincipal());

		// compacta las componentes de la ventana
		this.pack();
		
		// alilnea la ventana en el medio de la pantalla
		this.setLocationRelativeTo(null);

		// muestra la ventana
		this.setVisible(true);

		if(idProyecto != -1) {
			LlenarForm();
		}
	}
	
	private JPanel GetPanelPrincipal() {
		JPanel panel = new JPanel(new BorderLayout());
		
		LblTituloVentana = new JLabel("Complete los campos", SwingConstants.CENTER);
		panel.add(LblTituloVentana, BorderLayout.NORTH);

		//Parte central con campos a completar y listado de empleados
		JPanel panelCentral = new JPanel(new BorderLayout());
		
		JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
		JLabel LblTitulo = new JLabel("Titulo");
		panelCampos.add(LblTitulo);
		TxtTitulo = new JTextField("", 20);
		panelCampos.add(TxtTitulo);
		panelCentral.add(panelCampos,BorderLayout.NORTH);
		
		JPanel panelEmpleados = new JPanel(new BorderLayout());
		LblTituloEmpleados = new JLabel("Listado de empleados", SwingConstants.CENTER);
		panelEmpleados.add(LblTituloEmpleados, BorderLayout.NORTH);
		modelo = new EmpleadoTableModel();
		tabla = new JTable(modelo);
		scrollPaneParaTabla = new JScrollPane(tabla);
		panelEmpleados.add(scrollPaneParaTabla,BorderLayout.SOUTH);
		panelCentral.add(panelEmpleados,BorderLayout.SOUTH);
		
		panel.add(panelCentral, BorderLayout.CENTER);
		//fin parte central

		JPanel panelBotones = new JPanel(new FlowLayout());
		BtnAsignacionEmpleados = new JButton("Asignacion Empleados");
		BtnAsignacionEmpleados.addActionListener(this);
		panelBotones.add(BtnAsignacionEmpleados);
		BtnGuardar = new JButton("Guardar");
		BtnGuardar.addActionListener(this);
		panelBotones.add(BtnGuardar);
		panel.add(panelBotones, BorderLayout.SOUTH);

		return panel;
	}
	
	private void LlenarForm() {
		try {
			proyecto = proyectoService.getById(idProyecto);
			
			TxtTitulo.setText(proyecto.getTitulo());
		} catch (ServicioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Proyecto",
			        JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void cargarTabla(Empleado emp,boolean asignar) throws EmpleadoNoDisponibleException {
		if(asignar) {
			if(ListaEmpleados.contains(emp)) throw new EmpleadoNoDisponibleException("Ya se asigno ese empleado.");
			else ListaEmpleados.add(emp);
		} 
		else ListaEmpleados.remove(emp);
		
		modelo.setFilas(ListaEmpleados);
		modelo.fireTableDataChanged();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == BtnAsignacionEmpleados){
			new AsignacionEmpleados(idProyecto,this);
		} else {
			if(TxtTitulo.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El titulo no puede estar vacio. Vuelva a intentar nuevamente", "Proyecto",
				        JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					if(proyecto == null) proyecto = new Proyecto();
					
					proyecto.setTitulo(TxtTitulo.getText());
					
					if(idProyecto == -1) {
						proyectoService.crear(proyecto);
						//persisitrEmpleados();
					} else {
						proyectoService.modificar(proyecto);
					}
					frm.cargarTabla();
					this.setVisible(false);
					dispose();
				} catch (ServicioException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "FrmProyecto",
					        JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Algo salio mal: " + ex.getMessage(), "FrmProyecto",
					        JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}	

	
	
}
