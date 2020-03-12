/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.util;

import admin.FindInvoiceDateRangeServlet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import messages.LogFile;

/**
 *
 * @author williamdobbs
 */
public class DateUtil
{

    static final int MILLS_IN_DAY = 24 * 60 * 60 * 1000;

    public static Timestamp convertDate(String dateString)
    {
        try
        {
            java.sql.Timestamp sqlTimestamp;
            Date date;
            // date = new Date();
            if (dateString.contains("T"))
            {
                DateFormat utcFormat;
                if (!dateString.contains("Z"))
                {
                    // utc format
                    utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                }
                else
                {
                    // utc format
                    utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    utcFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // set universal time zone
                }
                // parse incoming utc date string
                date = utcFormat.parse(dateString);
                // create SimpleDateFormat object with source cst string date format
                DateFormat cstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                cstFormat.setTimeZone(TimeZone.getTimeZone("CST")); // set central time zone
                String format = cstFormat.format(date);

                date = cstFormat.parse(format);
                sqlTimestamp = new java.sql.Timestamp(date.getTime());
            }
            else
            {
                //create SimpleDateFormat object with source string date format
                SimpleDateFormat sdfSource = new SimpleDateFormat("MM/dd/yyyy");
                //parse the string into Date object
                date = sdfSource.parse(dateString);
                //parse the date object into sqlTimestamp format
                sqlTimestamp = new java.sql.Timestamp(date.getTime());
            }
            return sqlTimestamp;
        }
        catch (ParseException ex)
        {
            LogFile.databaseError("DateUtil convertDate error ", ex.getMessage(), ex.toString());
            Logger.getLogger(FindInvoiceDateRangeServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Date timestampToDate(String timestamp)
    {
        Date d = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            d = format.parse(timestamp);
        }
        catch (ParseException ex)
        {
            LogFile.databaseError("DateUtil convertToTimestamp error ", ex.getMessage(), ex.toString());
        }
        return d;
    }

    public static Timestamp convertTimeStamp(String timetamp)
    {
        Timestamp sqlTimestamp = null;
        try
        {
            Date date;
            //create SimpleDateFormat object with source string date format
            SimpleDateFormat sdfSource = new SimpleDateFormat("MM-dd-yyyy h:mm a");

            //parse the string into Date object
            date = sdfSource.parse(timetamp);

            //parse the date object into sqlTimestamp format
            sqlTimestamp = new Timestamp(date.getTime());
        }
        catch (ParseException ex)
        {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            LogFile.databaseError("DateUtil convertTimeStamp error ", ex.getMessage(), ex.toString());
        }
        return sqlTimestamp;
    }

    public static String createDateString(Timestamp timestamp)
    {
        Date date;
        String tsmpString;

        date = new java.sql.Timestamp(timestamp.getTime());
        DateFormat cstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        cstFormat.setTimeZone(TimeZone.getTimeZone("CST")); // set to central time zone
        tsmpString = cstFormat.format(date);

        return tsmpString;
    }

// convert current date and time to long string of numbers
    public static String dateNowLong()
    {
        Date now = new Date();
        long n = now.getTime() / 1000;
        String strLong = Long.toString(n);
        return strLong;
    }

    public static String convertDateString(String dateString)
    {
        try
        {
            //create SimpleDateFormat object with source string date format
            SimpleDateFormat sdfSource = new SimpleDateFormat("MM/dd/yyyy");
            //parse the string into Date object
            Date date = sdfSource.parse(dateString);
            //create SimpleDateFormat object with desired date format
            SimpleDateFormat sdfDestination = new SimpleDateFormat("EEE, MMM d, yyyy");
            // parse the date object into new date format
            dateString = sdfDestination.format(date);
            return dateString;
        }
        catch (ParseException ex)
        {
            LogFile.databaseError("SQL convertDateString error ", ex.getMessage(), ex.toString());
            Logger.getLogger(FindInvoiceDateRangeServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String convertTimeString(String timeString)
    {
        try
        {
            //create SimpleDateFormat object with source string time format
            SimpleDateFormat sdfSource = new SimpleDateFormat("k:mm:ss");
            //parse the string into Date object
            Date date = sdfSource.parse(timeString);
            //create SimpleDateFormat object with desired time format
            SimpleDateFormat sdfDestination = new SimpleDateFormat("h:mm a");
            // parse the time into another format
            timeString = sdfDestination.format(date);
            return timeString;
        }
        catch (ParseException ex)
        {
            LogFile.databaseError("SQL convertTimeString error ", ex.getMessage(), ex.toString());
            Logger.getLogger(FindInvoiceDateRangeServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Calendar createCalendar(int year, String month, int day)
    {
        Calendar cal = Calendar.getInstance();
        Date date;
        int monthInt;
        try
        {
            if (month == null || year == 0)
            {
                date = getCurrentDate();
                cal.setTime(date);
            }
            else
            {
                cal.setTime(new SimpleDateFormat("MMMM").parse(month));
                monthInt = cal.get(Calendar.MONTH);
                date = createDate(year, monthInt, day);
                cal.setTime(date);
            }
        }
        catch (ParseException ex)
        {
            LogFile.databaseError("DateUtil createCalendar  error ", ex.getMessage(), ex.toString());
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return cal;
    }

    public static Date convertDateSched(String dateString)
    {
        Date date;

        if (dateString.contains("T"))
        {
            if (!dateString.contains("Z"))
            {
                dateString = dateString + ".000Z";
            }
            try
            {
                // utc format
                DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                // parse incoming utc date string
                date = utcFormat.parse(dateString);

                // create SimpleDateFormat object with source cst string date format
                DateFormat cstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                cstFormat.setTimeZone(TimeZone.getTimeZone("CST")); // set central time zone
                String format = cstFormat.format(date);
                date = cstFormat.parse(format);
            }
            catch (ParseException ex)
            {
                LogFile.databaseError("DateUtil convertDateSched error "
                        + dateString + " ", ex.getMessage(), ex.toString());
                return null;
            }
        }
        else
        {
            try
            {
                //create SimpleDateFormat object with source string date format
                SimpleDateFormat sdfSource = new SimpleDateFormat("MM/dd/yyyy");

                //parse the string into Date object
                date = sdfSource.parse(dateString);
                return date;
            }
            catch (ParseException ex)
            {
                LogFile.databaseError("DateUtil convertDateSched error "
                        + dateString + " ", ex.getMessage(), ex.toString());
                return null;
            }
        }
        return date;
    }

    public static Date convertTime(String timeString)
    {
        DateFormat dbFormat = new SimpleDateFormat("h:mm a");
        try
        {
            Date time = dbFormat.parse(timeString); // format time
            return time;
        }
        catch (ParseException ex)
        {
            LogFile.databaseError("DateUtil convertTime error ", ex.getMessage(), ex.toString());
            return null;
        }
    }

    public static LocalDateTime convertDateTimeString(String dateTimeString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pdt = LocalDateTime.parse(dateTimeString, formatter);
        return pdt;
    }

    public static Date convertCalendar(Calendar calendar)
    {
        return calendar.getTime();
    }

    public static Date convertCal(Calendar calDate, Calendar calTime)
    {
        int yr = calDate.get(Calendar.YEAR);
        int mo = calDate.get(Calendar.MONTH);
        int da = calDate.get(Calendar.DAY_OF_MONTH);
        int hr = calTime.get(Calendar.HOUR_OF_DAY);
        int mi = calTime.get(Calendar.MINUTE);
        int se = calTime.get(Calendar.SECOND);

        GregorianCalendar currentDate = new GregorianCalendar(yr, mo, da, hr, mi, se);

        return currentDate.getTime();
    }

    public static Date getCurrentDate()
    {
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime();
    }

    public static Date createDate(int year, int month, int day)
    {
        GregorianCalendar date = new GregorianCalendar(year, month, day);
        return date.getTime();
    }

    public static Date createDate(int year, int month, int day, int hour, int min, int sec)
    {
        GregorianCalendar date = new GregorianCalendar(year, month, day, hour, min, sec);
        return date.getTime();
    }

    public static String createDateString(int year, int month, int day, int hour, int min, int sec)
    {
        GregorianCalendar date = new GregorianCalendar(year, month, day, hour, min, sec);
        Date dateObj = date.getTime();
        String startDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateObj.getTime());
        startDateStr = startDateStr.replace(" ", "T");
        return startDateStr;
    }

    public static Date stripTime(Date date)
    {
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(date);
        currentDate.set(Calendar.HOUR, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime();
    }

    public static Date addTime(Date date, int minutes)
    {
        GregorianCalendar calendarDate = new GregorianCalendar();
        calendarDate.setTime(date);
        calendarDate.add(Calendar.MINUTE, minutes);
        return calendarDate.getTime();
    }

    public static int daysDiff(Date date1, Date date2)
    {
        date1 = stripTime(date1);
        date2 = stripTime(date2);
        long longDate1 = date1.getTime();
        long longDate2 = date2.getTime();
        long longDiff = longDate2 - longDate1;
        return (int) (longDiff / MILLS_IN_DAY);
    }

    public static String todaysDate()
    {
        Date date = new Date();
        String dateAsString = date.toString();
        return dateAsString;
    }

    public static String formatDate()
    {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(date);
        return s;
    }

    public synchronized static String formatDate(Date date)
    {
        Format formatter = new SimpleDateFormat("EEE, MMM d, yyyy");
        String s = formatter.format(date);
        return s;
    }

    public static int convDay(String dayString)
    {
        int day;
        if (dayString.equalsIgnoreCase("SUN"))
        {
            day = 1;
        }
        else if (dayString.equalsIgnoreCase("MON"))
        {
            day = 2;
        }
        else if (dayString.equalsIgnoreCase("TUE"))
        {
            day = 3;
        }
        else if (dayString.equalsIgnoreCase("WED"))
        {
            day = 4;
        }
        else if (dayString.equalsIgnoreCase("THU"))
        {
            day = 5;
        }
        else if (dayString.equalsIgnoreCase("FRI"))
        {
            day = 6;
        }
        else
        {
            day = 7;
        }
        return day;
    }

    public static boolean dayCheckbox(String[] days, HttpServletRequest request)
    {
        boolean dayCheckboxFlag = false;
        boolean sunCheckbox;
        boolean monCheckbox;
        boolean tueCheckbox;
        boolean wedCheckbox;
        boolean thuCheckbox;
        boolean friCheckbox;
        boolean satCheckbox;

        for (String dayString : days)
        {
            if (dayString.equalsIgnoreCase("SUN"))
            {
                dayCheckboxFlag = true;
                sunCheckbox = true;
                request.setAttribute("sunCheckbox", sunCheckbox);
            }
            else if (dayString.equalsIgnoreCase("MON"))
            {
                dayCheckboxFlag = true;
                monCheckbox = true;
                request.setAttribute("monCheckbox", monCheckbox);
            }
            else if (dayString.equalsIgnoreCase("TUE"))
            {
                dayCheckboxFlag = true;
                tueCheckbox = true;
                request.setAttribute("tueCheckbox", tueCheckbox);
            }
            else if (dayString.equalsIgnoreCase("WED"))
            {
                dayCheckboxFlag = true;
                wedCheckbox = true;
                request.setAttribute("wedCheckbox", wedCheckbox);
            }
            else if (dayString.equalsIgnoreCase("THU"))
            {
                dayCheckboxFlag = true;
                thuCheckbox = true;
                request.setAttribute("thuCheckbox", thuCheckbox);
            }
            else if (dayString.equalsIgnoreCase("FRI"))
            {
                dayCheckboxFlag = true;
                friCheckbox = true;
                request.setAttribute("friCheckbox", friCheckbox);
            }
            else if (dayString.equalsIgnoreCase("SAT"))
            {
                dayCheckboxFlag = true;
                satCheckbox = true;
                request.setAttribute("satCheckbox", satCheckbox);
            }
        }
        return dayCheckboxFlag;
    }

    public static boolean dayNorCheckbox(String[] nDays, HttpServletRequest request)
    {
        boolean dayCheckboxFlag = false;
        boolean sunNorCheckbox;
        boolean monNorCheckbox;
        boolean tueNorCheckbox;
        boolean wedNorCheckbox;
        boolean thuNorCheckbox;
        boolean friNorCheckbox;
        boolean satNorCheckbox;

        for (String dayString : nDays)
        {
            if (dayString.equalsIgnoreCase("SUN"))
            {
                dayCheckboxFlag = true;
                sunNorCheckbox = true;
                request.setAttribute("sunNorCheckbox", sunNorCheckbox);
            }
            else if (dayString.equalsIgnoreCase("MON"))
            {
                dayCheckboxFlag = true;
                monNorCheckbox = true;
                request.setAttribute("monNorCheckbox", monNorCheckbox);
            }
            else if (dayString.equalsIgnoreCase("TUE"))
            {
                dayCheckboxFlag = true;
                tueNorCheckbox = true;
                request.setAttribute("tueNorCheckbox", tueNorCheckbox);
            }
            else if (dayString.equalsIgnoreCase("WED"))
            {
                dayCheckboxFlag = true;
                wedNorCheckbox = true;
                request.setAttribute("wedNorCheckbox", wedNorCheckbox);
            }
            else if (dayString.equalsIgnoreCase("THU"))
            {
                dayCheckboxFlag = true;
                thuNorCheckbox = true;
                request.setAttribute("thuNorCheckbox", thuNorCheckbox);
            }
            else if (dayString.equalsIgnoreCase("FRI"))
            {
                dayCheckboxFlag = true;
                friNorCheckbox = true;
                request.setAttribute("friNorCheckbox", friNorCheckbox);
            }
            else if (dayString.equalsIgnoreCase("SAT"))
            {
                dayCheckboxFlag = true;
                satNorCheckbox = true;
                request.setAttribute("satNorCheckbox", satNorCheckbox);
            }
        }
        return dayCheckboxFlag;
    }

    public static int scheduleLength(String lengthString)
    {
        int length = 0;
        if (lengthString.equalsIgnoreCase("1 day"))
        {
            length = 1;
        }
        else if (lengthString.equalsIgnoreCase("1 week"))
        {
            length = 7;
        }
        else if (lengthString.equalsIgnoreCase("2 weeks"))
        {
            length = 14;
        }
        else if (lengthString.equalsIgnoreCase("4 weeks"))
        {
            length = 28;
        }
        else if (lengthString.equalsIgnoreCase("3 months"))
        {
            length = 84;
        }
        else if (lengthString.equalsIgnoreCase("6 months"))
        {
            length = 168;
        }
        else if (lengthString.equalsIgnoreCase("1 year"))
        {
            length = 336;
        }
        return length;
    }
}
