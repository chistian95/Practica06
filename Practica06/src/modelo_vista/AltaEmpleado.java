package modelo_vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modeloDB_DAO.EmployeeDAO;
import modeloDB_DAO.OfficeDAO;
import modeloDB_DTO.EmployeeDTO;
import modeloDB_DTO.OfficeDTO;

public class AltaEmpleado extends JDialog {
	private static final long serialVersionUID = 6745035280936297753L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfCodigoEmpleado;
	private JTextField tfNombre;
	private JTextField tfExtension;
	private JTextField tfEmail;
	private JTextField tfApellido;
	private JTextField tfCategoria;
	private JComboBox<String> cbOficina;
	private JComboBox<String> cbReporta;

	/**
	 * Create the dialog.
	 */
	public AltaEmpleado() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 355);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCrearEmplaedo = new JLabel("CREAR EMPLEADO");
			lblCrearEmplaedo.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCrearEmplaedo.setHorizontalAlignment(SwingConstants.CENTER);
			lblCrearEmplaedo.setBounds(10, 11, 414, 14);
			contentPanel.add(lblCrearEmplaedo);
		}
		
		JLabel lblCodigoEmpleado = new JLabel("Codigo Empleado");
		lblCodigoEmpleado.setBounds(10, 36, 125, 14);
		contentPanel.add(lblCodigoEmpleado);
		
		tfCodigoEmpleado = new JTextField();
		tfCodigoEmpleado.setBounds(10, 61, 125, 20);
		contentPanel.add(tfCodigoEmpleado);
		tfCodigoEmpleado.setColumns(10);
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(10, 117, 125, 20);
		contentPanel.add(tfNombre);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 92, 125, 14);
		contentPanel.add(lblNombre);
		
		tfExtension = new JTextField();
		tfExtension.setColumns(10);
		tfExtension.setBounds(10, 173, 125, 20);
		contentPanel.add(tfExtension);
		
		JLabel lblExtension = new JLabel("Extension");
		lblExtension.setBounds(10, 148, 125, 14);
		contentPanel.add(lblExtension);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(299, 61, 125, 20);
		contentPanel.add(tfEmail);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(299, 36, 125, 14);
		contentPanel.add(lblEmail);
		
		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(299, 117, 125, 20);
		contentPanel.add(tfApellido);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(299, 92, 125, 14);
		contentPanel.add(lblApellido);
		
		JLabel lblOficina = new JLabel("Oficina");
		lblOficina.setBounds(299, 148, 125, 14);
		contentPanel.add(lblOficina);
		
		JLabel lblReportaA = new JLabel("Reporta a");
		lblReportaA.setBounds(10, 204, 125, 14);
		contentPanel.add(lblReportaA);
		
		tfCategoria = new JTextField();
		tfCategoria.setColumns(10);
		tfCategoria.setBounds(299, 229, 125, 20);
		contentPanel.add(tfCategoria);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(299, 204, 125, 14);
		contentPanel.add(lblCategoria);
		
		JButton btnCrear = new JButton("CREAR");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearEmpleado();
			}
		});
		btnCrear.setBounds(280, 283, 144, 23);
		contentPanel.add(btnCrear);
		
		cbReporta = new JComboBox<String>();
		cbReporta.setBounds(10, 229, 125, 20);
		contentPanel.add(cbReporta);
		
		cbOficina = new JComboBox<String>();
		cbOficina.setBounds(299, 173, 125, 20);
		contentPanel.add(cbOficina);
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(10, 283, 144, 23);
		contentPanel.add(btnSalir);
		
		cargarDatos();
		
		setVisible(true);
	}
	
	private void cargarDatos() {
		try {
			//Combo box oficinas
			OfficeDAO oficinasDAO = new OfficeDAO();
			ArrayList<OfficeDTO> listOficinas = oficinasDAO.listarTodos();
			
			String[] oficinas = new String[listOficinas.size()];
			for(int i=0; i<listOficinas.size(); i++) {
				oficinas[i] = listOficinas.get(i).getOfficeCode() + " - " + listOficinas.get(i).getCity();
			}
			
			DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>(oficinas);
			cbOficina.setModel(modelo);
			
			//Combo box empleados
			EmployeeDAO empleDAO = new EmployeeDAO();
			ArrayList<EmployeeDTO> listEmpleados = empleDAO.listarTodos();
			
			String[] empleados = new String[listEmpleados.size() + 1];
			empleados[0] = "";
			for(int i=0; i<listEmpleados.size(); i++) {
				empleados[i + 1] = listEmpleados.get(i).getEmployeeNumber() + " - " + listEmpleados.get(i).getFirstName();
			}
			
			DefaultComboBoxModel<String> modeloEmple = new DefaultComboBoxModel<String>(empleados);
			cbReporta.setModel(modeloEmple);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al cargar datos!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void crearEmpleado() {
		try {
			EmployeeDAO empleDAO = new EmployeeDAO();			
			
			int employeeNumber = Integer.parseInt(tfCodigoEmpleado.getText());
			String lastName = tfApellido.getText();
			String firstName = tfNombre.getText();
			String extension = tfExtension.getText();
			String email = tfEmail.getText();
			String officeCode = ((String) cbOficina.getSelectedItem()).split(" - ")[0];
			int reportsTo = 0;
			String reportString = (String) cbReporta.getSelectedItem();
			if(reportString.contains(" - ")) {
				reportsTo = Integer.parseInt(reportString.split(" - ")[0]);
			}
			String jobTitle = tfCategoria.getText();
			
			EmployeeDTO empleado = new EmployeeDTO(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);
			
			if(empleDAO.buscar(employeeNumber) != null) {
				JOptionPane.showMessageDialog(contentPanel, "Ya hay un empleado con ese número!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(empleDAO.insertar(empleado)) {
				tfCodigoEmpleado.setText("");
				tfApellido.setText("");
				tfNombre.setText("");
				tfExtension.setText("");
				tfEmail.setText("");
				tfCategoria.setText("");
				cargarDatos();
				cbReporta.setSelectedIndex(0);
				cbOficina.setSelectedIndex(0);
				
				JOptionPane.showMessageDialog(contentPanel, "Empleado creado!");
			} else {
				JOptionPane.showMessageDialog(contentPanel, "Error al crear el empleado!", "Error", JOptionPane.ERROR_MESSAGE);
			}			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al crear empleado!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
