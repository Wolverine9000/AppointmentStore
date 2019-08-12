/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import messages.LogFile;
import security.SecurityQuestions;

/**
 *
 * @author williamdobbs
 */
public class SecurityDB
{

    //This method returns null if a product isn't found.
    public static ArrayList<SecurityQuestions> selectQuestions()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM security_questions";
        try
            {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<SecurityQuestions> questions = new ArrayList<>();

            while (rs.next())
                {
                SecurityQuestions q = new SecurityQuestions();
                q.setQuestionSet(rs.getInt("security_question_set"));
                q.setQuestionId(rs.getInt("secure_questions_id"));
                q.setQuestion(rs.getString("questions"));

                questions.add(q);
                }
            return questions;
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

    public static int insertSecurityInfo(String email, String question, String answer)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method adds a new record to the customer table in the database
        String query
                = "INSERT INTO security (email_id, security_question, security_answer) "
                + "VALUES (?, ?, ?)";
        try
            {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, question);
            ps.setString(3, answer);
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

    public static void insertChallengeQandA(int securityQiid, int userId, String emailAddress, String securityAnswer)
            throws SQLException
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the client table in the database
        String query
                = "INSERT INTO appointment.security ("
                + "security_id, "
                + "user_id, "
                + "email_id,"
                + "security_answer) "
                + "VALUES (?, ?, ?, ?)";

        try
            {
            ps = connection.prepareStatement(query);

            ps.setInt(1, securityQiid);
            ps.setInt(2, userId);
            ps.setString(3, emailAddress);
            ps.setString(4, securityAnswer);

            ps.executeUpdate();
            LogFile.generalLog("SecurityDB insertChallengeQandA success", " user id " + userId);
            }
        finally
            {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
            }
    }

    public static void deleteUserQandA(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM appointment.security "
                + "WHERE user_id = ?";
        try
            {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.executeUpdate();
            }
        catch (SQLException e)
            {
            LogFile.databaseError("SecurityDB deleteUserQandA error ", e.getMessage(), e.toString());
            }
        finally
            {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
            }
    }
}
