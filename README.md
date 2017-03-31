# Maximal Contiguous Subsequent Sum (MCSS) Problem / Interactive Algorithm Tool

I originally wrote this web application back in 2010 for a job interview.
It allows allows the user to enter Java code and have it evaluated in real-time 
without compilation.  BeanShell is used to evaluate Java code in a Struts2 based action.

In addition, three common solutions may be loaded into the interactive Java 
code window, and the capability  for generating a populated int array that
can be used as input for the Java code to be evaluated.  This allows the user
to modify the Java code in real-time and test those modifications.

To use this web application, simply drop the mcss.war file into you web 
application server's deployment directory ((TOMCAT_HOME/webapps for Tomcat).

I've recently updated the project to use Gradle to build and to also build a Docker
image to run the webapp  in.  If you have Docker installed you can build the docker 
image by running Gradle with the dockerize task:

```bash
./gradlew dockerize
```

To run the Docker image:
```bash
 docker run --name 'mcss-tool' -d --rm -p 8080:8080 mcss-tool:1.0
 # Note: You may need to change the port if 8080 is already in use.
```

Once the Docker container is running you can launch the webapp at the following URL:

http://localhost:8080/mcss

**WARNING: SCRIPTS EXECUTED IN THIS ENVIRONMENT HAVE FULL ACCESS TO SYSTEM RESOURCES!
DO NOT USE THIS IN A PRODUCTION ENVIRONMENT!**

