package de.telekom.test.bddwebapp.api

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction
import io.restassured.filter.FilterContext
import io.restassured.response.Response
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.FilterableResponseSpecification
import spock.lang.Specification

class RequestInteractionFilterTest extends Specification {

    def scenarioInteraction = Mock(ScenarioInteraction.class)

    def requestInteractionFilter = new RequestInteractionFilter(scenarioInteraction)

    def "filter"() {
        given:
        def requestSpec = Mock(FilterableRequestSpecification.class)
        def responseSpec = Mock(FilterableResponseSpecification.class)
        def ctx = Mock(FilterContext.class)
        ctx.next(requestSpec, responseSpec) >> Mock(Response.class)
        when:
        def response = requestInteractionFilter.filter(requestSpec, responseSpec, ctx)
        then:
        response != null
        1 * scenarioInteraction.remember("response", _)
    }

}
