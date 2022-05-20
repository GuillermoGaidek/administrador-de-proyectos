package gui.empleado;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.tarea.FrmListadoTareas;
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

public class FrmEmpleado extends JFrame implements ActionListener {
	
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	
	private JLabel LblTituloVentana;
	
	private JLabel LblDni;
	private JLabel LblCostoPorHora;
	private JLabel LblLibre;
	private JLabel LblIdProyecto;

	private JTextField TxtDni;
	private JTextField TxtCostoPorHora;
	private JLabel TxtLibre;
	private JLabel TxtIdProyecto;
	
	private JButton BtnGuardar;
	private FrmListadoEmpleados frm;
	private long dniEmpleado;
	Empleado empleado;
	
	public FrmEmpleado(long dniEmpleado, FrmListadoEmpleados frm) {

		this.dniEmpleado = dniEmpleado;
		this.frm = frm;
		
		// setea titulo ventana
		this.setTitle("Empleado");

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

		if(dniEmpleado != -1) {
			LlenarForm();
		}
	}
	
	private void LlenarForm() {
		try {
			empleado = empleadoService.getById(dniEmpleado);
			
			TxtDni.setText(String.valueOf(empleado.getDni()));
			TxtCostoPorHora.setText(String.valueOf(empleado.getCostoPorHora()));
			TxtLibre.setText(empleado.estaLibre()? "SI": "NO");
			TxtIdProyecto.setText(empleado.getProyecto() == null? "-" : empleado.getProyecto().getTitulo());
			
		} catch (ServicioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Empleado",
			        JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JPanel GetPanelPrincipal() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		LblTituloVentana = new JLabel("Complete los campos", SwingConstants.CENTER);
		panel.add(LblTituloVentana, BorderLayout.NORTH);
		
		JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
		
		
		LblDni = new JLabel("DNI");
		panelCampos.add(LblDni);
		
		TxtDni = new JTextField("", 20);
		panelCampos.add(TxtDni);
		
		LblCostoPorHora = new JLabel("Costo por hora");
		panelCampos.add(LblCostoPorHora);
		
		TxtCostoPorHora = new JTextField("", 20);
		panelCampos.add(TxtCostoPorHora);
		
		LblLibre = new JLabel("Libre");
		panelCampos.add(LblLibre);
		
		TxtLibre = new JLabel("SI");
		panelCampos.add(TxtLibre);
		
		LblIdProyecto = new JLabel("Proyecto");
		panelCampos.add(LblIdProyecto);
		
		TxtIdProyecto = new JLabel("-");
		panelCampos.add(TxtIdProyecto);

		panel.add(panelCampos, BorderLayout.CENTER);
		
		BtnGuardar = new JButton("Guardar");
		BtnGuardar.addActionListener(this);
		panel.add(BtnGuardar, BorderLayout.SOUTH);

		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(TxtDni.getText().isEmpty() || TxtCostoPorHora.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacios. Vuelva a intentar nuevamente", "Empleado",
			        JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				if(empleado == null) empleado = new Empleado();
				
				//Asigno campos nuevos al empleado
				empleado.setDni(Long.parseLong(TxtDni.getText()));
				empleado.setCostoPorHora(Integer.parseInt(TxtCostoPorHora.getText()));
				empleado.setLibre(TxtLibre.getText() == "SI"? true : false);
				empleado.setProyecto(TxtIdProyecto.getText() == "-"? null : empleado.getProyecto());
				//Fin asigancion
				
				if(dniEmpleado == -1) {
					empleadoService.crear(empleado);
				} else {
					empleadoService.modificar(empleado);
				}
				frm.cargarTabla();
				this.setVisible(false);
				dispose();
			} catch (ServicioException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "FrmEmpleado",
				        JOptionPane.ERROR_MESSAGE);
			}  catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Algo salio mal: " + ex.getMessage(), "FrmEmpleado",
				        JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
}
