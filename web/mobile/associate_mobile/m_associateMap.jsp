<%-- 
    Document   : m_associateMap
    Created on : Dec 18, 2014, 11:57:30 AM
    Author     : williamdobbs
--%>
<script>
    $(document).ready(function () {
        var mapOptions = {
            zoom: 8,
            center: new google.maps.LatLng(36.7468422, -119.7725868),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map($("#map").get(0), mapOptions);
    });
</script>
<!-- ======================= scheduler page ============================== -->
<div data-role="page" id="map-page" data-url="map-page" date-title="Associate Map">

    <div data-role="panel" id="schPanel" data-theme="b"> 
        <ul data-role="listview">
            <li><a href="<c:url value='/mobile/m_associatePage.jsp'/>" data-ajax="false" data-transition="fade">Calendar</a></li>
            <li><a href="<c:url value='/mobile/m_associateScheduler.jsp' />" data-ajax="false" data-transition="slide">Scheduler</a></li>
            <li><a href="<c:url value='/mobile/m_associateClients.jsp' />" data-ajax="false" data-transition="fade">Clients</a></li>
            <li><a href="<c:url value='#' />">Settings</a></li>
            <li><a href="<c:url value='#' />">Messages</a></li>
            <li><a href="<c:url value='#' />">Account</a></li>
            <li><a href="<c:url value='logoutAssociate'/>" data-transition="fade">Logout</a></li>
        </ul>
    </div> 

    <header data-role="header" data-theme="b" class="header-logo">
        <a href="#schPanel" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-icon-bars ui-mini ui-btn-icon-notext">Menu</a>
        <h1>Google map</h1>
    </header>

    <div role="main" class="ui-content" id="map-canvas">
        <!-- map loads here... -->
    </div>
</div>
</body>
</html>
