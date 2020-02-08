package store.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import messages.LogFile;
import store.business.CalendarCustomer;
import store.business.FullCalendar2;
import store.business.ServiceStatus;

/**
 *
 * @author williamdobbs
 */
public class CalendarDB
{

    //This method returns null if a calendar isn't found.
    public static ArrayList<CalendarCustomer> selectCalendar(int customerId, int month, int year)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM calendar c "
                + "JOIN services s "
                + "ON c.service_id = s.service_product_id "
                + "JOIN associate a "
                + "ON c.associate_id = a.id "
                + "WHERE customer_id = ? AND month = ? AND year = ? "
                + "ORDER BY calendar_time ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            rs = ps.executeQuery();
            ArrayList<CalendarCustomer> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                CalendarCustomer c = new CalendarCustomer();
                c.setStartTimeHour(rs.getInt("start_time_hour"));
                c.setEndTimeHour(rs.getInt("end_time_hour"));
                c.setMonth(rs.getInt("month"));
                c.setDay(rs.getInt("day"));
                c.setDate(rs.getInt("date"));
                c.setYear(rs.getInt("year"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setServiceDescription(rs.getString("description"));
                c.setAssociateName(rs.getString("first_name"));
                c.setAssociateLastName(rs.getString("last_name"));
                c.setAssociateTime(rs.getTime("calendar_time"));
                c.setAssociateId(rs.getInt("associate_id"));

                calendarCustomer.add(c);
            }
            return calendarCustomer;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectCalendar(3 args) ", e.toString(), " clientID "
                    + customerId);
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
    public static ArrayList<CalendarCustomer> selectCalendar(int customerId, int month, int year, int day)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM calendar c "
                + "INNER JOIN services s "
                + "ON c.service_id = s.service_product_id "
                + "INNER JOIN associate a "
                + "ON c.associate_id = a.id "
                + "WHERE customer_id = ? AND month = ? AND year = ? AND date = ? "
                + "ORDER BY calendar_time ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ps.setInt(4, day);
            rs = ps.executeQuery();
            ArrayList<CalendarCustomer> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                CalendarCustomer c = new CalendarCustomer();
                c.setStartTimeHour(rs.getInt("start_time_hour"));
                c.setStartTimeMin(rs.getInt("start_time_min"));
                c.setEndTimeHour(rs.getInt("end_time_hour"));
                c.setMonth(rs.getInt("month"));
                c.setDay(rs.getInt("day"));
                c.setDate(rs.getInt("date"));
                c.setYear(rs.getInt("year"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setServiceDescription(rs.getString("description"));
                c.setAssociateName(rs.getString("first_name"));
                c.setAssociateLastName(rs.getString("last_name"));
                c.setAssociateTime(rs.getTime("calendar_time"));
                c.setAssociateId(rs.getInt("associate_id"));
                c.setDate(rs.getDate("calendar_date"));
                c.setServiceId(rs.getInt("service_id"));

                calendarCustomer.add(c);
            }
            return calendarCustomer;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectCalendar(4 args) ", e.toString(), " clientID "
                    + customerId);
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean eventExists(Timestamp sqlStart, int eventId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT event_id FROM calendar "
                + "WHERE start_timestamp = ? OR event_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setTimestamp(1, sqlStart);
            ps.setInt(2, eventId);
            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean eventExists(int eventId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT event_id FROM calendar "
                + "WHERE event_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, eventId);
            rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ServiceStatus serviceStatus(int statusId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM service_status "
                + "WHERE idservice_status = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, statusId);
            rs = ps.executeQuery();
            if (rs.next())
            {
                ServiceStatus s = new ServiceStatus();
                s.setStatusName(rs.getString("status_name"));
                s.setStatusColor(rs.getString("status_color"));
                s.setStatusId(rs.getInt("idservice_status"));

                return s;
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

    public static ArrayList<ServiceStatus> serviceStatusAll()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM service_status "
                + "ORDER BY idservice_status ASC";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<ServiceStatus> st = new ArrayList<>();
            while (rs.next())
            {
                ServiceStatus s = new ServiceStatus();
                s.setStatusId(rs.getInt("idservice_status"));
                s.setStatusName(rs.getString("status_name"));
                s.setStatusColor(rs.getString("status_color"));
                st.add(s);
            }
            return st;
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

    public static int serviceStatus(String statusName)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM service_status "
                + "WHERE status_name = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, statusName);
            rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getInt("idservice_status");
            }
            else
            {
                return 0;
            }
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

    public static int updateCalendar(FullCalendar2 fc, Time startTime, Date sqlStartDate, Date sqlEndDate, Time endTime)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate can't be found.
        String query = "UPDATE calendar SET "
                + "customer_firstName = ?, "
                + "customer_lastName = ?, "
                + "customer_emailAddress = ?, "
                + "service_description = ?, "
                + "backgroundColor = ?, "
                + "textColor = ?, "
                + "color = ?, "
                + "associate_name = ?, "
                + "associate_id = ?, "
                + "durationEditable = ?, "
                + "start_timestamp = ?, "
                + "end_timestamp = ?, "
                + "editable = ?, "
                + "calendar_time = ?, "
                + "endTime = ?, "
                + "calendar_date = ?, "
                + "calendar_endDate = ?, "
                + "allDayEvent = ?, "
                + "service_time = ?, "
                + "notes = ? "
                + "WHERE event_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, fc.getClient().getFirstName());
            ps.setString(2, fc.getClient().getLastName());
            ps.setString(3, fc.getClient().getEmail());
            ps.setString(4, fc.getTitle());
            ps.setString(5, fc.getBackgroundColor());
            ps.setString(6, fc.getTextColor());
            ps.setString(7, fc.getColor());
            ps.setString(8, fc.getAssociateName());
            ps.setInt(9, fc.getAssociateId());
            ps.setBoolean(10, fc.isDurationEditable());
            ps.setTimestamp(11, fc.getStartSql());
            ps.setTimestamp(12, fc.getEndSql());
            ps.setBoolean(13, fc.isEditable());
            ps.setTime(14, startTime);
            ps.setTime(15, endTime);
            ps.setDate(16, sqlStartDate);
            ps.setDate(17, sqlEndDate);
            ps.setBoolean(18, fc.isAllDay());
            ps.setInt(19, fc.getServiceTime());
            ps.setString(20, fc.getNotes());
            ps.setInt(21, fc.getEventId());

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CalendarDB - updateCalendar ", e.toString(), e.getMessage());
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a calendar isn't found.
    public static ArrayList<FullCalendar2> selectCalendarAll()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM calendar c "
                + "INNER JOIN services s "
                + "ON c.service_id = s.service_product_id "
                + "INNER JOIN customer cust "
                + "ON cust.id = c.customer_id "
                + "ORDER BY calendar_time ASC";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<FullCalendar2> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 cc = new FullCalendar2();
                cc.setClient(CustomerDB.selectClient(rs.getInt("customer_id")));
                cc.setAssociate2(AssociateDB.selectAssociateInfo(rs.getInt("associate_id")));
                cc.setStartTimeHour(rs.getInt("start_time_hour"));
                cc.setStartTimeMin(rs.getInt("start_time_min"));
                cc.setEndTimeHour(rs.getInt("end_time_hour"));
                cc.setEndTimeMin(rs.getInt("end_time_min"));
                cc.setMonth(rs.getInt("month"));
                cc.setDay(rs.getInt("day"));
                cc.setDate(rs.getInt("date"));
                cc.setYear(rs.getInt("year"));
                cc.setCustomerId(rs.getInt("customer_id"));
                cc.setServiceDescription(rs.getString("service_description"));
                cc.setNotes(rs.getString("notes"));
                cc.setServiceTime(rs.getInt("service_time"));
                cc.setAssociateTime(rs.getTime("calendar_time"));
                cc.setDate(rs.getDate("calendar_date"));
                cc.setServiceId(rs.getInt("service_id"));
                cc.getClient().setFirstName(rs.getString("customer_firstName"));
                cc.getClient().setLastName(rs.getString("customer_lastName"));
                cc.getClient().setEmail(rs.getString("customer_emailAddress"));
                cc.setAllDay(rs.getBoolean("allDayEvent"));
                cc.setEventId(rs.getInt("event_id"));
                cc.getAssociate2().setId(rs.getInt("associate_id"));
                cc.setBackgroundColor(rs.getString("backgroundColor"));
                cc.setTextColor(rs.getString("textColor"));
                cc.setDurationEditable(rs.getBoolean("durationEditable"));
                cc.setEditable(rs.getBoolean("editable"));
                cc.setColor(rs.getString("color"));
                cc.setStartSql(rs.getTimestamp("start_timestamp"));
                cc.setEndSql(rs.getTimestamp("end_timestamp"));
//                cc.setServiceStatus(serviceStatus(rs.getInt("service_status")));

                calendarCustomer.add(cc);
            }
            return calendarCustomer;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectCalendar() ", e.toString(), " AllClients ");
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
    public static ArrayList<FullCalendar2> selectCalendar(int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM calendar c "
                + "JOIN services s "
                + "ON c.service_id = s.service_product_id "
                + "JOIN customer cust "
                + "ON cust.id = c.customer_id "
                + "WHERE c.associate_id = ? "
                + "ORDER BY calendar_time ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, associateId);
            rs = ps.executeQuery();
            ArrayList<FullCalendar2> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 cc = new FullCalendar2();
                cc.setStartTimeHour(rs.getInt("start_time_hour"));
                cc.setStartTimeMin(rs.getInt("start_time_min"));
                cc.setEndTimeHour(rs.getInt("end_time_hour"));
                cc.setEndTimeMin(rs.getInt("end_time_min"));
                cc.setMonth(rs.getInt("month"));
                cc.setDay(rs.getInt("day"));
                cc.setDate(rs.getInt("date"));
                cc.setYear(rs.getInt("year"));
                cc.setCustomerId(rs.getInt("customer_id"));
                cc.setServiceDescription(rs.getString("service_description"));
                cc.setNotes(rs.getString("notes"));
                cc.setServiceTime(rs.getInt("service_time"));
                cc.setAssociateTime(rs.getTime("calendar_time"));
                cc.setDate(rs.getDate("calendar_date"));
                cc.setServiceId(rs.getInt("service_id"));
                cc.getClient().setFirstName(rs.getString("customer_firstName"));
                cc.getClient().setLastName(rs.getString("customer_lastName"));
                cc.getClient().setEmail(rs.getString("customer_emailAddress"));
                cc.setAllDay(rs.getBoolean("allDayEvent"));
                cc.setEventId(rs.getInt("event_id"));
                cc.getAssociate2().setId(rs.getInt("associate_id"));
                cc.setBackgroundColor(rs.getString("backgroundColor"));
                cc.setTextColor(rs.getString("textColor"));
                cc.setDurationEditable(rs.getBoolean("durationEditable"));
                cc.setEditable(rs.getBoolean("editable"));
                cc.setColor(rs.getString("color"));
                cc.setStartSql(rs.getTimestamp("start_timestamp"));
                cc.setEndSql(rs.getTimestamp("end_timestamp"));
                cc.setServiceStatus(serviceStatus(rs.getInt("service_status")));
                cc.setAssociate2(AssociateDB.selectAssociateInfo(rs.getInt("associate_id")));
                cc.setClient(CustomerDB.selectClient(rs.getInt("customer_id")));

                calendarCustomer.add(cc);
            }
            return calendarCustomer;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectCalendar() ", e.toString(), " AllClients ");
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
    public static ArrayList<FullCalendar2> selectClientCalendar(int clientId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM calendar c "
                + "INNER JOIN services s "
                + "ON c.service_id = s.service_product_id "
                + "INNER JOIN customer cust "
                + "ON cust.id = c.customer_id "
                + "WHERE c.customer_id = ? "
                + "ORDER BY calendar_time ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            rs = ps.executeQuery();
            ArrayList<FullCalendar2> fc = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 cc = new FullCalendar2();

                cc.setStartTimeHour(rs.getInt("start_time_hour"));
                cc.setStartTimeMin(rs.getInt("start_time_min"));
                cc.setEndTimeHour(rs.getInt("end_time_hour"));
                cc.setEndTimeMin(rs.getInt("end_time_min"));
                cc.setMonth(rs.getInt("month"));
                cc.setDay(rs.getInt("day"));
                cc.setDate(rs.getInt("date"));
                cc.setYear(rs.getInt("year"));
                cc.setCustomerId(rs.getInt("customer_id"));
                cc.setServiceDescription(rs.getString("service_description"));
                cc.setNotes(rs.getString("notes"));
                cc.setServiceTime(rs.getInt("service_time"));
                cc.setAssociateTime(rs.getTime("calendar_time"));
                cc.setDate(rs.getDate("calendar_date"));
                cc.setServiceId(rs.getInt("service_id"));
                cc.getClient().setFirstName(rs.getString("customer_firstName"));
                cc.getClient().setLastName(rs.getString("customer_lastName"));
                cc.getClient().setEmail(rs.getString("customer_emailAddress"));
                cc.setAllDay(rs.getBoolean("allDayEvent"));
                cc.setEventId(rs.getInt("event_id"));
                cc.getAssociate2().setId(rs.getInt("associate_id"));
                cc.setBackgroundColor(rs.getString("backgroundColor"));
                cc.setTextColor(rs.getString("textColor"));
                cc.setDurationEditable(rs.getBoolean("durationEditable"));
                cc.setEditable(rs.getBoolean("editable"));
                cc.setColor(rs.getString("color"));
                cc.setStartSql(rs.getTimestamp("start_timestamp"));
                cc.setEndSql(rs.getTimestamp("end_timestamp"));
                cc.getServices().setServiceStatus(rs.getInt("service_status"));
                cc.setAssociate2(AssociateDB.selectAssociateInfo(rs.getInt("associate_id")));
                cc.setClient(CustomerDB.selectClient(rs.getInt("customer_id")));

                fc.add(cc);
            }
            return fc;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectCalendar() ", e.toString(), " AllClients ");
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
    public static ArrayList<CalendarCustomer> selectCalendar(int customerId, Date startDay, Date endDay)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM calendar c "
                + "JOIN services s "
                + "ON c.service_id = s.service_product_id "
                + "JOIN associate a "
                + "ON c.associate_id = a.id "
                + "WHERE customer_id = ? AND calendar_date >= ? AND calendar_date <= ? "
                + "ORDER BY calendar_date ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setDate(2, startDay);
            ps.setDate(3, endDay);
            rs = ps.executeQuery();
            ArrayList<CalendarCustomer> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                CalendarCustomer c = new CalendarCustomer();
                c.setStartTimeHour(rs.getInt("start_time_hour"));
                c.setStartTimeMin(rs.getInt("start_time_min"));
                c.setEndTimeHour(rs.getInt("end_time_hour"));
                c.setMonth(rs.getInt("month"));
                c.setDay(rs.getInt("day"));
                c.setDate(rs.getInt("date"));
                c.setYear(rs.getInt("year"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setServiceDescription(rs.getString("description"));
                c.setAssociateName(rs.getString("first_name"));
                c.setAssociateLastName(rs.getString("last_name"));
                c.setAssociateTime(rs.getTime("calendar_time"));
                c.setAssociateId(rs.getInt("associate_id"));
                c.setDate(rs.getDate("calendar_date"));

                calendarCustomer.add(c);
            }
            return calendarCustomer;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectCalendar(3 args) ", e.toString(), " clientID "
                    + customerId);
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
    public static ArrayList<CalendarCustomer> selectCalendar(int customerId, Date month_1)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM calendar c "
                + "INNER JOIN product p "
                + "ON c.service_id = p.id "
                + "INNER JOIN appointment.associate a "
                + "ON c.associate_id = a.id "
                + "WHERE customer_id = ? AND calendar_date >= ? "
                + "ORDER BY calendar_time ASC";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setDate(2, month_1);
            rs = ps.executeQuery();
            ArrayList<CalendarCustomer> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                CalendarCustomer c = new CalendarCustomer();
                c.setStartTimeHour(rs.getInt("start_time_hour"));
                c.setEndTimeHour(rs.getInt("end_time_hour"));
                c.setMonth(rs.getInt("month"));
                c.setDay(rs.getInt("day"));
                c.setDate(rs.getInt("date"));
                c.setYear(rs.getInt("year"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setServiceDescription(rs.getString("description"));
                c.setAssociateName(rs.getString("first_name"));
                c.setAssociateLastName(rs.getString("last_name"));
                c.setAssociateDate(rs.getDate("calendar_date"));
                c.setAssociateTime(rs.getTime("calendar_time"));

                calendarCustomer.add(c);
            }
            return calendarCustomer;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectCalendar(2 args) ", e.toString(), " clientID "
                    + customerId);
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a calendar event isn't found.
    public static ArrayList<FullCalendar2> selectRecentVisits(int customerId, int num)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT event_id, start_timestamp, end_timestamp, associate_id, associate_name, customer_id, "
                + "service_status, service_description, calendarComments, service_id "
                + "FROM appointment.calendar c "
                + "WHERE c.customer_id = ? and c.start_timestamp <= now() "
                + "ORDER BY c.start_timestamp DESC "
                + "LIMIT ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setInt(2, num);
            rs = ps.executeQuery();
            ArrayList<FullCalendar2> fcArr = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 fc = new FullCalendar2();
                fc.setEventId(rs.getInt("event_id"));
                fc.setCustomerId(rs.getInt("customer_id"));
                fc.setServiceDescription(rs.getString("service_description"));
                fc.setAssociateName(rs.getString("associate_name"));
                fc.setAssociateId(rs.getInt("associate_id"));
                fc.setStartSql(rs.getTimestamp("start_timestamp"));
                fc.setStart(fc.convertStartTimestamp());

                fcArr.add(fc);
            }
            return fcArr;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectRecentVisits ", e.toString(), " clientID "
                    + customerId + " number of visits " + num);
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a calendar event isn't found.
    public static ArrayList<FullCalendar2> selectFutureVisits(int customerId, int num)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT event_id, start_timestamp, end_timestamp, associate_id, associate_name, customer_id, "
                + "service_status, service_description, calendarComments, service_id "
                + "FROM appointment.calendar c "
                + "WHERE c.customer_id = ? and c.start_timestamp >= now() "
                + "ORDER BY c.start_timestamp ASC "
                + "LIMIT ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setInt(2, num);
            rs = ps.executeQuery();
            ArrayList<FullCalendar2> fcArr = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 fc = new FullCalendar2();
                fc.setEventId(rs.getInt("event_id"));
                fc.setCustomerId(rs.getInt("customer_id"));
                fc.setServiceDescription(rs.getString("service_description"));
                fc.setAssociateName(rs.getString("associate_name"));
                fc.setAssociateId(rs.getInt("associate_id"));
                fc.setStartSql(rs.getTimestamp("start_timestamp"));
                fc.setStart(fc.convertStartTimestamp());
                fc.setEndSql(rs.getTimestamp("end_timestamp"));
                fc.setEnd(fc.convertEndTimestamp());
                fc.setStatusId(rs.getInt("service_status"));

                fcArr.add(fc);
            }
            return fcArr;
        }
        catch (SQLException | NullPointerException e)
        {
            LogFile.databaseError("CalendarDB - selectRecentVisits ", e.toString(), " clientID "
                    + customerId + " number of visits " + num);
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int insertAppointment(Date appointDate, int serviceId, int month, int day, int date, int year,
            Time startTime, Time endTime, Timestamp startTimestamp, Timestamp endTimestamp, int customerId, String customer_firstName, String customer_lastName,
            String customer_emailAddress, int startHour, int startMinute, int endHour, int endMinute, int confirmationNumber,
            String description, int serviceTime, int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the calendar table in the database
        //returns 0 if sql exception occurs adding record to database
        String query
                = "INSERT INTO calendar (calendar_date, service_id, "
                + "month, day, date,"
                + " year, calendar_time, endTime, "
                + "start_timestamp, end_timestamp, "
                + "customer_id, customer_firstName, "
                + "customer_lastName, customer_emailAddress, "
                + "start_time_hour, "
                + "start_time_min, end_time_hour, end_time_min, "
                + "confirmation_number, service_description, "
                + "service_time, associate_id) "
                + "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setDate(1, appointDate);
            ps.setInt(2, serviceId);
            ps.setInt(3, month);
            ps.setInt(4, day);
            ps.setInt(5, date);
            ps.setInt(6, year);
            ps.setTime(7, startTime);
            ps.setTime(8, endTime);
            ps.setTimestamp(9, startTimestamp);
            ps.setTimestamp(10, endTimestamp);
            ps.setInt(11, customerId);
            ps.setString(12, customer_firstName);
            ps.setString(13, customer_lastName);
            ps.setString(14, customer_emailAddress);
            ps.setInt(15, startHour);
            ps.setInt(16, startMinute);
            ps.setInt(17, endHour);
            ps.setInt(18, endMinute);
            ps.setInt(19, confirmationNumber);
            ps.setString(20, description);
            ps.setInt(21, serviceTime);
            ps.setInt(22, associateId);

            return ps.executeUpdate(); //appointmentID;
        }
        catch (SQLException sql)
        {
            LogFile.databaseError("CalendarDB - insertAppointment ", sql.toString(), " clientID "
                    + associateId);
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int insertAppointment(FullCalendar2 fc, Date sqlStartDate, Date sqlEndDate, int startMonth, int startDay, int startDate, int startYear,
            int endMonth, int endDay, int endDate, int endYear, Time startTime, Time endTime, int startHour, int startMinute, int endHour, int endMinute)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the calendar table in the database
        //returns 0 if sql exception occurs adding record to database 
        String query
                = "INSERT INTO calendar (allDayEvent, "
                + "service_description, "
                + "service_id, "
                + "service_time, "
                + "start_timestamp, "
                + "end_timestamp, "
                + "backgroundColor,"
                + "associate_name, "
                + "customer_firstName, "
                + "customer_lastName, "
                + "customer_emailAddress, "
                + "color, "
                + "textColor, "
                + "durationEditable, "
                + "editable, "
                + "calendar_time, "
                + "endTime, "
                + "associate_id, "
                + "notes, "
                + "customer_id, month, day, date, year, monthEnd, dayEnd, dateEnd, yearEnd,"
                + "calendar_date, calendar_endDate, start_time_hour, start_time_min, end_time_hour, end_time_min, service_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, fc.isAllDay());
            ps.setString(2, fc.getTitle());
            ps.setInt(3, fc.getServiceId());
            ps.setInt(4, fc.getServiceTime());
            ps.setTimestamp(5, fc.getStartSql());
            ps.setTimestamp(6, fc.getEndSql());
            ps.setString(7, fc.getBackgroundColor());
            ps.setString(8, fc.getAssociateName());
            ps.setString(9, fc.getFirstName());
            ps.setString(10, fc.getLastName());
            ps.setString(11, fc.getEmailAddress());
            ps.setString(12, fc.getColor());
            ps.setString(13, fc.getTextColor());
            ps.setBoolean(14, fc.isDurationEditable());
            ps.setBoolean(15, fc.isEditable());
            ps.setTime(16, startTime);
            ps.setTime(17, endTime);
            ps.setInt(18, fc.getAssociateId());
            ps.setString(19, fc.getNotes());
            ps.setInt(20, fc.getCustomerId());
            ps.setInt(21, startMonth);
            ps.setInt(22, startDay);
            ps.setInt(23, startDate);
            ps.setInt(24, startYear);
            ps.setInt(25, endMonth);
            ps.setInt(26, endDay);
            ps.setInt(27, endDate);
            ps.setInt(28, endYear);
            ps.setDate(29, sqlStartDate);
            ps.setDate(30, sqlEndDate);
            ps.setInt(31, startHour);
            ps.setInt(32, startMinute);
            ps.setInt(33, endHour);
            ps.setInt(34, endMinute);
            ps.setInt(35, fc.getStatusId());

            ps.executeUpdate();
            //Get the event ID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet;
            identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            int evtId = identityResultSet.getInt("IDENTITY");

            return evtId; // return event id number
        }
        catch (SQLException sql)
        {
            LogFile.databaseError("CalendarDB - Full Calendar insertAppointment ", sql.toString(), " Associate "
                    + fc.getAssociateName());
            return 0;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static boolean deleteAppointment(int eventId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM calendar "
                + "WHERE event_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, eventId);
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CalendarDB - deleteAppointment ", e.toString(), " eventID "
                    + eventId);
            return false;
        }
        finally
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateStatus(FullCalendar2 fc)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate can't be found.
        String query = "UPDATE calendar SET "
                + "service_status = ? "
                + "WHERE event_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, fc.getStatusId());
            ps.setInt(2, fc.getEventId());

            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CalendarDB - updateStatus ", e.toString(), e.getMessage());
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void updateStatus(int eventId, int svcStatusId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Event ID.
        //It returns a value of 0 if the Event can't be found.
        String query = "UPDATE calendar SET "
                + "service_status = ? "
                + "WHERE event_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, svcStatusId);
            ps.setInt(2, eventId);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("CalendarDB - updateStatus - event id ", e.toString(), e.getMessage());
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
