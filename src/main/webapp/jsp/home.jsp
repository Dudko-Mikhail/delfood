<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="imports.jspf" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title>Home</title>
</head>
<body>
<div class="container">
    <header>
        <jsp:include page="common/header.jsp"/>
    </header>
    <p>Locale: ${language}</p>
    <ul>
        <li><a href="${absolutePath}/jsp/sign_in.jsp">Sign In</a></li>
        <li><a href="${absolutePath}/controller?command=go_to_sign_up_page">Sign Up</a></li>
        <li><a href="controller?command=sign_out">Sign Out</a></li>
    </ul>
    <footer>
        <jsp:include page="common/footer.jsp"/>
    </footer>
</div>
</body>
</html>
