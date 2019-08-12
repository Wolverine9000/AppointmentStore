/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class ProcessNormalServlet extends HttpServlet
{

    private String url;
    private String daysError;
    private boolean validationDaysErrorFlag;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String currentTab = request.getParameter("currentTab");

        Associate2 associateRecord = (Associate2) session.getAttribute("associateRecord");

        if (associateRecord != null)
            {
            // get global settings  parameters
            String gloMT1 = request.getParameter("gloMT1");
            String gloMT2 = request.getParameter("gloMT2");
            String gloAT1 = request.getParameter("gloAT1");
            String gloAT2 = request.getParameter("gloAT2");
            // get sunday select schedule times
            String sunMT1 = request.getParameter("sunMT1");
            String sunMT2 = request.getParameter("sunMT2");
            String sunAT1 = request.getParameter("sunAT1");
            String sunAT2 = request.getParameter("sunAT2");
            // get monday select schedule times
            String monMT1 = request.getParameter("monMT1");
            String monMT2 = request.getParameter("monMT2");
            String monAT1 = request.getParameter("monAT1");
            String monAT2 = request.getParameter("monAT2");
            // get tuesday select schedule times
            String tueMT1 = request.getParameter("tueMT1");
            String tueMT2 = request.getParameter("tueMT2");
            String tueAT1 = request.getParameter("tueAT1");
            String tueAT2 = request.getParameter("tueAT2");
            // get wednesday select schedule times
            String wedMT1 = request.getParameter("wedMT1");
            String wedMT2 = request.getParameter("wedMT2");
            String wedAT1 = request.getParameter("wedAT1");
            String wedAT2 = request.getParameter("wedAT2");
            // get thursday select schedule times
            String thuMT1 = request.getParameter("thuMT1");
            String thuMT2 = request.getParameter("thuMT2");
            String thuAT1 = request.getParameter("thuAT1");
            String thuAT2 = request.getParameter("thuAT2");
            // get friday select schedule times
            String friMT1 = request.getParameter("friMT1");
            String friMT2 = request.getParameter("friMT2");
            String friAT1 = request.getParameter("friAT1");
            String friAT2 = request.getParameter("friAT2");
            // get saturday select schedule times
            String satMT1 = request.getParameter("satMT1");
            String satMT2 = request.getParameter("satMT2");
            String satAT1 = request.getParameter("satAT1");
            String satAT2 = request.getParameter("satAT2");

            int frequencyN = Integer.parseInt(request.getParameter("frequencyN"));
            String toDateN = request.getParameter("toN");
            String frDateN = request.getParameter("fromN");
            String[] nDays = request.getParameterValues("nDay");

            if (nDays != null)
                {
                boolean afternoonShed;
                boolean mainSchedFlag = true;
                boolean daysFlag = true;
                int associateId = associateRecord.getId(); // get the associate's id number

                boolean dayCheckboxFlag = false;
                dayCheckboxFlag = DateUtil.dayNorCheckbox(nDays, request); // flag days checked
                for (String dayString : nDays)
                    {
                        {
                        if (dayString.equalsIgnoreCase("sun"))
                            {
                            // validate user entry
                            boolean validationErrorFlagSun;
                            validationErrorFlagSun = Validator.validateSchedule(sunMT1, sunMT2, frDateN,
                                    sunMT1, sunMT2, nDays, daysFlag, mainSchedFlag, toDateN, request);

                            // if validation true found, return user associate scheduler
                            if (validationErrorFlagSun == true)
                                {
                                request.setAttribute("validationErrorFlagSun", validationErrorFlagSun);
                                }
                            else
                                {
                                afternoonShed = false;
                                boolean sunScheduleM = CalendarUtil.processScheduleN(associateId, dayString,
                                        frDateN, toDateN, sunMT1, sunMT2, nDays, frequencyN, afternoonShed, request);
                                if (!"-------".equals(sunAT1) && !"-------".equals(sunAT2))
                                    {
                                    validationErrorFlagSun = Validator.validateNoonTime(sunAT1, sunAT2, sunAT1, sunAT2, sunMT1, sunMT2, request);
                                    // if validation true found, return user associate scheduler
                                    if (validationErrorFlagSun == true)
                                        {
                                        request.setAttribute("validationErrorFlagSun", validationErrorFlagSun);
                                        }
                                    else
                                        {
                                        afternoonShed = true;
                                        boolean sunScheduleA = CalendarUtil.processScheduleN(associateId, dayString,
                                                frDateN, toDateN, sunAT1, sunAT2, nDays, frequencyN, afternoonShed, request);
                                        }
                                    }
                                }
                            }
                        else if (dayString.equalsIgnoreCase("mon"))
                            {
                            // validate user entry
                            boolean validationErrorFlagMon;
                            validationErrorFlagMon = Validator.validateSchedule(monMT1, monMT2, frDateN,
                                    monMT1, monMT2, nDays, daysFlag, mainSchedFlag, toDateN, request);

                            // if validation error true, return user associate scheduler
                            if (validationErrorFlagMon == true)
                                {
                                request.setAttribute("validationErrorFlagMon", validationErrorFlagMon);
                                }
                            else
                                {
                                afternoonShed = false;
                                boolean monScheduleM = CalendarUtil.processScheduleN(associateId, dayString,
                                        frDateN, toDateN, monMT1, monMT2, nDays, frequencyN, afternoonShed, request);
                                if (!"-------".equals(monAT1) && !"-------".equals(monAT2))
                                    {
                                    validationErrorFlagMon = Validator.validateNoonTime(monAT1, monAT2, monAT1, monAT2, monMT1, monMT2, request);
                                    // if validation error true, return user associate scheduler
                                    if (validationErrorFlagMon == true)
                                        {
                                        request.setAttribute("validationErrorFlagMon", validationErrorFlagMon);
                                        }
                                    else
                                        {
                                        afternoonShed = true;
                                        boolean monScheduleA = CalendarUtil.processScheduleN(associateId, dayString,
                                                frDateN, toDateN, monAT1, monAT2, nDays, frequencyN, afternoonShed, request);
                                        }
                                    }
                                }
                            }
                        else if (dayString.equalsIgnoreCase("tue"))
                            {
                            // validate user entry
                            boolean validationErrorFlagTue;
                            validationErrorFlagTue = Validator.validateSchedule(tueMT1, tueMT2, frDateN,
                                    tueAT1, tueAT2, nDays, daysFlag, mainSchedFlag, toDateN, request);

                            // if validation error true, return user associate scheduler
                            if (validationErrorFlagTue == true)
                                {
                                request.setAttribute("validationErrorFlagTue", validationErrorFlagTue);
                                }
                            else
                                {
                                afternoonShed = false;
                                boolean tueScheduleM = CalendarUtil.processScheduleN(associateId, dayString,
                                        frDateN, toDateN, tueMT1, tueMT2, nDays, frequencyN, afternoonShed, request);
                                if (!"-------".equals(tueAT1) && !"-------".equals(tueAT2))
                                    {
                                    validationErrorFlagTue = Validator.validateNoonTime(tueAT1, tueAT2, tueAT1, tueAT2, tueMT1, tueMT2, request);
                                    // if validation error true, return user associate scheduler
                                    if (validationErrorFlagTue == true)
                                        {
                                        request.setAttribute("validationErrorFlagTue", validationErrorFlagTue);
                                        }
                                    else
                                        {
                                        afternoonShed = true;
                                        boolean tueScheduleA = CalendarUtil.processScheduleN(associateId, dayString,
                                                frDateN, toDateN, tueAT1, tueAT2, nDays, frequencyN, afternoonShed, request);
                                        }
                                    }
                                }
                            }
                        else if (dayString.equalsIgnoreCase("wed"))
                            {
                            // validate user entry
                            boolean validationErrorFlagWed;
                            validationErrorFlagWed = Validator.validateSchedule(wedMT1, wedMT2, frDateN,
                                    wedAT1, wedAT2, nDays, daysFlag, mainSchedFlag, toDateN, request);

                            // if validation error found, return user associate scheduler
                            if (validationErrorFlagWed == true)
                                {
                                request.setAttribute("validationErrorFlagWed", validationErrorFlagWed);
                                }
                            else
                                {
                                afternoonShed = false;
                                boolean wedScheduleM = CalendarUtil.processScheduleN(associateId, dayString,
                                        frDateN, toDateN, wedMT1, wedMT2, nDays, frequencyN, afternoonShed, request);
                                if (!"-------".equals(wedAT1) && !"-------".equals(wedAT2))
                                    {
                                    validationErrorFlagWed = Validator.validateNoonTime(wedAT1, wedAT2, wedAT1, wedAT2, wedMT1, wedMT2, request);
                                    // if validation error found, return user associate scheduler
                                    if (validationErrorFlagWed == true)
                                        {
                                        request.setAttribute("validationErrorFlagWed", validationErrorFlagWed);
                                        }
                                    else
                                        {
                                        afternoonShed = true;
                                        boolean wedScheduleA = CalendarUtil.processScheduleN(associateId, dayString,
                                                frDateN, toDateN, wedAT1, wedAT2, nDays, frequencyN, afternoonShed, request);
                                        }
                                    }
                                }
                            }
                        else if (dayString.equalsIgnoreCase("thu"))
                            {
                            // validate user entry
                            boolean validationErrorFlagThu;
                            validationErrorFlagThu = Validator.validateSchedule(thuMT1, thuMT2, frDateN,
                                    thuAT1, thuAT2, nDays, daysFlag, mainSchedFlag, toDateN, request);

                            // if validation error found, return user associate scheduler
                            if (validationErrorFlagThu == true)
                                {
                                request.setAttribute("validationErrorFlagThu", validationErrorFlagThu);
                                }
                            else
                                {
                                afternoonShed = false;
                                boolean thuScheduleM = CalendarUtil.processScheduleN(associateId, dayString,
                                        frDateN, toDateN, thuMT1, thuMT2, nDays, frequencyN, afternoonShed, request);
                                if (!"-------".equals(thuAT1) && !"-------".equals(thuAT2))
                                    {
                                    validationErrorFlagThu = Validator.validateNoonTime(thuAT1, thuAT2, thuAT1, thuAT2, thuMT1, thuMT2, request);
                                    // if validation error found, return user associate scheduler
                                    if (validationErrorFlagThu == true)
                                        {
                                        request.setAttribute("validationErrorFlagThu", validationErrorFlagThu);
                                        }
                                    else
                                        {
                                        afternoonShed = true;
                                        boolean thuScheduleA = CalendarUtil.processScheduleN(associateId, dayString,
                                                frDateN, toDateN, thuAT1, thuAT2, nDays, frequencyN, afternoonShed, request);
                                        }
                                    }
                                }
                            }
                        else if (dayString.equalsIgnoreCase("fri"))
                            {
                            // validate user entry
                            boolean validationErrorFlagFri;
                            validationErrorFlagFri = Validator.validateSchedule(friMT1, friMT2, frDateN,
                                    friAT1, friAT2, nDays, daysFlag, mainSchedFlag, toDateN, request);

                            // if validation error found, return user associate scheduler
                            if (validationErrorFlagFri == true)
                                {
                                request.setAttribute("validationErrorFlagFri", validationErrorFlagFri);
                                }
                            else
                                {
                                afternoonShed = false;
                                boolean friScheduleM = CalendarUtil.processScheduleN(associateId, dayString,
                                        frDateN, toDateN, friMT1, friMT2, nDays, frequencyN, afternoonShed, request);
                                if (!"-------".equals(friAT1) && !"-------".equals(friAT2))
                                    {
                                    validationErrorFlagFri = Validator.validateNoonTime(friAT1, friAT2, friAT1, friAT2, friMT1, friMT2, request);
                                    // if validation error found, return user associate scheduler
                                    if (validationErrorFlagFri == true)
                                        {
                                        request.setAttribute("validationErrorFlagFri", validationErrorFlagFri);
                                        }
                                    else
                                        {
                                        afternoonShed = true;
                                        boolean friScheduleA = CalendarUtil.processScheduleN(associateId, dayString,
                                                frDateN, toDateN, friAT1, friAT2, nDays, frequencyN, afternoonShed, request);
                                        }
                                    }
                                }
                            }
                        else if (dayString.equalsIgnoreCase("sat"))
                            {
                            // validate user entry
                            boolean validationErrorFlagSat;
                            validationErrorFlagSat = Validator.validateSchedule(satMT1, satMT2, frDateN,
                                    satAT1, satAT2, nDays, daysFlag, mainSchedFlag, toDateN, request);

                            // if validation error found, return user associate scheduler
                            if (validationErrorFlagSat == true)
                                {
                                request.setAttribute("validationErrorFlagSat", validationErrorFlagSat);
                                }
                            else
                                {
                                afternoonShed = false;
                                boolean satScheduleM = CalendarUtil.processScheduleN(associateId, dayString,
                                        frDateN, toDateN, satMT1, satMT2, nDays, frequencyN, afternoonShed, request);
                                if (!"-------".equals(satAT1) && !"-------".equals(satAT2))
                                    {
                                    validationErrorFlagSat = Validator.validateNoonTime(satAT1, satAT2, satAT1, satAT2, satMT1, satMT2, request);
                                    // if validation error found, return user associate scheduler
                                    if (validationErrorFlagSat == true)
                                        {
                                        request.setAttribute("validationErrorFlagSat", validationErrorFlagSat);
                                        }
                                    else
                                        {
                                        afternoonShed = true;
                                        boolean thuScheduleA = CalendarUtil.processScheduleN(associateId, dayString,
                                                frDateN, toDateN, satAT1, satAT2, nDays, frequencyN, afternoonShed, request);
                                        }
                                    }
                                }
                            }
                        }
                    }
                url = "/associate/scheduler";
                }
            else
                {
                validationDaysErrorFlag = true;
                request.setAttribute("validationDaysErrorFlag", validationDaysErrorFlag);
                request.setAttribute("daysError", daysError);
                url = "/associate/scheduler#tab-mainSchedule";
                }
            // setAttribute for global settings
            request.setAttribute("gloMT1", gloMT1);
            request.setAttribute("gloMT2", gloMT2);
            request.setAttribute("gloAT1", gloAT1);
            request.setAttribute("gloAT2", gloAT2);
            // setAttribute for sunday times
            request.setAttribute("sunMT1", sunMT1);
            request.setAttribute("sunMT2", sunMT2);
            request.setAttribute("sunAT1", sunAT1);
            request.setAttribute("sunAT2", sunAT2);
            // setAttribute for monday times
            request.setAttribute("monMT1", monMT1);
            request.setAttribute("monMT2", monMT2);
            request.setAttribute("monAT1", monAT1);
            request.setAttribute("monAT2", monAT2);
            // setAttribute for tuesday times
            request.setAttribute("tueMT1", tueMT1);
            request.setAttribute("tueMT2", tueMT2);
            request.setAttribute("tueAT1", tueAT1);
            request.setAttribute("tueAT2", tueAT2);
            // setAttribute for wednesday times
            request.setAttribute("wedMT1", wedMT1);
            request.setAttribute("wedMT2", wedMT2);
            request.setAttribute("wedAT1", wedAT1);
            request.setAttribute("wedAT2", wedAT2);
            // setAttribute for thursday times
            request.setAttribute("thuMT1", thuMT1);
            request.setAttribute("thuMT2", thuMT2);
            request.setAttribute("thuAT1", thuAT1);
            request.setAttribute("thuAT2", thuAT2);
            // setAttribute for friday times
            request.setAttribute("friMT1", friMT1);
            request.setAttribute("friMT2", friMT2);
            request.setAttribute("friAT1", friAT1);
            request.setAttribute("friAT2", friAT2);
            // setAttribute for saturday times
            request.setAttribute("satMT1", satMT1);
            request.setAttribute("satMT2", satMT2);
            request.setAttribute("satAT1", satAT1);
            request.setAttribute("satAT2", satAT2);
            // setAttribute for selected requests
            request.setAttribute("frequencyN", frequencyN);
            request.setAttribute("toN", toDateN);
            request.setAttribute("fromN", frDateN);

            request.setAttribute("reloadTab", currentTab);
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
