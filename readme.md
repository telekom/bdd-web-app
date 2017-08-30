# jbehave-web-app

This JBehave extension should make web application behavior driven development much easier. It integrates Selenium and REST-assured into a spring lifecycle and help developing automated acceptance tests for frontends. 

## Features

- JBehave Steps are automatic loaded by Spring. You have just to add the annotation @Steps
- Generic test data management integrated into the JBehave lifecycle by spring.
- Access the WebDriver and Selenium-Page-Objects by Spring dependency injection.
- Integration of WebDriver-manager for automatic WebDriver updates into the JBehave lifecycle.
- Use an own extension of Selenium-Web-Elements if you want. The Extension include useful additional methods like scroll to element or element exist.
- Integration of REST-assured which is also available by Spring dependency injection.
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
                <version>1.1-SNAPSHOT</version>
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