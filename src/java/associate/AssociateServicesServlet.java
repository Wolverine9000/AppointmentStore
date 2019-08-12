/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package associate;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Associate2;
import store.business.Services;
import store.data.AssociateDB;

/**
 *
 * @author williamdobbs
 */
public class AssociateServicesServlet extends HttpServlet
{

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String url;

        Associate2 associate = (Associate2) session.getAttribute("associateRecord");

        if (associate != null)
            {
            ArrayList<Services> services;
            services = AssociateDB.selectAllAsscSvcs(associate.getId());
            if (services == null)
                {
                String notFoundMessage = "no services found for selected associate";
                request.setAttribute("notFoundMessage", notFoundMessage);
                }
            else
                {
                session.setAttribute("services", services);
                }
            url = "/associate/services";
            }
        else
            {
            url = "/index.jsp";
            }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
