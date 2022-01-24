package de.telekom.test.bddwebapp.taxi.customizing.config.webdriver;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class NoJsWebDriverConfiguration implements WebDriverConfiguration {

    @Override
    public WebDriver loadChrome(DesiredCapabilities capabilities) {
        log.info("Use alternative WebDriverConfiguration: noJsWebDriverConfiguration");
        ChromeOptions chromeOptions = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_setting_values.javascript", 2);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.merge(capabilities);
        String browserPath = this.getBrowserPath();
        if (isNotBlank(browserPath)) {
            log.info("Load portable chrome instance from '{}'", browserPath);
            chromeOptions.setBinary(browserPath);
        }
        return new ChromeDriver(chromeOptions);
    }

}
