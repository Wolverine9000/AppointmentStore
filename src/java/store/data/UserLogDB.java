/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import java.sql.*;

/**
 *
 * @author williamdobbs
 */
public class UserLogDB
{
    //This method adds one lineItem to the line_item table.
    public static int insert(int customerId, Timestamp timestamp, String inOut)
    {
      ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //This method logs a customer's time in and time out
        String query =
                "INSERT INTO log_records (user_id, log_timestamp, in_out) "
                + "VALUES (?, ?, ?)";
        try
            {
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setTimestamp(2, timestamp);
            ps.setString(3, inOut);
            return ps.executeUpdate();
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            return 0;
            }
        finally
            {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
            }
      }
    
    //This method logs an associate's time in and time out
    public static int insertAssociate(int associateId, Timestamp timestamp, String inOut)
    {
      ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query =
                "INSERT INTO associate_log (associate_id, timestamp, in_out) "
                + "VALUES (?, ?, ?)";
        try
            {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setTimestamp(2, timestamp);
            ps.setString(3, inOut);
            return ps.executeUpdate();
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            return 0;
            }
        finally
            {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
            }
      }
}
