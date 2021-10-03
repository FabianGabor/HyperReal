/*
 * Fábián Gábor
 * Copyright (c) 2021.
 * https://github.com/FabianGabor
 */

import com.fabiangabor.hyperreal.HyperInteger;

public class Main {
	public static void main(String[] args) throws Exception {
		int a = -989;
		int b = -9;
		HyperInteger hyperInteger1 = new HyperInteger(String.valueOf(a));
		HyperInteger hyperInteger2 = new HyperInteger(String.valueOf(b));

		System.out.println(hyperInteger1 + " * " + hyperInteger2 + " = " + hyperInteger1.multiply(hyperInteger2));
		System.out.println(hyperInteger1 + " / " + hyperInteger2 + " = " + (a/b) + " | " + hyperInteger1.divide(hyperInteger2));
	}
}