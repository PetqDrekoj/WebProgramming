package webubb.controller;

/**
 * Created by forest.
 */


import webubb.domain.User;
import webubb.model.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginController extends HttpServlet {

    public LoginController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String logout = "";
        try {
            logout = request.getParameter("logout");
        } catch (Exception e) {
            logout = "false";
        }
        if (logout == null || logout.equals("") || logout.equals("false")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            RequestDispatcher rd = null;

            DBManager dbmanager = new DBManager();
            User user = dbmanager.authenticate(username, password);
            if (user != null) {
                rd = request.getRequestDispatcher("/succes.jsp");
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
            } else {
                rd = request.getRequestDispatcher("/error.jsp");
            }
            rd.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.invalidate();
            response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
            PrintWriter p = new PrintWriter(response.getOutputStream());
            p.print("true");
            p.flush();
        }


    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || session.getAttribute("user") == "") {
            response.setContentType("text");
            PrintWriter p = new PrintWriter(response.getOutputStream());
            p.print("false");
            p.flush();
        } else {
            if(request.getParameter("result") == null || request.getParameter("result") == ""){
                session.getAttribute("result");
            }
            else{
                session.setAttribute("result",request.getParameter("result"));
            }
        }
    }

}