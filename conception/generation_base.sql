-- MySQL dump 10.13  Distrib 8.0.22, for osx10.16 (x86_64)
--
-- Host: 127.0.0.1    Database: shareloc
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Colocation`
--

DROP TABLE IF EXISTS `Colocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Colocation` (
  `idColocation` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idColocation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Colocation`
--

LOCK TABLES `Colocation` WRITE;
/*!40000 ALTER TABLE `Colocation` DISABLE KEYS */;
INSERT INTO `Colocation` (`idColocation`, `name`) VALUES (1,'Coloc1'),(2,'Coloc2');
/*!40000 ALTER TABLE `Colocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Recipients`
--

DROP TABLE IF EXISTS `Recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Recipients` (
  `emailWorker` varchar(255) NOT NULL,
  `idTask` int NOT NULL,
  `emailRecipients` varchar(255) NOT NULL,
  PRIMARY KEY (`emailWorker`,`idTask`,`emailRecipients`),
  KEY `fk_Recipients_User1_idx` (`emailRecipients`),
  CONSTRAINT `fk_Recipients_Service1` FOREIGN KEY (`emailWorker`, `idTask`) REFERENCES `Service` (`email`, `idTask`),
  CONSTRAINT `fk_Recipients_User1` FOREIGN KEY (`emailRecipients`) REFERENCES `User` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Recipients`
--

LOCK TABLES `Recipients` WRITE;
/*!40000 ALTER TABLE `Recipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `Recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Service`
--

DROP TABLE IF EXISTS `Service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Service` (
  `email` varchar(255) NOT NULL,
  `idTask` int NOT NULL,
  `date` datetime NOT NULL,
  `image` blob,
  `isValide` tinyint DEFAULT NULL,
  PRIMARY KEY (`email`,`idTask`),
  KEY `fk_Service_Task1_idx` (`idTask`),
  CONSTRAINT `fk_Service_Task1` FOREIGN KEY (`idTask`) REFERENCES `Task` (`idTask`),
  CONSTRAINT `fk_Service_User1` FOREIGN KEY (`email`) REFERENCES `User` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Service`
--

LOCK TABLES `Service` WRITE;
/*!40000 ALTER TABLE `Service` DISABLE KEYS */;
/*!40000 ALTER TABLE `Service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Task`
--

DROP TABLE IF EXISTS `Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Task` (
  `idTask` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `cost` int DEFAULT NULL,
  `idColocation` int NOT NULL,
  PRIMARY KEY (`idTask`),
  KEY `fk_Task_Colocation1_idx` (`idColocation`),
  CONSTRAINT `fk_Task_Colocation1` FOREIGN KEY (`idColocation`) REFERENCES `Colocation` (`idColocation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Task`
--

LOCK TABLES `Task` WRITE;
/*!40000 ALTER TABLE `Task` DISABLE KEYS */;
INSERT INTO `Task` (`idTask`, `name`, `description`, `cost`, `idColocation`) VALUES (1,'Aspirateur','Passer l\'aspirateur',12,1),(2,'Faire la vaisselle','Faite la correctement !',8,1),(3,'Faire les courses','Avec de chips',20,1),(4,'Faire le ménage',NULL,20,2),(5,'Sortir le chien',NULL,6,2);
/*!40000 ALTER TABLE `Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` (`email`, `password`, `firstname`, `lastname`) VALUES ('edward.gi@gmail.com','non','Edward','Gi'),('jean.huges@gmail.com','non','Jean','Huges'),('marc.franc@gmail.com','non','Marc','Franc'),('nathanael.derousseaux@outlook.fr','non','Nathanaël','Derousseaux');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserColocation`
--

DROP TABLE IF EXISTS `UserColocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserColocation` (
  `isManager` tinyint NOT NULL,
  `email` varchar(255) NOT NULL,
  `idColocation` int NOT NULL,
  PRIMARY KEY (`email`,`idColocation`),
  KEY `fk_UserColocation_Colocation1_idx` (`idColocation`),
  CONSTRAINT `fk_UserColocation_Colocation1` FOREIGN KEY (`idColocation`) REFERENCES `Colocation` (`idColocation`),
  CONSTRAINT `fk_UserColocation_User` FOREIGN KEY (`email`) REFERENCES `User` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserColocation`
--

LOCK TABLES `UserColocation` WRITE;
/*!40000 ALTER TABLE `UserColocation` DISABLE KEYS */;
INSERT INTO `UserColocation` (`isManager`, `email`, `idColocation`) VALUES (0,'edward.gi@gmail.com',1),(0,'jean.huges@gmail.com',1),(0,'marc.franc@gmail.com',2),(1,'nathanael.derousseaux@outlook.fr',1),(0,'nathanael.derousseaux@outlook.fr',2);
/*!40000 ALTER TABLE `UserColocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VoteAdd`
--

DROP TABLE IF EXISTS `VoteAdd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `VoteAdd` (
  `email` varchar(255) NOT NULL,
  `idTask` int NOT NULL,
  `vote` tinyint NOT NULL,
  PRIMARY KEY (`email`,`idTask`),
  KEY `fk_VoteAdd_Task2_idx` (`idTask`),
  CONSTRAINT `fk_VoteAdd_Task2` FOREIGN KEY (`idTask`) REFERENCES `Task` (`idTask`),
  CONSTRAINT `fk_VoteAdd_User2` FOREIGN KEY (`email`) REFERENCES `User` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VoteAdd`
--

LOCK TABLES `VoteAdd` WRITE;
/*!40000 ALTER TABLE `VoteAdd` DISABLE KEYS */;
/*!40000 ALTER TABLE `VoteAdd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VoteDelete`
--

DROP TABLE IF EXISTS `VoteDelete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `VoteDelete` (
  `email` varchar(255) NOT NULL,
  `idTask` int NOT NULL,
  `vote` tinyint NOT NULL,
  PRIMARY KEY (`email`,`idTask`),
  KEY `fk_VoteAdd_Task1_idx` (`idTask`),
  CONSTRAINT `fk_VoteAdd_Task1` FOREIGN KEY (`idTask`) REFERENCES `Task` (`idTask`),
  CONSTRAINT `fk_VoteAdd_User1` FOREIGN KEY (`email`) REFERENCES `User` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VoteDelete`
--

LOCK TABLES `VoteDelete` WRITE;
/*!40000 ALTER TABLE `VoteDelete` DISABLE KEYS */;
/*!40000 ALTER TABLE `VoteDelete` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-08  9:48:20
