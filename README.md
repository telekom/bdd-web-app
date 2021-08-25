# bdd-web-app

Use proven patterns for your behavior-driven test project. You can write the executable specifications in Cucumber and JBehave and benefit from years of web-application test automation experience.

The opinionated test framework "bdd-web-app" should solve the usual technical issues for web application regression tests.
It is based on Spring, Selenium, and Rest-assured. It can work with Cucumber or with JBehave and can be understood as a Plugin for these frameworks.
The framework will help you managing test data in the lifecycle. The browser instrumentalization driver will be updated automatically. And further, you can work with spring in your test project.

The included sample project allows a quick introduction to the test framework.

## Features

- Test data lifecycle.
- Access test data in Cucumber or JBehave with $. I.e., "Given account $myaccount".
- Automatic webdriver updates.
- Use Spring in your test project.
- Optional extension of Selenium-Web-Element.

## Cucumber maven dependency

If you want to use Cucumber for your web application test project, add this maven dependency. The dependency includes Cucumber, Spring, Selenium, Webdrivermanager, and other components.

```xml
            <dependency>
                <groupId>de.telekom.test</groupId>
                <artifactId>bddwebapp-cucumber</artifactId>
                <version>2.0.1</version>
            </dependency>
```

The framework is also available for other build-automation tools like Gradle. You will find the artifacts here: https://search.maven.org/artifact/de.telekom.test/bddwebapp-cucumber/2.0.1/jar.

## JBehave maven dependency

If you want to use JBehave for your web application test project, add this maven dependency. The dependency includes JBehave, Spring, Selenium, Webdrivermanager, and other components.

```xml
            <dependency>
                <groupId>de.telekom.test</groupId>
                <artifactId>bddwebapp-jbehave</artifactId>
                <version>2.0.1</version>
            </dependency>
```

The framework is also available for other build-automation tools like Gradle. You will find the artifacts here: https://search.maven.org/artifact/de.telekom.test/bddwebapp-jbehave/2.0.1/jar.

## Example project

In the bdd-web-app-demo folder, you will find the example web-application and the test-projects using bdd-web-app.

# Additional information

Additional information are here: https://github.com/telekom/bdd-web-app/wiki.