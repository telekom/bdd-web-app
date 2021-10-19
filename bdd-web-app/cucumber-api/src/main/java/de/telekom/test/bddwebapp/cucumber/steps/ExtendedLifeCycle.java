package de.telekom.test.bddwebapp.cucumber.steps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtendedLifeCycle {

    private static Integer testCaseCountBeforeAll = 0;

    private static String currentFeature;
    private static Integer testCaseCountCurrentFeature = 0;

    private ExtendedLifeCycle() {
    }

    public static boolean isBeforeAll() {
        return testCaseCountBeforeAll < 1;
    }

    public static void increaseTestCaseCountForBeforeAll() {
        testCaseCountBeforeAll++;
    }

    public static boolean isBeforeFeature() {
        return testCaseCountCurrentFeature < 1;
    }

    public static void increaseTestCaseCountForFeature(String feature) {
        if (feature.equals(currentFeature)) {
            testCaseCountCurrentFeature++;
        } else {
            log.info("Execution for new feature {0} is started", feature);
            currentFeature = feature;
            testCaseCountCurrentFeature = 0;
        }
    }
}
