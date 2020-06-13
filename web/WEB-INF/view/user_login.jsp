
<script src="js/jquery.validate.js" type="text/javascript"></script>

<script type="text/javascript">

    $(document).ready(function() {
        $("#checkoutForm").validate({
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
    });
</script>

<figure id="clientLoginTable">

    <p class="error">${message}</p>
    <p id="registerText" class="registerFont">new user?&nbsp;&nbsp;
        <a href="register_enter">please register</a></p>

    <form id="checkoutForm" action="<c:url value='checkuser'/>" method=post>
        <table>
            <c:if test="${!empty validationErrorFlag}">
                <tr>
                    <td colspan="2" style="text-align:left">
                        <span class="error smallText">Please provide valid entries for the following field(s):

                            <c:if test="${!empty emailError}">
                                <br><span class="indent"><strong>email address</strong> (e.g., j.doe@vise.com)</span>
                            </c:if>
                            <c:if test="${!empty passwordError}">
                                <br><span class="indent"><strong>password</strong>&nbsp;must be at least 8 characters</span>
                            </c:if>
                        </span>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td><label for="emailAddress">email address:</label></td>
                <td><input type="text" maxlength="45" class="inputField"
                           name="emailAddress" required value="${param.email}" 
                           id="emailAddress"
                           autofocus>
                </td>
            </tr>
            <tr>
                <td><label for="password">password:</label></td>
                <td><input type="password" maxlength="45" class="inputField"
                           name="password" required value="${param.password}"
                           id="password">
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <p><input type="submit" id="button" value="submit"></p> 
                </td>
            </tr>            
        </table>

        <p><a href="#">forgot&nbsp;&nbsp;password?</a></p>
    </form>
</figure>