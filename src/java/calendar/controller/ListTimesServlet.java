/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.controller;

import java.io.IOException;
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
public class ListTimesServlet extends HttpServlet
{

    private int month;
    private int year;
    private int day;
    private int associateTimesSize;
    private String url;
    private String notFoundMessage;
    private String foundMessage;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        String selectedDate = (String) session.getAttribute("date");
        String associateId = (String) session.getAttribute("associateId");
        String serviceId = request.getParameter("serviceId");

        Date date = null;

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
            ArrayList<Associate2> associateTimes
                    = AssociateDB.selectAvailableTimes(sqlDate, Integer.parseInt(associateId));

            associateTimesSize = associateTimes.size();
            if (associateTimesSize != 0)
            {
                if (associateTimesSize > 1)
                {
                    foundMessage = "found " + associateTimesSize + " available times";
                }
                else
                {
                    foundMessage = "found " + associateTimesSize + " available time";
                }
                request.setAttribute("foundMessage", foundMessage);
            }
            else
            {
                notFoundMessage = "Associate is booked for the selected day and is not available.<br>Please choose "
                        + "another Associate.";
                request.setAttribute("notFoundMessage", notFoundMessage);
            }

            request.setAttribute("associateTimes", associateTimes);
            session.setAttribute("date", selectedDate);
            session.setAttribute("serviceId", serviceId);

            url = "/book.jsp";
        }
        catch (Exception ex)
        {
            LogFile.databaseError("ListTImesServlet error ", ex.getMessage(), ex.toString());
            Logger.getLogger(ListAssociatesServlet.class.getName()).log(Level.SEVERE, null, ex);
            notFoundMessage = "Please choose a Date";
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
