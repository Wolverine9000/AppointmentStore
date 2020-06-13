/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import java.io.IOException;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Client;
import store.business.Invoice;
import store.data.CustomerDB;
import store.data.InvoiceDB;
import store.util.MailUtil;
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class PurchaseServlet extends HttpServlet
{

    private String url;
    private String errorMessage;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
    {
        HttpSession session = request.getSession();

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart != null)
            {
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String company = request.getParameter("company");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            String ccNumber = request.getParameter("creditcard");

            Client c = new Client();
            c.setFirstName(firstname);
            c.setLastName(lastname);
            c.setCompany(company);
            c.setEmail(email);
            c.sethomePhone(phone);
            c.setAddress(address);
            c.setCity(city);
            c.setState(state);
            c.setZip(zip);
            c.setCcNumber(ccNumber);

            // validate user data
            boolean validationErrorFlag = false;
            validationErrorFlag = Validator.validateForm(firstname, lastname, email, phone, address, city, state, zip, ccNumber, request);

            // if validation error found, return user to checkout
            if (validationErrorFlag == true)
                {
                request.setAttribute("validationErrorFlag", validationErrorFlag);
                session.setAttribute("client", c);
                url = "/checkout.jsp";
                }
            else
                {
                if (CustomerDB.emailExists(c.getEmail()) != null)
                    {
                    // TODO write code in case of writing to database fails
                    CustomerDB.update(c);
                    }
                else
                    {
                    CustomerDB.insert(c);
                    }

                // set cookie
                Cookie emailCookie = new Cookie("emailCookie", email);
                emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2); // set the age to 2 years
                emailCookie.setPath("/");     // allow access by the entire application
                response.addCookie(emailCookie);

                java.util.Date today = new java.util.Date();

                // create invoice and add purchase info
                Invoice invoice = new Invoice();
                invoice.setClient(c);
                invoice.setInvoiceDate(today);
                invoice.setLineItems(cart.getItems());

                // create confirmation number
                Random random = new Random();
                int i = random.nextInt(999999999);
                invoice.setConfirmationNumber(i);

                // TODO write code in case database write error
                int invoiceID = InvoiceDB.insert(invoice);

                if (invoiceID == 0)
                    {
                    LogFile.databaseError("InvoiceDB insert ", c.getFirstName() + c.getLastName(), "Confirmation# " + i);
                    }

                // Send an email to the user to confirm the order.
                boolean emailConfirmation = MailUtil.sendConfirmation(email, firstname, lastname, "Order Confirmation", i);
                if (emailConfirmation == false)
                    {
                    errorMessage = "Your purchase went through, however there was a problem sending you a confirmation email.<br><br>"
                            + "The Salon System Development Team have been notified and is working on a resolution.<br><br>"
                            + "Thank you for your business!<br>"
                            + "The Salon System Development Team";

                    request.setAttribute("errorMessage", errorMessage);
                    }
                if (emailConfirmation == true)
                    {
                    boolean emailSent = LogFile.emailLog("CONFIRMATION EMAIL SENT ", email, "confirmation email successfully sent " + "confirmation# " + i);
                    }
                else
                    {
                    boolean emailError = LogFile.emailLog("ERROR confirmation email not send ", email, "an error occurred attempting to send confirmation email " + "confirmation# " + i);
                    }

                request.setAttribute("invoice", invoice);
                url = "/confirmation.jsp";

                // remove cart items
                session.removeAttribute("cart");
                session.setAttribute("client", c);
                }
            }
        else
            {
            url = "/index.jsp";
            }
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }
}
