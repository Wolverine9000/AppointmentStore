/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.data.RegistrationDB;
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class ProcessRegistrationServlet extends HttpServlet
{

    private String message;
    private String url;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String regCode = request.getParameter("regCode");

        // validate user data
        boolean validationErrorFlag = false;
        validationErrorFlag = Validator.validateRegisterConfirm(email, regCode, request);

        // if validation error found, return user to checkout
        if (validationErrorFlag == true)
            {
            request.setAttribute("validationErrorFlag", validationErrorFlag);
            url = "/registrationConfirm";
            }
        else
            {
            if (RegistrationDB.isRegistrationValid(email, Integer.parseInt(regCode)) == null)
                {
                message = "email address or registration code not vaild\n"
                        + "please check your entry and retry";

                url = "/registrationConfirm";
                request.setAttribute("message", message);
                request.setAttribute("regCode", regCode);
                }
            else if (RegistrationDB.deleteRegistration(email, Integer.parseInt(regCode)) == 0)
                {
                message = "registration could not be found";
                url = "/registrationConfirm";
                request.setAttribute("message", message);
                }
            else
                {
                url = "/register.jsp";
                }
            }
        session.setAttribute("email", email);

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
