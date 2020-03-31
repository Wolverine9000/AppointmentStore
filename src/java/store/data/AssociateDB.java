/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import messages.LogFile;
import store.business.Associate2;
import store.business.FullCalendar2;
import store.business.Services;
import store.util.PasswordUtil;

/**
 *
 * @author williamdobbs
 */
public class AssociateDB
{

    //This method returns null if a Associate Availability records aren't found.
    public static ArrayList<Associate2> selectAssociateAvailability(int day, int year, int month)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT id, first_name, last_name, email FROM associate_availability "
                + "INNER JOIN associate "
                + "ON associate_avail_id = associate.id "
                + "WHERE associate_availability.associate_avail_year = ? "
                + "AND associate_availability.associate_avail_date = ? "
                + "AND associate_availability.associate_avail_month = ? "
                + "ORDER BY associate_avail_startTimeHour ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, year);
            ps.setInt(2, day);
            ps.setInt(3, month);
            rs = ps.executeQuery();
            ArrayList<Associate2> associateAvailability = new ArrayList<>();
            while (rs.next())
            {
                Associate2 a = new Associate2();
                a.setAssociateAvailabilityId(rs.getInt("associate_availability_id"));
                a.setAssociateAvailId(rs.getInt("associate_avail_id"));
                a.setAssociateAvailYear(rs.getInt("associate_avail_year"));
                a.setAssociateAvailMonth(rs.getInt("associate_avail_month"));
                a.setAssociateAvailDay(rs.getInt("associate_avail_day"));
                a.setAssociateAvailDate(rs.getInt("associate_avail_date"));
                a.setAssociateAvailStartTimeHour(rs.getInt("associate_avail_startTimeHour"));
                a.setAssociateAvailEndTimeMin(rs.getInt("associate_avail_endTimeMin"));
                a.setAssociateAvailName(rs.getString("first_name"));

                associateAvailability.add(a);
            }
            return associateAvailability;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateAvailability error ", e.getMessage(), e.toString());
//            e.printStackTrace();
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Associate2> selectAssociateAvailability(int service, int day, int year, int month)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate_availability a "
                + "JOIN associate_services s "
                + "ON a.associate_avail_id = s.associate_id "
                + "JOIN appointment.associate an "
                + "ON a.associate_avail_id = an.id "
                + "WHERE s.service_id = ? AND a.associate_avail_date = ? AND "
                + "a.associate_avail_year = ? AND a.associate_avail_month = ? "
                + "ORDER BY associate_avail_startTimeHour ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, service);
            ps.setInt(2, day);
            ps.setInt(3, year);
            ps.setInt(4, month);
            rs = ps.executeQuery();
            ArrayList<Associate2> aa = new ArrayList<>();
            while (rs.next())
            {
                Associate2 a = new Associate2();
                a.setAssociateAvailabilityId(rs.getInt("associate_availability_id"));
                a.setAssociateAvailId(rs.getInt("associate_avail_id"));
                a.setAssociateAvailYear(rs.getInt("associate_avail_year"));
                a.setAssociateAvailMonth(rs.getInt("associate_avail_month"));
                a.setAssociateAvailDay(rs.getInt("associate_avail_day"));
                a.setAssociateAvailDate(rs.getInt("associate_avail_date"));
                a.setAssociateAvailStartTimeHour(rs.getInt("associate_avail_startTimeHour"));
                a.setAssociateAvailEndTimeMin(rs.getInt("associate_avail_endTimeMin"));
                a.setAssociateAvailName(rs.getString("first_name"));

                aa.add(a);
            }
            return aa;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateAvailability 3-arguments error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Associate2> selectAvailableTimes(int day, int year, int month, int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT associate_avail_startTimeHour, associate_avail_endTimeMin "
                + "FROM associate_availability "
                + "WHERE associate_avail_date = ? AND associate_avail_year = ? "
                + "AND associate_avail_month = ? AND associate_avail_id = ? "
                + "ORDER BY associate_avail_startTimeHour ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, day);
            ps.setInt(2, year);
            ps.setInt(3, month);
            ps.setInt(4, associateId);
            rs = ps.executeQuery();
            ArrayList<Associate2> aa = new ArrayList<>();
            while (rs.next())
            {
                Associate2 a = new Associate2();
                a.setAssociateAvailStartTimeHour(rs.getInt("associate_avail_startTimeHour"));
                a.setAssociateAvailEndTimeMin(rs.getInt("associate_avail_endTimeMin"));

                aa.add(a);
            }
            return aa;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAvailableTimes 4-arguments error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Associate2> selectAvailableTimes(Date date, int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM associate_availability "
                + "WHERE associate_avail_datestamp = ? "
                + "AND associate_avail_id = ? "
                + "ORDER BY associate_avail_time ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setDate(1, date);
            ps.setInt(2, associateId);
            rs = ps.executeQuery();
            ArrayList<Associate2> aa = new ArrayList<>();
            while (rs.next())
            {
                Associate2 a = new Associate2();
                a.setAssociateAvailTimestamp(rs.getTime("associate_avail_time"));
                a.setAssociateAvailDatestamp(rs.getDate("associate_avail_datestamp"));
                a.setAssociateAvailEndTimeMin(rs.getInt("associate_avail_id"));

                aa.add(a);
            }
            return aa;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAvailableTimes 2-arguments error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Associate2> selectAvailableTimes(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM associate_availability "
                + "WHERE associate_avail_id = ? "
                + "ORDER BY associate_avail_time ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            rs = ps.executeQuery();
            ArrayList<Associate2> aa = new ArrayList<>();
            while (rs.next())
            {
                Associate2 a = new Associate2();
                a.setAssociateAvailTimestamp(rs.getTime("associate_avail_time"));
                a.setAssociateAvailDatestamp(rs.getDate("associate_avail_datestamp"));
                a.setAssociateAvailEndTimeMin(rs.getInt("associate_avail_id"));

                aa.add(a);
            }
            return aa;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAvailableTimes 1-arguments error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Associate2> selectAssociateAvailableTimes(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT associate_avail_id, associate_availability_id, first_name, last_name, email, "
                + "associate_imgUpload, associate_avail_datestamp, associate_avail_timestamp, associate_avail_time, associate_EndTimestamp, mobile_phone, work_phone, home_phone "
                + "FROM associate_availability a1 "
                + "INNER JOIN associate a2 "
                + "ON a1.associate_avail_id = a2.id "
                + "INNER JOIN associate_info a3 "
                + "ON a1.associate_avail_id = a3.associate_info_id "
                + "WHERE a1.associate_avail_id = ? AND a1.associate_avail_datestamp >= CURDATE() "
                + "ORDER BY a1.associate_avail_datestamp;";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            rs = ps.executeQuery();
            ArrayList<Associate2> aa = new ArrayList<>();
            while (rs.next())
            {
                Associate2 a = new Associate2();
                a.setImgUpl(rs.getBoolean("associate_imgUpload"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setEmail(rs.getString("email"));
                a.sethomePhone(rs.getString("home_phone"));
                a.setWorkPhone(rs.getString("work_phone"));
                a.setMobilePhone(rs.getString("mobile_phone"));
                a.setId(rs.getInt("associate_avail_id"));
                a.setAssociateAvailDatestamp(rs.getDate("associate_avail_datestamp"));
                a.setAssocAvailTimeStamp(rs.getTimestamp("associate_avail_timestamp"));
                a.setAssociateAvailTime(rs.getTime("associate_avail_time"));
                a.setAssocAvailEndTimeStamp(rs.getTimestamp("associate_EndTimestamp"));
                a.setAssociateAvailabilityId(rs.getInt("associate_availability_id"));

                aa.add(a);
            }
            return aa;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateAvailableTimes error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Associate2> selectAssociatesAvailable(Date sqlDate)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate a "
                + "JOIN associate_date ad "
                + "ON a.id = ad.associate_date_id "
                + "WHERE ad.associate_date = ? "
                + "ORDER BY a.first_name ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setDate(1, sqlDate);
            rs = ps.executeQuery();

            ArrayList<Associate2> aa = new ArrayList<>();
            while (rs.next())
            {
                Associate2 a = new Associate2();
                a.setId(rs.getInt("id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setEmail(rs.getString("email"));

                aa.add(a);
            }
            return aa;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociatesAvailable error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean isAssociateAvailability(int associateId, Date availDate)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate_date "
                + "WHERE associate_date_id = ? AND associate_date = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, availDate);

            rs = ps.executeQuery();
            if (rs == null)
            {
                return false;
            }
            else
            {
                return rs.next();
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB isAssociateAvailability error ", e.getMessage(), e.toString());
            return false;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean isAssociateTimeAvailable(int associateId, Date availDate, Time availTime)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate_availability "
                + "WHERE associate_date_id = ? AND associate_date = ? AND associate_time = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, availDate);
            ps.setTime(3, availTime);

            rs = ps.executeQuery();
            if (rs == null)
            {
                return false;
            }
            else
            {
                return rs.next();
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB isAssociateTimeAvailable error ", e.getMessage(), e.toString());
            return false;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Services> selectAssociateServices(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT service_id, service_product_id, name, description, price, service_time "
                + "FROM services ap "
                + "JOIN associate_services s "
                + "ON ap.service_id = s.service_id_asso  "
                + "WHERE s.associate_id = ? "
                + "ORDER BY ap.name ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            rs = ps.executeQuery();
            ArrayList<Services> services = new ArrayList<>();
            while (rs.next())
            {
                Services p = new Services();
                p.setServiceId(rs.getInt("service_id"));
                p.setServiceProductId(rs.getInt("service_product_id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                p.setServiceTime(rs.getInt("service_time"));
                p.setCanDoService(true);

                services.add(p);
            }

            return services;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateServices error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean isAssociateService(int associateId, int serviceId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate_services "
                + "WHERE service_id_asso = ? "
                + "AND associate_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, serviceId);
            ps.setInt(2, associateId);
            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB isAssociateService error ", e.getMessage(), e.toString());
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a service isn't found.
    public static ArrayList<Integer> selectAllServices()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM services "
                + "ORDER BY name ASC";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Integer> services = new ArrayList<>();
            while (rs.next())
            {
                services.add(rs.getInt("service_id"));
            }
            return services;
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

    //This method returns null if a service isn't found.
    public static ArrayList<Services> selectAllAsscSvcs(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM services "
                + "ORDER BY service_id ASC";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Services> services = new ArrayList<>(); // get all services
            ArrayList<Integer> associateSvcs; // get associate services
            // TODO provide code for occasional null pointer exception when retriveing associate services
            associateSvcs = selectAssociateSvcs(associateId);
            int serviceNum;
            int productNum;
            int associateSvcsSize = associateSvcs.size();
            while (rs.next())
            {
                Services s = new Services();
                s.setServiceId(rs.getInt("service_id"));
                s.setCategory_id(rs.getInt("service_category_service_category_id"));
                s.setServiceProductId(rs.getInt("service_product_id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setPrice(rs.getDouble("price"));
                s.setServiceTime(rs.getInt("service_time"));
                s.setGraceTime(rs.getInt("grace_time"));

                s.setCanDoService(false);
                productNum = s.getServiceId();

                if (associateSvcsSize > 0)
                {
                    for (int i = 0; i < associateSvcsSize; i++)
                    {
                        serviceNum = associateSvcs.get(i);
                        if (productNum == serviceNum) // does associate do service
                        {
                            s.setCanDoService(true);
                        }
                    }
                }
                services.add(s);
            }
            return services;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("AssociateDB selectAllAsscSvcs error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Integer> selectAssociateSvcs(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate_services "
                + "WHERE associate_id = ? "
                + "ORDER BY service_id_asso ASC";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            rs = ps.executeQuery();
            ArrayList<Integer> assoSvc = new ArrayList<>();
            while (rs.next())
            {
                assoSvc.add(rs.getInt("service_id_asso"));
            }
            return assoSvc;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateSvcs error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void deleteAvailability(FullCalendar2 fc)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM appointment.associate_availability "
                + "WHERE associate_avail_id = ? "
                + "AND associate_avail_timestamp >= ? AND associate_EndTimestamp <= ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, fc.getAssociate2().getId());
            ps.setTimestamp(2, fc.getStartSql());
            ps.setTimestamp(3, fc.getEndSql());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB deleteAvailability FC error ", e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int deleteAvailability(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM associate_availability "
                + "WHERE associate_avail_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB deleteAvailability int error ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int deleteAsscSvc(int associateId, int serviceId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM associate_services "
                + "WHERE associate_id = ? AND service_id_asso = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setInt(2, serviceId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB deleteAsscSvc error ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int deleteAllAssoSvcs(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query;
        query = "DELETE FROM associate_services "
                + "WHERE associate_id = ? ";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB deleteAllAssoSvcs error ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Map<Integer, Services> selecAllServices()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM services s "
                + "JOIN service_category sc "
                + "ON s.service_category_service_category_id = sc.service_category_id";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            Map<Integer, Services> services = new HashMap<>();
            while (rs.next())
            {
                Services s = new Services();
                s.setServiceId(rs.getInt("service_id"));
                s.setServiceProductId(rs.getInt("service_product_id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setPrice(rs.getDouble("price"));
                s.setServiceTime(rs.getInt("service_time"));
                s.setGraceTime(rs.getInt("grace_time"));
                s.setCategory(rs.getString("category_name"));
                s.setCategory_id(rs.getInt("service_category_service_category_id"));

                services.put(s.getServiceId(), s);
            }
            return services;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selecAllServices MAP error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a category name isn't found.
    public static Services selectService(int serviceId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM services s "
                + "JOIN service_category sc "
                + "ON s.service_category_service_category_id = sc.service_category_id "
                + "WHERE service_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();
            if (rs.next());
            {
                Services s = new Services();
                s.setServiceProductId(rs.getInt("service_product_id"));
                s.setServiceId(rs.getInt("service_id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setPrice(rs.getDouble("price"));
                s.setServiceTime(rs.getInt("service_time"));
                s.setGraceTime(rs.getInt("grace_time"));
                s.setCategory(rs.getString("category_name"));
                s.setCategory_id(rs.getInt("service_category_service_category_id"));

                return s;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectService error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a category name isn't found.
    public static int selectMsgsSent(int associtateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT SUM(total_recipients) "
                + "FROM appointment.message_responses "
                + "WHERE msg_sentBy_id = ? AND message_status = 'Success'";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associtateId);
            rs = ps.executeQuery();

            int total = 0;
            if (rs.next());
            {
                total = rs.getInt("SUM(total_recipients)");
                return total;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectMsgsSent error ", e.getMessage(), e.toString());
            return -1;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int deleteAvailability(int associateId, Date availDate, Time availTime)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM associate_availability "
                + "WHERE associate_avail_time = ? "
                + "AND associate_avail_timestamp >= ? AND associate_EndTimestamp <= ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, availDate);
            ps.setTime(3, availTime);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB deleteAvailability error ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int deleteAvailability(int associateId, Date availDate)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM associate_availability "
                + "WHERE associate_avail_id = ? AND associate_avail_datestamp = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, availDate);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.associateLog("AssociateDB deleteAvailability FAILED " + "AssociateID:"
                    + associateId + " availDate:" + availDate, e.toString(), e.getMessage());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

//    public static int deleteAvailability(int associateId)
//    {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String query = "DELETE FROM associate_availability "
//                + "WHERE associate_avail_id = ?";
//        try
//            {
//            ps = connection.prepareStatement(query);
//            ps.setInt(1, associateId);
//
//            return ps.executeUpdate();
//            }
//        catch (SQLException e)
//            {
//            e.printStackTrace();
//            return 0;
//            }
//        finally
//            {
//            DBUtil.closePreparedStatement(ps);
//            pool.freeConnection(connection);
//            }
//    }
    public static int deleteAvailabilityDate(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM associate_date "
                + "WHERE associate_date_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.associateLog("AssociateDB deleteAvailabilityDate FAILED " + "AssociateID:"
                    + associateId, e.toString(), e.getMessage());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int delAssoicateTimeSlot(int evtId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM associate_availability "
                + "WHERE associate_availability_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, evtId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.associateLog("AssociateDB delAssoicateTimeSlot FAILED " + "EventID:" + evtId, e.toString(), e.getMessage());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int deleteAvailabilityDate(int associateId, Date date)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM associate_date "
                + "WHERE associate_date_id = ? "
                + "AND associate_date = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, date);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.associateLog("AssociateDB deleteAvailabilityDate FAILED " + "AssociateID "
                    + associateId + date.toString(), e.toString(), e.getMessage());
            return 0;
        }
        finally
        {
            LogFile.associateLog("AssociateDB deleteAvailabilityDate SUCCESS " + "AssociateID "
                    + associateId, date.toString(), date.toString());
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int deleteAvailabilityDate(int associateId, Date frDate, Date toDate)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM associate_date "
                + "WHERE associate_date_id = ? "
                + "AND associate_date >= ? "
                + "AND associate_date <= ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, frDate);
            ps.setDate(3, toDate);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.associateLog("AssociateDB deleteAvailabilityDate FAILED " + "AssociateID "
                    + associateId + "associate_FROM_date:" + frDate + "associate_TO_date:" + toDate, e.toString(), e.getMessage());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int deleteDateRange(int associateId, Date start, Date end)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM associate_availability "
                + "WHERE associate_avail_id = ? AND associate_avail_datestamp >= ? "
                + "AND associate_avail_datestamp <= ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, start);
            ps.setDate(3, end);

            return ps.executeUpdate();
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
// this method returns null if an associate isn't found

    public static Associate2 selectAssociate(String associateEmail)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, associateEmail);
            rs = ps.executeQuery();
            if (rs.next())
            {
                Associate2 a = new Associate2();
                a.setId(rs.getInt("assoicate_info_assoicate_info_id"));
                a.setId(rs.getInt("id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setEmail(rs.getString("email"));
                return a;
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

    // this method returns null if an associate isn't found
    public static HashMap<String, Associate2> selectAllAssociates()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate aa "
                + "jOIN associate_info ai "
                + "ON aa.assoicate_info_assoicate_info_id = ai.associate_info_id";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            HashMap associates = new HashMap();
            String id;
            while (rs.next())
            {
                Associate2 a = new Associate2();
                id = Integer.toString(rs.getInt("id"));
                //a.set(rs.getInt("assoicate_info_assoicate_info_id"));
                a.setId(rs.getInt("id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setEmail(rs.getString("email"));
                a.setMobilePhone(rs.getString("mobile_phone"));
                a.setAddress(rs.getString("address"));
                a.setCity(rs.getString("city"));
                a.setState(rs.getString("state"));
                a.setZip(rs.getString("zip"));

                associates.put(id, a);
            }
            return associates;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAllAssociates error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    // this method returns null if an associate isn't found
    public static ArrayList<Associate2> selectAssociatesAll()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT id, first_name, last_name, email, associate_imgUpload, home_phone, work_phone, "
                + "mobile_phone, other_phone, assoc_email_adAlerts, assoc_email_apptAlerts, assoc_sms_adAlerts, assoc_sms_apptAlerts, "
                + "default_view, mor_time_in, mor_time_out "
                + "FROM associate aa "
                + "jOIN associate_info ai "
                + "ON aa.assoicate_info_assoicate_info_id = ai.associate_info_id "
                + "JOIN associate_alert_prefs a3 "
                + "ON aa.id = a3.assoc_id_alert "
                + "JOIN associate_calendar_prefs ap "
                + "ON aa.id = ap.associate_id "
                + "ORDER BY first_name";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Associate2> associates = new ArrayList<>();
            while (rs.next())
            {
                Associate2 a = new Associate2();
                a.setId(rs.getInt("id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setEmail(rs.getString("email"));
                a.setImgUpl(rs.getBoolean("associate_imgUpload"));
                a.setHomePhone(rs.getString("home_phone"));
                a.setWorkPhone(rs.getString("work_phone"));
                a.setMobilePhone(rs.getString("mobile_phone"));
                a.setOtherPhone(rs.getString("other_phone"));
                a.setEmailAdAlerts(rs.getBoolean("assoc_email_adAlerts"));
                a.setEmailApptAlerts(rs.getBoolean("assoc_email_apptAlerts"));
                a.setSmsAdAlerts(rs.getBoolean("assoc_sms_adAlerts"));
                a.setSmsApptAlerts(rs.getBoolean("assoc_sms_apptAlerts"));
                a.setDefaultCalendarView(rs.getString("default_view"));
                a.setMorningTimeInSql(rs.getTimestamp("mor_time_in"));
                a.setMorningTimeOutSql(rs.getTimestamp("mor_time_out"));

                associates.add(a);
            }
            return associates;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociatesAll error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Associate2 selectAssociate(String email, String password)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM associate "
                + "WHERE email = ? AND password = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next())
            {
                Associate2 a = new Associate2();
                a.setId(rs.getInt("id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setEmail(rs.getString("email"));

                return a;
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociate error " + email + " ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Associate2 selectAssociateHash(String email, String password)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT email, p.salt, p.passwordHashAndSalted,id, first_name, last_name, email, c.default_view "
                + "FROM associate a JOIN associate_pass p "
                + "JOIN associate_calendar_prefs c "
                + "ON a.id = c.associate_id "
                + "WHERE a.email = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next())
            {
                String salt = rs.getString("salt");
                String hashAndSalt1 = rs.getString("passwordHashAndSalted");
                String hashAndSalt2 = PasswordUtil.hashPassword(password + salt);

                if (hashAndSalt1 == null ? hashAndSalt2 == null : hashAndSalt1.equals(hashAndSalt2))
                {
                    Associate2 a = new Associate2();
                    a.setId(rs.getInt("id"));
                    a.setFirstName(rs.getString("first_name"));
                    a.setLastName(rs.getString("last_name"));
                    a.setEmail(rs.getString("email"));
                    a.setDefaultCalendarView(rs.getString("default_view"));

                    return a;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateHash error " + email + " ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Associate2 selectAssociateInfo(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM appointment.associate_info a1 "
                + "INNER JOIN appointment.associate a2 "
                + "ON a1.associate_info_id = a2.id "
                + "INNER JOIN appointment.associate_alert_prefs a3 "
                + "ON a1.associate_info_id = a3.assoc_id_alert "
                + "WHERE a1.associate_info_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            rs = ps.executeQuery();
            if (rs.next())
            {
                Associate2 a = new Associate2();
                a.setImgUpl(rs.getBoolean("associate_imgUpload"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setEmail(rs.getString("email"));
                a.sethomePhone(rs.getString("home_phone"));
                a.setWorkPhone(rs.getString("work_phone"));
                a.setMobilePhone(rs.getString("mobile_phone"));
                a.setOtherPhone(rs.getString("other_phone"));
                a.setAddress(rs.getString("address"));
                a.setCity(rs.getString("city"));
                a.setState(rs.getString("state"));
                a.setZip(rs.getString("zip"));
                a.setId(rs.getInt("associate_info_id"));
                a.setEmailAdAlerts(rs.getBoolean("assoc_email_adAlerts"));
                a.setEmailApptAlerts(rs.getBoolean("assoc_email_apptAlerts"));
                a.setSmsAdAlerts(rs.getBoolean("assoc_sms_adAlerts"));
                a.setSmsApptAlerts(rs.getBoolean("assoc_sms_apptAlerts"));

                return a;
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateInfo error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Associate2 selectAssociateProfile(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT associate_imgUpload, first_name, last_name, email, home_phone, work_phone, "
                + "mobile_phone, other_phone, address, city, state, zip, associate_info_id, assoc_email_adAlerts, "
                + "assoc_email_apptAlerts, assoc_sms_adAlerts, assoc_sms_apptAlerts, "
                + "default_view, mor_time_in, mor_time_out, aft_time_in, aft_time_out "
                + "FROM associate_info a1 "
                + "INNER JOIN associate a2 "
                + "ON a1.associate_info_id = a2.id "
                + "INNER JOIN associate_alert_prefs a3 "
                + "ON a1.associate_info_id = a3.assoc_id_alert "
                + "JOIN associate_calendar_prefs ap "
                + "ON a1.associate_info_id = ap.associate_id "
                + "WHERE a1.associate_info_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            rs = ps.executeQuery();
            LocalDate date = LocalDate.now(); // create instance of current date
            ValueRange range = date.range(ChronoField.DAY_OF_MONTH);
            Long max = range.getMaximum();
            LocalDate lastDom = date.withDayOfMonth(max.intValue()); // get the last day of the month
            LocalDate firstDom = date.withDayOfMonth(1);
            String lastDayStr = lastDom.toString(); // convert last day of month LocalDate object to string value
            String firstDayStr = firstDom.toString(); // convert first day of month LocalDate object to string value
            if (rs.next())
            {
                Associate2 a = new Associate2();
                a.setImgUpl(rs.getBoolean("associate_imgUpload"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setEmail(rs.getString("email"));
                a.sethomePhone(rs.getString("home_phone"));
                a.setWorkPhone(rs.getString("work_phone"));
                a.setMobilePhone(rs.getString("mobile_phone"));
                a.setOtherPhone(rs.getString("other_phone"));
                a.setAddress(rs.getString("address"));
                a.setCity(rs.getString("city"));
                a.setState(rs.getString("state"));
                a.setZip(rs.getString("zip"));
                a.setId(rs.getInt("associate_info_id"));
                a.setEmailAdAlerts(rs.getBoolean("assoc_email_adAlerts"));
                a.setEmailApptAlerts(rs.getBoolean("assoc_email_apptAlerts"));
                a.setSmsAdAlerts(rs.getBoolean("assoc_sms_adAlerts"));
                a.setSmsApptAlerts(rs.getBoolean("assoc_sms_apptAlerts"));
                // get total number sms messages sent by associate
                a.setSmsSent(selectAssociateSmsTotal(associateId));
                // get number of sms text messages sent this month
                a.setSmsSentRange(selectAssociateSmsRange(associateId, firstDayStr, lastDayStr));
                a.setMemberSettings(selectMemberSettings(associateId));
                a.setDefaultCalendarView(rs.getString("default_view"));
                a.setMorningTimeInSql(rs.getTimestamp("mor_time_in"));
                a.setMorningTimeOutSql(rs.getTimestamp("mor_time_out"));
                a.setAfternoonTimeInSql(rs.getTimestamp("aft_time_in"));
                a.setAfternoonTimeOutSql(rs.getTimestamp("aft_time_out"));

                return a;
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateProfile error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int selectAssociateSmsRange(int associateId, String from, String to)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT SUM(credits_charged) "
                + "FROM appointment.message_responses "
                + "WHERE  msg_sentBy_id = ? AND message_timestamp >= ? AND message_timestamp <= ?  AND message_status = 'success'";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setString(2, from);
            ps.setString(3, to);
            rs = ps.executeQuery();
            if (rs.next())
            {
                int smsMessages = rs.getInt("SUM(credits_charged)");
                return smsMessages;
            }
            else
            {
                return 0;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateSmsRange error ", e.getMessage(), e.toString());
            return -1;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int selectAssociateSmsTotal(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT SUM(total_recipients) "
                + "FROM appointment.message_responses "
                + "WHERE  msg_sentBy_id = ? AND message_status = 'success'";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            rs = ps.executeQuery();
            if (rs.next())
            {
                int smsTotalSentMsgs = rs.getInt("SUM(total_recipients)");
                return smsTotalSentMsgs;
            }
            else
            {
                return 0;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectAssociateSmsRange error ", e.getMessage(), e.toString());
            return -1;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static HashMap<String, Integer> selectMemberSettings(int id)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM appointment.associate_member_settings "
                + "WHERE associate_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            HashMap m = new HashMap();
            rs = ps.executeQuery();
            while (rs.next())
            {
                m.put("Platinum", rs.getInt("platinum"));
                m.put("Gold", rs.getInt("gold"));
                m.put("Silver", rs.getInt("silver"));
                m.put("Bronze", rs.getInt("bronze"));
            }
            return m;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB selectMemberSettings error ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean updateAssociatePassword(Associate2 associate)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String password = associate.getPassword();
        int associateId = associate.getId();

        String salt = PasswordUtil.getSalt();
        String pwdHashAndSalt = PasswordUtil.hashPassword(password + salt);

        //This method updates the password with a matching Associate ID.
        //It returns a value of false if the Associate can't be found.
        String query = "UPDATE associate_pass SET "
                + "passwordHashAndSalted = ?, "
                + "salt = ? "
                + "WHERE iduser = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, pwdHashAndSalt);
            ps.setString(2, salt);
            ps.setInt(3, associateId);

            ps.executeUpdate();
            return false;
        }
        catch (SQLException e)
        {
            return true;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean updateAssociateDefaultView(Associate2 associate)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the password with a matching Associate ID.
        //It returns a value of false if the Associate can't be found.
        String query = "UPDATE associate_calendar_prefs SET "
                + "default_view = ? "
                + "WHERE associate_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, associate.getDefaultCalendarView());
            ps.setInt(2, associate.getId());

            ps.executeUpdate();
            return false;
        }
        catch (SQLException e)
        {
            return true;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean updateAssociateDefaultTimes(Associate2 associate)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the password with a matching Associate ID.
        //It returns a value of false if the Associate can't be found.
        String query = "UPDATE associate_calendar_prefs SET "
                + "mor_time_in = ?, "
                + "mor_time_out = ?, "
                + "aft_time_in = ?, "
                + "aft_time_out = ? "
                + "WHERE associate_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setTimestamp(1, associate.getMorningTimeInSql());
            ps.setTimestamp(2, associate.getMorningTimeOutSql());
            ps.setTimestamp(3, associate.getAfternoonTimeInSql());
            ps.setTimestamp(4, associate.getAfternoonTimeOutSql());
            ps.setInt(5, associate.getId());

            ps.executeUpdate();
            return false;
        }
        catch (SQLException e)
        {
            return true;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean updateMemberSettings(Associate2 associate)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the password with a matching Associate ID.
        //It returns a value of false if the Associate can't be found.
        String query = "UPDATE associate_member_settings SET "
                + "platinum = ?, "
                + "gold = ?, "
                + "silver = ?, "
                + "bronze = ? "
                + "WHERE associate_id = ?";

        HashMap<String, Integer> ms = associate.getMemberSettings();
        int p, g, s, b;

        p = ms.get("Platinum");
        g = ms.get("Gold");
        s = ms.get("Silver");
        b = ms.get("Bronze");

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, p);
            ps.setInt(2, g);
            ps.setInt(3, s);
            ps.setInt(4, b);
            ps.setInt(5, associate.getId());

            ps.executeUpdate();
            return false;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("AssociateDB updateMemberSettings error ", e.getMessage(), e.toString());
            return true;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateAssociate(String firstName, String lastName,
            String email, int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate can't be found.
        String query = "UPDATE associate SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ? "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setInt(4, associateId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateAssociateInfo(String homePhone, String workPhone,
            String mobilePhone, String otherPhone, String address, String city,
            String state, String zip, int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate Info can't be found.
        String query = "UPDATE associate_info SET "
                + "home_phone = ?, "
                + "work_phone = ?, "
                + "mobile_phone = ?, "
                + "other_phone = ?, "
                + "address = ?, "
                + "city = ?, "
                + "state = ?, "
                + "zip = ? "
                + "WHERE associate_info_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, homePhone);
            ps.setString(2, workPhone);
            ps.setString(3, mobilePhone);
            ps.setString(4, otherPhone);
            ps.setString(5, address);
            ps.setString(6, city);
            ps.setString(7, state);
            ps.setString(8, zip);
            ps.setInt(9, associateId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean updateAssociateInfo(Associate2 a)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate Info can't be found.
        String query = "UPDATE associate_info SET "
                + "home_phone = ?, "
                + "work_phone = ?, "
                + "mobile_phone = ?, "
                + "other_phone = ?, "
                + "address = ?, "
                + "city = ?, "
                + "state = ?, "
                + "zip = ? "
                + "WHERE associate_info_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, a.getHomePhone());
            ps.setString(2, a.getWorkPhone());
            ps.setString(3, a.getMobilePhone());
            ps.setString(4, a.getOtherPhone());
            ps.setString(5, a.getAddress());
            ps.setString(6, a.getCity());
            ps.setString(7, a.getState());
            ps.setString(8, a.getZip());
            ps.setInt(9, a.getId());

            ps.executeUpdate();
            return false;
        }
        catch (SQLException e)
        {
            return true;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateAssociateInfo(String securityQuestion, String securityAnswer, int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate Info can't be found.
        String query = "UPDATE associate_info SET "
                + "security_question = ?, "
                + "security_answer = ? "
                + "WHERE associate_info_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, securityQuestion);
            ps.setString(2, securityAnswer);
            ps.setInt(3, associateId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean updateAssociateAlerts(Associate2 a)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate Info can't be found.
        String query = "UPDATE associate_alert_prefs SET "
                + "assoc_email_adAlerts = ?, "
                + "assoc_email_apptAlerts = ?, "
                + "assoc_sms_adAlerts = ?, "
                + "assoc_sms_apptAlerts = ? "
                + "WHERE assoc_id_alert = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, a.isEmailAdAlerts());
            ps.setBoolean(2, a.isEmailApptAlerts());
            ps.setBoolean(3, a.isSmsAdAlerts());
            ps.setBoolean(4, a.isSmsApptAlerts());
            ps.setInt(5, a.getId());

            ps.executeUpdate();
            return false;
        }
        catch (SQLException e)
        {
            return true;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void updateAssociatePhoto(Associate2 a)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate Info can't be found.
        String query = "UPDATE associate SET "
                + "associate_imgUpload = ? "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, a.isImgUpl());
            ps.setInt(2, a.getId());

            ps.executeUpdate();
            LogFile.generalLog("updateAssociatePhoto success ", "associate " + a.getEmail() + " ID " + a.getId());
        }
        catch (SQLException e)
        {
            LogFile.databaseError("updateAssociatePhoto error " + a.getEmail()
                    + " ID " + a.getId(), e.getMessage(), e.toString());
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int insertAssociateSchedule(Date date,
            Time time, int associateId, Timestamp sTimestamp, Timestamp eTimeStamp)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the associate availabilty table in the database
        //returns 0 if sql exception occurs adding record to database
        String query
                = "INSERT INTO associate_availability (associate_avail_id, associate_avail_datestamp, "
                + "associate_avail_time, associate_avail_timestamp, associate_EndTimestamp) "
                + "VALUES (?, ?, ?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, date);
            ps.setTime(3, time);
            ps.setTimestamp(4, sTimestamp);
            ps.setTimestamp(5, eTimeStamp);

            return ps.executeUpdate();

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

    public static int insertAvailableDate(int associateId, Date date)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the associate availabilty table in the database
        //returns 0 if sql exception occurs adding record to database
        String query
                = "INSERT INTO appointment.associate_date (associate_date_id, associate_date, associate_time) "
                + "VALUES (?, ?, '00:00:00')";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setDate(2, date);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("insertAvailableDate error ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int insertAssociateSrv(int associateId, int serviceId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the associate availabilty table in the database
        //returns 0 if sql exception occurs adding record to database
        String query
                = "INSERT INTO associate_services (associate_id, service_id_asso) "
                + "VALUES (?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            ps.setInt(2, serviceId);

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("insertAssociateSrv error ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
