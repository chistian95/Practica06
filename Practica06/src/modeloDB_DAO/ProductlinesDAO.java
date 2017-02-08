package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.ProductlinesDTO;

public class ProductlinesDAO implements Patron_DAO <ProductlinesDTO> {
	private static final String SQL_INSERT = "INSERT INTO payments (customerNumber, checkNumber, paymentDate, amount) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM payments WHERE customerNumber = ?";
	private static final String SQL_UPDATE = "UPDATE payments SET checkNumber = ?, paymentDate = ?, amount = ? WHERE customerNumber = ?";
	private static final String SQL_FIND = "SELECT * FROM payments WHERE customerNumber = ?";
	private static final String SQL_FINDALL = "SELECT * FROM payments";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(ProductlinesDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			
			ps.setString(1, dto.getProductLine());
			ps.setString(2, dto.getTextDescription());
			ps.setString(3, dto.getHtmlDescription());
			ps.setBlob(4, dto.getImage());
			
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
	public boolean actualizar(ProductlinesDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);

			ps.setString(1, dto.getTextDescription());
			ps.setString(2, dto.getHtmlDescription());
			ps.setBlob(3, dto.getImage());
			ps.setString(4, dto.getProductLine());
			
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
	public ProductlinesDTO buscar(Object pk) {
		ProductlinesDTO productLine = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setString(1, (String) pk);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				productLine = new ProductlinesDTO(rs.getString("productLine"), rs.getString("textDescription"), rs.getString("htmlDescription"), rs.getBlob("image"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productLine;
	}
	
	@Override
	public ArrayList<ProductlinesDTO> listarTodos() {
		ArrayList<ProductlinesDTO> listaProductLines = new ArrayList<ProductlinesDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductlinesDTO productLine = new ProductlinesDTO(rs.getString("productLine"), rs.getString("textDescription"), rs.getString("htmlDescription"), rs.getBlob("image"));
				listaProductLines.add(productLine);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaProductLines;
	}
}
