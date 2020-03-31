/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import calendar.controller.CalendarData;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import javax.servlet.http.HttpSession;
import service.data.ServicesData;
import store.business.Associate2;
import store.business.CalendarStatus;

/**
 *
 * @author williamdobbs
 */
public class FullCalPostServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException

    {
        HttpSession session = request.getSession();

        String json = request.getParameter("calEvents");
        String title = request.getParameter("title");
        String data = request.getParameter("data");

        Associate2 associate = (Associate2) session.getAttribute("associateRecord");
        boolean evtPostError = false;
        boolean isMessage = false;
        CalendarStatus c = new CalendarStatus();

        if (title != null)
        {
            isMessage = title.startsWith("sendMessage");
        }

        if ("updateMemberLevel".equals(title) || "updateClient".equals(title) || "add".equals(title)
                || "updateNormalSvc".equals(title) || isMessage == true || "deactivateClients".equals(title))
        {
            if (associate == null)
            {
                evtPostError = true;
            }
            else
            {
                evtPostError = CalendarData.postClientData(data, request, title, associate);
            }
        }
        else if ("updateAssociate".equals(title) || "updateAssociateAlerts".equals(title) || "updateAssoPwd".equals(title)
                || "securityQandA".equals(title) || "updateDefaultCalendarView".equals(title) || "updateDefaultCalendarTimes".equals(title)
                || "updateMemberSettings".equals(title) || "updateService".equals(title))
        {
            if (null != title)
            {
                switch (title)
                {
                    case "updateAssociate": // update associate info
                    case "updateAssoPwd": // update associate password
                    case "updateDefaultCalendarView": // update associate default calendar view
                    case "updateDefaultCalendarTimes": // update associate default calendar times
                    case "updateMemberSettings": // update member level settings
                        evtPostError = CalendarData.associateInfo(data, request, title);
                        break;
                    case "updateAssociateAlerts":
                        evtPostError = CalendarData.associateAlerts(data, request);
                        break;
                    case "securityQandA":
                        evtPostError = CalendarData.updateSecrityQuestions(data);
                        break;
                    case "updateService":
                        evtPostError = ServicesData.updateService(data, request, title, associate);
                        break;
                }
            }
        }
        else if ("invite".equals(title))
        {
            evtPostError = CalendarData.smsInviteRequest(data, request, title, associate);
        }
        else
        {
            evtPostError = CalendarData.postCalendarData(json, request, associate);
        }
        if (evtPostError == true)
        {
            if ("add".equals(title))
            {
                response.setStatus(SC_BAD_REQUEST);
            }
            else
            {
                response.setStatus(SC_INTERNAL_SERVER_ERROR);
            }
        }
        Gson gson = new Gson();
        String jsonCalendarStatus = gson.toJson(c);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
// Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object
        out.print(jsonCalendarStatus);
        out.flush();

    }
}
