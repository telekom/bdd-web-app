FROM openjdk:8-jdk-alpine

ENV reservation-service.url="http://testdata-sim:6000/testdata-sim/api"

COPY target/taxi-app.jar taxi-app.jar
ENTRYPOINT ["java","-jar","/taxi-app.jar"]

USER 1000

# labels ##################################################################
ARG CONTAINER_REGISTRY
ARG IMAGE_TAG
ARG GIT_URL
ARG GIT_BRANCH
ARG GIT_HASH
ARG BUILD_NO

LABEL de.telekom.test.bddwebapp.registry=${CONTAINER_REGISTRY} \
      de.telekom.test.bddwebapp.tag=${IMAGE_TAG} \
      de.telekom.test.bddwebapp.git.url=${GIT_URL} \
      de.telekom.test.bddwebapp.git.branch=${GIT_BRANCH} \
      de.telekom.test.bddwebapp.git.hash=${GIT_HASH} \
      de.telekom.test.bddwebapp.build.no=${BUILD_NO}