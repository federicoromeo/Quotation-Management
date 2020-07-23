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
				int i = 0;
				while (result.next()) {
					System.out.println("next");
					Quotation quotation = new Quotation();
					quotation.setCode(result.getInt("code"));
					quotation.setClientCode(this.id);
					try {
						quotation.setEmployeeCode(result.getString("employee_code"));
					} catch(Exception e) {
						quotation.setEmployeeCode("0");
					}
					try {
						quotation.setPrice(result.getInt("price"));
					} catch(Exception e) {
						quotation.setPrice(0);
					}
					myQuotations.add(quotation);
					i++;
				}
				System.out.println("numero quot: " +i);
			}
		}
		return myQuotations;
	}
	
	
	//second client functionality
	public void createQuotation(int productCode, String clientCode) throws SQLException {
		
		String query = "INSERT into quotation (client_code, product_code) VALUES(?,?)";
		
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			//pstatement.setString(1, Integer.toString(new Random().nextInt(1000)));
			//pstatement.setString(2, "n.a.");
			pstatement.setString(1, clientCode);
			pstatement.setInt(2, productCode);
			//pstatement.setFloat(5, (float) 0.0);
			pstatement.executeUpdate();
		}
	}


	public List<Product> selectAvailableProducts() throws SQLException{
		
		String query = "SELECT code,name FROM product";
		List<Product> productsList = new ArrayList<>();
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Product product = new Product();
					product.setCode(result.getInt("code"));
					product.setName(result.getString("name"));
					productsList.add(product);
				}
			}
		}
		return productsList;
	}

	
	public List<Option> selectAvailableOptions(Product p) {
		
		String query = "SELECT code,name FROM opzione WHERE product_code= ?";
		
		List<Option> optionsList = new ArrayList<>();
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
					
			pstatement.setInt(1, p.getCode());
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {

					int code = result.getInt("code");
					String name = result.getString("name");
					
					Option option = new Option(code,name,p.getCode());
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
		return optionsList;
	}
	

	
	// check for nasty client
	public int checkOptionCode(int optionCode) {
		
		String query = "SELECT product_code FROM opzione WHERE code =?";
		int rightCode = 0;
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, optionCode);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					rightCode = result.getInt("product_code");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rightCode;
	}
	
				
				
				
				
				
}
