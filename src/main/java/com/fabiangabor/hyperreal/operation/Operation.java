/*
 * @author Fábián Gábor
 * Copyright (c) 2022-2022.
 * @see <a href="https://github.com/FabianGabor/HyperReal">https://github.com/FabianGabor/HyperReal</a>
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.HyperReal;

public interface Operation {
    HyperReal execute(HyperReal number1, HyperReal number2);
}
