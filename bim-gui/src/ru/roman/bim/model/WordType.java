package ru.roman.bim.model;

import java.util.ArrayList;
import java.util.List;

/** @author Roman 16.01.13 0:22 */
public enum WordType {

    WORD(0L, "word"),
    EXPRESSION(1L, "expression"),
    IDIOM(2L, "idiom"),
    ;

    private final Long id;
    private final String name;

    private WordType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static WordType valueOf(Integer ord) {
        for (WordType i : values()) {
            if (i.ordinal() == ord) {
                return i;
            }
        }
        throw new IllegalArgumentException(String.format("Unknown ordinal %s", ord));
    }

    public static List<Long> getIds(List<WordType> list) {
        List<Long> ordinals = new ArrayList<Long>();
        for(WordType t : list) {
            ordinals.add(t.getId());
        }
        return ordinals;
    }

    public static List<Integer> getOrdinals(List<WordType> list) {
        List<Integer> ordinals = new ArrayList<Integer>();
        for(WordType t : list) {
            ordinals.add(t.ordinal());
        }
        return ordinals;
    }
}
