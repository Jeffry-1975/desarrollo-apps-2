<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel del Docente - Sistema de Gestión Académica</title>
    
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/views/dashboard/docente/docente.css">
</head>
<body class="bg-gray-50">
    <div class="flex h-screen overflow-hidden">
        <!-- Sidebar -->
        <aside class="sidebar" id="sidebar">
            <div class="p-4 border-b border-gray-200">
                <div class="flex items-center justify-between">
                    <h2 class="text-xl font-bold text-gray-800">Docente</h2>
                    <button id="mobileMenuButton" class="lg:hidden text-gray-500 hover:text-gray-700">
                        <i class="fas fa-times text-xl"></i>
                    </button>
                </div>
            </div>
            
            <!-- Menú de navegación -->
            <nav class="p-4">
                <ul>
                    <li class="mb-2">
                        <a href="#" class="flex items-center p-2 text-white bg-green-600 rounded-lg">
                            <i class="fas fa-home mr-3"></i>
                            <span>Inicio</span>
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="flex items-center p-2 text-gray-700 hover:bg-gray-100 rounded-lg">
                            <i class="fas fa-chalkboard-teacher mr-3"></i>
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
                            <span>Estadísticas</span>
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
                        <h1 class="text-xl font-semibold text-gray-800">Panel del Docente</h1>
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
                                <div class="h-10 w-10 rounded-full bg-green-100 flex items-center justify-center text-green-600 font-semibold">
                                    <span>DP</span>
                                </div>
                                <span class="hidden md:inline text-gray-700">Dra. Pérez</span>
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
                    <div class="bg-gradient-to-r from-green-600 to-green-800 rounded-xl p-6 mb-6 text-white">
                        <div class="flex flex-col md:flex-row md:items-center md:justify-between">
                            <div>
                                <h2 class="text-2xl font-bold mb-2">¡Bienvenida, Dra. Pérez!</h2>
                                <p class="text-green-100">Revisa tus próximas clases, tareas por calificar y estadísticas.</p>
                            </div>
                            <div class="mt-4 md:mt-0">
                                <a href="#" class="inline-block bg-white text-green-600 px-4 py-2 rounded-lg font-medium hover:bg-green-50 transition-colors">
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
                                    <i class="fas fa-chalkboard-teacher text-xl"></i>
                                </div>
                                <div>
                                    <p class="text-gray-500 text-sm">Cursos Activos</p>
                                    <h3 class="text-2xl font-bold text-gray-800">4</h3>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center">
                                <div class="p-3 rounded-full bg-green-100 text-green-600 mr-4">
                                    <i class="fas fa-tasks text-xl"></i>
                                </div>
                                <div>
                                    <p class="text-gray-500 text-sm">Tareas por Calificar</p>
                                    <h3 class="text-2xl font-bold text-gray-800">8</h3>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center">
                                <div class="p-3 rounded-full bg-yellow-100 text-yellow-600 mr-4">
                                    <i class="fas fa-calendar-check text-xl"></i>
                                </div>
                                <div>
                                    <p class="text-gray-500 text-sm">Clases Hoy</p>
                                    <h3 class="text-2xl font-bold text-gray-800">3</h3>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center">
                                <div class="p-3 rounded-full bg-purple-100 text-purple-600 mr-4">
                                    <i class="fas fa-users text-xl"></i>
                                </div>
                                <div>
                                    <p class="text-gray-500 text-sm">Estudiantes</p>
                                    <h3 class="text-2xl font-bold text-gray-800">120</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Gráficos -->
                    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
                        <!-- Gráfico de asistencia -->
                        <div class="card p-6">
                            <h3 class="text-lg font-semibold text-gray-800 mb-4">Asistencia de Estudiantes</h3>
                            <div class="h-64">
                                <canvas id="attendanceChart"></canvas>
                            </div>
                        </div>
                        
                        <!-- Gráfico de rendimiento -->
                        <div class="card p-6">
                            <div class="flex justify-between items-center mb-4">
                                <h3 class="text-lg font-semibold text-gray-800">Rendimiento de la Clase</h3>
                                <select class="text-sm border border-gray-300 rounded-md px-2 py-1">
                                    <option>Matemáticas Avanzadas</option>
                                    <option>Física Cuántica</option>
                                    <option>Álgebra Lineal</option>
                                </select>
                            </div>
                            <div class="h-64">
                                <canvas id="performanceChart"></canvas>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Próximas clases y tareas por calificar -->
                    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
                        <!-- Próximas clases -->
                        <div class="card p-6">
                            <div class="flex justify-between items-center mb-4">
                                <h3 class="text-lg font-semibold text-gray-800">Próximas Clases</h3>
                                <a href="#" class="text-sm text-green-600 hover:underline">Ver horario completo</a>
                            </div>
                            
                            <div class="space-y-4">
                                <div class="flex items-start p-3 border border-gray-100 rounded-lg hover:shadow-sm transition-shadow">
                                    <div class="p-2 bg-blue-100 text-blue-600 rounded-lg mr-4">
                                        <div class="text-center">
                                            <div class="font-bold text-lg">10</div>
                                            <div class="text-xs uppercase">MAY</div>
                                        </div>
                                    </div>
                                    <div class="flex-1">
                                        <h4 class="font-medium text-gray-800">Matemáticas Avanzadas</h4>
                                        <p class="text-sm text-gray-500">08:00 - 09:30 AM • Aula 302</p>
                                        <div class="mt-2">
                                            <span class="inline-block bg-green-100 text-green-800 text-xs px-2 py-1 rounded">15/20 estudiantes confirmados</span>
                                        </div>
                                    </div>
                                    <button class="text-green-600 hover:text-green-700">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                </div>
                                
                                <div class="flex items-start p-3 border border-gray-100 rounded-lg hover:shadow-sm transition-shadow">
                                    <div class="p-2 bg-green-100 text-green-600 rounded-lg mr-4">
                                        <div class="text-center">
                                            <div class="font-bold text-lg">10</div>
                                            <div class="text-xs uppercase">MAY</div>
                                        </div>
                                    </div>
                                    <div class="flex-1">
                                        <h4 class="font-medium text-gray-800">Física Cuántica</h4>
                                        <p class="text-sm text-gray-500">11:00 - 12:30 PM • Laboratorio 4</p>
                                        <div class="mt-2">
                                            <span class="inline-block bg-yellow-100 text-yellow-800 text-xs px-2 py-1 rounded">12/18 estudiantes confirmados</span>
                                        </div>
                                    </div>
                                    <button class="text-green-600 hover:text-green-700">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                </div>
                                
                                <div class="flex items-start p-3 border border-gray-100 rounded-lg hover:shadow-sm transition-shadow">
                                    <div class="p-2 bg-purple-100 text-purple-600 rounded-lg mr-4">
                                        <div class="text-center">
                                            <div class="font-bold text-lg">11</div>
                                            <div class="text-xs uppercase">MAY</div>
                                        </div>
                                    </div>
                                    <div class="flex-1">
                                        <h4 class="font-medium text-gray-800">Álgebra Lineal</h4>
                                        <p class="text-sm text-gray-500">02:00 - 03:30 PM • Aula 205</p>
                                        <div class="mt-2">
                                            <span class="inline-block bg-blue-100 text-blue-800 text-xs px-2 py-1 rounded">20/25 estudiantes confirmados</span>
                                        </div>
                                    </div>
                                    <button class="text-green-600 hover:text-green-700">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                </div>
                            </div>
                            
                            <div class="mt-4 text-center">
                                <a href="#" class="text-green-600 text-sm font-medium hover:underline">
                                    Ver calendario completo <i class="fas fa-arrow-right ml-1"></i>
                                </a>
                            </div>
                        </div>
                        
                        <!-- Tareas por calificar -->
                        <div class="card p-6">
                            <div class="flex justify-between items-center mb-4">
                                <h3 class="text-lg font-semibold text-gray-800">Tareas por Calificar</h3>
                                <a href="#" class="text-sm text-green-600 hover:underline">Ver todas</a>
                            </div>
                            
                            <div class="space-y-4">
                                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-yellow-100 text-yellow-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-book"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Tarea #3 - Derivadas</h4>
                                            <p class="text-sm text-gray-500">Matemáticas Avanzadas</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-yellow-600">15/25 por calificar</span>
                                        <span class="text-xs text-gray-500">Vence: Hoy 23:59</span>
                                    </div>
                                </div>
                                
                                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-blue-100 text-blue-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-flask"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Informe de Laboratorio 2</h4>
                                            <p class="text-sm text-gray-500">Física Cuántica</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-blue-600">8/18 por calificar</span>
                                        <span class="text-xs text-gray-500">Vence: Mañana</span>
                                    </div>
                                </div>
                                
                                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                    <div class="flex items-center">
                                        <div class="h-10 w-10 bg-purple-100 text-purple-600 rounded-lg flex items-center justify-center mr-3">
                                            <i class="fas fa-calculator"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Ejercicios de Matrices</h4>
                                            <p class="text-sm text-gray-500">Álgebra Lineal</p>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <span class="block text-sm font-medium text-purple-600">3/25 por calificar</span>
                                        <span class="text-xs text-gray-500">Vence: 12 May</span>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="mt-4">
                                <a href="#" class="block text-center border-2 border-dashed border-gray-300 hover:border-green-300 hover:bg-green-50 rounded-lg p-3 text-green-600 font-medium transition">
                                    <i class="fas fa-plus mr-2"></i> Crear nueva tarea
                                </a>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Anuncios recientes y acciones rápidas -->
                    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
                        <!-- Anuncios recientes -->
                        <div class="lg:col-span-2">
                            <div class="card p-6">
                                <div class="flex justify-between items-center mb-4">
                                    <h3 class="text-lg font-semibold text-gray-800">Anuncios Recientes</h3>
                                    <a href="#" class="text-sm text-green-600 hover:underline">Ver todos</a>
                                </div>
                                
                                <div class="space-y-4">
                                    <div class="flex items-start pb-4 border-b border-gray-100">
                                        <div class="h-10 w-10 bg-blue-100 text-blue-600 rounded-full flex items-center justify-center mr-3 flex-shrink-0">
                                            <i class="fas fa-bullhorn"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Nuevo material de estudio disponible</h4>
                                            <p class="text-sm text-gray-600 mt-1">He subido las diapositivas del tema 4: "Aplicaciones de las derivadas parciales" en la sección de Materiales del curso.</p>
                                            <div class="flex items-center mt-2 text-xs text-gray-500">
                                                <span>Hoy, 10:30 AM</span>
                                                <span class="mx-2">•</span>
                                                <span>Matemáticas Avanzadas</span>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start pb-4 border-b border-gray-100">
                                        <div class="h-10 w-10 bg-yellow-100 text-yellow-600 rounded-full flex items-center justify-center mr-3 flex-shrink-0">
                                            <i class="fas fa-exclamation-circle"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Recordatorio: Cambio de aula</h4>
                                            <p class="text-sm text-gray-600 mt-1">La clase de Física Cuántica del próximo lunes será en el Aula Magna en lugar del Laboratorio 4 debido a mantenimiento.</p>
                                            <div class="flex items-center mt-2 text-xs text-gray-500">
                                                <span>Ayer, 4:15 PM</span>
                                                <span class="mx-2">•</span>
                                                <span>Física Cuántica</span>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="h-10 w-10 bg-green-100 text-green-600 rounded-full flex items-center justify-center mr-3 flex-shrink-0">
                                            <i class="fas fa-check-circle"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Tarea #2 calificada</h4>
                                            <p class="text-sm text-gray-600 mt-1">He terminado de calificar la Tarea #2. Pueden revisar sus calificaciones en la sección de Calificaciones.</p>
                                            <div class="flex items-center mt-2 text-xs text-gray-500">
                                                <span>10 May, 2:30 PM</span>
                                                <span class="mx-2">•</span>
                                                <span>Álgebra Lineal</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="mt-4">
                                    <a href="#" class="inline-flex items-center text-green-600 hover:text-green-700 text-sm font-medium">
                                        <i class="fas fa-plus-circle mr-1"></i> Crear nuevo anuncio
                                    </a>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Acciones rápidas -->
                        <div>
                            <div class="card p-6">
                                <h3 class="text-lg font-semibold text-gray-800 mb-4">Acciones Rápidas</h3>
                                
                                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-1 gap-4">
                                    <a href="#" class="flex flex-col items-center justify-center p-4 border-2 border-dashed border-gray-200 rounded-lg hover:border-green-300 hover:bg-green-50 transition-colors text-center">
                                        <div class="h-12 w-12 bg-blue-100 text-blue-600 rounded-full flex items-center justify-center mb-2">
                                            <i class="fas fa-user-plus text-xl"></i>
                                        </div>
                                        <span class="text-sm font-medium text-gray-700">Registrar Asistencia</span>
                                    </a>
                                    
                                    <a href="#" class="flex flex-col items-center justify-center p-4 border-2 border-dashed border-gray-200 rounded-lg hover:border-green-300 hover:bg-green-50 transition-colors text-center">
                                        <div class="h-12 w-12 bg-green-100 text-green-600 rounded-full flex items-center justify-center mb-2">
                                            <i class="fas fa-tasks text-xl"></i>
                                        </div>
                                        <span class="text-sm font-medium text-gray-700">Crear Tarea</span>
                                    </a>
                                    
                                    <a href="#" class="flex flex-col items-center justify-center p-4 border-2 border-dashed border-gray-200 rounded-lg hover:border-green-300 hover:bg-green-50 transition-colors text-center">
                                        <div class="h-12 w-12 bg-yellow-100 text-yellow-600 rounded-full flex items-center justify-center mb-2">
                                            <i class="fas fa-bullhorn text-xl"></i>
                                        </div>
                                        <span class="text-sm font-medium text-gray-700">Publicar Anuncio</span>
                                    </a>
                                    
                                    <a href="#" class="flex flex-col items-center justify-center p-4 border-2 border-dashed border-gray-200 rounded-lg hover:border-green-300 hover:bg-green-50 transition-colors text-center">
                                        <div class="h-12 w-12 bg-purple-100 text-purple-600 rounded-full flex items-center justify-center mb-2">
                                            <i class="fas fa-file-upload text-xl"></i>
                                        </div>
                                        <span class="text-sm font-medium text-gray-700">Subir Material</span>
                                    </a>
                                </div>
                            </div>
                            
                            <!-- Próximos eventos -->
                            <div class="card p-6 mt-6">
                                <div class="flex justify-between items-center mb-4">
                                    <h3 class="text-lg font-semibold text-gray-800">Próximos Eventos</h3>
                                    <a href="#" class="text-sm text-green-600 hover:underline">Ver todos</a>
                                </div>
                                
                                <div class="space-y-4">
                                    <div class="flex items-start">
                                        <div class="bg-red-100 text-red-600 rounded-lg p-2 mr-3 flex-shrink-0">
                                            <div class="text-center">
                                                <div class="font-bold">15</div>
                                                <div class="text-xs">MAY</div>
                                            </div>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Entrega de Calificaciones Parciales</h4>
                                            <p class="text-sm text-gray-500">Fecha límite para subir calificaciones parciales</p>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="bg-blue-100 text-blue-600 rounded-lg p-2 mr-3 flex-shrink-0">
                                            <div class="text-center">
                                                <div class="font-bold">20</div>
                                                <div class="text-xs">MAY</div>
                                            </div>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Reunión de Docentes</h4>
                                            <p class="text-sm text-gray-500">Sala de Profesores, 4:00 PM</p>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="bg-green-100 text-green-600 rounded-lg p-2 mr-3 flex-shrink-0">
                                            <div class="text-center">
                                                <div class="font-bold">25</div>
                                                <div class="text-xs">MAY</div>
                                            </div>
                                        </div>
                                        <div>
                                            <h4 class="font-medium text-gray-800">Taller de Innovación Educativa</h4>
                                            <p class="text-sm text-gray-500">Aula Magna, 9:00 AM - 1:00 PM</p>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="mt-4 text-center">
                                    <a href="#" class="inline-flex items-center text-green-600 hover:text-green-700 text-sm font-medium">
                                        <i class="fas fa-calendar-plus mr-1"></i> Agregar evento
                                    </a>
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
                        <a href="#" class="text-sm text-gray-500 hover:text-green-600">Términos de servicio</a>
                        <a href="#" class="text-sm text-gray-500 hover:text-green-600">Política de privacidad</a>
                        <a href="#" class="text-sm text-gray-500 hover:text-green-600">Contacto</a>
                    </div>
                </div>
            </footer>
        </div>
    </div>

    <!-- Script de Alpine.js para menús desplegables -->
    <script src="https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js" defer></script>
    
    <!-- Scripts personalizados -->
    <script src="${pageContext.request.contextPath}/WEB-INF/views/dashboard/docente/docente.js"></script>
    
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
