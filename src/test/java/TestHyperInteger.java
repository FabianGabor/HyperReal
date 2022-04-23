/*
 * Fábián Gábor
 * Copyright (c) 2021.
 * https://github.com/FabianGabor
 */

import com.fabiangabor.hyperreal.HyperInteger;
import com.fabiangabor.hyperreal.HyperReal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TestHyperInteger {

    HyperReal underTest;

    @ParameterizedTest
    @ValueSource(strings = {"0", "00", "+0", "-0"})
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

    @ParameterizedTest
    @ValueSource(strings = {"++0", "--0", "+-0", "-+0"})
    void multipleSignsShouldThrowNumberFormatException(String arg) {
        final Class<? extends NumberFormatException> EXPECTED = NumberFormatException.class;

        assertThrows(EXPECTED, () -> underTest = new HyperInteger(arg));
    }


    @Test
    void isEqualShouldReturnTrueWhenNumbersAreEqual() {
        underTest = new HyperInteger("123");
        HyperInteger underTest2 = new HyperInteger("123");

        assertTrue(underTest.isEqual(underTest2));
    }

    @Test
    void isBiggerShouldReturnTrueWhenFirstNumberIsBigger() {
        underTest = new HyperInteger("1234");
        HyperInteger underTest2 = new HyperInteger("123");

        assertTrue(underTest.isBigger(underTest2));
    }

    @Test
    void isBiggerShouldReturnFalseWhenFirstNumberIsSmaller() {
        underTest = new HyperInteger("1233");
        HyperInteger underTest2 = new HyperInteger("1234");

        assertFalse(underTest.isBigger(underTest2));
    }

    @Test
    void isSmallerShouldReturnTrueWhenSecondNumberIsSmaller() {
        underTest = new HyperInteger("123");
        HyperInteger underTest2 = new HyperInteger("1234");

        assertTrue(underTest.isSmaller(underTest2));
    }

    @Test
    void isSmallerShouldReturnFalseWhenSecondNumberIsBigger() {
        underTest = new HyperInteger("1234");
        HyperInteger underTest2 = new HyperInteger("1233");

        assertFalse(underTest.isSmaller(underTest2));
    }

    private static Stream<Arguments> additionParameters() {
        return Stream.of(
                Arguments.of(0, 0, 0),
                Arguments.of(0, 1, 1),
                Arguments.of(1, 0, 1),
                Arguments.of(1, 1, 2),
                Arguments.of(1, -1, 0),
                Arguments.of(-1, 1, 0),
                Arguments.of(-1, -1, -2),
                Arguments.of(-1, 0, -1),
                Arguments.of(0, -1, -1),
                Arguments.of(123, 1, 124)
        );
    }

    @ParameterizedTest
    @MethodSource("additionParameters")
    void addShouldReturnSum(int num1, int num2, final int EXPECTED) {

        HyperInteger number1 = new HyperInteger(num1);
        HyperInteger number2 = new HyperInteger(num2);
        underTest = number1.add(number2);

        assertEquals(String.valueOf(EXPECTED), underTest.toString());
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
    void addShouldReturnSumWhenAddingBigNegativeNumbers() {
        final String EXPECTED = "-111111111011111111100";
        HyperInteger number1 = new HyperInteger("-98765432109876543210");
        HyperInteger number2 = new HyperInteger("-12345678901234567890");
        underTest = number1.add(number2);

        assertEquals(EXPECTED, underTest.toString());
    }

    private static Stream<Arguments> subtractionParameters() {
        return Stream.of(
                Arguments.of("-1", "-1", "0"),
                Arguments.of("-1", "0", "-1"),
                Arguments.of("-1", "1", "-2"),
                Arguments.of("0", "-1", "1"),
                Arguments.of("0", "0", "0"),
                Arguments.of("0", "1", "-1"),
                Arguments.of("1", "-1", "2"),
                Arguments.of("1", "0", "1"),
                Arguments.of("1", "1", "0"),
                Arguments.of("-123", "-1", "-122"),
                Arguments.of("-123", "1", "-124"),
                Arguments.of("123", "-1", "124"),
                Arguments.of("123", "1", "122"),
                Arguments.of("-1", "-123", "122"),
                Arguments.of("123", "123", "0"),
                Arguments.of("111111111011111111100", "98765432109876543210", "12345678901234567890" ),
                Arguments.of("-111111111011111111100", "-98765432109876543210", "-12345678901234567890" )
        );
    }


    @ParameterizedTest
    @MethodSource("subtractionParameters")
    void subtractShouldReturnDifference(String num1, String num2, final String EXPECTED) {
        HyperInteger number1 = new HyperInteger(num1);
        HyperInteger number2 = new HyperInteger(num2);
        underTest = number1.subtract(number2);

        assertEquals(EXPECTED, underTest.toString());
    }


    private static Stream<Arguments> multiplicationParameters() {
        return Stream.of(
                Arguments.of("0", "0", "0"),
                Arguments.of("0", "1", "0"),
                Arguments.of("1", "0", "0"),
                Arguments.of("10", "1", "10"),
                Arguments.of("1", "10", "10"),
                Arguments.of("-1", "10", "-10"),
                Arguments.of("-10", "1", "-10"),
                Arguments.of("-10", "-1", "10"),
                Arguments.of("10", "10", "100"),
                Arguments.of("-10", "-10", "100"),
                Arguments.of("123", "123", "15129"),
                Arguments.of("-123", "-123", "15129"),
                Arguments.of("-123", "123", "-15129"),
                Arguments.of("123", "-123", "-15129"),
                /*
                 * https://mae.ufl.edu/~uhk/QUICK-SEMI-PRIME-FACTORING.pdf
                 */
                Arguments.of("289673451203483", "672897345109469", "194920496263521028482429080527"),
                Arguments.of("-289673451203483", "-672897345109469", "194920496263521028482429080527"),
                Arguments.of("-289673451203483", "672897345109469", "-194920496263521028482429080527"),
                Arguments.of("289673451203483", "-672897345109469", "-194920496263521028482429080527")
                );
    }

    @ParameterizedTest
    @MethodSource("multiplicationParameters")
    void multiplyShouldReturnProduct(String num1, String num2, final String EXPECTED) {
        HyperInteger number1 = new HyperInteger(num1);
        HyperInteger number2 = new HyperInteger(num2);
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
