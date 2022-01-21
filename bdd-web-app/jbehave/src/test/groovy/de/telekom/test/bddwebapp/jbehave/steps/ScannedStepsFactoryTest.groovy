package de.telekom.test.bddwebapp.jbehave.steps

import de.telekom.test.bddwebapp.jbehave.steps.ScannedStepsFactory
import de.telekom.test.bddwebapp.jbehave.steps.Steps
import org.jbehave.core.configuration.Configuration
import org.jbehave.core.steps.InstanceStepsFactory
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import spock.lang.Specification

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class ScannedStepsFactoryTest extends Specification {

    def context = Mock(ApplicationContext)
    def configuration = Mock(Configuration)

    def scannedStepsFactory = new ScannedStepsFactory() {
        ApplicationContext getApplicationContext() {
            return context
        }

        Configuration configuration() {
            return configuration
        }
    }

    def setup() {
        context.getEnvironment() >> Mock(Environment)
    }

    def "scanned steps factory"() {
        given:
        context.getBeansWithAnnotation(Steps) >> ["steps": Mock(Steps)]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.scannedStepsFactory()
        then:
        factory.stepsInstances.size() == 1
    }

    def "run test level 0 with test level 0 steps only"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps) >>
                ["testLevel0": testStepLevel0]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(0)
        then:
        factory.stepsInstances.values().asList() == [testStepLevel0]
    }

    def "run test level 0 with steps on different test levels"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps) >>
                ["testLevel0"        : testStepLevel0,
                 "testLevel1"        : testStepLevel1,
                 "testLevel1Extended": testLevel1ExtendsTestLevel0Step]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(0)
        then:
        factory.stepsInstances.values().asList() == [testStepLevel0]
    }

    def "run test level 0 with test level 0 step that extends test level 1 step"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps) >>
                ["testLevel0"                     : testStepLevel0,
                 "testLevel0ExtendsTestLevel1Step": testLevel0ExtendsTestLevel1Step,
                 "testLevel1"                     : testStepLevel1,
                 "testLevel1Extended"             : testLevel1ExtendsTestLevel0Step]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(0)
        then:
        factory.stepsInstances.values().asList() == [testStepLevel0, testLevel0ExtendsTestLevel1Step]
    }

    def "run test level 1 with test level 1 steps only"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps) >>
                ["testLevel1"        : testStepLevel1,
                 "testLevel1Extended": testLevel1ExtendsTestLevel0Step]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(1)
        then:
        factory.stepsInstances.values().asList() == [testStepLevel1, testLevel1ExtendsTestLevel0Step]
    }

    def "run test level 1 with steps on different test levels"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps) >>
                ["testLevel0"        : testStepLevel0,
                 "testLevel1"        : testStepLevel1,
                 "testLevel1Extended": testLevel1ExtendsTestLevel0Step]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(1)
        then:
        factory.stepsInstances.values().asList() == [testLevel1ExtendsTestLevel0Step, testStepLevel1]
    }

    def "run test level 1 with steps on different test level with different order"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps) >>
                ["testLevel1Extended": testLevel1ExtendsTestLevel0Step,
                 "testLevel0"        : testStepLevel0,
                 "testLevel1"        : testStepLevel1]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(1)
        then:
        factory.stepsInstances.values().asList() == [testLevel1ExtendsTestLevel0Step, testStepLevel1]
    }

    def "run test level 2 with test level 2 step that extends test level 1 step that extends test level 0 step"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps) >>
                ["testLevel0"                                      : testStepLevel0,
                 "testLevel1Extended"                              : testLevel1ExtendsTestLevel0Step,
                 "testLevel2ExtendsTestLevel1ExtendsTestLevel0Step": testLevel2ExtendsTestLevel1ExtendsTestLevel0Step
                ]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(2)
        then:
        factory.stepsInstances.values().asList() == [testLevel2ExtendsTestLevel1ExtendsTestLevel0Step]
    }

    def "run test level 2 with crazy inheritance hierarchy"() {
        given:
        scannedStepsFactory.applicationContext.getBeansWithAnnotation(Steps) >>
                ["testLevel1"                                      : testStepLevel1,
                 "testLevel0ExtendsTestLevel1Step"                 : testLevel0ExtendsTestLevel1Step,
                 "testLevel2ExtendsTestLevel0ExtendsTestLevel1Step": testLevel2ExtendsTestLevel0ExtendsTestLevel1Step
                ]
        when:
        InstanceStepsFactory factory = scannedStepsFactory.testLevelStepsFactory(2)
        then:
        factory.stepsInstances.values().asList() == [testLevel2ExtendsTestLevel0ExtendsTestLevel1Step]
    }

    def "no test level configured"() {
        when:
        def testLevel = scannedStepsFactory.getTestLevel()
        then:
        testLevel == 0
    }

    def "get configured test level"() {
        given:
        context.getEnvironment().getProperty("testLevel", Integer) >> 1
        when:
        def testLevel = scannedStepsFactory.getTestLevel()
        then:
        testLevel == 1
    }

    def testStepLevel0 = new TestLevel0Step()
    def testStepLevel1 = new TestLevel1Step()
    def testLevel0ExtendsTestLevel1Step = new TestLevel0ExtendsTestLevel1Step()
    def testLevel1ExtendsTestLevel0Step = new TestLevel1ExtendsTestLevel0Step()
    def testLevel2ExtendsTestLevel1ExtendsTestLevel0Step = new TestLevel2ExtendsTestLevel1ExtendsTestLevel0Step()
    def testLevel2ExtendsTestLevel0ExtendsTestLevel1Step = new TestLevel2ExtendsTestLevel0ExtendsTestLevel1Step()

    @Steps(testLevel = 0)
    class TestLevel0ExtendsTestLevel1Step extends TestLevel1Step {
    }

    @Steps(testLevel = 0)
    class TestLevel0Step {
    }

    @Steps(testLevel = 1)
    class TestLevel1ExtendsTestLevel0Step extends TestLevel0Step {
    }

    @Steps(testLevel = 1)
    class TestLevel1Step {
    }

    @Steps(testLevel = 2)
    class TestLevel2ExtendsTestLevel1ExtendsTestLevel0Step extends TestLevel1ExtendsTestLevel0Step {
    }

    @Steps(testLevel = 2)
    class TestLevel2ExtendsTestLevel0ExtendsTestLevel1Step extends TestLevel0ExtendsTestLevel1Step {
    }

}
