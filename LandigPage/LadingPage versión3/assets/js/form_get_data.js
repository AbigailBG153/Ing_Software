document.addEventListener("DOMContentLoaded", function() {
    let tipoWorker = localStorage.getItem('tipoWorker') || "";
    document.querySelectorAll('.trainer, .nutritionist').forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            tipoWorker = this.classList.contains('trainer') ? 'entrenador' : 'nutricionista';
            console.log("Tipo seleccionado:", tipoWorker);
            localStorage.setItem('tipoWorker', tipoWorker);
        });
    });

    let tipoUser = localStorage.getItem('tipoUser') || "";

    document.querySelectorAll('.sign-up-user1, .sign-up-user2').forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            tipoUser = this.classList.contains('sign-up-user1') ? 'cliente' : 'trabajador';
            console.log("Tipo seleccionado:", tipoUser);
            localStorage.setItem('tipoUser', tipoUser);

            const dataRegister1 = document.querySelector(".form-data.user1");
            dataRegister1.addEventListener("submit", e => {
                e.preventDefault();
                console.log(tipoUser, ', ',tipoWorker)
                const fd = new FormData(dataRegister1);
                fd.append('Tipo de usuario',tipoUser);
                const formDataObj = {};
                for (const [key, value] of fd) {
                    formDataObj[key] = value;
                }
                localStorage.setItem('cliente', JSON.stringify(formDataObj));

                window.location.href = 'login_register.html';

                // Realizar más acciones si es necesario
            });

            const dataRegister2 = document.querySelector(".form-data.user2");
            dataRegister2.addEventListener("submit", e => {
                e.preventDefault();
                const fd = new FormData(dataRegister2);
                fd.append('Tipo de usuario',tipoUser);
                fd.append('Tipo de trabajador', tipoWorker);
                const formDataObj = {};
                for (const [key , value] of fd) {
                    formDataObj[key] = value;
                }
                localStorage.setItem('trabajador', JSON.stringify(formDataObj));

                window.location.href = 'login_register.html';
                
                // Realizar más acciones si es necesario
            });
        });
    });
});
