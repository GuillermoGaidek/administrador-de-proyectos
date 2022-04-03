package gui.proyecto;

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

public class FrmProyecto  extends JFrame implements ActionListener {

	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	GenericService<Estado> estadoService = new GenericService<Estado>(new EstadoDAOH2Impl());
		
	private JLabel LblTituloVentana;
	private JLabel LblTitulo;	
	private JTextField TxtTitulo;
	private JButton BtnGuardar;
	private FrmListadoProyectos frm;
	private long idProyecto;
	Proyecto proyecto;
	
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
	
	private void LlenarForm() {
		try {
			proyecto = proyectoService.getById(idProyecto);
			
			TxtTitulo.setText(proyecto.getTitulo());
		} catch (ServicioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Proyecto",
			        JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JPanel GetPanelPrincipal() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		LblTituloVentana = new JLabel("Complete los campos", SwingConstants.CENTER);
		panel.add(LblTituloVentana, BorderLayout.NORTH);
		
		JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
		
		LblTitulo = new JLabel("Titulo");
		panelCampos.add(LblTitulo);
		
		TxtTitulo = new JTextField("", 20);
		panelCampos.add(TxtTitulo);
		
		panel.add(panelCampos, BorderLayout.CENTER);
		
		BtnGuardar = new JButton("Guardar");
		BtnGuardar.addActionListener(this);
		panel.add(BtnGuardar, BorderLayout.SOUTH);

		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(TxtTitulo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacios. Vuelva a intentar nuevamente", "Tarea",
			        JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				if(proyecto == null) proyecto = new Proyecto();
				
				proyecto.setTitulo(TxtTitulo.getText());
				
				if(idProyecto == -1) {
					proyectoService.crear(proyecto);
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
				JOptionPane.showMessageDialog(this, "Algo salio mal: " + ex.getMessage(), "FrmTarea",
				        JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}	
	
}
