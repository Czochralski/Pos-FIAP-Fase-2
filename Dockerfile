FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x ./mvnw && ./mvnw -B -DskipTests dependency:go-offline

COPY src/ src/

RUN ./mvnw -B -DskipTests clean package

FROM eclipse-temurin:21-jre AS extracted

WORKDIR /app

COPY --from=build /app/target/restaurantes-api-*.jar /app/application.jar

RUN java -Djarmode=tools -jar application.jar extract \
    --layers \
    --destination extracted

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=extracted --chown=10001:10001 /app/extracted/dependencies/ ./
COPY --from=extracted --chown=10001:10001 /app/extracted/spring-boot-loader/ ./
COPY --from=extracted --chown=10001:10001 /app/extracted/snapshot-dependencies/ ./
COPY --from=extracted --chown=10001:10001 /app/extracted/application/ ./

USER 10001:10001

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]
