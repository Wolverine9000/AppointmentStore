/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package associate;

import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Associate2;
import store.data.UserLogDB;

/**
 *
 * @author williamdobbs
 */
public class LogOutAssociateServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String urlPath;
        urlPath = request.getServletPath();
        String requestURI = request.getRequestURI();
        String url;

        Associate2 associate = (Associate2) session.getAttribute("associateRecord");

        if (associate != null)
        {
            Date date = new Date();
            String out = "out";

            // record logout in database
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
            int logOut = UserLogDB.insertAssociate(associate.getId(), sqlDate, out);
            // record logout to log file
            LogFile.associateLog("LogOutAssociateServlet", associate.getEmail() + " ", "successful LOGOUT");

//            if (logOut == 0 || logEvent == false)
//                {
//                LogFile.loginMessage(associate.getEmail(), out);
//                }
            session.invalidate();
        }
        if ("/mobile/logoutAssociate".equals(urlPath))
        {
            url = "/mobile/m_associateHome.jsp";
        }
        else
        {
            url = "/associate/associateLogin";
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
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
