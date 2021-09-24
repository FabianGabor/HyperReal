import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHyperInteger {
	@Test
	void testZero() {
		HyperInteger hyperInteger1 = new HyperInteger("0");
		assertEquals("0", hyperInteger1.toString());
		assertEquals(1, hyperInteger1.sign);
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
	void substractTwoNumbers() {
		assertEquals("3", new HyperInteger("5").substract(new HyperInteger("2")).toString());
		assertEquals("5", new HyperInteger("5").substract(new HyperInteger("0")).toString());
		assertEquals("0", new HyperInteger("0").substract(new HyperInteger("0")).toString());

		assertEquals("-5", new HyperInteger("0").substract(new HyperInteger("5")).toString());
		assertEquals("-519", new HyperInteger("0").substract(new HyperInteger("519")).toString());
		assertEquals("-2", new HyperInteger("5").substract(new HyperInteger("7")).toString());
		assertEquals("-7", new HyperInteger("5").substract(new HyperInteger("12")).toString());
		assertEquals("-14", new HyperInteger("15").substract(new HyperInteger("29")).toString());
		assertEquals("-85", new HyperInteger("15").substract(new HyperInteger("100")).toString());

		assertEquals("6", new HyperInteger("1").substract(new HyperInteger("-5")).toString());
		assertEquals("16", new HyperInteger("1").substract(new HyperInteger("-15")).toString());
		assertEquals("4", new HyperInteger("-1").substract(new HyperInteger("-5")).toString());
		assertEquals("-2", new HyperInteger("-17").substract(new HyperInteger("-15")).toString());
	}

	@Test
	void compareTo() {
		assertEquals(0, new HyperInteger("-1").compareTo(new HyperInteger("-1")));
		assertEquals(0, new HyperInteger("0").compareTo(new HyperInteger("0")));
		assertEquals(0, new HyperInteger("1").compareTo(new HyperInteger("1")));

		assertEquals(1, new HyperInteger("1").compareTo(new HyperInteger("-1")));
		assertEquals(1, new HyperInteger("1").compareTo(new HyperInteger("0")));
		assertEquals(1, new HyperInteger("-2").compareTo(new HyperInteger("-3")));
	}
}
