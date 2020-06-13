/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import cart.ShoppingCartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import messages.LogFile;
import store.business.Client;
import store.business.Invoice;

/**
 *
 * @author williamdobbs
 */
public class InvoiceDB
{

    public static int insert(Invoice invoice)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method adds a record to the Invoices table.
        //To insert the exact invoice date, the SQL NOW() function is used.
        int customerID = CustomerDB.selectCustomerID(invoice.getClient());
        String query = "INSERT INTO invoice "
                + "(customer_id, invoice_date, total_amount, is_processed, confirmation_number) "
                + "VALUES (?, NOW(), ?, 'n', ?)";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerID);
            ps.setDouble(2, invoice.getInvoiceTotal());
            ps.setInt(3, invoice.getConfirmationNumber());
            ps.executeUpdate();

            //Get the InvoiceID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            int invoiceID;
            try (Statement identityStatement = connection.createStatement(); ResultSet identityResultSet = identityStatement.executeQuery(identityQuery))
            {
                identityResultSet.next();
                invoiceID = identityResultSet.getInt("IDENTITY");
            }

            //Write line items to the LineItem table.
            List<ShoppingCartItem> lineItems = invoice.getLineItems();
            for (int i = 0; i < lineItems.size(); i++)
            {
                ShoppingCartItem item = lineItems.get(i);
                LineItemDB.insert(invoiceID, item);
            }
            return invoiceID;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB insert ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Invoice> selectUnprocessedInvoices()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method reads in all invoices that have not been
        //processed yet. To do this, it creates a ArrayList<Invoice> of
        //Invoice objects, which each contain a Customer object.
        //This method returns null if no unprocessed invoices are found.
        String query = "SELECT * "
                + "FROM customer "
                + "INNER JOIN invoice "
                + "ON customer.id = invoice.customer_id "
                + "WHERE invoice.is_processed = 'n' "
                + "ORDER BY invoice_date";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Invoice> unprocessedInvoices = new ArrayList<>();
            while (rs.next())
            {
                //Create a Customer object
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setCompany(rs.getString("company"));
                client.setEmail(rs.getString("email"));
                client.setAddress(rs.getString("address"));
                client.sethomePhone(rs.getString("home_phone"));
                client.setCity(rs.getString("city"));
                client.setState(rs.getString("state"));
                client.setZip(rs.getString("zip_code"));
                client.setCcNumber(rs.getString("cc_number"));

                // get line items
                int invoiceID = rs.getInt("invoice_id");
                ArrayList<ShoppingCartItem> lineItems = LineItemDB.selectLineItems(invoiceID);

                //Create the Invoice object
                Invoice invoice = new Invoice();
                invoice.setClient(client);
                invoice.setInvoiceDate(rs.getTimestamp("invoice_date"));
                invoice.setConfirmationNumber(rs.getInt("confirmation_number"));
                invoice.setInvoiceNumber(rs.getInt("invoice_id"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setInvoiceNumber(invoiceID);
                invoice.setLineItems(lineItems);

                unprocessedInvoices.add(invoice);
            }
            return unprocessedInvoices;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB selectUnprocessedInvoices ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static ArrayList<Invoice> selectUnprocessedInvoices_2()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method returns null if no unprocessed invoices are found.
        String query = "SELECT * "
                + "FROM invoice "
                + "WHERE invoice.is_processed = 'n' "
                + "ORDER BY invoice_date";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Invoice> unprocessedInvoices = new ArrayList<>();
            while (rs.next())
            {
                //Create the Invoice object
                Invoice invoice = new Invoice();
                invoice.setInvoiceDate(rs.getTimestamp("invoice_date"));
                invoice.setConfirmationNumber(rs.getInt("confirmation_number"));
                invoice.setInvoiceNumber(rs.getInt("invoice_id"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setY_n(rs.getString("is_processed"));

                unprocessedInvoices.add(invoice);
            }
            return unprocessedInvoices;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB selectUnprocessedInvoices_2 ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static ArrayList<Invoice> selectProcessedInvoices_2()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method returns null if no unprocessed invoices are found.
        String query = "SELECT * "
                + "FROM invoice "
                + "WHERE invoice.is_processed = 'y' "
                + "ORDER BY invoice_date";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Invoice> unprocessedInvoices = new ArrayList<>();
            while (rs.next())
            {
                //Create the Invoice object
                Invoice invoice = new Invoice();
                invoice.setInvoiceDate(rs.getTimestamp("invoice_date"));
                invoice.setConfirmationNumber(rs.getInt("confirmation_number"));
                invoice.setInvoiceNumber(rs.getInt("invoice_id"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));

                unprocessedInvoices.add(invoice);
            }
            return unprocessedInvoices;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB selectProcessedInvoices_2 ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    // This method sets the IsProcessed column to 'y'
    public static int updateInvoiceIsProcessed(int invoiceNumber)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method is used by the admin application to set the is_processed field to "y".
        String query = "UPDATE invoice SET "
                + "is_processed = 'y' "
                + "WHERE invoice_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, invoiceNumber);
            return ps.executeUpdate();
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB updateInvoiceIsProcessed ", e.getMessage(), e.toString());
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Invoice> selectProcessedInvoices()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method reads in all invoices that HAVE been
        //processed. To do this, it creates a ArrayList<Invoice> of
        //Invoice objects, which each contain a User object.
        //This method returns null if no processed invoices are found.
        String query = "SELECT * "
                + "FROM customer "
                + "INNER JOIN invoice "
                + "ON customer.id = invoice.customer_id "
                + "WHERE is_processed = 'y' "
                + "ORDER BY invoice_date";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Invoice> processedInvoices = new ArrayList<>();
            while (rs.next())
            {
                //Create a User object
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setCompany(rs.getString("company"));
                client.setEmail(rs.getString("email"));
                client.sethomePhone(rs.getString("home_phone"));
                client.setAddress(rs.getString("address"));
                client.setCity(rs.getString("city"));
                client.setState(rs.getString("state"));
                client.setZip(rs.getString("zip_code"));
                client.setCcNumber(rs.getString("cc_number"));

                // get line items
                int invoiceID = rs.getInt("Invoice.InvoiceID");
                List<ShoppingCartItem> lineItems = LineItemDB.selectLineItems(invoiceID);

                //Create the Invoice object
                Invoice invoice = new Invoice();
                invoice.setClient(client);
                invoice.setInvoiceDate(rs.getDate("invoice_date"));
                invoice.setInvoiceNumber(invoiceID);
                invoice.setLineItems(lineItems);

                processedInvoices.add(invoice);
            }
            return processedInvoices;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB selectProcessedInvoices ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Invoice> selectAllInvoices()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method returns null if no invoices are found.
        String query = "SELECT * FROM invoice";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Invoice> invoices = new ArrayList<>();
            while (rs.next())
            {
                //Create the Invoice object
                Invoice i = new Invoice();
                i.setInvoiceNumber(rs.getInt("invoice_id"));
                i.setInvoiceDate(rs.getTimestamp("invoice_date"));
                i.setConfirmationNumber(rs.getInt("confirmation_number"));
                i.setTotalAmount(rs.getDouble("total_amount"));
                i.setY_n(rs.getString("is_processed"));

                invoices.add(i);
            }
            return invoices;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB selectAllInvoices ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Invoice> selectCustomerInvoices(int clientId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method returns null if no invoices are found.
        String query = "SELECT * FROM invoice "
                + "WHERE customer_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            rs = ps.executeQuery();
            ArrayList<Invoice> invoices = new ArrayList<>();
            while (rs.next())
            {
                //Create the Invoice object
                Invoice i = new Invoice();
                i.setInvoiceNumber(rs.getInt("invoice_id"));
                i.setInvoiceDate(rs.getTimestamp("invoice_date"));
                i.setConfirmationNumber(rs.getInt("confirmation_number"));
                i.setTotalAmount(rs.getDouble("total_amount"));

                invoices.add(i);
            }
            return invoices;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB selectCustomerInvoices ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Invoice selectInvoice(int invoice_id)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // get invoice, customer, and line items
        String query = "SELECT * FROM invoice "
                + "WHERE invoice_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, invoice_id);
            rs = ps.executeQuery();
            if (rs.next());
            {
                // Create the Invoice object
                Invoice i = new Invoice();
                i.setInvoiceNumber(rs.getInt("invoice_id"));
                i.setInvoiceDate(rs.getTimestamp("invoice_date"));
                i.setConfirmationNumber(rs.getInt("confirmation_number"));
                i.setTotalAmount(rs.getDouble("total_amount"));
                i.setY_n(rs.getString("is_processed"));
                i.setClient(CustomerDB.selectClient(rs.getInt("customer_id")));
                List<ShoppingCartItem> lineItems = LineItemDB.selectLineItems(invoice_id);
                i.setLineItems(lineItems);

                return i;
            }
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB selectInvoice ", e.getMessage(), e.toString());
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Invoice> selectInvoiceDates(Timestamp date_from, Timestamp date_to)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method returns null if no  invoices are found.
        String query = "SELECT * "
                + "FROM invoice "
                + "WHERE invoice_date > ? AND invoice_date < ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setTimestamp(1, date_from);
            ps.setTimestamp(2, date_to);
            rs = ps.executeQuery();
            ArrayList<Invoice> invoices = new ArrayList<>();
            while (rs.next())
            {
                //Create the Invoice object
                Invoice invoice = new Invoice();
                invoice.setInvoiceDate(rs.getTimestamp("Invoice_date"));
                invoice.setConfirmationNumber(rs.getInt("confirmation_number"));
                invoice.setInvoiceNumber(rs.getInt("invoice_id"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setY_n(rs.getString("is_processed"));

                invoices.add(invoice);
            }
            return invoices;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("InvoiceDB selectInvoiceDates ", e.getMessage(), e.toString());
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
