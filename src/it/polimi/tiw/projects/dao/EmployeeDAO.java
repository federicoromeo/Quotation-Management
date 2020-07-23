package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polimi.tiw.projects.beans.Quotation;

public class EmployeeDAO {
	private Connection con;
	private String id;

	public EmployeeDAO(Connection connection, String userid) {
		this.con = connection;
		this.id = userid;
	}

	//first employee functionality
	public List<Quotation> findMyQuotations() throws SQLException {
		
		List<Quotation> myQuotations = new ArrayList<Quotation>();
		
		String query = "SELECT * FROM quotation WHERE employee_code = ? ORDER BY price ASC";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, this.id);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Quotation q = new Quotation();
					q.setCode(result.getInt("code"));
					try {
						q.setPrice(result.getInt("price"));
					} catch(Exception e) {
						q.setPrice(0);
					}
					q.setEmployeeCode(this.id);
					q.setClientCode(result.getString("client_code"));
					myQuotations.add(q);
				}
			}
		}
		return myQuotations;
	}


	//second employee functionality
	public List<Quotation> findNeverAssignedQuotation() throws SQLException {
		
		List<Quotation> neverAssignedQuotations = new ArrayList<Quotation>();
		
		String query = "SELECT * FROM quotation WHERE employee_code IS NULL ORDER BY price ASC";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Quotation q = new Quotation();
					q.setCode(result.getInt("code"));
					q.setPrice(result.getInt("price"));
					q.setClientCode(result.getString("client_code"));
					q.setProductCode(result.getInt("product_code"));
					neverAssignedQuotations.add(q);
				}
			}
		}
		return neverAssignedQuotations;
	}

	
	//give the datas of the quotation to price it
	public Quotation returnSelectedQuotation(int chosenQuotation) throws SQLException {

		String query = "SELECT * FROM quotation WHERE code= ?";
		Quotation q = null;
		
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, chosenQuotation);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					q = new Quotation();
					q.setCode(result.getInt("code"));
					q.setPrice(result.getInt("price"));
					q.setClientCode(result.getString("client_code"));
					q.setEmployeeCode(result.getString("employee_code"));
					q.setProductCode(result.getInt("product_code"));
				}
			}
		}
		return q;
	}

	
	//update table price column as selected from user
	public void priceQuotation(int quotationCode, int selectedPrice) throws SQLException {

		String query = "UPDATE quotation SET price =?, employee_code= ? WHERE code =?";
		
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, selectedPrice);
			pstatement.setString(2, this.id);
			pstatement.setInt(3, quotationCode);
			
			pstatement.executeUpdate();	
			
		}
	}


}
