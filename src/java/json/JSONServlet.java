package json;

import store.business.ClientData;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import messages.LogFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import store.business.Client;
import store.data.CustomerDB;

public class JSONServlet extends HttpServlet
{

    private JSONArray customers;
    private JSONObject cust;

    private final ClientData clientData = new ClientData();
    //private final ArrayList<Client> clients = clientData.getClients();
    ArrayList<Client> clients = CustomerDB.selectAllCustomers();
    ArrayList<Client> clientsMore = CustomerDB.selectAllCustomers();

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {

        customers = new JSONArray();
        JSONObject json = new JSONObject();
        String jsonStr = null;
        String action = request.getParameter("action");
        String targetId = request.getParameter("id");

        try
            {
            for (Client cl : clients)
                {
                cust = new JSONObject();
                cust.put("FirstName", cl.getFirstName());
                cust.put("LastName", cl.getLastName());
                cust.put("CustID", cl.getId());
                cust.put("email", cl.getEmail());
                cust.put("Address", cl.getAddress());
                cust.put("City", cl.getCity());
                cust.put("Company", cl.getCompany());
                cust.put("HomePhone", cl.getHomePhone());
                cust.put("WorkPhone", cl.getWorkPhone());

                customers.add(cust);
                }
            Gson gson = new Gson();
            jsonStr = gson.toJson(clientsMore);
            //json.put("cust", cust);
            }
        catch (JSONException jse)
            {
            LogFile.generalLog("JSONServlet ", jse.toString());
            }
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        //response.getWriter().write(json.toString());
        response.getWriter().write(jsonStr);
    }
}
