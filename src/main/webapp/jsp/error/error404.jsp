<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../imports.jspf" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>404</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="${absolutePath}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="container">
    <div class="error__code">404</div>
    <div class="error__message">${requestScope.error_message}</div>
</div>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>


