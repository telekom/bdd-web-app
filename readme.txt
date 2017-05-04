jbehave-web

This jbehave extension is designed to facilitate the development of automated acceptance tests for web frontends.

Features:
- JBehave Steps are loaded via Spring and can directly access the webdriver by dependency injection
- Integration of the webdriver manager for automatic webdriver updates into the jbehave lifecycle
- Optional extension of selenium webelements to include useful additional methods
- Integration of restAssured which is also available via dependency injection
- Generic test data management integrated into the JBehave lifecycle
- Creation of screenshots in case of errors and if desired after each step
