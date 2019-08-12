/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Category;
import store.business.Product;
import store.data.ProductDB;

/**
 *
 * @author williamdobbs
 */
public class AddToCartServlet extends HttpServlet
{

    private String url;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        String userPath = request.getServletPath();

        Category selectedCategory = (Category) session.getAttribute("selectedCategory");
        session.setAttribute("selectedCategory", selectedCategory);

        if (cart == null)
            {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
            }
        // get user input from request
        String productId = request.getParameter("productId");

        if (!productId.isEmpty())
            {
            Product product = ProductDB.selectProduct(Integer.parseInt(productId));
            cart.addItem(product);
            }
        else
            {
            session.invalidate();
            url = "/index.jsp";
            }

        // forward to the category View
        url = "/category";
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
