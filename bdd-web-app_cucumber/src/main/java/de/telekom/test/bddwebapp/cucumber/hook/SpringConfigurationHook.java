package de.telekom.test.bddwebapp.cucumber.hook;

import de.telekom.test.bddwebapp.cucumber.extension.ApplicationContextReference;
import de.telekom.test.bddwebapp.cucumber.hook.config.ContextConfig;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.BEFORE_ALL_ORDER;
import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.isBeforeAll;

/**
 * Class to use spring application context while running cucumber
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextConfig.class)
public class SpringConfigurationHook {

    private static final Logger LOG = LoggerFactory.getLogger(SpringConfigurationHook.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    @Before(order = BEFORE_ALL_ORDER)
    public void setUpSpringContext() {
        if (isBeforeAll()) {
            LOG.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
            ApplicationContextReference.setApplicationContext(applicationContext);
        }
    }

}
