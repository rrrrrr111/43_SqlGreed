package ru.roman.bim.service.gae.dto;

/** @author Roman 22.12.12 12:40 */
public enum TypeModel{

    WORD("word"),
    EXPRESSION("expression"),
    ;

    private final String name;

    private TypeModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
