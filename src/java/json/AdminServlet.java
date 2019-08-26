/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import calendar.controller.CalendarData;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Associate2;
import store.business.SMSAppointmentMessage;

/**
 *
 * @author williamdobbs
 */
public class AdminServlet extends HttpServlet
{

@Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
        Associate2 associate = null;
        ArrayList<SMSAppointmentMessage> msgs = null;
        
        // get calendar id number and key id characters 
        String userId = request.getParameter("userId");
        String key1 = request.getParameter("key1");
        String key2 = request.getParameter("key2");
        String key3 = request.getParameter("key3");
        String jsonData = null;
        // retrieve messages
        msgs = CalendarData.messages(key1, key2, key3, userId);
        
         Gson gson = new Gson();
         
         jsonData = gson.toJson(msgs);
         
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            response.getWriter().write(jsonData);            
        }
        catch (IOException e)
        {
            LogFile.databaseError("AdminServlet  ", e.toString(), e.getMessage());
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    
    }

}
