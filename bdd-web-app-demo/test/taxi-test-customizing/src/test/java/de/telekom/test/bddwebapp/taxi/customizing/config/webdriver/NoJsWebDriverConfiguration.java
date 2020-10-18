package de.telekom.test.bddwebapp.taxi.customizing.config.webdriver;

import de.telekom.test.bddwebapp.frontend.lifecycle.UsefulWebDriverConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import java.util.HashMap;

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
    public ChromeOptions chromeOptions(DesiredCapabilities capabilities) {
        log.info("Use alternative WebDriverConfiguration: noJsWebDriverConfiguration");

        var options = super.chromeOptions(capabilities);
        var chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_setting_values.javascript", 2);
        options.setExperimentalOption("prefs", chromePrefs);
        return options;
    }

    @Override
    public FirefoxOptions firefoxOptions(DesiredCapabilities capabilities) {
        log.info("Use alternative WebDriverConfiguration: noJsWebDriverConfiguration");

        var options = super.firefoxOptions(capabilities);
        options.addPreference("javascript.enabled", false);
        return options;
    }

    @Override
    public DesiredCapabilities htmlUnitOptions(DesiredCapabilities capabilities) {
        log.info("Use alternative WebDriverConfiguration: noJsWebDriverConfiguration");

        var desiredCapabilities = super.htmlUnitOptions(capabilities);
        desiredCapabilities.setJavascriptEnabled(false);
        return desiredCapabilities;
    }

}