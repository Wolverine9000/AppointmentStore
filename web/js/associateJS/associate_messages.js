/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var sortBy; // client-side sort order
var startAtMsg = 0, limitMsgs = 20;
var orderBy = "DESC"; // this order by is done on the server-side

// settings tabs
$(function () {
    $("#message-tabs").tabs();
    $("#message-tabs ul li:nth-child(3)").on("click", function () {
        messageListing = messagesList.getNextMsgs(startAtMsg, limitMsgs, orderBy, associateInfo, "../Messages", false);
//        recentInviteRequests = messagesList.getInviteRequests(associateInfo, 10);
        renderMessages(messageListing);
//        renderMsgInvites(recentInviteRequests);
    });
});
$(document).on("click", "#senttext .next-page, #senttext .prev-page, #senttext .first-page", this, function (evt) {
    var target = $(evt.target);
    if (target.is('#senttext .next-page'))
    {
        startAtMsg = startAtMsg + 20;
    }
    else if (target.is('#senttext .prev-page'))
    {
        startAtMsg = startAtMsg - 20;
    }
    else if (target.is('#senttext .first-page'))
    {
        startAtMsg = 0;
    }
    if (startAtMsg < 0)
    {
        evt.preventDefault();
        startAtMsg = 0;
        limitMsgs = 20;
        return;
    }
    messageListing = messagesList.getNextMsgs(startAtMsg, limitMsgs, orderBy, associateInfo, "../Messages", false)
    renderMessages(messageListing, sortBy);
});

$(function () {
    var messageInfo = $("#message-info"),
            scheduledInfo = $("#message-tabs #scheduled #scheduled-info");
    associateInfo = associateClass.getCurrentAssociateInfo(); // get current Associate info
    messageListing = messagesList.getNextMsgs(startAtMsg, limitMsgs, orderBy, associateInfo, "../Messages", false);
    recentInviteRequests = messagesList.getInviteRequests(associateInfo, 10);
    renderMessages(messageListing);
    renderMsgInvites(recentInviteRequests);
    $(document).on("click", "#senttext .message-list-table tbody tr", this, function (evt) {
        var message = $("#messageObj", this).val();
        var msgObj = $.parseJSON(message);
        var msgInfoTable = messagesClass.renderMsgInfo(msgObj);
        openMessage(msgObj);
        messageInfo.dialog("option", "title", "Message Info");
        messageInfo.dialog("open");
        messageInfo.dialog("option", "minHeight", 400);
        messageInfo.dialog("option", "Height", 679);
        messageInfo.dialog("option", "width", 450);
        messageInfo.dialog("option", "minWidth", 400);
        messageInfo.dialog("option", "maxWidth", 700);
        $(".messgage-info-table tbody").html(msgInfoTable);
    }); // end (document).on("click", "#senttext .message-list-table tbody tr

    $(document).on("click", "#scheduled .scheduled-list-table tbody tr", this, function (evt) {
        var message = $("#messageObj", this).val();
        var msgObj = $.parseJSON(message);
        var scheduleInfoTable = messagesClass.renderMsgInfo(msgObj);
        openSchedule(msgObj);
        scheduledInfo.dialog("option", "title", "Schedule Message Info");
        scheduledInfo.dialog("open");
        scheduledInfo.dialog("option", "minHeight", 400);
        scheduledInfo.dialog("option", "Height", 679);
        scheduledInfo.dialog("option", "width", 450);
        scheduledInfo.dialog("option", "minWidth", 400);
        scheduledInfo.dialog("option", "maxWidth", 700);
        $(".scheduled-info-table tbody").html(scheduleInfoTable);
    }); // end on click #scheduled .scheduled-list-table tbody tr

}); // end function

$(document).ready(function () {
    var sendInviteWindow = $("#fieldset-send-invite");
    $("#new-client-request").on("click", ".invite", this, function () {
        var phone1 = $("#new-client-request-fieldset-1 #cell-number-1"),
                phone2 = $("#new-client-request-fieldset-1 #cell-number-2"),
                phone3 = $("#new-client-request-fieldset-1 #cell-number-3"),
                phone4 = $("#new-client-request-fieldset-1 #cell-number-4");
        var tips = $("#new-client-request-fieldset-1 .validateTips");
        var allFields = $([]).add(phone1).add(phone2).add(phone3).add(phone4);
        tips.html("");
        allFields.removeClass("ui-state-error");
        var bValid = true;
        var phoneArray = []; // clear phone number array
        // loop through each phone input element, then get and validate each phone number entry
        $('#new-client-request-fieldset-1  input').each(function (index, value) {
            if ($(this).val() !== "")
            {
                bValid = bValid && validateForm.checkRegexp($(this), phoneRegEx, "Mobile Phone  must be a 10-digit number", tips);
                phoneArray.push($(this).val());
            }
        });
        if (phoneArray.length === 0)
        {
            alert("No phone numbers entered!");
            bValid = false;
            return;
        }

        if (bValid === true)
        {
            var fmtPhArr = [];
            var phoneArray2 = [];
            for (var i in phoneArray)
            {
                // format phone numbers(s)
                fmtPhArr.push(formatPhone(phoneArray[i]));
            }
            // remove duplicates
            phoneArray2 = eliminateDuplicates(fmtPhArr);

            // new object to add phone list array
            var inviteRequestCheck = new Object();
            // get current date and time in unix timestamp
            var now = moment().unix();
            // add data to  object
            inviteRequestCheck.phoneNumbers = phoneArray2;
            inviteRequestCheck.associateId = associateInfo.id;
            // check to see if number already client of associate
            var smsObjArray = [];
            for (var i in phoneArray2)
            {
                smsObjArray = isClientOfAssociate(inviteRequestCheck);
            }

            var msgInviteTable = messagesClass.renderInviteWindow(smsObjArray);
            var tableHeader = "<th>" + "Mobile Number" + "</th>" + "<th>" + "Send" + "</th>";
            openInviteWindow(phoneArray);
            sendInviteWindow.dialog("option", "title", "Confirm Info");
            sendInviteWindow.dialog("open");
            sendInviteWindow.dialog("option", "minHeight", 400);
            sendInviteWindow.dialog("option", "Height", 679);
            sendInviteWindow.dialog("option", "width", 400);
            sendInviteWindow.dialog("option", "minWidth", 350);
            sendInviteWindow.dialog("option", "maxWidth", 500);
            $("#table-send-invite thead").html(tableHeader);
            $("#table-send-invite tbody").html(msgInviteTable);
        }
    });
});

var renderMessages = function (msgArr, sortBy) {
    var messageTable = $("#senttext .message-list-table tbody");
    var scheduledTable = $("#scheduled .scheduled-list-table tbody");
    var scheduledArray = [];
    messageTable.html("");
    scheduledTable.html("");
    switch (sortBy)
    {
        case "id":
            msgArr = sortMessages.byID(msgArr);
            break;
        case "name":
            msgArr = sortMessages.byName(msgArr);
            break;
        case "message":
            msgArr = sortMessages.byMessage(msgArr);
            break;
        case "sms_id":
            msgArr = sortMessages.bySmsId(msgArr);
            break;
        case "sent on":
            msgArr = sortMessages.bySentDate(msgArr);
            break;
        case "status":
            msgArr = sortMessages.byStatus(msgArr);
            break;
        default:
            msgArr = sortMessages.byID(msgArr);
            break;
    }
    var nowMom = moment();
    var timeToSendMom;
    for (var i = 0; i < msgArr.length; i++)
    {
        if (msgArr[i].status === "Success")
        {
            msgArr[i].status = "Sent";
        }
        var timeToSend;
        timeToSendMom = moment(msgArr[i].timeToSend, dateTimeFmts);
        if (msgArr[i].timeToSend === undefined)
        {
            timeToSend = moment(msgArr[i].timeToSend, "MMM D, YYYY h:mm:SS A").format("YYYY-MM-DD HH:mm");
        }
        else
        {
            timeToSend = moment(msgArr[i].timeToSend, "MM-DD-YYYY h:mm A").format("YYYY-MM-DD HH:mm");
        }

        if (msgArr[i].status === "Sent" && timeToSendMom.isAfter(nowMom))
        {
            msgArr[i].statusColor = "#E59866";
            msgArr[i].status = "Scheduled";
            scheduledArray.push(msgArr[i]);
        }

        messageTable.append(
                "<tr>"
                + "<td>" + "<input type='hidden' name='messageObj' id='messageObj' value='" + JSON.stringify(msgArr[i]) + "'>" + msgArr[i].messageId + "</td>"
                + "<td>" + msgArr[i].client.firstName + " " + msgArr[i].client.lastName + "</td>"
                + "<td>" + msgArr[i].message + "</td>"
                + "<td>" + msgArr[i].idMessageResponse + "</td>"
                + "<td>" + timeToSend + "</td>"
                + "<td style='color:" + msgArr[i].statusColor + "'>" + msgArr[i].status + "</td>"
                + "</tr>");
    }
    if (scheduledArray.length === 0)
    {
        scheduledTable.html("<h2 style='text-align:center'>" + "&#42;&#42;&#42;&#42;&#32;" + "nothing scheduled" + "&#32;&#42;&#42;&#42;&#42;" + "</h2>");
    }
    for (var i = 0; i < scheduledArray.length; i++)
    {
        timeToSend = moment(scheduledArray[i].timeToSend, "MM-DD-YYYY h:mm A").format("MM-DD-YYYY hh:mm A");
        scheduledTable.append(
                "<tr>"
                + "<td>" + "<input type='hidden' name='messageObj' id='messageObj' value='" + JSON.stringify(scheduledArray[i]) + "'>" + scheduledArray[i].messageId + "</td>"
                + "<td>" + scheduledArray[i].client.firstName + " " + scheduledArray[i].client.lastName + "</td>"
                + "<td>" + scheduledArray[i].message + "</td>"
                + "<td>" + timeToSend + "</td>"
                + "<td style='color:#ff9900'>" + scheduledArray[i].status + "</td>"
                + "</tr>");
    }
}; // end renderMessages

var renderMsgInvites = function (msgArr) {
    msgArr.reverse();
    var msgInviteTable = $("#recent-requests #table-recent-requests tbody");
    msgInviteTable.html("");
    for (var i = 0; i < 10; i++)
    {
        msgInviteTable.append(
                "<tr>"
                + "<td>" + "<input type='hidden' name='messageObj' id='messageObj' value='" + JSON.stringify(msgArr[i]) + "'>" + msgArr[i].phoneNumber + "</td>"
                + "<td style='color:" + msgArr[i].statusColor + "'>" + msgArr[i].status + "</td>"
                + "<td>" + jsDate.formatDateTime2(msgArr[i].timeToSend) + "</td>"
                + "</tr>");
    }
}; // end renderMsgInvites

var messagesClass = {
    renderMsgInfo: function (msgObj) {
        var msgInfoTable,
                timeToSendTableRow,
                statusTableRow;
        var mir;
        if (msgObj)
            if (msgObj.isMessageInvite === true)
            {
                var msgObjStr = convert.stringify(msgObj);
                mir = messagesList.selectMsgInviteRequest(associateInfo, msgObjStr);
                statusTableRow = "<tr>" + "<td>" + "response&#58;" + "</td>" + "<td style='color:" + mir.statusColor + "'>" + mir.status + "</td>" + "</tr>";
                msgObj.client.id = "n/a";
            }
            else
            {
                statusTableRow = "<tr>" + "<td>" + "status&#58;" + "</td>" + "<td style='color:" + msgObj.statusColor + "'>" + msgObj.status + "</td>" + "</tr>";
            }
        if (msgObj.status === "Scheduled")
        {
            timeToSendTableRow = "<tr>" + "<td>" + "will send on&#58;" + "</td>" + "<td>" + jsDate.formatDateTime3(msgObj.timeToSend) + "</td>" + "</tr>";
        }
        else
        {
            timeToSendTableRow = "<tr>" + "<td>" + "sent&#58;" + "</td>" + "<td>" + jsDate.formatDateTime3(msgObj.timeToSend) + "</td>" + "</tr>"
        }

        msgInfoTable = "<tr>" + "<td>" + "<input type='hidden' name='messageObj' id='messageObj' value='" + JSON.stringify(msgObj) + "'>" + "message&#35&#58;" + "</td>" + "<td>" + msgObj.messageId + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "sent to&#58;" + "</td>" + "<td>" + msgObj.client.firstName + " " + msgObj.client.lastName + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "subject&#58;" + "</td>" + "<td>" + msgObj.subject + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "message&#58;" + "</td>" + "<td>" + msgObj.message + "</td>" + "</tr>"
                + timeToSendTableRow
                + "<tr>" + "<td>" + "processed&#58;" + "</td>" + "<td>" + jsDate.formatDateTime4(msgObj.timestamp) + "</td>" + "</tr>"
                + statusTableRow
                + "<tr>" + "<td>" + "sms&#35&#58;" + "</td>" + "<td>" + msgObj.idMessageResponse + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "phone&#35&#58;" + "</td>" + "<td>" + formatPhone(msgObj.client.mobilePhone) + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "client&#35&#58;" + "</td>" + "<td>" + msgObj.client.id + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "code&#58;" + "</td>" + "<td>" + msgObj.code + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "sent by&#58;" + "</td>" + "<td>" + msgObj.associate2.firstName + " " + msgObj.associate2.lastName + "</td>" + "</tr>";
        return msgInfoTable;
    },
    renderInviteWindow: function (smsObjArray) {
        var inviteTable = [];
        for (var i in smsObjArray)
        {
            if (smsObjArray[i].isClientOfAssociate === true)
            {
                inviteTable.push(
                        "<tr>"
                        + "<td colspan='2' class='smallText error'>" + "already a client " + "&#45; check send box to send anyway&#46;" + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td class='error'>" + "<input type='hidden' name='message-obj' id='message-obj'>"
                        + formatPhone(smsObjArray[i].client.mobilePhone) + " " + smsObjArray[i].client.firstName + " " + smsObjArray[i].client.lastName + "</td>"
                        + "<td>" + " <input id='send-checked' name='send-checked' type='checkbox' value='" + JSON.stringify(smsObjArray[i]) + "'>" + "</td>"
                        + "</tr>");
            }
            else
            {
                inviteTable.push("<tr>" + "<td>" + "<input type='hidden' name='message-obj' id='message-obj' value='" + JSON.stringify(smsObjArray[i]) + "'>"
                        + formatPhone(smsObjArray[i].client.mobilePhone) + "</td>"
                        + "<td>" + " <input id='send-checked'name='send-checked' type='checkbox' checked  value='" + JSON.stringify(smsObjArray[i]) + "'>" + "</td>"
                        + "</tr>");
            }
        }
        return inviteTable;
    }
};
// open message dialog box
function openMessage(msgObj) {

    $("#message-tabs #senttext #message-info").dialog({
        autoOpen: false,
        dialogClass: "no-close",
        modal: true,
        show: {
        },
        buttons: {
            OK: function () {
                $(this).dialog("close");
            }
        },
        close: function () {
        }
    });
} // end openMessage function

function openInviteWindow(msgObj) {
    var phone1 = $("#new-client-request-fieldset-1 #cell-number-1"),
            phone2 = $("#new-client-request-fieldset-1 #cell-number-2"),
            phone3 = $("#new-client-request-fieldset-1 #cell-number-3"),
            phone4 = $("#new-client-request-fieldset-1 #cell-number-4"),
            phone5 = $("#new-client-request-fieldset-1 #cell-number-5"),
            phone6 = $("#new-client-request-fieldset-1 #cell-number-6"),
            phone7 = $("#new-client-request-fieldset-1 #cell-number-7"),
            phone8 = $("#new-client-request-fieldset-1 #cell-number-8");
    var tips = $("#new-client-request-fieldset-1 .validateTips");
    var phoneElements = $([]).add(phone1).add(phone2).add(phone3).add(phone4).add(phone5)
            .add(phone6).add(phone7).add(phone8);

    $("#message-tabs #new-client-request #fieldset-send-invite").dialog({
        autoOpen: false,
        dialogClass: "no-close",
        modal: true,
        show: {
            duration: 200
        },
        buttons: {
            Submit: function () {
                var numbersToSend = [];
                // new object to add phone list array
                var inviteRequest = new Object();
                // get current date and time in unix timestamp
                var now = moment().unix();
                var phoneList = 0;
                phoneList = $('[name="send-checked"]:checked').length;
                if (phoneList > 0)
                {
                    $(":input:checked").each(function (index, value) {
                        var phone = $(this).val();
                        var phoneObj = $.parseJSON(phone);
                        numbersToSend.push(phoneObj.client.mobilePhone);

                    });
                    // add data to json object
                    inviteRequest.phoneNumbers = numbersToSend;
                    inviteRequest.stampToSend = now;
                    inviteRequest.isMessageInvite = true;
                    inviteRequest.associateId = associateInfo.id;
                    var r = JSON.stringify(inviteRequest);
                    // post data to server
                    doAjaxPost(r, '../FullCalPost ', 'invite', true);

                    // update recent-requets table by adding requested phone numbers
                    for (var i in numbersToSend)
                    {
                        var x = new Object();
                        var day = moment.unix(now);
                        x.phoneNumber = numbersToSend[i];
                        x.timeToSend = day;
                        x.status = "Pending";
                        x.isMessageInvite = true;
                        x.statusColor = "#ff9900";
                        var y = new SMSMessage(x);
                        recentInviteRequests.push(y);
                    }

                    renderMsgInvites(recentInviteRequests);

                    phoneElements.val("").removeClass("ui-state-error");
                    tips.html("");

                    $(phone1).focus();
                    $(phone2).focus();
                    $(phone3).focus();
                    $(phone4).focus();
                    $(phone5).focus();
                    $(phone6).focus();
                    $(phone7).focus();
                    $(phone8).focus();
                }

                $(this).dialog("close");
            },
            Cancel: function () {
                $(this).dialog("close");
            }
        },
        close: function () {
        }
    });
} // end openMessage function

function openSchedule(msgObj) {
    var openDelSchedule = $("#confirmDelete");
    $("#message-tabs #scheduled #scheduled-info").dialog({
        autoOpen: false,
        dialogClass: "no-close",
        modal: true,
        show: {
        },
        buttons: {
            OK: function () {
                $(this).dialog("close");
            },
            Edit: function () {
                $(this).dialog("close");
            },
            Delete: function () {
                var confirmDelTable = messagesClass.renderMsgInfo(msgObj);
                openConfirmDelSchedule(msgObj);
                openDelSchedule.dialog("option", "title", "Confirm");
                openDelSchedule.dialog("open");
                openDelSchedule.dialog("option", "minHeight", 400);
                openDelSchedule.dialog("option", "Height", 679);
                openDelSchedule.dialog("option", "width", 450);
                openDelSchedule.dialog("option", "minWidth", 400);
                openDelSchedule.dialog("option", "maxWidth", 700);
                $(".confirm-del-table tbody").html(confirmDelTable);
                $(this).dialog("close");
            }
        },
        close: function () {
        }
    });
} // end openMessage function

function openConfirmDelSchedule(msgObj) {
    $("#confirmDelete").dialog({
        autoOpen: false,
        dialogClass: "no-close",
        modal: true,
        show: {
        },
        buttons: {
            Delete: function () {
                var msgArray = [];
                msgArray.push(msgObj);
                var msg = JSON.stringify(msgArray);
                $.ajax({
                    url: "../sms",
                    type: 'post',
                    async: true,
                    beforeSend: function () {
                        $("#postDataError").html("");
                        openLoading();
                        $("#loading").dialog("open"); // open loading element
                        $("#loading h4").html("Processing request.  Please wait...");
                        spinner('loading', 'auto');
                    },
                    data: {data: msg, action: "delete"},
                    error: function (xhr, status, error) {
                        $('#loading').data('spinner').stop(); // Stop the spinner
                        $("#loading h4").html("ERROR!");
                        $("#loading").dialog("close"); // close loading element
                    },
                    success: function () {
                        $('#loading').data('spinner').stop(); // Stop the spinner
                        $("#loading h4").html("Done!");
                        $("#loading").dialog("close"); // close loading element
                    }
                });
                $(this).dialog("close");
            },
            Cancel: function () {
                $(this).dialog("close");
            }
        },
        close: function () {
        }
    });
}

// sort by column functions
var sortMessages = {
    byID: function (msgArray, isDescending) {
        msgArray["sortArrayM"] = "";
        msgArray["sortArrayN"] = "";
        msgArray["sortArrayD"] = "";
        msgArray["sortArraySmsID"] = "";
        msgArray["sortArrayS"] = "";
        var currentSort = msgArray["sortArrayID"];
        if (typeof currentSort !== 'boolean')
        {
            msgArray.sort(function (a, b) {
                if (a.idMessageResponse < b.idMessageResponse)
                {
                    return 1;
                }
                if (a.idMessageResponse > b.idMessageResponse)
                {
                    return -1;
                }
// a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isDescending)
        {
            msgArray.reverse();
        }
// set the sort order
        msgArray["sortArrayID"] = isDescending ? true : false;
        return msgArray;
    },
    byName: function (msgArray, isAscending) {
        msgArray["sortArrayID"] = "";
        msgArray["sortArrayM"] = "";
        msgArray["sortArrayD"] = "";
        msgArray["sortArraySmsID"] = "";
        msgArray["sortArrayS"] = "";
        var currentSort = msgArray["sortArrayN"];
        if (typeof currentSort !== 'boolean')
        {
            msgArray.sort(function (a, b) {
                if (a.client.firstName.toLowerCase() > b.client.firstName.toLowerCase())
                {
                    return 1;
                }
                if (a.client.firstName.toLowerCase() < b.client.firstName.toLowerCase())
                {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            msgArray.reverse();
        }
        // set the sort order
        msgArray["sortArrayN"] = isAscending ? true : false;
        return msgArray;
    },
    byMessage: function (msgArray, isAscending) {
        msgArray["sortArrayID"] = "";
        msgArray["sortArrayN"] = "";
        msgArray["sortArrayD"] = "";
        msgArray["sortArraySmsID"] = "";
        msgArray["sortArrayS"] = "";
        var currentSort = msgArray["sortArrayM"];
        if (typeof currentSort !== 'boolean')
        {
            msgArray.sort(function (a, b) {
                if (a.message.toLowerCase() > b.message.toLowerCase())
                {
                    return 1;
                }
                if (a.message.toLowerCase() < b.message.toLowerCase())
                {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            msgArray.reverse();
        }
        // set the sort order
        msgArray["sortArrayM"] = isAscending ? true : false;
        return msgArray;
    },
    bySmsId: function (msgArray, isDescending) {
        msgArray["sortArrayID"] = "";
        msgArray["sortArrayN"] = "";
        msgArray["sortArrayD"] = "";
        msgArray["sortArrayM"] = "";
        msgArray["sortArrayS"] = "";
        var currentSort = msgArray["sortArraySmsID"];
        if (typeof currentSort !== 'boolean')
        {
            msgArray.sort(function (a, b) {
                if (a.messageId > b.messageId)
                {
                    return 1;
                }
                if (a.messageId < b.messageId)
                {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isDescending)
        {
            msgArray.reverse();
        }
        // set the sort order
        msgArray["sortArraySmsID"] = isDescending ? true : false;
        return msgArray;
    },
    bySentDate: function (msgArray, isAscending) {
        msgArray["sortArrayID"] = "";
        msgArray["sortArrayN"] = "";
        msgArray["sortArrayM"] = "";
        msgArray["sortArraySmsID"] = "";
        msgArray["sortArrayS"] = "";
        var currentSort = msgArray["sortArrayD"];
        if (typeof currentSort !== 'boolean')
        {
            msgArray.sort(function (a, b) {
                a = moment(a.timeToSend, "MM-DD-YYYY hh:mm A"); // convert to Moment objects for  date comparison
                b = moment(b.timeToSend, "MM-DD-YYYY hh:mm A");
                if (a < b)
                {
                    return 1;
                }
                if (a > b)
                {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            msgArray.reverse();
        }
        // set the sort order
        msgArray["sortArrayD"] = isAscending ? true : false;
        return msgArray;
    },
    byStatus: function (msgArray, isAscending) {
        msgArray["sortArrayID"] = "";
        msgArray["sortArrayN"] = "";
        msgArray["sortArrayM"] = "";
        msgArray["sortArrayD"] = "";
        msgArray["sortArraySmsID"] = "";
        var currentSort = msgArray["sortArrayS"];
        if (typeof currentSort !== 'boolean')
        {
            msgArray.sort(function (a, b) {
                if (a.status > b.status)
                {
                    return 1;
                }
                if (a.status < b.status)
                {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            msgArray.reverse();
        }
        // set the sort order
        msgArray["sortArrayS"] = isAscending ? true : false;
        return msgArray;
    }
}; // end sortServices functions

// sort when column name is clicked
$(document).on("click", ".message-list-table  th:first-child", function (evt) {
    sortBy = "id";
    renderMessages(messageListing, sortBy);
});
$(document).on("click", ".message-list-table  th:nth-child(2)", function (evt) {
    sortBy = "name";
    renderMessages(messageListing, "sortBy");
});
$(document).on("click", ".message-list-table  th:nth-child(3)", function (evt) {
    sortBy = "message";
    renderMessages(messageListing, "message");
});
$(document).on("click", ".message-list-table  th:nth-child(4)", function (evt) {
    sortBy =
            renderMessages(messageListing, "sms_id");
});
$(document).on("click", ".message-list-table  th:nth-child(5)", function (evt) {
    renderMessages(messageListing, "sent on");
});
$(document).on("click", ".message-list-table  th:nth-child(6)", function (evt) {
    renderMessages(messageListing, "status");
});

function eliminateDuplicates(arr) {
    var i,
            len = arr.length,
            out = [],
            obj = {};

    for (i = 0; i < len; i++)
    {
        obj[arr[i]] = 0;
    }
    for (i in obj)
    {
        out.push(i);
    }
    return out;
}
