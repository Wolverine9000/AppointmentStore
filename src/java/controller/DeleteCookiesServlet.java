/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author williamdobbs
 */
public class DeleteCookiesServlet extends HttpServlet
{

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies)
        {
            cookie.setMaxAge(0); //delete the cookie
            cookie.setPath("/"); //allow the entire application to access it
            response.addCookie(cookie);
        }

        String url = "/delete_cookies.jsp";
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }
}
