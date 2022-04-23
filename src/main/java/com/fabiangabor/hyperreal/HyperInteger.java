/*
 * Fábián Gábor
 * Copyright (c) 2021-2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal;

import com.fabiangabor.hyperreal.operation.*;

import java.util.Arrays;

import static com.fabiangabor.hyperreal.constants.EqualityConstants.EQUAL;
import static com.fabiangabor.hyperreal.constants.NumberConstants.*;
import static com.fabiangabor.hyperreal.service.ConversionService.convertToHyperInteger;
import static com.fabiangabor.hyperreal.service.HelperService.compareLenghts;
import static com.fabiangabor.hyperreal.service.HelperService.compareSigns;

public class HyperInteger implements HyperReal {

    private int sign;
    private byte[] digits;

    public HyperInteger() {
    }

    public HyperInteger(String number) {
        setValue(number);
    }

    public HyperInteger(String number, int sign) {
        setValue(number);
        if (number.equals(ZERO)) {
            this.sign = ZERO_SIGN_VAL;
        } else {
            this.sign = sign;
        }
    }

    public HyperInteger(int number) {
        this(String.valueOf(number));
    }

    private void setValue(String number) {
        HyperInteger hyperInteger = convertToHyperInteger(number);
        this.digits = hyperInteger.getDigits();
        this.sign = hyperInteger.getSign();
    }

    public byte[] getDigits() {
        return digits.clone();
    }

    public void setDigits(byte[] digits) {
        this.digits = digits;
    }

    @Override
    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    @Override
    public void setPositive() {
        this.sign = POSITIVE_SIGN_VAL;
    }

    @Override
    public void setNegative() {
        this.sign = NEGATIVE_SIGN_VAL;
    }

    @Override
    public int getLength() {
        return this.digits.length;
    }

    @Override
    public byte getDigit(int index) {
        return this.digits[index];
    }

    public void setDigit(int index, byte digit) {
        this.digits[index] = digit;
    }

    @Override
    public HyperReal add(HyperReal number2) {
        Operation add = new AddOperation();
        return add.execute(this, number2);
    }

    @Override
    public HyperReal subtract(HyperReal number2) {
        Operation subtract = new SubtractOperation();
        return subtract.execute(this, number2);
    }

    @Override
    public HyperReal multiply(HyperReal number2) {
        Operation multiply = new MultiplyOperation();
        return multiply.execute(this, number2);
    }

    @Override
    public HyperReal divide(HyperReal number2) throws ArithmeticException {
        Operation divide = new DivideOperation();
        return divide.execute(this, number2);
    }

    @Override
    public int compareTo(HyperReal number2) {
        int returnValue;

        returnValue = compareSigns(this, number2);
        if (returnValue != EQUAL) {
            return returnValue;
        }

        returnValue = compareLenghts(this, number2);
        if (returnValue != EQUAL) {
            return returnValue;
        }

        for (int i = 0; i < this.getLength(); i++) {
            if (this.getDigit(i) > number2.getDigit(i)) {
                return this.sign;
            }
            if (number2.getDigit(i) > this.getDigit(i)) {
                return this.sign * NEGATIVE_SIGN_VAL;
            }
        }

        return 0;
    }

    @Override
    public boolean isBigger(HyperReal x) {
        return this.compareTo(x) > 0;
    }

    @Override
    public boolean isBiggerOrEqual(HyperReal x) {
        return this.compareTo(x) >= 0;
    }

    @Override
    public boolean isSmaller(HyperReal x) {
        return this.compareTo(x) < 0;
    }

    @Override
    public boolean isSmallerOrEqual(HyperReal x) {
        return this.compareTo(x) <= 0;
    }

    @Override
    public boolean isEqual(HyperReal x) {
        return this.compareTo(x) == 0;
    }

    @Override
    public boolean isPositive() {
        return this.sign == POSITIVE_SIGN_VAL;
    }

    @Override
    public boolean isPositiveOrZero() {
        return this.sign >= ZERO_SIGN_VAL;
    }

    @Override
    public boolean isNegative() {
        return this.sign == NEGATIVE_SIGN_VAL;
    }

    @Override
    public boolean isNegativeOrZero() {
        return this.sign <= ZERO_SIGN_VAL;
    }

    public HyperReal abs() {
        return new HyperInteger(this.toString(), POSITIVE_SIGN_VAL);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (sign < ZERO_SIGN_VAL) {
            sb.insert(0, NEGATIVE_SIGN);
        }
        for (byte b : digits) {
            sb.append(b);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HyperInteger that = (HyperInteger) o;

        if (sign != that.sign) return false;
        return Arrays.equals(digits, that.digits);
    }

    @Override
    public int hashCode() {
        int result = sign;
        result = 31 * result + Arrays.hashCode(digits);
        return result;
    }
}