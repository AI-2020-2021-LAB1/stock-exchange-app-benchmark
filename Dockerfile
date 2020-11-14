# build stage
FROM openjdk:14 as builder
WORKDIR application
COPY ./pom.xml ./pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY ./src ./src
RUN ["chmod", "+x", "mvnw"]
RUN ./mvnw clean package && cp target/stock-exchange-app-benchmark.jar stock-exchange-app-benchmark.jar

# production stage
FROM openjdk:14
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring
WORKDIR application
COPY --from=builder  application/stock-exchange-app-benchmark.jar stock-exchange-app-benchmark.jar
ENTRYPOINT ["java", "-jar", "stock-exchange-app-benchmark.jar"]