<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf"%>

<fmt:message var="empty_dishes" key="message.empty.dishes"/>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${category_name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="${absolutePath}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="common/header.jsp"/>
    <h1>${category_name}</h1>
    <div>
        <c:if test="${empty dishes}">
            <c:out value="${empty_dishes}"/>
        </c:if>
        <c:forEach var="dish" items="${dishes}">
            <div>
                <p>${dish.name}</p>
                <p>${dish.price}</p>
                <a href="${absolutePath}/controller?command=go_to_dish_page&dish_id=${dish.id}">Link</a>
                <hr/>
            </div>
        </c:forEach>
    </div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
