/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

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
public class AdminLogOutServlet extends HttpServlet
{

    private String userPath;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        // TODO fix admin security time-out problem (DONE 4/8/13)
        HttpSession session = request.getSession();

        userPath = request.getServletPath();

        // if logout is requested
        if (userPath.equals("/admin/logout"))
            {
            session = request.getSession();
            session.invalidate();   // terminate session
            response.sendRedirect("/AppointmentStore/admin/");
            }
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
    {
        doPost(request, response);
    }
}
