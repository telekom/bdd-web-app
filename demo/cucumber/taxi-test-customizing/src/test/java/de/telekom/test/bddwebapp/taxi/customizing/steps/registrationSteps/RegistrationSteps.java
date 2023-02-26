package de.telekom.test.bddwebapp.taxi.customizing.steps.registrationSteps;

import de.telekom.test.bddwebapp.taxi.steps.AbstractTaxiSteps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default || sim")
@Slf4j
public class RegistrationSteps extends AbstractTaxiSteps {

    public void registeredUser(String testobject) {
        createRegistration();
        storyInteraction.rememberObject(testobject, recallResponseAsMap());
    }

    public void createRegistration() {
        log.info("We call to sim!");
        testDataSimRequest().post("/testdata/user").then().statusCode(200);
    }

}
