package de.telekom.test.bddwebapp.taxi.customizing.steps.registrationSteps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@Slf4j
public class ProdRegistrationSteps extends RegistrationSteps {

    @Override
    public void createRegistration() {
        log.info("We call to prod!");
        // if we had a production environment then this call would look different
        // we would have authorization upfront
        testDataSimRequest().post("/testdata/user").then().statusCode(200);
    }

}
