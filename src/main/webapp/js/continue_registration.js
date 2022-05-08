$(document).ready(function () {
    const sendButton = document.getElementById('send_verification_message');
    sendButton.addEventListener('click', e => {
        const recipientEmail = document.querySelector('input[name=email_to_verify]').value;
        sendVerificationMessageRequest(recipientEmail);
        animateTenSecondsCounter(sendButton);
    });

    function animateTenSecondsCounter(button) {
        const timerSpan = $('<span class="_counter">10</span>');
        button.appendChild(timerSpan[0]);
        let countdown = 10;
        button.setAttribute("disabled", "disabled");
        setTimeout(tickAction, 0, sendButton, countdown);
    }

    function tickAction(button, countdown) {
        if (countdown > 0) {
            const timerElem = button.querySelector("._counter");
            timerElem.textContent = countdown.toString();
            countdown--;
            setTimeout(tickAction, 1000, button, countdown);
        } else {
            button.removeAttribute("disabled");
            button.querySelector("._counter").remove();
        }
    }

    function sendVerificationMessageRequest(recipientEmail) {
        $.ajax({
            method: 'POST',
            url: 'action',
            data: {
                command: 'send_email_verification_message',
                email: recipientEmail
            },
            error: () => {
                alert(`Failed to send verification email to ${recipientEmail}`);
            }
        });
    }
});