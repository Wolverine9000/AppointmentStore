/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Associate2;
import store.data.AssociateDB;

/**
 *
 * @author williamdobbs
 */
public class ListAssociatesServlet extends HttpServlet
{

    private int numberOfSchedules;
    String notFoundMessage;
    private String url;
    private int month;
    private int year;
    private int day;
    private int numberOfAssociates;
    private String foundMessage;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        session.removeAttribute("associateId");
        session.removeAttribute("services");
        session.removeAttribute("time");

        String selectedDate = request.getParameter("date");
        Date date;

        try
        {
            date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(selectedDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);
            day = cal.get(Calendar.DAY_OF_MONTH);

            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            // TODO write code to ensure there are no exixting dates for assoicate
            // get associates available for selected date
            ArrayList<Associate2> associateSchedules
                    = AssociateDB.selectAssociatesAvailable(sqlDate);

            numberOfAssociates = associateSchedules.size();

            if (!associateSchedules.isEmpty() || numberOfAssociates != 0)
            {
                foundMessage = numberOfAssociates + " associates available";
                request.setAttribute("numberOfSchedules", numberOfSchedules);
                request.setAttribute("foundMessage", foundMessage);
            }
            else
            {
                notFoundMessage = "No associates available for date selected.<br>"
                        + "Please choose another Date.";
                request.setAttribute("notFoundMessage", notFoundMessage);
            }
            session.setAttribute("associateSchedules", associateSchedules);
            session.setAttribute("date", selectedDate);

            url = "/book.jsp";
        }
        catch (ParseException ex)
        {
            Logger.getLogger(ListAssociatesServlet.class.getName()).log(Level.SEVERE, null, ex);
            LogFile.databaseError("ListAsscociates error ", ex.getMessage(), ex.toString());
            notFoundMessage = "Invalid Date. <br>Please Enter a Valid Date";
            request.setAttribute("notFoundMessage", notFoundMessage);
            url = "/book.jsp";
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
