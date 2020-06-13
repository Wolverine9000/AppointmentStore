/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package associate;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Associate2;
import store.business.Services;
import store.data.AssociateDB;
import store.util.ServiceUtil;
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class UpdateAssociateServlet extends HttpServlet
{

    private static final String errorMessage = "Associate Update Failed!";
    private static final String successMessage = "Associate Update Successful!";
    private String url;
    private final String loginPage = "/associate/associate_info/associate_login.jsp";
    boolean validationErrorFlag = false;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Associate2 associate = (Associate2) session.getAttribute("associateRecord");

        if (associate == null)
        {
            url = loginPage;
        }
        else
        {
            String userPath = request.getServletPath();
            // if update service page is requested
            switch (userPath)
            {
                case "/associate/updateServices":
                    // get selected service check boxes
                    String[] serviceId = request.getParameterValues("serviceID");
                    boolean updateService = ServiceUtil.updateServices(serviceId, associate, request);
                    ArrayList<Services> services;
                    services = AssociateDB.selectAllAsscSvcs(associate.getId());
                    session.setAttribute("services", services);
                    url = "/associate/associate_info/services.jsp";
                    break;
                case "/associate/updateProfile":
                    String firstName = request.getParameter("firstname");
                    String lastName = request.getParameter("lastname");
                    String email = request.getParameter("email");
                    String homePhone = request.getParameter("homephone");
                    String workPhone = request.getParameter("workphone");
                    String mobilePhone = request.getParameter("mobilephone");
                    String otherPhone = request.getParameter("otherphone");
                    String address = request.getParameter("address");
                    String city = request.getParameter("city");
                    String state = request.getParameter("state");
                    String zip = request.getParameter("zip");

                    Associate2 i = new Associate2();
                    i.setId(associate.getId());
                    i.setAddress(address);
                    i.setHomePhone(homePhone);
                    i.setWorkPhone(workPhone);
                    i.setMobilePhone(mobilePhone);
                    i.setOtherPhone(otherPhone);
                    i.setCity(city);
                    i.setState(state);
                    i.setZip(zip);

                    Associate2 a = new Associate2();
                    a.setId(associate.getId());
                    a.setFirstName(firstName);
                    a.setLastName(lastName);
                    a.setEmail(email);

                    // validate user data
                    validationErrorFlag = Validator.validateAssociate(firstName, lastName, email,
                            homePhone, workPhone, mobilePhone, otherPhone, address, city, state, zip, request);
                    // if validation error found, return user to update page
                    if (validationErrorFlag == true)
                    {
                        request.setAttribute("validationErrorFlag", validationErrorFlag);
                        request.setAttribute("associateRecord", a);
                        session.setAttribute("associateInfo", i);
                        url = "/associate/updateAssociate";
                    }
                    else
                    {
                        int associateResult = AssociateDB.updateAssociate(firstName, lastName, email, associate.getId());
                        if (associateResult == 0)
                        {
                            LogFile.databaseError("UpdateAssociateServlet updateAssociate FAILED ",
                                    email, lastName);
                            request.setAttribute("errorMessage", errorMessage);
                            url = "/associate/updateAssociate";
                        }
                        else
                        {
                            int associateInfoResult = AssociateDB.updateAssociateInfo(homePhone, workPhone, mobilePhone,
                                    otherPhone, address, city, state, zip, associate.getId());
                            if (associateInfoResult == 0)
                            {
                                request.setAttribute("errorMessage", errorMessage);
                                url = "/associate/updateAssociate";
                            }
                            else
                            {
                                session.setAttribute("associateInfo", i);
                                session.setAttribute("associateRecord", a);
                                request.setAttribute("successMessage", successMessage);
                                url = "/associate/updateAssociate";
                            }
                        }
                    }
                    break;
                case "/associate/updatePassword":
                    String password = request.getParameter("password");
                    String confirmPassword = request.getParameter("confirmPassword");

                    String associateEmail = associate.getEmail();
                    String associateLastName = associate.getLastName();

                    // validate user data
                    validationErrorFlag = Validator.updatePassword(password, confirmPassword, request);
                    // if validation error found, return user to update page
                    if (validationErrorFlag == true)
                    {
                        request.setAttribute("validationPwdErrorFlag", validationErrorFlag);
                        request.setAttribute("password", password);
                        request.setAttribute("confirmPassword", confirmPassword);
                        url = "/associate/updateAssociate";
                    }
                    if (validationErrorFlag == false)
                    {
                        boolean associateResult = AssociateDB.updateAssociatePassword(associate);
                        if (associateResult == false)
                        {
                            String errorPwdMessage = "Password Update Failed!";
                            LogFile.databaseError("UpdateAssociateServlet updatePassword FAILED ",
                                    associateEmail, associateLastName);
                            request.setAttribute("errorPwdMessage", errorPwdMessage);
                            url = "/associate/updateAssociate";
                        }
                        else
                        {
                            String successPwdMessage = "Password Successfully Updated!";
                            request.setAttribute("successPwdMessage", successPwdMessage);
                            url = "/associate/updateAssociate";
                        }
                    }
                    break;
                case "/associate/updateSecurityQuestion":
                    String securityQuestion = request.getParameter("securityQuestion");
                    String securityAnswer = request.getParameter("securityAnswer");

                    String assoEmail = associate.getEmail();
                    String assoLastName = associate.getLastName();

                    // validate user security answer
                    validationErrorFlag = Validator.updateSecurityAnswer(securityAnswer, request);
                    // if validation error found, return user to update page
                    if (validationErrorFlag == true)
                    {
                        request.setAttribute("validationSecureErrorFlag", validationErrorFlag);
                        request.setAttribute("securityQuestion", securityQuestion);
                        request.setAttribute("securityAnswer", securityAnswer);
                        url = "/associate/updateAssociate";
                    }
                    if (validationErrorFlag == false)
                    {
                        int associateResult = AssociateDB.updateAssociateInfo(securityQuestion, securityAnswer,
                                associate.getId());
                        if (associateResult == 0)
                        {
                            String errorSecureMessage = "Security Update Failed!";
                            LogFile.databaseError("UpdateAssociateServlet updateSecurityQuestion FAILED ",
                                    assoEmail, assoLastName);
                            request.setAttribute("errorSecureMessage", errorSecureMessage);
                            url = "/associate/updateAssociate";
                        }
                        else
                        {
                            String successSecureMessage = "Security Question and Answer Successfully Updated!";
                            request.setAttribute("securityAnswer", securityAnswer);
                            request.setAttribute("successSecureMessage", successSecureMessage);
                            url = "/associate/updateAssociate";
                        }
                    }
                    break;
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
