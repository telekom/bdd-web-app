package de.telekom.test.bddwebapp.cucumber.extension;

import java.util.HashSet;
import java.util.Set;

public class ExtendedLifeCycle {

    public static final int BEFORE_ALL_ORDER = 0;
    public static final int BEFORE_FEATURE_ORDER = 10;

    private final static Set<String> beforeAllAlreadyExecutedMethods = new HashSet<>();
    private final static Set<String> beforeFeatureAlreadyExecutedMethods = new HashSet<>();

    private ExtendedLifeCycle() {
    }

    public static boolean isBeforeAll(String methodName) {
        if (!beforeAllAlreadyExecutedMethods.contains(methodName)) {
            beforeAllAlreadyExecutedMethods.add(methodName);
            return true;
        }
        return false;
    }

    public static void resetBeforeAll() {
        beforeAllAlreadyExecutedMethods.clear();
    }

    public static boolean isBeforeFeature(String methodName) {
        if (!beforeFeatureAlreadyExecutedMethods.contains(methodName)) {
            beforeFeatureAlreadyExecutedMethods.add(methodName);
            return true;
        }
        return false;
    }

    public static void resetBeforeFeature() {
        beforeFeatureAlreadyExecutedMethods.clear();
    }

}
