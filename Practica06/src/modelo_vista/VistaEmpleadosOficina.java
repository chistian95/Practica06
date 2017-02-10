package modelo_vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import modeloDB_DAO.EmployeeDAO;
import modeloDB_DTO.EmployeeDTO;

public class VistaEmpleadosOficina extends JDialog {
	private static final long serialVersionUID = 4131718840856810658L;
	private int codOficina;
	private JTable table;

	public VistaEmpleadosOficina(int codOficina) {
		this.codOficina = codOficina;
		setBounds(100, 100, 1000, 358);
		getContentPane().setLayout(null);
		
		JLabel lblEmpleados = new JLabel("LISTA DE EMPLEADOS");
		lblEmpleados.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmpleados.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmpleados.setBounds(10, 11, 964, 14);
		getContentPane().add(lblEmpleados);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 964, 241);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"N. Empleado", "Nombre", "Apellido", "Extension", "Email", "Cod. Oficina", "Reporta a", "Categoria"
				}
			));
		scrollPane.setViewportView(table);
		
		JButton button = new JButton("Salir");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBounds(10, 288, 163, 23);
		getContentPane().add(button);
		
		cargarDatos();
		setVisible(true);
	}
	
	private void cargarDatos() {
		EmployeeDAO empleDAO = new EmployeeDAO();
		List<EmployeeDTO> empleados = empleDAO.listarPorOficina(codOficina);
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		
		for(EmployeeDTO emp : empleados) {
			Object [] fila=new Object[modelo.getColumnCount()];
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
	}
}
