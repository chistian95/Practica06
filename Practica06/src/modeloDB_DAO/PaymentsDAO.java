package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.PaymentsDTO;

public class PaymentsDAO implements Patron_DAO <PaymentsDTO> {
	private static final String SQL_INSERT = "INSERT INTO payments (customerNumber, checkNumber, paymentDate, amount) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM payments WHERE customerNumber = ?";
	private static final String SQL_UPDATE = "UPDATE payments SET checkNumber = ?, paymentDate = ?, amount = ? WHERE customerNumber = ?";
	private static final String SQL_FIND = "SELECT * FROM payments WHERE customerNumber = ? AND checkNumber = ?";
	private static final String SQL_FINDALL = "SELECT * FROM payments";
	private static final String SQL_FINCUSTOMER = "SELECT * FROM payments WHERE customerNumber = ?";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(PaymentsDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			ps.setInt(1, dto.getCustomerNumber());
			ps.setString(2, dto.getCheckNumber());
			ps.setDate(3, dto.getPaymentDate());
			ps.setDouble(4, dto.getAmount());
			
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
	public boolean actualizar(PaymentsDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);

			ps.setString(1, dto.getCheckNumber());
			ps.setDate(2, dto.getPaymentDate());
			ps.setDouble(3, dto.getAmount());
			ps.setInt(4, dto.getCustomerNumber());
			
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
	public PaymentsDTO buscar(Object pk) {
		PaymentsDTO payment = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setInt(1, (int) ((Integer[]) pk)[0]);
			ps.setInt(2, (int) ((Integer[]) pk)[1]);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				payment = new PaymentsDTO(rs.getInt("customerNumber"), rs.getString("checkNumber"), rs.getDate("paymentDate"), rs.getDouble("amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payment;
	}
	
	@Override
	public ArrayList<PaymentsDTO> listarTodos() {
		ArrayList<PaymentsDTO> listaPayments = new ArrayList<PaymentsDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PaymentsDTO payment = new PaymentsDTO(rs.getInt("customerNumber"), rs.getString("checkNumber"), rs.getDate("paymentDate"), rs.getDouble("amount"));
				listaPayments.add(payment);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaPayments;
	}
	
	public ArrayList<PaymentsDTO> buscarCliente(Object pk) {
		ArrayList<PaymentsDTO> listaPayments = new ArrayList<PaymentsDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINCUSTOMER);
			ps.setInt(1, (int) pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PaymentsDTO payment = new PaymentsDTO(rs.getInt("customerNumber"), rs.getString("checkNumber"), rs.getDate("paymentDate"), rs.getDouble("amount"));
				listaPayments.add(payment);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaPayments;
	}
}
