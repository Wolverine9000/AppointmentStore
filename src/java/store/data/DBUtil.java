package store.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil
{

    public static void closeStatement(Statement s)
    {
        try
            {
            if (s != null)
                {
                s.close();
                }
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            }
    }

    public static void closePreparedStatement(Statement ps)
    {
        try
            {
            if (ps != null)
                {
                ps.close();
                }
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            }
    }

    public static void closeResultSet(ResultSet rs)
    {
        try
            {
            if (rs != null)
                {
                rs.close();
                }
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            }
    }

    public static void rollback(Connection c)
    {
        try
            {
            if (c != null)
                {
                c.rollback();
                }
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            }
    }
}
