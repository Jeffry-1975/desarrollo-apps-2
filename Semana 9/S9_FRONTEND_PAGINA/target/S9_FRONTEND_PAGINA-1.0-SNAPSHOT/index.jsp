<%-- 
    Document   : index
    Created on : 2 nov 2025, 20:35:38
    Author     : Jefferson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-M">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Login Interactivo</title>
    
    <!-- Cargar Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    
    <!-- Cargar Fuente (Inter) -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap" rel="stylesheet">
    
    <style>
        /* Aplicar la fuente base */
        body {
            font-family: 'Inter', sans-serif;
        }

        /* --- ANIMACIONES DE TRANSICIÓN DE PANTALLA --- */
        
        /* Animación de aparición (fade-in) */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        .animate-fade-in {
            animation: fadeIn 0.7s ease-out forwards;
        }

        /* Animación de desvanecimiento (fade-out) */
        @keyframes fadeOut {
            from {
                opacity: 1;
            }
            to {
                opacity: 0;
            }
        }
        .animate-fade-out {
            animation: fadeOut 0.5s ease-in forwards;
        }

        /* --- ESTILO DE BOTÓN CON RELLENO PROGRESIVO --- */
        
        .btn-fill {
            position: relative;
            display: inline-block;
            padding: 0.75rem 1.5rem; /* 12px 24px */
            border: 2px solid #3b82f6; /* Borde azul-600 */
            color: #3b82f6;
            background-color: transparent;
            text-decoration: none;
            font-weight: 600;
            border-radius: 9999px; /* rounded-full */
            overflow: hidden; /* Clave para el efecto */
            transition: color 0.4s ease-out;
            z-index: 1; /* Asegura que el texto esté sobre el pseudo-elemento */
        }

        .btn-fill::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: #3b82f6; /* Color de relleno (azul-600) */
            transform: scaleX(0); /* Inicia sin rellenar */
            transform-origin: left;
            transition: transform 0.4s cubic-bezier(0.23, 1, 0.32, 1);
            z-index: -1; /* Detrás del texto */
        }

        .btn-fill:hover {
            color: #ffffff; /* Texto blanco al hacer hover */
        }

        .btn-fill:hover::before {
            transform: scaleX(1); /* Rellena completamente */
        }

        /* Estilo alternativo (botón ya relleno que se oscurece) */
        .btn-fill-solid {
            position: relative;
            display: inline-block;
            padding: 0.75rem 1.5rem; /* 12px 24px */
            border: 2px solid #3b82f6;
            color: #ffffff;
            background-color: #3b82f6;
            text-decoration: none;
            font-weight: 600;
            border-radius: 8px; /* rounded-lg */
            overflow: hidden;
            transition: color 0.4s ease-out;
            z-index: 1;
        }

        .btn-fill-solid::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: #2563eb; /* Color más oscuro (azul-700) */
            transform: scaleX(0);
            transform-origin: left;
            transition: transform 0.4s cubic-bezier(0.23, 1, 0.32, 1);
            z-index: -1;
        }

        .btn-fill-solid:hover::before {
            transform: scaleX(1);
        }

        /* --- ESTILOS DE TARJETAS FLOTANTES --- */
        .dashboard-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            cursor: pointer; /* Añadido para indicar que es clickeable */
        }
        .dashboard-card:hover {
            transform: translateY(-5px); /* Efecto "flotante" al pasar el mouse */
            box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1); /* shadow-xl */
        }
    </style>
</head>
<body class="antialiased text-slate-800">

    <!-- 
      CONTENEDOR PRINCIPAL
      Usaremos JS para mostrar y ocultar estas secciones.
    -->
    <main>

        <!-- ============================================= -->
        <!-- PANTALLA 1: BIENVENIDA (INICIALMENTE VISIBLE)  -->
        <!-- ============================================= -->
        <section id="welcome-screen" class="h-screen w-full flex flex-col items-center justify-center p-8 bg-gradient-to-br from-indigo-100 via-white to-sky-100">
            
            <!-- El texto "Bienvenido" con animación de aparición -->
            <h1 class="text-6xl md:text-8xl font-bold text-indigo-700 animate-fade-in" style="animation-delay: 0.2s;">
                Bienvenido
            </h1>
            <p class="text-lg text-slate-600 mt-4 animate-fade-in" style="animation-delay: 0.4s;">
                Tu plataforma de gestión académica.
            </p>
            
            <!-- El botón "Comenzar" con animación -->
            <div class="mt-12 animate-fade-in" style="animation-delay: 0.6s;">
                <button id="comenzar-btn" class="btn-fill">
                    Comenzar
                </button>
            </div>
            
        </section>

        <!-- ============================================= -->
        <!-- PANTALLA 2: LOGIN (INICIALMENTE OCULTA)       -->
        <!-- ============================================= -->
        <section id="login-screen" class="h-screen w-full flex items-center justify-center p-8 bg-cover bg-center hidden" style="background-image: url('https://source.unsplash.com/random/1920x1080?university,library');">
            
            <!-- Tarjeta de Login con fondo difuminado -->
            <div class="w-full max-w-4xl bg-white/50 backdrop-blur-xl rounded-2xl shadow-2xl overflow-hidden grid grid-cols-1 md:grid-cols-2 animate-fade-in">
                
                <!-- Columna Izquierda: Imagen -->
                <div class="hidden md:block h-full">
                    <img src="https://source.unsplash.com/random/800x600?students,learning" alt="Estudiantes aprendiendo" class="w-full h-full object-cover">
                </div>

                <!-- Columna Derecha: Formulario -->
                <div class="p-8 md:p-12">
                    <h2 class="text-3xl font-bold text-slate-900 mb-4">Iniciar Sesión</h2>
                    <p class="text-slate-700 mb-8">Ingresa tus credenciales para acceder.</p>
                    
                    <form id="login-form" class="flex flex-col gap-6">
                        <div>
                            <label for="username" class="block text-sm font-semibold text-slate-700 mb-2">Usuario</label>
                            <input type="text" id="username" name="username" class="w-full px-4 py-3 rounded-lg border border-slate-300 focus:outline-none focus:ring-2 focus:ring-blue-500 transition" placeholder="tu.usuario">
                        </div>
                        
                        <div>
                            <label for="password" class="block text-sm font-semibold text-slate-700 mb-2">Contraseña</label>
                            <input type="password" id="password" name="password" class="w-full px-4 py-3 rounded-lg border border-slate-300 focus:outline-none focus:ring-2 focus:ring-blue-500 transition" placeholder="••••••••">
                        </div>

                        <!-- 
                          NOTA: Para esta simulación, usaremos botones separados
                          para cada rol. En una app real, el servidor 
                          determinaría el rol después de un único login.
                        -->
                        <div class="flex flex-col sm:flex-row gap-3 mt-4">
                            <!-- Botón Simulado Estudiante -->
                            <button data-role="estudiante" class="login-btn btn-fill-solid w-full text-center justify-center">
                                Ingresar (Estudiante)
                            </button>
                            
                            <!-- Botón Simulado Docente -->
                            <button data-role="docente" class="login-btn btn-fill-solid w-full text-center justify-center bg-green-600 border-green-600 hover:text-white" style="--tw-bg-opacity: 1; background-color: #16a34a; border-color: #16a34a;">
                                Ingresar (Docente)
                            </button>
                        </div>
                        
                        <!-- Botón Simulado Administrador -->
                        <button data-role="admin" class="login-btn btn-fill-solid w-full text-center justify-center bg-slate-700 border-slate-700 hover:text-white" style="--tw-bg-opacity: 1; background-color: #334155; border-color: #334155;">
                            Ingresar (Admin)
                        </button>

                    </form>
                </div>
            </div>

        </section>

        <!-- ============================================= -->
        <!-- PANTALLA 3: PANEL ESTUDIANTE (OCULTA)         -->
        <!-- ============================================= -->
        <section id="estudiante-dashboard" class="h-screen w-full flex flex-col hidden bg-blue-50">
            <header class="w-full bg-white shadow-md p-4 flex justify-between items-center">
                <h1 class="text-2xl font-bold text-blue-700">Panel del Estudiante</h1>
                <button class="logout-btn btn-fill">Cerrar Sesión</button>
            </header>
            <div class="flex-1 p-8 overflow-y-auto"> <!-- Añadido overflow-y-auto -->
                <h2 class="text-3xl font-bold text-slate-800 mb-8 animate-fade-in" style="animation-delay: 0.1s;">Bienvenido, Estudiante</h2>
                
                <!-- Contenedor de recuadros flotantes -->
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    
                    <!-- Tarjeta 1: Horario -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.2s;" data-target-page="estudiante-subpage" data-title="Horario de Clases" data-content="Aquí se mostrará un componente de calendario con tu horario.">
                        <h3 class="text-xl font-semibold text-blue-700 mb-3">Horario de Clases</h3>
                        <p class="text-slate-600">Revisa tus clases programadas para la semana.</p>
                        <!-- Aquí iría un componente de horario -->
                    </div>

                    <!-- Tarjeta 2: Cursos Inscritos -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.3s;" data-target-page="estudiante-subpage" data-title="Cursos Inscritos" data-content="Aquí verás una lista de tus cursos. Podrás hacer clic en cada uno para ver materiales.">
                        <h3 class="text-xl font-semibold text-blue-700 mb-3">Cursos Inscritos</h3>
                        <p class="text-slate-600">Accede a tus cursos y descarga materiales.</p>
                        <!-- Lista de cursos... -->
                    </div>

                    <!-- Tarjeta 3: Tareas -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.4s;" data-target-page="estudiante-subpage" data-title="Tareas" data-content="Listado de tareas pendientes y entregadas, con sus calificaciones.">
                        <h3 class="text-xl font-semibold text-blue-700 mb-3">Tareas</h3>
                        <p class="text-slate-600">Sube tus archivos y revisa calificaciones.</p>
                    </div>

                    <!-- Tarjeta 4: Asistencia -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.5s;" data-target-page="estudiante-subpage" data-title="Asistencia" data-content="Un gráfico o resumen de tu porcentaje de asistencia por curso.">
                        <h3 class="text-xl font-semibold text-blue-700 mb-3">Asistencia</h3>
                        <p class="text-slate-600">Tu porcentaje acumulado es: <span class="font-bold text-blue-800">95%</span></p>
                    </div>

                    <!-- Tarjeta 5: Mensajes -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.6s;" data-target-page="estudiante-subpage" data-title="Mensajes" data-content="Un chat o bandeja de entrada para comunicarte con docentes.">
                        <h3 class="text-xl font-semibold text-blue-700 mb-3">Mensajes</h3>
                        <p class="text-slate-600">Comunícate con tus docentes.</p>
                    </div>

                    <!-- Tarjeta 6: Perfil -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.7s;" data-target-page="estudiante-subpage" data-title="Editar Perfil" data-content="Formulario para actualizar tu nombre, correo y contraseña.">
                        <h3 class="text-xl font-semibold text-blue-700 mb-3">Editar Perfil</h3>
                        <p class="text-slate-600">Actualiza tu nombre, correo o contraseña.</p>
                    </div>

                </div>
            </div>
        </section>

        <!-- ============================================= -->
        <!-- PANTALLA 4: PANEL DOCENTE (OCULTA)            -->
        <!-- ============================================= -->
        <section id="docente-dashboard" class="h-screen w-full flex flex-col hidden bg-green-50">
            <header class="w-full bg-white shadow-md p-4 flex justify-between items-center">
                <h1 class="text-2xl font-bold text-green-700">Panel del Docente</h1>
                <button class="logout-btn btn-fill" style="color: #15803d; border-color: #15803d;">Cerrar Sesión</button>
            </header>
            <div class="flex-1 p-8 overflow-y-auto"> <!-- Añadido overflow-y-auto -->
                <h2 class="text-3xl font-bold text-slate-800 mb-8 animate-fade-in" style="animation-delay: 0.1s;">Bienvenido, Docente</h2>
                
                <!-- Contenedor de recuadros flotantes -->
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

                    <!-- Tarjeta 1: Resumen de Cursos -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.2s;" data-target-page="docente-subpage" data-title="Resumen de Cursos">
                        <h3 class="text-xl font-semibold text-green-700 mb-3">Resumen de Cursos</h3>
                        <p class="text-slate-600">Vista rápida de tus cursos asignados.</p>
                    </div>

                    <!-- Tarjeta 2: Gestión de Cursos -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.3s;" data-target-page="docente-subpage" data-title="Gestión de Cursos">
                        <h3 class="text-xl font-semibold text-green-700 mb-3">Gestión de Cursos</h3>
                        <p class="text-slate-600">Crea materiales y publica anuncios.</p>
                    </div>

                    <!-- Tarjeta 3: Evaluaciones -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.4s;" data-target-page="docente-subpage" data-title="Evaluaciones">
                        <h3 class="text-xl font-semibold text-green-700 mb-3">Evaluaciones</h3>
                        <p class="text-slate-600">Crea y califica tareas y exámenes.</p>
                    </div>

                    <!-- Tarjeta 4: Asistencia -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.5s;" data-target-page="docente-subpage" data-title="Registro de Asistencia">
                        <h3 class="text-xl font-semibold text-green-700 mb-3">Registro de Asistencia</h3>
                        <p class="text-slate-600">Registra la asistencia por curso.</p>
                    </div>

                    <!-- Tarjeta 5: Mensajería -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.6s;" data-target-page="docente-subpage" data-title="Mensajería Interna">
                        <h3 class="text-xl font-semibold text-green-700 mb-3">Mensajería Interna</h3>
                        <p class="text-slate-600">Envía mensajes a alumnos o admin.</p>
                    </div>

                    <!-- Tarjeta 6: Perfil -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.7s;" data-target-page="docente-subpage" data-title="Editar Perfil">
                        <h3 class="text-xl font-semibold text-green-700 mb-3">Editar Perfil</h3>
                        <p class="text-slate-600">Actualiza tu información profesional.</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- ============================================= -->
        <!-- PANTALLA 5: PANEL ADMIN (OCULTA)              -->
        <!-- ============================================= -->
        <section id="admin-dashboard" class="h-screen w-full flex flex-col hidden bg-slate-50">
            <header class="w-full bg-white shadow-md p-4 flex justify-between items-center">
                <h1 class="text-2xl font-bold text-slate-700">Panel de Administrador</h1>
                <button class="logout-btn btn-fill" style="color: #334155; border-color: #334155;">Cerrar Sesión</button>
            </header>
            <div class="flex-1 p-8 overflow-y-auto"> <!-- Añadido overflow-y-auto -->
                <h2 class="text-3xl font-bold text-slate-800 mb-8 animate-fade-in" style="animation-delay: 0.1s;">Panel de Control General</h2>

                <!-- Contenedor de recuadros flotantes -->
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

                    <!-- Tarjeta 1: Estadísticas -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.2s;" data-target-page="admin-subpage" data-title="Estadísticas del Sistema">
                        <h3 class="text-xl font-semibold text-slate-700 mb-3">Estadísticas del Sistema</h3>
                        <p class="text-slate-600">Usuarios activos, cursos, etc.</p>
                    </div>

                    <!-- Tarjeta 2: Gestión de Usuarios -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.3s;" data-target-page="admin-subpage" data-title="Gestión de Usuarios">
                        <h3 class="text-xl font-semibold text-slate-700 mb-3">Gestión de Usuarios</h3>
                        <p class="text-slate-600">Crear, editar, eliminar alumnos y docentes.</p>
                    </div>

                    <!-- Tarjeta 3: Gestión Académica -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.4s;" data-target-page="admin-subpage" data-title="Gestión Académica">
                        <h3 class="text-xl font-semibold text-slate-700 mb-3">Gestión Académica</h3>
                        <p class="text-slate-600">Crear cursos, asignar roles.</p>
                    </div>

                    <!-- Tarjeta 4: Gestión de Contenidos -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.5s;" data-target-page="admin-subpage" data-title="Gestión de Contenidos">
                        <h3 class="text-xl font-semibold text-slate-700 mb-3">Gestión de Contenidos</h3>
                        <p class="text-slate-600">Publicar comunicados institucionales.</p>
                    </div>

                    <!-- Tarjeta 5: Reportes -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.6s;" data-target-page="admin-subpage" data-title="Reportes">
                        <h3 class="text-xl font-semibold text-slate-700 mb-3">Reportes</h3>
                        <p class="text-slate-600">Genera y exporta reportes del sistema.</p>
                    </div>

                    <!-- Tarjeta 6: Configuración -->
                    <div class="dashboard-card bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.7s;" data-target-page="admin-subpage" data-title="Configuración">
                        <h3 class="text-xl font-semibold text-slate-700 mb-3">Configuración</h3>
                        <p class="text-slate-600">Logo, colores, mantenimiento, respaldos.</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- ============================================= -->
        <!-- PANTALLAS DE SUB-PÁGINAS (OCULTAS)            -->
        <!-- ============================================= -->

        <!-- Sub-página Estudiante -->
        <section id="estudiante-subpage" class="h-screen w-full flex flex-col hidden bg-blue-50">
            <header class="w-full bg-white shadow-md p-4 flex justify-between items-center">
                <button class="back-btn btn-fill" data-target-dashboard="estudiante-dashboard">
                    &larr; Volver
                </button>
                <h1 id="subpage-title-estudiante" class="text-2xl font-bold text-blue-700"></h1>
                <button class="logout-btn btn-fill">Cerrar Sesión</button>
            </header>
            <div id="subpage-content-estudiante" class="flex-1 p-8 overflow-y-auto">
                <!-- El contenido se inyectará aquí -->
                <p>Cargando contenido...</p>
            </div>
        </section>

        <!-- Sub-página Docente -->
        <section id="docente-subpage" class="h-screen w-full flex flex-col hidden bg-green-50">
            <header class="w-full bg-white shadow-md p-4 flex justify-between items-center">
                <button class="back-btn btn-fill" data-target-dashboard="docente-dashboard" style="color: #15803d; border-color: #15803d;">
                    &larr; Volver
                </button>
                <h1 id="subpage-title-docente" class="text-2xl font-bold text-green-700"></h1>
                <button class="logout-btn btn-fill" style="color: #15803d; border-color: #15803d;">Cerrar Sesión</button>
            </header>
            <div id="subpage-content-docente" class="flex-1 p-8 overflow-y-auto">
                <!-- El contenido se inyectará aquí -->
                <p>Cargando contenido...</p>
            </div>
        </section>

        <!-- Sub-página Admin -->
        <section id="admin-subpage" class="h-screen w-full flex flex-col hidden bg-slate-50">
            <header class="w-full bg-white shadow-md p-4 flex justify-between items-center">
                <button class="back-btn btn-fill" data-target-dashboard="admin-dashboard" style="color: #334155; border-color: #334155;">
                    &larr; Volver
                </button>
                <h1 id="subpage-title-admin" class="text-2xl font-bold text-slate-700"></h1>
                <button class="logout-btn btn-fill" style="color: #334155; border-color: #334155;">Cerrar Sesión</button>
            </header>
            <div id="subpage-content-admin" class="flex-1 p-8 overflow-y-auto">
                <!-- El contenido se inyectará aquí -->
                <p>Cargando contenido...</p>
            </div>
        </section>

    </main>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            
            // --- REFERENCIAS A LAS PANTALLAS ---
            const welcomeScreen = document.getElementById('welcome-screen');
            const loginScreen = document.getElementById('login-screen');
            // Dashboards
            const studentDashboard = document.getElementById('estudiante-dashboard');
            const teacherDashboard = document.getElementById('docente-dashboard');
            const adminDashboard = document.getElementById('admin-dashboard');
            // Sub-paginas
            const studentSubpage = document.getElementById('estudiante-subpage');
            const teacherSubpage = document.getElementById('docente-subpage');
            const adminSubpage = document.getElementById('admin-subpage');

            const allScreens = [
                welcomeScreen, loginScreen, 
                studentDashboard, teacherDashboard, adminDashboard,
                studentSubpage, teacherSubpage, adminSubpage
            ];

            // --- REFERENCIAS A LOS BOTONES ---
            const comenzarBtn = document.getElementById('comenzar-btn');
            const loginBtns = document.querySelectorAll('.login-btn');
            const logoutBtns = document.querySelectorAll('.logout-btn');
            const dashboardCards = document.querySelectorAll('.dashboard-card');
            const backBtns = document.querySelectorAll('.back-btn');

            // --- FUNCIÓN DE NAVEGACIÓN ---
            function navigateTo(screen) {
                // Ocultar todas las pantallas
                allScreens.forEach(s => {
                    if (s) { // Comprobar si la pantalla existe
                        s.classList.add('hidden');
                        s.classList.remove('animate-fade-in'); // Limpiar animación
                    }
                });
                
                // Mostrar la pantalla objetivo
                if (screen) {
                    screen.classList.remove('hidden');
                    screen.classList.add('animate-fade-in');
                }
            }

            // --- LÓGICA DE TRANSICIÓN ---

            // 1. Click en "Comenzar"
            comenzarBtn.addEventListener('click', () => {
                welcomeScreen.classList.add('animate-fade-out');
                welcomeScreen.addEventListener('animationend', () => {
                    navigateTo(loginScreen);
                    welcomeScreen.classList.remove('animate-fade-out');
                }, { once: true });
            });

            // 2. Click en "Ingresar" (Simulación de Roles)
            loginBtns.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    e.preventDefault(); 
                    const role = e.target.dataset.role;
                    let targetDashboard = null;

                    if (role === 'estudiante') targetDashboard = studentDashboard;
                    else if (role === 'docente') targetDashboard = teacherDashboard;
                    else if (role === 'admin') targetDashboard = adminDashboard;
                    
                    loginScreen.classList.add('animate-fade-out');
                    loginScreen.addEventListener('animationend', () => {
                        navigateTo(targetDashboard);
                        loginScreen.classList.remove('animate-fade-out');
                    }, { once: true });
                });
            });

            // 3. Click en "Cerrar Sesión"
            logoutBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    const currentDashboard = btn.closest('section');
                    if (currentDashboard) {
                        currentDashboard.classList.add('animate-fade-out');
                        currentDashboard.addEventListener('animationend', () => {
                            navigateTo(loginScreen);
                            currentDashboard.classList.remove('animate-fade-out');
                        }, { once: true });
                    }
                });
            });

            // 4. Click en Tarjetas del Panel (NUEVA LÓGICA)
            dashboardCards.forEach(card => {
                card.addEventListener('click', () => {
                    const targetPageId = card.dataset.targetPage;
                    const targetPage = document.getElementById(targetPageId);
                    const title = card.dataset.title;
                    const content = card.dataset.content || `<p>Contenido para "${title}" aún no implementado.</p>`; // Contenido de ejemplo

                    const currentDashboard = card.closest('section');

                    if (targetPage) {
                        // Actualizar contenido de la sub-página
                        const titleEl = targetPage.querySelector('h1[id^="subpage-title-"]');
                        const contentEl = targetPage.querySelector('div[id^="subpage-content-"]');
                        
                        if (titleEl) titleEl.textContent = title;
                        if (contentEl) contentEl.innerHTML = `
                            <div class="bg-white p-6 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                                ${content}
                            </div>
                        `; // Inyectar contenido

                        // Animar transición
                        currentDashboard.classList.add('animate-fade-out');
                        currentDashboard.addEventListener('animationend', () => {
                            navigateTo(targetPage);
                            currentDashboard.classList.remove('animate-fade-out');
                        }, { once: true });
                    }
                });
            });

            // 5. Click en "Volver" (NUEVA LÓGICA)
            backBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    const targetDashboardId = btn.dataset.targetDashboard;
                    const targetDashboard = document.getElementById(targetDashboardId);
                    const currentSubpage = btn.closest('section');

                    if (targetDashboard) {
                        currentSubpage.classList.add('animate-fade-out');
                        currentSubpage.addEventListener('animationend', () => {
                            navigateTo(targetDashboard);
                            currentSubpage.classList.remove('animate-fade-out');
                        }, { once: true });
                    }
                });
            });

        });
    </script>

</body>
</html>


