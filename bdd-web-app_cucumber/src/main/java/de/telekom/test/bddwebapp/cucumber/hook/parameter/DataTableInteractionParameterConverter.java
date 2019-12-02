package de.telekom.test.bddwebapp.cucumber.hook.parameter;

import de.telekom.test.bddwebapp.steps.InteractionParameterConverter;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class DataTableInteractionParameterConverter {

    @Autowired
    private InteractionParameterConverter interactionParameterConverter;

    public List<Map<String, Object>> getRowsWithInteractionKey(DataTable testData) {
        List<Map<String, String>> rows = testData.asMaps();
        List<Map<String, Object>> rowsWithValuesFromInteraction = new ArrayList<>();
        rows.forEach(row -> {
            Map<String, Object> rowWithValueFromInteraction = new HashMap<>();
            row.forEach((key, valueOrInteractionKey) -> rowWithValueFromInteraction.put(key, interactionParameterConverter.getValueFromKeyOrValueOrConcatenated(valueOrInteractionKey)));
            rowsWithValuesFromInteraction.add(rowWithValueFromInteraction);
        });
        return rowsWithValuesFromInteraction;
    }
}
