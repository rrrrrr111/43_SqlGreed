package ru.roman.bim.model;

/** @author Roman 13.01.13 14:37 */
public enum Lang {

    DEUTSCH("de"),
    RUSSIAN("ru"),
    ENGLISH("en"),

    ;

    private final String reduction;

    private Lang(String reduction) {
        this.reduction = reduction;
    }


    public static Lang valueOf(Long wordLandId) {
        for (Lang lang : values()) {
            if (lang.ordinal() == wordLandId) {
                return lang;
            }
        }
        throw new IllegalArgumentException(String.format("Unknown ordinal %s", wordLandId));
    }

    public String getReduction() {
        return reduction;
    }
}
