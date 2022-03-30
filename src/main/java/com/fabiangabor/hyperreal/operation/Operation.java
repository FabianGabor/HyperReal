/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;

public interface Operation {
    HyperInteger execute(HyperInteger number1, HyperInteger number2);
}
