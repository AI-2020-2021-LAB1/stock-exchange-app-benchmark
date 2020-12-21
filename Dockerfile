# build stage
FROM openjdk:14 as builder
WORKDIR application
COPY ./pom.xml ./pom.xml
COPY ./benchmark-backend/pom.xml ./benchmark-backend/pom.xml
COPY ./benchmark-algorithm/pom.xml ./benchmark-algorithm/pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY ./benchmark-backend/src ./benchmark-backend/src
COPY ./benchmark-algorithm/src ./benchmark-algorithm/src
RUN ["chmod", "+x", "mvnw"]
RUN ./mvnw clean package && cp benchmark-backend/target/benchmark-backend.jar stock-exchange-app-benchmark.jar

# production stage
FROM openjdk:14
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring
WORKDIR application
COPY --from=builder  application/stock-exchange-app-benchmark.jar stock-exchange-app-benchmark.jar
ENTRYPOINT ["java", "-jar", "stock-exchange-app-benchmark.jar"]