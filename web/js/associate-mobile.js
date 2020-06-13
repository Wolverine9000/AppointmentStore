// global variable
var dateFmts = ["MM-DD-YYYY", "YYYY-MM-DD", "MM/DD/YYYY", "YYYY/MM/DD"];
emailReExHhml = "^[\w.%+\-]+@[\w.\-]+\.[A-Za-z]{2,6}$",
        phoneRexExHtml = "/\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}",
        dateTimeFmt = "MM/DD/YYYY hh:mm a",
        fetchError = "There was an error while fectching calendar events!" + "<br>"
        + "Click Calendar on the sidebar or refresh the page to re-fetch calendar events." + "<br>"
        + "Contact support if problem persists.",
        phoneErrorMsg = "number must be one these formats:<br> 999-999-9999, (999) 999-9999, 9999999999",
        nameErrorMsg = "may consist of a-z, 0-9, underscores, begin with a letter.",
        zipErrorMsg = "Please enter a valid Zip Code. Ex. 12345 or 12345-1234",
        fieldEmptyMsg = "is required";
tips = "",
        emailRegEx = /^[\w.%+\-]+@[\w.\-]+\.[A-Za-z]{2,6}$/i,
        nameRegEx = /^[a-z]([0-9a-z _])+$/i,
        zipCodeRegEx = /^\d{5}(-\d{4})?$/,
        phoneRegEx = /^\d{10}?|(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}/;
var serviceListing = []; // global service listing variable  array
var serviceCategories = []; // global category variable array
var serviceStatusArr = []; // global service status array variable
var associateListArr = []; // global associate listing variable array
passwordRegEx = /^(?=.*\d)(?=.*[~!@#$%^&*()_\-+=|\\{}[\]:;<>?/])(?=.*[A-Z])(?=.*[a-z])\S{8,15}$/;
passwordErrorMsg = "Must include" + "<br>"
        + "&#8226;" + " One symbol. &#35;&#44; &#38;&#44; &#33;&#44; &#36; etc." + "<br>"
        + "&#8226;" + " One uppercase letter." + "<br>"
        + "&#8226;" + " One numeric digit." + "<br>"
        + "&#8226;" + " Min &#56; characters long.",
        pwdMatchError = "<font color='#c00'>" + "Passwords " + "</font>" + "must match. Please re-enter your password.";
var timer,
        clientSet = [],
        isAllSelected = false,
        errors = [];
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
var defaultView;

// spinner element format argument must be, 'spinnerelement'
function spinner(element) {
    var opts = {
        lines: 13, // The number of lines to draw
        length: 15, // The length of each line
        width: 3, // The line thickness
        radius: 12, // The radius of the inner circle
        scale: 1.0, // Scales overall size of the spinner
        rotate: 0, // Rotation offset
        corners: 1, // Roundness (0..1)
        color: '#4297d7', // #rgb or #rrggbb
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
//    setTimeout(function() {
//        spinner.stop(); // Stop the spinner
//    }, timeout);
} // end spinner function

function doAjaxRequest(key, url, async, title, response)
{
    $.ajax({
        url: url,
        beforeSend: function () {
            $("#postDataError").html("");
//            spinner('loading');
        },
        type: 'GET',
        dataType: "json",
        async: async,
        data: {key: key, title: title},
        error: function (xhr, status, error) {
            $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            alert("ERROR!  There was an requesting data. " + xhr.status + " - " + error);
        },
        success: function (data) {
            response(data);
        }
    });
} // end doAjaxRequest function

function doAjaxRequestNoElements(key, url, async, title, response)
{
    $.ajax({
        url: url,
        beforeSend: function () {
            $("#postDataError").html("");
        },
        type: 'GET',
        dataType: "json",
        async: async,
        data: {key: key, title: title},
        error: function (xhr, status, error) {
            $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            alert("ERROR!  There was an requesting data. " + xhr.status + " - " + error);
        },
        success: function (data) {
            response(data);
        }
    });
}
function doAjaxEventsRequest(key, url, userID, numOfEvts, async, title, response)
{
    $.ajax({
        url: url,
        beforeSend: function () {
            $("#postDataError").html("");
//            spinner('loading');
        },
        type: 'GET',
        dataType: "json",
        async: async,
        data: {key: key, title: title, userID: userID, numOfEvts: numOfEvts},
        error: function (xhr, status, error) {
            $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            alert("ERROR!  There was an requesting data. " + xhr.status + " - " + error);
        },
        success: function (data) {
            response(data);
        },
        complete: function (xhr, status)
        {
        }
    });
} // end doAjaxEventsRequest function

function doAjaxPost(data, url, id)
{
    var associatePwd = $("#security #postResults");
    var associateInfoElm = $("#profile #profile-fieldset-1 #postResults");
    var associateAlerts = $("#profile #alert-prefs-fieldset #postResults");
    var assocSecurity = $("#security #security-questions-postResults");
    var assocDefaultView = $("#calendar-settings #postResults");
    var assocDefaultTimes = $("#calendar-settings #defaulttimes-postResults");
    $.ajax({
        url: url,
        type: 'post',
        data: {data: data, title: id},
//        statusCode: {
//            500: function () {
//                alert("page not found");
//            }
//        },
        beforeSend: function () {
            $("#postDataError").html("");
//            spinner('loading');
        },
        error: function (xhr, status, error) {
            //$("#loading").hide(); // hide the spinner
            switch (id) {
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
            }
            alert("ERROR!  There was an posting data. " + xhr.status + " - " + error);
            if (xhr.status === 400)
            {
                $("#postDataError").html("Error Adding Client: " + xhr.status + " - " + error);
            }
            else {
                $("#postDataError").html("Error Posting Data: " + xhr.status + " - " + error);
            }
        },
        success: function () {
            // $("#loading").hide(); // hide the spinner
            switch (id) {
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
            }

            if (id === 'deactivateClients' || id === 'clients' || id === 'updateClient' || id === 'add')
            {
                listClients("ci", "../FullCalendar", false, "clientInfo");
            }
        },
        complete: function (xhr, status)
        {
            // $("#loading").hide(); // hide the spinner
        }
    });
} // end doAjaxPost function

var statesList = function () {
    var states = [
        "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "LA", "ME", "MD", "MA",
        "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
        "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    ];
    return states;
};
var serviceList = {
    getServiceList: function ()
    {
        var s = [];
        doAjaxRequest("svc", "../FullCalendar", false, "services", function (servicesArr) {
            $.each(servicesArr, function (svc_index, service) {
                s.push(service);
            });
        });
        return s;
    },
    getServiceCategories: function ()
    {
        var c = [];
        doAjaxRequest("cat", "../FullCalendar", false, "category", function (categoriesArr) {
            $.each(categoriesArr, function (cat_index, category) {
                c.push(category);
            });
        });
        return c;
    },
    getServiceStatusList: function ()
    {
        var c = [];
        doAjaxRequestNoElements("svcStatus", "../FullCalendar", false, "serviceStatusList", function (statusArr) {
            $.each(statusArr, function (status_index, statusObj) {
                c.push(statusObj);
            });
        });
        return c;
    },
    getCalendarEvents: function (userID, numOfEvts)
    {
        var evts = [];
        doAjaxEventsRequest("calEvts", "../FullCalendar", userID, numOfEvts, false, "client", function (calEventsArr) {
            evts = calEventsArr;
        });
        return evts;
    },
    getFutureEvents: function (id, futureEvts)
    {
        var evts = [];
        doAjaxEventsRequest("futureCalEvts", "../FullCalendar", id, futureEvts, false, "client", function (calEventsArr) {
            evts = calEventsArr;
        });
        return evts;
    }
}; // end serviceList functions

var services = {
    // services optoion listing
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
                else {
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
    getServiceName: function (o)
    {
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
        else if (typeof o === "number") {
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
                else {
                    assocList[i] = "<option value='" + JSON.stringify(a[i]) + "'>" + a[i].firstName + " " + a[i].lastName
                            + "</option>";
                }
            }
            else {
                assocList[i] = "<option value='" + JSON.stringify(a[i]) + "'>" + a[i].firstName + " " + a[i].lastName
                        + "</option>";
            }
        }
        return assocList;
    },
    delClientList: function (array)
    {
        var table = "<thead><tr><th>first</th><th>last</th><th>email</th></tr></thead>";
        table = table + "<tbody>";
        for (var i in array)
        {
            table += "<tr>" + "<td>" + array[i].client.firstName + "</td>" + "<td>" + array[i].client.lastName + "</td>" + "<td>" + array[i].client.email + "</td>" + "</tr>";
        }
        table = table + "</body>";
        return table;
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
        doAjaxRequest("assoProfile", "../FullCalendar", false, "currentAssoc", function (result) {
            a = new Associate(result);
        });
        return a;
    },
    renderAssociate: function (assoObj) {

        var associateTable;

        associateTable =
                "<tr>" + "<td title='account ID number'>" + "id&#58;" + "</td>" + "<td>" + assoObj.id + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate email address'>" + "email&#58;" + "</td>" + "<td>" + assoObj.email + "</td>" + "</tr>"
                + "<tr>" + "<td title='mobile phone'>" + "mobile&#58;" + "</td>" + "<td>" + assoObj.mobilePhone + "</td>" + "</tr>"
                + "<tr>" + "<td title='home phone number'>" + "home&#58;" + "</td>" + "<td>" + assoObj.homePhone + "</td>" + "</tr>"
                + "<tr>" + "<td title='work phone number'>" + "work&#58;" + "</td>" + "<td>" + assoObj.workPhone + "</td>" + "</tr>"
                + "<tr>" + "<td title='other phone number'>" + "other&#58;" + "</td>" + "<td>" + assoObj.otherPhone + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate address'>" + "address&#58;" + "</td>" + "<td>" + assoObj.address + "</td>" + "</tr>"
                + "<tr>" + "<td title='city'>" + "city&#58;" + "</td>" + "<td>" + assoObj.city + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate state'>" + "state&#58;" + "</td>" + "<td>" + assoObj.state + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate zip code'>" + "zip&#58;" + "</td>" + "<td>" + assoObj.zip + "</td>" + "</tr>";
        return associateTable;
    },
    alertPrefs: function (o, e)
    {
        if (o.emailAdAlerts === true) {
            $(e + " #email-ad-alerts").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #email-ad-alerts").prop('checked', false).checkboxradio('refresh');
        }
        if (o.smsAdAlerts === true) {
            $(e + " #sms-ad-alerts").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #sms-ad-alerts").prop('checked', false).checkboxradio('refresh');
        }
        if (o.emailApptAlerts === true) {
            $(e + " #email-appt-alerts").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #email-appt-alerts").prop('checked', false).checkboxradio('refresh');
        }
        if (o.smsApptAlerts === true) {
            $(e + " #sms-appt-alerts").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #sms-appt-alerts").prop('checked', false).checkboxradio('refresh');
        }
    }, associateInput: function (assoObj) {
        var asssoInputTable;

        var stateList = options.getState(assoObj);

        asssoInputTable =
                "<tr>" + "<td>" + "<label for='account ID number' title='id'>" + "id&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='text' name='id' value='" + assoObj.id + "'>" + "</td>"
                + "</tr>"
                + "<tr>" + "<td>" + "<label for='email' title='associate email address'>" + "email&#58" + "</label>" + "</td>"
                + "<td>" + "<input type='email' name='email' value='" + assoObj.email + "' >" + "</td>"
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
        ;
        return asssoInputTable;
    },
    associateAccount: function (assoObj) {
        var assocAcctTable;
        assocAcctTable =
                "<tr>" + "<td title='member number'>" + "member number&#58;" + "</td>" + "<td>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='account id'>" + "account id&#58;" + "</td>" + "<td>" + assoObj.id + "</td>" + "</tr>"
                + "<tr>" + "<td title='first name on account'>" + "first name&#58;" + "</td>" + "<td>" + assoObj.firstName + "</td>" + "</tr>"
                + "<tr>" + "<td title='last name on account '>" + "last name&#58;" + "</td>" + "<td>" + assoObj.lastName + "</td>" + "</tr>"
                + "<tr>" + "<td title='account email'>" + "email&#58;" + "</td>" + "<td>" + assoObj.email + "</td>" + "</tr>"
                + "<tr>" + "<td title='total sms messages sent this month'>" + "sms mgs sent&#58;" + "</td>" + "<td>" + assoObj.smsSent + "</td>" + "</tr>";
        return assocAcctTable;
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
            else {
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
            else {
                timesOptions += "<option value='" + timesArray[i] + "'>" + timesArray[i] + "</option>";
            }
        }
        return timesOptions;
    }
}; // end associateClass functions

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
}; // end getAssociates function

// new Event Object function
function EventObj(event) {
    if (typeof event === "undefined") {
    }
    else {
        if (!event.title === undefined)
        {
            this.title = stripName(event.title); // remove client name from title
        }
    }
    this.start = jsDate.convToJsDate(event.start);
    if (event.allDay)
    {
        this.end = new Date(this.start);
    }
    else
    {
        this.end = jsDate.convToJsDate(event.end);
    }
    this.serviceTime = event.serviceTime;
    this.allDay = event.allDay;
    this.backgroundColor = event.backgroundColor;
    this.color = event.color;
    this.editable = event.editable;
    this.durationEditable = event.durationEditable;
    this.textColor = event.textColor;
    this.eventId = event.eventId;
    this.serviceId = event.serviceId;
    this.firstName = event.firstName;
    this.lastName = event.lastName;
    this.mobilePhone = event.mobilePhone;
    this.emailAddress = event.emailAddress;
    this.associateName = event.associateName;
    this.customerId = event.customerId;
    this.associateId = event.associateId;
    this.notes = event.notes;
    this.statusId = event.statusId;
    this.notifyClient = event.notifyClient;
    this.restoreTime = event.restoreTime;
    this.newClient = event.newClient;
    this.action = event.action;
    this.cancelEvts = event.cancelEvts;
    this.client = event.client;
    this.associate2 = event.associate2;
    this.memberLevels = event.memberLevels;
} // end EventObj function
function client(firstName, lastName, email, homePhone, workPhone, company,
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
} // end client object 
function userObj(firstName, lastName, email, id, associate2,
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
} // end userObj object

function Associate(i) {
    this.id = i.id;
    this.firstName = i.firstName;
    this.lastName = i.lastName;
    this.email = i.email;
    this.homePhone = formatPhone(i.homePhone);
    this.workPhone = formatPhone(i.workPhone);
    this.mobilePhone = formatPhone(i.mobilePhone);
    this.otherPhone = formatPhone(i.otherPhone);
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
    this.defaultCalendarView = i.defaultCalendarView;
    this.morningTimeIn = i.morningTimeIn;
    this.morningTimeOut = i.morningTimeOut;
    this.afternoonTimeIn = i.afternoonTimeIn;
    this.afternoonTimeOut = i.afternoonTimeOut;
} // end Associate object

// update calendar using update button
$(function () {
    $("#updateCalendar").click(function () {
        var eventsToPost = []; // array to post events to server
        var eventsFromCalendar = $('#calendar').fullCalendar('clientEvents');
        $.each(eventsFromCalendar, function (index, value) {

            var eventObj = new EventObj(value);
            eventsToPost.push(eventObj);
        });
        var calEvents = JSON.stringify(eventsToPost);
        calEvents = calEvents.replace(/\"/g, '\''); // replace double quotes with single quote
        postEvents(calEvents);
    });
}); // end update Calendar function

// post Full Calendar events to server
function postToServer(event)
{
    var eventNew = [];
    var eventObj = new EventObj(event);
    eventNew.push(eventObj);
    var calEvents = convert.stringifyObj(event); // stringify event object for transmission
    // include event object if new event (1234) or status updated, or array of events marked for cancellation
    if (event.eventId === 1234 || event.action === "updateStatus" || event.cancelEvts instanceof Object === true
            || event.action === "delAssoicateTimeSlot")
    {
        postEvents(calEvents, event);
    }
    else
    {
        postEvents(calEvents);
    }

} // end postToServer function

// post FullCalendar Events to server via ajax
function postEvents(evtsToPost, event) {
    $("#postDataSuccess").html(""); // clear postDataSuccess
    $("#postDataError").html(""); // clear postDataError
    $("#messages").hide();
    $.ajax({
        type: "post",
        url: "../FullCalPost",
        beforeSend: function () {
            //$("#postDataError").html("");

            spinner('calendar');
        },
        data: {calEvents: evtsToPost},
        //timeout: 10000,
        error: function (xhr, status, error) {
            $('#calendar').data('spinner').stop(); // Stop the spinner
            $("#messages").show();
            // append error message to messages element
            $("#postDataError").append("Error Posting Data: " + xhr.status + " - " + error);
//            fadeInOutMessage("#messages");
        },
        success: function () {
            $('#calendar').data('spinner').stop(); // Stop the spinner
            refreshEvents();
            if (typeof event !== "undefined")
            {
                if (event.eventId === 1234)
                {
                    $('#calendar').fullCalendar('removeEvents', event.id);
                }
                // append success message to messages element
                $("#postDataSuccess").append("Calendar Update Successful!");
                //fadeInOutMessage("#messages");
            }
        }
    });
} // end postEvents function


function stripName(t) {
    var i = t.indexOf(":", 0);
    if (i !== -1)
    {
        t = t.substring(0, i);
    }
    return t;
}
/* Converts total in minutes to "h:mm" format */
function minutesFormat(m) {
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
} // end minutesFormat function

var updateStatus = function (event, c, s) {

    s = $("#updateStatus").val(); // get event status ID
    if (c !== parseInt(s))
    {
        event.statusId = s; // update event status ID
        event.action = "updateStatus"; // change event action so server can process update
        postToServer(event);
        $.mobile.changePage("#mainpage", {transition: "slideup"});
    }
}; // end updateStatus function

// goto selected fullcalendar date
var gotoDate = function (d) {
    var o = jsDate.fmtStr(d); // convert date string to date object
    $("#calendar").fullCalendar('gotoDate', o);
    $("#calendar").fullCalendar('changeView', 'agendaDay'); //change view to agendaDay
};

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
// format phone number
var formatPhone = function (phone) {
    phone = phone.replace(/[^0-9]/g, '');
    phone = phone.replace(/(\d{3})(\d{3})(\d{4})/, "($1) $2-$3");
    return phone;
};


var removeEvent = function (event)
{
    $("#yes").off();
    if (event.userType === "associate")
    {
        event.action = "delAssoicateTimeSlot";
    }
    else {
        event.restoreTime = true;
        event.notifyClient = true;
        $("#calendar").fullCalendar('removeEvents', event._id);
        event.action = "delete";
    }
    postToServer(event);
};
var reFresh = function reFreshCal() {
    $('#calendar').fullCalendar("refetchEvents");
};
function refreshEvents() {
    setTimeout(function () {
        $('#calendar').fullCalendar("refetchEvents");
    }, 500);
}
// this function checks for events with overlapping times and returns them in an array 
function eventConflictCk(event)
{
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
                    && value.serviceStatus.statusName === "Confirmed")
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

// global clear time(s) variable
var clearTime = "-------"; // clear time variable

$(document).ready(function () {
    serviceListing = serviceList.getServiceList();
    serviceCategories = serviceList.getServiceCategories();
    associateListArr = getAssociates();
    serviceStatusArr = serviceList.getServiceStatusList(); // get service status listing array
    listClients("ci", "../FullCalendar", false, "clientInfo", 'firstName'); // liist clients in client page
    var qsErrorPage = $("#qs-errorpage");
    var qsErrors = [];
    var countChecked = function () {
        var n = $(".qs-day:checked:checked").length;
        return n;
    };
// quick schedule validation and submission  
    $("#qs-submit").on("click",
            function () {
                $("#qs-errorpage ul").html("");
                qsErrors = [];
                var confirmSchedule = $("#scheduler-confirmpage");
                var isValid = true;
                var date = $("#qs-date").val();
                var qsStart = $("#qs-starttime").val();
                var qsEnd = $("#qs-endtime").val();
                var qsFrequency = $("#qsFrequency").val();
                var qsLength = $("#qsLength").val();
                var checkboxArray = [];
                var isTimeValid = jsTime.validateTime(qsStart, qsEnd);
                var isDateValid = jsDate.validateDate(date);
                var isLessOneYr = jsDate.validateOneYear(date);
                $(".qs-day:checked").each(function () {
                    checkboxArray.push($(this).val()); //push each checkbox value into the array
                });
                var checkedBoxes = countChecked(); // get the number of checkboxes checked

                if (checkedBoxes === 0 && qsLength !== "1 day") {
                    qsErrors.push("Select at least one of the <strong>Day</strong> checkboxes.");
                    isValid = false;
                }

                if (date === "" || isDateValid === false) {
                    qsErrors.push("<strong>Starting Date</strong>&nbsp;Must be today's date or greater");
                    isValid = false;
                }

                if (isLessOneYr === false) {
                    qsErrors.push("<strong>Starting Date</strong>&nbsp;Starting Date must be less than one year from today's date.");
                    isValid = false;
                }

                if (qsStart === clearTime && qsEnd === clearTime || isTimeValid === false) {
                    qsErrors.push("<strong>Time In</strong>&nbsp;&AMP;&nbsp;<strong>Time Out</strong>.&nbsp;Make sure Time Out is greater than Time In");
                    isValid = false;
                }

                if (qsStart === clearTime && qsEnd !== clearTime) {
                    qsErrors.push("<strong>Time In</strong>&nbsp;Please select a Time In.");
                    isValid = false;
                }

                if (qsStart !== clearTime && qsEnd === clearTime) {
                    qsErrors.push("<strong>Time Out</strong>,&nbsp;Please select a Time Out.");
                    isValid = false;
                }

                if (isValid === false) {
                    qsErrorPage.popup('open', {transition: "pop"});
                    errorMsg(qsErrors);
                }

                else {
                    confirmSchedule.popup('open', {transition: "pop"});
                    openConfirm();
                }
                function openConfirm() {
                    $("#qs-submit").unbind("submit").submit();
                    $('#scheduler-confirmpage p').html("<input type='hidden' name='qsLength' id='qsLength' value='" + qsLength + "'>"
                            + "<input type='hidden' name='qsDate' id='qsDate' value='" + jsDate.fmtStr(date) + "'>"
                            + "<input type='hidden' name='qsStart' id='qsStart' value='" + qsStart + "'>"
                            + "<input type='hidden' name='qsEnd' id='qsEnd' value='" + qsEnd + "'>"
                            + "<input type='hidden' name='qsFrequency' id='qsFrequency' value='" + qsFrequency + "'>");
                    for (var i = 0; i < checkboxArray.length; i++) {
                        $('#scheduler-confirmpage p').append("<input type='hidden' name='day' id='day' value='" + checkboxArray[i] + "'>");
                    }
                    $('#scheduler-confirmpage ul').html("<li>" + "Starting: " + "<strong>" + jsDate.formatDate(date) + "</strong>" + "</li>"
                            + "<li>" + "For: " + "<strong>" + qsLength + "</strong>" + "</li>"
                            + "<li>" + "Time in: " + "<strong>" + qsStart + "</strong>" + "</li>"
                            + "<li>" + "Time out: " + "<strong>" + qsEnd + "</strong>" + "</li>"
                            + "<li>" + "Every: " + "<strong>" + qsFrequency + " minutes" + "</strong>" + "</li>");
                    if (checkboxArray.length > 1) {
                        $('#scheduler-confirmpage ul').append("<li>" + "Days: " + "<strong>" + checkboxArray.join(", ") + "</strong>" + "</li>");
                    }
                } // end openConfirm function
            } // end function
    ); // end quick schedule submit

    //
    // validate clear schedule
    $("#clear-submit").on("click", function (event) {
        $("#clearScheduleMessages").html("");
        $("#validationErrorClr").html("");
        $("#qs-errorpage ul").html("");
        $("#submitClear ul").html("");
        $("#submitClear p").html("");
        qsErrors = [];
        var clearChoices = [];
        var header = "";
        var clearMsg = [];
        var clearPage = $("#clear-scheduler");
        var choice = $(".clearSchedule:checked").val();
        var clearOne = $("#clearone-day").val();
        var clearRangeTo = $("#to").val();
        var clearRangeFrom = $("#from").val();
        var isClrValid = true;
        if (typeof choice === "undefined")
        {
            qsErrors.push("Please select a <strong>Clear Schedule Option</strong>.");
            isClrValid = false;
        }
        else {
            switch (choice) {
                case "clearDate":
                    if (clearOne === "")
                    {
                        qsErrors.push("Please select a <strong>date to clear</strong>.");
                        isClrValid = false;
                    }
                    else if (jsDate.validateDate(clearOne) === false || jsDate.valSelectedDate(clearOne) === false)
                    {
                        qsErrors.push("Please select a valid&nbsp;<strong>date to clear</strong>.&nbsp;example:&nbsp;<strong>" + jsDate.todaysDate()
                                + "</strong>&nbsp;and&nbsp;is today's date or greater.");
                        isClrValid = false;
                    }
                    else if (clearOne !== "")
                    {
                        var clearDate = jsDate.fmtStr(clearOne);
                        clearChoices.push("<input type='hidden' name='clear' id='clearDate' value='clearDate'>");
                        clearChoices.push("<input type='hidden' name='eDate' id='eDate' value=" + clearDate + ">");
                        clearMsg.push("on&nbsp;<strong>" + jsDate.formatDate(clearOne) + "</strong>");
                        header = "Clear All appointments";
                        clearPage.popup();
                        clearPage.popup("open");
                        clearSchedule(clearMsg, clearChoices, header);
                    }
                    break;
                case "clearRange":
                    var clearRangeMessage = "&nbsp;date</strong>.&nbsp;ex.&nbsp;<strong>" + jsDate.todaysDate()
                            + "</strong>&nbsp;and is greater than the selected from date.";
                    if (clearRangeTo === "" && clearRangeFrom !== "")
                    {
                        qsErrors.push("Please enter a valid <strong>to date</strong>.");
                        isClrValid = false;
                    }

                    else if (clearRangeTo !== "" && clearRangeFrom === "")
                    {
                        qsErrors.push("Please enter a valid <strong>from date</strong>.");
                        isClrValid = false;
                    }

                    else if (clearRangeFrom === "" && clearRangeTo === "")
                    {
                        qsErrors.push("Please select a valid <strong>from date</strong>&nbsp;and&nbsp;<strong>to&nbsp;dates</strong>.");
                        isClrValid = false;
                    }

                    else if (jsDate.validateDate(clearRangeTo) === false)
                    {
                        qsErrors.push("Please select a valid <strong>to" + clearRangeMessage);
                        isClrValid = false;
                    }

                    else if (jsDate.validateDate(clearRangeFrom) === false)
                    {
                        qsErrors.push("Please select a valid <strong>from" + clearRangeMessage);
                        isClrValid = false;
                    }
                    else if (jsDate.validateToFromDates(clearRangeTo, clearRangeFrom) === false)
                    {
                        qsErrors.push("Please select a valid <strong>from" + clearRangeMessage);
                        isClrValid = false;
                    }
                    else if (clearRangeTo !== "" && clearRangeFrom !== "")
                    {
                        event.preventDefault();
                        header = "Clear All appointments";
                        clearChoices.push("<input type='hidden' name='clear' id='clearRange' value='clearRange'>");
                        clearChoices.push("<input type='hidden' name='from' id='from' value=" + jsDate.fmtStr(clearRangeFrom) + ">");
                        clearChoices.push("<input type='hidden' name='to' id='to' value=" + jsDate.fmtStr(clearRangeTo) + ">");
                        clearMsg.push("from:&nbsp;<strong>" + jsDate.formatDate(clearRangeFrom) + "</strong>");
                        clearMsg.push("to:&nbsp;<strong>" + jsDate.formatDate(clearRangeTo) + "</strong>");
                        clearPage.popup();
                        clearPage.popup("open");
                        clearSchedule(clearMsg, clearChoices, header);
                    }
                    break;
                case "clearAll":
                    event.preventDefault();
                    clearChoices.push("<input type='hidden' name='clear' id='clearAll' value='clearAll'>");
                    clearMsg.push("<span style='color: #c00;'>Are you sure you want to Clear your Enitre Schedule?</span>");
                    header = "Clear Enitre Schedule";
                    clearPage.popup();
                    clearPage.popup("open");
                    clearSchedule(clearMsg, clearChoices, header);
                    break;
            }
        }
        if (isClrValid === false) {
            qsErrorPage.popup('open', {transition: "pop"});
            errorMsg(qsErrors);
        }
        function clearSchedule(m, c, h) {
            $("#clear-submit").unbind("submit").submit();
            $("#clear-scheduler h4").html(h);
            for (var i = 0; i < m.length; i++) // list clear items
            {
                $("#submitClear ul").append("<li>" + m[i] + "</li>");
            }
            for (var i = 0; i < c.length; i++)
            {
                $("#submitClear p").append(c[i]);
            }
        }
    }); // end clear schedule sumbit    
}); // end quick schedule document ready

// display quick schedule entry errors
var errorMsg = function (e) {
    for (var i = e.length - 1; i >= 0; i--) {
        $("#qs-errorpage ul").append("<li>" + e[i] + "</li>");
    }
}; // end errorMsg function
var convert = {
    stringifyEvtObj: function (event)
    {
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
    stringifyObj: function (o)
    {
        if (o instanceof Object)
        {
            if (o instanceof Array)
            {
                var a = JSON.stringify(o);
                a = a.replace(/\"/g, '\''); // replace double quotes with single quote
                return a;
            }
            else {
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
    stringify: function (e)
    {
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
    capitalize: function (text)
    {
        return text.toUpperCase();
    },
    errorName: function (e) {
        var id = $(e)[0].id;
        var name;
        switch (id)
        {
            case "homePhone":
                name = "Home Phone ";
                break;
            case "workPhone":
                name = "Work Phone ";
                break;
            case "company":
                name = "Company ";
                break;
            case "address":
                name = "Address ";
                break;
            case "city":
                name = "City ";
                break;
            default:
                name = id;
                break;
        }
        return name;
    }
}; // end convert class functions

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
        today.seconds(0).hour(0).minute(0);
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
        oneYear.seconds(0).hour(0).minute(0);
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
        selDate.add(1, 's');
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
        var n = moment(timeStr, "h:mm a");
        var hour = n.hour();
        var minutes = n.minutes();
        var month = momObj.month(),
                date = momObj.date(),
                year = momObj.year(),
                calendar = $('#calendar').fullCalendar('getCalendar'),
                c = calendar.moment();
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
        if (d === null) {
            momentObj = moment();
            momentObj.hours(0);
            momentObj.minutes(0);
            momentObj.seconds(0);
        }
        else {
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
        var n = parseInt(t);
        var m = momObj.add(n, 'm');
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

// format phone number
var formatPhone = function (phone) {
    if (phoneRegEx.test(phone))
    {
        phone = phone.replace(/[^0-9]/g, '');
        phone = phone.replace(/(\d{3})(\d{3})(\d{4})/, "($1) $2-$3");
    }
    return phone;
};

function fadeInOutMessage(element) {
    $(element).fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(2000);
}
function fadeInMessage(element) {
    $(element).fadeIn(100).fadeOut(100).fadeIn(100);
}
// this function will show associtate events
var showEvts = function (key, c) {
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
            error: function (xhr, status, error) {
                $("#postDataError").append("Error Posting Data: " + xhr.status + " - " + error);
                //$("#postDataError").html(fetchError);
                //alert(fetchError);
            }
        });
    }

    $('#calendar').fullCalendar('addEventSource', {
        url: '../FullCalendar',
        type: 'GET',
        dataType: "json",
        data: {title: c, key: key
        },
        error: function (xhr, status, error) {
            $("#postDataError").append("Error Posting Data: " + xhr.status + " - " + error);
            //$("#postDataError").html(fetchError);
            //alert(fetchError);
        }
    });
}; // end showEvts function


/*
  * Google Maps documentation: http://code.google.com/apis/maps/documentation/javascript/basics.html
  * Geolocation documentation: http://dev.w3.org/geo/api/spec-source.html
  */
$(document).on("pagecreate", "#map-page", function () {
    var marker1,
            marker2,
            u,
            id = getIndex.getId("#showCal"),
            address,
            isAddrValid,
            latLng,
            addrLat,
            addrLng;
    doAjaxRequest("aa", "../FullCalendar", false, id, function (result) {
        u = new UserObj(result); // ajax request for associate info
        address = u.address + ", " + u.city + ", " + u.state;
    }, 'map-page');
    var geocoder = new google.maps.Geocoder();

    geocoder.geocode({address: address}, function (results, geoStatus) {
        if (geoStatus === "OK")
        {
            addrLat = results[0].geometry.location.lat();
            addrLng = results[0].geometry.location.lng();
             latLng = new google.maps.LatLng(addrLat, addrLng);
            isAddrValid = true;
        }
        else {
            alert("Not able to able find Geo address " + address + " for " + u.firstName
                    + " -- will use default Geo location -- error code: " + geoStatus);
             latLng = new google.maps.LatLng(33.5205556, -86.8025);
            isAddrValid = false;
        }
        var myOptions = {
                        zoom: 11,
                        center: latLng,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                };
        var map = new google.maps.Map($("#map-canvas").get(0), myOptions);
        marker1 = new google.maps.Marker({
            position: latLng,
            map: map
        });
        var overlay = new google.maps.OverlayView();
        overlay.draw = function () {
            var point = overlay.getProjection().fromLatLngToContainerPixel(marker1.getPosition());
            if (isAddrValid === false) {
                $("#message").html(
                        "Birmingham, AL");
            }
            else {
                $("#message").html(
                        "This is: " + u.firstName + " " + u.lastName + "  " + address +
                        "<br><a href=http://maps.google.com/maps?daddr=" +
                        address + ">Get directions to here</a>");
            }
            $("#message").show().css({
                top: point.y + 10,
                left: point.x
            });
        };
        overlay.setMap(map);
    });
});

$(document).ready(function () {
    $("#info-back").off();
    $("#info-btn").on("click", function () {
        var infoPage = $("#info-page"),
                key = getIndex.getKey("#showCal"),
                associateId = getIndex.getId("#showCal"),
                infoPageTable = $('#info-page tbody'),
                h4Ttitle = $("#info-page h4"),
                allAssociates = true,
                uObj,
                today = 0,
                conf = 0,
                comp = 0,
                todo = 0,
                pend = 0,
                cancel = 0,
                noshow = 0,
                now = moment();
        if (key === "all")
        {
            allAssociates = true;
            name = "All Associates";
        }
        else
        {
            allAssociates = false;
            doAjaxRequest("aa", "../FullCalendar", false, associateId, function (result) {
                uObj = new UserObj(result);
            });
        }
        var clientEvents = $('#calendar').fullCalendar('clientEvents');
        $.each(clientEvents, function (index, value) {

            if (value.start.isSame(now, 'day'))
            {
                today++;
                if (value.serviceStatus.statusName === "Confirmed")
                {
                    conf++;
                }
                if (value.serviceStatus.statusName === "Completed")
                {
                    comp++;
                }
                if (value.start.isAfter(now) && value.serviceStatus.statusName === "Confirmed")
                {
                    todo++;
                }
                if (value.start.isAfter(now) && value.serviceStatus.statusName === "Pending")
                {
                    pend++;
                }
            }
        });
        if (allAssociates === true)
        {
            h4Ttitle.html("All Associates");
        }
        if (allAssociates === false)
        {
            h4Ttitle.html(uObj.firstName);
        }

        infoPageTable.html(
                "<tr>" + " <td>" + today + "</td>" + "<td>" + " appointments today" + "</td>" + "</tr>"
                + "<tr>" + "<td>" + conf + "</td>" + "<td>" + "  confirmed " + "</td>" + "</tr>"
                + "<tr>" + "<td>" + comp + "</td>" + "<td>" + " completed " + "</td>" + "</tr>"
                + "<tr>" + "<td>" + todo + "</td>" + "<td>" + "  to do " + "</td>" + "</tr>"
                + "<tr>" + "<td>" + pend + "</td>" + "<td>" + " pending" + "</td>" + "</tr>");
        infoPage.popup('open', {transition: "pop"});
    });
});

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
                        + "<td>" + eventsArr[i].associateName + "</td>" + "<td>" + eventsArr[i].serviceDescription + "</td>" + "</tr>";
            }
            if (eventsArr.length === 0 || eventsArr === null)
            {
                return e[0] = "<td style='text-align:center'>" + "no past appointments on record" + "</td>";
            }
            else {
                var tableHeader = "<tr>" + "<th>" + "date" + "</th>" + "<th>" + "time" + "</th>" + "<th>" + "associate" + "</th>" + "<th>" + "service" + "</th>" + "</tr>";
                e.unshift(tableHeader);
            }
        }
        else if (type === "future")
        {
            for (var i in eventsArr)
            {
                var statusObj = services.getServiceStatusObj(eventsArr[i].statusId);
                e[i] = "<tr>" + "<td>" + jsDate.formatDate2(eventsArr[i].start) + "</td>" + "<td>" + jsTime.formatTime(eventsArr[i].start) + "</td>"
                        + "<td>" + eventsArr[i].associateName + "</td>" + "<td>" + eventsArr[i].serviceDescription + "</td>" + "<td style='color:" + statusObj.statusColor + "'>" + statusObj.statusName + "</td>" + "</tr>";
            }
            if (eventsArr.length === 0 || eventsArr === null)
            {
                return e[0] = "<td style='text-align:center'>" + "no future appointments scheduled" + "</td>";
            }
            else {
                var tableHeader = "<tr>" + "<th>" + "date" + "</th>" + "<th>" + "time" + "</th>" + "<th>" + "associate" + "</th>"
                        + "<th>" + "service" + "</th>" + "<th>" + "status" + "</th>" + "</tr>";
                e.unshift(tableHeader);
            }
        }
        return e;
    }
}; // end eventsRendering functions

//================ mobile clients scripts ==========================
var clientOptions = {
    editClient: function (o) {
        $("#send").off(); // turn off send button element
        $.mobile.changePage("#edit-client-page", {transition: "slideup"});
        var confirmUser = $(".confirmUser"),
                confirmpage = $("#client-confirm-page"), // original #confirmpage
                errorPage = $("#error-client-info"),
                firstName = $("#firstNm"),
                lastName = $("#edit-client-page #edit-user-form #lastNm"),
                email = $("#email"),
                emailChg = o.client.email,
                confirmEmail = $("#confirmEmail"),
                mobilePhone = $("#mobilePhone"),
                homePhone = $("#homePhone"),
                workPhone = $("#workPhone"),
                company = $("#company"),
                address = $("#address"),
                associate = $("#associate"),
                city = $("#city"),
                state = $("#state"),
                zip = $("#zip"),
                memberLevel = $("#edit-client-page #edit-user-form #memberLevel"),
                normalService = $("#edit-client-page #edit-user-form #normal-service"),
                associateNames = $("#edit-client-page #edit-user-form #associate"),
                emailAdAlerts = $("#edit-client-page #edit-user-form .alert-prefs-2 #email-ad-alerts"),
                smsAdAlerts = $("#edit-client-page #edit-user-form .alert-prefs-2 #sms-ad-alerts"),
                emailApptAlerts = $("#edit-client-page #edit-user-form .alert-prefs-2 #email-appt-alerts"),
                smsApptAlerts = $("#edit-client-page #edit-user-form .alert-prefs-2 #sms-appt-alerts"),
                allFields = $([]).add(firstName).add(lastName).add(email).add(confirmEmail).add(mobilePhone).add(homePhone).add(associateNames)
                .add(workPhone).add(company).add(address).add(city).add(state).add(zip).add(memberLevel).add(normalService);
        var opts = [];
        var svcs = [];
        var aList = [];
        var stateList = [];
        opts = options.getMemberLevel(o);
        svcs = services.getServices(o);
        aList = associateClass.getAssociateList(o);
        stateList = options.getState(o);
        memberLevel.html(opts);
        normalService.html(svcs);
        associateNames.html(aList);
        state.html(stateList);
        // must refresh each select menu after a select change
        memberLevel.selectmenu("refresh");
        normalService.selectmenu("refresh");
        associateNames.selectmenu("refresh");
        state.selectmenu("refresh");
        var notReqFields = [homePhone, workPhone, company, address, city, zip];
        firstName.val(o.client.firstName);
        lastName.val(o.client.lastName);
        email.val(o.client.email);
        confirmEmail.val(o.client.email);
        mobilePhone.val(formatPhone(o.client.mobilePhone));
        homePhone.val(formatPhone(o.client.homePhone));
        workPhone.val(formatPhone(o.client.workPhone));
        company.val(o.client.company);
        address.val(o.client.address);
        city.val(o.client.city);
        state.val(o.client.state);
        zip.val(o.client.zip);

        renderData.alertPrefs(o, "#edit-user-form .alert-prefs-2");

        $("#send").on("click", function () {
            $("#client-confirm-btn").off("click");
            $("#client-cancel-btn").off("click");

            var assoObj = convert.parseJsonObj(associate);
            var userInfo;
            o.associate2 = assoObj;
            o.associateName = assoObj.firstName;
            o.associateId = assoObj.id;

            errors = []; // clear errors array
            $("#error-client-info ul").html("");
            validateForm.checkLength(firstName, "First Name", 3, 45);
            validateForm.checkLength(lastName, "Last Name", 3, 45);
            validateForm.emailMatch(email, confirmEmail, "<b>Email Address Do Not Match</b>&#46; Please check your entry&#46;");
            validateForm.assoValid(o, "<b>Associate Not Selected!</b> Please select an Associate");
            validateForm.notEmpty(mobilePhone, "Mobile Phone ");
            validateForm.checkRegexp(firstName, nameRegEx, "First Name ", nameErrorMsg);
            validateForm.checkRegexp(lastName, nameRegEx, "Last Name ", nameErrorMsg);
            validateForm.checkRegexp(email, emailRegEx, "Email ", "format eg. jdoe@doe.com");

            if (o.action === "add" || o.action === "new")
            {
                validateForm.emailExists(email.val(), "<b>Email Address Already Exists!</b>", o.client.id);
            }
            validateForm.checkRegexp(mobilePhone, phoneRegEx, "Mobile Phone ", phoneErrorMsg);

            for (var z = 0; z < notReqFields.length; z++)
                if (notReqFields[z].val() !== "") // check if field has data
                {
                    if (notReqFields[z] === homePhone || notReqFields[z] === workPhone)
                    {
                        var p = convert.errorName(notReqFields[z]);
                        validateForm.checkRegexp(notReqFields[z], phoneRegEx, p, phoneErrorMsg);
                    }
                    else if (notReqFields[z] === company || notReqFields[z] === address || notReqFields[z] === city)
                    {
                        var v = convert.errorName(notReqFields[z]);
                        validateForm.checkLength(notReqFields[z], v, 3, 45);
                    }
                    else if (notReqFields[z] === zip)
                    {
                        validateForm.checkRegexp(zip, zipCodeRegEx, "Zip ", zipErrorMsg);
                    }
                }

            if (errors.length > 0) {
                errorPage.popup('open', {transition: "pop"});
                $('#error-client-info  ul').show();
                $("#error-client-info h3").html("Please correct these Errors!");
                $.each(errors, function (index, value) {
                    $("#error-client-info ul").append("<li>" + value + "</li>");
                });
            }
            else {
                $.mobile.changePage(confirmpage, {transition: "slideup"});
                o.client.firstName = firstName.val();
                o.client.lastName = lastName.val();
                o.client.mobilePhone = mobilePhone.val();
                o.client.email = email.val();
                o.client.homePhone = homePhone.val();
                o.client.workPhone = workPhone.val();
                o.client.company = company.val();
                o.client.address = address.val();
                o.client.city = city.val();
                o.client.state = state.val();
                o.client.zip = zip.val();
                o.client.memberLevel = $(memberLevel, this).val();
                o.client.serviceNormalid = parseInt($(normalService, this).val());
                o.client.serviceName = services.getServiceName(o);
                o.client.preferredAssociateName = assoObj.firstName;
                o.client.preferredAssociateId = assoObj.id;
                o.client.emailAdAlerts = emailAdAlerts.is(':checked');
                o.client.smsAdAlerts = smsAdAlerts.is(':checked');
                o.client.emailApptAlerts = emailApptAlerts.is(':checked');
                o.client.smsApptAlerts = smsApptAlerts.is(':checked');
                userInfo = renderData.drawInfo2(o);
                renderData.alertPrefs3(o, "#client-confirm-page .alert-prefs-3");
                $('.client-confirm-Table tbody').html(userInfo);

                $("#client-cancel-btn").on("click", function () {
                    $.mobile.changePage("#clients", {transition: "fade"});
                });
                $("#client-confirm-btn").on("click", function () {
                    var s = convert.stringifyObj(o);
                    doAjaxPost(s, '../FullCalPost', o.action, 'clients');
                    $(document).off("click", "#client-list-table tr td:nth-child(-n+3)");
                    $.mobile.changePage("#clients", {transition: "fade"});

                });
            }
        }); // end send element function
    } // end edit client function
}; // end clientOptions class functions

function  listClients(key, url, async, title) {
    var clientInfo = $(".clientInfo"),
            clients = [],
            memberLevels = [];
    clientSet = [];
    $("#client-list-table tbody").html("");
    doAjaxRequest(key, url, async, title, function (results) {
        $.each(results, function (index, value) {
            var o = new userObj(
                    value.firstName,
                    value.lastName,
                    value.emailAddress,
                    value.customerId,
                    value.associate2,
                    value.associateName,
                    value.associateId,
                    value.memberLevels,
                    value.client);
            clientSet.push(o);
        });
    });
    doAjaxRequest("ml", "../FullCalendar", false, "mLevels", function (results) {
        $.each(results, function (index, value) {
            memberLevels.push(value);
        });
    });
    renderClients(clientSet);
    $(document).on("click", "#client-list-table tr td:nth-child(-n+3)", function (evt) {

        var client = $("#client", this).val();
        var o = $.parseJSON(client);
        openUserInfo(o);
    });
} // end listClients function

function renderClients(clients, sortBy) {
    var clientTable = $("#client-list-table tbody");
    clientTable.html("");
    switch (sortBy)
    {
        case "id":
            clients = sort.byID(clients);
            break;
        case "firstName":
            clients = sort.byFirstName(clients);
            break;
        case "lastName":
            clients = sort.byLastName(clients);
            break;
        case "email":
            clients = sort.byEmail(clients);
            break;
        case "mobilePhone":
            clients = sort.byMobilePhone(clients);
            break;
        case "memberLevel":
            clients = sort.byMemberLevel(clients);
            break;
        default:
            clients.sort();
            break;
    }

    for (var i = 0; i < clients.length; i++)
    {
        var clientImgId = 0;
        if (clients[i].client.imgUpl === true)
        {
            clientImgId = clients[i].client.id;
        }

        //var opts = options.getSelectedOpt(clients[i]);
        clientTable.append(
                "<tr>" + "<td>" + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + '<img src="../img/clients/' + clientImgId + '.png" alt="client img" alt="img" >' + "</td>"
                + "<td id='" + clients[i].client.id + clients[i].client.id + "'  id='firstName' >" + clients[i].client.firstName + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + "</td>"
                + "<td>" + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + clients[i].client.lastName + "</td>"
                + "<td>" + "<input type='hidden' name='client' id='" + clients[i].client.id + "'>" + "<input type='checkbox' value='" + JSON.stringify(clients[i]) + "' class='c-select'>" + "</td>"
                + "</tr>");
    }
} // end renderClients function

// sorting object functions
var sort = {
    byID: function (array, isAscending) {
        array["sortArrayF"] = "";
        array["sortArrayL"] = "";
        array["sortArrayE"] = "";
        array["sortArrayP"] = "";
        array["sortArrayM"] = "";
        var currentSort = array["sortArrayID"];
        if (typeof currentSort !== 'boolean') {
            array.sort(function (a, b) {
                if (a.client.id > b.client.id) {
                    return 1;
                }
                if (a.client.id < b.client.id) {
                    return -1;
                }
// a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            array.reverse();
        }
// set the sort order
        array["sortArrayID"] = isAscending ? true : false;
        return array;
    },
    byFirstName: function (array, isAscending) {
        array["sortArrayID"] = "";
        array["sortArrayL"] = "";
        array["sortArrayE"] = "";
        array["sortArrayP"] = "";
        array["sortArrayM"] = "";
        var currentSort = array["sortArrayF"];
        if (typeof currentSort !== 'boolean') {
            array.sort(function (a, b) {
                if (a.firstName.toLowerCase() > b.firstName.toLowerCase()) {
                    return 1;
                }
                if (a.firstName.toLowerCase() < b.firstName.toLowerCase()) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            array.reverse();
        }
        // set the sort order
        array["sortArrayF"] = isAscending ? true : false;
        return array;
    },
    byLastName: function (array, isAscending) {
        array["sortArrayID"] = "";
        array["sortArrayF"] = "";
        array["sortArrayE"] = "";
        array["sortArrayM"] = "";
        array["sortArrayP"] = "";
        var currentSort = array["sortArrayL"];
        if (typeof currentSort !== 'boolean') {
            array.sort(function (a, b) {
                if (a.lastName.toLowerCase() > b.lastName.toLowerCase()) {
                    return 1;
                }
                if (a.lastName.toLowerCase() < b.lastName.toLowerCase()) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            array.reverse();
        }
        // set the sort order
        array["sortArrayL"] = isAscending ? true : false;
        return array;
    },
    byEmail: function (array, isAscending) {
        array["sortArrayID"] = "";
        array["sortArrayL"] = "";
        array["sortArrayF"] = "";
        array["sortArrayM"] = "";
        array["sortArrayP"] = "";
        var currentSort = array["sortArrayE"];
        if (typeof currentSort !== 'boolean') {
            array.sort(function (a, b) {
                if (a.client.email.toLowerCase() > b.client.email.toLowerCase()) {
                    return 1;
                }
                if (a.client.email.toLowerCase() < b.client.email.toLowerCase()) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            array.reverse();
        }
        // set the sort order
        array["sortArrayE"] = isAscending ? true : false;
        return array;
    },
    byMobilePhone: function (array, isAscending) {
        array["sortArrayID"] = "";
        array["sortArrayL"] = "";
        array["sortArrayF"] = "";
        array["sortArrayE"] = "";
        array["sortArrayM"] = "";
        var currentSort = array["sortArrayP"];
        if (typeof currentSort !== 'boolean') {
            array.sort(function (a, b) {
                if (a.client.mobilePhone.toLowerCase() > b.client.mobilePhone.toLowerCase()) {
                    return 1;
                }
                if (a.client.mobilePhone.toLowerCase() < b.client.mobilePhone.toLowerCase()) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            array.reverse();
        }
        // set the sort order
        array["sortArrayP"] = isAscending ? true : false;
        return array;
    },
    byMemberLevel: function (array, isAscending) {
        array["sortArrayID"] = "";
        array["sortArrayL"] = "";
        array["sortArrayF"] = "";
        array["sortArrayE"] = "";
        array["sortArrayP"] = "";
        var currentSort = array["sortArrayM"];
        if (typeof currentSort !== 'boolean') {
            array.sort(function (a, b) {
                if (a.client.memberLevel.toLowerCase() > b.client.memberLevel.toLowerCase()) {
                    return 1;
                }
                if (a.client.memberLevel.toLowerCase() < b.client.memberLevel.toLowerCase()) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            array.reverse();
        }
        // set the sort order
        array["sortArrayM"] = isAscending ? true : false;
        return array;
    }
}; // end sort object functions

var renderData = {
    drawInfo1: function (o) {
        var memberLevelColor = options.getMemberLevelColor(o);
        var associateImgId = 0;
        if (o.client.serviceName === undefined)
        {
            o.client.serviceName = "<FONT COLOR='#c00'>" + "not yet selected" + "</FONT>";
        }
        if (o.associate2.imgUpl === true)
        {
            associateImgId = o.associate2.id;
        }
        var clientImgId = 0;
        if (o.client.imgUpl === true)
        {
            clientImgId = o.client.id;
        }
        var c = "<tr>" + "<td title='client first and last name'>" + "client&#58;" + "</td>" + "<td>" + o.client.firstName + " " + o.client.lastName + '  <img src="../img/clients/'
                + clientImgId + '.png" alt="client img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='client email address'>" + "email&#58;" + "</td>" + "<td>" + "<a href='mailto:" + o.client.email + " '>" + o.client.email + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='client id number'>" + "id&#58;" + "</td>" + "<td>" + o.client.id + "</td>" + "</tr>"
                + "<tr>" + "<td title='mobile phone'>" + "mobile&#58;" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(o.client.mobilePhone) + "'>" + formatPhone(o.client.mobilePhone) + "</a>"
                + "&nbsp;"
                + "<a href='tel:" + o.client.mobilePhone + "'class='call-btn ui-btn ui-alt-icon ui-nodisc-icon ui-btn-inline ui-icon-phone ui-btn-icon-notext'></a>"
                + "<a href='sms:" + o.client.mobilePhone + "'class='call-btn ui-btn ui-alt-icon ui-nodisc-icon ui-btn-inline ui-icon-comment ui-btn-icon-notext'></a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='home phone number'>" + "home&#58;" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(o.client.homePhone) + " '>" + formatPhone(o.client.homePhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='work phone number'>" + "work&#58;" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(o.client.workPhone) + " '>" + formatPhone(o.client.workPhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='company'>" + "company&#58;" + "</td>" + "<td>" + o.client.company + "</td>" + "</tr>"
                + "<tr>" + "<td title='client address'>" + "address&#58;" + "</td>" + "<td>" + o.client.address + "</td>" + "</tr>"
                + "<tr>" + "<td title='city'>" + "city&#58;" + "</td>" + "<td>" + o.client.city + "</td>" + "</tr>"
                + "<tr>" + "<td title='client zip code'>" + "state&#58;" + "</td>" + "<td>" + o.client.state + "</td>" + "</tr>"
                + "<tr>" + "<td title='client zip code'>" + "zip&#58;" + "</td>" + "<td>" + o.client.zip + "</td>" + "</tr>"
                + "<tr>" + "<td title='member level'>" + "level&#58;" + "</td>" + "<td style='color:" + memberLevelColor + "'>" + o.client.memberLevel + "</td>" + "</tr>"
                + "<tr>" + "<td title='normal service'>" + "normal service&#58;" + "</td>" + "<td>" + o.client.serviceName + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate first name'>" + "associate&#58;" + "</td>" + "<td>" + o.associate2.firstName
                + '  <img src="../img/associates/' + associateImgId + '.png" alt="associate img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate email address'>" + "email&#58;" + "</td>" + "<td>" + "<a href='mailto:" + o.associate2.email + " '>" + o.associate2.email + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate mobile phone'>" + "mobile&#58;" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(o.associate2.mobilePhone) + " '>" + formatPhone(o.associate2.mobilePhone) + "</a>" + "</td>" + "</tr>";
        return c;
    },
    drawInfo2: function (o) {
        var memberLevelColor = options.getMemberLevelColor(o);
        var associateImgId = 0;
        if (o.client.serviceName === undefined)
        {
            o.client.serviceName = "<FONT COLOR='#c00'>" + "not yet selected" + "</FONT>";
        }
        if (o.associate2.imgUpl === true)
        {
            associateImgId = o.associate2.id;
        }
        var clientImgId = 0;
        if (o.client.imgUpl === true)
        {
            clientImgId = o.client.id;
        }
        var c = "<tr>" + "<td title='client first and last name'>" + "client&#58;" + "</td>" + "<td>" + o.client.firstName + " " + o.client.lastName + '  <img src="../img/clients/'
                + clientImgId + '.png" alt="client img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='client email address'>" + "email&#58;" + "</td>" + "<td>" + o.client.email + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='client id number'>" + "id&#58;" + "</td>" + "<td>" + o.client.id + "</td>" + "</tr>"
                + "<tr>" + "<td title='mobile phone'>" + "mobile&#58;" + "</td>" + "<td>" + formatPhone(o.client.mobilePhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='home phone number'>" + "home&#58;" + "</td>" + "<td>" + formatPhone(o.client.homePhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='work phone number'>" + "work&#58;" + "</td>" + "<td>" + formatPhone(o.client.workPhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='company'>" + "company&#58;" + "</td>" + "<td>" + o.client.company + "</td>" + "</tr>"
                + "<tr>" + "<td title='client address'>" + "address&#58;" + "</td>" + "<td>" + o.client.address + "</td>" + "</tr>"
                + "<tr>" + "<td title='city'>" + "city&#58;" + "</td>" + "<td>" + o.client.city + "</td>" + "</tr>"
                + "<tr>" + "<td title='client zip code'>" + "state&#58;" + "</td>" + "<td>" + o.client.state + "</td>" + "</tr>"
                + "<tr>" + "<td title='client zip code'>" + "zip&#58;" + "</td>" + "<td>" + o.client.zip + "</td>" + "</tr>"
                + "<tr>" + "<td title='member level'>" + "level&#58;" + "</td>" + "<td style='color:" + memberLevelColor + "'>" + o.client.memberLevel + "</td>" + "</tr>"
                + "<tr>" + "<td title='normal service'>" + "normal service&#58;" + "</td>" + "<td>" + o.client.serviceName + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate first name'>" + "associate&#58;" + "</td>" + "<td>" + o.associate2.firstName
                + '  <img src="../img/associates/' + associateImgId + '.png" alt="associate img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate email address'>" + "email&#58;" + "</td>" + "<td>" + o.associate2.email + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate mobile phone'>" + "mobile&#58;" + "</td>" + "<td>" + formatPhone(o.associate2.mobilePhone) + "</td>" + "</tr>";
        return c;
    },
    drawClientInfo: function (o) {
        var memberLevelColor = options.getMemberLevelColor(o);
        if (o.client.serviceName === undefined)
        {
            o.client.serviceName = "<FONT COLOR='#c00'>" + "not yet selected" + "</FONT>";
        }

        var client = "<li>" + "Client: " + "<strong>" + o.client.firstName + " " + o.client.lastName + "</strong>" + "</li>"
                + "<li>" + "Email: " + "<strong>" + o.client.email + "</strong>" + "</li>"
                + "<li>" + "Mobile: " + "<strong>" + o.client.mobilePhone + "</strong>" + "</li>"
                + "<li>" + "Home: " + "<strong>" + o.client.homePhone + "</strong>" + "</li>"
                + "<li>" + "Work: " + "<strong>" + o.client.workPhone + "</strong>" + "</li>"
                + "<li>" + "Company: " + "<strong>" + o.client.company + "</strong>" + "</li>"
                + "<li>" + "Address: " + "<strong>" + o.client.address + "</strong>" + "</li>"
                + "<li>" + "City: " + "<strong>" + o.client.city + "</strong>" + "</li>"
                + "<li>" + "State: " + "<strong>" + o.client.state + "</strong>" + "</li>"
                + "<li>" + "Zip: " + "<strong>" + o.client.zip + "</strong>" + "</li>"
                + "<li>" + "Level: " + "<strong style='color:" + memberLevelColor + "'>" + o.client.memberLevel + "</strong>" + "</li>"
                + "<li>" + "Service: " + "<strong>" + o.client.serviceName + "</strong>" + "</li>"
                + "<li>" + "Associate: " + "<strong>" + o.associate2.firstName + "</strong>" + "</li>";
        return client;
    },
    clientAlertPrefs: function (o) {
        var p =
                "<table>" + "<tbody>"
                + "<tr>" + "<td>" + "email alerts&#58;" + "</td>" + "<td>" + "<input type='checkbox' name='emailAdAlerts' id='emailAdAlerts' value='" + o.client.emailAdAlerts + "'>" + "</td>" + "</tr>"
                + "</tbody>" + "</table>";
        return p;
    },
    alertPrefs: function (o, e)
    {
        if (o.client.emailAdAlerts === true) {
            $(e + " #email-ad-alerts").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #email-ad-alerts").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.smsAdAlerts === true) {
            $(e + " #sms-ad-alerts").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #sms-ad-alerts").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.emailApptAlerts === true) {
            $(e + " #email-appt-alerts").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #email-appt-alerts").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.smsApptAlerts === true) {
            $(e + " #sms-appt-alerts").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #sms-appt-alerts").prop('checked', false).checkboxradio('refresh');
        }
    },
    alertPrefs1: function (o, e)
    {
        if (o.client.emailAdAlerts === true) {
            $(e + " #email-ad-alerts-1").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #email-ad-alerts-1").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.smsAdAlerts === true) {
            $(e + " #sms-ad-alerts-1").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #sms-ad-alerts-1").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.emailApptAlerts === true) {
            $(e + " #email-appt-alerts-1").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #email-appt-alerts-1").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.smsApptAlerts === true) {
            $(e + " #sms-appt-alerts-1").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #sms-appt-alerts-1").prop('checked', false).checkboxradio('refresh');
        }
    },
    alertPrefs3: function (o, e)
    {
        if (o.client.emailAdAlerts === true) {
            $(e + " #email-ad-alerts-3").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #email-ad-alerts-3").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.smsAdAlerts === true) {
            $(e + " #sms-ad-alerts-3").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #sms-ad-alerts-3").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.emailApptAlerts === true) {
            $(e + " #email-appt-alerts-3").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #email-appt-alerts-3").prop('checked', false).checkboxradio('refresh');
        }
        if (o.client.smsApptAlerts === true) {
            $(e + " #sms-appt-alerts-3").prop('checked', true).checkboxradio('refresh');
        }
        else {
            $(e + " #sms-appt-alerts-3").prop('checked', false).checkboxradio('refresh');
        }
    }
}; // end renderData class functions

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
            else {
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
        else {
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
            else {
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
            else {
                options[y] = "<option value='" + bkgColors[y].memberLevel + "'>" + bkgColors[y].memberLevel + "</option>";
            }
        }
        options.push(bkgColor);
        return options;
    },
    getMemberLevelColor: function (o) {
        var c = o.memberLevels;
        var memLevelColor;
        for (var y = 0; y < c.length; y++)
        {
            if (o.client.memberLevel === c[y].memberLevel)
            {
                memLevelColor = o.memberLevels[y].memberColor;
                return memLevelColor;
            }
        }
    }
}; // end options fucntion objects

var validateForm = {
    updateTips: function (t) {
        tips.html(t).addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    },
    checkRegexp: function (o, regexp, e, n)
    {
        if (!(regexp.test(o.val()))) {
            errors.push("<font color='#c00'>" + e + "</font>" + n);
        }
    },
    checkLength: function (o, n, min, max) {
        if (o.val().length > max || o.val().length < min) {
            errors.push("Length of " + "<font color='#c00'>" + n + "</font>" + " must be between " +
                    min + " and " + max + " characters");
        }
    },
    emailExists: function (e, n, id) {
        var clients = [];
        doAjaxRequest("ci", "../FullCalendar", false, "clientInfo", function (results) {
            $.each(results, function (index, value) {
                var o = new EventObj(value);
                clients.push(o);
            });
        });
        for (var i in clients)
        {
            if (clients[i].client.email === e && clients[i].client.id !== id)
            {
                errors.push(n + " " + "<b>" + clients[i].client.email + "</b>"
                        + " is assigned to " + "<b>" + clients[i].client.firstName + " " + clients[i].client.lastName + "</b>");
            }
        }
    },
    notEmpty: function (e, n) {
        if (e.val() === "" || e.val() === null)
        {
            errors.push("<font color='#c00'>" + n + "</font>" + fieldEmptyMsg);
        }
    },
    emailMatch: function (a, b, n) {
        if (a.val() !== b.val())
        {
            errors.push(n);
        }
    },
    assoValid: function (o, n) {
        if (o.associate2.id === 0)
        {
            errors.push(n);
        }
    },
    postErrors: function (e) {
        if (e.length > 0)
        {
            $('#error-client-info').popup('open', {transistion: 'pop'});
            $("#errorpage ul").show();
            $("#errorpage h3").html("Please correct these Errors!");
            $.each(e, function (index, value) {
                $('#errorpage ul').append("<li>" + value + "</li>");
            });
        }
    },
    doesMatch: function (a, b, msg) {
        if (a.val() !== b.val())
        {
            errors.push(msg);
        }
    },
    noChanges: function (obj1, obj2) {
        if (JSON.stringify(obj1) === JSON.stringify(obj2))
        {
            return true;
        }
        else {
            return false;
        }
    }
}; // end validateForm functions

$(function () {
    var sc = $("#search-client");
    sc.autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "../autocomplete",
                dataType: "json",
                data: {term: request.term},
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            label: item.firstName + " " + item.lastName + " " + item.email,
                            value: item.firstName + " " + item.lastName,
                            lastName: item.lastName,
                            firstName: item.firstName,
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
                            serviceNormalid: item.serviceNormalid,
                            serviceName: item.serviceName,
                            preferredAssociateName: item.preferredAssociateName,
                            preferredAssociateId: item.preferredAssociateId,
                            associateInfo: item.associateInfo,
                            memberLevels: item.memberLevels,
                            emailAdAlerts: item.emailAdAlerts,
                            smsAdAlerts: item.smsAdAlerts,
                            emailApptAlerts: item.emailApptAlerts,
                            smsApptAlerts: item.smsApptAlerts
                        };
                    }
                    ));
                } // end success function
            });
        },
        minLength: 2,
        select: function (event, ui) {
            var o = {
                firstName: ui.item.firstName,
                lastName: ui.item.lastName,
                emailAddress: ui.item.email,
                mobilePhone: ui.item.mobilePhone,
                customerId: ui.item.id,
                associate2: ui.item.associateInfo,
                associateName: ui.item.preferredAssociateName,
                associateId: ui.item.preferredAssociateId,
                memberLevels: ui.item.memberLevels,
                client: {
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
                    memberLevel: ui.item.memberLevel,
                    emailAdAlerts: ui.item.emailAdAlerts,
                    smsAdAlerts: ui.item.smsAdAlerts,
                    emailApptAlerts: ui.item.emailApptAlerts,
                    smsApptAlerts: ui.item.smsApptAlerts
                }
            };
            openUserInfo(o);
        }
    });

}); // end search client  function

function openUserInfo(o) {
    $("#edit-client-info").off(); // turn off edit-client-info button element
    o.action = "updateClient";
    var selClient = renderData.drawInfo1(o);
    var lastFiveEvents = serviceList.getCalendarEvents(o.client.id, 5);
    var lastVisitsTable = eventsRendering.drawEvents1(lastFiveEvents, "past");
    var futureEvents = serviceList.getFutureEvents(o.client.id, 5);
    var futureEventsTable = eventsRendering.drawEvents1(futureEvents, "future");

    $(".client-infoTable tbody").html(selClient);
    $.mobile.changePage("#client-infopage", {transition: "slideup"});
    $("#client-infopage .last-visits-table tbody").html(lastVisitsTable);
    $("#client-infopage .future-visits-table tbody").html(futureEventsTable);

    renderData.alertPrefs1(o, "#client-infopage .alert-prefs-1");

    $("#edit-client-info").on("click", function (evt) {
        $("#search-client").val("");
        o.action = "updateClient"; // notifies server to updae client info
        $("#edit-client-page h1").html("Edit Client Information");
        clientOptions.editClient(o);
    });
}
// toggle select all checboxes
$(document).on("click", "#select-header", function () {

    if (!isAllSelected)
    {
        $(".c-select").each(function () {
            $(".c-select").prop('checked', true);
        });
        isAllSelected = true;
    }
    else if (isAllSelected)
    {
        $(".c-select").each(function () {
            $(".c-select").prop('checked', false);
        });
        isAllSelected = false;
    }
    else {
        $(".c-select").each(function () {
            $(".c-select").prop('checked', true);
        });
        isAllSelected = true;
    }

}); // end select header on click function

// client select options
$(document).on("change", "#clients #c-options", function () {

    var clientArray = [],
            errorAction = $("#error-action"),
            optSel = $("#c-options option:selected").val();

    if (optSel === "new-client")
    {
        var n = {
            action: "add",
            customerId: 0,
            memberLevels: [
                {memberLevel: "bronze"},
                {memberLevel: "silver"},
                {memberLevel: "gold"},
                {memberLevel: "platinum"}
            ],
            client: {
                memberLevel: "bronze",
                imgUpl: false,
                state: "AL",
                emailAdAlerts: true,
                smsAdAlerts: true,
                emailApptAlerts: true,
                smsApptAlerts: true
            },
            associate2: {
                id: 0
            }
        };
        $("#edit-client-page h1").html("Enter New Client Information");
        clientOptions.editClient(n);
    }
    else {
        $(".c-select:checked").each(function () {
            var client = $(this).val();
            var clientObj = $.parseJSON(client);
            clientArray.push(clientObj); //push each checkbox value into the array
        });
        if (clientArray.length === 0) // no checkboxes checked
        {
            var notSelected = "Please select one or more checkboxes.";
            $("#action-error").popup("open", {transition: "flip"});
            $("#action-error h3").html(notSelected);
        }
        else {
            $("#send-message-btn").off();
            $("#send-deactivate").off();
            $("#deactivate-cancel-btn").off();
            $("#message-cancel-btn").off();

            var sendNumber = clientArray.length;
            var errorArray;

            var clientPlural = sendNumber === 1 ? "Client" : "Clients";
            /*===============  send message =================*/
            if (optSel === "send-message") // send message
            {
                $.mobile.changePage("#clients-message", {transition: "slideup"});
                var sendMsgText = "enter a message for " + "<font color='#c00'>" + sendNumber + " selected "
                        + clientPlural + "</font>";
                //sendMessage(clientArray);
                $("#clients-message p").html(sendMsgText);

                $("#message-cancel-btn").on("click", function () {
                    $.mobile.changePage("#clients", {transition: "fade"});
                });

                $("#send-message-btn").on("click", function () {
                    $("#message-error ul").html("");
                    var msgElement = $("#message-to-send");
                    var subjectElement = $("#subject");
                    errors = [];

                    validateForm.checkLength(msgElement, "Message", 10, 145);

                    if (errors.length > 0) {
                        $("#message-error").popup("open", {transistion: "pop"});
                        $("#message-error h3").html("Please correct these Errors!");
                        $.each(errors, function (index, value) {
                            $("#message-error ul").append("<li>" + value + "</li>");
                        });
                    }
                    else
                    {
                        var subject = subjectElement.val(); // get subject
                        var msgToSend = msgElement.val(); // get message
                        var s = convert.stringifyObj(clientArray);

                        doAjaxPost(s, '../FullCalPost', "sendMessage" + "_" + msgToSend + "_" + "subject" + "_" + subject);

                        $.mobile.changePage("#clients", {transition: "fade"});
                    }
                });
            }
            else if (optSel === "deactivate-clients") // deactivate clients
            {
                var clientTable = associateClass.delClientList(clientArray);

                var legendText = "Are you sure you want to" + "<font color='#c00'>" + " deactivate " + sendNumber + "</font>" + " " + clientPlural + "!";
                $.mobile.changePage("#clients-deactivate", {transition: "slideup"});
                $("#clients-deactivate fieldset#clients-del-fieldset legend").html(legendText);
                $("#clients-del-fieldset #deactivate-client-list").html(clientTable);

                $("#deactivate-cancel-btn").on("click", function () {
                    $.mobile.changePage("#clients", {transition: "fade"});
                });
                $("#send-deactivate").on("click", function () {
                    for (var i in clientArray)
                    {
                        clientArray[i].client.isAccountActive = false;
                    }
                    var s = convert.stringifyObj(clientArray);
                    doAjaxPost(s, '../FullCalPost', "deactivateClients");
                    $.mobile.changePage("#clients", {transition: "fade"});
                    listClients("ci", "../FullCalendar", false, "clientInfo");
                });
            }
        }
    }
    $('[name=c-options]').val('default').selectmenu("refresh"); // reset to default select option
}); // end client select options - on change
