INSERT INTO `Action` VALUES (1,'create role'),(2,'delete role'),(3,'update role'),(4,'read role');
INSERT INTO `Action` VALUES (5,'create role'),(6,'delete role'),(7,'update role'),(8,'read role');
INSERT INTO `Role` VALUES (1,'admin');
INSERT INTO `Role_Action` VALUES (1,1),(1,2),(1,3),(1,4);

INSERT INTO `User` VALUES (1,'alice.gabbana@gmail.com','alice','gabbana','motdepasse','monsupertoken',NULL,1);

INSERT INTO `Setting` VALUES ('API_KEY','e7a8d311-8e16-441b-9f0a-e78b62cb34b5'),('TOKEN_EXP','150');