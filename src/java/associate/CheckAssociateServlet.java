/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package associate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Associate2;
import store.business.Category;
import store.business.Client;
import store.business.Services;
import store.data.AssociateDB;
import store.data.CustomerDB;
import store.data.ProductDB;
import store.data.UserLogDB;
import store.util.CookieUtil;
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class CheckAssociateServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String urlPath;
        urlPath = request.getServletPath();

        String url;
        String mobileError = "Login failed. Please check your credentials and try again.";

        // get associate email address and username
        String emailAddress = request.getParameter("emailAddress");
        String password = request.getParameter("password");
        // TODO get cookies
        Cookie[] cookies = request.getCookies();
        String emailAddr = CookieUtil.getCookieValue(cookies, "emailCookie");

        // validate user data
        boolean validationErrorFlag = Validator.validateLogin(emailAddress, password, request);

        // if validation error found, return user to login
        if (validationErrorFlag == true)
        {
            request.setAttribute("validationErrorFlag", validationErrorFlag);
            request.setAttribute("emailAddress", emailAddress);
            request.setAttribute("password", password);

            if ("/mobile/m_associateHome".equals(urlPath))
            {
                url = urlPath + ".jsp";
                request.setAttribute("mobileError", mobileError);
            }
            else
            {
                url = "/associate/associateLogin";
            }
        }
        else
        {
            // get Associate record
            Associate2 associateRecord = AssociateDB.selectAssociateHash(emailAddress, password);

            if (associateRecord == null)
            {
                // write failed login to log file
                String failPoint = "Associate selectAssociate login failure " + urlPath + " ";
                LogFile.loginMessage(emailAddress, failPoint);
                validationErrorFlag = true;
                boolean notFoundMessage;
                notFoundMessage = true;

                request.setAttribute("notFoundMessage", notFoundMessage);
                request.setAttribute("validationErrorFlag", validationErrorFlag);
                if ("/mobile/m_associateHome".equals(urlPath))
                {
                    url = urlPath + ".jsp";
                    request.setAttribute("mobileError", mobileError);
                }
                else
                {
                    url = "/associate/associateLogin";
                }
            }
            else
            {
                Date date = new Date();
                String in = "in";
                java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
                // record Associate login date and time into database
                int login = UserLogDB.insertAssociate(associateRecord.getId(), sqlDate, in);
                // record Associate login to log file
                LogFile.associateLog("CheckAssociateServlet " + urlPath, emailAddress, " successful LOGIN ");

                if ("".equals(emailAddr))
                {
                    Cookie emailCookie = new Cookie("emailCookie", emailAddress);
                    emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
                    emailCookie.setPath("/");
                    response.addCookie(emailCookie);
                }

                ArrayList<Category> categoriesSvc = ProductDB.selectCategoryServices();
                ArrayList<Services> servicesAll;
                servicesAll = ProductDB.selectAllServices();
                ArrayList<Client> clients;
                clients = CustomerDB.selectAllCustomers();
                ArrayList<Associate2> associatesAll = AssociateDB.selectAssociatesAll();
                // set associate session to one day for development purposes only
                // TODO re-visit the settings for this session
                session.setMaxInactiveInterval(60 * 60 * 24);
                Associate2 associateInfo = AssociateDB.selectAssociateInfo(associateRecord.getId());
                if (associateInfo == null || login == 0)
                {
                    // write failed login into log file
                    String failPoint = "CheckAssociateServlet Associate login database insert failed ";
                    LogFile.associateLog(emailAddress, failPoint + " " + urlPath + " ", "LOGIN FAILED");

                    session.setAttribute("associateInfo", associateInfo);
                }
                else
                {
                    session.setAttribute("associatesAll", associatesAll);
                    session.setAttribute("clients", clients);
                    session.setAttribute("servicesAll", servicesAll);
                    session.setAttribute("categoriesSvc", categoriesSvc);
                    session.setAttribute("associateInfo", associateInfo);
                    session.setAttribute("associateRecord", associateRecord);
                }
                if ("/mobile/m_associateHome".equals(urlPath))
                {
                    url = "/mobile/m_associatePage.jsp";
                }
                else
                {
                    url = "/associate/calendarAssociate";
                }
            }
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
