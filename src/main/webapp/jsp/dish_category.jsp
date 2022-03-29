<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf"%>

<fmt:message var="empty_dishes" key="message.empty.dishes"/>
<fmt:message var="add_to_cart" key="action.add_to_cart"/>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${requestScope.category_name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="${absolutePath}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <h1 class="text-center">${requestScope.category_name}</h1>
    <hr/>
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3">
        <c:if test="${empty requestScope.dishes}">
            <span class="w-100 text-center">${empty_dishes}</span>
        </c:if>
        <c:forEach var="dish" items="${requestScope.dishes}">
            <div class="col">
                <div class="text-center fs-2">${dish.name}</div>
                <div class="scalable__image">
                    <c:choose>
                        <c:when test="${dish.imageUrl ne null}">
                            <img src="${dish.imageUrl}" alt="dish image">
                        </c:when>
                        <c:otherwise>
                            <img src="${absolutePath}/img/default_dish.png" alt="Dish image">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row">
                    <div class="col">${dish.price} BYN</div>
                    <div class="col">
                        <a href="${absolutePath}/controller?command=add_order_item&dish_id=${dish.id}&amount=1">${add_to_cart}</a> <!-- TODO add command later -->
                    </div>
                    <div>
                        <a href="${absolutePath}/controller?command=go_to_dish_page&dish_id=${dish.id}">
                            Подробнее
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>