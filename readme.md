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

At the jbehave-web-app-demo folder you will find the example web-application, including the test-project using jbehave-web-app. 
Here you will find an complete example of jbehave-web-app integration. 

The next sections will describe the integration of the test framework in general.

## Maven Integration

Add this dependency to your test project. The dependency includes JBehave, Spring, Selenium, Webdrivermanager and other components for frontend testing:

```xml
            <dependency>
                <groupId>de.telekom.jbehave</groupId>
                <artifactId>jbehave-web-app</artifactId>
                <version>1.2-SNAPSHOT</version>
                
            </dependency>
```

## Configuration

You need four java classes for configuration.

First add the spring configuration. Note that the component scan at jbehave-web-app package "de.telekom.jbehave.webapp" is needed.

```java
@Configuration
@ComponentScan({"de.telekom.jbehave.webapp", "your.package.structure"})
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
                <version>2.19.1</version>
                <configuration>
                    <includes>
                        <include>**/RunAllMyStories.java</include>
                    </includes>
                </configuration>
            </plugin>
```

## Build your first test

To build your first test you need a story class with the application context (described at configuration). The class name must be the same like your jbehave story file. Put the story file to the resources and use the same package structure like the class.

Then build a steps class with your jbehave test steps. Use the *@Steps* annotation so that the jbehave-web-app framework will find the class. If you want to use the class for selenium tests, you can extend the *SeleniumSteps* class from the test-framework:

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