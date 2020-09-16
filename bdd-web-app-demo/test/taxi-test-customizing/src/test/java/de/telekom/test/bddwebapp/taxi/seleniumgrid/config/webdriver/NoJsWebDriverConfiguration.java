package de.telekom.test.bddwebapp.taxi.seleniumgrid.config.webdriver;

import de.telekom.test.bddwebapp.frontend.lifecycle.UsefulWebDriverConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class NoJsWebDriverConfiguration extends UsefulWebDriverConfiguration {

    @Override
    public ChromeOptions chromeOptions(DesiredCapabilities capabilities) {
        log.info("Use alternative WebDriverConfiguration: noJsWebDriverConfiguration");

        ChromeOptions chromeOptions = super.chromeOptions(capabilities);
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_setting_values.javascript", 2);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        return chromeOptions;
    }

    @Override
    public DesiredCapabilities htmlUnitOptions(DesiredCapabilities capabilities) {
        log.info("Use alternative WebDriverConfiguration: noJsWebDriverConfiguration");

        DesiredCapabilities htmlUnitCapabilities = super.htmlUnitOptions(capabilities);
        htmlUnitCapabilities.setJavascriptEnabled(false);
        return htmlUnitCapabilities;
    }

}