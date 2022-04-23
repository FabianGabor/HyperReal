/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor/HyperReal
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.HyperInteger;
import com.fabiangabor.hyperreal.HyperReal;

import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.ADDITION;
import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.UNSUPPORTED_NUMBER;
import static com.fabiangabor.hyperreal.constants.NumberConstants.NEGATIVE_SIGN_VAL;
import static com.fabiangabor.hyperreal.constants.NumberConstants.ZERO;
import static com.fabiangabor.hyperreal.service.HelperService.reverse;

public class AddOperation implements Operation {

    @Override
    public HyperReal execute(HyperReal number1, HyperReal number2) {
        if (number1 instanceof HyperInteger && number2 instanceof HyperInteger) {
            return add((HyperInteger) number1, (HyperInteger) number2);
        }

        throw new IllegalArgumentException(String.format("%s %s", ADDITION, UNSUPPORTED_NUMBER));
    }

    private HyperReal add(HyperInteger number1, HyperInteger number2) {

        final HyperReal zero = new HyperInteger(ZERO);

        if (number1.isEqual(zero)) {
            return number2;
        }

        if (number2.isEqual(zero)) {
            return number1;
        }

        if (number1.isPositiveOrZero() && number2.isPositiveOrZero()) {
            return new HyperInteger(sum(number1, number2));
        }

        if (number1.isNegative() && number2.isNegative()) {
            return new HyperInteger(sum(number1, number2), NEGATIVE_SIGN_VAL);
        }

        // fentebb ellenőriztük az előjelek egyezését. Alább már különböző előjelűek a számok
        if (number1.abs().isEqual(number2.abs())) {
            return zero;
        }

        Operation subtract = new SubtractOperation();


        if (number1.isBigger(number2)) {
            if (number1.abs().isBigger(number2.abs())) {
                return new HyperInteger(subtract.execute(number1, number2).toString());
            } else {
                return new HyperInteger(subtract.execute(number2, number1).toString(), NEGATIVE_SIGN_VAL);
            }
        }

        if (number1.abs().isBigger(number2.abs())) {
            return new HyperInteger(subtract.execute(number1, number2).toString(), NEGATIVE_SIGN_VAL);
        }

        return new HyperInteger(subtract.execute(number2, number1).toString());
    }

    private String sum(HyperInteger number1, HyperInteger number2) {
        StringBuilder sum = new StringBuilder();

        byte[] revNumber1 = reverse(number1.getDigits());
        byte[] revNumber2 = reverse(number2.getDigits());

        int i = 0;
        int carry = 0;
        int localSum;

        do {
            localSum = addDigits(revNumber1, revNumber2, i);
            localSum += carry;

            carry = localSum / 10;

            sum.append(localSum % 10);

            i++;
        } while (carry > 0 || i < revNumber1.length || i < revNumber2.length);

        sum.reverse();

        return sum.toString();
    }

    private int addDigits(byte[] revNumber1, byte[] revNumber2, int i) {
        int localSum = 0;
        if (i < revNumber1.length) {
            localSum += revNumber1[i];
        }
        if (i < revNumber2.length) {
            localSum += revNumber2[i];
        }
        return localSum;
    }
}
