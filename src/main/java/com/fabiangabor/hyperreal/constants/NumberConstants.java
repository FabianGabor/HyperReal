/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.constants;

import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.MSG_UTILITY_CLASS;

public final class NumberConstants {

    public static final int POSITIVE_SIGN_VAL = 1;
    public static final int NEGATIVE_SIGN_VAL = -1;
    public static final int ZERO_SIGN_VAL = 0;
    public static final String NEGATIVE_SIGN = "-";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TEN = "10";

    private NumberConstants() {
        throw new IllegalStateException(MSG_UTILITY_CLASS);
    }
}