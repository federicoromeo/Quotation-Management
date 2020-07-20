package it.polimi.tiw.projects.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polimi.tiw.projects.beans.Quotation;

public class ClientDAO {
	private Connection con;
	private String id;

	public ClientDAO(Connection connection, String i) {
		this.con = connection;
		this.id = i;
	}
	
	//first client functionality
	public List<Quotation> findMyQuotations() throws SQLException {
		
		List<Quotation> myQuotations = new ArrayList<>();
		
		//query to extract my quotations
		String query = "SELECT code FROM quotation WHERE clientCode= ?";
		
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, this.id);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Quotation quotation = new Quotation();
					quotation.setCode(result.getString("quotationCode"));
					quotation.setClientCode(this.id);
					quotation.setEmployeeCode(result.getString("employeeCode"));
					quotation.setPrice(result.getFloat("price"));
					myQuotations.add(quotation);
				}
			}
		}
		return myQuotations;
	}
	
	
	//second client functionality
	public void createQuotation(String code/*, String e, String c, Float p*/) throws SQLException {
		
		String query = "INSERT into quotation (code, employeeCode, clientCode, price)   VALUES(?,?,?,?)";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, code);
			//pstatement.setString(2, e);
			//pstatement.setString(3, c);
			//pstatement.setFloat(4, p);
			pstatement.executeUpdate();
		}
	}
	
}
