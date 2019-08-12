<%-- asociateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>
<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_index.css">

<div id="main-section">
    <section id="section1">
        <div id="identifier"><h1>Account</h1>
            <p class="error">${message}</p>
        </div>

        <aside>
            <div id="aside-div">
                <img src="${initParam.associateImagePath}${associateRecord.id}.png"
                     alt="${associateRecord.id}"  id="associateImg" alt="associate picture">
                <div class="smallText" id="associateFirstName">${associateRecord.firstName}</div>

                <h3><a href="<c:url value='calendarAssociate'/>">Calendar</a></h3>

                <h3><a href="<c:url value='scheduler'/>">Scheduler</a></h3>

                <h3><a href="<c:url value='associate_clients'/>">Clients</a></h3>        

                <h3><a href="<c:url value='associateSettings'/>">Settings</a></h3>

                <h3><a href="<c:url value='#'/>">Messages</a></h3>

                <h3><a href="<c:url value='#'/>"><strong>Account</strong></a></h3>

                <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
            </div>
        </aside>

        <table class="associateTable" >

            <tr class="header">
                <th colspan="2" style="color: white">associate profile</th>
            </tr>
            <tr>
                <td style="width: 160px"><strong>associate id:</strong></td>
                <td id="associateTableCenter">${associateRecord.id}</td>
            </tr>
            <tr>
                <td><strong>name:</strong></td>
                <td>${associateRecord.firstName} ${associateRecord.lastName}</td>
            </tr>
            <tr>
                <td><strong>email:</strong></td>
                <td>${associateRecord.email}</td>
            </tr>
            <tr>
                <td><strong>home phone:</strong></td>
                <td>${associateInfo.homePhone}</td>
            </tr>
            <tr>
                <td><strong>work phone:</strong></td>
                <td>${associateInfo.workPhone}</td>
            </tr>
            <tr>
                <td><strong>mobile phone:</strong></td>
                <td>${associateInfo.mobilePhone}</td>
            </tr>
            <tr>
                <td><strong>other phone:</strong></td>
                <td>${associateInfo.otherPhone}</td>
            </tr>
            <tr>
                <td><strong>address:</strong></td>
                <td>${associateInfo.address}</td>
            </tr>
            <tr>
                <td><strong>city:</strong></td>
                <td>${associateInfo.city}</td>
            </tr>
            <tr>
                <td><strong>state:</strong></td>
                <td>${associateInfo.state}</td>
            </tr>
            <tr>
                <td><strong>zip:</strong></td>
                <td>${associateInfo.zip}</td>
            </tr>
        </table>
    </section>

</div>

