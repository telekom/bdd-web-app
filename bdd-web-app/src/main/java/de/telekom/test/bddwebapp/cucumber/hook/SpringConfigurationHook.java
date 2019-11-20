package de.telekom.test.bddwebapp.cucumber.hook;

import de.telekom.test.bddwebapp.cucumber.hook.config.ContextConfig;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class to use spring application context while running cucumber
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextConfig.class)
public class SpringConfigurationHook {

    private static final Logger LOG = LoggerFactory.getLogger(SpringConfigurationHook.class);

    @Given("IDE integration hook")
    public void ideIntegrationHook(){
    }

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    @Before
    public void setUp() {
        LOG.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }

}
