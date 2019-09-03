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

        String query = "INSERT INTO sys_email "
                + "(sys_email_addr, sys_email_password, sys_email_salt) "
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
    public static boolean updateEmailCredentials(String emailAddress, String password, String salt, int userId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
         String query = "UPDATE sys_email SET "
                + "sys_email_addr = ?, "
                + "sys_email_password = ?, "
                + "sys_email_salt = ? "
                + "WHERE sys_email_userid = ?";
         
           try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, emailAddress);
            ps.setString(2, password);
            ps.setString(3, salt);
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

}
