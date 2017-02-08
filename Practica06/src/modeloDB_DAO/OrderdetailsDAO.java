package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.OrderdetailsDTO;

public class OrderdetailsDAO implements Patron_DAO <OrderdetailsDTO> {
	private static final String SQL_INSERT = "INSERT INTO orderdetails (orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM orderdetails WHERE orderNumber = ?";
	private static final String SQL_UPDATE = "UPDATE orderdetails SET productCode = ?, quantityOrdered = ?, priceEach = ?, orderLineNumber = ? WHERE orderNumber = ?";
	private static final String SQL_FIND = "SELECT * FROM orderdetails WHERE orderNumber = ?";
	private static final String SQL_FINDALL = "SELECT * FROM orderdetails";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(OrderdetailsDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			ps.setInt(1, dto.getOrderNumber());
			ps.setString(2, dto.getProductCode());
			ps.setInt(3, dto.getQuantityOrdered());
			ps.setDouble(4, dto.getPriceEach());
			ps.setShort(5, dto.getOrderLineNumber());
			
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
	public boolean actualizar(OrderdetailsDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);

			ps.setString(1, dto.getProductCode());
			ps.setInt(2, dto.getQuantityOrdered());
			ps.setDouble(3, dto.getPriceEach());
			ps.setShort(4, dto.getOrderLineNumber());
			ps.setInt(5, dto.getOrderNumber());
			
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
	public OrderdetailsDTO buscar(Object pk) {
		OrderdetailsDTO orderDetail = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setInt(1, (int)pk);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				orderDetail = new OrderdetailsDTO(rs.getInt("orderNumber"), rs.getString("productCode"), rs.getInt("quantityOrdered"), rs.getDouble("priceEach"), rs.getShort("orderLineNumber"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderDetail;
	}
	
	@Override
	public ArrayList<OrderdetailsDTO> listarTodos() {
		ArrayList<OrderdetailsDTO> listaOrderDetails = new ArrayList<OrderdetailsDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderdetailsDTO orderDetails = new OrderdetailsDTO(rs.getInt("orderNumber"), rs.getString("productCode"), rs.getInt("quantityOrdered"), rs.getDouble("priceEach"), rs.getShort("orderLineNumber"));
				listaOrderDetails.add(orderDetails);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaOrderDetails;
	}
}
