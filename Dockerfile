FROM openjdk:11-jre-slim AS base
ENV TZ=America/Bogota
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR /app
EXPOSE 5000

FROM maven:3.6.3-openjdk-11 AS build
WORKDIR /app
COPY . .

RUN mvn package
FROM base AS final
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
ARG STAGE
ENTRYPOINT ["java","-jar","./app.jar"]