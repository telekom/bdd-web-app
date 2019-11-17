package de.telekom.test.bddwebapp.taxi.testlevel.steps;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import static org.junit.Assert.fail;

@Slf4j
public class BeforeInTestLevel0 {

    @Before
    public void before() {
        if (Integer.parseInt(System.getProperty("testLevel")) > 0) {
            fail("This step should be overridden by BeforeInTestLevel1");
        }
    }

}
