<c:set var="view" value="category" scope="session" />

<div id="categoryLeftColumn">
    <c:forEach var="category" items="${categories}">

        <c:choose>
            <c:when test="${category.name == selectedCategory.name}">
                <div class="categoryButton" id="selectedCategory">
                    <span class="categoryText">
                        ${category.name}
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="viewCategory?${category.category_id}" class="categoryButton">
                    <div class="categoryText">
                        ${category.name}
                    </div>
                </a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<%-- services is requested --%>
<c:if test="${category_id eq 5}">

    <div id="categoryRightColumn">
        <p id="categoryTitle">${selectedCategory.name}</p>

        <table id="productTable">
            <c:forEach var="service" items="${categoryServices}" varStatus="iter">

                <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                    <td>
                        <img src="${initParam.productImagePath}${service.name}.png"
                             alt="${service.name}">
                    </td>
                    <td>
                        ${service.name}
                        <br>
                        <span class="smallText">${service.description}</span>
                    </td>
                    <td>
                        &#36; ${service.priceCurrencyFormat} / unit
                        <br>service time:&nbsp;${service.serviceTime}&nbsp;min
                    </td>
                    <td>
                        <form action="book.jsp" method="post">
                            <input type="hidden"
                                   name="productId"
                                   value="${service.serviceProductId}">
                            <input type="submit"
                                   value="book appointment" id="button">
                        </form>
                    </td>
                </tr>

            </c:forEach>
        </table>
    </div>
</c:if>

<%-- services is not requested --%>
<c:if test="${category_id ne 5}">

    <div id="categoryRightColumn">
        <p id="categoryTitle">${selectedCategory.name}</p>

        <table id="productTable">
            <c:forEach var="product" items="${categoryProducts}" varStatus="iter">

                <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                    <td>
                        <img src="${initParam.productImagePath}${product.name}.png"
                             alt="${product.name}">
                    </td>
                    <td>
                        ${product.name}
                        <br>
                        <span class="smallText">${product.description}</span>
                    </td>
                    <td>
                        &#36; ${product.priceCurrencyFormat} / unit
                    </td>
                    <td>
                        <form action="addToCart" method="post">
                            <input type="hidden"
                                   name="productId"
                                   value="${product.productId}">
                            <input type="submit"
                                   value="add to cart" id="button">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>