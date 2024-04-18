/*==================== SHOW NAVBAR ====================*/
const showMenu = (headerToggle, navbarId) => {
    const toggleBtn = document.querySelector(`.${headerToggle}`),
        nav = document.querySelector(`#${navbarId}`);

    // Validar que las variables existen
    if (toggleBtn && nav) {
        toggleBtn.addEventListener('click', () => {
            // Agregamos la clase show-menu al elemento nav
            nav.classList.toggle('show-menu');
        });
    }
}



showMenu('nav-open-btn', 'navbar');

/*==================== LINK ACTIVE ====================*/
const linkColor = document.querySelectorAll('.navbar-link');

function colorLink() {
    // Remover la clase 'active' de todos los enlaces
    linkColor.forEach(l => l.classList.remove('active'));
    // Agregar la clase 'active' al enlace actual
    this.classList.add('active');
}

// Agregar evento 'click' a cada enlace
linkColor.forEach(l => l.addEventListener('click', colorLink));


