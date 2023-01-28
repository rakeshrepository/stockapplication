FROM alpine
RUN apk add openjdk11-jre
RUN wget -O /usr/local/bin/dumb-init https://github.com/Yelp/dumb-init/releases/download/v1.2.5/dumb-init_1.2.5_x86_64
RUN chmod +x /usr/local/bin/dumb-init
EXPOSE 8080
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} stockapplication.jar
CMD ["dumb-init", "java","-jar","/stockapplication.jar"]