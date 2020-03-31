/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import store.data.CustomerDB;
import store.util.DateUtil;

/**
 *
 * @author williamdobbs
 */
public class FullCalendar2 extends ProcessStatus implements Serializable, MessageConstants, Messenger
{

    private int customerId;
    private int eventId;
    private int serviceTime;
    private String serviceDescription;
    private Services services;
    private ServiceStatus serviceStatus;
    private Associate2 associate2;
    private SMScommunicator smsMessage;
    private Client client;
    private String start;
    private String end;
    private String startTimestamp;
    private String endTimestamp;
    private String startOffset;
    private String endOffset;
    private String title;
    private String action;
    private String actionType;
    private boolean notifyClient;
    private boolean notifyAssociate;
    private boolean eventChange;
    private boolean restoreTime;
    private boolean newClient;
    private boolean isAssociate;
    private String userType;
    private ArrayList<Integer> cancelEvts;
    private ArrayList<MemberExtras> memberLevels;
    private String message;
    private int date;
    private int startMonth;
    private int startDay;
    private int startYear;
    private int startDayOfWeek;
    private int startDayOfMonth;
    private int startTimeHour;
    private int startTimeMin;
    private int endMonth;
    private int endDay;
    private int endYear;
    private int endDayOfWeek;
    private int endDayOfMonth;
    private int endTimeHour;
    private int endTimeMin;
    private java.sql.Date sqlCalendarDate;
    private java.sql.Date calendarDate;
    private java.sql.Date sqlStartDate;
    private java.sql.Date sqlEndDate;
    private java.sql.Time sqlStartTime;
    private java.sql.Time sqlEndTime;
    private Timestamp startSql;
    private Timestamp endSql;
    private Time startTime;
    private Time endTime;
    private Date startDate;
    private Date endDate;
    private boolean allDay;
    private String backgroundColor;
    private String textColor;
    private boolean durationEditable;
    private boolean editable;
    private String color;
    private String notes;
    private String mobilePhone;
    private String timeToSend;
    private int messageId;
    private final Map<String, Object> otherProperties = new HashMap<>();
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String startTimeUtc;
    private String endTimeUtc;
    private String startMoment;
    private String timeZone;
//    private final Calendar c = Calendar.getInstance();
    private Random randomCode;
    private final String defaultMessage = "On-Time Appointment Store ";

    public FullCalendar2()
    {
        super();
        eventId = 0;
        serviceDescription = null;
        serviceTime = 0;
        customerId = 0;
        associate2 = new Associate2();
        client = new Client();
        start = null;
        end = null;
        title = null;
        notifyClient = false;
        notifyAssociate = false;
        restoreTime = false;
        newClient = false;
        action = "";
        actionType = "";
        isAssociate = false;
        userType = "";
        memberLevels = null;
        notes = "";
        startSql = null;
        message = "no message";
        services = new Services();
        serviceStatus = new ServiceStatus();
        smsMessage = new SMSAppointmentCommunicator();
        timeToSend = "";
        messageId = 0;
        eventChange = false;
        randomCode = null;

    }

    public String getStartMoment()
    {
        return startMoment;
    }

    public void setStartMoment(String startMoment)
    {
        this.startMoment = startMoment;
    }

    public String getStartTimeUtc()
    {
        return startTimeUtc;
    }

    public void setStartTimeUtc(String startTimeUtc)
    {
        this.startTimeUtc = startTimeUtc;
    }

    public String getEndTimeUtc()
    {
        return endTimeUtc;
    }

    public void setEndTimeUtc(String endTimeUtc)
    {
        this.endTimeUtc = endTimeUtc;
    }

    public String getTimeZone()
    {
        return timeZone;
    }

    public void setTimeZone(String timeZone)
    {
        this.timeZone = timeZone;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public Associate2 getAssociate2()
    {
        return associate2;
    }

    public void setAssociate2(Associate2 associate2)
    {
        this.associate2 = associate2;
    }

    public Services getServices()
    {
        return services;
    }

    public void setServices(Services services)
    {
        this.services = services;
    }

    public ServiceStatus getServiceStatus()
    {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus)
    {
        this.serviceStatus = serviceStatus;
    }

    public int getEventId()
    {
        return eventId;
    }

    public void setEventId(int eventId)
    {
        this.eventId = eventId;
    }

    public String eventIdStr()
    {
        String eventIdStr = String.valueOf(this.eventId);
        return eventIdStr;
    }

    public int getServiceTime()
    {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime)
    {
        this.serviceTime = serviceTime;
    }

    public String getServiceDescription(int serviceId)
    {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription)
    {
        this.serviceDescription = serviceDescription;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public ArrayList<Integer> getCancelEvts()
    {
        return cancelEvts;
    }

    public void setCancelEvts(ArrayList<Integer> cancelEvts)
    {
        this.cancelEvts = cancelEvts;
    }

    public int getDate()
    {
        return date;
    }

    public void setDate(int date)
    {
        this.date = date;
    }

    public String getStart()
    {
        return start;
    }

    public void setStart(String start)
    {
        this.start = start;
    }

    public String getEnd()
    {
        return end;
    }

    public void setEnd(String end)
    {
        this.end = end;
    }

    public String getStartTimestamp()
    {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp)
    {
        this.startTimestamp = startTimestamp;
    }

    public String getEndTimestamp()
    {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp)
    {
        this.endTimestamp = endTimestamp;
    }

    public String getStartOffset()
    {
        return startOffset;
    }

    public void setStartOffset(String startOffset)
    {
        this.startOffset = startOffset;
    }

    public String getEndOffset()
    {
        return endOffset;
    }

    public void setEndOffset(String endOffset)
    {
        this.endOffset = endOffset;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getActionType()
    {
        return actionType;
    }

    public void setActionType(String actionType)
    {
        this.actionType = actionType;
    }

    public boolean isNotifyClient()
    {
        return notifyClient;
    }

    public void setNotifyClient(boolean notifyClient)
    {
        this.notifyClient = notifyClient;
    }

    public boolean isNotifyAssociate()
    {
        return notifyAssociate;
    }

    public void setNotifyAssociate(boolean notifyAssociate)
    {
        this.notifyAssociate = notifyAssociate;
    }

    public boolean isRestoreTime()
    {
        return restoreTime;
    }

    public void setRestoreTime(boolean restoreTime)
    {
        this.restoreTime = restoreTime;
    }

    public boolean isNewClient()
    {
        return newClient;
    }

    public void setNewClient(boolean newClient)
    {
        this.newClient = newClient;
    }

    public Boolean getRestoreTime()
    {
        return restoreTime;
    }

    public void setRestoreTime(Boolean restoreTime)
    {
        this.restoreTime = restoreTime;
    }

    public Boolean getNewClient()
    {
        return newClient;
    }

    public void setNewClient(Boolean newClient)
    {
        this.newClient = newClient;
    }

    public boolean isIsAssociate()
    {
        return isAssociate;
    }

    public void setIsAssociate(boolean isAssociate)
    {
        this.isAssociate = isAssociate;
    }

    public ArrayList<MemberExtras> getMemberLevels()
    {
        memberLevels = CustomerDB.selectMemberLevels();
        return memberLevels;
    }

    public void setMemberLevels(ArrayList<MemberExtras> memberLevels)
    {
        this.memberLevels = memberLevels;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getStartTimeHour()
    {
        return startTimeHour;
    }

    public int getStartMonth()
    {
        return startMonth;
    }

    public void setStartMonth(int startMonth)
    {
        this.startMonth = startMonth;
    }

    public int getStartDay()
    {
        return startDay;
    }

    public void setStartDay(int startDay)
    {
        this.startDay = startDay;
    }

    public int getStartYear()
    {
        return startYear;
    }

    public void setStartYear(int startYear)
    {
        this.startYear = startYear;
    }

    public int getStartDayOfWeek()
    {
        return startDayOfWeek;
    }

    public void setStartDayOfWeek(int startDayOfWeek)
    {
        this.startDayOfWeek = startDayOfWeek;
    }

    public int getStartDayOfMonth()
    {
        return startDayOfMonth;
    }

    public void setStartDayOfMonth(int startDayOfMonth)
    {
        this.startDayOfMonth = startDayOfMonth;
    }

    public void setStartTimeHour(int startTimeHour)
    {
        this.startTimeHour = startTimeHour;
    }

    public int getStartTimeMin()
    {
        return startTimeMin;
    }

    public void setStartTimeMin(int startTimeMin)
    {
        this.startTimeMin = startTimeMin;
    }

    public int getEndTimeHour()
    {
        return endTimeHour;
    }

    public void setEndTimeHour(int endTimeHour)
    {
        this.endTimeHour = endTimeHour;
    }

    public int getEndTimeMin()
    {
        return endTimeMin;
    }

    public void setEndTimeMin(int endTimeMin)
    {
        this.endTimeMin = endTimeMin;
    }

    public int getEndDay()
    {
        return endDay;
    }

    public void setEndDay(int endDay)
    {
        this.endDay = endDay;
    }

    public int getEndMonth()
    {
        return endMonth;
    }

    public void setEndMonth(int endMonth)
    {
        this.endMonth = endMonth;
    }

    public int getEndYear()
    {
        return endYear;
    }

    public void setEndYear(int endYear)
    {
        this.endYear = endYear;
    }

    public int getEndDayOfWeek()
    {
        return endDayOfWeek;
    }

    public void setEndDayOfWeek(int endDayOfWeek)
    {
        this.endDayOfWeek = endDayOfWeek;
    }

    public int getEndDayOfMonth()
    {
        return endDayOfMonth;
    }

    public void setEndDayOfMonth(int endDayOfMonth)
    {
        this.endDayOfMonth = endDayOfMonth;
    }

    public java.sql.Date getCalendarDate()
    {
        return calendarDate;
    }

    public void setCalendarDate(java.sql.Date calendarDate)
    {
        this.calendarDate = calendarDate;
    }

    public Date getSqlCalendarDate()
    {
        return sqlCalendarDate;
    }

    public void setSqlCalendarDate(java.sql.Date sqlCalendarDate)
    {
        this.sqlCalendarDate = sqlCalendarDate;
    }

    public java.sql.Date getSqlStartDate()
    {
        return sqlStartDate;
    }

    public void setSqlStartDate(java.sql.Date sqlStartDate)
    {
        this.sqlStartDate = sqlStartDate;
    }

    public java.sql.Date getSqlEndDate()
    {
        return sqlEndDate;
    }

    public void setSqlEndDate(java.sql.Date sqlEndDate)
    {
        this.sqlEndDate = sqlEndDate;
    }

    public Time getSqlStartTime()
    {
        return sqlStartTime;
    }

    public void setSqlStartTime(Time sqlStartTime)
    {
        this.sqlStartTime = sqlStartTime;
    }

    public Time getSqlEndTime()
    {
        return sqlEndTime;
    }

    public void setSqlEndTime(Time sqlEndTime)
    {
        this.sqlEndTime = sqlEndTime;
    }

    public Timestamp getStartSql()
    {
        return startSql;
    }

    public void setStartSql(Timestamp startSql)
    {

        this.startSql = startSql;
    }

    public Timestamp getEndSql()
    {
        return endSql;
    }

    public void setEndSql(Timestamp endSql)
    {
        this.endSql = endSql;
    }

    public boolean isAllDay()
    {
        return allDay;
    }

    public void setAllDay(boolean allDay)
    {
        this.allDay = allDay;
    }

    public String getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor()
    {
        return textColor;
    }

    public void setTextColor(String textColor)
    {
        this.textColor = textColor;
    }

    public boolean isDurationEditable()
    {
        return durationEditable;
    }

    public void setDurationEditable(boolean durationEditable)
    {
        this.durationEditable = durationEditable;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String convertStartTimestamp()
    {
        String startDateString = DateUtil.createDateString(this.startSql);
        return startDateString;
    }

    public String convertEndTimestamp()
    {
        String endDateString = DateUtil.createDateString(this.endSql);
        return endDateString;
    }

    public String getStartTimeFormat()
    {
        NumberFormat nf = NumberFormat.getInstance();
        String amPM = "p";
        String startTimeFormat = nf.format(startTimeHour) + amPM + " -";

        if (this.startTimeHour == 12)
        {
            startTimeFormat = nf.format(startTimeHour) + amPM + " -";
        }
        else if (startTimeHour > 12)
        {
            startTimeFormat = nf.format(startTimeHour - 12) + amPM + " -";
        }
        else
        {
            amPM = "a";
            startTimeFormat = nf.format(startTimeHour) + amPM + " -";
        }
        return startTimeFormat;

    }

    public String getEndTimeFormat()
    {
        NumberFormat nf = NumberFormat.getInstance();
        String amPM = "p";
        String endTimeFormat = nf.format(endTimeHour) + amPM;

        if (endTimeHour == 12)
        {
            endTimeFormat = nf.format(endTimeHour) + amPM;
        }
        else if (endTimeHour > 12)
        {
            endTimeFormat = nf.format(endTimeHour - 12) + amPM;
        }
        else
        {
            amPM = "a";
            endTimeFormat = nf.format(endTimeHour) + amPM;
        }
        return endTimeFormat;
    }

    // strip client first name from calendar title
    public String stripName()
    {
        String s = null;
//        if (t != null)
//        {
        int i = this.title.indexOf(":", 0);
        if (i != -1) // if client first name part of service title, strip client name from title
        {
            this.title = this.title.substring(0, i);
        }
//        }
        return this.title;
    }

    public String addName()
    {
        String titleFirstNm = this.serviceDescription + ": " + this.client.getFirstName() + " "
                + this.client.getLastName();
        return titleFirstNm;
    }

    public SMScommunicator getSmsMessage()
    {
        return smsMessage;
    }

    public void setSmsMessage(SMScommunicator smsMessage)
    {
        this.smsMessage = smsMessage;
    }

    public String getTimeToSend()
    {
        return timeToSend;
    }

    public void setTimeToSend(String timeToSend)
    {
        this.timeToSend = timeToSend;
    }

    public int getMessageId()
    {
        return messageId;
    }

    public void setMessageId(int messageId)
    {
        this.messageId = messageId;
    }

    public boolean isEventChange()
    {
        return eventChange;
    }

    public void setEventChange(boolean eventChange)
    {
        this.eventChange = eventChange;
    }

    @Override
    public String smsAssociateMessage()
    {
        if ("add".equalsIgnoreCase(this.action) && "new".equalsIgnoreCase(this.actionType))
        {
            return "Your client " + this.client.getFirstName() + " " + this.client.getLastName() + " has scheduled a "
                    + this.title.toUpperCase() + " on " + this.startDateString2() + " at " + this.startTimeString() + ". Event# " + this.eventIdStr();
        }
        else if ("add".equalsIgnoreCase(this.action) && "resize".equalsIgnoreCase(this.actionType) || "move".equalsIgnoreCase(this.actionType))
        {
            return "Service time CHANGE for your client " + this.client.getFirstName() + " " + this.client.getLastName() + "."
                    + " The service " + this.title.toUpperCase() + " on " + this.startDateString2() + " will start at " + this.startTimeString()
                    + " and end at " + this.endTimeString() + ". Event# " + this.eventIdStr();
        }
        else if ("delete".equalsIgnoreCase(this.action))
        {
            return "Your appointment with " + this.client.getFirstName() + " " + this.client.getLastName() + " "
                    + " for the service " + this.title.toUpperCase() + " on " + this.startDateString2() + " at " + this.startTimeString()
                    + " has been DELETED" + ". Event# " + this.eventIdStr();
        }
        else if ("updateStatus".equalsIgnoreCase(this.action) && !"Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return "Your appointment " + this.title.toUpperCase() + " with client " + this.client.getFirstName() + " " + this.client.getLastName()
                    + " has changed to " + this.serviceStatus.getStatusName().toUpperCase() + " - " + this.startDateString2() + " at " + this.startTimeString()
                    + ". Event# " + this.eventIdStr();
        }
        else if ("updateStatus".equalsIgnoreCase(this.action) && "Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return "Your appointment " + this.title.toUpperCase() + " with client " + this.client.getFirstName() + " " + this.client.getLastName()
                    + " has been " + this.serviceStatus.getStatusName().toUpperCase() + " - " + this.startDateString2() + " at " + this.startTimeString()
                    + ". Event# " + this.eventIdStr();
        }
        return defaultMessage;
    }

    @Override
    public String smsClientMessage()
    {
        if ("add".equalsIgnoreCase(this.action) && "new".equalsIgnoreCase(this.actionType))
        {
            return this.client.getFirstName() + ", " + "Thank you for scheduling a "
                    + this.title.toUpperCase() + " on " + this.startDateString2() + " at " + this.startTimeString() + " to be perfomed by " + this.getAssociate2().getFirstName() + ". Event# " + this.eventIdStr();
        }
        else if ("add".equalsIgnoreCase(this.action) && "resize".equalsIgnoreCase(this.actionType) || "move".equalsIgnoreCase(this.actionType))
        {
            return this.client.getFirstName() + ", " + "your scheduled appointment for a " + this.title.toUpperCase() + " on "
                    + this.startDateString2() + " performed by " + this.getAssociate2().getFirstName() + " will be from "
                    + this.startTimeString() + " to " + this.endTimeString() + ". Event# " + this.eventIdStr();
        }
        else if ("delete".equalsIgnoreCase(this.action) || "Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return this.client.getFirstName() + ", " + "your appointment for a " + this.title.toUpperCase() + " on " + this.startDateString2()
                    + " at " + this.startTimeString() + " has been CANCELLED";
        }
        else if ("updateStatus".equalsIgnoreCase(this.action) && !"Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return this.client.getFirstName() + ", " + "the status of your appointment " + this.title.toUpperCase() + " with " + this.getAssociate2().getFirstName()
                    + " has changed to " + this.serviceStatus.getStatusName().toUpperCase() + " - " + this.startDateString2() + " at " + this.startTimeString()
                    + ". Event# " + this.eventIdStr();
        }
        return defaultMessage;
    }

    @Override
    public String subjectClient()
    {
        if ("add".equalsIgnoreCase(this.action) && "new".equalsIgnoreCase(this.actionType))
        {
            return "On-Time";
        }
        else if ("add".equalsIgnoreCase(this.action) && "move".equalsIgnoreCase(this.actionType))
        {
            return "RE-SCHEDULE";
        }
        else if ("add".equalsIgnoreCase(this.action) && "resize".equalsIgnoreCase(this.actionType))
        {
            return "TIME CHANGE";
        }
        else if ("delete".equalsIgnoreCase(this.action))
        {
            return "CANCELLED";
        }
        else if ("updateStatus".equalsIgnoreCase(this.action) && "Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return "CANCELLED";
        }
        else if ("updateStatus".equalsIgnoreCase(this.action) && !"Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return "STATUS CHANGE";
        }
        return "On-Time";
    }

    @Override
    public String emailAssociateMessage()
    {
        if ("add".equalsIgnoreCase(this.action) && "new".equalsIgnoreCase(this.actionType) || "resize".equalsIgnoreCase(this.actionType)
                || "move".equalsIgnoreCase(this.actionType) || ("updateStatus".equalsIgnoreCase(this.action)) && !"Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return "Hello " + this.associate2.getFirstName() + ",<br><br>"
                    + "Your client <strong> " + this.client.getFirstName() + " " + this.client.getLastName() + "</strong>" + " has scheduled an appointment "
                    + "with The Salon Store on " + this.startDateString() + ".<br><br>"
                    + "<table style=\"height: 206px; float: left;\" width=\"547\">" + " <tbody>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #f68b09;\">" + "Appointment" + "</td>" + "<td style=\"color: #f68b09;\">" + " Summary" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Client Name:" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.client.getFirstName() + " " + this.client.getLastName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Service Description:  " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.title + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Status:" + "</td>" + "<td style='color:" + this.serviceStatus.getStatusColor() + " ;'>" + "<strong>" + this.serviceStatus.getStatusName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment ID&#35;" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.eventId + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Client Email Address: " + "</td>" + "<td style=\"color: #333333;\">" + this.client.getEmail() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Client ID&#35; " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.client.getId() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Date: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.startDateString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Start Time: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.startTimeString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Estimated End Time: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.endTimeString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Notes: " + "</td>" + "<td style=\"color: #333333;\">" + this.notes + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Your Account ID&#35; " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.associate2.getId() + "</strong>" + "</td>"
                    + "</tr>"
                    + "</tbody>" + "</table>"
                    + "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>";
        }
        else if ("delete".equalsIgnoreCase(this.action) || "Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return "Hello " + this.associate2.getFirstName() + ",<br><br>"
                    + "Your appointment at " + this.startTimeString() + " on " + this.startDateString() + " was successfully " + "<font style=\"color: #ff0000;\">" + "CANCELLED" + "</font>" + ".<br><br>"
                    + "<table style=\"height: 206px; float: left;\" width=\"547\">" + " <tbody>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #f68b09;\">" + "Appointment" + "</td>" + "<td style=\"color: #f68b09;\">" + " Summary" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Client Name:" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.client.getFirstName() + " " + this.client.getLastName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Service Description:  " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.title + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Status:" + "</td>" + "<td style='color:" + this.serviceStatus.getStatusColor() + " ;'>" + "<strong>" + this.serviceStatus.getStatusName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment ID&#35;" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.eventId + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Client Email Address: " + "</td>" + "<td style=\"color: #333333;\">" + this.client.getEmail() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Client ID&#35; " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.client.getId() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Date: " + "</td>" + "<td style=\"color: #333333; text-decoration-line: line-through; text-decoration-color: red;\">"
                    + "<strong>" + this.startDateString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Start Time: " + "</td>" + "<td style=\"color: #333333; text-decoration-line: line-through; text-decoration-color: red;\">"
                    + "<strong>" + this.startTimeString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "</tbody>" + "</table>"
                    + "<br><br><br><br><br><br><br><br><br><br><br><br><br>";
        }
        return defaultMessage;
    }

    @Override
    public String emailAssociateSubject()
    {

        String eventStatus = "";
        String eventSubject = eventStatus + " - your client " + this.client.getFirstName() + " " + this.client.getLastName()
                + " has scheduled the service, " + this.title.toUpperCase()
                + " on " + this.startDateString() + " at " + this.startTimeString();

        if ("add".equalsIgnoreCase(this.action) && "new".equalsIgnoreCase(this.actionType))
        {
            eventStatus = "NEW Appointment";
            return eventStatus + eventSubject;
        }
        else if ("add".equalsIgnoreCase(this.action) && "move".equalsIgnoreCase(this.actionType))
        {
            eventStatus = "RE-SCHEDULED Appointment";
            return eventStatus + eventSubject;
        }
        else if ("add".equalsIgnoreCase(this.action) && "resize".equalsIgnoreCase(this.actionType))
        {
            eventStatus = "ESTIMATED APPOINTMENT END TIME CHANGED";
            return eventStatus + eventSubject;
        }
        else if ("updateStatus".equalsIgnoreCase(this.action))
        {
            eventStatus = "Appointment Status CHANGE";
            eventSubject = " - the appointment " + this.title + " with " + this.client.getFirstName() + " " + this.client.getLastName()
                    + " status has changed to " + this.serviceStatus.getStatusName().toUpperCase() + " - " + this.startDateString() + " at " + this.startTimeString();
            return eventStatus + eventSubject;
        }
        else if ("delete".equalsIgnoreCase(this.action))
        {
            eventStatus = "Appointment DELETED";
            eventSubject = " - your appointment with " + this.client.getFirstName() + " " + this.client.getLastName()
                    + " scheduled on " + this.startDateString() + " at " + this.startTimeString() + " has been DELETED";
            return eventStatus + eventSubject;
        }
        return defaultMessage;
    }

    @Override
    public String emailClientMessage()
    {
        if ("add".equalsIgnoreCase(this.action) && "new".equalsIgnoreCase(this.actionType) || "resize".equalsIgnoreCase(this.actionType)
                || "move".equalsIgnoreCase(this.actionType) || ("updateStatus".equalsIgnoreCase(this.action)) && !"Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return "Hello " + this.client.getFirstName() + ",<br><br>"
                    + "Thank you for scheduling an appointment with The Salon Store on " + this.startDateString() + " at " + this.startTimeString() + ".<br><br>"
                    + "<table style=\"height: 206px; float: left;\" width=\"547\">" + " <tbody>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #f68b09;\">" + "Appointment " + "</td>" + "<td style=\"color: #f68b09;\">" + " Summary" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Service Description:" + "</td>" + "<td style=\"color: #333333;\">" + " <strong>" + this.title + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Stylist Name:" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.associate2.getFirstName() + " " + this.associate2.getLastName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Status:" + "</td>" + "<td style='color:" + this.serviceStatus.getStatusColor() + " ;'>" + "<strong>" + this.serviceStatus.getStatusName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment ID&#35; " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + eventId + "</strong> " + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Date: " + "</td>" + "<td style=\"color: #333333;\">" + " <strong>" + this.startDateString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Time: " + "</td>" + "<td style=\"color: #333333; \">" + " <strong>" + this.startTimeString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Estimated End Time: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.endTimeString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Notes: " + "</td>" + "<td style=\"color: #333333;\">" + this.notes + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Your Account ID&#35;" + "</td style=\"color: #333333;\">" + "<td>" + " <strong>" + this.client.getId() + "</strong>" + "</td>"
                    + "</tr>"
                    + "</tbody>" + "</table>"
                    + "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>";
        }
        else if ("delete".equalsIgnoreCase(this.action) || "Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            return "Hello " + this.client.getFirstName() + ",<br><br>"
                    + "Your appointment at " + this.startTimeString() + " on " + this.startDateString() + " has been " + "<font style='color: #ff0000;'>" + "CANCELLED" + "</font>" + ".<br><br>"
                    + "<table style=\"height: 206px; float: left;\" width=\"547\">" + " <tbody>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #f68b09;\">" + "Appointment " + "</td>" + "<td style=\"color: #f68b09;\">" + " Summary" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Service Description:" + "</td>" + "<td style=\"color: #333333;\">" + " <strong>" + this.title + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Stylist Name:" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + this.associate2.getFirstName() + " " + this.associate2.getLastName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Status:" + "</td>" + "<td style='color:" + this.serviceStatus.getStatusColor() + " ;'>" + "<strong>" + this.serviceStatus.getStatusName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment ID&#35; " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + eventId + "</strong> " + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Date: " + "</td>" + "<td style='color: #333333; text-decoration-line: line-through; text-decoration-color: red;'>"
                    + "<strong>" + this.startDateString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Time: " + "</td>" + "<td style='color: #333333; text-decoration-line: line-through; text-decoration-color: red;'>"
                    + " <strong>" + this.startTimeString() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Your Account ID&#35;" + "</td style=\"color: #333333;\">" + "<td>" + " <strong>" + this.client.getId() + "</strong>" + "</td>"
                    + "</tr>"
                    + "</tbody>" + "</table>"
                    + "<br><br><br><br><br><br><br><br><br><br><br><br>";
        }
        return defaultMessage;
    }

    @Override
    public String emailClientSubject()
    {
        String eventStatus;
        String eventSubject = " - the service, " + this.title.toUpperCase()
                + " on " + this.startDateString() + " at " + this.startTimeString();

        if ("add".equalsIgnoreCase(this.action) && "new".equalsIgnoreCase(this.actionType))
        {
            eventStatus = "NEW On-Time Appointment";
            return eventStatus + eventSubject;
        }
        else if ("add".equalsIgnoreCase(this.action) && "move".equalsIgnoreCase(this.actionType))
        {
            eventStatus = "Your Appointment has been RE-SCHEDULED";
            return eventStatus + eventSubject;
        }
        else if ("add".equalsIgnoreCase(this.action) && "resize".equalsIgnoreCase(this.actionType))
        {
            eventStatus = "Your Appointment Estimated END TIME has CHANGED";
            return eventStatus + eventSubject;
        }
        else if ("updateStatus".equalsIgnoreCase(this.action) && !"Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            eventStatus = "Appointment Status CHANGE";
            eventSubject = " - the status of your appointment for a " + this.title.toUpperCase() + " has been changed to "
                    + this.serviceStatus.getStatusName().toUpperCase();
            return eventStatus + eventSubject;
        }
        else if ("delete".equalsIgnoreCase(this.action) || "Cancelled".equalsIgnoreCase(this.serviceStatus.getStatusName()))
        {
            eventStatus = "Appointment CANCELLED";
            eventSubject = " - your appointment for a " + this.title.toUpperCase() + " on " + this.startDateString()
                    + " at " + this.startTimeString() + " has been CANCELLED";
            return eventStatus + eventSubject;
        }
        return defaultMessage;
    }

    public Time getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Time startTime)
    {
        this.startTime = startTime;
    }

    public Time getEndTime()
    {

        return endTime;
    }

    public void setEndTime(Time endTime)
    {
        this.endTime = endTime;
    }

    public java.sql.Time sqlStartTime()
    {
        return new java.sql.Time(this.startDate.getTime());
    }

    public java.sql.Time sqlEndTime()
    {
        return new java.sql.Time(this.endDate.getTime());
    }

    public java.sql.Date sqlStartDate()
    {
        return new java.sql.Date(this.startDate.getTime());
    }

    public java.sql.Date sqlEndDate()
    {
        return new java.sql.Date(this.endDate.getTime());
    }

    private Calendar calendarStart()
    {
        Calendar c = calendarInstance();
        c.setTime(sqlStartTime());
        return c;
    }

    private Calendar calendarEnd()
    {
        Calendar c = calendarInstance();
        c.setTime(sqlEndTime());
        return c;
    }

    public int startMonth()
    {
        return calendarStart().get(Calendar.MONTH);
    }

    public int endMonth()
    {
        return calendarEnd().get(Calendar.MONTH);
    }

    public int startDayOfWeek()
    {
        return calendarStart().get(Calendar.DAY_OF_WEEK);
    }

    public int endDayOfWeek()
    {
        return calendarEnd().get(Calendar.DAY_OF_WEEK);
    }

    public int startTimeHour()
    {
        return calendarStart().get(Calendar.HOUR_OF_DAY);
    }

    public int endTimeHour()
    {
        return calendarEnd().get(Calendar.HOUR_OF_DAY);
    }

    public int startTimeMin()
    {
        return calendarStart().get(Calendar.MINUTE);
    }

    public int endTimeMin()
    {
        return calendarEnd().get(Calendar.MINUTE);
    }

    public int startDayOfMonth()
    {
        return calendarStart().get(Calendar.DAY_OF_MONTH);
    }

    public int endDayOfMonth()
    {
        return calendarEnd().get(Calendar.DAY_OF_MONTH);
    }

    public int startYear()
    {
        return calendarStart().get(Calendar.YEAR);
    }

    public int endYear()
    {
        return calendarEnd().get(Calendar.YEAR);
    }

    public Random getRandomCode()
    {
        return randomCode;
    }

    public void setRandomCode(Random randomCode)
    {
        this.randomCode = randomCode;
    }

    public int RandomCode()
    {
        Random random = new Random();
        return random.nextInt(999999);
    }

    private LocalDateTime getStartDateTime()
    {
        return DateUtil.convertDateTimeString(this.startTimestamp);
    }

    public void setStartDateTime(LocalDateTime startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime()
    {
        return DateUtil.convertDateTimeString(this.endTimestamp);
    }

    public void setEndDateTime(LocalDateTime endDateTime)
    {
        this.endDateTime = endDateTime;
    }

    @Override
    public String startTimeString()
    {
        return DateUtil.formatTime(getStartDateTime());
    }

    @Override
    public String startDateString()
    {
        return DateUtil.formatDate(getStartDateTime());
    }

    private String startDateString2()
    {
        return DateUtil.formatDate2(getStartDateTime());
    }

    @Override
    public String endTimeString()
    {
        return DateUtil.formatTime(getEndDateTime());
    }

    @Override
    public String endDateString()
    {
        return DateUtil.formatDate(getEndDateTime());
    }

    public static Calendar calendarInstance()
    {
        return Calendar.getInstance();
    }

    public static Date getCalendarTime()
    {
        Date d = calendarInstance().getTime();
        return d;
    }

}
