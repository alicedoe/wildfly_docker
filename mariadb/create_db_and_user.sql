DROP DATABASE IF EXISTS dbschema;
CREATE DATABASE IF NOT EXISTS dbschema;
GRANT ALL PRIVILEGES ON dbschema.* TO 'dbuser'@'%' IDENTIFIED BY 'dbpassword';
