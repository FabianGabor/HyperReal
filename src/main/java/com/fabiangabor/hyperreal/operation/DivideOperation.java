/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.domain.HyperReal;
import com.fabiangabor.hyperreal.service.ConversionService;

import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.*;
import static com.fabiangabor.hyperreal.constants.NumberConstants.*;
import static com.fabiangabor.hyperreal.service.HelperService.subArray;

public class DivideOperation implements Operation {

    @Override
    public HyperReal execute(HyperReal number1, HyperReal number2) {
        if (number1 instanceof HyperInteger && number2 instanceof HyperInteger) {
            return divide((HyperInteger) number1, (HyperInteger) number2);
        }

        throw new IllegalArgumentException(String.format("%s %s", DIVISION, UNSUPPORTED_NUMBER));
    }

    private HyperReal divide(HyperInteger number1, HyperInteger number2) {

        final HyperReal zero = new HyperInteger(ZERO);
        final HyperReal one = new HyperInteger(ONE);

        if (number2.isEqual(zero)) {
            throw new ArithmeticException(DIVISION_BY_ZERO);
        }
        if (number1.isEqual(zero)) {
            return zero;
        }
        if (number2.isEqual(one)) {
            return number1;
        }
        if (number2.abs().isEqual(one)) {
            return new HyperInteger(number1.toString(), number1.getSign() * number2.getSign());
        }
        if (number1.isEqual(number2)) {
            return one;
        }
        if (number1.abs().isEqual(number2.abs())) {
            return new HyperInteger(ONE, NEGATIVE_SIGN_VAL);
        }
        if (number1.abs().isSmaller(number2.abs())) {
            return zero;
        }
        if (number1.getLength() == number2.getLength() && number1.getDigit(0) / number2.getDigit(0) == 1) {
            return new HyperInteger(ONE, number1.getSign() * number2.getSign());
        }

        return div(number1, number2);
    }

    private HyperReal div(HyperInteger number1, HyperInteger number2) {

        final HyperReal zero = new HyperInteger(ZERO);
        final HyperReal one = new HyperInteger(ONE);

        StringBuilder sq = new StringBuilder();
        HyperReal subDivident;
        HyperReal remainder = new HyperInteger(ZERO);
        HyperReal subQuotient;
        HyperReal tmp;
        int start = 0;

        for (int end = 1; end < number1.getLength() + 1; end++) {
            if (remainder.isEqual(zero)) {
                subDivident = subArray(number1, start, end).abs();
            } else {
                subDivident = subArray(number1, start, end).abs().add(remainder.multiply(new HyperInteger(TEN)).abs());
            }

            subQuotient = new HyperInteger(TEN);

            do {
                subQuotient = subQuotient.subtract(one); // subQuotient--
                tmp = number2.multiply(subQuotient).abs();
            } while (tmp.isBigger(subDivident.abs()));
            start = end;

            remainder = subDivident.abs().subtract(tmp);

            sq.append(subQuotient.toString());
        }

        HyperInteger quotient = new HyperInteger(sq.toString());
        quotient.setSign(number1.getSign() * number2.getSign());

        return ConversionService.stripLeadingZeros(quotient);
    }
}
