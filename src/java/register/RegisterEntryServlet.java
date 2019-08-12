/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import java.io.IOException;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import messages.LogFile;
import store.business.Register;
import store.data.CustomerDB;
import store.data.RegistrationDB;
import store.util.MailUtil;
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class RegisterEntryServlet extends HttpServlet
{

    private String errorMessage;
    private String url;
    private int regCode;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
    {

        String email = request.getParameter("email");
        String emailConfirm = request.getParameter("emailConfirm");

        // validate user data
        boolean validationErrorFlag = false;
        validationErrorFlag = Validator.validateRegisterEntry(email, emailConfirm, request);

        // if validation error found, return user to checkout
        if (validationErrorFlag == true)
            {
            request.setAttribute("validationErrorFlag", validationErrorFlag);
            url = "/register_enter";
            }
        else if (CustomerDB.emailExists(email) != null)
            {
            errorMessage = "an account with this email address already exist.<br>"
                    + "please use a different email address or login.";
            url = "/register_enter";
            request.setAttribute("errorMessage", errorMessage);
            }
        else
            {
            Register registrationExist = RegistrationDB.registrationExist(email);
            if (registrationExist != null)
                {
                regCode = registrationExist.getRegCode();
                }
            else
                {
                // create registraion number
                Random random = new Random();
                regCode = random.nextInt(999999);
                int regInsert = RegistrationDB.insertRegister(email, regCode);
                if (regInsert == 0)
                    {
                    url = "/register_enter";
                    errorMessage = "registration insert failed!";
                    request.setAttribute("errorMessage", errorMessage);
                    }
                }

            // Send an email to the user to confirm the order.
            boolean emailConfirmation = MailUtil.sendConfirmation(email, "", "", "Registration Request", regCode);
            if (emailConfirmation == false)
                {
                // Log error message and notify user
                LogFile.emailLog("Registration ", email, "failed to send registration email " + "confirmation# " + regCode);
                boolean emailTech = MailUtil.sendTechEmail("whdobbs@gmail.com", email, "", "Registration email failure ", regCode);

                errorMessage = "An error occurred sending you a confirmation email.<br><br>"
                        + "Please Try Again.<br><br>"
                        + "If this error presists, please contact Salon System Support at, (205) 266-8143 or "
                        + "via email at whdobbs@gmail.com.<br><br>"
                        + "Thank you for your patience.<br>"
                        + "Salon System Development Team";

                request.setAttribute("errorMessage", errorMessage);
                url = "/register_enter";
                }
            else
                {
                if (emailConfirmation == true)
                    {
                    boolean emailError = LogFile.emailLog("Registration ", email, "registration email successfully sent " + "confirmation# " + regCode);
                    }
                url = "/registrationEmailed";
                }
            }

        request.setAttribute("email", email);
        Cookie emailCookie = new Cookie("emailCookie", email);
        emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        emailCookie.setPath("/");

        response.addCookie(emailCookie);
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
