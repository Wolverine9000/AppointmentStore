package com.SmsTexting;

import java.util.HashMap;
import java.util.List;

public class Contact extends BaseObject
{

    private static final String URL = "/contacts";

    public String phoneNumber;
    public String firstName;
    public String lastName;
    public String email;
    public String note;
    public String source;
    public List<String> groups;
    public String createdAt;

    public Contact(String id, String phoneNumber, String firstName, String lastName,
            String email, String note, String source, List<String> groups, String createdAt)
    {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.note = note;
        this.source = source;
        this.groups = groups;
        this.createdAt = createdAt;
    }

    public Contact(String phoneNumber, String firstName, String lastName, String email, String note, List<String> groups)
    {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.note = note;
        this.groups = groups;
    }

    public Contact(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Contact{"
                + "id='" + id + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", email='" + email + '\''
                + ", note='" + note + '\''
                + ", source='" + source + '\''
                + ", groups=" + groups
                + ", createdAt='" + createdAt + '\''
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
        HashMap<String, Object> res = new HashMap<>();
        putNotNull(res, "PhoneNumber", phoneNumber);
        putNotNull(res, "FirstName", firstName);
        putNotNull(res, "LastName", lastName);
        putNotNull(res, "Email", email);
        putNotNull(res, "Note", note);
        putList(res, "Groups", groups);
        return res;
    }
}
