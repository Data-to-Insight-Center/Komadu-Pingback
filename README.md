# Komadu Pingback Model

Provenance Pingback Tool

Komadu Pingback Model makes an attempt to build a PROV pingback model for Komadu Provenance system to connect existing provenance system with interconnected web of provenance and provide a technical model to involve downstream parties in the publication process of provenance and record forward provenance after the resource left originators’ purview. 

Following is a quick installation guide for Komadu Pingback Model. Basic Use Case Toturial can be found in 
https://github.com/Gabriel-Zhou/Komadu-Pingback/wiki/Basic-Use-Case:-Revision-of-Dummy-File

#Installation Guide

## Software Dependencies

1. Apache Maven 3.0 or higher
2. JDK 1.6 or higher
3. Apache XML Beans 2.3.0
4. Apache Tomcat 6.0.x or higher
5. Komadu Provenance Collection Tool V1.0 and Deploy as Web Service
(Available at: https://github.com/Data-to-Insight-Center/komadu)

##Building the Source
1. Check Out Komadu Pingback Model code from git repository:
```
git clone https://github.com/Gabriel-Zhou/Komadu-Pingback.git
```

2. Edit the config.properties file found under src/main/java/edu/indiana/d2i/komadu/pingback/server and set your host name, port number for Tomcat Service and data root path to store all transaction data.

3. Build Komadu Pingback Model
```
mvn clean compile war:war
```

##Set up Tomcat and deploy Komadu Pingback Web Service
1. Copy {Komadu-Pingback_checkout_path}/target/Komadu-Pingback-Server-1.0-SNAPSHOT.war to 
{your_tomcat_home}/webapps/ directory.
2. Start Tomcat.

##Executing Basic Use Case

Once you have successfully deployed Komadu Pingback Model on Tomcat, you can execute the basic use case with guide available at:
```
https://github.com/Gabriel-Zhou/Komadu-Pingback/wiki/Basic-Use-Case:-Revision-of-Dummy-File
```








