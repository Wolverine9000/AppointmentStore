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
import store.util.DateUtil;

/**
 *
 * @author williamdobbs
 */
public class FindInvoiceDateRangeServlet extends HttpServlet
{

    private String url;
    private String adminMessage;
    private String notFoundMessage;
    private int numberOfInvoices;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        // get dates from request
        String dateFrom = request.getParameter("from");
        String dateTo = request.getParameter("to");
        java.sql.Timestamp dateFromSt = DateUtil.convertDate(dateFrom);
        java.sql.Timestamp dateToSt = DateUtil.convertDate(dateTo);
        if (dateToSt != null || dateFromSt != null)
            {
            // get invoices
            ArrayList<Invoice> invoices = InvoiceDB.selectInvoiceDates(dateFromSt, dateToSt);
            numberOfInvoices = invoices.size();
            if (numberOfInvoices == 0)
                {
                notFoundMessage = "no invoices found";
                request.setAttribute("notFoundMessage", notFoundMessage);
                }
            else
                {
                adminMessage = "selected from " + dateFrom + " to " + dateTo;
                request.setAttribute("adminMessage", adminMessage);
                }

            request.setAttribute("numberOfInvoices", numberOfInvoices);
            request.setAttribute("orderList", invoices);
            request.setAttribute("dateFromSt", dateFromSt);
            request.setAttribute("dateToSt", dateToSt);

            url = "/adminIndex.jsp";
            }
        else
            {
            notFoundMessage = "please enter valid dates";
            url = "/admin/findAdmin.jsp";
            }

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
