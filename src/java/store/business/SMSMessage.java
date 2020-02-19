/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import store.data.MessagesDB;
import store.util.DateUtil;
import static store.util.StringUtil.convertArrayToList;

/**
 *
 * @author whdobbs
 */
public abstract class SMSMessage implements SystemAccounts
{

    private String recipientsCount;
    private String credits;
    private int messageId;
    private String code;
    private String status;
    private String statusColor;
    private int statusNumber;
    String subject;
    private String trimmedSubject;
    private String messageTypeID;
    String message;
    private String trimmedMessage;
    private String msgError;
    private int idMessageResponse;
    private JSONArray msgSentGroup;
    private int msgMessageGrpId;
    private Timestamp timestamp;
    private java.sql.Timestamp sqlTimestamp;
    private String stampToSend;
    private String timeToSend;
    private int timeToSendInteger;
    private int sentById;
    private JSONArray phoneNumbers;
    private String phoneNumber;
    boolean isMessageInvite;
    Associate2 associate2;
    private Client client;
    private ArrayList<Client> clients;
    private String response;
    private boolean isClientOfAssociate;
    private boolean isAccountActive;
    private boolean groupMessage;
    private String phoneNumStrArr;
    private ArrayList<String> phoneArray;

    public SMSMessage()
    {

        recipientsCount = "";
        credits = "";
        messageId = 0;
        code = "";
        status = null;
        statusColor = "";
        statusNumber = 0;
        subject = "";
        trimmedSubject = "";
        messageTypeID = "1";
        message = "";
        trimmedMessage = "";
        msgError = "";
        idMessageResponse = 0;
        msgSentGroup = null;
        msgMessageGrpId = 0;
        timestamp = null;
        sqlTimestamp = null;
        stampToSend = "";
        timeToSend = "";
        sentById = 0;
        phoneNumbers = null;
        phoneNumber = "";
        isMessageInvite = false;
        associate2 = null;
        client = null;
        clients = null;
        isClientOfAssociate = false;
        isAccountActive = true;
        groupMessage = false;
        phoneNumStrArr = "";
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
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

    public int getStatusNumber()
    {
        return statusNumber;
    }

    public void setStatusNumber(int statusNumber)
    {
        this.statusNumber = statusNumber;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getTrimmedSubject()
    {

        return trimmedSubject = trimString(trimmedSubject, 13);
    }

    public void setTrimmedSubject(String trimmedSubject)
    {
        this.trimmedSubject = trimmedSubject;
    }

    public String getTrimmedMessage()
    {
        return trimmedMessage = trimString(trimmedMessage, 160);
    }

    public void setTrimmedMessage(String trimmedMessage)
    {
        this.trimmedMessage = trimmedMessage;
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

    public boolean getIsMessageInvite()
    {
        return isMessageInvite;
    }

    public void setIsMessageInvite(boolean isMessageInvite)
    {
        this.isMessageInvite = isMessageInvite;
    }

    public void setMsgError(String msgError)
    {
        this.msgError = msgError;
    }

    public void setIdMessageResponse(int idMessageResponse)
    {
        this.idMessageResponse = idMessageResponse;
    }

    public int getIdMessageResponse()
    {
        return idMessageResponse;
    }

    public void setMsgSentGroup(JSONArray msgSentGroup)
    {
        this.msgSentGroup = msgSentGroup;
    }

    public JSONArray getMsgSentGroup()
    {
        return msgSentGroup;
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

    public String getMsgError()
    {
        return msgError;
    }

    public int getMsgMessageGrpId()
    {
        return msgMessageGrpId;
    }

    public void setRecipientsCount(String recipientsCount)
    {
        this.recipientsCount = recipientsCount;
    }

    public String getRecipientsCount()
    {
        return recipientsCount;
    }

    public void setCredits(String credits)
    {
        this.credits = credits;
    }

    public String getCredits()
    {
        return credits;
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

    public String getStampToSend()
    {
        return stampToSend;
    }

    public void setStampToSend(String stampToSend)
    {
        this.stampToSend = stampToSend;
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

    public void setPhoneNumbers(JSONArray phoneNumers)
    {
        this.phoneNumbers = phoneNumers;
    }

    public JSONArray getPhoneNumbers()
    {
        return phoneNumbers;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
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

    public boolean isIsAccountActive()
    {
        return isAccountActive;
    }

    public void setIsAccountActive(boolean isAccountActive)
    {
        this.isAccountActive = isAccountActive;
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

    public String getResponse()
    {
        return response;
    }

    public void setResponse(String response)
    {
        this.response = response;
    }

    public ArrayList<String> getConvertedPhoneNumersString()
    {
        JSONArray jsonArray = this.phoneNumbers;
        ArrayList<String> phNumArr;
        phNumArr = convertArrayToList(jsonArray);
        return phNumArr;
    }

    private String trimString(String message, int trimLength)
    {
        int messageSize = message.length();
        if (messageSize > trimLength);
        {
            message = message.substring(0, trimLength);
        }
        return message;
    }

    public String subjectDatabase(int c)
    {
        String s = MessagesDB.selectSmsMessage(c);
        return s;
    }

    public String messageDatabase(int r)
    {
        String m = MessagesDB.selectSmsMessage(r);
        return m;
    }

    @Override
    public boolean isAccountActive()
    {
        return false;
    }

    @Override
    public boolean isClientOfAssociate()
    {
        return false;
    }

    public boolean isGroupMessage()
    {
        return groupMessage;
    }

    public void setGroupMessage(boolean groupMessage)
    {
        this.groupMessage = groupMessage;
    }

    public String getPhoneNumStrArr()
    {
        return phoneNumStrArr;
    }

    public void setPhoneNumStrArr(String phoneNumStrArr)
    {
        this.phoneNumStrArr = phoneNumStrArr;
    }

    @Override
    public void activateAccount()
    {
    }

    @Override
    public void deActivateAccount()
    {
    }

    public ArrayList<String> getPhoneArray()
    {
        return phoneArray;
    }

    public void setPhoneArray(ArrayList<String> phoneArray)
    {
        this.phoneArray = phoneArray;
    }

}
