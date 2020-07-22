package it.polimi.tiw.projects.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.projects.beans.User;

public class QuotationDAO {
	private Connection con;
	private int code;

	public QuotationDAO(Connection connection, int c) {
		this.con = connection;
		this.code = c;
	}

	public User findEmployee() throws SQLException {
		User owner = null;
		String query = "SELECT U.code from user U JOIN quotation Q ON Q.employeeCode=U.id where Q.code = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, this.code);
			try (ResultSet result = pstatement.executeQuery();) {
				if (result.next()) {
					owner = new User();
					owner.setCode(result.getString("code"));
				}
			}
		}
		return owner;
	}


}
