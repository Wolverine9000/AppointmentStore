<%-- 
    Document   : associate_messages
    Created on : Aug 8, 2015, 3:49:05 PM
    Author     : williamdobbs
--%>
<%-- asociateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>
<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_messages.css">
<script src="../js/associateJS/associate_messages.js" type="text/javascript"></script>
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

            <h3><a href="<c:url value='associate_messages'/>"><strong>Messages</strong></a></h3>

            <h3><a href="<c:url value='associate_services'/>">Services</a></h3>

            <h3><a href="<c:url value='#'/>">Account</a></h3>
      
            <h3><a href="<c:url value='associate_admin'/>">Admin</a></h3>
            
            <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
        </div>
    </aside>

    <section id="section1">
        <div id="identifier"><h1>Messages</h1></div>
        <div id="loading" title="Processing . . .">
            <h4></h4>
        </div>
        <div id="messages">
            <p class="error">${errorMessage}</p>
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div>
        <div id="message-tabs">
            <ul>
                <li><a href="#overview">Overview</a></li>
                <li><a href="#senttext">Sent Texts</a></li>
                <li><a href="#scheduled">Scheduled</a></li>
                <li><a href="#new-client-request">New Client Request</a></li>
                <li><a href="#inbox">Inbox</a></li>
            </ul>
            <div id="overview">
            </div>
            <div id="senttext">

                <table class="message-list-table roundCorners-td-fc-4px roundCorners-td-lc-4px table-stripe-main-odd">
                    <thead>
                        <tr><th title="sort by ID">ID</th><th title="sort by To">To</th><th title="sort by Message">
                                Message</th><th title="sort by SMS&#35;">SMS&#35;</th><th title="sort by Sent Date">Sent On</th><th title="sort by Status">Status</th></tr>
                    </thead>    
                    <tbody>
                    </tbody>
                        <button class="next-page next-page-size margin-right"></button>
                        <button class="prev-page next-page-size"></button>
                        <button class="first-page next-page-size"></button>
                </table>
                         <button class="next-page next-page-size margin-right"></button>
                        <button class="prev-page next-page-size"></button>
                        <button class="first-page next-page-size"></button>
                <fieldset id="message-info">
                    <table class="messgage-info-table alignRight-td table-stripe-main width-td-first">
                        <tbody></tbody>
                    </table>
                </fieldset>
            </div>
            <div id="scheduled">
                <table id="scheduled-list-table" class="scheduled-list-table roundCorners-td-fc-4px roundCorners-td-lc-4px
                       table-stripe-main-odd">
                    <thead>
                        <tr><th title="sort by ID">ID</th><th title="sort by To">To</th><th title="sort by Message">
                                Message</th><th title="sort by Sent Date">Will Send On</th><th title="sort by Status">Status</th></tr>
                    </thead>    
                    <tbody>
                    </tbody>
                </table>
                <fieldset id="scheduled-info">
                    <table class="scheduled-info-table alignRight-td table-stripe-main width-td-first">
                        <tbody></tbody>
                    </table>
                </fieldset>
                <div id="confirmDelete">
                    <fieldset id="confirm-delete">
                        <table class="confirm-del-table alignRight-td table-stripe-main width-td-first">
                            <tbody></tbody>
                        </table>
                    </fieldset>
                </div>
            </div>
            <div id="new-client-request">
                <div id="mobile-requests">
                    <fieldset id="new-client-request-fieldset-1">
                        <legend
                            title="Enter mobile number&#40;s&#41; &#45; a text message request will be sent to become a client member">Invite New Clients
                        </legend>
                        <div class="validateTips error smallText normalText"></div>
                        <table id="table-mobile-requests-1">
                            <thead class="smallText">
                                <tr><th>Mobile Number</th></tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><input type="phone" title="enter a 10-digit cell phone number"
                                               name="cell-numbers" id="cell-number-1" placeholder="10-digit number" value=""></td>
                                    <td><input type="phone" title="enter a 10-digit cell phone number" 
                                               name="cell-numbers-2" id="cell-number-2" placeholder="10-digit number" value=""></td>
                                </tr>
                                <tr>
                                    <td><input type="phone" title="enter a 10-digit cell phone number"
                                               name="cell-numbers-3" id="cell-number-3" placeholder="10-digit number" value=""></td>
                                    <td><input type="phone" title="enter a 10-digit cell phone number" 
                                               name="cell-numbers-4" id="cell-number-4" placeholder="10-digit number" value=""></td>
                                </tr>
                            </tbody>
                        </table>
                        <table id="table-mobile-requests-2">
                            <thead class="smallText">
                                <tr><th>Mobile Number</th></tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><input type="phone" title="enter a 10-digit cell phone number" 
                                               name="cell-numbers-5" id="cell-number-5" placeholder="10-digit number" value=""></td>
                                    <td><input type="phone" title="enter a 10-digit cell phone number" 
                                               name="cell-numbers-6" id="cell-number-6" placeholder="10-digit number" value=""></td>
                                </tr>
                                <tr>
                                    <td><input type="phone" title="enter a 10-digit cell phone number" 
                                               name="cell-numbers-7" id="cell-number-7" placeholder="10-digit number" value=""></td>
                                    <td><input type="phone" title="enter a 10-digit cell phone number" 
                                               name="cell-numbers-8" id="cell-number-8" placeholder="10-digit number" value=""></td>
                                </tr>
                            </tbody>
                        </table>
                        <div id="postResults" class="smallText "></div>
                        <button class="invite button-size-1"></button>
                    </fieldset>
                </div>

                <div id="recent-requests">
                    <fieldset id="list-request-requests">
                        <legend>Recent Requests</legend>
                        <table id="table-recent-requests" class="table-stripe-main-odd tbody roundCorners-td-fc-4px roundCorners-td-lc-4px">
                            <thead class="smallText">
                            <th>Mobile Number</th><th>Response</th><th>Sent On</th>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </fieldset>
                </div>
                <fieldset id="fieldset-send-invite">
                    <legend style="border: none"></legend>
                    <table id="table-send-invite" class="table-stripe-main-odd tbody roundCorners-td-fc-4px roundCorners-td-lc-4px width-100-percent">
                        <thead></thead>
                        <tbody></tbody>
                    </table>
                </fieldset>
            </div>
            <div id="inbox">
            </div>
        </div>    
    </section>
</div>
