<link rel="stylesheet" type="text/css" href="../css/mobileCSS/associateMobileCSS_settings.css">
<script src="../js/associateJSmobile/associateMobile_settings.js" type="text/javascript"></script>

<%-- 
    Document   : m_associateSettings
    Created on : Jul 23, 2015, 8:15:23 AM
    Author     : williamdobbs
--%>
<%-- asociateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>
<!-- ======================= associate profile page ============================== -->  
<div data-role="page" id="settings" date-title="Associate">
    <div data-role="panel" id="panel" data-theme="b">
        <h2>Main Menu</h2>
        <ul data-role="listview">
            <li><a href="<c:url value='/mobile/m_associatePage.jsp'/>" data-ajax="false" data-transition="slide">Calendar</a></li>
            <li><a href="<c:url value='/mobile/m_associateScheduler.jsp' />" data-ajax="false" data-transition="slide">Scheduler</a></li>
            <li><a href="<c:url value='/mobile/m_associateClients.jsp' />" data-ajax="false" data-transition="slide">Clients</a></li>
            <li><a href="<c:url value='#' />">Settings</a></li>
            <li><a href="<c:url value='#' />">Messages</a></li>
            <li><a href="<c:url value='#' />">Account</a></li>
            <li><a href="<c:url value='logoutAssociate'/>" data-transition="fade">Logout</a></li>
        </ul>
    </div>
    <header data-role="header" data-theme="b" data-position="fixed" class="header-logo">
        <a href="#panel" display="reveal" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-bars ui-mini ui-btn-icon-notext">Menu</a>
        <h1></h1>
    </header>
    <div data-role="content">
        <div id="messages">
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div>  <!-- messages -->
        <!-- spinner div -->
        <div id="loading"></div>
        <div data-role="tabs" id="tabs">
            <div data-role="navbar">
                <ul>
                    <li><a href="#profile" data-ajax="false">Profile</a></li>
                    <li><a href="#account" data-ajax="false">Account</a> </li>
                    <li><a href="#security" data-ajax="false">Security</a></li>
                    <li><a href="#calendar-settings" data-ajax="false">Calendar</a> </li>
                </ul>
            </div>
            <div id="profile" class="ui-body-d ui-content">
                <div id="profile">
                    <div>
                        <form id="personal-form">
                            <fieldset id="profile-fieldset-1">
                                <h3 class="qs-h3-1">Personal</h3>
                                <div id="personal">
                                    <table id="profile-table">
                                        <tbody></tbody>
                                    </table>
                                </div>
                                <div id="postResults" class="smallText " style="text-align: center;"></div>
                                <a href="#edit-associate-page" data-transition="slideup" id="personal-btn" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all">Edit Info</a>
                            </fieldset>
                        </form>

                        <fieldset id="alert-prefs-fieldset">
                            <h3 class="qs-h3-1">Alert Preferences</h3>
                            <div id="alert-prefs">
                                <label><input type="checkbox" id="email-ad-alerts" name="email-ad-alerts">Email Ads</label>
                                <label><input type="checkbox" id="sms-ad-alerts" name="sms-ad-alerts">SMS Ads</label>
                                <label><input type="checkbox" id="email-appt-alerts" name="email-appt-alerts">Email Appt Reminders</label>
                                <label><input type="checkbox" id="sms-appt-alerts" name="sms-appt-alerts">SMS Appt Reminders</label>
                            </div>
                            <div id="postResults" class="smallText " style="text-align: center;"></div>
                            <button id="alert-btn" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all" >Save Alerts</button>
                        </fieldset>
                        <form id="input-upload" action="associatePhotoUpl" data-ajax="false" enctype="multipart/form-data" method="POST">
                            <fieldset id="photo-fieldset">
                                <h3 class="qs-h3-1">Photo</h3>
                                <div id="photo"></div>
                                <p class="error smallText">${messageError }</p>
                                <p class="good smallText">${messaeSuccess }</p>
                                <input type="file" name="${associateRecord.id}.png" accept="image/png">
                            </fieldset>
                            <button id="photo-upload-btn" class="ui-btn ui-corner-all ui-icon-check ui-btn-icon-left
                                    ui-shadow ui-corner-all">Save Photo</button>
                        </form>
                    </div>
                </div>
            </div>
            <div id="account" class="ui-body-d ui-content">
                <fieldset id="account-fieldset">
                    <h3 class="qs-h3-1">Account Summary</h3>
                    <table id="account-table">
                        <tbody></tbody>
                    </table>
                    <div id="personal"></div>
                    <div id="personal-btn"></div>
                </fieldset>
                <fieldset id="account-details">
                    <h3 class="qs-h3-1">Plan Details</h3>
                    <table class="widthAcct-first-td profile-table-1 alignRight-td table-stripe-main roundCorners-td1 roundCorners-td2 width-450-pixels tableBorder-top">
                        <tbody></tbody>
                    </table>
                    <div id="personal"></div>
                    <div id="personal-btn"></div>
                </fieldset>
            </div>
            <div id="security" class="ui-body-d ui-content">
                <h3 class="qs-h3-1">Change Password</h3>
                <label for="password">Password:</label>
                <input type="password" name="password" id="password" value="" autocomplete="off">
                <label for="confirm-password">Confirm Password:</label>
                <input type="password" name="confirm-password" id="confirm-password" value="" autocomplete="off">
                <div id="postResults" class="smallText " style="text-align: center;"></div>
                <button id="password-btn" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all">Change Password</button>

                <h3 class="qs-h3-1">Security Questions</h3>
                <label for="securityQuestion-1">question 1&#58;</label>
                <select name="securityQuestion-1" id="securityQuestion-1">
                    <c:forEach var="i" begin="1" end="3">
                        <c:forEach var="questions" items="${questions}">
                            <c:if test="${ i == 1 && questions.questionSet == 1}">
                                <option value="${questions.questionId}">${questions.question}</option>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </select>
                <label for="securityAnswer-1" title="enter answer to security question 1.">answer 1&#58;</label>
                <input type="text" name="securityAnswer-1" id="securityAnswer-1"
                       placeholder="An entry to this field is required."
                       maxlength="30">
                <label for="securityQuestion-2">question 2&#58;</label>
                <select name="securityQuestion-2" id="securityQuestion-2">
                    <c:forEach var="i" begin="1" end="3">
                        <c:forEach var="questions" items="${questions}">
                            <c:if test="${ i == 2 && questions.questionSet == 2}">
                                <option value="${questions.questionId}">${questions.question}</option>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </select>
                <label for="securityAnswer-2" title="enter answer to security question 2.">answer 2&#58;</label>
                <input type="text" name="securityAnswer-2" id="securityAnswer-2"
                       title="Answer may consist of a-z, 0-9, underscores, begin with a letter."
                       placeholder="An entry to this field is required."
                       maxlength="30">
                <label for="securityQuestion-3">question 3&#58;</label>
                <select name="securityQuestion-3" id="securityQuestion-3">
                    <c:forEach var="i" begin="1" end="3">
                        <c:forEach var="questions" items="${questions}">
                            <c:if test="${ i == 3 && questions.questionSet == 3}">
                                <option value="${questions.questionId}">${questions.question}</option>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </select>
                <label for="securityAnswer-3" title="enter answer to security question 3.">answer 3&#58;</label>
                <input type="text" name="securityAnswer-3" id="securityAnswer-3"
                       title="Answer may consist of a-z, 0-9, underscores, begin with a letter."
                       placeholder="An entry to this field is required."
                       maxlength="30">
                <div id="security-questions-postResults" class="smallText " style="text-align: center;"></div>
                <button id="security-questions-btn" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all">Save Answers</button>
                <!--       ================= entry errors  popup page ================= -->
                <div data-role="popup" id="error-security" data-overlay-theme="b" ui-shadow ui-btn-inline>
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
            </div>
            <!--   </div>
        </div>-->
            <div id="calendar-settings" class="ui-body-d ui-content">
                <h3 class="qs-h3-1">Calendar Preferences</h3>
                <label for="defaultCalendarView" title="Default Calendar View.">Default View&#58</label>
                <select name="defaultCalendarView" id="defaultCalendarView"></select>
                <div id="postResults" class="smallText " style="text-align: center;"></div>
                <button id="calendar-prefs-btn" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all">Save Default View</button>
                <h3 class="qs-h3-1">Normal Work Hours</h3>
                <label for="startTimeM" title="Default Morning Time In.">Morning Time In&#58;</label>
                <select name="startTimeM" id="startTimeM"></select>
                <label for="endTimeM" title="Default Morning Time Out.">Morning Time Out&#58;</label>
                <select name="endTimeM" id="endTimeM"></select>
                <label for="startTimeA" title="Default Afternoon Time In.">Afternoon Time In&#58;</label>
                <select name="startTimeA" id="startTimeA"></select>
                <label for="endTimeA" title="Default Afternoon Time Out.">Afternoon Time Out&#58;</label>
                <select name="endTimeA" id="endTimeA"></select>
                <div id="defaulttimes-postResults" class="smallText " style="text-align: center;"></div>
                <button id="workhours-btn" class="ui-btn ui-icon-check ui-btn-icon-left ui-shadow ui-corner-all">Save Normal Hours</button>
            </div>
        </div>
    </div>    <!-- end content -->
</div>
<!-- ======================= edit associate page ============================== -->
<div data-role="page" id="edit-associate-page" date-title="Edit Associate">
    <header data-role="header" data-theme="b" data-position="fixed">
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-back ui-mini ui-btn-icon-notext"data-rel="back">Back</a>
        <h1>Edit Associate Info</h1>
    </header>
    <section data-role="content">
        <div class="eventHeader"></div>
        <section id="edit-associate-form">
            <label for="mobilePhone">Mobile&#35;&#58;</label>
            <input type="tel" name="mobilePhone" id="mobilePhone" placeholder="10 digit phone number is required"
                   pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number"
                   required="please enter mobile phone">

            <label for="homePhone" title="home phone">Home&#58;</label>
            <input type="tel" name="homePhone" id="homePhone" 
                   placeholder="home phone number" pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number">

            <label for="workPhone" title="work phone">Work&#58;</label>
            <input type="tel"  name="workPhone" id="workPhone" 
                   placeholder="work phone number" pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number">

            <label for="otherPhone" title="other work phone">Other&#58;</label>
            <input type="tel"  name="otherPhone" id="otherPhone" 
                   placeholder="other phone number" pattern="\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}" title="Must be a 10 digit number">

            <label for="address" title="address">Address&#58;</label>
            <input type="text" name="address" id="address" maxlength="45">

            <label for="city" title="city">City&#58;</label>
            <input type="text" name="city" id="city" maxlength="45">

            <label for="state" title="state" style="margin-bottom: -.5em;">State&#58;</label>
            <select name="state" id="state"></select>

            <label for="zip" title="zip code">Zip&#58;</label>
            <input type="text" name="zip" id="zip">

            <button id="send" class="ui-btn ui-icon-check ui-btn-icon-left ui-btn ui-corner-all ui-shadow">Update</button>
        </section>
        <!--       ================= entry errors  popup page ================= -->
        <div data-role="popup" id="error-associate-info" data-overlay-theme="b" ui-shadow ui-btn-inline>
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
</div>  <!-- end edit associate page -->
</body>
</html>
