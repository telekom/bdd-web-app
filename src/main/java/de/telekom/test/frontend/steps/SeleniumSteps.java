package de.telekom.test.frontend.steps;

import com.google.common.collect.Maps;
import de.telekom.test.frontend.pages.Page;
import de.telekom.test.frontend.selenium.WebDriverWrapper;
import de.telekom.test.interaction.ScenarioInteraction;
import de.telekom.test.interaction.StoryInteraction;
import de.telekom.test.steps.LifecyleSteps;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.openqa.selenium.support.PageFactory.initElements;

/**
 * Created by d.keiss on 18.08.2016.
 */
public abstract class SeleniumSteps {

	private static final String QUERY_PARAMS = "QUERY_PARAMS";

	@Autowired
	protected WebDriverWrapper webDriverWrapper;

	@Autowired
	protected StoryInteraction storyInteraction;

	@Autowired
	protected ScenarioInteraction scenarioInteraction;

	protected <T extends Page> T createExpectedPage(Class<T> expectedPage) {
		WebDriver driver = webDriverWrapper.getDriver();
		T page;
		try {
			page = expectedPage.getConstructor(WebDriver.class).newInstance(driver);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		initElements(driver, page);
		storyInteraction.remember(LifecyleSteps.CURRENT_PAGE, page);
		return page;
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
		for (String appender : appenders) {
			boolean alreadyAppended = false;
			if (url.endsWith("/") && appender.startsWith("/")) {
				url += StringUtils.substring(appender, 1);
				alreadyAppended = true;
			}
			if (!alreadyAppended) {
				if (url.endsWith("/") || appender.startsWith("/")) {
					url += appender;
				} else {
					url += "/" + appender;
				}
			}
		}
		return url;
	}

	public Map<String, String> mapQueryParam() {
		return getMapFromStoryInteraction();
	}

	public <T> Map<String, T> getMapFromStoryInteraction() {
		Object body = scenarioInteraction.recall(SeleniumSteps.QUERY_PARAMS);
		if (body == null) {
			scenarioInteraction.remember(SeleniumSteps.QUERY_PARAMS, Maps.newHashMap());
		}
		return (Map<String, T>) scenarioInteraction.recallNotNull(SeleniumSteps.QUERY_PARAMS);
	}

}
