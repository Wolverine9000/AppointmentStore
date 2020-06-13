
import com.SmsTexting.Encoding;
import com.SmsTexting.Folder;
import com.SmsTexting.SmsTextingConnection;
import com.SmsTexting.SmsTextingException;

/**
 * Demonstration of Folder usage.
 */
public class FolderExamples
{

    public static void main(String[] args) throws Exception
    {

        try
        {
            System.out.println("Folder JSON example");
            SmsTextingConnection sms = new SmsTextingConnection("whdobbs8128", "hcr-6G6-wgX-GoW", Encoding.JSON);

            System.out.println("Get folders : " + sms.getFolders());
            Folder folder = new Folder(null, "Customers2");
            folder = (Folder) sms.create(folder);
            System.out.println("Folder create:" + folder);

            String folderId = folder.id;
            folder = sms.getFolder(folderId);
            System.out.println("Folder get:" + folder);

            folder.id = folderId;
            folder.name = "test";
            sms.update(folder);
            System.out.println("Folder update");
            System.out.println("Folder delete");
            sms.delete(folder);
            //try to get exception - delete already deleted item
            System.out.println("Folder delete2");
            sms.delete(folder);
        }
        catch (SmsTextingException e)
        {
            System.out.println("SmsTexting error code:" + e.getResponseCode() + "; messages:" + e.getMessage());
        }
    }

}
