/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    associateInfo = associateClass.getCurrentAssociateInfo(); // get current associate information
    defaultView = associateInfo.defaultCalendarView; // set associate default view
});

$(document).ready(function () {

//    timer = setInterval(reFresh, 10000
    var currlPath = document.URL;
    var path = currlPath.lastIndexOf(":");
    var net = currlPath.substring(0, path);
    $("#messages").hide();
// goto date
    $("#gotodate").change(function () {
        var calDate = $("#gotodate").val();
        gotoDate(calDate);
    });
    $("#recycle").on("click", function () {
        refreshEvents();
    });
    $("#reloadCal").on("click", function () {
        if (document.URL === net + ":8443/AppointmentStore/mobile/m_associateHome")
        {
            location.replace(net + ":8443/AppointmentStore/mobile/m_associatePage.jsp");
        }
        else {
            location.reload();
        }
    });
    var idlNum;
// function to show associate calendar
    $("#showCal").change(function () {
        idlNum = getIndex.getId("#showCal");
        var key = getIndex.getKey("#showCal");
        showEvts(key, idlNum);
    });
// function to show client calendar
    $("#clientCal").change(function () {
        idlNum = getIndex.getId("#clientCal");
        var key = getIndex.getKey("#clientCal");
        showEvts(key, idlNum);
    });
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultView: defaultView,
        slotDuration: '00:15:00',
        selectable: true,
        selectHelper: true,
        editable: true,
        height: 550,
        eventLimitClick: "popover",
        eventLimit: true, // allow "more" link when too many events
        loading: function (bool) {
            $('#loading').toggle(bool);
            spinner('loading');
        },
        eventClick: function (calEvent, jsEvent) {
            evtClick(calEvent, jsEvent);
        }, // end eventClick function
        select: function (start, end, jsEvent, view) {
            var now = moment().seconds(0);
            var m = start.seconds(0);
            if (start.diff(end) === -86400000 && start.isBefore(now, 'day'))
            {
                $('#popupBasic').popup('open', {transition: "pop"});
                $('#popupBasic p').html("<strong style='color: #c00'>" + "WARNING&#33;" + "</strong>" + "<br>" + "Past Date Selected&#33;" + "<br>"
                        + "<strong>" + jsDate.formatDate(m) + "</strong>" + "<br>" + "<br>" + "Please select the Current or Future Date");
                $("#calendar").fullCalendar('unselect');
            }
            else if (start.isBefore(now)) {

                $('#popupBasic').popup('open', {transition: "pop"});
                $('#popupBasic p').html("<strong style='color: #c00'>" + "WARNING&#33;" + "</strong>" + "<br>" + "Past Date &#38; Time Selected&#33;" + "<br>"
                        + "<strong>" + jsDate.formatDate(m) + " " + jsTime.formatTime(m) + "</strong>" + "<br>" + "<br>" + "Please select a Future Time");
                $("#calendar").fullCalendar('unselect');
            }
            else
            {
                var svcOpt = services.getSvcsStringify(); // list services select optgroup
                var assoOpt = associateClass.getAssociateList(); // list associate select group   
                $("#newEventForm #normal-service").html(svcOpt);
                $("#newEventForm #associate").html(assoOpt);
                $("#firstNm").val("");
                $("#lastNm").val("");
                $("#email").val("");
                $("#mobilePhone").val("");
                $("#notes").val("");
                $("#customerId").val("");
                newEvt(start, end, jsEvent, view);
            }
        }, // end select function

        eventSources: [
            {
                url: '../FullCalendar',
                type: 'GET',
                dataType: "json",
                data: {
                    title: idlNum,
                    start: 'start',
                    end: 'end'
                },
                error: function () {
                }
            }
        ],
        eventTextColor: 'white'
    });
}); // end document ready

function newEvt(start, end, jsEvent, view) {
    $("#firstNm").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "../autocomplete",
                dataType: "json",
                data: {term: request.term},
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            label: item.firstName + " " + item.lastName + " " + item.email,
                            value: item.firstName,
                            firstName: item.firstName,
                            lastName: item.lastName,
                            email: item.email,
                            memberLevel: item.memberLevel,
                            imgUpl: item.imgUpl,
                            mobilePhone: item.mobilePhone,
                            homePhone: item.homePhone,
                            workPhone: item.workPhone,
                            company: item.company,
                            address: item.address,
                            city: item.city,
                            state: item.state,
                            zip: item.zip,
                            id: item.id,
                            memberLevels: item.memberLevels,
                            serviceNormalid: item.serviceNormalid,
                            serviceName: item.serviceName,
                            preferredAssociateName: item.preferredAssociateName,
                            associateName: item.preferredAssociateName,
                            preferredAssociateId: item.preferredAssociateId,
                            smsAdAlerts: item.smsAdAlerts,
                            emailAdAlerts: item.smsAdAlerts,
                            smsApptAlerts: item.smsApptAlerts,
                            emailApptAlerts: item.emailApptAlerts,
                            associate2: item.associateInfo
                        };
                    }));
                }
            });
        },
        minLength: 2,
        select: function (event, ui) {
            var client = {
                firstName: ui.item.firstName,
                lastName: ui.item.lastName,
                email: ui.item.email,
                mobilePhone: ui.item.mobilePhone,
                homePhone: ui.item.homePhone,
                workPhone: ui.item.workPhone,
                company: ui.item.company,
                address: ui.item.address,
                city: ui.item.city,
                state: ui.item.state,
                zip: ui.item.zip,
                id: ui.item.id,
                serviceNormalid: ui.item.serviceNormalid,
                serviceName: ui.item.serviceName,
                imgUpl: ui.item.imgUpl,
                preferredAssociateName: ui.item.preferredAssociateName,
                associateName: ui.item.preferredAssociateName,
                preferredAssociateId: ui.item.preferredAssociateId,
                memberLevel: ui.item.memberLevel,
                emailAdAlerts: ui.item.emailAdAlerts,
                smsAdAlerts: ui.item.smsAdAlerts,
                emailApptAlerts: ui.item.emailApptAlerts,
                smsApptAlerts: ui.item.smsApptAlerts,
                associate2: ui.item.associate2
            };
            var clientObj = new Object;
            clientObj.client = client;
            clientObj.associate2 = ui.item.associate2;

            var assoList = associateClass.getAssociateList(client);
            var norSvcOpt = services.getSvcsStringify(clientObj);

            $("#newEventForm #associate").html(assoList);
            $("#newEventForm #normal-service").html(norSvcOpt);
            var myselect = $("#newEventForm #associate, #newEventForm #normal-service"); // variable for select menu options
            myselect.selectmenu("refresh"); // must refresh select menu after a select

            // calculate and adjust service time
            var svcObj = services.getServiceObj(client.serviceNormalid);
            var s = svcObj.serviceTime;
            var toMom = jsTime.toMomentObj(start, $("#startTime").val());
            var et = toMom.add(s, 'm');
            $("#endTime").val(jsTime.formatTime(et));


            $('#client-info').val(JSON.stringify(client)); // stringify client object
            $("#firstNm").val(ui.item.firstName);
            $('#customerId').val(ui.item.id);
            $('#lastNm').val(ui.item.lastName);
            $('#email').val(ui.item.email);
            $('#company').val(ui.item.company);
            $('#clientid').val(ui.item.id);

            if (typeof ui.item.mobilePhone !== "undefined")
            {
                $("#mobilePhone").val(formatPhone(ui.item.mobilePhone));
            }
//                    $('#title option:selected').html(ui.item.description);
//                    $('#title value').val(ui.item.serviceId); 
        }
    }); // end firstNm autocomplete
    $("#associateName").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "../associateAutoComp",
                dataType: "json",
                data: {term: request.term},
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            label: item.firstName + " " + item.lastName,
                            value: item.firstName,
                            lastName: item.lastName,
                            email: item.email,
                            mobilePhone: item.mobilePhone,
                            id: item.id
                        };
                    }
                    ));
                } // end success function
            });
        },
        minLength: 2,
        select: function (event, ui) {
            $("#associateName").val(ui.item.firstName);
            $("#associatelastName").val(ui.item.lastName);
            $("#associateId").val(ui.item.id);
            $("#associateEmail").val(ui.item.email);
            $("#associateMobilePhone").val(ui.item.mobilePhone);
        }
    }); // end associateName autocomplete
    $("#startTime").val(jsTime.formatTime(start));
    createEvent(start, end, jsEvent, view);
} // end newEvt function

var createEvent = function (start, end, jsEvent, view) {

    $("#send").off();
    $.mobile.changePage("#newEvent", {transition: "slideup"});
    $(".validateTips").html("");
    $("#errorpage h3").html("");
    var endEvtTime = $("#endTime"),
            startEvtTime = $("#startTime"),
            evtHeader = $(".eventHeader"),
            eventdate = $('#eventdate'),
            serviceNm = $("#servicetitle"),
            serviceElem = $("#normal-service"),
            endEvtTime = $("#endTime"),
            allDay;
    startEvtTime.change(function () {
        changeTime();
    });
    serviceNm.change(function () {
        changeTime();
    });

    serviceElem.change(function () {
        var serviceObj = convert.parseJsonObj(serviceElem);
        var s = serviceObj.serviceTime;
        var et;
        var toMom = jsTime.toMomentObj(start, startEvtTime.val());
        et = toMom.add(s, "m");
        endEvtTime.val(jsTime.formatTime(et));
    });
    function changeTime() {
        var serviceObj = convert.parseJsonObj(serviceElem); // get service info object
        var t = serviceObj.serviceTime; // get service time
        var et;
        var toMom = jsTime.toMomentObj(start, startEvtTime.val());
        et = toMom.add(t, "m");
        var fmtet = jsTime.formatTime(et);
        endEvtTime.val(fmtet);
    }

    evtHeader.html("<strong>" + jsDate.formatDate(start) + "</strong>");
    eventdate.val(jsDate.formatDate(start));
    if (start.diff(end) !== -86400000)
    {
        evtHeader.show();
        $("#startend").show();
        var e = start.clone();
        var serviceObj = convert.parseJsonObj(serviceElem); // get service info object
        var t = serviceObj.serviceTime; // get service time
        e.add(t, 'm'); // add service time to start time
        endEvtTime.val(jsTime.formatTime(e));
        startEvtTime.val(jsTime.formatTime(start));
        startEvtTime.selectmenu("refresh"); // must refresh select menu after a select
    }
    else {
        $("#startend").hide();
    }
    $("#send").on("click", function () {

        $("#confirm-btn").off();
        $(".validateTips").html("");
        var clientId = $("#customerId"),
                startMomObj,
                errors = [],
                endObj,
                conflictTable = $("#conflict-table"),
                errorpage = $("#errorpage"),
                confirmpage = $("#confirmpage"),
                errors = [],
                evts = [],
                firstNm = $("#firstNm"),
                lastNm = $("#lastNm"),
                status = $("#status"),
                email = $("#newEventForm #email"),
                clientInfoObj = $("#client-info"),
                mobilePh = $("#newEventForm #mobilePhone"),
                backgrdColor = $("#newEventForm #backgroundColor"),
                associateInfo = $("#newEventForm #associate"),
                nClient = $("#confirmpage .newClient:checked"),
                contactClient = $("#confirmpage .notifyClient:checked"),
                eventNotes = $("#notes"),
                eventdate = $('#eventdate'),
                allFields = $([]).add(firstNm).add(lastNm).add(email).add(mobilePh).add(contactClient)
                .add(nClient).add(endEvtTime).add(startEvtTime).add(clientId).add(eventNotes).add(eventdate).add(errors)
                .add(backgrdColor).add(confirmpage).add(clientInfoObj).add(serviceElem).add(errors);

        var serviceObj = convert.parseJsonObj(serviceElem);
        var associateObj = convert.parseJsonObj(associateInfo);

        function checkLength(o, n, min, max) {
            if (o.val().length > max || o.val().length < min) {
                errors.push("Length of " + "<strong>" + n + "</strong>" + " must be between " +
                        min + " and " + max + " characters");
            }
        }

        function checkRegexp(o, regexp, e, n) {
            if (!(regexp.test(o.val()))) {
                errors.push("<strong>" + e + "</strong>" + n);
            }
        }
        $('#confirmpage p').html("");
        $('#errorpage ul').html("");
        errors.length = 0;
        checkLength(firstNm, "First Name", 3, 45);
        checkLength(lastNm, "Last Name", 3, 45);
        checkLength(email, "Email", 6, 80);
        // checkRegexp(startFormat, /^[01]?\d\/[0-3]\d\/\d{4}$/, "Date format must be " + jsDate.formatDateNew(new Date));
        checkRegexp(firstNm, /^[a-z]([0-9a-z _])+$/i, "First Name ", "may consist of a-z, 0-9, underscores, begin with a letter");
        checkRegexp(lastNm, /^[a-z]([0-9a-z _])+$/i, "Last Name ", "may consist of a-z, 0-9, underscores, begin with a letter");
        // From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
        checkRegexp(email, /^[\w.%+\-]+@[\w.\-]+\.[A-Za-z]{2,6}$/i, "Email ", "format eg. jdoe@doe.com");
//                    bValid = bValid && checkRegexp(password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9");
        checkRegexp(mobilePh, /^\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}/, "Mobile Phone ", "format 999-999-9999 or (999) 999-9999");

        if (start.diff(end) !== -86400000) {
            allDay = false;
            var evtTimeObj = new Object();
            var t = serviceObj.serviceTime; // get service time
            startMomObj = jsTime.toMomentObj(start, startEvtTime.val());
            var stMomClone = startMomObj.clone();
            endObj = stMomClone.add(t, 'm'); // add service time to start time
            evtTimeObj.start = startMomObj;
            evtTimeObj.end = endObj;
            evtTimeObj.allDay = allDay;
            evtTimeObj.associateId = associateObj.id;
            evtTimeObj.eventId = 1234;
            evts = eventConflictCk(evtTimeObj); // check for event conflicts
            if (evts.length > 0) // alert user if event conflicts exist
            {
                $("#errorpage ul").hide();
                $("#errorpage h3").html("Scheduling Conflict!");
                conflictTable.show();
                $('#conflict-table tbody').html("<tr><th>Client</th><th>Service</th><th>Time</th><th>Associate</th></tr>");
                $.each(evts, function (index, value) {
                    $('#conflict-table tbody').append(
                            "<tr>"
                            + "<td>" + value.firstName + " " + value.lastName + "</td><td>"
                            + stripName(value.title) + "</td>" + "<td>" + jsTime.formatTime(value.start) + "</td>"
                            + "<td>" + value.associateName + "</td>"
                            + "</tr>");
                });
                errorpage.popup('open', {transition: "pop"});
            }
        }
        else if (start.diff(end) === -86400000) {
            allDay = true;
            var evtTimeObj = new Object();
            var t = serviceObj.serviceTime; // get service time
            startMomObj = jsTime.toMomentObj(start, eventdate.val());
            var stMomClone = startMomObj.clone();
            endObj = stMomClone.add(t, 'm'); // add service time to start time
            evtTimeObj.start = stMomClone;
            evtTimeObj.end = endObj;
            evtTimeObj.allDay = allDay;
            evtTimeObj.eventId = 1234;
        }
// loop through errors
        if (errors.length > 0)
        {
            errorpage.popup('open', {transition: "pop"});
            $("#errorpage ul").show();
            conflictTable.hide();
            $("#errorpage h3").html("Please correct these Errors!");
            $.each(errors, function (index, value) {
                $('#errorpage ul').append("<li>" + value + "</li>");
            });
        }
        else if (errors.length === 0)
        {
            var serviceObj = convert.parseJsonObj(serviceElem);
            var isAllDay = "No";
            if (evtTimeObj.allDay === true) {
                isAllDay = "Yes";
            }
            confirmpage.popup('open', {transition: "pop"});
            var serviceId = serviceObj.serviceId;
            var title = serviceObj.description;
            var serviceTime = serviceObj.serviceTime;
            $('#confirmpage ul').html("<li>" + "Client: " + "<strong>" + firstNm.val() + " " + lastNm.val() + "</strong>" + "</li>"
                    + "<li>" + "Service: " + "<strong>" + title + "</strong>" + "</li>"
                    + "<li>" + "Date: " + "<strong>" + jsDate.formatDate(start) + "</strong>" + "</li>"
                    + "<li>" + "Time: " + "<strong>" + jsTime.formatTime(startMomObj) + "</strong>" + "</li>"
                    + "<li>" + "All Day: " + "<strong>" + isAllDay + "</strong>" + "</li>"
                    + "<li>" + "Service ET: " + "<strong>" + minutesFormat(serviceTime) + "</strong>" + "</li>"
                    + "<li>" + "Service ID: " + "<strong>" + serviceId + "</strong>" + "</li>");
            if (eventNotes.val() !== "")
            {
                $('#confirmpage ul').append("<li>" + "Notes: " + "<strong>" + eventNotes.val() + "</strong>" + "</li>");
            }
        }
        $("#confirm-btn").on("click", function () {
            var serviceId = serviceObj.serviceId;
            var title = serviceObj.description;
            var serviceTime = serviceObj.serviceTime;
            var customerId = clientId.val();
            if (customerId === "")
            {
                customerId = 0;
            }
            var firstName = firstNm.val(),
                    lastName = lastNm.val(),
                    notes = eventNotes.val(),
                    emailAddress = email.val();
            var associateName = associateObj.firstName,
                    associateId = associateObj.id,
                    mobilePhone = mobilePh.val(),
                    backgroundColor = backgrdColor.val(),
                    statusId = status.val(),
                    notifyClient = contactClient.is(':checked'),
                    newClient = nClient.is(':checked');
            // embedded variables
            var eventId = 1234,
                    textColor = null,
                    editable = true,
                    durationEditable = true,
                    color = null,
                    client;

            if (clientInfoObj.val() === "") // if client not in database
            {
                client = {
                    firstName: firstName,
                    lastName: lastName,
                    emailAddress: emailAddress,
                    associateName: associateName,
                    associateId: associateId,
                    mobilePhone: mobilePhone
                };
            }
            else {
                // convert client string data to js object
                client = convert.parseJsonObj(clientInfoObj);
            }
            client.associate2 = associateObj;
            // render the event to calendar
            $("#calendar").fullCalendar('renderEvent',
                    {
                        title: title,
                        start: startMomObj,
                        end: endObj,
                        allDay: allDay,
                        backgroundColor: backgroundColor,
                        color: color,
                        editable: editable,
                        durationEditable: durationEditable,
                        textColor: textColor,
                        eventId: eventId,
                        serviceId: serviceId,
                        serviceTime: serviceTime,
                        firstName: firstName,
                        lastName: lastName,
                        emailAddress: emailAddress,
                        mobilePhone: mobilePhone,
                        client: client,
                        associate2: associateObj,
                        associateName: associateName,
                        associateId: associateId,
                        notes: notes,
                        customerId: customerId,
                        statusId: statusId,
                        notifyClient: notifyClient,
                        newClient: newClient
                    },
                    true // make the event "stick"
                    );
            var eventObj = new Object();
            eventObj.title = title;
            eventObj.start = startMomObj;
            eventObj.end = endObj;
            eventObj.allDay = allDay;
            eventObj.backgroundColor = backgroundColor;
            eventObj.eventId = eventId;
            eventObj.serviceId = serviceId;
            eventObj.serviceTime = serviceTime;
            eventObj.firstName = firstName;
            eventObj.lastName = lastName;
            eventObj.emailAddress = emailAddress;
            eventObj.mobilePhone = mobilePhone;
            eventObj.associateId = associateId;
            eventObj.associate2 = associateObj;
            eventObj.client = client;
            eventObj.notes = notes;
            eventObj.associateName = associateName;
            eventObj.customerId = customerId;
            eventObj.statusId = statusId;
            eventObj.notifyClient = notifyClient;
            eventObj.newClient = newClient;
            eventObj.action = "add";
            // in case client info has changed, add client data to client object
            eventObj.client.firstName = firstName;
            eventObj.client.lastName = lastName;
            eventObj.client.email = emailAddress;
            eventObj.client.mobilePhone = mobilePhone;
            eventObj.client.preferredAssociateName = associateName;
            eventObj.client.preferredAssociateId = associateId;
            eventObj.client.serviceNormalid = serviceId;
            // post new event to server
            postToServer(eventObj);
            $("#calendar").fullCalendar('unselect');
            allFields.val("");
        });
    }); // end on send click
}; // end create event function

var fetchError = "There was an error while fectching calendar events!" + "<br>"
        + "Click Calendar on the sidebar or refresh the page to re-fetch calendar events." + "<br>"
        + "Contact support if problem persists.";
function evtClick(calEvent, jsEvent, view) {
    $("#yes").off();
    var isAllDay = "No",
            eventInfo;
    if (calEvent.allDay === true)
    {
        isAllDay = "Yes";
        //calEvent.serviceTime = "n/a";
    }
    var associateImgId = 0;
    if (calEvent.associate2.imgUpl === true)
    {
        associateImgId = calEvent.associate2.id;
    }
    var clientImgId = 0;
    if (calEvent.client.imgUpl === true)
    {
        clientImgId = calEvent.customerId;
    }
    if (calEvent.userType === "associate")
    {
        $(".delete-appt").html("Remove Available Time");
        $("#event-header").text("Available Info");
        $(".del-event-ui-title").html("Are you want Remove this available Time Slot?");
        $("#statusField").hide();
        $("#del-conrolgroup").hide();
        eventInfo =
                "<tr>" + "<td title='associate first and last name'>" + "associate:" + "</td>" + "<td>" + calEvent.associate2.firstName + " " + calEvent.associate2.lastName + "&nbsp;" + '<img src="../img/associates/'
                + associateImgId + '.png" alt="associate img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate email address'>" + "email:" + "</td>" + "<td>" + "<a href='mailto:" + calEvent.associate2.email + " '>" + calEvent.associate2.email + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='mobile phone'>" + "mobile&#35;" + "</td>" + "<td>" + formatPhone(calEvent.associate2.mobilePhone)
                + "<a href='tel:" + calEvent.associate2.mobilePhone + "'class='call-btn ui-btn ui-alt-icon ui-nodisc-icon ui-btn-inline ui-icon-phone ui-btn-icon-notext'></a>"
                + "<a href='sms:" + calEvent.associate2.mobilePhone + "'class='call-btn ui-btn ui-alt-icon ui-nodisc-icon ui-btn-inline ui-icon-comment ui-btn-icon-notext'></a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='available date'>" + "date:" + "</td>" + "<td>" + jsDate.formatDate(calEvent.start) + "</td>" + "</tr>"
                + "<tr>" + "<td title='available start time'>" + "start:" + "</td>" + "<td>" + jsTime.formatTime(calEvent.start) + "</td>" + "</tr>"
                + "<tr>" + "<td title='available end time'>" + "end:" + "</td>" + "<td>" + jsTime.formatTime(calEvent.end) + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate available time id number'>" + "id&#35;" + "</td>" + "<td>" + calEvent.eventId + "</td>" + "</tr>";
    }
    else {
        $(".delete-appt").html("Delete Appointment");
        $("#event-header").text("Event Info");
        $("#statusField").show();
        $("#del-conrolgroup").show();
        $(".del-event-ui-title").html("Are you sure you want to delete this Appointment?");
        eventInfo =
                "<tr>" + "<td title='client first and last name'>" + "client:" + "</td>" + "<td>" + calEvent.firstName + " " + calEvent.lastName + '  <img src="../img/clients/'
                + clientImgId + '.png" alt="client img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='client email address'>" + "email:" + "</td>" + "<td>" + "<a href='mailto:" + calEvent.emailAddress + " '>" + calEvent.emailAddress + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='mobile phone'>" + "mobile:" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(calEvent.client.mobilePhone) + " '>" + formatPhone(calEvent.client.mobilePhone) + "</a>"
                + "<a href='tel:" + formatPhone(calEvent.client.mobilePhone) + "'class='call-btn ui-btn ui-alt-icon ui-nodisc-icon ui-btn-inline ui-icon-phone ui-btn-icon-notext'></a>"
                + "<a href='sms:" + formatPhone(calEvent.client.mobilePhone) + "'class='call-btn ui-btn ui-alt-icon ui-nodisc-icon ui-btn-inline ui-icon-comment ui-btn-icon-notext'></a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='service to perform'>" + "service:" + "</td>" + "<td>" + stripName(calEvent.title) + "</td>" + "</tr>"
                + "<tr>" + "<td title='date of service'>" + "date:" + "</td>" + "<td>" + jsDate.formatDate(calEvent.start) + "</td>" + "</tr>"
                + "<tr>" + "<td title='service start time'>" + "start:" + "</td>" + "<td>" + jsTime.formatTime(calEvent.start) + "</td>" + "</tr>"
                + "<tr>" + "<td title='service end time'>" + "end:" + "</td>" + "<td>" + jsTime.formatTime(calEvent.end) + "</td>" + "</tr>"
                + "<tr>" + "<td title='event id number'>" + "event:" + "</td>" + "<td>" + calEvent.eventId + "</td>" + "</tr>"
                + "<tr>" + "<td title='estimated time of service'>" + "eta:" + "</td>" + "<td>" + minutesFormat(calEvent.serviceTime) + "</td>" + "</tr>"
                + "<tr>" + "<td title='is event scheduled all day'>" + "all day:" + "</td>" + "<td>" + isAllDay + "</td>" + "</tr>"
                + "<tr>" + "<td title='client notes'>" + "notes:" + "</td>" + "<td>" + calEvent.notes + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate first name'>" + "associate:" + "</td>" + "<td>" + calEvent.associate2.firstName
                + '  <img src="../img/associates/' + associateImgId + '.png" alt="associate img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "status:" + "</td>" + '<td style="color:' + calEvent.serviceStatus.statusColor + '">' + calEvent.serviceStatus.statusName + "</td>" + "</tr>";
    }
    $("#updateStatus").off("change").submit();
    var currentStatusId = calEvent.serviceStatus.statusId;
    var statusId = $("#updateStatus").val(currentStatusId);
    $("#updateStatus").on("change", function () {
        statusId = statusId.val(); // get event status ID
        updateStatus(calEvent, currentStatusId, statusId);
    });
    $.mobile.changePage("#event", {transition: "slideup"});
    var event = $("#eventInfo tbody");
    event.html(eventInfo);
    $("#updateStatus").val(currentStatusId).selectmenu("refresh");
    $("#yes").on("click", function () {
        var restoreTime = $("#restoreTime:checked").val();
        var notifyClient = $("#notifyClient:checked").val();
        if (restoreTime === "true") {
            calEvent.restoreTime = true;
        }
        else {
            calEvent.restoreTime = false;
        }
        if (notifyClient === "true") {
            calEvent.notifyClient = true;
        }
        else {
            calEvent.notifyClient = false;
        }
        removeEvent(calEvent);
        $.mobile.changePage("#mainpage", {transition: "slideup"});
        refreshEvents();
    });
}
