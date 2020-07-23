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
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.beans.Option;
import it.polimi.tiw.projects.beans.Product;
import it.polimi.tiw.projects.beans.Quotation;
import it.polimi.tiw.projects.dao.ClientDAO;

@WebServlet("/GoToHomeClient")
public class GoToHomeClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;

	public GoToHomeClient() {
		super();
		// Auto-generated constructor stub
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
			String url = "jdbc:mysql://localhost:3306/mydb";
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = null;

		// FILTER	
		
		ClientDAO cDAO = new ClientDAO(connection, u.getCode());
		
		List<Quotation> myQuotations = new ArrayList<>();
		List<Product> availableProducts = new ArrayList<>();
		
		try {
			myQuotations = cDAO.findMyQuotations();
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in client's quotations database extraction");
		} catch (NullPointerException ex) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "my quot null");
		}
		
		for(Quotation q: myQuotations) {
			System.out.println("quot: "+q.getCode()+"  "+ q.getClientCode() + " empc: "+ q.getEmployeeCode()+ " price" +q.getPrice());
		}
		try {
			availableProducts = cDAO.selectAvailableProducts();
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in products database extraction");
		}
		
		for(Product p: availableProducts) {
			
			System.out.println("for product: "+ p.getCode() +" :");
			
			p.setOptionsList(cDAO.selectAvailableOptions(p));
			
			int i= 1;
			for(Option o: p.getOptionsList()) {
				System.out.println("option "+i+"  : "+ o.getName());
				i++;
			}
		}
		
		String path = "/WEB-INF/HomeClient.html";
		ServletContext servletContext = getServletContext();
		
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("myQuotations", myQuotations);
		ctx.setVariable("products", availableProducts);
		//ctx.setVariable("options", availableOptions);
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		try {
			doGet(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
