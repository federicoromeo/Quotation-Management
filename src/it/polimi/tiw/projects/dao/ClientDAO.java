package it.polimi.tiw.projects.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.projects.beans.Product;
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
		String query = "SELECT code FROM quotation WHERE client_code= ?";
		
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, this.id);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Quotation quotation = new Quotation();
					quotation.setCode(result.getString("quotation_code"));
					quotation.setClientCode(this.id);
					quotation.setEmployeeCode(result.getString("employee_code"));
					quotation.setPrice(result.getFloat("price"));
					myQuotations.add(quotation);
				}
			}
		}
		return myQuotations;
	}
	
	
	//second client functionality
	public void createQuotation(String code/*, String e, String c, Float p*/) throws SQLException {
		
		String query = "INSERT into quotation (code, employee_code, client_code, product_code, price)   VALUES(?,?,?,?,?)";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, code);
			pstatement.setString(2, "null");
			pstatement.setString(3, id);
			pstatement.setString(4, "null");
			pstatement.setFloat(5, 0);
			pstatement.executeUpdate();
		}
	}

	public List<Product> selectAvailableProducts() throws SQLException{
		
		String query = "INSERT into product (code,image,name)   VALUES(?,?,?)";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, "1");
			pstatement.setBlob(2, new FileInputStream("C:\\Users\\filip\\Pictures\\Sfondi\\37567.jpg"));
			pstatement.setString(3, "palla");
			pstatement.executeUpdate();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		query = "SELECT code,name FROM product";
		List<Product> productsList = new ArrayList<>();
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					System.out.println("entro");
					Product product = new Product();
					product.setCode(result.getString("code"));
					product.setName(result.getString("name"));
					productsList.add(product);
				}
			}
		}
		return productsList;
	}
	
}
