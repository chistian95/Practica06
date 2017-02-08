package modelo_vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
import javax.swing.JComboBox;

public class ModificarEmpleado extends JDialog {
	private static final long serialVersionUID = 6745035280936297753L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfCodigoEmpleado;
	private JTextField tfNombre;
	private JTextField tfExtension;
	private JTextField tfEmail;
	private JTextField tfApellido;
	private JTextField tfCategoria;
	private int numEmpe;
	private JComboBox<String> cbReporta;
	private JComboBox<String> cbOficina;

	/**
	 * Create the dialog.
	 */
	public ModificarEmpleado(int numEmpe) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.numEmpe = numEmpe;
		
		setBounds(100, 100, 450, 355);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCrearEmplaedo = new JLabel("MODIFICAR EMPLEADO");
			lblCrearEmplaedo.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCrearEmplaedo.setHorizontalAlignment(SwingConstants.CENTER);
			lblCrearEmplaedo.setBounds(10, 11, 414, 14);
			contentPanel.add(lblCrearEmplaedo);
		}
		
		JLabel lblCodigoEmpleado = new JLabel("Codigo Empleado");
		lblCodigoEmpleado.setBounds(10, 36, 125, 14);
		contentPanel.add(lblCodigoEmpleado);
		
		tfCodigoEmpleado = new JTextField();
		tfCodigoEmpleado.setEditable(false);
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
		
		JButton btnCrear = new JButton("MODIFICAR");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarEmpleado();
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
			EmployeeDAO empleDAO = new EmployeeDAO();
			EmployeeDTO empleado = empleDAO.buscar(numEmpe);
			
			tfCodigoEmpleado.setText(empleado.getEmployeeNumber() + "");
			tfApellido.setText(empleado.getLastName());
			tfNombre.setText(empleado.getFirstName());
			tfExtension.setText(empleado.getExtension());
			tfEmail.setText(empleado.getEmail());
			tfCategoria.setText(empleado.getJobTitle());
			
			//Combo box oficinas
			OfficeDAO oficinasDAO = new OfficeDAO();
			ArrayList<OfficeDTO> listOficinas = oficinasDAO.listarTodos();
			
			int ofiSelect = 0;
			String[] oficinas = new String[listOficinas.size()];
			for(int i=0; i<listOficinas.size(); i++) {
				oficinas[i] = listOficinas.get(i).getOfficeCode() + " - " + listOficinas.get(i).getCity();
				if(listOficinas.get(i).getOfficeCode().equals(empleado.getOfficeCode())) {
					ofiSelect = i;
				}
			}
			
			DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>(oficinas);
			cbOficina.setModel(modelo);
			cbOficina.setSelectedIndex(ofiSelect);
			
			//Combo box empleados
			ArrayList<EmployeeDTO> listEmpleados = empleDAO.listarTodos();
			
			int empleSelect = 0;
			String[] empleados = new String[listEmpleados.size() + 1];
			empleados[0] = "";
			for(int i=0; i<listEmpleados.size(); i++) {
				empleados[i + 1] = listEmpleados.get(i).getEmployeeNumber() + " - " + listEmpleados.get(i).getFirstName();
				if(listEmpleados.get(i).getEmployeeNumber() == empleado.getReportsTo()) {
					empleSelect = (i + 1);
				}
			}
			
			DefaultComboBoxModel<String> modeloEmple = new DefaultComboBoxModel<String>(empleados);
			cbReporta.setModel(modeloEmple);
			cbReporta.setSelectedIndex(empleSelect);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al cargar datos!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void modificarEmpleado() {
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
			
			if(empleDAO.actualizar(empleado)) {
				cargarDatos();
				JOptionPane.showMessageDialog(contentPanel, "Empleado modificado!");
			} else {
				JOptionPane.showMessageDialog(contentPanel, "Error al modificar el empleado!", "Error", JOptionPane.ERROR_MESSAGE);
			}			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al modificar empleado!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
