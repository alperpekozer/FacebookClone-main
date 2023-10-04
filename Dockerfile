# İLK olarak işletim sistemi ve JDK eklenir
# FROM amazoncorretto:17   -- openjdl ile java jdk17 sürümü kullanılacaktır demektir.

FROM azul/zulu-openjdk-alpine:17.0.7
#COPY ConfigServerGit/build/libs/ConfigServerGit-v.1.0.jar app.jar
#COPY AuthService/build/libs/AuthService-v.1.0.jar app.jar
#COPY UserService/build/libs/UserService-v.1.0.jar app.jar
COPY ApiGatewayService/build/libs/ApiGatewayService-v.1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]