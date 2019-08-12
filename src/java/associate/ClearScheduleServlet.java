package associate;

import java.io.IOException;
import java.util.Date;
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
import store.util.DateUtil;
import validate.Validator;

public class ClearScheduleServlet extends HttpServlet
{

    private String url;
    private String errorMessage;
    private final String clrAllMessage;
    private final String clrMessage;
    private final String clrRangeMessage;

    public ClearScheduleServlet()
    {
        this.clrRangeMessage = "Your Schedule was Successfully Cleared between dates";
        this.clrMessage = "Your Schedule was Successfully cleared for";
        this.clrAllMessage = "Your Entire Schedule was Successfully Cleared!";
        this.errorMessage = "We're sorry, but the system failed to process your schedule.<br>"
                + "Please make sure your Date is entered correctly example; 01/31/2014.";
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Associate2 associateRecord = (Associate2) session.getAttribute("associateRecord");
        String urlPath;
        urlPath = request.getServletPath();

        if (associateRecord != null)
            {
            // get clear information
            String clrChoice = request.getParameter("clear");
            String eDate = request.getParameter("eDate");
            String fromDate = request.getParameter("from");
            String toDate = request.getParameter("to");
            String currentTab = request.getParameter("currentTab");

            int associateId = associateRecord.getId(); // get the associate's id number

            if (clrChoice == null)
                {
                errorMessage = "Please select a Clear Schedule option ";
                request.setAttribute("clrErrorMessage", errorMessage);
                }
            else
                {
                boolean validationClrErrorFlag = Validator.validateSchedule(fromDate, toDate, eDate, clrChoice,
                        request);

                // if validation error found, return associate to scheduler
                if (validationClrErrorFlag == true)
                    {
                    request.setAttribute("from", fromDate);
                    request.setAttribute("to", toDate);
                    request.setAttribute("validationClrErrorFlag", validationClrErrorFlag);
                    }
                else
                    {
                    if ("clearAll".equalsIgnoreCase(clrChoice))
                        {
                        int clearEntireSched = AssociateDB.deleteAvailability(associateId);
                        int clearAvailDates = AssociateDB.deleteAvailabilityDate(associateId);

                        request.setAttribute("clrAllMessage", clrAllMessage);
                        }
                    else if ("clearDate".equalsIgnoreCase(clrChoice))
                        {
                        try
                            {
                            int delAvailDate = 0;
                            int delAssoDate = 0;
                            Date date;
                            // convert date string to date object
                            date = DateUtil.convertDateSched(eDate);

                            if (date == null)
                                {
                                LogFile.databaseError("ClearScheduleServlet error ", clrChoice,
                                        " to date:" + eDate + " user:" + associateRecord.getFirstName() + associateRecord.getLastName()
                                        + " userID " + associateRecord.getId());
                                request.setAttribute("clrErrorMessage", errorMessage);
                                }
                            else
                                {
                                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                                delAvailDate = AssociateDB.deleteAvailability(associateId, sqlDate);
                                delAssoDate = AssociateDB.deleteAvailabilityDate(associateId, sqlDate);

                                LogFile.associateLog("ClearScheduleServlet ERROR ", "insertSchedule=0"
                                        + "", " associateId:" + associateId + clrChoice);

                                request.setAttribute("date", date);
                                request.setAttribute("clrMessage", clrMessage);
                                }
                            request.setAttribute("eDate", eDate);
                            }
                        catch (NullPointerException npe)
                            {
                            LogFile.databaseError("ClearScheduleServlet error ", npe.getMessage(), npe.toString());
                            Logger.getLogger(ProcessScheduleServlet.class.getName()).log(Level.SEVERE, null, npe);
                            request.setAttribute("clrErrorMessage", errorMessage);
                            request.setAttribute("eDate", eDate);
                            }
                        }
                    else if ("clearRange".equalsIgnoreCase(clrChoice))
                        {
                        try
                            {
                            // convert date strings to date objects
                            Date newFrDate = DateUtil.convertDateSched(fromDate);
                            Date newToDate = DateUtil.convertDateSched(toDate);

                            if (newFrDate == null || newToDate == null)
                                {
                                LogFile.databaseError("ClearScheduleServlet error ", clrChoice, " from date "
                                        + fromDate + " to date " + toDate + " associate " + associateRecord.getFirstName() + associateRecord.getLastName()
                                        + " associateID " + associateRecord.getId());
                                request.setAttribute("clrErrorMessage", errorMessage);
                                }
                            else
                                {
                                java.sql.Date sqlDateFr = new java.sql.Date(newFrDate.getTime());
                                java.sql.Date sqlDateTo = new java.sql.Date(newToDate.getTime());
                                int delAvail = AssociateDB.deleteDateRange(associateId, sqlDateFr, sqlDateTo);
                                if (delAvail == 0)
                                    {
                                    LogFile.generalLog("ClearScheduleServlet ", clrChoice + "delAvail dateRange not found ");
                                    }
                                int delAsso = AssociateDB.deleteAvailabilityDate(associateId, sqlDateFr, sqlDateTo);
                                if (delAsso == 0)
                                    {
                                    LogFile.generalLog("ClearScheduleServlet ", clrChoice + "delAsso dateRange not found ");
                                    }
                                request.setAttribute("clrRangeMessage", clrRangeMessage);
                                }
                            request.setAttribute("newFrDate", newFrDate);
                            request.setAttribute("newToDate", newToDate);
                            request.setAttribute("from", fromDate);
                            request.setAttribute("to", toDate);
                            }
                        catch (NullPointerException npe)
                            {
                            LogFile.databaseError("ClearScheduleServlet error " + clrChoice + " ", npe.getMessage(), npe.toString()
                                    + " user:" + associateRecord.getFirstName() + associateRecord.getLastName()
                                    + " userID:" + associateRecord.getId());
                            Logger.getLogger(ProcessScheduleServlet.class.getName()).log(Level.SEVERE, null, npe);
                            request.setAttribute("clrErrorMessage", errorMessage);
                            }
                        }
                    }
                if ("clearAll".equalsIgnoreCase(clrChoice))
                    {
                    boolean clearAllCheckbox = true;
                    request.setAttribute("clearAllCheckbox", clearAllCheckbox);
                    }
                else if ("clearDate".equalsIgnoreCase(clrChoice))
                    {
                    boolean clearDateCheckbox = true;
                    request.setAttribute("clearDateCheckbox", clearDateCheckbox);
                    }
                else if ("clearRange".equalsIgnoreCase(clrChoice))
                    {
                    boolean clearRangeCheckbox = true;
                    request.setAttribute("clearRangeCheckbox", clearRangeCheckbox);
                    }
                }
            if ("/mobile/m_clearScheduler".equals(urlPath))
                {
                url = urlPath + ".jsp";
                }
            else
                {
                url = "/associate/scheduler";
                request.setAttribute("reloadTab", currentTab);
                }
            }
        else
            {
            url = "/associate/associateLogin";
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
