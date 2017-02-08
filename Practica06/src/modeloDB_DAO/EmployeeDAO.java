package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.EmployeeDTO;

public class EmployeeDAO implements Patron_DAO <EmployeeDTO> {	
	private static final String SQL_INSERT = "INSERT INTO employees (employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_INSERT_NOREPORT = "INSERT INTO employees (employeeNumber, lastName, firstName, extension, email, officeCode, jobTitle) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM employees WHERE employeeNumber = ?";
	private static final String SQL_UPDATE = "UPDATE employees SET lastName = ?, firstName = ?, extension = ?, email = ?, officeCode = ?, reportsTo = ?, jobTitle = ? WHERE employeeNumber = ?";
	private static final String SQL_FIND = "SELECT * FROM employees WHERE employeeNumber = ?";
	private static final String SQL_FINDALL = "SELECT * FROM employees";
	private static final String SQL_FINDOFFICE = "SELECT * FROM employees WHERE officeCode = ?";
	private static final String SQL_FINDTITLE = "SELECT * FROM employees WHERE jobTitle LIKE ?";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(EmployeeDTO dto) {
		PreparedStatement ps = null;
		try {
			if(dto.getReportsTo() == 0) {
				ps = con.getCon().prepareStatement(SQL_INSERT_NOREPORT);
				ps.setInt(1, dto.getEmployeeNumber());
				ps.setString(2, dto.getLastName());
				ps.setString(3, dto.getFirstName());
				ps.setString(4, dto.getExtension());
				ps.setString(5, dto.getEmail());
				ps.setString(6, dto.getOfficeCode());
				ps.setString(7, dto.getJobTitle());
			} else {
				ps = con.getCon().prepareStatement(SQL_INSERT);
				ps.setInt(1, dto.getEmployeeNumber());
				ps.setString(2, dto.getLastName());
				ps.setString(3, dto.getFirstName());
				ps.setString(4, dto.getExtension());
				ps.setString(5, dto.getEmail());
				ps.setString(6, dto.getOfficeCode());
				ps.setInt(7, dto.getReportsTo());
				ps.setString(8, dto.getJobTitle());
			}	
			
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
	public boolean actualizar(EmployeeDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);
			ps.setString(1, dto.getLastName());
			ps.setString(2, dto.getFirstName());
			ps.setString(3, dto.getExtension());
			ps.setString(4, dto.getEmail());
			ps.setString(5, dto.getOfficeCode());
			ps.setInt(6, dto.getReportsTo());
			ps.setString(7, dto.getJobTitle());
			ps.setInt(8, dto.getEmployeeNumber());
			
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
	public EmployeeDTO buscar(Object pk) {
		EmployeeDTO Empe = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setInt(1, (int)pk);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				Empe = new EmployeeDTO(rs.getInt("employeeNumber"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("extension"), rs.getString("email"), rs.getString("officeCode"), rs.getInt("reportsTo"), rs.getString("jobTitle"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Empe;
	}
	
	@Override
	public ArrayList<EmployeeDTO> listarTodos() {
		ArrayList<EmployeeDTO> listaEmples = new ArrayList<EmployeeDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EmployeeDTO emp = new EmployeeDTO(rs.getInt("employeeNumber"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("extension"), rs.getString("email"), rs.getString("officeCode"), rs.getInt("reportsTo"), rs.getString("jobTitle"));
				listaEmples.add(emp);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaEmples;
	}	
	
	public ArrayList<EmployeeDTO> listarPorOficina(Object pk) {
		ArrayList<EmployeeDTO> listaEmples = new ArrayList<EmployeeDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDOFFICE);
			ps.setString(1, (String) pk);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EmployeeDTO emp = new EmployeeDTO(rs.getInt("employeeNumber"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("extension"), rs.getString("email"), rs.getString("officeCode"), rs.getInt("reportsTo"), rs.getString("jobTitle"));
				listaEmples.add(emp);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaEmples;
	}
	
	public ArrayList<EmployeeDTO> listarPorCategoria(Object pk) {
		ArrayList<EmployeeDTO> listaEmples = new ArrayList<EmployeeDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDTITLE);
			ps.setString(1, (String) pk);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EmployeeDTO emp = new EmployeeDTO(rs.getInt("employeeNumber"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("extension"), rs.getString("email"), rs.getString("officeCode"), rs.getInt("reportsTo"), rs.getString("jobTitle"));
				listaEmples.add(emp);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaEmples;
	}
}
