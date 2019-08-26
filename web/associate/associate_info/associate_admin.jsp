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
            <form id="admin-password"action="<c:url value='checkAssociate'/>" method=post>
            <fieldset>
                    <table>
                        <thead>
                            <tr>
                                <th></th><th>Add User Account</th>
                            </tr>
                            <tr>
                                <th></th><th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%--
                            <c:if test="${!empty validationErrorFlag}">
                                <tr>
                                    <td colspan="2" style="text-align:left">
                                        <span class="error smallText">Please provide valid entries for the following field(s):
                                            <c:if test="${!empty emailError}">
                                                <br><span><strong>email address</strong>&nbsp;(e.g., j.doe@vise.com)</span>
                                            </c:if>
                                            <c:if test="${!empty passwordError}">
                                                <br><span><strong>password</strong>&nbsp;must be at least 8 characters</span>
                                            </c:if>
                                            <c:if test="${notFoundMessage eq true}">
                                                <br><span><strong>email &amp;&nbsp;password</strong>&nbsp;<br>Associate not Found</span>
                                                </c:if>
                           
                                        </span>
                                    </td>
                                </tr>
                            </c:if>
                             --%>
                            <tr>
                                <td><label for="emailAddress">Email Address</label></td>
                                <td><input type="email" name="emailAddress" id="emailAddress" autofocus required
                                           value="${param.emailAddress}" placeholder="email address"></td>
                            </tr>
                            <tr>
                                <td><label for="password">Password</label></td>
                                <td><input type="password" name="password" id="password" required
                                           value="${param.password}" placeholder="password"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td> <select id="">
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
            </form>
        </section>
</div>