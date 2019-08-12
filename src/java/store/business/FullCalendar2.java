/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import store.data.CustomerDB;

/**
 *
 * @author williamdobbs
 */
public class FullCalendar2 extends CalendarCustomer
{

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

    private final Map<String, Object> otherProperties = new HashMap<>();

    public FullCalendar2()
    {
        super(); // call constructor of CalendarCustomer superclass
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
    }

    public FullCalendar2(String title, String startDate, String endDate, int id, boolean allDay)
    {
        this.start = startDate;
        this.end = endDate;
        this.title = title;
    }

// strip client first name from calendar title
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

}
