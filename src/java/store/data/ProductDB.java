package store.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import messages.LogFile;
import store.business.Category;
import store.business.Product;
import store.business.Services;

/**
 *
 * @author williamdobbs
 */
public class ProductDB
{

    //This method returns null if a category isn't found.
    public static ArrayList<Category> selectCategory()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM category";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Category> category = new ArrayList<>();
            while (rs.next())
            {
                Category c = new Category();
                c.setCategory_id(rs.getInt("id"));
                c.setName(rs.getString("name"));

                category.add(c);
            }
            return category;
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
//This method returns null if a category isn't found.

    public static ArrayList<Category> selectCategoryServices()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM service_category";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Category> category = new ArrayList<>();
            while (rs.next())
            {
                Category c = new Category();
                c.setCategory_id(rs.getInt("service_category_id"));
                c.setName(rs.getString("category_name"));

                category.add(c);
            }
            return category;
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

    //This method returns null if a category name isn't found.
    public static Category selectCategory(int category_id)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM category "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();
            if (rs.next());
            {
                Category c = new Category();
                c.setCategory_id(category_id);
                c.setName(rs.getString("name"));

                return c;
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

    //This method returns null if a product isn't found.
    public static ArrayList<Product> selectProducts(int category_id)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM product "
                + "WHERE category_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next())
            {
                Product p = new Product();
                p.setCategory_id(rs.getInt("category_id"));
                p.setProductId(rs.getInt("id"));
                p.setName(rs.getString("product_name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));

                products.add(p);
            }
            return products;
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

    //This method returns null if a product isn't found.
    public static ArrayList<Product> selectAllProducts()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM product";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next())
            {
                Product p = new Product();
                p.setCategory_id(rs.getInt("category_id"));
                p.setProductId(rs.getInt("id"));
                p.setName(rs.getString("product_name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));

                products.add(p);
            }
            return products;
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
    public static ArrayList<Services> selectServices(int category_id)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM services "
                + "WHERE service_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();
            ArrayList<Services> services = new ArrayList<>();
            while (rs.next())
            {
                Services s = new Services();
                s.setCategory_id(rs.getInt("service_id"));
                s.setProductId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setPrice(rs.getDouble("price"));
                s.setServiceTime(rs.getInt("service_time"));
                s.setGraceTime(rs.getInt("grace_time"));
                s.setServiceProductId(rs.getInt("service_product_id"));

                services.add(s);
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

    //This method returns null if a product isn't found.
    public static Services selectService(int serviceId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT service_id, name, description, service_time, grace_time, price "
                + "FROM appointment.services s "
                + "WHERE s.service_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();
            if (rs.next())
            {
                Services s = new Services();
                s.setServiceId(rs.getInt("service_id"));
                s.setDescription(rs.getString("description"));
                s.setServiceTime(rs.getInt("service_time"));
                s.setPrice(rs.getDouble("price"));
                s.setGraceTime(rs.getInt("grace_time"));
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

    //This method returns null if a product isn't found.
    public static String selectServiceName(int serviceId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT description "
                + "FROM appointment.services s "
                + "WHERE s.service_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();
            if (rs.next())
            {
                return (rs.getString("description"));
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

    //This method returns null if a client isn't found.
    public static HashMap<String, Services> selectServicesMap()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM services s "
                + "JOIN service_category sc "
                + "ON s.service_category_service_category_id = sc.service_category_id "
                + "ORDER BY s.description";

        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            HashMap services = new HashMap();
            String id;
            while (rs.next())
            {
                Services s = new Services();
                id = Integer.toString(rs.getInt("service_id"));
                s.setServiceId(rs.getInt("service_id"));
                s.setCategory_id(rs.getInt("service_category_service_category_id"));
                s.setServiceProductId(rs.getInt("service_product_id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setPrice(rs.getDouble("price"));
                s.setServiceTime(rs.getInt("service_time"));
                s.setGraceTime(rs.getInt("grace_time"));
                s.setCategory(rs.getString("category_name"));

                services.put(id, s);
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
    public static ArrayList<Services> selectAllServices()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM services s "
                + "INNER JOIN service_category sc "
                + "ON s.service_category_service_category_id = sc.service_category_id "
                + "ORDER BY sc.service_category_id ";
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Services> services = new ArrayList<>();
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
                s.setCategory(rs.getString("category_name"));

                services.add(s);
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

    //This method returns null if a product isn't found.
//    public static HashMap<Integer, String> selectServices()
//    {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        
//        String query = "SELECT * FROM appointment.services";
//        try
//            {
//            ps = connection.prepareStatement(query);
//            rs = ps.executeQuery();
//            HashMap<Integer, String> services = new HashMap();
//            while (rs.next())
//                {
//                int serviceId = rs.getInt("service_id");
//                String memberLevel = rs.getString("memberLevel");
//                services.put(serviceId, memberLevel);
//                }
//            return services;
//            }
//        catch (SQLException e)
//            {
//            boolean insertFailed = LogFile.databaseError("ProductDB selectServices", e.getMessage(), e.toString());
//            return null;
//            }
//        finally
//            {
//            DBUtil.closeResultSet(rs);
//            DBUtil.closePreparedStatement(ps);
//            pool.freeConnection(connection);
//            }
//    }
    //This method returns null if a product isn't found.
    public static Product selectProduct(int productID)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Product "
                + "WHERE id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, productID);
            rs = ps.executeQuery();
            if (rs.next())
            {
                Product p = new Product();
                p.setCategory_id(rs.getInt("category_id"));
                p.setDescription(rs.getString("description"));
                p.setName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setProductId(rs.getInt("id"));
                return p;
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

    //This method will return 0 if product object isn't found.
    public static int selectProductID(Product product)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT id FROM Product "
                + "WHERE product_name = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, product.getName());
            rs = ps.executeQuery();
            rs.next();
            int productID = rs.getInt("id");
            return productID;
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

    public static boolean updateServiceInfo(Services s, int associateId)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method updates the record with a matching Services ID.
        //It returns a value of true if the Services Info can't be found.
        String query = "UPDATE services s "
                + "JOIN service_category sc "
                + "ON s.service_category_service_category_id = sc.service_category_id "
                + "SET "
                + "name = ?, "
                + "description = ?, "
                + "service_category_service_category_id = ?, "
                + "price = ?, "
                + "service_time = ? "
                + "WHERE associate_id = ? AND service_id = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.setInt(3, s.getCategory_id());
            ps.setDouble(4, s.getPrice());
            ps.setInt(5, s.getServiceTime());
            ps.setInt(6, associateId);
            ps.setInt(7, s.getServiceId());

            ps.executeUpdate();
            return false;
        }
        catch (SQLException e)
        {
            LogFile.databaseError("ProductDB updateServiceInfo error " + s.getName()
                    + " associate ID " + associateId, e.getMessage(), e.toString());
            return true;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
