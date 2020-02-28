package json;

import calendar.controller.CalendarData;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import store.business.Associate2;
import store.business.Category;
import store.business.FullCalendar2;
import store.business.SMSAppointmentCommunicator;
import store.business.SMSMemberInviteCommunicator;
import store.business.ServiceStatus;
import store.business.Services;
import store.data.AssociateDB;
import store.data.CalendarDB;
import store.data.CustomerDB;
import store.data.ProductDB;

/**
 *
 * @author williamdobbs test
 */
public class FullCalendarServlet extends HttpServlet
{

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Associate2 associate = null;

        ArrayList<FullCalendar2> fc2 = new ArrayList<>();
        FullCalendar2 fc2Info = new FullCalendar2();
        ArrayList<Services> svcs = new ArrayList<>();
        ArrayList<ServiceStatus> svcStatusArr = new ArrayList<>();
        ArrayList<Associate2> associates = new ArrayList<>();
        ArrayList<Category> cat = new ArrayList<>();
        HashMap<Integer, String> m = null;
        ArrayList<FullCalendar2> messages = null;
        SMSMemberInviteCommunicator m1 = null;
        ArrayList<SMSMemberInviteCommunicator> m2 = null;
        ArrayList<SMSAppointmentCommunicator> m3 = null;

        // get calendar id number and key id characters
        String title = request.getParameter("title");
        String key = request.getParameter("key");
        String altKey = request.getParameter("altKey");
        String jsonAppointment;

        if (title == null)
        {
            // select all calendar appointments
            fc2 = CalendarData.clientData();
//            fc2 = CalendarDB.selectCalendarAll();
        }
        if (null != key)
        {
            switch (key)
            {
                case "all":
                    fc2 = CalendarData.clientData();
                    break;
                case "ci":
                    fc2 = CalendarData.clientInfo(key, title);
                    break;
                case "getList":
                    fc2 = CalendarData.clientInfo(key, title);
                    break;
                case "clientInfo":
                    int clientId = Integer.parseInt(title);
                    fc2Info = CustomerDB.selectClientFc(clientId);
                    break;
                case "ml":
                    m = CustomerDB.selectLevels();
                    break;
                case "svc":
                    svcs = ProductDB.selectAllServices();
                    break;
                case "svcStatus":
                    svcStatusArr = CalendarDB.serviceStatusAll();
                    break;
                case "cat":
                    cat = ProductDB.selectCategoryServices();
                    break;
                case "cc":
                    fc2 = CalendarData.clientCalendar(title);
                    break;
                case "ca":
                    fc2 = CalendarData.associateCalendar(title);
                    break;
                case "aa":
                    fc2 = CalendarData.associateCalendar(title);
                    break;
                case "at":
                    fc2 = CalendarData.AssociateTimes(title); // list Associate available times
                    break;
                case "calEvts":
                    int userID;
                    userID = Integer.parseInt(request.getParameter("userID"));
                    int numOfEvts;
                    numOfEvts = Integer.parseInt(request.getParameter("numOfEvts"));
                    fc2 = CalendarDB.selectRecentVisits(userID, numOfEvts);
                    break;
                case "futureCalEvts":
                    int id;
                    id = Integer.parseInt(request.getParameter("userID"));
                    int numOfFutureEvts;
                    numOfFutureEvts = Integer.parseInt(request.getParameter("numOfEvts"));
                    fc2 = CalendarDB.selectFutureVisits(id, numOfFutureEvts);
                    break;
                case "allA":
                    associates = AssociateDB.selectAssociatesAll();
                    break;
                case "assoInfo":
                    // get calendar record - if null, could mean all cookies are being blocked
                    associate = (Associate2) session.getAttribute("associateRecord");
                    int assoId = associate.getId();
                    associate = AssociateDB.selectAssociateInfo(assoId);
                    break;
                case "assoProfile":
                    // get calendar record - if null, could mean all cookies are being blocked
                    associate = (Associate2) session.getAttribute("associateRecord");
                    int assoId2 = associate.getId();
                    associate = CalendarData.associateProfile(assoId2);
                    break;
//                case "retrieveMessages":
//                    m3 = CalendarData.messages(key, title);
//                    break;
                case "retrieveMsgInvites":
                    m2 = CalendarData.inviteMessages(key, title, altKey);
                    break;
                case "selectMsgInviteRequest":
                    m1 = CalendarData.selectInviteMessage(title, altKey);
                    break;
                case "checkRequest":
                    m2 = CalendarData.isClientOfAssociate(altKey, title);
                    break;
                default:
                    // select all calendar appointments
                    fc2 = CalendarData.clientData();
                    break;
            }
        }
        Gson gson = new Gson();

        if ("ml".equals(key) && "mLevels".equals(title))
        {
            //Convert from Java to JSON
            jsonAppointment = gson.toJson(m);
        }
        else if ("svc".equals(key) && "services".equals(title))
        {
            //Convert from Java to JSON
            jsonAppointment = gson.toJson(svcs);
        }
        else if ("svcStatus".equals(key))
        {
            //Convert from Java to JSON
            jsonAppointment = gson.toJson(svcStatusArr);
        }
        else if ("cat".equals(key) && "category".equals(title))
        {
            //Convert from Java to JSON
            jsonAppointment = gson.toJson(cat);
        }
        else if ("allA".equals(key))
        {
            //Convert from Java to JSON
            jsonAppointment = gson.toJson(associates);
        }
        else if ("clientInfo".equals(key))
        {
            //Convert from Java to JSON
            jsonAppointment = gson.toJson(fc2Info);
        }
        else if ("assoInfo".equals(key) || "assoProfile".equals(key))
        {
            //Convert from Java to JSON
            jsonAppointment = gson.toJson(associate);
        }
        else if ("retrieveMessages".equals((key)))
        {
            jsonAppointment = gson.toJson(m3);
        }
        else if ("retrieveMsgInvites".equals((key)) || "checkRequest".equals((key)))
        {
            jsonAppointment = gson.toJson(m2);
        }
        else if ("selectMsgInviteRequest".equals((key)))
        {
            jsonAppointment = gson.toJson(m1);
        }
        else
        {
            //Convert FullCalendar from Java to JSON
            jsonAppointment = gson.toJson(fc2);
        }

        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            response.getWriter().write(jsonAppointment);
        }
        catch (IOException e)
        {
            LogFile.databaseError("FullCalendarServlet  ", e.toString(), e.getMessage());
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
