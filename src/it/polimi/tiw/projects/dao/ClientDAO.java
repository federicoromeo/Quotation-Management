package it.polimi.tiw.projects.dao;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.polimi.tiw.projects.beans.Option;
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
	public void createQuotation(String productCode, String clientCode) throws SQLException {
		
		String query = "INSERT into quotation (code, employee_code, client_code, product_code, option_code, price)   VALUES(?,?,?,?,?,?)";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, productCode/*Integer.toString(new Random().nextInt(1000))*/);
			pstatement.setString(2, "null");
			pstatement.setString(3, clientCode);
			pstatement.setString(4, productCode);
			Option o = findOption();
			pstatement.setString(5, o.getName());
			pstatement.setFloat(6, 0);
			pstatement.executeUpdate();
		}
	}

	private Option findOption() {
		String query = "SELECT name,code FROM option WHERE code = ?";
		Option o = null;
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, "BIG");
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					o = new Option();
					o.setName(result.getString("name"));
					o.setCode(result.getString("code"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return o;
	}

	public List<Product> selectAvailableProducts() throws SQLException{
		
		String query = "SELECT code,name FROM product";
		List<Product> productsList = new ArrayList<>();
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Product product = new Product();
					product.setCode(result.getString("code"));
					product.setName(result.getString("name"));
					productsList.add(product);
				}
			}
		}
		return productsList;
	}

	
	public List<Option> selectAvailableOptions(Product p) {
		
		String query = "SELECT code,name,onsale FROM option WHERE product_code = ?";
		List<Option> optionsList = new ArrayList<>();
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, p.getCode());
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Option option = new Option(result.getString("code"),result.getString("name"),p.getCode());
					optionsList.add(option);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return p.getOptionsList();
	}
	
}
