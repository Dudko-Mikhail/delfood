<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<fmt:setBundle scope="session" basename="localization.localized_data"/>
<fmt:message var="login" key="field.login"/>
<fmt:message var="email" key="field.email"/>
<fmt:message var="password" key="field.password"/>
<fmt:message var="repeat_password" key="field.repeat_password"/>
<fmt:message var="first_name" key="field.first_name"/>
<fmt:message var="last_name" key="field.last_name"/>
<fmt:message var="phone_number" key="field.phone_number"/>
<fmt:message var="sign_up" key="btn.sign_up"/>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link rel="stylesheet" type="text/css" href="../css/registration.css">
    <title>Sing Up</title>
</head>
<body>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <div class="registration_form">
    <h2>${sign_up}</h2>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" id="command" name="command" value="sign_up">

            <label for="login">${login}</label>
            <input type="text" id="login" name="login" required/>

            <label for="email">${email}</label>
            <input type="email" id="email" name="email" required/>

            <label for="first_name">${first_name}</label>
            <input type="text" id="first_name" name="first_name"/>

            <label for="last_name">${last_name}</label>
            <input type="text" id="last_name" name="last_name"/>

            <label for="phone_number">${phone_number}</label>
            <input type="tel" id="phone_number" name="phone_number" required/>

            <label for="password">${password}</label>
            <input type="password" id="password" name="password" required>

            <label for="repeat_password">${repeat_password}</label>
            <input type="password" id="repeat_password" name="repeat_password" required>

            <button type="submit">${sign_up}</button>
        </form>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
