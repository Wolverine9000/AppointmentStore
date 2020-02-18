/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SmsTexting;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.LogFile;
import net.sf.json.JSONArray;
import store.business.SMSMemberInviteMessage;
import store.data.AssociateDB;
import store.data.CustomerDB;
import store.util.DateUtil;
import store.util.StringUtil;

/**
 *
 * @author whdobbs
 */
public class ProcessNewClient
{

    public static void processClientInvite(String message, String mobilePh)
    {
        boolean isSMSMsgSent = false;
        SMSMemberInviteMessage c = new SMSMemberInviteMessage();
        ArrayList<String> phArr = new ArrayList<>();
        c.setPhoneArray(phArr);
        c.setPhoneNumStrArr(StringUtil.mobilePhToSMS(mobilePh));
        c.setPhoneNumber(mobilePh);
        JSONArray jsArray = new JSONArray();
        jsArray.add(c.getPhoneNumber());
        c.setPhoneNumbers(jsArray);

        if (message.toLowerCase().contains("y".toLowerCase()) || message.toLowerCase().contains("yes".toLowerCase()))
        {
            c = CustomerDB.selectClientInvite(c);
            if ("no error".equals(c.getMsgError()))
            {
                String pn = c.getPhoneNumber();
                int as = c.getAssociate2().getId();
                c.setClient(CustomerDB.isClientOfAssociate(pn, as));
                c.setAssociate2(AssociateDB.selectAssociateInfo(as));
                String sendNow = DateUtil.dateNowLong();
                c.setTimeToSend(sendNow);
                c.setMessageTypeID("1");

                if (c.getClient() != null)
                {
                    /* update client request
                    --- request status keys---
                    0 = pending
                    1 = accepted
                    2 = declined
                    3 = already an active client for asscociate
                    4 = expired
                     */
                    CustomerDB.updateClientInviteRequest(3, c);
                    try
                    {
                        // send sms message reponse to client they are already a member
                        c.setMessage(c.getClient().getFirstName() + " " + c.messageDatabase(1));
                        c.setSubject(c.messageDatabase(4));

                        isSMSMsgSent = SendingSMSMessagesJSON.sendSMSMessage(c);
                        //Update
                        if (!isSMSMsgSent)
                        {
                            LogFile.smsError(ProcessNewClient.class.getName(), c.getMessage(), null);
                        }
                    }
                    catch (Exception ex)
                    {
                        LogFile.smsError(ProcessNewClient.class.getName(), c.getMessage() + " " + ex.getMessage(), null);
                    }
                }
                else
                {
                    // update client request
                    CustomerDB.updateClientInviteRequest(1, c);
                    // send sms mressage reponse to inform  client they have been accepted
                    c.setMessage(c.messageDatabase(3));
                    try
                    {
                        isSMSMsgSent = SendingSMSMessagesJSON.sendSMSMessage(c);
                        if (!isSMSMsgSent)
                        {
                            LogFile.smsError(ProcessNewClient.class.getName(), c.getMessage(), null);
                        }
                    }
                    catch (Exception ex)
                    {
                        LogFile.smsError(ProcessNewClient.class.getName(), c.getMessage() + " " + ex.getMessage(), null);
                    }
                }
            }
        }
        else if (message.toLowerCase().contains("N".toLowerCase()) || message.toLowerCase().contains("No".toLowerCase()))
        {
            // update client request they declined membership
            CustomerDB.updateClientInviteRequest(mobilePh, 3);
            c = CustomerDB.selectClientInvite(c);
            if (c != null)
            {
                c.setAssociate2(AssociateDB.selectAssociateInfo(c.getAssociate2().getId()));
                c.setMessage(c.messageDatabase(2) + " " + c.getAssociate2().getFirstName());
                c.setMessageTypeID("1");
                try
                {
                    isSMSMsgSent = SendingSMSMessagesJSON.sendSMSMessage(c);
                }
                catch (Exception ex)
                {
                    Logger.getLogger(ProcessNewClient.class.getName()).log(Level.SEVERE, null, ex);
                    LogFile.smsError(ProcessNewClient.class.getName(), c.getMessage() + " " + ex.getMessage(), null);
                }
                if (!isSMSMsgSent)
                {
                    LogFile.smsError(ProcessNewClient.class.getName(), c.getMessage(), null);
                }
            }
        }
    }

    private static ArrayList<String> addPhoneToList(String mobilePh)
    {
        ArrayList<String> ph = new ArrayList();
        ph.add(mobilePh);
        return ph;
    }
}
