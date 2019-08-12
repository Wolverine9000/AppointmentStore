package messages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import store.business.SMSMessage;
import store.util.DateUtil;

public class LogFile
{

    private static final String FILELOGPATH = "/Users/Shared/AppointmentStore Logs";

    private static void filePath(String dirString, String fileString)
    {
        Path dirPath = Paths.get(dirString);
        if (Files.notExists(dirPath))
        {
            try
            {
                Files.createDirectories(dirPath);
            }
            catch (IOException ex)
            {
                Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Path filePath = Paths.get(dirString, fileString);
        if (Files.notExists(filePath))
        {
            try
            {
                Files.createFile(filePath);
            }
            catch (IOException ex)
            {
                Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean loginMessage(String email, String failPoint)
    {
        String today = DateUtil.todaysDate();
        String currentDate = DateUtil.formatDate();
        String loginMessages;
        loginMessages = today + " " + failPoint + email;

        String dirString = FILELOGPATH + "/loginMessage logs/";
        String filesString = currentDate + "_loginMessages.txt";
        filePath(dirString, filesString);

        Path filePath = Paths.get(dirString, filesString);
        File filesPath = filePath.toFile();

        try (PrintWriter out
                = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(filesPath, true)), true))
        {
            out.println(loginMessages);
        }
        catch (IOException ioe)
        {
            return false;
        }
        return true;
    }

    public static boolean emailLog(String failPoint, String email, String exception)
    {
        String today = DateUtil.todaysDate();
        String currentDate = DateUtil.formatDate();
        String emailErrorMessage;
        emailErrorMessage = today + " " + failPoint + email + " " + exception;

        String dirString = FILELOGPATH + "/emailMessage logs/";
        String filesString = currentDate + "_emailMessageLog.txt";
        filePath(dirString, filesString);

        Path filePath = Paths.get(dirString, filesString);
        File filesPath = filePath.toFile();

        try (PrintWriter out
                = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(filesPath, true)), true))
        {
            out.println(emailErrorMessage);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    public static void databaseError(String failPoint, String exception, String errorString)
    {
        String today = DateUtil.todaysDate();
        String currentDate = DateUtil.formatDate();
        String databaseErrorMessage = null;
        databaseErrorMessage = today + " " + failPoint + exception + " " + errorString;

        String dirString = FILELOGPATH + "/databaseErrorMessage logs/";
        String filesString = currentDate + "_databaseErrorMessage.txt";
        filePath(dirString, filesString);

        Path filePath = Paths.get(dirString, filesString);
        File filesPath = filePath.toFile();

        try (PrintWriter out
                = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(filesPath.getPath(), true)), true))
        {
            out.println(databaseErrorMessage);
        }
        catch (IOException ioe)
        {
            generalLog("LogFile databaseError" + ioe.toString(), currentDate);
        }
    }

    public static boolean associateLog(String logPoint, String message, String result)
    {
        String today = DateUtil.todaysDate();
        String currentDate = DateUtil.formatDate();
        String associateMessage;
        associateMessage = today + " " + logPoint + " " + message + result;

        String dirString = FILELOGPATH + "/associate logs/";
        String filesString = currentDate + "_associate.txt";
        filePath(dirString, filesString);

        Path filePath = Paths.get(dirString, filesString);
        File filesPath = filePath.toFile();

        try (PrintWriter out
                = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(filesPath, true)), true))
        {
            out.println(associateMessage);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    public static void generalLog(String failPoint, String message)
    {
        String today = DateUtil.todaysDate();
        String currentDate = DateUtil.formatDate();
        String generalMessage;

        generalMessage = today + " " + failPoint + " " + message;

        String dirString = FILELOGPATH + "/generalMessage logs/";
        String filesString = currentDate + "_generalMessage.txt";
        filePath(dirString, filesString);

        Path filePath = Paths.get(dirString, filesString);
        File filesPath = filePath.toFile();

        try (PrintWriter out
                = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(filesPath, true)), true))
        {
            out.println(generalMessage);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static void smsResError(Object errorObj[])
    {
        String today = DateUtil.todaysDate();
        String currentDate = DateUtil.formatDate();
        String smsErrorMsg = null;

        for (Object ErrorMessage1 : errorObj)
        {
            smsErrorMsg = today + "  Error: " + ErrorMessage1;
        }

        String dirString = FILELOGPATH + "/message logs/";
        String filesString = currentDate + "_message.txt";
        filePath(dirString, filesString);

        Path filePath = Paths.get(dirString, filesString);
        File filesPath = filePath.toFile();

        try (PrintWriter out
                = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(filesPath, true)), true))
        {
            out.println(smsErrorMsg);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static void smsError(String failPoint, String message, SMSMessage m)
    {
        String today = DateUtil.todaysDate();
        String currentDate = DateUtil.formatDate();
        String smsErrorMsg;

        smsErrorMsg = today + " " + failPoint + " " + message;

        String dirString = FILELOGPATH + "/message logs/";
        String filesString = currentDate + "_message.txt";
        filePath(dirString, filesString);

        Path filePath = Paths.get(dirString, filesString);
        File filesPath = filePath.toFile();

        try (PrintWriter out
                = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(filesPath, true)), true))
        {
            out.println(smsErrorMsg);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
//private static void notifyAdminitrator(String str)
//{
//    SMSBasicMessage m = new SMSBasicMessage();
//sendSMSMessage
//}
}
