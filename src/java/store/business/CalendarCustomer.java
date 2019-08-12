/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import store.util.DateUtil;

/**
 *
 * @author williamdobbs
 */
public class CalendarCustomer extends Message2 implements Serializable
{

    private int id;
    private int serviceId;
    private int eventId;
    private int serviceTime;
    private String serviceDescription;
    private int month;
    private int day;
    private int date;
    private int year;
    private int startTimeHour;
    private int startTimeMin;
    private int endTimeHour;
    private int endTimeMin;
    private int customerId;
    private String associateName;
    private String associateLastName;
    private int associateId;
    private int associateInfo;
    private Date associateDate;
    private Time associateTime;
    private java.sql.Date sqlCalendarDate;
    private Timestamp startSql;
    private Timestamp endSql;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private boolean allDay;
    private String backgroundColor;
    private String textColor;
    private boolean durationEditable;
    private boolean editable;
    private String color;
    private String notes;
    private ServiceStatus serviceStatus;
    private Associate2 associate2;
    private Client client;
    private String mobilePhone;

    public CalendarCustomer()
    {
        id = 0;
        eventId = 0;
        serviceId = 0;
        serviceDescription = null;
        serviceTime = 0;
        month = 0;
        day = 0;
        date = 0;
        year = 0;
        startTimeHour = 0;
        startTimeMin = 0;
        endTimeHour = 0;
        endTimeMin = 0;
        customerId = 0;
        associateName = null;
        associateLastName = null;
        associateId = 0;
        associateInfo = 0;
        firstName = null;
        lastName = null;
        emailAddress = null;
        allDay = false;
        backgroundColor = null;
        textColor = null;
        durationEditable = true;
        editable = true;
        color = null;
        notes = null;
        startSql = null;
        endSql = null;
        serviceStatus = new ServiceStatus();
        associate2 = null;
        client = null;
        mobilePhone = null;
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

    public String addName()
    {
        String titleFirstNm = this.serviceDescription + ": " + this.firstName;
        return titleFirstNm;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
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

    public ServiceStatus getServiceStatus()
    {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus)
    {
        this.serviceStatus = serviceStatus;
    }

    public boolean isAllDay()
    {
        return allDay;
    }

    public void setAllDay(boolean allDay)
    {
        this.allDay = allDay;
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

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getTimestampFormat()
    {
        DateFormat shortTime = DateFormat.getTimeInstance(DateFormat.SHORT);
        String strTime = shortTime.format(associateTime);
        return strTime;
    }

    public java.sql.Date getSqlCalendarDate()
    {
        return sqlCalendarDate;
    }

    public void setSqlCalendarDate(java.sql.Date sqlCalendarDate)
    {
        this.sqlCalendarDate = sqlCalendarDate;
    }

    public boolean getAllDay()
    {
        return allDay;
    }

    public void setAllDay(Boolean allDay)
    {
        this.allDay = allDay;
    }

    public int getAssociateId()
    {
        return associateId;
    }

    public void setAssociateId(int associateId)
    {
        this.associateId = associateId;
    }

    public int getAssociateInfo()
    {
        return associateInfo;
    }

    public void setAssociateInfo(int associateInfo)
    {
        this.associateInfo = associateInfo;
    }

    public String getAssociateName()
    {
        return associateName;
    }

    public void setAssociateName(String associateName)
    {
        this.associateName = associateName;
    }

    public String getAssociateLastName()
    {
        return associateLastName;
    }

    public void setAssociateLastName(String associateLastName)
    {
        this.associateLastName = associateLastName;
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

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
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

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public Date getAssociateDate()
    {
        return associateDate;
    }

    public void setAssociateDate(Date associateDate)
    {
        this.associateDate = associateDate;
    }

    public Time getAssociateTime()
    {
        return associateTime;
    }

    public void setAssociateTime(Time associateTime)
    {
        this.associateTime = associateTime;
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

    public boolean getDurationEditable()
    {
        return durationEditable;
    }

    public void setDurationEditable(Boolean durationEditable)
    {
        this.durationEditable = durationEditable;
    }

    public boolean getEditable()
    {
        return editable;
    }

    public void setEditable(Boolean editable)
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
}
