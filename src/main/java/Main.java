/*
 * Fábián Gábor
 * Copyright (c) 2021.
 * https://github.com/FabianGabor
 */

import com.fabiangabor.hyperreal.HyperInteger;

public class Main {

	public static void main(String[] args) {
		HyperInteger hyperInteger1 = new HyperInteger(String.valueOf(89));
		HyperInteger hyperInteger2 = new HyperInteger(String.valueOf(89));
/*		HyperInteger sum = hyperInteger1.add(hyperInteger2);
		HyperInteger sub = hyperInteger1.substract(hyperInteger2);

		System.out.println("hyperInteger1 = " + hyperInteger1);
		System.out.println("hyperInteger2 = " + hyperInteger2);
		System.out.println("sum = " + sum);

		System.out.println("hyperInteger1 = " + hyperInteger1);
		System.out.println("hyperInteger2 = " + hyperInteger2);
		System.out.println(hyperInteger1 + " - " + hyperInteger2 + " = " + sub);
 */
		System.out.println(hyperInteger1 + " * " + hyperInteger2 + " = " + hyperInteger1.multiply(hyperInteger2));
	}
}