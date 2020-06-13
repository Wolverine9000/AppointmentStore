package com.SmsTexting;

import java.util.HashMap;

public class IncomingMessage extends BaseObject
{

    private static final String URL = "/incoming-messages";

    public String phoneNumber;
    public String subject;
    public String message;
    public int isNew;
    public String folderId;
    public String contactId;
    public String receivedOn;

    public IncomingMessage(String id)
    {
        this.id = id;
    }

    public IncomingMessage(String id, String phoneNumber, String subject, String message, int aNew, String folderId, String contactId, String receivedOn)
    {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
        this.message = message;
        isNew = aNew;
        this.folderId = folderId;
        this.contactId = contactId;
        this.receivedOn = receivedOn;
    }

    @Override
    public String toString()
    {
        return "Group{"
                + "id='" + id + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", subject='" + subject + '\''
                + ", message=" + message
                + ", isNew=" + isNew
                + ", folderId=" + folderId
                + ", contactId=" + contactId
                + ", receivedOn=" + receivedOn
                + '}';
    }

    @Override
    protected String getBaseUrl()
    {
        return URL;
    }

    @Override
    HashMap<String, Object> getParams()
    {
        return new HashMap<>();
    }
}
