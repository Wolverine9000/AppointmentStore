
import com.SmsTexting.Contact;
import com.SmsTexting.Encoding;
import com.SmsTexting.SmsTextingConnection;
import com.SmsTexting.SmsTextingException;

/**
 * Demonstration of Contacts usage.
 */
public class ContactExamples
{

    public static void main(String[] args) throws Exception
    {

        try
            {
            System.out.println("Contact JSON example");
            SmsTextingConnection sms = new SmsTextingConnection("whdobbs8128", "hcr-6G6-wgX-GoW", Encoding.JSON);

            System.out.println("Get contacts from group Honey Lovers: " + sms.getContacts(null, null, null, "Honey Lovers", null, null, null, null));

            Contact contact = new Contact("2123456896", "Piglet", "P.", "piglet@small-animals-alliance.org", "It is hard to be brave, when you are only a Very Small Animal.", null);
            contact = (Contact) sms.create(contact);
            System.out.println("Contact create:" + contact);

            contact = sms.getContact(contact.id);
            System.out.println("Contact get:" + contact);

            contact.groups.add("Friends");
            contact.groups.add("Neighbors");
            contact = (Contact) sms.update(contact);

            System.out.println("Contact update:" + contact);

            sms.delete(contact);

            //try to get exception - delete already deleted item
            sms.delete(contact);
            }
        catch (SmsTextingException e)
            {
            System.out.println("SmsTexting error code:" + e.getResponseCode() + "; messages:" + e.getMessage());
            }
    }
}
