/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @author williamdobbs
 */
public class FileUtil
{

    public static void close(Closeable stream) throws IOException
    {
        try
        {
            if (stream != null)
            {
                stream.close();
            }
        }
        catch (IOException ioe)
        {
            LogFile.databaseError("FileUtil ioe error ", ioe.getMessage(), ioe.toString());
        }
    }
}
