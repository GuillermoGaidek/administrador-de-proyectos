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
import logica.model.Estado;
import logica.model.Proyecto;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.dao.EmpleadoDAOH2Impl;
import persistencia.dao.EstadoDAOH2Impl;
import persistencia.dao.ProyectoDAOH2Impl;
import persistencia.dao.TareaDAOH2Impl;

public class FrmTarea extends JFrame implements ActionListener {
	
	GenericService<Tarea> tareaService = new GenericService<Tarea>(new TareaDAOH2Impl());
	GenericService<Empleado> empleadoService = new GenericService<Empleado>(new EmpleadoDAOH2Impl());
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	GenericService<Estado> estadoService = new GenericService<Estado>(new EstadoDAOH2Impl());
	
	boolean iniciado = false;
	boolean enCurso = false;
	boolean finalizado = false;
	
	private JLabel LblTituloVentana;
	private JLabel LblTitulo;
	private JLabel LblDescripcion;
	private JLabel LblHorasEstimadas;
	private JLabel LblHorasReales;
	private JLabel LblIdEmpleado;
	private JLabel LblEstado;
	private JLabel LblIdProyecto;
	
	private JTextField TxtTitulo;
	private JTextArea TxtDescripcion;
	private JTextField TxtHorasEstimadas;
	private JTextField TxtHorasReales;
	private JTextField TxtIdEmpleado;
	private JTextField TxtEstado;
	private JTextField TxtIdProyecto;
	
	private JButton BtnGuardar;
	private FrmListadoTareas frm;
	private long idTarea;
	
	public FrmTarea(long idTarea, FrmListadoTareas frm) {

		this.idTarea = idTarea;
		this.frm = frm;
		
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

		if(idTarea != -1) {
			LlenarForm();
		}
	}
	
	private void LlenarForm() {
		try {
			Tarea t = tareaService.getById(idTarea);
			
			TxtTitulo.setText(t.getTitulo());
			TxtDescripcion.setText(t.getDescripcion());
			TxtHorasEstimadas.setText(String.valueOf(t.getHorasEstimadas()));
			TxtHorasReales.setText(String.valueOf(t.getHorasReales()));
			TxtIdEmpleado.setText(String.valueOf(t.getEmpleado().getDni()));
			TxtEstado.setText(t.getEstado().stringifyEstado(t));
			TxtIdProyecto.setText(String.valueOf(t.getProyecto().getId()));
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
		
		LblEstado = new JLabel("Estado");
		panelCampos.add(LblEstado);
		
		TxtEstado = new JTextField("", 20);
		panelCampos.add(TxtEstado);

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
				//Deberia ser un dropdown
				t.asignarEmpleado(empleadoService.getById(Long.parseLong(TxtIdEmpleado.getText())));
				//Deberia ser un dropdown
				t.setProyecto(proyectoService.getById(Long.parseLong(TxtIdProyecto.getText())));
				
				//El estado deberia ser un dropdown(iniciado,encurso..) y tener/guardar Timestamp. 
				parseEstado(TxtEstado.getText());
				estadoService.crear(new Estado(t.getEmpleado(),iniciado,enCurso,finalizado));//Creo estado.
				t.cambiarEstadoA(estadoService.listar().get(0));//Asigno el ultimo estado creado recien
				
				if(idTarea == -1) {
					tareaService.crear(t);
				} else {
					t.setId(idTarea);
					tareaService.modificar(t);
				}
				frm.cargarTabla();
				this.setVisible(false);
				dispose();
			} catch (ServicioException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "FrmTarea",
				        JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Las horas deben ser numeros. Vuelva a intentar nuevamente", "FrmTarea",
				        JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Algo salio mal: " + ex.getMessage(), "FrmTarea",
				        JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	private void parseEstado(String estado) {
		
		if(estado.replaceAll("\\s+","").toUpperCase().equals("INICIADO")) {
			iniciado = true;
		} else if(estado.replaceAll("\\s+","").toUpperCase().equals("ENCURSO")) {
			enCurso = true;
		} else if(estado.replaceAll("\\s+","").toUpperCase().equals("FINALIZADO")) {
			finalizado = true;
		}
		
	}
	
	
}
