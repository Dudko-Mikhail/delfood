<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${dish.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="${absolutePath}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="common/header.jsp"/>
<div>
    <h2>Dish characteristics</h2>
    <p>${dish.name}</p>
    <p>${dish.category}</p>
    <p>${dish.weight}</p>
    <p>${dish.description}</p>
    <p>${dish.price}</p>
    <p>${dish.discount}</p>
    <p>${dish.imageUrl}</p>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>