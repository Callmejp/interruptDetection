package com.ecnu.auxiliary;


public abstract class Symbol {

    // 符号的名称
    public String name;

    Symbol(String name) {
       this.name = name;
    }

    public abstract Integer getValue();

    public abstract Integer getValue(int index);

    public abstract void assignValue(Integer value, Integer index);

}
