/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var viewObj1; // default view Object
var timeObj1; // default times Object
var profileObj1;
var alertObj1;
var memberSettings1; // member level object preference settings

// settings tabs
$(function () {
    $("#tabs").tabs();
});

// get current associate information
$(function () {
    associateInfo = associateClass.getCurrentAssociateInfo();
});

// associate default calendar view
$(function () {
//    $("#calendar-prefs-fieldset #personal-btn.save").off();
    var assoDefaultViewOpts = associateClass.renderDefaultCalView(associateInfo);
    $("#calendarOptions #calendar-prefs-fieldset #calendar-view-table #defaultCalendarView").html(assoDefaultViewOpts);

    var defaultCalendarView = $("#calendarOptions #calendar-prefs-fieldset #defaultCalendarView");
    var assocViewResultsMsg = $("#calendarOptions #calendar-prefs-fieldset #postResults");

    viewObj1 = {defaultCalView: defaultCalendarView.val()};

    $("#calendarOptions #calendar-prefs-fieldset #personal-btn").on("click", function () {

        var viewObj2 = {defaultCalView: defaultCalendarView.val()};

        if ((validateForm.noChanges(viewObj1, viewObj2)) === true) // any changes to object calendar
        {
            assocViewResultsMsg.removeClass("good").removeClass("error").addClass("cancel");
            assocViewResultsMsg.html("No changes made to your default calendar view.");
            fadeInMessage(assocViewResultsMsg);
        }
        else {
            viewObj1 = {
                defaultCalView: defaultCalendarView.val()};
            var calendarViewChoice = defaultCalendarView.val();
            defaultCalendarView.val(calendarViewChoice);
            associateInfo.defaultCalendarView = calendarViewChoice;
            var o = convert.stringifyObj(associateInfo);
            doAjaxPost(o, '../FullCalPost', "updateDefaultCalendarView");
        }
    }); // end calendar edit settings function

    $("#members select").change(function () {
        var platinimElem = $("#members #members-fieldset #platinum"),
                goldElem = $("#members #members-fieldset #gold"),
                silverElem = $("#members #members-fieldset #silver"),
                bronzeElem = $("#members #members-fieldset #bronze");
        var p = parseInt(platinimElem.val());
        var g = parseInt(goldElem.val());
        var s = parseInt(silverElem.val());
        var b = parseInt(bronzeElem.val());

        var isChoiceValid = true;
        if (g > p)
        {
            goldElem.val(p);
            g = parseInt(platinimElem.val());
            isChoiceValid = false;
        }
        if (s > g)
        {
            silverElem.val(g);
            s = parseInt(goldElem.val());
            isChoiceValid = false;
        }
        if (b > s)
        {
            bronzeElem.val(s);
            b = parseInt(silverElem.val());
            isChoiceValid = false;
        }
        if (isChoiceValid === false)
        {
            memberSettingsResultsMsg.removeClass("good").removeClass("error").addClass("cancel");
            memberSettingsResultsMsg.html("Member Level number may only be set to equal or lower than the next higher level.");
            fadeInMessage(memberSettingsResultsMsg);
        }
    });
    // change member level settings function
    var memberSettingsResultsMsg = $("#members #members-fieldset #postResults");
    $("#members #members-fieldset #personal-btn").on("click", function () {
        var platinum = parseInt($("#members #members-fieldset #platinum").val());
        var gold = parseInt($("#members #members-fieldset #gold").val());
        var silver = parseInt($("#members #members-fieldset #silver").val());
        var bronze = parseInt($("#members #members-fieldset #bronze").val());

        var memberSettings2 = {Bronze: bronze, Gold: gold, Silver: silver, Platinum: platinum};

        if ((validateForm.noChanges(memberSettings1, memberSettings2)) === true)// any changes to member settings object
        {
            memberSettingsResultsMsg.removeClass("good").removeClass("error").addClass("cancel");
            memberSettingsResultsMsg.html("No changes made to member level settings.");
            fadeInMessage(memberSettingsResultsMsg);
        }
        else {
            memberSettings1 = memberSettings2;
            associateInfo.memberSettings = memberSettings2;
            var m = convert.stringifyObj(associateInfo);
            doAjaxPost(m, '../FullCalPost', "updateMemberSettings");
        }
    }); // end member level settings function
}); // end associate default calendar view functions

// associate default schedule times
$(function () {
    var morTimeInOpts = associateClass.renderDefaultTimes(associateInfo, "startMTin");
    var morTimeOutOpts = associateClass.renderDefaultTimes(associateInfo, "startMTout");
    var aftTimeInOpts = associateClass.renderDefaultTimes(associateInfo, "startATin");
    var aftTimeOutOpts = associateClass.renderDefaultTimes(associateInfo, "startATout");

    $("#default-times-fieldset #default-times-table #startTimeM").html(morTimeInOpts);
    $("#default-times-fieldset #default-times-table #endTimeM").html(morTimeOutOpts);
    $("#default-times-fieldset #default-times-table #startTimeA").html(aftTimeInOpts);
    $("#default-times-fieldset #default-times-table #endTimeA").html(aftTimeOutOpts);

    var assocDefaultTimes = $("#calendarOptions #default-times-fieldset #postResults");

    var morTimeIn = $("#calendarOptions #default-times-fieldset #default-times-table #startTimeM");
    var morTimeOut = $("#calendarOptions #default-times-fieldset #default-times-table #endTimeM");
    var aftTimeIn = $("#calendarOptions #default-times-fieldset #default-times-table #startTimeA");
    var aftTimeOut = $("#calendarOptions #default-times-fieldset #default-times-table #endTimeA");

    timeObj1 = {
        morTimeIn: morTimeIn.val(),
        morTimeOut: morTimeOut.val(),
        aftTimeIn: aftTimeIn.val(),
        aftTimeOut: aftTimeOut.val()
    };

    $("#calendarOptions #default-times-fieldset #personal-btn.save").on("click", function () {
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

});

// security questions functions
$(function () {
    var tips = $("#security-questions-feildset .validateTips");
    var answerErrorMsg = "Answers may consist of a-z, 0-9, underscores, begin with a letter.";
    var question1 = $(".security-questions-table #securityQuestion-1"),
            answer1 = $(".security-questions-table #securityAnswer-1"),
            question2 = $(".security-questions-table #securityQuestion-2"),
            answer2 = $(".security-questions-table #securityAnswer-2"),
            question3 = $(".security-questions-table #securityQuestion-3"),
            answer3 = $(".security-questions-table #securityAnswer-3");

    var allFields = $([]).add(answer1).add(answer2).add(answer3);

    $("#security-questions-feildset #security-questions-div #personal-btn").on("click", function () {
        tips.html("");
        allFields.removeClass("ui-state-error");
        var bValid = true;
        var a1 = answer1.val(),
                a2 = answer2.val(),
                a3 = answer3.val(),
                q1 = question1.val(),
                q2 = question2.val(),
                q3 = question3.val();

        bValid = bValid && validateForm.checkLength(answer1, "Answer 1", 3, 30, tips);
        bValid = bValid && validateForm.checkRegexp(answer1, nameRegEx, answerErrorMsg, tips);
        bValid = bValid && validateForm.checkLength(answer2, "Answer 2", 3, 30, tips);
        bValid = bValid && validateForm.checkRegexp(answer2, nameRegEx, answerErrorMsg, tips);
        bValid = bValid && validateForm.checkLength(answer3, "Answer 3", 3, 30, tips);
        bValid = bValid && validateForm.checkRegexp(answer3, nameRegEx, answerErrorMsg, tips);

        if (bValid) {
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
            profileFunctions.displayAssoPwd();
        }
    });
}); // end security questions functions 

// password functions
$(function () {

    $("#password-feildset #personal-btn.change").off();
    var assoPwdTable = associateClass.associatePassword(associateInfo);
    $("#security #password-feildset .password-table tbody").html(assoPwdTable);
    $("#security #password-feildset .password-table #associate-password").prop('disabled', true);
    $(".password-table #associate-password:input").addClass("disabled-input");
    $("#password-feildset #personal-btn").removeClass("save").addClass("change button-size-1");

    $("#password-feildset #personal-btn.change").on("click", function () {

        $("#password-feildset #personal-btn.save").off();

        var editPwdTable = associateClass.associateEditPwd();
        $("#security #password-feildset .password-table tbody").html(editPwdTable);
        var tips = $("#password-feildset .validateTips");
        var pwd1 = $("#password-div .password-table #password-1");
        var pwd2 = $("#password-div .password-table #password-2");
        $("#password-feildset #personal-btn").removeClass("change .button-size-1").addClass("save");

        var allFields = $([]).add(pwd1).add(pwd2);

        $("#password-feildset #personal-btn.save").on("click", function () {

            tips.html("");
            allFields.removeClass("ui-state-error");
            var bValid = true;
            bValid = bValid && validateForm.checkRegexp(pwd1, passwordRegEx, passwordErrorMsg, tips);
            bValid = bValid && validateForm.checkRegexp(pwd2, passwordRegEx, passwordErrorMsg, tips);
            bValid = bValid && validateForm.doesMatch(pwd1, pwd2, pwdMatchError, tips);

            if (bValid) {
                associateInfo.password = pwd1.val();
                var o = convert.stringifyObj(associateInfo);
                doAjaxPost(o, '../FullCalPost', "updateAssoPwd");
            }
        });
    });
}); // end password functions

$(document).ready(function () {
    // display tabs
    profileFunctions.displayPersonal();
    profileFunctions.displayAlertPrefs();
    profileFunctions.displayAssoPhoto();
    profileFunctions.displayAssoAcct();
    profileFunctions.displayMemberSettings();
    // display upload button after an image has been selected
    $('input:file').on("change", function () {
        $('input:submit').prop('disabled', !$(this).val());
        var uploadBtn = "<input value='' type='submit' class='upload'alt='' >";
        $("#profile #input-upload").append(uploadBtn);
    });
}); // end document ready

var profileFunctions = {
    displayPersonal: function () {
        $("#profile-fieldset-1 #personal-btn.edit").off(); // turn off edit button class
        $("#profile-fieldset-1 #personal-btn.save").off(); // turn off save button class

        associateInfo = associateClass.getCurrentAssociateInfo(); // get current associate information
        var associateInfoTable = associateClass.renderAssociate(associateInfo); // render table for display

        $("#profile #profile-fieldset-1 #personal").html(associateInfoTable);
        $("#profile-fieldset-1 #personal-btn").removeClass("save").addClass("edit");

        $("#profile-fieldset-1 #personal-btn.edit").on("click", function () {
            profileFunctions.editPersonal(associateInfo);
        });
    },
    editPersonal: function (associateInfo) {
        var associateInfoMsg = $("#profile #profile-fieldset-1 #postResults");
        associateInfoMsg.html("");
        $("#profile-fieldset-1 #personal-btn.save").off();
        var associateInputTable = associateClass.associateInput(associateInfo);

        $("#profile #profile-fieldset-1 #personal").html(associateInputTable);
        $("#profile-fieldset-1 #personal-btn").removeClass("edit").addClass("save");

        $("#email, #id").prop('disabled', true);

        var tips = $("#profile-fieldset-1 .validateTips");
        var mobilePhone = $("#personal-table #mobilePhone"),
                homePhone = $("#personal-table #homePhone"),
                workPhone = $("#personal-table #workPhone"),
                otherPhone = $("#personal-table #otherPhone"),
                address = $("#personal-table #address"),
                city = $("#personal-table #city"),
                state = $("#personal-table #state"),
                zip = $("#personal-table #zip");

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

        var notReqFields = [homePhone, workPhone, otherPhone, address, city, zip];
        tips.html("");
        var bValid = true;
        $("#profile-fieldset-1 #personal-btn.save").on("click", function () {
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
                profileFunctions.displayPersonal();
                associateInfoMsg.removeClass("good").removeClass("error").addClass("cancel");
                associateInfoMsg.html("No changes were made your personal profile.");
                fadeInMessage(associateInfoMsg);
            }
            else {
                bValid = bValid && validateForm.checkRegexp(mobilePhone, phoneRegEx, phoneErrorMsg, tips);

                for (var z = 0; z < notReqFields.length; z++)
                    if (notReqFields[z].val() !== "") // check if field has data
                    {
                        if (notReqFields[z] === homePhone || notReqFields[z] === workPhone || notReqFields[z] === otherPhone)
                        {
                            bValid = bValid && validateForm.checkRegexp(notReqFields[z], phoneRegEx, phoneErrorMsg, tips);
                            if (!bValid) {
                            }
                        }
                        else if (notReqFields[z] === address || notReqFields[z] === city)
                        {
                            bValid = bValid && validateForm.checkLength(notReqFields[z], "This fieild", 3, 45, tips);
                        }
                        else if (notReqFields[z] === zip)
                        {
                            bValid = bValid && validateForm.checkRegexp(zip, zipCodeRegEx, zipErrorMsg, tips);
                        }
                    }
                if (bValid) {
                    associateInfo.mobilePhone = mobilePhone.val();
                    associateInfo.homePhone = homePhone.val();
                    associateInfo.workPhone = workPhone.val();
                    associateInfo.otherPhone = otherPhone.val();
                    associateInfo.address = address.val();
                    associateInfo.city = city.val();
                    associateInfo.state = state.val();
                    associateInfo.zip = zip.val();

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

                    var o = convert.stringifyObj(associateInfo);
                    doAjaxPost(o, '../FullCalPost', "updateAssociate");
                    profileFunctions.displayPersonal(associateInfo);
                }
            }
        });
    },
    displayAlertPrefs: function () {

        $("#alert-prefs-fieldset #personal-btn.edit").off(); // turn off edit button class
        associateClass.alertPrefs(associateInfo, ".alert-prefs-table"); // display alert preferences
        $("#alert-prefs-fieldset #alert-prefs #personal-btn").removeClass("save").addClass("edit");
        $(".alert-prefs-table #email-ad-alerts, #sms-ad-alerts, #email-appt-alerts, #sms-appt-alerts").prop('disabled', true);

        $("#alert-prefs-fieldset #alert-prefs #personal-btn.edit").on("click", function () {

            profileFunctions.editAlertPrefs(associateInfo);
        });
    },
    editAlertPrefs: function (associateInfo) {
        $("#alert-prefs-fieldset #personal-btn.edit").off();
        $("#alert-prefs-fieldset #personal-btn.save").off();

        var associateAlerts = $("#profile #alert-prefs-fieldset #alert-prefs #postResults");
        associateAlerts.html("");

        $(".alert-prefs-table #email-ad-alerts, #sms-ad-alerts, #email-appt-alerts, #sms-appt-alerts").prop('disabled', false);

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

        $("#alert-prefs-fieldset #personal-btn").removeClass("edit").addClass("save");

        $("#alert-prefs-fieldset #personal-btn.save").on("click", function () {

            var alertObj2 = {
                emailAdAlerts: emailAdAlerts.is(':checked'),
                smsAdAlerts: smsAdAlerts.is(':checked'),
                emailApptAlerts: emailApptAlerts.is(':checked'),
                smsApptAlerts: smsApptAlerts.is(':checked')
            };

            if ((validateForm.noChanges(alertObj1, alertObj2)) === true)
            {
                $("#alert-prefs-fieldset #personal-btn.save").off();
                $("#alert-prefs-fieldset #personal-btn.edit").off();
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

                alertObj1 = {
                    emailAdAlerts: emailAdAlerts.is(':checked'),
                    smsAdAlerts: smsAdAlerts.is(':checked'),
                    emailApptAlerts: emailApptAlerts.is(':checked'),
                    smsApptAlerts: smsApptAlerts.is(':checked')
                };

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
    displayMemberSettings: function () {
        // set the member level numbers
        $("#members fieldset#members-fieldset #platinum").val(associateInfo.memberSettings.Platinum);
        $("#members fieldset#members-fieldset #gold").val(associateInfo.memberSettings.Gold);
        $("#members fieldset#members-fieldset #silver").val(associateInfo.memberSettings.Silver);
        $("#members fieldset#members-fieldset #bronze").val(associateInfo.memberSettings.Bronze);
        memberSettings1 = associateInfo.memberSettings;
    }
}; //end profileFunctions
