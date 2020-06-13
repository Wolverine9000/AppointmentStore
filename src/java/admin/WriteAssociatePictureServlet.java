/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.data.ImageDB;

/**
 *
 * @author williamdobbs
 */
public class WriteAssociatePictureServlet extends HttpServlet
{

    private String url;
    private String notFoundMessage;
    private int numberOfCustomers;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
      {
        HttpSession session = request.getSession();

        String productsDir =
                "/Volumes/Viper250/Users/williamdobbs/Desktop/pictures/";
        String filePath = "";
        int byteCount = 0;

        // Read 3 images from file and write to database
//        filePath = productsDir + "8601_cover.jpg";
//        byteCount = ImageDB.writeImage(1, filePath);


        filePath = productsDir + "NSTexturedFullScreenBackgroundColor.png";
        byteCount = ImageDB.writeImage(2, filePath);
        notFoundMessage = byteCount + " bytes written to the database.";
//
//        filePath = productsDir + "jr01_cover.jpg";
//        byteCount = ProductDB.writeImage(3, filePath);
//        notFoundMessage = byteCount + " bytes written to the database.";
//
        // Read 1 image from database and write to file
        filePath = productsDir + "NSTexturedFullScreenBackgroundColor.png";
        byteCount = ImageDB.readImage(2, filePath);
        notFoundMessage = byteCount + " bytes read from the database.";

        if (byteCount == 0)
            {
            notFoundMessage = byteCount + " bytes written to the database.";
            }
        else
            {
            notFoundMessage = byteCount + " bytes successfully written to the database.";
            }
        request.setAttribute("notFoundMessage", notFoundMessage);


        url = "/admin/Index.jsp";

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);
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
