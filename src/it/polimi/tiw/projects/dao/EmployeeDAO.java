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
		
		String query = "SELECT code FROM quotation WHERE employee_code = ? ORDER BY price ASC";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, this.id);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Quotation q = new Quotation();
					q.setCode(result.getInt("code"));
					q.setPrice(Integer.parseInt(result.getString("price")));
					q.setEmployeeCode(result.getString("employeeCode"));
					if(!result.getString("clientCode").equals(null))
						q.setClientCode(result.getString("clientCode"));
					myQuotations.add(q);
				}
			}
		}
		return myQuotations;
	}


	//second employee functionality
	public List<Quotation> findNeverAssignedQuotation() throws SQLException {
		
		List<Quotation> neverAssignedQuotations = new ArrayList<Quotation>();
		
		String query = "SELECT code FROM quotation WHERE employeeCode IS NULL ORDER BY price ASC";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			//pstatement.setString(1, this.id);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Quotation q = new Quotation();
					q.setCode(result.getInt("code"));
					q.setPrice(Integer.parseInt(result.getString("price")));
					if(!result.getString("clientCode").equals(null))
						q.setClientCode(result.getString("clientCode"));
					neverAssignedQuotations.add(q);
				}
			}
		}
		return neverAssignedQuotations;
	}


}
