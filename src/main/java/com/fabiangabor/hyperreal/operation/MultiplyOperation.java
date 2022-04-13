/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;
import com.fabiangabor.hyperreal.domain.HyperReal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.MULTIPLICATION;
import static com.fabiangabor.hyperreal.constants.ExceptionMessageConstants.UNSUPPORTED_NUMBER;
import static com.fabiangabor.hyperreal.constants.NumberConstants.ONE;
import static com.fabiangabor.hyperreal.constants.NumberConstants.ZERO;

public class MultiplyOperation implements Operation {

    @Override
    public HyperReal execute(HyperReal number1, HyperReal number2) {
        if (number1 instanceof HyperInteger && number2 instanceof HyperInteger) {
            return getProduct((HyperInteger) number1, (HyperInteger) number2);
        }

        throw new IllegalArgumentException(String.format("%s %s", MULTIPLICATION, UNSUPPORTED_NUMBER));
    }

    private HyperReal getProduct(HyperInteger number1, HyperInteger number2) {

        final HyperReal zero = new HyperInteger(ZERO);
        final HyperReal one = new HyperInteger(ONE);

        if (number1.isEqual(zero) || number2.isEqual(zero)) {
            return zero;
        }
        if (number1.isEqual(one)) {
            return number2;
        }
        if (number2.isEqual(one)) {
            return number1;
        }

        HyperReal prod = multiply(number1.getDigits(), number2.getDigits());

        if (number1.getSign() != number2.getSign()) {
            prod.setNegative();
        } else {
            prod.setPositive();
        }

        return prod;
    }

    private HyperReal multiply(byte[] number1, byte[] number2) {
        List<List<Integer>> partialProducts = getPartialProducts(number1, number2);

        HyperReal sum = new HyperInteger(ZERO);
        HyperInteger tmp;

        for (List<Integer> digits : partialProducts) {
            Collections.reverse(digits);
            tmp = new HyperInteger();
            tmp.setDigits(new byte[digits.size()]);

            for (int j = 0; j < digits.size(); j++) {
                tmp.setDigit(j, digits.get(j).byteValue());
            }
            sum = sum.add(tmp);
        }

        return sum;
    }

    private List<List<Integer>> getPartialProducts(byte[] number1, byte[] number2) {
        List<List<Integer>> graph = IntStream.range(0, number2.length).<List<Integer>>mapToObj(i -> new ArrayList<>()).collect(Collectors.toList());

        for (int i = number2.length - 1; i >= 0; i--) {
            initGraphRow(graph, i, number2.length);

            int carry = multiplyDigitsAndGetCarry(number1, number2, graph, i);
            if (carry > 0) {
                graph.get(i).add(carry);
            }
        }

        return graph;
    }

    private int multiplyDigitsAndGetCarry(byte[] number1, byte[] number2, List<List<Integer>> graph, int i) {
        int carry = 0;

        for (int j = number1.length - 1; j >= 0; j--) {
            graph.get(i).add(getLocalProductLastDigit(carry, number1[j], number2[i]));
            carry = getCarry(carry, number1[j], number2[i]);
        }
        return carry;
    }

    private int getLocalProductLastDigit(int carry, byte number1, byte number2) {
        return (number2 * number1 + carry) % 10;
    }

    private int getCarry(int carry, byte number1, byte number2) {
        return (number2 * number1 + carry) / 10;
    }

    private void initGraphRow(List<List<Integer>> graph, int i, int n) {
        for (int k = i; k < n - 1; k++) {
            graph.get(i).add(0);
        }
    }
}
