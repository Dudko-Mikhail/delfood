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
<%--    <script src="${absolutePath}/js/validation.js"></script>--%>
</footer>

<c:if test="${requestScope.verification_result}">
    <!-- Modal Account activation -->
    <%@include file="../modal/account_activation.jspf" %>

    <script>
        let activationModal = new bootstrap.Modal(document.getElementById('account_activation'));
        activationModal.show();
    </script>
</c:if>

<c:if test="${requestScope.email_to_verify ne null}">
    <!-- continue_registration_modal -->
    <%@include file="../modal/continue_registration.jspf"%>

    <script>
        let continueRegistrationModal = new bootstrap.Modal(document.getElementById('continue_registration'));
        continueRegistrationModal.show();
    </script>
</c:if>
<c:if test="${requestScope.sign_up_result eq false}">
    <script>
        let signUpModal = new bootstrap.Modal(document.getElementById("sign_up_modal"));
        signUpModal.show();
    </script>
</c:if>

<c:if test="${requestScope.show_sign_in_modal}">
    <script>
        let signInModal = new bootstrap.Modal(document.getElementById('sign_in_modal'));
        signInModal.show();
    </script>
</c:if>

<c:if test="${requestScope.show_blocked_account_modal}">
    <%@include file="../modal/blocked_account.jspf"%>
    <script>
        let blockedAccount = new bootstrap.Modal(document.getElementById("blocked_account"));
        blockedAccount.show();
    </script>
</c:if>