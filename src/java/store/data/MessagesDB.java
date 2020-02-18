/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import messages.LogFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import store.business.Client;
import store.business.SMSAppointmentMessage;
import store.business.SMSMemberInviteMessage;
import store.business.SMSMessage;
import store.util.DateUtil;

/**
 *
 * @author williamdobbs
 */
public class MessagesDB
{

    public static int insertSmsMsg(SMSMessage m)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the message responses table in the database
        String query = "INSERT INTO appointment.message_responses ("
                + "message_status, "
                + "message_code, "
                + "msg_assocoteId, "
                + "msg_sentBy_id, "
                + "ismessage_invite, "
                + "is_group_msg) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, m.getStatus());
            ps.setInt(2, Integer.parseInt(m.getCode()));
            ps.setInt(3, m.getAssociate2().getId());
            ps.setInt(4, m.getSentById());
            ps.setBoolean(5, m.getIsMessageInvite());
            ps.setBoolean(6, m.isGroupMessage());

            ps.executeUpdate();

            //Get the clientID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            int msgId;
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);

            identityResultSet.next();
            msgId = identityResultSet.getInt("IDENTITY");
            return msgId;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB insertSmsMsg ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void updateSmsRespnse(JSONObject r, SMSMessage s)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int phoneClient;
        JSONObject entry = r.getJSONObject("Entry");

        //This method adds and updates record to the messages responses table in the database
        String query
                = "UPDATE message_responses SET "
                + "idmessage_response= ?, "
                + "subject = ?, "
                + "message = ?, "
                + "message_type_id = ?, "
                + "total_recipients = ?, "
                + "credits_charged = ?, "
                + "time_to_send = ?, "
                + "send_timestamp = ?, "
                + "unix_timestamp = ? "
                + "WHERE message_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(entry.getString("ID")));
            ps.setString(2, entry.getString("Subject"));
            ps.setString(3, entry.getString("Message"));
            ps.setString(4, entry.getString("MessageTypeID"));
            ps.setInt(5, Integer.parseInt(entry.getString("RecipientsCount")));
            ps.setInt(6, Integer.parseInt(entry.getString("Credits")));
            ps.setString(7, entry.getString("StampToSend"));
            ps.setTimestamp(8, s.getSqlTimestamp());
            ps.setInt(9, s.getTimeToSendInteger());
            ps.setInt(10, s.getMessageId());

            ps.executeUpdate();

            if (s.getPhoneArray() != null);
            {
                ArrayList<String> p = s.getPhoneArray();
                // insert sent-to phone numbers
                for (String i : p)
                {
                    if (s.getIsMessageInvite() == true)
                    {
                        phoneClient = 99999;
                    }
                    else
                    {
                        phoneClient = s.getClient().getId();
                    }
                    insertRespnsePhone(s.getMessageId(), i, phoneClient);
                }
            }
            if (entry.has("Groups"));
            {
                JSONArray g = entry.getJSONArray("Groups");
                if (!g.isEmpty())
                {
                    // insert sent-to groups
                    for (int i = 0; i < g.size(); i++)
                    {
                        String group = g.getString(i);
                        insertGroup(s.getMessageId(), group);
                    }
                }
            }

//            else if (!entry.getString("LocalOptOuts").equals("null"));
//                {
//                JSONArray getLocalOptOutsArray = entry.getJSONArray("LocalOptOuts");
//                // insert local opt out phone numbers
//                for (int i = 0; i < getLocalOptOutsArray.size(); i++)
//                    {
//                    String optOutPh = getLocalOptOutsArray.getString(i);
//                    insertLocalOptOuts(resId, optOutPh);
//                    }
//                }
//
//            if (!entry.getString("GlobalOptOuts").equals("null"));
//                {
//                JSONArray getGlobalOptOutsArray = entry.getJSONArray("GlobalOptOuts");
//                // insert global opt out phone numbers
//                for (int i = 0; i < getGlobalOptOutsArray.size(); i++)
//                    {
//                    String gloOptOutPh = getGlobalOptOutsArray.getString(i);
//                    insertGlobalOptOuts(resId, gloOptOutPh);
//                    }
//                }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB updateSmsRespnse FAILED", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insertRespnsePhone(int msgId, String phoneNumber, int clientId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO appointment.msgSent_phNumbers ("
                + "msg_response_id, "
                + "msgSent_client_id, "
                + "msg_phoneNumber) "
                + "VALUES (?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, msgId);
            ps.setInt(2, clientId);
            ps.setString(3, phoneNumber);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB insertRespnsePhone FAILED ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insertLocalOptOuts(int msgId, String optOutNumber)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO appointment.message_localOptOuts ("
                + "msg_res_id_localOptOuts, "
                + "msg_phoneNumber_locOptOut) "
                + "VALUES (?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, msgId);
            ps.setString(2, optOutNumber);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB insertLocalOptOuts FAILED ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insertGlobalOptOuts(int msgId, String gloOptOutNumber)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO appointment.message_globalOptOuts ("
                + "msg_res_id_globalOptOuts, "
                + "msg_phoneNumber_gloOtpOut) "
                + "VALUES (?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, msgId);
            ps.setString(2, gloOptOutNumber);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB insertLocalOptOuts FAILED ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insertGroup(int msgId, String group)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO appointment.message_sentResGroups ("
                + "msg_messageGrp_id, "
                + "msg_sentGroup) "
                + "VALUES (?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, msgId);
            ps.setString(2, group);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB insertGroup FAILED ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insertSmsErrors(int msgId, String error)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO appointment.message_smsSentErrors ("
                + "msg_sentId, "
                + "msg_error) "
                + "VALUES (?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, msgId);
            ps.setString(2, error);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB insertSmsErrors FAILED ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<SMSAppointmentMessage> sentMessages2(int idNumber, int startAtMsg, int limitMsgs)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT "
                + "idmessage_response, "
                + "message_id, "
                + "message_code, "
                + "message_status, "
                + "message_timestamp, "
                + "time_to_send, "
                + "send_timestamp, "
                + "unix_timestamp, "
                + "subject, "
                + "message, "
                + "msgSent_client_id, "
                + "msg_phoneNumber, "
                + "message_type_id, "
                + "msg_assocoteId, "
                + "msg_sentBy_id, "
                + "message_responses_event_id, "
                + "ismessage_invite, "
                + "msg_error, "
                + "is_group_msg "
                + "FROM (SELECT * FROM appointment.message_responses mr "
                + "LEFT JOIN message_smsSentErrors me "
                + "ON mr.message_id = me.msg_sentId "
                + "LEFT JOIN msgSent_phNumbers ms "
                + "ON mr.message_id = ms.msg_response_id "
                + "WHERE msg_sentBy_id = ?  "
                + "ORDER BY send_timestamp DESC LIMIT  ?, ?) sub "
                + "ORDER BY send_timestamp DESC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idNumber);
            ps.setInt(2, startAtMsg);
            ps.setInt(3, limitMsgs);
            ArrayList<SMSAppointmentMessage> messages = new ArrayList();
            ArrayList<SMSAppointmentMessage> groupMessages = new ArrayList();
            rs = ps.executeQuery();

            while (rs.next())
            {
                SMSAppointmentMessage m = new SMSAppointmentMessage();
                m.setStatus(rs.getString("message_status"));
                m.setStatusColor(m.getStatusColor());
                m.setMessageId(rs.getInt("message_id"));
                m.setSubject(rs.getString("subject"));
                m.setGroupMessage(rs.getBoolean("is_group_msg"));

                m.setPhoneNumber(rs.getString("msg_phoneNumber"));
                m.setClient(CustomerDB.selectClient(rs.getInt("msgSent_client_id")));
                if ("Failure".equals(m.getStatus()))
                {
                    m.setMessage(rs.getString("msg_error"));
                }
                else
                {
                    m.setMessage(rs.getString("message"));
                }
                m.setMessageTypeID(rs.getString("message_type_id"));
                m.setIdMessageResponse(rs.getInt("idmessage_response"));
                m.setEventId(rs.getInt("message_responses_event_id"));
                m.setTimeToSend(rs.getString("time_to_send"));
                m.setPhoneNumber(rs.getString("msg_phoneNumber"));
                m.setCode(rs.getString("message_code"));
                m.setTimestamp(rs.getTimestamp("message_timestamp"));
                m.setTimeToSendInteger(rs.getInt("unix_timestamp"));
                m.setSentById(rs.getInt("msg_sentBy_id"));
                m.setIsMessageInvite(rs.getBoolean("ismessage_invite"));
                m.setAssociate2(AssociateDB.selectAssociateInfo(rs.getInt("msg_assocoteId")));

                if (m.getClient() == null)
                {
                    Client c = new Client();
                    if (m.getIsMessageInvite() == true)
                    {
                        c.setFirstName("Invite");
                        c.setLastName("Request");
                        c.setMobilePhone(m.getPhoneNumber());
                    }
                    else
                    {
                        c.setFirstName("n/a");
                        c.setLastName("");
                        c.setMobilePhone("n/a");
                    }
                    m.setClient(c);
                }

                messages.add(m);
            }
            return messages;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB sentMessages FAILED ", e.getMessage(), e.toString());
            return null;
        }
        catch (NullPointerException e)
        {
            System.out.print("Caught the NullPointerException");
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }

    public static ArrayList<SMSMemberInviteMessage> selectRecentAssociateRequests(int associateId, int daysBack)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT idmember_request, member_request_phone, timestamp_request_tz, member_request_status "
                + "FROM (SELECT * FROM appointment.member_invite_requests "
                + "WHERE request_associate_id = ? "
                + "ORDER BY timestamp_request_tz DESC LIMIT ?) sub "
                + "ORDER BY timestamp_request_tz ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setInt(2, daysBack);
            rs = ps.executeQuery();
            ArrayList<SMSMemberInviteMessage> reqMsgs = new ArrayList();

            while (rs.next())
            {
                SMSMemberInviteMessage m = new SMSMemberInviteMessage();
                m.setStatusNumber(rs.getInt("member_request_status"));
                m.setPhoneNumber(rs.getString("member_request_phone"));
                m.setTimeToSend(DateUtil.createDateString(rs.getTimestamp("timestamp_request_tz")));
                m = selectSmsStatus(m);

                reqMsgs.add(m);
            }
            return reqMsgs;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB sentMessages FAILED ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean isClientSmsEntry(String smsId, int clientId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM customer_sms_id "
                + "WHERE customer_sys_id = ? AND idcustomer_sms_id = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, smsId);
            ps.setInt(2, clientId);

            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB isClientSmsEntry FAILED ", e.getMessage(), e.toString());
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insertInvitationRequests(String phNumber, int associateId, Timestamp timeToSend, int unixTimestamp)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO member_invite_requests ("
                + "member_request_phone, "
                + "request_associate_id, "
                + "timestamp_request_tz, "
                + "unix_timestamp) "
                + "VALUES (?,?,?,?)";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, phNumber);
            ps.setInt(2, associateId);
            ps.setTimestamp(3, timeToSend);
            ps.setInt(4, unixTimestamp);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB insertInvitationRequests FAILED ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static SMSMemberInviteMessage selectMsgInviteRequest(SMSMemberInviteMessage i, int id)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT "
                + "status_color, "
                + "status_name "
                + "FROM message_responses mr "
                + "JOIN member_invite_requests mi "
                + "ON mr.unix_timestamp = mi.unix_timestamp "
                + "JOIN message_invite_status ms "
                + "ON mi.member_request_status = ms.idmessage_status "
                + "WHERE msg_assocoteId = ? AND mr.unix_timestamp = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2, i.getTimeToSendInteger());
            rs = ps.executeQuery();

            if (rs.next())
            {
                i.setStatusColor(rs.getString("status_color"));
                i.setStatus(rs.getString("status_name"));
                return i;
            }
            else
            {
                return i;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB selectSmsStatus FAILED ", e.getMessage(), e.toString());
            return i;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static String selectSmsStatus(String statusName)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT sms_color "
                + "FROM message_sms_status "
                + "WHERE sms_status = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, statusName);
            rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("sms_color");
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB selectSmsStatus FAILED ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static SMSMemberInviteMessage selectSmsStatus(SMSMemberInviteMessage smsMsg)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM message_invite_status "
                + "WHERE idmessage_status = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, smsMsg.getStatusNumber());
            rs = ps.executeQuery();
            if (rs.next())
            {
                smsMsg.setStatusColor(rs.getString("status_color"));
                smsMsg.setStatus(rs.getString("status_name"));
                return smsMsg;
            }
            else
            {
                String name = smsMsg.getAssociate2().getFirstName() + " " + smsMsg.getAssociate2().getLastName();
                String smsMessage = smsMsg.getMessage();
                LogFile.databaseError("MessagesDB selectSmsStatus int statusNumber FAILED ", name, smsMessage);
                return smsMsg;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB selectSmsStatus int statusNumber FAILED ", e.getMessage(), e.toString());
            return smsMsg;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static SMSAppointmentMessage selectSmsStatus(SMSAppointmentMessage smsMsg)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM message_invite_status "
                + "WHERE idmessage_status = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, smsMsg.getStatusNumber());
            rs = ps.executeQuery();
            if (rs.next())
            {
                smsMsg.setStatusColor(rs.getString("status_color"));
                smsMsg.setStatus(rs.getString("status_name"));
                return smsMsg;
            }
            else
            {
                String name = smsMsg.getAssociate2().getFirstName() + " " + smsMsg.getAssociate2().getLastName();
                String smsMessage = smsMsg.getMessage();
                LogFile.databaseError("MessagesDB selectSmsStatus int statusNumber FAILED ", name, smsMessage);
                return smsMsg;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB selectSmsStatus int statusNumber FAILED ", e.getMessage(), e.toString());
            return smsMsg;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static String selectSmsMessage(int msgNumber)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT message "
                + "FROM messages "
                + "WHERE idmessages = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, msgNumber);
            rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("message");
            }
            else
            {
                return "no message";
            }

        }
        catch (SQLException e)
        {
            LogFile.databaseError("MessagesDB selectSmsStatus FAILED ", e.getMessage(), e.toString());
            return "database error";
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
