CREATE DATABASE  IF NOT EXISTS `bdcancha_proyecto` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bdcancha_proyecto`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: bdcancha_proyecto
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cancha`
--

DROP TABLE IF EXISTS `cancha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cancha` (
  `cod_cancha` varchar(7) NOT NULL,
  `nombre_cancha` varchar(100) NOT NULL,
  `deporte_cancha` varchar(50) NOT NULL,
  `capacidad_cancha` int NOT NULL,
  `descripcion_cancha` text NOT NULL,
  `estado_cancha` enum('Disponible','Mantenimiento','Cerrada') NOT NULL DEFAULT 'Disponible',
  `precio_hora` decimal(5,2) NOT NULL DEFAULT '50.00',
  PRIMARY KEY (`cod_cancha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cancha`
--

LOCK TABLES `cancha` WRITE;
/*!40000 ALTER TABLE `cancha` DISABLE KEYS */;
INSERT INTO `cancha` VALUES ('CAN001','Cancha Futsal 6 v 6','Futsal',12,'Cancha de futsal para 12 con césped sintético e iluminación artificial','Disponible',50.50),('CAN002','Cancha Vóley 6 v 6','Voley',12,'Cancha de voley con equipos actualizados y redes oficiales','Disponible',45.00),('CAN003','Cancha Básquet 5 v 5','Basquet',10,'Cancha techada de básquet con piso de madera y marcadores digitales','Disponible',40.00),('CAN004','Cancha Futsal 5 v 5','Futsal',10,'Cancha con Cesped Sintetico bien equipado','Disponible',35.00),('CAN005','Cancha Basquet 3 v 3','Basquet',6,'Cancha techada de básquet con piso de madera y marcadores digitales','Disponible',30.00);
/*!40000 ALTER TABLE `cancha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `id_reserva` int NOT NULL AUTO_INCREMENT,
  `cod_usuario` varchar(7) NOT NULL,
  `cod_cancha` varchar(7) NOT NULL,
  `fecha_reserva` date NOT NULL,
  `horaInicio_reserva` time NOT NULL,
  `horaFin_reserva` time NOT NULL,
  `estado_reserva` enum('Confirmada','Pendiente','Cancelada') NOT NULL DEFAULT 'Pendiente',
  `precio_reserva` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id_reserva`),
  KEY `cod_usuario` (`cod_usuario`),
  KEY `cod_cancha` (`cod_cancha`),
  CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`cod_usuario`) REFERENCES `usuario` (`cod_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`cod_cancha`) REFERENCES `cancha` (`cod_cancha`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva`
--

LOCK TABLES `reserva` WRITE;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
INSERT INTO `reserva` VALUES (1,'CLI001','CAN001','2024-01-20','16:00:00','18:00:00','Confirmada',120.50),(2,'CLI001','CAN002','2024-01-21','10:00:00','11:30:00','Cancelada',80.00),(3,'CLI001','CAN003','2025-10-19','14:00:00','15:00:00','Confirmada',3.00),(4,'CLI002','CAN002','2025-11-10','10:00:00','11:30:00','Confirmada',35.50),(5,'CLI002','CAN001','2025-10-20','15:00:00','17:00:25','Pendiente',101.00),(6,'CLI002','CAN003','2025-10-21','15:00:00','17:00:00','Pendiente',80.00),(7,'CLI002','CAN003','2025-10-19','15:00:00','16:00:00','Pendiente',40.00);
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `cod_usuario` varchar(7) NOT NULL,
  `dni_usuario` int NOT NULL,
  `password_usuario` varchar(255) NOT NULL,
  `nombre_usuario` varchar(100) NOT NULL,
  `apellido_usuario` varchar(100) NOT NULL,
  `email_usuario` varchar(150) NOT NULL,
  `telefono_usuario` varchar(15) NOT NULL,
  `rol_usuario` enum('CLIENTE','ADMIN') NOT NULL,
  PRIMARY KEY (`cod_usuario`),
  UNIQUE KEY `dni_usuario` (`dni_usuario`),
  UNIQUE KEY `email_usuario` (`email_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('ADM001',12345678,'admin123','María','Gonzales López','maria.gonzales@sports.com','987654321','ADMIN'),('ADM002',73110487,'diego123','Diego','Flores','diego@gmail.com','933441406','ADMIN'),('ADM003',74455112,'juan123','Jaimito','Perez','juan@gmail.com','987456212','ADMIN'),('CLI001',87654321,'cliente123','Carlos','Rodríguez Pérez','carlos.rodriguez@email.com','912345678','CLIENTE'),('CLI002',74455885,'jeffer123','Jefferson','Paredes','jefferson@gmail.com','985541152','CLIENTE'),('CLI003',78899665,'luis123','Luis','Romo','luis@gmail.com','987456321','CLIENTE'),('CLI004',45566112,'45566112','Percy','Flores','percy@gmail.com','987544556','CLIENTE');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bdcancha_proyecto'
--

--
-- Dumping routines for database 'bdcancha_proyecto'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-19 14:39:31
