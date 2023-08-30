package de.telekom.test.bddwebapp.frontend.steps;

import de.telekom.test.bddwebapp.api.steps.ApiSteps;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.frontend.page.Page;
import de.telekom.test.bddwebapp.interaction.InteractionParameterConverter;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static de.telekom.test.bddwebapp.frontend.util.UrlAppender.appendQueryParams;
import static de.telekom.test.bddwebapp.frontend.util.UrlAppender.appendUrl;
import static org.openqa.selenium.support.PageFactory.initElements;

/**
 * Abstract steps class for selenium test.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
public abstract class SeleniumSteps extends ApiSteps {

    /*
     * The current selenium page of story interaction. Is automatically deleted after a story.
     */
    public static final String CURRENT_PAGE = "CURRENT_PAGE";

    public static final String QUERY_PARAMS = "QUERY_PARAMS";

    @Autowired
    protected WebDriverWrapper webDriverWrapper;

    @Autowired
    protected StoryInteraction storyInteraction;

    @Autowired
    protected InteractionParameterConverter interactionParameterConverter;

    protected <T extends Page> T createExpectedPage(Class<T> expectedPage) {
        WebDriver driver = webDriverWrapper.getDriver();
        T page;
        try {
            Constructor<T> constructor = expectedPage.getConstructor(WebDriver.class);
            page = constructor.newInstance(driver);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        initElements(driver, page);
        page.checkPage();
        scenarioInteraction.remember(CURRENT_PAGE, page);
        storyInteraction.remember(CURRENT_PAGE, page);
        log.info("Created page object for class " + expectedPage);
        return page;
    }

    protected <T extends Page> T getCurrentPage() {
        T page = scenarioInteraction.recall(CURRENT_PAGE);
        if (page == null) {
            page = storyInteraction.recall(CURRENT_PAGE);
            String bddVariant = storyInteraction.recall(StoryInteraction.BDDWEBAPP_VARIANT);
            if(page != null && bddVariant != null && "cucumber".contains(bddVariant)){
                log.warn("Create the page {} in the current scenario and not in the scenario before to achieve independent scenarios!", page.getClass());
            }
        }
        return page;
    }

    protected String getUrlWithHost(String hostIncludingPort, String path) {
        return appendUrl(hostIncludingPort, path);
    }

    protected String getUrlWithHost(String hostIncludingPort, String contextPath, String path) {
        return appendUrl(hostIncludingPort, contextPath, path);
    }

    protected String getUrlWithHost(String hostIncludingPort, String contextPath, String path, Map<String, String> queryParams) {
        String url = appendUrl(hostIncludingPort, contextPath, path);
        return appendQueryParams(url, queryParams);
    }

    protected void open(String url) {
        WebDriver driver = webDriverWrapper.getDriver();
        driver.get(url);
    }

    protected Map<String, String> mapQueryParam() {
        return getMapFromStoryInteraction();
    }

    protected <T> Map<String, T> getMapFromStoryInteraction() {
        return scenarioInteraction.recallMapOrCreateNew(SeleniumSteps.QUERY_PARAMS);
    }

}
