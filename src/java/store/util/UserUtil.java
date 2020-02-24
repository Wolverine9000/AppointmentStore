/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.util;

import com.SmsTexting.BaseObject;
import com.SmsTexting.Encoding;
import com.SmsTexting.SmsTextingConnection;
import com.SmsTexting.SmsTextingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.LogFile;
import store.business.FullCalendar2;
import store.data.CustomerDB;
import static store.util.PhoneUtil.stripNonDigits2;

/**
 *
 * @author whdobbs
 */
public class UserUtil
{

    public static int processUserId(FullCalendar2 fc)
    {
        boolean clientExists;
        int clientId = 0;
        clientExists = CustomerDB.clientExists(fc); // check if client exists
        if (!clientExists)
        {
            clientId = (CustomerDB.insertNewClient(fc)); // if client does not exist, create in database
            if (clientId == 0)
            {
                LogFile.generalLog("UserUtil processUserId", "add client FAILED clientName " + fc.getClient().getFirstName() + " " + fc.getClient().getLastName());
            }
            else
            {
                LogFile.generalLog("UserUtil processUserId", "client ADDED clientName " + fc.getClient().getFirstName() + " " + fc.getClient().getLastName());
            }

        }
        else if (clientExists)
        {
            int updateClient = CustomerDB.update(fc.getClient().getId(), fc.getClient());
            clientId = fc.getClient().getId();
            if (updateClient == 0)
            {
                LogFile.generalLog("UserUtil processUserId", "UPDATE client FAILED clientName "
                        + fc.getClient().getFirstName() + " Client ID = " + fc.getClient().getId());

            }
            else if (updateClient != 0)
            {
                LogFile.generalLog("UserUtil processUserId", "UPDATE client SUCCESS clientName "
                        + fc.getClient().getFirstName() + " Client ID = " + fc.getClient().getId());
            }
        }
        return clientId;
    }

    public static void processSmsUser(FullCalendar2 fc)
    {

        SmsTextingConnection sms = new SmsTextingConnection("whdobbs8128", "hcr-6G6-wgX-GoW", Encoding.JSON);
        try
        {
            List<BaseObject> contact = sms.getContacts(stripNonDigits2(fc.getMobilePhone()), null, null, null, null, null, null, null);
            String contactString = contact.get(0).toString();
            System.out.println("contact is " + contactString);
        }
        catch (SmsTextingException ex)
        {
//            LogFile.smsError(UserUtil.class.getName() + " processSmsUser", "Response Code " + ex.getResponseCode() + " error message " + ex.getMessage(), fc);
            Logger.getLogger(UserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
//            LogFile.smsError(UserUtil.class.getName() + " processSmsUser", ex.getMessage(), fc);
            Logger.getLogger(UserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
