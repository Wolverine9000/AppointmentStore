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
import store.business.Invoice;
import store.data.InvoiceDB;

/**
 *
 * @author williamdobbs
 */
public class OrderRecordServlet extends HttpServlet
{

    private String url;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        String invoice_id = request.getQueryString();

        // TODO write error code for records not found
        Invoice orderRecord = InvoiceDB.selectInvoice(Integer.parseInt(invoice_id));

        request.setAttribute("orderRecord", orderRecord);

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
