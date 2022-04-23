/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.HyperReal;

public interface Operation {
    HyperReal execute(HyperReal number1, HyperReal number2);
}
