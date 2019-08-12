/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.sql.Timestamp;

/**
 *
 * @author whdobbs
 */
public abstract class Message2
{

    private final int totaRecipients;
    private final int creditsCharged;
    private int messageId;
    private String code;
    private String status;
    private String subject;
    private String msgError;
    private int idMessageResponse;
    private String msgSentGroup;
    private int msgMessageGrpId;
    private Timestamp timestamp;
    private String timeToSend;
    private int sentById;
    private String phoneNumersToSend;
    private boolean isMessageInvite;
    private Associate2 associate;

    public Message2()
    {
        totaRecipients = 0;
        creditsCharged = 0;
        messageId = 0;
        code = "";
        status = "";
        subject = "";
        msgError = "";
        idMessageResponse = 0;
        msgSentGroup = "";
        msgMessageGrpId = 0;
        timestamp = null;
        timeToSend = "";
        sentById = 0;
        phoneNumersToSend = null;
        isMessageInvite = false;
        associate = null;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public void setMsgError(String msgError)
    {
        this.msgError = msgError;
    }

    public void setIdMessageResponse(int idMessageResponse)
    {
        this.idMessageResponse = idMessageResponse;
    }

    public void setMsgSentGroup(String msgSentGroup)
    {
        this.msgSentGroup = msgSentGroup;
    }

    public void setMsgMessageGrpId(int msgMessageGrpId)
    {
        this.msgMessageGrpId = msgMessageGrpId;
    }

    public int getMessageId()
    {
        return messageId;
    }

    public void setMessageId(int messageId)
    {
        this.messageId = messageId;
    }

    public String getCode()
    {
        return code;
    }

    public String getStatus()
    {
        return status;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getMsgError()
    {
        return msgError;
    }

    public int getIdMessageResponse()
    {
        return idMessageResponse;
    }

    public String getMsgSentGroup()
    {
        return msgSentGroup;
    }

    public int getMsgMessageGrpId()
    {
        return msgMessageGrpId;
    }

    public int getTotaRecipients()
    {
        return totaRecipients;
    }

    public int getCreditsCharged()
    {
        return creditsCharged;
    }

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getTimeToSend()
    {
        return timeToSend;
    }

    public void setTimeToSend(String timeToSend)
    {
        this.timeToSend = timeToSend;
    }

    public int getSentById()
    {
        return sentById;
    }

    public void setSentById(int sentById)
    {
        this.sentById = sentById;
    }

    public String getPhoneNumersToSend()
    {
        return phoneNumersToSend;
    }

    public void setPhoneNumersToSend(String phoneNumersToSend)
    {
        this.phoneNumersToSend = phoneNumersToSend;
    }

    public boolean isIsMessageInvite()
    {
        return isMessageInvite;
    }

    public void setIsMessageInvite(boolean isMessageInvite)
    {
        this.isMessageInvite = isMessageInvite;
    }

    public Associate2 getAssociate()
    {
        return associate;
    }

    public void setAssociate(Associate2 associate)
    {
        this.associate = associate;
    }

}
