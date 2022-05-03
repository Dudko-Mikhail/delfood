<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="imports.jspf" %>

<fmt:message scope="page" var="title" key="page.title.dish_categories"/>
<fmt:message var="alt.category" key="alt.category"/>

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
    <div class="row row-cols-1 row-cols-md-2">
    <c:forEach var="category" items="${requestScope.dish_categories}">
        <div class="col mb-2">
            <a href="${absolutePath}/controller?command=go_to_category_page&category_id=${category.id}" class="text-decoration-none">
                <div class="text-center text-black fs-4">${category.name}</div>
                <div class="scalable__image">
                    <img src="${category.imageUrl}" alt="${category_alt}"/>
                </div>
            </a>
        </div>
    </c:forEach>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
