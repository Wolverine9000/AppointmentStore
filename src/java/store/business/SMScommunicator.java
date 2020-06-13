/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.util.ArrayList;
import net.sf.json.JSONArray;
import store.data.MessagesDB;
import static store.util.StringUtil.convertArrayToList;

/**
 *
 * @author whdobbs
 */
public class SMScommunicator extends Communicator implements SystemAccounts
{

    private String recipientsCount;
    private String credits;
    private String code;
    private int statusNumber;
    private String trimmedSubject;
    private String trimmedMessage;
    private int idMessageResponse;
    private JSONArray msgSentGroup;
    private int msgMessageGrpId;
    private String timeToSend;
    private JSONArray phoneNumbers;
    private String phoneNumber;
    boolean isMessageInvite;
    private String response;
    private boolean isAccountActive;
    private boolean groupMessage;
    private String phoneNumStrArr;
    private ArrayList<String> phoneArray;

    public SMScommunicator()
    {

        super(); // call constructor from superclass
        recipientsCount = "";
        credits = "";
        code = "";

        statusNumber = 0;
        trimmedSubject = "";
        trimmedMessage = "";
        idMessageResponse = 0;
        msgSentGroup = null;
        msgMessageGrpId = 0;
        timeToSend = "";
        phoneNumbers = null;
        phoneNumber = "";
        isMessageInvite = false;
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

    public int getStatusNumber()
    {
        return statusNumber;
    }

    public void setStatusNumber(int statusNumber)
    {
        this.statusNumber = statusNumber;
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

    public boolean getIsMessageInvite()
    {
        return isMessageInvite;
    }

    public void setIsMessageInvite(boolean isMessageInvite)
    {
        this.isMessageInvite = isMessageInvite;
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

    public String getTimeToSend()
    {
        return timeToSend;
    }

    public void setTimeToSend(String timeToSend)
    {
        this.timeToSend = timeToSend;
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

    public boolean isIsAccountActive()
    {
        return isAccountActive;
    }

    public void setIsAccountActive(boolean isAccountActive)
    {
        this.isAccountActive = isAccountActive;
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
