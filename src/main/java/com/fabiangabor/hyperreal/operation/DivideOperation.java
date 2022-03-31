/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.service.ConversionService;

import static com.fabiangabor.hyperreal.domain.HyperInteger.ZERO;
import static com.fabiangabor.hyperreal.service.HelperService.subArray;

public class DivideOperation implements Operation {
    @Override
    public HyperInteger execute(HyperInteger number1, HyperInteger number2) {
        if (number1.toString().equals(ZERO)) return new HyperInteger(ZERO);
        if (number2.toString().equals(ZERO)) throw new ArithmeticException("Division by 0");
        if (number2.toString().equals("1")) return number1;
        if (number2.abs().toString().equals("1"))
            return new HyperInteger(number1.toString(), number1.getSign() * number2.getSign());
        if (number1.compareTo(number2) == 0) return new HyperInteger("1");
        if (number1.abs().compareTo(number2.abs()) == 0) return new HyperInteger("-1");
        if (number1.abs().compareTo(number2.abs()) < 0) return new HyperInteger(ZERO);
        if (number1.getDigits().length == number2.getDigits().length && number1.getDigits()[0] / number2.getDigits()[0] == 1)
            return new HyperInteger("1", number1.getSign() * number2.getSign());

        return divide(number1, number2);
    }

    private HyperInteger divide(HyperInteger number1, HyperInteger number2) {
        int start = 0;
        StringBuilder sq = new StringBuilder();
        HyperInteger subDivident;
        HyperInteger remainder = new HyperInteger(ZERO);

        for (int end = 1; end < number1.getDigits().length + 1; end++) {
            if (remainder.getSign() == 0) {
                subDivident = subArray(number1, start, end).abs();
            } else {
                subDivident = subArray(number1, start, end).abs().add(remainder.multiply(new HyperInteger("10")).abs());
            }

            HyperInteger subQuotient = new HyperInteger("10");
            HyperInteger tmp;
            do {
                subQuotient = subQuotient.subtract(new HyperInteger("1")); // subQuotient--
                tmp = number2.multiply(subQuotient).abs();
            } while (tmp.compareTo(subDivident.abs()) > 0);
            start = end;

            remainder = subDivident.abs().subtract(tmp);

            sq.append(subQuotient.toString());
        }

        HyperInteger quotient = new HyperInteger(sq.toString());
        quotient.setSign(number1.getSign() * number2.getSign());
        return ConversionService.stripLeadingZeros(quotient);
    }
}
