
<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_update.css">

<script type="text/javascript">

    $(document).ready(function () {
        $("#upDateForm").validate({
            rules: {
                firstname: {
                    required: true
                },
                lastname: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                securityanswer: {
                    required: true
                },
                homephone: {
                    required: true,
                    number: true,
                    minlength: 9
                },
                workphone: {
                    number: true,
                    minlength: 9
                },
                mobliephone: {
                    number: true,
                    minlength: 9
                },
                otherphone: {
                    number: true,
                    minlength: 9
                },
                address: {
                    required: true
                },
                city: {
                    required: true
                },
                state: {
                    required: true
                },
                zip: {
                    required: true,
                    digits: true,
                    minlength: 5
                }
            }
        });
    });
</script>

<script>
    $(document).ready(function () {
        var states =
                ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL",
                    "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME",
                    "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
                    "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI",
                    "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];
        $("#state").autocomplete({
            source: states
        });
    });
</script>

<script type="text/javascript">

    $(document).ready(function () {
        $("#updatePassword").validate({
            rules: {
                password: {
                    required: true,
                    minlength: 8
                },
                confirmPassword: {
                    required: true,
                    minlength: 8
                }
            }
        });
    });
</script>
<script type="text/javascript">

    $(document).ready(function () {
        $("#updateSecure").validate({
            rules: {
                securityAnswer: {
                    required: true
                }
            }
        });
    });
</script>
<%-- asociateRecord is empty --%>
<c:if test="${empty associateRecord}">
    <c:redirect url="logoutAssociate"></c:redirect>
</c:if>

<aside id="navSide">
    <img src="${initParam.associateImagePath}${associateRecord.id}.png"
         alt="${associateRecord.id}"  id="associateImg" alt="associate picture">
    <div class="smallText" id="firstName">${associateRecord.firstName}</div>

    <h3><a href="<c:url value='calendarAssociate'/>">Calendar</a></h3>

    <h3><a href="<c:url value='scheduler'/>">Scheduler</a></h3>

    <h3><a href="<c:url value='associate_clients'/>">Clients</a></h3>

    <h3><a href="<c:url value='associateSettings'/>">Settings</a></h3>

    <h3><a href="<c:url value='updateAssociate'/>"><strong>Messages</strong></a></h3>

    <h3><a href="<c:url value='associateIndex'/>">Account</a></h3>

    <h3><a href="<c:url value='logoutAssociate'/>">Logout</a></h3>
</aside>

<section id="section1">
    <div id="identifier"><h1>Notifications</h1></div>
    <section id="sectionA">
        <form id="upDateForm" action="<c:url value='updateProfile'/>" method="post">
            <fieldset>
                <strong>update information</strong>
                <table id="updateTable">
                    <c:if test="${!empty validationErrorFlag}">
                        <tr>
                            <td colspan="2" style="text-align:left">
                                <span class="error smallText">Please provide valid entries for the following field(s):
                                    <c:if test="${!empty firstnameError}">
                                        <br><span class="indent"><strong>first name</strong> (ex., John)</span>
                                    </c:if>
                                    <c:if test="${!empty lastnameError}">
                                        <br><span class="indent"><strong>last name</strong> (ex., Doe)</span>
                                    </c:if>
                                    <c:if test="${!empty emailError}">
                                        <br><span class="indent"><strong>email</strong> (ex., j.doe@vise.com)</span>
                                    </c:if>
                                    <c:if test="${!empty passwordError}">
                                        <br><span class="indent"><strong>password</strong> (must be 8 characters)</span>
                                    </c:if>
                                    <c:if test="${!empty confirmpasswordError}">
                                        <br><span class="indent"><strong>confirm password</strong> (passwords must match)</span>
                                    </c:if>
                                    <c:if test="${!empty securityanswerError}">
                                        <br><span class="indent"><strong>security answer</strong> (please provide answer)</span>
                                    </c:if>
                                    <c:if test="${!empty homephoneError}">
                                        <br><span class="indent"><strong>home phone</strong> (ex., 999-999-9999)</span>
                                    </c:if>
                                    <c:if test="${!empty workphoneError}">
                                        <br><span class="indent"><strong>work phone</strong> (ex., 999-999-9999)</span>
                                    </c:if>
                                    <c:if test="${!empty mobilephoneError}">
                                        <br><span class="indent"><strong>mobile phone</strong> (ex., 999-999-9999)</span>
                                    </c:if>
                                    <c:if test="${!empty otherphoneError}">
                                        <br><span class="indent"><strong>other phone</strong> (ex., 999-999-9999)</span>
                                    </c:if>
                                    <c:if test="${!empty addressError}">
                                        <br><span class="indent"><strong>address</strong> (ex., 123 Main St)</span>
                                    </c:if>
                                    <c:if test="${!empty cityError}">
                                        <br><span class="indent"><strong>city</strong> (ex., Homewood)</span>
                                    </c:if>
                                    <c:if test="${!empty stateError}">
                                        <br><span class="indent"><strong>state</strong> two letter state code (ex., AL)</span>
                                    </c:if>
                                    <c:if test="${!empty zipError}">
                                        <br><span class="indent"><strong>zip</strong> 5 or 9 digit zip code</span>
                                    </c:if>
                                </span>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td colspan="2" style="text-align:center">
                            <c:if test="${!empty successMessage}">
                                <span class="good" >${successMessage}</span>
                            </c:if>
                            <c:if test="${!empty errorMessage}">
                                <span class="error">${errorMessage}</span>
                            </c:if></td>
                    </tr>
                    <tr>
                        <td><label for="firstname">first name:</label></td>
                        <td><input type="text"
                                   size="48"
                                   required
                                   maxlength="45"
                                   id="firstname"
                                   name="firstname"
                                   autofocus
                                   value="${associateRecord.firstName}"></td>
                    </tr>
                    <tr>
                        <td><label for="lastname:">last name:</label></td>
                        <td><input type="text"
                                   size="48"
                                   required
                                   maxlength="45"
                                   id="lastname"
                                   name="lastname"
                                   value="${associateRecord.lastName}"></td>
                    </tr>
                    <tr>
                        <td><label for="email">email:</label></td>
                        <td><input type="text"
                                   size="48"
                                   required
                                   maxlength="45"
                                   id="email"
                                   name="email"
                                   value="${associateRecord.email}"></td>
                    </tr>
                    <tr>
                        <td><label for="homephone">home phone:</label></td>
                        <td><input type="text"
                                   size="48"
                                   required
                                   maxlength="16"
                                   id="homephone"
                                   name="homephone"
                                   placeholder="999-999-9999"
                                   pattern="\d{3}[\-]\d{3}[\-]\d{4}"
                                   title="Must be 999-999-9999 format"
                                   required
                                   value="${associateInfo.homePhone}"></td>
                    </tr>
                    <tr>
                        <td><label for="workphone">work phone:</label></td>
                        <td><input type="text"
                                   size="48"
                                   maxlength="16"
                                   id="workphone"
                                   name="workphone"
                                   placeholder="999-999-9999"
                                   pattern="\d{3}[\-]\d{3}[\-]\d{4}"
                                   title="Must be 999-999-9999 format"
                                   value="${associateInfo.workPhone}"></td>
                    </tr>
                    <tr>
                        <td><label for="mobliephone">mobile phone:</label></td>
                        <td><input type="text"
                                   size="48"
                                   maxlength="16"
                                   id="mobilephone"
                                   name="mobilephone"
                                   placeholder="999-999-9999"
                                   pattern="\d{3}[\-]\d{3}[\-]\d{4}"
                                   title="Must be 999-999-9999 format"
                                   value="${associateInfo.mobilePhone}"></td>
                    </tr>
                    <tr>
                        <td><label for="otherphone">other phone:</label></td>
                        <td><input type="text"
                                   size="48"
                                   maxlength="16"
                                   id="otherphone"
                                   name="otherphone"
                                   placeholder="999-999-9999"
                                   pattern="\d{3}[\-]\d{3}[\-]\d{4}"
                                   title="Must be 999-999-9999 format"
                                   value="${associateInfo.otherPhone}"></td>
                    </tr>
                    <tr>
                        <td><label for="address">address:</label></td>
                        <td><input type="text"
                                   size="48"
                                   required
                                   maxlength="45"
                                   id="address"
                                   name="address"
                                   value="${associateInfo.address}"></td>
                    </tr>
                    <tr>
                        <td><label for="city">city:</label></td>
                        <td><input type="text"
                                   size="48"
                                   required
                                   maxlength="45"
                                   id="city"
                                   name="city"
                                   value="${associateInfo.city}"></td>
                    </tr>
                    <tr>
                        <td><label for="state" class="ui-widget" style="font-size: medium">state:</label></td>
                        <td><input type="text" 
                                   name="state"
                                   required
                                   id="state" 

                                   required maxlength="2" 
                                   placeholder="2-character code"
                                   title="Please enter a 2 character code state"
                                   value="${associateInfo.state}"></td>
                    </tr>
                    <tr>
                        <td><label for="zip">zip:</label></td>
                        <td><input type="text"
                                   size="48"
                                   required
                                   maxlength="10"
                                   id="zip"
                                   pattern="\d{5}([\-]\d{4})?"
                                   title="Please enter a 5 or 9 digit zip code"
                                   name="zip"
                                   value="${associateInfo.zip}"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="reset" id="reset" value="Reset Fields">&nbsp;
                            <input type="submit" id="button" value="Update">
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
        <br>

        <form id="updatePassword" action="<c:url value='updatePassword'/>" method="post">
            <fieldset>
                <strong>change password</strong>
                <table id="updateTable">
                    <c:if test="${!empty validationPwdErrorFlag}">
                        <tr>
                            <td colspan="2" style="text-align:left">
                                <span class="error smallText">Please provide valid entries for the following field(s):
                                    <c:if test="${!empty passwordError}">
                                        <br><span class="indent"><strong>password</strong> must at least 8 characters</span>
                                    </c:if>
                                    <c:if test="${!empty confirmPasswordError}">
                                        <br><span class="indent"><strong>confirm password</strong> passwords must match</span>
                                    </c:if>
                                </span>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td colspan="2" style="text-align:center">
                            <c:if test="${!empty successPwdMessage}">
                                <span class="good" >${successPwdMessage}</span>
                            </c:if>
                            <c:if test="${!empty errorPwdMessage}">
                                <span class="error">${errorPwdMessage}</span>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="password">password:</label></td>
                        <td><input type="password"
                                   maxlength="45"
                                   id="password"
                                   name="password"
                                   value="${password}"></td>
                    </tr>
                    <tr>
                        <td><label for="confirmPassword">confirm password:</label></td>
                        <td><input type="password"
                                   maxlength="45"
                                   id="confirmPassword"
                                   name="confirmPassword"
                                   value="${confirmPassword}"></td>
                    </tr>
                    <tr>
                        <td><label>&nbsp;</label></td>
                        <td><input type="submit" id="button" value="Change"></td>
                    </tr>
                </table>
            </fieldset>
        </form>
        <br>
        <form id="updateSecure" action="<c:url value='updateSecurityQuestion'/>" method="post">
            <fieldset>
                <table id="updateTable">
                    <strong>change security question and answer</strong>
                    <c:if test="${!empty validationSecureErrorFlag}">
                        <tr>
                            <td colspan="2" style="text-align:left">
                                <span class="error smallText">Please provide valid entries for the following field(s):
                                    <c:if test="${!empty securityAnswerError}">
                                        <br><span class="indent"><strong>security answer</strong> please enter a security answer</span>
                                    </c:if>
                                </span>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td colspan="2" style="text-align:center">
                            <c:if test="${!empty successSecureMessage}">
                                <span class="good" >${successSecureMessage}</span>
                            </c:if>
                            <c:if test="${!empty errorSecureMessage}">
                                <span class="error">${errorSecureMessage}</span>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="securityQuestion">question:</label></td>
                        <td><select name="securityQuestion">
                                <c:forEach var="questions" items="${questions}">
                                    <option value="${questions.question}">${questions.question}</option>
                                </c:forEach>
                            </select></td>
                    </tr>
                    <tr>
                        <td><label for="securityAnswer">answer:</label></td>
                        <td><input type="text"
                                   size="40"
                                   maxlength="45"
                                   id="securityAnswer"
                                   name="securityAnswer"
                                   title="Please enter a security answer."
                                   value="${securityAnswer}"></td>
                    </tr>
                    <tr>
                        <td><label>&nbsp;</label></td>
                        <td><input type="submit" id="button" value="Change"></td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </section>
</section>

