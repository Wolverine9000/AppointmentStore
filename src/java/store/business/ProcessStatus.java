/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

/**
 *
 * @author whdobbs
 */
public abstract class ProcessStatus
{

    boolean processClientCalendar;
    boolean processAssociateCalendar;
    boolean processClientSms;
    boolean processAssociateSms;
    boolean processClientEmail;
    boolean processAssociateEmail;
    boolean processAdminSms;
    boolean processAdminEmail;
    boolean processSuperAdminSms;
    boolean processSuperAdminEmail;
    boolean validateNewUser;
    boolean processUserId;

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
        processUserId = false;
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

    public boolean isProcessUserId()
    {
        return processUserId;
    }

    public void setProcessUserId(boolean processUserId)
    {
        this.processUserId = processUserId;
    }

}
