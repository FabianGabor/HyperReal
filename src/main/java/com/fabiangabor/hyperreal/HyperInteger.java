/*
 * Fábián Gábor
 * Copyright (c) 2021.
 * https://github.com/FabianGabor
 */

package com.fabiangabor.hyperreal;

import java.util.ArrayList;
import java.util.Collections;

public class HyperInteger implements HyperReal {
    private int sign;
    private byte[] digits;

    private HyperInteger() {
    }

    public HyperInteger(String number) {
        setValue(number);
    }

    public HyperInteger(String number, int sign) {
        setValue(number);
        if (number.equals("0"))
            this.sign = 0;
        else
            this.sign = sign;
    }

    public int getSign() {
        return sign;
    }

    public byte[] getDigits() {
        return digits;
    }

    public void setValue(String number) {
        if (number.equals("0")) {
            this.sign = 0;
            this.digits = new byte[]{0};
        } else {
            int cursor = 0;
            int numDigits;
            int sign = 1;

            int negative = number.lastIndexOf('-');
            int positive = number.lastIndexOf('+');

            if (negative >= 0) {
                sign = -1;
                cursor = negative + 1;
            } else if (positive >= 0)
                cursor = positive + 1;

            number = stripLeadingZeros(number, cursor);

            numDigits = number.length() - cursor;
            this.sign = sign;

            byte[] num = new byte[numDigits];
            for (int i = cursor; i < number.length(); i++) {
                num[i - cursor] = Byte.parseByte(String.valueOf(number.charAt(i)));
            }

            this.digits = num;

            if (this.digits.length == 1 && this.digits[0] == 0)
                this.sign = 0;
        }
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public void setDigits(byte[] digits) {
        this.digits = digits;
    }

    public HyperReal add(HyperReal number2) {
        if (this.toString().equals("0")) return number2;
        if (number2.toString().equals("0")) return this;

        if (this.sign >= 0 && number2.getSign() >= 0) {
            return new HyperInteger(add(digits, number2.getDigits()));
        }
        if (this.sign < 0 && number2.getSign() < 0) {
            return new HyperInteger(add(digits, number2.getDigits()), -1);
        }

        // fentebb ellenőriztük az előjelek egyezését. Alább már különböző előjelűek a számok
        if (this.abs().compareTo(number2.abs()) == 0) {
            return new HyperInteger("0");
        }

        if (this.compareTo(number2) > 0) {
            if (this.abs().compareTo(number2.abs()) > 0) {
                return new HyperInteger(subtract(this.digits, number2.getDigits()));
            } else {
                return new HyperInteger(subtract(number2.getDigits(), this.digits), -1);
            }
        }
        if (this.compareTo(number2) < 0) {
            if (this.abs().compareTo(number2.abs()) > 0) {
                return new HyperInteger(subtract(this.digits, number2.getDigits()), -1);
            } else {
                return new HyperInteger(subtract(number2.getDigits(), this.digits));
            }
        }
        return null;
    }

    private String add(byte[] number1, byte[] number2) {
        StringBuilder sum = new StringBuilder();

        reverse(number1);
        reverse(number2);

        int i = 0;
        int j = 0;
        int carry = 0;

        do {
            int localSum = 0;
            localSum += carry;

            if (i < number1.length) {
                localSum += number1[i];
            }
            if (j < number2.length) {
                localSum += number2[j];
            }

            i++;
            j++;

            if (localSum > 9) {
                localSum -= 10;
                carry = 1;
            } else {
                carry = 0;
            }

            sum.append(localSum);
        } while (carry > 0 || i < number1.length || j < number2.length);

        reverse(number1);
        reverse(number2);
        sum.reverse();

        return sum.toString();
    }

    public HyperReal subtract(HyperReal number2) {
        if (this.compareTo(number2) == 0) {
            return new HyperInteger("0");
        }

        if (this.sign >= 0 && number2.getSign() >= 0) {
            return subtract(this, number2);
        }

        if (this.sign < 0 && number2.getSign() < 0) {
            if (this.abs().compareTo(number2.abs()) > 0) {
                return new HyperInteger(subtract(this.digits, number2.getDigits()), -1);
            } else {
                return new HyperInteger(subtract(number2.getDigits(), this.digits), 1);
            }
        }

        if (this.compareTo(number2) > 0) {
            return new HyperInteger(add(digits, number2.getDigits()));
        }
        if (this.compareTo(number2) < 0) {
            return new HyperInteger(add(digits, number2.getDigits()), -1);
        }

        return null;
    }

    private HyperReal subtract(HyperReal number1, HyperReal number2) {
        HyperInteger diff;

        if (number1.compareTo(number2) < 0) {
            swap(number1, number2);
            diff = new HyperInteger(subtract(number1.getDigits(), number2.getDigits()));
            diff.sign = -1;
            swap(number1, number2);
            return diff;
        }

        if (number1.compareTo(number2) == 0) {
            diff = new HyperInteger("0");
            return diff;
        }

        diff = new HyperInteger(subtract(number1.getDigits(), number2.getDigits()));
        return diff;
    }

    private String subtract(byte[] number1, byte[] number2) {
        StringBuilder diff = new StringBuilder();

        reverse(number1);
        reverse(number2);

        int i = 0;
        int j = 0;
        int carry = 0;

        do {
            int localDiff = 0;
            localDiff -= carry;

            if (i < number1.length) localDiff += number1[i];
            if (j < number2.length) localDiff -= number2[j];

            i++;
            j++;

            if (localDiff < 0) {
                localDiff += 10;
                carry = 1;
            } else {
                carry = 0;
            }

            diff.append(localDiff);
        } while (carry > 0 || i < number1.length || j < number2.length);

        reverse(number1);
        reverse(number2);
        diff.reverse();
        stripLeadingZeros(diff);

        return stripLeadingZeros(diff);
    }

    public HyperReal multiply(HyperReal number2) {
        HyperReal prod;
        if (this.toString().equals("0") || number2.toString().equals("0")) return new HyperInteger("0");
        if (this.toString().equals("1")) return number2;
        if (number2.toString().equals("1")) return this;

        if (this.abs().compareTo(number2.abs()) < 0) {
            swap(this, number2);
            prod = multiply(this.digits, number2.getDigits());
            swap(this, number2);
        } else {
            prod = multiply(this.digits, number2.getDigits());
        }
        if (this.sign != number2.getSign()) {
            prod.setSign(-1);
        } else {
            prod.setSign(1);
        }

        return prod;
    }

    private HyperReal multiply(byte[] number1, byte[] number2) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>(number2.length);
        for (int i = 0; i < number2.length; i++)
            graph.add(new ArrayList<>());

        for (int i = number2.length - 1; i >= 0; i--) {
            int carry = 0;

            for (int k = i; k < number2.length - 1; k++)
                graph.get(i).add(0);

            for (int j = number1.length - 1; j >= 0; j--) {
                graph.get(i).add((number2[i] * number1[j] + carry) % 10);
                carry = (number2[i] * number1[j] + carry) / 10;
            }
            if (carry > 0) graph.get(i).add(carry);
        }

        HyperReal sum = new HyperInteger("0");
        for (ArrayList<Integer> integers : graph) {
            Collections.reverse(integers);
            HyperInteger tmp = new HyperInteger();
            tmp.digits = new byte[integers.size()];
            for (int j = 0; j < integers.size(); j++)
                tmp.digits[j] = integers.get(j).byteValue();
            sum = sum.add(tmp);
        }

        return sum;
    }

    public HyperReal divide(HyperReal number2) throws ArithmeticException {
        if (this.toString().equals("0")) return new HyperInteger("0");
        if (number2.toString().equals("0")) throw new ArithmeticException("Division by 0");
        if (number2.toString().equals("1")) return this;
        if (number2.abs().toString().equals("1"))
            return new HyperInteger(this.toString(), this.sign * number2.getSign());
        if (this.compareTo(number2) == 0) return new HyperInteger("1");
        if (this.abs().compareTo(number2.abs()) == 0) return new HyperInteger("-1");
        if (this.abs().compareTo(number2.abs()) < 0) return new HyperInteger("0");
        if (this.digits.length == number2.getDigits().length && this.digits[0] / number2.getDigits()[0] == 1)
            return new HyperInteger("1", this.sign * number2.getSign());

        return divide(this, number2);
    }

    private HyperReal divide(HyperReal number1, HyperReal number2) {
        int start = 0;
        StringBuilder sq = new StringBuilder();
        HyperReal subDivident;
        HyperReal remainder = new HyperInteger("0");

        for (int end = 1; end < number1.getDigits().length + 1; end++) {
            if (remainder.getSign() == 0) {
                subDivident = subArray(number1, start, end).abs();
            } else {
                subDivident = subArray(number1, start, end).abs().add(remainder.multiply(new HyperInteger("10")).abs());
            }

            HyperReal subQuotient = new HyperInteger("10");
            HyperReal tmp;
            do {
                subQuotient = subQuotient.subtract(new HyperInteger("1")); // subQuotient--
                tmp = number2.multiply(subQuotient).abs();
            } while (tmp.compareTo(subDivident.abs()) > 0);
            start = end;

            remainder = subDivident.abs().subtract(tmp);

            sq.append(subQuotient.toString());
        }


        HyperInteger quotient = new HyperInteger(sq.toString());
        quotient.sign = number1.getSign() * number2.getSign();
        return quotient.stripLeadingZeros();
    }

    private void swap(HyperReal number1, HyperReal number2) {
        byte[] tmp = number1.getDigits();
        number1.setDigits(number2.getDigits());
        number2.setDigits(tmp);
    }

    private String stripLeadingZeros(StringBuilder diff) {
        while (diff.charAt(0) == '0' && diff.length() > 1)
            diff.deleteCharAt(0);
        return diff.toString();
    }

    private String stripLeadingZeros(String s, int i) {
        StringBuilder diff = new StringBuilder(s);
        while (diff.charAt(i) == '0' && diff.length() > i + 1)
            diff.deleteCharAt(i);
        return diff.toString();
    }

    private HyperReal stripLeadingZeros() {
        StringBuilder sb = new StringBuilder(this.toString());
        int i = (this.sign < 0) ? 1 : 0;
        while (sb.charAt(i) == '0' && sb.length() > 1)
            sb.deleteCharAt(i);
        return new HyperInteger(sb.toString());
    }

    private void reverse(byte[] num) {
        byte tmp;
        for (int i = 0; i < num.length / 2; i++) {
            tmp = num[i];
            num[i] = num[num.length - 1 - i];
            num[num.length - 1 - i] = tmp;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (sign < 0)
            sb.insert(0, '-');
        for (byte b : digits)
            sb.append(b);
        return sb.toString();
    }

    public int compareTo(HyperReal number2) {
        if (this.sign > number2.getSign()) return 1;
        if (this.sign < number2.getSign()) return -1;

        if (this.digits.length < number2.getDigits().length) return -1;

        if (this.digits.length > number2.getDigits().length) return 1;

        for (int i = 0; i < this.digits.length; i++) {
            if (this.digits[i] > number2.getDigits()[i]) {
                if (this.sign == 1) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (this.digits[i] < number2.getDigits()[i]) {
                if (this.sign == 1) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        return 0;
    }

    public HyperReal abs() {
        return new HyperInteger(this.toString(), 1);
    }

    public HyperReal subArray(HyperReal arr, int start, int end) {
        StringBuilder s = new StringBuilder();
        for (int i = start; i < end; i++) s.append(arr.getDigits()[i]);
        return new HyperInteger(s.toString(), arr.getSign());
    }
}