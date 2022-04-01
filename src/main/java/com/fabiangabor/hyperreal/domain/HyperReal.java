/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.domain;

public interface HyperReal {

    HyperReal add(HyperReal x);

    HyperReal subtract(HyperReal x);

    HyperReal multiply(HyperReal x);

    HyperReal divide(HyperReal x);

    HyperReal abs();

    int compareTo(HyperReal x);
//
    int getSign();

    void setPositive();

    void setNegative();

    int getLength();

    byte getDigit(int i);

}