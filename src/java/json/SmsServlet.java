/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import com.SmsTexting.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Associate2;
import store.business.FullCalendar2;

/**
 *
 * @author whdobbs
 */
public class SmsServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        String msgData = request.getParameter("data");
        String title = request.getParameter("title");

        Associate2 associate;
        associate = (Associate2) session.getAttribute("associateRecord");

        Gson gson = new Gson();
        // create the type for the collection. In this case define that the collection is of type Dataset
        java.lang.reflect.Type datasetListType = new TypeToken<Collection<FullCalendar2>>()
        {
        }.getType();

        List<FullCalendar2> fcArray = gson.fromJson(msgData, datasetListType);
        FullCalendar2 fc1 = fcArray.get(0);
        int msgId = fc1.getMessageId();
        SmsTextingConnection sms = new SmsTextingConnection("whdobbs8128", "hcr-6G6-wgX-GoW", Encoding.JSON);

        List<BaseObject> groups = null;
        try
        {
            groups = sms.getGroups("Test", "asc", "10", "1");
        }
        catch (SmsTextingException ex)
        {
            Logger.getLogger(SmsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(SmsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (FullCalendar2 fc : fcArray)
        {
            int messageId = fc.getMessageId();
            for (BaseObject msgs : groups)
            {
                int msgInt = Integer.parseInt(msgs.id);
                if (messageId == msgInt)
                {
                    System.out.println("message id is " + msgInt);
                }

            }
        }

    }

}
