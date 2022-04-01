/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.constants;

import static com.fabiangabor.hyperreal.constants.NumberConstants.ZERO;

public class ExceptionMessageConstants {
    public static final String MSG_UTILITY_CLASS = "Utility class";
    public static final String INVALID_NUMBER = "Invalid number";
    public static final String DIVISION_BY_ZERO = "Division by " + ZERO;
    public static final String NUMBERS_NOT_SUPPORTED = "is not supported for this type of numbers";
    public static final String ADDITION = "Addition";
    public static final String SUBTRACTION = "Subtraction";
    public static final String MULTIPLICATION = "Multiplication";
    public static final String DIVISION = "Division";

    private ExceptionMessageConstants() {
        throw new IllegalStateException(MSG_UTILITY_CLASS);
    }
}