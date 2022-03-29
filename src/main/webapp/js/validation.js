'use strict'

document.addEventListener('DOMContentLoaded', function () {
    const LOGIN_MAX_LENGTH = 20;
    const EMAIL_MAX_LENGTH = 45;
    const PASSWORD_MIN_LENGTH = 4;
    const INITIALS_MAX_LENGTH = 20;
    const PHONE_NUMBER_MAX_LENGTH = 16;
    const LOGIN_PATTERN = /^[\w-]+$/;
    const EMAIL_PATTERN = /^.+@.+$/;
    const INITIALS_PATTERN = /^[^!"#$%&'()*+,\-./:;<=>?@\[\\\]^_`{|}~]*$/u; // FIXME fix initials pattern
    const PHONE_NUMBER_PATTERN = /^\d+$/; // TODO think about this pattern
    /* ['$+<=>^`|'] - \p{P} - doesn't match */
    const ErrorType = Object.freeze({
        PATTERN: 'pattern',
        TOO_LONG: 'long',
        TOO_SHORT: 'short',
        COMMON: 'common',
        EMPTY: 'empty'
    });


    function validateSignUpForm(form) {
        let login = form.querySelector('#login');
        let email = form.querySelector('#email');
        let firstName = form.querySelector('#first_name');
        let lastName = form.querySelector('#last_name');
        let phoneNumber = form.querySelector('#phone_number');
        let password = form.querySelector('#password');
        let passwordRepeat = form.querySelector('#repeat_password');
        // FIXME start

        // FIXME end
        const mapping = new Map();
        mapping.set(login, validateLogin);
        mapping.set(email, validateEmail);
        mapping.set(firstName, validateInitials);
        mapping.set(lastName, validateInitials);
        mapping.set(phoneNumber, validatePhoneNumber);
        mapping.set(password, validatePassword);
        mapping.forEach((validationFunction, field) => {
            validateField(field, validationFunction);
        });

        login.removeEventListener('input')


        validatePasswordRepeat(password, passwordRepeat);

    }

    function validateLogin(login) {
        if (!isMaxLengthValid(login.value, LOGIN_MAX_LENGTH)) {
            showError(login, ErrorType.TOO_LONG);
        }
        if (!LOGIN_PATTERN.test(login.value)) {
            showError(login, ErrorType.PATTERN);
        }
    }

    function validateEmail(email) {
        if (!isMaxLengthValid(email.value, EMAIL_MAX_LENGTH)) {
            showError(email, ErrorType.TOO_LONG);
        }
        if (!EMAIL_PATTERN.test(email.value)) {
            showError(email, ErrorType.PATTERN);
        }
    }

    function validateInitials(initials) {
        if (!isMaxLengthValid(initials.value, INITIALS_MAX_LENGTH)) {
            showError(initials, ErrorType.TOO_LONG);
        }
        if (!INITIALS_PATTERN.test(initials.value)) {
            showError(initials, ErrorType.PATTERN);
        }
    }

    function validatePhoneNumber(phoneNumber) {
        if (!isMaxLengthValid(phoneNumber.value, PHONE_NUMBER_MAX_LENGTH)) {
            showError(phoneNumber, ErrorType.TOO_LONG);
        }
        if (!PHONE_NUMBER_PATTERN.test(phoneNumber.value)) {
            showError(phoneNumber, ErrorType.PATTERN);
        }
    }

    function validatePassword(password) {
        if (!isMinLengthValid(password.value, PASSWORD_MIN_LENGTH)) {
            showError(password, ErrorType.TOO_SHORT);
        }
        if (!/\d/.test(password.value)) {
            showError(password, 'need_digit');
        }
        if (!/\p{P}/u.test(password.value)) {
            showError(password, 'need_special_character');
        }
    }

    function validatePasswordRepeat(password, passwordRepeat) {
        clearFieldValidation(passwordRepeat);
        if (password.value !== passwordRepeat.value) {
            showError(passwordRepeat, ErrorType.COMMON);
        }
        addValidationStyle(passwordRepeat);
    }

    function validateField(field, validationFunction) {
        clearFieldValidation(field);
        if (field.value === '' && field.hasAttribute('required')) {
            showError(field, ErrorType.EMPTY);
        }
        else {
            validationFunction(field);
        }
        addValidationStyle(field);
    }

    function isMinLengthValid(string, minLength) {
        return string.length >= minLength;
    }

    function isMaxLengthValid(string, maxLength) {
        return string.length <= maxLength;
    }

    function showError(element, errorType) {
        element.classList.add('is-invalid');
        let parent = element.parentElement;
        let errorElement = parent.querySelector(`.error .${errorType}`);
        errorElement.classList.add('active');
    }

    function clearFieldValidation(field) {
        field.classList.remove('is-invalid');
        field.classList.remove('is-valid');
        let parent = field.parentElement;
        let errorElements = parent.querySelectorAll('.error .active');
        Array.prototype.slice.call(errorElements)
            .forEach(activeError => {
            activeError.classList.remove('active');
        });
    }

    function hasAnyError(form) {
        return form.querySelector('.error .active') != null;
    }

    function hasError(input) { // TODO rename or remove
        return input.parentElement.querySelector('.error .active') != null;
    }

    function addValidationStyle(input) {
        const styleClass = hasError(input) ? 'is-invalid' : 'is-valid';
        input.classList.add(styleClass);
    }

    function resetForm(form) {
        cleanTempFields(form);
        const inputs = form.querySelectorAll('input');
        Array.prototype.slice.call(inputs)
            .forEach(field => clearFieldValidation(field));
    }

    function cleanTempFields(node) {
        let tempChildren = node.querySelectorAll('.temp');
        for (let child of tempChildren) {
            if (child.getAttribute('value') != null) {
                child.setAttribute('value', '');
            }
            else {
                child.remove();
            }
        }
    }

    let signUpForm = document.querySelector('#sign_up_form');

    signUpForm.addEventListener('submit', event => {
        validateSignUpForm(signUpForm);
        if (hasAnyError(signUpForm)) {
            event.preventDefault();
            event.stopPropagation();
        }
    });

    (() => {
        let forms = document.querySelectorAll("form");
        Array.prototype.slice.call(forms)
            .forEach(form => {
                form.addEventListener('reset', (event) => {
                    resetForm(form);
                });
            });

        let formsWithValidation = document.querySelector("form .need_validation");
        Array.prototype.slice.call(formsWithValidation)
            .forEach(form => {
                form.addEventListener('submit'), (event) => {

                }
            })
    })();

});


// (function () {
//     // Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
//     let forms = document.querySelectorAll('.needs-validation')
//
//     // Зацикливайтесь на них и предотвращайте отправку
//     Array.prototype.slice.call(forms)
//         .forEach(form => {
//             form.addEventListener('submit', event => {
//                 if (!form.checkValidity()) {
//                     event.preventDefault()
//                     event.stopPropagation()
//                 }
//                 form.classList.add('was-validated')
//             }, false)
//
//             form.addEventListener('reset', event => {
//                 form.classList.remove('was-validated');
//                 let nodes = form.querySelectorAll('.temp');
//                 for (let node of nodes) {
//                     if (node.getAttribute('value') != null) {
//                         node.setAttribute('value', '');
//                     }
//                     else {
//                         node.remove();
//                     }
//                 }
//             }, true)
//         })
// })()