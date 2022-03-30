/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal;import com.fabiangabor.hyperreal.domain.HyperInteger;

public class Main {
	public static void main(String[] args)  {

		HyperInteger number1;
		HyperInteger number2;

		number1 = new HyperInteger("-98765432109876543210");
		number2 = new HyperInteger("-12345678901234567890");

		System.out.println(number1 + " + " + number2 + " = " + number1.add(number2));
		System.out.println(number1 + " - " + number2 + " = " + number1.subtract(number2));
		System.out.println(number1 + " * " + number2 + " = " + number1.multiply(number2));
		System.out.println(number1 + " / " + number2 + " = " + number1.divide(number2));

	}
}