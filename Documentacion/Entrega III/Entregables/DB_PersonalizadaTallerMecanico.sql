-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: tallermecanico
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'GRIBAUDO',43883934,'GENERAL DEHEZA','FACUNDO','1',3534297983,'2025-02-18'),(2,'SANCHEZ',43558745,'HERNANDO','FACUNDO','1',3535845712,'2024-01-31'),(3,'BOTTA',42587459,'VILLA MARIA','JOAQUIN','1',3535842699,'2024-07-18'),(4,'CARNINO',43574126,'GENERAL DEHEZA','MARTIN','1',3584659803,'2025-07-07'),(5,'GIMENEZ',43652459,'ONCATIVO','TOMAS','1',3534780234,'2025-08-22'),(6,'FERREYRA',43556241,'HERNANDO','ENZO','1',3535962407,'2026-10-18');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `marca`
--

LOCK TABLES `marca` WRITE;
/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
INSERT INTO `marca` VALUES (1,'Activo','FORD'),(2,'Activo','AUDI'),(3,'Inactivo','BMW'),(4,'Activo','HONDA'),(5,'Inactivo','PEUGEOT');
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `marcas_eliminadas`
--

LOCK TABLES `marcas_eliminadas` WRITE;
/*!40000 ALTER TABLE `marcas_eliminadas` DISABLE KEYS */;
/*!40000 ALTER TABLE `marcas_eliminadas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `modelo`
--

LOCK TABLES `modelo` WRITE;
/*!40000 ALTER TABLE `modelo` DISABLE KEYS */;
INSERT INTO `modelo` VALUES (1,'Activo','A5',2),(2,'Activo','150CC',4),(3,'Activo','308',5),(4,'Inactivo','M4',3),(5,'Activo','FIESTA ',1);
/*!40000 ALTER TABLE `modelo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orden_trabajo`
--

LOCK TABLES `orden_trabajo` WRITE;
/*!40000 ALTER TABLE `orden_trabajo` DISABLE KEYS */;
INSERT INTO `orden_trabajo` VALUES (1,'EN ESPERA','2023-12-18 20:36:46.849789','2023-11-09 20:36:00.000000',NULL,0.00,0.00,0.00,1,NULL,NULL),(2,'EN ESPERA','2023-12-18 20:37:01.726925','2023-12-18 20:36:00.000000',NULL,0.00,0.00,0.00,3,NULL,NULL),(3,'EN ESPERA','2023-12-18 20:37:35.163786','2023-10-10 20:37:00.000000',NULL,0.00,0.00,0.00,2,NULL,NULL),(4,'EN ESPERA','2023-12-18 20:38:03.318763','2023-07-18 20:37:00.000000',NULL,0.00,0.00,0.00,3,NULL,NULL);
/*!40000 ALTER TABLE `orden_trabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orden_trabajo_horas`
--

LOCK TABLES `orden_trabajo_horas` WRITE;
/*!40000 ALTER TABLE `orden_trabajo_horas` DISABLE KEYS */;
INSERT INTO `orden_trabajo_horas` VALUES (1,4,2),(1,2,3),(2,1,1),(2,3,4),(3,2,3),(4,2,3);
/*!40000 ALTER TABLE `orden_trabajo_horas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orden_trabajo_minutos`
--

LOCK TABLES `orden_trabajo_minutos` WRITE;
/*!40000 ALTER TABLE `orden_trabajo_minutos` DISABLE KEYS */;
INSERT INTO `orden_trabajo_minutos` VALUES (1,0,2),(1,30,3),(2,30,1),(2,0,4),(3,30,3),(4,30,3);
/*!40000 ALTER TABLE `orden_trabajo_minutos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orden_trabajo_servicio`
--

LOCK TABLES `orden_trabajo_servicio` WRITE;
/*!40000 ALTER TABLE `orden_trabajo_servicio` DISABLE KEYS */;
INSERT INTO `orden_trabajo_servicio` VALUES (1,2),(1,3),(2,1),(2,4),(3,3),(4,3);
/*!40000 ALTER TABLE `orden_trabajo_servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orden_trabajo_tecnico`
--

LOCK TABLES `orden_trabajo_tecnico` WRITE;
/*!40000 ALTER TABLE `orden_trabajo_tecnico` DISABLE KEYS */;
INSERT INTO `orden_trabajo_tecnico` VALUES (1,2),(2,1),(3,3),(4,1);
/*!40000 ALTER TABLE `orden_trabajo_tecnico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `servicios_taller`
--

LOCK TABLES `servicios_taller` WRITE;
/*!40000 ALTER TABLE `servicios_taller` DISABLE KEYS */;
INSERT INTO `servicios_taller` VALUES (1,'CAMBIO DE ACEITE PARA EL VEHICULO',1,30,'CAMBIO ACEITE',0.00,0.00,100.00),(2,'REVISION COMPLETA DE VEHICULO',4,0,'SERVICE COMPLETO ',0.00,0.00,350.00),(3,'LAVADO DE VEHICULO',2,30,'LIMPIEZA DE VEHICULO',0.00,0.00,200.00),(4,'TRATADO PARA LOS PLASTICOS DE LOS VEHICULOS ',3,0,'TRATADO EXTERIORES',0.00,0.00,500.00);
/*!40000 ALTER TABLE `servicios_taller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tecnico`
--

LOCK TABLES `tecnico` WRITE;
/*!40000 ALTER TABLE `tecnico` DISABLE KEYS */;
INSERT INTO `tecnico` VALUES (1,'PEREZ','14239','JUAN',3534169804),(2,'GALLARDO','13892','EMANUEL',3535185222),(3,'MARTINEZ','15482','CARLOS',3584123580);
/*!40000 ALTER TABLE `tecnico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `vehiculo`
--

LOCK TABLES `vehiculo` WRITE;
/*!40000 ALTER TABLE `vehiculo` DISABLE KEYS */;
INSERT INTO `vehiculo` VALUES (1,2021,1000,'A181NHM',1,4,2),(2,2010,15000,'IZH579',2,1,5),(3,2006,23500,'GGG458',3,2,1);
/*!40000 ALTER TABLE `vehiculo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-18 20:40:22
