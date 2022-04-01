/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.service;

import com.fabiangabor.hyperreal.domain.Constants;
import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.domain.HyperReal;

import static com.fabiangabor.hyperreal.domain.Constants.INVALID_NUMBER;
import static com.fabiangabor.hyperreal.domain.Constants.MSG_UTILITY_CLASS;

public final class ConversionService {

    private ConversionService() {
        throw new IllegalStateException(MSG_UTILITY_CLASS);
    }

    public static HyperInteger convertToHyperInteger(String number) {

        HyperInteger hyperInteger = new HyperInteger();

        if (number.equals(Constants.ZERO)) {
            hyperInteger.setSign(0);
            hyperInteger.setDigits(new byte[]{0});
            return hyperInteger;
        } else {
            String[] sign = number.split("([0-9]+)");
            String[] digits = number.split("([+-])");

            number = stripLeadingZeros(digits[digits.length - 1]);
            hyperInteger.setSign(calculateSign(number, sign));
            byte[] num = new byte[number.length()];

            for (int i = 0; i < number.length(); i++) {
                num[i] = Byte.parseByte(String.valueOf(number.charAt(i)));
            }

            hyperInteger.setDigits(num);
        }

        return hyperInteger;
    }

    private static int calculateSign(String number, String[] sign) {
        checkValidSign(number, sign);

        if (number.equals(Constants.ZERO)) {
            return 0;
        }

        if (sign.length == 1 && sign[0].equals(Constants.NEGATIVE_SIGN)) {
            return Constants.NEGATIVE_SIGN_VAL;
        }

        return Constants.POSITIVE_SIGN_VAL;
    }

    private static void checkValidSign(String number, String[] sign) {
        if (sign.length == Constants.POSITIVE_SIGN_VAL && sign[0].length() > 1) {
            throw new NumberFormatException(String.format("%s: %s", INVALID_NUMBER, number));
        }
    }

    public static String stripLeadingZeros(StringBuilder diff) {
        while (diff.charAt(0) == Constants.ZERO.charAt(0) && diff.length() > 1)
            diff.deleteCharAt(0);
        return diff.toString();
    }

    public static String stripLeadingZeros(String s) {
        StringBuilder diff = new StringBuilder(s);
        while (diff.charAt(0) == Constants.ZERO.charAt(0) && diff.length() > 1)
            diff.deleteCharAt(0);
        return diff.toString();
    }

    public static HyperReal stripLeadingZeros(HyperReal hyperInteger) {
        StringBuilder sb = new StringBuilder(hyperInteger.toString());
        int i = (hyperInteger.getSign() < 0) ? 1 : 0;
        while (sb.charAt(i) == Constants.ZERO.charAt(0) && sb.length() > 1)
            sb.deleteCharAt(i);

        return new HyperInteger(sb.toString());
    }
}
