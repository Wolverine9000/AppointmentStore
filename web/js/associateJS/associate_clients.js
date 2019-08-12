/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global nameRegEx, emailRegEx, associateClass */

var clientSet = [];
var isAllSelected = false;
var associateObj1;
var todaysDate;
var minutes = [];
var hours = [];

$(document).ready(function () {
    // populate minutes option select
//    for (var i = 00; i < 60; i++)
//    {
//        minutes[i] = "<option value='" + i + "'>" + ((i < 10) ? "0" : "") + i + "</option>";
//    }
//    $("#send-minutes").html(minutes);
//    // populate hours option select
//    for (var i = 1; i < 13; i++)
//    {
//        hours[i] = "<option value='" + i + "'>" + i + "</option>";
//    }
//    $("#send-hour").html(hours);

    // populate date to send message entry input placeholders wtth today's date
    todaysDate = $.datepicker.formatDate("mm/dd/yy", new Date());
    $('input#dateToSendMsg.hasDatepicker').prop("value", todaysDate);

    serviceListing = serviceList.getServiceList();
    serviceCategories = serviceList.getServiceCategories();
    associateListArr = getAssociates();
    $(function () {
        $(".confirmUser #accordion-3").accordion({
            collapsible: true,
            active: 0,
            heightStyle: "content"
        });
    });

    var v = memberLevels();
    $("#memberLevels").append(v);
    listClients("ci", "../FullCalendar", false, "clientInfo", 'firstName');

//   add client
    $("#add-client").on("click", function () {
        var n = {
            action: "new",
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
        $(".editUser #accordion-2").show();
        editUserInfo(n);
        $(".editUser").dialog("option", "title", "Enter Client Information");
        $(".editUser").dialog("open");
        $(".editUser").dialog("option", "minHeight", 400);
        $(".editUser").dialog("option", "minWidth", 370);
        $(".editUser").dialog("option", "width", 450);
        $(".editUser").dialog("option", "maxWidth", 580);
    }); // end add-client function
    $(".editUser, #send-message-field").hide();
}); // end document ready function

function  listClients(key, url, async, title) {
    var clientInfo = $(".clientInfo"),
            memberLevels = [];
    clientSet = [];
    $("#client-list-table tbody").html("");
    doAjaxRequest(key, url, async, title, function (results) {
        $.each(results, function (index, value) {
            var o = new UserObj(
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
    $(document).on("click", "#client-list-table tr td:nth-child(-n+6)", function (evt) {

        var client = $("#client", this).val();
        var o = $.parseJSON(client);
        o.client.memberLevel = $("#" + o.client.id + " #memberLevel").val();

        var selClient = renderData.drawInfo1(o);
        var lastFiveEvents = serviceList.getCalendarEvents(o.client.id, 5);
        var lastVisitsTable = eventsRendering.drawEvents1(lastFiveEvents, "past");
        var futureEvents = serviceList.getFutureEvents(o.client.id, 5);
        var futureEventsTable = eventsRendering.drawEvents1(futureEvents, "future");
        $(".clientInfo .last-visits-table tbody").html(lastVisitsTable);
        $(".clientInfo .future-visits-table tbody").html(futureEventsTable);
        renderData.alertPrefs(o, ".clientInfo #accordion");

        openUserInfo(o);
        clientInfo.dialog("option", "title", "Client Info");
        clientInfo.dialog("open");
        clientInfo.dialog("option", "minHeight", 400);
        clientInfo.dialog("option", "Height", 679);
        clientInfo.dialog("option", "width", 450);
        clientInfo.dialog("option", "minWidth", 400);
        clientInfo.dialog("option", "maxWidth", 700);
        $("#accordion").show();
        $(".eventInfoTable tbody").html(selClient);
    });
}

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

        var opts = options.getSelectedOpt(clients[i]);
        clientTable.append(
                "<tr>" + "<td>" + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + '<img src="../img/clients/' + clientImgId + '.png" alt="client img" alt="img" >' + "</td>"
                + "<td>" + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + clients[i].client.id + "</td>"
                + "<td id='" + clients[i].client.id + clients[i].client.id + "'  id='firstName' >" + clients[i].client.firstName + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + "</td>"
                + "<td>" + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + clients[i].client.lastName + "</td>"
                + "<td>" + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + clients[i].client.email + "</td>"
                + "<td>" + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + formatPhone(clients[i].client.mobilePhone) + "</td>"
                + "<td id='" + clients[i].client.id + "'>" + "<select name='memberLevel' id='memberLevel' style='border-color:" + opts[4] + '&#59;' + 'color:' + opts[4] + "'>"
                + opts + "<input type='hidden' name='client' id='client' value='" + JSON.stringify(clients[i]) + "'>" + "</select>" + "</td>"
                + "<td>" + "<input type='hidden' name='client' id='" + clients[i].client.id + "'>" + "<input type='checkbox' value='" + JSON.stringify(clients[i]) + "' class='c-select'>" + "</td>"
                + "</tr>");
    }
} // end renderClients function

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
$(document).on("change", "#c-options", function () {
    var clientArray = [];
    var optSel = $("#c-options option:selected").val();

    $(".c-select:checked").each(function () {
        var client = $(this).val();
        var clientObj = $.parseJSON(client);
        clientObj.clientMessage = true;
        clientArray.push(clientObj); //push each checkbox value into the array
    });
    if (clientArray.length === 0)
    {
        alert("You must first select one or more clients.");
    }
    else {
        var sendNumber = clientArray.length;
        var clientPlural = sendNumber === 1 ? "Client" : "Clients";
        if (optSel === "send-message")
        {
            var sendMsgText = "enter a message for " + "<font color='#c00'>" + sendNumber + " selected "
                    + clientPlural + "</font>";
            sendMessage(clientArray);

            $("#send-message-field").dialog("option", "title", "Send Message");
            $("#send-message-field").dialog("open");
            $("#send-message-field").dialog("option", "height", 'auto');
            $("#send-message-field").dialog("option", "width", 'auto');
            $("#send-message-field").dialog("option", "maxWidth", 670);
            $("#send-message-field").dialog("option", "maxHeight", 370);
            $("#send-message-field #messageHeader").html(sendMsgText);
            $("#send-message-field").show();
        }
        else if (optSel === "deactivate-clients")
        {
            var delClientEle = $("#clients-del-fieldset");
            var clientTable = associateClass.delClientList(clientArray);

            var legendText = "Are you sure you want to" + "<font color='#c00'>" + " deactivate " + sendNumber + "</font>" + " " + clientPlural + "!";
            $("section fieldset#clients-del-fieldset span").addClass("ui-icon ui-icon-alert");
            $("fieldset#clients-del-fieldset legend").html(legendText);
            deactivateClient(clientArray);
            delClientEle.dialog("option", "title", "Deactivate " + clientPlural + " Confirmation");
            delClientEle.dialog("open");
            delClientEle.dialog("option", "height", 'auto');
            delClientEle.dialog("option", "width", 'auto');
            delClientEle.dialog("option", "maxWidth", 570);
            delClientEle.dialog("option", "maxHeight", 680);

            $("#clients-del-fieldset #delete-client-list").html(clientTable);
        }
    }
    $('[name=c-options]').val('default'); // reset to default select option
}); // end client select options - on change

// this element selection updates the member level on server and on the selected client list row
$(document).on("change", "#client-list-table tr td:nth-child(7)", function () {
    var client = $("#client", this).val();
    var c = $("#memberLevel", this).val();
    var o = $.parseJSON(client);
    o.action = "updateMemberLevel"; // add to client object the type action to be performed on server
    o.client.memberLevel = c;
    var color = options.getSelectedOpt(o);
    $("#memberLevel", this).css('border-color', color[4]);
    $("#memberLevel", this).css('color', color[4]);
    var s = convert.stringifyObj(o);
    doAjaxPost(s, '../FullCalPost', "updateMemberLevel");
}); // end member level on change function

function memberLevels() {
    var o = [];
    doAjaxRequest("ml", "../FullCalendar", false, "mLevels", function (results) {
        $.each(results, function (index, value) {
            o[index] = "<option value='" + value + "'>" + value + "</option>";
        });
    });
    var ml = "<select id='filterOpt'>" + o + "</select>";
    return ml;
} // end memberLevels() function

// filter for member levels
$(document).on("change", '#filterOpt', function () {
    var t = $("#filterOpt").val();
    isAllSelected = false;
    listClients("getList", "../FullCalendar", false, t);
});
// show all clients
$(document).on("click", "#showAllClients", function () {
    listClients("ci", "../FullCalendar", false, "clientInfo");
});
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

// sort when column name is clicked
$(document).on("click", "#client-list-table  th:nth-child(2)", function (evt) {
    renderClients(clientSet, "id");
});
$(document).on("click", "#client-list-table  th:nth-child(4)", function (evt) {
    renderClients(clientSet, "lastName");
});
$(document).on("click", "#client-list-table  th:nth-child(5)", function (evt) {
    renderClients(clientSet, "email");
});
$(document).on("click", "#client-list-table  th:nth-child(3)", function (evt) {
    renderClients(clientSet, "firstName");
});
$(document).on("click", "#client-list-table  th:nth-child(7)", function (evt) {
    renderClients(clientSet, "memberLevel");
});
$(document).on("click", "#client-list-table  th:nth-child(6)", function (evt) {
    renderClients(clientSet, "mobilePhone");
});
function openUserInfo(o) {
    //$(document).off("click", "#client-list-table tr td:nth-child(-n+6)");
    $("#client-list-table").unbind("click").click();
    var editUser = $(".editUser"),
            clientImg = $("#clientImg"),
            memLevel = o.client.memberLevel,
            normalSvcId = o.client.serviceNormalid;
    $(".eventInfoTable #memberLevel option:selected").val(memLevel);
    $(".eventInfoTable #normal-service option:selected").val(normalSvcId);
    $(function () {
        $(".clientInfo").dialog({
            autoOpen: false,
            modal: true,
            show: {
                duration: 200
            },
            buttons: {
                OK: function () {
                    var ml = $("#memberLevel", this).val();
                    var serviceNum = parseInt($("#normal-service", this).val());
                    $("#memberLevel", this).val(ml);
                    $("#normal-service", this).val(serviceNum);
                    // check if member level changed
                    if (memLevel !== ml)
                    {
                        var c = $("#memberLevel", this).val();
                        o.client.memberLevel = c;
                        o.action = "updateMemberLevel";
                        var s = convert.stringifyObj(o);
                        doAjaxPost(s, '../FullCalPost', "updateMemberLevel", true);
                        $("#" + o.client.id + " #memberLevel").val(c);
                        var color = options.getSelectedOpt(o);
                        $("#" + o.client.id + " #memberLevel").css('border-color', color[4]);
                        $("#" + o.client.id + " #memberLevel").css('color', color[4]);
                    }
                    $(this).dialog("close");
                },
                Edit: function () {
                    var clientImgId = 0;
                    if (o.client.imgUpl === true)
                    {
                        clientImgId = o.client.id;
                    }
                    var pic = '<img src="../img/clients/' + clientImgId + '.png" alt="client img" alt="img">';
                    $(".editUserTable #client-picture").html(pic);
                    o.client.memberLevel = $("#memberLevel", this).val();
                    o.action = "updateClient"; // notifies server to updae client info
                    editUserInfo(o);

                    editUser.dialog("option", "title", "Update Client Information");
                    editUser.dialog("open");
                    editUser.dialog("option", "minHeight", 400);
                    editUser.dialog("option", "minWidth", 370);
                    editUser.dialog("option", "width", 450);
                    editUser.dialog("option", "maxWidth", 580);
                    $(".editUser #accordion-2").show();
                    $(this).dialog("close");
                }
            },
            close: function () {
                $("#search-client").val("");
            }
        });
    });
} // end openUserInfo function

function editUserInfo(o, backFlag) {
    $(".clientInfo").unbind("click").click();
    //$("#add-client").unbind("click").click();
    $("#associate").unbind("change").change();
    var tips = $(".editUser .validateTips");
    var confirmUser = $(".confirmUser"),
            firstName = $("#firstNm"),
            lastName = $("#lastNm"),
            email = $("#email"),
            emailChg = o.client.email,
            confirmEmail = $("#confirmEmail"),
            mobilePhone = $("#mobilePhone"),
            homePhone = $("#homePhone"),
            workPhone = $("#workPhone"),
            company = $("#company"),
            address = $("#address"),
            city = $("#city"),
            state = $("#state"),
            zip = $("#zip"),
            memberLevel = $(".editUserTable #memberLevel"),
            normalService = $(".editUserTable #normal-service"),
            associate = $("#associate"),
            assoImg = $("#assoImg"),
            emailAdAlerts = $(".editUser #accordion-2 #email-ad-alerts"),
            smsAdAlerts = $(".editUser #accordion-2 #sms-ad-alerts"),
            emailApptAlerts = $(".editUser #accordion-2 #email-appt-alerts"),
            smsApptAlerts = $(".editUser #accordion-2 #sms-appt-alerts"),
            allFields = $([]).add(firstName).add(lastName).add(email).add(confirmEmail).add(mobilePhone).add(homePhone)
            .add(workPhone).add(company).add(address).add(city).add(state).add(zip).add(memberLevel).add(assoImg).add(normalService)
            .add(tips);

    var opts = [];
    var svcs = [];
    var aList = [];
    var stateList = [];

    function updateTips(t) {
        tips.html(t).addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }
    function checkRegexp(o, regexp, n)
    {
        if (!(regexp.test(o.val()))) {
            o.addClass("ui-state-error");
            updateTips(n);
            return false;
        }
        return true;
    }
    function checkLength(o, n, min, max) {
        if (o.val().length > max || o.val().length < min) {
            o.addClass("ui-state-error");
            updateTips("Length of " + n + " must be between " +
                    min + " and " + max + " characters");
            return false;
        }
        return true;
    }
    function emailExists(e, n, id) {
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
                updateTips(n + " " + "<b>" + clients[i].client.email + "</b>"
                        + " is assigned to " + "<b>" + clients[i].client.firstName + " " + clients[i].client.lastName + "</b>");
                return false;
            }
        }
        return true;
    }
    function emailMatch(a, b, n) {
        if (a.val() !== b.val())
        {
            updateTips(n);
            return false;
        }
        else if (a.val() === b.val()) {
            return true;
        }
    }
    function assoValid(o, n) {
        if (o.associate2.id === 0)
        {
            updateTips(n);
            return false;
        }
        return true;
    }
// change associate picture on change
    associate.on("change", function (evt) {
        var u = convert.parseJsonObj(associate);
        o.associate2 = u;
        assoImg.html(associateClass.getAssociatePicture(o));
    });
    if (o.action === "updateClient" || o.action === "add")
    {
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
    }

    renderData.alertPrefs(o, '#accordion-2 .alert-prefs-table');
    opts = options.getMemberLevel(o);
    svcs = services.getServices(o);
    aList = associateClass.getAssociateList(o);
    stateList = options.getState(o);
    memberLevel.html(opts);
    normalService.html(svcs);
    associate.html(aList);
    assoImg.html(associateClass.getAssociatePicture(o));
    state.html(stateList);

    if (backFlag !== true)
    {
        associateObj1 = {
            firstName: firstName.val(),
            lastName: lastName.val(),
            email: email.val(),
            mobilePhone: mobilePhone.val(),
            homePhone: homePhone.val(),
            workPhone: workPhone.val(),
            company: company.val(),
            address: address.val(),
            city: city.val(),
            state: state.val(),
            zip: zip.val(),
            memberLevel: memberLevel.val(),
            normalService: normalService.val(),
            associate: associate.val(),
            emailAdAlerts: emailAdAlerts.is(':checked'),
            smsAdAlerts: smsAdAlerts.is(':checked'),
            emailApptAlerts: emailApptAlerts.is(':checked'),
            smsApptAlerts: smsApptAlerts.is(':checked')
        };
    }

    var notReqFields = [homePhone, workPhone, company, address, city, zip];
    $(function () {
        $(".editUser").dialog({
            autoOpen: false,
            modal: true,
            show: {
                duration: 200
            },
            buttons: {
                Next: function () {
                    var associateObj2 = {
                        firstName: firstName.val(),
                        lastName: lastName.val(),
                        email: email.val(),
                        mobilePhone: mobilePhone.val(),
                        homePhone: homePhone.val(),
                        workPhone: workPhone.val(),
                        company: company.val(),
                        address: address.val(),
                        city: city.val(),
                        state: state.val(),
                        zip: zip.val(),
                        memberLevel: memberLevel.val(),
                        normalService: normalService.val(),
                        associate: associate.val(),
                        emailAdAlerts: emailAdAlerts.is(':checked'),
                        smsAdAlerts: smsAdAlerts.is(':checked'),
                        emailApptAlerts: emailApptAlerts.is(':checked'),
                        smsApptAlerts: smsApptAlerts.is(':checked')
                    };
                    if ((validateForm.noChanges(associateObj1, associateObj2)) === true)
                    {
                        updateTips("No changes were made to Client's profile.");
                        var bValid = false;
                    }
                    else {
                        var av = convert.parseJsonObj(associate);
                        o.associate2 = av;
                        o.associateName = av.firstName;
                        o.associateId = av.id;
                        var userInfo;
                        var bValid = true;
                        allFields.removeClass("ui-state-error");
                        bValid = bValid && checkLength(firstName, "First Name", 3, 45);
                        bValid = bValid && checkLength(lastName, "Last Name", 3, 45);
                        bValid = bValid && emailMatch(email, confirmEmail, "Confirmation Email Address Do Not Match&#46; Please check your entry&#46;");
                        bValid = bValid && assoValid(o, "Associate Not Selected! <br> Please select an Associate");
                        bValid = bValid && checkRegexp(firstName, nameRegEx, "First Name may consist of a-z, 0-9, underscores, begin with a letter&#46;");
                        bValid = bValid && checkRegexp(lastName, nameRegEx, "Last Name may consist of a-z, 0-9, underscores, begin with a letter&#46;");
//                    bValid = bValid && checkRegexp(associateName, nameRegEx, "Associate Name may consist of a-z, 0-9, underscores, begin with a letter.");
                        bValid = bValid && checkRegexp(email, emailRegEx, "A valid Email Address is required <br> eg. jdoe&#64;doe.com");
                        bValid = bValid && checkRegexp(confirmEmail, emailRegEx, "A valid Email Address is required <br> eg&#46; jdoe@doe.com");

                        if (o.action === "add" || o.action === "new" || email !== emailChg)
                        {
                            bValid = bValid && emailExists(email.val(), "Email Address Already Exists!", o.client.id);
                        }
                        bValid = bValid && checkRegexp(mobilePhone, phoneRegEx, phoneErrorMsg);

                        for (var z = 0; z < notReqFields.length; z++)
                            if (notReqFields[z].val() !== "") // check if field has data
                            {
                                if (notReqFields[z] === homePhone || notReqFields[z] === workPhone)
                                {
                                    bValid = bValid && checkRegexp(notReqFields[z], phoneRegEx, phoneErrorMsg);
                                }
                                else if (notReqFields[z] === company || notReqFields[z] === address || notReqFields[z] === city)
                                {
                                    bValid = bValid && checkLength(notReqFields[z], "This fieild", 3, 45);
                                }
                                else if (notReqFields[z] === zip)
                                {
                                    bValid = bValid && checkRegexp(zip, zipCodeRegEx, zipErrorMsg);
                                }
                            }
                        if (bValid)
                        {
                            if (o.action === "updateClient" || o.action === "add") {
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
                                o.client.memberLevel = $("#memberLevel", this).val();
                                o.client.serviceNormalid = parseInt($("#normal-service", this).val());
                                o.client.serviceName = services.getServiceName(o);
                                o.associate2 = av;
                                o.client.preferredAssociateName = av.firstName;
                                o.client.preferredAssociateId = av.id;
                                o.client.emailAdAlerts = emailAdAlerts.is(':checked');
                                o.client.smsAdAlerts = smsAdAlerts.is(':checked');
                                o.client.emailApptAlerts = emailApptAlerts.is(':checked');
                                o.client.smsApptAlerts = smsApptAlerts.is(':checked');
                                userInfo = renderData.drawInfo1(o);
                                renderData.alertPrefs(o, "#accordion-3");
                                confirmUserInfo(o);
                            }
                            else {
                                var s = parseInt($("#normal-service", this).val());
                                var n = services.getServiceName(s);
                                var c = {
                                    serviceId: 0,
                                    firstName: firstName.val(),
                                    lastName: lastName.val(),
                                    emailAddress: email.val(),
                                    mobilePhone: mobilePhone.val(),
                                    customerId: 0,
                                    associateName: o.associateName,
                                    associateId: o.associateId,
                                    associate2: o.associate2,
                                    memberLevels: o.memberLevels,
                                    action: "add",
                                    client: {
                                        firstName: firstName.val(),
                                        lastName: lastName.val(),
                                        email: email.val(),
                                        mobilePhone: mobilePhone.val(),
                                        homePhone: homePhone.val(),
                                        workPhone: workPhone.val(),
                                        company: company.val(),
                                        address: address.val(),
                                        city: city.val(),
                                        state: state.val(),
                                        zip: zip.val(),
                                        id: 0,
                                        imgUpl: false,
                                        serviceNormalid: s,
                                        serviceName: n,
                                        memberLevel: memberLevel.val(),
                                        preferredAssociateName: av.firstName,
                                        preferredAssociateId: av.id,
                                        emailAdAlerts: emailAdAlerts.is(':checked'),
                                        smsAdAlerts: smsAdAlerts.is(':checked'),
                                        emailApptAlerts: emailApptAlerts.is(':checked'),
                                        smsApptAlerts: smsApptAlerts.is(':checked')
                                    }
                                };

                                userInfo = renderData.drawInfo1(c);
                                renderData.alertPrefs(c, ".confirmUser #accordion-3");
                                confirmUserInfo(c);
                            }
                            confirmUser.dialog("option", "title", "Confirm Client Information");
                            confirmUser.dialog("open");
                            confirmUser.dialog("option", "minHeight", 400);
                            confirmUser.dialog("option", "width", 440);
                            confirmUser.dialog("option", "minWidth", 370);
                            confirmUser.dialog("option", "maxWidth", 580);
                            $(".confirmUser #accordion-3").show();
                            $(".confirmUser .confirmUserTable tbody").html(userInfo);
                            $(this).dialog("close");
                        }
                    }
                },
                Cancel: function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                notReqFields = [];
                allFields.html("").removeClass("ui-state-error");
                allFields.val("");
                $("#associate").off("change").change();
            }
        });
    });
} // end editUserInfo function

function confirmUserInfo(o) {

    var editUser = $(".editUser");
    $(function () {
        $(".confirmUser").dialog({
            autoOpen: false,
            modal: true,
            show: {
                duration: 200
            },
            buttons: {
                Submit: function () {
                    o.client.memberLevel = $("#memberLevel", this).val();
                    var s = convert.stringifyObj(o);
                    doAjaxPost(s, '../FullCalPost', o.action);
                    $(document).off("click", "#client-list-table tr td:nth-child(-n+6)").click();
                    listClients("ci", "../FullCalendar", false, "clientInfo");
                    $(this).dialog("close");
                },
                Back: function () {
                    var backFlag = true; // set as true to bypass any changes made check
                    var stateList = options.getState(o);
                    $("#userForm #state").html(stateList);
                    o.client.memberLevel = $("#memberLevel", this).val();
                    editUserInfo(o, backFlag);
                    editUser.dialog("option", "title", "Update Client Information");
                    editUser.dialog("open");
                    editUser.dialog("option", "minHeight", 400);
                    editUser.dialog("option", "minWidth", 450);
                    editUser.dialog("option", "maxWidth", 580);
                    $(this).dialog("close");
                }
            },
            close: function () {
            }
        });
    });
}

function nowHour(h)
{
    var hourNum = parseInt(h);
    var hourOptArr = [];
    for (var i = 1; i < 13; i++)
    {
        if (i === hourNum)
        {
            hourOptArr[i] += "<option value='" + i + "' selected >" + i + "</option>";
        }
        else
            hourOptArr[i] = "<option value='" + i + "'>" + i + "</option>";
    }
    return hourOptArr;
}

function nowMinute(m)
{
    var minNum = parseInt(m);
    var minOptArr = [];
    for (var i = 0; i < 60; i++)
    {
        if (i === minNum)
        {
            minOptArr[i] = "<option value='" + i + "' selected >" + ((i < 10) ? 0 : "") + i + "</option>";
        }
        else
            minOptArr[i] = "<option value='" + i + "'>" + ((i < 10) ? 0 : "") + i + "</option>";
    }
    return minOptArr;
}

function sendMessage(arr) {
    var message = $("#message-to-send"),
            tips = $("#send-message-field .validateTips"),
            msgElement = $("#message-to-send"),
            subjectElement = $("#subject"),
            dateToSendMsg = $('#dateToSendMsg'),
            sendNow = $("#send-now"),
            sendHour = $("#send-hour"),
            sendMinute = $("#send-minutes"),
            sendAmPm = $("#send-ampm"),
            allFields = $([]).add(message.add(dateToSendMsg).add(sendHour).add(sendMinute).add(sendAmPm)),
            msgValid;
    dateToSendMsg.val(todaysDate);

    sendNow.prop("checked", true); // set checkbox to checked state
    dateToSendMsg.prop('disabled', true);
    sendHour.prop('disabled', true);
    sendMinute.prop('disabled', true);
    sendAmPm.prop('disabled', true);
    dateToSendMsg.addClass('disabledTextColor');

// set the current time enter them in drop-down menus
    var now = moment();
    var h = now.format("h");
    var m = now.format("mm");
    var ampm = now.format("A");

    var hourOpt = nowHour(h);
    sendHour.html(hourOpt);

    var minOpt = nowMinute(m);
    sendMinute.html(minOpt);

    sendAmPm.val(ampm);

// toggle time set fields when checkbox send now is selected
    sendNow.change(function () {
        if (sendNow.is(':checked') === true) {
            dateToSendMsg.addClass('disabledTextColor');
            var nowDate = moment().format("MM/DD/YYYY"); // current date
            dateToSendMsg.val(nowDate).prop('disabled', true);
            sendHour.prop('disabled', true);
            sendMinute.prop('disabled', true);
            sendAmPm.prop('disabled', true);

            var now = moment();
            var h = now.format("h");
            var m = now.format("mm");
            var amPm = now.format("A");

            var hourOpt = nowHour(h);
            sendHour.html(hourOpt);

            var minOpt = nowMinute(m);
            sendMinute.html(minOpt);

            sendAmPm.val(amPm);
        }
        else {
            dateToSendMsg.removeClass('disabledTextColor');
            dateToSendMsg.prop('disabled', false);
            sendHour.prop('disabled', false);
            sendMinute.prop('disabled', false);
            sendAmPm.prop('disabled', false);
        }
    });

    $(function () {
        $("#send-message-field").dialog({
            autoOpen: false,
            modal: true,
            show: {
                duration: 200
            },
            buttons: {
                Send: function () {
                    var momObj = moment();
                    var now = moment();
                    allFields.removeClass("ui-state-error");
                    tips.html("");
                    var errorMsgs = [];
                    var timeToSend;

                    if (sendNow.is(':checked') === false)
                    {
                        var isScheduled = true;
                        // get the time and date values
                        sendAmPm.val();
                        var d = dateToSendMsg.val();
                        var h = sendHour.val();
                        var m = sendMinute.val() < 10 ? "0" + sendMinute.val() : sendMinute.val();
                        var ampm = sendAmPm.val();
                        // create date string in order to construct momemt object
                        var dateTimeStr = d + " " + h + ":" + m + " " + ampm;
                        momObj = moment(dateTimeStr, dateTimeFmts)
                        // validate date string
                        if (jsDate.validateDate(d) === false)
                        {
                            msgValid = false;
                            dateToSendMsg.addClass("ui-state-error");
                            errorMsgs.push("Invalid " + "<font color='#c00'>Date</font>"
                                    + " format! Please enter a valid Date format.");
                        }
                        else if (momObj.isBefore(now) === true)
                        {
                            msgValid = false;
                            if (momObj.isBefore(now, "day") === true || momObj.isBefore(now, "month") || momObj.isBefore(now, "year"))
                            {
                                dateToSendMsg.addClass("ui-state-error");
                            }
                            else if (momObj.isBefore(now, "hour"))
                            {
                                sendHour.addClass("ui-state-error");
                                sendAmPm.addClass("ui-state-error");
                            }
                            else if (momObj.isBefore(now, "minute"))
                            {
                                sendMinute.addClass("ui-state-error");
                            }
                            errorMsgs.push("<font color='#c00'>Past Time</font>"
                                    + " selected. Please select a future time.");
                        }
                        else
                        {
                            msgValid = true;
                        }
                    }
                    var subject = subjectElement.val(); // get subject
                    var msgToSend = message.val(); // get message
                    if (msgElement.val().length > 145 || msgElement.val().length < 10)
                    {
                        msgValid = false;
                        msgElement.addClass("ui-state-error");
                        errorMsgs.push("Length of  " + "<font color='#c00'>Message</font>"
                                + " must be between " + 10 + " and " + 145 + " characters.");
                    }
                    else {
                        msgValid = true;
                    }
                    if (!msgValid)
                    {
                        for (var i in errorMsgs)
                        {
                            tips.append(errorMsgs[i]);
                            if (errorMsgs.length > 0)
                                tips.append("<br>");
                        }
                    }
                    if (msgValid)
                    {
                        // convert moment object to time stamp
                        timeToSend = momObj.unix();
                        for (var i in arr)
                        {
                            arr[i].timeToSend = timeToSend;
                            arr[i].isGroupMessage = true;
                        }
                        var s = convert.stringifyObj(arr);
                        doAjaxPost(s, '../FullCalPost', "sendMessage" + "_" + msgToSend + "_" + "subject" + "_" + subject);
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
    });
}

function deactivateClient(arr) {

    $(function () {
        $("#clients-del-fieldset").dialog({
            autoOpen: false,
            modal: true,
            show: {
                duration: 200
            },
            buttons: {
                Cancel: function () {

                    $(this).dialog("close");
                },
                DeActivate: function () {
                    for (var i in arr)
                    {
                        arr[i].client.isAccountActive = false;
                    }
                    var s = convert.stringifyObj(arr);
                    doAjaxPost(s, '../FullCalPost', "deactivateClients");
                    $(this).dialog("close");
                }
            },
            close: function () {
            }
        });
    });
}

var renderData = {
    drawInfo1: function (o) {
        var opts = options.getMemberLevel(o);
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
                + "<tr>" + "<td title='mobile phone'>" + "mobile&#58;" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(o.client.mobilePhone) + " '>" + formatPhone(o.client.mobilePhone) + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='home phone number'>" + "home&#58;" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(o.client.homePhone) + " '>" + formatPhone(o.client.homePhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='work phone number'>" + "work&#58;" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(o.client.workPhone) + " '>" + formatPhone(o.client.workPhone) + "</td>" + "</tr>"
                + "<tr>" + "<td title='company'>" + "company&#58;" + "</td>" + "<td>" + o.client.company + "</td>" + "</tr>"
                + "<tr>" + "<td title='client address'>" + "address&#58;" + "</td>" + "<td>" + o.client.address + "</td>" + "</tr>"
                + "<tr>" + "<td title='city'>" + "city&#58;" + "</td>" + "<td>" + o.client.city + "</td>" + "</tr>"
                + "<tr>" + "<td title='client state'>" + "state&#58;" + "</td>" + "<td>" + o.client.state + "</td>" + "</tr>"
                + "<tr>" + "<td title='client zip code'>" + "zip&#58;" + "</td>" + "<td>" + o.client.zip + "</td>" + "</tr>"
                + "<tr>" + "<td title='member level'>" + "level&#58;" + "</td>" + "<td>"
                + "<select name='memberLevel' id='memberLevel'>" + opts + "</select>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='normal service'>" + "normal service&#58;" + "</td>" + "<td>" + o.client.serviceName + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate first name'>" + "associate&#58;" + "</td>" + "<td>" + o.associate2.firstName
                + '  <img src="../img/associates/' + associateImgId + '.png" alt="associate img" alt="img">' + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate email address'>" + "email&#58;" + "</td>" + "<td>" + "<a href='mailto:" + o.associate2.email + " '>" + o.associate2.email + "</a>" + "</td>" + "</tr>"
                + "<tr>" + "<td title='associate mobile phone'>" + "mobile&#58;" + "</td>" + "<td>" + "<a href='tel:" + formatPhone(o.associate2.mobilePhone) + " '>" + formatPhone(o.associate2.mobilePhone) + "</a>" + "</td>" + "</tr>";
        return c;
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
            $(e + " #email-ad-alerts").prop('checked', true);
        }
        else {
            $(e + " #email-ad-alerts").prop('checked', false);
        }
        if (o.client.smsAdAlerts === true) {
            $(e + " #sms-ad-alerts").prop('checked', true);
        }
        else {
            $(e + " #sms-ad-alerts").prop('checked', false);
        }
        if (o.client.emailApptAlerts === true) {
            $(e + " #email-appt-alerts").prop('checked', true);
        }
        else {
            $(e + " #email-appt-alerts").prop('checked', false);
        }
        if (o.client.smsApptAlerts === true) {
            $(e + " #sms-appt-alerts").prop('checked', true);
        }
        else {
            $(e + " #sms-appt-alerts").prop('checked', false);
        }
    }
}; // end renderData class functions

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
            var selClient = renderData.drawInfo1(o);
            renderData.alertPrefs(o, ".clientInfo #accordion");
            var lastFiveEvents = serviceList.getCalendarEvents(o.client.id, 5);
            var lastVisitsTable = eventsRendering.drawEvents1(lastFiveEvents, "past");
            var futureEvents = serviceList.getFutureEvents(o.client.id, 5);
            var futureEventsTable = eventsRendering.drawEvents1(futureEvents, "future");
            $(".clientInfo .last-visits-table tbody").html(lastVisitsTable);
            $(".clientInfo .future-visits-table tbody").html(futureEventsTable);
            var clientInfo = $(".clientInfo");
            openUserInfo(o);
            clientInfo.dialog("option", "title", "Client Info");
            clientInfo.dialog("open");
            clientInfo.dialog("option", "minHeight", 400);
            clientInfo.dialog("option", "minWidth", 370);
            clientInfo.dialog("option", "maxWidth", 580);
            $(".clientInfo #accordion").show();
            $(".eventInfoTable tbody").html(selClient);
        }
    });
}); // end search client  function
