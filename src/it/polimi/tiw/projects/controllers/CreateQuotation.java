package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.beans.Option;
import it.polimi.tiw.projects.beans.Product;
import it.polimi.tiw.projects.dao.ClientDAO;
import it.polimi.tiw.projects.dao.ProductDAO;

@WebServlet("/CreateQuotation")
public class CreateQuotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;

	public CreateQuotation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
		try {
			ServletContext context = getServletContext();
			String driver = context.getInitParameter("dbDriver");
			String url = "jdbc:mysql://localhost:3306/project_db";
			String user = context.getInitParameter("dbUser");
			String password = context.getInitParameter("dbPassword");
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Can't load database driver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		String loginpath = getServletContext().getContextPath() + "/index.html";
		User u = null;
				
		//verifico che ci sia la sessione e che contenga un bean utente
		//TOLGO: uso filtri
		
		System.out.println("faccio doPost");
		
		HttpSession s = request.getSession();
	 
		if (s.isNew() || s.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		} else {
			u = (User) s.getAttribute("user");
			if (!u.getRole().equals("client")) {
				response.sendRedirect(loginpath);
				return;
			}
		}
		////////////////////////////////////////
		
		String productCode = request.getParameter("productCode");
		String optionCode = request.getParameter("optionCode");
		String clientCode = request.getParameter("clientCode");
		System.out.println("prod code: " + productCode);
		System.out.println("opt code: " + optionCode);
		System.out.println("cli code: " + clientCode);

		if (productCode == null || clientCode==null || optionCode==null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing product or client or option code");
		}

		ClientDAO cDAO = new ClientDAO(connection, u.getCode());
		
		try {
			cDAO.createQuotation(productCode, clientCode, optionCode);
		} catch (SQLException e) {
			throw new ServletException(e);
			//response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure of project creation in database");
		}
			String ctxpath = getServletContext().getContextPath();
			String path = ctxpath + "/GoToHomeClient";
			response.sendRedirect(path);
	}


	
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {
		}
	}
	
}
