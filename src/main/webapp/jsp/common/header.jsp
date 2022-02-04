<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../imports.jspf" %>

<fmt:message var="home" key="nav.home"/>
<fmt:message var="categories" key="nav.categories"/>

<header>
    <div id="nav">
        <ul>
            <li>DelFood</li>
            <li><a href="${absolutePath}/jsp/home.jsp">${home}</a></li>
            <li><a href="${absolutePath}/controller?command=go_to_categories_page">${categories}</a></li>
            <li><a href="${absolutePath}/controller?command=change_locale&language=ru">RU</a></li>
            <li><a href="${absolutePath}/controller?command=change_locale&language=en_US">EN</a></li>

            <li>Current user: ${login}</li>
        </ul>
    </div>
    <hr/>
</header>
