/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Product;
import store.data.ProductDB;

/**
 *
 * @author williamdobbs
 */
public class UpdateCartServlet extends HttpServlet
{

    private String url;

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
      {
        HttpSession session = request.getSession();

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart != null)
            {
            // get input from request
            int productId = Integer.parseInt(request.getParameter("productId"));
            String quantity = request.getParameter("quantity");

            Product product = ProductDB.selectProduct(productId);
            cart.update(product, quantity);

            // forward to the cart View
            url = "/cart";
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
            }
        else
            {
            session.invalidate();
            }
        url = "/index.jsp";
      }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
      {
        doGet(request, response);
      }
}
           // TODO provide code for when session 'times out'
           // error is > java.lang.NullPointerException
	   // controller.UpdateCartServlet.doGet(UpdateCartServlet.java:40)