<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page  language="java" import="java.util.*,java.text.*"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="styles/schoolschedule.css" />
        <title>School Schedule Calendar</title>
    </head>
    <body>       

<table width="100" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="25%">&nbsp;</td>
                <td width="45%">&nbsp;</td>
                <td width="30%">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><table width="200%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="6%">Year</td>
                                        <td width="7%">
                                            <form action="calendar" method="post">
                                                <select name="iYear" onchange="submit()">
                                                    <c:forEach var="years" items="${calendarYears}">
                                                        <c:choose>
                                                            <c:when test="${years == iYear}"><option selected="selected">
                                                                    ${iYear}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <option value="${years}">${years}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select><input type="hidden" name="userID" value="${customer.customer_id}"
                                            </form></td>
                                        <td width="73%" align="center"><h4>${cMonth} ${iYear}</h4></td>
                                        <td width="7%">Month</td>
                                        <td width="8">
                                            <form action="calendar" method="post">
                                                <select name="iMonth" onchange="submit()">
                                                    <c:forEach var="months" items="${months}">
                                                        <c:choose>
                                                            <c:when test="${months == cMonth}"><option value="${cMonth}" selected="selected">
                                                                    ${cMonth}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <option value="${months}">${months}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select><input type="hidden" name="userID" value="${customer.customer_id}">
                                            </form></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td><table align="center" border="1" cellpadding="2" cellspacing="0" width="200%" height="200">
                                    <tbody>
                                        <tr>
                                            <th>Sun</th>
                                            <th>Mon</th>
                                            <th>Tue</th>
                                            <th>Wed</th>
                                            <th>Thu</th>
                                            <th>Fri</th>
                                            <th>Sat</th>
                                        </tr>
                                        <c:forEach items="${totalWeeks}" var="totalWeeks"
                                                   begin="0" end="${iTotalweeks}" step="1">
                                            <tr><c:forEach items="${weekDays}" var="weekDays"
                                                       begin="0" end="7" step="1">
                                                    <c:choose>
                                                        <c:when test="${(cnt < weekStartDay) || (cnt - weekStartDay + 1)  > days}">
                                                            <td align="center" height="35" class="dsb">&nbsp;</td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td align="left" height="50" <c:out value="${cnt - weekStartDay + 1}"></c:out>
                                                                <span>${cnt - weekStartDay + 1}</span>
                                                                    <c:forEach items="${calendar}" var="calendar">
                                                                    <c:if test="${(calendar.month == iMonth) && (calendar.day == weekDays)}">
                                                                        <a><c:out value="${calendar.startTimeHour}">${calendar.startTimeHour}</c:out>
                                                                            <c:out value="${calendar.endTimeHour}">${calendar.endTimeHour}</c:out></a>
                                                                        <a><c:out value="${calendar.serviceDescription}">${calendar.serviceDescription}</c:out></a>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </td>
                                                        </c:otherwise>
                                                    </c:choose><c:set var="cnt" value="${cnt + 1}"></c:set>
                                                </c:forEach>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table></td>
                        </tr>
                    </table></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table>
                                                    </form>
</body>