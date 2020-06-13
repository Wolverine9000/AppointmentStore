/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package associate;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Associate2;
import store.util.DateUtil;

/**
 *
 * @author williamdobbs
 */
public class GlobalSettingsServlet extends HttpServlet
{

    static final String clear = "-------";
    private String url;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Associate2 associateRecord = (Associate2) session.getAttribute("associateRecord");

        if (associateRecord != null)
            {
            // get global selections
            String gloMT1 = request.getParameter("gloMT1");
            String gloMT2 = request.getParameter("gloMT2");
            String gloAT1 = request.getParameter("gloAT1");
            String gloAT2 = request.getParameter("gloAT2");

            String[] global = request.getParameterValues("global");

            if (global != null)
                {
                for (String global1 : global)
                    {
                    // initalize global day states to false
                    boolean everyday = false;
                    boolean weekdays = false;
                    boolean weekends = false;
                    boolean tueToSat = false;
                    // iterate through global day states and set appropriate time attributes
                    String dayString = global1;
                    if (dayString.equalsIgnoreCase("everyday"))
                        {
                        String[] nDays = new String[7];
                        everyday = true;
                        nDays[0] = "sun";
                        nDays[1] = "mon";
                        nDays[2] = "tue";
                        nDays[3] = "wed";
                        nDays[4] = "thu";
                        nDays[5] = "fri";
                        nDays[6] = "sat";
                        boolean daySelections = DateUtil.dayNorCheckbox(nDays, request);
                        request.setAttribute("everday", everyday);
                        // setAttribute for sunday times
                        request.setAttribute("sunMT1", gloMT1);
                        request.setAttribute("sunMT2", gloMT2);
                        request.setAttribute("sunAT1", gloAT1);
                        request.setAttribute("sunAT2", gloAT2);
                        // setAttribute for saturday times
                        request.setAttribute("satMT1", gloMT1);
                        request.setAttribute("satMT2", gloMT2);
                        request.setAttribute("satAT1", gloAT1);
                        request.setAttribute("satAT2", gloAT2);
                        // setAttribute for monday times
                        request.setAttribute("monMT1", gloMT1);
                        request.setAttribute("monMT2", gloMT2);
                        request.setAttribute("monAT1", gloAT1);
                        request.setAttribute("monAT2", gloAT2);
                        // setAttribute for tuesday times
                        request.setAttribute("tueMT1", gloMT1);
                        request.setAttribute("tueMT2", gloMT2);
                        request.setAttribute("tueAT1", gloAT1);
                        request.setAttribute("tueAT2", gloAT2);
                        // setAttribute for wednesday times
                        request.setAttribute("wedMT1", gloMT1);
                        request.setAttribute("wedMT2", gloMT2);
                        request.setAttribute("wedAT1", gloAT1);
                        request.setAttribute("wedAT2", gloAT2);
                        // setAttribute for thursday times
                        request.setAttribute("thuMT1", gloMT1);
                        request.setAttribute("thuMT2", gloMT2);
                        request.setAttribute("thuAT1", gloAT1);
                        request.setAttribute("thuAT2", gloAT2);
                        // setAttribute for friday times
                        request.setAttribute("friMT1", gloMT1);
                        request.setAttribute("friMT2", gloMT2);
                        request.setAttribute("friAT1", gloAT1);
                        request.setAttribute("friAT2", gloAT2);
                        }
                    if (dayString.equalsIgnoreCase("weekdays"))
                        {
                        String[] nDays = new String[5];
                        weekdays = true;
                        nDays[0] = "mon";
                        nDays[1] = "tue";
                        nDays[2] = "wed";
                        nDays[3] = "thu";
                        nDays[4] = "fri";
                        boolean daySelections = DateUtil.dayNorCheckbox(nDays, request);
                        request.setAttribute("weekdays", weekdays);
                        // setAttribute for sunday times
                        request.setAttribute("sunMT1", clear);
                        request.setAttribute("sunMT2", clear);
                        request.setAttribute("sunAT1", clear);
                        request.setAttribute("sunAT2", clear);
                        // setAttribute for sunday times
                        request.setAttribute("satMT1", clear);
                        request.setAttribute("satMT2", clear);
                        request.setAttribute("satAT1", clear);
                        request.setAttribute("satAT2", clear);
                        // setAttribute for monday times
                        request.setAttribute("monMT1", gloMT1);
                        request.setAttribute("monMT2", gloMT2);
                        request.setAttribute("monAT1", gloAT1);
                        request.setAttribute("monAT2", gloAT2);
                        // setAttribute for tuesday times
                        request.setAttribute("tueMT1", gloMT1);
                        request.setAttribute("tueMT2", gloMT2);
                        request.setAttribute("tueAT1", gloAT1);
                        request.setAttribute("tueAT2", gloAT2);
                        // setAttribute for wednesday times
                        request.setAttribute("wedMT1", gloMT1);
                        request.setAttribute("wedMT2", gloMT2);
                        request.setAttribute("wedAT1", gloAT1);
                        request.setAttribute("wedAT2", gloAT2);
                        // setAttribute for thursday times
                        request.setAttribute("thuMT1", gloMT1);
                        request.setAttribute("thuMT2", gloMT2);
                        request.setAttribute("thuAT1", gloAT1);
                        request.setAttribute("thuAT2", gloAT2);
                        // setAttribute for friday times
                        request.setAttribute("friMT1", gloMT1);
                        request.setAttribute("friMT2", gloMT2);
                        request.setAttribute("friAT1", gloAT1);
                        request.setAttribute("friAT2", gloAT2);
                        }
                    else if (dayString.equalsIgnoreCase("weekends"))
                        {
                        String[] nDays = new String[2];
                        weekends = true;
                        nDays[0] = "sun";
                        nDays[1] = "sat";
                        boolean daySelections = DateUtil.dayNorCheckbox(nDays, request);
                        request.setAttribute("weekends", weekends);
                        // setAttribute for sunday times
                        request.setAttribute("sunMT1", gloMT1);
                        request.setAttribute("sunMT2", gloMT2);
                        request.setAttribute("sunAT1", gloAT1);
                        request.setAttribute("sunAT2", gloAT2);
                        // setAttribute for saturday times
                        request.setAttribute("satMT1", gloMT1);
                        request.setAttribute("satMT2", gloMT2);
                        request.setAttribute("satAT1", gloAT1);
                        request.setAttribute("satAT2", gloAT2);
                        // setAttribute for monday times
                        request.setAttribute("monMT1", clear);
                        request.setAttribute("monMT2", clear);
                        request.setAttribute("monAT1", clear);
                        request.setAttribute("monAT2", clear);
                        // setAttribute for tuesday times
                        request.setAttribute("tueMT1", clear);
                        request.setAttribute("tueMT2", clear);
                        request.setAttribute("tueAT1", clear);
                        request.setAttribute("tueAT2", clear);
                        // setAttribute for wednesday times
                        request.setAttribute("wedMT1", clear);
                        request.setAttribute("wedMT2", clear);
                        request.setAttribute("wedAT1", clear);
                        request.setAttribute("wedAT2", clear);
                        // setAttribute for thursday times
                        request.setAttribute("thuMT1", clear);
                        request.setAttribute("thuMT2", clear);
                        request.setAttribute("thuAT1", clear);
                        request.setAttribute("thuAT2", clear);
                        // setAttribute for friday times
                        request.setAttribute("friMT1", clear);
                        request.setAttribute("friMT2", clear);
                        request.setAttribute("friAT1", clear);
                        request.setAttribute("friAT2", clear);
                        }
                    else if (dayString.equalsIgnoreCase("tueToSat"))
                        {
                        String[] nDays = new String[5];
                        tueToSat = true;
                        nDays[0] = "tue";
                        nDays[1] = "wed";
                        nDays[2] = "thu";
                        nDays[3] = "fri";
                        nDays[4] = "sat";
                        boolean daySelections = DateUtil.dayNorCheckbox(nDays, request);
                        request.setAttribute("tueToSat", tueToSat);
                        // setAttribute for sunday times
                        request.setAttribute("sunMT1", clear);
                        request.setAttribute("sunMT2", clear);
                        request.setAttribute("sunAT1", clear);
                        request.setAttribute("sunAT2", clear);
                        // setAttribute for saturday times
                        request.setAttribute("satMT1", gloMT1);
                        request.setAttribute("satMT2", gloMT2);
                        request.setAttribute("satAT1", gloAT1);
                        request.setAttribute("satAT2", gloAT2);
                        // setAttribute for monday times
                        request.setAttribute("monMT1", clear);
                        request.setAttribute("monMT2", clear);
                        request.setAttribute("monAT1", clear);
                        request.setAttribute("monAT2", clear);
                        // setAttribute for tuesday times
                        request.setAttribute("tueMT1", gloMT1);
                        request.setAttribute("tueMT2", gloMT2);
                        request.setAttribute("tueAT1", gloAT1);
                        request.setAttribute("tueAT2", gloAT2);
                        // setAttribute for wednesday times
                        request.setAttribute("wedMT1", gloMT1);
                        request.setAttribute("wedMT2", gloMT2);
                        request.setAttribute("wedAT1", gloAT1);
                        request.setAttribute("wedAT2", gloAT2);
                        // setAttribute for thursday times
                        request.setAttribute("thuMT1", gloMT1);
                        request.setAttribute("thuMT2", gloMT2);
                        request.setAttribute("thuAT1", gloAT1);
                        request.setAttribute("thuAT2", gloAT2);
                        // setAttribute for friday times
                        request.setAttribute("friMT1", gloMT1);
                        request.setAttribute("friMT2", gloMT2);
                        request.setAttribute("friAT1", gloAT1);
                        request.setAttribute("friAT2", gloAT2);
                        }
                    }
                }
            else
                {
                // setAttribute for sunday times
                request.setAttribute("sunMT1", gloMT1);
                request.setAttribute("sunMT2", gloMT2);
                request.setAttribute("sunAT1", gloAT1);
                request.setAttribute("sunAT2", gloAT2);
                // setAttribute for monday times
                request.setAttribute("monMT1", gloMT1);
                request.setAttribute("monMT2", gloMT2);
                request.setAttribute("monAT1", gloAT1);
                request.setAttribute("monAT2", gloAT2);
                // setAttribute for tuesday times
                request.setAttribute("tueMT1", gloMT1);
                request.setAttribute("tueMT2", gloMT2);
                request.setAttribute("tueAT1", gloAT1);
                request.setAttribute("tueAT2", gloAT2);
                // setAttribute for wednesday times
                request.setAttribute("wedMT1", gloMT1);
                request.setAttribute("wedMT2", gloMT2);
                request.setAttribute("wedAT1", gloAT1);
                request.setAttribute("wedAT2", gloAT2);
                // setAttribute for thursday times
                request.setAttribute("thuMT1", gloMT1);
                request.setAttribute("thuMT2", gloMT2);
                request.setAttribute("thuAT1", gloAT1);
                request.setAttribute("thuAT2", gloAT2);
                // setAttribute for friday times
                request.setAttribute("friMT1", gloMT1);
                request.setAttribute("friMT2", gloMT2);
                request.setAttribute("friAT1", gloAT1);
                request.setAttribute("friAT2", gloAT2);
                // setAttribute for saturday times
                request.setAttribute("satMT1", gloMT1);
                request.setAttribute("satMT2", gloMT2);
                request.setAttribute("satAT1", gloAT1);
                request.setAttribute("satAT2", gloAT2);
                }
            // setAttribute for global times
            request.setAttribute("gloMT1", gloMT1);
            request.setAttribute("gloMT2", gloMT2);
            request.setAttribute("gloAT1", gloAT1);
            request.setAttribute("gloAT2", gloAT2);
            url = "/associate/scheduler";
            }
        else
            {
            url = "/associate/associateLogin";
            }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
    {
        doPost(request, response);
    }
}
