/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author whdobbs
 */
public class ProcessStatus implements Eventer
{

    private boolean processClientCalendar;
    private boolean processAssociateCalendar;
    private boolean processClientSms;
    private boolean processAssociateSms;
    private boolean processClientEmail;
    private boolean processAssociateEmail;
    private boolean processAdminSms;
    private boolean processAdminEmail;
    private boolean processSuperAdminSms;
    private boolean processSuperAdminEmail;
    private boolean validateUser;
    private boolean processCurrentUserId;
    private boolean processNewUserId;
    private int processClientId;
    private boolean processAssociateAvailabilty;
    private boolean insertAssociateAvailability;
    private int eventId;
    public Map<String, Boolean> processResultsArray;

    private static final String PROCESS_FAILED = "Process FAILED";
    private static final String PROCESS_SUCCESSFUL = "Process SUCCESSFUL";

    public ProcessStatus()
    {
        processClientCalendar = false;
        processAssociateCalendar = false;
        processClientSms = false;
        processAssociateSms = false;
        processClientEmail = false;
        processAssociateEmail = false;
        processAdminSms = false;
        processAdminEmail = false;
        processSuperAdminSms = false;
        processSuperAdminEmail = false;
        validateUser = false;
        processCurrentUserId = false;
        processNewUserId = false;
        processClientId = 0;
        processAssociateAvailabilty = false;
        insertAssociateAvailability = false;
        eventId = 0;

        processResultsArray = new HashMap<>();
    }

    public boolean isProcessClientCalendar()
    {
        return processClientCalendar;
    }

    public void setProcessClientCalendar(boolean processClientCalendar)
    {
        this.processClientCalendar = processClientCalendar;
    }

    public boolean isProcessAssociateCalendar()
    {
        return processAssociateCalendar;
    }

    public void setProcessAssociateCalendar(boolean processAssociateCalendar)
    {
        this.processAssociateCalendar = processAssociateCalendar;
    }

    public boolean isProcessClientSms()
    {
        return processClientSms;
    }

    public void setProcessClientSms(boolean processClientSms)
    {
        this.processClientSms = processClientSms;
    }

    public boolean isProcessAssociateSms()
    {
        return processAssociateSms;
    }

    public void setProcessAssociateSms(boolean processAssociateSms)
    {
        this.processAssociateSms = processAssociateSms;
    }

    public boolean isProcessClientEmail()
    {
        return processClientEmail;
    }

    public void setProcessClientEmail(boolean processClientEmail)
    {
        this.processClientEmail = processClientEmail;
    }

    public boolean isProcessAssociateEmail()
    {
        return processAssociateEmail;
    }

    public void setProcessAssociateEmail(boolean processAssociateEmail)
    {
        this.processAssociateEmail = processAssociateEmail;
    }

    public boolean isProcessAdminSms()
    {
        return processAdminSms;
    }

    public void setProcessAdminSms(boolean processAdminSms)
    {
        this.processAdminSms = processAdminSms;
    }

    public boolean isProcessAdminEmail()
    {
        return processAdminEmail;
    }

    public void setProcessAdminEmail(boolean processAdminEmail)
    {
        this.processAdminEmail = processAdminEmail;
    }

    public boolean isProcessSuperAdminSms()
    {
        return processSuperAdminSms;
    }

    public void setProcessSuperAdminSms(boolean processSuperAdminSms)
    {
        this.processSuperAdminSms = processSuperAdminSms;
    }

    public boolean isProcessSuperAdminEmail()
    {
        return processSuperAdminEmail;
    }

    public void setProcessSuperAdminEmail(boolean processSuperAdminEmail)
    {
        this.processSuperAdminEmail = processSuperAdminEmail;
    }

    public boolean isValidateUser()
    {
        return validateUser;
    }

    public void setValidateUser(boolean validateUser)
    {
        this.validateUser = validateUser;
    }

    public boolean isProcessCurrentUserId()
    {
        return processCurrentUserId;
    }

    public void setProcessCurrentUserId(boolean processCurrentUserId)
    {
        this.processCurrentUserId = processCurrentUserId;
    }

    public boolean isProcessNewUserId()
    {
        return processNewUserId;
    }

    public void setProcessNewUserId(boolean processNewUserId)
    {
        this.processNewUserId = processNewUserId;
    }

    public Map<String, Boolean> getProcessResultsArray()
    {
        return processResultsArray;
    }

    public void setProcessResultsArray(Map<String, Boolean> processResultsArray)
    {
        this.processResultsArray = processResultsArray;
    }

    public int getProcessClientId()
    {
        return processClientId;
    }

    public void setProcessClientId(int processClientId)
    {
        this.processClientId = processClientId;
    }

    @Override
    public int getEventId()
    {
        return eventId;
    }

    @Override
    public void setEventId(int eventId)
    {
        this.eventId = eventId;
    }

    public boolean isProcessAssociateAvailabilty()
    {
        return processAssociateAvailabilty;
    }

    public void setProcessAssociateAvailabilty(boolean processAssociateAvailabilty)
    {
        this.processAssociateAvailabilty = processAssociateAvailabilty;
    }

    public boolean isInsertAssociateAvailability()
    {
        return insertAssociateAvailability;
    }

    public void setInsertAssociateAvailability(boolean insertAssociateAvailability)
    {
        this.insertAssociateAvailability = insertAssociateAvailability;
    }

    public void processResults(String process, boolean result)
    {

        if (result == false)
        {
            this.processResultsArray.put(process + " " + PROCESS_FAILED, result);
        }
        if (result == true)
        {
            this.processResultsArray.put(process + " " + PROCESS_SUCCESSFUL, result);
        }
    }

}
