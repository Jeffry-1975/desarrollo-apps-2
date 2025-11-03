<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel del Estudiante - Sistema de Gestión Académica</title>
    
    <!-- Cargar Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    
    <!-- Cargar Fuente (Inter) -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap" rel="stylesheet">
    
    <!-- Iconos de Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/views/dashboard/estudiante/estudiante.css">
</head>
<body class="bg-gray-50">
    <div class="flex h-screen overflow-hidden">
        <!-- Sidebar -->
        <aside class="sidebar" id="sidebar">
            <div class="p-4 border-b border-gray-200">
                <div class="flex items-center justify-between">
                    <h2 class="text-xl font-bold text-gray-800">Estudiante</h2>
                    <button id="mobileMenuButton" class="lg:hidden text-gray-500 hover:text-gray-700">
                        <i class="fas fa-times text-xl"></i>
                    </button>
                </div>
            </div>
            
            <!-- Menú de navegación -->
            <nav class="p-4">
                <ul>
                    <li class="mb-2">
                        <a href="#" class="flex items-center p-2 text-white bg-blue-600 rounded-lg">
                            <i class="fas fa-home mr-3"></i>
                            <span>Inicio</span>
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="flex items-center p-2 text-gray-700 hover:bg-gray-100 rounded-lg">
                            <i class="fas fa-book-open mr-3"></i>
                            <span>Mis Cursos</span>
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="flex items-center p-2 text-gray-700 hover:bg-gray-100 rounded-lg">
                            <i class="fas fa-tasks mr-3"></i>
                            <span>Tareas</span>
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="flex items-center p-2 text-gray-700 hover:bg-gray-100 rounded-lg">
                            <i class="fas fa-calendar-alt mr-3"></i>
                            <span>Calendario</span>
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="flex items-center p-2 text-gray-700 hover:bg-gray-100 rounded-lg">
                            <i class="fas fa-chart-line mr-3"></i>
                            <span>Rendimiento</span>
                        </a>
                    </li>
                    <li class="mt-8 pt-4 border-t border-gray-200">
                        <a href="#" class="flex items-center p-2 text-red-600 hover:bg-red-50 rounded-lg">
                            <i class="fas fa-sign-out-alt mr-3"></i>
                            <span>Cerrar Sesión</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </aside>
        
        <!-- Contenido principal -->
        <div class="main-content flex-1 flex flex-col overflow-hidden" id="mainContent">
            <!-- Barra superior -->
            <header class="bg-white shadow-sm z-10">
                <div class="flex items-center justify-between p-4">
                    <div class="flex items-center">
                        <button id="sidebarToggle" class="text-gray-500 hover:text-gray-700 mr-4">
                            <i class="fas fa-bars text-xl"></i>
                        </button>
                        <h1 class="text-xl font-semibold text-gray-800">Panel del Estudiante</h1>
                    </div>
                    
                    <div class="flex items-center space-x-4">
                        <div class="relative">
                            <button class="p-2 text-gray-500 hover:text-gray-700 focus:outline-none">
                                <i class="fas fa-bell text-xl"></i>
                                <span class="absolute top-0 right-0 h-2 w-2 rounded-full bg-red-500"></span>
                            </button>
                        </div>
                        
                        <div class="relative" x-data="{ open: false }">
                            <button @click="open = !open" class="flex items-center space-x-2 focus:outline-none">
                                <div class="h-10 w-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-semibold">
                                    <span>JE</span>
                                </div>
                                <span class="hidden md:inline text-gray-700">Juan Estudiante</span>
                                <i class="fas fa-chevron-down text-xs text-gray-500"></i>
                            </button>
                            
                            <div x-show="open" @click.away="open = false" class="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-50 hidden" :class="{'hidden': !open}">
                                <a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                    <i class="fas fa-user mr-2"></i> Perfil
                                </a>
                                <a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                    <i class="fas fa-cog mr-2"></i> Configuración
                                </a>
                                <div class="border-t border-gray-100 my-1"></div>
                                <a href="#" class="block px-4 py-2 text-sm text-red-600 hover:bg-red-50">
                                    <i class="fas fa-sign-out-alt mr-2"></i> Cerrar Sesión
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            
            <!-- Contenido -->
            <main class="flex-1 overflow-y-auto p-4 md:p-6 bg-gray-50">
                <div class="max-w-7xl mx-auto">
                    <!-- Banner de bienvenida -->
                    <div class="bg-gradient-to-r from-blue-600 to-blue-800 rounded-xl p-6 mb-6 text-white">
                        <div class="flex flex-col md:flex-row md:items-center md:justify-between">
                            <div>
                                <h2 class="text-2xl font-bold mb-2">¡Bienvenido de nuevo, Juan!</h2>
                                <p class="text-blue-100">Revisa tus próximas clases, tareas y calificaciones.</p>
                            </div>
                            <div class="mt-4 md:mt-0">
                                <a href="#" class="inline-block bg-white text-blue-600 px-4 py-2 rounded-lg font-medium hover:bg-blue-50 transition-colors">
                                    Ver Calendario
                                </a>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Resumen rápido -->
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
                        <div class="card p-6">
                            <div class="flex items-center">
                                <div class="p-3 rounded-full bg-blue-100 text-blue-600 mr-4">
                                    <i class="fas fa-book text-xl"></i>
                                </div>
                                <div>
                                    <p class="text-gray-500 text-sm">Cursos Activos</p>
                                    <h3 class="text-2xl font-bold text-gray-800">5</h3>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center">
                                <div class="p-3 rounded-full bg-green-100 text-green-600 mr-4">
                                    <i class="fas fa-check-circle text-xl"></i>
                                </div>
                                <div>
                                    <p class="text-gray-500 text-sm">Tareas Completadas</p>
                                    <h3 class="text-2xl font-bold text-gray-800">12</h3>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center">
                                <div class="p-3 rounded-full bg-yellow-100 text-yellow-600 mr-4">
                                    <i class="fas fa-tasks text-xl"></i>
                                </div>
                                <div>
                                    <p class="text-gray-500 text-sm">Tareas Pendientes</p>
                                    <h3 class="text-2xl font-bold text-gray-800">3</h3>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center">
                                <div class="p-3 rounded-full bg-purple-100 text-purple-600 mr-4">
                                    <i class="fas fa-chart-line text-xl"></i>
                                </div>
                                <div>
                                    <p class="text-gray-500 text-sm">Promedio General</p>
                                    <h3 class="text-2xl font-bold text-gray-800">8.7</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Gráfico de rendimiento -->
                    <div class="card p-6 mb-6">
                        <div class="flex justify-between items-center mb-6">
                            <h3 class="text-lg font-semibold text-gray-800">Mi Rendimiento</h3>
                            <div class="flex space-x-2">
                                <button class="px-3 py-1 text-sm bg-blue-100 text-blue-600 rounded-md">Mensual</button>
                                <button class="px-3 py-1 text-sm text-gray-500 hover:bg-gray-100 rounded-md">Trimestral</button>
                                <button class="px-3 py-1 text-sm text-gray-500 hover:bg-gray-100 rounded-md">Anual</button>
                            </div>
                        </div>
                        <div class="h-80">
                            <canvas id="performanceChart"></canvas>
                        </div>
                    </div>
                    
                    <!-- Próximas clases y actividades recientes -->
                    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-6">
                        <!-- Próximas clases -->
                        <div class="lg:col-span-2">
                            <div class="card p-6 h-full">
                                <div class="flex justify-between items-center mb-4">
                                    <h3 class="text-lg font-semibold text-gray-800">Próximas Clases</h3>
                                    <a href="#" class="text-sm text-blue-600 hover:underline">Ver horario completo</a>
                                </div>
                                
                                <div class="space-y-4">
                                    <div class="flex items-center p-3 border border-gray-100 rounded-lg hover:shadow-sm transition-shadow">
                                        <div class="p-2 bg-blue-100 text-blue-600 rounded-lg mr-4">
                                            <i class="fas fa-calculator text-xl"></i>
                                        </div>
                                        <div class="flex-1">
                                            <h4 class="font-medium text-gray-800">Matemáticas Avanzadas</h4>
                                            <p class="text-sm text-gray-500">08:00 - 09:30 AM • Aula 302</p>
                                        </div>
                                        <span class="text-sm text-gray-500">Hoy</span>
                                    </div>
                                    
                                    <div class="flex items-center p-3 border border-gray-100 rounded-lg hover:shadow-sm transition-shadow">
                                        <div class="p-2 bg-green-100 text-green-600 rounded-lg mr-4">
                                            <i class="fas fa-flask text-xl"></i>
                                        </div>
                                        <div class="flex-1">
                                            <h4 class="font-medium text-gray-800">Laboratorio de Química</h4>
                                            <p class="text-sm text-gray-500">10:00 - 12:00 PM • Laboratorio 4</p>
                                        </div>
                                        <span class="text-sm text-gray-500">Mañana</span>
                                    </div>
                                    
                                    <div class="flex items-center p-3 border border-gray-100 rounded-lg hover:shadow-sm transition-shadow">
                                        <div class="p-2 bg-yellow-100 text-yellow-600 rounded-lg mr-4">
                                            <i class="fas fa-book text-xl"></i>
                                        </div>
                                        <div class="flex-1">
                                            <h4 class="font-medium text-gray-800">Literatura Universal</h4>
                                            <p class="text-sm text-gray-500">02:00 - 03:30 PM • Aula 205</p>
                                        </div>
                                        <span class="text-sm text-gray-500">Mañana</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Actividades recientes -->
                        <div>
                            <div class="card p-6 h-full">
                                <div class="flex justify-between items-center mb-4">
                                    <h3 class="text-lg font-semibold text-gray-800">Actividad Reciente</h3>
                                    <a href="#" class="text-sm text-blue-600 hover:underline">Ver todo</a>
                                </div>
                                
                                <div class="space-y-4">
                                    <div class="flex items-start">
                                        <div class="p-2 bg-blue-100 text-blue-600 rounded-lg mr-3">
                                            <i class="fas fa-file-upload"></i>
                                        </div>
                                        <div>
                                            <p class="text-sm text-gray-800">Has subido la tarea de <span class="font-medium">Álgebra Lineal</span></p>
                                            <p class="text-xs text-gray-500">Hace 2 horas</p>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="p-2 bg-green-100 text-green-600 rounded-lg mr-3">
                                            <i class="fas fa-check-circle"></i>
                                        </div>
                                        <div>
                                            <p class="text-sm text-gray-800">Tarea de <span class="font-medium">Física</span> calificada: 18/20</p>
                                            <p class="text-xs text-gray-500">Ayer, 4:30 PM</p>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="p-2 bg-purple-100 text-purple-600 rounded-lg mr-3">
                                            <i class="fas fa-comment-alt"></i>
                                        </div>
                                        <div>
                                            <p class="text-sm text-gray-800">Nuevo comentario en <span class="font-medium">Historia del Arte</span></p>
                                            <p class="text-xs text-gray-500">Ayer, 1:15 PM</p>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="p-2 bg-yellow-100 text-yellow-600 rounded-lg mr-3">
                                            <i class="fas fa-exclamation-triangle"></i>
                                        </div>
                                        <div>
                                            <p class="text-sm text-gray-800">Próximo examen de <span class="font-medium">Matemáticas</span> en 2 días</p>
                                            <p class="text-xs text-gray-500">Lunes, 9:00 AM</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Tareas pendientes y próximos exámenes -->
                    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                        <!-- Tareas pendientes -->
                        <div class="card p-6">
                            <div class="flex justify-between items-center mb-4">
                                <h3 class="text-lg font-semibold text-gray-800">Tareas Pendientes</h3>
                                <a href="#" class="text-sm text-blue-600 hover:underline">Ver todas</a>
                            </div>
                            
                            <div class="space-y-4">
                                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-blue-100 text-blue-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-book"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Ensayo sobre Literatura</h4>
                                            <p class="text-sm text-gray-500">Literatura Universal</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-yellow-600">Mañana</span>
                                        <span class="text-xs text-gray-500">Entrega: 15:00</span>
                                    </div>
                                </div>
                                
                                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-green-100 text-green-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-flask"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Informe de Laboratorio</h4>
                                            <p class="text-sm text-gray-500">Química Orgánica</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-red-600">Vence en 2 días</span>
                                        <span class="text-xs text-gray-500">Entrega: 23:59</span>
                                    </div>
                                </div>
                                
                                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-purple-100 text-purple-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-laptop-code"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Proyecto de Programación</h4>
                                            <p class="text-sm text-gray-500">Algoritmos I</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-blue-600">Próxima semana</span>
                                        <span class="text-xs text-gray-500">Entrega: Lunes</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Próximos exámenes -->
                        <div class="card p-6">
                            <div class="flex justify-between items-center mb-4">
                                <h3 class="text-lg font-semibold text-gray-800">Próximos Exámenes</h3>
                                <a href="#" class="text-sm text-blue-600 hover:underline">Ver calendario</a>
                            </div>
                            
                            <div class="space-y-4">
                                <div class="flex items-center justify-between p-3 bg-red-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-red-100 text-red-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-calendar-alt"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Examen Parcial</h4>
                                            <p class="text-sm text-gray-500">Matemáticas Avanzadas</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-red-600">Mañana</span>
                                        <span class="text-xs text-gray-500">08:00 - 10:00 AM</span>
                                    </div>
                                </div>
                                
                                <div class="flex items-center justify-between p-3 bg-yellow-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-yellow-100 text-yellow-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-file-alt"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Quiz</h4>
                                            <p class="text-sm text-gray-500">Historia Universal</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-yellow-600">Viernes</span>
                                        <span class="text-xs text-gray-500">11:30 AM - 12:30 PM</span>
                                    </div>
                                </div>
                                
                                <div class="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-blue-100 text-blue-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-flask"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Examen de Laboratorio</h4>
                                            <p class="text-sm text-gray-500">Física Experimental</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-blue-600">Próxima semana</span>
                                        <span class="text-xs text-gray-500">Miércoles, 9:00 AM</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            
            <!-- Pie de página -->
            <footer class="bg-white border-t border-gray-200 py-4 px-6">
                <div class="flex flex-col md:flex-row justify-between items-center">
                    <p class="text-sm text-gray-500">© 2023 Sistema de Gestión Académica. Todos los derechos reservados.</p>
                    <div class="flex space-x-4 mt-2 md:mt-0">
                        <a href="#" class="text-sm text-gray-500 hover:text-blue-600">Términos de servicio</a>
                        <a href="#" class="text-sm text-gray-500 hover:text-blue-600">Política de privacidad</a>
                        <a href="#" class="text-sm text-gray-500 hover:text-blue-600">Contacto</a>
                    </div>
                </div>
            </footer>
        </div>
    </div>

    <!-- Script de Alpine.js para menús desplegables -->
    <script src="https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js" defer></script>
    
    <!-- Scripts personalizados -->
    <script src="${pageContext.request.contextPath}/WEB-INF/views/dashboard/estudiante/estudiante.js"></script>
    
    <script>
        // Toggle sidebar en versión móvil
        document.addEventListener('DOMContentLoaded', function() {
            const sidebar = document.getElementById('sidebar');
            const mainContent = document.getElementById('mainContent');
            const sidebarToggle = document.getElementById('sidebarToggle');
            
            if (sidebarToggle) {
                sidebarToggle.addEventListener('click', function() {
                    sidebar.classList.toggle('sidebar-collapsed');
                    mainContent.classList.toggle('sidebar-active');
                });
            }
            
            // Cerrar menú móvil al hacer clic en un enlace
            const navLinks = document.querySelectorAll('.sidebar a');
            navLinks.forEach(link => {
                link.addEventListener('click', function() {
                    if (window.innerWidth < 1024) {
                        sidebar.classList.add('sidebar-collapsed');
                        mainContent.classList.remove('sidebar-active');
                    }
                });
            });
        });
        
        // Manejo del redimensionamiento de la ventana
        window.addEventListener('resize', function() {
            const sidebar = document.getElementById('sidebar');
            const mainContent = document.getElementById('mainContent');
            
            if (window.innerWidth >= 1024) {
                sidebar.classList.remove('sidebar-collapsed');
                mainContent.classList.remove('sidebar-active');
            }
        });
    </script>
</body>
</html>
