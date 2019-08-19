FROM java:8
COPY build/libs/ /var/http-server/
COPY www/ var/http-server/www/
WORKDIR /var/http-server/
EXPOSE 80
CMD ["java", "-jar", "HttpServer-1.0-SNAPSHOT.jar"]