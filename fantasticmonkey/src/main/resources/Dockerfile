FROM openjdk:8
VOLUME /tmp
RUN echo 'Asia/Shanghai' > /etc/timezone
COPY *.jar /root/app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/root/app.jar"]