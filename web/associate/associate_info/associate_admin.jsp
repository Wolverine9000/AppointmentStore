<%-- 
    Document   : associate_admin
    Created on : Aug 17, 2019, 6:10:13 PM
    Author     : whdobbs
--%>
<%-- associateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>

<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_admin.css">
<script src="../js/associateJS/associate_admin.js" type="text/javascript"></script>

<div id="main-section">
    <aside>
        <div id="aside-div">
            <img src="${initParam.associateImagePath}${associateRecord.id}.png"
                 alt="${associateRecord.id}"  id="associateImg" alt="associate picture">
            <div class="smallText" id="associateFirstName">${associateRecord.firstName}</div>

            <h3><a href="<c:url value='calendarAssociate'/>">Calendar</a></h3>

            <h3><a href="<c:url value='scheduler'/>">Scheduler</a></h3>

            <h3><a href="<c:url value='associate_clients'/>">Clients</a></h3>

            <h3><a href="<c:url value='associateSettings'/>">Settings</a></h3>

            <h3><a href="<c:url value='associate_messages'/>">Messages</a></h3>

            <h3><a href="<c:url value='associate_services'/>">Services</a></h3>

            <h3><a href="<c:url value='#'/>">Account</a></h3>

            <h3><a href="<c:url value='associate_admin'/>"><strong>Admin</strong></a></h3>

            <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
        </div>
    </aside>
    <section id="section1">
        <div id="identifier"><h1>Admin</h1></div>
        <div id="loading" title="Processing . . .">
            <h4></h4>
        </div>
        <div id="admin">
            <p class="error">${errorMessage}</p>
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div>
        <form id="admin-password">
            <fieldset id="admin-fieldset-1">
                <legend></legend>
                <div class="validateTips error smallText normalText"></div>
                <div id="admin"></div>
                <div id="postResults" class="smallText "></div>
                <div id="admin-btn"></div>
            </fieldset>
        </form>
        <fieldset>
            <table id="admin-accounts-table">
                <thead>
                    <tr>
                        <th></th><th>Add/Update Account</th>
                    </tr>
                    <tr>
                        <th></th><th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><label for="emailAddress">Email Address:</label></td>
                        <td><input type="email" name="emailAddress" id="admin-emailAddress" autofocus required
                                   placeholder="email address"></td>
                    </tr>
                    <tr>
                        <td><label for="password-admin-1">Enter Password:</label></td>
                        <td><input type="password" name="password-admin-1" id="password-admin-1" required
                                   placeholder="password">
                        </td>
                    </tr>
                    <tr>
                        <td><label for="password-admin-2">Re-Enter Password:</label></td>
                        <td><input type="password" name="password-admin-2" id="password-admin-2" required
                                   placeholder="password">
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td> <select id="admin-type">
                                <option value="super-admin">Super Admin</option>
                                <option value="normal-admin">Normal Admin</option>
                                <option value="system-email">System Email</option>
                                <option value="manager">Manager</option>
                                <option value="programmer">Programmer</option>
                                <option value="service">Service</option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><button type="submit" class="submit"></button></td>
                    </tr>

                </tbody>
            </table>
            <div id="postResults" class="smallText "></div>
            <div id="personal-btn"></div>
        </fieldset>

    </section>
</div>