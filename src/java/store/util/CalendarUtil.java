/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import messages.LogFile;
import store.data.AssociateDB;

/**
 *
 * @author williamdobbs
 */
public class CalendarUtil
{

    private static final String CALENDARUTIL = "CalendarUtil ";
    private static final String ERRORMESSGE = "We're sorry, but the system failed to process your schedule.<br>"
            + "Please make sure your 'Dates' and 'Times' are entered correctly.<br> • Date example; 01/31/2012 - "
            + " and selected 'accept appointment every minutes' is less than the time you select between 'time in' and 'time out'.";
    private static final String MESSAGE = "Schedule Request was Successfully Processed!";

    public static int nullIntconv(String inv) throws IOException
    {
        int conv = 0;

        try
        {
            conv = Integer.parseInt(inv);
        }
        catch (NumberFormatException e)
        {
            LogFile.databaseError(CALENDARUTIL + "nullIntconv error ", e.getMessage(), e.toString());
        }
        return conv;
    }

    public static int MonthNameConv(String monthName, boolean prevMonth, boolean nextMonth)
    {
        int convMonth = 0;
        try
        {
            if (monthName.equalsIgnoreCase("January"))
            {
                if (prevMonth == true)
                {
                    convMonth = 11;
                }
                else if (nextMonth == true)
                {
                    convMonth = 1;
                }
                else
                {
                    convMonth = 0;
                }
            }
            else if (monthName.equalsIgnoreCase("February"))
            {
                if (prevMonth == true)
                {
                    convMonth = 0;
                }
                else if (nextMonth == true)
                {
                    convMonth = 2;
                }
                else
                {
                    convMonth = 1;
                }
            }
            else if (monthName.equalsIgnoreCase("March"))
            {
                if (prevMonth == true)
                {
                    convMonth = 1;
                }
                else if (nextMonth == true)
                {
                    convMonth = 3;
                }
                else
                {
                    convMonth = 2;
                }
            }
            else if (monthName.equalsIgnoreCase("April"))
            {
                if (prevMonth == true)
                {
                    convMonth = 2;
                }
                else if (nextMonth == true)
                {
                    convMonth = 4;
                }
                else
                {
                    convMonth = 3;
                }
            }
            else if (monthName.equalsIgnoreCase("May"))
            {
                if (prevMonth == true)
                {
                    convMonth = 3;
                }
                else if (nextMonth == true)
                {
                    convMonth = 5;
                }
                else
                {
                    convMonth = 4;
                }
            }
            else if (monthName.equalsIgnoreCase("June"))
            {
                if (prevMonth == true)
                {
                    convMonth = 4;
                }
                else if (nextMonth == true)
                {
                    convMonth = 6;
                }
                else
                {
                    convMonth = 5;
                }
            }
            else if (monthName.equalsIgnoreCase("July"))
            {
                if (prevMonth == true)
                {
                    convMonth = 5;
                }
                else if (nextMonth == true)
                {
                    convMonth = 7;
                }
                else
                {
                    convMonth = 6;
                }
            }
            else if (monthName.equalsIgnoreCase("August"))
            {
                if (prevMonth == true)
                {
                    convMonth = 6;
                }
                else if (nextMonth == true)
                {
                    convMonth = 8;
                }
                else
                {
                    convMonth = 7;
                }
            }
            else if (monthName.equalsIgnoreCase("September"))
            {
                if (prevMonth == true)
                {
                    convMonth = 7;
                }
                else if (nextMonth == true)
                {
                    convMonth = 9;
                }
                else
                {
                    convMonth = 8;
                }
            }
            else if (monthName.equalsIgnoreCase("October"))
            {
                if (prevMonth == true)
                {
                    convMonth = 8;
                }
                else if (nextMonth == true)
                {
                    convMonth = 10;
                }
                else
                {
                    convMonth = 9;
                }
            }
            else if (monthName.equalsIgnoreCase("November"))
            {
                if (prevMonth == true)
                {
                    convMonth = 9;
                }
                else if (nextMonth == true)
                {
                    convMonth = 11;
                }
                else
                {
                    convMonth = 10;
                }
            }
            else if (monthName.equalsIgnoreCase("December"))
            {
                if (prevMonth == true)
                {
                    convMonth = 10;
                }
                else if (nextMonth == true)
                {
                    convMonth = 0;
                }
                else
                {
                    convMonth = 11;
                }
            }
        }
        catch (Exception e)
        {
        }
        return convMonth;
    }

    public static ArrayList<Integer> TotalWeeks(int iTotalweeks)
    {
        // create array for totalweeks
        ArrayList<Integer> totalWeeks = new ArrayList<>();
        for (int i = 0; i < iTotalweeks; i++)
        {
            totalWeeks.add(i + 1);
        }
        return totalWeeks;
    }

    public static boolean processScheduleN(int associateId, String dayString, String dateFrom,
            String dateTo, String startTime, String endTime, String[] days,
            int frequency, boolean afternoonShed, HttpServletRequest request) throws IOException
    {
        try
        {
            int insertSchedule = 0;
            // convert dates and times to Date objects
            Date convDateFrom = DateUtil.convertDateSched(dateFrom);
            Date convDateTo = DateUtil.convertDateSched(dateTo);
            Date convStart = DateUtil.convertTime(startTime);
            Date convEnd = DateUtil.convertTime(endTime);

            // convert dayString to integer
            int day = DateUtil.convDay(dayString);

            Calendar cal = Calendar.getInstance(); // get calendar instance
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(convDateFrom); // calendar object time to converted date object time
            cal2.setTime(convDateTo);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            // get the number of days difference
            int totalDaysDiff = DateUtil.daysDiff(convDateFrom, convDateTo);

            Calendar calStartTime = Calendar.getInstance();
            calStartTime.setTime(convStart);

            Calendar calEndTime = Calendar.getInstance();
            calEndTime.setTime(convEnd);

            // get integers for hour difference calculation
            int calStartHour = calStartTime.get(Calendar.HOUR_OF_DAY);
            int calEndHour = calEndTime.get(Calendar.HOUR_OF_DAY);
            int calStartMinute = calStartTime.get(Calendar.MINUTE);
            int calEndMinute = calEndTime.get(Calendar.MINUTE);

            // convert frequency to multiplier number
            int calculateTimes = calculateTimes(frequency);

            // calculate the number of times to insert into database using for-loop
            int totalTime = (calEndHour - calStartHour) * calculateTimes;
            // get total loops adjustment conversion
            totalTime = totalTimes(calStartMinute, calEndMinute, frequency, totalTime);

            for (int j = 0; j <= totalDaysDiff; j++)
            {
                Date newDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());

                if (day == dayOfWeek)
                {
                    if (afternoonShed == false)
                    {
                        // delete all times on current scheduled date
                        int delCurrentSchedule = AssociateDB.deleteAvailability(associateId, sqlDate);
                    }
                    // check and see if Associate is available on selected date
                    boolean isAssociateAvailable = AssociateDB.isAssociateAvailability(associateId, sqlDate);
                    if (isAssociateAvailable == false)
                    { // TODO write message code if database insertAvailableDate fails to record
                        int insertAvailDate = AssociateDB.insertAvailableDate(associateId, sqlDate);
                    }
                    for (int i = 0; i < totalTime; i++)
                    {
                        Date newTime = calStartTime.getTime();
                        java.sql.Time sqlTime = new java.sql.Time(newTime.getTime());
                        Date c = DateUtil.convertCalendar(cal, calStartTime);
                        Timestamp sqlTimestamp = new java.sql.Timestamp(c.getTime());

                        // sql end timestamp conversion
                        Calendar et = (Calendar) calStartTime.clone();
                        et.add(Calendar.MINUTE, frequency);
                        Date end = DateUtil.convertCalendar(cal, et);
                        Timestamp sqlEndTime = new java.sql.Timestamp(end.getTime());

                        // insert new scheduled date, time, and associate id into database
                        insertSchedule = AssociateDB.insertAssociateSchedule(sqlDate, sqlTime, associateId, sqlTimestamp, sqlEndTime);
                        // add appointment interval frequency time to scheduled date
                        calStartTime.add(Calendar.MINUTE, frequency); // add frequency time
                    }
                }
                // advance the day of month by 1
                cal.add(Calendar.DAY_OF_MONTH, 1);
                dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // get new day of week number (integer)
                calStartTime.setTime(convStart); // reset startTime back to original startTime
            }
            if (insertSchedule == 0)
            {
                LogFile.associateLog(CALENDARUTIL + "processScheduleN ERROR ", "insertSchedule=0", " associateId:" + associateId + " toDate:"
                        + dateTo + " fromDate:" + dateFrom + " startTime:" + startTime + " endTime:" + endTime + " frequency:" + frequency);
                request.setAttribute("errorNorMessage", ERRORMESSGE);
            }
            else
            {
                LogFile.associateLog(CALENDARUTIL + "processScheduleN ", MESSAGE, " associateId:" + associateId + " toDate:"
                        + dateTo + " fromDate:" + dateFrom + " startTime:" + startTime + " endTime:" + endTime + " frequency:" + frequency);
                request.setAttribute("messageN", MESSAGE);
                request.setAttribute("nDay", days);
                request.setAttribute("dateFrom", convDateFrom);
                request.setAttribute("dateTo", convDateTo);
            }
        }
        catch (NullPointerException npe)
        {
            LogFile.databaseError(CALENDARUTIL + "processScheduleN error ", npe.getMessage(), npe.toString());
            request.setAttribute("errorNorMessage", ERRORMESSGE);
            return false;
        }
        return true;
    }
    // TODO code for quick schedule

    public static boolean processQuickSched(int associateId, int length, String qsDate,
            String qsStart, String qsEnd, String[] days,
            int frequency, HttpServletRequest request) throws IOException
    {

        try
        {
            int delCurrentSchedule;
            int insertAvailDate;
            int insertSchedule = 0;
            // convert date and time strings to Date objects
            Date date = DateUtil.convertDateSched(qsDate);
            Date startTime = DateUtil.convertTime(qsStart);
            Date endTime = DateUtil.convertTime(qsEnd);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            Calendar calStartTime = Calendar.getInstance();
            calStartTime.setTime(startTime);

            Calendar calEndTime = Calendar.getInstance();
            calEndTime.setTime(endTime);

            // get integers for hour difference calculation
            int calStartHour = calStartTime.get(Calendar.HOUR_OF_DAY);
            int calEndHour = calEndTime.get(Calendar.HOUR_OF_DAY);
            int calStartMinute = calStartTime.get(Calendar.MINUTE);
            int calEndMinute = calEndTime.get(Calendar.MINUTE);

            // convert frequency to multiplier number
            int calculateTimes = calculateTimes(frequency);

            // calculate the number of times to insert into database using for-loop
            int totalTime = (calEndHour - calStartHour) * calculateTimes;
            // get total loops adjustment conversion
            totalTime = totalTimes(calStartMinute, calEndMinute, frequency, totalTime);

            if (length == 1) // process and schedule just one day
            {
                Date newDate = cal.getTime();
                java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());

                // delete all times on current scheduled date
                delCurrentSchedule = AssociateDB.deleteAvailability(associateId, sqlDate);
                // check and see if Associate is available on selected date
                boolean isAssociateAvailable = AssociateDB.isAssociateAvailability(associateId, sqlDate);
                if (isAssociateAvailable == false)
                {
                    insertAvailDate = AssociateDB.insertAvailableDate(associateId, sqlDate);
                }
                for (int i = 0; i < totalTime; i++)
                {
                    Date newTime = calStartTime.getTime();
                    java.sql.Time sqlTime = new java.sql.Time(newTime.getTime());

                    Date c = DateUtil.convertCalendar(cal, calStartTime);
                    Timestamp sqlTimestamp = new java.sql.Timestamp(c.getTime());

                    // sql end timestamp conversion
                    Calendar et = (Calendar) calStartTime.clone();
                    et.add(Calendar.MINUTE, frequency);
                    Date end = DateUtil.convertCalendar(cal, et);
                    Timestamp sqlEndTime = new java.sql.Timestamp(end.getTime());

                    // insert new scheduled date, time, and associate id into database
                    insertSchedule = AssociateDB.insertAssociateSchedule(sqlDate, sqlTime, associateId, sqlTimestamp, sqlEndTime);
                    // add appointment interval frequency time to scheduled date
                    calStartTime.add(Calendar.MINUTE, frequency); // add frequency time
                }
            }
            else
            {
                for (int j = 0; j < length; j++)
                {
                    Date newDate = cal.getTime();
                    java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());
                    for (String dayString : days)
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
                        if (day == dayOfWeek)
                        {
                            // delete all times on current scheduled date
                            delCurrentSchedule = AssociateDB.deleteAvailability(associateId, sqlDate);
                            // check and see if Associate is available on selected date
                            boolean isAssociateAvailable = AssociateDB.isAssociateAvailability(associateId, sqlDate);
                            if (isAssociateAvailable == false)
                            { // TODO write message code if database insertAvailableDate fails to record
                                insertAvailDate = AssociateDB.insertAvailableDate(associateId, sqlDate);
                            }
                            for (int i = 0; i < totalTime; i++)
                            {
                                Date newTime = calStartTime.getTime();
                                java.sql.Time sqlTime = new java.sql.Time(newTime.getTime());
                                // sql timestamp conversion
                                Date c = DateUtil.convertCalendar(cal, calStartTime);
                                Timestamp sqlTimestamp = new java.sql.Timestamp(c.getTime());

                                // sql end timestamp conversion
                                Calendar et = (Calendar) calStartTime.clone();
                                et.add(Calendar.MINUTE, frequency);
                                Date end = DateUtil.convertCalendar(cal, et);
                                Timestamp sqlEndTime = new java.sql.Timestamp(end.getTime());

                                // insert new scheduled date, time, and associate id into database
                                insertSchedule = AssociateDB.insertAssociateSchedule(sqlDate, sqlTime, associateId, sqlTimestamp, sqlEndTime);
                                // add appointment interval frequency time to scheduled date
                                calStartTime.add(Calendar.MINUTE, frequency); // add frequency time
                            }
                        }
                    }
                    cal.add(Calendar.DAY_OF_MONTH, 1); // advance the day of month by 1
                    dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // get new day of week number (integer)
                    calStartTime.setTime(startTime); // reset startTime back  to original startTime
                }
            }
            if (insertSchedule == 0)
            {
                // error occured; respond with error message
                request.setAttribute("errorQSMessage", ERRORMESSGE);
                // log result
                LogFile.associateLog(CALENDARUTIL + "processQuickSched ERROR ", "insertSchedule=0", " associateId:" + associateId + " qsDate:"
                        + qsDate + " qsStartTime:" + qsStart + " qsEndTime:" + qsEnd + " frequency:" + frequency + " length:" + length);
            }
            else
            {
                // successfully entered into database; respond with message
                request.setAttribute("message", MESSAGE);
                request.setAttribute("qsFrequency", frequency);
                // record into associate log file
                LogFile.associateLog(CALENDARUTIL + "processQuickSched ", MESSAGE, " associateId:" + associateId + " qsDate:"
                        + qsDate + " qsStartTime:" + qsStart + " qsEndTime:" + qsEnd + " frequency:" + frequency + " length:" + length);

                if (length != 1)
                {
                    request.setAttribute("days", days);
                }
                request.setAttribute("dateQS", date);
            }
        }
        catch (NullPointerException npe)
        {
            LogFile.databaseError(CALENDARUTIL + "processQuickSched error ", npe.getMessage(), npe.toString());
            request.setAttribute("errorQSMessage", ERRORMESSGE);
            return false;
        }
        return true;
    }

    public static int calculateTimes(int frequency)
    {
        int numTimes = 0;
        switch (frequency)
        {
            case 15:
                numTimes = 4;
                break;
            case 30:
                numTimes = 2;
                break;
            case 60:
                numTimes = 1;
                break;
            default:
                break;
        }
        return numTimes;
    }

    public static int totalTimes(int startMinute, int endMinute, int frequency, int totalTime)
    {
        if (startMinute == 0 && endMinute == 30 && frequency == 15)
        {
            totalTime = totalTime + 2;
        }
        else if (startMinute == 30 && endMinute == 0 && frequency == 15)
        {
            totalTime = totalTime - 2;
        }
        else if (startMinute == 0 && endMinute == 30 && frequency == 30)
        {
            totalTime = totalTime + 1;
        }
        else if (startMinute == 30 && endMinute == 0 && frequency == 30)
        {
            totalTime = totalTime - 1;
        }
        return totalTime;
    }
}
