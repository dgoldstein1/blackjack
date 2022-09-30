# docker build . -t dgoldstein1/blackjack:0.0.1
FROM openjdk:11-jre-slim
COPY ./build/libs/blackjack-0.0.1-SNAPSHOT.jar /home/blackjack-0.0.1-SNAPSHOT.jar
RUN touch /home/application.properties
CMD ["java","-jar","/home/blackjack-0.0.1-SNAPSHOT.jar","--spring.config.location=file:///home/application.properties"]