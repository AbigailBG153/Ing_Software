document.addEventListener("DOMContentLoaded", function() {
    const boxBienvenida = document.getElementById("box-bienvenida");
    const nombreDia = document.getElementById("nombre-dia");
    const saludo = document.getElementById("saludo");
    const horaActual = new Date().getHours();
    const diaSemana = new Date().getDay(); // 0 para domingo, 1 para lunes, etc.
    let mensaje;
    let fondoSaludo;

    // Obtener el nombre del día
    const nombresDias = ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"];
    const nombreDelDia = nombresDias[diaSemana];

    nombreDia.textContent = nombreDelDia;

    if (diaSemana === 0 || diaSemana === 6) {
        mensaje = "¡Feliz fin de semana!";
        fondoSaludo = "bg-weekend";
    } else {
        if (horaActual >= 6 && horaActual < 12) {
            mensaje = "¡Buenos días!";
            fondoSaludo = "bg-morning";
            mensaje += esHoraDeComida(horaActual, "desayuno") ? " Es hora de desayunar." : "";
        } else if (horaActual >= 12 && horaActual < 15) {
            mensaje = "¡Hola! Buen provecho.";
            fondoSaludo = "bg-noon";
            mensaje += esHoraDeComida(horaActual, "almuerzo") ? " Es hora de almorzar." : "";
        } else if (horaActual >= 15 && horaActual < 18) {
            mensaje = "¡Hola! ¿Cómo va tu tarde?";
            fondoSaludo = "bg-afternoon";
            mensaje += esHoraDeComida(horaActual, "merienda") ? " Es hora de merendar." : "";
        } else if (horaActual >= 18 && horaActual < 22) {
            mensaje = "¡Hola! ¿Qué tal tu noche?";
            fondoSaludo = "bg-evening";
            mensaje += esHoraDeComida(horaActual, "cena") ? " Es hora de cenar." : "";
        } else {
            mensaje = "¡Hola! Espero que tengas un buen momento.";
            fondoSaludo = "bg-night";
        }
    }

    saludo.textContent = mensaje;
    boxBienvenida.classList.add(fondoSaludo);
});

function esHoraDeComida(hora, comida) {
    const horasDeComida = {
        "desayuno": [6, 7, 8, 9, 10],
        "almuerzo": [12, 13, 14],
        "merienda": [15, 16, 17],
        "cena": [18, 19, 20, 21]
    };

    return horasDeComida[comida].includes(hora);
}






/*========== BALANCE ==========*/
 // Datos para el gráfico
const data = {
    datasets: [{
        data: [200, 500], // Aquí puedes poner tus propios datos
        backgroundColor: [
        '#FF5722', // Color para las calorías quemadas
        '#8BC34A'  // Color para las calorías ingeridas
        ]
    }]
};

  // Configuración del gráfico
const options = {
    cutout: '80%', // Tamaño del agujero interior (en porcentaje)
    animation: {
        animateScale: true,
        animateRotate: true
    }
};

  // Crear el gráfico de anillos dobles
const ctx = document.getElementById('doubleRingChart').getContext('2d');
const doubleRingChart = new Chart(ctx, {
    type: 'doughnut',
    data: data,
    options: options
});

  // Calcular el déficit y mostrarlo en el centro del gráfico
const burnedCalories = data.datasets[0].data[0];
const consumedCalories = data.datasets[0].data[1];
const deficit = consumedCalories - burnedCalories;
document.getElementById('deficitText').innerText = ` ${deficit}`;

/*========== Control de peso ==========*/
// Datos de ejemplo del historial de pesos (fecha y peso)
const weightData = [
    { date: '2024-04-01', weight: 75 },
    { date: '2024-03-28', weight: 74.5 },
    { date: '2024-04-01', weight: 70 },
    { date: '2024-04-02', weight: 69 },
    { date: '2024-04-03', weight: 68.5 },
    // Agregar más datos según sea necesario
];

// Obtener las fechas y pesos del historial de pesos
const dates = weightData.map(data => data.date);
const weights = weightData.map(data => data.weight);

// Configuración del gráfico
const ctx_ = document.getElementById('weightChart').getContext('2d');
const weightChart = new Chart(ctx_, {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            label: 'Peso',
            data: weights,
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            x: {
                display: false // Oculta las etiquetas del eje x
            },
            y: {
                display: true // Oculta las etiquetas del eje y
            }
        }
    }
});




document.addEventListener("DOMContentLoaded", function() {
    const carrusel = document.getElementById("carrusel-cards");
    const flechaIzquierda = document.querySelector(".flecha-izquierda");
    const flechaDerecha = document.querySelector(".flecha-derecha");
    let indiceVisible = 0;
    
    // Función para desplazar el carrusel a la izquierda
    flechaIzquierda.addEventListener("click", function() {
        carrusel.scrollLeft -= 100; // Cambia el valor 300 según el ancho de tus tarjetas en el carrusel
    });

    // Función para desplazar el carrusel a la derecha
    flechaDerecha.addEventListener("click", function() {
        carrusel.scrollLeft += 100; // Cambia el valor 300 según el ancho de tus tarjetas en el carrusel
    });
});


