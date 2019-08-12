/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import messages.LogFile;
import org.apache.commons.fileupload.FileItem;
import store.business.Associate2;
import store.data.AssociateDB;
import store.util.UpDnFileUtil;

public class CommonsFileUploadServlet extends HttpServlet
{

    private static final String TMP_DIR_PATH = "../temp";
    private File tmpDir;
    private static final String DESTINATION_DIR_PATH = "/img/associates";
    private File destinationDir;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        Associate2 associate;
        associate = (Associate2) session.getAttribute("associateRecord");
        String urlPath = request.getServletPath();
        String url;
        FileItem item = null;

        tmpDir = new File(TMP_DIR_PATH);
        if (!tmpDir.isDirectory())
            {
            try
                {
                throw new ServletException(TMP_DIR_PATH + " is not a directory");
                }
            catch (ServletException ex)
                {
                LogFile.generalLog("CommonsFileUploadServlet TMP_DIR_PATH", ex.toString());
                }
            }
        String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
        try
            {
            destinationDir = new File(realPath);
            }
        catch (NullPointerException npe)
            {
            LogFile.generalLog("CommonsFileUploadServlet DESTINATION_DIR_PATH", npe.toString());
            }

        if (destinationDir.isDirectory() && null != destinationDir)
            {
            item = UpDnFileUtil.upLoadPicture(request, destinationDir, tmpDir);
            }

        if (item == null)
            {
//            associate.setImgUpl(Boolean.FALSE);
//            AssociateDB.updateAssociatePhoto(associate);
            String messageError = "An error occurred uploading your Photo.";
            request.setAttribute("messageError", messageError);
            }
        else
            {
            associate.setImgUpl(Boolean.TRUE);
            AssociateDB.updateAssociatePhoto(associate);
            String messaeSuccess = "Your Photo was successfully uploaded.";
            request.setAttribute("messaeSuccess", messaeSuccess);
            }
        if ("/associate/associatePhotoUpl".equals(urlPath))
            {
            url = "/associate/associateSettings";
            }
        else if ("/moblie/associatePhotoUpl".equals(urlPath))
            {
            url = "/moblie/associateSettings.jsp";
            }
        else
            {
            url = "/associate/associateLogin";
            }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        try
            {
            dispatcher.forward(request, response);
            }
        catch (ServletException | IOException ex)
            {
            Logger.getLogger(CommonsFileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException
    {
        doPost(request, response);
    }
}
