FROM jboss/wildfly

# Switch to root to create directory users etc ...
USER root

# Copy all config files
RUN mkdir /start
ADD bin/ /start
ADD customization /opt/jboss/wildfly/customization/
ADD modules/ /opt/jboss/wildfly/modules

# Create user and give rights access to it
RUN chown -R jboss:jboss /start
RUN chown -R jboss:jboss /opt/jboss/wildfly/modules
RUN chown -R jboss:jboss /opt/jboss/wildfly/standalone
RUN chmod 775 /opt/jboss/wildfly/customization/execute.sh
RUN chmod 775 /start/entrypoint.sh

# Switch to Jboss User
USER jboss

# provide mount able volume path
VOLUME /data/logs

# Set values for environnement variables
ENV DB_HOST 172.17.0.2
ENV DB_PORT 3306
ENV DB_USER dbuser
ENV DB_PASSWORD dbpassword
ENV DB_SCHEMA dbschema

# Create a user to access admin panel of wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

# Setting up Wildfly connect to MariaDB database
RUN /opt/jboss/wildfly/customization/execute.sh standalone standalone.xml

# Use Docker environnement variables inside scripts
ENTRYPOINT ["/start/entrypoint.sh"]

RUN rm -rf /opt/jboss/wildfly/standalone/configuration/standalone_xml_history/current

# Bind adress to allowed access
# CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
# define pseudo command, which is recognized by entry point script
CMD ["jboss"]