package de.telekom.test.bddwebapp.taxi.multithreading;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;

public class GenerateLoginStories {

    public static void main(String args[]) throws IOException {
        String resourcePath = "demo/test/taxi-test-multithreading/src/test/resources/de/telekom/test/bddwebapp/taxi/multithreading/stories/login";

        for (int i = 2; i <= 100; i++) {
            Path storySource = get(resourcePath + "/login.story");
            Path storyTarget = get(resourcePath + "/login" + i + ".story");
            copy(storySource, storyTarget, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
