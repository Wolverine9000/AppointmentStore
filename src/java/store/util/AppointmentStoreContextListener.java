package store.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import security.SecurityQuestions;
import store.business.Category;
import store.business.ServiceStatus;
import store.data.CalendarDB;
import store.data.ProductDB;
import store.data.SecurityDB;

public class AppointmentStoreContextListener implements ServletContextListener
{

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        ServletContext sc = event.getServletContext();

        boolean loggedInFlag = false;
        sc.setAttribute("loggedInFlag", loggedInFlag);

        ArrayList<Category> categories = ProductDB.selectCategory();
        sc.setAttribute("categories", categories);

        ArrayList<ServiceStatus> status;
        status = CalendarDB.serviceStatusAll();
        sc.setAttribute("status", status);

        ArrayList<SecurityQuestions> questions = SecurityDB.selectQuestions();
        sc.setAttribute("questions", questions);

// default start and end times for scheduler
        sc.setAttribute("defaultStartTime", "7:00 am");
        sc.setAttribute("defaultEndTime", "6:00 pm");

        ArrayList<String> times = new ArrayList<>();
        times.add("-------");
        times.add("12:00 am");
        times.add("12:15 am");
        times.add("12:30 am");
        times.add("12:45 am");
        times.add("1:00 am");
        times.add("1:15 am");
        times.add("1:30 am");
        times.add("1:45 am");
        times.add("2:00 am");
        times.add("2:15 am");
        times.add("2:30 am");
        times.add("2:45 am");
        times.add("3:00 am");
        times.add("3:15 am");
        times.add("3:30 am");
        times.add("3:45 am");
        times.add("4:00 am");
        times.add("4:15 am");
        times.add("4:30 am");
        times.add("4:45 am");
        times.add("5:00 am");
        times.add("5:15 am");
        times.add("5:30 am");
        times.add("5:45 am");
        times.add("6:00 am");
        times.add("6:15 am");
        times.add("6:30 am");
        times.add("6:45 am");
        times.add("7:00 am");
        times.add("7:15 am");
        times.add("7:30 am");
        times.add("7:45 am");
        times.add("8:00 am");
        times.add("8:15 am");
        times.add("8:30 am");
        times.add("8:45 am");
        times.add("9:00 am");
        times.add("9:15 am");
        times.add("9:30 am");
        times.add("9:45 am");
        times.add("10:00 am");
        times.add("10:15 am");
        times.add("10:30 am");
        times.add("10:45 am");
        times.add("11:00 am");
        times.add("11:15 am");
        times.add("11:30 am");
        times.add("11:45 am");
        times.add("12:00 pm");
        times.add("12:15 pm");
        times.add("12:30 pm");
        times.add("12:45 pm");
        times.add("1:00 pm");
        times.add("1:15 pm");
        times.add("1:30 pm");
        times.add("1:45 pm");
        times.add("2:00 pm");
        times.add("2:15 pm");
        times.add("2:30 pm");
        times.add("2:45 pm");
        times.add("3:00 pm");
        times.add("3:15 pm");
        times.add("3:30 pm");
        times.add("3:45 pm");
        times.add("4:00 pm");
        times.add("4:15 pm");
        times.add("4:30 pm");
        times.add("4:45 pm");
        times.add("5:00 pm");
        times.add("5:15 pm");
        times.add("5:30 pm");
        times.add("5:45 pm");
        times.add("6:00 pm");
        times.add("6:15 pm");
        times.add("6:30 pm");
        times.add("6:45 pm");
        times.add("7:00 pm");
        times.add("7:15 pm");
        times.add("7:30 pm");
        times.add("7:45 pm");
        times.add("8:00 pm");
        times.add("8:15 pm");
        times.add("8:30 pm");
        times.add("8:45 pm");
        times.add("9:00 pm");
        times.add("9:15 pm");
        times.add("9:30 pm");
        times.add("9:45 pm");
        times.add("10:00 pm");
        times.add("10:15 pm");
        times.add("10:30 pm");
        times.add("10:45 pm");
        times.add("11:00 pm");
        times.add("11:15 pm");
        times.add("11:30 pm");
        times.add("11:45 pm");

        sc.setAttribute("times", times);

        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("15");
        frequency.add("30");
        frequency.add("60");
        sc.setAttribute("frequency", frequency);

        ArrayList<String> length = new ArrayList<>();
        length.add("1 day");
        length.add("1 week");
        length.add("2 weeks");
        length.add("4 weeks");
        length.add("3 months");
        length.add("6 months");
        length.add("1 year");
        sc.setAttribute("length", length);

        ArrayList<String> states = new ArrayList<>();
        states.add("AL");
        states.add("AK");
        states.add("AZ");
        states.add("AR");
        states.add("CA");
        states.add("CO");
        states.add("CT");
        states.add("DE");
        states.add("DC");
        states.add("FL");
        states.add("GA");
        states.add("HI");
        states.add("ID");
        states.add("IL");
        states.add("IN");
        states.add("IA");
        states.add("KS");
        states.add("KY");
        states.add("LA");
        states.add("ME");
        states.add("MD");
        states.add("MA");
        states.add("MI");
        states.add("MN");
        states.add("MS");
        states.add("MO");
        states.add("MT");
        states.add("NE");
        states.add("NV");
        states.add("NH");
        states.add("NJ");
        states.add("NM");
        states.add("NY");
        states.add("NC");
        states.add("ND");
        states.add("OH");
        states.add("OK");
        states.add("PA");
        states.add("SC");
        states.add("RI");
        states.add("SD");
        states.add("TN");
        states.add("TX");
        states.add("UT");
        states.add("VT");
        states.add("VA");
        states.add("WA");
        states.add("WV");
        states.add("WI");
        states.add("WY");

        sc.setAttribute("states", states);

        // initialize  today's date
        Date todaysDate = DateUtil.getCurrentDate();
        sc.setAttribute("todaysDate", todaysDate);

        // initialize the customer service email address that's used throughout the web site
        String custServEmail = sc.getInitParameter("custServEmail");
        sc.setAttribute("custServEmail", custServEmail);

        // initialize the current year that's used in the copyright notice
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentYear = currentDate.get(Calendar.YEAR);
        sc.setAttribute("currentYear", currentYear);

        // initialize the array of years that's used for the credit card year
        ArrayList<String> creditCardYears = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            int year = currentYear + i;
            String yearString = Integer.toString(year);
            creditCardYears.add(yearString);
        }
        sc.setAttribute("creditCardYears", creditCardYears);

        // initialize the array of years that's used for calendar combo box
        ArrayList<String> calendarYears = new ArrayList<>();
        for (int i = -1; i < 4; i++)
        {
            int year = currentYear + i;
            String yearString = Integer.toString(year);
            calendarYears.add(yearString);
        }
        sc.setAttribute("calendarYears", calendarYears);

        // initialize and create array for month name combo box
        ArrayList<String> months = new ArrayList<>();
        for (int im = 0; im <= 11; im++)
        {
            {
                String mo = new SimpleDateFormat("MMMM").format(new Date(2008, im, 01));
                months.add(mo);
            }
        }
        sc.setAttribute("months", months);

        // initialize and create array for weekdays
        ArrayList<Integer> weekDays = new ArrayList<>();
        for (int j = 0; j < 7; j++)
        {
            weekDays.add(j + 1);
        }
        sc.setAttribute("weekDays", weekDays);

        // initialize the service category
//        ArrayList<Services> services = ProductDB.selectAllServices();
//        sc.setAttribute("services", services);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        // no cleanup necessary
    }
}
