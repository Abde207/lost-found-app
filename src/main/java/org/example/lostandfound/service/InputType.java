package org.example.lostandfound.service;

import java.util.Arrays;

import static java.util.Comparator.comparingInt;

public enum InputType {
    EMAIL("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$", 0),
    USERNAME("^[a-zA-Z0-9_]{3,20}$", 1),
    INVALID(".*", 2);

    private final String regex;
    private final int order;

    InputType(String regex, int order) {
        this.regex = regex;
        this.order = order;
    }

    public static InputType inputType(String string) {
        return Arrays.stream(values()).sorted(comparingInt(inputType -> inputType.order))
                .filter(t -> string.matches(t.regex))
                .findFirst().get();
    }

}