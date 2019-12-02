package de.telekom.test.bddwebapp.cucumber.extension;

import java.util.HashSet;
import java.util.Set;

public class ExtendedLifeCycle {

    public static final int BEFORE_ALL_ORDER = 0;
    public static final int BEFORE_FEATURE_ORDER = 10;

    private static Integer testCaseCountBeforeAll = 0;

    private static String currentFeature;
    private static Integer testCaseCountCurrentFeature = 0;

    private ExtendedLifeCycle() {
    }

    public static boolean isBeforeAll() {
        return testCaseCountBeforeAll <= 1;
    }

    public static void increaseTestCaseCountForBeforeAll() {
        testCaseCountBeforeAll++;
    }

    public static boolean isBeforeFeature() {
        return testCaseCountCurrentFeature <= 1;
    }

    public static void increaseTestCaseCountForFeature(String feature) {
        if (feature.equals(currentFeature)) {
            testCaseCountCurrentFeature++;
        } else {
            currentFeature = feature;
            testCaseCountCurrentFeature = 1;
        }
    }
}
