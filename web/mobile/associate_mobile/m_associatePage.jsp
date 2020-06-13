<script src="../js/associateJSmobile/associateMobile_calendar.js" type="text/javascript"></script>
<%-- 
    Document   : m_associatePage
    Created on : Aug 5, 2014, 2:41:49 PM
    Author     : williamdobbs
--%>
<%-- asociateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>
<input type="hidden" name="associateView" id="associateView" value="${associateRecord.defaultCalendarView}" >
<!-- ================= mainpage page ================= -->
<div data-role="page" id="mainpage" date-title="Calendar">
    <div data-role="panel" id="panel" data-theme="b">
        <h2>Main Menu</h2>
        <ul data-role="listview">
            <li><a href="#mainpage" data-transition="slide">Calendar</a></li>
            <li><a href="<c:url value='/mobile/m_associateScheduler.jsp' /> "data-ajax="false"  data-transition="slide">Scheduler</a></li>
            <li><a href="<c:url value='/mobile/m_associateClients.jsp' /> "data-ajax="false" data-transition="slide">Clients</a></li>
            <li><a href="<c:url value='/mobile/m_associateSettings.jsp'/>" data-ajax="false" data-transition="slide">Settings</a></li>
            <li><a href="<c:url value='#' />">Messages</a></li>
            <li><a href="<c:url value='#' />">Account</a></li>
            <li><a href="<c:url value='logoutAssociate'/>" data-transition="fade">Logout</a></li>
        </ul>
    </div>
    <header data-role="header" data-theme="b" data-position="fixed" class="header-logo">
        <a href="#panel" display="reveal" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-bars ui-mini ui-btn-icon-notext">Menu</a>
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-recycle ui-mini ui-btn-icon-notext" data-position="right" id="recycle">recycle</a>
        <h1></h1>
    </header>

    <div data-role="content">
        <section id="container">
            <div id="messages">
                <div id="postDataError" class="error smallText "></div>
                <div id="postDataSuccess" class="good smallText "></div>
            </div> <!-- messages -->
            <!-- spinner div-->
            <div id="loading"></div>
            <div id="datediv"><input type="date" class="date-input ui-btn ui-btn-inline" data-mini="true" id="gotodate" value placeholder="jump to date"></div>
            <select name="calendars" id="showCal" id="select-choice-mini" data-mini="true" data-inline="true" data-native-menu="false">
                    <optgroup label="CALENDAR EVENTS">
                    <option value="0:all">all associates</option>
                </optgroup>
                <optgroup label="ASSOCIATE CALENDARS">
                    <c:forEach var="associate" items="${associatesAll}">
                        <option value="<c:out value='${associate.id}:aa' />"><c:out value="${associate.firstName}" /></option>
                    </c:forEach>
                </optgroup>
                <optgroup label="CURRENT ASSOCIATE CALENDAR">
                    <option value="<c:out value='${associateRecord.id}:ca' />"><c:out value="${associateRecord.firstName}" /></option>
                </optgroup>
                <optgroup label="AVAILABLE TIMES">
                    <c:forEach var="associate" items="${associatesAll}">
                        <option value="<c:out value='${associate.id}:at' />"><c:out value="${associate.firstName}" /></option>
                    </c:forEach>
                </optgroup>
            </select>
            <div id="div-cal-icons">
                <a href="#map-page" id="map-info" data-transition="slide" class=" ui-icon-navigation ui-btn ui-btn-inline ui-btn-icon-notext ui-corner-all cal-icons ">Google Map</a>
                <button id="info-btn" class="ui-btn ui-btn-inline ui-icon-info  ui-btn-icon-notext ui-corner-all cal-icons ">Info</button>
                <!--            <div id="reloadCal">Reload</div>-->
            </div>
        </section>
        <div id="calendar"></div>
        <div data-role="popup" id="popupBasic" data-overlay-theme="b">
            <p></p>
        </div>
    </div> <!--content main page-->
    <!-- ================= summary info popup page ================= -->
    <div data-role="popup" id="info-page" data-overlay-theme="b" ui-shadow ui-btn-inline>
        <div data-role="header" data-theme="b">
            <h1>Today</h1>
        </div>
        <h4 class="ui-title" id="new-event-title"></h4>
        <table class="info-table">
            <tbody></tbody>
        </table>
        <div role="main" class="ui-content">
            <a href="#" id="info-back" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-check ui-btn-icon-left" data-rel="back">OK</a>
        </div>
    </div> <!-- end summary popup page -->
</div> <!-- mainpage -->
<!-- ======================== map page ========================= -->
<div data-role="page" id="map-page" data-url="map-page">
    <header data-role="header" data-theme="b">
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-refresh ui-mini ui-btn-icon-notext" data-position="right" id="reloadCal">Refresh</a>
        <h1>Map</h1>
    </header>
    <div role="main" class="ui-content" id="map-canvas" >
        <!-- map loads here... -->
    </div>
    <div id="message"></div>
</div><!-- end map page -->
<!-- ===================== event page =========================== -->
<div data-role="page" id="event" date-title="Event Info">
    <header data-role="header" data-theme="b" data-position="fixed">
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <h1 id="event-header"></h1>
    </header>

    <div id="eventInfo">
        <table class="eventInfoTable">
            <tbody>
            </tbody>
        </table>
        <fieldset id="statusField">
            <table class="statusTable">
                <thead>
                    <tr>
                        <th>
                            <select name="updateStatus" id="updateStatus">
                                <c:forEach var="status" items="${status}">
                                    <option value="<c:out value='${status.statusId}' />">
                                        <c:out value="${status.statusName}" />
                                    </option>
                                </c:forEach> service status
                            </select>
                        </th>
                    </tr>
                </thead>
            </table>
        </fieldset>
        <a id="delete-appt" href="#popupDialog" data-rel="popup" data-position-to="window" data-transition="pop" class="ui-btn ui-corner-all ui-shadow ui-icon-delete ui-btn-icon-left ui-btn-a delete-appt"></a>
        <div data-role="popup" id="popupDialog" data-overlay-theme="b" data-theme="a" data-dismissible="false" style="max-width:400px;">
            <div data-role="header" data-theme="b">
                <h1>Delete Event</h1>
            </div>
            <div role="main" class="ui-content" id="confirmDelete">
                <h3 class="del-event-ui-title"></h3>
                <p>This action cannot be undone.</p>
                <fieldset data-role="controlgroup" id="del-conrolgroup">
                    <input type="checkbox" name="notifyClient" id="notifyClient" value="true">
                    <label for="notifyClient" >Notify Client</label>
                    <input type="checkbox" name="restoreTime" id="restoreTime" value="true">
                    <label for="restoreTime">Restore Time</label>
                </fieldset>
                <a href="#" id="cancelEvent" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-back  ui-btn-icon-left" data-rel="back">Cancel</a>
                <a href="#" id="yes" class="ui-btn ui-corner-all ui-shadow  ui-btn-a ui-icon-delete ui-btn-icon-left" data-transition="flow">Delete</a>
            </div>
        </div> <!--end delete popupDialog -->
    </div>
</div> <!-- end event page -->
<!-- ======================= newEvent page ============================== -->
<div data-role="page" id="newEvent" date-title="Create New Appointment">
    <header data-role="header" data-theme="b" data-position="fixed">
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <h1>New Event</h1>
    </header>
    <section data-role="content">
        <div class="eventHeader"></div>
        <div id="startend">
            <table class="times">
                <tbody>
                    <tr><th>Start Time</th><th>End Time</th></tr>
                    <tr>
                        <td>
                            <select id="startTime" title="service start time"  data-inline="true">
                                <c:forEach var="time" items="${times}">
                                    <option value="<c:out value='${time}' />">
                                        <c:out value="${time}" />
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td> <input type="text" name="endTime" id="endTime" value="" readonly="true"
                                    title="service end time"></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <section id="newEventForm">
            <div class="validateTips"></div>
            <label for="firstNm">First Name:</label>
            <input type="text" name="firstNm" id="firstNm" placeholder="name, email, id" required>
            <label for="lastNm">Last Name:</label>
            <input type="text" name="lastNm" id="lastNm" required>
            <label for="mobilePhone">Mobile&#35;&#58;</label>
            <input type="tel" name="mobilePhone" id="mobilePhone" placeholder="10 digit phone number"
                   pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number" required>
            <label for="email">Email:</label>
            <input type="email" name="email" id="email">
            <label for="customerId">Client&#35;&#58;</label>
            <input type="text" name="customerId" id="customerId" readonly="true">

            <label for="associate" title="associate to perform service">Associate&#58;</label>
            <select name="associate" id="associate"></select>

            <input type="hidden" id="client-info">

            <label for="normal-service" title="normal-service">Service&#58;</label>
            <select name="normal-service" id="normal-service"></select>

            <label for="notes" title="service notes">Notes:</label>
            <textarea type="text" name="notes" id="notes"  placeholder="100 characters or less" 
                      maxlength="100"></textarea>

            <label for="status" title="appointment current status">Status:</label>
            <select name="status" id="status">
                <c:forEach var="status" items="${status}">
                    <option value="<c:out value='${status.statusId}' />">
                        <c:out value="${status.statusName}" />
                    </option>
                </c:forEach><!-- service status-->
            </select>
            <button id="send" class="ui-btn ui-icon-check ui-btn-icon-left ui-btn ui-corner-all ui-shadow">Submit</button>
            <!-- ================= new event confirmation popup page ================= -->
            <div data-role="popup" id="confirmpage" data-overlay-theme="b" ui-shadow ui-btn-inline>
                <div data-role="header" data-theme="b">
                    <h1>Confirmation</h1>
                </div>
                <h3 class="ui-title" id="new-event-title">Please confirm your Appointment Request</h3>
                <ul></ul>
                <fieldset data-role="controlgroup">
                    <label for="newClient" title="add contact info to client list">Add / Update Client</label>
                    <input type="checkbox" id="newClient" name="newClient" class="newClient" checked value="true">
                    <label for="notifyClient" title="send text and/or email reminder">Notify Client</label>
                    <input type="checkbox" id="notifyClient" name="notifyClient" class="notifyClient" checked value="true">
                </fieldset>

                <div role="main" class="ui-content">
                    <a href="#" id="confirm-back-btn" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-back ui-btn-icon-left" data-rel="back">Back</a>
                    <a href="#mainpage" data-role="button" id="confirm-btn" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-check ui-btn-icon-left" data-transition="slide">Confirm</a>
                </div>
            </div>
            <!-- ================= entry errors  popup page ================= -->
            <div data-role="popup" id="errorpage" data-overlay-theme="b" ui-shadow ui-btn-inline>
                <div data-role="header" data-theme="b">
                    <h1>Entry Error</h1>
                </div>
                <h3 class="ui-title" id="new-event-title"></h3>
                <ul></ul>
                <table id="conflict-table">
                    <tbody>
                    </tbody>
                </table>
                <div role="main" class="ui-content" id="btn1">
                    <a href="#" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-back ui-btn-icon-left" id="error-back" data-rel="back">Back</a>
                </div>
            </div>
        </section>
    </section>
</div> <!-- end newEvent page -->

</body>
</html>