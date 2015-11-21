/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import bean.LoginBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author David
 */
public class LoginFilter implements Filter {

    /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute
        LoginBean loginBean = (LoginBean) ((HttpServletRequest) request).getSession().getAttribute("LoginBean");
        String rol = "";
        if ((String) ((HttpServletRequest) request).getSession().getAttribute("rol")!=null) {
            rol = (String) ((HttpServletRequest) request).getSession().getAttribute("rol");
        }
        HttpServletRequest req = (HttpServletRequest) request;
        // For the first application request there is no loginBean in the session so user needs to log in
        // For other requests loginBean is present but we need to check if user has logged in successfully
        if (loginBean != null) {
            if (!rol.equals("")&& (!req.getRequestURL().toString().contains(rol))) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/"+rol+"/index.xhtml");
            }else{
               chain.doFilter(request, response);
            }
         }
        else{
         String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath +"/index.xhtml");
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }

    @Override
    public void destroy() {
        // Nothing to do here!
    }
}