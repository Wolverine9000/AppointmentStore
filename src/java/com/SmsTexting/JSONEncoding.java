package com.SmsTexting;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * JSON protocol support.
 */
class JSONEncoding extends Encoding
{

    @Override
    String getEncodingParam()
    {
        return "format=json";
    }

    @Override
    String parseErrors(String encodedString)
    {
        //Use json-lib (http://json-lib.sourceforge.net/) for response processing
        JSONObject response1 = (JSONObject) JSONSerializer.toJSON(encodedString);
        JSONObject response = response1.getJSONObject("Response");
        Object ErrorMessage[] = (Object[]) JSONArray.toArray(response.getJSONArray("Errors"));

        String res = "";
        for (Object aErrorMessage : ErrorMessage)
        {
            res += "; " + aErrorMessage;
        }
        if (res.length() > 1)
        {
            res = res.substring(2);
        }
        return res;
    }

    @Override
    BaseObject parseObjectResponse(Class<BaseObject> aClass, String encodedString)
    {
        //Use json-lib (http://json-lib.sourceforge.net/) for response processing
        JSONObject response1 = (JSONObject) JSONSerializer.toJSON(encodedString);
        JSONObject response = response1.getJSONObject("Response");
        JSONObject entry = response.getJSONObject("Entry");
        return parseEntry(aClass, entry);
    }

    private BaseObject parseEntry(Class<BaseObject> aClass, JSONObject entry)
    {
        if (aClass.equals(Contact.class))
        {
            return new Contact(entry.getString("ID"), entry.getString("PhoneNumber"), entry.getString("FirstName"),
                    entry.getString("LastName"), entry.getString("Email"), entry.getString("Note"), entry.getString("Source"),
                    convertArrayToList(entry.optJSONArray("Groups")),
                    entry.getString("CreatedAt"));
        }
        else if (aClass.equals(Group.class))
        {
            return new Group(entry.getString("ID"), entry.getString("Name"), entry.getString("Note"), entry.getInt("ContactCount"));
        }
        else if (aClass.equals(Folder.class))
        {
            return new Folder(entry.has("ID") ? entry.getString("ID") : null, entry.has("Name") ? entry.getString("Name") : null);
        }
        else if (aClass.equals(IncomingMessage.class))
        {
            return new IncomingMessage(entry.getString("ID"), entry.getString("PhoneNumber"), entry.getString("Subject"), entry.getString("Message"), entry.getBoolean("New") ? 1 : 0, entry.getString("FolderID"), entry.getString("ContactID"), entry.getString("ReceivedOn"));
        }

        return null;
    }

    @Override
    List<BaseObject> parseObjectsResponse(Class<BaseObject> aClass, String encodedString)
    {
        List<BaseObject> res = new ArrayList<>();
        //Use json-lib (http://json-lib.sourceforge.net/) for response processing
        JSONObject response1 = (JSONObject) JSONSerializer.toJSON(encodedString);
        JSONObject response = response1.getJSONObject("Response");
        JSONArray entries = response.getJSONArray("Entries");
        for (int i = 0; i < entries.size(); i++)
        {
            JSONObject entry = entries.getJSONObject(i);
            res.add(parseEntry(aClass, entry));
        }
        return res;
    }

    private List<String> convertArrayToList(JSONArray jsonArray)
    {
        List<String> res = null;
        if (jsonArray != null)
        {
            res = new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++)
            {
                res.add(jsonArray.getString(i));
            }
        }
        return res;
    }
}
