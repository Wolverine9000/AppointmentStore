
import com.SmsTexting.Encoding;
import com.SmsTexting.Group;
import com.SmsTexting.SmsTextingConnection;
import com.SmsTexting.SmsTextingException;

/**
 * Demonstration of Group usage.
 */
public class GroupExamples
{

    public static void main(String[] args) throws Exception
    {

        try
        {
            System.out.println("Group JSON example");
            SmsTextingConnection sms = new SmsTextingConnection("whdobbs8128", "hcr-6G6-wgX-GoW", Encoding.JSON);

            System.out.println("Get groups from group Honey Lovers: " + sms.getGroups("Name", "asc", "10", "1"));

            Group group = new Group("Tubby Bears", "A bear, however hard he tries, grows tubby without exercise");
            group = (Group) sms.create(group);
            System.out.println("Group create:" + group);

            group = sms.getGroup(group.id);
            System.out.println("Group get:" + group);

            group = (Group) sms.update(group);
            System.out.println("Group update:" + group);

            sms.delete(group);
            //try to get exception - delete already deleted item
            sms.delete(group);
        }
        catch (SmsTextingException e)
        {
            System.out.println("SmsTexting error code:" + e.getResponseCode() + "; messages:" + e.getMessage());
        }

    }

}
