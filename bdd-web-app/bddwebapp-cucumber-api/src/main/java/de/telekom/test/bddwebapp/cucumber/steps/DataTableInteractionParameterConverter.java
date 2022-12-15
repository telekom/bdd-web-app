package de.telekom.test.bddwebapp.cucumber.steps;

import de.telekom.test.bddwebapp.interaction.InteractionParameterConverter;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps interaction key like $key to value. Simple values will return as value.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class DataTableInteractionParameterConverter {

    private final InteractionParameterConverter interactionParameterConverter;

    public DataTableInteractionParameterConverter(InteractionParameterConverter interactionParameterConverter) {
        this.interactionParameterConverter = interactionParameterConverter;
    }

    public List<Map<String, Object>> getRowsWithInteractionKey(DataTable testData) {
        var rows = testData.asMaps();
        var rowsWithValuesFromInteraction = new ArrayList<Map<String, Object>>();
        rows.forEach(row -> {
            Map<String, Object> rowWithValueFromInteraction = new HashMap<>();
            row.forEach((key, valueOrInteractionKey) -> {
                if (valueOrInteractionKey == null) {
                    valueOrInteractionKey = "";
                }
                rowWithValueFromInteraction.put(key, interactionParameterConverter.getValueFromKeyOrValueOrConcatenated(valueOrInteractionKey));
            });
            rowsWithValuesFromInteraction.add(rowWithValueFromInteraction);
        });
        return rowsWithValuesFromInteraction;
    }
}
