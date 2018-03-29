INSERT INTO `Role` VALUES (1,'admin');

INSERT INTO `Action` VALUES (1,'create role'),(2,'delete role'),(3,'update role'),(4,'read role');
INSERT INTO `Role_Action` VALUES (1,1),(1,2),(1,3),(1,4);

INSERT INTO `Action` VALUES (5,'create user'),(6,'delete user'),(7,'update user'),(8,'read user');
INSERT INTO `Role_Action` VALUES (1,5),(1,6),(1,7),(1,8);

INSERT INTO `Action` VALUES (9,'create action'),(10,'delete action'),(11,'update action'),(12,'read action');
INSERT INTO `Role_Action` VALUES (1,9),(1,10),(1,11),(1,12);

INSERT INTO `Action` VALUES (13,'create level'),(14,'delete level'),(15,'update level'),(16,'read level');
INSERT INTO `Role_Action` VALUES (1,13),(1,14),(1,15),(1,16);

INSERT INTO `Action` VALUES (17,'create town'),(18,'delete town'),(19,'update town'),(20,'read town');
INSERT INTO `Role_Action` VALUES (1,17),(1,18),(1,19),(1,20);

INSERT INTO `Action` VALUES (21,'create tag'),(22,'delete tag'),(23,'update tag'),(24,'read tag');
INSERT INTO `Role_Action` VALUES (1,21),(1,22),(1,23),(1,24);

INSERT INTO `Action` VALUES (25,'create subject'),(26,'delete subject'),(27,'update subject'),(28,'read subject');
INSERT INTO `Role_Action` VALUES (1,25),(1,26),(1,27),(1,28);

INSERT INTO `User` VALUES (1,'alice.gabbana@gmail.com','alice','gabbana','motdepasse','monsupertoken',NULL,1);

INSERT INTO `Setting` VALUES ('API_KEY','e7a8d311-8e16-441b-9f0a-e78b62cb34b5'),('TOKEN_EXP','150');
