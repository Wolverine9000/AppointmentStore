package store.data;

import java.sql.*;

public class SQLUtil
{

    public static String getHtmlRows(ResultSet results)
            throws SQLException
    {
        StringBuilder htmlRows = new StringBuilder();
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        htmlRows.append("<tr>");
        for (int i = 1; i <= columnCount; i++)
        {
            htmlRows.append("<td><b>").append(metaData.getColumnName(i)).append("</td>");
        }
        htmlRows.append("</tr>");

        while (results.next())
        {
            htmlRows.append("<tr>");
            for (int i = 1; i <= columnCount; i++)
            {
                htmlRows.append("<td>").append(results.getString(i)).append("</td>");
            }
        }
        htmlRows.append("</tr>");

        return htmlRows.toString();
    }

    public static String encode(String s)
    {
        if (s == null)
        {
            return s;
        }
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++)
        {
            char ch = sb.charAt(i);
            if (ch == 39)
            {  // 39 is the ASCII code for an apostrophe
                sb.insert(i++, "'");
            }
        }
        return sb.toString();
    }
}
