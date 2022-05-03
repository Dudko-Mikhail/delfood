<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="imports.jspf" %>

<fmt:message scope="page" var="title" key="page.title.home"/>

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

    <div class="row">
        <ul>
            <li><a href="${absolutePath}/controller?command=go_to_sign_up_page">Sign Up</a></li>
            <li><a href="${absolutePath}/controller?command=sign_out">Sign Out</a></li>
        </ul>
        <form>
            <label for="test_input" class="form-label">Experiment input</label>
            <input type="text" id="test_input" class="mb-2 bg-light form-control"/>
        </form>
        <div class="form-label">Ajax response:</div>
        <div id="answer"></div>
    </div>

    <div class="container w-50">
        <div class="form-floating mb-3">
            <input type="number" class="form-control" id="duration" placeholder="4">
            <label for="duration">Продолжительность</label>
        </div>

        <div class="form-floating config mb-3">
            <input type="number" class="form-control" id="tick" placeholder="1">
            <label for="tick">Тиков в секунду</label>
            <button class="mt-1 btn btn-primary" data-action="start">
                Старт
            </button>
        </div>
    </div>

    <div class="timer">
        <div class="big__circle">
            <div class="small__circle">
                <span></span>
            </div>
        </div>
    </div>

</div>
<jsp:include page="common/footer.jsp"/>
<script>
    $(document).ready(function () {
        if (!String.prototype.format) {
            String.prototype.format = function() {
                let args = arguments;
                return this.replace(/{(\d+)}/g, function(match, number) {
                    return typeof args[number] != 'undefined'
                        ? args[number]
                        : match;
                });
            };
        }

        $('#test_input').blur(function () {
            query();
        })

        function query() {
            $.ajax({
                method: "POST",
                url: "${absolutePath}/localization",
                dataType: "text",
                data: {
                    key: $('#test_input').val()
                },
                success: function(responseText) {
                    $('#answer').html(responseText);
                }
            });
        }


    });
</script>
<script src="../js/timer.js"></script>
</body>
</html>
