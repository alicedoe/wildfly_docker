-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: 172.17.0.2    Database: dbschema
-- ------------------------------------------------------
-- Server version	5.5.5-10.2.8-MariaDB-10.2.8+maria~jessie

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Action`
--

DROP TABLE IF EXISTS `Action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Action` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Action`
--

LOCK TABLES `Action` WRITE;
/*!40000 ALTER TABLE `Action` DISABLE KEYS */;
/*!40000 ALTER TABLE `Action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Homework`
--

DROP TABLE IF EXISTS `Homework`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Homework` (
  `id` bigint(20) NOT NULL,
  `creationDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `text` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  `kidsClass_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoy6t8ejyy2v1vde6dd97abhew` (`creator_id`),
  KEY `FKcwtstbly5x8dy56osl5yorvxk` (`kidsClass_id`),
  KEY `FKd9e4wevt26ljden56xl34blrl` (`subject_id`),
  CONSTRAINT `FKcwtstbly5x8dy56osl5yorvxk` FOREIGN KEY (`kidsClass_id`) REFERENCES `KidsClass` (`id`),
  CONSTRAINT `FKd9e4wevt26ljden56xl34blrl` FOREIGN KEY (`subject_id`) REFERENCES `Subject` (`id`),
  CONSTRAINT `FKoy6t8ejyy2v1vde6dd97abhew` FOREIGN KEY (`creator_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Homework`
--

LOCK TABLES `Homework` WRITE;
/*!40000 ALTER TABLE `Homework` DISABLE KEYS */;
/*!40000 ALTER TABLE `Homework` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Homework_Tag`
--

DROP TABLE IF EXISTS `Homework_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Homework_Tag` (
  `Homework_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  KEY `FK8nlpb58rokucv1ae24oidwer4` (`tag_id`),
  KEY `FKj3mxsenh1durlmsf5bdrateu2` (`Homework_id`),
  CONSTRAINT `FK8nlpb58rokucv1ae24oidwer4` FOREIGN KEY (`tag_id`) REFERENCES `Tag` (`id`),
  CONSTRAINT `FKj3mxsenh1durlmsf5bdrateu2` FOREIGN KEY (`Homework_id`) REFERENCES `Homework` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Homework_Tag`
--

LOCK TABLES `Homework_Tag` WRITE;
/*!40000 ALTER TABLE `Homework_Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `Homework_Tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KidsClass`
--

DROP TABLE IF EXISTS `KidsClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KidsClass` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `level_id` bigint(20) NOT NULL,
  `school_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKchpe9eugkblrmr98ukxcjnstc` (`level_id`),
  KEY `FKk0yjml6wvxlap20l3p0b5edmu` (`school_id`),
  CONSTRAINT `FKchpe9eugkblrmr98ukxcjnstc` FOREIGN KEY (`level_id`) REFERENCES `Level` (`id`),
  CONSTRAINT `FKk0yjml6wvxlap20l3p0b5edmu` FOREIGN KEY (`school_id`) REFERENCES `School` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KidsClass`
--

LOCK TABLES `KidsClass` WRITE;
/*!40000 ALTER TABLE `KidsClass` DISABLE KEYS */;
/*!40000 ALTER TABLE `KidsClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Level`
--

DROP TABLE IF EXISTS `Level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Level` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Level`
--

LOCK TABLES `Level` WRITE;
/*!40000 ALTER TABLE `Level` DISABLE KEYS */;
/*!40000 ALTER TABLE `Level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role_Action`
--

DROP TABLE IF EXISTS `Role_Action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Role_Action` (
  `Role_id` bigint(20) NOT NULL,
  `actions_id` bigint(20) NOT NULL,
  KEY `FKbq5i1lu4jrqew1vugk750avdb` (`actions_id`),
  KEY `FK717p0r4m9d5gt8ah6orxadbd1` (`Role_id`),
  CONSTRAINT `FK717p0r4m9d5gt8ah6orxadbd1` FOREIGN KEY (`Role_id`) REFERENCES `Role` (`id`),
  CONSTRAINT `FKbq5i1lu4jrqew1vugk750avdb` FOREIGN KEY (`actions_id`) REFERENCES `Action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role_Action`
--

LOCK TABLES `Role_Action` WRITE;
/*!40000 ALTER TABLE `Role_Action` DISABLE KEYS */;
/*!40000 ALTER TABLE `Role_Action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Action`
--

DROP TABLE IF EXISTS `SEQUENCE_Action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Action` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Action`
--

LOCK TABLES `SEQUENCE_Action` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Action` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Action` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Homework`
--

DROP TABLE IF EXISTS `SEQUENCE_Homework`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Homework` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Homework`
--

LOCK TABLES `SEQUENCE_Homework` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Homework` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Homework` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Homework` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Kidsclass`
--

DROP TABLE IF EXISTS `SEQUENCE_Kidsclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Kidsclass` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Kidsclass`
--

LOCK TABLES `SEQUENCE_Kidsclass` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Kidsclass` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Kidsclass` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Kidsclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Level`
--

DROP TABLE IF EXISTS `SEQUENCE_Level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Level` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Level`
--

LOCK TABLES `SEQUENCE_Level` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Level` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Level` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Role`
--

DROP TABLE IF EXISTS `SEQUENCE_Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Role` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Role`
--

LOCK TABLES `SEQUENCE_Role` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Role` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Role` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_School`
--

DROP TABLE IF EXISTS `SEQUENCE_School`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_School` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_School`
--

LOCK TABLES `SEQUENCE_School` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_School` DISABLE KEYS */;
INSERT INTO `SEQUENCE_School` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_School` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Subject`
--

DROP TABLE IF EXISTS `SEQUENCE_Subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Subject` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Subject`
--

LOCK TABLES `SEQUENCE_Subject` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Subject` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Subject` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Tag`
--

DROP TABLE IF EXISTS `SEQUENCE_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Tag` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Tag`
--

LOCK TABLES `SEQUENCE_Tag` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Tag` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Tag` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_User`
--

DROP TABLE IF EXISTS `SEQUENCE_User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_User` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_User`
--

LOCK TABLES `SEQUENCE_User` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_User` DISABLE KEYS */;
INSERT INTO `SEQUENCE_User` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Ville`
--

DROP TABLE IF EXISTS `SEQUENCE_Ville`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Ville` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Ville`
--

LOCK TABLES `SEQUENCE_Ville` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Ville` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Ville` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Ville` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `School`
--

DROP TABLE IF EXISTS `School`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `School` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `town_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmchcql760yl5q3igw858bnjgs` (`town_id`),
  CONSTRAINT `FKmchcql760yl5q3igw858bnjgs` FOREIGN KEY (`town_id`) REFERENCES `Town` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `School`
--

LOCK TABLES `School` WRITE;
/*!40000 ALTER TABLE `School` DISABLE KEYS */;
/*!40000 ALTER TABLE `School` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Setting`
--

DROP TABLE IF EXISTS `Setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Setting` (
  `id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `param` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Setting`
--

LOCK TABLES `Setting` WRITE;
/*!40000 ALTER TABLE `Setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `Setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subject`
--

DROP TABLE IF EXISTS `Subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subject` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subject`
--

LOCK TABLES `Subject` WRITE;
/*!40000 ALTER TABLE `Subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `Subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tag`
--

LOCK TABLES `Tag` WRITE;
/*!40000 ALTER TABLE `Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Town`
--

DROP TABLE IF EXISTS `Town`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Town` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Town`
--

LOCK TABLES `Town` WRITE;
/*!40000 ALTER TABLE `Town` DISABLE KEYS */;
/*!40000 ALTER TABLE `Town` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `firstname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pwd` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `kidsClass_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi7xhj7gjhdyrt7de63lgjnqtf` (`kidsClass_id`),
  KEY `FK84qlpfci484r1luck11eno6ec` (`role_id`),
  CONSTRAINT `FK84qlpfci484r1luck11eno6ec` FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`),
  CONSTRAINT `FKi7xhj7gjhdyrt7de63lgjnqtf` FOREIGN KEY (`kidsClass_id`) REFERENCES `KidsClass` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-31 22:09:03
