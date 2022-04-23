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
                Arguments.of("111111111011111111100", "98765432109876543210", "12345678901234567890"),
                Arguments.of("-111111111011111111100", "-98765432109876543210", "-12345678901234567890")
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
        /*
         * https://www.dcode.fr/big-numbers-multiplication
         */
    void multiplyingLargePrimeNumbersShouldReturnProduct() {
        final HyperReal EXPECTED = new HyperInteger("27124085580330618743669333519443888621795861445863123864791733729049137533924077043680286713599162167670162887112070058451439820440159887040569671062108117688752038632732468543520419990460830482902878465085393139250182082028096972722440105169742313962261443667949869133661712656782238439534325248063445843860241579643956081150212205137739571395692359185640646275145010744309400590094490224776215277541598297316987151468646026576298992017330915076366989374237175965230892098046596026227531951574275836161526111862915117366125377236908241426405348411357742139290519764726621536093369907895386753530692038320880306121962248116567034455846477159777840955941168641890306103626942829692877101815955691286577111296576624255064948510753267748004573098963794190719439389546152722223253553061280083945859501740880406835588230330409797814559305972082881432022326852143304015892554085478026353754387844038556516568229075128366061578902261643957891376937149238828333808723806009099865364088422179535189529203658979814275332071324165101453063916412437978140566890724519305291311759059199786886343243415578211408864471507394807239639381019961690735497454734549761538037010744079407314400457304407485857604162055802086448321929932699069066903051000715739538363547807393096025237851500014666355539616399818745854965325056435193198058625759801564338452017310549098809697588363114863199865173037");

        /*
         * https://primes.utm.edu/curios/page.php?number_id=3104
         */
        HyperReal prime1 = new HyperInteger("488206692078974757239578751106501798481193064305790060593268437941627128786518531687782038367395832494215303395275266261062870163042743713567477092591560354372022298711585313960166069663510866751747594454428370134263085036017021235153166806087444438422225508307090473714775930968942163305842969968960054562144882959808916790127856236878924126832766246591681391552185769201675241103805718565160271127279144286915245849425395672583677793953258530912351358504190352840627804277371662423874093878957332436516983050363505381795724757049138580227182965779234414020354586369950286426688735805438813033123751256021943190348046084856883806613660223368049593031293490849051397554356373996859058494329846897");
        /*
         * https://primes.utm.edu/curios/page.php?number_id=916
         */
        HyperReal prime2 = new HyperInteger("55558610769601845084032117764963690338979187239694411954612197817642698758345854432054120170830714766464188007124699226386623422387769456601176448956639325334537116275182973025840249128269412580280436561380140570587351647498514970765269750764472034037444958854210834276380918003970357231651598262223096012857397343437198448323531581388309338876444647380924682260339504523027125448155104599046828313551545739370286071676941209229143837472675844264230173707541208165228259972996459002751062573298411402344252697111948207941349668125541583535558456937012199450299952011671765759602629849268239727951048247103741366554054627352274368579415801299207137433624252756056310758472298250621");

        underTest = prime1.multiply(prime2);

        assertEquals(EXPECTED, underTest);
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
