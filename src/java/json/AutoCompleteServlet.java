/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Associate2;
import store.business.Client;
import store.business.Services;
import store.data.AssociateDB;
import store.data.CustomerDB;

/**
 *
 * @author williamdobbs
 */
public class AutoCompleteServlet extends HttpServlet
{

    //private final ClientData clientData = new ClientData();
    // private final HashMap clients = clientData.getClientsHashMap();
    // private final HashMap clients = CustomerDB.selectClients();
    private Associate2 associate;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        
        HttpSession session = request.getSession();
        final String targetId = request.getParameter("term");
        
        ArrayList<Client> results = new ArrayList<>();
        HashMap clients = CustomerDB.selectClients();
        
        Iterator it = clients.keySet().iterator();
        
        while (it.hasNext())
        {
            String id = (String) it.next();
            Client client = (Client) clients.get(id);
            
            if (client.getFirstName().toLowerCase().contains(targetId.toLowerCase())
                    || client.getLastName().toLowerCase().contains(targetId.toLowerCase())
                    || id.equals(targetId)
                    || client.getMobilePhone().contains(targetId)
                    || client.getEmail().toLowerCase().contains(targetId.toLowerCase()))
            {
                if (client.getServiceNormalid() != 0)
                {
                    Services s = AssociateDB.selectService(client.getServiceNormalid());
                    client.setServiceNormal(s);
                }
                associate = AssociateDB.selectAssociateInfo(client.getPreferredAssociateId());
                if (associate != null)
                {
                    client.setPreferredAssociateName(associate.getFirstName());
                    client.setAssociateInfo(associate);
                    client.getMemberLevels();
                }
                results.add(client);
            }
        }
        if (results.isEmpty())
        {
            Client c = new Client();
            c.setFirstName("client not found");
            results.add(c);
        }
        // Map real data into JSON
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(new Gson().toJson(results));
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
