/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.domain;

public interface HyperReal {

    HyperInteger add(HyperInteger x);

    HyperInteger subtract(HyperInteger x);

    HyperInteger multiply(HyperInteger x);

    HyperInteger divide(HyperInteger x);

    HyperInteger abs();

    int compareTo(HyperInteger x);

}