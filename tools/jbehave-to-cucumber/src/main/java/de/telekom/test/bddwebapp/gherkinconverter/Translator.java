package de.telekom.test.bddwebapp.gherkinconverter;

import gherkin.formatter.model.*;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.StoryResourceNotFound;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Based on <a href="https://github.com/seize-the-dave/jbehave-to-gherkin">...</a>.
 */
@Slf4j
public class Translator {

    public FeatureWrapper translate(StoryWrapper storyWrapper) {
        var featureWrapper = new FeatureWrapper();
        Story story = storyWrapper.getStory();
        if (!story.getNarrative().asA().isEmpty()) {
            StringBuilder description = new StringBuilder();
            if (!story.getNarrative().inOrderTo().isEmpty()) {
                description.append("In order to ");
                description.append(story.getNarrative().inOrderTo());
                description.append('\n');
                description.append("As a ");
                description.append(story.getNarrative().asA());
                description.append('\n');
                description.append("I want to ");
                description.append(story.getNarrative().iWantTo());
            } else {
                description.append("As a ");
                description.append(story.getNarrative().asA());
                description.append('\n');
                description.append("I want to ");
                description.append(story.getNarrative().iWantTo());
                description.append('\n');
                description.append("So that ");
                description.append(story.getNarrative().soThat());
            }
            Feature feature = new Feature(noComments(), translate(story.getMeta()), "Feature", story.getDescription().asString(), description.toString(), 1, "");
            featureWrapper.setFeature(feature);
        } else if (storyWrapper.getStoryAsText().contains("Narrative:")) {
            Pattern pattern = Pattern.compile("Narrative.\\s.+\\s.+\\s.+\\s");
            String storyAsText = storyWrapper.getStoryAsText();
            storyAsText = storyAsText.replaceAll("\r\n", "\n");
            Matcher matcher = pattern.matcher(storyAsText);
            boolean matchFound = matcher.find();
            if (matchFound) {
                String description = storyAsText.substring(matcher.start() + 11, matcher.end() - 1);

                Feature feature = new Feature(noComments(), translate(story.getMeta()), "Feature", story.getDescription().asString(), description, 1, "");
                featureWrapper.setFeature(feature);
            }
        }

        featureWrapper.setBackgroundWrapper(translate(story.getLifecycle()));
        featureWrapper.setScenarios(translateScenarios(story.getScenarios()));

        return featureWrapper;
    }

    public List<ScenarioWrapper> translateScenarios(List<Scenario> scenarios) {
        List<ScenarioWrapper> scenarioWrappers = new ArrayList<>();
        for (Scenario scenario : scenarios) {
            scenarioWrappers.add(translate(scenario));
        }
        return scenarioWrappers;
    }

    private ScenarioWrapper translate(Scenario scenario) {
        ScenarioWrapper scenarioWrapper = new ScenarioWrapper();
        if (scenario.getExamplesTable().getHeaders().isEmpty()) {
            scenarioWrapper.setScenario(new gherkin.formatter.model.Scenario(noComments(), translate(scenario.getMeta()), "Scenario", scenario.getTitle(), "", 2, null));
        } else {
            scenarioWrapper.setScenarioOutline(new ScenarioOutline(noComments(), translate(scenario.getMeta()), "Scenario Outline", scenario.getTitle(), "", 2, null));
            scenarioWrapper.setExamples(translate(scenario.getExamplesTable()));
        }
        scenarioWrapper.setSteps(translateSteps(scenario.getSteps()));
        return scenarioWrapper;
    }

    private List<Tag> noTags() {
        return new ArrayList<>();
    }

    private List<Comment> noComments() {
        return new ArrayList<>();
    }

    private Examples translate(ExamplesTable examples) {
        List<ExamplesTableRow> rows = new ArrayList<>();
        rows.add(new ExamplesTableRow(noComments(), examples.getHeaders(), 0, ""));
        for (Map<String, String> row : examples.getRows()) {
            List<String> jBehaveRow = new ArrayList<>();
            for (String header : examples.getHeaders()) {
                jBehaveRow.add(row.get(header));
            }
            rows.add(new ExamplesTableRow(noComments(), jBehaveRow, 0, ""));
        }
        return new Examples(noComments(), noTags(), "Examples", "", "", 1, "", rows);
    }

    private List<Step> translateSteps(List<String> jBehaveSteps) {
        List<Step> steps = new ArrayList<>();
        for (String step : jBehaveSteps) {
            steps.add(translate(step));
        }
        return steps;
    }

    private List<Tag> translate(Meta meta) {
        List<Tag> tags = new ArrayList<>();
        if (meta.isEmpty()) {
            tags = noTags();
        } else {
            for (String name : meta.getPropertyNames()) {
                tags.add(new Tag(lowerCase("@" + join(split(name + " " + meta.getProperty(name), " "), "-")), 1));
            }
        }
        return tags;
    }

    private BackgroundWrapper translate(Lifecycle lifecycle) {
        BackgroundWrapper backgroundWrapper = new BackgroundWrapper();
        if (!lifecycle.isEmpty() && !lifecycle.getBeforeSteps().isEmpty()) {
            List<String> beforeSteps = lifecycle.getBeforeSteps();
            backgroundWrapper.setBackground(new Background(noComments(), "Background", "", "", 0));
            backgroundWrapper.setSteps(translateSteps(beforeSteps));
        }
        return backgroundWrapper;
    }

    public Step translate(String step) {
        String[] stepParts = split(step, " ", 2);
        String keyword = stepParts[0];
        String stepName = stepParts[1];
        ExamplesTableFactory tableFactory = new ExamplesTableFactory(new MostUsefulConfiguration());
        Keywords keywords = new Keywords();

        List<DataTableRow> tableData;
        if (stepName.contains(keywords.examplesTableHeaderSeparator())) {
            String[] parts = split(stepName, keywords.examplesTableHeaderSeparator(), 2);
            stepName = parts[0];
            String stepTableValue = parts[1];
            try {
                ExamplesTable examplesTable = tableFactory.createExamplesTable(stepTableValue);
                tableData = translateDataTable(examplesTable);
            } catch (StoryResourceNotFound storyResourceNotFound) {
                log.error("Can't convert the following step to table: [{}].", stepTableValue);
                tableData = noTableData();
            }
        } else {
            tableData = noTableData();
        }
        return new Step(noComments(), keyword, " " + stepName, 1, tableData, null);
    }

    private List<DataTableRow> translateDataTable(ExamplesTable examplesTable) {
        List<DataTableRow> dataTable = new ArrayList<>();
        dataTable.add(new DataTableRow(noComments(), examplesTable.getHeaders(), 0, Row.DiffType.NONE));
        for (Map<String, String> row : examplesTable.getRows()) {
            List<String> jBehaveRow = new ArrayList<>();
            for (String header : examplesTable.getHeaders()) {
                jBehaveRow.add(row.get(header));
            }
            dataTable.add(new DataTableRow(noComments(), jBehaveRow, 0, Row.DiffType.NONE));
        }
        return dataTable;
    }

    private List<DataTableRow> noTableData() {
        return emptyList();
    }
}