package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {

	private String name;
	private Connection connection;
	
	
	public ProductDAO(Connection connection) {
		this.connection = connection;
	}

	public String findProduct() throws SQLException{
		
		String query = "SELECT code FROM product WHERE name=?";
		String code = "not found";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setString(1, this.name);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					try {
						code = result.getString("code");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return code;
	}
	

}
