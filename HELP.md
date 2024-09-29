# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-mongodb)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-security)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)


# How to Run Programe
java -Dspring.profiles.active=prod -jar videoApp-1.0.jar  

# Running on PROD Linux Terminal
nohup java -Dspring.profiles.active=prod -jar videoApp-1.0.jar

# Running on DEV Linux Terminal
nohup java -Dspring.profiles.active=devLinux -jar videoApp-1.0.jar


-- Requried Library Installation
-- FFMPEG Installation on Ubunti
sudo apt update
sudo apt install ffmpeg

-- YouTube Downloader Installation  (yt-dlp)

sudo curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -o /usr/bin/yt-dlp
sudo chmod a+rx /usr/bin/yt-dlp

-- WebSite : https://github.com/yt-dlp/yt-dlp



# Close Existing Running App on Linux

1) Search PID of Running Java APP

ps -elf |grep java

2) Kill App by PID
kill -9 138053
