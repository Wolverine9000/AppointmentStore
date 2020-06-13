<%-- 
    Document   : book_appointment
    Created on : May 28, 2012, 1:22:10 PM
    Author     : williamdobbs
--%>
<script src="js/jquery.ui.datepicker.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<meta charset="utf-8">

<script>
    $(function() {
        $("#datepicker").datepicker({
            minDate: -0,
            // maxDate: "+2M +10D",
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            selectOtherMonths: true,
            showOtherMonths: true
        });
    });
</script>
<p class="error">${notFoundMessage}</p>
<p class="good">${foundMessage}</p>
<p class="error">${errorMessage}</p>

<div class="bookAppointmentBox">

    <form action="listAssociates" onchange="submit()" method="post">
        <p>Date: <input name="date" type="text" onchange="submit()" value="${date}"id="datepicker"></p>
    </form>

    <form action="listServices" method="post">
        Available Associates:
        <select name="associateId"  onclick="submit()" method="post">
            <c:forEach var="associateSchedules" items="${associateSchedules}">
                <c:choose>
                    <c:when test="${associateSchedules.id == associateId}">
                        <option value="${associateSchedules.id}" selected="selected">${associateSchedules.firstName}&nbsp;${associateSchedules.lastName}
                        </option>
                    </c:when>
                    <c:otherwise>
                        <option value="${associateSchedules.id}">
                            ${associateSchedules.firstName}&nbsp;${associateSchedules.lastName}
                        </option>
                    </c:otherwise>
                </c:choose>    
            </c:forEach>
        </select>
    </form>

    <br>

    <form action="listTimes" onclick="submit()" method="post">
        select a service:
        <select name="serviceId" onclick="submit()" >
            <c:forEach var="service" items="${services}">
                <c:choose>
                    <c:when test="${service.productId == serviceId}">
                        <option value="${service.serviceId}" selected="selected">${service.description}&nbsp;${service.serviceTime}:min
                        </option>
                    </c:when>
                    <c:otherwise>
                        <option value="${service.serviceId}">
                            ${service.description}&nbsp;${service.serviceTime}:min</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
        </select>
    </form>
    <br>
    <form action="bookAppointment" method="post">
        select a time:
        <select name="time">
            <c:forEach var="associateTimes" items="${associateTimes}">
                <option value="${associateTimes.associateAvailTimestamp}" selected="selected">
                    ${associateTimes.timestampFormat}</option>
                </c:forEach>
        </select>
        <br><br>
        <input type="submit" value="book appointment">
    </form>

</div><!-- End demo -->

