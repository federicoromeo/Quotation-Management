package it.polimi.tiw.projects.controllers;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import java.sql.*;

@WebServlet("/BasicQueryServlet")
public class BasicQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	
	public void init() throws ServletException{
		try {
			ServletContext context = getServletContext();
			String driver = context.getInitParameter("dbDriver");
			String url = context.getInitParameter("dbUrl2");
			String user = context.getInitParameter("dbUser");
			String password = context.getInitParameter("dbPassword");
			Class.forName(driver);
			connection = DriverManager.getConnection(url,user,password);
		}catch (ClassNotFoundException e) {
			throw new UnavailableException("Can't load db driver");
		}catch (SQLException e ) {
			throw new UnavailableException("Couldn't connect");
		}catch(NullPointerException e) {
			throw new NullPointerException("Null p");
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String query = "SELECT code, FROM client";
		ResultSet result = null;
		Statement statement = null;
		String test = "Ok";
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while(result.next()) {
				out.println("Firstname: "+ result.getString("firstname") + "Lastname: " + result.getString("lastname") );
				
			}
		}catch (SQLException e) { out.append("SQL RES ERROR"); 
		}finally {
			try { result.close(); }
			catch (Exception el) {out.append("SQL STMT ERROR "); }
			try { statement.close(); 
			}catch( Exception el) {
				out.append("SQL STMT ERROR ");
			}
				
			}
		
		}
		
		
	
	}
