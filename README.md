# Java Components 
[![Build Status](https://travis-ci.org/baptistelebail/java-components.svg?branch=master)](https://travis-ci.org/baptistelebail/java-components)
[![Dependencies Status](https://www.versioneye.com/user/projects/5899d3661e07ae0048c8e4c9/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5899d3661e07ae0048c8e4c9)

Provides commons java components with Spring Boot.

## Technical stack
* Java : [Java 9](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)
* Build : [Maven](https://maven.apache.org/)
* Testing : [JUnit 4](http://junit.org)
* Data access : [Querydsl 4](http://www.querydsl.com/), [MySQL Dialect](https://www.mysql.com/)
* Caching : [Caffeine 2.3](https://github.com/ben-manes/caffeine)
* Web tier : [Spring Boot 2](https://projects.spring.io/spring-boot/)
* Templating : [Thymeleaf 3](http://www.thymeleaf.org/)

## Getting started
### Repository
```xml
<repositories>
    <repository>
        <id>daeliin-repository</id>
        <url>http://daeliin.com/repository/</url>
    </repository>   
</repositories>
```
### Maven depencencies
```xml
<dependency>
    <groupId>com.daeliin.components</groupId>
    <artifactId>components-core</artifactId>
    <version>0.2-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.daeliin.components</groupId>
    <artifactId>components-persistence</artifactId>
    <version>0.2-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.daeliin.components</groupId>
    <artifactId>components-security</artifactId>
    <version>0.2-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.daeliin.components</groupId>
    <artifactId>components-cms</artifactId>
    <version>0.2-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.daeliin.components</groupId>
    <artifactId>components-webservices</artifactId>
    <version>0.2-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.daeliin.components</groupId>
    <artifactId>components-thirdparty</artifactId>
    <version>0.2-SNAPSHOT</version>
</dependency>
```

## Documentation
* [GitHub Wiki](https://github.com/baptistelebail/java-components/wiki)
