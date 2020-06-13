package admin;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Client;
import store.data.CustomerDB;

/**
 *
 * @author williamdobbs
 */
public class ViewCustomersServlet extends HttpServlet
{

    private String url;
    private String notFoundMessage;
    private int numberOfCustomers;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
      {
        HttpSession session = request.getSession();

        ArrayList<Client> customers = CustomerDB.selectAllCustomers();

        if (!customers.isEmpty())
            {
            numberOfCustomers = customers.size();
            request.setAttribute("numberOfCustomers", numberOfCustomers);
            request.setAttribute("customerList", customers);
            }
        else
            {
            notFoundMessage = "no customer records found";
            request.setAttribute("notFoundMessage", notFoundMessage);
            }

        request.setAttribute("customerList", customers);

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