/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import messages.LogFile;
import store.business.Associate2;
import store.business.Client;
import store.business.FullCalendar2;
import store.business.MemberExtras;
import store.business.SMSMemberInviteMessage;

/**
 *
 * @author williamdobbs
 */
public class CustomerDB
{

    public static int insert(Client client)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO customer (first_name, last_name, company, email, "
                + "address, home_phone, city, State, zip_code, "
                + "cc_number) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getCompany());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getAddress());
            ps.setString(6, client.gethomePhone());
            ps.setString(7, client.getCity());
            ps.setString(8, client.getState());
            ps.setString(9, client.getZip());
            ps.setString(10, client.getCcNumber());
            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB insert int", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int insert(String firstname, String lastname, String company, String email,
            String password, String homephone, String workphone, String mobilephone,
            String address, String city, String state, String zip, String sercurityanswer)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO customer (first_name, last_name, company, email, password, "
                + "home_phone, work_phone, mobile_phone, address, city, state, zip_code, "
                + "registration_confirmed, security_answer) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'y', ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, company);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, homephone);
            ps.setString(7, workphone);
            ps.setString(8, mobilephone);
            ps.setString(9, address);
            ps.setString(10, city);
            ps.setString(11, state);
            ps.setString(12, zip);
            ps.setString(13, sercurityanswer);
            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB insert int long", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int insertClient(FullCalendar2 fc)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO customer (first_name, last_name, email, "
                + "mobile_phone, preferred_associateID, normal_service_id) "
                + "VALUES (?, ?, ?, ?, ?, ?,)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, fc.getClient().getFirstName());
            ps.setString(2, fc.getClient().getLastName());
            ps.setString(3, fc.getClient().getEmail());
            ps.setString(4, fc.getClient().getMobilePhone());
            ps.setInt(5, fc.getAssociate2().getId());
            ps.setInt(6, fc.getServices().getServiceId());
            ps.executeUpdate();

            //Get the clientID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            int clientID;
            try (Statement identityStatement = connection.createStatement(); ResultSet identityResultSet = identityStatement.executeQuery(identityQuery))
            {
                identityResultSet.next();
                clientID = identityResultSet.getInt("IDENTITY");
            }
            return clientID;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB insertClient ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int insertNewClient(FullCalendar2 fc)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO customer ("
                + "first_name, "
                + "last_name, "
                + "email, "
                + "company, "
                + "mobile_phone, "
                + "work_phone, "
                + "home_phone, "
                + "address, "
                + "city, "
                + "state, "
                + "zip_code, "
                + "client_img_uploaded, "
                + "member_level, "
                + "preferred_associateID, "
                + "normal_service_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, fc.getClient().getFirstName());
            ps.setString(2, fc.getClient().getLastName());
            ps.setString(3, fc.getClient().getEmail());
            ps.setString(4, fc.getClient().getCompany());
            ps.setString(5, fc.getClient().getMobilePhone());
            ps.setString(6, fc.getClient().getWorkPhone());
            ps.setString(7, fc.getClient().getHomePhone());
            ps.setString(8, fc.getClient().getAddress());
            ps.setString(9, fc.getClient().getCity());
            ps.setString(10, fc.getClient().getState());
            ps.setString(11, fc.getClient().getZip());
            ps.setBoolean(12, fc.getClient().getImgUpl());
            ps.setString(13, fc.getClient().getMemberLevel());
            ps.setInt(14, fc.getClient().getPreferredAssociateId());
            ps.setInt(15, fc.getClient().getServiceNormalid());

            ps.executeUpdate();

            //Get the clientID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            int clientID;
            try (Statement identityStatement = connection.createStatement(); ResultSet identityResultSet = identityStatement.executeQuery(identityQuery))
            {
                identityResultSet.next();
                clientID = identityResultSet.getInt("IDENTITY");
            }
            insertCustAlerts(fc.getClient(), clientID); // insert customer alert preferences
            return clientID;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB insertNewClient ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insertCustAlerts(Client c, int clientId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO customer_alert_prefs ("
                + "customer_id_alerts, "
                + "cust_email_adAlerts, "
                + "cust_sms_adAlerts, "
                + "cust_email_apptAlerts, "
                + "cust_sms_apptAlerts) "
                + "VALUES (?, ?, ?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            ps.setBoolean(2, c.isEmailAdAlerts());
            ps.setBoolean(3, c.isSmsAdAlerts());
            ps.setBoolean(4, c.isEmailApptAlerts());
            ps.setBoolean(5, c.isSmsApptAlerts());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB insertCustAlerts ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int update(Client client)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //It returns a value of 0 if the client can't be found.
        String query = "UPDATE customer SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "company = ?, "
                + "address = ?, "
                + "home_phone = ?, "
                + "mobile_phone = ?, "
                + "city = ?, "
                + "state = ?, "
                + "zip_code = ?, "
                + "cc_number = ? "
                + "WHERE email = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getCompany());
            ps.setString(4, client.getAddress());
            ps.setString(5, client.gethomePhone());
            ps.setString(6, client.getMobilePhone());
            ps.setString(7, client.getCity());
            ps.setString(8, client.getState());
            ps.setString(9, client.getZip());
            ps.setString(10, client.getCcNumber());
            ps.setString(11, client.getEmail());

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB update", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int update(int clienId, Client c)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        // It returns a value of 0 if the client can't be found.
        String query = "UPDATE customer SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ?, "
                + "mobile_phone = ? "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getMobilePhone());
            ps.setInt(5, clienId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB update", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateClient(int clientId, Client c)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        // It returns a value of 0 if the client can't be found.
        String query = "UPDATE customer SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ?, "
                + "mobile_phone = ?, "
                + "home_phone = ?, "
                + "work_phone = ?, "
                + "company = ?, "
                + "address = ?, "
                + "city = ?, "
                + "state = ?, "
                + "zip_code = ?, "
                + "member_level = ?, "
                + "normal_service_id = ?, "
                + "preferred_associateID = ? "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getMobilePhone());
            ps.setString(5, c.getHomePhone());
            ps.setString(6, c.getWorkPhone());
            ps.setString(7, c.getCompany());
            ps.setString(8, c.getAddress());
            ps.setString(9, c.getCity());
            ps.setString(10, c.getState());
            ps.setString(11, c.getZip());
            ps.setString(12, c.getMemberLevel());
            ps.setInt(13, c.getServiceNormalid());
            ps.setInt(14, c.getPreferredAssociateId());
            ps.setInt(15, clientId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB updateClient", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void updateClientAlerts(int clientId, Client c)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        // It returns a value of 0 if the client can't be found.
        String query = "UPDATE customer_alert_prefs SET "
                + "cust_email_adAlerts = ?, "
                + "cust_sms_adAlerts = ?, "
                + "cust_email_apptAlerts = ?, "
                + "cust_sms_apptAlerts = ? "
                + "WHERE customer_id_alerts = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, c.isEmailAdAlerts());
            ps.setBoolean(2, c.isSmsAdAlerts());
            ps.setBoolean(3, c.isEmailApptAlerts());
            ps.setBoolean(4, c.isSmsApptAlerts());
            ps.setInt(5, clientId);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB updateClientAlerts", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateMemberLevel(int clienId, Client c)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //It returns a value of 0 if the client can't be found.
        String query = "UPDATE customer SET "
                + "member_level= ? "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, c.getMemberLevel());
            ps.setInt(2, clienId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB updateMemberLevel", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateNormalSvc(int clienId, Client c)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //It returns a value of 0 if the client can't be found.
        String query = "UPDATE customer SET "
                + "normal_service_id = ? "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, c.getServiceNormalid());
            ps.setInt(2, clienId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB updateNormalSvc", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void updateClientInviteRequest(int statusId, SMSMemberInviteMessage c)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE member_invite_requests "
                + "SET member_request_status = ? "
                + "WHERE member_request_phone = ? AND request_associate_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, statusId);
            ps.setString(2, c.getPhoneNumber());
            ps.setInt(3, c.getAssociateSender().getId());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB updateClientInviteRequest", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void updateClientInviteRequest(String mobilephone, int statusId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE member_invite_requests "
                + "SET member_request_status = ? "
                + "WHERE member_request_phone = ? "
                + "AND HOUR(TIMEDIFF(NOW(), timestamp_request_tz)) < 12";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, statusId);
            ps.setString(2, mobilephone);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB updateClientInvite ERROR ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Client checkCredenitals(String email, String password)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM customer "
                + "WHERE email = ? "
                + "AND password = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next())
            {
                Client c = new Client();
                c.setEmail(rs.getString("email"));
                c.setPassword(rs.getString("password"));

                return c;
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB checkCredenitals", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Client emailExists(String email)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT email FROM customer "
                + "WHERE email = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next())
            {
                Client c = new Client();
                c.setEmail(rs.getString("email"));

                return c;
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB emailExists", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean emailExists(FullCalendar2 fc)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT email FROM customer "
                + "WHERE email = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, fc.getClient().getEmail());
            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB emailExists", e.getMessage(), e.toString());
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean clientExists(FullCalendar2 fc)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT mobile_phone, email, id  FROM customer "
                + "WHERE mobile_phone = ? OR email = ? OR id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, fc.getClient().getMobilePhone());
            ps.setString(2, fc.getClient().getEmail());
            ps.setInt(3, fc.getCustomerId());
            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB clientExists", e.getMessage(), e.toString());
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean clientExists(String mp)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT mobile_phone, email, id  FROM customer "
                + "WHERE mobile_phone = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, mp);
            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB clientExists by mobile phone", e.getMessage(), e.toString());
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a client isn't found.
    /**
     *
     * @param c
     * @return
     */
    public static SMSMemberInviteMessage selectClientInvite(SMSMemberInviteMessage c)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Associate2 a = new Associate2();

        String query = "SELECT * FROM appointment.member_invite_requests "
                + "WHERE member_request_phone = ? "
                + "AND member_request_status = 0 "
                + "AND HOUR(TIMEDIFF(NOW(), timestamp_request_tz)) < 12";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, c.getPhoneNumber());
            rs = ps.executeQuery();
            if (rs.next())
            {
                c.setPhoneNumber(rs.getString("member_request_phone"));
                a.setId(rs.getInt("request_associate_id"));
                c.setAssociateSender(a);
                c.setMsgError("no error");
                return c;
            }
            else
            {
                c.setMsgError("request not found");
                return c;
            }

        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectClientInvite ", e.getMessage(), e.toString());
            c.setMsgError("sql  error");
            return c;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a client email isn't found.
    public static Client selectClient(String email)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM customer "
                + "WHERE email = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next());
            {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setCompany(rs.getString("company"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setHomePhone(rs.getString("home_phone"));
                c.setWorkPhone(rs.getString("work_phone"));
                c.setMobilePhone(rs.getString("mobile_phone"));
                c.setCity(rs.getString("city"));
                c.setState(rs.getString("state"));
                c.setZip(rs.getString("zip_code"));
                c.setCcNumber(rs.getString("cc_number"));
                c.setMemberLevel(rs.getString("member_level"));
                c.setImgUpl(rs.getBoolean("client_img_uploaded"));

                return c;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectClient ERROR ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    // TODO switch over and use this inherited class
    //This method returns null if a client email isn't found.
    public static Client selectUser(String email)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM customer "
                + "WHERE email = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next());
            {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setCompany(rs.getString("company"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setHomePhone(rs.getString("home_phone"));
                c.setWorkPhone(rs.getString("work_phone"));
                c.setMobilePhone(rs.getString("mobile_phone"));
                c.setCity(rs.getString("city"));
                c.setState(rs.getString("state"));
                c.setZip(rs.getString("zip_code"));
                c.setCcNumber(rs.getString("cc_number"));
                c.setMemberLevel(rs.getString("member_level"));
                c.setImgUpl(rs.getBoolean("client_img_uploaded"));

                return c;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectUser", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a Client id isn't found.
    public static Client selectClient(int clientId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<MemberExtras> a;
        a = CustomerDB.selectMemberLevels();

        String query = "SELECT * FROM appointment.customer c1 "
                + "INNER JOIN appointment.customer_alert_prefs c2 "
                + "on c1.id = c2.customer_id_alerts "
                + "WHERE c1.id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            rs = ps.executeQuery();
            if (rs.next());
            {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setCompany(rs.getString("company"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setHomePhone(rs.getString("home_phone"));
                c.setWorkPhone(rs.getString("work_phone"));
                c.setMobilePhone(rs.getString("mobile_phone"));
                c.setCity(rs.getString("city"));
                c.setState(rs.getString("state"));
                c.setZip(rs.getString("zip_code"));
                c.setMemberLevel(rs.getString("member_level"));
                c.setServiceNormalid(rs.getInt("normal_service_id"));
                c.setServiceName(c.getServiceName());
                c.setSmsAdAlerts(rs.getBoolean("cust_sms_adAlerts"));
                c.setSmsApptAlerts(rs.getBoolean("cust_sms_apptAlerts"));
                c.setEmailAdAlerts(rs.getBoolean("cust_email_adAlerts"));
                c.setEmailApptAlerts(rs.getBoolean("cust_email_apptAlerts"));
                c.setImgUpl(rs.getBoolean("client_img_uploaded"));

                a.stream().filter((a1) -> (a1.getMemberLevel() == null ? c.getMemberLevel() == null : a1.getMemberLevel().equals(c.getMemberLevel()))).forEach((a1) ->
                {
                    c.setMemLevelClr(a1.getMemberColor());
                });

                return c;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectClient int client id ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a Client id isn't found.
    public static FullCalendar2 selectClientFc(int clientId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<MemberExtras> a;
        a = CustomerDB.selectMemberLevels();

        String query = "SELECT * FROM appointment.customer "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            rs = ps.executeQuery();
            if (rs.next());
            {
                FullCalendar2 fc = new FullCalendar2();
                fc.setCustomerId(rs.getInt("id"));
                fc.getClient().setFirstName(rs.getString("first_name"));
                fc.getClient().setLastName(rs.getString("last_name"));
                fc.getClient().setEmail(rs.getString("email"));
                fc.setAssociateId(rs.getInt("preferred_associateID"));
                fc.setAssociate2(AssociateDB.selectAssociateInfo(rs.getInt("preferred_associateID")));
                fc.setClient(CustomerDB.selectClient(rs.getInt("id")));

                return fc;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectClient fc", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int selectCustomerID(Client client)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method returns -1 if client isn't found.
        String query = "SELECT id FROM customer "
                + "WHERE email = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, client.getEmail());
            rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getInt("id");
            }
            else
            {
                return -1;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectCustomerID", e.getMessage(), e.toString());
            return -1;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a product isn't found.
    public static ArrayList<Client> selectAllCustomers()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM customer c1 "
                + "INNER JOIN customer_alert_prefs c2 "
                + "ON c1.id = c2.customer_id_alerts "
                + "ORDER BY first_name";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Client> client = new ArrayList<>();
            while (rs.next())
            {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setCompany(rs.getString("company"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setHomePhone(rs.getString("home_phone"));
                c.setWorkPhone(rs.getString("work_phone"));
                c.setMobilePhone(rs.getString("mobile_phone"));
                c.setCity(rs.getString("city"));
                c.setState(rs.getString("state"));
                c.setZip(rs.getString("zip_code"));
                c.setMemberLevel(rs.getString("member_level"));
                c.setImgUpl(rs.getBoolean("client_img_uploaded"));
                c.setServiceNormalid(rs.getInt("normal_service_id"));
                c.setPreferredAssociateId(rs.getInt("preferred_associateID"));
                c.setEmailAdAlerts(rs.getBoolean("cust_email_adAlerts"));
                c.setEmailApptAlerts(rs.getBoolean("cust_email_apptAlerts"));
                c.setSmsAdAlerts(rs.getBoolean("cust_sms_adAlerts"));
                c.setSmsApptAlerts(rs.getBoolean("cust_sms_apptAlerts"));
                Associate2 a;
                a = AssociateDB.selectAssociateInfo(c.getPreferredAssociateId());
                c.setAssociateInfo(a);

                client.add(c);
            }
            return client;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectAllCustomers", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

//This method returns null if a calendar isn't found.
    public static ArrayList<FullCalendar2> selectClientsAll()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM customer  "
                + "WHERE isAccount_active = true "
                + "ORDER BY first_name";

        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<FullCalendar2> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 cc = new FullCalendar2();
                cc.setCustomerId(rs.getInt("id"));
                cc.getClient().setFirstName(rs.getString("first_name"));
                cc.getClient().setLastName(rs.getString("last_name"));
                cc.getClient().setEmail(rs.getString("email"));
                cc.setAssociateId(rs.getInt("preferred_associateID"));
                cc.setAssociate2(AssociateDB.selectAssociateInfo(rs.getInt("preferred_associateID")));
                cc.setClient(CustomerDB.selectClient(rs.getInt("id")));

                calendarCustomer.add(cc);
            }
            return calendarCustomer;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectClientsAll() ", e.toString(), " AllClients ");
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a calendar isn't found.
    public static ArrayList<FullCalendar2> selectClientsAll(String level)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM appointment.customer "
                + "WHERE member_level = ? "
                + "ORDER BY first_name ASC;";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, level);
            rs = ps.executeQuery();
            ArrayList<FullCalendar2> clients = new ArrayList<>();

            while (rs.next())
            {
                FullCalendar2 cc = new FullCalendar2();
                cc.getClient().setId(rs.getInt("id"));
                cc.getClient().setLastName(rs.getString("first_name"));
                cc.getClient().setLastName(rs.getString("last_name"));
                cc.getClient().setEmail(rs.getString("email"));
                cc.setAssociateId(rs.getInt("preferred_associateID"));
                cc.setAssociate2(AssociateDB.selectAssociateInfo(rs.getInt("preferred_associateID")));
                cc.setClient(CustomerDB.selectClient(rs.getInt("id")));

                clients.add(cc);
            }
            return clients;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectClientsAll ", e.toString(), " AllClients ");
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a product isn't found.
    public static List<String> selectAllClients()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM customer";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            List<String> client = new ArrayList<>();
            while (rs.next())
            {
                client.add(rs.getString("first_name"));
                client.add(rs.getString("last_name"));
            }
            Collections.sort(client);
            return client;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectAllClients", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a client isn't found.
    public static HashMap<String, Client> selectClients()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM appointment.customer c1 "
                + "INNER JOIN appointment.customer_alert_prefs c2 "
                + "ON c1.id = c2.customer_id_alerts "
                + "ORDER BY c1.first_name";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            HashMap clients = new HashMap();
            String id;
            while (rs.next())
            {
                Client c = new Client();
                id = Integer.toString(rs.getInt("id"));
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setCompany(rs.getString("company"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setHomePhone(rs.getString("home_phone"));
                c.setWorkPhone(rs.getString("work_phone"));
                c.setMobilePhone(rs.getString("mobile_phone"));
                c.setCity(rs.getString("city"));
                c.setState(rs.getString("state"));
                c.setZip(rs.getString("zip_code"));
                c.setCcNumber(rs.getString("cc_number"));
                c.setMemberLevel(rs.getString("member_level"));
                c.setPreferredAssociateId(rs.getInt("preferred_associateID"));
                c.setServiceNormalid(rs.getInt("normal_service_id"));
                c.setServiceName(c.getServiceName());
                c.setImgUpl(rs.getBoolean("client_img_uploaded"));
                c.setEmailAdAlerts(rs.getBoolean("cust_email_adAlerts"));
                c.setEmailApptAlerts(rs.getBoolean("cust_email_apptAlerts"));
                c.setSmsAdAlerts(rs.getBoolean("cust_sms_adAlerts"));
                c.setSmsApptAlerts(rs.getBoolean("cust_sms_apptAlerts"));

                clients.put(id, c);
            }
            return clients;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectClients HashMap", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a product isn't found.
    public static ArrayList<MemberExtras> selectMemberLevels()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM appointment.memberLevels";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<MemberExtras> levels = new ArrayList();
            while (rs.next())
            {
                MemberExtras m = new MemberExtras();
                m.setIdMemberLevels(rs.getInt("idMemberLevels"));
                m.setMemberLevel(rs.getString("memberLevel"));
                m.setMemberColor(rs.getString("levelColor"));
                levels.add(m);
            }
            return levels;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectMemberLevels", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a product isn't found.
    public static HashMap<Integer, String> selectLevels()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM appointment.memberLevels";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            HashMap<Integer, String> levels = new HashMap();
            while (rs.next())
            {
                int idMemberLevel = rs.getInt("idMemberLevels");
                String memberLevel = rs.getString("memberLevel");
                levels.put(idMemberLevel, memberLevel);
            }
            return levels;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB selectLevels", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void updateActiveStatus(List<FullCalendar2> fcArray)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE customer SET "
                + "isAccount_active = ? "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);

            for (FullCalendar2 fc : fcArray)
            {
                ps.setBoolean(1, fc.getClient().isIsAccountActive());
                ps.setInt(2, fc.getClient().getId());

                ps.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB deActiveClient", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Client isClientOfAssociate(String mobilePh, int a)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Client c = null;

        String query = "SELECT * FROM assocoate_clients "
                + "WHERE client_mobile_phone = ? AND assocoate_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, mobilePh);
            ps.setInt(2, a);
            rs = ps.executeQuery();
            if (rs.next())
            {
                c = selectClient(rs.getInt("client_id"));
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB clientExists by mobile phone", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return c;
    }

    public static Client isClientAndActive(String mobilePh, int a)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Client c = null;

        String query = "SELECT * FROM appointment.assocoate_clients "
                + "JOIN customer "
                + "ON client_id = id "
                + "WHERE assocoate_id = ? AND client_mobile_phone = ? AND isAccount_active = true";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, a);
            ps.setString(2, mobilePh);
            rs = ps.executeQuery();
            if (rs.next())
            {
                c = selectClient(rs.getInt("client_id"));
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CustomerDB clientExists by mobile phone", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return c;
    }
}
