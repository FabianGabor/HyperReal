/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.domain.HyperReal;

import static com.fabiangabor.hyperreal.constants.EqualityConstants.*;
import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.ADDITION;
import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.UNSUPPORTED_NUMBER;
import static com.fabiangabor.hyperreal.constants.NumberConstants.*;
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

        if (number1.toString().equals(ZERO)) {
            return number2;
        }
        if (number2.toString().equals(ZERO)) {
            return number1;
        }

        if (number1.getSign() >= ZERO_SIGN_VAL && number2.getSign() >= ZERO_SIGN_VAL) {
            return new HyperInteger(add(number1.getDigits(), number2.getDigits()));
        }
        if (number1.getSign() < ZERO_SIGN_VAL && number2.getSign() < ZERO_SIGN_VAL) {
            return new HyperInteger(add(number1.getDigits(), number2.getDigits()), NEGATIVE_SIGN_VAL);
        }

        // fentebb ellenőriztük az előjelek egyezését. Alább már különböző előjelűek a számok
        if (number1.abs().compareTo(number2.abs()) == EQUAL) {
            return new HyperInteger(ZERO);
        }

        Operation subtract = new SubtractOperation();

        if (number1.compareTo(number2) == BIGGER) {
            if (number1.abs().compareTo(number2.abs()) > 0) {
                return new HyperInteger(subtract.execute(number1, number2).toString());
            } else {
                return new HyperInteger(subtract.execute(number2, number1).toString(), NEGATIVE_SIGN_VAL);
            }
        }
        if (number1.compareTo(number2) == SMALLER) {
            if (number1.abs().compareTo(number2.abs()) > 0) {
                return new HyperInteger(subtract.execute(number1, number2).toString(), NEGATIVE_SIGN_VAL);
            }
        }
        return new HyperInteger(subtract.execute(number2, number1).toString());
    }

    private String add(byte[] number1, byte[] number2) {
        StringBuilder sum = new StringBuilder();

        byte[] revNumber1 = reverse(number1);
        byte[] revNumber2 = reverse(number2);

        int i = 0;
        int j = 0;
        int carry = 0;

        do {
            int localSum = 0;
            localSum += carry;

            if (i < revNumber1.length) {
                localSum += revNumber1[i];
            }
            if (j < revNumber2.length) {
                localSum += revNumber2[j];
            }

            i++;
            j++;

            if (localSum > 9) {
                localSum -= 10;
                carry = 1;
            } else {
                carry = 0;
            }

            sum.append(localSum);
        } while (carry > 0 || i < revNumber1.length || j < revNumber2.length);

        sum.reverse();

        return sum.toString();
    }
}
