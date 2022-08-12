package de.telekom.test.bddwebapp.gherkinconverter;


import gherkin.formatter.Formatter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Based on https://github.com/seize-the-dave/jbehave-to-gherkin.
 */
public class BackgroundWrapper {
    private Background background = new Background(null, null, null, null, null) {
        public void replay(Formatter formatter) {
            // Do nothing
        }
    };
    private List<Step> steps = new ArrayList<>();

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void replay(Formatter formatter) {
        background.replay(formatter);
        for (Step step : steps) {
            step.replay(formatter);
        }
    }
}
