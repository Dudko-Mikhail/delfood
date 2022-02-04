<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<fmt:message scope="page" var="title" key="page.title.home"/>

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
<div class="row">
    <ul>
        <li><a href="${absolutePath}/jsp/sign_in.jsp">Sign In</a></li>
        <li><a href="${absolutePath}/controller?command=go_to_sign_up_page">Sign Up</a></li>
        <li><a href="${absolutePath}/controller?command=sign_out">Sign Out</a></li>
    </ul>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
