package ru.roman.bim.model;

/** @author Roman 13.01.13 14:37 */
public enum Lang {

    DEUTSCH("de", "DE"),
    RUSSIAN("ru", "RU"),
    ENGLISH("en", "EN"),

    ;

    private final String reductionLower;
    private final String reductionUpper;

    private Lang(String reduction, String reductionUpper) {
        this.reductionLower = reduction;
        this.reductionUpper = reductionUpper;
    }


    public static Lang valueOf(Long wordLandId) {
        for (Lang lang : values()) {
            if (lang.ordinal() == wordLandId) {
                return lang;
            }
        }
        throw new IllegalArgumentException(String.format("Unknown ordinal %s", wordLandId));
    }

    public String getReductionLower() {
        return reductionLower;
    }

    public String getReductionUpper() {
        return reductionUpper;
    }
}
