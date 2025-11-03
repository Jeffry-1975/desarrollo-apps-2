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
    // Gráfico de crecimiento de usuarios
    const userGrowthCtx = document.getElementById('userGrowthChart')?.getContext('2d');
    if (userGrowthCtx) {
        new Chart(userGrowthCtx, {
            type: 'line',
            data: {
                labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul'],
                datasets: [{
                    label: 'Usuarios',
                    data: [650, 720, 780, 810, 890, 930, 1020],
                    borderColor: '#4f46e5',
                    backgroundColor: 'rgba(79, 70, 229, 0.1)',
                    tension: 0.3,
                    fill: true,
                    pointBackgroundColor: '#4f46e5',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: '#4f46e5'
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
                        grid: {
                            display: true,
                            drawBorder: false
                        },
                        ticks: {
                            stepSize: 100
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

    // Gráfico de distribución de cursos
    const courseDistributionCtx = document.getElementById('courseDistributionChart')?.getContext('2d');
    if (courseDistributionCtx) {
        new Chart(courseDistributionCtx, {
            type: 'doughnut',
            data: {
                labels: ['Matemáticas', 'Ciencias', 'Historia', 'Literatura', 'Idiomas'],
                datasets: [{
                    data: [25, 20, 15, 20, 20],
                    backgroundColor: [
                        '#4f46e5',
                        '#6366f1',
                        '#818cf8',
                        '#a5b4fc',
                        '#c7d2fe'
                    ],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                cutout: '70%',
                plugins: {
                    legend: {
                        position: 'right',
                        labels: {
                            usePointStyle: true,
                            padding: 20
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
