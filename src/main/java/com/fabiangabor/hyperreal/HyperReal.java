package com.fabiangabor.hyperreal;

public interface HyperReal {

    HyperInteger add(HyperInteger x);

    HyperInteger subtract(HyperInteger x);

    HyperInteger multiply(HyperInteger x);

    HyperInteger divide(HyperInteger x);

    HyperInteger abs();

    int compareTo(HyperInteger x);

    /*

    byte[] getDigits();

    int getSign();

    void setSign(int i);

    void setDigits(byte[] digits);

     */
}