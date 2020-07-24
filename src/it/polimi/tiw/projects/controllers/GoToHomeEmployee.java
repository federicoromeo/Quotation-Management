package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.projects.beans.Product;
import it.polimi.tiw.projects.beans.Quotation;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.EmployeeDAO;

@WebServlet("/GoToHomeEmployee")
@MultipartConfig
public class GoToHomeEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	private Connection connection = null;
	
	public GoToHomeEmployee() {
		super();
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
				
	    HttpSession session = ((HttpServletRequest) request).getSession();
        User u = (User) session.getAttribute("user");
		
		// FILTER	
		
		EmployeeDAO eDAO = new EmployeeDAO(connection, u.getCode());
		List<Quotation> myQuotations = new ArrayList<>();
		List<Quotation> neverAssignedQuotations = new ArrayList<>();
		
		try {
			myQuotations = eDAO.findMyQuotations();
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in employee's quotations database extraction");
		}
		
		try {
			neverAssignedQuotations = eDAO.findNeverAssignedQuotation();
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in never assigned quotations database extraction");
		}
		
		String path = "/WEB-INF/HomeEmployee.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("myQuotations", myQuotations);
		ctx.setVariable("neverAssignedQuotations", neverAssignedQuotations);
		templateEngine.process(path, ctx, response.getWriter());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		int selectedPrice = 0;
		try {
			selectedPrice = Integer.parseInt(request.getParameter("price"));
		}
		catch(Exception ex) {
			response.sendRedirect(getServletContext().getContextPath() +"/WEB-INF/PriceQuotation.html");
		}
		
		User u = null;
		HttpSession s = request.getSession();
		if (s.isNew() || s.getAttribute("user") == null) {
			response.sendRedirect(getServletContext().getContextPath() +"index.html");
			return;
		} else {
			u = (User) s.getAttribute("user");
			if (!u.getRole().equals("employee")) {
				response.sendRedirect(getServletContext().getContextPath() +"index.html");
				return;
			}
		}
		
		System.out.println("ci sono         oooo");

		
		EmployeeDAO eDAO = new EmployeeDAO(connection, u.getCode());
		int quotationCode = Integer.parseInt(request.getParameter("selectedQuotationCode"));
		
		try {
			eDAO.priceQuotation(quotationCode, selectedPrice);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doGet(request, response);
	
	}
	
	
	

}
