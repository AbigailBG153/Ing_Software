
document.addEventListener("DOMContentLoaded", function() {
    const btnSignIn = document.querySelector(".sign-in"),
        btnSignUp = document.querySelectorAll(".sign-up"),
        btnSignUpOcup = document.querySelectorAll(".sign-up-ocup"),
        btnSignUpUser1 = document.querySelector(".sign-up-user1"),
        btnSignUpUser2 = document.querySelector(".sign-up-user2"),
        formRegisterUser = document.querySelector(".register-user"),
        formRegisterOcupation = document.querySelector(".register-ocupation"),
        formLogin = document.querySelector(".login");
        formOcupation = document.querySelector(".ocupation-type"),
        formUser = document.querySelector(".user-type");

    btnSignIn.addEventListener("click", e => {
        e.preventDefault();
        formOcupation.classList.add("hide");
        formUser.classList.add("hide");
        formRegisterUser.classList.add("hide");
        formRegisterOcupation.classList.add("hide");
        formLogin.classList.remove("hide");
    });

    btnSignUp.forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault();
            formOcupation.classList.add("hide");
            formUser.classList.remove("hide");
            formLogin.classList.add("hide");
            formRegisterUser.classList.add("hide");
            formRegisterOcupation.classList.add("hide");
        });
    });

    btnSignUpOcup.forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault();
            formOcupation.classList.add("hide");
            formUser.classList.add("hide");
            formLogin.classList.add("hide");
            formRegisterUser.classList.add("hide");
            formRegisterOcupation.classList.remove("hide");
        });
    });

    btnSignUpUser1.addEventListener("click", e => {
            e.preventDefault();
            formOcupation.classList.add("hide");
            formUser.classList.add("hide");
            formRegisterUser.classList.remove("hide");
            formRegisterOcupation.classList.add("hide");
            formLogin.classList.add("hide");
    });

    btnSignUpUser2.addEventListener("click", e => {
        e.preventDefault();
        formOcupation.classList.remove("hide");
        formUser.classList.add("hide");
        formRegisterUser.classList.add("hide");
        formRegisterOcupation.classList.add("hide");
        formLogin.classList.add("hide");
    });

});
