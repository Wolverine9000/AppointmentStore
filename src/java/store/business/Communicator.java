/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import store.util.DateUtil;

/**
 *
 * @author williamdobbs
 */
public abstract class Communicator
{

    Associate2 associate2;
    private Client client;
    private ArrayList<Client> clients;
    String subject;
    private int sentById;
    private int timeToSendInteger;
    private String messageTypeID;
    private String msgError;
    String message;
    private boolean isClientOfAssociate;
    private String stampToSend;
    private Timestamp timestamp;
    private java.sql.Timestamp sqlTimestamp;
    private int messageId;
    private int status_id;
    private String statusMsg;
    private String status;
    private String statusColor;

    public Communicator()
    {
        subject = "";
        sentById = 0;
        client = new Client();
        clients = null;
        associate2 = new Associate2();
        msgError = "";
        isClientOfAssociate = false;
        stampToSend = "";
        timestamp = null;
        sqlTimestamp = null;
        messageId = 0;
        status = null;
        statusColor = "";
        status_id = 0;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getMessageTypeID()
    {
        return messageTypeID;
    }

    public void setMessageTypeID(String MessageTypeID)
    {
        this.messageTypeID = MessageTypeID;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setMsgError(String msgError)
    {
        this.msgError = msgError;
    }

    public String getMsgError()
    {
        return msgError;
    }

    public int getTimeToSendInteger()
    {
        return timeToSendInteger = Integer.parseInt(this.stampToSend);
    }

    public void setTimeToSendInteger(int timeToSendInteger)
    {
        this.timeToSendInteger = timeToSendInteger;
    }

    public int getSentById()
    {
        return sentById;
    }

    public void setSentById(int sentById)
    {
        this.sentById = sentById;
    }

    public Associate2 getAssociate2()
    {
        return associate2;
    }

    public void setAssociate2(Associate2 associate2)
    {
        this.associate2 = associate2;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public ArrayList<Client> getClients()
    {
        return clients;
    }

    public void setClients(ArrayList<Client> clients)
    {
        this.clients = clients;
    }

    public boolean isIsClientOfAssociate()
    {
        return isClientOfAssociate;
    }

    public void setIsClientOfAssociate(boolean isClientOfAssociate)
    {
        this.isClientOfAssociate = isClientOfAssociate;
    }

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getStampToSend()
    {
        return stampToSend;
    }

    public void setStampToSend(String stampToSend)
    {
        this.stampToSend = stampToSend;
    }

    public Timestamp getSqlTimestamp()
    {
        int t = Integer.parseInt(stampToSend);
        java.util.Date time = new java.util.Date((long) t * 1000);
        return sqlTimestamp = new java.sql.Timestamp(time.getTime());
    }

    public String getTimeStampString()
    {
        return DateUtil.createDateString(timestamp);
    }

    public int getMessageId()
    {
        return messageId;
    }

    public void setMessageId(int messageId)
    {
        this.messageId = messageId;
    }

    public int getStatus_id()
    {
        return status_id;
    }

    public void setStatus_id(int status_id)
    {
        this.status_id = status_id;
    }

    public String getStatusMsg()
    {
        if (this.status_id == 1)
        {
            this.statusMsg = "Sent";
        }
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg)
    {
        this.statusMsg = statusMsg;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public String getStatusColor()
    {
        return statusColor;
    }

    public void setStatusColor(String statusColor)
    {
        this.statusColor = statusColor;
    }

}
