package com.ecnu.auxiliary;

public class BoolSymbol extends Symbol {

    Integer value;

    public BoolSymbol(String name, Integer value) {
        super(name);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getValue(int index) {
        return getValue();
    }

    public void assignValue(Integer value, Integer index) {
        this.value = value;
    }
}
