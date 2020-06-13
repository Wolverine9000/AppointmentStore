
import com.SmsTexting.BaseObject;
import com.SmsTexting.Encoding;
import com.SmsTexting.IncomingMessage;
import com.SmsTexting.SmsTextingConnection;
import com.SmsTexting.SmsTextingException;
import java.util.List;

/**
 * Demonstration of IncomingMessage usage.
 */
public class IncomingMessageExamples
{

    public static void main(String[] args) throws Exception
    {

        try
        {
            System.out.println("IncomingMessage JSON example");
            SmsTextingConnection sms = new SmsTextingConnection("whdobbs8128", "hcr-6G6-wgX-GoW", Encoding.JSON);

            List<BaseObject> messages = sms.getIncomingMessages(null, null, "Message", "asc", "40", "1");
            System.out.println("Get incomingMessages: " + messages);

            String messageId = messages.get(0).id;

//            System.out.println("Move message to folder");
//            sms.moveMessageToFolder(messageId, "Test");
            IncomingMessage incomingMessage = sms.getIncomingMessage(messageId);
            System.out.println("IncomingMessage get:" + incomingMessage);

            System.out.println("Delete message");
            sms.delete(incomingMessage);

            //try to get exception - delete already deleted item
            System.out.println("Delete message2");
            sms.delete(incomingMessage);
        }
        catch (SmsTextingException e)
        {
            System.out.println("SmsTexting error code:" + e.getResponseCode() + "; messages:" + e.getMessage());
        }

    }
}
