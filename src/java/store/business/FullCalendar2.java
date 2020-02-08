/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import store.data.CustomerDB;
import store.util.DateUtil;

/**
 *
 * @author williamdobbs
 */
public class FullCalendar2 implements Serializable
{

    private int customerId;
    private int serviceId;
    private int eventId;
    private int serviceTime;
    private String serviceDescription;
    private Services services;
    private Associate2 associate2;
    private Client client;
    private String start;
    private String end;
    private String title;
    private String action;
    private Boolean notifyClient;
    private Boolean restoreTime;
    private Boolean newClient;
    private boolean isAssociate;
    private int statusId;
    private String userType;
    private ArrayList<Integer> cancelEvts;
    private ArrayList<MemberExtras> memberLevels;
    private String message;
    private int id;
    private int month;
    private int day;
    private int date;
    private int year;
    private int startTimeHour;
    private int startTimeMin;
    private int endTimeHour;
    private int endTimeMin;
    private java.sql.Date sqlCalendarDate;
    private Timestamp startSql;
    private Timestamp endSql;
    private boolean allDay;
    private String backgroundColor;
    private String textColor;
    private boolean durationEditable;
    private boolean editable;
    private String color;
    private String notes;
    private String mobilePhone;

    private final Map<String, Object> otherProperties = new HashMap<>();

    public FullCalendar2()
    {
        eventId = 0;
        serviceId = 0;
        serviceDescription = null;
        serviceTime = 0;
        customerId = 0;
        associate2 = null;
        client = null;
        start = null;
        end = null;
        title = null;
        notifyClient = false;
        restoreTime = false;
        newClient = null;
        action = "";
        statusId = 5;
        isAssociate = false;
        userType = "";
        memberLevels = null;
        message = "no message";
        // strip client first name from calendar title

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

    public int getEventId()
    {
        return eventId;
    }

    public void setEventId(int eventId)
    {
        this.eventId = eventId;
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

    public int getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(int serviceId)
    {
        this.serviceId = serviceId;
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

    public int getStatusId()
    {
        return statusId;
    }

    public void setStatusId(int statusId)
    {
        this.statusId = statusId;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStartDate()
    {
        return start;
    }

    public void setStartDate(String startDate)
    {
        this.start = startDate;
    }

    public String getEndDate()
    {
        return end;
    }

    public void setEndDate(String endDate)
    {
        this.end = endDate;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public Boolean getNotifyClient()
    {
        return notifyClient;
    }

    public void setNotifyClient(Boolean notifyClient)
    {
        this.notifyClient = notifyClient;
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

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int getDate()
    {
        return date;
    }

    public void setDate(int date)
    {
        this.date = date;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getStartTimeHour()
    {
        return startTimeHour;
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

    public Date getSqlCalendarDate()
    {
        return sqlCalendarDate;
    }

    public void setSqlCalendarDate(Date sqlCalendarDate)
    {
        this.sqlCalendarDate = sqlCalendarDate;
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

    public String getTimestampFormat()
    {
        DateFormat shortTime = DateFormat.getTimeInstance(DateFormat.SHORT);
        String strTime = shortTime.format(associateTime);
        return strTime;
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

    public void setDate(java.sql.Date date)
    {
        sqlCalendarDate = new java.sql.Date(date.getTime());
    }

    public String stripName(String t)
    {
        if (t != null)
        {
            int i = t.indexOf(":", 0);
            if (i != -1) // if client first name part of service title, strip client name from title
            {
                return t.substring(0, i);
            }
        }
        return t;
    }

    public String addName()
    {
        String titleFirstNm = this.serviceDescription + ": " + this.client.getFirstName();
        return titleFirstNm;
    }

    public void setAssociateTime(Time time)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
