package de.telekom.test.bddwebapp.cucumber.extension;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;

public class PluginWebDriverReference {

    private PluginWebDriverReference(){
    }

    private static WebDriverWrapper webDriverWrapper;

    public static void setWebDriverWrapper(WebDriverWrapper webDriverWrapper) {
        PluginWebDriverReference.webDriverWrapper = webDriverWrapper;
    }

    public static WebDriverWrapper getWebDriverWrapper() {
        return webDriverWrapper;
    }
}
