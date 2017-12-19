FROM openjdk:8
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
CMD ["java", "-Djava.security.egd=file:/dev/./urandom","-Dserver.port=8080","-jar","/usr/src/myapp/build/libs/gs-rest-service-0.1.0.jar"]
EXPOSE 8080
