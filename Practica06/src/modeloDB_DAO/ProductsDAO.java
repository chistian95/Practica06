package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.ProductsDTO;

public class ProductsDAO implements Patron_DAO <ProductsDTO> {
	private static final String SQL_INSERT = "INSERT INTO products (productCode, productName, productLine, productVendor, productDescription, quantityInStock, buyPrice, MSRP) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM products WHERE productCode = ?";
	private static final String SQL_UPDATE = "UPDATE products SET productName = ?, productLine = ?, productVendor = ?, productDescription = ?, quantityInStock = ?, buyPrice = ?, MSRP = ? WHERE productCode = ?";
	private static final String SQL_FIND = "SELECT * FROM products WHERE productCode = ?";
	private static final String SQL_FINDALL = "SELECT * FROM products";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(ProductsDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			
			ps.setString(1, dto.getProductCode());
			ps.setString(2, dto.getProductName());
			ps.setString(3, dto.getProductLine());
			ps.setString(4, dto.getProductVendor());
			ps.setString(5, dto.getProductDescription());
			ps.setShort(6, dto.getQuantityInStock());
			ps.setDouble(7, dto.getBuyPrice());
			ps.setDouble(8, dto.getMSRP());
			
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
	public boolean actualizar(ProductsDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);

			ps.setString(1, dto.getProductName());
			ps.setString(2, dto.getProductLine());
			ps.setString(3, dto.getProductVendor());
			ps.setString(4, dto.getProductDescription());
			ps.setShort(5, dto.getQuantityInStock());
			ps.setDouble(6, dto.getBuyPrice());
			ps.setDouble(7, dto.getMSRP());
			ps.setString(8, dto.getProductCode());
			
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
	public ProductsDTO buscar(Object pk) {
		ProductsDTO product = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setString(1, (String) pk);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				product = new ProductsDTO(rs.getString("productCode"), rs.getString("productName"), rs.getString("productLine"), rs.getString("productVendor"), rs.getString("productDescription"), rs.getShort("quantityInStock"), rs.getDouble("buyPrice"), rs.getDouble("MSRP"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	
	@Override
	public ArrayList<ProductsDTO> listarTodos() {
		ArrayList<ProductsDTO> listaProduct = new ArrayList<ProductsDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductsDTO product = new ProductsDTO(rs.getString("productCode"), rs.getString("productName"), rs.getString("productLine"), rs.getString("productVendor"), rs.getString("productDescription"), rs.getShort("quantityInStock"), rs.getDouble("buyPrice"), rs.getDouble("MSRP"));
				listaProduct.add(product);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaProduct;
	}
}
