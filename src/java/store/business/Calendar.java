/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import store.util.DateUtil;

/**
 *
 * @author whdobbs
 */
public abstract class Calendar
{

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

    public Calendar()
    {

        id = 0;
        month = 0;
        day = 0;
        date = 0;
        year = 0;
        startTimeHour = 0;
        startTimeMin = 0;
        endTimeHour = 0;
        endTimeMin = 0;
        allDay = false;
        backgroundColor = null;
        textColor = null;
        durationEditable = true;
        editable = true;
        color = null;
        notes = null;
        startSql = null;
        endSql = null;
        mobilePhone = null;

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
}
