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
import messages.LogFile;
import store.business.Client;
import store.data.CustomerDB;

/**
 *
 * @author williamdobbs
 */
public class CustomerRecordServlet extends HttpServlet
{

    private String url;
    private String notFoundMessage;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        // get customer id from request
        String clientID = request.getQueryString();
        try
            {
            int clientIdInt = Integer.parseInt(clientID);
            Client clientRecord = (Client) CustomerDB.selectClient(clientIdInt);

            if (clientRecord != null)
                {
                request.setAttribute("customerRecord", clientRecord);
                }
            else
                {
                notFoundMessage = "customer record not found";
                request.setAttribute("notFoundMessage", notFoundMessage);
                }
            }
        catch (NullPointerException npe)
            {
            LogFile.generalLog("CustomerRecordServlet ", npe.getMessage());
            notFoundMessage = "A Client ID error has occured";
            }

        url = "/admin/index.jsp";

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
