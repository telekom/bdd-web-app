package de.telekom.test.bddwebapp.interaction.flatinteractiontest;

import lombok.Data;

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
