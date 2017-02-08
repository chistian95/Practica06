package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.OrderDTO;

public class OrderDAO implements Patron_DAO <OrderDTO> {
	private static final String SQL_INSERT = "INSERT INTO orders (orderNumber, orderDate, requiredDate, shippedDate, status, coments, customerNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM orders WHERE orderNumber = ?";
	private static final String SQL_UPDATE = "UPDATE orders SET orderNumber = ?, orderDate = ?, requiredDate = ?, shippedDate = ?, status = ?, coments = ?, customerNumber = ? WHERE orderNumber = ?";
	private static final String SQL_FIND = "SELECT * FROM orders WHERE orderNumber = ?";
	private static final String SQL_FINDALL = "SELECT * FROM orders";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(OrderDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			
			ps.setInt(1, dto.getOrderNumber());
			ps.setDate(2, dto.getOrderDate());
			ps.setDate(3, dto.getRequiredDate());
			ps.setDate(4, dto.getShippedDate());
			ps.setString(5, dto.getStatus());
			ps.setString(6, dto.getComments());
			ps.setInt(7, dto.getCustomerNumber());
			
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
	public boolean actualizar(OrderDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);

			ps.setDate(1, dto.getOrderDate());
			ps.setDate(2, dto.getRequiredDate());
			ps.setDate(3, dto.getShippedDate());
			ps.setString(4, dto.getStatus());
			ps.setString(5, dto.getComments());
			ps.setInt(6, dto.getCustomerNumber());
			ps.setInt(7, dto.getOrderNumber());
			
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
	public OrderDTO buscar(Object pk) {
		OrderDTO order = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setInt(1, (int)pk);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				order = new OrderDTO(rs.getInt("orderNumber"), rs.getDate("orderDate"), rs.getDate("requiredDate"), rs.getDate("shippedDate"), rs.getString("status"), rs.getString("comments"), rs.getInt("customerNumber"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	@Override
	public ArrayList<OrderDTO> listarTodos() {
		ArrayList<OrderDTO> listaOrder = new ArrayList<OrderDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderDTO order = new OrderDTO(rs.getInt("orderNumber"), rs.getDate("orderDate"), rs.getDate("requiredDate"), rs.getDate("shippedDate"), rs.getString("status"), rs.getString("comments"), rs.getInt("customerNumber"));
				listaOrder.add(order);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaOrder;
	}
}
