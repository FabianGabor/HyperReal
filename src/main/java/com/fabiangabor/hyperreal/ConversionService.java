/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal;

public final class ConversionService {

    private ConversionService() {
    }

    public static HyperInteger convertToHyperInteger(String number) {
        HyperInteger hyperInteger = new HyperInteger();

        if (number.equals(HyperInteger.ZERO)) {
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

        if (number.equals(HyperInteger.ZERO)) {
            return 0;
        }

        if (sign.length == 1 && sign[0].equals("-")) {
            return -1;
        }

        return 1;
    }

    private static void checkValidSign(String number, String[] sign) {
        if (sign.length == 1 && sign[0].length() > 1) {
            throw new NumberFormatException("Invalid number: " + number);
        }
    }

    public static String stripLeadingZeros(StringBuilder diff) {
        while (diff.charAt(0) == '0' && diff.length() > 1)
            diff.deleteCharAt(0);
        return diff.toString();
    }

    public static String stripLeadingZeros(String s) {
        StringBuilder diff = new StringBuilder(s);
        while (diff.charAt(0) == '0' && diff.length() > 1)
            diff.deleteCharAt(0);
        return diff.toString();
    }

    public static HyperInteger stripLeadingZeros(HyperInteger hyperInteger) {
        StringBuilder sb = new StringBuilder(hyperInteger.toString());
        int i = (hyperInteger.getSign() < 0) ? 1 : 0;
        while (sb.charAt(i) == '0' && sb.length() > 1)
            sb.deleteCharAt(i);

        return new HyperInteger(sb.toString());
    }
}
