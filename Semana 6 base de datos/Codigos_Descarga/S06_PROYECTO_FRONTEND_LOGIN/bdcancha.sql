CREATE TABLE cancha (
  id_cancha int(11) PRIMARY KEY,
  nombre_cancha varchar(100) NOT NULL,
  deporte_cancha varchar(50) NOT NULL,
  capacidad_cancha int(11) NOT NULL,
  descripcion_cancha text DEFAULT NULL,
  estado_cancha enum('Disponible','Mantenimiento','Cerrada') NOT NULL DEFAULT 'Disponible'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


CREATE TABLE usuario (
  cod_usuario varchar(7) PRIMARY KEY,
  dni_usuario int(11) NOT NULL,
  nombres_usuario varchar(100) NOT NULL,
  apellidos_usuario varchar(100) NOT NULL,
  email_usuario varchar(150) NOT NULL,
  telefono_usuario varchar(20) DEFAULT NULL,
  rol_usuario enum('CLIENTE','ADMIN') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE reserva (
  id_reserva int(11) PRIMARY KEY,
  cod_usuario varchar(7) NOT NULL,
  id_cancha int NOT NULL,
  fecha_reserva date NOT NULL,
  horaInicio_reserva time NOT NULL,
  horaFin_reserva time NOT NULL,
  duracion_reserva decimal(3,1) NOT NULL,
  estado_reserva enum('Confirmada','Pendiente','Cancelada') NOT NULL DEFAULT 'Pendiente',
  precio_reserva decimal(3,1) NOT NULL,
  FOREIGN KEY (cod_usuario) REFERENCES USUARIO(cod_usuario) ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_cancha) REFERENCES CANCHA(id_cancha)  ON UPDATE CASCADE ON DELETE RESTRICT,
  INDEX idx_disponibilidad (id_cancha, fecha_reserva, horaInicio_reserva) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;