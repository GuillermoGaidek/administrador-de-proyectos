package gui.estado;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import gui.tarea.TareaTableModel;
import logica.excepciones.ServicioException;
import logica.model.Estado;
import logica.model.Tarea;
import logica.service.GenericService;
import persistencia.dao.EstadoDAOH2Impl;

public class ListadoEstados extends JFrame {
		
	GenericService<Estado> estadoService = new GenericService<Estado>(new EstadoDAOH2Impl());
	
	private JLabel LblTituloVentana;
	private long idTarea;
	private EstadoTableModel modelo;
	private JTable tabla;
	private JScrollPane scrollPaneParaTabla;
	
	public ListadoEstados(long idTarea) {

		this.idTarea = idTarea;
		
		// setea titulo ventana
		this.setTitle("Estados");

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
		LblTituloVentana = new JLabel("Historial Estados", SwingConstants.CENTER);
		LblTituloVentana.setFont(new Font("Serif", Font.PLAIN, 18));
		
		panelPrincipal.add(LblTituloVentana, BorderLayout.NORTH);
		
		modelo = new EstadoTableModel();
		tabla = new JTable(modelo);
		cargarTabla();
		scrollPaneParaTabla = new JScrollPane(tabla);
		
		panelPrincipal.add(scrollPaneParaTabla, BorderLayout.CENTER);
		
		return panelPrincipal;
	}
	
	public void cargarTabla() {
		List<Estado> listaEstados;
		try {
			listaEstados = estadoService.listarById(idTarea);
			modelo.setFilas(listaEstados);
			modelo.fireTableDataChanged();	
		} catch (ServicioException e) {
			JOptionPane.showMessageDialog(new JFrame() , e.getMessage(),
					"Estados",JOptionPane.ERROR_MESSAGE);
			
		}
	}
	
}
