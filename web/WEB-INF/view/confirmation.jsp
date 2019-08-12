
<div id="singleColumn">

    <p id="confirmationText">
        <strong>Your order has been successfully processed and will be delivered within 48 hours.</strong>
        <br><br>
        Please keep a note of your confirmation number:
        <strong>${invoice.confirmationNumber}</strong>
        <br>
        If you have a question concerning your order, feel free to <a href="#">contact us</a>.
        <br><br>
        Thank you for shopping at the Appointment and Salon Store!
    </p>
</div>

<div class="summaryColumn" >

    <table id="orderSummaryTable" class="detailsTable">
        <tr class="header">
            <th colspan="3">order summary</th>
        </tr>

        <tr class="tableHeading">
            <td>product</td>
            <td>quantity</td>
            <td>price</td>
        </tr>

        <c:forEach var="item" items="${invoice.lineItems}" varStatus="iter">

            <tr class="${((iter.index % 2) != 0) ? 'lightBlue' : 'white'}">
                <td>${item.product.name}</td>
                <td class="quantityColumn">
                    ${item.quantity}
                </td>
                <td class="confirmationPriceColumn">
                    &#36; ${item.product.price * item.quantity}
                </td>
            </tr>

        </c:forEach>

        <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

        <tr class="lightBlue">
            <td colspan="2" id="deliverySurchargeCellLeft"><strong>delivery surcharge:</strong></td>
            <td id="deliverySurchargeCellRight">&#36; ${initParam.deliverySurcharge}</td>
        </tr>

        <tr class="lightBlue">
            <td colspan="2" id="totalCellLeft"><strong>total:</strong></td>
            <td id="totalCellRight">&#36; ${invoice.invoiceTotalCurrencyFormat}</td>
        </tr>

        <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

        <tr class="lightBlue">
            <td colspan="3" id="dateProcessedRow"><strong>date processed:</strong>
                ${invoice.invoiceDate}
            </td>
        </tr>
    </table>
</div>
<div>
    <table class="summaryTableBox">
        <thead>
            <tr>
                <th colspan="2">shipping address</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <tr></td><td><td>${client.firstName}&nbsp;${client.lastName}</td></tr>
            <tr><td></td><td>${client.company}</td></tr>
            <tr><td></td><td>${client.address}</td><td></td></tr>
            <tr><td></td><td>${client.city},&nbsp;${client.state}&nbsp;${client.zip}</td></tr>

            <tr><td><strong>email:</strong></td><td>${client.email}</td></tr>
            <tr><td><strong>phone:</strong></td><td>${client.homePhone}</td></tr>
        </tbody>
    </table>
    <p class="error">${errorMessage}</p>

</div>