package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.taxi.TaxiAppApplication;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.Before;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

/**
 * Class to use spring application context while running cucumber
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {TaxiAppApplication.class}, loader = SpringBootContextLoader.class)
@ComponentScan("de.telekom.test.bddwebapp")
public class Configuration {


    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    @Before
    public void setUp() {
        LOG.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }

}
