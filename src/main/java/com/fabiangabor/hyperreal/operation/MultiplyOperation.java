/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.operation;

import com.fabiangabor.hyperreal.domain.HyperInteger;

import java.util.ArrayList;
import java.util.Collections;

import static com.fabiangabor.hyperreal.domain.HyperInteger.ZERO;
import static com.fabiangabor.hyperreal.service.HelperService.*;

public class MultiplyOperation implements Operation {
    @Override
    public HyperInteger execute(HyperInteger number1, HyperInteger number2) {
        HyperInteger prod;

        if (number1.toString().equals(ZERO) || number2.toString().equals(ZERO)) return new HyperInteger(ZERO);
        if (number1.toString().equals("1")) return number2;
        if (number2.toString().equals("1")) return number1;

        if (number1.abs().compareTo(number2.abs()) < 0) {
            swap(number1, number2);
            prod = multiply(number1.getDigits(), number2.getDigits());
            swap(number1, number2);
        } else {
            prod = multiply(number1.getDigits(), number2.getDigits());
        }
        if (number1.getSign() != number2.getSign()) {
            prod.setSign(-1);
        } else {
            prod.setSign(1);
        }

        return prod;
    }

    private HyperInteger multiply(byte[] number1, byte[] number2) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>(number2.length);
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

        HyperInteger sum = new HyperInteger(ZERO);
        for (ArrayList<Integer> integers : graph) {
            Collections.reverse(integers);
            HyperInteger tmp = new HyperInteger();
            tmp.setDigits(new byte[integers.size()]);
            for (int j = 0; j < integers.size(); j++) {
                tmp.getDigits()[j] = integers.get(j).byteValue();
            }
            sum = sum.add(tmp);
        }

        return sum;
    }
}
