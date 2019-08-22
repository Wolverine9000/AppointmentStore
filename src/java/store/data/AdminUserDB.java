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
}
