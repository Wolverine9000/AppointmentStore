<link rel="stylesheet" type="text/css" href="../css/associateCSS/associateCSS_login.css">
<div id="main-section">
    <aside>
        <div id="aside-div">
            <form id="associateLogin"action="<c:url value='checkAssociate'/>" method=post>
                <fieldset>
                    <table>
                        <thead>
                            <tr>
                                <th></th><th>Welcome</th>
                            </tr>
                            <tr>
                                <th></th><th>Please log into your account</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${!empty validationErrorFlag}">
                                <tr>
                                    <td colspan="2" style="text-align:left">
                                        <span class="error smallText">Please provide valid entries for the following field(s):
                                            <c:if test="${!empty emailError}">
                                                <br><span><strong>email address</strong>&nbsp;(e.g., j.doe@vise.com)</span>
                                            </c:if>
                                            <c:if test="${!empty passwordError}">
                                                <br><span><strong>password</strong>&nbsp;must be at least 8 characters</span>
                                            </c:if>
                                            <c:if test="${notFoundMessage eq true}">
                                                <br><span><strong>email &amp;&nbsp;password</strong>&nbsp;<br>Associate not Found</span>
                                                </c:if>
                                        </span>
                                    </td>
                                    <!--<td></td>-->
                                </tr>
                            </c:if>
                            <tr>
                                <td><label for="emailAddress">Email Address</label></td>
                                <td><input type="email" name="emailAddress" id="emailAddress" autofocus required
                                           value="${param.emailAddress}" placeholder="email address"></td>
                            </tr>
                            <tr>
                                <td><label for="password">Password</label></td>
                                <td><input type="password" name="password" id="password" required
                                           value="${param.password}" placeholder="password"></td>
                            </tr>
                            <tr>
                                <td><label for="rememberme">Remember Me</label></td>
                                <td><input type="checkbox" name="rememberme" id="rememberme"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><button type="submit" class="login"></button></td>
                            </tr>

                        </tbody>
                    </table>
                </fieldset>
                <%--
    <fieldset id="submit-fieldset">
        <table>
            <tbody>
                
                <tr>
                    <td></td>
                    <td><label>Forgot Username</label></td>
                </tr>
                
                <tr>
                    <td></td>
                    <td><label>Not a Member?</label></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <a href="<c:url value='#'/>">
                            <img src="../img/nav/register.png" height="83" width="150" alt="accountLogin"/>
                        </a>
                    </td>
                --%>
                </tr>

                </tbody>
                </table>
                </fieldset>
            </form>
        </div>
    </aside>
    <section>
        <h1 style="text-align: center;">Under Construction</h1>
    </section>
</div>
