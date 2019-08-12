/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Client;
import store.data.UserLogDB;

/**
 *
 * @author williamdobbs
 */
public class CustomerLogOutServlet extends HttpServlet
{

    private String url;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Client client = (Client) session.getAttribute("client");

        if (client != null)
            {
            Date date = new Date();
            String out = "out";

            // write successful logout insert to log file
            String successPoint = "CustomerLogOutServlet logout database insert SUCCESSFUL ";
            LogFile.loginMessage(client.getEmail(), successPoint);
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
            int logOut = UserLogDB.insert(client.getId(), sqlDate, out);
            if (logOut == 0)
                {
                LogFile.loginMessage(client.getEmail(), out);
                }
            }
        session.invalidate();
        url = "/index.jsp";

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
