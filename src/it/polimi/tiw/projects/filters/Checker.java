package it.polimi.tiw.projects.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class Checker implements Filter {

    public Checker() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("Executing login checker filter...");

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String loginPath = servletRequest.getServletContext().getContextPath() + "/index.html";

        HttpSession session = servletRequest.getSession();
        
        System.out.println(session.isNew());
        System.out.println(session.getAttribute("user") == null);
        System.out.println(session.getAttribute("user"));
        if (session.isNew() || session.getAttribute("user") == null) {
            servletResponse.sendRedirect(loginPath);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
}
