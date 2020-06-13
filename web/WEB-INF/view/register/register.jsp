<script src="js/jquery.validate.js" type="text/javascript"></script>

<script type="text/javascript">

    $(document).ready(function() {
        $("#checkoutForm").validate({
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
                password: {
                    required: true,
                    minlength: 8
                },
                confirmpassword: {
                    required: true,
                    minlength: 8
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

<div id="singleColumn">

    <h2>register</h2>

    <p>Please enter information and then click the 'register' button.<br>A confirmation email message will be send to your email address.</p>

    <form id="checkoutForm" action="<c:url value='registerUser'/>" method="post">
        <table id="registerTable">
            <c:if test="${!empty validationErrorFlag}">
                <tr>
                    <td colspan="2" style="text-align:left">
                        <span class="error smallText">Please provide valid entries for the following field(s):

                            <c:if test="${!empty firstnameError}">
                                <br><span class="indent"><strong>first name</strong> (e.g., John)</span>
                            </c:if>
                            <c:if test="${!empty lastnameError}">
                                <br><span class="indent"><strong>last name</strong> (e.g., Doe)</span>
                            </c:if>
                            <c:if test="${!empty emailError}">
                                <br><span class="indent"><strong>email</strong> (e.g., j.doe@vise.com)</span>
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
                                <br><span class="indent"><strong>home phone</strong> (e.g., 2223334444)</span>
                            </c:if>
                            <c:if test="${!empty workphoneError}">
                                <br><span class="indent"><strong>work phone</strong> (e.g., 2223334444)</span>
                            </c:if>
                            <c:if test="${!empty mobilephoneError}">
                                <br><span class="indent"><strong>mobile phone</strong> (e.g., 2223334444)</span>
                            </c:if>
                            <c:if test="${!empty addressError}">
                                <br><span class="indent"><strong>address</strong> (e.g., 123 Main St)</span>
                            </c:if>
                            <c:if test="${!empty cityError}">
                                <br><span class="indent"><strong>city</strong> (e.g., Homewood)</span>
                            </c:if>
                            <c:if test="${!empty stateError}">
                                <br><span class="indent"><strong>state</strong> (e.g., AL)</span>
                            </c:if>
                            <c:if test="${!empty zipError}">
                                <br><span class="indent"><strong>zip</strong> (e.g., 35209)</span>
                            </c:if>

                        </span>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td><label for="firstname">first name:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="45"
                           id="firstname"
                           name="firstname"
                           value="${param.firstname}">
                </td>
            </tr>
            <tr>
                <td><label for="lastname:">last name:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="45"
                           id="lastname"
                           name="lastname"
                           value="${param.lastname}">
                </td>
            </tr>
            <tr>
                <td><label for="company">company:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="45"
                           id="company"
                           name="company"
                           value="${param.company}">
                </td>
            </tr>
            <tr>
                <td><label for="email">email:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="45"
                           id="email"
                           name="email"
                           title="Enter a valid email address"
                           value="${param.email}">
                </td>
            </tr>
            <tr>
                <td><label for="password">password:</label></td>
                <td class="inputField">
                    <input type="password"
                           size="48"
                           maxlength="45"
                           id="password"
                           name="password"
                           value="${param.password}">
                </td>
            </tr>
            <tr>
                <td><label for="confirmpassword">confirm password:</label></td>
                <td class="inputField">
                    <input type="password"
                           size="48"
                           maxlength="45"
                           id="confirmpassword"
                           name="confirmpassword"
                           value="${param.confirmpassword}">
                </td>
            </tr>
            <tr>
                <td><label for="securityQuestion">security question:</label></td>
                <td class="inputField">
                    <select name="securityQuestion">
                        <c:forEach var="questions" items="${questions}">
                            <option value="${questions.questionId}">${questions.question}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="securityanswer">security answer:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="45"
                           id="securityanswer"
                           name="securityanswer"
                           value="${param.securityanswer}">
                </td>
            </tr>
            <tr>
                <td><label for="homephone">home phone:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="16"
                           id="homephone"
                           name="homephone"
                           placeholder="999-999-9999"
                           pattern="\d{3}[\-]\d{3}[\-]\d{4}"
                           title="Must be 999-999-9999 format"
                           value="${param.homephone}">
                </td>
            </tr>
            <tr>
                <td><label for="workphone">work phone:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="16"
                           id="workphone"
                           name="workphone"
                           placeholder="999-999-9999"
                           pattern="\d{3}[\-]\d{3}[\-]\d{4}"
                           title="Must be 999-999-9999 format"
                           value="${param.workphone}">
                </td>
            </tr>
            <tr>
                <td><label for="mobilephone">mobile phone:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="16"
                           id="mobilephone"
                           name="mobilephone"
                           placeholder="999-999-9999"
                           pattern="\d{3}[\-]\d{3}[\-]\d{4}"
                           title="Must be 999-999-9999 format"
                           value="${param.mobilephone}">
                </td>
            </tr>
            <tr>
                <td><label for="address">address:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="45"
                           id="address"
                           name="address"
                           value="${param.address}">
                    <br>
                </td>
            </tr>
            <tr>
                <td><label for="city">city:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="45"
                           id="city"
                           name="city"
                           value="${param.city}">
                </td>
            </tr>
            <tr>
                <td><label for="state">state:</label></td>
                <td class="inputField">
                    <select name="state">
                        <c:forEach var="state" items="${states}">
                            <option value="${state}">${state}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="zip">zip:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="48"
                           maxlength="5"
                           id="zip"
                           name="zip"
                           value="${param.zip}">
                </td>
            </tr>          
            <tr>
                <td colspan="2">
                    <input type="submit" value="register">
                </td>
            </tr>
        </table>
    </form>

</div>
<p class="error">${errorMessage}</p>