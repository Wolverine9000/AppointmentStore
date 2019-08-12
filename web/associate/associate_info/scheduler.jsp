<%-- 
    Document   : scheduler
    Created on : Aug 13, 2012, 3:20:01 PM
    Author     : williamdobbs
--%>
<%-- asociateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>

<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_scheduler.css">
<script src="../js/associateJS/associate_schedule.js" type="text/javascript"></script>

<div id="main-section">
    <aside>
        <div id="aside-div">
            <img src="${initParam.associateImagePath}${associateRecord.id}.png"
                 alt="${associateRecord.id}"  id="associateImg" alt="associate picture">
            <div class="smallText" id="associateFirstName">${associateRecord.firstName}</div>

            <h3><a href="<c:url value='calendarAssociate'/>">Calendar</a></h3>

            <h3><a href="<c:url value='scheduler'/>"><strong>Scheduler</strong></a></h3>

            <h3><a href="<c:url value='associate_clients'/>">Clients</a></h3>

            <h3><a href="<c:url value='associateSettings'/>">Settings</a></h3>

            <h3><a href="<c:url value='associate_messages'/>">Messages</a></h3>

            <h3><a href="<c:url value='associate_services'/>">Services</a></h3>

            <h3><a href="<c:url value='#'/>">Account</a></h3>

            <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
        </div>
    </aside>
    <section id="section1">
        <div id="identifier"><h1>Scheduler</h1></div>
        <input type="hidden" id="reloadTab" name="reloadTab" value="${reloadTab}"/>
        <div id="schedulertabs">
            <ul>
                <li><a href="#tab-quickSchedule">Quick</a></li>
                <li><a href="#tab-mainSchedule">Main</a></li>
                <li><a href="#tab-clearSchedule">Clear</a></li>
            </ul>
            <div id="tab-quickSchedule">
                <%--Quick Schedule Box--%>
                <fieldset id="weeklyFieldset">
                    <legend>Quick Schedule</legend>
                    <form class="quickScheduleRequest" action="processQuickSchedule" method="post">
                        <input type="hidden" name="currentTab" value="0"/>
                        <table class="weekly" id="shadowBox">
                            <thead>
                                <tr><th></th>
                                    <th>
                                        <%--Sunday Checkbox--%>
                                        <label for="sun1">Sun</label>
                                        <c:if test="${!empty sunCheckbox}">
                                            <input type="checkbox" name="day" id ="sun1"  checked value="Sun">
                                        </c:if>
                                        <c:if test="${empty sunCheckbox}">
                                            <input class="quickSchedule" type="checkbox" name="day" id ="sun1"  value="Sun">
                                        </c:if>
                                        <%--Monday Checkbox--%>
                                        <label for="mon1"> Mon</label>
                                        <c:if test="${!empty monCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="mon1" checked value="Mon">
                                        </c:if>
                                        <c:if test="${empty monCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="mon1" value="Mon">
                                        </c:if>
                                        <%--Tuesday Checkbox--%>
                                        <label for="tue1"> Tue</label>
                                        <c:if test="${!empty tueCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="tue1" checked value="Tue">
                                        </c:if>
                                        <c:if test="${empty tueCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="tue1" value="Tue">
                                        </c:if>
                                        <%--Wednesday Checkbox--%>
                                        <label for="wed1">Wed</label>
                                        <c:if test="${!empty wedCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="wed1" checked value="Wed">
                                        </c:if>
                                        <c:if test="${empty wedCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="wed1" value="Wed">
                                        </c:if>
                                        <%--Thursday Checkbox--%>
                                        <label for="thu1">Thu</label>
                                        <c:if test="${!empty thuCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="thu1" checked value="Thu">
                                        </c:if>
                                        <c:if test="${empty thuCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="thu1" value="Thu">
                                        </c:if>
                                        <%--Friday Checkbox--%>
                                        <label for="fri1">Fri</label>
                                        <c:if test="${!empty friCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="fri1" checked value="Fri">
                                        </c:if>
                                        <c:if test="${empty friCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="fri1" value="Fri">
                                        </c:if>
                                        <%--Saturday Checkbox--%>
                                        <label for="sat1">Sat</label>
                                        <c:if test="${!empty satCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="sat1" checked value="Sat">
                                        </c:if>
                                        <c:if test="${empty satCheckbox}">
                                            <INPUT class="quickSchedule" type="checkbox" name="day" id ="sat1" value="Sat">
                                        </c:if>    
                                    </th>
                                </tr>
                            </thead>
                            <%--Quick Schedule Selection Times--%>
                            <tbody>
                                <tr><td>quick schedule</td>
                                    <td>
                                        in:&nbsp;<select name="qsStart" id="qsStart">
                                            <c:if test="${!empty qsStart}">
                                                <c:forEach var="times" items="${times}">
                                                    <c:choose>
                                                        <c:when test="${times == qsStart}"><option selected="selected">
                                                                ${qsStart}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <option value="${times}">${times}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${empty qsStart}">
                                                <c:forEach var="times" items="${times}">
                                                    <c:choose>
                                                        <c:when test="${times == defaultStartTime}">
                                                            <option selected="selected" value="${times}">${times}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${times}">${times}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                        out:&nbsp;<select name="qsEnd" id="qsEnd">
                                            <c:if test="${!empty qsEnd}">
                                                <c:forEach var="times" items="${times}">
                                                    <c:choose>
                                                        <c:when test="${times == qsStart}"><option selected="selected">
                                                                ${qsEnd}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <option value="${times}">${times}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${empty qsEnd}">
                                                <c:forEach var="times" items="${times}">
                                                    <c:choose>
                                                        <c:when test="${times == defaultEndTime}">
                                                            <option selected="selected" value="${times}">${times}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${times}">${times}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                        &nbsp;every:&nbsp;<select name="qsFrequency" id="qsFrequency">
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
                                        </select>min
                                        &nbsp;
                                        &nbsp;<select name="qsLength" id="qsLength">
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
                                        &nbsp;start date:&nbsp;<input 
                                            name="qsDate" 
                                            size="11" 
                                            type="text" 
                                            title="Please enter a Future Date"
                                            placeholder=""
                                            value="${qsDate}"
                                            id="datepicker">
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                        <legend class="submitLegend"></legend>
                        <%--client-side validation error messages--%>
                        <div id="messagesQS">
                            <div id="validationQSError" class="error smallText "></div>
                            <div id="quickScheduleMessages" class="error smallText "></div>
                        </div>
                        <%--server-side validation error messages--%>
                        <c:if test="${!empty validationErrorFlag}">
                            <span class="error smallText">Please provide valid entries for the following field(s):
                                <c:if test="${!empty startTimeError}">
                                    <br><span>&bull;&nbsp(<strong>time in</strong>)&nbsp;&nbsp;Please select a <strong>time in</strong>.</span>
                                </c:if>
                                <c:if test="${!empty endTimeError}">
                                    <br><span>&bull;&nbsp(<strong>time out</strong>)&nbsp;&nbsp;Please select a <strong>time out</strong>.</span>
                                </c:if>
                                <c:if test="${!empty dateError}">
                                    <br><span>&bull;&nbsp(<strong>starting date</strong>)&nbsp;&nbsp;Please enter a valid <strong>starting date</strong>.</span>
                                </c:if>
                                <c:if test="${!empty hourError}">
                                    <br><span>&bull;&nbsp(<strong>time in &AMP; time out</strong>)&nbsp;&nbsp;Make sure <strong>time out</strong> 
                                        is greater than the selected&nbsp;<strong>time  in</strong>.</span>
                                    </c:if>
                                    <c:if test="${!empty daysError}">
                                    <br><span>&bull;&nbsp(<strong>days</strong>)&nbsp;&nbsp;Select at least one of the <strong>Day</strong> check boxes from above.</span>
                                </c:if>
                            </c:if>
                            <c:if test="${!empty errorQSMessage}">
                                <span class="error smallText">${errorQSMessage}</span><br>
                            </c:if>
                            <c:if test="${!empty message}">
                                <span class="good smallText">${message}<br>Your Quick Schedule request for time in: <strong>${qsStart}</strong> time out: <strong>${qsEnd}</strong>
                                    accepting appointments every <strong>:${qsFrequency}</strong> minutes for <strong>${qsLength}</strong> starting on
                                    <strong><fmt:formatDate value="${dateQS}"
                                                    type="date"
                                                    dateStyle="full"/></strong> <c:if test="${!empty days}">on days
                                            <c:forEach var="day" items="${days}">
                                            <strong>${fn:toUpperCase(day)}</strong>,
                                        </c:forEach>
                                    </c:if>is now entered into your schedule.</span><br>
                                </c:if>
                        </span>
                        <button type="submit" class="submit"></button>
                    </form>
                </fieldset>

            </div>
            <div id="tab-mainSchedule">
                <form class="mainSchedule" action="<c:url value='processNormalServlet'/>" method="post">
                    <fieldset id="globalFieldset">
                        <%--action="globalSettings" onclick="submit();" method="post"--%>
                        <legend>Quick Settings</legend>
                        <div id="globalMain">
                            <table class="global" id="shadowBox">
                                <thead>
                                    <tr><th></th>
                                        <th>
                                            <label for="everyday">Every Day</label>
                                            <c:if test="${!empty everyday}">
                                                <input class="tableInput" type="radio" name="global" id ="everyday" checked value="everyday">
                                            </c:if>
                                            <c:if test="${empty everyday}">
                                                <input class="tableInput" type="radio" name="global" id ="everyday" value="everyday">
                                            </c:if>
                                            <label for="weekdays">Mon - Fri</label>
                                            <c:if test="${!empty weekdays}">
                                                <input class="tableInput" type="radio" name="global" id ="weekdays" checked value="weekdays">
                                            </c:if>
                                            <c:if test="${empty weekdays}">
                                                <input class="tableInput" type="radio" name="global" id ="weekdays" value="weekdays">
                                            </c:if>
                                            <label for="weekends">Sat &AMP; Sun</label>
                                            <c:if test="${!empty weekends}">
                                                <input class="tableInput" type="radio" name="global" id ="weekends" checked value="weekends">
                                            </c:if>
                                            <c:if test="${empty weekends}">
                                                <input class="tableInput" type="radio" name="global" id ="weekends" value="weekends">
                                            </c:if>
                                            <label for="tueToSat">Tue - Sat</label>
                                            <c:if test="${!empty tueToSat}">
                                                <input class="tableInput" type="radio" name="global" id ="tueToSat" checked value="tueToSat">
                                            </c:if>
                                            <c:if test="${empty tueToSat}">
                                                <input class="tableInput" type="radio" name="global" id ="tueToSat" value="tueToSat">
                                            </c:if>
                                            <br> <div class="timetitle">time in&nbsp;&nbsp; - &nbsp;&nbsp;time out</div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <tr><td>morning (or all day)</td>
                                        <td>
                                            <select name="gloMT1" class="gloMT1">
                                                <c:if test="${!empty gloMT1}">
                                                    <c:forEach var="times" items="${times}">
                                                        <c:choose>
                                                            <c:when test="${times == gloMT1}"><option selected="selected">
                                                                    ${gloMT1}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <option value="${times}">${times}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${empty gloMT1}">
                                                    <c:forEach var="times" items="${times}">
                                                        <c:choose>
                                                            <c:when test="${times == defaultStartTime}">
                                                                <option selected="selected" value="${times}">${times}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${times}">${times}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </c:if>
                                            </select>
                                            <select name="gloMT2" class="gloMT2">
                                                <c:if test="${!empty gloMT2}">
                                                    <c:forEach var="times" items="${times}">
                                                        <c:choose>
                                                            <c:when test="${times == gloMT1}"><option selected="selected">
                                                                    ${gloMT2}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                <option value="${times}">${times}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${empty gloMT2}">
                                                    <c:forEach var="times" items="${times}">
                                                        <c:choose>
                                                            <c:when test="${times == defaultEndTime}">
                                                                <option selected="selected" value="${times}">${times}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${times}">${times}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </c:if>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr><td>afternoon</td>
                                        <td>
                                            <select name="gloAT1" class="gloAT1">
                                                <c:forEach var="times" items="${times}">
                                                    <c:choose>
                                                        <c:when test="${times == gloAT1}"><option selected="selected">
                                                                ${gloAT1}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <option value="${times}">${times}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                            <select name="gloAT2" class="gloAT2">
                                                <c:forEach var="times" items="${times}">
                                                    <c:choose>
                                                        <c:when test="${times == gloAT2}"><option selected="selected">
                                                                ${gloAT2}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <option value="${times}">${times}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                            </form>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </fieldset>


                    <input type="hidden" name="currentTab" value="1"/>
                    <fieldset id="table1Fieldset">
                        <legend>Main Schedule</legend>

                        <table class="table1" id="shadowBox">
                            <thead>
                                <tr><th></th><th></th>
                                        <%--Sunday Checkbox--%>
                                    <th><c:if test="${!empty sunNorCheckbox}">
                                            <label for="sun">Sun</label>
                                            <input class="tableInputMain" type="checkbox" name="nDay" id ="sun"  checked value="sun">
                                        </c:if>
                                        <c:if test="${empty sunNorCheckbox}">
                                            <label for="sun">Sun</label>
                                            <input class="tableInputMain" type="checkbox" name="nDay" id ="sun"  value="sun">
                                        </c:if>
                                    </th>
                                    <%--Monday Checkbox--%>
                                    <th><c:if test="${!empty monNorCheckbox}">
                                            <label for="mon">Mon</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="mon" checked value="mon">
                                        </c:if>
                                        <c:if test="${empty monNorCheckbox}">
                                            <label for="mon">Mon</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="mon" value="mon">
                                        </c:if>
                                    </th>
                                    <%--Tuesday Checkbox--%>
                                    <th><c:if test="${!empty tueNorCheckbox}">
                                            <label for="tue">Tue</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="tue" checked value="tue">
                                        </c:if>
                                        <c:if test="${empty tueNorCheckbox}">
                                            <label for="tue">Tue</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="tue" value="tue">
                                        </c:if>
                                    </th>
                                    <%--Wednesday Checkbox--%>
                                    <th><c:if test="${!empty wedNorCheckbox}">
                                            <label for="wed">Wed</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="wed" checked value="wed">
                                        </c:if>
                                        <c:if test="${empty wedNorCheckbox}">
                                            <label for="wed">Wed</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="wed" value="wed">
                                        </c:if>
                                    </th>
                                    <%--Thursday Checkbox--%>
                                    <th><c:if test="${!empty thuNorCheckbox}">
                                            <label for="thu">Thu</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="thu" checked value="thu">
                                        </c:if>
                                        <c:if test="${empty thuNorCheckbox}">
                                            <label for="thu">Thu</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="thu" value="thu">
                                        </c:if>
                                    </th>
                                    <%--Friday Checkbox--%>
                                    <th><c:if test="${!empty friNorCheckbox}">
                                            <label for="fri">Fri</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="fri" checked value="fri">
                                        </c:if>
                                        <c:if test="${empty friNorCheckbox}">
                                            <label for="fri">Fri</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="fri" value="fri">
                                        </c:if>
                                    </th>
                                    <%--Saturday Checkbox--%>
                                    <th><c:if test="${!empty satNorCheckbox}">
                                            <label for="sat">Sat</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="sat" checked value="sat">
                                        </c:if>
                                        <c:if test="${empty satNorCheckbox}">
                                            <label for="sat">Sat</label>
                                            <INPUT class="tableInputMain" type="checkbox" name="nDay" id ="sat" value="sat">
                                        </c:if>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>morning (or all day)</td>
                                    <td>in:<br>out:</td>
                                    <td>
                                        <select name="sunMT1" class="sunMT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == sunMT1}"><option selected="selected">
                                                            ${sunMT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>                                        
                                        <select name="sunMT2" class="sunMT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == sunMT2}"><option selected="selected">
                                                            ${sunMT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><select name="monMT1" class="monMT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == monMT1}"><option selected="selected">
                                                            ${monMT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="monMT2" class="monMT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == monMT2}"><option selected="selected">
                                                            ${monMT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><select name="tueMT1" class="tueMT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == tueMT1}"><option selected="selected">
                                                            ${tueMT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="tueMT2" class="tueMT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == tueMT2}"><option selected="selected">
                                                            ${tueMT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><select name="wedMT1" class="wedMT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == wedMT1}"><option selected="selected">
                                                            ${wedMT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="wedMT2" class="wedMT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == wedMT2}"><option selected="selected">
                                                            ${wedMT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><select name="thuMT1" class="thuMT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == thuMT1}"><option selected="selected">
                                                            ${thuMT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="thuMT2" class="thuMT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == thuMT2}"><option selected="selected">
                                                            ${thuMT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><select name="friMT1" class="friMT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == friMT1}"><option selected="selected">
                                                            ${friMT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="friMT2" class="friMT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == friMT2}"><option selected="selected">
                                                            ${friMT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><select name="satMT1" class="satMT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == satMT1}"><option selected="selected">
                                                            ${satMT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="satMT2" class="satMT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == satMT2}"><option selected="selected">
                                                            ${satMT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr> <%--afternoon times--%>
                                    <td>afternoon</td>
                                    <td>in:<br>out:</td>
                                    <td><select name="sunAT1"class="sunAT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == sunAT1}"><option selected="selected">
                                                            ${sunAT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="sunAT2"class="sunAT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == sunAT2}"><option selected="selected">
                                                            ${sunAT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="monAT1"class="monAT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == monAT1}"><option selected="selected">
                                                            ${monAT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="monAT2"class="monAT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == monAT2}"><option selected="selected">
                                                            ${monAT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="tueAT1"class="tueAT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == tueAT1}"><option selected="selected">
                                                            ${tueAT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="tueAT2"class="tueAT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == tueAT2}"><option selected="selected">
                                                            ${tueAT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><select name="wedAT1"class="wedAT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == wedAT1}"><option selected="selected">
                                                            ${wedAT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="wedAT2"class="wedAT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == wedAT2}"><option selected="selected">
                                                            ${wedAT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="thuAT1"class="thuAT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == thuAT1}"><option selected="selected">
                                                            ${thuAT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="thuAT2"class="thuAT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == thuAT2}"><option selected="selected">
                                                            ${thuAT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="friAT1"class="friAT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == friAT1}"><option selected="selected">
                                                            ${friAT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="friAT2"class="friAT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == friAT2}"><option selected="selected">
                                                            ${friAT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="satAT1"class="satAT1">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == satAT1}"><option selected="selected">
                                                            ${satAT1}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <select name="satAT2"class="satAT2">
                                            <c:forEach var="times" items="${times}">
                                                <c:choose>
                                                    <c:when test="${times == satAT2}"><option selected="selected">
                                                            ${satAT2}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${times}">${times}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="9">accept appointments
                                        every:
                                        <select name="frequencyN" id="frequencyN">
                                            <c:forEach var="frequency" items="${frequency}">
                                                <c:choose>
                                                    <c:when test="${frequency == frequencyN}"><option selected="selected">
                                                            ${frequencyN}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <option value="${frequency}">${frequency}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        minutes
                                        &nbsp&nbsp;
                                        &nbsp;
                                        <label for="fromN">from date:</label>
                                        <input class="mainInput" size="11" type="text" id="fromN"
                                               title="Enter a Starting From Date"
                                               placeholder="" 
                                               name="fromN" value="${fromN}"/>
                                        <label for="toN">to date:</label>
                                        <input class="mainInput" size="11" type="text" id="toN" 
                                               title="Enter a Ending To Date"
                                               placeholder="" 
                                               name="toN" value="${toN}"/>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                        <legend class="submitLegend"></legend>

                        <%--Client-Side Validation Error Messages--%>
                        <div id="messagesMain">
                            <div id="validationMSError" class="error smallText"></div>
                            <div id="validationMSmessages" class="error smallText"></div>
                        </div>

                        <%--Server-Side Validation Error Messages--%>
                        <c:if test="${!empty validationDaysErrorFlag}">
                            <span class="error smallText">Please provide valid entries for the following field(s):
                                <br><span>(<strong>days</strong>)&nbsp;&nbsp;Select at least one of the <strong>Day</strong> check boxes from above.</span><br>
                            </c:if>
                            <c:if test="${!empty validationErrorFlagSun || !empty validationErrorFlagMon || !empty validationErrorFlagTue || !empty validationErrorFlagWed
                                          || !empty validationErrorFlagThu || !empty validationErrorFlagFri || !empty validationErrorFlagSat}">
                                  <span class="error smallText">Please check these selected Day(s) 
                                      <c:if test="${!empty validationErrorFlagSun}"><strong>Sun,</strong></c:if>
                                      <c:if test="${!empty validationErrorFlagMon}"><strong>Mon,</strong></c:if>
                                      <c:if test="${!empty validationErrorFlagTue}"><strong>Tue,</strong></c:if>
                                      <c:if test="${!empty validationErrorFlagWed}"><strong>Wed,</strong></c:if>
                                      <c:if test="${!empty validationErrorFlagThu}"><strong>Thu,</strong></c:if>
                                      <c:if test="${!empty validationErrorFlagFri}"><strong>Fri,</strong></c:if>
                                      <c:if test="${!empty validationErrorFlagSat}"><strong>Sat,</strong></c:if>
                                          and provide valid entries for the following Field(s):
                                      <c:if test="${!empty startTimeError}">
                                          <br><span>(<strong>time in</strong>)&nbsp;&nbsp;Please select a <strong>time in</strong>.</span>
                                      </c:if>
                                      <c:if test="${!empty endTimeError}">
                                          <br><span>(<strong>time out</strong>)&nbsp;&nbsp;Please select a <strong>time out</strong>.</span>
                                      </c:if>
                                      <c:if test="${!empty dateError}">
                                          <br><span>(<strong>from date</strong>)&nbsp;&nbsp;Please enter a valid starting <strong>from date</strong>.</span>
                                      </c:if>
                                      <c:if test="${!empty toDateError}">
                                          <br><span>(<strong>to date</strong>)&nbsp;&nbsp;Please enter a valid starting <strong>to date</strong>.</span>
                                      </c:if>
                                      <c:if test="${!empty lessThanYearError}">
                                          <br><span>(<strong>date</strong>)&nbsp;&nbsp;Date must be less than one year from today&#39;s&nbsp;date.
                                              &nbsp;Please enter a valid <strong>date</strong>.</span>
                                          </c:if>
                                          <c:if test="${!empty hourError}">
                                          <br><span>(<strong>morning time in &AMP; time out</strong>)&nbsp;&nbsp;Make sure <strong>morning time out</strong> 
                                              is greater than the selected&nbsp;<strong>morning time  in</strong>.</span><br>
                                          </c:if>
                                          <c:if test="${!empty daysError}">
                                          <br><span>(<strong>days</strong>)&nbsp;&nbsp;Select your desired <strong>Day(s)</strong> check boxes from above.</span>
                                      </c:if><c:if test="${!empty noonTime1Error}">
                                          <br><span>(<strong>afternoon time in</strong>)&nbsp;&nbsp;Please select a <strong>afternoon time in</strong>.</span>
                                      </c:if>
                                      <c:if test="${!empty noonTime2Error}">
                                          <br><span>(<strong>afternoon time out</strong>)&nbsp;&nbsp;Please select a <strong>time out</strong>.</span>
                                      </c:if>
                                      <c:if test="${!empty noonHourError}">
                                          <br><span>(<strong>afternoon time in &AMP; time out</strong>)&nbsp;&nbsp;Make sure <strong>afternoon time out</strong> 
                                              is greater than the selected&nbsp;<strong>afternoon time  in</strong> and also that the <strong>afternoon time in</strong> 
                                              is greater than the <strong>morning time out</strong>.</span>
                                          </c:if>
                                      </c:if>
                            </span>
                            <div>
                                <c:if test="${!empty errorNorMessage}">
                                    <span class="error smallText">${errorNorMessage}<c:if test="${!empty noonHourError}"><span class="error smallText"> however, Afternoon Time failed.
                                                Check your Afternoon Times.
                                            </span></span>
                                        </c:if></c:if>
                                </div>
                                <div>
                                <c:if test="${!empty messageN}">
                                    <span class="good smallText">${messageN}
                                        <br>Accepting appointments every <strong>:${frequencyN}</strong> minutes from
                                        <strong><fmt:formatDate value="${dateFrom}"
                                                        type="date"
                                                        dateStyle="medium"/></strong> to<strong>
                                            <fmt:formatDate value="${dateTo}"
                                                            type="date"
                                                            dateStyle="medium"/>
                                        </strong><c:if test="${!empty nDay}"> on 
                                            <c:forEach var="day" items="${nDay}">
                                                <strong>${fn:toUpperCase(day)}</strong>,
                                            </c:forEach>
                                        </c:if>is now entered into your schedule.</span>
                                    </c:if>
                            </div>
                            <button type="submit" class="submit"></button>
                    </fieldset>
                </form>

            </div>
            <div id="tab-clearSchedule">
                <%--Clear Schedule Optons Box--%>
                <form id="clearSchedule" action="<c:url value='clearSchedule'/>" method="post">
                    <input type="hidden" name="currentTab" value="2"/>
                    <fieldset id="schedulerBox">
                        <legend>Clear Schedule Options</legend>
                        <div id="clear-box">
                            <%--clear schedule client-side validation error messages--%>
                            <div id="messagesClear">
                                <div id="validationErrorClr" class="error smallText"></div>
                                <div id="clearScheduleMessages"class="error smallText"></div>
                            </div>

                            <%--clear schedule server-side validation error messages--%>
                            <c:if test="${!empty validationClrErrorFlag}">
                                <span class="error smallText">Please provide valid entries for the following field(s):
                                    <c:if test="${!empty clrDateError}">
                                        <br><span>&bull;&nbsp(<strong>clear date</strong>)&nbsp;&nbsp;Please select a <strong>date to clear</strong>.</span>
                                    </c:if>
                                    <c:if test="${!empty frDateError}">
                                        <br><span>&bull;&nbsp(<strong>from date</strong>)&nbsp;&nbsp;Please select a valid <strong>from date</strong>.</span>
                                    </c:if>
                                    <c:if test="${!empty toDateError}">
                                        <br><span>&bull;&nbsp(<strong>to date</strong>)&nbsp;&nbsp;Please enter a valid <strong>to date</strong>.</span>
                                    </c:if>
                                </span><br>
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
                                        <fmt:formatDate value="${newFrDate}"
                                                        type="date"
                                                        dateStyle="medium"/></strong>&nbsp;to<strong>
                                        <fmt:formatDate
                                            value="${newToDate}"
                                            type="date"
                                            pattern="E"/>
                                        <fmt:formatDate value="${newToDate}"
                                                        type="date"
                                                            dateStyle="medium"/></strong>.</span><br>
                                    </c:if>
                                    <c:if test="${!empty clearAllCheckbox}">
                                <input class="schedulerInput" type="radio" name="clear" id="clearAll" checked value="clearAll">
                                <label for="clearAll">clear entire schedule</label>
                            </c:if>
                            <c:if test="${empty clearAllCheckbox}">
                                <input class="schedulerInput" type="radio" title="clear "name="clear" id="clearAll" value="clearAll">
                                <label for="clearAll">clear entire schedule</label>
                            </c:if>
                            <br>
                            <c:if test="${!empty clearDateCheckbox}">
                                <input class="schedulerInput" type="radio" name="clear" id="clearDate" checked value="clearDate">
                                <label for="clearDate">clear one day</label>
                            </c:if>
                            <c:if test="${empty clearDateCheckbox}">
                                <input class="schedulerInput" type="radio" name="clear" id="clearDate" value="clearDate">
                                <label for="clearDate">clear one day</label>
                            </c:if>
                            &nbsp;&nbsp;&nbsp;
                            date:<input class="schedulerInput" name="eDate" size="11" type="text" 
                                        title="Enter a  Date" placeholder="<fmt:formatDate value="${todaysDate}"
                                                        pattern="MM/dd/yyyy" />" value="${eDate}" id="datepicker3">
                            <br>
                            <c:if test="${!empty clearRangeCheckbox}">
                                <input class="schedulerInput" type="radio" name="clear" id="clearRange"  checked value="clearRange">
                                <label for="clearRange">clear date range</label>
                            </c:if>
                            <c:if test="${empty clearRangeCheckbox}">
                                <input class="schedulerInput" type="radio" name="clear" id="clearRange"   value="clearRange">
                                <label for="clearRange">clear date range</label>
                            </c:if>
                            <br>
                            <div class="alignCenter"><label for="from">from:</label>
                                <input class="schedulerInput" size="11" type="text" id="from" 
                                       title="Clear Starting From Date" placeholder="<fmt:formatDate value="${todaysDate}"
                                                       pattern="MM/dd/yyyy" />" name="from" value="${from}"/>
                                <label for="to">to:</label>
                                <input class="schedulerInput" size="11" type="text" id="to" 
                                       title="Clear Ending To Date" placeholder="<fmt:formatDate value="${todaysDate}" 
                                                       pattern="MM/dd/yyyy" />" name="to" value="${to}"/>
                            </div>
                            <button type="submit" class="submit"></button>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>

        <div id="confirmSchedule" title="Confirm Your Request">
            <div class="eventHeader"></div>
            <table class="schedulerInfoTable">
                <tbody>
                </tbody>
            </table>
        </div>
    </section>
</div>

