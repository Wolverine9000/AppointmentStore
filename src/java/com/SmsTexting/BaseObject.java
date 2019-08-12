package com.SmsTexting;

import java.util.HashMap;
import java.util.List;

public abstract class BaseObject
{

    public String id;

    String getUpdateUrl()
    {
        return getBaseUrl() + "/" + id;
    }

    abstract String getBaseUrl();

    abstract HashMap<String, Object> getParams();

    protected void putList(HashMap<String, Object> res, String key, List<String> vals)
    {
        if (vals != null)
            {
            for (int i = 0; i < vals.size(); i++)
                {
                String val = vals.get(i);
                res.put(key + "[" + i + "]", val);
                }
            }
    }

    static void putNotNull(HashMap<String, Object> res, String key, String val)
    {
        if (val != null)
            {
            res.put(key, val);
            }
    }

    protected boolean hasResponseAfterUpdate()
    {
        return true;
    }
}
