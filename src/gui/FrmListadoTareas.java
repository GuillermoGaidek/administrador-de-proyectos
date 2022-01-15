package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import model.Tarea;
import service.TareaService;

public class FrmListadoTareas extends JFrame implements ActionListener {
	TareaService servicio;
	private JTable tabla;
	private TareaTableModel modelo;
	private JScrollPane scrollPaneParaTabla;
	private JLabel LblTitulo;
	private JButton botonAgregar;
	private JButton botonBorrar;
	private JButton botonModificar;
	
	public FrmListadoTareas() {
		servicio = new TareaService();
		
		// setear titulo ventana
		this.setTitle("Tarea");

		// se cierra la ventana al hacer click en el boton X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setear panel de la ventana
		this.setContentPane(GetPanelPrincipal());

		// compacta las componentes de la ventana
		this.pack();

		// muestra la ventana
		this.setVisible(true);
		
	}
	
	private JPanel GetPanelPrincipal() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		LblTitulo = new JLabel("Listado de tareas", SwingConstants.CENTER);
		panel.add(LblTitulo, BorderLayout.NORTH);
		modelo = new TareaTableModel();
		tabla = new JTable(modelo);
		CargarTabla();
		scrollPaneParaTabla = new JScrollPane(tabla);
		panel.add(scrollPaneParaTabla, BorderLayout.CENTER);
		panel.add(GetPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel GetPanelBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		botonAgregar = new JButton("Agregar");
		botonAgregar.addActionListener(this);
		panel.add(botonAgregar);

		botonBorrar = new JButton("Borrar");
		botonBorrar.addActionListener(this);
		panel.add(botonBorrar);
		
		botonModificar = new JButton("Modificar");
		botonModificar.addActionListener(this);
		panel.add(botonModificar);
		
		return panel;
	}
	
	public  void CargarTabla() {
		List<Tarea> lista = servicio.listar();
		modelo.setFilas(lista);
		modelo.fireTableDataChanged();		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonAgregar) {
			new FrmTarea(-1, this);
		} else if (e.getSource() == botonModificar) {
			int fila = this.tabla.getSelectedRow();
			int id = (int)this.tabla.getValueAt(fila, 0);
			new FrmTarea(id, this);
		} else if (e.getSource() == botonBorrar) {
			int fila = this.tabla.getSelectedRow();
			int id = (int)this.tabla.getValueAt(fila, 0);
			Tarea t = new Tarea();
			t.setId(id);
			servicio.borrar(t);
			CargarTabla();			
		}
	}
}
