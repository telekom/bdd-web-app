FROM openjdk:8-jdk-alpine

# Copy smoke-tests.jar
COPY target/smoke-tests.jar /home/seluser

ENV reservation-service.url="http://testdata-sim:6000/testdata-sim/api"
ENV testdata-sim.url="http://testdata-sim:6000/testdata-sim"
ENV taxi-app.url="http://taxi-app:5000/taxi-app"

COPY target/smoke-tests.jar smoke-tests.jar
ENTRYPOINT ["java","-jar","/smoke-tests.jar"]

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