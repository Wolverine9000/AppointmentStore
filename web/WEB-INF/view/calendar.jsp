<link rel="stylesheet" type="text/css" href="css/clientCSS/clientCSS_calendar.css">
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="js/jquery.ui.datepicker.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>

<script>
    $(function() {
        $("#datepicker").datepicker({
            minDate: "-24M", maxDate: "+24M",
            changeYear: true,
            changeMonth: true,
            showButtonPanel: true,
            selectOtherMonths: true,
            showOtherMonths: true
        });
    });
</script>
<%--display message if error occurs--%>
<p class="error">${errorMessage}</p>

<%--if viewWeek or viewDay is NOT selected (i.e.true)--%>
<c:if test="${viewWeek == false && viewDay == false}">
    <figure id="calendar">
        <figcaption>Month View&nbsp;&nbsp;
            ${cMonth}&nbsp;${iYear}
        </figcaption>
        <table id="calendarTable">
            <thead>
                <tr>
                    <th></th>
                    <th colspan="2">
                        <a href="<c:url value='/prevMonth?prevMonth=${cMonth}'/>" style="text-decoration: none">
                            <img src="/AppointmentStore/img/nav/Arrow-Back.png" id="backArrow" alt="previous month">
                        </a>
                        <a href="<c:url value='/today'/>">
                            This Month
                        </a>&nbsp;
                        <a href="<c:url value='/viewDay'/>">
                            Day
                        </a>&nbsp;
                        <a href="<c:url value='/viewWeek'/>">
                            Week
                        </a>
                        <a href="<c:url value='/nextMonth?prevMonth=${cMonth}' />">
                            <img src="/AppointmentStore/img/nav/Arrow-Next.png" id="nextArrow" alt="next month">
                        </a>
                    </th>
                    <th colspan="2">
            <form action="calendar" method="post">
                <select name="iYear" onchange="submit();">
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
            </form>
            <form action="calendar" method="post">
                <select name="iMonth" onchange="submit();">
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
            </form>
            </th>
            <th>
            <form action="gotoDate" onchange="submit();" method="post">
                <input placeholder="<fmt:formatDate value="${todaysDate}" pattern="MM/dd/yyyy" />" 
                       name="gotoDate" type="text"  pattern="[01]?\d\/[0-3]\d\/\d{4}" 
                       onchange="submit();" value="${gotoDate}"id="datepicker" size="10">
            </form>
            </th>
            <th></th>
            </tr>
            </thead>
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
                                    <td>&nbsp;</td>
                                </c:when>
                                <c:otherwise>
                                    <td <c:out value="${cnt - weekStartDay + 1}"></c:out>

                                            <span><a href="<c:url value='/viewDay?weekStartDay=${cnt - weekStartDay + 1}' /> ">
                                                ${cnt - weekStartDay + 1})
                                            </a></span><br>

                                        <c:forEach items="${calendar}" var="calendar">
                                            <c:if test="${(calendar.month == iMonth) && (calendar.day == weekDays) && (calendar.year == iYear) 
                                                          && (calendar.date == (cnt - weekStartDay + 1))}">
                                                <%--<c:out value="${calendar.associateTime}">${calendar.associateTime}</c:out>--%>
                                                <h4><c:out value="${calendar.timestampFormat}">${calendar.timestampFormat}</c:out></h4>
                                                <a class="good" href="<c:url value='/calendar?serviceDescription=${calendar.serviceDescription}' />">
                                                    ${calendar.serviceDescription}
                                                </a>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </c:otherwise>
                            </c:choose><c:set var="cnt" value="${cnt + 1}"></c:set>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </figure>
</c:if>

<%--if viewWeek is selected--%>
<c:if test="${viewWeek == true}">
    <figure id="weekView">
        <figcaption>Week View&nbsp;&nbsp;&nbsp;
            <fmt:formatDate value="${viewWeekStartDate}"
                            dateStyle="medium" />&nbsp;&nbsp;to&nbsp;
            <fmt:formatDate value="${viewWeekEndDate}"
                            dateStyle="medium" /></figcaption>
        <table id="weekTable">
            <thead>
                <tr>
                    <th></th>
                    <th colspan="3">
                        <a href="<c:url value='/prevWeek?prevWeek=${viewWeekStartDate}'/>" style="text-decoration: none">
                            <img src="/AppointmentStore/img/nav/Arrow-Back.png" id="weekBackArrow" alt="previous month">
                        </a>
                        <a href="<c:url value='/thisWeek'/>">
                            This Week
                        </a>&nbsp;
                        <a href="<c:url value='/viewToday'/>">
                            Day
                        </a>&nbsp;
                        <a href="<c:url value='/calendar?calendar=${iMonth}'/>">
                            Month
                        </a>
                        <a href="<c:url value='/nextWeek?nextWeek=${viewWeekEndDate}' />">
                            <img src="/AppointmentStore/img/nav/Arrow-Next.png" id="weekNextArrow" alt="next month">
                        </a>
                    </th>
                    <th>
                    </th>
                    <th colspan="2">
                        ${cMonth}
                    </th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th></th>
                    <th>Sun&nbsp;<a href="<c:url value='/viewDate?viewDate=${sunString}'/>">(<fmt:formatDate value="${sun}"
                                                                                       pattern="MM/dd/yyyy" />)</a></th>
                    <th>Mon&nbsp;(<fmt:formatDate value="${mon}"
                                    pattern="d" />)</th>
                    <th>Tue&nbsp;(<fmt:formatDate value="${tue}"
                                    pattern="d" />)</th>
                    <th>Wed&nbsp;(<fmt:formatDate value="${wed}"
                                    pattern="d" />)</th>
                    <th>Thu&nbsp;(<fmt:formatDate value="${thu}"
                                    pattern="d" />)</th>
                    <th>Fri&nbsp;(<fmt:formatDate value="${fri}"
                                    pattern="d" />)</th>
                    <th>Sat&nbsp;(<fmt:formatDate value="${sat}"
                                    pattern="d" />)</th>
                </tr>

                <c:forEach begin="6" end="21" step="1" var="time">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${time == 12}">
                                    <c:out value="${time}" />:00pm
                                </c:when>
                                <c:when test="${time > 12}">
                                    <c:out value="${time - 12}" />:00pm
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${time}" />:00am
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <c:forEach begin="1" end="7" step="1" var="day">
                            <td><c:forEach items="${calendarWeek}" var="calendarWeek">
                                    <c:if test="${calendarWeek.startTimeHour == time && calendarWeek.startTimeMin == 0
                                                  && calendarWeek.day == day}">

                                          <a class="good" href="<c:url value='/calendar?serviceDescription=${calendarWeek.serviceDescription}' />">
                                              ${calendarWeek.serviceDescription} ${calendarWeek.startTimeHour} ${minute}<br>${day}
                                              ${calendarWeek.startTimeMin} ${calendarWeek.day} ${calendarWeek.date}
                                          </a>
                                    </c:if>
                                </c:forEach></td>
                            </c:forEach>
                    </tr>
                    <%--start loop to list :15, :30, :45 minute intervals--%>
                    <c:forEach begin="0" end="2" step="1" var="minute">
                        <tr>
                            <td>
                                <c:if test="${minute ==  0}">
                                    <c:choose>
                                        <c:when test="${time == 12}">
                                            <c:out value="${time}" />:15pm
                                        </c:when>
                                        <c:when test="${time > 12}">
                                            <c:out value="${time - 12}" />:15pm
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${time}" />:15am
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:if test="${minute ==  1}">
                                    <c:choose>
                                        <c:when test="${time == 12}">
                                            <c:out value="${time}" />:30pm
                                        </c:when>
                                        <c:when test="${time > 12}">
                                            <c:out value="${time - 12}" />:30pm
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${time}" />:30am
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:if test="${minute ==  2}">
                                    <c:choose>
                                        <c:when test="${time == 12}">
                                            <c:out value="${time}" />:45pm
                                        </c:when>
                                        <c:when test="${time > 12}">
                                            <c:out value="${time - 12}" />:45pm
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${time}" />:45am
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <%--start loop for the seven days of the week--%>
                            <c:forEach begin="1" end="7" step="1" var="day">
                                <td>
                                    <%--get appointment that matches the hour, day, at the :15 minute interval--%>
                                    <c:forEach items="${calendarWeek}" var="calendarWeek">
                                        <c:if test="${calendarWeek.startTimeHour == time && calendarWeek.startTimeMin == 15
                                                      && minute == 0 && calendarWeek.day == day}">
                                              <a class="good" href="<c:url value='/calendar?serviceDescription=${calendarWeek.serviceDescription}' />">
                                                  ${calendarWeek.serviceDescription} ${calendarWeek.startTimeHour} ${minute}<br>${day}
                                                  ${calendarWeek.startTimeMin} ${calendarWeek.day} ${calendarWeek.date}
                                              </a>
                                        </c:if>
                                        <%--get appointment that matches the hour, day, at the :30 minute interval--%>
                                        <c:if test="${calendarWeek.startTimeHour == time && calendarWeek.startTimeMin == 30
                                                      && minute == 1 && calendarWeek.day == day}">
                                              <a class="good" href="<c:url value='/calendar?serviceDescription=${calendarWeek.serviceDescription}' />">
                                                  ${calendarWeek.serviceDescription} ${calendarWeek.startTimeHour} ${minute}<br>${day}
                                                  ${calendarWeek.startTimeMin} ${calendarWeek.day} ${calendarWeek.date}
                                              </a>
                                        </c:if>
                                        <%--get appointment that matches the hour, day, at the :45 minute interval--%>
                                        <c:if test="${calendarWeek.startTimeHour == time && calendarWeek.startTimeMin == 45
                                                      && minute == 2 && calendarWeek.day == day}">
                                              <a class="good" href="<c:url value='/calendar?serviceDescription=${calendarWeek.serviceDescription}' />">
                                                  ${calendarWeek.serviceDescription} ${calendarWeek.startTimeHour} ${minute}<br>${day}
                                                  ${calendarWeek.startTimeMin} ${calendarWeek.day} ${calendarWeek.date}
                                              </a>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
    </figure>
</c:if>

<%--if viewDay is selected--%>
<c:if test="${viewDay == true}">
    <figure id="dayView">
        <figcaption>Day View</figcaption>
        <table id="dayTable">
            <thead>
                <tr>
                    <th></th>

                    <th>
            <form action="gotoDate" onchange="submit();" method="post">
                <input placeholder="<fmt:formatDate value="${todaysDate}"
                                pattern="MM/dd/yyyy" />" name="gotoDate" type="text"  pattern="[01]?\d\/[0-3]\d\/\d{4}" 
                       onchange="submit();" value="${gotoDate}" size="10" id="datepicker">
            </form>
            <a href="<c:url value='/prevDay?prevDay=${day}'/>" style="text-decoration: none">
                <img src="/AppointmentStore/img/nav/Arrow-Back.png" id="dayBackArrow" alt="previous month">
            </a>
            <a href="<c:url value='/viewToday'/>">
                Today
            </a>&nbsp;
            <a href="<c:url value='/viewWeek'/>">
                Week
            </a>&nbsp;
            <a href="<c:url value='/calendar?calendar=${iMonth}'/>">
                Month
            </a>

            <a href="<c:url value='/nextDay?nextDay=${day}' />">
                <img src="/AppointmentStore/img/nav/Arrow-Next.png" id="dayNextArrow" alt="next month">
            </a>
            </th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <th></th>
                    <th><fmt:formatDate value="${selectedDate}"
                                    dateStyle="FULL" /></th>
                </tr>

                <c:forEach begin="6" end="21" step="1" var="time">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${time == 12}">
                                    <c:out value="${time}" />:00pm
                                </c:when>
                                <c:when test="${time > 12}">
                                    <c:out value="${time - 12}" />:00pm
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${time}" />:00am
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:forEach items="${calendarDay}" var="calendarDay">
                                <c:if test="${calendarDay.startTimeHour == time && calendarDay.startTimeMin == 0}">
                                    <a class="good" href="<c:url value='/calendar?serviceDescription=${calendarDay.serviceDescription}' />">
                                        ${calendarDay.serviceDescription}
                                    </a>
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <c:forEach begin="0" end="2" step="1" var="minute">
                        <tr>
                            <td>
                                <c:if test="${minute ==  0}">
                                    <c:choose>
                                        <c:when test="${time == 12}">
                                            <c:out value="${time}" />:15pm
                                        </c:when>
                                        <c:when test="${time > 12}">
                                            <c:out value="${time - 12}" />:15pm
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${time}" />:15am
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:if test="${minute ==  1}">
                                    <c:choose>
                                        <c:when test="${time == 12}">
                                            <c:out value="${time}" />:30pm
                                        </c:when>
                                        <c:when test="${time > 12}">
                                            <c:out value="${time - 12}" />:30pm
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${time}" />:30am
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:if test="${minute ==  2}">
                                    <c:choose>
                                        <c:when test="${time == 12}">
                                            <c:out value="${time}" />:45pm
                                        </c:when>
                                        <c:when test="${time > 12}">
                                            <c:out value="${time - 12}" />:45pm
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${time}" />:45am
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>
                                <c:forEach items="${calendarDay}" var="calendarDay">
                                    <c:if test="${calendarDay.startTimeHour == time && 
                                                  minute == 0 && calendarDay.startTimeMin == 15}">
                                          <a class="good" href="<c:url value='/calendar?serviceDescription=${calendarDay.serviceDescription}' />">
                                              ${calendarDay.serviceDescription}
                                          </a>
                                    </c:if>
                                    <c:if test="${calendarDay.startTimeHour == time && 
                                                  minute == 1 && calendarDay.startTimeMin == 30}">
                                          <a class="good" href="<c:url value='/calendar?serviceDescription=${calendarDay.serviceDescription}' />">
                                              ${calendarDay.serviceDescription}
                                          </a>
                                    </c:if>
                                    <c:if test="${calendarDay.startTimeHour == time && 
                                                  minute == 2 && calendarDay.startTimeMin == 45}">
                                          <a class="good" href="<c:url value='/calendar?serviceDescription=${calendarDay.serviceDescription}' />">
                                              ${calendarDay.serviceDescription}
                                          </a>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
    </figure>
</c:if>

