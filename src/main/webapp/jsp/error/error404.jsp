<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../imports.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error 404</title>
</head>
<body>
    <h1>Error 404</h1>
    <h1>Current page ${page}</h1>
    <h2>Status=${pageContext.errorData.statusCode}</h2>
</body>
</html>
