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

import static com.fabiangabor.hyperreal.domain.HyperInteger.ZERO;
import static com.fabiangabor.hyperreal.service.HelperService.subArray;

public class DivideOperation implements Operation {
    @Override
    public HyperReal execute(HyperReal number1, HyperReal number2) {
        if (number1 instanceof HyperInteger && number2 instanceof HyperInteger) {
            return divide((HyperInteger) number1, (HyperInteger) number2);
        }

        throw new  IllegalArgumentException("Division is not supported for this type of numbers");
    }

    @NotNull
    private HyperReal divide(HyperInteger number1, HyperInteger number2) {
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

        return div(number1, number2);
    }

    private HyperReal div(HyperInteger number1, HyperInteger number2) {
        int start = 0;
        StringBuilder sq = new StringBuilder();
        HyperReal subDivident;
        HyperReal remainder = new HyperInteger(ZERO);

        for (int end = 1; end < number1.getDigits().length + 1; end++) {
            if (remainder.getSign() == 0) {
                subDivident = subArray(number1, start, end).abs();
            } else {
                subDivident = subArray(number1, start, end).abs().add(remainder.multiply(new HyperInteger("10")).abs());
            }

            HyperReal subQuotient = new HyperInteger("10");
            HyperReal tmp;
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
