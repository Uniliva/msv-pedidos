FROM openjdk:8-jdk-slim
VOLUME /tmp
ADD target/msv-pedidos-0.0.1.jar msv-pedidos.jar
EXPOSE 80
RUN bash -c 'touch /msv-pedidos.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/msv-pedidos.jar"]