# todo add xmx jvm memory limit
#FROM gradle:7.3.2-jdk-alpine as gradleimage
FROM 746460560240.dkr.ecr.ap-south-1.amazonaws.com/gradle:7.3.3 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build
RUN ls build/libs

EXPOSE 8081
EXPOSE 8080

#FROM openjdk:8-jdk-alpine
#testing
FROM public.ecr.aws/sam/build-java8:1.37.0
COPY --from=gradleimage /home/gradle/source/build/libs/catalog-service-v1-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","app.jar"]
