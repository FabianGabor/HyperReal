/*
 * Fábián Gábor
 * Copyright (c) 2021-2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal.domain;

import com.fabiangabor.hyperreal.operation.*;

import static com.fabiangabor.hyperreal.service.ConversionService.*;
import static com.fabiangabor.hyperreal.service.HelperService.*;

public class HyperInteger implements HyperReal{
    public static final String ZERO = "0";

    private int sign;
    private byte[] digits;

    public HyperInteger() {
    }

    public HyperInteger(String number) {
        setValue(number);
    }

    public HyperInteger(String number, int sign) {
        setValue(number);
        if (number.equals(ZERO))
            this.sign = 0;
        else
            this.sign = sign;
    }

    public HyperInteger(int number) {
        this(String.valueOf(number));
    }

    private void setValue(String number) {
        HyperInteger hyperInteger = convertToHyperInteger(number);
        this.digits = hyperInteger.getDigits();
        this.sign = hyperInteger.getSign();
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public byte[] getDigits() {
        return digits;
    }

    public void setDigits(byte[] digits) {
        this.digits = digits;
    }

    public HyperReal add(HyperReal number2) {
        Operation add = new AddOperation();
        return add.execute(this, number2);
    }

    public HyperReal subtract(HyperReal number2) {
        Operation subtract = new SubtractOperation();
        return subtract.execute(this, number2);
    }

    public HyperReal multiply(HyperReal number2) {
        Operation multiply = new MultiplyOperation();
        return multiply.execute(this, number2);
    }

    public HyperReal divide(HyperReal number2) throws ArithmeticException {
        Operation divide = new DivideOperation();
        return divide.execute(this, number2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (sign < 0)
            sb.insert(0, '-');
        for (byte b : digits)
            sb.append(b);
        return sb.toString();
    }

    public int compareTo(HyperReal number2) {
        int returnValue;

        returnValue = compareSigns(this, number2);
        if (returnValue != 0) return returnValue;

        returnValue = compareLenghts(this, number2);
        if (returnValue != 0) return returnValue;

        for (int i = 0; i < this.digits.length; i++) {
            if (this.digits[i] > number2.getDigits()[i]) {
                if (this.sign == 1) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (number2.getDigits()[i] > this.digits[i]) {
                if (this.sign == 1) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        return 0;
    }

    public HyperReal abs() {
        return new HyperInteger(this.toString(), 1);
    }

}