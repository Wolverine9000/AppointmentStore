package controller;

import cart.ShoppingCart;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Client;
import store.data.CustomerDB;
import store.util.CookieUtil;

public class CheckOutServlet extends HttpServlet
{

    private String surcharge = "0.00";
    private String url = "";
    private String message = "";

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Client client = (Client) session.getAttribute("client");
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart != null)
            {
            // calculate total
            cart.calculateTotal(surcharge);

            if (client != null)
                {
                // send client to checkout page
                url = "/checkout.jsp";
                }
            else if (client == null)
                {
                Cookie[] cookies = request.getCookies();
                String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
                if (emailAddress != null)
                    {
                    client = CustomerDB.selectClient(emailAddress);
                    if (client != null)
                        {
                        session.setAttribute("client", client);
                        }
                    // send client to login page
                    message = "Please login before completing your purchase";
                    String checkout = "checkout"; // var for checkout page
                    request.setAttribute("message", message);
                    session.setAttribute("view", checkout); // send client to checkout page after logging in
                    url = "/userlogin";
                    }
                }
            }
        else
            {
            url = "/index.jsp";
            }
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }
}
