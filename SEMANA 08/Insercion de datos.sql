INSERT INTO usuario (cod_usuario, dni_usuario, nombre_usuario, apellido_usuario, email_usuario, telefono_usuario, rol_usuario) VALUES
('ADM001', 12345678, 'Jefferson', 'Paredes', 'jefferson@gmail.com', '987654321', 'ADMIN'),
('CLI001', 12345679, 'Diego', 'Flores', 'diego@gmail.com', '933441406', 'CLIENTE');

INSERT INTO cancha (cod_cancha, nombre_cancha, deporte_cancha, capacidad_cancha, descripcion_cancha, estado_cancha) VALUES
('CAN001', 'Cancha Futsal 6 v 6', 'Futsal', 12, 'Cancha de futsal para 12 con césped sintético e iluminación artificial', 'Disponible'),
('CAN002', 'Cancha Vóley 6 v 6', 'Voley', 12, 'Cancha de voley con equipos actualizados y redes oficiales', 'Disponible'),
('CAN003', 'Cancha Básquet 5 v 5', 'Basquet', 10, 'Cancha techada de básquet con piso de madera y marcadores digitales', 'Disponible');

INSERT INTO reserva (cod_usuario, cod_cancha, fecha_reserva, horaInicio_reserva, horaFin_reserva, estado_reserva, precio_reserva) VALUES
('CLI001', 'CAN001', '2025-10-25', '16:00:00', '18:00:00', 'Pendiente', 120.50),
('CLI001', 'CAN002', '2025-10-26', '10:00:00', '11:30:00', 'Pendiente', 80.00);