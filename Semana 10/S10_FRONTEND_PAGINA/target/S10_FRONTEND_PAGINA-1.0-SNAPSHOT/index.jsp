<%-- 
    Document   : index
    Created on : 9 nov 2025, 16:24:22
    Author     : Jefferson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Login Interactivo</title>
    
    <!-- Cargar Tailwind CSS -->
    <!-- ***** INICIO DE LA CORRECCIÓN ***** -->
    
    <!-- 1. Cargar el script principal de Tailwind -->
    <script src="https://cdn.tailwindcss.com"></script> 
    
    <!-- 2. Configurar Tailwind para que use la estrategia 'class' para el modo oscuro -->
    <script>
      // Esto se ejecuta DESPUÉS de que el script de arriba se cargue
      tailwind.config = {
        darkMode: 'class'
      }
    </script>
    <!-- ***** FIN DE LA CORRECCIÓN ***** -->
    
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
        
        .dark .btn-fill {
            border-color: #60a5fa; /* azul-400 */
            color: #60a5fa;
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
        
        .dark .btn-fill::before {
            background-color: #60a5fa;
        }

        .btn-fill:hover {
            color: #ffffff; /* Texto blanco al hacer hover */
        }
        
        .dark .btn-fill:hover {
            color: #1e3a8a; /* azul-900 (oscuro) */
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
            /* Mantenemos la lógica de hover para enlaces y tarjetas */
            /* Para enlaces de barra lateral, un cambio de fondo es suficiente (manejado por tailwind) */
            /* Para las tarjetas reales (si aún las usamos), el efecto flotante es bueno */
            transform: translateY(-5px); 
            box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1); 
        }
        
        .dark .dashboard-card:hover {
             box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.2), 0 8px 10px -6px rgb(0 0 0 / 0.2); 
        }
        
        /* Estilo para los enlaces de la barra lateral que también son "dashboard-card" */
        .sidebar-link {
            transition: background-color 0.2s ease;
        }
        
        /* Ocultar iconos de tema por defecto */
        .theme-icon-sun,
        .theme-icon-moon {
            display: none;
        }

        /* Mostrar el icono correcto basado en el tema */
        html:not(.dark) .theme-icon-sun {
            display: block;
        }
        html.dark .theme-icon-moon {
            display: block;
        }


    </style>
</head>
<body class="antialiased text-slate-800 bg-white dark:bg-slate-900 dark:text-slate-200">

    <!-- 
      CONTENEDOR PRINCIPAL
      Usaremos JS para mostrar y ocultar estas secciones.
    -->
    <main>

        <!-- ============================================= -->
        <!-- PANTALLA 1: BIENVENIDA (INICIALMENTE VISIBLE)  -->
        <!-- ============================================= -->
        <section id="welcome-screen" class="h-screen w-full flex flex-col items-center justify-center p-8 bg-gradient-to-br from-indigo-100 via-white to-sky-100 dark:from-indigo-900 dark:via-slate-900 dark:to-sky-900">
            
            <!-- ICONO DE GORRO DE GRADUACIÓN (NUEVO) -->
            <div class="animate-fade-in mb-6 text-indigo-600 dark:text-indigo-400" style="animation-delay: 0.1s;">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-24 h-24 md:w-32 md:h-32">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M4.26 10.147a60.436 60.436 0 00-.491 6.347A48.627 48.627 0 0112 20.904a48.627 48.627 0 018.232-4.41 60.46 60.46 0 00-.491-6.347m-15.482 0a50.57 50.57 0 00-2.658-.813A59.905 59.905 0 0112 3.493a59.902 59.902 0 0110.499 5.221 50.57 50.57 0 00-2.658.814m-15.482 0A50.697 50.697 0 0112 13.489a50.702 50.702 0 017.74-3.342M6.75 15a.75.75 0 100-1.5.75.75 0 000 1.5zm0 0v-3.675A55.378 55.378 0 0112 8.443m-7.007 11.55A5.981 5.981 0 006.75 15.75v-1.5" />
                </svg>
            </div>

            <!-- El texto "Bienvenido" con animación de aparición -->
            <h1 class="text-6xl md:text-8xl font-bold text-slate-800 dark:text-slate-100 animate-fade-in" style="animation-delay: 0.2s;">
                Bienvenido
            </h1>
            <p class="text-lg text-slate-600 dark:text-slate-400 mt-4 animate-fade-in" style="animation-delay: 0.4s;">
                Tu plataforma de gestión de tesis.
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
        <section id="login-screen" class="h-screen w-full flex items-center justify-center p-8 bg-cover bg-center hidden relative" style="background-image: url('https://source.unsplash.com/random/1920x1080?university,library');">
            
            <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
            <!-- Botón de Tema (Añadido) -->
            <div class="absolute top-6 right-6 z-20">
                <button class="theme-toggle-btn p-2 rounded-full text-white bg-black/20 backdrop-blur-sm hover:bg-black/40 transition-colors" title="Cambiar tema">
                    <!-- Icono de Sol -->
                    <svg class="theme-icon-sun w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M12 3v2.25m6.364.386-1.591 1.591M21 12h-2.25m-.386 6.364-1.591-1.591M12 18.75V21m-6.364-.386 1.591-1.591M3 12H.75m.386-6.364 1.591 1.591" />
                    </svg>
                    <!-- Icono de Luna -->
                    <svg class="theme-icon-moon w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25c0 5.385 4.365 9.75 9.75 9.75 2.671 0 5.1-1.057 6.9-2.798Z" />
                    </svg>
                </button>
            </div>
            <!-- ***** FIN DE LA MODIFICACIÓN ***** -->

            <!-- Tarjeta de Login con fondo difuminado -->
            <div class="w-full max-w-4xl bg-white/50 backdrop-blur-xl dark:bg-slate-900/50 dark:backdrop-blur-xl rounded-2xl shadow-2xl overflow-hidden grid grid-cols-1 md:grid-cols-2 animate-fade-in">
                
                <!-- Columna Izquierda: Imagen -->
                <div class="hidden md:block h-full">
                    <img src="https://source.unsplash.com/random/800x600?students,learning" alt="Estudiantes aprendiendo" class="w-full h-full object-cover">
                </div>

                <!-- Columna Derecha: Formulario -->
                <div class="p-8 md:p-12">
                    <!-- LOGO DE LA INSTITUCIÓN (IMAGEN REAL) -->
                    <div class="flex justify-center mb-8">
                        <!-- REEMPLAZA 'src' CON LA URL DE TU LOGO REAL -->
                        <img src="https://via.placeholder.com/150x150.png?text=Logo+Institucion" alt="Logo Institución" class="w-32 h-32 object-contain">
                    </div>

                    <h2 class="text-3xl font-bold text-slate-900 dark:text-white mb-4 text-center md:text-left">Iniciar Sesión</h2>
                    <p class="text-slate-700 dark:text-slate-300 mb-8 text-center md:text-left">Ingresa tus credenciales para acceder.</p>
                    
                    <!-- FORMULARIO DE LOGIN (COMBINADO) -->
                    <div class="space-y-6">
                        <!-- Campos de Usuario y Contraseña -->
                        <div>
                            <label for="username" class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Usuario</label>
                            <input type="text" id="username" name="username" 
                                   class="w-full px-4 py-3 rounded-lg border border-slate-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition dark:bg-slate-800 dark:border-slate-600 dark:text-white dark:focus:ring-blue-400"
                                   placeholder="ej. a123456789">
                        </div>
                        
                        <div>
                            <div class="flex justify-between items-center mb-2">
                                <label for="password" class="block text-sm font-semibold text-slate-700 dark:text-slate-300">Contraseña</label>
                                <a href="#" class="text-sm text-blue-600 hover:underline dark:text-blue-400">¿Olvidaste tu contraseña?</a>
                            </div>
                            <input type="password" id="password" name="password" 
                                   class="w-full px-4 py-3 rounded-lg border border-slate-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition dark:bg-slate-800 dark:border-slate-600 dark:text-white dark:focus:ring-blue-400"
                                   placeholder="••••••••">
                        </div>

                        <!-- Separador visual opcional -->
                        <div class="relative flex items-center py-2">
                            <div class="flex-grow border-t border-slate-300 dark:border-slate-700"></div>
                            <span class="flex-shrink mx-4 text-slate-500 dark:text-slate-400 text-sm">Accesos Directos (Demo)</span>
                            <div class="flex-grow border-t border-slate-300 dark:border-slate-700"></div>
                        </div>

                        <!-- BOTONES PROVISIONALES DE ACCESO -->
                        <div class="space-y-4">
                            <button data-target="estudiante-dashboard" class="login-btn w-full py-3 px-6 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md transition-transform transform hover:scale-105 flex items-center justify-center">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 mr-2">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M4.26 10.147a60.436 60.436 0 00-.491 6.347A48.627 48.627 0 0112 20.904a48.627 48.627 0 018.232-4.41 60.46 60.46 0 00-.491-6.347m-15.482 0a50.57 50.57 0 00-2.658-.813A59.905 59.905 0 0112 3.493a59.902 59.902 0 0110.499 5.221 50.57 50.57 0 00-2.658.814m-15.482 0A50.697 50.697 0 0112 13.489a50.702 50.702 0 017.74-3.342M6.75 15a.75.75 0 100-1.5.75.75 0 000 1.5zm0 0v-3.675A55.378 55.378 0 0112 8.443m-7.007 11.55A5.981 5.981 0 006.75 15.75v-1.5" />
                                </svg>
                                Ingresar como Estudiante
                            </button>
                            <button data-target="docente-dashboard" class="login-btn w-full py-3 px-6 bg-green-600 hover:bg-green-700 text-white font-semibold rounded-lg shadow-md transition-transform transform hover:scale-105 flex items-center justify-center">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 mr-2">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 6.042A8.967 8.967 0 006 3.75c-1.052 0-2.062.18-3 .512v14.25A8.987 8.987 0 016 18c2.305 0 4.408.867 6 2.292m0-14.25a8.966 8.966 0 016-2.292c1.052 0 2.062.18 3 .512v14.25A8.987 8.987 0 0018 18a8.967 8.967 0 00-6 2.292m0-14.25v14.25" />
                                </svg>
                                Ingresar como Docente
                            </button>
                            <button data-target="admin-dashboard" class="login-btn w-full py-3 px-6 bg-slate-700 hover:bg-slate-800 text-white font-semibold rounded-lg shadow-md transition-transform transform hover:scale-105 flex items-center justify-center">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 mr-2">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M9 17.25v1.007a3 3 0 01-.879 2.122L7.5 21h9l-.621-.621A3 3 0 0115 18.257V17.25m6-12V15a2.25 2.25 0 01-2.25 2.25H5.25A2.25 2.25 0 013 15V5.25m18 0A2.25 2.25 0 0018.75 3H5.25A2.25 2.25 0 003 5.25m18 0V12a2.25 2.25 0 01-2.25 2.25H5.25A2.25 2.25 0 013 12V5.25" />
                                </svg>
                                Ingresar como Admin
                            </button>
                        </div>
                    </div>

                </div>
            </div>

        </section>

        <!-- ============================================= -->
        <!-- PANTALLA 3: PANEL ESTUDIANTE (OCULTA)         -->
        <!-- ============================================= -->
        <section id="estudiante-dashboard" class="h-screen w-full flex flex-row hidden bg-blue-50 dark:bg-slate-800">
            
            <!-- Columna Izquierda: Navegación Estática -->
            <nav class="w-72 bg-white dark:bg-slate-900 shadow-lg p-6 flex flex-col flex-shrink-0 overflow-y-auto"> <!-- Aumentado ancho a w-72 y añadido overflow -->
                
                <!-- NUEVA CABECERA DEL SIDEBAR (Logo + Perfil) -->
                <div class="flex flex-col items-center text-center mb-8 space-y-4">
                    <!-- Logo Institución Pequeño -->
                    <div class="p-2 bg-blue-50 dark:bg-slate-800 rounded-full mb-2">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-blue-600 dark:text-blue-400">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 21v-8.25M15.75 21v-8.25M8.25 21v-8.25M3 9l9-6 9 6m-1.5 12V10.332A48.36 48.36 0 0012 9.75c-2.551 0-5.056.2-7.5.582V21M3 21h18M12 6.75h.008v.008H12V6.75z" />
                        </svg>
                    </div>
                    <!-- Foto de Perfil -->
                    <img src="https://source.unsplash.com/random/100x100?portrait" alt="Foto de Perfil" class="w-20 h-20 rounded-full ring-4 ring-blue-50 dark:ring-slate-800">
                    <!-- Info Usuario -->
                    <div>
                        <h2 class="text-lg font-bold text-slate-800 dark:text-slate-200">Nombre Alumno</h2>
                        <p class="text-sm font-medium text-blue-600 dark:text-blue-400">Ingeniería de Sistemas</p>
                        <span class="inline-block mt-1 px-2 py-1 text-xs font-semibold text-blue-800 bg-blue-100 rounded-full dark:bg-blue-900 dark:text-blue-200">Estudiante</span>
                    </div>
                </div>
                
                <!-- Links de Navegación con ICONOS -->
                <div class="flex-1 space-y-1"> <!-- Reducido space-y -->
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mb-2 px-2">Mi Tesis</h3>
                    
                    <!-- Link 1: Estado de Tesis -->
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Estado de Tesis" data-template-id="template-est-estado">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M3 13.125C3 12.504 3.504 12 4.125 12h2.25c.621 0 1.125.504 1.125 1.125v6.75C7.5 20.496 6.996 21 6.375 21h-2.25A1.125 1.125 0 0 1 3 19.875v-6.75ZM9.75 8.625c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125v11.25c0 .621-.504 1.125-1.125 1.125h-2.25a1.125 1.125 0 0 1-1.125-1.125V8.625ZM16.5 4.125c0-.621.504-1.125 1.125-1.125h2.25C20.496 3 21 3.504 21 4.125v15.75c0 .621-.504 1.125-1.125 1.125h-2.25a1.125 1.125 0 0 1-1.125-1.125V4.125Z" />
                        </svg>
                        Estado de Tesis
                    </a>
                    
                    <!-- Link 2: Mi Tesis -->
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Mi Tesis" data-template-id="template-est-mi-tesis">
                         <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m2.25 0H5.625c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z" />
                        </svg>
                        Mi Tesis
                    </a>
                    
                    <!-- Link 3: Comentarios -->
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Comentarios del Docente" data-template-id="template-est-comentarios">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M7.5 8.25h9m-9 3H12m-9.75 1.51c0 1.6 1.123 2.994 2.707 3.227 1.129.166 2.27.293 3.423.379.35.026.67.21.865.501L12 21l2.755-4.133a1.14 1.14 0 0 1 .865-.501 48.172 48.172 0 0 0 3.423-.379c1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0 0 12 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018Z" />
                        </svg>
                        Comentarios
                    </a>
                    
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Organización</h3>
                    
                    <!-- Link 4: Calendario -->
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Calendario" data-template-id="template-est-calendario">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5" />
                        </svg>
                        Calendario
                    </a>
                    
                    <!-- Link 5: Mensajes -->
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Mensajes" data-template-id="template-est-mensajes">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M21.75 6.75v10.5a2.25 2.25 0 0 1-2.25 2.25h-15a2.25 2.25 0 0 1-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0 0 19.5 4.5h-15a2.25 2.25 0 0 0-2.25 2.25m19.5 0v.243a2.25 2.25 0 0 1-1.07 1.916l-7.5 4.615a2.25 2.25 0 0 1-2.36 0L3.32 8.91a2.25 2.25 0 0 1-1.07-1.916V6.75" />
                        </svg>
                        Mensajes
                    </a>
                    
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Cuenta</h3>
                    
                    <!-- Link 6: Configuración -->
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Editar Perfil" data-template-id="template-est-configuracion">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M10.343 3.94c.09-.542.56-.94 1.11-.94h1.093c.55 0 1.02.398 1.11.94l.149.894c.07.424.384.764.78.93.398.164.855.142 1.205-.108l.737-.527a1.125 1.125 0 0 1 1.45.12l.773.774c.39.389.44 1.002.12 1.45l-.527.737c-.25.35-.272.806-.107 1.204.165.397.505.71.93.78l.893.15c.543.09.94.56.94 1.109v1.094c0 .55-.397 1.02-.94 1.11l-.894.149c-.424.07-.764.383-.929.78-.165.398-.143.854.107 1.204l.527.738c.32.447.269 1.06-.12 1.45l-.774.773a1.125 1.125 0 0 1-1.449.12l-.738-.527c-.35-.25-.806-.272-1.203-.107-.397.165-.71.505-.781.929l-.149.894c-.09.542-.56.94-1.11.94h-1.094c-.55 0-1.019-.398-1.11-.94l-.148-.894c-.071-.424-.384-.764-.781-.93-.398-.164-.854-.142-1.204.108l-.738.527c-.447.32-1.06.269-1.45-.12l-.773-.774a1.125 1.125 0 0 1-.12-1.45l.527-.737c.25-.35.273-.806.108-1.204-.165-.397-.506-.71-.93-.78l-.894-.15c-.542-.09-.94-.56-.94-1.109v-1.094c0-.55.398-1.02.94-1.11l.894-.149c.424-.07.765-.383.93-.78.165-.398.143-.854-.108-1.204l-.526-.738a1.125 1.125 0 0 1 .12-1.45l.773-.773a1.125 1.125 0 0 1 1.45-.12l.737.527c.35.25.807.272 1.204.107.397-.165.71-.505.78-.929l.15-.894Z" />
                            <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                        </svg>
                        Configuración
                    </a>
                </div>
            </nav>

            <!-- Columna Derecha: Contenido Principal -->
            <div class="flex-1 flex flex-col h-screen">
                <!-- Cabecera del Contenido -->
                <header class="bg-white dark:bg-slate-900 shadow-md p-4 flex justify-between items-center">
                    <h1 class="text-2xl font-bold text-blue-700 dark:text-blue-300">Panel del Estudiante</h1>
                    
                    <div class="flex items-center space-x-2">
                        <!-- Botón de Tema (NUEVO) -->
                        <button class="theme-toggle-btn p-2 rounded-full text-slate-500 hover:text-blue-600 hover:bg-blue-100 dark:text-slate-400 dark:hover:text-yellow-400 dark:hover:bg-slate-800 transition-colors" title="Cambiar tema">
                            <!-- Icono de Sol -->
                            <svg class="theme-icon-sun w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 3v2.25m6.364.386-1.591 1.591M21 12h-2.25m-.386 6.364-1.591-1.591M12 18.75V21m-6.364-.386 1.591-1.591M3 12H.75m.386-6.364 1.591 1.591" />
                            </svg>
                            <!-- Icono de Luna -->
                            <svg class="theme-icon-moon w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25c0 5.385 4.365 9.75 9.75 9.75 2.671 0 5.1-1.057 6.9-2.798Z" />
                            </svg>
                        </button>

                        <!-- Botón de Cerrar Sesión (Icono) -->
                        <button class="logout-btn p-2 rounded-full text-slate-500 hover:text-red-600 hover:bg-red-100 dark:text-slate-400 dark:hover:bg-red-200 dark:hover:text-red-700 transition-colors" title="Cerrar Sesión">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15M15.75 9l-3.375 3.375M15.75 9h6.75m-6.75 0-3.375-3.375" />
                            </svg>                      
                        </button>
                    </div>
                </header>
                
                <!-- Área de Contenido Desplazable -->
                <div class="flex-1 p-8 overflow-y-auto"> <!-- Añadido overflow-y-auto -->
                    <h2 class="text-3xl font-bold text-slate-800 dark:text-slate-100 mb-8 animate-fade-in" style="animation-delay: 0.1s;">Bienvenido, Estudiante</h2>
                    
                    <!-- Resumen General del Estudiante -->
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 animate-fade-in" style="animation-delay: 0.2s;">
                        
                        <!-- Card 1: Estado de Tesis (AHORA INTERACTIVA) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <!-- Se añadieron: class="dashboard-card ... cursor-pointer ...", data-target-page, data-title, data-template-id -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="estudiante-subpage" data-title="Estado de Tesis" data-template-id="template-est-estado">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-blue-100 text-blue-600 dark:bg-blue-900 dark:text-blue-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m-1.125 0H5.625A2.25 2.25 0 0 0 3.375 6v13.5a2.25 2.25 0 0 0 2.25 2.25h13.5A2.25 2.25 0 0 0 20.625 18V9.875c0-1.018-.42-1.97-1.125-2.625L14.25 4.5Z" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Estado de Tesis (En Progreso)</p>
                                <p class="text-lg font-bold text-blue-700 dark:text-blue-300">En Revisión</p>
                            </div>
                        </div>

                        <!-- Card 2: Próximo Evento (AHORA INTERACTIVA -> Calendario) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="estudiante-subpage" data-title="Calendario" data-template-id="template-est-calendario">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-yellow-100 text-yellow-600 dark:bg-yellow-900 dark:text-yellow-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Calendario (Próxima Revisión)</p>
                                <p class="text-lg font-bold text-slate-800 dark:text-slate-100">15 Oct: Entrega Correcciones</pre>
                            </div>
                        </div>
                        
                        <!-- Card 3: Mensajes (AHORA INTERACTIVA -> Comentarios) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="estudiante-subpage" data-title="Comentarios del Docente" data-template-id="template-est-comentarios">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-green-100 text-green-600 dark:bg-green-900 dark:text-green-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 12.76c.243-1.07 1.13-1.9.918-3.097C3.01 8.52 3.961 7.5 5.105 7.5h13.79c1.144 0 2.095 1.02 1.913 2.163-.18 1.197.675 2.027.918 3.097.243 1.07.094 2.23-.413 3.189l-.523 1.046a.75.75 0 0 1-.682.46H5.105a.75.75 0 0 1-.682-.46l-.523-1.046C3.473 15 3.324 13.83 3.567 12.76Z" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Comentarios</s-p>
                                <p class="text-lg font-bold text-slate-800 dark:text-slate-100">1 nuevo del Dr. Ramos</p>
                            </div>
                        </div>

                    </div>
                    <!-- Fin del Resumen -->
                    
                </div>
            </div>
        </section>

        <!-- ============================================= -->
        <!-- PANTALLA 4: PANEL DOCENTE (OCULTA)            -->
        <!-- ============================================= -->
        <section id="docente-dashboard" class="h-screen w-full flex flex-row hidden bg-green-50 dark:bg-slate-800">
            
            <!-- Columna Izquierda: Navegación Estática -->
            <nav class="w-72 bg-white dark:bg-slate-900 shadow-lg p-6 flex flex-col flex-shrink-0 overflow-y-auto">
                
                <!-- NUEVA CABECERA SIDEBAR DOCENTE -->
                <div class="flex flex-col items-center text-center mb-8 space-y-4">
                    <!-- Logo Institución Pequeño -->
                    <div class="p-2 bg-green-50 dark:bg-slate-800 rounded-full mb-2">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-green-600 dark:text-green-400">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 21v-8.25M15.75 21v-8.25M8.25 21v-8.25M3 9l9-6 9 6m-1.5 12V10.332A48.36 48.36 0 0012 9.75c-2.551 0-5.056.2-7.5.582V21M3 21h18M12 6.75h.008v.008H12V6.75z" />
                        </svg>
                    </div>
                    <img src="https://source.unsplash.com/random/100x100?portrait,professor" alt="Foto de Perfil" class="w-20 h-20 rounded-full ring-4 ring-green-50 dark:ring-slate-800">
                    <div>
                        <h2 class="text-lg font-bold text-slate-800 dark:text-slate-200">Dr. Ramos</h2>
                        <p class="text-sm font-medium text-green-600 dark:text-green-400">Dpto. Ciencias de la Computación</p>
                         <span class="inline-block mt-1 px-2 py-1 text-xs font-semibold text-green-800 bg-green-100 rounded-full dark:bg-green-900 dark:text-green-200">Docente</span>
                    </div>
                </div>
                
                <!-- Links de Navegación con ICONOS -->
                <div class="flex-1 space-y-1">
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mb-2 px-2">Revisión</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Tesis Asignadas" data-template-id="template-doc-asignadas">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 6.042A8.967 8.967 0 0 0 6 3.75c-1.052 0-2.062.18-3 .512v14.25A8.987 8.987 0 0 1 6 18c2.305 0 4.408.867 6 2.292m0-14.25a8.966 8.966 0 0 1 6-2.292c1.052 0 2.062.18 3 .512v14.25A8.987 8.987 0 0 0 18 18a8.967 8.967 0 0 0-6 2.292m0-14.25v14.25" />
                        </svg>
                        Tesis Asignadas
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Calificar Tesis" data-template-id="template-doc-calificar">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12c0 1.268-.63 2.39-1.593 3.068a3.745 3.745 0 0 1-1.043 3.296 3.745 3.745 0 0 1-3.296 1.043A3.745 3.745 0 0 1 12 21c-1.268 0-2.39-.63-3.068-1.593a3.746 3.746 0 0 1-3.296-1.043 3.745 3.745 0 0 1-1.043-3.296A3.745 3.745 0 0 1 3 12c0-1.268.63-2.39 1.593-3.068a3.745 3.745 0 0 1 1.043-3.296 3.746 3.746 0 0 1 3.296-1.043A3.746 3.746 0 0 1 12 3c1.268 0 2.39.63 3.068 1.593a3.746 3.746 0 0 1 3.296 1.043 3.746 3.746 0 0 1 1.043 3.296A3.745 3.745 0 0 1 21 12Z" />
                        </svg>
                        Calificar Tesis
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Historial de Revisiones" data-template-id="template-doc-historial">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                        </svg>
                        Historial
                    </a>

                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Organización</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Calendario de Entregas" data-template-id="template-est-calendario">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5" />
                        </svg>
                        Calendario
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Mensajería Interna" data-template-id="template-est-mensajes">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M21.75 6.75v10.5a2.25 2.25 0 0 1-2.25 2.25h-15a2.25 2.25 0 0 1-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0 0 19.5 4.5h-15a2.25 2.25 0 0 0-2.25 2.25m19.5 0v.243a2.25 2.25 0 0 1-1.07 1.916l-7.5 4.615a2.25 2.25 0 0 1-2.36 0L3.32 8.91a2.25 2.25 0 0 1-1.07-1.916V6.75" />
                        </svg>
                        Mensajería
                    </a>

                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Cuenta</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Editar Perfil" data-template-id="template-doc-configuracion">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M10.343 3.94c.09-.542.56-.94 1.11-.94h1.093c.55 0 1.02.398 1.11.94l.149.894c.07.424.384.764.78.93.398.164.855.142 1.205-.108l.737-.527a1.125 1.125 0 0 1 1.45.12l.773.774c.39.389.44 1.002.12 1.45l-.527.737c-.25.35-.272.806-.107 1.204.165.397.505.71.93.78l.893.15c.543.09.94.56.94 1.109v1.094c0 .55-.397 1.02-.94 1.11l-.894.149c-.424.07-.764.383-.929.78-.165.398-.143.854.107 1.204l.527.738c.32.447.269 1.06-.12 1.45l-.774.773a1.125 1.125 0 0 1-1.449.12l-.738-.527c-.35-.25-.806-.272-1.203-.107-.397.165-.71.505-.781.929l-.149.894c-.09.542-.56.94-1.11.94h-1.094c-.55 0-1.019-.398-1.11-.94l-.148-.894c-.071-.424-.384-.764-.781-.93-.398-.164-.854-.142-1.204.108l-.738.527c-.447.32-1.06.269-1.45-.12l-.773-.774a1.125 1.125 0 0 1-.12-1.45l.527-.737c.25-.35.273-.806.108-1.204-.165-.397-.506-.71-.93-.78l-.894-.15c-.542-.09-.94-.56-.94-1.109v-1.094c0-.55.398-1.02.94-1.11l.894-.149c.424-.07.765-.383.93-.78.165-.398.143-.854-.108-1.204l-.526-.738a1.125 1.125 0 0 1 .12-1.45l.773-.773a1.125 1.125 0 0 1 1.45-.12l.737.527c.35.25.807.272 1.204.107.397-.165.71-.505.78-.929l.15-.894Z" />
                            <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                        </svg>
                        Configuración
                    </a>
                </div>
            </nav>
            
            <!-- Columna Derecha: Contenido Principal -->
            <div class="flex-1 flex flex-col h-screen">
                <!-- Cabecera del Contenido -->
                <header class="bg-white dark:bg-slate-900 shadow-md p-4 flex justify-between items-center">
                    <h1 class="text-2xl font-bold text-green-700 dark:text-green-300">Panel del Docente</h1>
                    <div class="flex items-center space-x-2">
                        <!-- Botón de Tema (NUEVO) -->
                        <button class="theme-toggle-btn p-2 rounded-full text-slate-500 hover:text-green-600 hover:bg-green-100 dark:text-slate-400 dark:hover:text-yellow-400 dark:hover:bg-slate-800 transition-colors" title="Cambiar tema">
                            <!-- Icono de Sol -->
                            <svg class="theme-icon-sun w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 3v2.25m6.364.386-1.591 1.591M21 12h-2.25m-.386 6.364-1.591-1.591M12 18.75V21m-6.364-.386 1.591-1.591M3 12H.75m.386-6.364 1.591 1.591" />
                            </svg>
                            <!-- Icono de Luna -->
                            <svg class="theme-icon-moon w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25c0 5.385 4.365 9.75 9.75 9.75 2.671 0 5.1-1.057 6.9-2.798Z" />
                            </svg>
                        </button>
                        <!-- Botón de Cerrar Sesión (Icono) -->
                        <button class="logout-btn p-2 rounded-full text-slate-500 hover:text-red-600 hover:bg-red-100 dark:text-slate-400 dark:hover:bg-red-200 dark:hover:text-red-700 transition-colors" title="Cerrar Sesión">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15M15.75 9l-3.375 3.375M15.75 9h6.75m-6.75 0-3.375-3.375" />
                            </svg>                      
                        </button>
                    </div>
                </header>
                
                <!-- Área de Contenido Desplazable -->
                <div class="flex-1 p-8 overflow-y-auto">
                    <h2 class="text-3xl font-bold text-slate-800 dark:text-slate-100 mb-8 animate-fade-in" style="animation-delay: 0.1s;">Bienvenido, Docente</h2>
                    
                    <!-- Resumen General del Docente -->
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 animate-fade-in" style="animation-delay: 0.2s;">
                        
                        <!-- Card 1: Tesis Pendientes (AHORA INTERACTIVA -> Tesis Asignadas) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="docente-subpage" data-title="Tesis Asignadas" data-template-id="template-doc-asignadas">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-red-100 text-red-600 dark:bg-red-900 dark:text-red-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Tesis Pendientes</p>
                                <p class="text-2xl font-bold text-red-700 dark:text-red-400">3</p>
                            </div>
                        </div>

                        <!-- Card 2: Próxima Entrega (AHORA INTERACTIVA -> Calendario) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="docente-subpage" data-title="Calendario de Entregas" data-template-id="template-est-calendario">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-yellow-100 text-yellow-600 dark:bg-yellow-900 dark:text-yellow-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Próxima Entrega</p>
                                <p class="text-lg font-bold text-slate-800 dark:text-slate-100">Ana García (5 días)</p>
                            </div>
                        </div>
                        
                        <!-- Card 3: Mensajes (AHORA INTERACTIVA -> Mensajería) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="docente-subpage" data-title="Mensajería Interna" data-template-id="template-est-mensajes">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-blue-100 text-blue-600 dark:bg-blue-900 dark:text-blue-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 12.76c.243-1.07 1.13-1.9.918-3.097C3.01 8.52 3.961 7.5 5.105 7.5h13.79c1.144 0 2.095 1.02 1.913 2.163-.18 1.197.675 2.027.918 3.097.243 1.07.094 2.23-.413 3.189l-.523 1.046a.75.75 0 0 1-.682.46H5.105a.75.75 0 0 1-.682-.46l-.523-1.046C3.473 15 3.324 13.83 3.567 12.76Z" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Mensajes de Alumnos</p>
                                <p class="text-lg font-bold text-slate-800 dark:text-slate-100">2 sin leer</p>
                            </div>
                        </div>

                    </div>
                    <!-- Fin del Resumen -->
                </div>
            </div>
        </section>

        <!-- ============================================= -->
        <!-- PANTALLA 5: PANEL ADMIN (OCULTA)              -->
        <!-- ============================================= -->
        <section id="admin-dashboard" class="h-screen w-full flex flex-row hidden bg-slate-50 dark:bg-slate-800">
            
            <!-- Columna Izquierda: Navegación Estática -->
            <nav class="w-72 bg-white dark:bg-slate-900 shadow-lg p-6 flex flex-col flex-shrink-0 overflow-y-auto">
                
                <!-- NUEVA CABECERA SIDEBAR ADMIN -->
                <div class="flex flex-col items-center text-center mb-8 space-y-4">
                    <!-- Logo Institución Pequeño -->
                    <div class="p-2 bg-slate-100 dark:bg-slate-800 rounded-full mb-2">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-slate-600 dark:text-slate-400">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 21v-8.25M15.75 21v-8.25M8.25 21v-8.25M3 9l9-6 9 6m-1.5 12V10.332A48.36 48.36 0 0012 9.75c-2.551 0-5.056.2-7.5.582V21M3 21h18M12 6.75h.008v.008H12V6.75z" />
                        </svg>
                    </div>
                    <img src="https://source.unsplash.com/random/100x100?portrait,manager" alt="Foto de Perfil" class="w-20 h-20 rounded-full ring-4 ring-slate-200 dark:ring-slate-800">
                    <div>
                        <h2 class="text-lg font-bold text-slate-800 dark:text-slate-200">Admin. Principal</h2>
                        <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Oficina de Grados y Títulos</p>
                         <span class="inline-block mt-1 px-2 py-1 text-xs font-semibold text-slate-800 bg-slate-200 rounded-full dark:bg-slate-700 dark:text-slate-300">Administrador</span>
                    </div>
                </div>
                
                <!-- Links de Navegación con ICONOS -->
                <div class="flex-1 space-y-1">
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mb-2 px-2">Gestión</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Gestión de Tesis (CRUD)" data-template-id="template-admin-gestion-tesis">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m0 12.75h7.5m-7.5 3H12M10.5 2.25H5.625c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z" />
                        </svg>
                        Gestión de Tesis
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Gestión de Usuarios (CRUD)" data-template-id="template-admin-gestion-usuarios">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z" />
                        </svg>
                        Usuarios
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Asignar Revisiones" data-template-id="template-admin-asignar">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M7.5 21 3 16.5m0 0L7.5 12M3 16.5h13.5m0-13.5L21 7.5m0 0L16.5 12M21 7.5H7.5" />
                        </svg>
                        Asignar Revisiones
                    </a>

                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Sistema</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Estadísticas" data-template-id="template-admin-estadisticas">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 3v11.25A2.25 2.25 0 0 0 6 16.5h2.25M3.75 21h16.5A2.25 2.25 0 0 0 22.5 18.75v-11.25A2.25 2.25 0 0 0 20.25 5.25h-2.25m-9 13.5v-6m0 0L6.75 12M12.75 18.75v-6m0 0 2.25-2.25M12.75 18.75v-6m0 0L16.5 12M3.75 21v-9m16.5 0v9M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                        </svg>
                        Estadísticas
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Reportes" data-template-id="template-admin-reportes">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m6.75 12-3-3m0 0-3 3m3-3v6m-1.5-15H5.625c-.621 0-1.125.504-1.125 1.125v13.5c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z" />
                        </svg>
                        Reportes
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Configuración" data-template-id="template-admin-configuracion">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M10.343 3.94c.09-.542.56-.94 1.11-.94h1.093c.55 0 1.02.398 1.11.94l.149.894c.07.424.384.764.78.93.398.164.855.142 1.205-.108l.737-.527a1.125 1.125 0 0 1 1.45.12l.773.774c.39.389.44 1.002.12 1.45l-.527.737c-.25.35-.272.806-.107 1.204.165.397.505.71.93.78l.893.15c.543.09.94.56.94 1.109v1.094c0 .55-.397 1.02-.94 1.11l-.894.149c-.424.07-.764.383-.929.78-.165.398-.143.854.107 1.204l.527.738c.32.447.269 1.06-.12 1.45l-.774.773a1.125 1.125 0 0 1-1.449.12l-.738-.527c-.35-.25-.806-.272-1.203-.107-.397.165-.71.505-.781.929l-.149.894c-.09.542-.56.94-1.11.94h-1.094c-.55 0-1.019-.398-1.11-.94l-.148-.894c-.071-.424-.384-.764-.781-.93-.398-.164-.854-.142-1.204.108l-.738.527c-.447.32-1.06.269-1.45-.12l-.773-.774a1.125 1.125 0 0 1-.12-1.45l.527-.737c.25-.35.273-.806.108-1.204-.165-.397-.506-.71-.93-.78l-.894-.15c-.542-.09-.94-.56-.94-1.109v-1.094c0-.55.398-1.02.94-1.11l.894-.149c.424-.07.765-.383.93-.78.165-.398.143-.854-.108-1.204l-.526-.738a1.125 1.125 0 0 1 .12-1.45l.773-.773a1.125 1.125 0 0 1 1.45-.12l.737.527c.35.25.807.272 1.204.107.397-.165.71-.505.78-.929l.15-.894Z" />
                            <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                        </svg>
                        Configuración
                    </a>
                </div>
            </nav>
            
            <!-- Columna Derecha: Contenido Principal -->
            <div class="flex-1 flex flex-col h-screen">
                <!-- Cabecera del Contenido -->
                <header class="bg-white dark:bg-slate-900 shadow-md p-4 flex justify-between items-center">
                    <h1 class="text-2xl font-bold text-slate-700 dark:text-slate-300">Panel de Administrador</h1>
                    <div class="flex items-center space-x-2">
                        <!-- Botón de Tema (NUEVO) -->
                        <button class="theme-toggle-btn p-2 rounded-full text-slate-500 hover:text-slate-800 hover:bg-slate-100 dark:text-slate-400 dark:hover:text-yellow-400 dark:hover:bg-slate-800 transition-colors" title="Cambiar tema">
                            <!-- Icono de Sol -->
                            <svg class="theme-icon-sun w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 3v2.25m6.364.386-1.591 1.591M21 12h-2.25m-.386 6.364-1.591-1.591M12 18.75V21m-6.364-.386 1.591-1.591M3 12H.75m.386-6.364 1.591 1.591" />
                            </svg>
                            <!-- Icono de Luna -->
                            <svg class="theme-icon-moon w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25c0 5.385 4.365 9.75 9.75 9.75 2.671 0 5.1-1.057 6.9-2.798Z" />
                            </svg>
                        </button>
                        <!-- Botón de Cerrar Sesión (Icono) -->
                        <button class="logout-btn p-2 rounded-full text-slate-500 hover:text-red-600 hover:bg-red-100 dark:text-slate-400 dark:hover:bg-red-200 dark:hover:text-red-700 transition-colors" title="Cerrar Sesión">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15M15.75 9l-3.375 3.375M15.75 9h6.75m-6.75 0-3.375-3.375" />
                            </svg>                      
                        </button>
                    </div>
                </header>
                
                <!-- Área de Contenido Desplazable -->
                <div class="flex-1 p-8 overflow-y-auto">
                    <h2 class="text-3xl font-bold text-slate-800 dark:text-slate-100 mb-8 animate-fade-in" style="animation-delay: 0.1s;">Panel de Control General</h2>

                    <!-- Resumen General del Administrador -->
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 animate-fade-in" style="animation-delay: 0.2s;">
                        
                        <!-- Card 1: Total Tesis (AHORA INTERACTIVA -> Gestión de Tesis) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="admin-subpage" data-title="Gestión de Tesis (CRUD)" data-template-id="template-admin-gestion-tesis">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-blue-100 text-blue-600 dark:bg-blue-900 dark:text-blue-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m-1.125 0H5.625A2.25 2.25 0 0 0 3.375 6v13.5a2.25 2.25 0 0 0 2.25 2.25h13.5A2.25 2.25 0 0 0 20.625 18V9.875c0-1.018-.42-1.97-1.125-2.625L14.25 4.5Z" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Tesis en Sistema</p>
                                <p class="text-2xl font-bold text-slate-800 dark:text-slate-100">27</p>
                            </div>
                        </div>

                        <!-- Card 2: Total Usuarios (AHORA INTERACTIVA -> Gestión de Usuarios) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="admin-subpage" data-title="Gestión de Usuarios (CRUD)" data-template-id="template-admin-gestion-usuarios">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-green-100 text-green-600 dark:bg-green-900 dark:text-green-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M18 18.72a9.094 9.094 0 0 0 3.741-.479 3 3 0 0 0-4.672-2.97l-4.143-2.366c.114-.33.176-.68.176-1.037 0-.357-.062-.707-.176-1.037l4.143-2.366a3 3 0 1 0-.82-1.49l-4.143 2.366A3.989 3.989 0 0 0 12 6c-.357 0-.707.062-1.037.176L6.82 3.808a3 3 0 1 0-.82 1.49l4.143 2.366c-.114.33-.176.68-.176 1.037s.062.707.176 1.037l-4.143 2.366a3 3 0 1 0 .82 1.49l4.143-2.366c.114.33.176.68.176 1.037 0 .357-.062.707-.176 1.037l-4.143 2.366A3 3 0 1 0 5.28 18.192l4.143-2.366a3.989 3.989 0 0 0 2.577 0l4.143 2.366a3 3 0 1 0 .82-1.49Z" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Usuarios Activos</p>
                                <p class="text-2xl font-bold text-slate-800 dark:text-slate-100">128</p>
                            </div>
                        </div>

                        <!-- Card 3: Tesis sin Asignar (AHORA INTERACTIVA -> Asignar Revisiones) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="admin-subpage" data-title="Asignar Revisiones" data-template-id="template-admin-asignar">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-yellow-100 text-yellow-600 dark:bg-yellow-900 dark:text-yellow-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M9.879 7.519c1.171-1.025 3.071-1.025 4.242 0 1.172 1.025 1.172 2.687 0 3.712-.203.179-.43.326-.67.442-.745.361-1.45.999-1.45 1.827v.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9 5.25h.008v.008H12v-.008Z" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Tesis Sin Asignar</s-p>
                                <p class="text-2xl font-bold text-yellow-700 dark:text-yellow-400">2</p>
                            </div>
                        </div>
                        
                        <!-- Card 4: Reportes (AHORA INTERACTIVA -> Reportes) -->
                        <!-- ***** INICIO DE LA MODIFICACIÓN ***** -->
                        <div class="dashboard-card bg-white dark:bg-slate-800 p-6 rounded-lg shadow-md flex items-center space-x-4 cursor-pointer hover:bg-slate-50 dark:hover:bg-slate-700 transition-colors" data-target-page="admin-subpage" data-title="Reportes" data-template-id="template-admin-reportes">
                        <!-- ***** FIN DE LA MODIFICACIÓN ***** -->
                            <!-- Icono -->
                            <div class="p-3 rounded-full bg-slate-100 text-slate-600 dark:bg-slate-700 dark:text-slate-300">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M3 16.5v2.25A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75V16.5M16.5 12 12 16.5m0 0L7.5 12m4.5 4.5V3" />
                                </svg>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Reportes</s-p>
                                <span class="text-base font-bold text-slate-800 dark:text-slate-100">Ver Opciones</span>
                            </div>
                        </div>

                    </div>
                    <!-- Fin del Resumen -->
                </div>
            </div>
        </section>

        <!-- ============================================= -->
        <!-- PANTALLAS DE SUB-PÁGINAS (OCULTAS)            -->
        <!-- ============================================= -->

        <!-- Sub-página Estudiante -->
        <section id="estudiante-subpage" class="h-screen w-full flex flex-row hidden bg-blue-50 dark:bg-slate-800">
            <!-- Columna Izquierda: Navegación (REPETIDA con cambios de perfil e iconos) -->
            <nav class="w-72 bg-white dark:bg-slate-900 shadow-lg p-6 flex flex-col flex-shrink-0 overflow-y-auto">
                 <div class="flex flex-col items-center text-center mb-8 space-y-4">
                    <div class="p-2 bg-blue-50 dark:bg-slate-800 rounded-full mb-2">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-blue-600 dark:text-blue-400">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 21v-8.25M15.75 21v-8.25M8.25 21v-8.25M3 9l9-6 9 6m-1.5 12V10.332A48.36 48.36 0 0012 9.75c-2.551 0-5.056.2-7.5.582V21M3 21h18M12 6.75h.008v.008H12V6.75z" />
                        </svg>
                    </div>
                    <img src="https://source.unsplash.com/random/100x100?portrait" alt="Foto de Perfil" class="w-20 h-20 rounded-full ring-4 ring-blue-50 dark:ring-slate-800">
                    <div>
                        <h2 class="text-lg font-bold text-slate-800 dark:text-slate-200">Nombre Alumno</h2>
                        <p class="text-sm font-medium text-blue-600 dark:text-blue-400">Ingeniería de Sistemas</p>
                        <span class="inline-block mt-1 px-2 py-1 text-xs font-semibold text-blue-800 bg-blue-100 rounded-full dark:bg-blue-900 dark:text-blue-200">Estudiante</span>
                    </div>
                </div>
                <div class="flex-1 space-y-1">
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mb-2 px-2">Mi Tesis</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Estado de Tesis" data-template-id="template-est-estado">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M3 13.125C3 12.504 3.504 12 4.125 12h2.25c.621 0 1.125.504 1.125 1.125v6.75C7.5 20.496 6.996 21 6.375 21h-2.25A1.125 1.125 0 0 1 3 19.875v-6.75ZM9.75 8.625c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125v11.25c0 .621-.504 1.125-1.125 1.125h-2.25a1.125 1.125 0 0 1-1.125-1.125V8.625ZM16.5 4.125c0-.621.504-1.125 1.125-1.125h2.25C20.496 3 21 3.504 21 4.125v15.75c0 .621-.504 1.125-1.125 1.125h-2.25a1.125 1.125 0 0 1-1.125-1.125V4.125Z" /></svg>
                        Estado de Tesis
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Mi Tesis" data-template-id="template-est-mi-tesis">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m2.25 0H5.625c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z" /></svg>
                        Mi Tesis
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Comentarios del Docente" data-template-id="template-est-comentarios">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M7.5 8.25h9m-9 3H12m-9.75 1.51c0 1.6 1.123 2.994 2.707 3.227 1.129.166 2.27.293 3.423.379.35.026.67.21.865.501L12 21l2.755-4.133a1.14 1.14 0 0 1 .865-.501 48.172 48.172 0 0 0 3.423-.379c1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0 0 12 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018Z" /></svg>
                        Comentarios
                    </a>
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Organización</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Calendario" data-template-id="template-est-calendario">
                         <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5" /></svg>
                        Calendario
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Mensajes" data-template-id="template-est-mensajes">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M21.75 6.75v10.5a2.25 2.25 0 0 1-2.25 2.25h-15a2.25 2.25 0 0 1-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0 0 19.5 4.5h-15a2.25 2.25 0 0 0-2.25 2.25m19.5 0v.243a2.25 2.25 0 0 1-1.07 1.916l-7.5 4.615a2.25 2.25 0 0 1-2.36 0L3.32 8.91a2.25 2.25 0 0 1-1.07-1.916V6.75" /></svg>
                        Mensajes
                    </a>
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Cuenta</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-blue-50 hover:text-blue-600 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-blue-400 transition-colors" data-target-page="estudiante-subpage" data-title="Editar Perfil" data-template-id="template-est-configuracion">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M10.343 3.94c.09-.542.56-.94 1.11-.94h1.093c.55 0 1.02.398 1.11.94l.149.894c.07.424.384.764.78.93.398.164.855.142 1.205-.108l.737-.527a1.125 1.125 0 0 1 1.45.12l.773.774c.39.389.44 1.002.12 1.45l-.527.737c-.25.35-.272.806-.107 1.204.165.397.505.71.93.78l.893.15c.543.09.94.56.94 1.109v1.094c0 .55-.397 1.02-.94 1.11l-.894.149c-.424.07-.764.383-.929.78-.165.398-.143.854.107 1.204l.527.738c.32.447.269 1.06-.12 1.45l-.774.773a1.125 1.125 0 0 1-1.449.12l-.738-.527c-.35-.25-.806-.272-1.203-.107-.397.165-.71.505-.781.929l-.149.894c-.09.542-.56.94-1.11.94h-1.094c-.55 0-1.019-.398-1.11-.94l-.148-.894c-.071-.424-.384-.764-.781-.93-.398-.164-.854-.142-1.204.108l-.738.527c-.447.32-1.06.269-1.45-.12l-.773-.774a1.125 1.125 0 0 1-.12-1.45l.527-.737c.25-.35.273-.806.108-1.204-.165-.397-.506-.71-.93-.78l-.894-.15c-.542-.09-.94-.56-.94-1.109v-1.094c0-.55.398-1.02.94-1.11l.894-.149c.424-.07.765-.383.93-.78.165-.398.143-.854-.108-1.204l-.526-.738a1.125 1.125 0 0 1 .12-1.45l.773-.773a1.125 1.125 0 0 1 1.45-.12l.737.527c.35.25.807.272 1.204.107.397-.165.71-.505.78-.929l.15-.894Z" /><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" /></svg>
                        Configuración
                    </a>
                </div>
            </nav>

            <!-- Columna Derecha: Contenido de Sub-página -->
            <div class="flex-1 flex flex-col h-screen">
                <header class="bg-white dark:bg-slate-900 shadow-md p-4 flex justify-between items-center">
                    <button class="back-btn btn-fill" data-target-dashboard="estudiante-dashboard">
                        &larr; Volver
                    </button>
                    <h1 id="subpage-title-estudiante" class="text-2xl font-bold text-blue-700 dark:text-blue-300"></h1>
                    
                    <div class="flex items-center space-x-2">
                        <!-- Botón de Tema (NUEVO) -->
                        <button class="theme-toggle-btn p-2 rounded-full text-slate-500 hover:text-blue-600 hover:bg-blue-100 dark:text-slate-400 dark:hover:text-yellow-400 dark:hover:bg-slate-800 transition-colors" title="Cambiar tema">
                            <!-- Icono de Sol -->
                            <svg class="theme-icon-sun w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 3v2.25m6.364.386-1.591 1.591M21 12h-2.25m-.386 6.364-1.591-1.591M12 18.75V21m-6.364-.386 1.591-1.591M3 12H.75m.386-6.364 1.591 1.591" />
                            </svg>
                            <!-- Icono de Luna -->
                            <svg class="theme-icon-moon w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25c0 5.385 4.365 9.75 9.75 9.75 2.671 0 5.1-1.057 6.9-2.798Z" />
                            </svg>
                        </button>
                        <!-- Botón de Cerrar Sesión (Icono) -->
                        <button class="logout-btn p-2 rounded-full text-slate-500 hover:text-red-600 hover:bg-red-100 dark:text-slate-400 dark:hover:bg-red-200 dark:hover:text-red-700 transition-colors" title="Cerrar Sesión">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15M15.75 9l-3.375 3.375M15.75 9h6.75m-6.75 0-3.375-3.375" />
                            </svg>                      
                        </button>
                    </div>
                </header>
                <div id="subpage-content-estudiante" class="flex-1 p-8 overflow-y-auto">
                    <!-- El contenido se inyectará aquí -->
                    <p>Cargando contenido...</p>
                </div>
            </div>
        </section>

        <!-- Sub-página Docente -->
        <section id="docente-subpage" class="h-screen w-full flex flex-row hidden bg-green-50 dark:bg-slate-800">
            <!-- Columna Izquierda: Navegación (REPETIDA con cambios de perfil e iconos) -->
            <nav class="w-72 bg-white dark:bg-slate-900 shadow-lg p-6 flex flex-col flex-shrink-0 overflow-y-auto">
                <div class="flex flex-col items-center text-center mb-8 space-y-4">
                    <div class="p-2 bg-green-50 dark:bg-slate-800 rounded-full mb-2">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-green-600 dark:text-green-400">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 21v-8.25M15.75 21v-8.25M8.25 21v-8.25M3 9l9-6 9 6m-1.5 12V10.332A48.36 48.36 0 0012 9.75c-2.551 0-5.056.2-7.5.582V21M3 21h18M12 6.75h.008v.008H12V6.75z" />
                        </svg>
                    </div>
                    <img src="https://source.unsplash.com/random/100x100?portrait,professor" alt="Foto de Perfil" class="w-20 h-20 rounded-full ring-4 ring-green-50 dark:ring-slate-800">
                    <div>
                        <h2 class="text-lg font-bold text-slate-800 dark:text-slate-200">Dr. Ramos</h2>
                        <p class="text-sm font-medium text-green-600 dark:text-green-400">Dpto. Ciencias de la Computación</p>
                         <span class="inline-block mt-1 px-2 py-1 text-xs font-semibold text-green-800 bg-green-100 rounded-full dark:bg-green-900 dark:text-green-200">Docente</span>
                    </div>
                </div>
                <div class="flex-1 space-y-1">
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mb-2 px-2">Revisión</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Tesis Asignadas" data-template-id="template-doc-asignadas">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M12 6.042A8.967 8.967 0 0 0 6 3.75c-1.052 0-2.062.18-3 .512v14.25A8.987 8.987 0 0 1 6 18c2.305 0 4.408.867 6 2.292m0-14.25a8.966 8.966 0 0 1 6-2.292c1.052 0 2.062.18 3 .512v14.25A8.987 8.987 0 0 0 18 18a8.967 8.967 0 0 0-6 2.292m0-14.25v14.25" /></svg>
                        Tesis Asignadas
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Calificar Tesis" data-template-id="template-doc-calificar">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12c0 1.268-.63 2.39-1.593 3.068a3.745 3.745 0 0 1-1.043 3.296 3.745 3.745 0 0 1-3.296 1.043A3.745 3.745 0 0 1 12 21c-1.268 0-2.39-.63-3.068-1.593a3.746 3.746 0 0 1-3.296-1.043 3.745 3.745 0 0 1-1.043-3.296A3.745 3.745 0 0 1 3 12c0-1.268.63-2.39 1.593-3.068a3.745 3.745 0 0 1 1.043-3.296 3.746 3.746 0 0 1 3.296-1.043A3.746 3.746 0 0 1 12 3c1.268 0 2.39.63 3.068 1.593a3.746 3.746 0 0 1 3.296 1.043 3.746 3.746 0 0 1 1.043 3.296A3.745 3.745 0 0 1 21 12Z" /></svg>
                        Calificar Tesis
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Historial de Revisiones" data-template-id="template-doc-historial">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" /></svg>
                        Historial de Revisiones
                    </a>
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Organización</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Calendario de Entregas" data-template-id="template-est-calendario">
                         <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5" /></svg>
                        Calendario de Entregas
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Mensajería Interna" data-template-id="template-est-mensajes">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M21.75 6.75v10.5a2.25 2.25 0 0 1-2.25 2.25h-15a2.25 2.25 0 0 1-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0 0 19.5 4.5h-15a2.25 2.25 0 0 0-2.25 2.25m19.5 0v.243a2.25 2.25 0 0 1-1.07 1.916l-7.5 4.615a2.25 2.25 0 0 1-2.36 0L3.32 8.91a2.25 2.25 0 0 1-1.07-1.916V6.75" /></svg>
                        Mensajería
                    </a>
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Cuenta</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-green-50 hover:text-green-700 dark:text-slate-300 dark:hover:bg-slate-800 dark:hover:text-green-400 transition-colors" data-target-page="docente-subpage" data-title="Editar Perfil" data-template-id="template-doc-configuracion">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M10.343 3.94c.09-.542.56-.94 1.11-.94h1.093c.55 0 1.02.398 1.11.94l.149.894c.07.424.384.764.78.93.398.164.855.142 1.205-.108l.737-.527a1.125 1.125 0 0 1 1.45.12l.773.774c.39.389.44 1.002.12 1.45l-.527.737c-.25.35-.272.806-.107 1.204.165.397.505.71.93.78l.893.15c.543.09.94.56.94 1.109v1.094c0 .55-.397 1.02-.94 1.11l-.894.149c-.424.07-.764.383-.929.78-.165.398-.143.854.107 1.204l.527.738c.32.447.269 1.06-.12 1.45l-.774.773a1.125 1.125 0 0 1-1.449.12l-.738-.527c-.35-.25-.806-.272-1.203-.107-.397.165-.71.505-.781.929l-.149.894c-.09.542-.56.94-1.11.94h-1.094c-.55 0-1.019-.398-1.11-.94l-.148-.894c-.071-.424-.384-.764-.781-.93-.398-.164-.854-.142-1.204.108l-.738.527c-.447.32-1.06.269-1.45-.12l-.773-.774a1.125 1.125 0 0 1-.12-1.45l.527-.737c.25-.35.273-.806.108-1.204-.165-.397-.506-.71-.93-.78l-.894-.15c-.542-.09-.94-.56-.94-1.109v-1.094c0-.55.398-1.02.94-1.11l.894-.149c.424-.07.765-.383.93-.78.165-.398.143-.854-.108-1.204l-.526-.738a1.125 1.125 0 0 1 .12-1.45l.773-.773a1.125 1.125 0 0 1 1.45-.12l.737.527c.35.25.807.272 1.204.107.397-.165.71-.505.78-.929l.15-.894Z" /><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" /></svg>
                        Configuración
                    </a>
                </div>
            </nav>

            <!-- Columna Derecha: Contenido de Sub-página -->
            <div class="flex-1 flex flex-col h-screen">
                <header class="bg-white dark:bg-slate-900 shadow-md p-4 flex justify-between items-center">
                    <button class="back-btn btn-fill" data-target-dashboard="docente-dashboard" style="color: #15803d; border-color: #15803d;">
                        &larr; Volver
                    </button>
                    <h1 id="subpage-title-docente" class="text-2xl font-bold text-green-700 dark:text-green-300"></h1>
                    <div class="flex items-center space-x-2">
                        <!-- Botón de Tema (NUEVO) -->
                        <button class="theme-toggle-btn p-2 rounded-full text-slate-500 hover:text-green-600 hover:bg-green-100 dark:text-slate-400 dark:hover:text-yellow-400 dark:hover:bg-slate-800 transition-colors" title="Cambiar tema">
                            <!-- Icono de Sol -->
                            <svg class="theme-icon-sun w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 3v2.25m6.364.386-1.591 1.591M21 12h-2.25m-.386 6.364-1.591-1.591M12 18.75V21m-6.364-.386 1.591-1.591M3 12H.75m.386-6.364 1.591 1.591" />
                            </svg>
                            <!-- Icono de Luna -->
                            <svg class="theme-icon-moon w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25c0 5.385 4.365 9.75 9.75 9.75 2.671 0 5.1-1.057 6.9-2.798Z" />
                            </svg>
                        </button>
                        <!-- Botón de Cerrar Sesión (Icono) -->
                        <button class="logout-btn p-2 rounded-full text-slate-500 hover:text-red-600 hover:bg-red-100 dark:text-slate-400 dark:hover:bg-red-200 dark:hover:text-red-700 transition-colors" title="Cerrar Sesión">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15M15.75 9l-3.375 3.375M15.75 9h6.75m-6.75 0-3.375-3.375" />
                            </svg>                      
                        </button>
                    </div>
                </header>
                <div id="subpage-content-docente" class="flex-1 p-8 overflow-y-auto">
                    <!-- El contenido se inyectará aquí -->
                    <p>Cargando contenido...</p>
                </div>
            </div>
        </section>

        <!-- Sub-página Admin -->
        <section id="admin-subpage" class="h-screen w-full flex flex-row hidden bg-slate-50 dark:bg-slate-800">
            <!-- Columna Izquierda: Navegación (REPETIDA con cambios de perfil e iconos) -->
            <nav class="w-72 bg-white dark:bg-slate-900 shadow-lg p-6 flex flex-col flex-shrink-0 overflow-y-auto">
                <div class="flex flex-col items-center text-center mb-8 space-y-4">
                    <div class="p-2 bg-slate-100 dark:bg-slate-800 rounded-full mb-2">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-10 h-10 text-slate-600 dark:text-slate-400">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 21v-8.25M15.75 21v-8.25M8.25 21v-8.25M3 9l9-6 9 6m-1.5 12V10.332A48.36 48.36 0 0012 9.75c-2.551 0-5.056.2-7.5.582V21M3 21h18M12 6.75h.008v.008H12V6.75z" />
                        </svg>
                    </div>
                    <img src="https://source.unsplash.com/random/100x100?portrait,manager" alt="Foto de Perfil" class="w-20 h-20 rounded-full ring-4 ring-slate-200 dark:ring-slate-800">
                    <div>
                        <h2 class="text-lg font-bold text-slate-800 dark:text-slate-200">Admin. Principal</h2>
                        <p class="text-sm font-medium text-slate-500 dark:text-slate-400">Oficina de Grados y Títulos</p>
                         <span class="inline-block mt-1 px-2 py-1 text-xs font-semibold text-slate-800 bg-slate-200 rounded-full dark:bg-slate-700 dark:text-slate-300">Administrador</span>
                    </div>
                </div>
                <div class="flex-1 space-y-1">
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mb-2 px-2">Gestión</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Gestión de Tesis (CRUD)" data-template-id="template-admin-gestion-tesis">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m0 12.75h7.5m-7.5 3H12M10.5 2.25H5.625c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z" /></svg>
                        Gestión de Tesis
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Gestión de Usuarios (CRUD)" data-template-id="template-admin-gestion-usuarios">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z" /></svg>
                        Gestión de Usuarios
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Asignar Revisiones" data-template-id="template-admin-asignar">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M7.5 21 3 16.5m0 0L7.5 12M3 16.5h13.5m0-13.5L21 7.5m0 0L16.5 12M21 7.5H7.5" /></svg>
                        Asignar Revisiones
                    </a>
                    <h3 class="text-xs font-semibold text-slate-400 dark:text-slate-500 uppercase mt-6 mb-2 px-2">Sistema</h3>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Estadísticas" data-template-id="template-admin-estadisticas">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M3.75 3v11.25A2.25 2.25 0 0 0 6 16.5h2.25M3.75 21h16.5A2.25 2.25 0 0 0 22.5 18.75v-11.25A2.25 2.25 0 0 0 20.25 5.25h-2.25m-9 13.5v-6m0 0L6.75 12M12.75 18.75v-6m0 0 2.25-2.25M12.75 18.75v-6m0 0L16.5 12M3.75 21v-9m16.5 0v9M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" /></svg>
                        Estadísticas
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Reportes" data-template-id="template-admin-reportes">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m6.75 12-3-3m0 0-3 3m3-3v6m-1.5-15H5.625c-.621 0-1.125.504-1.125 1.125v13.5c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z" /></svg>
                        Reportes
                    </a>
                    <a class="dashboard-card sidebar-link flex items-center py-2 px-3 rounded-lg text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800 transition-colors" data-target-page="admin-subpage" data-title="Configuración" data-template-id="template-admin-configuracion">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-3 opacity-75"><path stroke-linecap="round" stroke-linejoin="round" d="M10.343 3.94c.09-.542.56-.94 1.11-.94h1.093c.55 0 1.02.398 1.11.94l.149.894c.07.424.384.764.78.93.398.164.855.142 1.205-.108l.737-.527a1.125 1.125 0 0 1 1.45.12l.773.774c.39.389.44 1.002.12 1.45l-.527.737c-.25.35-.272.806-.107 1.204.165.397.505.71.93.78l.893.15c.543.09.94.56.94 1.109v1.094c0 .55-.397 1.02-.94 1.11l-.894.149c-.424.07-.764.383-.929.78-.165.398-.143.854.107 1.204l.527.738c.32.447.269 1.06-.12 1.45l-.774.773a1.125 1.125 0 0 1-1.449.12l-.738-.527c-.35-.25-.806-.272-1.203-.107-.397.165-.71.505-.781.929l-.149.894c-.09.542-.56.94-1.11.94h-1.094c-.55 0-1.019-.398-1.11-.94l-.148-.894c-.071-.424-.384-.764-.781-.93-.398-.164-.854-.142-1.204.108l-.738.527c-.447.32-1.06.269-1.45-.12l-.773-.774a1.125 1.125 0 0 1-.12-1.45l.527-.737c.25-.35.273-.806.108-1.204-.165-.397-.506-.71-.93-.78l-.894-.15c-.542-.09-.94-.56-.94-1.109v-1.094c0-.55.398-1.02.94-1.11l.894-.149c.424-.07.765-.383.93-.78.165-.398.143-.854-.108-1.204l-.526-.738a1.125 1.125 0 0 1 .12-1.45l.773-.773a1.125 1.125 0 0 1 1.45-.12l.737.527c.35.25.807.272 1.204.107.397-.165.71-.505.78-.929l.15-.894Z" /><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" /></svg>
                        Configuración
                    </a>
                </div>
            </nav>

            <!-- Columna Derecha: Contenido de Sub-página -->
            <div class="flex-1 flex flex-col h-screen">
                <header class="bg-white dark:bg-slate-900 shadow-md p-4 flex justify-between items-center">
                    <button class="back-btn btn-fill" data-target-dashboard="admin-dashboard" style="color: #334155; border-color: #334155;">
                        &larr; Volver
                    </button>
                    <h1 id="subpage-title-admin" class="text-2xl font-bold text-slate-700 dark:text-slate-300"></h1>
                    <div class="flex items-center space-x-2">
                        <!-- Botón de Tema (NUEVO) -->
                        <button class="theme-toggle-btn p-2 rounded-full text-slate-500 hover:text-slate-800 hover:bg-slate-100 dark:text-slate-400 dark:hover:text-yellow-400 dark:hover:bg-slate-800 transition-colors" title="Cambiar tema">
                            <!-- Icono de Sol -->
                            <svg class="theme-icon-sun w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 3v2.25m6.364.386-1.591 1.591M21 12h-2.25m-.386 6.364-1.591-1.591M12 18.75V21m-6.364-.386 1.591-1.591M3 12H.75m.386-6.364 1.591 1.591" />
                            </svg>
                            <!-- Icono de Luna -->
                            <svg class="theme-icon-moon w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25c0 5.385 4.365 9.75 9.75 9.75 2.671 0 5.1-1.057 6.9-2.798Z" />
                            </svg>
                        </button>
                        <!-- Botón de Cerrar Sesión (Icono) -->
                        <button class="logout-btn p-2 rounded-full text-slate-500 hover:text-red-600 hover:bg-red-100 dark:text-slate-400 dark:hover:bg-red-200 dark:hover:text-red-700 transition-colors" title="Cerrar Sesión">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15M15.75 9l-3.375 3.375M15.75 9h6.75m-6.75 0-3.375-3.375" />
                            </svg>                      
                        </button>
                    </div>
                </header>
                <div id="subpage-content-admin" class="flex-1 p-8 overflow-y-auto">
                    <!-- El contenido se inyectará aquí -->
                    <p>Cargando contenido...</p>
                </div>
            </div>
        </section>

    </main>

    <!-- ============================================= -->
    <!-- PLANTILLAS DE CONTENIDO (NUEVO)               -->
    <!-- ============================================= -->
    <div id="content-templates" class="hidden">

        <!-- --- Plantillas de Estudiante --- -->

        <!-- Estado de Tesis -->
        <template id="template-est-estado">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Progreso de tu Tesis</h3>
                <p class="text-slate-600 dark:text-slate-400 mb-8">Tu tesis "Análisis de Algoritmos de IA para Mercados Emergentes" está actualmente <span class="font-bold text-blue-600 dark:text-blue-400">En Revisión</span>.</p>
                <!-- Stepper de Progreso -->
                <ol class="relative border-l border-gray-200 dark:border-slate-700">                  
                    <li class="mb-10 ml-6">            
                        <span class="absolute flex items-center justify-center w-6 h-6 bg-blue-600 dark:bg-blue-500 rounded-full -left-3 ring-8 ring-white dark:ring-slate-800">
                            <svg class="w-3 h-3 text-white" fill="currentColor" viewBox="0 0 20 20"><path d="M20 6L9 17l-5-5"></path></svg>
                        </span>
                        <h3 class="flex items-center mb-1 text-lg font-semibold text-gray-900 dark:text-white">Tesis Registrada</h3>
                        <time class="block mb-2 text-sm font-normal leading-none text-gray-400 dark:text-slate-500">1 de Septiembre, 2024</time>
                        <p class="text-base font-normal text-gray-500 dark:text-slate-400">El administrador subió tu documento y fue asignado.</p>
                    </li>
                    <li class="mb-10 ml-6">
                        <span class="absolute flex items-center justify-center w-6 h-6 bg-blue-600 dark:bg-blue-500 rounded-full -left-3 ring-8 ring-white dark:ring-slate-800">
                            <svg class="w-3 h-3 text-white" fill="currentColor" viewBox="0 0 20 20"><path d="M20 6L9 17l-5-5"></path></svg>
                        </span>
                        <h3 class="mb-1 text-lg font-semibold text-gray-900 dark:text-white">En Revisión</h3>
                        <time class="block mb-2 text-sm font-normal leading-none text-gray-400 dark:text-slate-500">3 de Septiembre, 2024</time>
                        <p class="text-gray-500 dark:text-slate-400">Tu docente (Dr. Ramos) ha comenzado la revisión. Recibió tus comentarios el 5 de Septiembre.</p>
                    </li>
                    <li class="mb-10 ml-6">
                        <span class="absolute flex items-center justify-center w-6 h-6 bg-gray-200 dark:bg-slate-700 rounded-full -left-3 ring-8 ring-white dark:ring-slate-800">
                            <svg class="w-3 h-3 text-gray-600 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20"><path d="M10 2a8 8 0 100 16 8 8 0 000-16zM5 10a1 1 0 011-1h6a1 1 0 110 2H6a1 1 0 01-1-1z"></path></svg>
                        </span>
                        <h3 class="mb-1 text-lg font-semibold text-gray-900 dark:text-white">Correcciones Pendientes</h3>
                        <time class="block mb-2 text-sm font-normal leading-none text-gray-400 dark:text-slate-500">Próximamente</time>
                    </li>
                    <li class="ml-6">
                        <span class="absolute flex items-center justify-center w-6 h-6 bg-gray-200 dark:bg-slate-700 rounded-full -left-3 ring-8 ring-white dark:ring-slate-800">
                            <svg class="w-3 h-3 text-gray-600 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20"><path d="M10 2a8 8 0 100 16 8 8 0 000-16zM5 10a1 1 0 011-1h6a1 1 0 110 2H6a1 1 0 01-1-1z"></path></svg>
                        </span>
                        <h3 class="mb-1 text-lg font-semibold text-gray-900 dark:text-white">Aprobado / Rechazado</h3>
                    </li>
                </ol>
            </div>
        </template>

        <!-- Mi Tesis -->
        <template id="template-est-mi-tesis">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Visualizador de Documento</h3>
                <div class="border border-slate-300 dark:border-slate-700 rounded-lg p-4">
                    <div class="flex justify-between items-center mb-4">
                        <span class="font-medium text-slate-700 dark:text-slate-300">Tesis_Final_v3.pdf</span>
                        <a href="#" class="btn-fill-solid text-sm" style="padding: 0.5rem 1rem;">Descargar</a>
                    </div>
                    <!-- Simulación de un visor de PDF -->
                    <div class="w-full h-[600px] bg-slate-200 dark:bg-slate-700 rounded-md flex items-center justify-center">
                        <p class="text-slate-500 dark:text-slate-400">Previsualización de documento no disponible (Simulación).</p>
                    </div>
                </div>
            </div>
        </template>

        <!-- Comentarios del Docente -->
        <template id="template-est-comentarios">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Comentarios y Revisiones</h3>
                <div class="space-y-6">
                    <!-- Comentario 1 -->
                    <article class="p-4 rounded-lg bg-slate-50 dark:bg-slate-700 border dark:border-slate-600">
                        <div class="flex justify-between items-center mb-2">
                            <span class="font-semibold text-green-700 dark:text-green-400">Dr. Ramos</span>
                            <time class="text-sm text-slate-500 dark:text-slate-400">5 de Septiembre, 2024</time>
                        </div>
                        <p class="text-slate-700 dark:text-slate-300">Buen trabajo en la introducción. Sin embargo, la sección de metodología necesita ser más detallada. Revisa la página 12, hay un error de cita.</p>
                    </article>
                    <!-- Comentario 2 -->
                    <article class="p-4 rounded-lg bg-slate-50 dark:bg-slate-700 border dark:border-slate-600">
                        <div class="flex justify-between items-center mb-2">
                            <span class="font-semibold text-green-700 dark:text-green-400">Dr. Ramos</span>
                            <time class="text-sm text-slate-500 dark:text-slate-400">3 de Septiembre, 2024</time>
                        </div>
                        <p class="text-slate-700 dark:text-slate-300">He recibido el documento. Comenzaré la revisión esta semana.</p>
                    </article>
                </div>
            </div>
        </template>

        <!-- Calendario -->
        <template id="template-est-calendario">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Calendario Académico</h3>
                <ul class="divide-y divide-slate-200 dark:divide-slate-700">
                    <li class="py-4">
                        <strong class="text-blue-600 dark:text-blue-400">15 de Octubre, 2024:</strong> Límite de entrega de correcciones.
                    </li>
                    <li class="py-4">
                        <strong class="text-blue-600 dark:text-blue-400">1 de Noviembre, 2024:</strong> Fecha de aprobación final.
                    </li>
                    <li class="py-4">
                        <strong class="text-red-600 dark:text-red-400">15 de Noviembre, 2024:</strong> Fecha de sustentación de Tesis.
                    </li>
                </ul>
            </div>
        </template>

        <!-- Mensajes -->
        <template id="template-est-mensajes">
            <div class="bg-white dark:bg-slate-800 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s; overflow: hidden;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6 p-8 pb-0">Mensajería</h3>
                <!-- Simulación de Chat -->
                <div class="flex flex-col h-[500px]">
                    <div class="flex-1 p-8 space-y-4 overflow-y-auto bg-slate-50 dark:bg-slate-900">
                        <!-- Mensaje Docente -->
                        <div class="flex">
                            <div class="p-3 rounded-lg bg-white dark:bg-slate-700 border border-slate-200 dark:border-slate-600 max-w-xs">
                                <p class="text-sm text-slate-700 dark:text-slate-300">Hola, por favor revisa mis comentarios sobre la metodología.</p>
                                <time class="text-xs text-slate-400 dark:text-slate-500 mt-1 block">Dr. Ramos - Ayer, 3:30 PM</time>
                            </div>
                        </div>
                        <!-- Mensaje Alumno -->
                        <div class="flex justify-end">
                            <div class="p-3 rounded-lg bg-blue-600 dark:bg-blue-500 text-white max-w-xs">
                                <p class="text-sm">Entendido, doctor. Trabajaré en ello este fin de semana. ¡Gracias!</p>
                                <time class="text-xs text-blue-100 dark:text-blue-200 mt-1 block">Tú - Ayer, 4:15 PM</time>
                            </div>
                        </div>
                    </div>
                    <div class="p-4 bg-white dark:bg-slate-800 border-t dark:border-slate-700">
                        <input type="text" placeholder="Escribe tu mensaje..." class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500 transition">
                    </div>
                </div>
            </div>
        </template>

        <!-- Configuración Estudiante -->
        <template id="template-est-configuracion">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Configuración de Perfil</h3>
                <form class="space-y-6 max-w-lg">
                    <div>
                        <label for="est-nombre" class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Nombre Completo</label>
                        <input type="text" id="est-nombre" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" value="Nombre Alumno">
                    </div>
                    <div>
                        <label for="est-email" class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Correo Electrónico</label>
                        <input type="email" id="est-email" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" value="alumno@universidad.edu">
                    </div>
                    <div>
                        <label for="est-pass" class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Nueva Contraseña</label>
                        <input type="password" id="est-pass" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" placeholder="••••••••">
                    </div>
                    <button type="submit" class="btn-fill-solid">Guardar Cambios</button>
                </form>
            </div>
        </template>

        <!-- --- Plantillas de Docente --- -->

        <!-- Tesis Asignadas -->
        <template id="template-doc-asignadas">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Tesis Asignadas</h3>
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-slate-200 dark:divide-slate-700">
                        <thead class="bg-slate-50 dark:bg-slate-700">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Estudiante</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Título de Tesis</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Fecha Asignada</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Estado</th>
                                <th class="px-6 py-3 text-right text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Acción</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white dark:bg-slate-800 divide-y divide-slate-200 dark:divide-slate-700">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-900 dark:text-white">Nombre Alumno</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">Análisis de Algoritmos de IA...</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">1 Sep, 2024</td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200">En Revisión</span>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                                    <a href="#" class="text-green-600 hover:text-green-900 dark:text-green-400 dark:hover:text-green-200">Revisar</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-900 dark:text-white">Ana García</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">Impacto del 5G en Zonas Rurales</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">2 Sep, 2024</td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200">Pendiente</span>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                                    <a href="#" class="text-green-600 hover:text-green-900 dark:text-green-400 dark:hover:text-green-200">Revisar</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>

        <!-- Calificar Tesis -->
        <template id="template-doc-calificar">
            <div class="animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Calificar Tesis: "Impacto del 5G en Zonas Rurales"</h3>
                <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
                    <div class="lg:col-span-2 bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md">
                        <h4 class="text-lg font-semibold mb-4 dark:text-white">Documento</h4>
                        <div class="w-full h-[600px] bg-slate-200 dark:bg-slate-700 rounded-md flex items-center justify-center">
                            <p class="text-slate-500 dark:text-slate-400">Previsualización de Tesis_AnaGarcia.pdf</p>
                        </div>
                    </div>
                    <div class="lg:col-span-1 bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md">
                        <h4 class="text-lg font-semibold mb-4 dark:text-white">Panel de Calificación</h4>
                        <form class="space-y-6">
                            <div>
                                <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Estado</label>
                                <select class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white">
                                    <option>Aprobado</option>
                                    <option>Rechazado</option>
                                    <option>Requiere Correcciones</option>
                                </select>
                            </div>
                            <div>
                                <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Comentarios (Visibles para el alumno)</label>
                                <textarea rows="8" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" placeholder="Escribe tus correcciones aquí..."></textarea>
                            </div>
                            <button type="submit" class="btn-fill-solid w-full justify-center" style="background-color: #16a34a; border-color: #16a34a;">Enviar Calificación</button>
                        </form>
                    </div>
                </div>
            </div>
        </template>

        <!-- Historial Docente -->
        <template id="template-doc-historial">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Historial de Revisiones</h3>
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-slate-200 dark:divide-slate-700">
                        <thead class="bg-slate-50 dark:bg-slate-700">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Estudiante</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Título de Tesis</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Fecha Revisión</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Estado Final</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white dark:bg-slate-800 divide-y divide-slate-200 dark:divide-slate-700">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-900 dark:text-white">Carlos Perez</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">Redes Neuronales en Finanzas</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">15 Ago, 2024</td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200">Aprobado</span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>
        
        <!-- Configuración Docente -->
        <template id="template-doc-configuracion">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Configuración de Perfil</h3>
                <form class="space-y-6 max-w-lg">
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Nombre Completo</label>
                        <input type="text" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" value="Nombre Docente">
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Correo Electrónico</label>
                        <input type="email" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" value="docente@universidad.edu">
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Áreas de Especialidad</label>
                        <input type="text" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" value="IA, Machine Learning, Big Data">
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Nueva Contraseña</label>
                        <input type="password" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" placeholder="••••••••">
                    </div>
                    <button type="submit" class="btn-fill-solid" style="background-color: #16a34a; border-color: #16a34a;">Guardar Cambios</button>
                </form>
            </div>
        </template>


        <!-- --- Plantillas de Administrador --- -->

        <!-- Gestión de Tesis (CRUD) -->
        <template id="template-admin-gestion-tesis">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <div class="flex justify-between items-center mb-6">
                    <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100">Gestión de Tesis</h3>
                    <button class="btn-fill-solid" style="background-color: #334155; border-color: #334155;">+ Subir Tesis</button>
                </div>
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-slate-200 dark:divide-slate-700">
                        <thead class="bg-slate-50 dark:bg-slate-700">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Título</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Autor (Alumno)</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Revisor (Docente)</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Estado</th>
                                <th class="px-6 py-3 text-right text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white dark:bg-slate-800 divide-y divide-slate-200 dark:divide-slate-700">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-900 dark:text-white">Análisis de Algoritmos de IA...</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">Nombre Alumno</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">Dr. Ramos</td>
                                <td class="px-6 py-4 whitespace-nowrap"><span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200">En Revisión</span></td>
                                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
                                    <a href="#" class="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-200">Editar</a>
                                    <a href="#" class="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-200">Eliminar</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-900 dark:text-white">Impacto del 5G...</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">Ana García</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">Dr. Ramos</td>
                                <td class="px-6 py-4 whitespace-nowrap"><span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200">Pendiente</span></td>
                                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
                                    <a href="#" class="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-200">Editar</a>
                                    <a href="#" class="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-200">Eliminar</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>
        
        <!-- Gestión de Usuarios (CRUD) -->
        <template id="template-admin-gestion-usuarios">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <div class="flex justify-between items-center mb-6">
                    <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100">Gestión de Usuarios</h3>
                    <button class="btn-fill-solid" style="background-color: #334155; border-color: #334155;">+ Nuevo Usuario</button>
                </div>
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-slate-200 dark:divide-slate-700">
                        <thead class="bg-slate-50 dark:bg-slate-700">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Nombre</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Email</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Rol</th>
                                <th class="px-6 py-3 text-right text-xs font-medium text-slate-500 dark:text-slate-400 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white dark:bg-slate-800 divide-y divide-slate-200 dark:divide-slate-700">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-900 dark:text-white">Nombre Alumno</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">alumno@universidad.edu</td>
                                <td class="px-6 py-4 whitespace-nowrap"><span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200">Estudiante</span></td>
                                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
                                    <a href="#" class="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-200">Editar</a>
                                    <a href="#" class="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-200">Eliminar</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-900 dark:text-white">Dr. Ramos</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-500 dark:text-slate-400">docente@universidad.edu</td>
                                <td class="px-6 py-4 whitespace-nowrap"><span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200">Docente</span></td>
                                <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
                                    <a href="#" class="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-200">Editar</a>
                                    <a href="#" class="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-200">Eliminar</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>
        
        <!-- Asignar Revisiones -->
        <template id="template-admin-asignar">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Asignar Revisión de Tesis</h3>
                <form class="space-y-6 max-w-lg">
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Seleccionar Tesis (por Alumno)</label>
                        <select class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white">
                            <option>Ana García - Impacto del 5G...</option>
                            <option>Luis Marto - Ciberseguridad en IoT</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Asignar a Docente (Revisor)</label>
                        <select class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white">
                            <option>Dr. Ramos</option>
                            <option>Dra. Herrera</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-fill-solid" style="background-color: #334155; border-color: #334155;">Asignar</button>
                </form>
            </div>
        </template>
        
        <!-- Estadísticas -->
        <template id="template-admin-estadisticas">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Estadísticas del Sistema</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <!-- Card 1 -->
                    <div class="bg-slate-50 dark:bg-slate-700 p-6 rounded-lg border dark:border-slate-600">
                        <h4 class="text-lg font-semibold text-slate-700 dark:text-slate-200 mb-3">Tesis por Estado</h4>
                        <div class="space-y-3">
                            <div class="flex justify-between"><span class="text-sm dark:text-slate-300">Aprobadas</span><span class="font-bold dark:text-white">12</span></div>
                            <div class="w-full bg-slate-200 dark:bg-slate-600 rounded-full h-2.5"><div class="bg-green-600 h-2.5 rounded-full" style="width: 45%"></div></div>
                            
                            <div class="flex justify-between"><span class="text-sm dark:text-slate-300">En Revisión</span><span class="font-bold dark:text-white">8</span></div>
                            <div class="w-full bg-slate-200 dark:bg-slate-600 rounded-full h-2.5"><div class="bg-blue-600 h-2.5 rounded-full" style="width: 30%"></div></div>
                            
                            <div class="flex justify-between"><span class="text-sm dark:text-slate-300">Pendientes</span><span class="font-bold dark:text-white">7</span></div>
                            <div class="w-full bg-slate-200 dark:bg-slate-600 rounded-full h-2.5"><div class="bg-yellow-500 h-2.5 rounded-full" style="width: 25%"></div></div>
                        </div>
                    </div>
                    <!-- Card 2 -->
                    <div class="bg-slate-50 dark:bg-slate-700 p-6 rounded-lg border dark:border-slate-600">
                        <h4 class="text-lg font-semibold text-slate-700 dark:text-slate-200 mb-3">Usuarios Activos</h4>
                        <div class="text-4xl font-bold text-slate-800 dark:text-white">128</div>
                        <p class="text-slate-600 dark:text-slate-400">Usuarios totales</p>
                        <div class="mt-4">
                            <span class="text-sm font-medium text-blue-700 dark:text-blue-400">85 Estudiantes</span> | 
                            <span class="text-sm font-medium text-green-700 dark:text-green-400">40 Docentes</span> | 
                            <span class="text-sm font-medium text-slate-700 dark:text-slate-300">3 Admins</span>
                        </div>
                    </div>
                </div>
            </div>
        </template>
        
        <!-- Reportes -->
        <template id="template-admin-reportes">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Generar Reportes</h3>
                <form class="space-y-6 max-w-lg">
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Tipo de Reporte</label>
                        <select class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white">
                            <option>Listado de Tesis Aprobadas</option>
                            <option>Carga de trabajo por Docente</option>
                            <option>Alumnos sin Tesis asignada</option>
                        </select>
                    </div>
                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Fecha de Inicio</label>
                            <input type="date" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white">
                        </div>
                        <div>
                            <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Fecha de Fin</label>
                            <input type="date" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white">
                        </div>
                    </div>
                    <button type="submit" class="btn-fill-solid" style="background-color: #334155; border-color: #334155;">Exportar PDF</button>
                </form>
            </div>
        </template>
        
        <!-- Configuración Admin -->
        <template id="template-admin-configuracion">
            <div class="bg-white dark:bg-slate-800 p-8 rounded-lg shadow-md animate-fade-in" style="animation-delay: 0.1s;">
                <h3 class="text-2xl font-semibold text-slate-800 dark:text-slate-100 mb-6">Configuración del Sistema</h3>
                <form class="space-y-6 max-w-lg">
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Nombre de la Institución</label>
                        <input type="text" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" value="Mi Universidad">
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Logo de la Institución</label>
                        <input type="file" class="w-full text-sm text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-slate-100 file:text-slate-700 hover:file:bg-slate-200 dark:text-slate-400 file:dark:bg-slate-700 file:dark:text-slate-200 dark:hover:file:bg-slate-600"/>
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Periodo Académico Actual</label>
                        <input type="text" class="w-full px-4 py-3 rounded-lg border border-slate-300 dark:bg-slate-700 dark:border-slate-600 dark:text-white" value="2024-II">
                    </div>
                    <button type="submit" class="btn-fill-solid" style="background-color: #334155; border-color: #334155;">Guardar Cambios</button>
                </form>
            </div>
        </template>

    </div>


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
            const dashboardCards = document.querySelectorAll('.dashboard-card'); // Esto ahora incluye los enlaces de la barra lateral
            const backBtns = document.querySelectorAll('.back-btn');

            // --- LÓGICA DE MODO OSCURO ---
            const themeToggleBtns = document.querySelectorAll('.theme-toggle-btn');
            console.log('Botones de tema encontrados:', themeToggleBtns.length);

            // Función para aplicar el tema
            function applyTheme(theme) {
                console.log('Aplicando tema:', theme);
                if (theme === 'dark') {
                    document.documentElement.classList.add('dark');
                } else {
                    document.documentElement.classList.remove('dark');
                }
            }

            // Cargar tema al inicio
            let currentTheme = localStorage.getItem('theme');
            if (!currentTheme) {
                // currentTheme = window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
                // console.log('No hay tema guardado, detectando tema del sistema:', currentTheme);
                
                // ***** INICIO DE LA MODIFICACIÓN *****
                // Por defecto, el tema será 'dark'
                currentTheme = 'dark';
                console.log('No hay tema guardado, estableciendo "dark" por defecto.');
                // ***** FIN DE LA MODIFICACIÓN *****

            } else {
                console.log('Cargando tema guardado:', currentTheme);
            }
            applyTheme(currentTheme);
            
            // Listener para todos los botones de tema
            themeToggleBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    console.log('¡Click en botón de tema!');
                    
                    // --- INICIO DE LA CORRECCIÓN LÓGICA (Tu idea del switch) ---
                    let nuevoTema;
                    
                    // 1. Revisar el estado actual (valor 1)
                    if (document.documentElement.classList.contains('dark')) {
                        // Si está oscuro, el nuevo tema es claro (valor 2)
                        nuevoTema = 'light';
                    } else {
                        // Si está claro (valor 1), el nuevo tema es oscuro (valor 2)
                        nuevoTema = 'dark';
                    }

                    console.log('El nuevo tema será:', nuevoTema);
                    localStorage.setItem('theme', nuevoTema);
                    applyTheme(nuevoTema);
                    // --- FIN DE LA CORRECCIÓN LÓGICA ---
                });
            });
            // --- FIN DE LÓGICA DE MODO OSCURO ---


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

            // 2. Click en Botones de Login (Provisionales)
            loginBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    const targetId = btn.dataset.target;
                    const targetDashboard = document.getElementById(targetId);
                    if (targetDashboard) {
                        loginScreen.classList.add('animate-fade-out');
                        loginScreen.addEventListener('animationend', () => {
                            navigateTo(targetDashboard);
                            loginScreen.classList.remove('animate-fade-out');
                        }, { once: true });
                    }
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
                            
                            // Restablecer visibilidad de contenido en subpáginas (por si acaso)
                            document.querySelectorAll('div[id^="subpage-content-"]').forEach(el => {
                                el.parentElement.closest('section').classList.add('hidden');
                            });
                            
                        }, { once: true });
                    }
                });
            });

            // 4. Click en Tarjetas del Panel (o enlaces de la barra lateral)
            dashboardCards.forEach(card => {
                card.addEventListener('click', (e) => {
                    e.preventDefault(); // Prevenir si es un <a>
                    const currentDashboard = card.closest('section[id$="-dashboard"]');
                    
                    // Si estamos en una subpágina, currentDashboard será null, así que buscamos la sección principal
                    let mainDashboard;
                    if (currentDashboard) {
                        mainDashboard = currentDashboard;
                    } else {
                        // Estamos en una sub-página, encontramos el dashboard principal asociado
                        const subpageSection = card.closest('section[id$="-subpage"]');
                        if (subpageSection.id.includes('estudiante')) mainDashboard = studentDashboard;
                        else if (subpageSection.id.includes('docente')) mainDashboard = teacherDashboard;
                        else if (subpageSection.id.includes('admin')) mainDashboard = adminDashboard;
                    }

                    // --- INICIO DE LA CORRECCIÓN ---
                    // Definir targetPage y cargar contenido de la plantilla
                    const targetPageId = card.dataset.targetPage;
                    const targetPage = document.getElementById(targetPageId);
                    const templateId = card.dataset.templateId;
                    const template = document.getElementById(templateId);

                    if (targetPage && template) {
                        const title = card.dataset.title;
                        const titleEl = targetPage.querySelector('h1[id^="subpage-title-"]');
                        const contentEl = targetPage.querySelector('div[id^="subpage-content-"]');
                        
                        if (titleEl) titleEl.textContent = title;
                        if (contentEl) {
                            contentEl.innerHTML = ''; // Limpiar contenido anterior
                            contentEl.appendChild(template.content.cloneNode(true));
                        }
                    } else if (targetPage) {
                        // Fallback si no hay plantilla (aunque en nuestro caso siempre hay)
                        const title = card.dataset.title;
                        const titleEl = targetPage.querySelector('h1[id^="subpage-title-"]');
                        const contentEl = targetPage.querySelector('div[id^="subpage-content-"]');
                        if (titleEl) titleEl.textContent = title;
                        if (contentEl) contentEl.innerHTML = '<p>Contenido no encontrado.</p>';
                    }
                    // --- FIN DE LA CORRECCIÓN ---


                    if (targetPage) {
                        // Ocultar el contenido principal del dashboard (los widgets)
                        const mainContent = mainDashboard.querySelector('.flex-1.p-8.overflow-y-auto');
                        if (mainContent) mainContent.classList.add('hidden');

                        // Animar transición
                        // No animamos si la página de destino es la misma que la actual
                        // (ej. hacer clic en el mismo enlace de la barra lateral dos veces)
                        if (!targetPage.classList.contains('hidden')) {
                            // Ya estamos en la subpágina, solo actualiza el contenido (ya hecho arriba)
                            // Y nos aseguramos que el contenido principal (widgets) esté oculto
                            if (mainContent) mainContent.classList.add('hidden');
                            return; 
                        }
                        
                        // Ocultar la sección actual (ya sea dashboard o subpage)
                        const currentSection = card.closest('section');
                        currentSection.classList.add('animate-fade-out');
                        currentSection.addEventListener('animationend', () => {
                            navigateTo(targetPage); // Muestra la subpágina
                            currentSection.classList.remove('animate-fade-out');
                            currentSection.classList.add('hidden'); // Asegura que la anterior esté oculta
                        }, { once: true });
                    }
                });
            });

            // 5. Click en "Volver"
            backBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    const targetDashboardId = btn.dataset.targetDashboard;
                    const targetDashboard = document.getElementById(targetDashboardId);
                    const currentSubpage = btn.closest('section');

                    if (targetDashboard) {
                        // Mostrar el contenido principal del dashboard (widgets)
                        const mainContent = targetDashboard.querySelector('.flex-1.p-8.overflow-y-auto');
                        if (mainContent) mainContent.classList.remove('hidden');

                        currentSubpage.classList.add('animate-fade-out');
                        currentSubpage.addEventListener('animationend', () => {
                            navigateTo(targetDashboard); // Vuelve al dashboard
                            currentSubpage.classList.remove('animate-fade-out');
                        }, { once: true });
                    }
                });
            });

        });
    </script>

</body>
</html>
