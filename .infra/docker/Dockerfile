FROM java:8
COPY build/libs/ /var/http-server/
COPY www/ var/www/
WORKDIR /var/http-server/
CMD ["java", "-jar", "HttpServer-1.0-SNAPSHOT.jar"]