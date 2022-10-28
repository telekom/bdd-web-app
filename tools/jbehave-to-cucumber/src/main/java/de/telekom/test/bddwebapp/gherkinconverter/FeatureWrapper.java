package de.telekom.test.bddwebapp.gherkinconverter;

import gherkin.formatter.Formatter;
import gherkin.formatter.model.*;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Based on <a href="https://github.com/seize-the-dave/jbehave-to-gherkin">...</a>.
 */
@Getter
@Setter
public class FeatureWrapper {
    private List<ScenarioWrapper> scenarios = new ArrayList<>();
    private File file;
    private Feature feature = new Feature(null, null, null, null, null, null, null) {
        public void replay(Formatter formatter) {
            // do nothing
        }
    };
    private BackgroundWrapper backgroundWrapper = new BackgroundWrapper();

    public void replay(Formatter formatter) {
        feature.replay(formatter);
        backgroundWrapper.replay(formatter);
        for (ScenarioWrapper scenario : scenarios) {
            scenario.replay(formatter);
        }
    }

}
