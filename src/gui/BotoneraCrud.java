package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BotoneraCrud {
	
	public JButton botonAgregar;
	public JButton botonBorrar;
	public JButton botonModificar;
	
	public JPanel GetPanelBotones(ActionListener listener) {
		JPanel panel = new JPanel(new FlowLayout());

		botonAgregar = new JButton("Agregar");
		botonAgregar.addActionListener(listener);
		panel.add(botonAgregar);

		botonModificar = new JButton("Modificar");
		botonModificar.addActionListener(listener);
		panel.add(botonModificar);
		
		botonBorrar = new JButton("Borrar");
		botonBorrar.addActionListener(listener);
		panel.add(botonBorrar);
		
		return panel;
	}
	
}
