package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet responsible for checking if user logged in or no. */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = UserServiceFactory.getUserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        if (userService.isUserLoggedIn()) {
            String logoutUrl = userService.createLogoutURL("/");
            String email = userService.getCurrentUser().getEmail();
            response.getWriter().println("<p>Logged In!</p>");
            response.getWriter().println("<p>Email: " + email + "</p>");
            response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a></p>");
            response.setHeader("logIn", "true");
        } 
        else {
            String loginUrl = userService.createLoginURL("/");
            response.getWriter().println("<p>Not logged in.</p>");
            response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a></p>");
            response.setHeader("logIn", "false");
        }
    }

} 