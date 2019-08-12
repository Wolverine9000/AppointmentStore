<%-- 
    Document   : request_headers
    Created on : Mar 17, 2013, 1:25:03 PM
    Author     : williamdobbs
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>William Dobbs request headers</title>
    </head>
    <body>
        <h1>Request Headers</h1>
        <table cellpadding='5' border="1">
            <tr align="left">
                <th>name</th>
                <th>Value</th>
            </tr>
            <%@ page import="java.util.Enumeration" %>
            <%
                Enumeration headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements())
                    {
                    String name = (String) headerNames.nextElement();
                    String value = request.getHeader(name);
            %>
            <tr>
                <td width="120"><%= name%></td>
                <td><%= value%></td>
            </tr>
            <% }%>
            <%
                Cookie[] cookies = request.getCookies();
                for (Cookie c : cookies)
                    {
            %>
            <tr>
                <td align="right"><%= c.getName()%></td>
                <td><%= c.getValue()%></td>
            </tr>
            <%
                    }%>
        </table>
    </body>
</html>
