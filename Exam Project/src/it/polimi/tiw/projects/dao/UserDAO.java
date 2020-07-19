package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import it.polimi.tiw.projects.beans.Client;

public class UserDAO {
	private Connection con;

	public UserDAO(Connection connection) {
		this.con = connection;
	}

	public Client checkCredentials(String usrn, String pwd) throws SQLException {
		String query = "SELECT  id, role, username FROM user  WHERE username = ? AND password =?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, usrn);
			pstatement.setString(2, pwd);
			try (ResultSet result = pstatement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					result.next();
					Client user = new Client();
					user.setId(result.getInt("id"));
					user.setRole(result.getString("role"));
					user.setUsername(result.getString("username"));
					return user;
				}
			}
		}
	}
}
