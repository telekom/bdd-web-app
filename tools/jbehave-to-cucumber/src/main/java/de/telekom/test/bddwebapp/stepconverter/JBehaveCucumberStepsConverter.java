package de.telekom.test.bddwebapp.stepconverter;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RequiredArgsConstructor
public class JBehaveCucumberStepsConverter {

    private final List<File> jBehaveSteps;

    public void convertToCucumberSteps() throws IOException {
        for (var jBehaveStep : jBehaveSteps) {
            Path path = Paths.get(jBehaveStep.getAbsolutePath());

            String content = Files.readString(path, Charset.defaultCharset());
            content = content.replaceAll("import de.telekom.test.bddwebapp.jbehave.steps.Steps;", "");
            content = content.replaceAll("import org.jbehave.core.annotations.Given;", "io.cucumber.java.en.Given");
            content = content.replaceAll("import org.jbehave.core.annotations.When;", "io.cucumber.java.en.When");
            content = content.replaceAll("import org.jbehave.core.annotations.Then;", "io.cucumber.java.en.Then");
            content = content.replaceAll("import static de.telekom.test.bddwebapp.frontend.util.UrlAppender.appendUrl;", "import static de.telekom.test.bddwebapp.frontend.util.UrlAppender.appendUrl;");
            Files.write(path, content.getBytes(Charset.defaultCharset()));
        }
    }

}
