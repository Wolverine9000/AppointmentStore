<%-- 
    Document   : m_scheduler
    Created on : Nov 23, 2014, 1:38:02 PM
    Author     : williamdobbs
--%>

<!-- ======================= scheduler page ============================== -->
<div data-role="page" id="scheduler" date-title="Scheduler">

    <div data-role="panel" id="schPanel" data-theme="b">
        <h2>Main Menu</h2>
        <ul data-role="listview">
            <li><a href="<c:url value='/mobile/m_associatePage.jsp'/>" data-ajax="false" data-transition="fade">Calendar</a></li>
            <li><a href="/mobile/m_associateScheduler.jsp" data-transition="slide">Scheduler</a></li>
            <li><a href="<c:url value='/mobile/m_associateClients.jsp' />" data-ajax="false" data-transition="fade">Clients</a></li>
            <li><a href="<c:url value='/mobile/m_associateSettings.jsp'/>" data-ajax="false" data-transition="slide">Settings</a></li>
            <li><a href="<c:url value='#' />">Messages</a></li>
            <li><a href="<c:url value='#' />">Account</a></li>
            <li><a href="<c:url value='logoutAssociate'/>" data-transition="fade">Logout</a></li>
        </ul>
    </div> 

    <header data-role="header" data-theme="b" data-position="fixed" class="header-logo">
        <a href="#schPanel" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-bars ui-mini ui-btn-icon-notext">Menu</a>
        <h1></h1>
    </header>

    <div data-role="content">
        <div id="messages">
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div> <!-- messages -->
        <!-- spinner div-->
        <div id="loading"></div>
        <h3 class="qs-h3-1">Quick Schedule</h3>
        <div id="loading"></div> 
        <section id="container">
            <div id="messages">
                <div id="postDataError" class="error smallText "></div>
                <div id="postDataSuccess" class="good smallText "></div>
            </div> <!-- messages -->

            <%--server-side validation error messages--%>
            <c:if test="${!empty validationErrorFlag}">
                <span class="error smallText">Please provide valid entries for the following field(s):</span><br>
                <ul  class="msg-ul">
                    <c:if test="${!empty startTimeError}">
                        <li>Please select a&nbsp;<strong>time in</strong>.</li>
                        </c:if>
                        <c:if test="${!empty endTimeError}">
                        <li>Please select a&nbsp;<strong>time out</strong>.</li>
                        </c:if>
                        <c:if test="${!empty dateError}">
                        <li>Please enter a valid&nbsp;<strong>starting date</strong>.</li>
                        </c:if>
                        <c:if test="${!empty hourError}">
                        <li>Make sure&nbsp;<strong>time out</strong>&nbsp;is greater than the selected&nbsp;<strong>time  in</strong>.</li>
                        </c:if>
                        <c:if test="${!empty daysError}">
                        <li>Select at least one of the&nbsp;<strong>Day</strong>&nbsp;check boxes from above.</li>
                        </c:if>
                </ul>
            </c:if>
            <c:if test="${!empty errorQSMessage}">
                <span class="error smallText">${errorQSMessage}</span><br>
            </c:if>
            <c:if test="${!empty message}">
                <span class="good smallText">${message}<br>
                    <ul class="msg-ul">
                        <li>time in: <strong>${qsStart}</strong></li>
                        <li>time out: <strong>${qsEnd}</strong></li>

                        <li>appointments every: <strong>:${qsFrequency}</strong> minutes</li> 
                        <li>length of time:&nbsp;<strong>${qsLength}</strong></li>
                        <li>starting on:
                            <strong><fmt:formatDate value="${dateQS}"
                                            type="date"
                                            dateStyle="FULL"/></strong> </li>
                                <c:if test="${!empty days}">
                            <li>on days:
                                <c:forEach var="day" items="${days}">
                                    <strong>${fn:toLowerCase(day)}</strong>,
                                </c:forEach></li>
                        </ul>
                    </c:if></span>
                </c:if>
        </section>
        <div id="qs-div">
            <table id="qs-table">
                <tbody>
                    <tr>
                        <th>Time In</th>
                        <th>Time Out</th>
                    </tr>
                    <tr>
                        <td>
                            <label for="qs-starttime" class="ui-hidden-accessible">Start:</label>
                            <select id="qs-starttime" data-inline="true" class="qs-times">
                                <c:forEach var="time" items="${times}">
                                    <c:choose>
                                        <c:when test="${time == defaultStartTime}">
                                            <option value="${time}" selected="selected">${time}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="<c:out value='${time}' />">
                                                <c:out value="${time}" />
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                        <td> 
                            <label for="qs-endtime" class="ui-hidden-accessible">End:</label>
                            <select name="qs-endtime" id="qs-endtime" data-inline="true" class="qs-times">
                                <c:forEach var="time" items="${times}">
                                    <c:choose>
                                        <c:when test="${time == defaultEndTime}">
                                            <option value="${time}" selected="selected">${time}</option>
                                        </c:when>
                                    </c:choose>
                                    <option value="<c:out value='${time}' />">
                                        <c:out value="${time}" />
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="days">

            <fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
                <legend>Days:</legend>
                <input type="checkbox" class="qs-day" name="sun-schedule" id="sun-schedule" value="Sun">
                <label for="sun-schedule" class="qs-btn">Sun</label>
                <input type="checkbox" class="qs-day" name="mon-schedule" id="mon-schedule" value="Mon">
                <label for="mon-schedule">Mon</label>
                <input type="checkbox" class="qs-day" name="tue-schedule" id="tue-schedule" value="Tue">
                <label for="tue-schedule">Tue</label>
                <input type="checkbox" class="qs-day" name="wed-schedule" id="wed-schedule" value="Wed">
                <label for="wed-schedule">Wed</label>
                <input type="checkbox" class="qs-day" name="thu-schedule" id="thu-schedule" value="Thu">
                <label for="thu-schedule">Thu</label>
                <input type="checkbox" class="qs-day" name="fri-schedule" id="fri-schedule" value="Fri">
                <label for="fri-schedule">Fri</label>
                <input type="checkbox" class="qs-day" name="sat-schedule" id="sat-schedule" value="Sat">
                <label for="sat-schedule">Sat</label>
            </fieldset>
            <label for="qsFrequency">Every (minutes):</label>
                <select name="qsFrequency" id="qsFrequency">
                <c:forEach var="frequency" items="${frequency}">
                    <c:choose>
                        <c:when test="${frequency == qsFrequency}"><option selected="selected">
                                ${qsFrequency}</option>
                            </c:when>
                            <c:otherwise>
                            <option value="${frequency}">${frequency}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <label for="qsLength" class="select">Length of Time:</label>
            <select name="qsLength" id="qsLength">
                <c:forEach var="length" items="${length}">
                    <c:choose>
                        <c:when test="${length == qsLength}"><option selected="selected">
                                ${qsLength}</option>
                            </c:when>
                            <c:otherwise>
                            <option value="${length}">${length}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <label for="qs-date">Starting Date:</label>
            <input type="date" data-inline="false"id="qs-date" 
                   placeholder="enter starting date">
        </div>
        <button id="qs-submit" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all">
            Submit Schedule</button>
        <!-- ================= Clear Schedule Options ================= -->
        <h3 class="qs-h3-1">Clear Schedule</h3>
        <div id="clearMessages">
            <%--clear schedule client-side validation error messages--%>
            <div id="messagesClear">
                <div id="validationErrorClr" class="error smallText"></div>
                <div id="clearScheduleMessages"class="error smallText"></div>
            </div>

            <%--clear schedule server-side validation error messages--%>
            <c:if test="${!empty validationClrErrorFlag}">
                <span class="error smallText">Please provide valid entries for the following field(s):</span><br>
                <ul>
                    <c:if test="${!empty clrDateError}">
                        <li>Please select a <strong>date to clear</strong>.</li>
                        </c:if>
                        <c:if test="${!empty frDateError}">
                        <li>Please select a valid <strong>from date</strong>.</li>
                        </c:if>
                        <c:if test="${!empty toDateError}">
                        <li>Please enter a valid <strong>to date</strong>.</li>
                        </c:if>
                </ul>
            </c:if>
            <c:if test="${!empty clrErrorMessage}">
                <span class="error smallText">${clrErrorMessage}</span><br>
            </c:if>
            <c:if test="${!empty clrAllMessage}">
                <span class="good smallText">${clrAllMessage}</span><br>
            </c:if>
            <c:if test="${!empty clrMessage}">
                <span class="good smallText">${clrMessage}<strong> <fmt:formatDate value="${date}"
                                                      type="date"
                                                      dateStyle="full"/></strong>.</span><br>
                    </c:if>
                    <c:if test="${!empty clrRangeMessage}">
                <span class="good smallText">${clrRangeMessage}&nbsp;<strong>
                        <fmt:formatDate
                            value="${newFrDate}"
                            type="date"
                            pattern="E"/>
                        <fmt:formatDate
                            value="${newFrDate}"
                            type="date"
                            dateStyle="medium"/></strong>&nbsp;to<strong>
                        <fmt:formatDate
                            value="${newToDate}"
                            type="date"
                            pattern="E"/>
                        <fmt:formatDate value="${newToDate}"
                                        type="date"
                                            dateStyle="medium"/></strong></span><br>
                    </c:if>
        </div>

        <fieldset data-role="controlgroup" id="clear-options">
                    <c:if test="${!empty clearAllCheckbox}">
                <input class="clearSchedule" type="radio" name="clear" id="clearAll" checked value="clearAll">
                <label for="clearAll">clear entire schedule</label>
            </c:if>
                    <c:if test="${empty clearAllCheckbox}">
                <input class="clearSchedule" type="radio" title="clear "name="clear" id="clearAll" value="clearAll">
                <label for="clearAll">clear entire schedule</label>
            </c:if>
                    <c:if test="${!empty clearDateCheckbox}">
                <input class="clearSchedule" type="radio" name="clear" id="clearDate" checked value="clearDate">
                <label for="clearDate">clear one day</label>
            </c:if>
            <c:if test="${empty clearDateCheckbox}">
                <input class="clearSchedule" type="radio" name="clear" id="clearDate" value="clearDate">
                <label for="clearDate">clear one day</label>
            </c:if>
            <input type="date" data-inline="false"  id="clearone-day" 
                   placeholder="select a date to clear&nbsp;ex.<fmt:formatDate value="${todaysDate}"
                                   pattern="MM/dd/yyyy"/>" value="${eDate}">

                    <c:if test="${!empty clearRangeCheckbox}">
                <input class="clearSchedule" type="radio" name="clear" id="clearRange"  checked value="clearRange">
                <label for="clearRange">clear date range (From-To)</label>
            </c:if>
            <c:if test="${empty clearRangeCheckbox}">
                <input class="clearSchedule" type="radio" name="clear" id="clearRange"   value="clearRange">
                <label for="clearRange">clear date range (From-To)</label>
            </c:if>
            <input type="date" id="from"placeholder="select FROM date&nbsp;ex.<fmt:formatDate value="${todaysDate}"
                            pattern="MM/dd/yyyy" />" name="from" value="${from}" >
            <label for="from">from:</label>
            <input type="date" id="to"placeholder="select TO date&nbsp;ex.<fmt:formatDate value="${todaysDate}"
                            pattern="MM/dd/yyyy" />" name="to" value="${to}" >
            <label for="to">to:</label>
        </fieldset>
        <button id="clear-submit" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all">
            Clear Schedule</button>
    </div>
    <!-- ================= new scheduler confirmation popup page ================= -->
    <div data-role="popup" id="scheduler-confirmpage" data-overlay-theme="b" ui-shadow ui-btn-inline>
        <div data-role="header" data-theme="b">
            <h1>Scheduler</h1>
        </div>
        <h3>Confirm Request</h3>
        <form id="submitQS" action="<c:url value='/mobile/m_associateScheduler' />" method="post" data-ajax="false">
            <ul></ul>
            <p></p>
            <div role="main" class="ui-content">
                <a href="#" id="confirm-back-btn" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a
                   ui-icon-back ui-btn-icon-left" data-rel="back">Back</a>
            </div>
            <button type="submit" class="submit ui-corner-all ui-shadow ui-btn-a
                    ui-btn-icon-check ui-btn-icon-left">Submit</button>
        </form>
    </div> <!-- end qs-confirmpage -->
    <!-- ================= scheduler entry errors popup page ================= -->
    <div data-role="popup" id="qs-errorpage" data-overlay-theme="b" ui-shadow ui-btn-inline>
        <div data-role="header" data-theme="b">
            <h1>Error Check</h1>
        </div>
        <h3>Check these Fields</h3>
        <ul></ul>
        <div role="main" class="ui-content">
            <a href="#" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a" data-rel="back">OK</a>
        </div>
    </div> <!-- end scheduler entry errors -->
    <!-- ================= clear scheduler confirmation popup page ================= -->
    <div data-role="popup" id="clear-scheduler" data-overlay-theme="b" ui-shadow ui-btn-inline>
        <div data-role="header" data-theme="b">
            <h1>Clear Schedule</h1>
        </div>
        <h3>Confirm Request</h3>
        <h4></h4>
        <form id="submitClear" action="<c:url value='/mobile/m_clearScheduler' />" method="post" data-ajax="false">
            <ul></ul>
            <p></p>
            <div role="main" class="ui-content">
                <a href="#" id="confirm-back-btn" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a
                   ui-icon-back ui-btn-icon-left" data-rel="back">Back</a>
            </div>
            <button type="submit" class="submit ui-corner-all ui-shadow ui-btn-a
                    ui-btn-icon-check ui-btn-icon-left" id="clr-submit">Submit</button>
        </form>
    </div> <!-- end clear-scheduler -->
</div>  <!-- scheduler -->

</body>
</html>
