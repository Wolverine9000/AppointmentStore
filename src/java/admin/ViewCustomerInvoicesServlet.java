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

public class ViewCustomerInvoicesServlet extends HttpServlet
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

        // get customer id from request
        String clientId = request.getQueryString();

        // get customer invoices using customer id
        ArrayList<Invoice> invoices = InvoiceDB.selectCustomerInvoices(Integer.parseInt(clientId));
        if (invoices != null)
            {
            numberOfInvoices = invoices.size();

            url = "/admin/index.jsp";

            request.setAttribute("orderList", invoices);
            request.setAttribute("adminMessage", adminMessage);
            request.setAttribute("numberOfInvoices", numberOfInvoices);

            }
        else
            {
            adminMessage = "not found";
            request.setAttribute("adminMessage", adminMessage);
            }

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
