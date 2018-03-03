#!/bin/bash

docker stop wildfly-cont mariadb-cont
docker rm wildfly-cont mariadb-cont
docker rmi wildfly-image
docker run --name mariadb-cont -e MYSQL_ROOT_PASSWORD=rootpassword -d mariadb-utf8:latest

echo "=> Wait for MariaDB to be ready ..."
sleep 10

mysql -uroot -prootpassword -h 172.17.0.2 < mariadb/create_db_and_user.sql
docker build --tag=wildfly-image wildfly/
docker run --name wildfly-cont -it wildfly-image