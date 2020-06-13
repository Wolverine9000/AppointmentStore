package com.SmsTexting;

import java.util.HashMap;

public class Folder extends BaseObject
{

    private static final String URL = "/messages-folders";

    public String name;

    public Folder(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Folder(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Group{"
                + "id='" + id + '\''
                + ", phoneNumber='" + name + '\''
                + '}';
    }

    @Override
    protected boolean hasResponseAfterUpdate()
    {
        return false;
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
        putNotNull(res, "Name", name);
        return res;
    }
}
