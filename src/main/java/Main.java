/*
 * Fábián Gábor
 * Copyright (c) 2021.
 * https://github.com/FabianGabor
 */

import com.fabiangabor.hyperreal.HyperInteger;

public class Main {
	public static void main(String[] args) throws Exception {
//		int a = -989;
//		int b = -9;
//		HyperInteger hyperInteger1 = new HyperInteger(String.valueOf(a));
//		HyperInteger hyperInteger2 = new HyperInteger(String.valueOf(b));
//
//		System.out.println(hyperInteger1 + " * " + hyperInteger2 + " = " + hyperInteger1.multiply(hyperInteger2));
//		System.out.println(hyperInteger1 + " / " + hyperInteger2 + " = " + (a/b) + " | " + hyperInteger1.divide(hyperInteger2));


//		HyperInteger number1 = new HyperInteger("-98765432109876543210");
//		HyperInteger number2 = new HyperInteger("-12345678901234567890");
//		System.out.println(number1 + " + " + number2 + " = " + number1.add(number2));


		HyperInteger number1 = new HyperInteger("6");
		HyperInteger number2 = new HyperInteger("3");
		try {
			number1.divide(number2);
		} catch (ArithmeticException e) {
		}
	}
}