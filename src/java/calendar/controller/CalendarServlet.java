package calendar.controller;

import store.data.CalendarDB;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.CalendarCustomer;
import store.business.Client;
import store.util.CalendarUtil;
import store.util.DateUtil;
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class CalendarServlet extends HttpServlet
{

    private String url;
    private ArrayList<CalendarCustomer> calendarWeek;
    private int weekStartDate;
    private int weekEndDate;
    private Date viewWeekStartDate;
    private Date viewWeekEndDate;
    private java.sql.Date sqlWkStartDate;
    private java.sql.Date sqlWkEndDate;
    boolean prevMonth = false;
    boolean nextMonth = false;
    Calendar calendar = new GregorianCalendar();
    private String gotoDate;
    private String errorMessage;
    private Date sun;
    private Date mon;
    private Date tue;
    private Date wed;
    private Date thu;
    private Date fri;
    private Date sat;
    private Date viewDate;

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {

        int iMonth = 0;

        int iYear = 0;
        String cMonth = null;
        int day = 0;
        boolean viewWeek = false;
        boolean viewDay = false;
        ArrayList<CalendarCustomer> calendarMonth = null;
        ArrayList<CalendarCustomer> calendarDay = null;
        Date selectedDate = null;

        HttpSession session = request.getSession();

        Client client = (Client) session.getAttribute("client");

        if (client != null)
            {
            String userPath = request.getServletPath();
            int clientId = client.getId();

            switch (userPath)
                {
                case "/today":
                    iYear = 0;
                    break;
                case "/viewDay":
                case "/gotoDate":
                case "/viewToday":
                    iYear = (Integer) session.getAttribute("iYear"); // get currently selected year integer
                    cMonth = (String) session.getAttribute("cMonth"); // get currently selected month name string
                    if ("/gotoDate".equals(userPath))
                        {
                        gotoDate = request.getParameter("gotoDate");
                        Date date = null;
                        boolean validateDate = false;
                        validateDate = Validator.isDateValid(gotoDate);
                        if (validateDate == false)
                            {
                            // TODO create code if validateDate equals false respond with message
                            date = DateUtil.getCurrentDate();
                            calendar.setTime(date);
                            }
                        // convert date string into Date object
                        date = DateUtil.convertDateSched(gotoDate);
                        if (date != null)
                            {
                            calendar.setTime(date); // set Calendar object from Date object
                            day = calendar.get(Calendar.DATE); // get the date/day integer from Calendar object
                            }
                        else
                            {
                            day = 0;
                            }
                        }
                    if ("/viewDay".equals(userPath))
                        {
                        day = CalendarUtil.nullIntconv(request.getParameter("weekStartDay"));
                        calendar = DateUtil.createCalendar(iYear, cMonth, day);
                        }
                    if ("/viewToday".equals(userPath))
                        {
                        Date date = null;
                        date = DateUtil.getCurrentDate();
                        calendar.setTime(date);
                        }
                    iMonth = calendar.get(Calendar.MONTH);
                    iYear = calendar.get(Calendar.YEAR);
                    day = calendar.get(Calendar.DATE);
                    if (day == 0)
                        {
                        selectedDate = DateUtil.getCurrentDate();
                        calendar.setTime(selectedDate);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                        iMonth = calendar.get(Calendar.MONTH);
                        iYear = calendar.get(Calendar.YEAR);
                        }
                    else
                        {
                        selectedDate = DateUtil.createDate(iYear, iMonth, day);
                        }
                    viewDay = true;
                    break;
                case "/viewDate":
                    String dateWeek = request.getParameter("viewDate");
                    calendar = new GregorianCalendar();
                    calendar.setTime(viewDate);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    iMonth = calendar.get(Calendar.MONTH);
                    iYear = calendar.get(Calendar.YEAR);
                    viewDay = true;
                    break;
                case "/nextDay":
                    iYear = (Integer) session.getAttribute("iYear");
                    cMonth = (String) session.getAttribute("cMonth");
                    selectedDate = (Date) session.getAttribute("selectedDate");
                    calendar = new GregorianCalendar();
                    calendar.setTime(selectedDate); // set calendar object to the selected date
                    calendar.add(Calendar.DATE, 1); // add one calenday day
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    iMonth = calendar.get(Calendar.MONTH);
                    iYear = calendar.get(Calendar.YEAR);
                    selectedDate = DateUtil.createDate(iYear, iMonth, day); // create new next day Date object
                    viewDay = true;
                    break;
                case "/prevDay":
                    iYear = (Integer) session.getAttribute("iYear");
                    cMonth = (String) session.getAttribute("cMonth");
                    selectedDate = (Date) session.getAttribute("selectedDate");
                    calendar = new GregorianCalendar();
                    calendar.setTime(selectedDate); // set calendar object to the selected date
                    calendar.add(Calendar.DATE, -1); // subtract one calenday day
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    iMonth = calendar.get(Calendar.MONTH);
                    iYear = calendar.get(Calendar.YEAR);
                    selectedDate = DateUtil.createDate(iYear, iMonth, day); // create new previous day Date object
                    viewDay = true;
                    break;
                case "/viewWeek":
                case "/nextWeek":
                case "/prevWeek":
                case "/thisWeek":
                    iYear = (Integer) session.getAttribute("iYear");
                    cMonth = (String) session.getAttribute("cMonth");
                    day = CalendarUtil.nullIntconv(request.getParameter("weekStartDay"));
                    // create calendar oject and get integers for day, month, year
                    calendar = new GregorianCalendar();
                    if ("/nextWeek".equals(userPath))
                        {
                        viewWeekEndDate = (Date) session.getAttribute("viewWeekEndDate");
                        calendar.setTime(viewWeekEndDate); // set calendar object
                        calendar.add(Calendar.DATE, 1); // add one calendar day
                        }
                    else if ("/prevWeek".equals(userPath))
                        {
                        viewWeekStartDate = (Date) session.getAttribute("viewWeekStartDate");
                        calendar.setTime(viewWeekStartDate); // set calendar object
                        calendar.add(Calendar.DATE, -1); // subtract one calendar day
                        }
                    else if ("/viewWeek".equals(userPath) || "/thisWeek".equals(userPath))
                        {
                        if (day == 0) // if day requested parameter equals zero
                            {
                            selectedDate = DateUtil.getCurrentDate(); // use the current date for Date object
                            calendar.setTime(selectedDate);
                            }
                        else
                            {
                            calendar = DateUtil.createCalendar(iYear, cMonth, day);
                            }
                        }
                    // execute while loop until day of week equals 1 (sunday)
                    while (calendar.get(Calendar.DAY_OF_WEEK) > 1)
                        {
                        calendar.add(Calendar.DATE, -1);
                        }
                    weekStartDate = calendar.get(Calendar.DATE); // get the current date should be sun
                    iYear = calendar.get(Calendar.YEAR); // get the year at the start date
                    iMonth = calendar.get(Calendar.MONTH); // get the month

                    // make weekCalendar equal to current calendar Object which should be sunday
                    Calendar weekCalendar = new GregorianCalendar(iYear, iMonth, weekStartDate);
                    // get Date objects for the days of the week
                    sun = DateUtil.convertCalendar(weekCalendar);
                    sun = DateUtil.stripTime(sun);
                    String sunString = sun.toString();
                    weekCalendar.add(Calendar.DATE, 1);
                    mon = DateUtil.convertCalendar(weekCalendar);
                    weekCalendar.add(Calendar.DATE, 1);
                    tue = DateUtil.convertCalendar(weekCalendar);
                    weekCalendar.add(Calendar.DATE, 1);
                    wed = DateUtil.convertCalendar(weekCalendar);
                    weekCalendar.add(Calendar.DATE, 1);
                    thu = DateUtil.convertCalendar(weekCalendar);
                    weekCalendar.add(Calendar.DATE, 1);
                    fri = DateUtil.convertCalendar(weekCalendar);
                    weekCalendar.add(Calendar.DATE, 1);
                    sat = DateUtil.convertCalendar(weekCalendar);

                    request.setAttribute("sunString", sunString);

                    request.setAttribute("sun", sun);
                    request.setAttribute("mon", mon);
                    request.setAttribute("tue", tue);
                    request.setAttribute("wed", wed);
                    request.setAttribute("thu", thu);
                    request.setAttribute("fri", fri);
                    request.setAttribute("sat", sat);

                    calendar.add(Calendar.DATE, 6); // add six days from sunday weekStartDate
                    weekEndDate = calendar.get(Calendar.DATE); // now get end date should be sat
                    int weekEndYear = calendar.get(Calendar.YEAR);
                    int weekEndMonth = calendar.get(Calendar.MONTH);
                    // create Week Start and End Date objects from Calendar integers
                    viewWeekStartDate = DateUtil.createDate(iYear, iMonth, weekStartDate);
                    viewWeekEndDate = DateUtil.createDate(weekEndYear, weekEndMonth, weekEndDate);
                    // create SQL Date objects from Date objects so they can be inserted into SQL database
                    sqlWkStartDate = new java.sql.Date(viewWeekStartDate.getTime());
                    sqlWkEndDate = new java.sql.Date(viewWeekEndDate.getTime());
                    // set viewWeek to true so jsp page will display week calendar
                    viewWeek = true;
                    break;
                case "/prevMonth":
                    cMonth = (String) session.getAttribute("cMonth");
                    iYear = (Integer) session.getAttribute("iYear");
                    calendar = new GregorianCalendar();
                    // create calendar object
                    calendar = DateUtil.createCalendar(iYear, cMonth, 1);
                    calendar.add(Calendar.MONTH, -1); // go back one month
                    iMonth = calendar.get(Calendar.MONTH);
                    iYear = calendar.get(Calendar.YEAR);
                    break;
                case "/nextMonth":
                    cMonth = (String) session.getAttribute("cMonth");
                    iYear = (Integer) session.getAttribute("iYear");
                    calendar = new GregorianCalendar();
                    // create calendar object
                    calendar = DateUtil.createCalendar(iYear, cMonth, 1);
                    calendar.add(Calendar.MONTH, 1); // go forward one month
                    iMonth = calendar.get(Calendar.MONTH);
                    iYear = calendar.get(Calendar.YEAR);
                    break;
                case "/calendar":
                    iYear = CalendarUtil.nullIntconv(request.getParameter("iYear"));
                    cMonth = request.getParameter("iMonth");
                    calendar = new GregorianCalendar();
                    calendar = DateUtil.createCalendar(iYear, cMonth, 1);
                    iMonth = calendar.get(Calendar.MONTH);
                    break;
                }

            Calendar ca = new GregorianCalendar();
            int iTYear = ca.get(Calendar.YEAR);
            int iTMonth = ca.get(Calendar.MONTH);

            if (iYear == 0)
                {
                iYear = iTYear;
                iMonth = iTMonth;
                }

            GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);
            int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);
            cal = new GregorianCalendar(iYear, iMonth, days);
            int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
            switch (userPath)
                {
                case "/viewDay":
                case "/prevDay":
                case "/nextDay":
                case "/gotoDate":
                case "/viewToday":
                case "/viewDate":
                    // get user day calendar data from database
                    calendarDay = CalendarDB.selectCalendar(clientId, iMonth, iYear, day);
                    if (calendarDay == null)
                        {
                        errorMessage = "There was an error retrieving your calendar data";
                        request.setAttribute("errorMessage", errorMessage);
                        }
                    break;
                case "/viewWeek":
                case "/nextWeek":
                case "/prevWeek":
                case "/thisWeek":
                    // get user calendar week within start and end selected date ranges
                    calendarWeek = CalendarDB.selectCalendar(clientId, sqlWkStartDate, sqlWkEndDate);
                    if (calendarWeek == null)
                        {
                        errorMessage = "There was an error retrieving your calendar data";
                        request.setAttribute("errorMessage", errorMessage);
                        }
                    break;
                default:
                    // get user month calendar data from database
                    calendarMonth = CalendarDB.selectCalendar(clientId, iMonth, iYear);
                    if (calendarMonth == null)
                        {
                        errorMessage = "There was an error retrieving your calendar data";
                        request.setAttribute("errorMessage", errorMessage);
                        }
                    break;
                }
            // get LONG (i.e. January) Month name using SimpleDateFormat
            cMonth = new SimpleDateFormat("MMMM").format(new Date(iYear, iMonth, days));

            // place total weeks into an array
            ArrayList<Integer> totalWeeks = CalendarUtil.TotalWeeks(iTotalweeks);

            int cnt = 1;
            // redirect  jsp userpath back to page of user origin
            url = userPath + ".jsp";

            session.setAttribute("iYear", iYear);
            session.setAttribute("iTYear", iTYear);
            session.setAttribute("iTotalweeks", iTotalweeks);
            session.setAttribute("weekStartDay", weekStartDay);
            session.setAttribute("cMonth", cMonth);
            session.setAttribute("calendar", calendarMonth);
            session.setAttribute("calendarDay", calendarDay);
            session.setAttribute("iMonth", iMonth);
            session.setAttribute("days", days);
            session.setAttribute("totalWeeks", totalWeeks);
            session.setAttribute("cnt", cnt);
            request.setAttribute("viewWeek", viewWeek);
            request.setAttribute("viewDay", viewDay);
            request.setAttribute("day", day);
            session.setAttribute("selectedDate", selectedDate);
            session.setAttribute("calendarWeek", calendarWeek);

            session.setAttribute("viewWeekStartDate", viewWeekStartDate);
            session.setAttribute("viewWeekEndDate", viewWeekEndDate);

            }
        else
            {
            session.invalidate();
            url = "/index.jsp";
            }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);

    }
}
