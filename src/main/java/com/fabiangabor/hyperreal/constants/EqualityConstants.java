/*
 * @author Fábián Gábor
 * Copyright (c) 2022-2022.
 * @see <a href="https://github.com/FabianGabor/HyperReal">https://github.com/FabianGabor/HyperReal</a>
 */

package com.fabiangabor.hyperreal.constants;

import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.MSG_UTILITY_CLASS;

public final class EqualityConstants {

    public static final int BIGGER = 1;
    public static final int EQUAL = 0;
    public static final int SMALLER = -1;

    private EqualityConstants() {
        throw new IllegalStateException(MSG_UTILITY_CLASS);
    }
}