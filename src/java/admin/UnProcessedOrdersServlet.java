/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Invoice;
import store.data.InvoiceDB;

/**
 *
 * @author williamdobbs
 */
public class UnProcessedOrdersServlet extends HttpServlet
{

    private String url;
    private String adminMessage;
    private int numberOfInvoices;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
      {
        HttpSession session = request.getSession();

        ArrayList<Invoice> unProcessedInvoices = InvoiceDB.selectUnprocessedInvoices_2();
        if (!unProcessedInvoices.isEmpty())
            {
            adminMessage = " (un-processed)";
            numberOfInvoices = unProcessedInvoices.size();
            request.setAttribute("numberOfInvoices", numberOfInvoices);
            request.setAttribute("orderList", unProcessedInvoices);
            }
        else
            {
            adminMessage = "no un-processed invoices found";
            }
        
        request.setAttribute("adminMessage", adminMessage);
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
