<%-- 
    Document   : settings
    Created on : Nov 27, 2012, 9:19:34 AM
    Author     : williamdobbs
--%>
<%-- asociateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>
<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_settings.css">
<script src="../js/associateJS/associate_settings.js" type="text/javascript"></script>

<div id="main-section">
    <aside>
        <div id="aside-div">
            <img src="${initParam.associateImagePath}${associateRecord.id}.png"
                 alt="${associateRecord.id}"  id="associateImg" alt="associate picture">
            <div class="smallText" id="associateFirstName">${associateRecord.firstName}</div>

            <h3><a href="<c:url value='calendarAssociate'/>">Calendar</a></h3>

            <h3><a href="<c:url value='scheduler'/>">Scheduler</a></h3>

            <h3><a href="<c:url value='associate_clients'/>">Clients</a></h3>

            <h3><a href="<c:url value='associateSettings'/>"><strong>Settings</strong></a></h3>

            <h3><a href="<c:url value='associate_messages'/>">Messages</a></h3>

            <h3><a href="<c:url value='associate_services'/>">Services</a></h3>

            <h3><a href="<c:url value='#'/>">Account</a></h3>
      
            <h3><a href="<c:url value='associate_admin'/>">Admin</a></h3>
            
            <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
        </div>
    </aside>

    <section id="section1">
        <div id="identifier"><h1>Settings</h1></div>
        <div id="loading" title="Processing . . .">
            <h4></h4>
        </div>
        <div id="messages">
            <p class="error">${errorMessage}</p>
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div>
        <div id="tabs">
            <ul>
                <li><a href="#profile">Profile</a></li>
                <li><a href="#account">Account</a></li>
                <li><a href="#members">Members</a></li>
                <li><a href="#security">Security</a></li>
                <li><a href="#calendarOptions">Calendar</a></li>
            </ul>
            <div id="profile">
                <div id="personal-div">
                    <form id="personal-form">
                        <fieldset id="profile-fieldset-1">
                            <legend>Personal</legend>
                            <div class="validateTips error smallText normalText"></div>
                            <div id="personal"></div>
                            <div id="postResults" class="smallText "></div>
                            <div id="personal-btn"></div>
                        </fieldset>
                    </form>

                    <fieldset id="alert-prefs-fieldset">
                        <legend>Alert Preferences</legend>
                        <div id="alert-prefs">
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
                            <div id="postResults" class="smallText "></div>
                            <div id="personal-btn"></div>
                        </div>
                    </fieldset>
                </div>
                <div id="photo-div">
                    <fieldset id="photo-fieldset">
                        <legend>Photo</legend>
                        <div id="photo"></div>
                        <p class="error smallText">${messageError }</p>
                        <p class="good smallText">${messaeSuccess }</p>
                        <div id="personal-btn"></div>
                        <form id="input-upload" action="associatePhotoUpl" enctype="multipart/form-data" method="POST">
                            <input type="file" name="${associateRecord.id}.png" accept="image/png"><br>
                        </form>
                    </fieldset>
                </div>
            </div>
            <div id="account">
                <fieldset id="account-fieldset">
                    <legend>Account Summary</legend>
                    <table id="account-table" class="widthAcct-first-td profile-table-1 alignRight-td table-stripe-main roundCorners-td1 roundCorners-td2 tableBorder-top">
                        <tbody></tbody>
                    </table>
                    <div id="personal"></div>
                    <div id="personal-btn"></div>
                </fieldset>
                <fieldset id="account-details">
                    <legend>Plan Details</legend>
                    <table class="widthAcct-first-td profile-table-1 alignRight-td table-stripe-main roundCorners-td1 roundCorners-td2 width-450-pixels tableBorder-top">
                        <tbody></tbody>
                    </table>
                    <div id="personal"></div>
                    <div id="personal-btn"></div>
                </fieldset>
            </div>
            <div id="members">
                <fieldset id="members-fieldset">
                    <legend>Member Level Settings</legend>
                    <table  class="profile-table-1 alignRight-td table-stripe-main roundCorners-td1 roundCorners-td2 tableBorder-top">
                        <tbody>
                            <tr><th class="table-header-font" style="text-align: right;">Level</th><th class="table-header-font">Number of Visits</th></tr>
                            <tr><td>Platinum</td>
                                <td>
                                    <select id="platinum">
                                        <option value="30">30</option>
                                        <option value="25">25</option>
                                        <option value="20">20</option>
                                        <option value="15">15</option>
                                        <option value="10">10</option>
                                        <option value="5">5</option>
                                        <option value="0">0</option>
                                    </select>
                                </td>
                            </tr>
                            <tr><td>Gold</td>
                                <td>
                                    <select id="gold">
                                        <option value="30">30</option>
                                        <option value="25">25</option>
                                        <option value="20">20</option>
                                        <option value="15">15</option>
                                        <option value="10">10</option>
                                        <option value="5">5</option>
                                        <option value="0">0</option>
                                    </select>
                                </td>
                            </tr>
                            <tr><td>Silver</td>
                                <td>
                                    <select id="silver">
                                        <option value="30">30</option>
                                        <option value="25">25</option>
                                        <option value="20">20</option>
                                        <option value="15">15</option>
                                        <option value="10">10</option>
                                        <option value="5">5</option>
                                        <option value="0">0</option>
                                    </select>
                                </td>
                            </tr>
                            <tr><td>Bronze</td>
                                <td>
                                    <select id="bronze">
                                        <option value="30">30</option>
                                        <option value="25">25</option>
                                        <option value="20">20</option>
                                        <option value="15">15</option>
                                        <option value="10">10</option>
                                        <option value="5">5</option>
                                        <option value="0">0</option>
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div id="postResults" class="smallText "></div>
                    <div id="personal-btn" class="save"></div>
                </fieldset>
            </div>
            <div id="security">
                <form id="password-form">
                    <fieldset id="password-feildset">
                        <legend>Password</legend>
                        <div class="validateTips error smallText normalText"></div>
                        <div id="password-div">
                            <table class="password-table">
                                <tbody>
                                </tbody>
                            </table>
                            <div id="postResults" class="smallText "></div>
                            <div id="personal-btn"></div>
                        </div>
                    </fieldset>
                </form>
                <fieldset id="security-questions-feildset">
                    <legend>Security Challenge Questions</legend>
                    <div class="validateTips error smallText normalText"></div>
                    <div id="security-questions-div">
                        <table class="security-questions-table">
                            <tbody>
                                <tr>
                                    <td><label for="securityQuestion-1">question 1&#58;</label></td>
                                    <td><select name="securityQuestion-1" id="securityQuestion-1">
                                            <c:forEach var="i" begin="1" end="3">
                                                <c:forEach var="questions" items="${questions}">
                                                    <c:if test="${ i == 1 && questions.questionSet == 1}">
                                                        <option value="${questions.questionId}">${questions.question}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </c:forEach>
                                        </select></td>
                                </tr>
                                <tr>
                                    <td class="space"><label for="securityAnswer-1" title="enter answer to security question 1."></label>answer 1&#58;</td>
                                    <td class="space"><input type="text" name="securityAnswer-1" id="securityAnswer-1"
                                                             title="Answer may consist of a-z, 0-9, underscores, begin with a letter." 
                                                             placeholder="An entry to this field is required."
                                                             maxlength="30"></td>
                                </tr>
                                <tr>
                                    <td><label for="securityQuestion-2">question 2&#58;</label></td>
                                    <td><select name="securityQuestion-2" id="securityQuestion-2">
                                            <c:forEach var="i" begin="1" end="3">
                                                <c:forEach var="questions" items="${questions}">
                                                    <c:if test="${ i == 2 && questions.questionSet == 2}">
                                                        <option value="${questions.questionId}">${questions.question}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </c:forEach>
                                        </select></td>
                                </tr>
                                <tr>
                                    <td class="space"><label for="securityAnswer-2" title="enter answer to security question 2."></label>answer 2&#58;</td>
                                    <td class="space"><input type="text" name="securityAnswer-2" id="securityAnswer-2"
                                                             title="Answer may consist of a-z, 0-9, underscores, begin with a letter." 
                                                             placeholder="An entry to this field is required."
                                                             maxlength="30"></td>
                                </tr>
                                <tr>
                                    <td><label for="securityQuestion-3">question 3&#58;</label></td>
                                    <td><select name="securityQuestion-3" id="securityQuestion-3">
                                            <c:forEach var="i" begin="1" end="3">
                                                <c:forEach var="questions" items="${questions}">
                                                    <c:if test="${ i == 3 && questions.questionSet == 3}">
                                                        <option value="${questions.questionId}">${questions.question}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </c:forEach>
                                        </select></td>
                                </tr>
                                <tr>
                                    <td class="space"><label for="securityAnswer-3" title="enter answer to security question 3."></label>answer 3&#58;</td>
                                    <td class="space"><input type="text" name="securityAnswer-3" id="securityAnswer-3"
                                                             title="Answer may consist of a-z, 0-9, underscores, begin with a letter." 
                                                             placeholder="An entry to this field is required."
                                                             maxlength="30"></td>
                                </tr>
                            </tbody>
                        </table>
                        <div id="postResults" class="smallText "></div>
                        <div id="personal-btn" class="save"></div>
                    </div>
                </fieldset>
            </div>
            <div id="calendarOptions">
                <fieldset id="calendar-prefs-fieldset">
                    <legend id="calendar-legend">Calendar Preferences</legend>
                    <table id="calendar-view-table">
                        <tbody>
                            <tr>
                                <td><label for="defaultCalendarView" title="Default Calendar View.">Default View&#58</label></td>
                                <td><select name="defaultCalendarView" id="defaultCalendarView"></select></td>
                            </tr>
                        </tbody>
                    </table>
                    <div id="postResults" class="smallText "></div>
                    <div id="personal-btn" class="save"></div>
                </fieldset>
                <fieldset id="default-times-fieldset">
                    <legend id="default-times" class="border-bottom">Normal Work Hours</legend>
                    <table id="default-times-table" class="alignRight-td">
                        <tbody>
                            <tr>
                                <td style="padding-top: 10px;"><label for="startTimeM" title="Default Morning Time In.">Morning Time In&#58;</label></td>
                                <td style="padding-top: 10px;">
                                    <select name="startTimeM" id="startTimeM">
                                    </select></td>
                            </tr>
                            <tr>
                                <td class="space"><label for="endTimeM" title="Default Morning Time Out.">Morning Time Out&#58;</label></td>
                                <td class="space">
                                    <select name="endTimeM" id="endTimeM">
                                    </select></td>
                            </tr>
                            <tr>
                                <td style="padding-top: 10px;"><label for="startTimeA" title="Default Afternoon Time In.">Afternoon Time In&#58;</label></td>
                                <td style="padding-top: 10px;">
                                    <select name="startTimeA" id="startTimeA">
                                    </select></td>
                            </tr>
                            <tr>
                                <td><label for="endTimeA" title="Default Afternoon Time Out.">Afternoon Time Out&#58;</label></td>
                                <td>
                                    <select name="endTimeA" id="endTimeA">
                                    </select></td>
                            </tr>
                        </tbody>
                    </table>
                    <div id="postResults" class="smallText "></div>
                    <div id="personal-btn" class="save"></div>
                </fieldset>
            </div>
        </div>
    </section>
</div>

