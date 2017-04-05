package de.telekom.test.frontend.lifecycle;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeleniumTestRule implements MethodRule {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebDriverWrapper webDriverWrapper;

	@Override
	public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				startBrowser();
				statement.evaluate();
				stopBrowser();
			}

			private void startBrowser() {
				log.info("Start Testcase");
				try {
					if (webDriverWrapper.isClosed()) {
						webDriverWrapper.loadWebdriver();
					}
				} catch (Exception e) {
					log.warn("can not start the browser " + e.getMessage());
				}
			}

			private void stopBrowser() {
				log.info("Testcase finished");
				try {
					webDriverWrapper.quit();
				} catch (Exception e) {
					log.warn("can not stop the browser " + e.getMessage());
				}
			}
		};
	}

}
