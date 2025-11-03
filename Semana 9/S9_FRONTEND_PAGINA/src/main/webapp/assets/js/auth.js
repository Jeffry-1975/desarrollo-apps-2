// auth.js - Manejo de autenticación

document.addEventListener('DOMContentLoaded', () => {
    // Elementos del DOM
    const welcomeScreen = document.getElementById('welcome-screen');
    const loginScreen = document.getElementById('login-screen');
    const comenzarBtn = document.getElementById('comenzar-btn');
    const loginForm = document.getElementById('login-form');
    
    // Si estamos en la pantalla de bienvenida
    if (comenzarBtn) {
        comenzarBtn.addEventListener('click', () => {
            // Animación de salida
            welcomeScreen.classList.add('animate-fade-out');
            welcomeScreen.addEventListener('animationend', () => {
                // Redirigir al login
                window.location.href = 'login.jsp';
            }, { once: true });
        });
    }
    
    // Si estamos en el formulario de login
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            
            // Obtener credenciales
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const role = document.querySelector('button[type="submit"]').dataset.role;
            
            // Validación básica
            if (!username || !password) {
                alert('Por favor ingresa usuario y contraseña');
                return;
            }
            
            // Redirigir según el rol
            switch(role) {
                case 'estudiante':
                    window.location.href = '../dashboard/student/';
                    break;
                case 'docente':
                    window.location.href = '../dashboard/teacher/';
                    break;
                case 'admin':
                    window.location.href = '../dashboard/admin/';
                    break;
                default:
                    alert('Rol no válido');
            }
        });
    }
});
