# Use Tomcat 10 with JDK 17
FROM tomcat:10.1-jdk17

# Remove default webapps to keep it clean (optional)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file to the Tomcat webapps directory
# Since we set finalName to iotplatform, the file is iotplatform.war
COPY target/iotplatform.war /usr/local/tomcat/webapps/iotplatform.war

# Expose the default HTTP port
EXPOSE 8080

# Default CMD is catalina.sh run
