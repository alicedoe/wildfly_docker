#!/bin/bash

echo "=> Stop wildfly & mariadb container and delete them + delete images ..."
docker stop wildfly-cont mariadb-cont
docker rm wildfly-cont mariadb-cont
docker rmi wildfly-image

echo "=> Remove mariadb volume directory and recreate it and set rights ..."
if [ -d /opt/mariadb ]; then
    rm -rf /opt/mariadb
fi
mkdir /opt/mariadb
chown -R aliiiiiiiiii:aliiiiiiiiii /opt/mariadb

echo "=> Run MariaDB container and create volume ..."
docker run --name mariadb-cont -v /opt/mariadb:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=rootpassword -d mariadb-utf8:latest

echo "=> Wait for MariaDB to be ready ..."
sleep 10

mysql -uroot -prootpassword -h 172.17.0.2 < mariadb/create_db_and_user.sql
docker build --tag=wildfly-image wildfly/
docker run --name wildfly-cont -it wildfly-image