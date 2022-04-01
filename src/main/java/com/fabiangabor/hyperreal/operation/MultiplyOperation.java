/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.Constants;
import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.domain.HyperReal;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fabiangabor.hyperreal.domain.Constants.*;

public class MultiplyOperation implements Operation {
    @Override
    public HyperReal execute(HyperReal number1, HyperReal number2) {
        if (number1 instanceof HyperInteger && number2 instanceof HyperInteger) {
            return getProduct((HyperInteger) number1, (HyperInteger) number2);
        }

        throw new  IllegalArgumentException(String.format("%s %s", MULTIPLICATION, NUMBERS_NOT_SUPPORTED));
    }

    @NotNull
    private HyperReal getProduct(HyperInteger number1, HyperInteger number2) {
        HyperReal prod;

        if (number1.toString().equals(Constants.ZERO) || number2.toString().equals(Constants.ZERO)) return new HyperInteger(Constants.ZERO);
        if (number1.toString().equals(Constants.ONE)) return number2;
        if (number2.toString().equals(Constants.ONE)) return number1;

        prod = multiply(number1.getDigits(), number2.getDigits());

        if (number1.getSign() != number2.getSign()) {
            prod.setNegative();
        } else {
            prod.setPositive();
        }

        return prod;
    }

    private HyperReal multiply(byte[] number1, byte[] number2) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < number2.length; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = number2.length - 1; i >= 0; i--) {
            int carry = 0;

            for (int k = i; k < number2.length - 1; k++) {
                graph.get(i).add(0);
            }

            for (int j = number1.length - 1; j >= 0; j--) {
                graph.get(i).add((number2[i] * number1[j] + carry) % 10);
                carry = (number2[i] * number1[j] + carry) / 10;
            }
            if (carry > 0) {
                graph.get(i).add(carry);
            }
        }

        HyperReal sum = new HyperInteger(Constants.ZERO);
        for (List<Integer> integers : graph) {
            Collections.reverse(integers);
            HyperInteger tmp = new HyperInteger();
            tmp.setDigits(new byte[integers.size()]);
            for (int j = 0; j < integers.size(); j++) {
                tmp.setDigit(j, integers.get(j).byteValue());
            }
            sum = sum.add(tmp);
        }

        return sum;
    }
}
