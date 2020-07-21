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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginpath = getServletContext().getContextPath() + "/index.html";
		User u = null;
		
		
		//verifico che ci sia la sessione e che contenga un bean utente
		
		//TOLGO: uso filtri
		
		System.out.println("faccio doPost");
		
		HttpSession s = request.getSession();
		System.out.println("isNew() : " + s.isNew());
		System.out.println("user(createQuot) : " + s.getAttribute("user").toString());
		System.out.println("user(createQuot) : " + s.getAttribute("user") == null);
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
		
		
		String Q = request.getParameter("quotationCode");
		System.out.println("Q : " + Q);
		if (Q == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing project code");
		}
		String userid = u.getCode();
		ClientDAO cDAO = new ClientDAO(connection, userid);
		try {
			cDAO.createQuotation(Q);
		} catch (SQLException e) {
			throw new ServletException(e);
			//response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure of project creation in database");

		}
			String ctxpath = getServletContext().getContextPath();
			String path = ctxpath + "/GoToHomeClient";
			response.sendRedirect(path);
	}

	
	public List<Option> giveOptions(Product p){
		
		List<Option> options = new ArrayList<>();
		ProductDAO pd = new ProductDAO(p.getName(), connection);
		try {
			pd.setCode(pd.findProduct());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		switch(p.getName()) {
			case "palla": {
				Option o1 = new Option("da basket");
				Option o2 = new Option("da calcio");
				Option o3 = new Option("da tennis");
				options.add(o1);
				options.add(o2);
				options.add(o3);
			}
			case "lampada": {
				Option o1 = new Option("rossa");
				Option o2 = new Option("a led");
				Option o3 = new Option("verde");
				options.add(o1);
				options.add(o2);
				options.add(o3);
			}
		}
		
		return options;
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
