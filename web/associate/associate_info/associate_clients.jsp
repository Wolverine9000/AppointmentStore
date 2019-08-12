<%-- 
    Document   : clients
    Created on : Jan 7, 2015, 11:00:35 AM
    Author     : williamdobbs
--%>
<%-- associateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>

<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_clients.css">
<script src="../js/associateJS/associate_clients.js" type="text/javascript"></script>

<div id="main-section">
    <aside>
        <div id="aside-div">
            <img src="${initParam.associateImagePath}${associateRecord.id}.png"
                 alt="${associateRecord.id}"  id="associateImg" alt="associate picture">
            <div class="smallText" id="associateFirstName">${associateRecord.firstName}</div>

            <h3><a href="<c:url value='calendarAssociate'/>">Calendar</a></h3>

            <h3><a href="<c:url value='scheduler'/>">Scheduler</a></h3>

            <h3><a href="<c:url value='associate_clients'/>"><strong>Clients</strong></a></h3>

            <h3><a href="<c:url value='associateSettings'/>">Settings</a></h3>

            <h3><a href="<c:url value='associate_messages'/>">Messages</a></h3>

            <h3><a href="<c:url value='associate_services'/>">Services</a></h3>

            <h3><a href="<c:url value='#'/>">Account</a></h3>

            <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
        </div>
    </aside>

    <section id="section1">
        <div id="identifier"><h1>Clients</h1>
            <div id="loading" title="Processing . . .">
                <h4></h4>
            </div>
        </div>
        <div id="messages">
            <p class="error">${errorMessage}</p>
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div>
        <div id="msgBar">
            <div id="memberLevels">filter:&nbsp;</div>
            <div id="showAllClients">&nbsp;all</div>
            <span style="float: left;" class="ui-icon ui-icon-search"></span>
            <div id="search"><input type="text" id="search-client" placeholder="search&#58; name&#44; email&#44; id" value="" class="text ui-widget-content ui-corner-all"></div>
            <span class="ui-icon ui-icon-person"></span>
            <div id="add-client">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;new</div>
            <div id="select-client-div">
                <fieldset id="select-client-options">
                    <label for="c-options">actions:&nbsp;</label>
                    <select name="c-options" id="c-options">
                        <option selected="selected" value="default">client options</option>
                        <option disabled="">-------------</option>
                        <option value="send-message">send message</option>
                        <option value="deactivate-clients">deactivate</option>
                    </select>
                </fieldset>
            </div>
        </div>
        <table id="client-list-table">
            <thead>
                <tr><th></th><th title="sort by ID number">ID</th><th title="sort by First Name">First</th><th title="sort by Last Name">Last</th>
                    <th title="sort by Email Address">Email</th><th title="sort by Mobile Number">Mobile</th><th title="sort by Member Level">Level</th>
                    <th title="select all checkboxes" id="select-header">Select</th></tr>
            </thead>    
            <tbody>
            </tbody>
        </table>
        <div class="clientInfo">
            <table class="eventInfoTable alignRight-td table-stripe-main width-td-first">
                <tbody>
                </tbody>
            </table>
            <div id="accordion">
                <h3>Notification Preferences</h3>
                <div class="alert-prefs">
                    <table class="alert-prefs-table">
                        <tbody>
                            <tr>
                                <td><INPUT type="checkbox" id="email-ad-alerts" name="email-ad-alerts" class="email-ad-alerts" disabled="true"></td>
                                <td><label for="email-ad-alerts" title="receive email ad alerts">email ads</label></td>
                                <td><INPUT type="checkbox" id="sms-ad-alerts" name="sms-ad-alerts" class="sms-ad-alerts" disabled="true"></td>
                                <td><label for="sms-ad-alerts" title="receive sms ad alerts">sms ads</label></td>
                            </tr>
                            <tr>
                                <td><INPUT type="checkbox" id="email-appt-alerts" name="email-appt-alerts" class="email-appt-alerts" disabled="true"></td>
                                <td><label for="email-appt-alerts" title="receive email appointment reminders">email reminder</label></td>
                                <td><INPUT type="checkbox" id="sms-appt-alerts" name="sms-appt-alerts" class="sms-appt-alerts" disabled="true"></td>
                                <td><label for="sms-appt-alerts" title="receive sms appointment reminders">sms reminder</label></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <h3>Past 5 Appointments</h3>
                <div class="visits">
                    <table class="last-visits-table">
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <h3>Future Appointments</h3>
                <div class="visits">
                    <table class="future-visits-table">
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <form id="userForm">
            <fieldset class="editUser">
                <legend class="validateTips error smallText normalText"></legend>
                <table class="editUserTable table-td-first td">
                    <tbody>
                        <tr>
                            <td><label for="clientImg" title="client picture">picture&#58</label></td>
                            <td id="client-picture"></td>
                        <tr>
                            <td><label for="firstNm" title="client first name">first name&#58;</label></td>
                            <td><input type="text" name="firstNm" id="firstNm" placeholder="first name is required" class="text ui-widget-content ui-corner-all"
                                       required="please enter client first name" maxlength="45"></td>
                        </tr>
                        <tr>
                            <td><label for="lastNm" title="client last name">last name&#58;</label></td>
                            <td><input type="text" name="lastNm" id="lastNm" placeholder="last name is required" class="text ui-widget-content ui-corner-all"
                                       required="please enter client last name" maxlength="45"></td>
                        </tr>
                        <tr>
                            <td><label for="email" title="email address">email&#58;</label></td>
                            <td><input type="email" name="email" id="email" placeholder="email address is required" class="text ui-widget-content ui-corner-all"
                                       title="Enter a valid email address"  required="please enter client email address"></td>
                        </tr>
                        <tr>
                            <td><label for="confirmEmail" title="confirm email address">confirm email&#58;</label></td>
                            <td><input type="email" name="confirmEmail" id="confirmEmail" placeholder="confirm email address" class="text ui-widget-content ui-corner-all"
                                       title="re-enter email address"  required="please re-enter email address"></td>
                        </tr>
                        <tr>
                            <td><label for="mobilePhone" title="client moblie phone">mobile&#58;</label></td>
                            <td><input type="tel" name="mobilePhone" id="mobilePhone" value="" placeholder="mobile phone is required" class="text ui-widget-content ui-corner-all"  
                                       pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number"
                                       required="please enter client mobile phone"></td>
                        </tr>
                        <tr>
                            <td><label for="homePhone" title="client home phone">home&#58;</label></td>
                            <td><input type="tel" name="homePhone" id="homePhone" value="" class="text ui-widget-content ui-corner-all"  
                                       placeholder="home phone number" pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number"></td>
                        </tr>
                        <tr>
                            <td><label for="workPhone" title="client work phone">work&#58;</label></td>
                            <td><input type="tel" name="workPhone" id="workPhone" value="" class="text ui-widget-content ui-corner-all"  
                                       placeholder="work phone number" pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number"></td>
                        </tr>
                        <tr>
                            <td><label for="company" title="company">company&#58;</label></td>
                            <td><input type="text" name="company" id="company" class="text ui-widget-content ui-corner-all" maxlength="45"></td>
                        </tr>
                        <tr>
                            <td><label for="address" title="client address">address&#58;</label></td>
                            <td><input type="text" name="address" id="address" class="text ui-widget-content ui-corner-all"
                                       maxlength="45"></td>
                        </tr>
                        <tr>
                            <td><label for="city" title="city">city&#58;</label></td>
                            <td><input type="text" name="city" id="city" value="" class="text ui-widget-content ui-corner-all" maxlength="45"></td>
                        </tr>
                        <tr>
                            <td><label for="state" title="state">state&#58;</label></td>
                            <td><select name="state" id="state" value="" class="text ui-widget-content ui-corner-all">
                                </select></td>
                        </tr>
                        <tr>
                            <td><label for="zip" title="client zip code">zip&#58;</label></td>
                            <td><input type="text" name="zip" id="zip" value="" class="text ui-widget-content ui-corner-all"></td>
                        </tr>
                        <tr>
                            <td><label for="memberLevel" title="member level">level&#58;</label></td>
                            <td><select name="memberLevel" id="memberLevel" value="" class="text ui-widget-content ui-corner-all">
                                </select></td>
                        </tr>
                        <tr>
                            <td><label for="normal-service" title="normal-service">normal service&#58;</label></td>
                            <td><select name="normal-service" id="normal-service" class="text ui-widget-content ui-corner-all">
                                </select></td>
                        </tr>
                        <tr style="vertical-align: bottom;">
                            <td><label for="associate" title="associate">associate&#58;</label></td>
                            <td><select name="associate" id="associate" class="text ui-widget-content ui-corner-all">
                                </select>&nbsp;<div id="assoImg"></div></td>
                        </tr>
                    </tbody>
                </table>
                <div id="accordion-2">
                    <h3>Notification Preferences</h3>
                    <div class="alert-prefs">
                        <table class="alert-prefs-table">
                            <tbody>
                                <tr>
                                    <td><INPUT type="checkbox" id="email-ad-alerts" name="email-ad-alerts" class="email-ad-alerts"></td>
                                    <td><label for="email-ad-alerts" title="receive email ad alerts">email ads</label></td>
                                    <td><INPUT type="checkbox" id="sms-ad-alerts" name="sms-ad-alerts" class="sms-ad-alerts"></td>
                                    <td><label for="sms-ad-alerts" title="receive sms ad alerts">sms ads</label></td>
                                </tr>
                                <tr>
                                    <td><INPUT type="checkbox" id="email-appt-alerts" name="email-appt-alerts" class="email-appt-alerts"></td>
                                    <td><label for="email-appt-alerts" title="receive email appointment reminders">email reminder</label></td>
                                    <td><INPUT type="checkbox" id="sms-appt-alerts" name="sms-appt-alerts" class="sms-appt-alerts"></td>
                                    <td><label for="sms-appt-alerts" title="receive sms appointment reminders">sms reminder</label></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </fieldset>
        </form>
        <form>
            <fieldset class="confirmUser">
                <table class="confirmUserTable alignRight-td table-stripe-main width-td-first">
                    <tbody></tbody>
                </table>
                <div id="accordion-3">
                    <h3>Notification Preferences</h3>
                    <div class="alert-prefs">
                        <table class="alert-prefs-table">
                            <tbody>
                                <tr>
                                    <td><INPUT type="checkbox" id="email-ad-alerts" name="email-ad-alerts" class="email-ad-alerts" disabled="true"></td>
                                    <td><label for="email-ad-alerts" title="receive email ad alerts">email ads</label></td>
                                    <td><INPUT type="checkbox" id="sms-ad-alerts" name="sms-ad-alerts" class="sms-ad-alerts" disabled="true"></td>
                                    <td><label for="sms-ad-alerts" title="receive sms ad alerts">sms ads</label></td>
                                </tr>
                                <tr>
                                    <td><INPUT type="checkbox" id="email-appt-alerts" name="email-appt-alerts" class="email-appt-alerts" disabled="true"></td>
                                    <td><label for="email-appt-alerts" title="receive email appointment reminders">email reminder</label></td>
                                    <td><INPUT type="checkbox" id="sms-appt-alerts" name="sms-appt-alerts" class="sms-appt-alerts" disabled="true"></td>
                                    <td><label for="sms-appt-alerts" title="receive sms appointment reminders">sms reminder</label></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </fieldset>
        </form>
        <fieldset id="send-message-field">
            <legend class="validateTips"></legend>
            <div id="messageHeader"></div>
            <div>
                <label for="subject" title="message subject (optional)">Subject &#40;optional&#41;&#58;</label>
                <input type="text" name="subject" id="subject" maxlength="13" placeholder="Subject is limted to 13 characters."
                       maxlength="13">
                <div style="white-space: nowrap">
                    When To Send&#58;
                    <label for="send-now" title="send message now" class="send-now-label">Now</label>
                    <input type="checkbox" name="send-now" id="send-now">
                    <input type="text" size="11" name="dateToSendMsg" id="dateToSendMsg" placeholder="" class="enabledTextColor">
                    <label for="send-hour" title="hour to send message">Hour</label>
                    <select name="send-hour" id="send-hour"></select>
                    <label for="send-minutes" title="minute to send message">Minute</label>
                    <select name="send-minutes" id="send-minutes"></select>
                    <select name="send-ampm" id="send-ampm">
                        <option value="AM">AM</option>
                        <option value="PM">PM</option>
                    </select>
                </div>
                <div id="msgName">Message&#58;</div>
                <textarea name="message-to-send" id="message-to-send" maxlength="145" placeholder="10 to 145 characters"
                          title="clients will recieve your message via text and email only if those options are enabled within their account"></textarea>
            </div>
        </fieldset>
        <fieldset id="clients-del-fieldset">
            <span></span>
            <legend></legend>
            <table id="delete-client-list">
            </table>
        </fieldset>
    </section>
</div>
