/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $("#section1 button.submit").off();
    associateInfo = associateClass.getCurrentAssociateInfo();
    $("#section1 button.submit").on("click", function () {
        var adminEmailAddress = $("#section1 #admin-emailAddress"),
                tips = $("#admin-fieldset-1 .validateTips"),
                adminPwd1 = $("#section1 #password-admin-1"),
                adminPwd2 = $("#section1 #password-admin-2"),
                username = $("#section1 #admin-username"),
                allFields = $([]).add(adminEmailAddress).add(adminPwd1).add(adminPwd2).add(username);
        tips.html("");
        allFields.removeClass("ui-state-error");


        var bValid = true;
        bValid = bValid && validateForm.checkRegexp(adminEmailAddress, emailRegEx, emailEntryError, tips);
        bValid = bValid && validateForm.checkLength(username, "username", 2, 45, tips);
//        bValid = bValid && validateForm.checkRegexp(adminPwd1, passwordRegEx, passwordErrorMsg, tips);
        bValid = bValid && validateForm.notNull(adminPwd1, pwdNullError, tips);
        bValid = bValid && validateForm.doesMatch(adminPwd1, adminPwd2, pwdMatchError, tips);

        if (bValid)
        {
            tips.html("");
            allFields.removeClass("ui-state-error");
            var adminPwd1Val = adminPwd1.val();
            var adminTypeVal = $("#section1 #admin-type").val(),
                    emailAddress = adminEmailAddress.val();
            var usernameVal = username.val();
            doAjaxAminPost(emailAddress, adminPwd1Val, adminTypeVal, usernameVal, "../adminCon", associateInfo.id, false);
        }
    });

});
function doAjaxAminPost(key1, key2, key3, key4, url, userId, async) {
    var ajaxPostSuccess;
    var adminPwd = $("#section1 #admin-fieldset-1 #postResults");

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
        data: {key1: key1, key2: key2, key3: key3, key4, userId: userId},
        error: function (xhr, status, error) {
            $('#loading').data('spinner').stop(); // Stop the spinner
            $("#loading h4").html("ERROR!");
            $("#loading").dialog("close"); // close loading element

            adminPwd.removeClass("good").addClass("error");
            adminPwd.html("An error occurred updating your Password.");
            fadeInMessage(adminPwd);

            alert("ERROR!  There was an posting data. " + xhr.status + " - " + error);
            if (xhr.status === 400)
            {
                $("#postDataError").html("Error: " + xhr.status + " - " + error);
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

            adminPwd.removeClass("error").addClass("good");
            adminPwd.html("Your Password was successfully updated.");
            fadeInMessage(adminPwd);
        }
    });
    return ajaxPostSuccess;
}