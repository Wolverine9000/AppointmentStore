<%-- 
    Document   : m_associateClients
    Created on : May 11, 2015, 3:17:13 PM
    Author     : williamdobbs
--%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>
<!-- ======================= clients page ============================== -->
<div data-role="page" id="clients" date-title="Clients">

    <div data-role="panel" id="panel" data-theme="b">
        <h2>Main Menu</h2>
        <ul data-role="listview">
            <li><a href="<c:url value='/mobile/m_associatePage.jsp'/>" data-ajax="false" data-transition="slide">Calendar</a></li>
            <li><a href="<c:url value='/mobile/m_associateScheduler.jsp' />" data-ajax="false" data-transition="slide">Scheduler</a></li>
            <li><a href="<c:url value='/mobile/m_associateClients.jsp' />" data-transition="slide">Clients</a></li>
            <li><a href="<c:url value='/mobile/m_associateSettings.jsp' />" data-ajax="false" data-transition="slide">Settings</a></li>
            <li><a href="<c:url value='#' />">Messages</a></li>
            <li><a href="<c:url value='#' />">Account</a></li>
            <li><a href="<c:url value='logoutAssociate'/>" data-transition="fade">Logout</a></li>
        </ul>
    </div> 

    <header data-role="header" data-theme="b" data-position="fixed" class="header-logo">
        <a href="#panel" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-bars ui-mini ui-btn-icon-notext">Menu</a>
        <h1></h1>
    </header>
    <div data-role="content">
        <div id="messages">
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div> <!-- messages -->
        <!-- spinner div-->
        <div id="loading"></div>
        <section id="container">
            <div id="loading"></div>
            <div id="messages">
                <div id="postDataError" class="error smallText "></div>
                <div id="postDataSuccess" class="good smallText "></div>
            </div> <!-- messages -->
            <div id="search">
                <label for="search-client" class="ui-hidden-accessible">Search</label>
                <input type="text" id="search-client" placeholder="search&#58; name&#44; email&#44; id" class="text ui-widget-content ui-corner-all"></div>
            <div id="client-actions">
                <div class="ui-field-contain">
                    <select name="c-options" id="c-options">
                        <option selected="selected" value="default">actions</option>
                        <option disabled value="medium">------------</option>
                        <option value="send-message">message</option>
                        <option value="deactivate-clients">deactivate client</option>
                        <option value="new-client">new client</option>
                    </select>
                </div>
            </div>
        </section>
        <div>
            <table id="client-list-table">
                <thead>
                    <tr><th></th><th title="sort by First Name">First</th><th title="sort by Last Name">Last</th>
                        <th title="select all checkboxes" id="select-header">Select</th></tr>
                </thead>    
                <tbody>
                </tbody>
            </table>
        </div>
    </div><!-- content clients -->
    <!-- ================= client selection entry errors  popup page ================= -->
    <div data-role="popup" id="action-error" data-overlay-theme="b" ui-shadow ui-btn-inline>
        <div data-role="header" data-theme="b">
            <h1>Entry Error</h1>
        </div>
        <h3 class="ui-title"></h3>
        <div role="main" class="ui-content">
            <ul></ul>
            <a href="#" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-check ui-btn-icon-left" id="action-error-btn" data-rel="back">Ok</a>
        </div>
    </div><!-- end client selection errors  popup page -->
</div><!-- end clients -->
<!-- ================= client message confirm page ================= -->
<div data-role="page" id="clients-message" date-title="Client Message">
    <header data-role="header" data-theme="b" data-position="fixed">
        <a href="#" id="clients-message-back" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <h1 id="event-header">Send Message</h1>
    </header>
    <div data-role="content">
        <!--        <h3 id="message-h3"></h3>-->
        <p></p>
        <label for="subject" title="message subject (optional)">Subject &#40;optional&#41;&#58;</label>
        <input type="text" name="subject" id="subject" placeholder="Please note&#58 sms subject will be limted to 13 characters."
               maxlength="45">
        <legend>Message&#58;</legend>
        <textarea name="message-to-send" id="message-to-send" maxlength="145" placeholder="10 to 145 characters."
                  title="clients will recieve your message via text and email only if those options are enabled within their account(s)."></textarea>
        <a href="#" data-role="button" id="message-cancel-btn" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-left" data-transition="fade">Cancel</a>
        <a href="#" data-role="button" id="send-message-btn" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-check ui-btn-icon-left" data-transition="slide">Send</a>
        <!-- ================= client message entry errors  popup page ================= -->
        <div data-role="popup" id="message-error" data-dismissible="false" data-overlay-theme="b" ui-shadow ui-btn-inline>
            <div data-role="header" data-theme="b">
                <h1>Entry Error</h1>
            </div>
            <h3 class="ui-title"></h3>
            <div role="main" class="ui-content">
                <ul></ul>
                <a href="#" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-back ui-btn-icon-left" id="message-error-back" data-rel="back">Back</a>
            </div>
        </div><!-- end client message error popup -->
    </div>    
</div> <!--client message confirm page--->

<!-- ================= client deactivate confirm page ================= -->
<div data-role="page" id="clients-deactivate" date-title="Deactivate Client">
    <header data-role="header" data-theme="b" data-position="fixed">
        <a href="#" id="clients-deactivate-back" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <h1 id="event-header">De-Activate Clients</h1>
    </header>
    <div data-role="content">
        <fieldset id="clients-del-fieldset">
            <span></span>
            <legend></legend>
            <table id="deactivate-client-list">
            </table>
        </fieldset>
        <a href="#" data-role="button" id="deactivate-cancel-btn" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-left" data-transition="fade">Cancel</a>
        <a href="#" data-role="button" id="send-deactivate" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-check ui-btn-icon-left" data-transition="slide">Submit</a>
        <!-- ================= client deactivate entry errors  popup page ================= -->
        <div data-role="popup" id="deactivate-error" data-dismissible="false" data-overlay-theme="b" ui-shadow ui-btn-inline>
            <div data-role="header" data-theme="b">
                <h1>Entry Error</h1>
            </div>
            <h3 class="ui-title"></h3>
            <div role="main" class="ui-content">
                <ul></ul>
                <a href="#" class="error-back-btn ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-back ui-btn-icon-left" id="deactivate-back" data-rel="back">Back</a>
            </div>
        </div><!-- end client message error popup -->
    </div>    
</div> <!--client deactivate confirm page--->

<!-- ============================ client info page ======================== -->
<div data-role="page" id="client-infopage" date-title="Client Info">
    <c:if test="${empty associateRecord}">
        <c:redirect url="logoutAssociate"></c:redirect>
    </c:if>
    <header data-role="header" data-theme="b" data-position="fixed">
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <h1 id="event-header">Client Information</h1>
    </header>

    <div id="client-Info">
        <table class="client-infoTable">
            <tbody>
            </tbody>
        </table>
        <div data-role="collapsibleset" data-theme="a" data-content-theme="a">
            <div data-role="collapsible" class="alert-prefs-1">
                <h3>Notification Preferences</h3>
                <div data-role="controlgroup">
                    <input type="checkbox" id="email-ad-alerts-1" name="email-ad-alerts-1" disabled="">
                    <label for="email-ad-alerts-1" title="receive email ad alerts">email ads</label>
                    <input type="checkbox" id="sms-ad-alerts-1" name="sms-ad-alerts-1" disabled="">
                    <label for="sms-ad-alerts-1" title="receive sms ad alerts">sms ads</label>
                    <input type="checkbox" id="email-appt-alerts-1" name="email-appt-alerts-1" disabled="">
                    <label for="email-appt-alerts-1" title="receive email appointment reminders">email reminder</label>
                    <input type="checkbox" id="sms-appt-alerts-1" name="sms-appt-alerts-1" disabled="">
                    <label for="sms-appt-alerts-1" title="receive sms appointment reminders">sms reminder</label>
                </div>
            </div>
            <div data-role="collapsible">
                <h3>Past 5 Appointments</h3>
                <table class="last-visits-table" style="width: 100%">
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div data-role="collapsible">
                <h3>Future Appointments</h3>
                <table class="future-visits-table" style="width: 100%">
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <a  id="edit-client-info" class="ui-btn ui-corner-all ui-shadow  ui-btn-a ui-icon-edit ui-btn-icon-left" data-transition="flow">Edit</a>
</div> <!--client info page--->
<!-- ======================= edit client page ============================== -->
<div data-role="page" id="edit-client-page" date-title="Edit Client Information">
    <header data-role="header" data-theme="b" data-position="fixed">
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <h1></h1>
    </header>
    <section data-role="content">
        <div class="eventHeader"></div>
        <section id="edit-user-form">
            <label for="firstNm" title="client first name">First Name:</label>
            <input type="text" name="firstNm" id="firstNm" placeholder="first name is required"
                   required="please enter client first name" maxlength="45">

            <label for="lastNm" title="client last name">Last Name:</label>
            <input type="text" name="lastNm" id="lastNm" placeholder="last name is required"
                   required="please enter client last name" maxlength="45">

            <label for="email" title="email address">Email:</label>
            <input type="email" name="email" id="email" placeholder="email address is required" 
                   required="please enter client email address" title="Enter a valid email address">

            <label for="confirmEmail" title="confirm email address">Confirm Email&#58;</label>
            <input type="Email" name="confirmEmail" id="confirmEmail" placeholder="confirmation email address is required" 
                   required="please re-enter email address" title="re-enter valid email address">

            <label for="mobilePhone">Mobile&#35;&#58;</label>
            <input type="tel" name="mobilePhone" id="mobilePhone" placeholder="10 digit phone number is required"
                   pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number"
                   required="please enter client mobile phone">

            <label for="homePhone" title="client home phone">Home&#58;</label>
            <input type="tel" name="homePhone" id="homePhone" 
                   placeholder="home phone number" pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number">

            <label for="workPhone" title="client work phone">Work&#58;</label>
            <input type="tel"  name="workPhone" id="workPhone" 
                   placeholder="work phone number" pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number">

            <label for="company" title="company">Company&#58;</label>
            <input type="text" name="company" id="company" maxlength="45">

            <label for="address" title="client address">Address&#58;</label>
            <input type="text" name="address" id="address" maxlength="45">

            <label for="city" title="city">City&#58;</label>
            <input type="text" name="city" id="city" maxlength="45">

            <label for="state" title="state" style="margin-bottom: -.5em;">State&#58;</label>
            <select name="state" id="state"></select>

            <label for="zip" title="client zip code">Zip&#58;</label>
            <input type="text" name="zip" id="zip">

            <label for="memberLevel" title="member level" style="margin-bottom: -.5em;">Level&#58;</label>
            <select name="memberLevel" id="memberLevel"></select>

            <label for="normal-service" title="normal-service" style="margin-bottom: -.5em;">Service&#58;</label>
            <select name="normal-service" id="normal-service"></select>

            <label for="associate" title="associate service" style="margin-bottom: -.5em;">Associate&#58;</label>
            <select name="associate" id="associate"></select>
            <input type="hidden" id="client-info">
            <label for="associate" title="associate service" style="margin-bottom: -.05em;">Alerts&#58;</label>
            <fieldset data-role="collapsible" class="alert-prefs-2">
                <legend>Notification Preferences</legend>
                <div data-role="controlgroup">
                    <input type="checkbox" id="email-ad-alerts" name="email-ad-alerts">
                    <label for="email-ad-alerts" title="receive email ad alerts">email ads</label>
                    <input type="checkbox" id="sms-ad-alerts" name="sms-ad-alerts">
                    <label for="sms-ad-alerts" title="receive sms ad alerts">sms ads</label>
                    <input type="checkbox" id="email-appt-alerts" name="email-appt-alerts">
                    <label for="email-appt-alerts" title="receive email appointment reminders">email reminder</label>
                    <input type="checkbox" id="sms-appt-alerts" name="sms-appt-alerts">
                    <label for="sms-appt-alerts" title="receive sms appointment reminders">sms reminder</label>
                </div>
            </fieldset>
            <button id="send" class="ui-btn ui-icon-check ui-btn-icon-left ui-btn ui-corner-all ui-shadow">Confirm</button>
        </section>
        <!-- ================= entry errors  popup page ================= -->
        <div data-role="popup" id="error-client-info" data-overlay-theme="b" ui-shadow ui-btn-inline>
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
</div> <!-- end edit client page -->
<!-- ================= client edit confirm page ================= -->
<div data-role="page" id="client-confirm-page" date-title="Client Confirm Info">
    <header data-role="header" data-theme="b" data-position="fixed">
        <a href="#" id="client-confirm-back" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <h1 id="event-header">Confirm Client Information</h1>
    </header>
    <div id="client-confirm-Info">
        <table class="client-confirm-Table">
            <tbody>
            </tbody>
        </table>
        <fieldset data-role="collapsible" class="alert-prefs-3">
            <legend>Notification Preferences</legend>
            <div data-role="controlgroup">
                <input type="checkbox" id="email-ad-alerts-3" name="email-ad-alerts-3" disabled="">
                <label for="email-ad-alerts-3" title="receive email ad alerts">email ads</label>
                <input type="checkbox" id="sms-ad-alerts-3" name="sms-ad-alerts-3" disabled="">
                <label for="sms-ad-alerts-3" title="receive sms ad alerts">sms ads</label>
                <input type="checkbox" id="email-appt-alerts-3" name="email-appt-alerts-3" disabled="">
                <label for="email-appt-alerts-3" title="receive email appointment reminders">email reminder</label>
                <input type="checkbox" id="sms-appt-alerts-3" name="sms-appt-alerts-3" disabled="">
                <label for="sms-appt-alerts-3" title="receive sms appointment reminders">sms reminder</label>
            </div>
        </fieldset>
    </div>
    <a href="#" data-role="button" id="client-cancel-btn" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-left" data-transition="fade">Cancel</a>
    <a href="#clients" data-role="button" id="client-confirm-btn" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-check ui-btn-icon-left" data-transition="slide">Submit</a>
</div> <!--client edit confirm page--->
</body>
</html>