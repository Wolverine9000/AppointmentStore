/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Category;
import store.business.Product;
import store.business.Services;
import store.data.ProductDB;

/**
 *
 * @author williamdobbs
 */
public class ViewCategoryServlet extends HttpServlet
{

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        int category_id = Integer.parseInt(request.getQueryString());

        if (category_id == 5)
            {
            Category selectedCategory = ProductDB.selectCategory(category_id);
            ArrayList<Services> categoryServices = ProductDB.selectAllServices();

            session.setAttribute("categoryServices", categoryServices);
            // set session attribute on category name
            session.setAttribute("selectedCategory", selectedCategory);
            }
        else
            {
            // get category name using category_id
            Category selectedCategory = ProductDB.selectCategory(category_id);
            ArrayList<Product> categoryProducts = ProductDB.selectProducts(category_id);

            // set session attribute on category name
            session.setAttribute("selectedCategory", selectedCategory);
            session.setAttribute("categoryProducts", categoryProducts);
            }

        request.setAttribute("category_id", category_id);

        // forward to the category View
        String url = "/category";

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
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
