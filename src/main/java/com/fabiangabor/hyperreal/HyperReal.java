/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal;

/**
 * HyperReal is a simple, fast, and powerful Java library for handling large numbers.
 */
public interface HyperReal {

    /**
     * Returns a HyperReal whose value is {@code (this + x)}.
     *
     * @param  x value to be added to this HyperReal.
     * @return {@code this + val}
     */
    HyperReal add(HyperReal x);

    /**
     * Returns a HyperReal whose value is {@code (this - x)}.
     *
     * @param  x value to be subtracted from this HyperReal.
     * @return {@code this - val}
     */
    HyperReal subtract(HyperReal x);

    /**
     * Returns a HyperReal whose value is {@code (this * x)}.
     *
     * @param  x value to be multiplied with this HyperReal.
     * @return {@code this * val}
     */
    HyperReal multiply(HyperReal x);

    /**
     * Returns a HyperReal whose value is {@code (this / x)}.
     *
     * @param  x value by which this HyperReal is to be divided.
     * @return {@code this / val}
     */
    HyperReal divide(HyperReal x);

    /**
     * Returns a HyperReal whose value is the absolute value of this HyperReal.
     * @return abs(this)
     */
    HyperReal abs();

    /**
     * Compares this HyperReal with the specified HyperReal. This
     * method is provided in preference to individual methods for each
     * of the six boolean comparison operators ({@literal <}, ==,
     * {@literal >}, {@literal >=}, !=, {@literal <=}).  The suggested
     * idiom for performing these comparisons is: {@code
     * (x.compareTo(y)} &lt;<i>op</i>&gt; {@code 0)}, where
     * &lt;<i>op</i>&gt; is one of the six comparison operators.
     *

     * @param  x HyperReal to which this HyperReal is to be compared.
     * @return -1, 0 or 1 as this HyperReal is numerically less than, equal
     *         to, or greater than {@code x}.
     */
    int compareTo(HyperReal x);

    /**
     * Compares this HyperReal with the specified HyperReal.
     * @param  x HyperReal to which this HyperReal is to be compared.
     * @return true if and only if this HyperReal is numerically bigger than the specified HyperReal.
     */
    boolean isBigger(HyperReal x);

    /**
     * Compares this HyperReal with the specified HyperReal.
     * @param  x HyperReal to which this HyperReal is to be compared.
     * @return true if and only if this HyperReal is numerically bigger than or equal to the specified HyperReal.
     */
    boolean isBiggerOrEqual(HyperReal x);

    /**
     * Compares this HyperReal with the specified HyperReal.
     * @param  x HyperReal to which this HyperReal is to be compared.
     * @return true if and only if this HyperReal is numerically smaller than the specified HyperReal.
     */
    boolean isSmaller(HyperReal x);

    /**
     * Compares this HyperReal with the specified HyperReal.
     * @param  x HyperReal to which this HyperReal is to be compared.
     * @return true if and only if this HyperReal is numerically smaller than or equal to the specified HyperReal.
     */
    boolean isSmallerOrEqual(HyperReal x);

    /**
     * Compares this HyperReal with the specified HyperReal.
     * @param  x HyperReal to which this HyperReal is to be compared.
     * @return true if and only if this HyperReal is numerically equal to the specified HyperReal.
     */
    boolean isEqual(HyperReal x);

    /**
     * Returns the sign of this HyperReal.
     * @return -1 for negative, 0 for zero, or 1 for positive.
     */
    int getSign();

    /**
     * Verifies if this HyperReal is positive.
     * @return true if and only if this HyperReal is positive.
     */
    boolean isPositive();

    /**
     * Verifies if this HyperReal is positive or zero.
     * @return true if and only if this HyperReal is positive or zero.
     */
    boolean isPositiveOrZero();

    /**
     * Verifies if this HyperReal is negative.
     * @return true if and only if this HyperReal is negative.
     */
    boolean isNegative();

    /**
     * Verifies if this HyperReal is negative or zero.
     * @return true if and only if this HyperReal is negative or zero.
     */
    boolean isNegativeOrZero();

    /**
     * Sets this HyperReal sign's to positive.
     */
    void setPositive();

    /**
     * Sets this HyperReal sign's to negative.
     */
    void setNegative();

    /**
     * Returns the number of of digits of this HyperReal.
     * @return int number of digits.
     */
    int getLength();

    /**
     * Returns the digit at the specified position.
     * @param  i position of the digit.
     * @return the digit at the specified position.
     */
    byte getDigit(int i);

}