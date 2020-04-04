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
public class ProcessStatus
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
    private boolean validateNewUser;
    private boolean processCurrentUserId;
    private boolean processNewUserId;
    private int processClientId;
    public Map<String, Boolean> processErrorArray;

    private static final String PROCESS_FAILED = "Process FAILED";
    private static final String PROCESS_SUCCESSFUL = "Process UCCESSFUL";

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
        validateNewUser = false;
        processCurrentUserId = false;
        processNewUserId = false;
        processClientId = 0;
        processErrorArray = new HashMap<>();
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

    public boolean isValidateNewUser()
    {
        return validateNewUser;
    }

    public void setValidateNewUser(boolean validateNewUser)
    {
        this.validateNewUser = validateNewUser;
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

    public Map<String, Boolean> getProcessErrorArray()
    {
        return processErrorArray;
    }

    public void setProcessErrorArray(Map<String, Boolean> processErrorArray)
    {
        this.processErrorArray = processErrorArray;
    }

    public int getProcessClientId()
    {
        return processClientId;
    }

    public void setProcessClientId(int processClientId)
    {
        this.processClientId = processClientId;
    }

    public void processErrors(String process, boolean result)
    {

        if (result == false)
        {
            this.processErrorArray.put(process + " " + PROCESS_FAILED, result);
        }
        if (result == true)
        {
            this.processErrorArray.put(process + " " + PROCESS_SUCCESSFUL, result);
        }
    }
}
