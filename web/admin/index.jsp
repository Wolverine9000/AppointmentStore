
<div id="adminMenu" class="alignLeft">
    <p><a href="<c:url value='viewCustomers'/>">view all customers</a></p>

    <p><a href="<c:url value='viewOrders'/>">view all orders</a></p>

    <p><a href="<c:url value='unProcessedOrders'/>">un-processed orders</a></p>

    <p><a href="<c:url value='findAdmin.jsp'/>">search for orders</a></p>

    <p><a href="<c:url value='processedOrders'/>">processed</a></p>

    <p><a href="<c:url value='writeAssociatePicture'/>">write picture</a></p>

    <p><a href="<c:url value='logout'/>">log out</a></p>
</div>

<%-- customerList is requested --%>
<c:if test="${!empty notFoundMessage}"></c:if>
<p class="error">${notFoundMessage}</p>

<c:if test="${!empty customerList}">

    <table id="adminTable" class="detailsTable">

        <tr class="header">
            <th colspan="4">customers&nbsp;&nbsp;found&nbsp;&nbsp;(${numberOfCustomers})</th>
        </tr>

        <tr class="tableHeading">
            <td>customer id</td>
            <td>name</td>
            <td>email</td>
            <td>phone</td>
        </tr>

        <c:forEach var="customer" items="${customerList}" varStatus="iter">

            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                onclick="document.location.href = 'customerRecord?${customer.id}';">

                <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="customerRecord?${customer.id}" class="noDecoration">${customer.id}</a></td>
                <td><a href="customerRecord?${customer.id}" class="noDecoration">${customer.firstName}</a></td>
                <td><a href="customerRecord?${customer.id}" class="noDecoration">${customer.email}</a></td>
                <td><a href="customerRecord?${customer.id}" class="noDecoration">${customer.homePhone}</a></td>
            </tr>

        </c:forEach>

    </table>

</c:if>

<%-- orderList is requested --%>
<c:if test="${!empty orderList}">

    <table id="adminTable" class="detailsTable">

        <tr class="header">
            <th colspan="5">orders&nbsp;${adminMessage}&nbsp;found&nbsp;&nbsp;(${numberOfInvoices})
        </tr>

        <tr class="tableHeading">
            <td>invoice id</td>
            <td>confirmation number</td>
            <td>amount</td>
            <td>date created</td>
            <td>processed</td>
        </tr>

        <c:forEach var="order" items="${orderList}" varStatus="iter">

            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                onclick="document.location.href = 'orderRecord?${order.invoiceNumber}';">

                <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="orderRecord?${order.invoiceNumber}" class="noDecoration">${order.invoiceNumber}</a></td>
                <td><a href="orderRecord?${order.invoiceNumber}" class="noDecoration">${order.confirmationNumber}</a></td>
                <td><a href="orderRecord?${order.invoiceNumber}" class="noDecoration">
                        <fmt:formatNumber type="currency"
                                          currencySymbol="&#36; "
                                          value="${order.totalAmount}"/></a></td>

                <td><a href="orderRecord?${order.invoiceNumber}" class="noDecoration">
                        <fmt:formatDate value="${order.invoiceDate}"
                                        type="both"
                                        dateStyle="short"
                                        timeStyle="short"/></a>
                </td>
                <td><a href="orderRecord?${order.y_n}" class="noDecoration">${order.y_n}</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<%-- customerRecord is requested --%>
<c:if test="${!empty customerRecord}">

    <table id="adminTable" class="detailsTable">

        <tr class="header">
            <th colspan="2">customer details</th>
        </tr>
        <tr>
            <td style="width: 290px"><strong>customer id:</strong></td>
            <td>${customerRecord.id}</td>
        </tr>
        <tr>
            <td><strong>name:</strong></td>
            <td>${customerRecord.firstName} ${customerRecord.lastName}</td>
        </tr>
        <tr>
            <td><strong>company:</strong></td>
            <td>${customerRecord.company}</td>
        </tr>
        <tr>
            <td><strong>email:</strong></td>
            <td>${customerRecord.email}</td>
        </tr>
        <tr>
            <td><strong>phone:</strong></td>
            <td>${customerRecord.homePhone}</td>
        </tr>
        <tr>
            <td><strong>address:</strong></td>
            <td>${customerRecord.address}</td>
        </tr>
        <tr>
            <td><strong>city:</strong></td>
            <td>${customerRecord.city}</td>
        </tr>
        <tr>
            <td><strong>state</strong></td>
            <td>${customerRecord.state}</td>
        </tr>
        <tr>
            <td><strong>zip</strong></td>
            <td>${customerRecord.zip}</td>
        </tr>
        <tr>
            <td><strong>member level:</strong></td>
            <td>${customerRecord.memberLevel}</td>
        </tr>
        <tr>
            <td><strong>credit card (last 4 digits)</strong></td>
            <td>${customerRecord.creditCardFormat}</td>
        </tr>

        <tr><td colspan="2" style="padding: 0 20px"><hr></td></tr>

        <tr class="tableRow"
            onclick="document.location.href = 'customerInvoices?${customerRecord.id}';">
            <td colspan="2">
                <%-- Anchor tag is provided in case JavaScript is disabled --%>
                <a href="customerInvoices?${customerRecord.id}" class="noDecoration">
                    <strong>view customer invoices &#x279f;</strong></a></td>
        </tr>
    </table>

</c:if>

<%-- orderRecord is requested --%>
<c:if test="${!empty orderRecord}">

    <table id="adminTable" class="detailsTable">

        <tr class="header">
            <th colspan="2">order summary&nbsp;<a href="processInvoice?${orderRecord.invoiceNumber}">process</a></th>
        </tr>
        <tr>
            <td><strong>order id:</strong></td>
            <td>${orderRecord.invoiceNumber}</td>
        </tr>
        <tr>
            <td><strong>name:</strong></td>
            <td>${orderRecord.client.firstName}&nbsp;${orderRecord.client.lastName}</td>
        </tr>
        <tr>
            <td><strong>company:</strong></td>
            <td>${orderRecord.client.company}</td>
        </tr>
        <tr>
            <td><strong>confirmation number:</strong></td>
            <td>${orderRecord.confirmationNumber}</td>
        </tr>
        <tr>
            <td><strong>date processed:</strong></td>
            <td>
                <fmt:formatDate value="${orderRecord.invoiceDate}"
                                type="both"
                                dateStyle="long"
                                timeStyle="short"/></td>
        </tr>

        <tr>
            <td colspan="4">
                <table class="embedded detailsTable">
                    <tr class="tableHeading">
                        <td class="rigidWidth">product</td>
                        <td class="rigidWidth">quantity</td>
                        <td class="rigidWidth">unit price</td>
                        <td>price</td>
                    </tr>

                    <tr><td colspan="4" style="padding: 0 20px"><hr></td></tr>

                    <c:forEach var="orderedProduct" items="${orderRecord.lineItems}" varStatus="iter">

                        <tr>
                            <td>
                                ${orderedProduct.product.name}
                            </td>
                            <td>
                                ${orderedProduct.quantity}
                            </td>
                            <td> 
                                &#36;&nbsp;${orderedProduct.product.price}
                            </td>
                            <td class="confirmationPriceColumn">
                                <fmt:formatNumber type="currency" currencySymbol="&#36; "
                                                  value="${orderedProduct.product.price * orderedProduct.quantity}"/>
                            </td>
                        </tr>

                    </c:forEach>

                    <tr><td colspan="4" style="padding: 0 20px"><hr></td></tr>

                    <tr>
                        <td colspan="3" id="deliverySurchargeCellLeft"><strong>delivery surcharge:</strong></td>
                        <td id="deliverySurchargeCellRight">
                            <fmt:formatNumber type="currency"
                                              currencySymbol="&#36; "
                                              value="${initParam.deliverySurcharge}"/></td>
                    </tr>

                    <tr>
                        <td colspan="3" id="totalCellLeft"><strong>total amount:</strong></td>
                        <td id="totalCellRight">
                            <fmt:formatNumber type="currency"
                                              currencySymbol="&#36; "
                                              value="${orderRecord.totalAmount}"/></td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr><td colspan="4" style="padding: 0 20px"><hr></td></tr>

        <tr class="tableRow"
            onclick="document.location.href = 'customerRecord?${orderRecord.client.id}';">
            <td colspan="2">
                <%-- Anchor tag is provided in case JavaScript is disabled --%>
                <a href="customerRecord?${orderRecord.client.id}" class="noDecoration">
                    <strong>view customer details &#x279f;</strong></a></td>
        </tr>
    </table>
</c:if>