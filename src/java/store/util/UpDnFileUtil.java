/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import messages.LogFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author williamdobbs
 */
public class UpDnFileUtil
{

    public static FileItem upLoadPicture(HttpServletRequest request,
            File relavtivePath, File tmpDir)
    {
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 1 MB

        // Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(tmpDir);
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
        try
        {
            FileItem item = null;
            List items = uploadHandler.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext())
            {
                item = (FileItem) itr.next();

                // Write file to the ultimate location.
                File file = new File(relavtivePath, item.getFieldName());
                item.write(file);
            }
            return item;
        }
        catch (FileUploadException fue)
        {
            LogFile.databaseError("upLoadPicture file FileUploadException error ", fue.getMessage(), fue.toString());
            return null;
        }
        catch (Exception ex)
        {
            LogFile.databaseError("upLoadPicture Exception error ", ex.getMessage(), ex.toString());
            return null;
        }
    }
}
