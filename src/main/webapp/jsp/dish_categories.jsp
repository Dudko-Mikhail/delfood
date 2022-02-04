<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="imports.jspf" %>

<fmt:message scope="page" var="title" key="page.title.dish_categories"/>

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
    <h1>Dish Categories</h1>
    <div>
        <c:forEach varStatus="index" var="category" items="${dish_categories}">
            <c:out value="${category.name}"/>
            <img src="${category.imageUrl}" alt="Category page"/>
            <a href="${absolutePath}/controller?command=go_to_category_page&category_id=${category.id}">${category.id}</a>
        </c:forEach>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
