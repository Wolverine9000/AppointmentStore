<link rel="stylesheet" type="text/css" href="css/registerEntry.css">
<script src="js/jquery.validate.js" type="text/javascript"></script>

<script type="text/javascript">

    $(document).ready(function() {
        $("#checkoutForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true
                },
                emailConfirm: {
                    required: true,
                    email: true
                }
            }
        });
    });
</script>

<div id="registerEntryBox">

    <h2>register</h2>

    <p>Submit your email address address.<br>A confirmation email message will be sent to your email address.</p>

    <form id="checkoutForm" action="<c:url value='enterRegister'/>" method="post">
        <table class="registerEntryTable">
            <c:if test="${!empty validationErrorFlag}">
                <tr>
                    <td colspan="2" style="text-align:left">
                        <br><span class="error smallText">Please provide valid entries for the following field(s):

                            <c:if test="${!empty emailError}">
                                <br><span class="indent"><strong>email address</strong> (e.g., j.doe@vise.com)</span>
                            </c:if>
                            <c:if test="${!empty emailConfirmError}">
                                <br><span class="indent"><strong>confirm email address</strong> (email addresses must match)</span>
                            </c:if>
                        </span>
                    </td>
                </tr>
            </c:if>
        </table>

        <fieldset>
            <p class="error">${errorMessage}</p><br>
            <label for="email">email address:</label>
            <input type="email" name="email" id="email" 
                   autofocus required
                   value="${param.email}"><br>
            <label for="emailConfirm">confirm email:</label>
            <input type="email" name="emailConfirm" id="emailConfirm" 
                   required
                   value="${param.emailConfirm}"><br>
        </fieldset>
        <fieldset>
            <h4 class="legendTitle">submit your request</h4>
            <label>&nbsp;</label>
            <input class="registerEntryInput" type="submit" id="button" value="Submit">
        </fieldset>
    </form>

</div>
