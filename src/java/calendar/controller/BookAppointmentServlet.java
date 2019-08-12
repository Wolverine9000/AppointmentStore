/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
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
import store.business.Client;
import store.business.Services;
import store.data.AssociateDB;
import store.data.CalendarDB;

/**
 *
 * @author williamdobbs
 */
public class BookAppointmentServlet extends HttpServlet
{

    private int month;
    private int year;
    private int day;
    private String notFoundMessage;
    private String url;
    private int day_of_week;
    private String foundMessage;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Client client = (Client) session.getAttribute("client");

        String selectedDate = (String) session.getAttribute("date");
        String serviceId = (String) session.getAttribute("serviceId");
        String associateId = (String) session.getAttribute("associateId");
        String startTime = request.getParameter("time");

        int startHour = Integer.parseInt(startTime.substring(0, 2));
        int startMinute = Integer.parseInt(startTime.substring(3, 5));

        if (selectedDate == null)
        {
            notFoundMessage = "Please select a Date.";
            request.setAttribute("notFoundMessage", notFoundMessage);
            url = "/book.jsp";
        }
        else if (associateId == null)
        {
            notFoundMessage = "Please select a Asscociate with an available Date.";
            request.setAttribute("notFoundMessage", notFoundMessage);
            url = "/book.jsp";
        }
        else if (serviceId == null)
        {
            notFoundMessage = "Please select a Service.";
            request.setAttribute("notFoundMessage", notFoundMessage);
            url = "/book.jsp";
        }
        else if (startTime == null)
        {
            notFoundMessage = "Please select a Time.";
            request.setAttribute("notFoundMessage", notFoundMessage);
            url = "/book.jsp";
        }
        else if (client != null)
        {
            DateFormat dbTimeFormat = new SimpleDateFormat("HH:mm:ss");

            Date dStartTime = null;
            try
            {
                dStartTime = dbTimeFormat.parse(startTime);

                java.sql.Time sqlStartTime = new java.sql.Time(dStartTime.getTime());

                Date date = null;

                date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(selectedDate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
                day = cal.get(Calendar.DAY_OF_MONTH);
                day_of_week = cal.get(Calendar.DAY_OF_WEEK);

                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                // create confirmation number
                Random random = new Random();
                int confirmationNumber = random.nextInt(999999999);

                // get associate object info via associateId number
                int associateIdNumber = Integer.parseInt(associateId);
                Associate2 associate = AssociateDB.selectAssociateInfo(associateIdNumber);
                // get service object info via serviceId number
                Services service = AssociateDB.selectService(Integer.parseInt(serviceId));
                // setup service end time
                SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
                Date endDate = dt.parse(selectedDate + " " + startTime);
                // convert start time to sql timestamp
                java.sql.Timestamp sqlStartTimestamp = new java.sql.Timestamp(endDate.getTime());

                cal.setTime(endDate);
                cal.add(Calendar.MINUTE, service.getServiceTime());
                int endHour;
                endHour = cal.get(Calendar.HOUR_OF_DAY);
                int endMinute;
                endMinute = cal.get(Calendar.MINUTE);
                endDate = cal.getTime();
                java.sql.Timestamp sqlEndTimestamp = new java.sql.Timestamp(endDate.getTime());
                java.sql.Time sqlEndTime = new java.sql.Time(endDate.getTime());

                // insert data into calendar database
                int insertAppointment = CalendarDB.insertAppointment(sqlDate, Integer.parseInt(serviceId),
                        month, day_of_week, day, year, sqlStartTime, sqlEndTime, sqlStartTimestamp, sqlEndTimestamp,
                        client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), startHour, startMinute,
                        endHour, endMinute, confirmationNumber, service.getDescription(), service.getServiceTime(), associate.getId());
                if (insertAppointment == 0)
                {
                    notFoundMessage = "insert failed";
                    request.setAttribute("notFoundMessage", notFoundMessage);
                }
                else
                {
                    int removedAvailDate = AssociateDB.deleteAvailability(Integer.parseInt(associateId), sqlDate, sqlStartTime);
                    if (removedAvailDate == 0)
                    {
                        notFoundMessage = "failed to remove available date!";
                        request.setAttribute("notFoundMessage", notFoundMessage);
                    }
                    //                   else
//                        {
//                        // Send an email to the user to confirm the appointment.
//                        boolean emailConfirmation = MailUtil.sendConfirmation("Appointment Confirmation", selectedDate,
//                                startTime, client, associate, service, confirmationNumber);
//                        if (emailConfirmation == false)
//                            {
//                            String errorMessage = "Your appointment has been scheduled , however there was a problem "
//                                    + "sending you a appointment confirmation email.<br><br>"
//                                    + "The Salon System Development Team have been notified and is working on a resolution.<br><br>"
//                                    + "Thank you for your business!<br>"
//                                    + "The Salon System Development Team";
//
//                            request.setAttribute("errorMessage", errorMessage);
//                            }
//                        if (emailConfirmation == true)
//                            {
//                            boolean emailSent = LogFile.emailLog("APPOINTMENT CONFIRMATION EMAIL SENT ",
//                                    client.getEmail(), "appointment confirmation email successfully sent "
//                                    + "appointment ID# " + confirmationNumber);
//                            }
//                        else
//                            {
//                            boolean emailError = LogFile.emailLog("ERROR confirmation email not send ",
//                                    client.getEmail(), "an error occurred attempting to send appointment confirmation email "
//                                    + "confirmation# " + confirmationNumber);
//                            }
//                        foundMessage = "Scheduled appointment date successful!";
//                        request.setAttribute("foundMessage", foundMessage);
//                        }
                }
                url = "/book.jsp";
            }
            catch (ParseException | NumberFormatException ex)
            {
                LogFile.databaseError("BookAppointmentServlet error ", ex.getMessage(), ex.toString());
                Logger.getLogger(BookAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
                notFoundMessage = "An error occured. Please check your selections.";
                request.setAttribute("notFoundMessage", notFoundMessage);
                url = "/book.jsp";
            }
        }
        else if (client == null)
        {
            notFoundMessage = "You must login before booking an Appointment";
            request.setAttribute("message", notFoundMessage);
            session.setAttribute("date", selectedDate);
            session.setAttribute("serviceId", serviceId);
            session.setAttribute("associateId", associateId);
            session.setAttribute("time", startTime);

            url = "/userlogin";
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
