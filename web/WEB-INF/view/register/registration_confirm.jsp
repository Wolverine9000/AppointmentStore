<%-- 
    Document   : registration_confirm
    Created on : Jun 9, 2012, 1:43:45 AM
    Author     : williamdobbs
--%>

<script src="js/jquery.validate.js" type="text/javascript"></script>

<script type="text/javascript">

    $(document).ready(function(){
        $("#checkoutForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true,
                    maxlength: 45
                },
                regCode: {
                    required: true,
                    minlenght: 4,
                    maxlength: 6,
                    number: true
                }
                
            }
        });
    });
</script>

<%--<p class="error">${message}</p>--%>
<p id="registerText" class="registerFont">
    Please enter email address and registration code:</p>

<form id="checkoutForm"action="<c:url value='processRegistration'/>" method=post>
    <div id="loginBox">
        <c:if test="${!empty validationErrorFlag}">
                <tr>
                    <td colspan="2" style="text-align:left">
                        <span class="error smallText">Please provide valid entries for the following field(s):
                            
                            <c:if test="${!empty emailError}">
                                <br><span class="indent"><strong>email</strong> (e.g., j.doe@vise.com)</span>
                            </c:if>
                                <c:if test="${!empty regCodeError}">
                                <br><span class="indent"><strong>registration code</strong> (check your registration code)</span>
                            </c:if>
                        </span>
                    </td>
                </tr>
            </c:if>
        <p><strong>Email Address:</strong>
            <input type="text" size="20" name="email" value="${param.email}"></p>

        <p><strong>Registration Code:</strong>
            <input type="text" size="20" name="regCode" value="${param.regCode}"></p>

        <p><input type="submit" value="submit"></p>
    </div>
</form>
<p class="error">${message}</p>
