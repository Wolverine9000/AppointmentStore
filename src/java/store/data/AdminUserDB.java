/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import messages.LogFile;
import store.business.SystemAdmin;

/**
 *
 * @author williamdobbs
 */
public class AdminUserDB
{

    public static boolean checkAdminCredenitals(String username, String password)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM UserPass "
                + "WHERE Username = ? "
                + "AND Password = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AdminUserDB checkAdminCredenitals error " + username + " ", e.getMessage(), e.toString());
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean insertEmailCredentials(String emailAddress, String password, String salt)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO system_users "
                + "(system_user_email_address, system_user_email_password, system_user_email_passwordl_salt) "
                + "VALUES (?, ?, ?)";

        try
        {
            ps = connection.prepareStatement(query);

            ps.setString(1, emailAddress);
            ps.setString(2, password);
            ps.setString(3, salt);

            ps.executeUpdate();

            return false;
        }
        catch (SQLException sql)
        {
            LogFile.databaseError("AdminUserDB insertEmailCredentials " + emailAddress + " ", sql.toString(), sql.getMessage());
            return true;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static boolean updateEmailCredentials(String emailAddress, String password, String username, int userId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE system_users SET "
                + "system_user_email_address = ?, "
                + "system_user_email_password = ?, "
                + "system_username = ? "
                + "WHERE system_userid = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, emailAddress);
            ps.setString(2, password);
            ps.setString(3, username);
            ps.setInt(4, userId);

            ps.executeUpdate();
            return false;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AdminUserDB updateEmailCredentials", e.getMessage(), e.toString());
            return true;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static SystemAdmin selectSysAdmin(int sysId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM system_users "
                + "WHERE system_userid = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, sysId);
            rs = ps.executeQuery();
            if (rs.next());
            {
                SystemAdmin a = new SystemAdmin();
                a.setSysId(sysId);
                a.setSysEmailAddress(rs.getString("system_user_email_address"));
                a.setUserName(rs.getString("system_username"));
                a.setPassword(rs.getString("system_user_email_password"));
                a.setSmsPassword(rs.getString("system_sms_password"));
                a.setSmsURL(rs.getString("system_sms_url"));
                a.setSmsUsername(rs.getString("system_sms_username"));
                
                return a;
            }
        }

        catch (SQLException e)
        {
            LogFile.databaseError("AdminUserDB selectSysAdmin", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

}
