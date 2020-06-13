<%-- 
    Document   : associate_services
    Created on : Oct 19, 2015, 10:20:03 AM
    Author     : whdobbs
--%>
<%-- associateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>

<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_services.css">
<script src="../js/associateJS/associate_services.js" type="text/javascript"></script>

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

            <h3><a href="<c:url value='associate_services'/>"><strong>Services</strong></a></h3>

            <h3><a href="<c:url value='#'/>">Account</a></h3>
      
            <h3><a href="<c:url value='associate_admin'/>">Admin</a></h3>
            
            <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
        </div>
    </aside>

    <section id="section1">
        <div id="identifier"><h1>Services</h1>
            <div id="loading" title="Processing . . .">
                <h4></h4>
            </div>
        </div>
        <div id="messages">
            <p class="error">${errorMessage}</p>
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div>
        <div id="services-tabs">
            <ul>
                <li><a href="#services">Services</a></li>
                <li><a href="#Products">Products</a></li>
            </ul>
            <div id="services">
                <div id="postResults" class="smallText "></div>
                <table id="services-list-table">
                    <thead>
                        <tr><th title="sort by service id number">ID</th><th title="sort by name">Name</th><th title="sort by description">Description</th>
                            <th title="sort by category">Category</th><th title="sort by time">Time</th><th title="sort by price">Price</th><th title="select all checkboxes" id="select-header">Select</th></tr>
                    </thead>    
                    <tbody>
                    </tbody>
                </table>
                <fieldset id="service-info">
                    <table id="service-info-table" class="alignRight-td table-stripe-main width-td-first">
                        <tbody></tbody>
                    </table>
                </fieldset>

                <form id="serviceform">
                    <fieldset id="editService">
                        <legend class="validateTips error smallText normalText"></legend>
                        <table id="editServiceTable" class="width-100-percent alignRight-td table-stripe-main width-td-first">
                            <tbody></tbody>
                        </table>
                    </fieldset>
                </form>
            </div>
            <div id="products">

            </div>
        </div>
    </section>