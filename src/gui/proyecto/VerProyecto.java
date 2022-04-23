package gui.proyecto;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import gui.empleado.FrmListadoEmpleados;
import gui.tarea.FrmListadoTareas;
import logica.excepciones.ServicioException;
import logica.model.Proyecto;
import logica.service.GenericService;
import persistencia.dao.ProyectoDAOH2Impl;

public class VerProyecto extends JFrame implements ActionListener{
	
	GenericService<Proyecto> proyectoService = new GenericService<Proyecto>(new ProyectoDAOH2Impl());
	
	private JLabel LblTituloVentana;
	private JButton BtnTarea;
	private long idProyecto;
	Proyecto proyecto;
	
	public VerProyecto(long idProyecto) {

		this.idProyecto = idProyecto;
		try {
			this.proyecto = proyectoService.getById(idProyecto);
		} catch (ServicioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ver Proyecto",
			        JOptionPane.ERROR_MESSAGE);
		}
		
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
	}
	
	private JPanel GetPanelPrincipal() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		LblTituloVentana = new JLabel(proyecto.getTitulo().toUpperCase(), SwingConstants.CENTER);
		LblTituloVentana.setFont(new Font("Serif", Font.PLAIN, 18));
		
		panelPrincipal.add(LblTituloVentana, BorderLayout.NORTH);
		panelPrincipal.add(new FrmListadoEmpleados(idProyecto,false).GetPanelPrincipal(),BorderLayout.SOUTH);
		panelPrincipal.add(new FrmListadoTareas(idProyecto).GetPanelPrincipal(),BorderLayout.CENTER);
		
		return panelPrincipal;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == BtnTarea){
			new FrmListadoTareas(idProyecto);
		}
	}
}
