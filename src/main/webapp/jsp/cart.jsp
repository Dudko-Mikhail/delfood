<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<fmt:message var="title" key="page.title.cart"/>
<fmt:message var="make_order" key="action.make_order"/>
<fmt:message var="empty_cart" key="message.empty_cart"/>
<fmt:message var="cart" key="header.cart"/>
<fmt:message var="dish_icon" key="alt.dish_icon"/>

<c:set var="base_image" value="${absolutePath}/img/default_dish.png" scope="page"/>
<c:set var="order" value="${sessionScope.order_manager}" scope="page"/>
<c:set var="isEmptyCart" value="${empty requestScope.dishes}" scope="page"/>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="${absolutePath}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <h1 class="text-center">${cart}</h1>
    <div class="content__wrapper shadow-sm px-4 py-3">
            <c:if test="${!isEmptyCart}">
                <c:forEach var="dish" items="${requestScope.dishes}">
                    <c:set var="orderItem" value="${order.getOrderItemById(dish.id).get()}"/>
                    <div data-item="${dish.id}" class="row cart__item py-2">
                        <div class="col dish__icon">
                            <c:set var="imageUrl" value="${dish.imageUrl != null ? dish.imageUrl : base_image}"/>
                            <img src="${imageUrl}" alt="${dish_icon}"/>
                        </div>
                        <div class="col">${dish.name}</div>
                        <div class="col">
                            <div class="quantity__counter">
                                <button data-action="decrease" class="counter__button">-</button>
                                <span class="quantity">${order.getItemQuantity(dish.id)}</span>
                                <button data-action="increase" class="counter__button">+</button>
                            </div>
                        </div>
                        <div class="col">
                            <span class="total_item_price">
                                <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${orderItem.discountedPrice}"/>
                            </span>
                            <span class="currency">BYN</span>
                        </div>
                        <button data-action="remove" class="btn btn-close"></button>
                    </div>
                </c:forEach>
                <%-- TODO ?????????? ???????????????? ?????????????? - 1 ????????????, ???????? + counter - 2 ???????????? --%>
                <%-- TODO ?????????????? order-fix: ?????? width <  --%>
                <div class="order_statistics">
                    <div>
                        <span>?????????? ???????? ????????????: </span>
                        <span id="total_price"><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${order.price}"/></span>
                        <span class="currency">BYN</span>
                    </div>
                    <div>
                        <span>???????????? ????????????: </span>
                        <span id="total_discount"><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${order.totalDiscount}"/></span>
                        <span class="currency">BYN</span>
                    </div>
                    <div>
                        ???????????? ????????????????: <fmt:formatNumber maxFractionDigits="2" value="${order.orderPrice}"/>
                    </div>
                    <a class="btn btn-warning" href="#">${make_order}</a>
                </div>
            </c:if>
        <div class="_empty fs-4 ${isEmptyCart ? '' : 'd-none'}">${empty_cart}</div>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
<script src="${absolutePath}/js/cart.js"></script>
</body>
</html>
