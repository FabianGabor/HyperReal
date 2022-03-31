/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.service;

import com.fabiangabor.hyperreal.domain.HyperInteger;

public final class HelperService {

    public static void swap(HyperInteger number1, HyperInteger number2) {
        byte[] tmp = number1.getDigits();
        number1.setDigits(number2.getDigits());
        number2.setDigits(tmp);
    }

    public static byte[] reverse(byte[] num) {
        byte[] reverse = new byte[num.length];

        for (int i = 0; i < num.length; i++) {
            reverse[num.length - 1 - i] = num[i];
        }

        return reverse;
    }

    public static int compareSigns(HyperInteger number1, HyperInteger number2) {
        return Integer.compare(number1.getSign(), number2.getSign());
    }

    public static int compareLenghts(HyperInteger number1, HyperInteger number2) {
        return Integer.compare(number1.getDigits().length, number2.getDigits().length);
    }

    public static HyperInteger subArray(HyperInteger arr, int start, int end) {
        StringBuilder s = new StringBuilder();
        for (int i = start; i < end; i++) s.append(arr.getDigits()[i]);
        return new HyperInteger(s.toString(), arr.getSign());
    }
}
