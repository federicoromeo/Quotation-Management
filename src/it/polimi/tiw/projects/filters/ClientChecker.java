package it.polimi.tiw.projects.filters;


import it.polimi.tiw.projects.beans.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ClientChecker implements Filter {

    public ClientChecker() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("Executing Client filter...");
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        String loginPath = request.getServletContext().getContextPath() + "/index.html";

        HttpSession session = ((HttpServletRequest) request).getSession();
        User user;
        user = (User) session.getAttribute("user");

        if (!user.getRole().equals("client")){
        	servletResponse.sendRedirect(loginPath);
            return;
        }
        // ok, go on
        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {
    }
    
}
