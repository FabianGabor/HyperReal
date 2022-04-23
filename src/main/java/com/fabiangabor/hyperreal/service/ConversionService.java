/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.service;

import com.fabiangabor.hyperreal.constants.ExceptionMessageConstants;
import com.fabiangabor.hyperreal.HyperInteger;
import com.fabiangabor.hyperreal.HyperReal;

import static com.fabiangabor.hyperreal.constants.NumberConstants.*;

public final class ConversionService {

    private ConversionService() {
        throw new IllegalStateException(ExceptionMessageConstants.MSG_UTILITY_CLASS);
    }

    public static HyperInteger convertToHyperInteger(String number) {

        HyperInteger hyperInteger = new HyperInteger();

        if (number.equals(ZERO)) {
            hyperInteger.setSign(ZERO_SIGN_VAL);
            hyperInteger.setDigits(new byte[]{0});
        } else {
            String[] sign = number.split(SIGN_REGEX);
            String[] digits = number.split(DIGITS_REGEX);

            String digitsProcessed = stripLeadingZeros(digits[digits.length - 1]);
            hyperInteger.setSign(calculateSign(digitsProcessed, sign));
            hyperInteger.setDigits(getDigitsFromString(digitsProcessed));
        }

        return hyperInteger;
    }

    public static String stripLeadingZeros(String s) {
        StringBuilder diff = new StringBuilder(s);
        while (diff.charAt(0) == ZERO.charAt(0) && diff.length() > 1) {
            diff.deleteCharAt(0);
        }
        return diff.toString();
    }

    public static HyperReal stripLeadingZeros(HyperReal hyperInteger) {
        StringBuilder sb = new StringBuilder(hyperInteger.toString());
        int i = hyperInteger.getSign() == NEGATIVE_SIGN_VAL ? 1 : 0;
        while (sb.charAt(i) == ZERO.charAt(0) && sb.length() > 1) {
            sb.deleteCharAt(i);
        }

        return new HyperInteger(sb.toString());
    }

    private static byte[] getDigitsFromString(String number) {
        byte[] digits = new byte[number.length()];

        for (int i = 0; i < number.length(); i++) {
            digits[i] = Byte.parseByte(String.valueOf(number.charAt(i)));
        }
        return digits;
    }

    private static int calculateSign(String number, String... sign) {
        checkValidSign(number, sign);

        if (number.equals(ZERO)) {
            return ZERO_SIGN_VAL;
        }

        if (sign.length == 1 && sign[0].equals(NEGATIVE_SIGN)) {
            return NEGATIVE_SIGN_VAL;
        }

        return POSITIVE_SIGN_VAL;
    }

    private static void checkValidSign(String number, String... sign) {
        if (sign.length == 1 && sign[0].length() > 1) {
            throw new NumberFormatException(String.format("%s: %s", ExceptionMessageConstants.INVALID_NUMBER, number));
        }
    }
}
