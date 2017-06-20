# jbehave-web-app

This JBehave extension should make web application behaviour driven development much more easier. It integrates Selenium and REST assured into an spring lifecycle and help developing automated acceptance tests for frontends. 

## Features

- JBehave Steps are automatic loaded by Spring. You have just add the annotation @Steps
- Generic test data management integrated into the JBehave lifecycle by spring.
- Access the webdriver and selenium-page-objects by spring dependency injection.
- Integration of webdriver-manager for automatic webdriver updates into the JBehave lifecycle.
- Use an own extension of Selenium-WebElements if you want. The Extension include useful additional methods like scroll to element or element exist.
- Integration of REST assured which is also available by spring dependency injection.
- Creation of screenshots in case of errors and if desired after each step.

## Maven Integration

```xml
            <dependency>
                <groupId>de.telekom.jbehave</groupId>
                <artifactId>jbehave-web-app</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
```

