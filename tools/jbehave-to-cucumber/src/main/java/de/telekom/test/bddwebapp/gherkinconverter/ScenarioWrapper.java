package de.telekom.test.bddwebapp.gherkinconverter;

import gherkin.formatter.Formatter;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Based on https://github.com/seize-the-dave/jbehave-to-gherkin.
 */
public class ScenarioWrapper {
    private List<Step> steps = new ArrayList<>();
    private Scenario scenario = new Scenario(null, null, null, null, null, null, null) {
        public void replay(Formatter formatter) {
            // Do nothing
        }
    };
    private ScenarioOutline scenarioOutline = new ScenarioOutline(null, null, null, null, null, null, null) {
        public void replay(Formatter formatter) {
            // Do nothing
        }
    };
    private Examples examples = new Examples(null, null, null, null, null, null, null, null) {
        public void replay(Formatter formatter) {
            // Do nothing
        }
    };

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void replay(Formatter formatter) {
        scenario.replay(formatter);
        scenarioOutline.replay(formatter);
        for (Step step : steps) {
            step.replay(formatter);
        }
        examples.replay(formatter);
    }

    public void setScenarioOutline(ScenarioOutline scenarioOutline) {
        this.scenarioOutline = scenarioOutline;
    }

    public void setExamples(Examples examples) {
        this.examples = examples;
    }
}
