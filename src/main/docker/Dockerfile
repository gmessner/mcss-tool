# Use the Alpine based Tomcat 8.5 image (it is the smallest)
FROM tomcat:8.5-alpine

# Copy the mcss-tool WAR file to the webapps directory. Tomcat will explode this when started.
COPY mcss-tool-*.war /usr/local/tomcat/webapps/mcss.war
