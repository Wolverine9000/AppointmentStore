/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.controller;

import com.SmsTexting.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static java.lang.Integer.parseInt;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import messages.LogFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import store.business.Associate2;
import store.business.CalendarCustomer;
import store.business.Client;
import store.business.FullCalendar2;
import store.business.SMSAppointmentMessage;
import store.business.SMSBasicMessage;
import store.business.SMSMemberInviteMessage;
import store.business.Services;
import store.data.AssociateDB;
import static store.data.AssociateDB.selectAssociateInfo;
import store.data.CalendarDB;
import store.data.CustomerDB;
import store.data.MessagesDB;
import store.data.ProductDB;
import store.data.SecurityDB;
import store.util.CalendarUtil;
import store.util.DateUtil;
import store.util.MailUtil;
import store.util.PhoneUtil;
import store.util.StringUtil;
import store.util.UserUtil;
import validate.Validator;

/**
 *
 * @author williamdobbs
 */
public class CalendarData
{

    public static ArrayList<FullCalendar2> clientCalendar(String id)
    {
        int clientId = Integer.parseInt(id);

        ArrayList<FullCalendar2> fullCalendar2 = new ArrayList<>();
        ArrayList<CalendarCustomer> c;
        c = CalendarDB.selectClientCalendar(clientId);
        c.stream().map((cc)
                ->
        {
            Associate2 a;
            a = selectAssociateInfo(cc.getAssociateId());
            FullCalendar2 fc = new FullCalendar2();
            fc.setTitle(cc.addName());
            fc.setNotes(cc.getNotes());
            fc.setStart(cc.convertStartTimestamp());
            fc.setEnd(cc.convertEndTimestamp());
            fc.setServiceId(cc.getServiceId());
            fc.setAllDay(cc.getAllDay());
            fc.setFirstName(cc.getFirstName());
            fc.setLastName(cc.getLastName());
            fc.setEmailAddress(cc.getEmailAddress());
            fc.setCustomerId(cc.getCustomerId());
            fc.setEventId(cc.getEventId());
            fc.setAssociateName(a.getFirstName());
            fc.setAssociateId(cc.getAssociateId());
            fc.setTextColor(cc.getTextColor());
            fc.setBackgroundColor(cc.getServiceStatus().getStatusColor());
            fc.setAssociate2(cc.getAssociate2());
            fc.setServiceStatus(cc.getServiceStatus());
            fc.setClient(cc.getClient());
            fc.setServiceTime(cc.getServiceTime());
            fc.setDurationEditable(cc.getDurationEditable());
            fc.setEditable(cc.getEditable());
            fc.setStartSql(cc.getStartSql());
            fc.setEndSql(cc.getEndSql());
            fc.setId(cc.getEventId());

            return fc;
        }).forEach((fc)
                ->
        {
            fullCalendar2.add(fc);
        });
        return fullCalendar2;
    }

    public static Associate2 associateProfile(int associateId)
    {
        Associate2 associate;
        associate = AssociateDB.selectAssociateProfile(associateId);
        associate.setMorningTimeIn(associate.getMorningTimeInStr());
        associate.setMorningTimeOut(associate.getMorningTimeOutStr());
        associate.setAfternoonTimeIn(associate.getAfternoonTimeInStr());
        associate.setAfternoonTimeOut(associate.getAfternoonTimeOutStr());

        return associate;
    }

    public static ArrayList<FullCalendar2> associateCalendar(String id)
    {
        int associateId = Integer.parseInt(id);

        ArrayList<FullCalendar2> fullCalendar2 = new ArrayList<>();
        ArrayList<CalendarCustomer> c;
        c = CalendarDB.selectCalendar(associateId);
        c.stream().map((CalendarCustomer cc) ->
        {
            Associate2 a;
            a = selectAssociateInfo(cc.getAssociateId());
            FullCalendar2 fc = new FullCalendar2();
            fc.setTitle(cc.addName());
            fc.setNotes(cc.getNotes());
            fc.setStart(cc.convertStartTimestamp());
            fc.setEnd(cc.convertEndTimestamp());
            fc.setServiceId(cc.getServiceId());
            fc.setAllDay(cc.getAllDay());
            fc.setFirstName(cc.getFirstName());
            fc.setLastName(cc.getLastName());
            fc.setEmailAddress(cc.getEmailAddress());
            fc.setCustomerId(cc.getCustomerId());
            fc.setEventId(cc.getEventId());
            fc.setAssociateName(a.getFirstName());
            fc.setAssociateId(cc.getAssociateId());
            fc.setTextColor(cc.getTextColor());
            fc.setBackgroundColor(cc.getServiceStatus().getStatusColor());
            fc.setAssociate2(cc.getAssociate2());
            fc.setServiceStatus(cc.getServiceStatus());
            fc.setClient(cc.getClient());
            fc.setServiceTime(cc.getServiceTime());
            fc.setDurationEditable(cc.getDurationEditable());
            fc.setEditable(cc.getEditable());
            fc.setStartSql(cc.getStartSql());
            fc.setEndSql(cc.getEndSql());
            fc.setId(cc.getEventId());
            return fc;
        }).forEachOrdered((fc) ->
        {
            fullCalendar2.add(fc);
        });
        return fullCalendar2;
    }

    public static ArrayList<FullCalendar2> AssociateTimes(String id)
    {
        int associateId = Integer.parseInt(id);

        ArrayList<FullCalendar2> fullCalendar2 = new ArrayList<>();
        ArrayList<Associate2> a;
        a = AssociateDB.selectAssociateAvailableTimes(associateId);

        a.stream().map((aa) ->
        {
            FullCalendar2 fc = new FullCalendar2();
            fc.setTitle(aa.getFirstName());
            fc.setStart(aa.getTimestampStr());
            fc.setEnd(aa.getEndTimestampStr());
            fc.setAllDay(Boolean.FALSE);
            fc.setIsAssociate(true);
            fc.setEditable(Boolean.FALSE); // This determines if the events can be dragged and resized.
            fc.setEventId(aa.getAssociateAvailabilityId());
            fc.setDurationEditable(Boolean.FALSE); //Allow events' durations to be editable through resizing
            fc.setAssociateName(aa.getFirstName());
            fc.setAssociateLastName(aa.getLastName());
            fc.setAssociate2(selectAssociateInfo(aa.getId()));
            return fc;
        }).map((fc) ->
        {
            fc.setUserType("associate");
            return fc;
        }).forEachOrdered((fc) ->
        {
            fullCalendar2.add(fc);
        });
        return fullCalendar2;
    }

    public static ArrayList<FullCalendar2> clientData()
    {
        ArrayList<FullCalendar2> fullCalendar2 = new ArrayList<>();
        ArrayList<FullCalendar2> c;
        c = CalendarDB.selectCalendarAll();
        c.stream().map((FullCalendar2 cc) ->
        {
            FullCalendar2 fc = new FullCalendar2();
            fc.setTitle(cc.addName());
            fc.setNotes(cc.getNotes());
            fc.setStart(cc.convertStartTimestamp());
            fc.setEnd(cc.convertEndTimestamp());
            fc.setServiceId(cc.getServiceId());
            fc.setAllDay(cc.getAllDay());
            fc.setFirstName(cc.getFirstName());
            fc.setLastName(cc.getLastName());
            fc.setEmailAddress(cc.getEmailAddress());
            fc.setCustomerId(cc.getCustomerId());
            fc.setEventId(cc.getEventId());
            fc.setAssociateName(cc.getAssociate2().getFirstName());
            fc.setAssociateId(cc.getAssociateId());
            fc.setTextColor(cc.getTextColor());
            fc.setBackgroundColor(cc.getServiceStatus().getStatusColor());
            fc.setAssociate2(cc.getAssociate2());
            fc.setServiceStatus(cc.getServiceStatus());
            fc.setClient(cc.getClient());
            fc.setServiceTime(cc.getServiceTime());
            fc.setDurationEditable(cc.getDurationEditable());
            fc.setEditable(cc.getEditable());
            fc.setStartSql(cc.getStartSql());
            fc.setEndSql(cc.getEndSql());
            fc.setId(cc.getEventId());
            return fc;
        }).forEach((fc)
                ->
        {
            fullCalendar2.add(fc);
        });
        return fullCalendar2;
    }

    public static ArrayList<FullCalendar2> clientInfo(String key, String title)
    {
        ArrayList<FullCalendar2> fullCalendar2 = new ArrayList<>();
        ArrayList<CalendarCustomer> c = null;
        if ("getList".equals(key))
        {
            c = CustomerDB.selectClientsAll(title);
        }
        else
        {
            c = CustomerDB.selectClientsAll();
        }
        for (CalendarCustomer cc : c)
        {
            FullCalendar2 fc = new FullCalendar2();
            fc.setFirstName(cc.getFirstName());
            fc.setLastName(cc.getLastName());
            fc.setEmailAddress(cc.getEmailAddress());
            fc.setCustomerId(cc.getCustomerId());
            fc.setAssociateName(cc.getAssociate2().getFirstName());
            fc.setAssociateId(cc.getAssociateId());
            fc.setAssociate2(cc.getAssociate2());
            fc.setClient(cc.getClient());
            fc.getMemberLevels();
            fullCalendar2.add(fc);
        }
        return fullCalendar2;
    }

// Get sms messages
//    public static ArrayList<SMSAppointmentMessage> messages(String key, String idnumber)
//    {
//        ArrayList<SMSAppointmentMessage> messages;
//        int id = Integer.parseInt(idnumber);
//        messages = MessagesDB.sentMessages2(id);
//
//        return messages;
//    }
    // Get sms   messages
    public static ArrayList<SMSAppointmentMessage> messages(String key1, String key2, String key3, String userId)
    {
        ArrayList<SMSAppointmentMessage> messages;
        int id = Integer.parseInt(userId);
        int start = Integer.parseInt(key1);
        int limit = Integer.parseInt(key2);

        messages = MessagesDB.sentMessages2(id, start, limit);

        return messages;
    }

// Get sms invite messages
    public static ArrayList<SMSMemberInviteMessage> inviteMessages(String key, String idnumber, String altKey)
    {
        ArrayList<SMSMemberInviteMessage> messages;
        int id = Integer.parseInt(idnumber);
        int altkeyInt = Integer.parseInt(altKey);
        messages = MessagesDB.selectRecentAssociateRequests(id, altkeyInt);

        return messages;
    }

// Get sms selected invite messages
    public static SMSMemberInviteMessage selectInviteMessage(String id, String json)
    {

        SMSMemberInviteMessage m = new SMSMemberInviteMessage();
        JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(json);
        m.setIsMessageInvite(jsonObj.getBoolean("isMessageInvite"));
        m.setStampToSend(jsonObj.getString("timeToSendInteger"));
        int n = parseInt(id);

        m = MessagesDB.selectMsgInviteRequest(m, n);

        return m;
    }

    public static ArrayList<SMSMemberInviteMessage> isClientOfAssociate(String json, String title) // Get sms messages
    {

        ArrayList<SMSMemberInviteMessage> smsObjArray = new ArrayList();

        Associate2 a = new Associate2();
        JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(json);
        JSONArray jsonarr = jsonObj.getJSONArray("phoneNumbers");
        ArrayList<String> strPhArr = StringUtil.convertArrayToList(jsonarr);
        a.setId(Integer.parseInt(jsonObj.getString("associateId")));
        int associateId = a.getId();
        for (int i = 0; i < strPhArr.size(); i++)
        {
            SMSMemberInviteMessage m;
            Client c;
            m = new SMSMemberInviteMessage();
            String n = strPhArr.get(i);
            c = CustomerDB.isClientAndActive(n, associateId);
            if (c != null)
            {
                m.setClient(c);
                m.setIsClientOfAssociate(true);
            }
            else
            {
                Client nc = new Client();
                nc.setMobilePhone(n);
                m.setClient(nc);
                m.setIsClientOfAssociate(false);
            }
            boolean add = smsObjArray.add(m);
        }
        return smsObjArray;
    }

    public static boolean associateInfo(String json, HttpServletRequest request, String actionTitle)
    {
        boolean errorFlag = false;
        Gson gson = new Gson();

        Type collectionType = new TypeToken<Collection<Associate2>>()
        {
        }.getType();
        Collection<Associate2> associateArray = gson.fromJson(json, collectionType);

        for (Iterator<Associate2> it = associateArray.iterator(); it.hasNext();)
        {
            Associate2 associate = it.next();
            if (null != actionTitle)
            {
                switch (actionTitle)
                {
                    case "updateAssociate":
                        errorFlag = AssociateDB.updateAssociateInfo(associate);
                        break;
                    case "updateAssoPwd":
                        errorFlag = AssociateDB.updateAssociatePassword(associate);
                        break;
                    case "updateDefaultCalendarView":
                        errorFlag = AssociateDB.updateAssociateDefaultView(associate);
                        break;
                    case "updateDefaultCalendarTimes":
                        associate.setMorningTimeInSql(DateUtil.convertDate(associate.getMorningTimeIn()));
                        associate.setMorningTimeOutSql(DateUtil.convertDate(associate.getMorningTimeOut()));
                        associate.setAfternoonTimeInSql(DateUtil.convertDate(associate.getAfternoonTimeIn()));
                        associate.setAfternoonTimeOutSql(DateUtil.convertDate(associate.getAfternoonTimeOut()));
                        errorFlag = AssociateDB.updateAssociateDefaultTimes(associate);
                        break;
                    case "updateMemberSettings":
                        errorFlag = AssociateDB.updateMemberSettings(associate);
                        break;
                    default:
                        errorFlag = true;
                }
            }
        }
        return errorFlag;
    }

    public static boolean associateAlerts(String json, HttpServletRequest request)
    {
        boolean errorFlag = false;
        Gson gson = new Gson();

        Type collectionType = new TypeToken<Collection<Associate2>>()
        {
        }.getType();
        Collection<Associate2> associateArray = gson.fromJson(json, collectionType);

        for (Associate2 associate : associateArray)
        {
            errorFlag = AssociateDB.updateAssociateAlerts(associate);
        }
        return errorFlag;
    }

    public static boolean updateSecrityQuestions(String data)
    {
        boolean errorFlag = false;
        JSONObject response1 = (JSONObject) JSONSerializer.toJSON(data);
        JSONArray getQAArray;
        getQAArray = response1.getJSONArray("challengeQandA");
        JSONObject user = response1.getJSONObject("user");
        int userId = Integer.parseInt(user.getString("id"));
        String email = user.getString("emailAddress");
        SecurityDB.deleteUserQandA(userId); // delete any existing security questions and answers
        for (int i = 0; i < getQAArray.size(); i++)
        {
            try
            {
                JSONObject objectInArray = getQAArray.getJSONObject(i);
                int questionId = Integer.parseInt(objectInArray.getString("q"));
                String answer = objectInArray.getString("a");
                SecurityDB.insertChallengeQandA(questionId, userId, email, answer);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(CalendarData.class.getName()).log(Level.SEVERE, null, ex);
                LogFile.databaseError("CalendarData SecurityDB updateSecrityQuestions error ", ex.getMessage(), ex.toString());
                errorFlag = true;
            }
        }
        return errorFlag;
    }

    public static boolean postCalendarData(String json, HttpServletRequest request, Associate2 associateSession)
    {
        boolean errorFlag = false;
        boolean dataInsert;

        Gson gson = new Gson();

        // create the type for the collection. In this case define that the collection is of type Dataset
        java.lang.reflect.Type datasetListType = new TypeToken<Collection<FullCalendar2>>()
        {
        }.getType();

        List<FullCalendar2> fcArray = gson.fromJson(json, datasetListType);
        for (FullCalendar2 fc : fcArray)
        {
            String t = fc.getTitle();
            // strip client name from calendar title; stripName function
            // client-side javascript should have already done this
            fc.setTitle(fc.stripName(t));
            // convert start date-time strings to Date object
            Date startDate = DateUtil.convertDateSched(fc.getStart());
            Date endDate = DateUtil.convertDateSched(fc.getEnd());
            // get sql start and end times
            Time sqlStartTime = new java.sql.Time(startDate.getTime());
            Time sqlEndTime = new java.sql.Time(endDate.getTime());
            // get sql start date and end date
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
            // get Calendar object instances
            Calendar calStart = Calendar.getInstance();
            Calendar calEnd = Calendar.getInstance();
            // set Calendar objects from Date objects
            calStart.setTime(startDate);
            calEnd.setTime(endDate);
            // get integers for start date and time calendar objects
            int startMonth = calStart.get(Calendar.MONTH);
            int startDayOfWeek = calStart.get(Calendar.DAY_OF_WEEK);
            int calStartHour = calStart.get(Calendar.HOUR_OF_DAY);
            int calStartMinute = calStart.get(Calendar.MINUTE);
            int startDayOfMonth = calStart.get(Calendar.DAY_OF_MONTH);
            int startYear = calStart.get(Calendar.YEAR);
            // get end date and time calendar object integers
            int endMonth = calEnd.get(Calendar.MONTH);
            int endDayOfWeek = calEnd.get(Calendar.DAY_OF_WEEK);
            int calEndHour = calEnd.get(Calendar.HOUR_OF_DAY);
            int calEndMinute = calEnd.get(Calendar.MINUTE);
            int endDayOfMonth = calEnd.get(Calendar.DAY_OF_MONTH);
            int endYear = calEnd.get(Calendar.YEAR);
            // convert date-time strings to sql date-time objects
            fc.setStartSql(DateUtil.convertDate(fc.getStart())); // convert start time to sql timestamp
            fc.setEndSql(DateUtil.convertDate(fc.getEnd())); // convert end time to sql timestamp

            boolean sendEmailApptAlert;
            boolean sendSmsApptAlert;
            boolean notifyClient;

            // boolean appointment notification settings for client and associate
            notifyClient = fc.getNotifyClient();

            boolean emailApptAsso = fc.getAssociate2().isEmailApptAlerts();
            boolean smsApptAsso = fc.getAssociate2().isSmsApptAlerts();

            switch (fc.getAction())
            {
                case "add":
                    boolean eventExists;
                    if (fc.getAllDay())
                    { // if event is an all day calendar event, check to see if it already exists in the database
                        eventExists = CalendarDB.eventExists(fc.getEventId());
                    }
                    else
                    { // check to see if timed calendar event exists
                        eventExists = CalendarDB.eventExists(fc.getStartSql(), fc.getEventId());
                    }
                    if (eventExists)
                    {
                        // if event exists, update the calendar event in the database
                        int updateCalendar = CalendarDB.updateCalendar(fc, sqlStartTime, sqlStartDate, sqlEndDate, sqlEndTime);
                        if (updateCalendar == 0)
                        {
                            errorFlag = true;
                        }
                        // are there any events in array to cancel
                        if (fc.getCancelEvts() != null)
                        {
                            int svcStatusId = CalendarDB.serviceStatus("Cancelled"); // grab the service status integer for service status - -> cancelled?
                            // loop through array of event id numbers  and change the status to cancelled
                            fc.getCancelEvts().stream().forEach((cancelEvt)
                                    ->
                            {
                                CalendarDB.updateStatus(cancelEvt, svcStatusId);
                            });
                        }
                    }
                    else if (!eventExists) // if event does NOT exist
                    {
                        if (fc.getNewClient() == true)
                        {
                            boolean userInfoError;
                            userInfoError = Validator.validateFullCalUser(fc, request);
                            if (true == userInfoError)
                            {
                                // TODO enter return info for inValid user info
                            }
                            int clientId = UserUtil.processUserId(fc); // process user
                            if (clientId > 0)
                            {
                                fc.setCustomerId(clientId);
                                fc.getClient().setId(clientId);
                            }
                            else
                            {
                                errorFlag = true;
                            }
                        }
                        // insert new calendar event into database
                        fc.setEventId(CalendarDB.insertAppointment(fc, sqlStartDate, sqlEndDate, startMonth, startDayOfWeek,
                                startDayOfMonth, startYear, endMonth, endDayOfWeek, endDayOfMonth, endYear, sqlStartTime,
                                sqlEndTime, calStartHour, calStartMinute, calEndHour, calEndMinute));

                        if (fc.getEventId() != 0)
                        {
                            // remove associate available date and time
                            AssociateDB.deleteAvailability(fc.getAssociateId(), fc.getStartSql(), fc.getEndSql());

                            Date start = calStart.getTime();
                            String dateStr = DateUtil.formatDate(start);
                            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
                            DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT);
                            String dateString = df.format(start);
                            String timeString = tf.format(start);
                            // convert current date and time to long string of numbers
                            String nowTimeUnix = DateUtil.dateNowLong();

                            Random random = new Random();
                            int regCode;
                            regCode = random.nextInt(999999);

                            String regCodeStr = String.valueOf(regCode);

                            String eventIdStr = String.valueOf(fc.getEventId());

                            Associate2 a = fc.getAssociate2();
                            Services s = ProductDB.selectService(fc.getServiceId());

                            boolean smsSent = true;
                            SMSAppointmentMessage m = new SMSAppointmentMessage();
                            m.setAssociateSender(fc.getAssociate2());
                            m.setSentById(associateSession.getId());
                            m.setClient(fc.getClient());
                            m.setMessageTypeID("1");
                            m.setStampToSend(nowTimeUnix);

                            if (notifyClient)
                            {
                                sendEmailApptAlert = fc.getClient().isEmailApptAlerts();
                                sendSmsApptAlert = fc.getClient().isSmsApptAlerts();
                                // TODO code new appointment email/sms message
                                String message = fc.getFirstName() + ", the service " + fc.getTitle() + " with " + fc.getAssociateName() + " has been scheduled on " + dateStr + " at " + timeString + "."
                                        + " Event:# " + eventIdStr + " Code: " + regCode;
//                                String message = fc.getFirstName() + " " + dateStr + " at " + timeString + "."
//                                        + "http://71.8.84.224:8081/AppointmentStore/IncomingMessage?phone=" + mobileStr2 + "&reg=" + regCodeStr;
                                if (sendSmsApptAlert)
                                {

                                    try
                                    {
                                        // sms text message to client
                                        ArrayList<String> mobilePhArr = new ArrayList<>();
                                        String mobileStr2 = PhoneUtil.stripNonDigits2(fc.getMobilePhone());
                                        mobilePhArr.add(mobileStr2);
                                        String clientMobile = StringUtil.convStrArray(mobilePhArr);
                                        m.setPhoneNumStrArr(clientMobile);
                                        m.setPhoneArray(mobilePhArr);
                                        m.setSubject(m.subjectDatabase(4));
                                        m.setMessage(message);
                                        smsSent = SendingSMSMessagesJSON.sendSMSMessage(m);
                                        if (smsSent == false)
                                        {
                                            errorFlag = true;
                                        }
                                    }
                                    catch (Exception ex)
                                    {
                                        LogFile.smsError("CalendarData postCalendarData Client SMS", ex.toString(), m);
                                        errorFlag = true;
                                    }

                                }
                                if (sendEmailApptAlert)
                                {
                                    boolean sendConfirmation = MailUtil.sendConfirmation("Appointment Confirmation", dateStr, timeString, fc, a, s, regCode);
                                    if (sendConfirmation == false)
                                    {
                                        errorFlag = true;
                                    }
                                }
                            }
                            if (emailApptAsso)
                            {
                                boolean sendConfirmation = MailUtil.sendAssociateConfirm("Appointment Confirmation", dateStr, timeString, fc, a, s, regCode);
                                if (sendConfirmation == false)
                                {
                                    errorFlag = true;
                                }
                            }
                            if (smsApptAsso)
                            {
                                String message = "Your client " + fc.getFirstName() + " has scheduled the service " + fc.getTitle() + " on " + dateStr + " at " + timeString + "."
                                        + " Event# " + eventIdStr;
                                try
                                {
                                    ArrayList<String> mobilePhAssocArr = new ArrayList<>();
                                    String mobileStrAsso = PhoneUtil.stripNonDigits2(fc.getAssociate2().getMobilePhone());
                                    mobilePhAssocArr.add(mobileStrAsso);
                                    String associateMobile = StringUtil.convStrArray(mobilePhAssocArr);
                                    m.setMessage(message);
                                    m.setPhoneNumStrArr(associateMobile);
                                    m.setPhoneArray(mobilePhAssocArr);
                                    m.setSubject(m.getAssociateSender().getFirstName());
                                    smsSent = SendingSMSMessagesJSON.sendSMSMessage(m);
                                }
                                catch (Exception ex)
                                {
                                    LogFile.smsError("CalendarData postCalendarData Associate SMS", ex.toString(), m);
                                    errorFlag = true;
                                }
                                if (smsSent == false)
                                {
                                    errorFlag = true;
                                }
                            }
                        }
                        else
                        {
                            dataInsert = false;
                            errorFlag = true;
                            request.setAttribute("dataInsert", dataInsert);
                        }
                    }
                    break;
                case "delete":
                    boolean eventDeleted;
                    eventDeleted = CalendarDB.deleteAppointment(fc.getEventId());
                    if (eventDeleted && fc.getNotifyClient())
                    {
                        // TODO code client appointment cancellation notification email/sms
                    }
                    if (eventDeleted && fc.getRestoreTime() && !fc.getAllDay())
                    {
                        boolean isAssoDateAvailable = AssociateDB.isAssociateAvailability(fc.getAssociateId(), sqlStartDate);
                        if (!isAssoDateAvailable) // if Associate Date not available, restore Associate time frame
                        {
                            int insertAvailDate = AssociateDB.insertAvailableDate(fc.getAssociateId(), sqlStartDate);
                            if (insertAvailDate == 0)
                            {
                                errorFlag = true;
                            }
                        }
                        // convert frequency to multiplier number
                        int calculateTimes = CalendarUtil.calculateTimes(15);
                        // calculate the number of times to insert into database using for-loop
                        int totalTime = (calEndHour - calStartHour) * calculateTimes;
                        // get total loops adjustment conversion
                        totalTime = CalendarUtil.totalTimes(calStartMinute, calEndMinute, 15, totalTime);
                        for (int j = 0; j < totalTime; j++)
                        {
                            Date newTime = calStart.getTime();
                            fc.setStartSql(new java.sql.Timestamp(newTime.getTime()));
                            java.sql.Time sqlStTime = new java.sql.Time(newTime.getTime());
                            // sql end timestamp conversion
                            Calendar et = (Calendar) calStart.clone(); // calStart time calendar instance
                            et.add(Calendar.MINUTE, 15); // TODO change frequency hard-coded minutes to pull from associate database setting
                            Date end = DateUtil.convertCal(calStart, et); // use calendar date and time to convert to end-time Date object
                            fc.setEndSql(new java.sql.Timestamp(end.getTime())); // create sql end-time sql timestamp
                            // check if time-slot is not avialable/open
                            boolean isAssoTimeAvailable = AssociateDB.isAssociateTimeAvailable(fc.getAssociateId(), sqlStartDate, sqlStartTime);
                            if (!isAssoTimeAvailable)
                            {
                                // insert new scheduled date, time, start timestamp, end timestamp and associate id into database
                                int insertSchedule = AssociateDB.insertAssociateSchedule(sqlStartDate, sqlStTime, fc.getAssociateId(), fc.getStartSql(), fc.getEndSql());
                            }
                            // add appointment interval frequency time to scheduled date
                            calStart.add(Calendar.MINUTE, 15); // add frequency time
                        }
                    }
                case "delAssociateTimeSlot":
                    int delAssoicateTimeSlot = AssociateDB.delAssoicateTimeSlot(fc.getEventId());
                    int associateId = fc.getAssociate2().getId();
                    String associateFirstName = fc.getAssociate2().getFirstName();
                    String associateLastName = fc.getAssociate2().getLastName();
                    String action = fc.getAction();

                    if (delAssoicateTimeSlot != 0)
                    {
                        // log event
                        boolean logAction = LogFile.associateLog("FullCalPostServlet postCalendarData SUCCESS", "associateFirstName:"
                                + associateFirstName + " associateLastName:"
                                + associateLastName + " AssociateID:" + associateId
                                + " Event ID: " + fc.getEventId()
                                + " Start Time slot deleted: " + startDate + " End Time slot deleted:" + endDate, " action:" + action);
                    }
                    if (delAssoicateTimeSlot == 0)
                    {
                        //errorFlag = true;
                    }
                    break;
                case "updateStatus":
                    int updateStatus = CalendarDB.updateStatus(fc);
                    if (updateStatus == 0)
                    {
                        //errorFlag = true;
                    }
                    break;
            }
            boolean logAction = LogFile.associateLog("FullCalPostServlet postCalendarData", "client:" + fc.getFirstName() + " clientID:" + fc.getCustomerId() + " Event ID: " + fc.getEventId()
                    + " service:" + fc.getTitle() + " svcDate:" + fc.getStart(), " action:" + fc.getAction());
        }
        return errorFlag;
    }

    public static boolean postClientData(String json, HttpServletRequest request, String title, Associate2 associate)
    {
        boolean errorFlag = false;
        boolean smsSent;

        Gson gson = new Gson();
        // create the type for the collection. In this case define that the collection is of type Dataset
        java.lang.reflect.Type datasetListType = new TypeToken<Collection<FullCalendar2>>()
        {
        }.getType();

        List<FullCalendar2> fcArray = gson.fromJson(json, datasetListType);

        if (title.startsWith("sendMessage"))
        {
            int msgStart = title.indexOf("_", 1);
            int msgEnd = title.indexOf("_subject");
            int subjectStart = title.indexOf("_", msgEnd);
            msgStart++;
            subjectStart++;
            String msg = title.substring(msgStart, msgEnd); // extract message
            String subjectStr = title.substring(subjectStart);
            int subjectIndex = subjectStr.indexOf("_", 1);
            subjectIndex++;
            String subject = subjectStr.substring(subjectIndex); // extract subject
            String mobileStr;
            String emailStr;
            String emailBccStr;
            ArrayList<String> mobilePhArr = new ArrayList<>();
            ArrayList<String> emailArr = new ArrayList<>();
            ArrayList<Client> c = new ArrayList<>();
            // create Message Object
            SMSBasicMessage m = new SMSBasicMessage();
            m.setMessage(msg);
            m.setSubject(subject);
            m.setMessageTypeID("1");
            m.setGroupMessage(true);

            FullCalendar2 fc1 = fcArray.get(0); // get fullcalendar2 object at the zero index

            for (FullCalendar2 fc : fcArray) // loop through array of users
            {
                m.setStampToSend(fc.getTimeToSend()); // get long unix format timeToSend string from fc object
                m.setAssociateSender(fc.getAssociate2());
                m.setSentById(associate.getId());
                m.setClient(fc.getClient());
                c.add(fc.getClient());

                if (fc.getClient().isSmsAdAlerts() == true) // does user allow sms ad messages
                {
                    mobileStr = PhoneUtil.stripNonDigits2(fc.getClient().getMobilePhone());
                    mobilePhArr.add(mobileStr);
                    String clientMobile = StringUtil.convStrArray(mobilePhArr);
                    m.setPhoneNumStrArr(clientMobile);
                    m.setPhoneArray(mobilePhArr);
                }
                if (fc.getClient().isEmailAdAlerts() == true) // does user allow email ad messages
                {
                    emailStr = fc.getClient().getEmail();
                    emailArr.add(emailStr);
                }
                m.setClients(c);
            }
            emailBccStr = StringUtil.emailAddrToStr(emailArr); // convert email address array to strings

            MailUtil.sendShortMsg(fc1, subject, msg, emailBccStr);

            LogFile.generalLog("CalendarData postClientData quick sms " + fc1.getAssociateName() + " " + fc1.getAssociateName() + " "
                    + msg, " - email addresses " + emailBccStr);

            if (m.getPhoneNumStrArr() != null)
            {
                try
                {
                    smsSent = SendingSMSMessagesJSON.sendSMSMessage(m);
                    if (smsSent == false)
                    {
                        errorFlag = true;
                    }
                }
                catch (Exception ex)
                {
                    LogFile.smsError("CalendarData postClientData quick sms", ex.toString(), m);
                    errorFlag = true;
                }
            }
        }
        else if (title.equals("deactivateClients"))
        {
            CustomerDB.updateActiveStatus(fcArray);
        }
        else if (title.equals("invite"))
        {

        }
        else
        {
            for (FullCalendar2 fc : fcArray)
            {

                switch (fc.getAction())
                {
                    case "updateMemberLevel":
                        int updateMemberLevel = CustomerDB.updateMemberLevel(fc.getCustomerId(), fc.getClient());
                        if (updateMemberLevel == 0)
                        {
                            errorFlag = true;
                        }
                        break;
                    case "updateNormalSvc":
                        int updateNormalSvc = CustomerDB.updateNormalSvc(fc.getCustomerId(), fc.getClient());
                        if (updateNormalSvc == 0)
                        {
                            errorFlag = true;
                        }
                        break;
                    case "updateClient":
                        int uptateInfo = CustomerDB.updateClient(fc.getCustomerId(), fc.getClient());
                        CustomerDB.updateClientAlerts(fc.getCustomerId(), fc.getClient());
                        if (uptateInfo == 0)
                        {
                            errorFlag = true;
                        }
                        break;
                    case "add":
                        int clientId = UserUtil.processUserId(fc); // process user
                        if (clientId > 0)
                        {
                            fc.setCustomerId(clientId);
                        }
                        else
                        {
                            errorFlag = true;
                        }
                        break;
                }
                boolean logAction = LogFile.associateLog("FullCalPostServlet postClientData", "client:" + fc.getFirstName() + " clientID:" + fc.getCustomerId() + " Event ID: n/a"
                        + " svcDate: n/a", " action:" + fc.getAction());
            }
        }
        return errorFlag;
    }

    public static boolean smsInviteRequest(String json, HttpServletRequest request, String title, Associate2 associate)
    {
        boolean errorFlag = false;
        boolean smsSent;

        SMSMemberInviteMessage m = new SMSMemberInviteMessage();
        JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(json);

        m.setPhoneNumbers(jsonObj.getJSONArray("phoneNumbers"));
        m.setPhoneArray(m.getConvertedPhoneNumersString());
        m.setPhoneNumStrArr(StringUtil.convStrArray(m.getPhoneArray()));
        m.setStampToSend(jsonObj.getString("stampToSend"));
        m.setIsMessageInvite(jsonObj.getBoolean("isMessageInvite"));
        m.setMessageTypeID("1");
        m.setAssociateSender(associate);
        m.setSentById(associate.getId());
        String f = m.getAssociateSender().getFirstName();
        String l = m.getAssociateSender().getLastName();
        m.setMessage(f + " " + l + " " + m.messageDatabase(0));
        m.setSubject(m.subjectDatabase(4));
        try
        {
            smsSent = SendingSMSMessagesJSON.sendSMSMessage(m);
            if (smsSent == false)
            {
                errorFlag = true;
            }
            else if (smsSent == true)
            {
                m.getConvertedPhoneNumersString().forEach((String mobilePh) ->
                {
                    MessagesDB.insertInvitationRequests(mobilePh, m.getAssociateSender().getId(), m.getSqlTimestamp(), m.getTimeToSendInteger());
                });
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(CalendarData.class.getName()).log(Level.SEVERE, null, ex);
            LogFile.smsError("CalendarData smsInviteRequest", ex.toString(), m);
            errorFlag = true;
        }

        return errorFlag;
    }

}
