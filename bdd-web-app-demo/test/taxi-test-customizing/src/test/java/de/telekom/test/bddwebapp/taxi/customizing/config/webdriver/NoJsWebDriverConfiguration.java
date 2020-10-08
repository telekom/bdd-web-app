package de.telekom.test.bddwebapp.taxi.customizing.config.webdriver;

import de.telekom.test.bddwebapp.frontend.lifecycle.UsefulWebDriverConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NoJsWebDriverConfiguration extends UsefulWebDriverConfiguration {

    @Override
    public DesiredCapabilities extraCapabilities(String browser) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
        return desiredCapabilities;
    }

    @Override
    public DesiredCapabilities htmlUnitOptions(DesiredCapabilities capabilities) {
        log.info("Use alternative WebDriverConfiguration: noJsWebDriverConfiguration");

        var htmlUnitCapabilities = super.htmlUnitOptions(capabilities);
        htmlUnitCapabilities.setJavascriptEnabled(false);
        return htmlUnitCapabilities;
    }

}