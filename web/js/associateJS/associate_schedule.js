/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



// global clear time(s) variable
var clearTime = "-------"; // clear time variable
var validationQSError = "Please provide valid entries for the following field(s):<br>";
var active;

var countChecked = function () {
    var n = $(".quickSchedule:checked").length;
    return n;
};

$(function () {
    $("#schedulertabs").tabs();
    var currentTabVal = $("#reloadTab").val();
    $("#schedulertabs").tabs("option", "active", currentTabVal);

    // populate date entry placeholders wtth today's date
    var todaysDate = $.datepicker.formatDate("mm/dd/yy", new Date());
    $('#section1  input').attr("placeholder", todaysDate);
});

// quick global main schedule settings
$(document).ready(function () {
    $("#globalMain").on("click", function () {
        var n = $(".tableInput:checked").val();
        var gloMT1 = $(".gloMT1").val();
        var gloMT2 = $(".gloMT2").val();
        var gloAT1 = $(".gloAT1").val();
        var gloAT2 = $(".gloAT2").val();
        // global morning and afternoon time arrays
        var globalMT1Array = [".sunMT1", ".monMT1", ".tueMT1", ".wedMT1", ".thuMT1", ".friMT1", ".satMT1"];
        var globalMT2Array = [".sunMT2", ".monMT2", ".tueMT2", ".wedMT2", ".thuMT2", ".friMT2", ".satMT2"];
        var globalAT1Array = [".sunAT1", ".monAT1", ".tueAT1", ".wedAT1", ".thuAT1", ".friAT1", ".satAT1"];
        var globalAT2Array = [".sunAT2", ".monAT2", ".tueAT2", ".wedAT2", ".thuAT2", ".friAT2", ".satAT2"];
        // global set days of the week
        var daysOfWeekArray = ["#sun", "#mon", "#tue", "#wed", "#thu", "#fri", "#sat"];
        // reset all time values
//        for (i = 0; i < 7; i++)
//        {
//            $(globalMT1Array[i]).val(clearTime);
//            $(globalMT2Array[i]).val(clearTime);
//            $(globalAT1Array[i]).val(clearTime);
//            $(globalAT2Array[i]).val(clearTime);
//        }

        switch (n) {
            case "everyday":
                for (var i = 0; i < 7; i++)
                {
                    $(daysOfWeekArray[i]).each(function () {
                        this.checked = true;
                    });
                    // set times
                    $(globalMT1Array[i]).val(gloMT1);
                    $(globalMT2Array[i]).val(gloMT2);
                    $(globalAT1Array[i]).val(gloAT1);
                    $(globalAT2Array[i]).val(gloAT2);
                }
                break;
            case "weekdays":
                for (var i = 0; i < 7; i++)
                {
                    if (i === 1 || i === 2 || i === 3 || i === 4 || i === 5)
                    {
                        $(daysOfWeekArray[i]).each(function () {
                            this.checked = true;
                        });
                        $(globalMT1Array[i]).val(gloMT1);
                        $(globalMT2Array[i]).val(gloMT2);
                        $(globalAT1Array[i]).val(gloAT1);
                        $(globalAT2Array[i]).val(gloAT2);
                    }
                    if (i === 0 || i === 6)
                    {
                        $(daysOfWeekArray[i]).each(function () {
                            this.checked = false;
                        });
                        $(globalMT1Array[i]).val(clearTime);
                        $(globalMT2Array[i]).val(clearTime);
                        $(globalAT1Array[i]).val(clearTime);
                        $(globalAT2Array[i]).val(clearTime);
                    }
                }
                break;
            case "weekends":
                for (var i = 0; i < 7; i++)
                {
                    if (i === 1 || i === 2 || i === 3 || i === 4 || i === 5)
                    {
                        $(daysOfWeekArray[i]).each(function () {
                            this.checked = false;
                        });
                        $(globalMT1Array[i]).val(clearTime);
                        $(globalMT2Array[i]).val(clearTime);
                        $(globalAT1Array[i]).val(clearTime);
                        $(globalAT2Array[i]).val(clearTime);
                    }
                    if (i === 0 || i === 6)
                    {
                        $(daysOfWeekArray[i]).each(function () {
                            this.checked = true;
                        });
                        $(globalMT1Array[i]).val(gloMT1);
                        $(globalMT2Array[i]).val(gloMT2);
                        $(globalAT1Array[i]).val(gloAT1);
                        $(globalAT2Array[i]).val(gloAT2);
                    }
                }
                break;
            case "tueToSat":
                for (var i = 0; i < 7; i++)
                {
                    if (i === 2 || i === 3 || i === 4 || i === 5 || i === 6)
                    {
                        $(daysOfWeekArray[i]).each(function () {
                            this.checked = true;
                        });
                        $(globalMT1Array[i]).val(gloMT1);
                        $(globalMT2Array[i]).val(gloMT2);
                        $(globalAT1Array[i]).val(gloAT1);
                        $(globalAT2Array[i]).val(gloAT2);
                    }
                    if (i === 0 || i === 1)
                    {
                        $(daysOfWeekArray[i]).each(function () {
                            this.checked = false;
                        });
                        $(globalMT1Array[i]).val(clearTime);
                        $(globalMT2Array[i]).val(clearTime);
                        $(globalAT1Array[i]).val(clearTime);
                        $(globalAT2Array[i]).val(clearTime);
                    }
                }
                break;
        }
    }); // end global main function document ready
// quick schedule validation and submission  
    $(".quickScheduleRequest").submit(
            function (event) {
                $("#validationQSError").html("");
                $("#quickScheduleCancel").html("");
                $("#quickScheduleMessages").html("");
                var quickScheduleMsg = $("#quickScheduleMessages");
                var isValid = true;
                var date = $("#datepicker").val();
                var qsStart = $("#qsStart").val();
                var qsEnd = $("#qsEnd").val();
                var qsFrequency = $("#qsFrequency").val();
                var qsLength = $("#qsLength").val();
                var checkboxArray = [];
                var isTimeValid = jsTime.validateTime(qsStart, qsEnd);
                var isDateValid = jsDate.validateDate(date);
                var isLessOneYr = jsDate.validateOneYear(date);
                $(".quickSchedule:checked").each(function () {
                    checkboxArray.push($(this).val()); //push each checkbox value into the array
                });
                var checkedBoxes = countChecked(); // get the number of checkboxes checked

                if (checkedBoxes === 0 && qsLength !== "1 day") {
                    quickScheduleMsg.append("• <span>(<strong>days</strong>)&nbsp;&nbsp;Select at least one of the <strong>Day</strong> checkboxes from above.</span><br>");
                    isValid = false;
                }

                if (date === "" || isDateValid === false) {
                    quickScheduleMsg.append("• <span>(<strong>start date</strong>)&nbsp;&nbsp;Please enter a valid <strong>start date</strong>. example:&nbsp;<strong>" + jsDate.todaysDate() + "</strong>&nbsp;and\n\
                    <strong>date is today or greater.</strong></span><br>");
                    // move focus to first text box
                    $("#datepicker").focus();
                    isValid = false;
                }

                if (isLessOneYr === false) {
                    quickScheduleMsg.append("• <span>(<strong>start date</strong>)&nbsp;&nbsp;Start Date must be less than one year from today's date.</span><br>");
                    // move focus to first text box
                    $("#datepicker").focus();
                    isValid = false;
                }

                if (qsStart === clearTime && qsEnd === clearTime || isTimeValid === false) {
                    quickScheduleMsg.append("• <span>(<strong>time in &AMP; time out</strong>)&nbsp;&nbsp;Make sure the selected&nbsp;<strong>time out</strong>\n\
                        is greater than the selected&nbsp;<strong>time  in</strong>.</span><br>");
                    $("#qsStart").focus();
                    isValid = false;
                }

                if (qsStart === clearTime && qsEnd !== clearTime) {
                    quickScheduleMsg.append("• <span>(<strong>time in</strong>)&nbsp;&nbsp;Please select a <strong>time in</strong>.</span><br>");
                    $("#qsStart").focus();
                    isValid = false;
                }

                if (qsStart !== clearTime && qsEnd === clearTime) {
                    quickScheduleMsg.append("• <span>(<strong>time out</strong>)&nbsp;&nbsp;Please select a <strong>time out</strong>.</span><br>");
                    $("#qsEnd").focus();
                    isValid = false;
                }

                if (isValid === false) {
                    $("#validationQSError").html(validationQSError);
                    fadeInMessage("#messagesQS");
                    // prevent the submission of the form if any entries are invalid 
                    event.preventDefault();
                }

                else {
                    var dateNew = new Date(date); // create date object from date string
                    event.preventDefault();
                    confirmMessage("#confirmSchedule", ".quickScheduleRequest", quickScheduleMsg,
                            "#messagesQS", "Quick Schedule Request Cancelled.<br>");
                    var clearQSInfo =
                            "<tr>" + "<td>" + "starting:" + "</td>" + "<td>" + jsDate.formatDate(date) + "</td>" + "</tr>"
                            + "<tr>" + "<td>" + "for:" + "</td>" + "<td>" + qsLength + "</td>" + "</tr>"
                            + "<tr>" + "<td>" + "time in:" + "</td>" + "<td>" + qsStart + "</td>" + "</tr>"
                            + "<tr>" + "<td>" + "time out:" + "</td>" + "<td>" + qsEnd + "</td>" + "</tr>"
                            + "<tr>" + "<td>" + "every:" + "</td>" + "<td>" + qsFrequency + " minutes" + "</td>" + "</tr>"
                            + "<tr>" + "<td>" + "day(s):" + "</td>" + "<td>" + checkboxArray.join(", ") + "</td>" + "</tr>";
                    $("#confirmSchedule").dialog("open");
                    $("#confirmSchedule").dialog("option", "minHeight", 250); // set min height
                    $("#confirmSchedule .eventHeader").html("Submit request to accept appointments");
                    $("#confirmSchedule tbody").html(clearQSInfo);
                }
            } // end function
    ); // end quick schedule submit
    $("#clearSchedule").submit("click", function (event) {
        $("#clearScheduleMessages").html("");
        $("#validationErrorClr").html("");
        var clearScheduleMsg = $("#clearScheduleMessages");
        var clrCancelledMsg = "Clear Schedule Request Cancelled." + "<br>";
        var choice = $(".schedulerInput:checked").val();
        var datepicker3 = $("#datepicker3").val();
        var clearRangeTo = $("#to").val();
        var clearRangeFrom = $("#from").val();
        var isClrValid = true;
        var validationErrorClr = "Please provide valid entries for the following field(s):<br>";
        if (typeof choice === "undefined")
        {
            clearScheduleMsg.append("• <span>Please select a <strong>Clear Schedule Option</strong>.</span><br>");
            isClrValid = false;
        }
        else {
            switch (choice) {
                case "clearDate":
                    if (datepicker3 === "")
                    {
                        clearScheduleMsg.append("• <span>(<strong>clear date</strong>)&nbsp;&nbsp;Please select a <strong>date to clear</strong>.</span><br>");
                        $("#datepicker3").focus();
                        isClrValid = false;
                    }
                    else if (jsDate.validateDate(datepicker3) === false || jsDate.valSelectedDate(datepicker3) === false)
                    {
                        clearScheduleMsg.append("• <span>(<strong>clear date</strong>)&nbsp;&nbsp;Please select a valid <strong>date to clear</strong>.example:&nbsp;<strong>" + jsDate.todaysDate() + "</strong>&nbsp;and\n\
                    <strong>is today's date or greater.</strong></span><br>");
                        $("#datepicker3").focus();
                        isClrValid = false;
                    }
                    else if (datepicker3 !== "")
                    {
                        event.preventDefault();
                        confirmMessage("#confirmSchedule", "#clearSchedule", clearScheduleMsg,
                                "#messagesClear", clrCancelledMsg);
                        $("#confirmSchedule").dialog("open");
                        $("#confirmSchedule").dialog("option", "minHeight", 170); // set min height for dialog box

                        var clearDateInfo =
                                "<tr>" + "<td>" + "clear date:" + "</td>" + "<td>" + jsDate.formatDate(datepicker3) + "</td>" + "</tr>";
                        $("#confirmSchedule .eventHeader").html("Clear your appointments on one day");
                        $("#confirmSchedule tbody").html(clearDateInfo);
                    }
                    break;
                case "clearRange":
                    var clearRangeMessage = "&nbsp;date</strong>.&nbsp;example:&nbsp;<strong>" + jsDate.todaysDate() + "</strong>&nbsp;and is\n\
                    <strong>less than the selected to date.</strong></span><br>";
                    if (clearRangeTo === "" && clearRangeFrom !== "")
                    {
                        clearScheduleMsg.append("• <span>(<strong>from date</strong>)&nbsp;&nbsp;Please enter a valid <strong>to date</strong>.</span><br>");
                        $("#to").focus();
                        isClrValid = false;
                    }

                    else if (clearRangeTo !== "" && clearRangeFrom === "")
                    {
                        clearScheduleMsg.append("• <span>(<strong>from date</strong>)&nbsp;&nbsp;Please enter a valid <strong>from date</strong>.</span><br>");
                        $("#from").focus();
                        isClrValid = false;
                    }

                    else if (clearRangeFrom === "" && clearRangeTo === "")
                    {
                        clearScheduleMsg.append("• <span>(<strong>from date and to date</strong>)\n\
            &nbsp;&nbsp;Please select valid <strong>from date and to dates.</strong>.</span><br>");
                        $("#from").focus();
                        isClrValid = false;
                    }

                    else if (jsDate.validateDate(clearRangeTo) === false)
                    {
                        clearScheduleMsg.append("• <span>(<strong>clear date range</strong>)&nbsp;&nbsp;Please select a <strong>to" + clearRangeMessage);
                        $("#to").focus();
                        isClrValid = false;
                    }

                    else if (jsDate.validateDate(clearRangeFrom) === false)
                    {
                        clearScheduleMsg.append("• <span>(<strong>clear date range</strong>)&nbsp;&nbsp;Please select a <strong>from" + clearRangeMessage);
                        $("#from").focus();
                        isClrValid = false;
                    }
                    else if (jsDate.validateToFromDates(clearRangeTo, clearRangeFrom) === false)
                    {
                        clearScheduleMsg.append("• <span>(<strong>clear date range</strong>)&nbsp;&nbsp;Please select a <strong>from" + clearRangeMessage);
                        $("#from").focus();
                        isClrValid = false;
                    }
                    else if (clearRangeTo !== "" && clearRangeFrom !== "")
                    {
                        event.preventDefault();
                        confirmMessage("#confirmSchedule", "#clearSchedule", "#clearScheduleMessages",
                                "#messagesClear", clrCancelledMsg);
                        $("#confirmSchedule").dialog("open");
                        var clearInfo =
                                "<tr>" + "<td>" + "from: " + "</td>" + "<td>" + jsDate.formatDate(clearRangeFrom) + "</td>" + "</tr>"
                                + "<tr>" + "<td>" + "to: " + "</td>" + "<td>" + jsDate.formatDate(clearRangeTo) + "</td>" + "</tr>";
                        $("#confirmSchedule .eventHeader").html("Submit request to clear your schedule");
                        $("#confirmSchedule tbody").html(clearInfo);
                    }
                    break;
                case "clearAll":
                    event.preventDefault();
                    confirmMessage("#confirmSchedule", "#clearSchedule", "#clearScheduleMessages",
                            "#messagesClear", clrCancelledMsg);
                    $("#confirmSchedule").dialog("open");
                    $("#confirmSchedule .eventHeader").html("<span style='color: #c00;'>Are you sure you want to Clear your Enitre Schedule?</span>");
                    break;
            }
        }

        if (isClrValid === false) {
            $("#validationErrorClr").html(validationErrorClr);
            fadeInMessage("#messagesClear");
            // prevent the submission of the form if any entries are invalid 
            event.preventDefault();
        }
    }); // end clear schedule sumbit    
}); // document ready function

$(document).ready(function () {
    $(".mainSchedule").submit("click", function (event) {
        $("#validationMSmessages").html("");
        var validationMSmsg = $("#validationMSmessages");
        var validationMSError = "Please provide valid entries for the following field(s):<br>";
        var isValid = true; // set main error flag
        var dayCheckbox = []; // reset/create days of the week checkboxes
        // get values of the Day checkboxes checked
        $(".tableInputMain:checked").each(function () {
            dayCheckbox.push(this.value);
        }); // end tableInputMain checked
        // clear time errors array
        var timeErrors = [];
        // if no Day checkbox is selected
        if (dayCheckbox.length === 0) {
            validationMSmsg.append("• <span>(<strong>days</strong>)&nbsp;&nbsp;Select at least one of the <strong>Day</strong> checkboxes from above.</span><br>");
            isValid = false;
        }
// if at least one Day checkbox is selected - start for-loop to validate dates and times
        if (dayCheckbox !== 0) {
            var dayTimeError = false;
            // get Main Schedule from date and to date
            var fromN = $("#fromN").val();
            var toN = $("#toN").val();
            var frequencyN = $("#frequencyN").val();
            // set time error message variables
            var timeError1 = " time in and time out not selected.\n\
                        &nbsp;Please select&nbsp;<strong>time in</strong> and <strong>time out</strong>.&nbsp; Or uncheck Day checkbox.<br>";
            var timeError2 = "&nbsp;times not vaild<br>";
            var timeError3 = "<strong> time out</strong> )&nbsp;not vaild&nbsp;\n\
                        Select a valid time in or reset to '-------'.<br>";
            var timeError4 = "<strong> time in</strong> )&nbsp;not vaild&nbsp;\n\
                        Select a valid time in or reset to '-------'.<br>";
            // start for-loop to validate selected dates and times
            for (var i in dayCheckbox) {
// if Sunday checkbox is selected, validate Sunday dates and times
                if (dayCheckbox[i] === "sun") {
// get Sun time variables
                    var sunMT1 = $(".sunMT1").val();
                    var sunMT2 = $(".sunMT2").val();
                    var sunAT1 = $(".sunAT1").val();
                    var sunAT2 = $(".sunAT2").val();
                    var sunErrorFlag = false; // set sun error flag
                    // setup error variables
                    var sunM = "• ( <strong>Sun morning </strong>)";
                    var sunA = "• ( <strong>Sun afternoon </strong>)";
                    if (sunMT1 === clearTime || sunMT2 === clearTime) {
                        validationMSmsg.append(sunM);
                        validationMSmsg.append(timeError1);
                        sunErrorFlag = true;
                        isValid = false;
                    }
                    else if (jsTime.validateTime(sunMT1, sunMT2) === false) {
                        validationMSmsg.append(sunM);
                        validationMSmsg.append(timeError2);
                        sunErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (sunMT1 !== clearTime && sunMT2 !== clearTime && sunAT1 !== clearTime && sunAT2 === clearTime) {
                        validationMSmsg.append(sunA);
                        validationMSmsg.append(timeError3);
                        sunErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (sunMT1 !== clearTime && sunMT2 !== clearTime && sunAT1 === clearTime && sunAT2 !== clearTime) {
                        validationMSmsg.append(sunA);
                        validationMSmsg.append(timeError4);
                        sunErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (sunAT1 !== clearTime && sunAT2 !== clearTime) {
                        if (jsTime.validateTime(sunAT1, sunAT2) === false || jsTime.validateTime(sunMT2, sunAT1) === false) {
                            validationMSmsg.append(sunA);
                            validationMSmsg.append(timeError2);
                            sunErrorFlag = true;
                            isValid = false;
                            dayTimeError = true;
                        }
                    }
                    if (sunErrorFlag === true)
                    {
                        timeErrors.push("sun"); // add day name to the end of timeErrors array   
                    }
                }
// if Monday checkbox is selected, validate times
                if (dayCheckbox[i] === "mon") {
// get Mon time variables
                    var monMT1 = $(".monMT1").val();
                    var monMT2 = $(".monMT2").val();
                    var monAT1 = $(".monAT1").val();
                    var monAT2 = $(".monAT2").val();
                    var monErrorFlag = false; // set mon error flag
                    // setup error variables
                    var monM = "• ( <strong>Mon morning </strong>)";
                    var monA = "• ( <strong>Mon afternoon </strong>)";
                    if (monMT1 === clearTime || monMT2 === clearTime) {
                        validationMSmsg.append(monM);
                        validationMSmsg.append(timeError1);
                        monErrorFlag = true;
                        isValid = false;
                    }
                    else if (jsTime.validateTime(monMT1, monMT2) === false) {
                        validationMSmsg.append(monM);
                        validationMSmsg.append(timeError2);
                        monErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (monMT1 !== clearTime && monMT2 !== clearTime && monAT1 !== clearTime && monAT2 === clearTime) {
                        validationMSmsg.append(monA);
                        validationMSmsg.append(timeError3);
                        monErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (monMT1 !== clearTime && monMT2 !== clearTime && monAT1 === clearTime && monAT2 !== clearTime) {
                        validationMSmsg.append(monA);
                        validationMSmsg.append(timeError4);
                        monErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (monAT1 !== clearTime && monAT2 !== clearTime) {
                        if (jsTime.validateTime(monAT1, monAT2) === false || jsTime.validateTime(monMT2, monAT1) === false) {
                            validationMSmsg.append(monA);
                            validationMSmsg.append(timeError2);
                            monErrorFlag = true;
                            isValid = false;
                            dayTimeError = true;
                        }
                    }
                    if (monErrorFlag === true)
                    {
                        timeErrors.push("mon"); // add day name to the end of timeErrors array   
                    }
                }
// if Tuesday checkbox is selected, validate Tuesday dates and times
                if (dayCheckbox[i] === "tue") {
// get Tue time variables
                    var tueMT1 = $(".tueMT1").val();
                    var tueMT2 = $(".tueMT2").val();
                    var tueAT1 = $(".tueAT1").val();
                    var tueAT2 = $(".tueAT2").val();
                    var tueErrorFlag = false; // set tue error flag
                    // setup error variables
                    var tueM = "• ( <strong>Tue morning </strong>)";
                    var tueA = "• ( <strong>Tue afternoon </strong>)";
                    if (tueMT1 === clearTime || tueMT2 === clearTime) {
                        validationMSmsg.append(tueM);
                        validationMSmsg.append(timeError1);
                        tueErrorFlag = true;
                        isValid = false;
                    }
                    else if (jsTime.validateTime(tueMT1, tueMT2) === false) {
                        validationMSmsg.append(tueM);
                        validationMSmsg.append(timeError2);
                        tueErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (tueMT1 !== clearTime && tueMT2 !== clearTime && tueAT1 !== clearTime && tueAT2 === clearTime) {
                        validationMSmsg.append(tueA);
                        validationMSmsg.append(timeError3);
                        tueErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (tueMT1 !== clearTime && tueMT2 !== clearTime && tueAT1 === clearTime && tueAT2 !== clearTime) {
                        validationMSmsg.append(tueA);
                        validationMSmsg.append(timeError4);
                        tueErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (tueAT1 !== clearTime && tueAT2 !== clearTime) {
                        if (jsTime.validateTime(tueAT1, tueAT2) === false || jsTime.validateTime(tueMT2, tueAT1) === false) {
                            validationMSmsg.append(tueA);
                            validationMSmsg.append(timeError2);
                            tueErrorFlag = true;
                            isValid = false;
                            dayTimeError = true;
                        }
                    }
                    if (tueErrorFlag === true)
                    {
                        timeErrors.push("tue"); // add day name to the end of timeErrors array   
                    }
                }
// if Wednesday checkbox is selected, validate Wednesday dates and times
                if (dayCheckbox[i] === "wed") {
// get Wed time variables
                    var wedMT1 = $(".wedMT1").val();
                    var wedMT2 = $(".wedMT2").val();
                    var wedAT1 = $(".wedAT1").val();
                    var wedAT2 = $(".wedAT2").val();
                    var wedErrorFlag = false; // set wed error flag
                    // setup error variables
                    var wedM = "• ( <strong>Wed morning </strong>)";
                    var wedA = "• ( <strong>Wed afternoon </strong>)";
                    if (wedMT1 === clearTime || wedMT2 === clearTime) {
                        validationMSmsg.append(wedM);
                        validationMSmsg.append(timeError1);
                        wedErrorFlag = true;
                        isValid = false;
                    }
                    else if (jsTime.validateTime(wedMT1, wedMT2) === false) {
                        validationMSmsg.append(wedM);
                        validationMSmsg.append(timeError2);
                        wedErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (wedMT1 !== clearTime && wedMT2 !== clearTime && wedAT1 !== clearTime && wedAT2 === clearTime) {
                        validationMSmsg.append(wedA);
                        validationMSmsg.append(timeError3);
                        wedErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (wedMT1 !== clearTime && wedMT2 !== clearTime && wedAT1 === clearTime && wedAT2 !== clearTime) {
                        validationMSmsg.append(wedA);
                        validationMSmsg.append(timeError4);
                        wedErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (wedAT1 !== clearTime && wedAT2 !== clearTime) {
                        if (jsTime.validateTime(wedAT1, wedAT2) === false || jsTime.validateTime(wedMT2, wedAT1) === false) {
                            validationMSmsg.append(wedA);
                            validationMSmsg.append(timeError2);
                            wedErrorFlag = true;
                            isValid = false;
                            dayTimeError = true;
                        }
                    }
                    if (wedErrorFlag === true)
                    {
                        timeErrors.push("wed"); // add day name to the end of timeErrors array   
                    }
                }
// if Thursday checkbox is selected, validate Thursday dates and times
                if (dayCheckbox[i] === "thu") {
// get Thu time variables
                    var thuMT1 = $(".thuMT1").val();
                    var thuMT2 = $(".thuMT2").val();
                    var thuAT1 = $(".thuAT1").val();
                    var thuAT2 = $(".thuAT2").val();
                    var thuErrorFlag = false; // set thu error flag
                    // setup error variables
                    var thuM = "• ( <strong>Thu morning </strong>)";
                    var thuA = "• ( <strong>Thu afternoon </strong>)";
                    if (thuMT1 === clearTime || thuMT2 === clearTime) {
                        validationMSmsg.append(thuM);
                        validationMSmsg.append(timeError1);
                        thuErrorFlag = true;
                        isValid = false;
                    }
                    else if (jsTime.validateTime(thuMT1, thuMT2) === false) {
                        validationMSmsg.append(thuM);
                        validationMSmsg.append(timeError2);
                        thuErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (thuMT1 !== clearTime && thuMT2 !== clearTime && thuAT1 !== clearTime && thuAT2 === clearTime) {
                        validationMSmsg.append(thuA);
                        validationMSmsg.append(timeError3);
                        thuErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (thuMT1 !== clearTime && thuMT2 !== clearTime && thuAT1 === clearTime && thuAT2 !== clearTime) {
                        validationMSmsg.append(wedA);
                        validationMSmsg.append(timeError4);
                        thuErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (thuAT1 !== clearTime && thuAT2 !== clearTime) {
                        if (jsTime.validateTime(thuAT1, thuAT2) === false || jsTime.validateTime(thuMT2, thuAT1) === false) {
                            validationMSmsg.append(thuA);
                            validationMSmsg.append(timeError2);
                            thuErrorFlag = true;
                            isValid = false;
                            dayTimeError = true;
                        }
                    }
                    if (thuErrorFlag === true)
                    {
                        timeErrors.push("thu"); // add day name to the end of timeErrors array   
                    }
                }
// if Friday checkbox is selected, validate Friday dates and times
                if (dayCheckbox[i] === "fri") {
// get Fri time variables
                    var friMT1 = $(".friMT1").val();
                    var friMT2 = $(".friMT2").val();
                    var friAT1 = $(".friAT1").val();
                    var friAT2 = $(".friAT2").val();
                    var friErrorFlag = false; // set fri error flag
                    // setup error variables
                    var friM = "• ( <strong>Fri morning </strong>)";
                    var friA = "• ( <strong>Fri afternoon </strong>)";
                    if (friMT1 === clearTime || friMT2 === clearTime) {
                        validationMSmsg.append(friM);
                        validationMSmsg.append(timeError1);
                        friErrorFlag = true;
                        isValid = false;
                    }
                    else if (jsTime.validateTime(friMT1, friMT2) === false) {
                        validationMSmsg.append(friM);
                        validationMSmsg.append(timeError2);
                        friErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (friMT1 !== clearTime && friMT2 !== clearTime && friAT1 !== clearTime && friAT2 === clearTime) {
                        validationMSmsg.append(friA);
                        validationMSmsg.append(timeError3);
                        friErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (friMT1 !== clearTime && friMT2 !== clearTime && friAT1 === clearTime && friAT2 !== clearTime) {
                        validationMSmsg.append(friA);
                        validationMSmsg.append(timeError4);
                        friErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (friAT1 !== clearTime && friAT2 !== clearTime) {
                        if (jsTime.validateTime(friAT1, friAT2) === false || jsTime.validateTime(friMT2, friAT1) === false) {
                            validationMSmsg.append(friA);
                            validationMSmsg.append(timeError2);
                            friErrorFlag = true;
                            isValid = false;
                            dayTimeError = true;
                        }
                    }
                    if (friErrorFlag === true)
                    {
                        timeErrors.push("fri"); // add day name to the end of timeErrors array   
                    }
                }
// if Saturday checkbox is selected, validate Saturday dates and times
                if (dayCheckbox[i] === "sat") {
// get Sat time variables
                    var satMT1 = $(".satMT1").val();
                    var satMT2 = $(".satMT2").val();
                    var satAT1 = $(".satAT1").val();
                    var satAT2 = $(".satAT2").val();
                    var satErrorFlag = false; // set sat error flag
                    // setup error variables
                    var satM = "• ( <strong>Sat morning </strong>)";
                    var satA = "• ( <strong>Sat afternoon </strong>)";
                    if (satMT1 === clearTime || satMT2 === clearTime) {
                        validationMSmsg.append(satM);
                        validationMSmsg.append(timeError1);
                        satErrorFlag = true;
                        isValid = false;
                    }
                    else if (jsTime.validateTime(satMT1, satMT2) === false) {
                        validationMSmsg.append(satM);
                        validationMSmsg.append(timeError2);
                        satErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (satMT1 !== clearTime && satMT2 !== clearTime && satAT1 !== clearTime && satAT2 === clearTime) {
                        validationMSmsg.append(satA);
                        validationMSmsg.append(timeError3);
                        satErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (satMT1 !== clearTime && satMT2 !== clearTime && satAT1 === clearTime && satAT2 !== clearTime) {
                        validationMSmsg.append(satA);
                        validationMSmsg.append(timeError4);
                        satErrorFlag = true;
                        isValid = false;
                        dayTimeError = true;
                    }
                    else if (satAT1 !== clearTime && satAT2 !== clearTime) {
                        if (jsTime.validateTime(satAT1, satAT2) === false || jsTime.validateTime(satMT2, satAT1) === false) {
                            validationMSmsg.append(satA);
                            validationMSmsg.append(timeError2);
                            satErrorFlag = true;
                            isValid = false;
                            dayTimeError = true;
                        }
                    }
                }
                if (satErrorFlag === true)
                {
                    timeErrors.push("sat"); // add day name to the end of timeErrors array   
                }
            }
// check and validate main schedule to and from dates
            if (fromN === "" && toN === "") {
                validationMSmsg.append("<span>• (<strong>from date & date to</strong>)&nbsp;&nbsp;Please enter valid&nbsp;<strong>date from</strong> and<strong> to dates</strong>.</span><br>");
                isValid = false;
                $("#fromN").focus();
            }
            else if (fromN === "") {
                validationMSmsg.append("<span>• (<strong>from date</strong>)&nbsp;&nbsp;Please enter valid&nbsp;<strong>date from</strong>.</span><br>");
                isValid = false;
                $("#fromN").focus();
            }
            else if (fromN !== "" && toN === "") {
                validationMSmsg.append("<span>• (<strong>to date</strong>)&nbsp;&nbsp;Please enter valid&nbsp;<strong>date to</strong>.</span><br>");
                isValid = false;
                $("#toN").focus();
            }
            else if (fromN !== "" || toN !== "") {
                if (jsDate.validateToFromDates(toN, fromN) === false)
                {
                    validationMSmsg.append("<span>• (<strong>from date & to date</strong>)&nbsp;&nbsp;Please enter valid&nbsp;<strong>from date</strong> and <strong>to dates</strong>. example:&nbsp;<strong>" + jsDate.todaysDate() + "</strong>&nbsp;and\n\
                    <strong>date is today or greater.</strong></span><br>");
                    $("#fromN").focus();
                    isValid = false;
                }
                else if (jsDate.validateDate(fromN) === false)
                {
                    validationMSmsg.append("<span>• (<strong>from date</strong>)&nbsp;&nbsp;Please enter valid&nbsp;<strong>from date</strong>.</span><br>");
                    $("#fromN").focus();
                    isValid = false;
                }
                else if (jsDate.validateDate(toN) === false)
                {
                    validationMSmsg.append("<span>• (<strong>to date</strong>)&nbsp;&nbsp;Please enter valid&nbsp;<strong>to date</strong>.</span><br>");
                    $("#toN").focus();
                    isValid = false;
                }
                else if (jsDate.validateOneYear(toN) === false)
                {
                    validationMSmsg.append("<span>• (<strong>to date</strong>)&nbsp;&nbsp;To Date must be less than one year from today's date.&nbsp;Please enter a valid&nbsp;<strong>to date</strong>.</span><br>");
                    $("#toN").focus();
                    isValid = false;
                }
                else if (jsDate.validateOneYear(fromN) === false)
                {
                    validationMSmsg.append("<span>• (<strong>from date</strong>)&nbsp;&nbsp;From Date must be less than one year from today's date.&nbsp;Please enter a valid&nbsp;<strong>from date</strong>.</span><br>");
                    $("#fromN").focus();
                    isValid = false;
                }
            }
        }
        if (dayTimeError === true) {
            validationMSmsg.append("<span>• (<strong>time in &AMP; time out</strong>)&nbsp;&nbsp;Please check these selected Day(s)&nbsp;<strong>"
                    + convert.capitalize(timeErrors.join(", ")) + "</strong> &nbsp;-&nbsp;Make sure the selected&nbsp;<strong>time out&nbsp;</strong>is greater than the selected&nbsp;<strong>time  in</strong>.</span><br>");
            // prevent the submission of the form if any entries are invalid 
            event.preventDefault();
        }
        else {
            $("#dayTimeError").html(""); // clear day time error message
        }

        if (isValid === false) {
            $("#validationMSError").html(validationMSError);
            fadeInMessage("#messagesMain");
            // prevent the submission of the form if any entries are invalid 
            event.preventDefault();
        }
        else {
            $("#validationMSError").html("");
            var dayOrDays = "";
            event.preventDefault();
            if (dayCheckbox.length > 1)
            {
                dayOrDays = "s"; // plural number of days
            }
            var submitMessage = "Submit request to accept appointments.";
            confirmMessage("#confirmSchedule", ".mainSchedule", "#validationMSmessages",
                    "#messagesMain", "Main Schedule Request Cancelled." + "<br>");
            var submitInfo =
                    "<tr>" + "<td>" + "from:" + "</td>" + "<td>" + jsDate.formatDate(fromN) + "</td>" + "</tr>"
                    + "<tr>" + "<td>" + "to:" + "</td>" + "<td>" + jsDate.formatDate(toN) + "</td>" + "</tr>"
                    + "<tr>" + "<td>" + "every:" + "</td>" + "<td>" + frequencyN + " minutes" + "</td>" + "</tr>"
                    + "<tr>" + "<td>" + "day" + dayOrDays + ":" + "</td>" + "<td>" + dayCheckbox.join(", ") + "</td>" + "</tr>";
            $("#confirmSchedule").dialog("open");
            $("#confirmSchedule").dialog("option", "minHeight", 180); // set min height for dialog box
            $("#confirmSchedule .eventHeader").html(submitMessage);
            $("#confirmSchedule tbody").html(submitInfo);
        }
    }); // end main schedule submit function
}); // end main schedule document ready function
