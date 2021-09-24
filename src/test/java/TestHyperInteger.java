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
}
