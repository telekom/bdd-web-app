package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories;
import de.telekom.test.bddwebapp.taxi.customizing.config.webdriver.NoJsWebDriverConfiguration;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class WebDriverConfigurationSteps {

    @Autowired
    private WebDriverWrapper webDriverWrapper;

    @Before("@noJsWebDriverConfiguration")
    public void noJsWebDriverConfiguration() {
        webDriverWrapper.setAlternativeWebDriverConfiguration(NoJsWebDriverConfiguration.class);
    }

}
