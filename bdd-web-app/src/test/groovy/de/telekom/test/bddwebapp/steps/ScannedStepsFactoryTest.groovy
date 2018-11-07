package de.telekom.test.bddwebapp.steps

import org.jbehave.core.configuration.Configuration
import org.jbehave.core.steps.InstanceStepsFactory
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class ScannedStepsFactoryTest extends Specification {

    def applicationContextMock = Mock(ApplicationContext.class)
    def configurationMock = Mock(Configuration.class)
    def testStepLevel0 = new TestLevel0Step();
    def testLevel1ExtendsTestLevel0Step = new TestLevel1ExtendsTestLevel0Step();

    ScannedStepsFactory scannedStepsFactory = new ScannedStepsFactory() {
        ApplicationContext getApplicationContext() {
            return applicationContextMock;
        }

        Configuration configuration() {
            return configurationMock;
        }
    }

    def "run test level 0 with test level 0 steps only"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps.class) >>
                ["testLevel0": testStepLevel0]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(0)
        then:
        factory.stepsInstances.values().asList() == [testStepLevel0]
    }

    def "run test level 0 with steps on different test level"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps.class) >>
                ["testLevel0": testStepLevel0]
        ["testLevel1": testLevel1ExtendsTestLevel0Step]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(0)
        then:
        factory.stepsInstances.values().asList() == [testStepLevel0]
    }

    def "run test level 1 with test level 1 steps only"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps.class) >>
                ["testLevel1": testLevel1ExtendsTestLevel0Step]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(1)
        then:
        factory.stepsInstances.values().asList() == [testLevel1ExtendsTestLevel0Step]
    }

    def "run test level 1 with steps on different test level"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps.class) >>
                ["testLevel0": testStepLevel0,
                 "testLevel1": testLevel1ExtendsTestLevel0Step]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(1)
        then:
        factory.stepsInstances.values().asList() == [testLevel1ExtendsTestLevel0Step]
    }


}
