/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;

import static com.fabiangabor.hyperreal.domain.HyperInteger.ZERO;
import static com.fabiangabor.hyperreal.service.HelperService.*;

public class AddOperation implements Operation {
    @Override
    public HyperInteger execute(HyperInteger number1, HyperInteger number2) {
        Operation subtract = new SubtractOperation();

        if (number1.toString().equals(ZERO)) return number2;
        if (number2.toString().equals(ZERO)) return number1;

        if (number1.getSign() >= 0 && number2.getSign() >= 0) {
            return new HyperInteger(add(number1.getDigits(), number2.getDigits()));
        }
        if (number1.getSign() < 0 && number2.getSign() < 0) {
            return new HyperInteger(add(number1.getDigits(), number2.getDigits()), -1);
        }

        // fentebb ellenőriztük az előjelek egyezését. Alább már különböző előjelűek a számok
        if (number1.abs().compareTo(number2.abs()) == 0) {
            return new HyperInteger(ZERO);
        }

        if (isNumber1BiggerThanNumber2(number1.compareTo(number2), 0)) {
            if (isNumber1BiggerThanNumber2(number1.abs().compareTo(number2.abs()), 0)) {
                return new HyperInteger(subtract.execute(number1, number2).toString());
            } else {
                return new HyperInteger(subtract.execute(number2, number1).toString(), -1);
            }
        }
        if (number1.compareTo(number2) < 0) {
            if (isNumber1BiggerThanNumber2(number1.abs().compareTo(number2.abs()), 0)) {
                return new HyperInteger(subtract.execute(number1, number2).toString(), -1);
            } else {
                return new HyperInteger(subtract.execute(number2, number1).toString());
            }
        }
        return null;

    }

    private String add(byte[] number1, byte[] number2) {
        StringBuilder sum = new StringBuilder();

        number1 = reverse(number1);
        number2 = reverse(number2);

        int i = 0;
        int j = 0;
        int carry = 0;

        do {
            int localSum = 0;
            localSum += carry;

            if (i < number1.length) {
                localSum += number1[i];
            }
            if (j < number2.length) {
                localSum += number2[j];
            }

            i++;
            j++;

            if (isNumber1BiggerThanNumber2(localSum, 9)) {
                localSum -= 10;
                carry = 1;
            } else {
                carry = 0;
            }

            sum.append(localSum);
        } while (isNumber1BiggerThanNumber2(carry, 0) || i < number1.length || j < number2.length);

        sum.reverse();

        return sum.toString();
    }
}
