package com.ecnu.auxiliary;


public class ArraySymbol extends Symbol {

    private int[] value;

    public ArraySymbol(String name, int[] value) {
        super(name);
        this.value = value;
    }

    public Integer getValue(int index) {
        return value[index];
    }

    public Integer getValue() {
        return value[0];
    }

    public void assignValue(Integer value, Integer index) {
        this.value[index] = value;
    }
}
