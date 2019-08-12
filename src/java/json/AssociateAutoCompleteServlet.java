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
import store.business.Associate2;
import store.data.AssociateDB;

/**
 *
 * @author williamdobbs
 */
public class AssociateAutoCompleteServlet extends HttpServlet
{

    private final HashMap associates;

    public AssociateAutoCompleteServlet()
    {
        this.associates = AssociateDB.selectAllAssociates();
    }

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

        final String targetId = request.getParameter("term");

        final ArrayList<Associate2> results = new ArrayList<>();

        Iterator it = associates.keySet().iterator();

        while (it.hasNext())
            {
            String id = (String) it.next();
            Associate2 associate = (Associate2) associates.get(id);

            if ( // targetId matches first name
                    associate.getFirstName().toLowerCase().contains(targetId.toLowerCase())
                    || // targetId matches last name
                    associate.getLastName().toLowerCase().contains(targetId.toLowerCase())
                    // targetId matches client id
                    || id.equals(targetId))
                {
                results.add(associate);
                }
            }
        // Map real data into JSON
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        if (results.size() > 0)
            {
            response.getWriter().write(new Gson().toJson(results));
            }
        else if (results.isEmpty())
            {
            Associate2 a = new Associate2();
            a.setFirstName("associate not found");
            results.add(a);
            response.getWriter().write(new Gson().toJson(results));
            }
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
