/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// refresh the page every minute unless someone presses a key or moves the mouse.
//var time = new Date().getTime();
//     $(document.body).bind("mousemove keypress", function(e) {
//         time = new Date().getTime();
//     });
//
//     function refresh() {
//         if(new Date().getTime() - time >= 60000)
//             window.location.reload(true);
//         else
//             setTimeout(refresh, 10000);
//     }
//
//     setTimeout(refresh, 10000); // set refresh page
//
$(function () {
    associateInfo = associateClass.getCurrentAssociateInfo(); // get current associate information
    defaultView = associateInfo.defaultCalendarView; // set associate default view

    // populate date entry placeholder wtth today's date
    var todaysDate = $.datepicker.formatDate("mm/dd/yy", new Date());
    $('div#main-section input#calendarAssociate.hasDatepicker').attr("placeholder", todaysDate);
});
// Full Calendar
$(document).ready(function () {
    serviceListing = serviceList.getServiceList();
    serviceCategories = serviceList.getServiceCategories();
    associateListArr = getAssociates();
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
    // goto date
    $("#calendarAssociate").change(function () {
        var calDate = $("#calendarAssociate").val();
        gotoDate(calDate);
    });
    $("#newEvent").hide();
    $("#delete").hide();
    $(".calEvent").hide();
    $("#errorMsg").hide();
    var errorMsg = $("#errorMsg"),
            conflickMsg = "<normal style='color: #c00;'" + ">" + "Scheduling Conflict!" + "</normal>" + "<br>",
            th = "<tr>" + "<th title='client first name'>" + "client" + "</th>" + "<th title='appointment ID number'>"
            + "event&#35;" + "</th>" + "<th title='service to perform'>" + "service" + "<th title='service start time'>" + "start" + "</th>"
            + "<th title='service end time'>" + "end" + "</th>"
            + "<th title='current appointment status'>" + "status" + "</th>" + "<th title='check to cancel appointment'>" + "cancel" + "</th>" + "</tr>",
            conflickMsg2,
            s = "";
    //spinner('calendar', -500);

// full calendar shortcut
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,today,next',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        dragOpacity: {
            month: .2,
            '': .5
        }, // end dragOpacity
        loading: function (bool) {
            if (bool)
            {
                openLoading();
                $("#loading").dialog("open"); // open loading element
                $("#loading h4").html("Loading data.  Please wait...");
                spinner('loading', 'auto');
            }
            else
            {
                $('#loading').data('spinner').stop(); // Stop the spinner
                $("#loading h4").html("Done!");
                $("#loading").dialog("close"); // close loading element
            }
        },
        defaultView: defaultView,
        nowIndicator: true,
        slotDuration: '00:15:00',
        selectable: true,
        selectHelper: true,
        longPressDelay: 500,
        eventLimitClick: "popover",
        eventLimit: true, // allow "more" links when too many events
        //theme: true,
        dayClick: function () {
            $(this).css('background-color', 'dfeffc');
        },
        eventClick: function (calEvent, jsEvent) {
            evtClick(calEvent, jsEvent);
        }, // end eventClick function
        select: function (start, end, jsEvent, view) {
            $("#accordion-4 table.last-visits-table tbody").empty(); // clear contents of last five visits table tbody
            newEvent(start, end, jsEvent, view);
        }, // end select function
        editable: true,
        // add event name to title attribute on mouseover
//        eventMouseover: function (event, jsEvent, view) {
//            if (view.name !== 'agendaDay') {
//                $(jsEvent.target).attr('title', event.title);
//            }
//        },
        eventDrop: function (event, revertFunc) {
            event.action = "add";
            var hasConflict = eventConflictCk(event); // check for time conflict
            if (hasConflict.length > 0)
            {
                openMsg(event, revertFunc); // open errorMsg window
                errorMsg.dialog("open"); // open erroMsg jQuery dialog window
                errorMsg.dialog("option", "minWidth", 350);
                errorMsg.dialog("option", "maxWidth", 600);
                if (hasConflict.length === 1)
                {
                    conflickMsg2 = "<u>" + "overlaps with another appointment" + "</u>";
                }
                else
                {
                    conflickMsg2 = "<u>" + "overlaps with mulitple appointments" + "</u>";
                }

                $("#errorMsg div").html(conflickMsg + "<br>" + " Moving client:" + "<strong>" + event.client.firstName + "</strong>"
                        + " scheduled service:" + "<strong>" + stripName(event.title) + "</strong>" + "<br>" + conflickMsg2);
                $("#errorTable2 tbody").html("<tr>" + "<th title='client first name'>" + "client" + "</th>" + "<th title='appointment ID number'>"
                        + "event&#35;" + "</th>" + "<th title='service to perform'>" + "service" + "<th title='service start time'>" + "start" + "</th>"
                        + "<th title='service end time'>" + "end" + "</th>" + "<th title='current appointment status'>" + "status" + "</th>" + "</tr>");
                $("#errorTable2 tbody").append(
                        "<tr>" + "<td>" + event.client.firstName + "</td>" + "<td>" + event.eventId + "</td>" + "<td>" + stripName(event.title) + "</td>" + "<td>" + jsTime.fmtTimeAlt(event.start) + "</td>"
                        + "<td>" + jsTime.fmtTimeAlt(event.end) + "</td>" + '<td style="color:' + event.services.serviceStatus.statusColor + '">' + event.serviceStatus.statusName + "</td>" + "</tr>");
                $(".errorTable tbody").html(th);
                $.each(hasConflict, function (index, value) {
                    $(".errorTable tbody").append(
                            "<tr>" + "<td>" + value.client.firstName + "</td>" + "<td>" + value.eventId + "</td>" + "<td>" + stripName(value.title) + "</td>" + "<td>" + jsTime.fmtTimeAlt(value.start) + "</td>"
                            + "<td>" + jsTime.fmtTimeAlt(value.end) + "</td>"
                            + '<td style="color:' + value.services.serviceStatus.statusColor + '">' + value.services.serviceStatus.statusName + "</td>" + "<td>" + '<input type="checkbox" id="cancel" value="' + value.eventId + '">' + "</td>" + "</tr>");
                });
            }
            else
            {
                event = updateEvent(event);
                postToServer(event);
            }
        },
        eventResize: function (event, revertFunc) {
//            event.serviceTime = event.end.diff(event.start, 'minutes');
//            event.action = "add";
            var hasConflict = eventConflictCk(event); // check for time conflict
            if (hasConflict.length > 0)
            {
                openMsg(event, revertFunc); // open errorMsg window
                errorMsg.dialog("open"); // open erroMsg jQuery dialog window
                errorMsg.dialog("option", "minWidth", 350);
                errorMsg.dialog("option", "maxWidth", 600);
                if (hasConflict.length === 1)
                {
                    conflickMsg2 = "<u>" + "overlaps with another appointment" + "</u>";
                }
                else
                {
                    conflickMsg2 = "<u>" + "overlaps with mulitple appointments" + "</u>";
                }

                $("#errorMsg div").html(conflickMsg + "<br>" + " Changing time for client:" + "<strong>" + event.client.firstName + "</strong>"
                        + " scheduled service:" + "<strong>" + stripName(event.title) + "</strong>" + "<br>" + conflickMsg2);
                $("#errorTable2 tbody").html("<tr>" + "<th title='client first name'>" + "client" + "</th>" + "<th title='appointment ID number'>"
                        + "event&#35;" + "</th>" + "<th title='service to perform'>" + "service" + "<th title='service start time'>" + "start" + "</th>"
                        + "<th title='service end time'>" + "end" + "</th>" + "<th title='current appointment status'>" + "status" + "</th>" + "</tr>");
                $("#errorTable2 tbody").append(
                        "<tr>" + "<td>" + event.client.firstName + "</td>" + "<td>" + event.eventId + "</td>" + "<td>" + stripName(event.title) + "</td>" + "<td>" + jsTime.fmtTimeAlt(event.start) + "</td>"
                        + "<td>" + jsTime.fmtTimeAlt(event.end) + "</td>" + '<td style="color:' + event.services.serviceStatus.statusColor + '">' + event.serviceStatus.statusName + "</td>" + "</tr>");
                $(".errorTable tbody").html(th);
                $.each(hasConflict, function (index, value) {
                    $(".errorTable tbody").append(
                            "<tr>" + "<td>" + value.firstName + "</td>" + "<td>" + value.eventId + "</td>" + "<td>" + stripName(value.title) + "</td>" + "<td>" + jsTime.fmtTimeAlt(value.start) + "</td>"
                            + "<td>" + jsTime.fmtTimeAlt(value.end) + "</td>"
                            + '<td style="color:' + value.services.serviceStatus.statusColor + '">' + value.services.serviceStatus.statusName + "</td>" + "<td>" + '<input type="checkbox" id="cancel" value="' + value.eventId + '">' + "</td>" + "</tr>");
                });
            }
            else
            {
                event = updateEvent(event);
                postToServer(event);
            }
        },
        eventSources: [
            {
                url: '../FullCalendar',
                type: 'GET',
                dataType: "json",
                data: {
                    title: idlNum,
                    start: 'start',
                    end: 'end',
                    backgroundColor: 'red'
                },
                error: function () {
                    $("#postDataError").html(fetchError);
                }
            }
        ]
    });
}); // end Full Calendar document ready
function updateEvent(event)
{
    var addEventObj = new Object();
    addEventObj.action = event.action;
    addEventObj.client = event.client;
    addEventObj.associate2 = event.associate2;
    addEventObj.editable = event.editable;
    addEventObj.end = event.end;
    addEventObj.start = event.start;
    addEventObj.title = event.title;
    addEventObj.serviceDescription = event.serviceDescription;
    addEventObj.notifyClient = event.notifyClient;
    addEventObj.notes = event.notes;
    addEventObj.userType = event.userType;
    addEventObj.services = event.services;
    addEventObj.serviceStatus = event.serviceStatus;
    addEventObj.eventId = event.eventId;
    addEventObj.serviceId = event.serviceId;
    addEventObj.durationEditable = event.durationEditable;
    addEventObj.editable = event.editable;
    addEventObj.allDay = event.allDay;
    addEventObj.serviceTime = event.serviceTime;
    addEventObj.backgroundColor = event.backgroundColor;
    addEventObj.serviceStatus.statusId = event.serviceStatus.statusId;

    return addEventObj;

}
// refresh calendar function button
$(function () {
    $("#refreshCalendar").click(function () {
        $('#calendar').fullCalendar("refetchEvents");
        $("#postDataSuccess").html("Calendar Refresh Successful!");
        fadeInOutMessage("#messages");
    });
}); // end refresh calendar function

// update calendar using update button
$(function () {
    $("#updateCalendar").click(function () {
        var eventsToPost = []; // array to post events to server
        var eventsFromCalendar = $('#calendar').fullCalendar('clientEvents');
        $.each(eventsFromCalendar, function (index, value) {

            var eventObj = new EventObj(value);
            eventObj.action = "add"; // action tells the server how to process
            eventsToPost.push(eventObj);
        });
        var calEvents = JSON.stringify(eventsToPost);
        calEvents = calEvents.replace(/\"/g, '\''); // replace double quotes with single quote
        postEvents(calEvents);
    });
}); // end update Calendar function

// this function checks for events with overlapping times and then returns them in an array
function eventConflictCk(event) {
    var conflictEvts = [],
            stJs = jsDate.convToJsDate(event.start),
            etJs = jsDate.convToJsDate(event.end),
            vst,
            vet,
            std = new Date(stJs),
            clientEvents = $('#calendar').fullCalendar('clientEvents');
    var st = stJs.getTime();
    var et = etJs.getTime();
    $.each(clientEvents, function (index, value) {
        // only process if not allDay event and only events on same day of the event being scheduled,
        // dropped or resized
        // process if associate id is the same
        vst = jsDate.convToJsDate(value.start);
        vet = jsDate.convToJsDate(value.end);
        if (std.getDate() === vst.getDate() && parseInt(event.associateId) === value.associateId)
        {
            if (!value.allDay && value.id !== event.eventId
                    && value.services.serviceStatus.statusName === "Confirmed")
            {
                vst = vst.getTime();
                vet = vet.getTime();
                if (st < vst && st < vet && et > vst && et < vet)
                {
                    conflictEvts.push(value);
                }
                if (st < vet && st > vst && et > vet && et > vet)
                {
                    conflictEvts.push(value);
                }
                if (st > vst && st < vet && et > vst && et < vet)
                {
                    conflictEvts.push(value);
                }
                if (st < vst && st < vet && et > vst && et > vet)
                {
                    conflictEvts.push(value);
                }
                if (st === vst || et === vet)
                {
                    conflictEvts.push(value);
                }
            }
        }
    });
    return  conflictEvts; // return array
} // end event conflick check function

// create new event from select full calendar select fucntion
function newEvent(start, end, jsEvent, view) {

    var pastDateMsg = $("#pastDateMsg"),
            now = moment(),
            minWidth = 250,
            alertIcon = "ui-icon ui-icon-alert";
    now.seconds(0).milliseconds(0);

    start.local();  // switch start time from UTC to local time

    if (start.diff(end) === -86400000 && start.isBefore(now, 'day'))
    {
        openPastMsg(start, end, jsEvent, view);
        pastDateMsg.dialog("open");
        pastDateMsg.dialog("option", "minWidth", minWidth);
        $("#pastDateMsg span").addClass(alertIcon);
        $("#pastDateMsg figcaption").html("Past Date Selection!");
        $("#pastDateMsg tfoot").html("The Date you selected for creating " + "<br>" + "a new Event "
                + "<b>" + jsDate.formatDate(start) + "</b>"
                + " is a Date in the past.");
    }
    else if (start.isBefore(now))
    {
        openPastMsg(start, end, jsEvent, jsEvent, view);
        pastDateMsg.dialog("open");
        pastDateMsg.dialog("option", "minWidth", minWidth);
        $("#pastDateMsg span").addClass(alertIcon);
        $("#pastDateMsg figcaption").html("Past Date &#38; Time Selection!");
        $("#pastTable tbody").html(
                "<tr>" + "<td>" + "date:" + "</td>" + "<td>" + jsDate.formatDate(start) + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "time:" + "</td>" + "<td>" + jsTime.formatTime(start) + "&nbsp;&nbsp;" + jsTime.formatTime(end) + "</td>" + "</tr>");
        $("#pastTable tfoot").html("<tr>" + "<th colspan='2'>" + " select a future date &#38; time" + "</th>" + "</tr>");
    }
    else
    {
        var svcOpt = services.getSvcsStringify(); // list services select group
        var assoOpt = associateClass.getAssociateList(); // list associate select group
        $(".notifyTable #normal-service").html(svcOpt);
        $(".notifyTable #associate").html(assoOpt);
        newEvt(start, end, jsEvent, view);
    }
} // end newEvent fucntion

function newEvt(start, end, jsEvent, view) {
    createEvent(start, end, jsEvent, view);
    $("#newEvent").dialog("open");
    $("#newEvent").dialog("option", "minHeight", 400);
    $("#newEvent").dialog("option", "minWidth", 488);
    $("#newEvent").dialog("option", "maxWidth", 580);

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

            var lastFiveEvents = serviceList.getCalendarEvents(client.id, 5);
            var lastVisitsTable = eventsRendering.drawEvents1(lastFiveEvents, "past");
            $("#accordion-4 .last-visits-table tbody").html(lastVisitsTable);
            $(".notifyTable #associate").html(assoList);
            $(".notifyTable #normal-service").html(norSvcOpt);
            // calculate and adjust service time
            var svcObj = services.getServiceObj(client.serviceNormalid);
            var s = svcObj.serviceTime;
            var e = start.clone();
            e.add(s, 'm');
            $("#endTime").val(jsTime.formatTime(e));

            $('#client-info').val(JSON.stringify(client)); // stringify client object
            $('#customerId').val(ui.item.id);
            $("#firstNm").val(ui.item.firstName);
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
    });
    $("calendar").fullCalendar('unselect');
} // end newEvt function

// create new event or appointment
function createEvent(start, end, jsEvent, view) {

    var tips = $("#newEventField .validateTips");
    var customerIdClient = $("#customerId"),
            startMomObj,
            firstNameClient = $("#firstNm"),
            lastNameClient = $("#lastNm"),
            endObj,
            statusIdClient = $("#status"),
            emailAddressClient = $("#email"),
            clientInfoObj = $("#client-info"),
            mobilePhoneClient = $("#mobilePhone"),
            backgroundColorClient = $("#backgroundColor"),
            associateInfo = $("#associate"),
            newClientChk = $("#newEventTable .newClient:checked"),
            notifyClientChk = $("#newEventTable .notifyClient:checked"),
            eventHeader = $(".eventHeader"),
            serviceElem = $("#normal-service"),
            endTime = $("#endTime"),
            startTime = $("#startTime"),
            notesClient = $("#notes"),
            allDay = true,
            allFields = $([]).add(firstNameClient).add(lastNameClient).add(emailAddressClient)
            .add(backgroundColorClient).add(customerIdClient).add(mobilePhoneClient)
            .add(associateInfo).add(notesClient).add(clientInfoObj).add(serviceElem);

    function updateTips(t) {
        tips.html(t).addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }
    serviceElem.change(function () {
        var serviceObj = convert.parseJsonObj(serviceElem);
        var s = serviceObj.serviceTime;
        var e = start.clone();
        e.add(s, 'm');
        endTime.val(jsTime.formatTime(e));
    });
    eventHeader.html("<strong>" + jsDate.formatDate(start) + "</strong>");
    if (start.diff(end) !== -86400000)
    {
        $("#startend").show();
        var e = start.clone();
        var serviceObj = convert.parseJsonObj(serviceElem); // get service info object
        var t = serviceObj.serviceTime; // get service time
        e.add(t, 'm'); // add service time to start time
        endTime.val(jsTime.formatTime(e));
        startTime.val(jsTime.formatTime(start));
    }
    else
    {
        $("#startend").hide();
    }

    $("#newEvent").dialog({
        autoOpen: false,
        modal: true,
        show: {
            duration: 200
        },
        buttons: {
            'Book': function () {
                var serviceObj = convert.parseJsonObj(serviceElem);
                var associateObj = convert.parseJsonObj(associateInfo);
                var bValid = true;

                allFields.removeClass("ui-state-error");
                bValid = bValid && validateForm.checkLength(firstNameClient, "First Name", 3, 45, tips);
                bValid = bValid && validateForm.checkLength(lastNameClient, "Last Name", 3, 45, tips);
                bValid = bValid && validateForm.checkLength(emailAddressClient, "Email", 6, 80, tips);
                //bValid = bValid && checkRegexp(startFormat, /^[01]?\d\/[0-3]\d\/\d{4}$/, "Date format must be " + formatDateNew(new Date));
                bValid = bValid && validateForm.checkRegexp(firstNameClient, nameRegEx, "First Name may consist of a-z, 0-9, underscores, begin with a letter.", tips);
                bValid = bValid && validateForm.checkRegexp(lastNameClient, nameRegEx, "Last Name may consist of a-z, 0-9, underscores, begin with a letter.", tips);
                // From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
                bValid = bValid && validateForm.checkRegexp(emailAddressClient, emailRegEx, "Please enter a valid email address.  eg. jdoe@doe.com", tips);
                // /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
//                    bValid = bValid && checkRegexp(password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9");
                bValid = bValid && validateForm.checkRegexp(mobilePhoneClient, phoneRegEx, "Mobile Phone format 999-999-9999", tips);
                if (start.diff(end) !== -86400000)
                {
                    allDay = false;
                    var evtTimeObj = new Object;
                    var t = serviceObj.serviceTime;
                    startMomObj = jsTime.toMomentObj(start, startTime.val());
                    var stMomClone = startMomObj.clone();
                    endObj = stMomClone.add(t, 'm'); // add service time to start time
                    evtTimeObj.start = startMomObj;
                    evtTimeObj.end = endObj;
                    evtTimeObj.allDay = allDay;
                    evtTimeObj.associateId = associateObj.id;
                    evtTimeObj.eventId = 1234;
                    var evts = eventConflictCk(evtTimeObj); // check for event conflicts
                    if (evts.length > 0) // alert user if event conflicts exist
                    {
                        updateTips("<normal style='color: #c00;'>" + " Appointment Conflict!" + "</normal>" + "<br>" + "Selected time &#38; service overlaps with an existing appointment.");
                        bValid = false;
                    }
                }
                else if (start.diff(end) === -86400000)
                {
                    allDay = true;
                    var evtTimeObj = new Object;
                    var t = serviceObj.serviceTime;
                    startMomObj = jsTime.toMomentObj(start, startTime.val());
                    var stMomClone = startMomObj.clone();
                    endObj = stMomClone.add(t, 'm'); // add service time to start time
                    evtTimeObj.start = stMomClone;
                    evtTimeObj.end = endObj;
                    evtTimeObj.allDay = allDay;
                    evtTimeObj.associateId = associateObj.id;
                    evtTimeObj.eventId = 1234;
                }

                if (bValid)
                {
                    var serviceId = serviceObj.serviceId;
                    var title = serviceObj.description;
                    var serviceTime = serviceObj.serviceTime;

                    // calculate end time by adding service time/duration
                    end = start.clone();
                    end.add(serviceTime, 'm');
                    var customerId = customerIdClient.val();
                    if (customerId === "")
                    {
                        customerId = 0;
                    }
                    var firstName = firstNameClient.val();
                    var lastName = lastNameClient.val();
                    var emailAddress = emailAddressClient.val();
                    var associateName = associateObj.firstName;
                    var associateId = associateObj.id;
                    var notes = notesClient.val();
                    var backgroundColor = backgroundColorClient.val();
                    var mobilePhone = mobilePhoneClient.val();
                    var statusId = statusIdClient.val();
                    var notifyClient = notifyClientChk.is(':checked');
                    var newClient = newClientChk.is(':checked');
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
                    else
                    {
                        // convert client string data to js object
                        client = convert.parseJsonObj(clientInfoObj);
                    }
                    client.associate2 = associateObj;
                    // rendar the event to calendar
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
                    var eventObj = new Object(); // add data to eventObj
                    eventObj.title = title;
                    eventObj.start = startMomObj;
                    eventObj.end = endObj;
                    eventObj.allDay = allDay;
                    eventObj.backgroundColor = backgroundColor;
                    eventObj.editable = editable;
                    eventObj.durationEditable = durationEditable;
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
                    $(this).dialog("close");
                }
            },
            Cancel: function () {
                $(this).dialog("close");
            }
        },
        close: function () {
            allFields.val("").removeClass("ui-state-error");
            tips.html("");
        }
    });
} // end create event function

function openMsg(event, revertFunc) {
    $(function () {
        $("#errorMsg").dialog({
            autoOpen: false,
            modal: true,
            width: 'auto',
            height: 'auto',
            buttons: {
                Cancel: function () {
                    if (event instanceof Object)
                    {
                        $('#calendar').fullCalendar('refetchEvents');
                    }
                    $(this).dialog("close");
                },
                Allow: function () {
                    if (typeof revertFunc === undefined)
                    {
                        newEvt(event.start, event.end, event.allDay);
                    }
                    else
                    {
                        var cancelVal = [];
                        $("#cancel:checked").each(function () {
                            cancelVal.push(this.value);
                        });
                        if (cancelVal.length > 0)
                        {
                            event.cancelEvts = cancelVal;
                        }
                        postToServer(event);
                    }
                    $(this).dialog("close");
                }
            },
            close: function () {
                $("#errorMsg tbody").html("");
            }
        });
    });
} // end openMsg function
function openPastMsg(start, end, jsEvent, view) {
    $(function () {
        $("#pastDateMsg").dialog({
            autoOpen: false,
            modal: true,
            width: 'auto',
            maxWidth: 400,
            buttons: {
                Okay: function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                $("#pastTable tbody").html("");
                $("#pastDateMsg figcaption").html("");
                $("#pastTable tfoot").html("");
            }
        });
    });
} // end pastDateMsg function

function evtClick(calEvent, jsEvent) {
//    var lastFiveEvents = serviceList.getCalendarEvents(calEvent.customerId, 5);
    var isAllDay = "No",
            calEvtElem = $(".calEvent");
    var isEvtClientObj = calEvent.client instanceof Object;

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
    if (isEvtClientObj === true && calEvent.client.imgUpl === true) // check if client is an Object type
    {
        clientImgId = calEvent.customerId;
    }
    if (calEvent.userType === "associate")
    {
        $("#statusField").hide();
        var eventInfo =
                "<tr>" + "<td title='client first and last name'>" + "associate:" + "</td>" + "<td>" + calEvent.associate2.firstName + " " + calEvent.associate2.lastName + "&nbsp;" + '<img src="../img/associates/'
                + associateImgId + '.png" alt="associate img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate email address'>" + "email:" + "</td>" + "<td>" + "<a href='mailto:" + calEvent.associate2.email + " '>" + calEvent.associate2.email + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='mobile phone'>" + "mobile&#35;" + "</td>" + "<td>" + formatPhone(calEvent.associate2.mobilePhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='available date'>" + "date:" + "</td>" + "<td>" + jsDate.formatDate(calEvent.start) + "</td>" + "</tr>"
                + "<tr>" + "<td title='available start time'>" + "start:" + "</td>" + "<td>" + jsTime.formatTime(calEvent.start) + "</td>" + "</tr>"
                + "<tr>" + "<td title='available end time'>" + "end:" + "</td>" + "<td>" + jsTime.formatTime(calEvent.end) + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate available time id number'>" + "id&#35;" + "</td>" + "<td>" + calEvent.eventId + "</td>" + "</tr>";
        openEvent(calEvent, eventInfo, jsEvent);
        calEvtElem.dialog("option", "title", "Available Time Info");
        calEvtElem.dialog("open");
        calEvtElem.dialog("option", "minHeight", 400);
        calEvtElem.dialog("option", "minWidth", 370);
        calEvtElem.dialog("option", "maxWidth", 580);
        $(".calEvent .eventInfoTable").html(eventInfo);
    }
    else
    {
        $("#statusField").show();
        var notes = calEvent.notes;
        if (typeof notes === "undefined")
        {
            calEvent.notes = "none";
        }
        var eventInfo =
                "<tr>" + "<td title='client first and last name'>" + "client:" + "</td>" + "<td>" + calEvent.client.firstName + " " + calEvent.client.lastName + '  <img src="../img/clients/'
                + clientImgId + '.png" alt="client img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='client email address'>" + "email:" + "</td>" + "<td>" + "<a href='mailto:" + calEvent.client.email + " '>" + calEvent.client.email + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='mobile phone'>" + "mobile:" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(calEvent.client.mobilePhone) + " '>" + formatPhone(calEvent.client.mobilePhone) + "</a>" + "</td>" + "</tr>"
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
                + "<tr>" + "<td>" + "status:" + "</td>" + '<td style="color:' + calEvent.services.serviceStatus.statusColor + '">' + calEvent.services.serviceStatus.statusName + "</td>" + "</tr>";
        openEvent(calEvent);
        calEvtElem.dialog("option", "title", "Appointment Info");
        calEvtElem.dialog("open");
        calEvtElem.dialog("option", "minHeight", 400);
        calEvtElem.dialog("option", "minWidth", 340);
        calEvtElem.dialog("option", "maxWidth", 580);
        $(".calEvent .eventInfoTable").html(eventInfo);
    }
// change the border
    $(this).css('border-color', '#c00');
} // end evtClick function

function openEvent(event) {
    var calEvent = $(".calEvent");
    var remove = $("#delete");
    var notifyField = $("#notifyField");
    if (event.userType === "associate")
    {
        calEvent.dialog({
            autoOpen: false,
//            width: 'auto',
//            hegith: 'auto',
//            maxWidth: 600,
//            minWidth: 250,
            modal: true,
            show: {
                duration: 200
            },
            buttons: {
                OK: function () {
                    $(this).dialog("close");
                },
                Remove: function () {
                    notifyField.hide();
                    var eventInfo =
                            "<tr>" + "<td title='client first and last name'>" + "associate:" + "</td>" + "<td>" + event.associate2.firstName + " " + event.associate2.lastName + "</td>" + "</tr>"
                            + "<tr>" + "<td title='available date'>" + "date:" + "</td>" + "<td>" + jsDate.formatDate(event.start) + "</td>" + "</tr>"
                            + "<tr>" + "<td title='available start time'>" + "start:" + "</td>" + "<td>" + jsTime.formatTime(event.start) + "</td>" + "</tr>"
                            + "<tr>" + "<td title='available end time'>" + "end:" + "</td>" + "<td>" + jsTime.formatTime(event.end) + "</td>" + "</tr>"
                            + "<tr>" + "<td title='associate available time id number'>" + "id&#35;" + "</td>" + "<td>" + event.eventId + "</td>" + "</tr>";
                    removeEvent(event);
                    remove.dialog("option", "title", "Confirm Request");
                    remove.dialog("open");
                    $("#delete #deleteHeader").html("Are you sure you want" + "<br>" + "to " + "<strong>" + "REMOVE" + "</strong>" + " this available time slot?");
                    $("#delete .deleteTable").html(eventInfo);
                    $(this).dialog("close");
                }
            }
        });
    }
    else
    {
        var currentStatus = event.services.serviceStatus.statusId;
        var statusId = $("#updateStatus").val(currentStatus);
        calEvent.dialog({
            autoOpen: false,
            modal: true,
            show: {
                duration: 200
            },
            buttons: {
                OK: function () {
                    statusId = statusId.val(); // get event status ID
                    if (currentStatus !== parseInt(statusId))
                    {
                        event.serviceStatus.statusId = statusId; // update event status ID
                        event.action = "updateStatus"; // change event action so server can process update
                        event = updateEvent(event);
                        postToServer(event);
                    }
                    $(this).dialog("close");
                },
                Delete: function () {
                    notifyField.show();
                    var eventInfo =
                            "<tr>" + "<td title='client first and last name'>" + "client:" + "</td>" + "<td>" + event.client.firstName + " " + event.client.lastName + "</td>" + "</tr>"
                            + "<tr>" + "<td title='service to perform'>" + "service:" + "</td>" + "<td>" + stripName(event.title) + "</td>" + "</tr>"
                            + "<tr>" + "<td title='date of service'>" + "date:" + "</td>" + "<td>" + jsDate.formatDate(event.start) + "</td>" + "</tr>"
                            + "<tr>" + "<td title='service start time'>" + "start:" + "</td>" + "<td>" + jsTime.formatTime(event.start) + "</td>" + "</tr>"
                            + "<tr>" + "<td title='service end time'>" + "end:" + "</td>" + "<td>" + jsTime.formatTime(event.end) + "</td>" + "</tr>"
                            + "<tr>" + "<td title='associate first name'>" + "associate:" + "</td>" + "<td>" + event.associate2.firstName + "</td>" + "</tr>"
                            + "<tr>" + "<td title='event id number'>" + "event:" + "</td>" + "<td>" + event.eventId + "</td>" + "</tr>";
                    removeEvent(event);
                    remove.dialog("option", "title", "Confirm Request");
                    remove.dialog("open");
                    $("#delete #deleteHeader").html("Are you sure you want" + "<br>" + "to " + "<strong>" + "DELETE" + "</strong>" + " this appointment?");
                    $("#delete .deleteTable").html(eventInfo);
                    $(this).dialog("close");
                }
            }
        });
    }
} // end openEvent function

function removeEvent(event) {
    $("#delete").dialog({
        autoOpen: false,
        width: 280,
        hegith: 'auto',
        maxWidth: 600,
        modal: true,
        show: {
            duration: 200
        },
        buttons: {
            No: function () {
                $(this).dialog("close");
            },
            Yes: function () {
                if (event.userType === "associate")
                {
                    event.action = "delAssociateTimeSlot";
                }
                else
                {
                    event.restoreTime = $("#restoreTime:checked").val();
                    event.notifyClient = $("#notifyClient:checked").val();
                    $("#calendar").fullCalendar('removeEvents', event._id);
                    event.action = "delete";
                }
                $(this).dialog("close");
                postToServer(event);
            }
        }
    });
} // end removeEvent function

function gotoDate(d) {
    var o = new Date(d); // convert date string to date object
    $('#calendar').fullCalendar('gotoDate', o);
    $('#calendar').fullCalendar('changeView', 'agendaDay'); //change view to agendaDay
}

// this function will show associtate events
function showEvts(key, c) {
    $("#postDataError").html("");
    if (c !== 0)
    {
        $('#calendar').fullCalendar('removeEventSource', {
            url: '../FullCalendar',
            type: 'GET',
            dataType: "json",
            data: {
                title: c, key: "aa"
            },
            error: function () {
                $("#postDataError").html(fetchError);
                alert(fetchError);
            }
        });
    }

    $('#calendar').fullCalendar('addEventSource', {
        url: '../FullCalendar',
        type: 'GET',
        dataType: "json",
        data: {title: c, key: key
        },
        error: function () {
            $("#postDataError").html(fetchError);
            alert(fetchError);
        }
    });
} // end showEvts function

var getIndex = {
    getKey: function (e) {
        var t = $(e).val();
        var l = t.lastIndexOf(":");
        l++;
        var s = t.substring(l);
        return s;
    },
    // this function gets id number from calendar element
    getId: function (e) {
        var t = $(e).val();
        var l = t.indexOf(":", 0);
        var n = t.substring(0, l);
        return n;
    }
};