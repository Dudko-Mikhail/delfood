<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="imports.jspf" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title>Home</title>
</head>
<body>
<header>
    <jsp:include page="common/header.jsp"/>
</header>
<ul>
    <li><a href="${pageContext.request.contextPath}/jsp/sign_in.jsp">Sign In</a></li>
    <li><a href="${pageContext.request.contextPath}/jsp/sign_up.jsp">Sign Up</a></li>
</ul>
<footer>
    <jsp:include page="common/footer.jsp"/>
</footer>
</body>
</html>
