package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

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

import it.polimi.tiw.projects.beans.Quotation;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.EmployeeDAO;
import it.polimi.tiw.projects.dao.QuotationDAO;


@WebServlet("/GoToPriceQuotation")
@MultipartConfig
public class GoToPriceQuotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;

	public GoToPriceQuotation() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		
	    HttpSession session = ((HttpServletRequest) request).getSession();
        User u = (User) session.getAttribute("user");
		
		// FILTER	

		int chosenQuotation = 0;
		try {
			chosenQuotation = Integer.parseInt(request.getParameter("selectedQuotation"));	
			
			EmployeeDAO eDao = new EmployeeDAO(connection, u.getCode());
				
			Quotation selectedQuotation = null;
			try {
				selectedQuotation = eDao.returnSelectedQuotation(chosenQuotation);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String path = "/WEB-INF/PriceQuotation.html";
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("selectedQuotation", selectedQuotation);
			templateEngine.process(path, ctx, response.getWriter());
		} catch(Exception e) {
			System.out.println("nasty client, no quotations available");
			String ctxpath = getServletContext().getContextPath();
			String path = ctxpath + "/GoToHomeEmployee";
			System.out.println("path : " + path);
			response.sendRedirect(path);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doGet(request, response);
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
