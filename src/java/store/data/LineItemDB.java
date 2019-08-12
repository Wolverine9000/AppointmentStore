/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import cart.ShoppingCartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import store.business.Product;

/**
 *
 * @author williamdobbs
 */
public class LineItemDB
{
    //This method adds one lineItem to the line_item table.
    public static int insert(int invoiceID, ShoppingCartItem items)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        int productID = ProductDB.selectProductID(items.getProduct());
        String query = "INSERT INTO line_item(invoice_id, product_id, quantity) "
                    + "VALUES (?, ?, ?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, invoiceID);
            ps.setInt(2, productID);
            ps.setShort(3, items.getQuantity());
            return ps.executeUpdate();
        }
        catch(SQLException e)
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

    //This method returns null if a record isn't found.
    public static ArrayList<ShoppingCartItem> selectLineItems(int invoiceID)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM line_item "
                + "WHERE invoice_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, invoiceID);
            rs = ps.executeQuery();
            ArrayList<ShoppingCartItem> lineItems = new ArrayList<ShoppingCartItem>();
            while (rs.next())
            {
                ShoppingCartItem lineItem = new ShoppingCartItem();
                int productID = rs.getInt("product_id");
                Product product = ProductDB.selectProduct(productID);
                lineItem.setProduct(product);
                lineItem.setQuantity(rs.getShort("quantity"));
                lineItems.add(lineItem);
            }
            return lineItems;
        }
        catch(SQLException e)
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
