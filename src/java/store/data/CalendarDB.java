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
import store.business.FullCalendar2;
import store.business.ServiceStatus;

/**
 *
 * @author williamdobbs
 */
public class CalendarDB
{

    //This method returns null if a calendar isn't found.
    public static ArrayList<FullCalendar2> selectCalendar(int customerId, int month, int year)
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
            ArrayList<FullCalendar2> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 c = new FullCalendar2();
                c.setStartTimeHour(rs.getInt("start_time_hour"));
                c.setEndTimeHour(rs.getInt("end_time_hour"));
                c.setStartMonth(rs.getInt("month"));
                c.setStartDay(rs.getInt("day"));
                c.setStartDay(rs.getInt("date"));
                c.setStartYear(rs.getInt("year"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setServiceDescription(rs.getString("description"));
                c.getAssociate2().setFirstName(rs.getString("first_name"));
                c.getAssociate2().setLastName(rs.getString("last_name"));
                c.getAssociate2().setAssociateTime(rs.getTime("calendar_time"));
                c.getAssociate2().setId(rs.getInt("associate_id"));

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
    public static ArrayList<FullCalendar2> selectCalendar(int customerId, int month, int year, int day)
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
            ArrayList<FullCalendar2> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 c = new FullCalendar2();
                c.setStartTimeHour(rs.getInt("start_time_hour"));
                c.setStartTimeMin(rs.getInt("start_time_min"));
                c.setEndTimeHour(rs.getInt("end_time_hour"));
                c.setStartMonth(rs.getInt("month"));
                c.setStartDay(rs.getInt("day"));
                c.setDate(rs.getInt("date"));
                c.setStartYear(rs.getInt("year"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setServiceDescription(rs.getString("description"));
                c.getAssociate2().setFirstName(rs.getString("first_name"));
                c.getAssociate2().setLastName(rs.getString("last_name"));
                c.getAssociate2().setAssociateTime(rs.getTime("calendar_time"));
                c.getAssociate2().setId(rs.getInt("associate_id"));
                c.setCalendarDate(rs.getDate("calendar_date"));
                c.getServices().setServiceId(rs.getInt("service_id"));

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
            LogFile.databaseError("CalendarDB eventExists timestamp ", e.toString(), " event ID "
                    + eventId);
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
            LogFile.databaseError("CalendarDB eventExists event ID arg only ", e.toString(), " event ID "
                    + eventId);
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
            LogFile.databaseError("CalendarDB serviceStatus status ID arg only ", e.toString(), " status ID "
                    + statusId);
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
            LogFile.databaseError("CalendarDB serviceStatusAll ", e.toString(), " serviceStatusAll");
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
            LogFile.databaseError("CalendarDB serviceStatus ", e.toString(), " statusName" + statusName
                    + " " + e.getMessage());
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateCalendar(FullCalendar2 fc)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Associate ID.
        //It returns a value of 0 if the Associate can't be found.
        String query = "UPDATE calendar SET "
                + "allDayEvent = ?, "
                + "service_description = ?, "
                + "service_id = ?, "
                + "service_time = ?, "
                + "start_timestamp = ?, "
                + "end_timestamp = ?, "
                + "backgroundColor = ?,"
                + "associate_name = ?, "
                + "customer_firstName = ?, "
                + "customer_lastName = ?, "
                + "customer_emailAddress = ?, "
                + "color = ?, "
                + "textColor = ?, "
                + "durationEditable = ?, "
                + "editable = ?, "
                + "calendar_time = ?, "
                + "endTime = ?, "
                + "associate_id = ?, "
                + "notes = ?, "
                + "customer_id = ?, "
                + "month = ?, "
                + "day = ?, "
                + "date = ?, "
                + "year = ?, "
                + "monthEnd = ?, "
                + "dayEnd = ?, "
                + "dateEnd = ?, "
                + "yearEnd = ?, "
                + "calendar_date = ?, "
                + "calendar_endDate = ?, "
                + "start_time_hour = ?, "
                + "start_time_min = ?, "
                + "end_time_hour = ?, "
                + "end_time_min = ?, "
                + "service_status = ?, "
                + "time_zone = ?, "
                + "start_time_utc = ?, "
                + "end_time_utc = ?, "
                + "start_time_offset = ?, "
                + "end_time_offset = ? "
                + "WHERE event_id = ?";
        try
        {
            ps = connection.prepareStatement(query);

            ps.setBoolean(1, fc.isAllDay());
            ps.setString(2, fc.getTitle());
            ps.setInt(3, fc.getServices().getServiceId());
            ps.setInt(4, fc.getServiceTime());
            ps.setTimestamp(5, fc.getStartSql());
            ps.setTimestamp(6, fc.getEndSql());
            ps.setString(7, fc.getBackgroundColor());
            ps.setString(8, fc.getAssociate2().getFirstName());
            ps.setString(9, fc.getClient().getFirstName());
            ps.setString(10, fc.getClient().getLastName());
            ps.setString(11, fc.getClient().getEmail());
            ps.setString(12, fc.getColor());
            ps.setString(13, fc.getTextColor());
            ps.setBoolean(14, fc.isDurationEditable());
            ps.setBoolean(15, fc.isEditable());
            ps.setTime(16, fc.sqlStartTime());
            ps.setTime(17, fc.sqlEndTime());
            ps.setInt(18, fc.getAssociate2().getId());
            ps.setString(19, fc.getNotes());
            ps.setInt(20, fc.getClient().getId());
            ps.setInt(21, fc.startMonth());
            ps.setInt(22, fc.startDayOfWeek());
            ps.setInt(23, fc.startDayOfMonth());
            ps.setInt(24, fc.startYear());
            ps.setInt(25, fc.endMonth());
            ps.setInt(26, fc.endDayOfWeek());
            ps.setInt(27, fc.endDayOfMonth());
            ps.setInt(28, fc.endYear());
            ps.setDate(29, fc.sqlStartDate());
            ps.setDate(30, fc.sqlEndDate());
            ps.setInt(31, fc.startTimeHour());
            ps.setInt(32, fc.startTimeMin());
            ps.setInt(33, fc.endTimeHour());
            ps.setInt(34, fc.endTimeMin());
            ps.setInt(35, fc.getServices().getServiceStatus().getStatusId());
            ps.setString(36, fc.getTimeZone());
            ps.setString(37, fc.getStartTimeUtc());
            ps.setString(38, fc.getEndTimeUtc());
            ps.setString(39, fc.getStartOffset());
            ps.setString(40, fc.getEndOffset());
            ps.setInt(41, fc.getEventId());

            ps.executeUpdate();
            return 1;
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
                + "WHERE c.calendar_date >= DATE_SUB(CURDATE(),INTERVAL 1 YEAR) "
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

                cc.setStartTimeHour(rs.getInt("start_time_hour"));
                cc.setStartTimeMin(rs.getInt("start_time_min"));
                cc.setEndTimeHour(rs.getInt("end_time_hour"));
                cc.setEndTimeMin(rs.getInt("end_time_min"));
                cc.setStartMonth(rs.getInt("month"));
                cc.setStartDay(rs.getInt("day"));
                cc.setDate(rs.getInt("date"));
                cc.setStartYear(rs.getInt("year"));
                cc.setCustomerId(rs.getInt("customer_id"));
                cc.setServiceDescription(rs.getString("service_description"));
                cc.setNotes(rs.getString("notes"));
                cc.setServiceTime(rs.getInt("service_time"));
                cc.setCalendarDate(rs.getDate("calendar_date"));
                cc.getServices().setServiceId(rs.getInt("service_id"));
                cc.setAllDay(rs.getBoolean("allDayEvent"));
                cc.setEventId(rs.getInt("event_id"));
                cc.setTextColor(rs.getString("textColor"));
                cc.setDurationEditable(rs.getBoolean("durationEditable"));
                cc.setEditable(rs.getBoolean("editable"));
                cc.setColor(rs.getString("color"));
                cc.setStartSql(rs.getTimestamp("start_timestamp"));
                cc.setEndSql(rs.getTimestamp("end_timestamp"));
                cc.getServices().setServiceStatus(CalendarDB.serviceStatus(rs.getInt("service_status")));
                cc.getServiceStatus().setStatusId(rs.getInt("service_status"));
                cc.setAssociate2(AssociateDB.selectAssociateInfo(rs.getInt("associate_id")));

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
                cc.setStartMonth(rs.getInt("month"));
                cc.setStartDay(rs.getInt("day"));
                cc.setDate(rs.getInt("date"));
                cc.setStartYear(rs.getInt("year"));
                cc.setCustomerId(rs.getInt("customer_id"));
                cc.setServiceDescription(rs.getString("service_description"));
                cc.setNotes(rs.getString("notes"));
                cc.setServiceTime(rs.getInt("service_time"));
                cc.getAssociate2().setAssociateTime(rs.getTime("calendar_time"));
                cc.setCalendarDate(rs.getDate("calendar_date"));
                cc.getServices().setServiceId(rs.getInt("service_id"));
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
                cc.getServices().getServiceStatus().setStatusId(rs.getInt("service_status"));
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
                cc.setStartMonth(rs.getInt("month"));
                cc.setStartDay(rs.getInt("day"));
                cc.setDate(rs.getInt("date"));
                cc.setStartYear(rs.getInt("year"));
                cc.setCustomerId(rs.getInt("customer_id"));
                cc.setServiceDescription(rs.getString("service_description"));
                cc.setNotes(rs.getString("notes"));
                cc.setServiceTime(rs.getInt("service_time"));
                cc.getAssociate2().setAssociateTime(rs.getTime("calendar_time"));
                cc.setCalendarDate(rs.getDate("calendar_date"));
                cc.getServices().setServiceId(rs.getInt("service_id"));
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
                cc.getServices().setServiceStatus(CalendarDB.serviceStatus(rs.getInt("service_status")));
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
    public static ArrayList<FullCalendar2> selectCalendar(int customerId, Date startDay, Date endDay)
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
            ArrayList<FullCalendar2> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 c = new FullCalendar2();
                c.setStartTimeHour(rs.getInt("start_time_hour"));
                c.setStartTimeMin(rs.getInt("start_time_min"));
                c.setEndTimeHour(rs.getInt("end_time_hour"));
                c.setStartMonth(rs.getInt("month"));
                c.setStartDay(rs.getInt("day"));
                c.setDate(rs.getInt("date"));
                c.setStartYear(rs.getInt("year"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setServiceDescription(rs.getString("description"));
                c.getAssociate2().setFirstName(rs.getString("first_name"));
                c.getAssociate2().setLastName(rs.getString("last_name"));
                c.getAssociate2().setAssociateTime(rs.getTime("calendar_time"));
                c.getAssociate2().setId(rs.getInt("associate_id"));
                c.setCalendarDate(rs.getDate("calendar_date"));

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
    public static ArrayList<FullCalendar2> selectCalendar(int customerId, Date month_1)
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
            ArrayList<FullCalendar2> calendarCustomer = new ArrayList<>();
            while (rs.next())
            {
                FullCalendar2 c = new FullCalendar2();
                c.setStartTimeHour(rs.getInt("start_time_hour"));
                c.setEndTimeHour(rs.getInt("end_time_hour"));
                c.setStartMonth(rs.getInt("month"));
                c.setStartDay(rs.getInt("day"));
                c.setDate(rs.getInt("date"));
                c.setStartYear(rs.getInt("year"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setServiceDescription(rs.getString("description"));
                c.getAssociate2().setFirstName(rs.getString("first_name"));
                c.getAssociate2().setLastName(rs.getString("last_name"));
//                c.setAssociateDate(rs.getDate("calendar_date"));
                c.getAssociate2().setAssociateTime(rs.getTime("calendar_time"));

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
                fc.getAssociate2().setFirstName(rs.getString("associate_name"));
                fc.getAssociate2().setId(rs.getInt("associate_id"));
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
                fc.getAssociate2().setFirstName(rs.getString("associate_name"));
                fc.getAssociate2().setId(rs.getInt("associate_id"));
                fc.setStartSql(rs.getTimestamp("start_timestamp"));
                fc.setStart(fc.convertStartTimestamp());
                fc.setEndSql(rs.getTimestamp("end_timestamp"));
                fc.setEnd(fc.convertEndTimestamp());
                fc.getServices().getServiceStatus().setStatusId(rs.getInt("service_status"));

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

    public static int insertAppointment(FullCalendar2 fc)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        //This method adds a new record to the calendar table in the database
        //returns 0 if sql exception occurs adding record to database 
        String query
                = "INSERT INTO calendar "
                + "(allDayEvent, "
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
                + "customer_id, "
                + "month, "
                + "day, "
                + "date, "
                + "year, "
                + "monthEnd, "
                + "dayEnd, "
                + "dateEnd, "
                + "yearEnd, "
                + "calendar_date, "
                + "calendar_endDate, "
                + "start_time_hour, "
                + "start_time_min, "
                + "end_time_hour, "
                + "end_time_min, "
                + "service_status, "
                + "time_zone, "
                + "start_time_utc, "
                + "end_time_utc, "
                + "start_time_offset, "
                + "end_time_offset) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);

            ps.setBoolean(1, fc.isAllDay());
            ps.setString(2, fc.getTitle());
            ps.setInt(3, fc.getServices().getServiceId());
            ps.setInt(4, fc.getServiceTime());
            ps.setTimestamp(5, fc.getStartSql());
            ps.setTimestamp(6, fc.getEndSql());
            ps.setString(7, fc.getBackgroundColor());
            ps.setString(8, fc.getAssociate2().getFirstName());
            ps.setString(9, fc.getClient().getFirstName());
            ps.setString(10, fc.getClient().getLastName());
            ps.setString(11, fc.getClient().getEmail());
            ps.setString(12, fc.getColor());
            ps.setString(13, fc.getTextColor());
            ps.setBoolean(14, fc.isDurationEditable());
            ps.setBoolean(15, fc.isEditable());
            ps.setTime(16, fc.sqlStartTime());
            ps.setTime(17, fc.sqlEndTime());
            ps.setInt(18, fc.getAssociate2().getId());
            ps.setString(19, fc.getNotes());
            ps.setInt(20, fc.getClient().getId());
            ps.setInt(21, fc.startMonth());
            ps.setInt(22, fc.startDayOfWeek());
            ps.setInt(23, fc.startDayOfMonth());
            ps.setInt(24, fc.startYear());
            ps.setInt(25, fc.endMonth());
            ps.setInt(26, fc.endDayOfWeek());
            ps.setInt(27, fc.endDayOfMonth());
            ps.setInt(28, fc.endYear());
            ps.setDate(29, fc.sqlStartDate());
            ps.setDate(30, fc.sqlEndDate());
            ps.setInt(31, fc.startTimeHour());
            ps.setInt(32, fc.startTimeMin());
            ps.setInt(33, fc.endTimeHour());
            ps.setInt(34, fc.endTimeMin());
            ps.setInt(35, fc.getServices().getServiceStatus().getStatusId());
            ps.setString(36, fc.getTimeZone());
            ps.setString(37, fc.getStartTimeUtc());
            ps.setString(38, fc.getEndTimeUtc());
            ps.setString(39, fc.getStartOffset());
            ps.setString(40, fc.getEndOffset());

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
                    + fc.getAssociate2().getFirstName());
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
            ps.setInt(1, fc.getServiceStatus().getStatusId());
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
