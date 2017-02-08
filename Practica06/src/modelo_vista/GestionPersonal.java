package modelo_vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modeloDB_DAO.EmployeeDAO;
import modeloDB_DAO.OfficeDAO;
import modeloDB_DTO.EmployeeDTO;
import modeloDB_DTO.OfficeDTO;

public class GestionPersonal extends JDialog {
	private static final long serialVersionUID = 1476581259513751943L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private ArrayList<EmployeeDTO> empleados;
	private EmployeeDAO emp = new EmployeeDAO();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox<String> comboBox;
	private JRadioButton rdbtnSalesRep;
	private JRadioButton rdbtnSalesManger;

	public GestionPersonal() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1000, 534);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblGestinDePersonal = new JLabel("GESTI\u00D3N DE PERSONAL");
		lblGestinDePersonal.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestinDePersonal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGestinDePersonal.setBounds(10, 11, 964, 14);
		contentPanel.add(lblGestinDePersonal);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 210, 964, 241);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N. Empleado", "Nombre", "Apellido", "Extension", "Email", "Cod. Oficina", "Reporta a", "Categoria"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnListarTodos = new JButton("Listar Todos");
		btnListarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarTodos();
			}
		});
		btnListarTodos.setBounds(10, 176, 163, 23);
		contentPanel.add(btnListarTodos);
		
		JButton btnBuscarPorOficina = new JButton("Buscar por Oficina");
		btnBuscarPorOficina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarPorOficina();
			}
		});
		btnBuscarPorOficina.setBounds(423, 176, 163, 23);
		contentPanel.add(btnBuscarPorOficina);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(284, 179, 129, 20);
		contentPanel.add(comboBox);
		
		JLabel lblOficinas = new JLabel("Oficinas");
		lblOficinas.setBounds(284, 154, 129, 14);
		contentPanel.add(lblOficinas);
		
		JButton btnBuscarPorCategoria = new JButton("Buscar por Categoria");
		btnBuscarPorCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarPorCategoria();
			}
		});
		btnBuscarPorCategoria.setBounds(811, 176, 163, 23);
		contentPanel.add(btnBuscarPorCategoria);
		
		rdbtnSalesRep = new JRadioButton("Sales Rep");
		rdbtnSalesRep.setSelected(true);
		buttonGroup.add(rdbtnSalesRep);
		rdbtnSalesRep.setBounds(696, 150, 109, 23);
		contentPanel.add(rdbtnSalesRep);
		
		rdbtnSalesManger = new JRadioButton("Sales Manager");
		buttonGroup.add(rdbtnSalesManger);
		rdbtnSalesManger.setBounds(696, 176, 109, 23);
		contentPanel.add(rdbtnSalesManger);
		
		JButton btnNuevoEmpleado = new JButton("Nuevo Empleado");
		btnNuevoEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AltaEmpleado();
			}
		});
		btnNuevoEmpleado.setBounds(10, 66, 163, 23);
		contentPanel.add(btnNuevoEmpleado);
		
		JButton btnEliminarEmpleado = new JButton("Eliminar Empleado");
		btnEliminarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarEmpleado();
			}
		});
		btnEliminarEmpleado.setBounds(811, 66, 163, 23);
		contentPanel.add(btnEliminarEmpleado);
		
		JButton btnEditarEmpleado = new JButton("Editar Empleado");
		btnEditarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarEmpleado();
			}
		});
		btnEditarEmpleado.setBounds(408, 66, 163, 23);
		contentPanel.add(btnEditarEmpleado);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setBounds(10, 462, 163, 23);
		contentPanel.add(btnSalir);
		
		listarTodos();
		cargarComboBox();
		
		setVisible(true);
	}
	
	private void eliminarEmpleado() {
		int row = table.getSelectedRow();
		
		if(row == -1) {
			JOptionPane.showMessageDialog(contentPanel, "Debes seleccionar un empleado!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		int numEmpleado = (int) modelo.getValueAt(row, 0);
		if(JOptionPane.showConfirmDialog(contentPanel, "Quieres eliminar este empleado?") == 0) {
			EmployeeDAO empleDAO = new EmployeeDAO();
			if(empleDAO.borrar(numEmpleado)) {
				JOptionPane.showMessageDialog(contentPanel, "Empleado eliminado!");
				listarTodos();
				cargarComboBox();
			} else {
				JOptionPane.showMessageDialog(contentPanel, "Error al eliminar el empleado!", "Error", JOptionPane.ERROR_MESSAGE);
			}			
		}
	}
	
	private void modificarEmpleado() {
		int row = table.getSelectedRow();
		
		if(row == -1) {
			JOptionPane.showMessageDialog(contentPanel, "Debes seleccionar un empleado!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		int numEmpleado = (int) modelo.getValueAt(row, 0);		
		new ModificarEmpleado(numEmpleado);
	}
	
	private void cargarComboBox() {
		OfficeDAO officeDao = new OfficeDAO();
		ArrayList<OfficeDTO> oficinasList = officeDao.listarTodos();
		
		String[] oficinas = new String[oficinasList.size()];
		for(int i=0; i<oficinasList.size(); i++) {
			oficinas[i] = oficinasList.get(i).getOfficeCode() + " - " + oficinasList.get(i).getCity();
		}		
		
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>(oficinas);
		comboBox.setModel(modelo);
	}
	
	private void borrarTabla() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		while(modelo.getRowCount() > 0) {
			modelo.removeRow(0);
		}
	}
	
	private void listarTodos() {
		try {
			borrarTabla();
			empleados = emp.listarTodos();
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			int numcols = modelo.getColumnCount();
			for(EmployeeDTO emp : empleados){
				Object [] fila=new Object[numcols];
				fila[0] = emp.getEmployeeNumber();
				fila[1] = emp.getFirstName();
				fila[2] = emp.getLastName();
				fila[3] = emp.getExtension();
				fila[4] = emp.getEmail();
				fila[5] = emp.getOfficeCode();
				fila[6] = emp.getReportsTo();
				fila[7] = emp.getJobTitle();
				
				modelo.addRow(fila);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al leer los empleados!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void listarPorOficina() {
		try {
			borrarTabla();
						
			String item = (String) comboBox.getSelectedItem();
			String oficina = item.split(" - ")[0];
			
			empleados = emp.listarPorOficina(oficina);
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			int numcols = modelo.getColumnCount();
			for(EmployeeDTO emp : empleados){
				Object [] fila=new Object[numcols];
				fila[0] = emp.getEmployeeNumber();
				fila[1] = emp.getFirstName();
				fila[2] = emp.getLastName();
				fila[3] = emp.getExtension();
				fila[4] = emp.getEmail();
				fila[5] = emp.getOfficeCode();
				fila[6] = emp.getReportsTo();
				fila[7] = emp.getJobTitle();
				
				modelo.addRow(fila);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al leer los empleados!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void listarPorCategoria() {
		try {
			borrarTabla();
				
			String categoria = "%Sales Rep%";
			if(rdbtnSalesManger.isSelected()) {
				categoria = "%Sale% Manager%";
			}
			
			empleados = emp.listarPorCategoria(categoria);
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			int numcols = modelo.getColumnCount();
			for(EmployeeDTO emp : empleados){
				Object [] fila=new Object[numcols];
				fila[0] = emp.getEmployeeNumber();
				fila[1] = emp.getFirstName();
				fila[2] = emp.getLastName();
				fila[3] = emp.getExtension();
				fila[4] = emp.getEmail();
				fila[5] = emp.getOfficeCode();
				fila[6] = emp.getReportsTo();
				fila[7] = emp.getJobTitle();
				
				modelo.addRow(fila);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al leer los empleados!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
