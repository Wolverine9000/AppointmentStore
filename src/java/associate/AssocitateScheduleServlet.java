/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package associate;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author williamdobbs
 */
public class AssocitateScheduleServlet extends HttpServlet
{

    private String url;
    private String notFoundMessage;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
      {
//        HttpSession session = request.getSession();
//
//        String userPath = request.getServletPath();
//
//        // if category page is requested
//        if (userPath.equals("/associateSchedule"))
//            {
//            // get associate email address from j_username
//        String associateEmail = request.getParameter("emailAddress");
//
//        Associate associate = AssociateDB.selectAssociate(associateEmail);
//            }
//            
//        
//
//        if (associate != null)
//            {
//            request.setAttribute("associateRecord", associate);
//            }
//        else
//            {
//            notFoundMessage = "associate record not found";
//            request.setAttribute("notFoundMessage", notFoundMessage);
//            }
//
//        url = "/associate/index.jsp";

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
