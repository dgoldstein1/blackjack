# docker build . -t dgoldstein1/blackjack:0.0.1
FROM openjdk:11-jre-slim
COPY ./build/libs/blackjack-0.0.1-SNAPSHOT.jar /home/blackjack-0.0.1-SNAPSHOT.jar
COPY start_docker.sh /home

ENV MONGO_HOST "localhost"

CMD ["/home/start_docker.sh"]
