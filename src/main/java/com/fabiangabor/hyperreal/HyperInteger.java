/*
 * Fábián Gábor
 * Copyright (c) 2021.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal;

import com.fabiangabor.hyperreal.operation.*;
import com.fabiangabor.hyperreal.service.ConversionService;
import com.fabiangabor.hyperreal.service.HelperService;

public class HyperInteger {
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

    private void setValue(String number) {
        HyperInteger hyperInteger = ConversionService.convertToHyperInteger(number);
        this.digits = hyperInteger.getDigits();
        this.sign = hyperInteger.getSign();
    }

    public HyperInteger add(HyperInteger number2) {
        Operation add = new AddOperation();
        return add.execute(this, number2);
    }

    public HyperInteger subtract(HyperInteger number2) {
        Operation subtract = new SubtractOperation();
        return subtract.execute(this, number2);
    }

    public HyperInteger multiply(HyperInteger number2) {
        Operation multiply = new MultiplyOperation();
        return multiply.execute(this, number2);
    }

    public HyperInteger divide(HyperInteger number2) throws ArithmeticException {
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

    public int compareTo(HyperInteger number2) {
        int returnValue = 0;

        returnValue = compareSigns(this, number2);
        if (returnValue != 0) return returnValue;

        returnValue = compareLenghts(this, number2);
        if (returnValue != 0) return returnValue;

        for (int i = 0; i < this.digits.length; i++) {
            if (HelperService.isNumber1BiggerThanNumber2(this.digits[i], number2.getDigits()[i])) {
                if (this.sign == 1) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (HelperService.isNumber1BiggerThanNumber2(number2.getDigits()[i], this.digits[i])) {
                if (this.sign == 1) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        return 0;
    }

    private int compareSigns(HyperInteger number1, HyperInteger number2) {
        if (HelperService.isNumber1BiggerThanNumber2(number1.getSign(), number2.getSign())) return 1;
        if (HelperService.isNumber1BiggerThanNumber2(number2.getSign(), number1.getSign())) return -1;
        return 0;
    }

    private int compareLenghts(HyperInteger number1, HyperInteger number2) {
        if (HelperService.isNumber1BiggerThanNumber2(number1.getDigits().length, number2.getDigits().length)) return 1;
        if (HelperService.isNumber1BiggerThanNumber2(number2.getDigits().length, number1.getDigits().length)) return -1;
        return 0;
    }

    public HyperInteger abs() {
        return new HyperInteger(this.toString(), 1);
    }


}