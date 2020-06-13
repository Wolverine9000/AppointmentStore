/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import store.business.Client;
import store.business.Register;

/**
 *
 * @author williamdobbs
 */
public class RegistrationDB
{

    public static int insertRegister(String email, int regCode)
      {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method adds registration code table in the database
        String query =
                "INSERT INTO registration "
                + "(email_address, reg_code) "
                + "VALUES (?, ?)";
        try
            {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setInt(2, regCode);
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

    public static Client isRegistrationValid(String email, int regCode)
      {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT email_address, reg_code "
                + "FROM registration "
                + "WHERE email_address = ? AND reg_code = ?";
        try
            {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setInt(2, regCode);
            rs = ps.executeQuery();
            if (rs.next())
                {
                Client c = new Client();
                c.setEmail(rs.getString("email_address"));

                return c;
                }
            else
                {
                return null;
                }
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            return null;
            }
        finally
            {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
            }
      }

    public static int deleteRegistration(String email, int regCode)
      {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM registration "
                + "WHERE email_address = ? AND reg_code = ?";
        try
            {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setInt(2, regCode);
            ps.executeUpdate();
            return 1;
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            return 0;
            }
        finally
            {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
            }
      }
  
    public static Register registrationExist(String email)
      {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM registration "
                + "WHERE email_address = ?";
        try
            {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next())
                {
                Register r = new Register();
                r.setEmailAddress(rs.getString("email_address"));
                r.setRegCode(rs.getInt("reg_code"));

                return r;
                }
            else
                {
                return null;
                }
            }
        catch (SQLException e)
            {
            e.printStackTrace();
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
