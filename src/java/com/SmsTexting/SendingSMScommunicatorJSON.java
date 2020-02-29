/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SmsTexting;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import messages.LogFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;
import store.business.SMScommunicator;
import store.business.SmsAuthenticator;
import store.data.MessagesDB;

/**
 *
 * @author williamdobbs
 */
public class SendingSMScommunicatorJSON implements SmsAuthenticator
{

    // private static final String URL = "https://app.eztexting.com/sending/messages?format=json";
    public static boolean sendSMScommunicator(SMScommunicator msg) throws Exception
    {

//        String convStrArray = StringUtil.convStrArray(msg.getConvertedPhoneNumersString());
//  The list of allowed characters for messages and subjects is: a-z, A-Z, 0-9 and these special characters: .,:;!?()~=+-_\/@$#&%'"
// The following characters count as two characters when used: ~, @, #, %, +, =, /, \, \r\n
        String data = "User=" + SmsAuthenticator.smsUsername() + "&Password=" + SmsAuthenticator.smsPassword() + "&PhoneNumbers[]="
                + msg.getPhoneNumStrArr() + "&Subject=" + msg.getSubject() + "&Message=" + msg.getMessage() + "&StampToSend=" + msg.getStampToSend() + "&MessageTypeID=" + msg.getMessageTypeID();

        URL url = new URL(SmsAuthenticator.smsCredentials().getSmsURL());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        boolean isSmsSent = false;

        try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()))
        {
            wr.write(data);
            wr.flush();

            int responseCode = conn.getResponseCode();

            boolean isSuccessResponse = responseCode < 400;

            InputStream responseStream = isSuccessResponse ? conn.getInputStream() : conn.getErrorStream();
            if (responseStream != null)
            {
                String responseString = IOUtils.toString(responseStream);
                responseStream.close();

                //Use json-lib (http://json-lib.sourceforge.net/) for response processing
                JSONObject response1 = (JSONObject) JSONSerializer.toJSON(responseString);
                JSONObject response = response1.getJSONObject("Response");
                msg.setResponse(responseString);
                msg.setStatus(parseSMSJsonObj(responseString, "Status"));
                msg.setCode(parseSMSJsonObj(responseString, "Code"));
                msg.setMessageId(MessagesDB.insertSmsMsg(msg));

                if (isSuccessResponse)
                {
                    MessagesDB.updateSmsRespnse(response, msg);
                    isSmsSent = true;
                }
                else
                {
                    processErrors(response, msg.getMessageId());
                    isSmsSent = false;
                }
            }
            wr.close();
        }
        return isSmsSent;
    }

    private static void processErrors(JSONObject response, int msgId)
    {
        Object ErrorMessage[] = (Object[]) JSONArray.toArray(response.getJSONArray("Errors"));
        LogFile.smsResError(ErrorMessage);
        JSONArray errorArray = response.getJSONArray("Errors");
        // insert error message into table
        for (int i = 0; i < errorArray.size(); i++)
        {
            String error = errorArray.getString(i);
            MessagesDB.insertSmsErrors(msgId, error);
        }
    }

    private static String parseSMSJsonObj(String responseString, String encodingResponse)
    {
        JSONObject response1 = (JSONObject) JSONSerializer.toJSON(responseString);
        JSONObject response = response1.getJSONObject("Response");
        if (encodingResponse.equals(encodingResponse))
        {
            return response.has(encodingResponse) ? response.getString(encodingResponse) : null;
        }
        return null;
    }
}
