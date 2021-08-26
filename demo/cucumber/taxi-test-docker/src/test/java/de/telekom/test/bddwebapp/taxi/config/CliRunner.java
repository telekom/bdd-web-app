package de.telekom.test.bddwebapp.taxi.config;

import java.util.stream.Stream;

public class CliRunner {

    private static final String[] defaultArguments = {
            "--glue",
            "de.telekom.test.bddwebapp",
            "classpath:de/telekom/test/bddwebapp"
    };

    public static void main(String[] args) {
        Stream<String> cucumberOptions = Stream.concat(Stream.of(defaultArguments), Stream.of(args));
        byte status = io.cucumber.core.cli.Main.run(cucumberOptions.toArray(String[]::new));
        System.exit(status);
    }

}