<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido - Sistema de Gestión Académica</title>
    
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
    </style>
</head>
<body class="antialiased text-slate-800">
    <main>
        <!-- Pantalla de Bienvenida -->
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
                <a href="login.jsp" class="btn-fill">
                    Comenzar
                </a>
            </div>
        </section>
    </main>
</body>
</html>
