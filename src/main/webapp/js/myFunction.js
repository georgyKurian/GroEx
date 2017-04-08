/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    $("#email").focus();

    $("#member_form").submit(
            function (event) {
                var isValid = true;

                var emailPattern = /\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}\b/;
                var email = $("#email").val();

                if (email = "") {
                   $("#spann").text("This field is required");
                    isValid = false;
                } else if (!emailPattern.test(email)) {
                    $("#spann").text("Must be a valid email address");
                    isValid = false;
                } else {
                    $("#spann").text("");
                }

                var password = $("#password").val();

                if (password > 6) {
                    $("#spann1").text("Enter password less than 6 digits");
                    isValid = false;
                } else {
                   $("#spann1").text("");
                }
            }
    )
});

