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
INSERT INTO `Classe` VALUES (1,'A',1,1),(2,'B',1,1),(3,'A',1,3),(4,'A',2,3),(5,'A',3,3),(6,'A',4,3);
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
INSERT INTO `Niveau` VALUES (1,'6eme'),(2,'5eme'),(3,'4eme'),(4,'3eme');
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
INSERT INTO `Role` VALUES (1,'prof'),(2,'eleve'),(3,'admin');
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
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
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `classe_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8nf9dlrddc0t4q7rwc2phdkng` (`classe_id`),
  KEY `FK84qlpfci484r1luck11eno6ec` (`role_id`),
  CONSTRAINT `FK84qlpfci484r1luck11eno6ec` FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`),
  CONSTRAINT `FK8nf9dlrddc0t4q7rwc2phdkng` FOREIGN KEY (`classe_id`) REFERENCES `Classe` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'Marcel','Jandreau',1,1),(2,'Janette','Chennault',2,1),(3,'Master','Admin',1,3);
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
INSERT INTO `Ville` VALUES (1,'Maraussan'),(2,'Toulouse'),(3,'Montpellier');
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
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1),(1),(1),(1),(1),(1);
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

-- Dump completed on 2018-03-11 10:30:16
