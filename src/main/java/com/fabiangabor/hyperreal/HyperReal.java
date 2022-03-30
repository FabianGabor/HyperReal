package com.fabiangabor.hyperreal;

public interface HyperReal {

    HyperReal add(HyperReal x);

    HyperReal subtract(HyperReal x);

    HyperReal multiply(HyperReal x);

    HyperReal divide(HyperReal x);

    HyperReal abs();

    int compareTo(HyperReal x);

}