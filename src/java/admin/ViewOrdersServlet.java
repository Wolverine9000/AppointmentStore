/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Invoice;
import store.data.InvoiceDB;

/**
 *
 * @author williamdobbs
 */
public class ViewOrdersServlet extends HttpServlet
{

    private String url;
    private String notFoundMessage;
    private int numberOfInvoices;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            HttpSession session = request.getSession();

            ArrayList<Invoice> invoices = InvoiceDB.selectAllInvoices();

            if (!invoices.isEmpty())
            {
                numberOfInvoices = invoices.size();

                request.setAttribute("numberOfInvoices", numberOfInvoices);
                request.setAttribute("orderList", invoices);
            }
            else if (invoices == null)
            {
                notFoundMessage = "a database error has occurred.";
                request.setAttribute("notFoundMessage", notFoundMessage);
            }
        }
        catch (NullPointerException npe)
        {
            LogFile.databaseError("viewOrdersServlet ",
                    npe.getMessage(), npe.toString());

            FileOutputStream fos = new FileOutputStream(new File("/Users/williamdobbs/NetBeansProjects/AppointmentStore/"
                    + "/web/logs/ExceptionsLogFiles/viewOrdersExceptionLogFile.txt"));
            PrintStream ps = new PrintStream(fos);
            npe.printStackTrace(ps);

            notFoundMessage = "A NPE error has occurred.";
            request.setAttribute("notFoundMessage", notFoundMessage);
        }

        url = "/admin/index.jsp";

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
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
