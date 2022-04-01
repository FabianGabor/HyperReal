/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.Constants;
import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.domain.HyperReal;
import com.fabiangabor.hyperreal.service.ConversionService;

import static com.fabiangabor.hyperreal.domain.Constants.*;
import static com.fabiangabor.hyperreal.service.HelperService.*;

public class SubtractOperation implements Operation {
    @Override
    public HyperReal execute(HyperReal number1, HyperReal number2) {
        if (number1 instanceof HyperInteger && number2 instanceof HyperInteger) {
            return subtract((HyperInteger) number1, (HyperInteger) number2);
        }

        throw new  IllegalArgumentException(String.format("%s %s", SUBTRACTION, NUMBERS_NOT_SUPPORTED));
    }

    private HyperReal subtract(HyperInteger number1, HyperInteger number2) {

        if (number1.compareTo(number2) == Constants.EQUAL) {
            return new HyperInteger(Constants.ZERO);
        }

        if (number1.getSign() >= Constants.ZERO_SIGN_VAL && number2.getSign() >= Constants.ZERO_SIGN_VAL) {
            return getDiff(number1, number2);
        }

        if (number1.getSign() == Constants.NEGATIVE_SIGN_VAL && number2.getSign() == Constants.NEGATIVE_SIGN_VAL) {
            if (number1.abs().compareTo(number2.abs()) == Constants.BIGGER) {
                return new HyperInteger(subtract(number1.getDigits(), number2.getDigits()), Constants.NEGATIVE_SIGN_VAL);
            } else {
                return new HyperInteger(subtract(number2.getDigits(), number1.getDigits()), Constants.POSITIVE_SIGN_VAL);
            }
        }

        Operation add = new AddOperation();

        if (number1.compareTo(number2) == Constants.BIGGER) {
            return new HyperInteger(add.execute(number1, number2).toString());
        }
        if (number1.compareTo(number2) == Constants.SMALLER) {
            return new HyperInteger(add.execute(number1, number2).toString(), Constants.NEGATIVE_SIGN_VAL);
        }

        return null;
    }

    private HyperReal getDiff(HyperInteger number1, HyperInteger number2) {
        HyperInteger diff;

        if (number1.compareTo(number2) == Constants.SMALLER) {
            diff = new HyperInteger(subtract(number1.getDigits(), number2.getDigits()));
            diff.setNegative();
            return diff;
        }

        if (number1.compareTo(number2) == Constants.EQUAL) {
            diff = new HyperInteger(Constants.ZERO);
            return diff;
        }

        diff = new HyperInteger(subtract(number1.getDigits(), number2.getDigits()));
        return diff;
    }

    private String subtract(byte[] number1, byte[] number2) {
        StringBuilder diff = new StringBuilder();

        number1 = reverse(number1);
        number2 = reverse(number2);

        int i = 0;
        int j = 0;
        int carry = 0;

        do {
            int localDiff = 0;
            localDiff -= carry;

            if (i < number1.length) localDiff += number1[i];
            if (j < number2.length) localDiff -= number2[j];

            i++;
            j++;

            if (localDiff < 0) {
                localDiff += 10;
                carry = 1;
            } else {
                carry = 0;
            }

            diff.append(localDiff);
        } while (carry > 0 || i < number1.length || j < number2.length);

        diff.reverse();

        return ConversionService.stripLeadingZeros(diff);
    }
}
