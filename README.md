Test!


To use the maven tomcat plugin, make sure you have the following configuration in place on your machine:

1. In the TOMCAT_HOME/conf/tomcat-users.xml file, add the following lines:

	<role rolename="manager-gui"/>
 	<role rolename="manager"/>
  	<role rolename="admin"/>
  	<user username="admin" password="admin" roles="admin,manager,manager-gui,manager-script"/>

2. In the MAVEN_HOME/conf/settings.xml , add the following lines:
	 <server>
                <id>LocalTomcat</id>
                <username>admin</username>
                <password>admin</password>
         </server>

3. Make sure you have the following lines in the pom.xml file:

	<plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>tomcat-maven-plugin</artifactId>
                <configuration>
                    <url>http://localhost:8080/manager/text</url>
                    <server>LocalTomcat</server>
                    <path>/RepositoryApi-1.0</path>
                    <username>admin</username>
                    <password>admin</password>
                </configuration>
            </plugin>	

To deploy the application for the first time, run tomcat:deploy. To redeploy the application after some modifications, run tomcat:redeploy.
