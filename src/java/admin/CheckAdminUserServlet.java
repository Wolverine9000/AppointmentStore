/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.SystemAdmin;
import store.data.AdminUserDB;

/**
 *
 * @author williamdobbs
 */
public class CheckAdminUserServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException            
            
      {
        
        HttpSession session = request.getSession();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SystemAdmin adminuser = new SystemAdmin();
        adminuser.setUserName(username);
        adminuser.setPassword(password);        

        // validate the parameters
        String message = "";
        String url = null;
        if (username.length() == 0
                || password.length() == 0)
            {
            message = "Please fill out both text boxes.";
            url = "/admin/login.jsp";
            }
        //check email address and password
        else if (AdminUserDB.checkAdminCredenitals(username, password) == false)
            {
            message = "Your username or password is incorrect. <br>"
                    + "Please re-enter.";
            url = "/admin/login.jsp";
            }
        else
            {
            url = "/admin/index.jsp";
            }
        request.setAttribute("message", message);
        session.setAttribute("username", username);

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
      }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
      {
        doPost(request, response);
      }
}
