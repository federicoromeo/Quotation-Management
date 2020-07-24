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
import javax.servlet.annotation.MultipartConfig;
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
@MultipartConfig
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
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		

		////////////////////////////////////////
		String ctxpath = getServletContext().getContextPath();
		String path = ctxpath + "/GoToHomeClient";

		
		String[] selectedOptions = request.getParameterValues("optionsList");
		Integer productCode = null;
		try {
			productCode = Integer.parseInt(request.getParameter("productCode"));
		} catch(Exception e) {
			response.sendRedirect(path);
			return;
		}
		String clientCode = request.getParameter("clientCode");
		if(!notEmptyOptions(selectedOptions)) {
			response.sendRedirect(path);
			return;
		}
		int[] selectedOptionsInt = new int[selectedOptions.length];
		boolean isValid = false;
		
		HttpSession session = ((HttpServletRequest) request).getSession();
        User u = (User) session.getAttribute("user");

		ClientDAO cDAO = new ClientDAO(connection, u.getCode());

		if (productCode != null) {
			
			if (clientCode != null) { 
				int j = 0;
				while( j<selectedOptions.length ) {
					if(selectedOptions[j]!=null) {
						selectedOptionsInt[j] = Integer.parseInt(selectedOptions[j]);
						j++;
					}
					else response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing option code");
				}
				
				isValid = checkInput(selectedOptionsInt, productCode, cDAO);
				
				// nasty client
				if(!isValid) {
					System.out.println("\nNON AGGIUNTO\n");
					response.sendRedirect(path);
				}
				// allright
				else {
					try {
						System.out.println("\n AGGIUNGENDO....\n");
						cDAO.createQuotation(productCode, clientCode, selectedOptionsInt);
					} catch (SQLException e) {
						throw new ServletException(e);
						//response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure of project creation in database");
					}
					response.sendRedirect(path);			
				}
			}
			else response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing client code");
		}
		else response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing product code");
	}


	// check if the user selected options non belonging to the selected product
	private boolean checkInput(int[] selectedOptionsInt, int rightProductCode, ClientDAO cDAO) {

		if(selectedOptionsInt.length==0 || selectedOptionsInt == null) {
			return false;
		}
		int j = 0;
		while( j < selectedOptionsInt.length ) {
			
			//query to get the real code
			int product_code = cDAO.checkOptionCode(selectedOptionsInt[j]);
			
			if(!(rightProductCode == product_code)) {
				return false;
			}
			j++;
		}
		return true;
	}
	
	private boolean notEmptyOptions(String[] options) {
		if(options==null || options.length==0) {
			return false;
		}
		return true;
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
