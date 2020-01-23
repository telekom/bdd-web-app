FROM openjdk:8-jdk-alpine

ENV taxi-app.url="http://taxi-app:5000/taxi-app"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bdd-web-app-demo-testdata-sim-1.6-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/bdd-web-app-demo-testdata-sim-1.6-SNAPSHOT.jar"]

USER 1000

# labels ##################################################################
ARG GIT_URL=https://github.com/telekom/bdd-web-app.git
LABEL de.telekom.test.bddwebapp.git.url=${GIT_URL}