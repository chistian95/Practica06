package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.CustomersDTO;

public class CustomersDAO implements Patron_DAO <CustomersDTO> {
	private static final String SQL_INSERT = "INSERT INTO customers (customerNumber, customerName, contactLastname, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM customers WHERE customerNumber = ?";
	private static final String SQL_UPDATE = "UPDATE customers SET customerName = ?, contactLastname = ?, contactFirstName = ?, phone = ?, addressLine1 = ?, addressLine2 = ?, city = ?, state = ?, postalCode = ?, country = ?, salesRepEmployeeNumber = ?, creditLimit = ? WHERE customerNumber = ?";
	private static final String SQL_FIND = "SELECT * FROM customers WHERE customerNumber = ?";
	private static final String SQL_FINDALL = "SELECT * FROM customers";
	private static final String SQL_FINDEMPLOYEE = "SELECT * FROM customers WHERE salesRepEmployeeNumber = ?";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(CustomersDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			
			ps.setInt(1, dto.getCustomerNumber());
			ps.setString(2, dto.getCustomerName());
			ps.setString(3, dto.getContactLastname());
			ps.setString(4, dto.getContactFirstName());
			ps.setString(5, dto.getPhone());
			ps.setString(6, dto.getAddressLine1());
			ps.setString(7, dto.getAddressLine2());
			ps.setString(8, dto.getCity());
			ps.setString(9, dto.getState());
			ps.setString(10, dto.getPostalCode());
			ps.setString(11, dto.getCountry());
			ps.setString(12, dto.getSalesRepEmployeeNumber());
			ps.setDouble(13, dto.getCreditLimit());
			
			if (ps.executeUpdate() > 0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps!=null) ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean borrar(Object pk) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_DELETE);
			ps.setInt(1, (int)pk);
			
			if (ps.executeUpdate()>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps!=null) ps.close();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean actualizar(CustomersDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);

			ps.setString(1, dto.getCustomerName());
			ps.setString(2, dto.getContactLastname());
			ps.setString(3, dto.getContactFirstName());
			ps.setString(4, dto.getPhone());
			ps.setString(5, dto.getAddressLine1());
			ps.setString(6, dto.getAddressLine2());
			ps.setString(7, dto.getCity());
			ps.setString(8, dto.getState());
			ps.setString(9, dto.getPostalCode());
			ps.setString(10, dto.getCountry());
			ps.setString(11, dto.getSalesRepEmployeeNumber());
			ps.setDouble(12, dto.getCreditLimit());
			ps.setInt(13, dto.getCustomerNumber());
			
			if (ps.executeUpdate()>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps!=null) ps.close();
			} catch (Exception e4) {
				e4.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public CustomersDTO buscar(Object pk) {
		CustomersDTO customer = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setInt(1, (int)pk);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				customer = new CustomersDTO(rs.getInt("customerNumber"), rs.getString("customerName"), rs.getString("contactLastname"), rs.getString("constactFirstName"), rs.getString("phone"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("city"), rs.getString("state"), rs.getString("postalCode"), rs.getString("country"), rs.getString("salesRepEmployeeNumber"), rs.getDouble("creditLimit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	@Override
	public ArrayList<CustomersDTO> listarTodos() {
		ArrayList<CustomersDTO> listaCustomers = new ArrayList<CustomersDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CustomersDTO customer = new CustomersDTO(rs.getInt("customerNumber"), rs.getString("customerName"), rs.getString("contactLastname"), rs.getString("constactFirstName"), rs.getString("phone"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("city"), rs.getString("state"), rs.getString("postalCode"), rs.getString("country"), rs.getString("salesRepEmployeeNumber"), rs.getDouble("creditLimit"));
				listaCustomers.add(customer);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaCustomers;
	}
	
	public ArrayList<CustomersDTO> buscarEmpleado(Object pk) {
		ArrayList<CustomersDTO> listaCustomers = new ArrayList<CustomersDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDEMPLOYEE);
			ps.setInt(1, (int) pk); 
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CustomersDTO customer = new CustomersDTO(rs.getInt("customerNumber"), rs.getString("customerName"), rs.getString("contactLastname"), rs.getString("contactFirstName"), rs.getString("phone"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("city"), rs.getString("state"), rs.getString("postalCode"), rs.getString("country"), rs.getString("salesRepEmployeeNumber"), rs.getDouble("creditLimit"));
				listaCustomers.add(customer);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaCustomers;
	}
}
