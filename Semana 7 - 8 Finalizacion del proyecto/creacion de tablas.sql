-- 1. Primero eliminar la tabla reserva (por las foreign keys)
DROP TABLE IF EXISTS reserva;

-- 2. Eliminar la tabla usuario
DROP TABLE IF EXISTS usuario;

-- 3. Crear tabla usuario con contraseña
CREATE TABLE usuario (
  cod_usuario varchar(7) PRIMARY KEY,
  dni_usuario int(8) NOT NULL UNIQUE,
  password_usuario varchar(255) NOT NULL,
  nombre_usuario varchar(100) NOT NULL,
  apellido_usuario varchar(100) NOT NULL,
  email_usuario varchar(150) NOT NULL UNIQUE,
  telefono_usuario varchar(15) NOT NULL,
  rol_usuario enum('CLIENTE','ADMIN') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- 4. Insertar usuarios con contraseñas
INSERT INTO usuario (cod_usuario, dni_usuario, password_usuario, nombre_usuario, apellido_usuario, email_usuario, telefono_usuario, rol_usuario) VALUES
('ADM001', 12345678, 'admin123', 'María', 'Gonzales López', 'maria.gonzales@sports.com', '987654321', 'ADMIN'),
('CLI001', 87654321, 'cliente123', 'Carlos', 'Rodríguez Pérez', 'carlos.rodriguez@email.com', '912345678', 'CLIENTE');

-- 5. Recrear la tabla reserva
CREATE TABLE reserva (
  id_reserva int(11) PRIMARY KEY AUTO_INCREMENT,
  cod_usuario varchar(7) NOT NULL,
  cod_cancha varchar(7) NOT NULL,
  fecha_reserva date NOT NULL,
  horaInicio_reserva time NOT NULL,
  horaFin_reserva time NOT NULL,  
  estado_reserva enum('Confirmada','Pendiente','Cancelada') NOT NULL DEFAULT 'Pendiente',
  precio_reserva decimal(5,2) NOT NULL,
  FOREIGN KEY (cod_usuario) REFERENCES usuario(cod_usuario) ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (cod_cancha) REFERENCES cancha(cod_cancha) ON UPDATE CASCADE ON DELETE RESTRICT  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- 6. Insertar reservas de ejemplo
INSERT INTO reserva (cod_usuario, cod_cancha, fecha_reserva, horaInicio_reserva, horaFin_reserva, estado_reserva, precio_reserva) VALUES 
('CLI001', 'CAN001', '2024-01-20', '16:00:00', '18:00:00', 'Confirmada', 120.50),
('CLI001', 'CAN002', '2024-01-21', '10:00:00', '11:30:00', 'Pendiente', 80.00);