/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.domain;

public final class Constants {

    private Constants() {
        throw new IllegalStateException(MSG_UTILITY_CLASS);
    }

    public static final int POSITIVE_SIGN_VAL = 1;
    public static final int NEGATIVE_SIGN_VAL = -1;
    public static final int ZERO_SIGN_VAL = 0;
    public static final String NEGATIVE_SIGN = "-";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TEN = "10";
    public static final int BIGGER = 1;
    public static final int EQUAL = 0;
    public static final int SMALLER = -1;

    public static final String MSG_UTILITY_CLASS = "Utility class";
    public static final String INVALID_NUMBER = "Invalid number";

    public static final String DIVISION_BY_ZERO = "Division by " + ZERO;

    public static final String NUMBERS_NOT_SUPPORTED = "is not supported for this type of numbers";
    public static final String ADDITION = "Addition";
    public static final String SUBTRACTION = "Subtraction";
    public static final String MULTIPLICATION = "Multiplication";
    public static final String DIVISION = "Division";
}