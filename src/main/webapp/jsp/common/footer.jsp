<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../imports.jspf" %>

<fmt:message var="privacy" key="text.privacy"/>
<fmt:message var="phone" key="label.phone_number"/>

</div>

<footer class="footer">
    <div class="container">
        <ul class="footer__list">
            <li>${phone}: +375 (25) 955-32-23</li>
            <li>Dudko Mikhail. ${privacy} &#169; 2021-2022</li>
        </ul>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="${absolutePath}/js/burger.js"></script>
</footer>
