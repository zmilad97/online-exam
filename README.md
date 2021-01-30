## Online Exam API

## Running online-exam localy
online-exam is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:


```
git clone https://github.com/zmilad97/online-exam.git
cd online-exam
./mvnw package
java -jar target/*.jar
```

You can then access online-exam here: http://localhost:9090/



Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```

## In case you find a bug/suggested improvement for Online-Exam
Our issue tracker is available here: https://github.com/zmilad97/online-exam/issues


## Database configuration

In its default configuration, online-exam uses a MySql database which you must
create on your own in database .Make a get call to /user/init to initiate Admin user for the first time
You could start MySql locally with whatever installer works for your OS




