/*
 Author     : williamdobbs
 */
// global variables
var dateFmts = ["MM-DD-YYYY", "YYYY-MM-DD", "MM/DD/YYYY", "YYYY/MM/DD"],
        dateTimeFmts = ["M-DD-YYYY h:mm a", "MM-DD-YYYY h:mm A", "YYYY-MM-DD h:mm A", "MM/DD/YYYY h:mm A", "YYYY-MM-DD HH:mm:ss", "YYYY/MM/DD h:mm A", "MMM DD, YYYY h:mmss A "],
        dateTimeFmt = "MM/DD/YYYY hh:mm a",
        emailReExHhml = "^[\w.%+\-]+@[\w.\-]+\.[A-Za-z]{2,6}$",
        phoneRexExHtml = "/\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}",
        fetchError = "There was an error while fectching calendar events!" + "<br>"
        + "Click Calendar on the sidebar or refresh the page to re-fetch calendar events." + "<br>"
        + "Contact support if problem persists.",
        phoneErrorMsg = "<font color='#c00'>" + "Phone number " + "</font>" + "must be one these formats:<br> 999-999-9999, (999) 999-9999, 9999999999",
        nameErrorMsg = "<font color='#c00'>" + "First Name " + "</font>" + "may consist of a-z, 0-9, underscores, begin with a letter.",
        zipErrorMsg = "Please enter a valid  " + "<font color='#c00'>" + "Zip Code." + "</font>" + " Ex. 12345 or 12345-1234",
        passwordErrorMsg = "<ul>"
        + "<li>" + "<font color='#c00'>" + "Password " + "</font>" + "Must include at least one symbol." + "</li>"
        + "<li>" + "Include at least one uppercase letter." + "</li>"
        + "<li>" + "Include at least one numeric digit." + "</li>",
        pwdMatchError = "<font color='#c00'>" + "Passwords " + "</font>" + "must match. Please re-enter your password.",
        pwdNullError = "<font color='#c00'>" + "Password fields " + "</font>" + "cannot be empty. Please re-enter your password.",
        currencyErrorMsg = "<font color='#c00'>" + "Price " + "</font>" + "must be one these formats:&nbsp;19, 19.00, 19.03",
        tips = "",
        emailEntryError = "A valid Email Address is required <br> eg. jdoe&#64;doe.com",
        emailRegEx = /^[\w.%+\-]+@[\w.\-]+\.[A-Za-z]{2,6}$/i,
        nameRegEx = /^[a-z]([0-9a-z _])+$/i,
        zipCodeRegEx = /^\d{5}(-\d{4})?$/,
        phoneRegEx = /(\W|^)[(]{0,1}\d{3}[)]{0,1}[\s-]{0,1}\d{3}[\s-]{0,1}\d{4}(\W|$)/,
        // /\D*([2-9]\d{2})(\D*)([2-9]\d{2})(\D*)(\d{4})\D*/,
        // //^\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}/,
        currencyRegEx = /^(\d*\.\d{2}|\d+)$/,
        /* Password Must include at least one symbol,
         * at least one uppercase letter,
         * Between 8 and 15 characters long,
         * May contain any character except space,
         Must include at least one lowercase letter, Must include at least one numeric digit */
        passwordRegEx = /^(?=.*\d)(?=.*[~!@#$%^&*()_\-+=|\\{}[\]:;<>?/])(?=.*[A-Z])(?=.*[a-z])\S{8,15}$/;
var serviceListing = []; // global service listing variable  array
var messageListing = []; // global message listing variable  array
var recentInviteRequests = []; // global message listing variable  array
var serviceCategories = []; // global category variable array
var serviceStatusArr = []; // global service status array variable
var associateListArr = []; // global associate listing variable array
var timesArray = ["12:15 am", "12:30 am", "12:45 am", "1:00 am", "1:15 am", "1:30 am", "1:45 am", "2:00 am", "2:15 am",
    "2:30 am", "2:45 am", "3:00 am", "3:15 am", "3:30 am", "3:45 am", "4:00 am", "4:15 am", "4:30 am", "4:45 am",
    "5:00 am", "5:15 am", "5:30 am", "5:45 am", "6:00 am", "6:15 am", "6:30 am", "6:45 am", "7:00 am", "7:15 am",
    "7:30 am", "7:45 am", "8:00 am", "8:15 am", "8:30 am", "8:45 am", "9:00 am", "9:15 am", "9:30 am", "9:45 am",
    "10:00 am", "10:15 am", "10:30 am", "10:45 am", "11:00 am", "11:15 am", "11:30 am", "11:45 am", "12:00 pm",
    "12:15 pm", "12:30 pm", "12:45 pm", "1:00 pm", "1:15 pm", "1:30 pm", "1:45 pm", "2:00 pm", "2:15 pm",
    "2:30 pm", "2:45 pm", "3:00 pm", "3:15 pm", "3:30 pm", "3:45 pm", "4:00 pm", "4:15 pm", "4:30 pm", "4:45 pm",
    "5:00 pm", "5:15 pm", "5:30 pm", "5:45 pm", "6:00 pm", "6:15 pm", "6:30 pm", "6:45 pm", "7:00 pm", "7:15 pm",
    "7:30 pm", "7:45 pm", "8:00 pm", "8:15 pm", "8:30 pm", "8:45 pm", "9:00 pm", "9:15 pm", "9:30 pm", "9:45 pm",
    "10:00 pm", "10:15 pm", "10:30 pm", "10:45 pm", "11:00 pm", "11:15 pm", "11:30 pm", "11:45 pm"];

var associateInfo; // current associate info

var defaultView; // default calendar view
// spinner element format argument must be, 'spinnerelement'
function spinner(element, top, timeout) {
    var opts = {
        lines: 13, // The number of lines to draw
        length: 15, // The length of each line
        width: 3, // The line thickness
        radius: 12, // The radius of the inner circle
        scale: 1.0, // Scales overall size of the spinner
        rotate: 0, // Rotation offset
        corners: 1, // Roundness (0..1)
        color: '#555555', // #rgb or #rrggbb
        direction: 1, // 1: clockwise, -1: counterclockwise
        speed: 1, // Rounds per second
        trail: 50, // Afterglow percentage
        opacity: 1 / 4, // Opacity of the lines
        fps: 20, // Frames per second when using setTimeout()
        zIndex: 2e9, // Use a high z-index by default
        className: 'spinner', // CSS class to assign to the element
        top: '50%', // center vertically
        left: '50%', // center horizontally
        position: 'absolute'  // element position
    };
    var target = document.getElementById(element);
    var spinner = new Spinner(opts).spin(target);
    $(target).data('spinner', spinner);
// Run a function after x seconds.
//    setTimeout(function () {
//        spinner.stop(); // Stop the spinner
//    }, timeout);
} // end spinner function

var doAjaxMsgRequest = function (key, url, async, title, altKey, response) {

    $.ajax({
        url: url,
        beforeSend: function () {
            $("#postDataError").html("");
            openLoading();
            $("#loading").dialog("open"); // open loading element
            $("#loading h4").html("Loading data.  Please wait...");
            spinner('loading', 'auto');
        },
        type: 'GET',
        dataType: "json",
        async: async,
        data: {key: key, title: title, altKey: altKey},
        error: function (xhr, status, error) {
            $("#loading h4").html("ERROR!");
            $("#loading").dialog("close"); // close loading element
            $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            alert("ERROR!  There was an requesting data. " + xhr.status + " - " + error);
        },
        success: function (data) {
            response(data);
        },
        complete: function (xhr, status) {
            $("#loading").dialog("close"); // close loading element
        }
    });

};
var doAjaxMsgRequest2 = function (key1, key2, key3, userId, url, async, response) {

    $.ajax({
        url: url,
        beforeSend: function () {
            $("#postDataError").html("");
            openLoading();
            $("#loading").dialog("open"); // open loading element
            $("#loading h4").html("Loading data.  Please wait...");
            spinner('loading', 'auto');
        },
        type: 'GET',
        dataType: "json",
        async: async,
        data: {key1: key1, key2: key2, key3: key3, userId: userId, url: url},
        error: function (xhr, status, error) {
            $("#loading h4").html("ERROR!");
            $("#loading").dialog("close"); // close loading element
            $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            alert("ERROR!  There was an requesting data. " + xhr.status + " - " + error);
        },
        success: function (data) {
            response(data);
        },
        complete: function (xhr, status) {
            $("#loading").dialog("close"); // close loading element
        }
    });

};
function doAjaxRequest(key, url, async, title, response, element) {
    $.ajax({
        url: url,
        beforeSend: function () {
            $("#postDataError").html("");
            openLoading();
            fadeOut(element);
            $("#loading").dialog("open"); // open loading element
            $("#loading h4").html("Loading data.  Please wait...");
            spinner('loading', 'auto');
        },
        type: 'GET',
        dataType: "json",
        async: async,
        data: {key: key, title: title},
        error: function (xhr, status, error) {
            $("#loading h4").html("ERROR!");
            $("#loading").dialog("close"); // close loading element
            $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            alert("ERROR!  There was an requesting data. " + xhr.status + " - " + error);
        },
        success: function (data) {
            response(data);
            $("#loading h4").html("Done!");
            $("#loading").dialog("close"); // close loading element
        },
        complete: function (xhr, status) {
            $("#loading").dialog("close"); // close loading element

        }

    });
    fadeIn(element);
} // end doAjaxRequest function

function doAjaxRequestNoElements(key, url, async, title, response) {
    $.ajax({
        url: url,
        type: 'GET',
        dataType: "json",
        async: async,
        data: {key: key, title: title},
        error: function (xhr, status, error) {
            alert("ERROR!  There was an requesting data. " + xhr.status + " - " + error);
        },
        success: function (data) {
            response(data);
        }
    });
}
function doAjaxEventsRequest(key, url, userID, numOfEvts, async, title, response) {
    $.ajax({
        url: url,
        beforeSend: function () {
            $("#postDataError").html("");
            openLoading();
            $("#loading").dialog("open"); // open loading element
            $("#loading h4").html("Loading data.  Please wait...");
            spinner('loading', 'auto');
        },
        type: 'GET',
        dataType: "json",
        async: async,
        data: {key: key, title: title, userID: userID, numOfEvts: numOfEvts},
        error: function (xhr, status, error) {
            $('#loading').data('spinner').stop(); // Stop the spinner
            $("#loading h4").html("ERROR!");
            $("#loading").dialog("close"); // close loading element
            $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            alert("ERROR!  There was an requesting data. " + xhr.status + " - " + error);
        },
        success: function (data) {
            response(data);
            $('#loading').data('spinner').stop(); // Stop the spinner
            $("#loading h4").html("Done!");
            $("#loading").dialog("close"); // close loading element
        }
    });
} // end doAjaxEventsRequest function

function doAjaxPost(data, url, id, async) {
    var ajaxPostSuccess;
    var associatePwd = $("#security #password-div #postResults");
    var associateInfoElm = $("#profile #profile-fieldset-1 #postResults");
    var associateAlerts = $("#profile #alert-prefs #postResults");
    var assocSecurity = $("#security #security-questions-div #postResults");
    var assocDefaultView = $("#calendarOptions #calendar-prefs-fieldset #postResults");
    var assocDefaultTimes = $("#calendarOptions #default-times-fieldset #postResults");
    var memberSettingsResultsMsg = $("#members #members-fieldset #postResults");
    var updateServicesMsg = $("#services #postResults");
    var updateInviteMsg = $("#new-client-request #postResults");
    $.ajax({
        url: url,
        type: 'post',
        async: async,
        beforeSend: function () {
            $("#postDataError").html("");
            openLoading();
            $("#loading").dialog("open"); // open loading element
            $("#loading h4").html("Processing request.  Please wait...");
            spinner('loading', 'auto');
        },
        data: {data: data, title: id},
        error: function (xhr, status, error) {
            $('#loading').data('spinner').stop(); // Stop the spinner
            $("#loading h4").html("ERROR!");
            $("#loading").dialog("close"); // close loading element

            switch (id)
            {
                case "updateAssoPwd":
                    associatePwd.removeClass("good").addClass("error");
                    associatePwd.html("An error occurred updating your Password.");
                    fadeInMessage(associatePwd);
                    break;
                case "updateAssociate":
                    associateInfoElm.removeClass("good").removeClass("cancel").addClass("error");
                    associateInfoElm.html("An error occurred updating your Account Information.");
                    fadeInMessage(associateInfoElm);
                    break;
                case "updateAssociateAlerts":
                    associateAlerts.removeClass("good").removeClass("cancel").addClass("error");
                    associateAlerts.html("An error occurred updating your Alert Preferences.");
                    fadeInMessage(associateAlerts);
                    break;
                case "securityQandA":
                    assocSecurity.removeClass("good").addClass("error");
                    assocSecurity.html("An error occurred updating your Security Answers.");
                    fadeInMessage(assocSecurity);
                    break;
                case "updateDefaultCalendarView":
                    assocDefaultView.removeClass("good").removeClass("cancel").addClass("error");
                    assocDefaultView.html("An error occurred updating your default Calendar View.");
                    fadeInMessage(assocDefaultView);
                    break;
                case "updateDefaultCalendarTimes":
                    assocDefaultTimes.removeClass("good").removeClass("cancel").addClass("error");
                    assocDefaultTimes.html("An error occurred updating your Normal Work Hours.");
                    fadeInMessage(assocDefaultTimes);
                    break;
                case "updateMemberSettings":
                    memberSettingsResultsMsg.removeClass("good").removeClass("cancel").addClass("error");
                    memberSettingsResultsMsg.html("An error occurred updating Member Level Settings.");
                    fadeInMessage(memberSettingsResultsMsg);
                    break;
                case "updateService":
                    updateServicesMsg.removeClass("good").removeClass("cancel").addClass("error");
                    updateServicesMsg.html("An error occurred updating services entries.");
                    fadeInMessage(updateServicesMsg);
                    break;
                case "invite":
                    updateInviteMsg.removeClass("good").removeClass("cancel").addClass("error");
                    updateInviteMsg.html("An error occurred sending sms invitation message.");
                    fadeInMessage(updateInviteMsg);
                    break;
            }
            alert("ERROR!  There was an posting data. " + xhr.status + " - " + error);
            if (xhr.status === 400)
            {
                $("#postDataError").html("Error Adding Client: " + xhr.status + " - " + error);
            }
            else
            {
                $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            }
            ajaxPostSuccess = false;
        },
        success: function () {
            ajaxPostSuccess = true;
            $('#loading').data('spinner').stop(); // Stop the spinner
            $("#loading h4").html("Done!");
            $("#loading").dialog("close"); // close loading element
            switch (id)
            {
                case "updateAssoPwd":
                    associatePwd.removeClass("error").addClass("good");
                    associatePwd.html("Your Password was successfully updated.");
                    fadeInMessage(associatePwd);
                    break;
                case "updateAssociate":
                    associateInfoElm.removeClass("error").removeClass("cancel").addClass("good");
                    associateInfoElm.html("Your Account was successfully updated.");
                    fadeInMessage(associateInfoElm);
                    break;
                case "updateAssociateAlerts":
                    associateAlerts.removeClass("error").removeClass("cancel").addClass("good");
                    associateAlerts.html("Your Alert Preferences were successfully updated.");
                    fadeInMessage(associateAlerts);
                    break;
                case "securityQandA":
                    assocSecurity.removeClass("error").addClass("good");
                    assocSecurity.html("Your Security Challenge Answers were successfully updated.");
                    fadeInMessage(assocSecurity);
                    break;
                case "updateDefaultCalendarView":
                    assocDefaultView.removeClass("error").removeClass("cancel").addClass("good");
                    assocDefaultView.html("Your default Calendar View was successfully updated.");
                    fadeInMessage(assocDefaultView);
                    break;
                case "updateDefaultCalendarTimes":
                    assocDefaultTimes.removeClass("error").removeClass("cancel").addClass("good");
                    assocDefaultTimes.html("Your Normal Work Hours were successfully updated.");
                    fadeInMessage(assocDefaultTimes);
                    break;
                case "updateMemberSettings":
                    memberSettingsResultsMsg.removeClass("error").removeClass("cancel").addClass("good");
                    memberSettingsResultsMsg.html("The Member Level Settings were succesfully updated.");
                    fadeInMessage(memberSettingsResultsMsg);
                    break;
                case "updateService":
                    updateServicesMsg.removeClass("error").removeClass("cancel").addClass("good");
                    updateServicesMsg.html("Services were succesfully updated.");
                    fadeInMessage(updateServicesMsg);
                    break;
                case "invite":
                    updateInviteMsg.removeClass("error").removeClass("cancel").addClass("good");
                    updateInviteMsg.html("A text message invitation to become a client member was succesfully sent.");
                    fadeInMessage(updateInviteMsg);
                    break;
            }
            fadeInOutMessage("#postDataSuccess");

        }
    });
    return ajaxPostSuccess;
} // end doAjaxPost function

// post FullCalendar Events to server via ajax
function postEvents(evtsToPost, event) {
//    $("#postDataSuccess").html(""); // clear postDataSuccess
    $("#postDataError").html(""); // clear postDataError
//    $("#postDataSuccess").html(""); // clear postDataSuccess
    $.ajax({
        type: "post",
        url: '../FullCalPost',
        beforeSend: function () {
            $("#postDataError").html("");
            openLoading();
            $("#loading").dialog("open"); // open loading element
            $("#loading h4").html("Processing request.  Please wait...");
            spinner('loading', 'auto');
        },
        data: {calEvents: evtsToPost},
        // timeout: 10000,
        error: function (xhr, status, error) {

            $('#loading').data('spinner').stop(); // Stop the spinner
            $("#loading h4").html("ERROR!");
            $("#loading").dialog("close"); // close loading element
            // append error message to messages element
            $("#postDataError").append("Error Posting Data: " + xhr.status + " - " + error);
            alert("Error Posting Data: " + xhr.status + " - " + error);
//            fadeInOutMessage("#messages");
        },
        success: function (data, textStatus, jqXHR) {
            var p = new ProcessStatus(data);
            $('#loading').data('spinner').stop(); // Stop the spinner
            $("#loading h4").html("Done!");
            $("#loading").dialog("close"); // close loading element
            if (typeof event !== "undefined")
            {
                if (event.eventId === 1234)
                {
                    $('#calendar').fullCalendar('removeEvents', event.id);
                }
//                $('#calendar').fullCalendar("refetchEvents");
            }
            $('#calendar').fullCalendar("refetchEvents");
            // append success message to messages element
//            $("#postDataSuccess").append("Calendar Update Successful!");
//            fadeInOutMessage("#messages");
        }
    });
} // end postEvents function

var statesList = function () {
    var states = [
        "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "LA", "ME", "MD", "MA",
        "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
        "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    ];
    return states;
};

var options = {
    getMemberLevel: function (o) {
        var mlSel = o.client.memberLevel;
        var options = [];
        for (var i in o.memberLevels)
        {
            if (mlSel === o.memberLevels[i].memberLevel)
            {
                options[i] = "<option selected=" + o.memberLevels[i].memberLevel + ">" + o.memberLevels[i].memberLevel + "</option>";
            }
            else
            {
                options[i] = "<option>" + o.memberLevels[i].memberLevel + "</option>";
            }
        }
        return options;
    },
    getState: function (o) {
        var selState;
        if (o instanceof Associate)
        {
            selState = o.state;
        }
        else
        {
            selState = o.client.state;
        }
        var s = [];
        s = statesList();
        for (var state in s)
        {
            if (selState === s[state])
            {
                s[state] = "<option value='" + s[state] + "' selected>" + s[state] + "</option>";
            }
            else
            {
                s[state] = "<option value='" + s[state] + "'>" + s[state] + "</option>";
            }
        }
        return s;
    },
    getSelectedOpt: function (o) {
        var bkgColors = o.memberLevels;
        var bkgColor;
        var options = [];
        for (var y = 0; y < bkgColors.length; y++)
        {
            if (o.client.memberLevel === bkgColors[y].memberLevel)
            {
                options[y] = "<option selected=" + o.client.memberLevel + ">" + o.client.memberLevel + "</option>";
                bkgColor = o.memberLevels[y].memberColor;
            }
            else
            {
                options[y] = "<option value='" + bkgColors[y].memberLevel + "'>" + bkgColors[y].memberLevel + "</option>";
            }
        }
        options.push(bkgColor);
        return options;
    }
}; // end options fucntion objects

var serviceList = {
    getServiceList: function () {
        var s = [];
        doAjaxRequestNoElements("svc", "../FullCalendar", false, "services", function (servicesArr) {
            $.each(servicesArr, function (svc_index, service) {
                s.push(service);
            });
        });
        return s;
    },
    getServiceCategories: function () {
        var c = [];
        doAjaxRequestNoElements("cat", "../FullCalendar", false, "category", function (categoriesArr) {
            $.each(categoriesArr, function (cat_index, category) {
                c.push(category);
            });
        });
        return c;
    },
    getServiceStatusList: function () {
        var c = [];
        doAjaxRequestNoElements("svcStatus", "../FullCalendar", false, "serviceStatusList", function (statusArr) {
            $.each(statusArr, function (status_index, statusObj) {
                c.push(statusObj);
            });
        });
        return c;
    },
    getCalendarEvents: function (userID, numOfEvts) {
        var evts = [];
        doAjaxEventsRequest("calEvts", "../FullCalendar", userID, numOfEvts, false, "client", function (calEventsArr) {
            evts = calEventsArr;
        });
        return evts;
    },
    getFutureEvents: function (id, futureEvts) {
        var evts = [];
        doAjaxEventsRequest("futureCalEvts", "../FullCalendar", id, futureEvts, false, "client", function (calEventsArr) {
            evts = calEventsArr;
        });
        return evts;
    }
}; // end serviceList functions

// message retrieval functions
var messagesList = {
    // get current associate messages
    getMessagesList: function (associateInfo, async) {
        var m = [];
        // retrieve sms message objects
        doAjaxRequest("retrieveMessages", "../FullCalendar", async, associateInfo.id, function (messagesArr) {
            $.each(messagesArr, function (index, message) {
                var messageObj = new SMSMessage(message);
                m.push(messageObj);
            }); // end request to retrieve sms message objects
        });
        return m;
    },
    getNextMsgs: function (key1, key2, key3, associateInfo, url, async) {
        var m = [];
        // retrieve sms message objects
        doAjaxMsgRequest2(key1, key2, key3, associateInfo.id, url, async, function (messagesArr) {
            $.each(messagesArr, function (index, message) {
                var messageObj = new SMSMessage(message);
                m.push(messageObj);
            }); // end request to retrieve sms message objects
        });
        return m;
    },
    getInviteRequests: function (associateInfo, dayNum) {
        var m = [];
        var messageObj;
        doAjaxMsgRequest("retrieveMsgInvites", "../FullCalendar", false, associateInfo.id, dayNum, function (messagesArr) {
            $.each(messagesArr, function (index, message) {
                messageObj = new SMSMessage(message);
                m.push(messageObj);
            }); // end request to retrieve sms message objects
        });
        return m;
    },
    selectMsgInviteRequest: function (associateInfo, smsObj) {
        var messageObj;
        doAjaxMsgRequest("selectMsgInviteRequest", "../FullCalendar", false, associateInfo.id, smsObj, function (msg) {
            messageObj = new SMSMessage(msg);
        });
        return messageObj;
    }
}; // end message retrieval functions

function isClientOfAssociate(obj) {
    var m = [];
    var messageObj;
    var jsonObj = convert.stringify(obj);
    doAjaxMsgRequest("checkRequest", "../FullCalendar", false, obj.associateId, jsonObj, function (messagesArr) {
        $.each(messagesArr, function (index, message) {
            messageObj = new SMSMessage(message);
            m.push(messageObj);
        });
    });
    return m;
}

var services = {
    // services option listing
    getServices: function (o) {
        var servId = o.client.serviceNormalid;
        var optionGrp = [];

        for (var c in serviceCategories)
        {
            optionGrp[c] = "<optgroup label='" + serviceCategories[c].name + "'>";
            for (var s in serviceListing)
                if (serviceCategories[c].category_id === serviceListing[s].category_id)
                {
                    if (servId === serviceListing[s].serviceId)
                    {
                        optionGrp[c] += "<option value='" + serviceListing[s].serviceId + "' selected='" + serviceListing[s].description + "'>" + serviceListing[s].description + "</option>";
                    }
                    else
                    {
                        optionGrp[c] += "<option value='" + serviceListing[s].serviceId + "'>" + serviceListing[s].description + "</option>";
                    }
                }
            +"</optgroup>";
        }
        return optionGrp;
    },
    getSvcsStringify: function (o) {
        if (o instanceof Object)
        {
            var servId = o.client.serviceNormalid;
        }
        var optionGrp = [];

        for (var c in serviceCategories)
        {
            optionGrp[c] = "<optgroup label='" + serviceCategories[c].name + "'>";
            for (var s in serviceListing)
                if (o instanceof Object)
                {
                    if (serviceCategories[c].category_id === serviceListing[s].category_id)
                    {
                        if (servId === serviceListing[s].serviceId)
                        {
                            optionGrp[c] += "<option value='" + JSON.stringify(serviceListing[s]) + "' selected='" + serviceListing[s].description + "'>"
                                    + serviceListing[s].description + " " + minutesFormat(serviceListing[s].serviceTime)
                                    + " &#36;" + serviceListing[s].price.toFixed(2)
                                    + "</option>";
                        }
                        else
                        {
                            optionGrp[c] += "<option value='" + JSON.stringify(serviceListing[s]) + "'>" + serviceListing[s].description
                                    + " " + minutesFormat(serviceListing[s].serviceTime)
                                    + " &#36;" + serviceListing[s].price.toFixed(2)
                                    + "</option>";
                        }
                    }
                }
                else
                {
                    if (serviceCategories[c].category_id === serviceListing[s].category_id)
                    {
                        optionGrp[c] += "<option value='" + JSON.stringify(serviceListing[s]) + "'>" + serviceListing[s].description
                                + " " + minutesFormat(serviceListing[s].serviceTime)
                                + " &#36;" + serviceListing[s].price.toFixed(2)
                                + "</option>";
                    }
                }
            +"</optgroup>";
        }
        return optionGrp;
    },
    getServiceName: function (o) {
        var svcList = serviceList.getServiceList();
        var y = "not yet selected";
        if (o instanceof Object)
        {
            var servId = o.client.serviceNormalid;
            for (var i = 0; i < svcList.length; i++)
                if (servId === svcList[i].serviceId)
                {
                    y = svcList[i].description;
                }
        }
        else if (typeof o === "number")
        {
            for (var i = 0; i < svcList.length; i++)
                if (o === svcList[i].serviceId)
                {
                    y = svcList[i].description;
                }
        }
        return y;
    },
    getServiceObj: function (serviceId) {
        var s;
        for (var i in serviceListing)
            if (serviceId === serviceListing[i].serviceId)
            {
                s = serviceListing[i];
            }
        return s;
    },
    getServiceStatusObj: function (statusId) {
        var statusObj;
        for (var i in serviceStatusArr)
            if (statusId === serviceStatusArr[i].statusId)
            {
                statusObj = serviceStatusArr[i];
            }
        return statusObj;
    }
}; // end services functions

// functions for facilitaing associate data
var associateClass = {
    getAssociateList: function (o) {
        var a = associateListArr;
        var assocList = [];
        if (o instanceof Object)
        {
            var selA = o.associate2.id;
            if (selA === 0)
            {
                var c = {
                    firstName: "select an associate",
                    lastName: "",
                    id: 0
                };
                a.unshift(c);
            }
        }
        for (var i in a)
        {
            if (o instanceof Object)
            {
                if (selA === a[i].id)
                {
                    assocList[i] = "<option value='" + JSON.stringify(a[i]) + "' selected>" + a[i].firstName + " " + a[i].lastName
                            + "</option>";
                }
                else
                {
                    assocList[i] = "<option value='" + JSON.stringify(a[i]) + "'>" + a[i].firstName + " " + a[i].lastName
                            + "</option>";
                }
            }
            else
            {
                assocList[i] = "<option value='" + JSON.stringify(a[i]) + "'>" + a[i].firstName + " " + a[i].lastName
                        + "</option>";
            }
        }
        return assocList;
    },
    delClientList: function (array) {
        var table = "<thead><tr><th>first</th><th>last</th><th>email</th></tr></thead>";
        table = table + "</tbody>";
        for (var i in array)
        {
            table += "<tr>" + "<td>" + array[i].client.firstName + "</td>" + "<td>" + array[i].client.lastName + "</td>" + "<td>" + array[i].client.email + "</td>" + "</tr>";
        }
        return table + "</tbody>";
    },
    getAssociatePicture: function (o) {
        var assocImgId = 0;
        var img;
        if (o.associate2.imgUpl === true)
        {
            assocImgId = o.associate2.id;
        }
        img = "<input type='hidden' name='associateImg' id='associateImg' value='" + JSON.stringify(o) + "'>" + '<img src="../img/associates/' + assocImgId + '.png" alt="associate img" alt="img" >';
        return img;
    },
    getCurrentAssociateInfo: function () {
        var a;
        doAjaxRequestNoElements("assoProfile", "../FullCalendar", false, "currentAssoc", function (result) {
            a = new Associate(result);
        });
        return a;
    },
    renderAssociate: function (assoObj) {

        var associateTable = "<table id='table-personal' class='width-first-td profile-table-1 alignRight-td table-stripe-main roundCorners-td1 roundCorners-td2 tableBorder-top' >" + "<tbody>";

        associateTable +=
                "<tr>" + "<td title='account ID number'>" + "id&#58;" + "</td>" + "<td>" + assoObj.id + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate email address'>" + "email&#58;" + "</td>" + "<td>" + assoObj.email + "</td>" + "</tr>"
                + "<tr>" + "<td title='mobile phone'>" + "mobile&#58;" + "</td>" + "<td>" + formatPhone(assoObj.mobilePhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='home phone number'>" + "home&#58;" + "</td>" + "<td>" + formatPhone(assoObj.homePhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='work phone number'>" + "work&#58;" + "</td>" + "<td>" + formatPhone(assoObj.workPhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='other phone number'>" + "other&#58;" + "</td>" + "<td>" + formatPhone(assoObj.otherPhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate address'>" + "address&#58;" + "</td>" + "<td>" + assoObj.address + "</td>" + "</tr>"
                + "<tr>" + "<td title='city'>" + "city&#58;" + "</td>" + "<td>" + assoObj.city + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate state'>" + "state&#58;" + "</td>" + "<td>" + assoObj.state + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate zip code'>" + "zip&#58;" + "</td>" + "<td>" + assoObj.zip + "</td>" + "</tr>";
        associateTable += "</tbody>" + "</table>";
        return associateTable;
    },
    renderDefaultCalView: function (assoObj) {
        var viewOptions = [];
        // make use of Associative Array
        var viewsArray = [];
        viewsArray["month"] = "Month";
        viewsArray["agendaWeek"] = "Week";
        viewsArray["agendaDay"] = "Day";

        for (var i in viewsArray)
        {
            if (i === assoObj.defaultCalendarView)
            {
                viewOptions += "<option value='" + i + "' selected >" + viewsArray[i] + "</option> ";
            }
            else
            {
                viewOptions += "<option value='" + i + "'>" + viewsArray[i] + "</option>";
            }
        }
        return viewOptions;
    },
    renderDefaultTimes: function (assoObj, t) {
        var timesOptions = [];
        var time;

        switch (t)
        {
            case "startMTin":
                time = jsTime.formatTime(moment(assoObj.morningTimeIn, moment.ISO_8601))
                break;
            case "startMTout":
                time = jsTime.formatTime(moment(assoObj.morningTimeOut, moment.ISO_8601))
                break;
            case "startATin":
                time = jsTime.formatTime(moment(assoObj.afternoonTimeIn, moment.ISO_8601))
                break;
            case "startATout":
                time = jsTime.formatTime(moment(assoObj.afternoonTimeOut, moment.ISO_8601))
                break;
        }

        for (var i in timesArray)
        {
            if (timesArray[i] === time)
            {
                timesOptions += "<option value='" + timesArray[i] + "' selected >" + timesArray[i] + "</option>";
            }
            else
            {
                timesOptions += "<option value='" + timesArray[i] + "'>" + timesArray[i] + "</option>";
            }
        }
        return timesOptions;
    },
    associateInput: function (assoObj) {
        var asssoInputTable = "<table id='personal-table' class='width-first-td profile-table-1 alignRight-td roundCorners-td1 roundCorners-td2 width-425-pixels tableBorder-top' >" + "<tbody>";

        var stateList = options.getState(assoObj);

        asssoInputTable +=
                "<tr>" + "<td>" + "<label for='account ID number' title='id'>" + "id&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='text' name='id' id='id' value='" + assoObj.id + "'>" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='email' title='associate email address'>" + "email&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='email' name='email' id='email' value='" + assoObj.email + "' >" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='mobilePhone' title='mobile phone'>" + "mobile&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='tel' name='mobilePhone' autocomplete='off' id='mobilePhone' value='" + formatPhone(assoObj.mobilePhone) + "'placeholder='a 10 digit number is required'  title='a 10 digit number is required' required >" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='homePhone' title='home phone'>" + "home&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='tel' name='homePhone' id='homePhone' value='" + formatPhone(assoObj.homePhone) + "'>" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='workPhone' title='work phone'>" + "work&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='tel' name='workPhone' id='workPhone' value='" + formatPhone(assoObj.workPhone) + "'>" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='otherPhone' title='other phone'>" + "other&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='tel' name='otherPhone' id='otherPhone' value='" + formatPhone(assoObj.otherPhone) + "'>" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='address' title='address'>" + "address&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='text' name='address' id='address' value='" + assoObj.address + "'>" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='city' title='city'>" + "city&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='text' name='city' id='city' value='" + assoObj.city + "'>" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='state' title='state'>" + "state&#58" + "</label>" + "</td>"
                + "<td>" + "<select name='state' id='state'>" + stateList + "</select>" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='zip' title='zip'>" + "zip&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='text' name='zip' id='zip' value='" + assoObj.zip + "'>" + "</td>"
                + "</tr>";

        asssoInputTable += "</tbody>" + "</table>";
        return asssoInputTable;
    },
    alertPrefs: function (o, e) {
        if (o.emailAdAlerts === true)
        {
            $(e + " #email-ad-alerts").prop('checked', true);
        }
        else
        {
            $(e + " #email-ad-alerts").prop('checked', false);
        }
        if (o.smsAdAlerts === true)
        {
            $(e + " #sms-ad-alerts").prop('checked', true);
        }
        else
        {
            $(e + " #sms-ad-alerts").prop('checked', false);
        }
        if (o.emailApptAlerts === true)
        {
            $(e + " #email-appt-alerts").prop('checked', true);
        }
        else
        {
            $(e + " #email-appt-alerts").prop('checked', false);
        }
        if (o.smsApptAlerts === true)
        {
            $(e + " #sms-appt-alerts").prop('checked', true);
        }
        else
        {
            $(e + " #sms-appt-alerts").prop('checked', false);
        }
    },
    associateAccount: function (assoObj) {
        var assocAcctTable;
        assocAcctTable =
                "<tr>" + "<td title='member number'>" + "member number&#58;" + "</td>" + "<td>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='account id'>" + "account id&#58;" + "</td>" + "<td>" + assoObj.id + "</td>" + "</tr>"
                + "<tr>" + "<td title='first name on account'>" + "first name&#58;" + "</td>" + "<td>" + assoObj.firstName + "</td>" + "</tr>"
                + "<tr>" + "<td title='last name on account '>" + "last name&#58;" + "</td>" + "<td>" + assoObj.lastName + "</td>" + "</tr>"
                + "<tr>" + "<td title='account email'>" + "email&#58;" + "</td>" + "<td>" + assoObj.email + "</td>" + "</tr>"
                + "<tr>" + "<td title='total sms messages sent this month'>" + "sms messages sent this month, " + moment().format('MMM') + "&#58;" + "</td>" + "<td>" + assoObj.smsSentRange + "</td>" + "</tr>"
                + "<tr>" + "<td title='total sms messages'>" + "sms mgs sent&#58;" + "</td>" + "<td>" + assoObj.smsSent + "</td>" + "</tr>";
        return assocAcctTable;
    },
    associatePassword: function (assoObj) {
        var assocPwdTable = +"<tr>" + "<td>" + "<label for='associate-password' title='associate password'>" + "password&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='password' name='associate-password' id='associate-password' value='" + assoObj.address + "'>" + "</td>"
                + "</tr>";
        return assocPwdTable;
    },
    associateEditPwd: function () {
        var assocEditPwd = "<tr>" + "<td>" + "<label for='password-1' title='enter new password'>" + "new&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='password' name='password-1' id='password-1' maxlength='15' placeholder='password must be at least 8 characters long' title='&#8226; Password Must include at least one symbol\n\
&#8226; Must contain at least one uppercase letter\n\
&#8226; Between 8 and 15 characters long\n\
&#8226; May contain any character except space\n\
&#8226; Must include at least one lowercase letter\n\
&#8226; Must include at least one numeric digit' >" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='password-2' title='re-enter password'>" + "re-enter&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='password' name='password-2' id='password-2' maxlength='15' placeholder='password must be at least 8 characters long' title='&#8226; Password Must include at least one symbol\n\
&#8226; Must contain at least one uppercase letter\n\
&#8226; Between 8 and 15 characters long\n\
&#8226; May contain any character except space\n\
&#8226; Must include at least one lowercase letter\n\
&#8226; Must include at least one numeric digit'>" + "</td>"
                + "</tr>";
        return assocEditPwd;
    }
}; // end associateClass functions

// new Event Object function
var EventObj = function (event) {
    this.startMoment = event.start.toJSON();
    this.timeZone = timeZone.getTimeZone();
    this.start = event.start.set("second", 00);
    this.end = event.end.set("second", 00);
    this.startTimestamp = event.start.format("YYYY-MM-DD HH:mm:ss");
    this.endTimestamp = event.end.format("YYYY-MM-DD HH:mm:ss");
    this.startOffset = timeZone.getTimeOffset(event.start);
    this.endOffset = timeZone.getTimeOffset(event.end);
    this.startTimeUtc = event.start.utc().format();
    this.endTimeUtc = event.end.utc().format();
    this.title = event.title;
    this.serviceTime = event.serviceTime;
    this.allDay = event.allDay;
    this.backgroundColor = event.backgroundColor;
    this.color = event.color;
    this.editable = event.editable;
    this.durationEditable = event.durationEditable;
    this.textColor = event.textColor;
    this.eventId = event.eventId;
    this.services = event.services;
    this.associateName = event.associateName;
    this.notes = event.notes;
    this.statusId = event.statusId;
    this.notifyClient = this.start.isSameOrAfter(moment());
    this.notifyAssociate = this.start.isSameOrAfter(moment());
    this.restoreTime = event.restoreTime;
    this.newClient = event.newClient;
    this.action = event.action;
    this.actionType = event.actionType;
    this.cancelEvts = event.cancelEvts;
    this.client = event.client;
    this.associate2 = event.associate2;
    this.services = event.services;
    this.serviceStatus = event.serviceStatus;
    this.memberLevels = event.memberLevels;
    this.smsMessage = event.smsMessage;
    this.eventChange = event.eventChange;
}; // end EventObj function

// ****** EventObj prototypes ******
// remove client name from title
EventObj.prototype.getTitle = function () {
    if (typeof this.title !== "undefined")
    {
        var i = this.title.indexOf(":", 0);
        if (i !== -1)
        {
            this.title = this.title.substring(0, i);
        }
    }
    return this.title;
};

EventObj.prototype.toJson = function () {
    return this.startMoment.toJSON();
};
EventObj.prototype.getStartTime = function () {
    return moment.tz(this.start, timeZone.getTimeZone()).format("h:mm a");
};
EventObj.prototype.getEndTime = function () {
    return moment.tz(this.end, timeZone.getTimeZone()).format("h:mm a");
};
EventObj.prototype.getServiceTimeFormat = function () {
    var minutes = this.serviceTime % 60;
    var hours = Math.floor(this.serviceTime / 60);
    var hourFmt = " hour ";
    var minFmt = " minutes";
    minutes = (minutes < 10 ? '0' : '') + minutes;
    //hours = (hours < 10 ? '0' : '') + hours;
    if (hours === 0)
    {
        return minutes + minFmt;
    }
    else
    {
        if (hours > 1)
        {
            hourFmt = " hours ";
        }
        if (minutes === "00")
        {
            return hours + hourFmt;
        }
        return hours + hourFmt + minutes + minFmt;
    }
};
EventObj.prototype.formatDate = function () {
    var momObj = moment(this.start, dateFmts),
            momFormat = momObj.format("ddd MMM D, YYYY");
    return momFormat;
};
EventObj.prototype.isAllDay = function () {
    if (this.allDay === true)
    {
        return "Yes";
    }
    else
    {
        return "No";
    }
};
EventObj.prototype.imageUploadId = function (i) {
    if (i.imgUpl === true)
    {
        return i.id;
    }
    else
    {
        return 0;
    }
};
// ****** END EventObj prototypes ******

var Client = function (firstName, lastName, email, homePhone, workPhone, company,
        address, city, state, zip, id, imgUpl, memberLevel, memberLevels) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.homePhone = homePhone;
    this.workPhone = workPhone;
    this.company = company;
    this.address = address;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.id = id;
    this.imgUpl = imgUpl;
    this.memberLevel = memberLevel;
    this.memberLevels = memberLevels;
};
var UserObj = function (firstName, lastName, email, id, associate2,
        preferredAssociateName, preferredAssociateId, memberLevels, client) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = email;
    this.customerId = id;
    this.associate2 = associate2;
    this.associateName = preferredAssociateName;
    this.associateId = preferredAssociateId;
    this.memberLevels = memberLevels;
    this.client = client;
};

// associate object
var Associate = function (i) {
    this.id = i.id;
    this.firstName = i.firstName;
    this.lastName = i.lastName;
    this.email = i.email;
    this.homePhone = i.homePhone;
    this.workPhone = i.workPhone;
    this.mobilePhone = i.mobilePhone;
    this.otherPhone = i.otherPhone;
    this.imgUpl = i.imgUpl;
    this.address = i.address;
    this.city = i.city;
    this.state = i.state;
    this.zip = i.zip;
    this.emailAdAlerts = i.emailAdAlerts;
    this.emailApptAlerts = i.emailApptAlerts;
    this.smsAdAlerts = i.smsAdAlerts;
    this.smsApptAlerts = i.smsApptAlerts;
    this.smsSent = i.smsSent;
    this.smsSentRange = i.smsSentRange;
    this.memberSettings = i.memberSettings;
    this.defaultCalendarView = i.defaultCalendarView;
    this.morningTimeIn = i.morningTimeIn;
    this.morningTimeOut = i.morningTimeOut;
    this.afternoonTimeIn = i.afternoonTimeIn;
    this.afternoonTimeOut = i.afternoonTimeOut;
};

var Message = function (obj) {
    this.messageId = obj.messageId;
    this.eventId = obj.eventId;
    this.idMessageResponse = obj.idMessageResponse;
    this.client = obj.client;
    this.associate2 = obj.associate2;
    this.clientId = obj.Id;
    this.message = stripQuotes(obj.message);
    this.timeToSend = obj.timeToSend;
    this.subject = stripQuotes(obj.subject);
    this.msgPhoneNumber = formatPhone(obj.mobilePhone);
    this.status = obj.status;
    this.messageTypeId = obj.messageTypeId;
    this.code = obj.code;
    this.sentById = obj.sentById;
    this.timestamp = obj.timestamp;
};

Message.prototype.name = function () {
    return this.clientId + " " + this.status;
};

var SMSMessage = function (obj) {
    this.isMessageInvite = obj.isMessageInvite;
    this.msgError = obj.msgError;
    this.msgMessageGrpId = obj.msgMessageGrpId;
    this.trimmedSubject = obj.trimmedSubject;
    this.messageId = obj.messageId;
    this.msgSentGroup = obj.msgSentGroup;
    this.eventId = obj.eventId;
    this.recipientsCount = obj.recipientsCount;
    this.idMessageResponse = obj.idMessageResponse;
    this.client = obj.client;
    this.associateSender = obj.associateSender;
    this.message = stripQuotes(obj.message);
    this.timeToSend = obj.timeToSend;
    this.timestamp = obj.timestamp;
    this.stampToSend = obj.stampToSend;
    this.timeToSendInteger = obj.timeToSendInteger;
    this.subject = stripQuotes(obj.subject);
    this.phoneNumber = formatPhone(obj.phoneNumber);
    this.status = obj.status;
    this.status_id = obj.status_id;
    this.statusColor = obj.statusColor;
    this.messageTypeId = obj.messageTypeId;
    this.code = obj.code;
    this.sentById = obj.sentById;
    this.timestamp = obj.timestamp;
    this.clientId = obj.Id;
    this.isClientOfAssociate = obj.isClientOfAssociate;
    this.isAccountActive = obj.isAccountActive;
    this.clientMessage = obj.clientMessage;
    this.associate2 = obj.associate2;
};

SMSMessage.prototype.getStatus = function () {
    if (this.status === "Success")
    {
        this.status = "Sent";
        return this.status;
    }
    else
    {
        return this.status;
    }
};

var ProcessStatus = function (proObj) {
    this.processClientCalendar = proObj.processClientCalendar;
    this.processAssociateCalendar = proObj.processAssociateCalendar;
    this.processClientSms = proObj.processClientSms;
    this.processAssociateSms = proObj.processAssociateSms;
    this.processClientEmail = proObj.processClientEmail;
    this.processAssociateEmail = proObj.processAssociateEmail;
    this.processAdminSms = proObj.processAdminSms;
    this.processAdminEmail = proObj.processAdminEmail;
    this.processSuperAdminSms = proObj.processSuperAdminSms;
    this.processSuperAdminEmail = proObj.processSuperAdminEmail;
    this.validateUser = proObj.validateUser;
    this.processCurrentUserId = proObj.processCurrentUserId;
    this.processNewUserId = proObj.processNewUserId;
    this.processClientId = proObj.processClientId;
    this.processErrorArray = proObj.processErrorArray;
    this.processAssociateAvailabilty = proObj.processAssociateAvailabilty;
    this.insertAssociateAvailability = proObj.insertAssociateAvailability;
};

var stripQuotes = function (msg) {
    if (msg === undefined)
    {
        msg = "no message to display";
    }
    else
    {
        msg = msg.replace(/'/g, '&#39;');
    }
    return msg;
};

$(function () {
    $("#datepicker, #datepicker2, #dateToSendMsg, #datepicker3").datepicker({
        minDate: -0,
        maxDate: "+12M +0D",
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        selectOtherMonths: true,
        showOtherMonths: true
    });
});
$(function () {
    $("#calendarAssociate").datepicker({
        changeMonth: true,
        changeYear: true,
        selectOtherMonths: true,
        showOtherMonths: true,
        showButtonPanel: true
    });
});
$(function () {
    var dates = $("#from, #to").datepicker({
        // defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        selectOtherMonths: true,
        showOtherMonths: true,
        showButtonPanel: true,
        // numberOfMonths: 2,
        minDate: -0,
        //maxDate: "+48M",
        onSelect: function (selectedDate) {
            var option = this.id === "from" ? "minDate" : "maxDate",
                    instance = $(this).data("datepicker"),
                    date = $.datepicker.parseDate(
                            instance.settings.dateFormat ||
                            $.datepicker._defaults.dateFormat,
                            selectedDate, instance.settings);
            dates.not(this).datepicker("option", option, date);
        }
    });
});
$(function () {
    $("#calendarAsso").datepicker({
        minDate: -0,
        maxDate: "+3M +10D",
        changeMonth: true,
        showButtonPanel: true,
        selectOtherMonths: true,
        showOtherMonths: true
    });
});
$(function () {
    $("#fromN").datepicker({
        defaultDate: "+1w",
        minDate: -0,
        maxDate: "+12M +10D",
        changeMonth: true,
        numberOfMonths: 1,
        showOtherMonths: true,
        selectOtherMonths: true,
        showButtonPanel: true,
        changeYear: true,
        onClose: function (selectedDate) {
            $("#toN").datepicker("option", "minDate", selectedDate);
        }
    });
    $("#toN").datepicker({
        defaultDate: "+1w",
        maxDate: "+12M +0D",
        changeMonth: true,
        numberOfMonths: 2,
        showOtherMonths: true,
        selectOtherMonths: true,
        showButtonPanel: true,
        onClose: function (selectedDate) {
            $("#fromN").datepicker("option", "maxDate", selectedDate);
        }
    });
});
// get asociates
var getAssociates = function () {
    var associateSet = [];
    doAjaxRequest("allA", "../FullCalendar", false, "na", function (results) {
        $.each(results, function (index, value) {
            var a = new Associate(
                    value);
            associateSet.push(a);
        });
    });
    return associateSet;
};
$(document).ready(function () {
    serviceStatusArr = serviceList.getServiceStatusList(); // get service status listing array
//
    //    $("h3").hover(
//            function() {
//                $(this).toggleClass("highlight");
//            },
//            function() {
//                $(this).toggleClass("highlight");
//            }
//    );
    $(".clientInfo #accordion, .editUser #accordion-2, .confirmUser #accordion-3").hide();
    $(function () {
        $(".clientInfo #accordion, .editUser #accordion-2, #newEventField #accordion-4, #newEventField #accordion-5, .clientInfo #accordion-4").accordion({
            collapsible: true,
            active: false,
            heightStyle: "content"
        });
    });
    $('h3').hover(
            function () {
                $(this).toggleClass("highlight-user");
            },
            function () {
                $(this).toggleClass("highlight-user");
            }
    );
    $("#checkoutForm #associateLogin").validate({
        rules: {
            emailAddress: {
                required: true,
                email: true
            },
            password: {
                required: true,
                password: true,
                minlength: 8
            }
        }
    });
    //calendar validation
    $("#form").validate({
        rules: {
            firstNm: {
                required: true,
                minlength: 3
            },
            lastNm: {
                required: true,
                minlength: 3
            },
            email: {
                required: true,
                email: true
            },
            mobilePhone: {
                required: true,
                number: true,
                minlength: 9
            }
        }
    });
    $("#submitRequest").submit("click", function (event) {
        var response = confirm("Submit your request?");
        if (!response)
        {
            $("#updateSrvMessage").html("Associate Services Update Request Cancelled.");
            fadeInMessage("#updateSrvMessage");
            // prevent the submission of the form if any entries are invalid
            event.preventDefault();
        }
    }); // end submit
});
function fadeInOutMessage(element) {
    $(element).fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(2000);
}
function fadeInMessage(element) {
    $(element).fadeIn(100).fadeOut(100).fadeIn(100);
}
function fadeOutIn(element) {
    $(element).fadeOut(100).fadeIn(1000);
}
function fadeOut(element) {
    $(element).fadeOut(100);
}
function fadeIn(element) {
    $(element).fadeIn(1000);
}


var convert = {
    stringifyEvtObj: function (event) {
        if (event instanceof EventObj)
        {
            var eventNew = [];
            var eventObj = new EventObj(event);
            eventNew.push(eventObj);
            var eventStr = JSON.stringify(eventNew);
            eventStr = eventStr.replace(/\"/g, '\''); // replace double quotes with single quote
            return eventStr;
        }
        else
        {
            return event;
        }
    },
    stringifyObj: function (o) {
        if (o instanceof Object)
        {
            if (o instanceof Array)
            {
                var a = JSON.stringify(o);
                a = a.replace(/\"/g, '\''); // replace double quotes with single quote
                return a;
            }
            else
            {
                var eventNew = [];
                //var eventObj = new EventObj(o);
                eventNew.push(o);
                var s = JSON.stringify(eventNew);
                s = s.replace(/\"/g, '\''); // replace double quotes with single quote
                return s;
            }
        }
        return o;
    },
    stringify: function (e) {
        if (e instanceof Object)
        {
            var s = JSON.stringify(e);
            return s;
        }
        return e;
    },
    parseJsonObj: function (element) {
        var c = $(element, this).val();
        var o = $.parseJSON(c);
        return o;
    },
    capitalize: function (text) {
        return text.toUpperCase();
    }
}; // end convert class functions

var timeZone = {
    getTimeZone: function () {
        return moment.tz.guess(true);
    },
    getUtcTime: function (momObj) {
        var a = moment.tz(momObj, timeZone.getTimeZone());
        return a.utc().format();
    },
    getTimeOffset: function (momObj) {
        var a = moment.tz(momObj, timeZone.getTimeZone());
        return a.format();
    },
    addTimeZone: function (momObj) {
        return momObj.add(timeZone.getTimeZone());
    }
};

// Date Object functions
var jsDate = {
    fmtStr: function (d) {
        var momObj = moment(d, dateFmts),
                momFormat = momObj.format("M/D/YYYY");
        return momFormat;
    },
    formatDate: function (e) {
        var momObj = moment(e, dateFmts);
        var f = momObj.format("ddd MMM D, YYYY");
        return f;
    },
    formatDate2: function (e) {
        var momObj = moment(e, dateFmts);
        var f = momObj.format("ddd MMM D, YY");
        return f;
    },
    formatDateTime: function (e) {
        var d = moment(e);
        var dt = d.format("YYYY-MM-DD HH:mm:ss");
        return dt;
    },
    formatDateTime2: function (e) {
        var momObj = moment(e, dateTimeFmts);
        var dt = momObj.format("YYYY-MM-DD h:mm:ss a");
        return dt;
    },
    formatDateTime3: function (e) {
        var momObj = moment(e, dateTimeFmts);
        var dt = momObj.format("ddd MMM DD, YYYY h:mm A");
        return dt;
    },
    formatDateTime4: function (e) {
        var momObj = moment(e, dateTimeFmts);
        var dt = momObj.format("ddd MMM DD, YYYY h:mm:ss A");
        return dt;
    },
    // this function returns false if the selected from date is greater than the selected to date
    validateToFromDates: function (t, f) {
        var to = moment(t, dateFmts),
                from = moment(f, dateFmts);
        return from.isBefore(to);
    },
    // this function returns false if date format is invalid or date is less than today
    validateDate: function (d) {
        var datePattern = /^[01]?\d[\/-][0-3]\d[\/-]20[1-3][0-9]$|^20[1-3][0-9][-/](0?[1-9]|1[012])[-/](0?[1-9]|[12][0-9]|3[01])$/;
        var today = moment(),
                momObj = moment(d, dateFmts);
        momObj.seconds(1);
        today.seconds(0).hour(0).minute(0).milliseconds(0);
        if (!datePattern.test(d) || momObj.isBefore(today))
        {
            return false;
        }
    },
    todaysDate: function () {
        var now = moment();
        var f = now.format("M/D/YYYY,");
        return f;
    },
    // this function returns false if selected date is greater than one year from the current date
    validateOneYear: function (d) {
        var momObj = moment(d, dateFmts);
        var oneYear = moment().add(1, 'y').add(1, 's');
        oneYear.seconds(0).hour(0).minute(0).milliseconds(0);
        if (momObj.isAfter(oneYear))
        {
            return false;
        }
    },
    // this function returns false if selected date is less than today
    valSelectedDate: function (d) {
        var selDate = moment(d, dateFmts);
        // get today's date, hours, minutes and seconds
        var now = moment();
        var hour = now.hour();
        var minutes = now.minutes();
        var seconds = now.seconds();
        selDate.hour(hour);
        selDate.minutes(minutes);
        selDate.seconds(seconds);
        selDate.add(1, 's');// add 1 second to selected date
        if (selDate.isBefore(now))
        {
            return false;
        }
    },
    convToMomObj: function (d) {
        if (!moment.isMoment(d) && d instanceof Date)
        {
            var b = moment(d);
            return b;
        }
        else if (moment.isMoment(d))
        {
            return d;
        }
    },
    convToJsDate: function (t) {
        if (t instanceof Date)
        {
            return t;
        }
        else if (moment.isMoment(t))
        {
            var h = t.hour(),
                    mi = t.minutes(),
                    mo = t.month(),
                    da = t.date(),
                    y = t.year();
            var j = new Date(y, mo, da, h, mi);
            return j;
        }
    }
}; //end Date Object functions

// Time Object functions
var jsTime = {
    // this function returns false if the selected start time is greater than selected end time
    validateTime: function (s, e) {
        var startTime = moment(s, "h:mm a"),
                endTime = moment(e, "h:mm a");
        if (endTime.isBefore(startTime) || endTime.isSame(startTime))
            return false;
    },
    validateTime2: function (m1, m2, a1, a2) {
        var errors = [];
        var morIn = moment(m1, "h:mm a"),
                morOut = moment(m2, "h:mm a"),
                aftIn = moment(a1, "h:mm a"),
                aftOut = moment(a2, "h:mm a");

        if (morOut.isBefore(morIn) || morOut.isSame(morIn))
            errors.push("morError");
        if (aftOut.isBefore(aftIn) || aftOut.isSame(aftIn) || aftIn.isBefore(morIn) || aftIn.isBefore(morOut)
                || aftIn.isSame(morOut) || aftOut.isSame(morOut))
            errors.push("aftError");

        return errors;
    },
    // convert time string to moment object
    toMomentObj: function (momObj, timeStr) {
        var n = moment(timeStr, "h:mm a"),
                hour = n.hour(),
                minutes = n.minutes(),
                month = momObj.month(),
                date = momObj.date(),
                year = momObj.year();
        var calendar = $('#calendar').fullCalendar('getCalendar');
        var c = calendar.moment();
        c.month(month);
        c.date(date);
        c.year(year);
        c.hours(hour);
        c.minutes(minutes);
        c.seconds(0);
        return c;
    },
    // moment.js time format converter
    formatTime: function (d) {
        var f;
        var momentObj;
        if (d === null)
        {
            momentObj = moment();
            momentObj.hours(0);
            momentObj.minutes(0);
            momentObj.seconds(0);
        }
        else
        {
            momentObj = moment(d);
        }
        f = momentObj.format("h:mm a");
        return f;
    },
    fmtTime: function (m) {
        var momObj = moment(m);
        var b = momObj.format("HH:mm");
        return b;
    },
    // FullCalendar converter utility to convert time from dateTime objects
    fmtTimeAlt: function (d) {
        var f = d.format('h:mmt');
        return f;
    },
    addMinutes: function (momObj, t) {
        var m = momObj.add(t, 'm');
        return m;
    },
    timeIncrements: function () {
        var timesArray = [];
        var now = moment("00:00", "HH:mm");
        for (var i = 0; i < 95; i++)
        {
            timesArray[i] = jsTime.formatTime(now.add(15, 'm'));
        }
        return timesArray;
    }
}; // end Time Object functions

var validateForm = {
    updateTips: function (t, tips) {
        tips.html(t).addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    },
    checkRegexp: function (o, regexp, n, tips) {
        if (!(regexp.test(o.val())))
        {
            o.addClass("ui-state-error");
            validateForm.updateTips(n, tips);
            return false;
        }
        else
        {
            return true;
        }
    },
    checkLength: function (o, n, min, max, tips) {
        if (o.val().length > max || o.val().length < min)
        {
            o.addClass("ui-state-error");
            validateForm.updateTips("Length of " + "<font color='#c00'>" + n + "</font>"
                    + " must be between " + min + " and " + max + " characters.", tips);
            return false;
        }
        else
        {
            return true;
        }
    },
    emailExists: function (e, n) {
        var clients = [];
        doAjaxRequest("ci", "../FullCalendar", false, "clientInfo", function (results) {
            $.each(results, function (index, value) {
                var o = new EventObj(value);
                clients.push(o);
            });
        });
        for (var i = 0; i < clients.length; i++)
        {
            if (clients[i].client.email === e)
            {
                validateForm.updateTips(n + " " + "<b>" + clients[i].client.email + "</b>"
                        + " is assigned to " + "<b>" + clients[i].client.firstName + " " + clients[i].client.lastName + "</b>");
                return false;
            }
            else
            {
                return true;
            }
        }
    },
    doesMatch: function (a, b, msg, tips) {
        if (a.val() !== b.val())
        {
            validateForm.updateTips(msg, tips);
            b.addClass("ui-state-error");
            return false;
        }
        else if (a.val() === b.val())
        {
            return true;
        }
    },
    notNull: function (a, msg, tips) {
        if (a.val() === "")
        {
            validateForm.updateTips(msg, tips);
            a.addClass("ui-state-error");
            return false;
        }
        else
        {
            return true;
        }
    },
    noChanges: function (obj1, obj2) {
        if (JSON.stringify(obj1) === JSON.stringify(obj2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}; // end validateForm functions
// post Full Calendar events to server
function postToServer(event) {
    var eventNew = [];
    var eventObj = new EventObj(event);
    eventNew.push(eventObj);
    var calEvents = JSON.stringify(eventNew);
    calEvents = calEvents.replace(/\"/g, '\''); // replace double quotes with single quote
    // include event object if new event (1234) or status updated, or array of events marked for cancellation
    if (event.eventId === 1234 || event.action === "updateStatus" || event.cancelEvts instanceof Object === true
            || event.action === "delAssociateTimeSlot")
    {
        postEvents(calEvents, event);
//        doAjaxPost(calEvents, '../FullCalPost', "delAssociateTimeSlot", true);
    }
    else
    {
        postEvents(calEvents);
    }
} // end postToServer function

// format phone number
var formatPhone = function (phone) {
    if (phoneRegEx.test(phone))
    {
        phone = phone.replace(/[^0-9]/g, '');
        phone = phone.replace(/(\d{3})(\d{3})(\d{4})/, "($1) $2-$3");
    }
    return phone;
}; // end formatPhone function
function confirmMessage(element1, element2, message1, message2, message3) {
    $(element1).dialog({
        autoOpen: false,
        modal: true,
        width: 'auto',
        height: 'auto',
        maxWidth: 600,
        show: {
            duration: 200
        },
        hide: {
            duration: 200
        },
        buttons: {
            Submit: function () {
                $(element2).unbind("submit").submit();
                $('#confirmSchedule').html("<h4 style='color: #800000'>" + "Please wait. Processing your request..." + "</h4>");
                spinner('confirmSchedule', 'auto', 300);
                $(this).dialog("close");
            },
            Cancel: function () {
                $(this).dialog("close");
                $(message1).html(message3);
                fadeInMessage(message2);
            }
        }
    });
    $("#confirmSchedule tbody").html(""); // clear confirmSchedule contents
} // end confirmMessage function

// element to display spinner
function openLoading() {
    $("#loading").dialog({
        autoOpen: false,
        width: 'auto',
        hegith: 'auto',
        maxWidth: 600,
        minWidth: 400,
        modal: true
    });
} // end loading popup dialog function

// Converts total number of minutes to "h:mm" format
var minutesFormat = function (m) {
    var minutes = m % 60;
    var hours = Math.floor(m / 60);
    var hourFmt = " hour ";
    var minFmt = " minutes";
    minutes = (minutes < 10 ? '0' : '') + minutes;
    //hours = (hours < 10 ? '0' : '') + hours;
    if (hours === 0)
    {
        return minutes + minFmt;
    }
    else
    {
        if (hours > 1)
        {
            hourFmt = " hours ";
        }
        if (minutes === "00")
        {
            return hours + hourFmt;
        }
        return hours + hourFmt + minutes + minFmt;
    }
}; // end minutesFormat function

var timeConverter = {
    hour: function (m) {
//        var hour = Math.floor(m / 60);
        var hour = moment.duration({minutes: m}).hours();
        var opts = [];

        for (var i = 0; i < 24; i++)
        {
            if (hour === i)
            {
                opts[i] = "<option value='" + i + "' selected>" + i + "</option>";
            }
            else
            {
                opts[i] = "<option value='" + i + "'>" + i + "</option>";
            }
        }
        return opts;
    },
    minute: function (m) {
//        var minutes = m % 60;
        var minutes = moment.duration({minutes: m}).minutes();
        var opts = [];

        for (var i = 00; i < 60; i = i + 5)
        {
            if (minutes === i)
            {
                opts[i] = "<option value='" + i + "' selected>" + i + "</option>";
            }
            else
            {
                opts[i] = "<option value='" + i + "'>" + i + "</option>";
            }
        }
        return opts;
    }
};

var stripName = function (t) {
    if (typeof t !== "undefined")
    {
        var i = t.indexOf(":", 0);
        if (i !== -1)
        {
            t = t.substring(0, i);
        }
    }
    return t;
}; // end stripName function
$(function () {
    // create the image rotator
    setInterval("rotateImages()", 3000);
});
function rotateImages() {
    var oCurPhoto = $('#photoShow div.current');
    var oNxtPhoto = oCurPhoto.next();
    if (oNxtPhoto.length === 0)
        oNxtPhoto = $('#photoShow div:first');
    oCurPhoto.removeClass('current').addClass('previous');
    oNxtPhoto.css({opacity: 0.0}).addClass('current').animate({opacity: 1.0}, 1000,
            function () {
                oCurPhoto.removeClass('previous');
            });
} // end rotateImages function

var autocomplete = {
    associateName: function (element) {
        element.autocomplete({
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
            } // end select function
        });
    }
}; // end autocomplete function

var eventsRendering = {
    drawEvents1: function (eventsArr, type) {
        var e = [];
        if (type === "past")
        {
            for (var i in eventsArr)
            {
                e[i] = "<tr>" + "<td>" + jsDate.formatDate2(eventsArr[i].start) + "</td>" + "<td>" + jsTime.formatTime(eventsArr[i].start) + "</td>"
                        + "<td>" + eventsArr[i].associate2.firstName + "</td>" + "<td>" + eventsArr[i].serviceDescription + "</td>" + "</tr>";
            }
            if (eventsArr.length === 0 || eventsArr === null)
            {
                return e[0] = "<td style='text-align:center'>" + "no past appointments on record" + "</td>";
            }
            else
            {
                var tableHeader = "<tr>" + "<th>" + "date" + "</th>" + "<th>" + "time" + "</th>" + "<th>" + "associate" + "</th>" + "<th>" + "service" + "</th>" + "</tr>";
                e.unshift(tableHeader);
            }
        }
        else if (type === "future")
        {
            for (var i in eventsArr)
            {
//                var statusObj = services.getServiceStatusObj(eventsArr[i].statusId);
                e[i] = "<tr>" + "<td>" + jsDate.formatDate2(eventsArr[i].start) + "</td>" + "<td>" + jsTime.formatTime(eventsArr[i].start) + "</td>"
                        + "<td>" + eventsArr[i].associate2.firstName + "</td>" + "<td>" + eventsArr[i].serviceDescription + "</td>" + "<td style='color:" + eventsArr[i].services.serviceStatus.statusColor + "'>" + eventsArr[i].services.serviceStatus.statusName + "</td>" + "</tr>";
            }
            if (eventsArr.length === 0 || eventsArr === null)
            {
                return e[0] = "<td style='text-align:center'>" + "no future appointments scheduled" + "</td>";
            }
            else
            {
                var tableHeader = "<tr>" + "<th>" + "date" + "</th>" + "<th>" + "time" + "</th>" + "<th>" + "associate" + "</th>"
                        + "<th>" + "service" + "</th>" + "<th>" + "status" + "</th>" + "</tr>";
                e.unshift(tableHeader);
            }
        }
        return e;
    }
}; // end eventsRendering functions
