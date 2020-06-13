package associate;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Associate2;
import store.util.CalendarUtil;
import store.util.DateUtil;
import static store.util.DateUtil.scheduleLength;
import validate.Validator;

public class ProcessScheduleServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String currentTab = request.getParameter("currentTab");

        Associate2 associateRecord = (Associate2) session.getAttribute("associateRecord");
        String url;
        String urlPath;
        urlPath = request.getServletPath();

        if (associateRecord != null)
            {
            // get quick schedule information
            String qsStart = request.getParameter("qsStart");
            String qsEnd = request.getParameter("qsEnd");
            String qsFrequency = request.getParameter("qsFrequency");
            int frequency = 0;
            if (qsFrequency != null)
                {
                frequency = Integer.parseInt(qsFrequency);
                }
            String qslength = request.getParameter("qsLength");
            String qsDate = request.getParameter("qsDate");
            String[] days = request.getParameterValues("day");

            if (days != null)
                {
                // check and set which checkboxes are checked
                boolean dayCheckboxFlag = false;
                dayCheckboxFlag = DateUtil.dayCheckbox(days, request);
                }

            int length;
            length = scheduleLength(qslength); // convert the length of quick schedule number of days integer

            boolean mainSchedFlag = false; // not the main scheduler - this the quick scheduler
            boolean daysFlag = false;
            if (length != 1)
                {
                daysFlag = true;
                }
            // validate user data
            boolean validationErrorFlag = false;
            validationErrorFlag = Validator.validateSchedule(qsStart, qsEnd, qsDate, qsStart,
                    qsEnd, days, daysFlag, mainSchedFlag, qsDate, request);

            // if validation error found, return user associate scheduler
            if (validationErrorFlag == true)
                {
                request.setAttribute("validationErrorFlag", validationErrorFlag);
                }
            else
                {
                int associateId = associateRecord.getId(); // get the associate's id number

                boolean processSched = CalendarUtil.processQuickSched(associateId, length, qsDate, qsStart, qsEnd, days,
                        frequency, request);
                }
            if ("/mobile/m_associateScheduler".equals(urlPath))
                {
                url = urlPath + ".jsp";
                }
            else
                {
                url = "/associate/scheduler";
                request.setAttribute("reloadTab", currentTab);
                }
            request.setAttribute("qsStart", qsStart);
            request.setAttribute("qsEnd", qsEnd);
            request.setAttribute("qsLength", qslength);
            request.setAttribute("qsDate", qsDate);
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
