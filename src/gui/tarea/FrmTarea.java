package gui.tarea;

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
import logica.excepciones.ServicioException;
import logica.model.Empleado;
import logica.model.Proyecto;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;
import persistencia.dao.ProyectoDAOH2Impl;
import persistencia.dao.TareaDAOH2Impl;

public class FrmTarea extends JFrame implements ActionListener {
	
	GenericService<Tarea> tareaService = new GenericService<Tarea>(new TareaDAOH2Impl());
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	
	private JLabel LblTituloVentana;
	
	private JLabel LblTitulo;
	private JLabel LblDescripcion;
	private JLabel LblHorasEstimadas;
	private JLabel LblHorasReales;
	private JLabel LblIdEmpleado;
	private JLabel LblIdEstado;
	private JLabel LblIdProyecto;
	
	private JTextField TxtTitulo;
	private JTextArea TxtDescripcion;
	private JTextField TxtHorasEstimadas;
	private JTextField TxtHorasReales;
	private JTextField TxtIdEmpleado;
	private JTextField TxtIdEstado;
	private JTextField TxtIdProyecto;
	
	private JButton BtnGuardar;
	private FrmListadoTareas frm;
	private boolean llenar;
	private int id;
	
	public FrmTarea(int id, FrmListadoTareas frm, boolean llenar) {

		this.id = id;
		this.frm = frm;
		this.llenar = llenar;
		
		// setea titulo ventana
		this.setTitle("Tarea");

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

		if(llenar) {
			LlenarForm();
		}
	}
	
	private void LlenarForm() {
		try {
			Tarea t = tareaService.getById(id);
			TxtTitulo.setText(t.getTitulo());
			TxtDescripcion.setText(t.getDescripcion());
			TxtHorasEstimadas.setText(String.valueOf(t.getHorasEstimadas()));
			TxtHorasReales.setText(String.valueOf(t.getHorasReales()));
			TxtIdEmpleado.setText(String.valueOf(t.getEmpleado()));
			TxtIdEstado.setText(String.valueOf(t.getEstado()));
			TxtIdProyecto.setText(String.valueOf(t.getProyecto()));
		} catch (ServicioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Tarea",
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
		
		LblDescripcion = new JLabel("Descripcion");
		panelCampos.add(LblDescripcion);
		
		TxtDescripcion = new JTextArea();
		panelCampos.add(TxtDescripcion);

		LblHorasEstimadas = new JLabel("Horas Estimadas");
		panelCampos.add(LblHorasEstimadas);
		
		TxtHorasEstimadas = new JTextField("", 20);
		panelCampos.add(TxtHorasEstimadas);

		LblHorasReales = new JLabel("Horas Reales");
		panelCampos.add(LblHorasReales);

		TxtHorasReales = new JTextField("", 20);
		panelCampos.add(TxtHorasReales);
		
		LblIdEmpleado = new JLabel("Id Empleado");
		panelCampos.add(LblIdEmpleado);

		TxtIdEmpleado = new JTextField("", 20);
		panelCampos.add(TxtIdEmpleado);
		
		LblIdProyecto = new JLabel("Id Proyecto");
		panelCampos.add(LblIdProyecto);

		TxtIdProyecto = new JTextField("", 20);
		panelCampos.add(TxtIdProyecto);
		
		LblIdEstado = new JLabel("Estado");
		panelCampos.add(LblIdEstado);
		
		TxtIdEstado = new JTextField("", 20);
		panelCampos.add(TxtIdEstado);

		panel.add(panelCampos, BorderLayout.CENTER);
		
		BtnGuardar = new JButton("Guardar");
		BtnGuardar.addActionListener(this);
		panel.add(BtnGuardar, BorderLayout.SOUTH);

		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(TxtTitulo.getText().isEmpty() || TxtDescripcion.getText().isEmpty() || TxtHorasEstimadas.getText().isEmpty() || TxtHorasReales.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacios. Vuelva a intentar nuevamente", "Tarea",
			        JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				Tarea t = new Tarea();
				
				t.setTitulo(TxtTitulo.getText());
				t.setDescripcion(TxtDescripcion.getText());
				t.setHorasEstimadas(Integer.parseInt(TxtHorasEstimadas.getText()));
				t.setHorasReales(Integer.parseInt(TxtHorasReales.getText()));
				
				//deberia ser un dropdown
				t.asignarEmpleado(empleadoService.getById(Long.parseLong(TxtIdEmpleado.getText())));
				
				//deberia crear el estado..
				t.cambiarEstadoA(Integer.parseInt(TxtHorasReales.getText()));
				
				//deberia ser un dropdown
				t.setProyecto(proyectoService.getById(Long.parseLong(TxtIdProyecto.getText())));
				
				if(id == -1) {
					tareaService.crear(t);
				} else {
					t.setId(id);
					tareaService.modificar(t);
				}
				frm.cargarTabla();
				this.setVisible(false);
				dispose();
			} catch (ServicioException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Tarea",
				        JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Las horas deben ser numeros. Vuelva a intentar nuevamente", "Tarea",
				        JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Algo salio mal: " + ex.getMessage(), "Tarea",
				        JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
