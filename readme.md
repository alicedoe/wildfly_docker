# Docker full stack project : JavaEE / Hibernate / Wildfly / MariaDB

This my First personnal project using this kind of stack : the goal is to create an online application for a school to view homeworks that teachers put on it

## Getting Started

there is a bash script : go.sh look inside, it's pretty simple at the end you have 2 containers : MariaDB & Wildfly using eclipse you just have to deploy your project on wildfly container and it's online

## Screenshot

No screenshot for now

## History

* Mariadb container with a simple database and a user
* Create a wildfly container with mariadb drivers and connect it to the database already created
* Setup Rest Api and one endpoint : http://ipadresswildflycontainer:8080/application/hello/toto

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details