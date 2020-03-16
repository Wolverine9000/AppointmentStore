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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import store.business.Client;
import store.business.FullCalendar2;
import store.business.SMSAppointmentCommunicator;
import store.business.SMSBasicCommunicator;
import store.business.SMSMemberInviteCommunicator;
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

    boolean smsSent = false;

    public static ArrayList<FullCalendar2> clientCalendar(String id)
    {
        int clientId = Integer.parseInt(id);

        ArrayList<FullCalendar2> fullCalendar2 = new ArrayList<>();
        ArrayList<FullCalendar2> c;
        c = CalendarDB.selectClientCalendar(clientId);
        c.stream().map((FullCalendar2 cc) ->
        {
            Associate2 a;
            a = selectAssociateInfo(cc.getAssociate2().getId());
            FullCalendar2 fc = new FullCalendar2();
            fc.setTitle(cc.addName());
            fc.setNotes(cc.getNotes());
            fc.setStart(cc.convertStartTimestamp());
            fc.setEnd(cc.convertEndTimestamp());
            fc.getServices().setServiceId(cc.getServices().getServiceId());
            fc.setAllDay(cc.isAllDay());
            fc.getAssociate2().setFirstName(a.getFirstName());
            fc.getAssociate2().setLastName(a.getLastName());
            fc.getAssociate2().setEmail(a.getEmail());
            fc.setCustomerId(cc.getCustomerId());
            fc.setEventId(cc.getEventId());
            fc.getAssociate2().setFirstName(a.getFirstName());
            fc.getAssociate2().setId(a.getId());
            fc.setTextColor(cc.getTextColor());
            fc.setBackgroundColor(cc.getBackgroundColor());
            fc.setAssociate2(cc.getAssociate2());
//            fc.getServices().getServiceStatus().getStatusColor()(id);
//            tStatusColor()
//            );
            fc.setClient(cc.getClient());
            fc.setServiceTime(cc.getServiceTime());
            fc.setDurationEditable(cc.isDurationEditable());
            fc.setEditable(cc.isEditable());
            fc.setStartSql(cc.getStartSql());
            fc.setEndSql(cc.getEndSql());
            fc.setEventId(cc.getEventId());

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
        ArrayList<FullCalendar2> c;
        c = CalendarDB.selectCalendar(associateId);
        c.stream().map((FullCalendar2 cc) ->
        {
            Associate2 a;
            a = selectAssociateInfo(cc.getAssociate2().getId());
            FullCalendar2 fc = new FullCalendar2();
            fc.setTitle(cc.addName());
            fc.setNotes(cc.getNotes());
            fc.setStart(cc.convertStartTimestamp());
            fc.setEnd(cc.convertEndTimestamp());
            fc.getServices().setServiceId(cc.getServices().getServiceId());
            fc.setAllDay(cc.isAllDay());
            fc.setCustomerId(cc.getCustomerId());
            fc.setEventId(cc.getEventId());
            fc.getAssociate2().setId(cc.getAssociate2().getId());
            fc.setTextColor(cc.getTextColor());
            fc.setBackgroundColor(cc.getServices().getServiceStatus().getStatusColor());
            fc.setAssociate2(cc.getAssociate2());
            fc.setServiceStatus(cc.getServiceStatus());
            fc.setClient(cc.getClient());
            fc.setServiceTime(cc.getServiceTime());
            fc.setDurationEditable(cc.isDurationEditable());
            fc.setEditable(cc.isEditable());
            fc.setStartSql(cc.getStartSql());
            fc.setEndSql(cc.getEndSql());
            fc.setEventId(cc.getEventId());
            fc.setNotifyClient(cc.isNewClient());
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
            FullCalendar2 fc;
            fc = new FullCalendar2();
            fc.setClient(cc.getClient());
            fc.setCustomerId(cc.getClient().getId());
            fc.setAssociate2(cc.getAssociate2());
            fc.setServices(cc.getServices());
            fc.setStart(cc.convertStartTimestamp());
            fc.setEnd(cc.convertEndTimestamp());
            fc.setTitle(cc.addName());
            fc.setNotes(cc.getNotes());
            fc.setDurationEditable(cc.isDurationEditable());
            fc.setTextColor(cc.getTextColor());
            fc.setEditable(cc.isEditable());
            fc.setBackgroundColor(cc.getServices().getServiceStatus().getStatusColor());
            fc.setServiceStatus(cc.getServiceStatus());
            fc.setServiceTime(cc.getServiceTime());
            fc.setAllDay(cc.isAllDay());
            fc.setEventId(cc.getEventId());
            fc.setEventId(cc.getEventId());
//                 fc.setAssociateId(cc.getAssociateId());

            return fc;
        }).forEachOrdered((fc) ->
        {
            fullCalendar2.add(fc);
        });
        return fullCalendar2;
    }

    public static ArrayList<FullCalendar2> clientInfo(String key, String title)
    {
        ArrayList<FullCalendar2> fullCalendar2 = new ArrayList<>();
        ArrayList<FullCalendar2> c;
        if ("getList".equals(key))
        {
            c = CustomerDB.selectClientsAll(title);
        }
        else
        {
            c = CustomerDB.selectClientsAll();
        }
        c.stream().map((cc) ->
        {
            FullCalendar2 fc = new FullCalendar2();
            fc.getClient().setFirstName(cc.getClient().getFirstName());
            fc.getClient().setLastName(cc.getClient().getLastName());
            fc.getClient().setEmail(cc.getClient().getEmail());
            fc.setCustomerId(cc.getCustomerId());
            fc.setAssociate2(cc.getAssociate2());
            fc.setClient(cc.getClient());
            return fc;
        }).map((fc) ->
        {
            fc.getMemberLevels();
            return fc;
        }).forEachOrdered((fc) ->
        {
            fullCalendar2.add(fc);
        });
        return fullCalendar2;
    }

// Get sms messages
//    public static ArrayList<SMSAppointmentCommunicator> messages(String key, String idnumber)
//    {
//        ArrayList<SMSAppointmentCommunicator> messages;
//        int id = Integer.parseInt(idnumber);
//        messages = MessagesDB.sentMessages2(id);
//
//        return messages;
//    }
    // Get sms   messages
    public static ArrayList<SMSAppointmentCommunicator> messages(String key1, String key2, String key3, String userId)
    {
        ArrayList<SMSAppointmentCommunicator> messages;
        int id = Integer.parseInt(userId);
        int start = Integer.parseInt(key1);
        int limit = Integer.parseInt(key2);

        messages = MessagesDB.sentMessages2(id, start, limit);

        return messages;
    }

// Get sms invite messages
    public static ArrayList<SMSMemberInviteCommunicator> inviteMessages(String key, String idnumber, String altKey)
    {
        ArrayList<SMSMemberInviteCommunicator> messages;
        int id = Integer.parseInt(idnumber);
        int altkeyInt = Integer.parseInt(altKey);
        messages = MessagesDB.selectRecentAssociateRequests(id, altkeyInt);

        return messages;
    }

// Get sms selected invite messages
    public static SMSMemberInviteCommunicator selectInviteMessage(String id, String json)
    {

        SMSMemberInviteCommunicator m = new SMSMemberInviteCommunicator();
        JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(json);
        m.setIsMessageInvite(jsonObj.getBoolean("isMessageInvite"));
        m.setStampToSend(jsonObj.getString("timeToSendInteger"));
        int n = parseInt(id);

        m = MessagesDB.selectMsgInviteRequest(m, n);

        return m;
    }

    public static ArrayList<SMSMemberInviteCommunicator> isClientOfAssociate(String json, String title) // Get sms messages
    {

        ArrayList<SMSMemberInviteCommunicator> smsObjArray = new ArrayList();

        Associate2 a = new Associate2();
        JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(json);
        JSONArray jsonarr = jsonObj.getJSONArray("phoneNumbers");
        ArrayList<String> strPhArr = StringUtil.convertArrayToList(jsonarr);
        a.setId(Integer.parseInt(jsonObj.getString("associateId")));
        int associateId = a.getId();
        for (int i = 0; i < strPhArr.size(); i++)
        {
            SMSMemberInviteCommunicator m;
            Client c;
            m = new SMSMemberInviteCommunicator();
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

        for (Associate2 associate : associateArray)
        {
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

        // Convert JSON File to Java Object
        // create the type for the collection. In this case define that the collection is of type Dataset
        java.lang.reflect.Type datasetListType;
        datasetListType = new TypeToken<Collection<FullCalendar2>>()
        {
        }.getType();

        List<FullCalendar2> fcArray = gson.fromJson(json, datasetListType);

        for (FullCalendar2 fc : fcArray)
        {
            fc.setTitle(fc.stripName());

            // convert timestamp strings to Date objects
            fc.setStartDate(DateUtil.timestampToDate(fc.getStartTimestamp()));
            fc.setEndDate(DateUtil.timestampToDate(fc.getEndTimestamp()));
            fc.setStartDateTime(DateUtil.convertDateTimeString(fc.getStartTimestamp()));
            fc.setEndDateTime(DateUtil.convertDateTimeString(fc.getEndTimestamp()));

            // get Calendar object instances
            Calendar calStart = Calendar.getInstance();

            // convert date-time strings to sql date-time objects
            fc.setStartSql(DateUtil.convertDate(fc.getStart())); // convert start time to sql timestamp
            fc.setEndSql(DateUtil.convertDate(fc.getEnd())); // convert end time to sql timestamp

            // prepare SMS message
            Date start = calStart.getTime();
            String dateStr = DateUtil.formatDate(start);
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT);
            String dateString = df.format(start);
            String timeString = tf.format(start);

            // convert current date and time to long string of numbers
            String nowTimeUnix = DateUtil.dateNowLong();

            int regCode = RandomCode();

            Associate2 a = fc.getAssociate2();
            Services s = ProductDB.selectService(fc.getServices().getServiceId());

            boolean smsSent = true;
            SMSAppointmentCommunicator m = new SMSAppointmentCommunicator();
            m.setAssociate2(fc.getAssociate2());
            m.setSentById(associateSession.getId());
            m.setClient(fc.getClient());
            m.setMessageTypeID("1");
            m.setStampToSend(nowTimeUnix);

//            boolean sendEmailApptAlert;
//            boolean sendSmsApptAlert;
            String smsEventChangeMsg = " has been scheduled on ";

            if (fc.isEventChange())
            {
                smsEventChangeMsg = " has been RE-SCHEDULED to ";
                m.setSubject("RE-SCHEDULE");
            }
            else if ("delete".equals(fc.getAction()))
            {
                smsEventChangeMsg = " has been CANCELLED for ";
                m.setSubject("CANCELLED");
            }

            switch (fc.getAction())
            {
                case "add":
                    boolean eventExists;
                    if (fc.isAllDay())
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
                        int updateCalendar = CalendarDB.updateCalendar(fc);
                        if (updateCalendar == 0)
                        {
                            errorFlag = true;
                        }
                        else if (updateCalendar == 1)
                        {
                            m.setMessage(fc.getClient().getFirstName() + ", the service " + fc.getTitle() + " with "
                                    + fc.getAssociate2().getFirstName() + smsEventChangeMsg + dateStr + " at " + timeString + "."
                                    + " Event:# " + fc.eventIdStr());

                            if (fc.isNotifyClient())
                            {
                                if (fc.getClient().isSmsApptAlerts())
                                {
                                    // send client sms message
                                    smsSent = sendSMS(m, fc);
                                    if (smsSent == false)
                                    {
                                        errorFlag = true;
                                    }
                                }
                                if (fc.getClient().isEmailApptAlerts())
                                {
                                    // email client
                                    boolean sendConfirmation = MailUtil.sendConfirmation("Your Appointment " + smsEventChangeMsg, dateStr, timeString, fc, a, s, regCode);
                                    if (sendConfirmation == false)
                                    {
                                        errorFlag = true;
                                    }
                                }
                            }
                            if (fc.isNotifyAssociate())
                            {
                                if (fc.getAssociate2().isSmsApptAlerts())
                                {
                                    m.setMessage(fc.getAssociate2().getFirstName() + ", the service " + fc.getTitle() + " with "
                                            + fc.getClient().getFirstName() + smsEventChangeMsg + dateStr + " at " + timeString + "."
                                            + " Event:# " + fc.eventIdStr());
                                    // send associate sms message
                                    smsSent = sendSMS(m, fc);
                                    if (smsSent == false)
                                    {
                                        errorFlag = true;
                                    }
                                }
                                if (fc.getAssociate2().isEmailApptAlerts())
                                {
                                    // email associate
                                    boolean sendConfirmation;
                                    sendConfirmation = MailUtil.sendAssociateConfirm("Appointment RE-SCHEDULED " + smsEventChangeMsg, dateStr, timeString, fc, a, s, regCode);
                                    if (sendConfirmation == false)
                                    {
                                        errorFlag = true;
                                    }
                                }
                            }
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
                                fc.getClient().setId(clientId);
                            }
                            else
                            {
                                errorFlag = true;
                            }
                        }
                        // insert new calendar event into database
                        fc.setEventId(CalendarDB.insertAppointment(fc));

                        if (fc.getEventId() != 0)
                        {
                            // remove associate available date and time
                            AssociateDB.deleteAvailability(fc);

                            if (fc.isNotifyClient())
                            {

                                // TODO code new appointment email/sms message
                                m.setMessage(fc.getClient().getFirstName() + ", the service " + fc.getTitle() + " with " + fc.getAssociate2().getFirstName() + smsEventChangeMsg + dateStr + " at " + timeString + "."
                                        + " Event:# " + fc.eventIdStr() + " Code: " + regCode);
//                                String message = fc.getFirstName() + " " + dateStr + " at " + timeString + "."
//                                        + "http://71.8.84.224:8081/AppointmentStore/IncomingMessage?phone=" + mobileStr2 + "&reg=" + regCodeStr;
                                if (fc.getClient().isSmsApptAlerts())
                                {

                                    try
                                    {
                                        // sms text message to client
                                        m.setSubject(m.subjectDatabase(4));
                                        smsSent = sendSMS(m, fc);

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
                                if (fc.getClient().isEmailApptAlerts())
                                {
                                    boolean sendConfirmation = MailUtil.sendConfirmation("Appointment Confirmation", dateStr, timeString, fc, a, s, regCode);
                                    if (sendConfirmation == false)
                                    {
                                        errorFlag = true;
                                    }
                                }
                            }
                            if (fc.getAssociate2().isEmailApptAlerts())
                            {
                                boolean sendConfirmation = MailUtil.sendAssociateConfirm("Appointment Confirmation", dateStr, timeString, fc, a, s, regCode);
                                if (sendConfirmation == false)
                                {
                                    errorFlag = true;
                                }
                            }
                            if (fc.getAssociate2().isSmsApptAlerts())
                            {
                                m.setMessage("Your client " + fc.getClient().getFirstName() + smsEventChangeMsg + fc.getTitle() + " on " + dateStr + " at " + timeString + "."
                                        + " Event# " + fc.eventIdStr());
                                try
                                {
                                    m.setSubject(m.getAssociate2().getFirstName());
                                    smsSent = sendSMS(m, fc);
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
                    if (eventDeleted && fc.isNotifyClient())
                    {
                        LogFile.generalLog(fc.getEventId() + " event successfully deleted ", " event ID" + fc.eventIdStr() + " client " + fc.getClient().getFirstName());
                        if (fc.getClient().isSmsApptAlerts())
                        {
                            m.setMessage(fc.getClient().getFirstName() + ", the service " + fc.getTitle() + " with " + fc.getAssociate2().getFirstName() + smsEventChangeMsg + dateStr + " at " + timeString + "."
                                    + " Event:# " + fc.eventIdStr() + " Code: " + regCode);

                            smsSent = sendSMS(m, fc);
                            if (smsSent == false)
                            {
                                errorFlag = true;
                            }
                        }
                    }
                    if (eventDeleted && fc.getRestoreTime() && !fc.isAllDay())
                    {
                        boolean isAssoDateAvailable = AssociateDB.isAssociateAvailability(fc.getAssociate2().getId(), fc.sqlStartDate());
                        if (!isAssoDateAvailable) // if Associate Date not available, restore Associate time frame
                        {
                            int insertAvailDate = AssociateDB.insertAvailableDate(fc.getAssociate2().getId(), fc.sqlStartDate());
                            if (insertAvailDate == 0)
                            {
                                errorFlag = true;
                            }
                        }
                        // convert frequency to multiplier number
                        int calculateTimes = CalendarUtil.calculateTimes(15);
                        // calculate the number of times to insert into database using for-loop
                        int totalTime = (fc.endTimeHour() - fc.startTimeHour()) * calculateTimes;
                        // get total loops adjustment conversion
                        totalTime = CalendarUtil.totalTimes(fc.startTimeMin(), fc.endTimeMin(), 15, totalTime);
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
                            boolean isAssoTimeAvailable = AssociateDB.isAssociateTimeAvailable(fc.getAssociate2().getId(), fc.sqlStartDate(), fc.sqlStartTime());
                            if (!isAssoTimeAvailable)
                            {
                                // insert new scheduled date, time, start timestamp, end timestamp and associate id into database
                                int insertSchedule = AssociateDB.insertAssociateSchedule(fc.sqlStartDate(), sqlStTime, fc.getAssociate2().getId(), fc.getStartSql(), fc.getEndSql());
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
                                + " Start Time slot deleted: " + fc.getStartDate() + " End Time slot deleted:" + fc.getEndDate(), " action:" + action);
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
            boolean logAction = LogFile.associateLog("FullCalPostServlet postCalendarData", "client:" + fc.getClient().getFirstName() + " clientID:" + fc.getClient().getId() + " Event ID: " + fc.getEventId()
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
            SMSBasicCommunicator m = new SMSBasicCommunicator();
            m.setMessage(msg);
            m.setSubject(subject);
            m.setMessageTypeID("1");
            m.setGroupMessage(true);

            FullCalendar2 fc1 = fcArray.get(0); // get fullcalendar2 object at the zero index

            for (FullCalendar2 fc : fcArray) // loop through array of users
            {
                m.setStampToSend(fc.getTimeToSend()); // get long unix format timeToSend string from fc object
                m.setAssociate2(fc.getAssociate2());
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

            LogFile.generalLog("CalendarData postClientData quick sms " + fc1.getAssociate2().getFirstName() + " "
                    + fc1.getAssociate2().getLastName() + " "
                    + msg, " - email addresses " + emailBccStr);

            if (m.getPhoneNumStrArr() != null)
            {
                try
                {
//                    smsSent = SendingSMSMessagesJSON.sendSMSMessage(m);
                    m.setSubject("On-Time");
                    smsSent = sendSMS(m, fc1);
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
                boolean logAction = LogFile.associateLog("FullCalPostServlet postClientData", "client:" + fc.getAssociate2().getFirstName() + " clientID:" + fc.getCustomerId() + " Event ID: n/a"
                        + " svcDate: n/a", " action:" + fc.getAction());
            }
        }
        return errorFlag;
    }

    public static boolean smsInviteRequest(String json, HttpServletRequest request, String title, Associate2 associate)
    {
        boolean errorFlag = false;
        boolean smsSent;

        SMSMemberInviteCommunicator m = new SMSMemberInviteCommunicator();
        JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(json);

        m.setPhoneNumbers(jsonObj.getJSONArray("phoneNumbers"));
        m.setPhoneArray(m.getConvertedPhoneNumersString());
        m.setPhoneNumStrArr(StringUtil.convStrArray(m.getPhoneArray()));
        m.setStampToSend(jsonObj.getString("stampToSend"));
        m.setIsMessageInvite(jsonObj.getBoolean("isMessageInvite"));
        m.setMessageTypeID("1");
        m.setAssociate2(associate);
        m.setSentById(associate.getId());
        String f = m.getAssociate2().getFirstName();
        String l = m.getAssociate2().getLastName();
        m.setMessage(f + " " + l + " " + m.messageDatabase(0));
        m.setSubject(m.subjectDatabase(4));
        try
        {
            smsSent = SendingSMScommunicatorJSON.sendSMScommunicator(m);
            if (smsSent == false)
            {
                errorFlag = true;
            }
            else if (smsSent == true)
            {
                m.getConvertedPhoneNumersString().forEach((String mobilePh) ->
                {
                    MessagesDB.insertInvitationRequests(mobilePh, m.getAssociate2().getId(), m.getSqlTimestamp(), m.getTimeToSendInteger());
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

    private static boolean sendSMS(SMSBasicCommunicator m, FullCalendar2 fc)
    {
        boolean smsSent = false;

        try
        {
            ArrayList<String> mobilePhAssocArr = new ArrayList<>();
            String mobileStrAsso = PhoneUtil.stripNonDigits2(fc.getAssociate2().getMobilePhone());
            mobilePhAssocArr.add(mobileStrAsso);
            String mobileNumbers = StringUtil.convStrArray(mobilePhAssocArr);
            m.setPhoneNumStrArr(mobileNumbers);
            m.setPhoneArray(mobilePhAssocArr);
            // sne SMS message
            smsSent = SendingSMScommunicatorJSON.sendSMScommunicator(m);

        }
        catch (Exception ex)
        {
            Logger.getLogger(CalendarData.class.getName()).log(Level.SEVERE, null, ex);
            LogFile.smsError("CalendarData sendSMS", ex.toString(), m);
        }
        return smsSent;
    }

    private static int RandomCode()
    {
        Random random = new Random();
        return random.nextInt(999999);
    }
}
