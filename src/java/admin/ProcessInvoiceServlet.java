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
import store.data.InvoiceDB;

/**
 *
 * @author williamdobbs
 */
public class ProcessInvoiceServlet extends HttpServlet
{

    private String url;
    private String notFoundMessage;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
      {
        
        String invoice_id = request.getQueryString();

        int processInvoice = InvoiceDB.updateInvoiceIsProcessed(Integer.parseInt(invoice_id));
        if (processInvoice != 0)
            {
            notFoundMessage = "invoice processed";
            }
        else
            {
            notFoundMessage = "a database error has occurred";
            }
            request.setAttribute("notFoundMessage", notFoundMessage);

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
