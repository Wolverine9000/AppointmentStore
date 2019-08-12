<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>--%>

<%--<sql:query var="categories" dataSource="jdbc/appointment">
    SELECT * FROM category
</sql:query>--%>
<c:set var="view" value="home" scope="session" />
<%-- HTML markup starts below --%>

<div id="indexLeftColumn">
    <div id="welcomeText">
        <p style="font-size: larger">Welcome to the online Salon Store and Appointment System.</p>

        <p>Enjoy browsing and learning more about our online booking system as
            well as bringing you name brand hair and body products
            and other salon and beauty items to your doorstep.</p>
    </div>
</div>

<div id="indexRightColumn">
    <c:forEach var="category" items="${categories}">
        <div class="categoryBox">
            <a href="viewCategory?${category.category_id}" method="post">
                <span class="categoryLabel"></span>
                <span class="categoryLabelText">${category.name}</span>

                <img src="${initParam.categoryImagePath}${category.name}.jpg"
                     alt="${category.name}">
            </a>
        </div>
    </c:forEach>
</div>
