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
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Action`
--

LOCK TABLES `Action` WRITE;
/*!40000 ALTER TABLE `Action` DISABLE KEYS */;
INSERT INTO `Action` VALUES (1,'create role'),(2,'delete role'),(3,'update role'),(4,'read role'),(5,'create role'),(6,'delete role'),(7,'update role'),(8,'read role');
/*!40000 ALTER TABLE `Action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Classe`
--

DROP TABLE IF EXISTS `Classe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Classe` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `niveau_id` bigint(20) NOT NULL,
  `ville_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7vjp73nf3rh411tuaht5djkyt` (`niveau_id`),
  KEY `FK3kqx1sh57gf5crj7cv0ehdg98` (`ville_id`),
  CONSTRAINT `FK3kqx1sh57gf5crj7cv0ehdg98` FOREIGN KEY (`ville_id`) REFERENCES `Ville` (`id`),
  CONSTRAINT `FK7vjp73nf3rh411tuaht5djkyt` FOREIGN KEY (`niveau_id`) REFERENCES `Niveau` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Classe`
--

LOCK TABLES `Classe` WRITE;
/*!40000 ALTER TABLE `Classe` DISABLE KEYS */;
/*!40000 ALTER TABLE `Classe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Devoir`
--

DROP TABLE IF EXISTS `Devoir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Devoir` (
  `id` bigint(20) NOT NULL,
  `creation` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `text` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  `matiere_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8meyoobc8puolfj87f9rnqbfx` (`creator_id`),
  KEY `FKfob3m6gxu8mjy5mrhew6l507x` (`matiere_id`),
  CONSTRAINT `FK8meyoobc8puolfj87f9rnqbfx` FOREIGN KEY (`creator_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKfob3m6gxu8mjy5mrhew6l507x` FOREIGN KEY (`matiere_id`) REFERENCES `Matiere` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Devoir`
--

LOCK TABLES `Devoir` WRITE;
/*!40000 ALTER TABLE `Devoir` DISABLE KEYS */;
/*!40000 ALTER TABLE `Devoir` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Devoir_Tag`
--

DROP TABLE IF EXISTS `Devoir_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Devoir_Tag` (
  `Devoir_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  KEY `FKne8g8dwfs1mh5mvm4lk9vnpyp` (`tag_id`),
  KEY `FKdnp64s8dyua979662beh37vq5` (`Devoir_id`),
  CONSTRAINT `FKdnp64s8dyua979662beh37vq5` FOREIGN KEY (`Devoir_id`) REFERENCES `Devoir` (`id`),
  CONSTRAINT `FKne8g8dwfs1mh5mvm4lk9vnpyp` FOREIGN KEY (`tag_id`) REFERENCES `Tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Devoir_Tag`
--

LOCK TABLES `Devoir_Tag` WRITE;
/*!40000 ALTER TABLE `Devoir_Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `Devoir_Tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Homework`
--

DROP TABLE IF EXISTS `Homework`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Homework` (
  `id` bigint(20) NOT NULL,
  `creation` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `text` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  `matiere_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoy6t8ejyy2v1vde6dd97abhew` (`creator_id`),
  KEY `FKevdcle2hmdonrbrkiigjmc4f0` (`matiere_id`),
  CONSTRAINT `FKevdcle2hmdonrbrkiigjmc4f0` FOREIGN KEY (`matiere_id`) REFERENCES `Matiere` (`id`),
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
-- Table structure for table `Kidsclass`
--

DROP TABLE IF EXISTS `Kidsclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Kidsclass` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `level_id` bigint(20) NOT NULL,
  `school_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfsj2c4np63m7t398hjofq9fde` (`level_id`),
  KEY `FKfob986k1ew6j2f5sc8k2jf4bp` (`school_id`),
  CONSTRAINT `FKfob986k1ew6j2f5sc8k2jf4bp` FOREIGN KEY (`school_id`) REFERENCES `School` (`id`),
  CONSTRAINT `FKfsj2c4np63m7t398hjofq9fde` FOREIGN KEY (`level_id`) REFERENCES `Level` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Kidsclass`
--

LOCK TABLES `Kidsclass` WRITE;
/*!40000 ALTER TABLE `Kidsclass` DISABLE KEYS */;
/*!40000 ALTER TABLE `Kidsclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Level`
--

DROP TABLE IF EXISTS `Level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Level` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
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
-- Table structure for table `Matiere`
--

DROP TABLE IF EXISTS `Matiere`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Matiere` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Matiere`
--

LOCK TABLES `Matiere` WRITE;
/*!40000 ALTER TABLE `Matiere` DISABLE KEYS */;
/*!40000 ALTER TABLE `Matiere` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Niveau`
--

DROP TABLE IF EXISTS `Niveau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Niveau` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Niveau`
--

LOCK TABLES `Niveau` WRITE;
/*!40000 ALTER TABLE `Niveau` DISABLE KEYS */;
/*!40000 ALTER TABLE `Niveau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Role` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
INSERT INTO `Role` VALUES (1,'admin');
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
INSERT INTO `Role_Action` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `Role_Action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Classe`
--

DROP TABLE IF EXISTS `SEQUENCE_Classe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Classe` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Classe`
--

LOCK TABLES `SEQUENCE_Classe` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Classe` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Classe` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Classe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Devoir`
--

DROP TABLE IF EXISTS `SEQUENCE_Devoir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Devoir` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Devoir`
--

LOCK TABLES `SEQUENCE_Devoir` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Devoir` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Devoir` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Devoir` ENABLE KEYS */;
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
-- Table structure for table `SEQUENCE_Matiere`
--

DROP TABLE IF EXISTS `SEQUENCE_Matiere`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Matiere` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Matiere`
--

LOCK TABLES `SEQUENCE_Matiere` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Matiere` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Matiere` VALUES (1);
/*!40000 ALTER TABLE `SEQUENCE_Matiere` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE_Niveau`
--

DROP TABLE IF EXISTS `SEQUENCE_Niveau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_Niveau` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE_Niveau`
--

LOCK TABLES `SEQUENCE_Niveau` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_Niveau` DISABLE KEYS */;
INSERT INTO `SEQUENCE_Niveau` VALUES (1),(1);
/*!40000 ALTER TABLE `SEQUENCE_Niveau` ENABLE KEYS */;
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
INSERT INTO `SEQUENCE_Ville` VALUES (1),(1);
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
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
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
INSERT INTO `Setting` VALUES ('API_KEY','e7a8d311-8e16-441b-9f0a-e78b62cb34b5'),('TOKEN_EXP','150');
/*!40000 ALTER TABLE `Setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
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
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
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
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pwd` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `classe_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6h24hv5p4ahvbonkf1y1mtxgk` (`classe_id`),
  KEY `FK84qlpfci484r1luck11eno6ec` (`role_id`),
  CONSTRAINT `FK6h24hv5p4ahvbonkf1y1mtxgk` FOREIGN KEY (`classe_id`) REFERENCES `Kidsclass` (`id`),
  CONSTRAINT `FK84qlpfci484r1luck11eno6ec` FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'alice.gabbana@gmail.com','alice','gabbana','motdepasse','monsupertoken',NULL,1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ville`
--

DROP TABLE IF EXISTS `Ville`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ville` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ville`
--

LOCK TABLES `Ville` WRITE;
/*!40000 ALTER TABLE `Ville` DISABLE KEYS */;
/*!40000 ALTER TABLE `Ville` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-17 17:17:08
