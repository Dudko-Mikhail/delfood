<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>
<fmt:message var="login" key="field.login"/>
<fmt:message var="password" key="field.password"/>
<fmt:message var="sign_in" key="btn.sign_in"/>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title>Sign In</title>
</head>
<jsp:include page="common/header.jsp"/>
<body>
    <h1>${sign_in}</h1>
    <div>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <label for="login">${login}</label>
            <input type="text" id="login" name="login" required/>

            <label for="password">${password}</label>
            <input type="password" id="password" name="password" required>
            <button type="submit">${sign_in}</button>
        </form>
    </div>
</body>
<jsp:include page="common/footer.jsp"/>
</html>
