
<script src="js/jquery.validate.js" type="text/javascript"></script>

<c:set var="view" value="checkout" scope="session" />

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
                phone: {
                    required: true,
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
                    required: true,
                    rangelength: [2, 2]
                },
                zip: {
                    required: true,
                    minlength: 5
                },
                creditcard: {
                    required: true,
                    creditcard: true
                }
            }
        });
    });              // end validate
</script>

<script>
    $(document).ready(function() {
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

<div id="singleColumn">

    <h2>checkout</h2>

    <p>In order to purchase the items in your shopping cart, please provide us with the following information:</p>

    <c:if test="${!empty orderFailureFlag}">
        <p class="error">We were unable to process your order. Please try again!</p>
    </c:if>

    <form id="checkoutForm" action="<c:url value='purchase'/>" method="post">
        <table id="checkoutTable">
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
                            <c:if test="${!empty phoneError}">
                                <br><span class="indent"><strong>phone</strong> (e.g., 999-999-9999)</span>
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
                                <br><span class="indent"><strong>zip</strong> (e.g., 12345 or 12345-1234)</span>
                            </c:if>
                            <c:if test="${!empty ccNumberError}">
                                <br><span class="indent"><strong>credit card</strong> (e.g., 1111-2222-3333-4444)</span>
                            </c:if>

                        </span>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td><label for="firstname">first name:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="45"
                           autofocus
                           id="firstname"
                           name="firstname"
                           required
                           title="Please enter your first name"
                           <c:choose><c:when test="${!empty client.firstName}"> value="${client.firstName}"
                               </c:when>
                               <c:otherwise> value="${param.firstname}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><label for="lastname:">last name:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="lastname"
                           name="lastname"
                           required
                           title="Please enter your last name"
                           <c:choose><c:when test="${!empty client.lastName}"> value="${client.lastName}"
                               </c:when>
                               <c:otherwise> value="${param.lastname}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><label for="company">company:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="company"
                           name="company"
                           <c:choose><c:when test="${!empty client.company}"> value="${client.company}"
                               </c:when>
                               <c:otherwise> value="${param.company}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><label for="email">email:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="email"
                           name="email"
                           required
                           title="Enter a valid email address"
                           placeholder="j.doe@vise.com"
                           <c:choose><c:when test="${!empty client.email}"> value="${client.email}"
                               </c:when>
                               <c:otherwise> value="${param.email}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><label for="phone">phone:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="16"
                           id="phone"
                           name="phone"
                           required
                           placeholder="999-999-9999"
                           pattern="\d{3}[\-]\d{3}[\-]\d{4}"
                           title="Must be 999-999-9999 format"
                           <c:choose><c:when test="${!empty client.homePhone}"> value="${client.homePhone}"
                               </c:when>
                               <c:otherwise> value="${param.homePhone}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><label for="address">address:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="address"
                           required
                           name="address"
                           required
                           title="Please enter your address"
                           <c:choose><c:when test="${!empty client.address}"> value="${client.address}"
                               </c:when>
                               <c:otherwise> value="${param.address}"></c:otherwise>
                    </c:choose>
                    <br>
                </td>
            </tr>
            <tr>
                <td><label for="city">city:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="city"
                           name="city"
                           required
                           title="Please enter your city"
                           <c:choose><c:when test="${!empty client.city}"> value="${client.city}"
                               </c:when>
                               <c:otherwise> value="${param.city}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><label for="state">state:</label></td>
                <td class="stateInputField" class="ui-widget">
                    <input type="text" 
                           size="2"
                           name="state"
                           id="state" 
                           required 
                           maxlength="2" 
                           placeholder="2-character code"
                           required
                           title="Please enter a 2 character code state"
                           <c:choose><c:when test="${!empty client.state}"> value="${client.state}"
                               </c:when>
                               <c:otherwise> value="${param.state}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><label for="zip">zip:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="10"
                           id="zip"
                           name="zip"
                           required
                           pattern="\d{5}([\-]\d{4})?"
                           title="Please enter a 5 or 9 digit zip code"
                           <c:choose><c:when test="${!empty client.zip}"> value="${client.zip}"
                               </c:when>
                               <c:otherwise> value="${param.zip}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><label for="creditcard">credit card:</label></td>
                <td >
                    <input type="text"
                           size="31"
                           maxlength="19"
                           id="creditcard"
                           name="creditcard"
                           <%--pattern="\d{4}-\d{4}-\d{4}-\d{4}$"--%>
                           placeholder="1111-2222-3333-4444"
                           required
                           title="Please enter a valid credit card number "
                           <c:choose><c:when test="${!empty client.ccNumber}"> value="${client.ccNumber}"
                               </c:when>
                               <c:otherwise> value="${param.creditcard}"></c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr> 
                <td><label for="creditCartType">credit card type:</label></td>
                <td><select name="creditCardType">
                        <option value="Visa">Visa</option>
                        <option value="Mastercard">Mastercard</option>
                        <option value="AmEx">American Express</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="creditCardExpirationMonth">expiration date:</label></td>
                <td><select name="creditCardExpirationMonth">
                        <option value="01">01
                        <option value="02">02
                        <option value="03">03
                        <option value="04">04 
                        <option value="05">05
                        <option value="06">06
                        <option value="07">07
                        <option value="08">08 
                        <option value="09">09
                        <option value="10">10
                        <option value="11">11
                        <option value="12">12 
                    </select>
                    <select name="creditCardExpirationYear">
                        <c:forEach var="year" items="${creditCardYears}">
                            <option value="${year}">${year}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="submit" value="purchase" id="button">
                </td>
            </tr>
        </table>
    </form>

    <div id="infoBox">

        <ul>
            <li>Next-day delivery is guaranteed</li>
                <%--           <li>A &#36; ${initParam.deliverySurcharge}
                delivery surcharge is applied to all purchase orders</li>--%>
        </ul>

        <figure class="testBox">

            <figcaption>summary</figcaption>	
            <table>
                <thead>
                    <tr>
                        <th></th>
                        <th>description</th>
                        <th>qty</th>
                        <th>price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">

                        <c:set var="product" value="${cartItem.product}"/>

                        <tr  class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                            <td>
                                <img id="imgsmall" src="${initParam.productImagePath}${product.name}.png"
                                     alt="${product.name}">
                            </td>

                            <td>${product.name}</td>
                            <td>${cartItem.quantity}</td>

                            <td>
                                <fmt:formatNumber type="currency"
                                                  currencySymbol="&#36; "
                                                  value="${cartItem.total}"/>
                                <%--&#36; ${cartItem.total}--%> 
                                <br>
                                <span class="smallText">( 
                                    <fmt:formatNumber type="currency"
                                                      currencySymbol="&#36; "
                                                      value="${product.price}"/>
                                    <%--&#36; ${product.price}--%> / unit )</span>
                            </td>
                        </tr>
                    </c:forEach>
                <tfoot>
                    <tr>
                        <td></td>
                        <td></td>
                        <td>subtotal:</td>

                        <td>
                            <fmt:formatNumber type="currency"
                                              currencySymbol="&#36; "
                                              value="${cart.subtotal}"/>
                            <%--&#36; ${cart.subtotal}--%></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td class="total">total:</td>
                        <td class="total">
                            <fmt:formatNumber type="currency"
                                              currencySymbol="&#36; "
                                              value="${cart.total}"/>
                            <%-- &#36; ${cart.total}--%></td>
                    </tr>
                </tfoot>
            </table>
        </figure>
    </div>
</div>