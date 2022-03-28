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
	Tarea tarea;
	
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
			tarea = tareaService.getById(idTarea);
			
			TxtTitulo.setText(tarea.getTitulo());
			TxtDescripcion.setText(tarea.getDescripcion());
			TxtHorasEstimadas.setText(String.valueOf(tarea.getHorasEstimadas()));
			TxtHorasReales.setText(String.valueOf(tarea.getHorasReales()));
			TxtIdEmpleado.setText(String.valueOf(tarea.getEmpleado().getDni()));
			TxtEstado.setText(tarea.getEstado().stringifyEstado(tarea));
			TxtIdProyecto.setText(String.valueOf(tarea.getProyecto().getId()));
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
				if(tarea == null) tarea = new Tarea();
				
				//Asigno campos nuevos a la tarea
				tarea.setTitulo(TxtTitulo.getText());
				tarea.setDescripcion(TxtDescripcion.getText());
				tarea.setHorasEstimadas(Integer.parseInt(TxtHorasEstimadas.getText()));
				tarea.setHorasReales(Integer.parseInt(TxtHorasReales.getText()));

				//Empleado
				if(tarea.getEmpleado() == null) {
					//Deberia ser un dropdown
					tarea.asignarEmpleado(empleadoService.getById(Long.parseLong(TxtIdEmpleado.getText())));
				}
				if(tarea.getEmpleado().getDni() != Long.parseLong(TxtIdEmpleado.getText())){
					Empleado emp = tarea.getEmpleado();
					emp.setLibre(true);
					empleadoService.modificar(emp);
					tarea.asignarEmpleado(empleadoService.getById(Long.parseLong(TxtIdEmpleado.getText())));
				}
			
				//Proyecto
				//Deberia ser un dropdown
				tarea.setProyecto(proyectoService.getById(Long.parseLong(TxtIdProyecto.getText())));
				
				//Estado
				parseEstado(TxtEstado.getText());
				if(tarea.getEstado() == null ||
				tarea.getEstado().estaIniciado() != iniciado || 
				tarea.getEstado().estaEnCurso() != enCurso || 
				tarea.getEstado().estaFinalizado() != finalizado) {
					//El estado deberia ser un dropdown(iniciado,encurso..) y tener/guardar Timestamp.
					estadoService.crear(new Estado(tarea.getEmpleado(),iniciado,enCurso,finalizado));//Creo estado.
					tarea.cambiarEstadoA(estadoService.listar().get(0));//Asigno el ultimo estado creado recien
				}
				//FIN de asignacion de campos nuevos a la tarea
				
				if(idTarea == -1) {
					tareaService.crear(tarea);
				} else {
					tarea.setId(idTarea);
					tareaService.modificar(tarea);
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
