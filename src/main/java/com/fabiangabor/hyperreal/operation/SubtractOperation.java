/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.domain.HyperReal;
import com.fabiangabor.hyperreal.service.ConversionService;

import static com.fabiangabor.hyperreal.constants.EqualityConstants.*;
import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.SUBTRACTION;
import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.UNSUPPORTED_NUMBER;
import static com.fabiangabor.hyperreal.constants.NumberConstants.*;
import static com.fabiangabor.hyperreal.service.HelperService.reverse;

public class SubtractOperation implements Operation {

    @Override
    public HyperReal execute(HyperReal number1, HyperReal number2) {
        if (number1 instanceof HyperInteger && number2 instanceof HyperInteger) {
            return subtract((HyperInteger) number1, (HyperInteger) number2);
        }

        throw new IllegalArgumentException(String.format("%s %s", SUBTRACTION, UNSUPPORTED_NUMBER));
    }

    private HyperReal subtract(HyperInteger number1, HyperInteger number2) {

        if (number1.compareTo(number2) == EQUAL) {
            return new HyperInteger(ZERO);
        }

        if (number1.getSign() >= ZERO_SIGN_VAL && number2.getSign() >= ZERO_SIGN_VAL) {
            return getDiff(number1, number2);
        }

        if (number1.getSign() == NEGATIVE_SIGN_VAL && number2.getSign() == NEGATIVE_SIGN_VAL) {
            if (number1.abs().compareTo(number2.abs()) == BIGGER) {
                return new HyperInteger(subtract(number1.getDigits(), number2.getDigits()), NEGATIVE_SIGN_VAL);
            } else {
                return new HyperInteger(subtract(number2.getDigits(), number1.getDigits()), POSITIVE_SIGN_VAL);
            }
        }

        Operation add = new AddOperation();

        if (number1.compareTo(number2) == BIGGER) {
            return new HyperInteger(add.execute(number1, number2).toString());
        }
        return new HyperInteger(add.execute(number1, number2).toString(), NEGATIVE_SIGN_VAL);
    }

    private HyperReal getDiff(HyperInteger number1, HyperInteger number2) {
        HyperInteger diff;

        if (number1.compareTo(number2) == SMALLER) {
            diff = new HyperInteger(subtract(number1.getDigits(), number2.getDigits()));
            diff.setNegative();
            return diff;
        }

        if (number1.compareTo(number2) == EQUAL) {
            diff = new HyperInteger(ZERO);
            return diff;
        }

        diff = new HyperInteger(subtract(number1.getDigits(), number2.getDigits()));
        return diff;
    }

    private String subtract(byte[] number1, byte[] number2) {
        StringBuilder diff = new StringBuilder();

        byte[] revNumber1 = reverse(number1);
        byte[] revNumber2 = reverse(number2);

        int i = 0;
        int j = 0;
        int carry = 0;

        do {
            int localDiff = 0;
            localDiff -= carry;

            if (i < revNumber1.length) {
                localDiff += revNumber1[i];
            }
            if (j < revNumber2.length) {
                localDiff -= revNumber2[j];
            }

            i++;
            j++;

            if (localDiff < 0) {
                localDiff += 10;
                carry = 1;
            } else {
                carry = 0;
            }

            diff.append(localDiff);
        } while (carry > 0 || i < revNumber1.length || j < revNumber2.length);

        diff.reverse();

        return ConversionService.stripLeadingZeros(diff.toString());
    }
}
