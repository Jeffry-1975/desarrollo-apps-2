// Inicialización de tooltips
function initializeTooltips() {
    const tooltipTriggers = document.querySelectorAll('[data-tooltip]');
    
    tooltipTriggers.forEach(trigger => {
        const tooltip = document.createElement('div');
        tooltip.className = 'hidden absolute z-50 py-1 px-2 text-xs text-white bg-gray-900 rounded';
        tooltip.textContent = trigger.getAttribute('data-tooltip');
        document.body.appendChild(tooltip);
        
        const updateTooltipPosition = (e) => {
            const rect = trigger.getBoundingClientRect();
            tooltip.style.top = `${rect.bottom + window.scrollY + 5}px`;
            tooltip.style.left = `${rect.left + (rect.width / 2) - (tooltip.offsetWidth / 2) + window.scrollX}px`;
        };
        
        trigger.addEventListener('mouseenter', (e) => {
            tooltip.classList.remove('hidden');
            updateTooltipPosition(e);
        });
        
        trigger.addEventListener('mousemove', updateTooltipPosition);
        
        trigger.addEventListener('mouseleave', () => {
            tooltip.classList.add('hidden');
        });
    });
}

// Inicialización de gráficos
function initializeCharts() {
    // Gráfico de rendimiento académico
    const performanceCtx = document.getElementById('performanceChart')?.getContext('2d');
    if (performanceCtx) {
        new Chart(performanceCtx, {
            type: 'bar',
            data: {
                labels: ['Matemáticas', 'Ciencias', 'Historia', 'Literatura', 'Idiomas'],
                datasets: [{
                    label: 'Calificaciones',
                    data: [85, 78, 92, 88, 95],
                    backgroundColor: [
                        'rgba(79, 70, 229, 0.7)',
                        'rgba(79, 70, 229, 0.7)',
                        'rgba(79, 70, 229, 0.7)',
                        'rgba(79, 70, 229, 0.7)',
                        'rgba(79, 70, 229, 0.7)'
                    ],
                    borderColor: [
                        '#4f46e5',
                        '#4f46e5',
                        '#4f46e5',
                        '#4f46e5',
                        '#4f46e5'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100,
                        grid: {
                            display: true,
                            drawBorder: false
                        },
                        ticks: {
                            stepSize: 20
                        }
                    },
                    x: {
                        grid: {
                            display: false,
                            drawBorder: false
                        }
                    }
                }
            }
        });
    }
}

// Manejo del sidebar móvil
function setupMobileMenu() {
    const mobileMenuButton = document.getElementById('mobileMenuButton');
    const sidebar = document.getElementById('sidebar');
    const mainContent = document.getElementById('mainContent');
    
    if (mobileMenuButton && sidebar && mainContent) {
        mobileMenuButton.addEventListener('click', function() {
            sidebar.classList.toggle('sidebar-collapsed');
            mainContent.classList.toggle('sidebar-active');
        });
    }
}

// Manejo de dropdowns
function setupDropdowns() {
    const dropdownButtons = document.querySelectorAll('[data-dropdown-toggle]');
    
    dropdownButtons.forEach(button => {
        const dropdownId = button.getAttribute('data-dropdown-toggle');
        const dropdownMenu = document.getElementById(dropdownId);
        
        if (dropdownMenu) {
            button.addEventListener('click', (e) => {
                e.stopPropagation();
                dropdownMenu.classList.toggle('hidden');
            });
            
            // Cerrar al hacer clic fuera
            document.addEventListener('click', (e) => {
                if (!button.contains(e.target) && !dropdownMenu.contains(e.target)) {
                    dropdownMenu.classList.add('hidden');
                }
            });
        }
    });
}

// Manejo de logout
function setupLogout() {
    const logoutButtons = document.querySelectorAll('.nav-link.text-red-600, .text-red-600.hover\\:text-red-700');
    
    logoutButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            // Aquí iría la lógica de cierre de sesión
            window.location.href = '../../auth/login.jsp';
        });
    });
}

// Inicialización cuando el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    setupMobileMenu();
    setupDropdowns();
    setupLogout();
    initializeTooltips();
    initializeCharts();
});

// Manejo de redimensionamiento de ventana
window.addEventListener('resize', function() {
    // Re-inicializar tooltips en caso de redimensionamiento
    initializeTooltips();
});
