package store.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import messages.LogFile;
import store.business.Associate2;
import store.business.FullCalendar2;
import store.business.Services;

public class MailUtil
{

    private static final String HOMEURL = "http://ontimeappointmensystem.com";
    private static final String REGISTRATIONURL = "http://174.80.101.20/AppointmentStore/registrationConfirm";
    private static final String WILLIAMDOBBS = "support@ontimeappointmentsystem.com";
    private static final String WHDTECHNOLOGIESEMAIL = "donotreply@whdtechnologies.com";
    
    private static void getEmailPwd() {

    }

    public static void sendMail(String to, String from, String subject, String body, String bcc, boolean bodyIsHTML)
    {
        try
        {
            // 1 - get a mail session
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", "smtp.gmail.com");
            props.put("mail.smtps.port", 465);
            props.put("mail.smtps.auth", "true");
            props.put("mail.smtps.quitwait", "false");
            
            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            // 2 - create a message  
            MimeMessage message;
            message = new MimeMessage(session);
//            SMTPMessage message = new SMTPMessage(session);
            MimeMultipart content = new MimeMultipart("related");

            message.setSubject(subject);

            if (bodyIsHTML)
            {
//                message.setContent(body, "text/html");
                // ContentID is used by both parts
                String cid;
                cid = ContentIdGenerator.getContentId();

                // HTML part
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(body + "<img src=\"cid:"
                        + cid
                        + "\" /></div>\n", "US-ASCII", "html");
                content.addBodyPart(textPart);

                // Image part
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.attachFile("/Users/Shared/AppointmentStore Logs/media/clock_large.png");
                imagePart.setContentID("<" + cid + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                content.addBodyPart(imagePart);
                message.setContent(content);
            }
            else
            {
                message.setText(body);
            }

            // 3 - address the message
            Address fromAddress = new InternetAddress(from);
            Address toAddress = new InternetAddress(to);
            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            InternetAddress[] addressArray = InternetAddress.parse(bcc);
            message.setRecipients(RecipientType.BCC, addressArray);
            // 4 - send the message
            Transport transport = session.getTransport();
            transport.connect("whdobbs", "wzpyidenzuyzdgzs");
            transport.sendMessage(message, message.getAllRecipients());
        }
        catch (MessagingException ex)
        {
            LogFile.emailLog("confirmationEmail ", to, ex.getMessage());
        }
        catch (IOException ex)
        {
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean sendConfirmation(String email, String firstName,
            String lastName, String subject, int confirmNumber)
    {
        if (subject.equalsIgnoreCase("Order Confirmation"))
        {
            sendMail(email, WHDTECHNOLOGIESEMAIL, subject + " " + confirmNumber,
                    "Dear " + firstName + ",<br><br>"
                    + "Thanks for ordering from us.  "
                    + "You should receive your order in <strong>3-5 business days</strong>.<br> "
                    + "Please contact us if you have any questions.<br><br>"
                    + "Your confirmation number is <strong>" + confirmNumber + "</strong>"
                    + "<br><br>Have a great day and thanks again!<br><br>"
                    + "William Dobbs<br>"
                    + "WHD Productions", WILLIAMDOBBS, true);

            return true;
        }
        else if (subject.equalsIgnoreCase("Registration Request"))
        {
            sendMail(email, WHDTECHNOLOGIESEMAIL, subject + " " + confirmNumber,
                    "Dear " + email + ",<br><br>"
                    + "Thanks for registering with The Salon Store.<br><br>"
                    + "To complete your registration, copy this code <strong>" + confirmNumber + "</strong>"
                    + " then click the link below. <br><br>"
                    + REGISTRATIONURL + "<br><br>"
                    + "Please contact us if you have any questions.<br><br>"
                    + "Have a great day and thanks again!<br>"
                    + "The Salon Store Development Team", WILLIAMDOBBS, true);
            return true;
        }
        else if (subject.equalsIgnoreCase("Registration Complete"))
        {
            sendMail(email, WHDTECHNOLOGIESEMAIL, subject,
                    "Dear " + firstName + ",<br><br>"
                    + "Thank you for scheduling an appointment with The Salon Store.<br>"
                    + "Your appointment ID<br>"
                    + "your account.<br><br>"
                    + "Please contact us if you have any questions.<br>"
                    + "Have a great day and thanks again!<br><br>"
                    + "The Salon Store Development Team", WILLIAMDOBBS, true);
            return true;
        }
        else if (subject.equalsIgnoreCase("Appointment Confirmation"))
        {
            sendMail(email, WHDTECHNOLOGIESEMAIL, subject,
                    "Dear " + firstName + ",<br><br>"
                    + "Thanks for completing your registraton with The Salon Store.<br>"
                    + "You may now schedule appointments, purchase products as well as manage<br>"
                    + "your account.<br>"
                    + "Please contact us if you have any questions.<br>"
                    + "Have a great day and thanks again!<br><br>"
                    + "The Salon Store Development Team", WILLIAMDOBBS, true);
            return true;
        }
        return false;
    }

    public static boolean sendConfirmation(String subject, String dateString, String startTime, FullCalendar2 fc,
            Associate2 associate, Services service, int confirmNumber)
    {

        if (subject.equalsIgnoreCase("Appointment Confirmation"))
        {
            String firstName = fc.getClient().getFirstName();
            String lastName = fc.getClient().getLastName();
            String email = fc.getClient().getEmail();
            int eventId = fc.getEventId();
            int id = fc.getClient().getId();
            String notes = fc.getNotes();
            // send appointment confirmtion email to the Client
            sendMail(email, WHDTECHNOLOGIESEMAIL, subject + " " + eventId,
                    "Hello " + firstName + ",<br><br>"
                    + "Thank you for scheduling an appointment with The Salon Store on " + dateString + " at " + startTime + ".<br><br>"
                    + "<table style=\"height: 206px; float: left;\" width=\"547\">" + " <tbody>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #f68b09;\">" + "Appointment " + "</td>" + "<td style=\"color: #f68b09;\">" + " Summary" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment ID&#35; " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + eventId + "</strong> " + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Client Name: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + firstName + " " + lastName + "</strong> " + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Stylist Name:" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + associate.getFirstName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Service Name: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + service.getName() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Service Description:" + "</td>" + "<td style=\"color: #333333;\">" + " <strong>" + service.getDescription() + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Date: " + "</td>" + "<td style=\"color: #333333;\">" + " <strong>" + dateString + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Time: " + "</td>" + "<td style=\"color: #333333;\">" + " <strong>" + startTime + "</strong>" + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Notes: " + "</td>" + "<td style=\"color: #333333;\">" + notes + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style=\"text-align: right; color: #808080;\">" + "Your Account ID:" + "</td style=\"color: #333333;\">" + "<td>" + " <strong>" + id + "</strong>" + "</td>"
                    + "</tr>"
                    + "</tbody>" + "</table>"
                    + "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"
                    + "Manage your account at " + HOMEURL + "<br><br>"
                    + "Thank You!<br>"
                    + "The Salon Store Development Team", WILLIAMDOBBS, true);
            return true;
        }
        return false;
    }

    public static boolean sendAssociateConfirm(String subject, String dateString, String startTime, FullCalendar2 fc,
            Associate2 associate, Services service, int confirmNumber)
    {

        String firstName = fc.getClient().getFirstName();
        String lastName = fc.getClient().getLastName();
        String email = fc.getClient().getEmail();
        int eventId = fc.getEventId();
        int id = fc.getClient().getId();
        String notes = fc.getNotes();
        String associateEmail = associate.getEmail();
        int associateId = associate.getId();
        // send an appointment email to the Associate Stylist
        sendMail(associateEmail, WHDTECHNOLOGIESEMAIL, "Your client " + firstName
                + " " + lastName + " has scheduled an appointment to perform the service, "
                + service.getDescription() + " on the date of " + dateString + " at " + startTime + " confirmation# " + confirmNumber,
                "Hello " + associate.getFirstName() + ",<br><br>"
                + "Your client, <strong> " + firstName + " " + lastName + "</strong>," + " has scheduled an appointment "
                + "with The Salon Store on " + dateString + ".<br><br>"
                + "<table style=\"height: 206px; float: left;\" width=\"547\">" + " <tbody>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #f68b09;\">" + "Appointment" + "</td>" + "<td style=\"color: #f68b09;\">" + " Summary" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Appointment ID&#35;" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + eventId + "</strong>" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Confirmation#: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + confirmNumber + "</strong>" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Client Name:" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + firstName + " " + lastName + "</strong>" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Client Email Address: " + "</td>" + "<td style=\"color: #333333;\">" + email + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Client ID&#35; " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + id + "</strong>" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Service Name: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + service.getName() + "</strong>" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Service Description:" + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + service.getDescription() + "</strong>" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Date: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + dateString + "</strong>" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Appointment Time: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + startTime + "</strong>" + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Notes: " + "</td>" + "<td style=\"color: #333333;\">" + notes + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"text-align: right; color: #808080;\">" + "Your Account ID: " + "</td>" + "<td style=\"color: #333333;\">" + "<strong>" + associateId + "</strong>" + "</td>"
                + "</tr>"
                + "</tbody>" + "</table>"
                + "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"
                + "Manage your account at " + HOMEURL + "<br><br>"
                + "Thank You!<br>"
                + "The Salon Store Development Team", WILLIAMDOBBS, true);
        return true;
    }

    public static void sendShortMsg(FullCalendar2 fcAsso, String subject,
            String message, String bcc)
    {
        if (subject == null || "".equals(subject))
        {
            subject = "A message from " + fcAsso.getAssociate2().getFirstName();
        }
        sendMail(fcAsso.getAssociate2().getEmail(), WHDTECHNOLOGIESEMAIL, subject,
                message, bcc, true);
    }

    public static boolean sendTechEmail(String emailTech, String email,
            String lastName, String subject, int confirmNumber) throws IOException
    {

        if (subject.equalsIgnoreCase("Registration email failure "))
        {
            // TODO optimize sendTechEmail information --- some of which is not correct
            sendMail(emailTech, WHDTECHNOLOGIESEMAIL, subject + " " + email + " " + confirmNumber,
                    "A Registration email error has occured "
                    + "sending to " + email, WILLIAMDOBBS, true);
            LogFile.generalLog("Registration email failed" + subject + " " + email + " " + confirmNumber, "sendTechEmail");
            return true;
        }
        else if (subject.equalsIgnoreCase("Appointment Associate email failure "))
        {
            sendMail(emailTech, WHDTECHNOLOGIESEMAIL, subject + " " + email + " " + confirmNumber,
                    "An Appointment Associate email error has occured "
                    + "sending to " + email, WILLIAMDOBBS, true);
            LogFile.generalLog("Appointment Associate email failed " + subject + " " + email + " " + confirmNumber, "sendTechEmail");
            return true;
        }
        else
        {
            return false;
        }
    }
}
