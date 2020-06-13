package controller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Client;
import store.data.CustomerDB;
import store.data.UserLogDB;
import validate.Validator;

public class CheckUserServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        String emailAddress = request.getParameter("emailAddress");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        String currentPage = (String) session.getAttribute("view");

        String message = "";
        String url = null;
        Date date = new Date();

        // validate user data
        boolean validationErrorFlag = false;
        validationErrorFlag = Validator.validateLogin(emailAddress, password, request);

        // if validation error found, return user to checkout
        if (validationErrorFlag == true)
            {
            request.setAttribute("validationErrorFlag", validationErrorFlag);
            url = "/userlogin";
            }
        //check to see if email address exist
        else if (CustomerDB.emailExists(emailAddress) == null)
            {
            // write failed login to log file
            String failPoint = "Customer check email does not exist login failure ";
            LogFile.loginMessage(emailAddress, failPoint);

            message = "This email address does not exists. <br>"
                    + "Please check your entry and retry.";
            session.setAttribute("message", message);
            url = "/error.jsp";
            }
        //check email address and password
        else if (CustomerDB.checkCredenitals(emailAddress, password) == null)
            {
            // write failed login to log file
            String failPoint = "Customer login password credentials failed ";
            LogFile.loginMessage(emailAddress, failPoint);

            message = "Email or Password is incorrect. <br>"
                    + "Please check your entry and re-enter.";
            session.setAttribute("message", message);
            url = "/error.jsp";;
            }
        else
            {
            // get customer info then goto selected page
            Client client_info = CustomerDB.selectClient(emailAddress);
            if (client_info == null)
                {
                // write failed login to log file
                String failPoint = "Customer selectCustomer login failure ";
                LogFile.loginMessage(emailAddress, failPoint);

                message = "There was an error retrieving your data";
                url = "/error.jsp";
                }
            String in = "in";
            // write successful login insert to log file
            String successPoint = "Customer login database insert SUCCESSFUL ";
            LogFile.loginMessage(emailAddress, successPoint);
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
            int login = UserLogDB.insert(client_info.getId(), sqlDate, in);
            // set client session to one day
            // TODO re-visit the settings for this session
            session.setMaxInactiveInterval(60 * 60 * 24);

            if (login == 0)
                {
                // write failed database insert to log file
                String failPoint = "Customer login database insert failed ";
                LogFile.loginMessage(emailAddress, failPoint);
                }
            if (null != currentPage)
                // return user to current page
                // return user to current page
                {
                switch (currentPage)
                    {
                    case "category":
                        url = "/category";
                        break;
                    case "checkout":
                        url = "/checkout";
                        break;
                    case "home":
                        url = "/index.jsp";
                        break;
                    default:
                        url = "/index.jsp";
                        break;
                    }
                }
            session.setAttribute("client", client_info);
            session.setAttribute("loggedInFlag", true);

            }
        request.setAttribute("message", message);
        session.setAttribute("emailAddress", emailAddress);

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
