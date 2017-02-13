package modelo_vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import modeloDB_DAO.CustomersDAO;
import modeloDB_DAO.EmployeeDAO;
import modeloDB_DAO.OfficeDAO;
import modeloDB_DAO.PaymentsDAO;
import modeloDB_DTO.CustomersDTO;
import modeloDB_DTO.EmployeeDTO;
import modeloDB_DTO.OfficeDTO;
import modeloDB_DTO.PaymentsDTO;

public class GestionOficinas extends JDialog {
	private static final long serialVersionUID = 2327111902506128036L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfPosicion;
	private JTextField tfCiudad;
	private JTextField tfTelefono;
	private JTextField tfDireccion;
	private JTextField tfTerritorio;
	private JTextField tfCodigoPostal;
	private JTextField tfEstado;
	private JTextField tfPais;
	private JTextField tfRecaudacion;
	private JTextField tfEmpleados;
	private OfficeDAO officeDAO = new OfficeDAO();
	private List<OfficeDTO> oficinas;
	private JTextField tfOficina;

	public GestionOficinas() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 336);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblOficinas = new JLabel("OFICINAS");
		lblOficinas.setHorizontalAlignment(SwingConstants.CENTER);
		lblOficinas.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOficinas.setBounds(10, 11, 414, 14);
		contentPanel.add(lblOficinas);
		
		JLabel lblNOficina = new JLabel("N Oficina");
		lblNOficina.setBounds(10, 36, 46, 14);
		contentPanel.add(lblNOficina);
		
		tfPosicion = new JTextField();
		tfPosicion.setBorder(null);
		tfPosicion.setVisible(false);
		tfPosicion.setEditable(false);
		tfPosicion.setBounds(179, 66, 25, 20);
		contentPanel.add(tfPosicion);
		tfPosicion.setColumns(10);
		
		JButton btnInicio = new JButton("<<");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarDatos(0);
			}
		});
		btnInicio.setBounds(154, 32, 60, 23);
		contentPanel.add(btnInicio);
		
		JButton btnFin = new JButton(">>");
		btnFin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDatos(oficinas.size() - 1);
			}
		});
		btnFin.setBounds(364, 32, 60, 23);
		contentPanel.add(btnFin);
		
		JButton btnAlante = new JButton(">");
		btnAlante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pos = Integer.parseInt(tfPosicion.getText());
					pos++;
					if(pos >= oficinas.size()) {
						pos = 0;
					}
					mostrarDatos(pos);
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(contentPanel, "Ha ocurrido un error!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAlante.setBounds(294, 32, 60, 23);
		contentPanel.add(btnAlante);
		
		JButton btnAtras = new JButton("<");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pos = Integer.parseInt(tfPosicion.getText());
					pos--;
					if(pos < 0) {
						pos = oficinas.size() - 1;
					}
					mostrarDatos(pos);
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(contentPanel, "Ha ocurrido un error!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAtras.setBounds(224, 32, 60, 23);
		contentPanel.add(btnAtras);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 87, 414, 164);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(10, 11, 46, 14);
		panel.add(lblCiudad);
		
		tfCiudad = new JTextField();
		tfCiudad.setBounds(66, 8, 86, 20);
		panel.add(tfCiudad);
		tfCiudad.setColumns(10);
		
		JLabel lblTf = new JLabel("Telefono");
		lblTf.setBounds(162, 11, 46, 14);
		panel.add(lblTf);
		
		tfTelefono = new JTextField();
		tfTelefono.setColumns(10);
		tfTelefono.setBounds(218, 8, 86, 20);
		panel.add(tfTelefono);
		
		JLabel lblDireccion = new JLabel("Direccion");
		lblDireccion.setBounds(10, 39, 46, 14);
		panel.add(lblDireccion);
		
		tfDireccion = new JTextField();
		tfDireccion.setColumns(10);
		tfDireccion.setBounds(66, 36, 143, 20);
		panel.add(tfDireccion);
		
		JLabel lblTerritorio = new JLabel("Territorio");
		lblTerritorio.setBounds(218, 39, 46, 14);
		panel.add(lblTerritorio);
		
		tfTerritorio = new JTextField();
		tfTerritorio.setColumns(10);
		tfTerritorio.setBounds(274, 36, 86, 20);
		panel.add(tfTerritorio);
		
		JLabel lblCp = new JLabel("CP");
		lblCp.setBounds(10, 67, 29, 14);
		panel.add(lblCp);
		
		tfCodigoPostal = new JTextField();
		tfCodigoPostal.setColumns(10);
		tfCodigoPostal.setBounds(49, 64, 62, 20);
		panel.add(tfCodigoPostal);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(121, 67, 46, 14);
		panel.add(lblEstado);
		
		tfEstado = new JTextField();
		tfEstado.setColumns(10);
		tfEstado.setBounds(177, 64, 86, 20);
		panel.add(tfEstado);
		
		JLabel lblPais = new JLabel("Pais");
		lblPais.setBounds(273, 67, 46, 14);
		panel.add(lblPais);
		
		tfPais = new JTextField();
		tfPais.setColumns(10);
		tfPais.setBounds(329, 64, 75, 20);
		panel.add(tfPais);
		
		JLabel lblRecaudacion = new JLabel("Recaudacion");
		lblRecaudacion.setBounds(10, 95, 75, 14);
		panel.add(lblRecaudacion);
		
		tfRecaudacion = new JTextField();
		tfRecaudacion.setColumns(10);
		tfRecaudacion.setBounds(81, 92, 86, 20);
		panel.add(tfRecaudacion);
		
		JLabel lblNEmpleados = new JLabel("N Empleados");
		lblNEmpleados.setBounds(177, 95, 75, 14);
		panel.add(lblNEmpleados);
		
		tfEmpleados = new JTextField();
		tfEmpleados.setColumns(10);
		tfEmpleados.setBounds(262, 92, 86, 20);
		panel.add(tfEmpleados);
		
		JButton btnVerEmpleados = new JButton("VER EMPLEADOS");
		btnVerEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VistaEmpleadosOficina(Integer.parseInt(tfOficina.getText()));
			}
		});
		btnVerEmpleados.setBounds(135, 130, 142, 23);
		panel.add(btnVerEmpleados);
		
		JLabel lblDatosOficina = new JLabel("Datos Oficina");
		lblDatosOficina.setBounds(10, 62, 149, 14);
		contentPanel.add(lblDatosOficina);
		
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(282, 262, 142, 23);
		contentPanel.add(btnVolver);
		
		tfOficina = new JTextField();
		tfOficina.setEditable(false);
		tfOficina.setColumns(10);
		tfOficina.setBounds(66, 33, 25, 20);
		contentPanel.add(tfOficina);
		
		cargarDatos();
		setVisible(true);
	}
	
	private void mostrarDatos(int pos) {
		try {
			OfficeDTO oficina = oficinas.get(pos);
			tfPosicion.setText(pos + "");
			tfOficina.setText(oficina.getOfficeCode());
			tfCiudad.setText(oficina.getCity());
			tfTelefono.setText(oficina.getPhone());
			tfDireccion.setText(oficina.getAddressLine1());
			tfTerritorio.setText(oficina.getTerritory());
			tfCodigoPostal.setText(oficina.getPostalCode());
			tfEstado.setText(oficina.getState());
			tfPais.setText(oficina.getCountry());
			
			EmployeeDAO empleDAO = new EmployeeDAO();
			CustomersDAO clienteDAO = new CustomersDAO();
			PaymentsDAO pagosDAO = new PaymentsDAO();
			List<EmployeeDTO> empleados = empleDAO.listarPorOficina(Integer.parseInt(tfOficina.getText()));
			
			tfEmpleados.setText(empleados.size() + "");
			
			double recaudacion = 0.0;
			for(EmployeeDTO empleado : empleados) {				
				List<CustomersDTO> clientes = clienteDAO.buscarEmpleado(empleado.getEmployeeNumber());
				for(CustomersDTO cliente : clientes) {
					List<PaymentsDTO> pagos = pagosDAO.buscarCliente(cliente.getCustomerNumber());
					for(PaymentsDTO pago : pagos) {
						recaudacion += pago.getAmount();
					}
				}
			}
			/*
			recaudacion *= 100;
			recaudacion = Math.round(recaudacion);
			recaudacion /= 100;
			 */
			tfRecaudacion.setText(String.format("%.2f", recaudacion));
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(contentPanel, "Ha ocurrido un error al cargar las oficinas", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cargarDatos() {
		new Thread() {
			@Override
			public void run() {
				try {
					oficinas = officeDAO.listarTodos();			
					mostrarDatos(0);
				} catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(contentPanel, "Ha ocurrido un error al cargar las oficinas", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}.start();
	}
}
