package de.telekom.test.bddwebapp.taxi.multithreading;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;

public class GenerateMultithreadingStories {

    public static void main(String[] args) throws IOException {
        multiplyStories("login");
        multiplyStories("registration");
    }

    public static void multiplyStories(String story) throws IOException {
        String resourcePath = "demo/test/taxi-test-multithreading/src/test/resources/de/telekom/test/bddwebapp/taxi/multithreading/stories/";

        for (int i = 2; i <= 49; i++) {
            Path storySource = get(resourcePath + story + ".story");
            Path storyTarget = get(resourcePath + story + i + ".story");
            copy(storySource, storyTarget, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Generated " + storyTarget);
        }
    }

}
