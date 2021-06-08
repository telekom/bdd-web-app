# bdd-web-app

BDD-Web-App is a lean test framework which solves the usual technical issues for web application integration tests.
The included sample project allows a quick introduction to the test framework.

## Features

- Integrating Selenium and REST-assured into test lifecycle.
- Access everything in Spring. 
- Reuse test data in test lifecycle with a common pattern.
- Automatic WebDriver updates for Selenium.
- Optional extension of Selenium-Web-Element. 

## Maven Integration Cucumber

Add this dependency to your test project. The dependency includes Cucumber, Spring, Selenium, Webdrivermanager and other components for frontend testing:

```xml
            <dependency>
                <groupId>de.telekom.test</groupId>
                <artifactId>bddwebapp-cucumber</artifactId>
                <version>2.1-SNAPSHOT</version>
            </dependency>
```

BDD-Web-App for Cucumber is also available for other build-automation-tools like gradle. You will find the artefacts here: https://search.maven.org/artifact/de.telekom.test/bddwebapp-cucumber/2.1-SNAPSHOT/jar.

## Maven Integration JBehave

Add this dependency to your test project. The dependency includes JBehave, Spring, Selenium, Webdrivermanager and other components for frontend testing:

```xml
            <dependency>
                <groupId>de.telekom.test</groupId>
                <artifactId>bddwebapp-jbehave</artifactId>
                <version>2.1-SNAPSHOT</version>
            </dependency>
```

BDD-Web-App for JBehave is also available for other build-automation-tools like gradle. You will find the artefacts here: https://search.maven.org/artifact/de.telekom.test/bddwebapp-jbehave/2.1-SNAPSHOT/jar.

## Example project

In the bdd-web-app-demo folder you find the example web-application and the test-projects using bdd-web-app.

# Additional information

Additional information are here: https://github.com/telekom/bdd-web-app/wiki.