$(document).ready(function () {
   $('.burger__menu').click(function (e) {
      $('.header__menu, .burger__menu').toggleClass('active');
      $('body').toggleClass('lock');
   });
});