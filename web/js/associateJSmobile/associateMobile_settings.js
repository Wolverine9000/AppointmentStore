/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var viewObj1; // default view Object
var timeObj1; // default times Object
var profileObj1;
var alertObj1;

//================ mobile settings scripts ==========================
// get current associate information
$(function () {
//    associateInfo = associateClass.getCurrentAssociateInfo();
    profileFunctions.displayPersonal();
    profileFunctions.displayAlertPrefs(associateInfo);
    profileFunctions.displayAssoPhoto();
    profileFunctions.displayAssoAcct();
    profileFunctions.defaultCalView(associateInfo);
    $("a#ui-id-1.ui-link").focus();
    $("a#ui-id-1.ui-link").addClass("ui-btn-active").addClass("ui-tabs-anchor");
    // enable button after an image has been selected
//    $('input:file').on("change", function () {
//        $('input:submit').prop('disabled', !$(this).val());
//    });
});

// security questions functions
$(function () {
//    $("#security #security-questions-btn").off();
    var answerErrorMsg = "Answers may consist of a-z, 0-9, underscores, begin with a letter.";
    var errorPage = $("#error-security");
    var question1 = $("#security #securityQuestion-1"),
            answer1 = $("#security #securityAnswer-1"),
            question2 = $("#security #securityQuestion-2"),
            answer2 = $("#security #securityAnswer-2"),
            question3 = $("#security #securityQuestion-3"),
            answer3 = $("#security #securityAnswer-3");

    var allFields = $([]).add(answer1).add(answer2).add(answer3);

    $("#security #security-questions-btn").on("click", function () {
        var a1 = answer1.val(), a2 = answer2.val(), a3 = answer3.val(), q1 = question1.val(),
                q2 = question2.val(), q3 = question3.val();

        errors = []; // clear errors array
        $("#error-security ul").html("");

        validateForm.checkLength(answer1, "Answer 1", 3, 30);
        validateForm.checkRegexp(answer1, nameRegEx, "Answer 1 ", answerErrorMsg);
        validateForm.checkLength(answer2, "Answer 2", 3, 30);
        validateForm.checkRegexp(answer2, nameRegEx, "Answer 2 ", answerErrorMsg);
        validateForm.checkLength(answer3, "Answer 3", 3, 30);
        validateForm.checkRegexp(answer3, nameRegEx, "Answer 3 ", answerErrorMsg);

        if (errors.length > 0) {
            errorPage.popup('open', {transition: "pop"});
            $("#error-security h3").html("Please correct these Errors!");
            $.each(errors, function (index, value) {
                $("#error-security ul").append("<li>" + value + "</li>");
            });
        }
        else if (errors.length === 0) {
            allFields.val("");
            var associateId = associateInfo.id,
                    associateEmail = associateInfo.email;
            var security = {
                id: associateId,
                email: associateEmail,
                challengeQandA: [{q: q1, a: a1}, {q: q2, a: a2}, {q: q3, a: a3}],
                user: {id: associateId, emailAddress: associateEmail}
            };
            associateInfo.security = security;
            var o = convert.stringify(security);
            doAjaxPost(o, '../FullCalPost', "securityQandA");
        }
    });
}); // end security questions functions 

// password functions
$(function () {
    $("#security #personal-btn").off();
    var pwd1 = $("#security input#password");
    var pwd2 = $("#security input#confirm-password");
    var errorPage = $("#error-security");

    var allFields = $([]).add(pwd1).add(pwd2);

    $("#security button#password-btn").on("click", function () {

        errors = []; // clear errors array
        $("#error-security ul").html("");
        validateForm.checkRegexp(pwd1, passwordRegEx, "Password ",
                passwordErrorMsg);
        validateForm.doesMatch(pwd1, pwd2, pwdMatchError);
        // check for errors
        if (errors.length > 0) {
            errorPage.popup('open', {transition: "pop"});
            $("#error-security h3").html("Please correct these Errors!");
            $.each(errors, function (index, value) {
                $("#error-security ul").append("<li>" + value + "</li>");
            });
        }
        else {
            associateInfo.password = pwd1.val();
            allFields.val(""); //clear password fields
            var o = convert.stringifyObj(associateInfo);
            doAjaxPost(o, '../FullCalPost', "updateAssoPwd");
        }
    });
}); // end password functions

// profile functions
var profileFunctions = {
    displayPersonal: function () {
        $("#profile-fieldset-1 #personal-btn").off();
        associateInfo = associateClass.getCurrentAssociateInfo(); // get current associate information
        var associateInfoTable = associateClass.renderAssociate(associateInfo); // render table for display

        $("#profile #profile-fieldset-1 #profile-table tbody").html(associateInfoTable);

        $("#profile-fieldset-1 #personal-btn").on("click", function () {
            profileFunctions.editPersonal(associateInfo);
        });
    },
    editPersonal: function (associateInfo) {
        $("#send").off();
        var associateInfoMsg = $("#profile #profile-fieldset-1 #postResults");
        associateInfoMsg.html("");
        $("#profile-fieldset-1 #personal-btn.save").off();
        var stateList = options.getState(associateInfo);
        var
                mobilePhone = $("#edit-associate-page #edit-associate-form #mobilePhone"),
                homePhone = $("#edit-associate-page #edit-associate-form #homePhone"),
                workPhone = $("#edit-associate-page #edit-associate-form #workPhone"),
                otherPhone = $("#edit-associate-page #edit-associate-form #otherPhone"),
                address = $("#edit-associate-page #edit-associate-form #address"),
                city = $("#edit-associate-page #edit-associate-form #city"),
                state = $("#edit-associate-page #edit-associate-form #state"),
                zip = $("#edit-associate-page #edit-associate-form #zip"),
                errorPage = $("#edit-associate-page #error-associate-info");

        var notReqFields = [homePhone, workPhone, address, city, zip];

        mobilePhone.val(associateInfo.mobilePhone);
        homePhone.val(associateInfo.homePhone);
        workPhone.val(associateInfo.workPhone);
        otherPhone.val(associateInfo.otherPhone);
        address.val(associateInfo.address);
        city.val(associateInfo.city);
        state.html(stateList);
        zip.val(associateInfo.zip);

        profileObj1 = {
            mobilePhone: mobilePhone.val(),
            homePhone: homePhone.val(),
            workPhone: workPhone.val(),
            otherPhone: otherPhone.val(),
            address: address.val(),
            city: city.val(),
            state: state.val(),
            zip: zip.val()
        };

        $("#send").on("click", function () {
            associateInfoMsg = $("#profile #profile-fieldset-1 #postResults");
            profileObj2 = {
                mobilePhone: mobilePhone.val(),
                homePhone: homePhone.val(),
                workPhone: workPhone.val(),
                otherPhone: otherPhone.val(),
                address: address.val(),
                city: city.val(),
                state: state.val(),
                zip: zip.val()
            };
            if ((validateForm.noChanges(profileObj1, profileObj2)) === true)
            {
                $.mobile.changePage("#settings", {transition: "fade"});
                profileFunctions.displayPersonal();
                associateInfoMsg.removeClass("good").removeClass("error").addClass("cancel");
                associateInfoMsg.html("No changes were made your personal profile.");
                fadeInMessage(associateInfoMsg);
            }
            else {
                errors = []; // clear errors array
                $("#error-associate-info ul").html("");
                validateForm.notEmpty(mobilePhone, "Mobile Phone ");
                validateForm.checkRegexp(mobilePhone, phoneRegEx, "Mobile Phone ", phoneErrorMsg);

                for (var z = 0; z < notReqFields.length; z++)
                    if (notReqFields[z].val() !== "") // check if field has data
                    {
                        if (notReqFields[z] === homePhone || notReqFields[z] === workPhone)
                        {
                            var p = convert.errorName(notReqFields[z]);
                            validateForm.checkRegexp(notReqFields[z], phoneRegEx, p, phoneErrorMsg);
                        }
                        else if (notReqFields[z] === address || notReqFields[z] === city)
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
                    $('#error-associate-info  ul').show();
                    $("#error-associate-info h3").html("Please correct these Errors!");
                    $.each(errors, function (index, value) {
                        $("#error-associate-info ul").append("<li>" + value + "</li>");
                    });
                }
                else {
                    associateInfo.mobilePhone = mobilePhone.val();
                    associateInfo.homePhone = homePhone.val();
                    associateInfo.workPhone = workPhone.val();
                    associateInfo.otherPhone = otherPhone.val();
                    associateInfo.address = address.val();
                    associateInfo.city = city.val();
                    associateInfo.state = state.val();
                    associateInfo.zip = zip.val();

                    var o = convert.stringifyObj(associateInfo);
                    doAjaxPost(o, '../FullCalPost', "updateAssociate");
                    $.mobile.changePage("#settings", {transition: "fade"});
                    profileFunctions.displayPersonal();
                }
            }
        }); // end send element function
    },
    displayAlertPrefs: function (associateInfo) {

        $("#alert-prefs-fieldset #alert-btn").off(); // turn off edit button class
        associateClass.alertPrefs(associateInfo, "#alert-prefs-fieldset #alert-prefs"); // display alert preferences

        var emailAdAlerts = $("#alert-prefs #email-ad-alerts"),
                smsAdAlerts = $("#alert-prefs #sms-ad-alerts"),
                emailApptAlerts = $("#alert-prefs #email-appt-alerts"),
                smsApptAlerts = $("#alert-prefs #sms-appt-alerts");

        alertObj1 = {
            emailAdAlerts: emailAdAlerts.is(':checked'),
            smsAdAlerts: smsAdAlerts.is(':checked'),
            emailApptAlerts: emailApptAlerts.is(':checked'),
            smsApptAlerts: smsApptAlerts.is(':checked')
        };

        $("#alert-prefs-fieldset #alert-btn").on("click", function () {
            var associateAlerts = $("#profile #alert-prefs-fieldset #postResults");
            associateAlerts.html("");

            var alertObj2 = {
                emailAdAlerts: emailAdAlerts.is(':checked'),
                smsAdAlerts: smsAdAlerts.is(':checked'),
                emailApptAlerts: emailApptAlerts.is(':checked'),
                smsApptAlerts: smsApptAlerts.is(':checked')
            };

            if ((validateForm.noChanges(alertObj1, alertObj2)) === true)
            {
                associateAlerts.removeClass("good").removeClass("error").addClass("cancel");
                associateAlerts.html("No changes made to your Alert preferences.");
                fadeInMessage(associateAlerts);
                associateInfo.emailAdAlerts = emailAdAlerts.is(':checked');
                associateInfo.smsAdAlerts = smsAdAlerts.is(':checked');
                associateInfo.emailApptAlerts = emailApptAlerts.is(':checked');
                associateInfo.smsApptAlerts = smsApptAlerts.is(':checked');
                profileFunctions.displayAlertPrefs(associateInfo);
            }
            else {
                associateInfo.emailAdAlerts = emailAdAlerts.is(':checked');
                associateInfo.smsAdAlerts = smsAdAlerts.is(':checked');
                associateInfo.emailApptAlerts = emailApptAlerts.is(':checked');
                associateInfo.smsApptAlerts = smsApptAlerts.is(':checked');

                var o = convert.stringifyObj(associateInfo);
                doAjaxPost(o, '../FullCalPost', "updateAssociateAlerts");
                profileFunctions.displayAlertPrefs(associateInfo);
            }
        });
    },
    editAlertPrefs: function (associateInfo) {
        $("#alert-prefs-fieldset #personal-btn.edit").off();
        $("#alert-prefs-fieldset #personal-btn.save").off();

        var associateAlerts = $("#profile #alert-prefs-fieldset #alert-prefs #postResults");
        associateAlerts.html("");


        var emailAdAlerts = $("#alert-prefs #email-ad-alerts"),
                smsAdAlerts = $("#alert-prefs #sms-ad-alerts"),
                emailApptAlerts = $("#alert-prefs #email-appt-alerts"),
                smsApptAlerts = $("#alert-prefs #sms-appt-alerts");

        alertObj1 = {
            emailAdAlerts: emailAdAlerts.is(':checked'),
            smsAdAlerts: smsAdAlerts.is(':checked'),
            emailApptAlerts: emailApptAlerts.is(':checked'),
            smsApptAlerts: smsApptAlerts.is(':checked')
        };


        $("#alert-prefs-fieldset #personal-btn.save").on("click", function () {

            var alertObj2 = {
                emailAdAlerts: emailAdAlerts.is(':checked'),
                smsAdAlerts: smsAdAlerts.is(':checked'),
                emailApptAlerts: emailApptAlerts.is(':checked'),
                smsApptAlerts: smsApptAlerts.is(':checked')
            };

            if ((validateForm.noChanges(alertObj1, alertObj2)) === true)
            {
                associateAlerts.removeClass("good").removeClass("error").addClass("cancel");
                associateAlerts.html("No changes were made to your Alert preferences.");
                fadeInMessage(associateAlerts);
                associateInfo.emailAdAlerts = emailAdAlerts.is(':checked');
                associateInfo.smsAdAlerts = smsAdAlerts.is(':checked');
                associateInfo.emailApptAlerts = emailApptAlerts.is(':checked');
                associateInfo.smsApptAlerts = smsApptAlerts.is(':checked');
                profileFunctions.displayAlertPrefs(associateInfo);
            }
            else {
                associateInfo.emailAdAlerts = emailAdAlerts.is(':checked');
                associateInfo.smsAdAlerts = smsAdAlerts.is(':checked');
                associateInfo.emailApptAlerts = emailApptAlerts.is(':checked');
                associateInfo.smsApptAlerts = smsApptAlerts.is(':checked');

                var o = convert.stringifyObj(associateInfo);
                doAjaxPost(o, '../FullCalPost', "updateAssociateAlerts");
                profileFunctions.displayAlertPrefs(associateInfo);
            }
        });

    },
    displayAssoPhoto: function () {
        var associateImgId = 0;
        if (associateInfo.imgUpl === true)
        {
            associateImgId = associateInfo.id;
        }
        var associatePhoto = '<img src="../img/associates/' + associateImgId + '.png" alt="associate img" alt="img">';

        $("#photo-fieldset #photo").html(associatePhoto);
    },
    displayAssoAcct: function () {
        acctInfoTable = associateClass.associateAccount(associateInfo);
        $("#account #account-fieldset tbody").html(acctInfoTable);
    },
    defaultCalView: function (associateInfo) {
        //    $("#calendar-prefs-fieldset #personal-btn.save").off();
        var assoDefaultViewOpts = associateClass.renderDefaultCalView(associateInfo);
        $("#calendar-settings #defaultCalendarView").html(assoDefaultViewOpts);
        $("#calendar-settings #defaultCalendarView").selectmenu("refresh");

        var defaultCalendarView = $("#calendar-settings #defaultCalendarView");
        var assocViewResultsMsg = $("#calendar-settings #postResults");

        viewObj1 = {defaultCalView: defaultCalendarView.val()};

        $("#calendar-settings #calendar-prefs-btn").on("click", function () {

            var viewObj2 = {defaultCalView: defaultCalendarView.val()};

            if ((validateForm.noChanges(viewObj1, viewObj2)) === true)
            {
                assocViewResultsMsg.removeClass("good").removeClass("error").addClass("cancel");
                assocViewResultsMsg.html("No changes made to your default calendar view.");
                fadeInMessage(assocViewResultsMsg);
            }

            else {
                viewObj1 = {defaultCalView: defaultCalendarView.val()};
                var calendarViewChoice = defaultCalendarView.val();
                defaultCalendarView.val(calendarViewChoice);
                associateInfo.defaultCalendarView = calendarViewChoice;
                var o = convert.stringifyObj(associateInfo);
                doAjaxPost(o, '../FullCalPost', "updateDefaultCalendarView");
            }
        });
    }
}; //end profileFunctions

// associate default schedule times
$(function () {
    $("#calendar-settings #workhours-btn").off();
    var morTimeInOpts = associateClass.renderDefaultTimes(associateInfo, "startMTin");
    var morTimeOutOpts = associateClass.renderDefaultTimes(associateInfo, "startMTout");
    var aftTimeInOpts = associateClass.renderDefaultTimes(associateInfo, "startATin");
    var aftTimeOutOpts = associateClass.renderDefaultTimes(associateInfo, "startATout");

    $("#calendar-settings #startTimeM").html(morTimeInOpts).selectmenu("refresh");
    $("#calendar-settings #endTimeM").html(morTimeOutOpts).selectmenu("refresh");
    $("#calendar-settings #startTimeA").html(aftTimeInOpts).selectmenu("refresh");
    $("#calendar-settings #endTimeA").html(aftTimeOutOpts).selectmenu("refresh");

    var assocDefaultTimes = $("#calendar-settings #defaulttimes-postResults");

    var morTimeIn = $("#calendar-settings #startTimeM");
    var morTimeOut = $("#calendar-settings #endTimeM");
    var aftTimeIn = $("#calendar-settings #startTimeA");
    var aftTimeOut = $("#calendar-settings #endTimeA");

    timeObj1 = {
        morTimeIn: morTimeIn.val(),
        morTimeOut: morTimeOut.val(),
        aftTimeIn: aftTimeIn.val(),
        aftTimeOut: aftTimeOut.val()
    };

    $("#calendar-settings #workhours-btn").on("click", function () {
        assocDefaultTimes.html("");
        var timeErrors = [];

        var timeObj2 = {
            morTimeIn: morTimeIn.val(),
            morTimeOut: morTimeOut.val(),
            aftTimeIn: aftTimeIn.val(),
            aftTimeOut: aftTimeOut.val()
        };
        var morTIn = morTimeIn.val(),
                morTOut = morTimeOut.val(),
                aftTIn = aftTimeIn.val(),
                aftTOut = aftTimeOut.val();

        timeErrors = jsTime.validateTime2(morTIn, morTOut, aftTIn, aftTOut) // validate times

        if (validateForm.noChanges(timeObj1, timeObj2) === true)
        {
            timeErrors.push("noChanges");
        }

        if (timeErrors.length > 0)
        {
            for (var i in timeErrors)
            {
                if (timeErrors[i] === "noChanges")
                {
                    assocDefaultTimes.html("No changes made to Normal Work Hours.");
                    assocDefaultTimes.removeClass("good").removeClass("error").addClass("cancel");
                }
                if (timeErrors[i] === "morError")
                {
                    assocDefaultTimes.html("&#8226; Check " + "<b>" + "Morning" + "</b>" + " times. Morning In must be less than Morning Out.");
                    if (timeErrors.length > 1)
                    {
                        assocDefaultTimes.append("<br>");
                    }
                    assocDefaultTimes.removeClass("good").removeClass("cancel").addClass("error");
                }
                else if (timeErrors[i] === "aftError")
                {
                    assocDefaultTimes.append("&#8226;  Check " + "<b>" + "Afternoon" + "</b>" + " times. Afternoon In must be less than Afternoon Out and greater than Morning times.");
                    assocDefaultTimes.removeClass("good").removeClass("cancel").addClass("error");
                }
            }
            fadeInMessage(assocDefaultTimes);
        }

        else {
            timeObj1 = {
                morTimeIn: morTimeIn.val(),
                morTimeOut: morTimeOut.val(),
                aftTimeIn: aftTimeIn.val(),
                aftTimeOut: aftTimeOut.val()
            };
            var now = jsDate.todaysDate();
            associateInfo.morningTimeIn = moment(now + " " + morTimeIn.val(), dateTimeFmt);
            associateInfo.morningTimeOut = moment(now + " " + morTimeOut.val(), dateTimeFmt);
            associateInfo.afternoonTimeIn = moment(now + " " + aftTimeIn.val(), dateTimeFmt);
            associateInfo.afternoonTimeOut = moment(now + " " + aftTimeOut.val(), dateTimeFmt);
            var o = convert.stringifyObj(associateInfo);
            doAjaxPost(o, '../FullCalPost', "updateDefaultCalendarTimes");
        }
    });
}); // end default time schedule