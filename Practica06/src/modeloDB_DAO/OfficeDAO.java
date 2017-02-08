package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.OfficeDTO;

public class OfficeDAO implements Patron_DAO <OfficeDTO> {	
	private static final String SQL_INSERT = "INSERT INTO offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM offices WHERE officeCode = ?";
	private static final String SQL_UPDATE = "UPDATE offices SET city = ?, phone = ?, addressLine1 = ?, addressLine2 = ?, state = ?, country = ?, postalCode = ?, territory = ? WHERE officeCode = ?";
	private static final String SQL_FIND = "SELECT * FROM offices WHERE officeCode = ?";
	private static final String SQL_FINDALL = "SELECT * FROM offices";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(OfficeDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			
			ps.setString(1, dto.getOfficeCode());
			ps.setString(2, dto.getCity());
			ps.setString(3, dto.getPhone());
			ps.setString(4, dto.getAddressLine1());
			ps.setString(5, dto.getAddressLine2());
			ps.setString(6, dto.getState());
			ps.setString(7, dto.getCountry());
			ps.setString(8, dto.getPostalCode());
			ps.setString(9, dto.getTerritory());
			
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
			ps.setString(1, (String) pk);
			
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
	public boolean actualizar(OfficeDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);
			
			ps.setString(1, dto.getCity());
			ps.setString(2, dto.getPhone());
			ps.setString(3, dto.getAddressLine1());
			ps.setString(4, dto.getAddressLine2());
			ps.setString(5, dto.getState());
			ps.setString(6, dto.getCountry());
			ps.setString(7, dto.getPostalCode());
			ps.setString(8, dto.getTerritory());
			ps.setString(9, dto.getOfficeCode());
			
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
	public OfficeDTO buscar(Object pk) {
		OfficeDTO office = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setString(1, (String) pk);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				office = new OfficeDTO(rs.getString("officeCode"), rs.getString("city"), rs.getString("phone"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("state"), rs.getString("country"), rs.getString("postalCode"), rs.getString("territory"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return office;
	}
	
	@Override
	public ArrayList<OfficeDTO> listarTodos() {
		ArrayList<OfficeDTO> listaEmples = new ArrayList<OfficeDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OfficeDTO emp = new OfficeDTO(rs.getString("officeCode"), rs.getString("city"), rs.getString("phone"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("state"), rs.getString("country"), rs.getString("postalCode"), rs.getString("territory"));
				listaEmples.add(emp);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaEmples;
	}	
}
