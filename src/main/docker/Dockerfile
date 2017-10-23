FROM openjdk:8u131-jre-alpine

VOLUME /tmp
ADD spring-caching-sample-1.0-SNAPSHOT.jar /home/spring-caching-sample.jar
RUN apk --update add curl

EXPOSE 7745

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /home/spring-caching-sample.jar"]
