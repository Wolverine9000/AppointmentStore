/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import com.mysql.jdbc.Blob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author williamdobbs
 */
public class ImageDB
{
    public static int writeImage(int product_id, String file_path)
      {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
            {
            connection.setAutoCommit(false);
            // set up the input stream from file
            File inputFile = new File(file_path);
            FileInputStream fileInputStream = new FileInputStream(inputFile);

            // insert into the BLOB in the database
            String query =
                    "INSERT INTO product_images (product_id, product_image) "
                    + "  VALUES(?, ?)";
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, product_id);
            ps.setBinaryStream(2, fileInputStream, (int) file_path.length());
            ps.executeUpdate();
            
            // close the input stream
            fileInputStream.close();

            connection.commit();
            
            return 1;
            
            }
        catch (Exception e)
            {
            e.printStackTrace();
            DBUtil.rollback(connection);
            return 0;
            }
        finally
            {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
            }
      }

    public static int readImage(int product_id, String filename)
      {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
            {
            // set up output stream to file
            File outputFile = new File(filename);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            // set up input stream from database
            String query =
                    "SELECT product_image "
                    + "FROM product_images "
                    + "WHERE product_id = ?";
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, product_id);
            rs = ps.executeQuery();
            rs.next();
            
            // reading image as InputStream
            Blob productImageBlob = (Blob) rs.getBlob("product_image");
            InputStream inputStream = productImageBlob.getBinaryStream();

            // set buffer
            int blobLength = (int) productImageBlob.length();
            int bufferSize = 1024;
            byte[] binaryBuffer = new byte[bufferSize];

            int byteCount = 0;
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(binaryBuffer)) != -1)
                {
                outputStream.write(binaryBuffer, 0, bytesRead);
                byteCount += bytesRead;
                }

            // close the input and output streams
            outputStream.close();
            inputStream.close();

            return byteCount;
            
            }
        catch (Exception e)
            {
            e.printStackTrace();
            DBUtil.rollback(connection);
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
