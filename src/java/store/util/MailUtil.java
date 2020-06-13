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
import store.business.EmailCommunicator;
import store.business.Emailer;
import store.business.FullCalendar2;

public class MailUtil implements Emailer
{

    private static final String HOMEURL = "http://ontimeappointmensystem.com";
    private static final String REGISTRATIONURL = "http://174.80.101.20/AppointmentStore/registrationConfirm";
    private static final String SUPPORT_URL = "support@ontimeappointmentsystem.com";
    private static final String WHDTECHNOLOGIESEMAIL = "donotreply@whdtechnologies.com";
    private static final String CLIENT_EMAIL_FOOTER_INFO = "Manage your account at " + HOMEURL + "<br><br>"
            + "Thank You!<br>" + "The Salon Store Development Team";

    public static boolean sendMail(String to, String from, String subject, String body, String bcc, boolean bodyIsHTML)
    {
        boolean emailSent = false;
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
            transport.connect(Emailer.emailUsername(), Emailer.emialPassword());
            transport.sendMessage(message, message.getAllRecipients());
            emailSent = true;
        }
        catch (MessagingException ex)
        {
            emailSent = false;
            LogFile.emailLog("MailUtil sendMail ", to, ex.getMessage() + " " + ex.toString());
        }
        catch (IOException ex)
        {
            emailSent = false;
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailSent;
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
                    + "WHD Productions", SUPPORT_URL, true);

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
                    + "The Salon Store Development Team", SUPPORT_URL, true);
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
                    + "The Salon Store Development Team", SUPPORT_URL, true);
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
                    + "The Salon Store Development Team", SUPPORT_URL, true);
            return true;
        }
        return false;
    }

    public static boolean sendClientConfirmation(EmailCommunicator ec)
    {
        // send appointment confirmtion email to the Client
        ec.setMessageSent(sendMail(ec.getClient().getEmail(), WHDTECHNOLOGIESEMAIL, ec.getSubject(),
                ec.getMessage()
                + CLIENT_EMAIL_FOOTER_INFO, SUPPORT_URL, true));
        return ec.isMessageSent();

    }

    public static boolean sendAssociateConfirm(EmailCommunicator ec)
    {
        // send an appointment email to the Associate Stylist
        ec.setMessageSent(sendMail(ec.getAssociate2().getEmail(), WHDTECHNOLOGIESEMAIL, ec.getSubject(), ec.getMessage()
                + CLIENT_EMAIL_FOOTER_INFO, SUPPORT_URL, true));
        return ec.isMessageSent();
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
                    + "sending to " + email, SUPPORT_URL, true);
            LogFile.generalLog("Registration email failed" + subject + " " + email + " " + confirmNumber, "sendTechEmail");
            return true;
        }
        else if (subject.equalsIgnoreCase("Appointment Associate email failure "))
        {
            sendMail(emailTech, WHDTECHNOLOGIESEMAIL, subject + " " + email + " " + confirmNumber,
                    "An Appointment Associate email error has occured "
                    + "sending to " + email, SUPPORT_URL, true);
            LogFile.generalLog("Appointment Associate email failed " + subject + " " + email + " " + confirmNumber, "sendTechEmail");
            return true;
        }
        else
        {
            return false;
        }
    }
}
