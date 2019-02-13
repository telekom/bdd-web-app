# bdd-web-app

This JBehave extension make automated acceptance tests for web applications much easier! It integrates Selenium for frontend testing and REST-assured for interface testing into a common test data lifecycle. The included sample project allows a quick introduction to the test framework.

## Features

- JBehave classes like stories or steps are automatic loaded by using annotations.
- Access Selenium and REST-Assured in Spring context for uniform access. 
- Enhanced test data management for web applications integrated into the JBehave lifecycle, so can easily use dynamic test data.
- Integration of WebDriver-manager for automatic WebDriver updates.
- Optional extension of Selenium-Web-Element with convenient functions and the possibility to add own extensions. 
- Creation of screenshots in case of errors and if desired after each step.

## Example project

At the bdd-web-app-demo folder you will find the example web-application, including the test-project using bdd-web-app. 
Here you will find an complete example of bdd-web-app integration. 

The next sections will describe the integration of the test framework in general.

## Maven Integration

Add this dependency to your test project. The dependency includes JBehave, Spring, Selenium, Webdrivermanager and other components for frontend testing:

```xml
            <dependency>
                <groupId>de.telekom.test</groupId>
                <artifactId>bdd-web-app</artifactId>
                <version>1.2-SNAPSHOT</version>
            </dependency>
```

## Configuration

You need four java classes for configuration.

First add the spring configuration. Note that the component scan at bdd-web-app package "de.telekom.jbehave.webapp" is needed.

```java
@Configuration
@ComponentScan({"de.telekom.test.bddwebapp", "your.package.structure"})
public class TestConfig {
}

```

At the second step, you need a bridge between jbehave and spring. Add an application context provider for this reason:

```java
public class ApplicationContextProvider {
    private static final ApplicationContext applicationContext = createApplicationContext();
    
    private static AnnotationConfigApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TestConfig.class);
        applicationContext.refresh();
        return applicationContext;
    }
    
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}

```

Now you can use the application context for story execution. Therefore, extend the *AbstractStory* class from the test-framework. 
Note that the story name has to be the same like your story class:

```java
public abstract class YourStory extends AbstractStory {
    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }
}
    
```

If you want to run all stories, add a class to your configuration that extend the *RunAllStories* class from the test-framework:

```java
public class RunAllMyStories extends RunAllStories {
    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }
}
```

You can run this class as a JUnit class.
If you want to integrate the test execution into your maven-lifecycle, then add this to your pom.xml:

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.1</version>
                <configuration>
                    <includes>
                        <include>**/RunAllMyStories.java</include>
                    </includes>
                </configuration>
            </plugin>
```

## Build your first test

To build your first test you need a story class with the application context (described at configuration). The class name must be the same like your jbehave story file. Put the story file to the resources and use the same package structure like the class.

Then build a steps class with your jbehave test steps. Use the *@Steps* annotation so that the bdd-web-app framework will find the class. If you want to use the class for selenium tests, you can extend the *SeleniumSteps* class from the test-framework:

```java
@Steps
public class YourSteps extends SeleniumSteps {
    @When("run step")
    public void runStep(){}
}
```

Map your web pages to page classes so that you can access these. Therefore, you can extend the *Page* class from the framework. If you use jquery you can use the *JQueryPage*.

```java
public class YourPage extends Page {
    @Override
    public String getURL() {
        return "relative/url";
    }
    @FindBy(id = "username")
    private WebElementEnhanced usernameInput;
}
```

Now you can access your page object with the *createExpectedPage* method from *SeleniumSteps*. 
The URL will be checked at page creation. If the url in the page class no contain in the current URL, an exception will throw. You can also use regular expressions at the page URL.
After the page object is created, you can use it directly or from the StoryInteraction by using the *getCurrentPage* method (recommended).
If you use the page by the *getCurrentPage* method, then you can access it from every step object. Note that if you call the *createExpectedPage* method, an new current page will be set.

```java
@Steps
public class YourSteps extends SeleniumSteps {
    @When("open page")
    public void openPage(){
        open("yourUrl.com/yourPage");
    }
    @Then("page is open")
    public void openPage(){
        createExpectedPage(YourPage.class);
    }
    @Then("page shows user name $userName")
    public void pageShowsUserName(String userName){
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.userNameIsShown(userName));   
    }
}
```

After you wrote your steps class, you can use it at your story. Note that your story file must be the same name as your story class for execution.

```story
When open page
Then page is open
And page shows user name hans
```

Run your story and look at the test report. If you miss the report styling call the "mvn site" command on your test project.

## Run on different browsers

The default browser is google chrome. 
If you want to use another browser set the system parameter: `browser=[BROWSER]`.

Supported browsers are: Firefox, Chrome, Edge, Internet Explorer (IE), Opera and Safari.
E.g. if you want to test with firefox you can use this VM-Parameter: `-Dbrowser=firefox`.

### Portable browser

You can use portable browsers by: `browser.path=[BROWSER_PATH]`.

Supported portable browsers are: Firefox, Chrome, and Opera.
E.g. if you want to use firefox portable you can use this VM-Parameters: ``` -Dbrowser=firefox -Dbrowser.path="[Base_path]\FirefoxPortable\App\Firefox\firefox.exe"```.

### Webdriver management

The instrumentation drivers will be updated automatically before running the stories.

In an intranet you will probably need a http proxy to update the drivers:
`webdriver.proxy.host=[PROXY_HOST]`
`webdriver.proxy.port=[PROXY_PORT]`

When you updating Firefox or Opera drivers you will often get the status code 403 from Github.
You can prevent this by setting a token. A description can be found here: https://github.com/bonigarcia/webdrivermanager.

## Reporting

The framework extends the reporting from JBehave by screenshots. The screenshots are linked in the JBehave Report and stored in a separate folder "target/jbehave/screenshots".

By default, screenshots are only taken in case of an error, but it can also be activated for all successful displayed pages.
You can active this by setting `screenshot.onsuccess=true`

## Test level

This feature is probably the most important for large projects with distributed systems.
During development, external systems usually need to be simulated. Only by this way, faults or unimplemented functions can be set in external system with reasonable effort.
In later development, all systems must be operated together. Here then an integrative test is necessary to ensure the perfect cooperation of the systems.

In distributed projects, test automation often results in duplicate work. 
The development teams test their software in simulated environments and a separate test team setup the integration tests, both with their own implementations.

This framework offers the possibility to reuse steps in integration test from development and overwrite if necessary.
For the integrative test then only test steps must be written that are absolutely necessary.
Examples can be found in the demo project "taxi-test-customizing"

### Setup

If you wan't to use it, you have to setup another resolvers for steps and stories first. 
Your AbstractStory need the test level step resolver.
```
public abstract class AbstractTestLevelStory extends AbstractStory {
    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }
    @Override
    public InjectableStepsFactory stepsFactory() {
        return testLevelStepsFactory(getTestLevel());
    }
}
```

Your RunAllStories class needs the test level step resolver and the test level story resolver.

```
public class RunAllTestlevel0TaxiStories extends RunAllStories {
    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }
    @Override
    public InjectableStepsFactory stepsFactory() {
        return testLevelStepsFactory(getTestLevel());
    }
    @Override
    public List<String> storyPaths() {
        return testLevelStoryPaths(getTestLevel());
    }
}
```

By default, the test level ist set by system property 'testLevel'. 
You can overwrite the getTestLevel() method or just set the test level directly in if you want.

### Story Configuration

Stories can be configured on which test levels they should run. If no test level is set, this is 0 by default.
Otherwise it must always be set explicitly on which test levels the story should run.

```
@TestLevel(testLevels = {0, 1})
public class LoginTestLevel0And1 extends AbstractTestLevelStory {
...
}
```

### Steps Configuration

If you do not specify a test level in steps, the default is 0. 
These steps can be used at any higher test level. If necessary, they can also be overwritten.

```
@Steps(testLevel = 1)
public class RegistrationStepsTestLevel extends RegistrationSteps {
...
}
```

Of course it is also possible to define test steps that require a minimum test level.

```
@Steps(testLevel = 1)
public class MinTestLevel1Step {
...
}
```

## Customizing stories

This section describes the customizing functions of bdd-web-app. 
Examples can be found in the demo project "taxi-test-customizing"

All default customizing can be configured in the same way. Either global, by type or by annotation.
When customizing annotations, please note that these are not inherited.

If you wan't you can write your own story customizing by using the 'customizingStories' and the 'currentStory' bean.
The customizing stories bean remember all stories that has to be executed.
The current story bean returns the current story being executed.
You can refer to this information about the spring context and then use JBehave annotations like @BeforeScenario for Customizing.

### Api only

If you have pure api stories that does not need any frontend you can disable the driver update and the browser start.
With this feature api only stories can run faster than stories with frontend interaction.
There are several ways to configure ApiOnly.

**Disable frontend interaction for all stories**
``` 
CustomizingStories customizingStories = applicationContext.getBean(CustomizingStories.class);
storyClasses.setApiOnlyForAllStories(true); 
```

**Disable frontend interaction for story base types**
``` 
CustomizingStories customizingStories = applicationContext.getBean(CustomizingStories.class);
customizingStories.setApiOnlyBaseType(YourAbstractApiOnlyStory.class);
```

**Disable frontend interaction for single story**
``` 
@ApiOnly
public class YourStory extends YourAbstractStory {
}
```

### Restart browser before scenario

By default, the browser is restarted before each story.
This should then delete all cookies so that the stories do not overlap there.

Within a story, you can write a step that restart the browser.
But if you want to restart always before a scenario, this can be also configured by bdd-web-app.
For that there are different possibilities.

**Restart browser before scenario for all stories**
``` 
CustomizingStories customizingStories = applicationContext.getBean(CustomizingStories.class);
storyClasses.setRestartBrowserBeforeScenarioForAllStories(true); 
```

**Restart browser before scenario for story base types**
``` 
CustomizingStories customizingStories = applicationContext.getBean(CustomizingStories.class);
customizingStories.setRestartBrowserBeforeScenarioBaseType(YourAbstractRestartBeforeEachScenarioStory.class);
```

**Restart browser before scenario for single story**
``` 
@RestartBrowserBeforeScenario
public class YourStory extends YourAbstractStory {
}
```
