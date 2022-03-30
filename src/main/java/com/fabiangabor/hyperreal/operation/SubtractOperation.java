/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.HyperInteger;
import com.fabiangabor.hyperreal.service.ConversionService;

import static com.fabiangabor.hyperreal.service.HelperService.*;

public class SubtractOperation implements Operation {
    @Override
    public HyperInteger execute(HyperInteger number1, HyperInteger number2) {
        Operation add = new AddOperation();

        if (number1.compareTo(number2) == 0) {
            return new HyperInteger(HyperInteger.ZERO);
        }

        if (number1.getSign() >= 0 && number2.getSign() >= 0) {
            return subtract(number1, number2);
        }

        if (number1.getSign() < 0 && number2.getSign() < 0) {
            if (isNumber1BiggerThanNumber2(number1.abs().compareTo(number2.abs()), 0)) {
                return new HyperInteger(subtract(number1.getDigits(), number2.getDigits()), -1);
            } else {
                return new HyperInteger(subtract(number2.getDigits(), number1.getDigits()), 1);
            }
        }

        if (isNumber1BiggerThanNumber2(number1.compareTo(number2), 0)) {
            return new HyperInteger(add.execute(number1, number2).toString());
        }
        if (number1.compareTo(number2) < 0) {
            return new HyperInteger(add.execute(number1, number2).toString(), -1);
        }

        return null;
    }

    private HyperInteger subtract(HyperInteger number1, HyperInteger number2) {
        HyperInteger diff;

        if (number1.compareTo(number2) < 0) {
            swap(number1, number2);
            diff = new HyperInteger(subtract(number1.getDigits(), number2.getDigits()));
            diff.setSign(-1);
            swap(number1, number2);
            return diff;
        }

        if (number1.compareTo(number2) == 0) {
            diff = new HyperInteger(HyperInteger.ZERO);
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
        } while (isNumber1BiggerThanNumber2(carry, 0) || i < number1.length || j < number2.length);

        diff.reverse();

        return ConversionService.stripLeadingZeros(diff);
    }
}
