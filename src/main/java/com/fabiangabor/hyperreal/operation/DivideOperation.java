/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.domain.HyperReal;
import com.fabiangabor.hyperreal.service.ConversionService;
import org.jetbrains.annotations.NotNull;

import static com.fabiangabor.hyperreal.domain.Constants.*;
import static com.fabiangabor.hyperreal.service.HelperService.subArray;

public class DivideOperation implements Operation {
    @Override
    public HyperReal execute(HyperReal number1, HyperReal number2) {
        if (number1 instanceof HyperInteger && number2 instanceof HyperInteger) {
            return divide((HyperInteger) number1, (HyperInteger) number2);
        }

        throw new  IllegalArgumentException(String.format("%s %s", DIVISION, NUMBERS_NOT_SUPPORTED));
    }

    @NotNull
    private HyperReal divide(HyperInteger number1, HyperInteger number2) {
        if (number1.toString().equals(ZERO)) return new HyperInteger(ZERO);
        if (number2.toString().equals(ZERO)) throw new ArithmeticException(DIVISION_BY_ZERO);
        if (number2.toString().equals(ONE)) return number1;
        if (number2.abs().toString().equals(ONE))
            return new HyperInteger(number1.toString(), number1.getSign() * number2.getSign());
        if (number1.compareTo(number2) == EQUAL) return new HyperInteger(ONE);
        if (number1.abs().compareTo(number2.abs()) == EQUAL) return new HyperInteger(ONE, NEGATIVE_SIGN_VAL);
        if (number1.abs().compareTo(number2.abs()) == SMALLER) return new HyperInteger(ZERO);
        if (number1.getLength() == number2.getLength() && number1.getDigit(0) / number2.getDigit(0) == 1)
            return new HyperInteger(ONE, number1.getSign() * number2.getSign());

        return div(number1, number2);
    }

    private HyperReal div(HyperInteger number1, HyperInteger number2) {
        int start = 0;
        StringBuilder sq = new StringBuilder();
        HyperReal subDivident;
        HyperReal remainder = new HyperInteger(ZERO);

        for (int end = 1; end < number1.getLength() + 1; end++) {
            if (remainder.getSign() == ZERO_SIGN_VAL) {
                subDivident = subArray(number1, start, end).abs();
            } else {
                subDivident = subArray(number1, start, end).abs().add(remainder.multiply(new HyperInteger(TEN)).abs());
            }

            HyperReal subQuotient = new HyperInteger(TEN);
            HyperReal tmp;
            do {
                subQuotient = subQuotient.subtract(new HyperInteger(ONE)); // subQuotient--
                tmp = number2.multiply(subQuotient).abs();
            } while (tmp.compareTo(subDivident.abs()) == BIGGER);
            start = end;

            remainder = subDivident.abs().subtract(tmp);

            sq.append(subQuotient.toString());
        }

        HyperInteger quotient = new HyperInteger(sq.toString());
        quotient.setSign(number1.getSign() * number2.getSign());
        return ConversionService.stripLeadingZeros(quotient);
    }
}
