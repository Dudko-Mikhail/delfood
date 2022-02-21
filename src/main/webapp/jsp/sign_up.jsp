<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<fmt:message scope="page" var="title" key="page.title.sign_up"/>
<fmt:message var="login" key="label.login"/>
<fmt:message var="email" key="label.email"/>
<fmt:message var="password" key="label.password"/>
<fmt:message var="repeat_password" key="label.repeat_password"/>
<fmt:message var="first_name" key="label.first_name"/>
<fmt:message var="last_name" key="label.last_name"/>
<fmt:message var="phone_number" key="label.phone_number"/>
<fmt:message var="sign_up" key="action.sign_up"/>
<fmt:message var="header_sign_up" key="header.sign_up"/>

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
    <h1 class="mb-2">${header_sign_up}</h1>
    <form class="border px-4 py-3" action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" id="command" name="command" value="sign_up">

        <div class="mb-3">
            <label for="login" class="form-label">${login}</label>
            <input type="text" class="form-control" id="login" name="login" required/>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">${email}</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="email@example.com" required/>
        </div>

        <div class="mb-3">
            <label for="first_name" class="form-label">${first_name}</label>
            <input type="text" class="form-control" id="first_name" name="first_name"/>
        </div>

        <div class="mb-3">
            <label for="last_name" class="form-label">${last_name}</label>
            <input type="text" class="form-control" id="last_name" name="last_name"/>
        </div>

        <div class="mb-3">
            <label for="phone_number" class="form-label">${phone_number}</label>
            <input type="tel" class="form-control" id="phone_number" name="phone_number" required/>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">${password}</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <div class="mb-3">
            <label for="repeat_password" class="form-label">${repeat_password}</label>
            <input type="password" class="form-control" id="repeat_password" name="repeat_password" required>
        </div>

        <button class="btn btn-primary" type="submit">${sign_up}</button>
    </form>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
