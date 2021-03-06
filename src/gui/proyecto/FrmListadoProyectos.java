package gui.proyecto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

import gui.BotoneraCrud;
import gui.empleado.FrmListadoEmpleados;
import gui.tarea.FrmListadoTareas;
import gui.tarea.FrmTarea;
import gui.tarea.TareaTableModel;
import logica.excepciones.EmpleadoYaAsignadoException;
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

public class FrmListadoProyectos extends JFrame implements ActionListener{
	
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Tarea> tareaService = new GenericService<Tarea>(new TareaDAOH2Impl());
	GenericService<Estado> estadoService = new GenericService<Estado>(new EstadoDAOH2Impl());
	
	private JTable tabla;
	private ProyectoTableModel modelo;
	private JScrollPane scrollPaneParaTabla;
	private JLabel LblTitulo;
	private BotoneraCrud botoneraCrud = new BotoneraCrud();
	JButton botonVerProyecto;
	JButton botonPoolEmpleados;
	
	public FrmListadoProyectos() {
		
		// setea titulo ventana
		this.setTitle("Administrador de Proyectos");

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
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		
		LblTitulo = new JLabel("Listado de proyectos", SwingConstants.CENTER);
		
		panelPrincipal.add(LblTitulo, BorderLayout.NORTH);
		
		modelo = new ProyectoTableModel();
		tabla = new JTable(modelo);
		cargarTabla();
		scrollPaneParaTabla = new JScrollPane(tabla);
		
		panelPrincipal.add(scrollPaneParaTabla, BorderLayout.CENTER);
		
		
		JPanel panelSouth = new JPanel(new FlowLayout());
		JPanel panelWest = new JPanel(new BorderLayout());
		JPanel panelEast = new JPanel(new BorderLayout());
		
		botonVerProyecto = new JButton("Ver Proyecto");
		botonVerProyecto.addActionListener(this);
		botonPoolEmpleados = new JButton("Pool Empleados");
		botonPoolEmpleados.addActionListener(this);
		
		panelWest.add(botonPoolEmpleados);
		panelEast.add(botoneraCrud.GetPanelBotones(this),BorderLayout.CENTER);
		panelEast.add(botonVerProyecto,BorderLayout.NORTH);
		
		panelSouth.add(panelWest);
		panelSouth.add(panelEast);
		
				
		panelPrincipal.add(panelSouth, BorderLayout.SOUTH);
		
		return panelPrincipal;
	}
	
	public void cargarTabla() {
		List<Proyecto> lista;
		try {
			lista = proyectoService.listar();
			modelo.setFilas(lista);
			modelo.fireTableDataChanged();	
		} catch (ServicioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					"Cargar Proyecto",JOptionPane.ERROR_MESSAGE);
		}
	}	

	public void actionPerformed(ActionEvent e){
		int fila;
		if(e.getSource() == botoneraCrud.botonAgregar) {
			new FrmProyecto(-1, this);
		} else if(e.getSource() == botoneraCrud.botonModificar) {
			if(this.tabla.getSelectedRow() != -1) {
				fila = this.tabla.getSelectedRow();
				long idProyecto = (long)this.tabla.getValueAt(fila, 0);
				new FrmProyecto(idProyecto, this);	
			} else {
				JOptionPane.showMessageDialog(this, "No selecciono ningun proyecto",
												"Modificar",JOptionPane.ERROR_MESSAGE);
			}	
		} else if(e.getSource() == botoneraCrud.botonBorrar) {
			try {
				if(this.tabla.getSelectedRow() != -1) {
					fila = this.tabla.getSelectedRow();
					long id = (long)this.tabla.getValueAt(fila, 0);
					Proyecto p = proyectoService.getById(id);
					borrarProyectoEnCascada(p);
					cargarTabla();		
				} else {
					JOptionPane.showMessageDialog(this, "No selecciono ningun proyecto", "Borrar",
					        JOptionPane.ERROR_MESSAGE);
				}
			} catch(EmpleadoYaAsignadoException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(),
						"Borrar",JOptionPane.ERROR_MESSAGE);
			} catch(ServicioException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(),
						"Borrar",JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == botonVerProyecto) {
			if(this.tabla.getSelectedRow() != -1) {
				fila = this.tabla.getSelectedRow();
				long id = (long)this.tabla.getValueAt(fila, 0);
				new VerProyecto(id);
			} else {
				JOptionPane.showMessageDialog(this, "No selecciono ningun proyecto", "Ver Proyecto",
				        JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == botonPoolEmpleados) {
			new FrmListadoEmpleados();
			}
		}
	
	private void borrarProyectoEnCascada(Proyecto proy) throws ServicioException, EmpleadoYaAsignadoException {
		List<Tarea> listaTareas = tareaService.listarById(proy.getId());
		for(Tarea t : listaTareas) {
			List<Estado> listaEstados = estadoService.listarById(t.getId());
			for(Estado e : listaEstados) {
				estadoService.borrar(e);
			}
			tareaService.borrar(t);
		}
		List<Empleado> listaEmpleados = empleadoService.listarById(proy.getId());
		for(Empleado emp: listaEmpleados) {
			emp.setProyecto(null);
		}
		
		proyectoService.borrar(proy);
	}
	
}
