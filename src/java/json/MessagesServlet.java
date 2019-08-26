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
import messages.LogFile;
import store.business.Associate2;
import store.business.SMSAppointmentMessage;

/**
 *
 * @author whdobbs
 */
public class MessagesServlet extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {        
        Associate2 associate = null;
        ArrayList<SMSAppointmentMessage> msgs = null;
        
        // get calendar id number and key id characters 
        String userId = request.getParameter("userId");
        String key1 = request.getParameter("key1");
        String key2 = request.getParameter("key2");
        String key3 = request.getParameter("key3");
        String jsonMessage = null;
        // retrieve messages
        msgs = CalendarData.messages(key1, key2, key3, userId);
        
         Gson gson = new Gson();
         
         jsonMessage = gson.toJson(msgs);
         
         response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            response.getWriter().write(jsonMessage);            
        }
        catch (IOException e)
        {
            LogFile.databaseError("MessagesServlet  ", e.toString(), e.getMessage());
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    
    }

}
