/*
 * Fábián Gábor
 * Copyright (c) 2021.
 * https://github.com/FabianGabor
 */

import com.fabiangabor.hyperreal.HyperInteger;
import com.fabiangabor.hyperreal.HyperReal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TestHyperInteger {

    HyperReal underTest;

    @ParameterizedTest
    @ValueSource(strings = {"0", "00", "+0", "-0", "++0", "--0", "+-0"})
    void zeroShouldBeZero(String arg) {
        final String EXPECTED = "0";
        underTest = new HyperInteger(arg);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void numberShouldEqualToItsValue() {
        final String EXPECTED = "123";
        underTest = new HyperInteger(EXPECTED);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void bigNumberShouldEqualToItsValue() {
        final String EXPECTED = "987654321098765432109876543210987654321098765432109876543210";
        underTest = new HyperInteger(EXPECTED);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void positiveNumberSignShouldBeOne() {
        final int EXPECTED = 1;
        underTest = new HyperInteger("+123");

        assertEquals(EXPECTED, underTest.getSign());
    }

    @Test
    void negativeNumberSignShouldBeMinusOne() {
        final int EXPECTED = -1;
        underTest = new HyperInteger("-123");

        assertEquals(EXPECTED, underTest.getSign());
    }

    @Test
    void multiplePositiveSignShouldBeOne() {
        final int EXPECTED = 1;
        underTest = new HyperInteger("++123");

        assertEquals(EXPECTED, underTest.getSign());
    }

    @Test
    void multipleNegativeSignShouldBeMinusOne() {
        final int EXPECTED = -1;
        underTest = new HyperInteger("--123");

        assertEquals(EXPECTED, underTest.getSign());
    }

    @Test
    void compareToShouldReturnZeroWhenNumbersAreEqual() {
        final int EXPECTED = 0;
        underTest = new HyperInteger("123");
        HyperInteger underTest2 = new HyperInteger("123");

        assertEquals(EXPECTED, underTest.compareTo(underTest2));
    }

    @Test
    void compareToShouldReturnOneWhenFirstNumberIsBigger() {
        final int EXPECTED = 1;
        underTest = new HyperInteger("1234");
        HyperInteger underTest2 = new HyperInteger("123");

        assertEquals(EXPECTED, underTest.compareTo(underTest2));
    }

    @Test
    void compareToShouldReturnMinusOneWhenSecondNumberIsBigger() {
        final int EXPECTED = -1;
        underTest = new HyperInteger("123");
        HyperInteger underTest2 = new HyperInteger("1234");

        assertEquals(EXPECTED, underTest.compareTo(underTest2));
    }

    @Test
    void addShouldReturnSum() {
        final String EXPECTED = "124";
        HyperReal number1 = new HyperInteger("123");
        HyperReal number2 = new HyperInteger("1");
        underTest = number1.add(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void addShouldReturnSumWhenAddingBigNumbers() {
        final String EXPECTED = "111111111011111111100";
        HyperInteger number1 = new HyperInteger("98765432109876543210");
        HyperInteger number2 = new HyperInteger("12345678901234567890");
        underTest = number1.add(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void addShouldReturnSumWhenAddingNegativeNumbers() {
        final String EXPECTED = "-7";
        HyperInteger number1 = new HyperInteger("-2");
        HyperInteger number2 = new HyperInteger("-5");
        underTest = number1.add(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void addTwoBigNegativeNumbers() {
        final String EXPECTED = "-111111111011111111100";
        HyperInteger number1 = new HyperInteger("-98765432109876543210");
        HyperInteger number2 = new HyperInteger("-12345678901234567890");
        underTest = number1.add(number2);

        assertEquals(EXPECTED, underTest.toString());
    }


    @Test
    void subtractShouldReturnDifference() {
        final String EXPECTED = "122";
        HyperInteger number1 = new HyperInteger("123");
        HyperInteger number2 = new HyperInteger("1");
        underTest = number1.subtract(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void subtractShouldReturnDifferenceWhenSubtractingBigNumbers() {
        final String EXPECTED = "12345678901234567890";
        HyperInteger number1 = new HyperInteger("111111111011111111100");
        HyperInteger number2 = new HyperInteger("98765432109876543210");
        underTest = number1.subtract(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void subtractShouldReturnDifferenceWhenSubtractingNegativeNumbers() {
        final String EXPECTED = "3";
        HyperInteger number1 = new HyperInteger("-2");
        HyperInteger number2 = new HyperInteger("-5");
        underTest = number1.subtract(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void subtractTwoBigNegativeNumbers() {
        final String EXPECTED = "-12345678901234567890";
        HyperInteger number1 = new HyperInteger("-111111111011111111100");
        HyperInteger number2 = new HyperInteger("-98765432109876543210");
        underTest = number1.subtract(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void multiplyShouldReturnProductWhenMultiplyingTwoPositiveNumbers() {
        final String EXPECTED = "15129";
        HyperInteger number1 = new HyperInteger("123");
        HyperInteger number2 = new HyperInteger("123");
        underTest = number1.multiply(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
        /*
         * https://mae.ufl.edu/~uhk/QUICK-SEMI-PRIME-FACTORING.pdf
         */
    void multiplyShouldReturnProductWhenMultiplyingTwoReallyBigPositiveNumbers() {
        final String EXPECTED = "194920496263521028482429080527";
        HyperInteger number1 = new HyperInteger("289673451203483");
        HyperInteger number2 = new HyperInteger("672897345109469");
        underTest = number1.multiply(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void multiplyShouldReturnProductWhenMultiplyingTwoNegativeNumbers() {
        final String EXPECTED = "15129";
        HyperInteger number1 = new HyperInteger("-123");
        HyperInteger number2 = new HyperInteger("-123");
        underTest = number1.multiply(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void multiplyShouldReturnProductWhenMultiplyingOnePositiveOneNegativeNumber() {
        final String EXPECTED = "-15129";
        HyperInteger number1 = new HyperInteger("123");
        HyperInteger number2 = new HyperInteger("-123");
        underTest = number1.multiply(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    @Test
    void divideShouldReturnQuotientWhenDividingTwoPositiveNumbers() {
        final String EXPECTED = "1";
        HyperInteger number1 = new HyperInteger("123");
        HyperInteger number2 = new HyperInteger("123");
        try {
            underTest = number1.divide(number2);
            assertEquals(EXPECTED, underTest.toString());
        } catch (ArithmeticException e) {
            fail("Division by zero");
        }
    }

    @Test
        /*
         * https://mae.ufl.edu/~uhk/QUICK-SEMI-PRIME-FACTORING.pdf
         */
    void divideShouldReturnQuotientWhenDividingTwoBigPositiveNumbers() {
        final String EXPECTED = "672897345109469";
        HyperInteger number1 = new HyperInteger("194920496263521028482429080527");
        HyperInteger number2 = new HyperInteger("289673451203483");
        try {
            underTest = number1.divide(number2);
            assertEquals(EXPECTED, underTest.toString());
        } catch (ArithmeticException e) {
            fail("Division by zero");
        }
    }

    @Test
    void divideShouldThrowArithmeticExceptionWhenDividingByZero() {
        final Class<? extends ArithmeticException> EXPECTED = ArithmeticException.class;
        final String EXCEPTION_MESSAGE = "Division by 0";

        HyperInteger number1 = new HyperInteger("123");
        HyperInteger number2 = new HyperInteger("0");

        ArithmeticException result = assertThrows(ArithmeticException.class, () -> number1.divide(number2));

        assertThrows(EXPECTED, () -> number1.divide(number2));
        assertEquals(EXCEPTION_MESSAGE, result.getMessage());
    }
}
