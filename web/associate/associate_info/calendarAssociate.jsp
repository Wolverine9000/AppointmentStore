<%-- asociateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>

<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_calendarAssociate.css">
<script src="../js/associateJS/associate_calendar.js" type="text/javascript"></script>
<%-- <input type="hidden" name="associateId" id="associateId" value="${associateRecord.id}">
--%>

<style>
    #calendar {
        width: 100%;
        margin-bottom: 5px;
    }
    .no-close .ui-dialog-titlebar-close {
        display: none;
    }
</style>
<div id="main-section">
    <aside>
        <div id="aside-div">
            <img src="${initParam.associateImagePath}${associateRecord.id}.png"
                 alt="${associateRecord.id}"  id="associateImg" alt="associate picture">
            <div class="smallText" id="associateFirstName">${associateRecord.firstName}</div>

            <h3><a href="<c:url value='calendarAssociate'/>"><strong>Calendar</strong></a></h3>

            <h3><a href="<c:url value='scheduler'/>">Scheduler</a></h3>

            <h3><a href="<c:url value='associate_clients'/>">Clients</a></h3>

            <h3><a href="<c:url value='associateSettings'/>">Settings</a></h3>

            <h3><a href="<c:url value='associate_messages'/>">Messages</a></h3>    

            <h3><a href="<c:url value='associate_services'/>">Services</a></h3>

            <h3><a href="<c:url value='#'/>">Account</a></h3>

            <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
        </div>
    </aside>
    <section id="section1">

        <div id="identifier"><h1>Calendar</h1>
            <div id="loading" title="Processing . . .">
                <h4></h4>
            </div>
        </div>
        <div id="messages">
            <p class="error">${notFoundMessage}</p>
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div>

        <div id="msgBar">
            <div id="gotodiv">
                <table id="gototable">
                    <td><label for="calendarAssociate">goto:</label></td>
                    <td><input size="11" type="text" 
                               title="Enter a  Date" placeholder="" id="calendarAssociate"></td>
                </table>
            </div>
            <div id="showdiv">
                associates:
                <select name="calendars" id="showCal">
                    <optgroup label="calendar events">
                        <option value="0:all">all associates</option>
                    </optgroup>
                    <optgroup label="calendar">
                        <c:forEach var="associate" items="${associatesAll}">
                            <option value="<c:out value='${associate.id}:aa' />"><c:out value="${associate.firstName}" /></option>
                        </c:forEach>
                    </optgroup>
                    <optgroup label="current associate">
                        <option value="<c:out value='${associateRecord.id}:ca' />"><c:out value="${associateRecord.firstName}" /></option>
                    </optgroup>
                    <optgroup label="available times">
                        <c:forEach var="associate" items="${associatesAll}">
                            <option value="<c:out value='${associate.id}:at' />"><c:out value="${associate.firstName}" /></option>
                        </c:forEach>
                    </optgroup>
                </select>
                &nbsp;clients:
                <select name="client calendars" id="clientCal">
                    <optgroup label="calendar events">
                        <option value="0:all">all clients</option>
                    </optgroup>
                    <optgroup label="client calendar">
                        <c:forEach var="client" items="${clients}">
                            <option value="<c:out value='${client.id}:cc' />"><c:out value="${client.firstName}"/>&nbsp;<c:out value="${client.lastName}" /></option>
                        </c:forEach>
                    </optgroup>
                </select>
            </div>
        </div>
        <div id='calendar'></div>

        <div class="calEvent">
            <table class="eventInfoTable alignRight-td table-stripe-main">
                <tbody>
                </tbody>
            </table>
            <form>
                <fieldset id="statusField">
                    <table class="statusTable">
                        <thead>
                            <tr>
                                <th><label for="updateStatus" title="update current status">update status</label></th>
                                <th>
                                    <select name="updateStatus" id="updateStatus" class="text ui-widget-content ui-corner-all">
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
            </form>
        </div>

        <div id="delete">
            <span class="ui-icon ui-icon-alert"></span>    
            <div id="deleteHeader"></div>
            <table class="deleteTable alignRight-td table-stripe-main border-bottom-table">
                <tbody>
                </tbody>
            </table>
            <form>
                <fieldset id="notifyField">
                    <table id="notifyCancelTabel ">
                        <tr>
                            <td><label for="notifyClient" title="send text and/or email reminder">notify client:</label></td>
                            <td><INPUT type="checkbox" name="notifyClient" id="notifyClient" checked value="true"></td>
                        </tr>
                        <tr>
                            <td><label for="restoreTime" title="free time slot for associate">restore time:</label></td>
                            <td><INPUT type="checkbox" name="restoreTime" id="restoreTime" checked value="true"></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div>

        <div id="newEvent" title="Create New Appointment">
            <div class="eventHeader"></div>
            <div id="startend">
                <table class="times">
                    <thead>
                        <tr>
                            <th><label for="startTime" title="service start time">start time</label></th>
                            <th><input type="text" name="startTime" id="startTime" disabled="disabled" value=""
                                       title="service start time"></th>


                            <th><label for="endTime" title="service end time">end time</label></th>
                            <th><input type="text" name="endTime" id="endTime" disabled="disabled" value=""
                                       title="service end time"></th>
                        </tr>
                    </thead>
                </table>
            </div>
            <form id="form">
                <fieldset id="newEventField">
                    <div class="validateTips error smallText normalText"></div>
                    <table class="notifyTable">
                        <tbody>
                            <tr>
                                <td><label for="firstNm" title="client first name">first name&#58;</label></td>
                                <td><input type="text" name="firstNm" id="firstNm" maxlength="45" placeholder="name, email, id" class="text ui-widget-content ui-corner-all"
                                           title="Enter a first name"></td>
                            </tr>
                            <tr>
                                <td><label for="lastNm" title="client last name">last name&#58;</label></td>
                                <td><input type="text" name="lastNm" id="lastNm" maxlength="45" class="text ui-widget-content ui-corner-all"
                                           title="Enter a last name"></td>
                            </tr>
                            <tr>
                                <td><label for="mobilePhone" title="client moblie phone">mobile&#58;</label></td>
                                <td><input type="tel" name="mobilePhone" id="mobilePhone" maxlength="45" value="" class="text ui-widget-content ui-corner-all"  
                                           placeholder="phone number" pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number"></td>
                            </tr>

                            <tr>
                                <td><label for="email" title="client email address">email&#58;</label></td>
                                <td><input type="text" name="email" id="email" maxlength="45" value="" class="text ui-widget-content ui-corner-all"
                                           title="Enter a valid email address"></td>
                            </tr>
                            <tr>
                                <td><label for="customerId" title="client id or account number">ID&#58;</label></td>
                                <td><input type="text" name="customerId" id="customerId" disabled="disabled"></td>
                            </tr>
                            <tr style="vertical-align: bottom;">
                                <td><label for="associate" title="associate">associate&#58;</label></td>
                                <td><select name="associate" id="associate" class="text ui-widget-content ui-corner-all">
                                    </select></td>
                            </tr>
                        <input type="hidden" id="client-info">
                        <tr>
                            <td><label for="normal-service" title="normal-service">service&#58;</label></td>
                            <td><select name="normal-service" id="normal-service" class="text ui-widget-content ui-corner-all">
                                </select></td>
                        </tr>
                        <tr>
                            <td><label for="notes" title="service notes">notes&#58;</label></td>
                            <td><input type="text" name="notes" id="notes"  placeholder="100 characters or less" 
                                       class="text ui-widget-content ui-corner-all" maxlength="100"></td>
                        </tr>
                        <tr>
                            <td><label for="status" title="appointment current status">status&#58;</label></td>
                            <td style="text-align: left"><select name="status" id="status" class="text ui-widget-content ui-corner-all">
                                    <c:forEach var="status" items="${status}">
                                        <option value="<c:out value='${status.statusId}' />">
                                            <c:out value="${status.statusName}" />
                                        </option>
                                    </c:forEach><!-- service status-->
                                </select></td>
                        </tr>
                        </tbody>
                    </table>
                    <div id="accordion-5">
                        <h3>Make Appointment for Family or Friend</h3>
                        <div class="last-5-visits">
                            <table class="last-visits-table">
                                <tbody>
                                    <tr><th>Name</th><th>Service</th></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div id="accordion-4">
                        <h3>Last 5 Visits</h3>
                        <div class="last-5-visits">
                            <table class="last-visits-table">
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </fieldset>
            </form>
            <fieldset>
                <table id="newEventTable">
                    <tbody>
                        <tr>
                            <td><label for="newClient" title="add or update contact info to client list">add/update client</label></td>
                            <td><INPUT type="checkbox" id="newClient" name="newClient" class="newClient" checked></td>
                            <td><label for="notifyClient" title="send text and/or email reminder">notify client</label></td>
                            <td><INPUT type="checkbox" id="notifyClient" name="notifyClient" class="notifyClient" checked></td>
                        </tr>
                    </tbody>
                </table>
            </fieldset>
        </div>

        <div id="errorMsg" title="WARNING!">
            <span class="ui-icon ui-icon-alert"></span>
            <div style="text-align: left"></div>
            <fieldset>
                <legend>Selected Appointment</legend>
                <table id="errorTable2">
                    <tbody>
                    </tbody>
                </table>
            </fieldset>
            <fieldset>
                <legend>Conflicting Appointment(s)</legend>
                <table class="errorTable">
                    <tbody>
                    </tbody>
                </table>
            </fieldset>
        </div>

        <div id="pastDateMsg" title="WARNING!">
            <span></span>
            <figcaption></figcaption>
            <table id="pastTable">
                <tbody>
                </tbody>
                <tfoot></tfoot>
            </table>
        </div>

    </section>
</div>