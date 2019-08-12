/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Client;
import store.data.CustomerDB;
import store.data.SecurityDB;
import store.util.MailUtil;
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class RegisterUserServlet extends HttpServlet
{

    private String errorMessage;
    private String url;
    private Object loggedInFlag;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
    {
        HttpSession session = request.getSession();

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String company = request.getParameter("company");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confirmpassword");
        String sercurityquestion = request.getParameter("securityQuestion");
        String securityanswer = request.getParameter("securityanswer");
        String homephone = request.getParameter("homephone");
        String workphone = request.getParameter("workphone");
        String mobilephone = request.getParameter("mobilephone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");

        // validate user data
        boolean validationErrorFlag = false;
        validationErrorFlag = Validator.validateRegisterForm(firstname, lastname, email, password,
                confirmpassword, securityanswer, homephone, workphone, mobilephone, address, city, state, zip, request);

        // if validation error found, return user to checkout
        if (validationErrorFlag == true)
            {
            request.setAttribute("validationErrorFlag", validationErrorFlag);
            url = "/register";
            }
        // check to see if email exist
        else if (CustomerDB.emailExists(email) != null)
            {
            errorMessage = "an account with this email address already exist.<br>"
                    + "please use a different email address or login.";
            url = "/register";
            request.setAttribute("errorMessage", errorMessage);
            }
        // enter user data into database
        else if (CustomerDB.insert(firstname, lastname, company, email, password, homephone,
                workphone, mobilephone, address, city, state, zip, securityanswer) == 0)
            {
            errorMessage = "customer insert failed - check database";
            request.setAttribute("errorMessage", errorMessage);
            url = "/register";
            }
        // enter user security info in separate database table
        else if (SecurityDB.insertSecurityInfo(email, sercurityquestion, securityanswer) == 0)
            {
            errorMessage = "customer insert succeeded but security info insert failed - check database";
            request.setAttribute("errorMessage", errorMessage);
            url = "/register";
            }
        else
            {
            // Send an email to the user to confirm the order.
            boolean emailConfirmation = MailUtil.sendConfirmation(email, firstname, lastname, "Registration Complete", 0);
            if (emailConfirmation == false)
                {
                errorMessage = "Your purchase went through, however there was a problem sending you a confirmation email.<br><br>"
                        + "The Salon System Development Team have been notified and is working on a resolution.<br><br>"
                        + "Thank you for your business!<br>"
                        + "The Salon System Development Team";

                request.setAttribute("errorMessage", errorMessage);
                }
            Cookie emailCookie = new Cookie("emailCookie", email);
            emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2); //set its age to 2 years
            emailCookie.setPath("/"); //allow the entire application to access it
            response.addCookie(emailCookie);

            Client c = new Client();
            c.setFirstName(firstname);
            c.setLastName(lastname);
            c.setCompany(company);
            c.setEmail(email);
            c.sethomePhone(homephone);
            c.setAddress(address);
            c.setCity(city);
            c.setState(state);
            c.setZip(zip);

            // log user into system
            Client client = CustomerDB.checkCredenitals(email, password);
            if (client != null)
                {
                loggedInFlag = true;
                session.setAttribute("client", c);
                session.setAttribute("loggedInFlag", loggedInFlag);
                url = "/index.jsp";
                }
            else
                {
                url = "/userlogin";
                }
            }
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
