package de.telekom.test.api;

import com.jayway.restassured.response.Response;
import de.telekom.test.interaction.ScenarioInteraction;
import de.telekom.test.steps.Steps;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static de.telekom.test.api.CompareUtils.compareAnyAttributeTypeWithString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

@Steps
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class ApiBaseSteps {

	private final @NonNull ScenarioInteraction scenarioInteraction;
	private final @NonNull RequestBuilder requestBuilder;

	@Given("path param $attribute $value")
	public void pathParamAttributeWithValue(String attribute, String value) {
		scenarioInteraction.mapPathParam().put(attribute, value);
	}

	@Given("query param $attribute $value")
	public void queryParamAttributeWithValue(String attribute, String value) {
		scenarioInteraction.mapQueryParam().put(attribute, value);
	}

	@Given("request body $attribute $value")
	public void requestBodyAttributeWithValue(String attribute, String value) {
		scenarioInteraction.mapBody().put(attribute, value);
	}

	@Then("the StatusCode is $code")
	public void thenStatusCodeShouldBe(Integer code) {
		requestBuilder.response().then().statusCode(code);
	}

	@Then("the response contains attribute $key with value $value")
	public void checkResponseAttributeEqualsValue(String key, String value) {
		Object responseAttribute = getResponseAttribute(key);
		compareAnyAttributeTypeWithString(responseAttribute, value);
	}

	@Then("the response is empty")
	public void responseIsEmpty() {
		String responseBody = requestBuilder.response().body().asString();
		responseBody = responseBody.replaceAll("[{}]", "");
		assertThat(responseBody, isEmptyString());
	}

	private <T> T getResponseAttribute(String key) {
		Response response = requestBuilder.response();
		if (response != null) {
			return response.jsonPath().get(key);
		}
		return null;
	}

}
