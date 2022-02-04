<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<fmt:message scope="page" var="title" key="page.title.sign_up"/>
<fmt:message var="login" key="form.login"/>
<fmt:message var="email" key="form.email"/>
<fmt:message var="password" key="form.password"/>
<fmt:message var="repeat_password" key="form.repeat_password"/>
<fmt:message var="first_name" key="form.first_name"/>
<fmt:message var="last_name" key="form.last_name"/>
<fmt:message var="phone_number" key="form.phone_number"/>
<fmt:message var="sign_up" key="form.action.sign_up"/>

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
