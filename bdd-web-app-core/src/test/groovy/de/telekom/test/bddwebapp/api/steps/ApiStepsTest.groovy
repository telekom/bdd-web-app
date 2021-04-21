package de.telekom.test.bddwebapp.api.steps

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.response.ResponseBody
import io.restassured.specification.RequestSpecification
import spock.lang.Specification

class ApiStepsTest extends Specification {

    def apiSteps = new ApiSteps() {
    }

    void setup() {
        apiSteps.scenarioInteraction = Mock(ScenarioInteraction)
    }

    def "clear request"() {
        when:
        apiSteps.clearRequest()
        then:
        1 * apiSteps.scenarioInteraction.clear("request")
        1 * apiSteps.scenarioInteraction.clear("response")
    }

    def "recall request"() {
        given:
        apiSteps.scenarioInteraction.recallNotNull("request") >> Mock(RequestSpecification)
        when:
        def request = apiSteps.recallRequest()
        then:
        request != null
    }

    def "remember request"() {
        when:
        apiSteps.rememberRequest(Mock(RequestSpecification))
        then:
        apiSteps.scenarioInteraction.remember("request", _ as RequestSpecification)
    }

    def "recall response"() {
        given:
        apiSteps.scenarioInteraction.recallNotNull("response") >> Mock(Response)
        when:
        def response = apiSteps.recallResponse()
        then:
        response != null
    }

    def "recall response as map"() {
        given:
        def response = Mock(Response)
        response.getBody() >> Mock(ResponseBody)
        response.getBody().as(Map) >> ["key": "value"]
        apiSteps.scenarioInteraction.recallNotNull("response") >> response
        when:
        def responseAsMap = apiSteps.recallResponseAsMap()
        then:
        responseAsMap == ["key": "value"]
    }

    def "create request"() {
        when:
        apiSteps.createRequest()
        then:
        apiSteps.scenarioInteraction.remember("request", _ as RequestSpecification)
    }

    def "create request with base uri and proxy"() {
        given:
        def request = Mock(RequestSpecification)
        apiSteps.scenarioInteraction.recallNotNull("request") >> request
        request.baseUri(_) >> request
        when:
        apiSteps.createRequestWithBaseUriAndProxy("http://localhost", "test", "http://localhost", "1234")
        then:
        1 * apiSteps.scenarioInteraction.remember("request", _ as RequestSpecification)
        1 * request.basePath('test')
        1 * request.proxy('http://localhost', 1234)
        1 * request.port(80)
    }

    def "create request with json config"() {
        given:
        def request = Mock(RequestSpecification)
        apiSteps.scenarioInteraction.recallNotNull("request") >> request
        request.baseUri(_) >> request
        when:
        apiSteps.createRequestWithJsonConfig("http://localhost", "test", "http://localhost", "1234")
        then:
        1 * apiSteps.scenarioInteraction.remember("request", _ as RequestSpecification)
        1 * request.basePath('test')
        1 * request.proxy('http://localhost', 1234)
        1 * request.port(80)
        1 * request.config(_)
        1 * request.header("Accept", ContentType.JSON.toString())
        1 * request.header("Content-Type", ContentType.JSON.toString())
    }

}

