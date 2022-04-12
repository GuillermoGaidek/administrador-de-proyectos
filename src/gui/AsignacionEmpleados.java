package gui;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.proyecto.FrmListadoProyectos;
import gui.proyecto.FrmProyecto;
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Estado;
import logica.model.Proyecto;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;
import persistencia.dao.EstadoDAOH2Impl;
import persistencia.dao.ProyectoDAOH2Impl;

public class AsignacionEmpleados extends JFrame implements ActionListener{
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	
	private JLabel LblTituloVentana;	
	private JLabel LblEmpleado;
	private JTextField TxtEmpleado;
	private JButton BtnAsignar;
	private JButton BtnDesasignar;
	private FrmProyecto frm;
	private long idProyecto;
	private boolean asignar = true;
	
	public AsignacionEmpleados(long idProyecto, FrmProyecto frm) {
		
		this.idProyecto = idProyecto;
		this.frm = frm;
		
		// setea titulo ventana
		this.setTitle("Asignar empleado");

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

	}
	
	private JPanel GetPanelPrincipal() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		LblTituloVentana = new JLabel("Seleccione el empleado", SwingConstants.CENTER);
		panel.add(LblTituloVentana, BorderLayout.NORTH);
		
		JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
		
		LblEmpleado = new JLabel("Empleado");
		panelCampos.add(LblEmpleado);
		
		TxtEmpleado = new JTextField("", 20);
		panelCampos.add(TxtEmpleado);
		
		panel.add(panelCampos, BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel(new FlowLayout());
		BtnAsignar = new JButton("Agregar");
		BtnAsignar.addActionListener(this);
		BtnDesasignar = new JButton("Eliminar");
		BtnDesasignar.addActionListener(this);
		panelBotones.add(BtnAsignar);
		panelBotones.add(BtnDesasignar);
		
		panel.add(panelBotones, BorderLayout.SOUTH);

		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(TxtEmpleado.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El campo no puede estar vacio. Vuelva a intentar nuevamente", "Asignacion de empleados",
			        JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				Empleado emp = empleadoService.getById(Long.parseLong(TxtEmpleado.getText()));
				
				if(idProyecto == -1) { //Si el proyecto no existe cargo en la lista en memoria
					if(e.getSource() == BtnAsignar) {
						frm.cargarTabla(emp,asignar);
					} else {
						frm.cargarTabla(emp,!asignar);
					}
					
				} else { // Si ya existe modifico en la BD
					Proyecto proy = proyectoService.getById(idProyecto);
					
					if(e.getSource() == BtnAsignar) {
						emp.setProyecto(proy);
					} else {
						emp.setProyecto(null);
					}
					
					empleadoService.modificar(emp);
					
					//frm.cargarTabla();
				}
				
				this.setVisible(false);
				dispose();
			} catch (ServicioException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Asignacion de empleados",
				        JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Algo salio mal: " + ex.getMessage(), "Asignacion de empleados",
				        JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
