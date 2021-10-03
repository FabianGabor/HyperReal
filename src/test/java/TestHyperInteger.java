/*
 * Fábián Gábor
 * Copyright (c) 2021.
 * https://github.com/FabianGabor
 */

import com.fabiangabor.hyperreal.HyperInteger;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHyperInteger {
	@Test
	void testZero() {
		assertEquals("0", new HyperInteger("0").toString());
		assertEquals("0", new HyperInteger("00").toString());
		assertEquals("0", new HyperInteger("+0").toString());
		assertEquals("0", new HyperInteger("-0").toString());
		assertEquals("0", new HyperInteger("+++000").toString());
		assertEquals(0, new HyperInteger("00").sign);
	}

	@Test
	void testPositive() {
		HyperInteger hyperInteger1 = new HyperInteger("1");
		assertEquals("1", hyperInteger1.toString());
		assertEquals(1, hyperInteger1.sign);

		HyperInteger hyperInteger2 = new HyperInteger("987654321098765432109876543210987654321098765432109876543210");
		assertEquals("987654321098765432109876543210987654321098765432109876543210", hyperInteger2.toString());
		assertEquals(1, hyperInteger2.sign);
	}

	@Test
	void testNegative() {
		HyperInteger hyperInteger1 = new HyperInteger("-1");
		assertEquals("-1", hyperInteger1.toString());
		assertEquals(-1, hyperInteger1.sign);

		HyperInteger hyperInteger2 = new HyperInteger("-987654321098765432109876543210987654321098765432109876543210");
		assertEquals("-987654321098765432109876543210987654321098765432109876543210", hyperInteger2.toString());
		assertEquals(-1, hyperInteger2.sign);
	}

	@Test
	void compareTo() {
		assertEquals(0, new HyperInteger("-1").compareTo(new HyperInteger("-1")));
		assertEquals(0, new HyperInteger("0").compareTo(new HyperInteger("0")));
		assertEquals(0, new HyperInteger("1").compareTo(new HyperInteger("1")));

		assertEquals(1, new HyperInteger("1").compareTo(new HyperInteger("-1")));
		assertEquals(1, new HyperInteger("1").compareTo(new HyperInteger("0")));
		assertEquals(1, new HyperInteger("-2").compareTo(new HyperInteger("-3")));
		assertEquals(1, new HyperInteger("20").compareTo(new HyperInteger("11")));
	}

	@Test
	void addTwoPositiveNumbers() {
		HyperInteger hyperInteger1 = new HyperInteger("0");
		HyperInteger hyperInteger2 = new HyperInteger("2");
		HyperInteger hyperIntegerSum = hyperInteger1.add(hyperInteger2);

		assertEquals("2", hyperIntegerSum.toString());
		assertEquals(1, hyperIntegerSum.sign);
	}

	@Test
	void addTwoBigPositiveNumbers() {
		HyperInteger hyperInteger1 = new HyperInteger("98765432109876543210");
		HyperInteger hyperInteger2 = new HyperInteger("12345678901234567890");
		HyperInteger hyperIntegerSum = hyperInteger1.add(hyperInteger2);

		assertEquals("111111111011111111100", hyperIntegerSum.toString());
		assertEquals(1, hyperIntegerSum.sign);
	}

	@Test
	void addTwoNegativeNumbers() {
		HyperInteger hyperInteger1 = new HyperInteger("-2");
		HyperInteger hyperInteger2 = new HyperInteger("-5");
		HyperInteger hyperIntegerSum = hyperInteger1.add(hyperInteger2);

		assertEquals("-7", hyperIntegerSum.toString());
		assertEquals(-1, hyperIntegerSum.sign);
	}

	@Test
	void addTwoBigNegativeNumbers() {
		HyperInteger hyperInteger1 = new HyperInteger("-98765432109876543210");
		HyperInteger hyperInteger2 = new HyperInteger("-12345678901234567890");
		HyperInteger hyperIntegerSum = hyperInteger1.add(hyperInteger2);

		assertEquals("-111111111011111111100", hyperIntegerSum.toString());
		assertEquals(-1, hyperIntegerSum.sign);
	}

	@Test
	void addTwoNumbers() {
		for (int i = -100; i <= 100; i++) // +/- 10000-nél már több perc lehet a futásidő!
			for (int j = -100; j <= 100; j++) {
				assertEquals(String.valueOf(i + j), new HyperInteger(String.valueOf(i)).add(new HyperInteger(String.valueOf(j))).toString());
			}
	}

	@Test
	void substractTwoNumbers() {
		for (int i = -100; i <= 100; i++) // +/- 10000-nél már több perc lehet a futásidő!
			for (int j = -100; j <= 100; j++)
				assertEquals(String.valueOf(i - j), new HyperInteger(String.valueOf(i)).substract(new HyperInteger(String.valueOf(j))).toString());
	}

	@Test
	void multiply() {
		for (int i = -100; i <= 100; i++) // +/- 10000-nél már több perc lehet a futásidő!
			for (int j = -100; j <= 100; j++) {
				//System.out.println(i + " * " + j + " = " + i * j);
				assertEquals(String.valueOf(i * j), new HyperInteger(String.valueOf(i)).multiply(new HyperInteger(String.valueOf(j))).toString());
			}
	}

	@Test
	void divide() throws Exception {
		for (int i = -100; i <= 100; i++) // +/- 10000-nél már több perc lehet a futásidő!
			for (int j = -100; j <= 100; j++) {
				if (j != 0) {
					assertEquals(String.valueOf(i / j), new HyperInteger(String.valueOf(i)).divide(new HyperInteger(String.valueOf(j))).toString());
				}
			}
	}


	void addTwoBigNumbers() {
		String aString = "", bString = "";
		Random random = new Random();

		for (int i = 0; i < random.nextInt(1000000) + 1; i++)
			aString += random.nextInt(10);

		for (int i = 0; i < random.nextInt(1000000) + 1; i++)
			bString += random.nextInt(10);

		BigInteger sum = new BigInteger(aString).add(new BigInteger(bString));
		assertEquals(sum.toString(), new HyperInteger(aString).add(new HyperInteger(bString)).toString());
	}

	@Test
	void addTwoBigNumbersLoop() {
		for (int i = 0; i < 1000; i++)
			addTwoBigNumbers();
	}

	void substractTwoBigNumbers() {
		String aString = "", bString = "";
		Random random = new Random();

		for (int i = 0; i < random.nextInt(1000000) + 1; i++)
			aString += random.nextInt(10);

		for (int i = 0; i < random.nextInt(1000000) + 1; i++)
			bString += random.nextInt(10);

		BigInteger sum = new BigInteger(aString).subtract(new BigInteger(bString));
		assertEquals(sum.toString(), new HyperInteger(aString).substract(new HyperInteger(bString)).toString());
	}

	@Test
	void substractTwoBigNumbersLoop() {
		for (int i = 0; i < 1000; i++)
			substractTwoBigNumbers();
	}

	void multiplyTwoBigNumbers() {
		String aString = "", bString = "";
		Random random = new Random();

		for (int i = 0; i < random.nextInt(1000) + 1; i++)
			aString += random.nextInt(10);

		for (int i = 0; i < random.nextInt(1000) + 1; i++)
			bString += random.nextInt(10);

		BigInteger sum = new BigInteger(aString).multiply(new BigInteger(bString));
		assertEquals(sum.toString(), new HyperInteger(aString).multiply(new HyperInteger(bString)).toString());
	}



	@Test
	void multiplyTwoBigNumbersLoop() {
		int n = 100000;
		for (int i = 0; i < n; i++) {
			multiplyTwoBigNumbers();
		}
	}
}
