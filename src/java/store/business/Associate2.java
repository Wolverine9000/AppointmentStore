/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import store.util.DateUtil;

/**
 *
 * @author williamdobbs
 */
public class Associate2 extends User
{

    private int associateAvailabilityId;
    private int associateAvailId;
    private int associateAvailYear;
    private int associateAvailMonth;
    private int associateAvailDay;
    private int associateAvailDate;
    private int associateAvailStartTimeHour;
    private int associateAvailEndTimeMin;
    private String associateAvailName;
    private Date associateAvailDatestamp;
    private Time associateAvailTime;
    private Timestamp assocAvailTimeStamp;
    private Timestamp assocAvailEndTimeStamp;
    private boolean adminUser;
    private boolean superAdminUser;
    private int smsSent;
    private int smsSentRange;
    private int smsCredits;
    private Timestamp morningTimeInSql;
    private Timestamp morningTimeOutSql;
    private Timestamp afternoonTimeInSql;
    private Timestamp afternoonTimeOutSql;
    private String morningTimeIn;
    private String morningTimeOut;
    private String afternoonTimeIn;
    private String afternoonTimeOut;
    private ArrayList<String> adminLevels;
    private HashMap<String, Integer> memberSettings;
    private Time associateTime;

    public Associate2()
    {
        super(); // call constructor of User superclasss
        associateAvailabilityId = 0;
        associateAvailId = 0;
        associateAvailYear = 0;
        associateAvailMonth = 0;
        associateAvailDay = 0;
        associateAvailDate = 0;
        associateAvailStartTimeHour = 0;
        associateAvailEndTimeMin = 0;
        smsSent = 0;
        smsSentRange = 0;
        smsCredits = 0;
        associateAvailName = "";
        assocAvailTimeStamp = null;
        assocAvailEndTimeStamp = null;
        adminUser = false;
        superAdminUser = false;
        associateTime = null;
    }

    public HashMap<String, Integer> getMemberSettings()
    {
        return memberSettings;
    }

    public void setMemberSettings(HashMap<String, Integer> memberSettings)
    {
        this.memberSettings = memberSettings;
    }

    public Timestamp getMorningTimeInSql()
    {
        return morningTimeInSql;
    }

    public void setMorningTimeInSql(Timestamp morningTimeInSql)
    {
        this.morningTimeInSql = morningTimeInSql;
    }

    public Timestamp getMorningTimeOutSql()
    {
        return morningTimeOutSql;
    }

    public void setMorningTimeOutSql(Timestamp morningTimeOutSql)
    {
        this.morningTimeOutSql = morningTimeOutSql;
    }

    public Timestamp getAfternoonTimeInSql()
    {
        return afternoonTimeInSql;
    }

    public void setAfternoonTimeInSql(Timestamp afternoonTimeInSql)
    {
        this.afternoonTimeInSql = afternoonTimeInSql;
    }

    public Timestamp getAfternoonTimeOutSql()
    {
        return afternoonTimeOutSql;
    }

    public void setAfternoonTimeOutSql(Timestamp afternoonTimeOutSql)
    {
        this.afternoonTimeOutSql = afternoonTimeOutSql;
    }

    public String getMorningTimeIn()
    {
        return morningTimeIn;
    }

    public String getMorningTimeInStr()
    {
        String morInString = DateUtil.createDateString(morningTimeInSql);
        return morInString;
    }

    public void setMorningTimeIn(String morningTimeIn)
    {
        this.morningTimeIn = morningTimeIn;
    }

    public String getMorningTimeOut()
    {
        return morningTimeOut;
    }

    public String getMorningTimeOutStr()
    {
        String morOutString = DateUtil.createDateString(morningTimeOutSql);
        return morOutString;
    }

    public void setMorningTimeOut(String morningTimeOut)
    {
        this.morningTimeOut = morningTimeOut;
    }

    public String getAfternoonTimeIn()
    {
        return afternoonTimeIn;
    }

    public String getAfternoonTimeInStr()
    {
        String aftInString = DateUtil.createDateString(afternoonTimeInSql);
        return aftInString;
    }

    public void setAfternoonTimeIn(String afternoonTimeIn)
    {
        this.afternoonTimeIn = afternoonTimeIn;
    }

    public String getAfternoonTimeOut()
    {
        return afternoonTimeOut;
    }

    public String getAfternoonTimeOutStr()
    {
        String aftOutString = DateUtil.createDateString(afternoonTimeOutSql);
        return aftOutString;
    }

    public void setAfternoonTimeOut(String afternoonTimeOut)
    {
        this.afternoonTimeOut = afternoonTimeOut;
    }

    public int getSmsSent()
    {
        return smsSent;
    }

    public void setSmsSent(int smsSent)
    {
        this.smsSent = smsSent;
    }

    public int getSmsSentRange()
    {
        return smsSentRange;
    }

    public void setSmsSentRange(int smsSentRange)
    {
        this.smsSentRange = smsSentRange;
    }

    public int getSmsCredits()
    {
        return smsCredits;
    }

    public void setSmsCredits(int smsCredits)
    {
        this.smsCredits = smsCredits;
    }

    public ArrayList<String> getAdminLevels()
    {
        return adminLevels;
    }

    public void setAdminLevels(ArrayList<String> adminLevels)
    {
        this.adminLevels = adminLevels;
    }

    public String getTimestampFormat()
    {
        DateFormat shortTime = DateFormat.getTimeInstance(DateFormat.SHORT);
        String t = shortTime.format(associateAvailTime);
        return t;
    }

    public String getTimestampStr()
    {
        String startDateString = DateUtil.createDateString(assocAvailTimeStamp);
        return startDateString;
    }

    public String getEndTimestampStr()
    {
        String endDateString = DateUtil.createDateString(assocAvailEndTimeStamp);
        return endDateString;
    }

    public Timestamp getAssocAvailEndTimeStamp()
    {
        return assocAvailEndTimeStamp;
    }

    public void setAssocAvailEndTimeStamp(Timestamp assocAvailEndTimeStamp)
    {
        this.assocAvailEndTimeStamp = assocAvailEndTimeStamp;
    }

    public Time getAssociateAvailTimestamp()
    {
        return associateAvailTime;
    }

    public void setAssociateAvailTimestamp(Time associateAvailTimestamp)
    {
        this.associateAvailTime = associateAvailTimestamp;
    }

    public Time getAssociateAvailTime()
    {
        return associateAvailTime;
    }

    public void setAssociateAvailTime(Time associateAvailTime)
    {
        this.associateAvailTime = associateAvailTime;
    }

    public Timestamp getAssocAvailTimeStamp()
    {
        return assocAvailTimeStamp;
    }

    public void setAssocAvailTimeStamp(Timestamp assocAvailTimeStamp)
    {
        this.assocAvailTimeStamp = assocAvailTimeStamp;
    }

    public String getDatetampFormat()
    {
        DateFormat shortDate = DateFormat.getDateInstance(DateFormat.SHORT);
        String d = shortDate.format(associateAvailDatestamp);
        return d;
    }

    public Date getAssociateAvailDatestamp()
    {
        return associateAvailDatestamp;
    }

    public void setAssociateAvailDatestamp(Date associateAvailDatestamp)
    {
        this.associateAvailDatestamp = associateAvailDatestamp;
    }

    public int getAssociateAvailDate()
    {
        return associateAvailDate;
    }

    public void setAssociateAvailDate(int associateAvailDate)
    {
        this.associateAvailDate = associateAvailDate;
    }

    public int getAssociateAvailDay()
    {
        return associateAvailDay;
    }

    public void setAssociateAvailDay(int associateAvailDay)
    {
        this.associateAvailDay = associateAvailDay;
    }

    public int getAssociateAvailEndTimeMin()
    {
        return associateAvailEndTimeMin;
    }

    public void setAssociateAvailEndTimeMin(int associateAvailEndTimeMin)
    {
        this.associateAvailEndTimeMin = associateAvailEndTimeMin;
    }

    public int getAssociateAvailId()
    {
        return associateAvailId;
    }

    public void setAssociateAvailId(int associateAvailId)
    {
        this.associateAvailId = associateAvailId;
    }

    public int getAssociateAvailMonth()
    {
        return associateAvailMonth;
    }

    public void setAssociateAvailMonth(int associateAvailMonth)
    {
        this.associateAvailMonth = associateAvailMonth;
    }

    public String getAssociateAvailName()
    {
        return associateAvailName;
    }

    public void setAssociateAvailName(String associateAvailName)
    {
        this.associateAvailName = associateAvailName;
    }

    public int getAssociateAvailStartTimeHour()
    {
        return associateAvailStartTimeHour;
    }

    public void setAssociateAvailStartTimeHour(int associateAvailStartTimeHour)
    {
        this.associateAvailStartTimeHour = associateAvailStartTimeHour;
    }

    public int getAssociateAvailYear()
    {
        return associateAvailYear;
    }

    public void setAssociateAvailYear(int associateAvailYear)
    {
        this.associateAvailYear = associateAvailYear;
    }

    public int getAssociateAvailabilityId()
    {
        return associateAvailabilityId;
    }

    public void setAssociateAvailabilityId(int associateAvailabilityId)
    {
        this.associateAvailabilityId = associateAvailabilityId;
    }

    public boolean isAdminUser()
    {
        return adminUser;
    }

    public void setAdminUser(boolean adminUser)
    {
        this.adminUser = adminUser;
    }

    public boolean isSuperAdminUser()
    {
        return superAdminUser;
    }

    public void setSuperAdminUser(boolean superAdminUser)
    {
        this.superAdminUser = superAdminUser;
    }

    public String getStartTimeFormat()
    {
        NumberFormat nf = NumberFormat.getInstance();
        String amPM = "p";
        String startTimeFormat = nf.format(associateAvailStartTimeHour) + amPM;

        if (this.associateAvailStartTimeHour == 12)
        {
            startTimeFormat = nf.format(associateAvailStartTimeHour) + amPM;
        }
        else if (associateAvailStartTimeHour > 12)
        {
            startTimeFormat = nf.format(associateAvailStartTimeHour - 12) + amPM;
        }
        else
        {
            amPM = "a";
            startTimeFormat = nf.format(associateAvailStartTimeHour) + amPM;
        }
        return startTimeFormat;
    }

    public Time getAssociateTime()
    {
        return associateTime;
    }

    public void setAssociateTime(Time associateTime)
    {
        this.associateTime = associateTime;
    }

}
