/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author williamdobbs
 */
public class ViewCartServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
      {
        String url = request.getServletPath();
        HttpSession session = request.getSession();

        String clear = request.getParameter("clear");
        
        if ((clear != null) && clear.equals("true"))
            {

            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            cart.clear();
            }

        url = "/cart";
        try
            {
            request.getRequestDispatcher(url).forward(request, response);
            }
        catch (Exception ex)
            {
            }
        // TODO provide code for when session 'times out'
        // error is > java.lang.NullPointerException
	// controller.UpdateCartServlet.doGet(UpdateCartServlet.java:40)
      }
}