/*
 * Fábián Gábor
 * Copyright (c) 2022.
 * https://github.com/FabianGabor
 */

package com.fabiangabor;

import com.fabiangabor.hyperreal.HyperInteger;
import com.fabiangabor.hyperreal.HyperReal;

public class Main {
    public static void main(String[] args) {

        HyperReal number1;
        HyperReal number2;

        number1 = new HyperInteger("-98765432109876543210");
        number2 = new HyperInteger("-12345678901234567890");

        System.out.println(number1 + " + " + number2 + " = " + number1.add(number2));
        System.out.println(number1 + " - " + number2 + " = " + number1.subtract(number2));
        System.out.println(number1 + " * " + number2 + " = " + number1.multiply(number2));
        System.out.println(number1 + " / " + number2 + " = " + number1.divide(number2));


        number1 = new HyperInteger("531137992816767098689588206552468627329593117727031923199444138200403559860852242739162502265229285668889329486246501015346579337652707239409519978766587351943831270835393219031728127");
        number2 = new HyperInteger(
                "6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057151");

        System.out.println(number1.multiply(number2));
    }
}