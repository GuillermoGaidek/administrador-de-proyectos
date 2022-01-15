package gui;

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

import exceptions.ServicioException;
import model.Tarea;
import service.TareaService;

public class FrmTarea extends JFrame implements ActionListener {
	TareaService servicio;
	private JLabel LblTituloVentana;
	private JLabel LblTitulo;
	private JLabel LblDescripcion;
	private JLabel LblHorasEstimadas;
	private JLabel LblHorasReales;
	private JTextField TxtTitulo;
	private JTextArea TxtDescripcion;
	private JTextField TxtHorasEstimadas;
	private JTextField TxtHorasReales;
	private JButton BtnGuardar;
	private FrmListadoTareas frm;
	private int id;
	
	public FrmTarea(int id, FrmListadoTareas frm) {
		servicio = new TareaService();
		this.id = id;
		this.frm = frm;
		
		// setea titulo ventana
		this.setTitle("Tarea");

		// cerrar programa
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// setear panel de la ventana
		this.setContentPane(GetPanelPrincipal());

		// compacta las componentes de la ventana
		this.pack();

		// muestra la ventana
		this.setVisible(true);

		if(id != -1) {
			LlenarForm();
		}
	}
	
	private void LlenarForm() {		
		Tarea t = servicio.getTarea(id);
		TxtTitulo.setText(t.getTitulo());
		TxtDescripcion.setText(t.getDescripcion());
		TxtHorasEstimadas.setText(String.valueOf(t.getHorasEstimadas()));
		TxtHorasReales.setText(String.valueOf(t.getHorasReales()));
	}
	
	private JPanel GetPanelPrincipal() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		LblTituloVentana = new JLabel("Complete los campos", SwingConstants.CENTER);
		panel.add(LblTituloVentana, BorderLayout.NORTH);
		
		JPanel panelCampos = new JPanel(new GridLayout(0, 2, 10, 10));
		
		LblTitulo = new JLabel("Título");
		panelCampos.add(LblTitulo);
		
		TxtTitulo = new JTextField("", 20);
		panelCampos.add(TxtTitulo);
		
		LblDescripcion = new JLabel("Descripción");
		panelCampos.add(LblDescripcion);
		
		TxtDescripcion = new JTextArea();
		//TxtDescripcion.setRows(50);
		panelCampos.add(TxtDescripcion);

		LblHorasEstimadas = new JLabel("Horas Estimadas");
		panelCampos.add(LblHorasEstimadas);
		
		TxtHorasEstimadas = new JTextField("", 20);
		panelCampos.add(TxtHorasEstimadas);

		LblHorasReales = new JLabel("Horas Reales");
		panelCampos.add(LblHorasReales);

		TxtHorasReales = new JTextField("", 20);
		panelCampos.add(TxtHorasReales);

		panel.add(panelCampos, BorderLayout.CENTER);
		
		BtnGuardar = new JButton("Guardar");
		BtnGuardar.addActionListener(this);
		panel.add(BtnGuardar, BorderLayout.SOUTH);

		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == BtnGuardar) {
			try {
				Tarea t = new Tarea();
				t.setTitulo(TxtTitulo.getText());
				t.setDescripcion(TxtDescripcion.getText());
				t.setHorasEstimadas(Integer.parseInt(TxtHorasEstimadas.getText()));
				t.setHorasReales(Integer.parseInt(TxtHorasReales.getText()));
				if(id == -1) {
					servicio.crear(t);
				} else {
					t.setId(id);
					servicio.modificar(t);
				}
				frm.CargarTabla();
				JOptionPane.showMessageDialog(this, "La tarea se guardó correctamente", "Tarea",
				        JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
				dispose();
			} catch (ServicioException ex) {
				JOptionPane.showMessageDialog(this, "Algo salio mal. Vuelva a intentar nuevamente", "Tarea",
				        JOptionPane.ERROR_MESSAGE);
			}
		} 
	}
}
