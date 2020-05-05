package de.telekom.test.bddwebapp.interaction.flatinteractiontest;

import lombok.Data;

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Data
public class ComplexType {

    private final int hierarchy;
    private String stringValue;
    private ComplexType complexValue;

    public ComplexType() {
        this.hierarchy = 0;
        this.stringValue = "value" + 0;
        this.complexValue = new ComplexType(1);
    }

    private ComplexType(int hierarchy) {
        this.hierarchy = hierarchy;
        this.stringValue = "value" + hierarchy;
        if (hierarchy < 10) {
            this.complexValue = new ComplexType(++hierarchy);
        }
    }

}
