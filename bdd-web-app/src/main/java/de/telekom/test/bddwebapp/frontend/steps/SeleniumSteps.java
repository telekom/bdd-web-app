package de.telekom.test.bddwebapp.frontend.steps;

import com.google.common.collect.Maps;
import de.telekom.test.bddwebapp.api.steps.ApiSteps;
import de.telekom.test.bddwebapp.frontend.element.decorator.WebElementDecorator;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.frontend.page.Page;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.interaction.steps.InteractionLifecycleSteps;
import de.telekom.test.bddwebapp.steps.StoryInteractionParameterConverter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.openqa.selenium.support.PageFactory.initElements;

/**
 * Abstract steps class for selenium test.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
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
    protected InteractionLifecycleSteps lifecycleSteps;

    @Autowired
    protected StoryInteractionParameterConverter storyInteractionParameterConverter;

    protected synchronized <T extends Page> T createExpectedPage(Class<T> expectedPage) {
        WebDriver driver = webDriverWrapper.getDriver();
        T page;
        try {
            Constructor<T> constructor = expectedPage.getConstructor(WebDriver.class);
            page = constructor.newInstance(driver);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        initElements(new WebElementDecorator(driver), page);
        page.checkPage();
        storyInteraction.remember(CURRENT_PAGE, page);
        return page;
    }

    protected <T extends Page> T getCurrentPage() {
        return storyInteraction.recall(CURRENT_PAGE);
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

    protected String appendQueryParams(String url, Map<String, String> queryParams) {
        if (queryParams != null && queryParams.size() > 0) {
            StringBuilder query = new StringBuilder();
            boolean isFirstparameter = true;
            for (String key : queryParams.keySet()) {
                if (isFirstparameter) {
                    isFirstparameter = false;
                } else {
                    query.append("&");
                }
                String value = queryParams.get(key);
                if (StringUtils.isEmpty(value)) {
                    query.append(key);
                } else {
                    query.append(key).append("=").append(value);
                }
            }
            url += "?" + query;
        }
        return url;
    }

    protected String appendUrl(String url, String... appenders) {
        StringBuilder urlBuilder = new StringBuilder(url);
        for (String appender : appenders) {
            boolean alreadyAppended = false;
            if (urlBuilder.toString().endsWith("/") && appender.startsWith("/")) {
                urlBuilder.append(StringUtils.substring(appender, 1));
                alreadyAppended = true;
            }
            if (!alreadyAppended) {
                if (urlBuilder.toString().endsWith("/") || appender.startsWith("/")) {
                    urlBuilder.append(appender);
                } else {
                    urlBuilder.append("/").append(appender);
                }
            }
        }
        url = urlBuilder.toString();
        return url;
    }

    protected Map<String, String> mapQueryParam() {
        return getMapFromStoryInteraction();
    }

    protected <T> Map<String, T> getMapFromStoryInteraction() {
        Object body = scenarioInteraction.recall(SeleniumSteps.QUERY_PARAMS);
        if (body == null) {
            scenarioInteraction.remember(SeleniumSteps.QUERY_PARAMS, Maps.newHashMap());
        }
        return (Map<String, T>) scenarioInteraction.recallNotNull(SeleniumSteps.QUERY_PARAMS);
    }

}
