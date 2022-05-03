<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf"%>

<fmt:message var="empty_dishes" key="message.empty.dishes"/>
<fmt:message var="add_to_cart" key="action.add_to_cart"/>
<fmt:message var="dish_image_alt" key="alt.dish"/>
<fmt:message var="gram" key="gram"/>

<c:set var="base_image" value="${absolutePath}/img/default_dish.png" scope="page"/>
<c:set var="order" value="${sessionScope.order_manager}"/>

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
    <c:choose>
        <c:when test="${empty requestScope.dishes}"><div class="fs-4 text-center">${empty_dishes}</div></c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 gy-2">
            <c:forEach var="dish" items="${requestScope.dishes}">
                <div data-item="${dish.id}" class="item__container col">
                    <div class="dish__content">
                        <div class="dish__name fs-2">${dish.name}</div>
                        <div class="scalable__image">
                            <c:set var="imageUrl" value="${dish.imageUrl != null ? dish.imageUrl : base_image}"/>
                            <img src="${imageUrl}" alt="${dish_image_alt}"/>
                        </div>
                        <div class="mb-2">${dish.description}
                            <span class="d-inline-block">(${dish.weight} ${gram})</span>
                        </div>
                    </div>
                    <div class="dish__footer">
                        <div>
                            <span>${dish.price}</span>
                            <span class="currency">BYN</span>
                        </div>
                        <c:set var="isPresent" value="${order.containsItem(dish.id)}"/>
                        <div class="counter__wrapper">
                            <button data-action="increase" class="${isPresent ? 'd-none' : ''} add__to__cart">${add_to_cart}</button>
                            <div class="quantity__counter ${isPresent ? '' : 'd-none'}">
                                <button data-action="decrease" class="counter__button">-</button>
                                <span class="quantity">${order.getItemQuantity(dish.id)}</span>
                                <button data-action="increase" class="counter__button">+</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

</div>
<jsp:include page="common/footer.jsp"/>
<script src="${absolutePath}/js/dish_category.js"></script>
</body>
</html>
