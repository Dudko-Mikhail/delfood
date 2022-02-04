<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<fmt:message scope="page" var="title" key="page.title.sign_in"/>
<fmt:message var="login_email" key="form.login_email"/>
<fmt:message var="email" key="form.email"/>
<fmt:message var="password" key="form.password"/>
<fmt:message var="sign_in" key="form.action.sign_in"/>

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
    <h1>${sign_in}</h1>
    <div>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" id="command" name="command" value="sign_in">

            <label for="login_email">${login_email}</label>
            <input type="text" id="login_email" name="login_email" required/>

            <label for="password">${password}</label>
            <input type="password" id="password" name="password" required>
            <button type="submit">${sign_in}</button>
        </form>
    </div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
