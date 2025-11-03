<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Sistema de Gestión Académica</title>
    
    <!-- Cargar Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    
    <!-- Cargar Fuente (Inter) -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap" rel="stylesheet">
    
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    
    <style>
        body {
            font-family: 'Inter', sans-serif;
        }
        
        .btn-fill {
            position: relative;
            display: inline-block;
            padding: 0.75rem 1.5rem;
            border: 2px solid #3b82f6;
            color: #3b82f6;
            background-color: transparent;
            text-decoration: none;
            font-weight: 600;
            border-radius: 9999px;
            overflow: hidden;
            transition: color 0.4s ease-out;
            z-index: 1;
            cursor: pointer;
        }
        
        .btn-fill::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: #3b82f6;
            transform: scaleX(0);
            transform-origin: left;
            transition: transform 0.4s cubic-bezier(0.23, 1, 0.32, 1);
            z-index: -1;
        }
        
        .btn-fill:hover {
            color: #ffffff;
        }
        
        .btn-fill:hover::before {
            transform: scaleX(1);
        }
        
        .btn-fill-solid {
            position: relative;
            display: inline-block;
            padding: 0.75rem 1.5rem;
            border: 2px solid #3b82f6;
            color: #ffffff;
            background-color: #3b82f6;
            text-decoration: none;
            font-weight: 600;
            border-radius: 0.5rem;
            overflow: hidden;
            transition: all 0.3s ease;
            z-index: 1;
            cursor: pointer;
            text-align: center;
        }
        
        .btn-fill-solid:hover {
            background-color: #2563eb;
            border-color: #2563eb;
            transform: translateY(-2px);
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        }
        
        .login-card {
            background: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(8px);
            border-radius: 1rem;
            overflow: hidden;
            box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
        }
    </style>
</head>
<body class="antialiased text-slate-800 bg-cover bg-center" style="background-image: url('https://source.unsplash.com/random/1920x1080?university,library'); min-height: 100vh; display: flex; align-items: center; justify-content: center; padding: 1rem;">
    <div class="w-full max-w-4xl">
        <div class="login-card grid grid-cols-1 md:grid-cols-2 overflow-hidden">
            <!-- Columna Izquierda: Imagen -->
            <div class="hidden md:block h-full">
                <img src="https://source.unsplash.com/random/800x600?students,learning" alt="Estudiantes aprendiendo" class="w-full h-full object-cover">
            </div>

            <!-- Columna Derecha: Formulario -->
            <div class="p-8 md:p-12 bg-white">
                <div class="text-center mb-8">
                    <h2 class="text-3xl font-bold text-slate-900">Iniciar Sesión</h2>
                    <p class="text-slate-600 mt-2">Ingresa tus credenciales para acceder al sistema</p>
                </div>
                
                <form id="login-form" class="space-y-6">
                    <div>
                        <label for="username" class="block text-sm font-medium text-slate-700 mb-1">Usuario</label>
                        <input 
                            type="text" 
                            id="username" 
                            name="username" 
                            class="w-full px-4 py-3 rounded-lg border border-slate-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition" 
                            placeholder="Ingresa tu usuario"
                            required
                        >
                    </div>
                    
                    <div>
                        <div class="flex justify-between items-center mb-1">
                            <label for="password" class="block text-sm font-medium text-slate-700">Contraseña</label>
                            <a href="#" class="text-sm text-blue-600 hover:text-blue-800 hover:underline">¿Olvidaste tu contraseña?</a>
                        </div>
                        <input 
                            type="password" 
                            id="password" 
                            name="password" 
                            class="w-full px-4 py-3 rounded-lg border border-slate-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition" 
                            placeholder="••••••••"
                            required
                        >
                    </div>

                    <div class="flex items-center">
                        <input 
                            type="checkbox" 
                            id="remember-me" 
                            name="remember-me" 
                            class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-slate-300 rounded"
                        >
                        <label for="remember-me" class="ml-2 block text-sm text-slate-700">
                            Recordar mi sesión
                        </label>
                    </div>

                    <div class="space-y-3">
                        <button 
                            type="submit" 
                            data-role="estudiante"
                            class="w-full btn-fill-solid bg-blue-600 hover:bg-blue-700 border-blue-600"
                        >
                            Ingresar como Estudiante
                        </button>
                        
                        <button 
                            type="button" 
                            data-role="docente"
                            class="w-full btn-fill-solid bg-green-600 hover:bg-green-700 border-green-600"
                        >
                            Ingresar como Docente
                        </button>
                        
                        <button 
                            type="button" 
                            data-role="admin"
                            class="w-full btn-fill-solid bg-slate-700 hover:bg-slate-800 border-slate-700"
                        >
                            Ingresar como Administrador
                        </button>
                    </div>
                </form>
                
                <div class="mt-6 text-center">
                    <p class="text-sm text-slate-600">
                        ¿No tienes una cuenta? 
                        <a href="#" class="font-medium text-blue-600 hover:text-blue-500">
                            Contáctate con el administrador
                        </a>
                    </p>
                </div>
                
                <div class="mt-8 pt-6 border-t border-slate-200">
                    <p class="text-xs text-slate-500 text-center">
                        © 2025 Sistema de Gestión Académica. Todos los derechos reservados.
                    </p>
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const loginForm = document.getElementById('login-form');
            const loginButtons = document.querySelectorAll('[data-role]');
            
            // Manejar el envío del formulario
            if (loginForm) {
                loginForm.addEventListener('submit', (e) => {
                    e.preventDefault();
                    const formData = new FormData(loginForm);
                    const role = e.submitter?.dataset.role || 'estudiante';
                    handleLogin(formData, role);
                });
                
                // Agregar manejadores a los botones de rol
                loginButtons.forEach(button => {
                    button.addEventListener('click', (e) => {
                        e.preventDefault();
                        const form = e.target.closest('form');
                        const formData = new FormData(form);
                        const role = e.target.dataset.role;
                        handleLogin(formData, role);
                    });
                });
            }
            
            function handleLogin(formData, role) {
                // Aquí iría la lógica de autenticación
                console.log('Iniciando sesión como:', role);
                console.log('Usuario:', formData.get('username'));
                
                // Simular redirección según el rol
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
                        console.error('Rol no válido');
                }
            }
        });
    </script>
</body>
</html>
