<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<fmt:message scope="page" var="title" key="page.title.profile"/>
<fmt:message var="email_label" key="label.email"/>
<fmt:message var="login_label" key="label.login"/>
<fmt:message var="login_label" key="label.login"/>
<fmt:message var="email_label" key="label.email"/>
<fmt:message var="password_label" key="label.password"/>
<fmt:message var="confirm_password_label" key="label.confirm_password"/>
<fmt:message var="first_name_label" key="label.first_name"/>
<fmt:message var="last_name_label" key="label.last_name"/>
<fmt:message var="phone_number_label" key="label.phone_number"/>
<fmt:message var="old_password" key="message.old_password"/>
<fmt:message var="new_password" key="message.new_password"/>
<fmt:message var="confirm_new_password" key="message.confirm_new_password"/>
<fmt:message var="change_password" key="action.change_password"/>
<fmt:message var="save" key="action.save_changes"/>
<fmt:message var="cancel" key="action.cancel"/>
<fmt:message var="common_info" key="header.common_info"/>

<c:set var="user" value="${requestScope.user}"/>

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
        <form id="update_profile_form" method="post" action="${absolutePath}/controller" autocomplete="off" novalidate>
            <input type="hidden" name="command" value="update_profile">

            <div class="fs-3 mb-2">${common_info}</div>
            <hr/>
            <div class="row border mx-1 p-3 row-cols-1 row-cols-md-2">

                <div class="mb-2 col">
                    <div class="form-label">${login_label}*</div>
                    <div class="form-control">${user.login}</div>
                </div>

                <div class="mb-2 col">
                    <div class="form-label">${email_label}*</div>
                    <div class="form-control">${user.email}</div>
                </div>

                <div class="mb-2 col">
                    <label for="edit_first_name" class="form-label">${first_name_label}</label>
                    <input class="form-control" type="text" id="edit_first_name" name="first_name"
                           value="${user.firstName}">
                </div>

                <div class="mb-2 col">
                    <label for="edit_last_name" class="form-label">${last_name_label}</label>
                    <input class="form-control" type="text" id="edit_last_name" name="last_name"
                           value="${user.lastName}">
                </div>

                <div class="mb-2 col">
                    <label for="edit_phone_number" class="form-label">${phone_number_label}*</label>
                    <input class="form-control" type="text" id="edit_phone_number" name="phone_number"
                           value="${user.phoneNumber}">
                </div>

            </div>

            <div class="fs-3 mb-2">Смена пароля</div>
            <hr/>

            <div class="row py-2 row-cols-1 row-cols-md-2">

                <a class="btn btn-primary" data-bs-toggle="collapse" href="#change_password_collapse" role="button"
                   aria-expanded="false" aria-controls="collapseExample">${change_password}</a>

                <div class="collapse mb-2" id="change_password_collapse">
                    <div class="card card-body">
                        <div class="mb-2">
                            <label for="old_password" class="form-label">${old_password}</label>
                            <input class="form-control" type="password" id="old_password" name="old_password"/>
                        </div>

                        <div class="mb-2">
                            <label for="new_password" class="form-label">${new_password}</label>
                            <input class="form-control" type="password" id="new_password" name="new_password"/>
                        </div>

                        <div class="mb-2">
                            <label for="confirm_new_password" class="form-label">${confirm_new_password}</label>
                            <input class="form-control" type="password" id="confirm_new_password"
                                   name="confirm_new_password"/>
                        </div>
                    </div>
                </div>
            </div>



            <button type="button" class="btn btn-primary">${save}</button>
            <button type="button" class="btn btn-warning">${cancel}</button>
        </form>
    </div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
