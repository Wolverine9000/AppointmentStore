/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import calendar.controller.AdminData;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import javax.servlet.http.HttpSession;
import store.business.Associate2;

/**
 *
 * @author williamdobbs
 */
public class AdminServlet extends HttpServlet
{

@Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
                
        // get user id number and key id characters 
        String userId = request.getParameter("userId");
        String key1 = request.getParameter("key1");
        String key2 = request.getParameter("key2");
        String key3 = request.getParameter("key3");
        String key4 = request.getParameter("key4");
        
        Associate2 associate;
        associate = (Associate2) session.getAttribute("associateRecord");
        boolean evtPostError = false;
        
        if (associate == null)
            {
                evtPostError = true;
            }
        else
            {
               if ("system-email".equals(key3))
               {
                evtPostError = AdminData.processAdmin(key1, key2, key3, key4, userId, request);
               }
            }
        
         
        if (evtPostError == true)
        {
               if ("system-email".equals(key3))
            {
                response.setStatus(SC_BAD_REQUEST);
            }
               else
            {
                response.setStatus(SC_INTERNAL_SERVER_ERROR);
            }
        }
    
    }

}
