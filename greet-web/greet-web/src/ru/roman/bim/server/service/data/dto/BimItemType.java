package ru.roman.bim.server.service.data.dto;

import java.util.ArrayList;
import java.util.List;

/** @author Roman 22.12.12 12:40 */
public enum BimItemType {

    WORD("word"),
    EXPRESSION("expression"),
    ;

    private final String name;

    private BimItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static BimItemType byOrdinal(Integer ord) {
        for (BimItemType i : values()) {
            if (i.ordinal() == ord) {
                return i;
            }
        }
        return null;
    }

    public static List<Integer> getOrdinals(List<BimItemType> list) {
        List<Integer> ordinals = new ArrayList<Integer>();
        for(BimItemType t : list) {
            ordinals.add(t.ordinal());
        }
        return ordinals;
    }
}
