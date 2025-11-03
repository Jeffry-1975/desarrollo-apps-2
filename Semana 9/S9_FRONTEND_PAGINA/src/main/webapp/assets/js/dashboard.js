// dashboard.js - Funcionalidad común para los dashboards
document.addEventListener('DOMContentLoaded', () => {
    // Manejar el cierre de sesión
    const logoutBtns = document.querySelectorAll('.logout-btn');
    logoutBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            // Aquí iría la lógica de cierre de sesión
            // Por ahora, redirigimos al login
            window.location.href = '../../auth/login.jsp';
        });
    });

    // Manejar el botón de volver
    const backBtns = document.querySelectorAll('.back-btn');
    backBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const targetDashboard = btn.dataset.targetDashboard;
            if (targetDashboard) {
                document.querySelector('.subpage-container').classList.add('animate-fade-out');
                setTimeout(() => {
                    document.querySelector('.subpage-container').remove();
                    document.querySelector('.dashboard-grid').classList.remove('hidden');
                }, 500);
            }
        });
    });

    // Manejar clics en las tarjetas del dashboard
    const dashboardCards = document.querySelectorAll('.dashboard-card');
    dashboardCards.forEach(card => {
        card.addEventListener('click', () => {
            const targetPage = card.dataset.targetPage;
            const title = card.dataset.title;
            const content = card.dataset.content || `
                <div class="p-6 bg-white rounded-lg shadow-md">
                    <h3 class="text-2xl font-bold mb-4">${title}</h3>
                    <p>Contenido de ejemplo para ${title}.</p>
                    <p class="mt-4 text-sm text-gray-500">Esta es una vista previa. El contenido real se cargaría dinámicamente.</p>
                </div>
            `;

            // Ocultar el grid actual
            document.querySelector('.dashboard-grid').classList.add('hidden');
            
            // Crear contenedor de subpágina
            const subpageContainer = document.createElement('div');
            subpageContainer.className = 'subpage-container animate-fade-in';
            subpageContainer.innerHTML = `
                <div class="bg-white p-6 rounded-lg shadow-md">
                    <button class="back-btn btn-fill mb-4" data-target-dashboard="main">
                        &larr; Volver al panel
                    </button>
                    <h2 class="text-2xl font-bold mb-6">${title}</h2>
                    <div class="subpage-content">
                        ${content}
                    </div>
                </div>
            `;
            
            // Insertar después del header
            const header = document.querySelector('.dashboard-header');
            header.parentNode.insertBefore(subpageContainer, header.nextSibling);
            
            // Agregar evento al botón de volver
            subpageContainer.querySelector('.back-btn').addEventListener('click', () => {
                subpageContainer.classList.add('animate-fade-out');
                setTimeout(() => {
                    subpageContainer.remove();
                    document.querySelector('.dashboard-grid').classList.remove('hidden');
                }, 500);
            });
        });
    });
});
