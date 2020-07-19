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

	public List<Quotation> findMyQuotations() throws SQLException {
		List<Quotation> myQuotations = new ArrayList<>();
		String query = "SELECT Q.id as quotationId, Q.name,  A.usrid as userid, A.maxhours, COALESCE(sum(R.hours), 0) as workedhours FROM project P JOIN assignment A ON P.id=A.prjid LEFT JOIN report R ON R.prjid=P.id AND A.usrid=R.usrid where A.usrid = ? GROUP BY P.id, R.usrid;	";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, this.id);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Quotation quotation = new Quotation();
					quotation.setCode(result.getString("quotationCode"));
					quotation.setClientCode(this.id);
					quotation.setEmployeeCode(result.getString("employeeCode"));
					myQuotations.add(quotation);
				}
			}
		}
		return myQuotations;
	}
}
