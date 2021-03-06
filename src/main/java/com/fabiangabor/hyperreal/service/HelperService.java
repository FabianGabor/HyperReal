/*
 * @author Fábián Gábor
 * Copyright (c) 2022-2022.
 * @see <a href="https://github.com/FabianGabor/HyperReal">https://github.com/FabianGabor/HyperReal</a>
 */

package com.fabiangabor.hyperreal.service;

import com.fabiangabor.hyperreal.constants.ExceptionMessageConstants;
import com.fabiangabor.hyperreal.HyperInteger;
import com.fabiangabor.hyperreal.HyperReal;

public final class HelperService {

    private HelperService() {
        throw new IllegalStateException(ExceptionMessageConstants.MSG_UTILITY_CLASS);
    }

    public static byte[] reverse(byte[] num) {
        byte[] reverse = new byte[num.length];

        for (int i = 0; i < num.length; i++) {
            reverse[num.length - 1 - i] = num[i];
        }

        return reverse;
    }

    public static int compareSigns(HyperReal number1, HyperReal number2) {
        return Integer.compare(number1.getSign(), number2.getSign());
    }

    public static int compareLenghts(HyperReal number1, HyperReal number2) {
        return Integer.compare(number1.getLength(), number2.getLength());
    }

    public static HyperReal subArray(HyperReal arr, int start, int end) {
        StringBuilder s = new StringBuilder();
        for (int i = start; i < end; i++) {
            s.append(arr.getDigit(i));
        }
        return new HyperInteger(s.toString(), arr.getSign());
    }
}
