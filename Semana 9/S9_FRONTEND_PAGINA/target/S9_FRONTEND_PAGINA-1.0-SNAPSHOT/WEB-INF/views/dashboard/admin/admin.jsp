<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración - Sistema de Gestión Académica</title>
    
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/views/dashboard/admin/admin.css">
</head>
<body class="bg-gray-50">
    <div class="flex h-screen overflow-hidden">
        <!-- Sidebar -->
        <aside class="sidebar bg-white shadow-md fixed h-full z-10" id="sidebar">
            <div class="p-4 border-b border-gray-200">
                <div class="flex items-center justify-between">
                    <div class="flex items-center">
                        <div class="w-10 h-10 rounded-full bg-indigo-100 flex items-center justify-center text-indigo-600 font-bold text-xl">
                            <i class="fas fa-user-shield"></i>
                        </div>
                        <span class="ml-3 font-bold text-lg">Admin</span>
                    </div>
                    <button id="sidebarToggle" class="text-gray-500 hover:text-gray-700">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </div>
            <nav class="p-4">
                <ul class="space-y-2">
                    <li>
                        <a href="#" class="nav-link active">
                            <i class="fas fa-home"></i>
                            <span>Inicio</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="nav-link">
                            <i class="fas fa-users"></i>
                            <span>Usuarios</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="nav-link">
                            <i class="fas fa-user-graduate"></i>
                            <span>Estudiantes</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="nav-link">
                            <i class="fas fa-chalkboard-teacher"></i>
                            <span>Docentes</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="nav-link">
                            <i class="fas fa-book"></i>
                            <span>Cursos</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="nav-link">
                            <i class="fas fa-calendar-alt"></i>
                            <span>Horarios</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="nav-link">
                            <i class="fas fa-chart-bar"></i>
                            <span>Reportes</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="nav-link">
                            <i class="fas fa-cog"></i>
                            <span>Configuración</span>
                        </a>
                    </li>
                </ul>
                
                <div class="mt-8 pt-4 border-t border-gray-200">
                    <a href="#" class="nav-link text-red-600 hover:text-red-700">
                        <i class="fas fa-sign-out-alt"></i>
                        <span>Cerrar Sesión</span>
                    </a>
                </div>
            </nav>
        </aside>
        
        <!-- Contenido Principal -->
        <div class="main-content flex-1 flex flex-col overflow-hidden" id="mainContent">
            <!-- Barra Superior -->
            <header class="bg-white shadow-sm z-10">
                <div class="flex items-center justify-between p-4">
                    <div class="flex items-center">
                        <button id="mobileMenuButton" class="text-gray-500 hover:text-gray-700 mr-4 md:hidden">
                            <i class="fas fa-bars text-xl"></i>
                        </button>
                        <h1 class="text-xl font-semibold text-gray-800">Panel de Administración</h1>
                    </div>
                    <div class="flex items-center space-x-4">
                        <div class="relative">
                            <button class="text-gray-500 hover:text-gray-700">
                                <i class="fas fa-bell text-xl"></i>
                                <span class="notification-badge">3</span>
                            </button>
                        </div>
                        <div class="relative" x-data="{ open: false }">
                            <button @click="open = !open" class="flex items-center space-x-2 focus:outline-none">
                                <div class="w-8 h-8 rounded-full bg-indigo-100 flex items-center justify-center text-indigo-600 font-semibold">
                                    A
                                </div>
                                <span class="hidden md:inline text-sm font-medium text-gray-700">Administrador</span>
                                <i class="fas fa-chevron-down text-xs text-gray-500"></i>
                            </button>
                            
                            <div x-show="open" 
                                 @click.away="open = false"
                                 class="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-50" 
                                 style="display: none;">
                                <a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                    <i class="fas fa-user mr-2"></i> Perfil
                                </a>
                                <a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                    <i class="fas fa-cog mr-2"></i> Configuración
                                </a>
                                <div class="border-t border-gray-100 my-1"></div>
                                <a href="#" class="block px-4 py-2 text-sm text-red-600 hover:bg-gray-100">
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
                    <!-- Banner de Bienvenida -->
                    <div class="bg-gradient-to-r from-indigo-600 to-indigo-800 rounded-xl p-6 mb-6 text-white">
                        <div class="flex flex-col md:flex-row md:items-center md:justify-between">
                            <div>
                                <h2 class="text-2xl font-bold mb-2">¡Bienvenido, Administrador!</h2>
                                <p class="text-indigo-100">Gestiona tu sistema académico de manera eficiente y mantén todo bajo control.</p>
                            </div>
                            <button class="mt-4 md:mt-0 bg-white text-indigo-600 hover:bg-indigo-50 font-medium py-2 px-4 rounded-lg transition duration-200">
                                Ver Estadísticas Completas
                            </button>
                        </div>
                    </div>
                    
                    <!-- Tarjetas de Resumen -->
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
                        <div class="card p-6">
                            <div class="flex items-center justify-between">
                                <div>
                                    <p class="text-sm text-gray-500">Usuarios Totales</p>
                                    <h3 class="text-2xl font-bold">1,248</h3>
                                    <p class="text-sm text-green-500 mt-1">
                                        <i class="fas fa-arrow-up mr-1"></i> 12.5%
                                    </p>
                                </div>
                                <div class="p-3 rounded-full bg-blue-100 text-blue-600">
                                    <i class="fas fa-users text-xl"></i>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center justify-between">
                                <div>
                                    <p class="text-sm text-gray-500">Estudiantes Activos</p>
                                    <h3 class="text-2xl font-bold">856</h3>
                                    <p class="text-sm text-green-500 mt-1">
                                        <i class="fas fa-arrow-up mr-1"></i> 5.3%
                                    </p>
                                </div>
                                <div class="p-3 rounded-full bg-green-100 text-green-600">
                                    <i class="fas fa-user-graduate text-xl"></i>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center justify-between">
                                <div>
                                    <p class="text-sm text-gray-500">Docentes Activos</p>
                                    <h3 class="text-2xl font-bold">124</h3>
                                    <p class="text-sm text-green-500 mt-1">
                                        <i class="fas fa-arrow-up mr-1"></i> 2.1%
                                    </p>
                                </div>
                                <div class="p-3 rounded-full bg-yellow-100 text-yellow-600">
                                    <i class="fas fa-chalkboard-teacher text-xl"></i>
                                </div>
                            </div>
                        </div>
                        
                        <div class="card p-6">
                            <div class="flex items-center justify-between">
                                <div>
                                    <p class="text-sm text-gray-500">Cursos Activos</p>
                                    <h3 class="text-2xl font-bold">48</h3>
                                    <p class="text-sm text-red-500 mt-1">
                                        <i class="fas fa-arrow-down mr-1"></i> 1.8%
                                    </p>
                                </div>
                                <div class="p-3 rounded-full bg-purple-100 text-purple-600">
                                    <i class="fas fa-book text-xl"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Gráficos -->
                    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
                        <!-- Gráfico de Crecimiento de Usuarios -->
                        <div class="card p-6">
                            <div class="flex justify-between items-center mb-4">
                                <h3 class="text-lg font-semibold text-gray-800">Crecimiento de Usuarios</h3>
                                <div class="flex space-x-2">
                                    <button class="text-xs px-2 py-1 rounded bg-indigo-100 text-indigo-700">Mensual</button>
                                    <button class="text-xs px-2 py-1 rounded text-gray-500 hover:bg-gray-100">Anual</button>
                                </div>
                            </div>
                            <div class="h-64">
                                <canvas id="userGrowthChart"></canvas>
                            </div>
                        </div>
                        
                        <!-- Distribución de Cursos -->
                        <div class="card p-6">
                            <h3 class="text-lg font-semibold text-gray-800 mb-4">Distribución de Cursos</h3>
                            <div class="h-64">
                                <canvas id="courseDistributionChart"></canvas>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Actividad Reciente y Acciones Rápidas -->
                    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
                        <!-- Actividad Reciente -->
                        <div class="lg:col-span-2">
                            <div class="card p-6 h-full">
                                <div class="flex justify-between items-center mb-4">
                                    <h3 class="text-lg font-semibold text-gray-800">Actividad Reciente</h3>
                                    <a href="#" class="text-sm text-indigo-600 hover:underline">Ver todo</a>
                                </div>
                                
                                <div class="space-y-4">
                                    <div class="flex items-start">
                                        <div class="bg-green-100 text-green-600 p-2 rounded-full mr-3 mt-1">
                                            <i class="fas fa-user-plus"></i>
                                        </div>
                                        <div class="flex-1">
                                            <p class="text-sm font-medium">Nuevo estudiante registrado</p>
                                            <p class="text-xs text-gray-500">Juan Pérez se ha registrado en el curso de Matemáticas Avanzadas</p>
                                            <p class="text-xs text-gray-400 mt-1">Hace 5 minutos</p>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="bg-blue-100 text-blue-600 p-2 rounded-full mr-3 mt-1">
                                            <i class="fas fa-file-upload"></i>
                                        </div>
                                        <div class="flex-1">
                                            <p class="text-sm font-medium">Nueva tarea subida</p>
                                            <p class="text-xs text-gray-500">María García ha subido la tarea de Física Cuántica</p>
                                            <p class="text-xs text-gray-400 mt-1">Hace 1 hora</p>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="bg-purple-100 text-purple-600 p-2 rounded-full mr-3 mt-1">
                                            <i class="fas fa-comment-alt"></i>
                                        </div>
                                        <div class="flex-1">
                                            <p class="text-sm font-medium">Nuevo comentario</p>
                                            <p class="text-xs text-gray-500">Carlos López ha comentado en la publicación de Álgebra Lineal</p>
                                            <p class="text-xs text-gray-400 mt-1">Hace 3 horas</p>
                                        </div>
                                    </div>
                                    
                                    <div class="flex items-start">
                                        <div class="bg-yellow-100 text-yellow-600 p-2 rounded-full mr-3 mt-1">
                                            <i class="fas fa-exclamation-triangle"></i>
                                        </div>
                                        <div class="flex-1">
                                            <p class="text-sm font-medium">Advertencia de asistencia</p>
                                            <p class="text-xs text-gray-500">Ana Martínez ha alcanzado el límite de faltas en Cálculo Diferencial</p>
                                            <p class="text-xs text-gray-400 mt-1">Hace 1 día</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Acciones Rápidas -->
                        <div>
                            <div class="card p-6 mb-6">
                                <h3 class="text-lg font-semibold text-gray-800 mb-4">Acciones Rápidas</h3>
                                
                                <div class="space-y-3">
                                    <a href="#" class="flex items-center p-3 bg-indigo-50 text-indigo-700 rounded-lg hover:bg-indigo-100 transition-colors">
                                        <div class="w-10 h-10 rounded-full bg-indigo-100 flex items-center justify-center text-indigo-600 mr-3">
                                            <i class="fas fa-user-plus"></i>
                                        </div>
                                        <div>
                                            <p class="font-medium">Nuevo Usuario</p>
                                            <p class="text-xs text-gray-500">Agregar un nuevo usuario al sistema</p>
                                        </div>
                                    </a>
                                    
                                    <a href="#" class="flex items-center p-3 bg-green-50 text-green-700 rounded-lg hover:bg-green-100 transition-colors">
                                        <div class="w-10 h-10 rounded-full bg-green-100 flex items-center justify-center text-green-600 mr-3">
                                            <i class="fas fa-book"></i>
                                        </div>
                                        <div>
                                            <p class="font-medium">Crear Curso</p>
                                            <p class="text-xs text-gray-500">Configurar un nuevo curso académico</p>
                                        </div>
                                    </a>
                                    
                                    <a href="#" class="flex items-center p-3 bg-purple-50 text-purple-700 rounded-lg hover:bg-purple-100 transition-colors">
                                        <div class="w-10 h-10 rounded-full bg-purple-100 flex items-center justify-center text-purple-600 mr-3">
                                            <i class="fas fa-bullhorn"></i>
                                        </div>
                                        <div>
                                            <p class="font-medium">Nuevo Anuncio</p>
                                            <p class="text-xs text-gray-500">Publicar un anuncio importante</p>
                                        </div>
                                    </a>
                                    
                                    <a href="#" class="flex items-center p-3 bg-blue-50 text-blue-700 rounded-lg hover:bg-blue-100 transition-colors">
                                        <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 mr-3">
                                            <i class="fas fa-file-export"></i>
                                        </div>
                                        <div>
                                            <p class="font-medium">Generar Reporte</p>
                                            <p class="text-xs text-gray-500">Exportar datos del sistema</p>
                                        </div>
                                    </a>
                                </div>
                            </div>
                            
                            <!-- Estado del Sistema -->
                            <div class="card p-6">
                                <h3 class="text-lg font-semibold text-gray-800 mb-4">Estado del Sistema</h3>
                                
                                <div class="space-y-4">
                                    <div>
                                        <div class="flex justify-between text-sm mb-1">
                                            <span class="font-medium">Almacenamiento</span>
                                            <span>65% usado</span>
                                        </div>
                                        <div class="w-full bg-gray-200 rounded-full h-2">
                                            <div class="bg-blue-600 h-2 rounded-full" style="width: 65%"></div>
                                        </div>
                                        <p class="text-xs text-gray-500 mt-1">325 GB de 500 GB disponibles</p>
                                    </div>
                                    
                                    <div>
                                        <div class="flex justify-between text-sm mb-1">
                                            <span class="font-medium">Base de Datos</span>
                                            <span class="text-green-600">Estable</span>
                                        </div>
                                        <div class="w-full bg-gray-200 rounded-full h-2">
                                            <div class="bg-green-500 h-2 rounded-full" style="width: 100%"></div>
                                        </div>
                                        <p class="text-xs text-gray-500 mt-1">Última copia de seguridad: Hoy, 02:00 AM</p>
                                    </div>
                                    
                                    <div>
                                        <div class="flex justify-between text-sm mb-1">
                                            <span class="font-medium">Rendimiento</span>
                                            <span class="text-yellow-600">Moderado</span>
                                        </div>
                                        <div class="w-full bg-gray-200 rounded-full h-2">
                                            <div class="bg-yellow-500 h-2 rounded-full" style="width: 75%"></div>
                                        </div>
                                        <p class="text-xs text-gray-500 mt-1">Uso de CPU: 45% | Memoria: 3.2/8 GB</p>
                                    </div>
                                    
                                    <div class="pt-2">
                                        <div class="flex items-center text-sm text-gray-600">
                                            <div class="w-2 h-2 rounded-full bg-green-500 mr-2"></div>
                                            <span>Sistema operativo: Linux</span>
                                        </div>
                                        <div class="flex items-center text-sm text-gray-600 mt-1">
                                            <div class="w-2 h-2 rounded-full bg-green-500 mr-2"></div>
                                            <span>Versión: 3.2.1</span>
                                        </div>
                                        <div class="flex items-center text-sm text-gray-600 mt-1">
                                            <div class="w-2 h-2 rounded-full bg-green-500 mr-2"></div>
                                            <span>Última actualización: 01/11/2023</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Tabla de Usuarios Recientes -->
                    <div class="card overflow-hidden mt-6">
                        <div class="p-6">
                            <div class="flex flex-col md:flex-row md:items-center md:justify-between">
                                <div>
                                    <h3 class="text-lg font-semibold text-gray-800">Usuarios Recientes</h3>
                                    <p class="text-sm text-gray-500 mt-1">Lista de los últimos usuarios registrados</p>
                                </div>
                                <div class="mt-4 md:mt-0">
                                    <div class="relative">
                                        <input type="text" placeholder="Buscar usuarios..." class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 w-full md:w-64">
                                        <div class="absolute left-3 top-2.5 text-gray-400">
                                            <i class="fas fa-search"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="overflow-x-auto">
                            <table class="min-w-full divide-y divide-gray-200">
                                <thead class="bg-gray-50">
                                    <tr>
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Usuario</th>
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Rol</th>
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Estado</th>
                                        <th scope="col" class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody class="bg-white divide-y divide-gray-200">
                                    <tr class="hover:bg-gray-50">
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="flex items-center">
                                                <div class="flex-shrink-0 h-10 w-10">
                                                    <div class="h-10 w-10 rounded-full bg-indigo-100 flex items-center justify-center text-indigo-600 font-semibold">
                                                        JL
                                                    </div>
                                                </div>
                                                <div class="ml-4">
                                                    <div class="text-sm font-medium text-gray-900">Juan López</div>
                                                    <div class="text-sm text-gray-500">@juanlopez</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                                                Estudiante
                                            </span>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">juan.lopez@example.com</td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                                                Activo
                                            </span>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                                            <a href="#" class="text-indigo-600 hover:text-indigo-900 mr-3">Editar</a>
                                            <a href="#" class="text-red-600 hover:text-red-900">Eliminar</a>
                                        </td>
                                    </tr>
                                    <!-- Más filas de usuarios... -->
                                </tbody>
                            </table>
                        </div>
                        
                        <!-- Paginación -->
                        <div class="pagination">
                            <div class="pagination-info">
                                Mostrando 1 a 5 de 24 entradas
                            </div>
                            <div class="pagination-controls">
                                <button class="pagination-btn" disabled>
                                    <i class="fas fa-chevron-left"></i>
                                </button>
                                <button class="pagination-btn active">1</button>
                                <button class="pagination-btn">2</button>
                                <button class="pagination-btn">3</button>
                                <button class="pagination-btn">4</button>
                                <button class="pagination-btn">5</button>
                                <button class="pagination-btn">
                                    <i class="fas fa-chevron-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            
            <!-- Pie de Página -->
            <footer class="bg-white border-t border-gray-200 py-4 px-6">
                <div class="flex flex-col md:flex-row justify-between items-center">
                    <div class="text-sm text-gray-500">
                        &copy; 2023 Sistema de Gestión Académica. Todos los derechos reservados.
                    </div>
                    <div class="flex space-x-6 mt-4 md:mt-0">
                        <a href="#" class="text-gray-500 hover:text-gray-700">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a href="#" class="text-gray-500 hover:text-gray-700">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a href="#" class="text-gray-500 hover:text-gray-700">
                            <i class="fab fa-instagram"></i>
                        </a>
                        <a href="#" class="text-gray-500 hover:text-gray-700">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                    </div>
                </div>
            </footer>
        </div>
    </div>

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/WEB-INF/views/dashboard/admin/admin.js"></script>
</body>
</html>
