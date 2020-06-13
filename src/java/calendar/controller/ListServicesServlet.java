/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Services;
import store.data.AssociateDB;

/**
 *
 * @author williamdobbs
 */
public class ListServicesServlet extends HttpServlet
{

    private String url;
    private String notFoundMessage;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        String associateId = request.getParameter("associateId");

        // TODO error and null handling
        ArrayList<Services> services =
                AssociateDB.selectAssociateServices(Integer.parseInt(associateId));
        if (services == null)
            {
            notFoundMessage = "no services found for selected associate";
            request.setAttribute("notFoundMessage", notFoundMessage);
            }

        session.setAttribute("services", services);
        session.setAttribute("associateId", associateId);

        url = "/book.jsp";

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
