<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../imports.jspf" %>

<fmt:message var="home" key="nav.home"/>
<fmt:message var="categories" key="nav.categories"/>
<fmt:message var="sign_in" key="nav.sign_in"/>
<fmt:message var="sign_up" key="nav.sign_up"/>
<fmt:message var="sign_out" key="nav.user.dropdown.sign_out"/>
<fmt:message var="profile" key="nav.user.dropdown.profile"/>
<fmt:message var="reservation" key="nav.reservation"/>
<fmt:message var="cart" key="nav.cart"/>
<fmt:message var="localization_alt" key="alt.localization"/>

<c:set var="totalProductQuantity" value="${sessionScope.order_manager.totalProductQuantity}"/>
<c:set var="totalProductQuantity" value="${totalProductQuantity ne 0 ? totalProductQuantity : ''}"/>

<header class="header">
    <div class="container px-4">
        <div class="header__body">
            <a href="#" class="header__logo fs-2">
                DelFood
            </a>
            <nav class="header__menu">
                <ul class="menu__list">
                    <li class="d-block d-md-none d-xl-block">
                        <a href="${absolutePath}/jsp/home.jsp" class="menu__link">${home}</a>
                    </li>
                    <li>
                        <a href="${absolutePath}/controller?command=go_to_categories_page"
                           class="menu__link">${categories}</a>
                    </li>
                    <li><a href="" class="menu__link">${reservation}</a></li>
                    <li>
                        <a href="${absolutePath}/controller?command=go_to_cart_page" class="menu__link">
                            <span>${cart}</span>
                            <span id="cart_size" class="ms-1">${totalProductQuantity}</span>
                        </a>
                    </li>
                </ul>
            </nav>
            <ul class="menu__list">
                <c:choose>
                    <c:when test="${sessionScope.user_login ne null}">
                        <li>
                            <div class="dropdown">
                                <a href="#" class="menu__link dropdown-toggle" id="user_menu"
                                   data-bs-toggle="dropdown" aria-expanded="false">
                                    <span>${sessionScope.user_login}</span>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="user_menu">
                                    <li>
                                        <a class="dropdown-item"
                                           href="${absolutePath}/controller?command=go_to_profile_page">${profile}</a>
                                    </li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li>
                                        <a class="dropdown-item"
                                           href="${absolutePath}/controller?command=sign_out">${sign_out}</a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="#" data-bs-toggle="modal" data-bs-target="#sign_in_modal"
                               class="menu__link">${sign_in}</a>
                        </li>
                        <li class="d-none d-sm-block d-md-none d-xl-block">
                            <a href="#" data-bs-toggle="modal" data-bs-target="#sign_up_modal"
                               class="menu__link">${sign_up}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li>
                    <a href="#" class="header__icon d-block link-dark text-decoration-none" id="localization"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="${absolutePath}/img/planet.png" alt="${localization_alt}">
                    </a>
                    <ul class="language__list dropdown-menu dropdown-menu-end" aria-labelledby="localization">
                        <c:forEach var="locale" items="${sessionScope.languages}">
                            <c:set var="link" value="${absolutePath}/controller?command=change_locale&language=${locale}"/>
                            <li><a class="dropdown-item" href="${locale ne sessionScope.language.name ? link : '#'}">${locale}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li>
                    <div class="burger__menu">
                        <span></span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</header>

<c:if test="${sessionScope.user_login eq null}">
    <%-- Modal Sign In --%>
    <%@include file="../modal/sign_in.jspf" %>
    <%-- Modal Sign Up --%>
    <%@include file="../modal/sign_up.jspf" %>
</c:if>

<div class="wrapper">