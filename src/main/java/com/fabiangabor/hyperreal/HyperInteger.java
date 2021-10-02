package com.fabiangabor.hyperreal;

import java.util.ArrayList;
import java.util.Collections;

public class HyperInteger implements Comparable<HyperInteger> {
	public int sign;
	byte[] digits;

	private HyperInteger() {
	}

	public HyperInteger(String number) {
		parseString(number);
	}

	public HyperInteger(String number, int sign) {
		parseString(number);
		if (number.equals("0"))
			this.sign = 0;
		else
			this.sign = sign;
	}

	private void parseString(String number) {
		setValue(number);
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
				cursor = 1;
			} else if (positive >= 0)
				cursor = 1;

			numDigits = number.length() - cursor;
			this.sign = sign;

			byte[] num = new byte[numDigits];
			for (int i = cursor; i < number.length(); i++) {
				num[i - cursor] = Byte.parseByte(String.valueOf(number.charAt(i)));
			}

			this.digits = num;
		}
	}

	public HyperInteger add(HyperInteger number2) {
		if (this.toString().equals("0")) return number2;
		if (number2.toString().equals("0")) return this;

		if (this.sign >= 0 && number2.sign >= 0)
			return new HyperInteger(add(digits, number2.digits));
		if (this.sign < 0 && number2.sign < 0)
			return new HyperInteger(add(digits, number2.digits), -1);

		// fentebb ellenőriztük az előjelek egyezését. Alább már különböző előjelűek a számok
		if (this.abs().compareTo(number2.abs()) == 0)
			return new HyperInteger("0");

		if (this.compareTo(number2) > 0)
			if (this.abs().compareTo(number2.abs()) > 0)
				return new HyperInteger(substract(this.digits, number2.digits));
			else
				return new HyperInteger(substract(number2.digits, this.digits), -1);
		if (this.compareTo(number2) < 0)
			if (this.abs().compareTo(number2.abs()) > 0)
				return new HyperInteger(substract(this.digits, number2.digits), -1);
			else
				return new HyperInteger(substract(number2.digits, this.digits));
		return null;
	}

	private String add(byte[] number1, byte[] number2) {
		StringBuilder sum = new StringBuilder();

		reverse(number1);
		reverse(number2);

		int i = 0, j = 0;
		int carry = 0;

		do {
			int localSum = 0;
			localSum += carry;

			if (i < number1.length) localSum += number1[i];
			if (j < number2.length) localSum += number2[j];

			i++;
			j++;

			if (localSum > 9) {
				localSum -= 10;
				carry = 1;
			} else
				carry = 0;

			sum.append(localSum);
		} while (carry > 0 || i < number1.length || j < number2.length);

		reverse(number1);
		reverse(number2);
		sum.reverse();

		return sum.toString();
	}

	public HyperInteger substract(HyperInteger number2) {
		if (this.compareTo(number2) == 0)
			return new HyperInteger("0");

		if (this.sign >= 0 && number2.sign >= 0)
			return substract(this, number2);

		if (this.sign < 0 && number2.sign < 0)
			if (this.abs().compareTo(number2.abs()) > 0)
				return new HyperInteger(substract(this.digits, number2.digits), -1);
			else
				return new HyperInteger(substract(number2.digits, this.digits), 1);

		if (this.compareTo(number2) > 0)
			return new HyperInteger(add(digits, number2.digits));
		if (this.compareTo(number2) < 0)
			return new HyperInteger(add(digits, number2.digits), -1);

		return null;
	}

	private HyperInteger substract(HyperInteger number1, HyperInteger number2) {
		HyperInteger diff;

		if (number1.compareTo(number2) < 0) {
			swap(number1, number2);
			diff = new HyperInteger(substract(number1.digits, number2.digits));
			diff.sign = -1;
			swap(number1, number2);
			return diff;
		}

		if (number1.compareTo(number2) == 0) {
			diff = new HyperInteger("0");
			return diff;
		}

		diff = new HyperInteger(substract(number1.digits, number2.digits));
		return diff;
	}

	private String substract(byte[] number1, byte[] number2) {
		StringBuilder diff = new StringBuilder();

		reverse(number1);
		reverse(number2);

		int i = 0, j = 0;
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
			} else
				carry = 0;

			diff.append(localDiff);
		} while (carry > 0 || i < number1.length || j < number2.length);

		reverse(number1);
		reverse(number2);
		diff.reverse();
		stripLeadingZeros(diff);

		return diff.toString();
	}

	public HyperInteger multiply(HyperInteger number2) {
		HyperInteger prod;
		if (this.toString().equals("0") || number2.toString().equals("0")) return new HyperInteger("0");
		if (this.toString().equals("1")) return number2;
		if (number2.toString().equals("1")) return this;

		if (this.abs().compareTo(number2.abs()) < 0) {
			swap(this, number2);
			prod = multiply(this.digits, number2.digits);
			swap(this, number2);
		} else {
			prod = multiply(this.digits, number2.digits);
		}
		if (this.sign != number2.sign) {
			prod.sign = -1;
		} else {
			prod.sign = 1;
		}

		return prod;
	}

	private HyperInteger multiply(byte[] number1, byte[] number2) {
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

		HyperInteger sum = new HyperInteger("0");
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

	public HyperInteger divide(HyperInteger number2) throws Exception {
		if (this.toString().equals("0")) return new HyperInteger("0");
		if (number2.toString().equals("0")) throw new Exception("Divison by 0");
		if (number2.toString().equals("1")) return this;
		if (this.compareTo(number2) == 0) return new HyperInteger("1");
		if (this.abs().compareTo(number2.abs()) == 0) return new HyperInteger("-1");
		if (this.abs().compareTo(number2.abs()) < 0) return new HyperInteger("0");
		if (this.digits.length == number2.digits.length && this.digits[0] / number2.digits[0] == 1) return new HyperInteger("1");

		return divide(this, number2);
	}

	private HyperInteger divide(HyperInteger number1, HyperInteger number2) {
		HyperInteger quotient;
		int start = 0;
		int end = 1;

		HyperInteger subDivident;
		{ // ide valami ciklus kell
			do {
				subDivident = subArray(number1, start, end);
				end++;
			} while (subDivident.abs().compareTo(number2.abs()) < 0);
			//start = end;

			HyperInteger subQuotient = new HyperInteger("10");
			HyperInteger tmp;
			do {
				subQuotient = subQuotient.substract(new HyperInteger("1"));
				tmp = number2.multiply(subQuotient).abs();
			} while (tmp.compareTo(subDivident.abs()) == 1);

			return subQuotient;
		}

		//return null;
	}

	private HyperInteger divide(byte[] number1, byte[] number2) {
		return null;
	}

	private void swap(HyperInteger number1, HyperInteger number2) {
		byte[] tmp = number1.digits;
		number1.digits = number2.digits;
		number2.digits = tmp;
	}

	private void stripLeadingZeros(StringBuilder diff) {
		while (diff.charAt(0) == '0' && diff.length() > 0)
			diff.deleteCharAt(0);
	}

	private HyperInteger stripLeadingZeros() {
		StringBuilder sb = new StringBuilder(this.toString());
		int i = (this.sign < 0) ? 1 : 0;
		while (sb.charAt(i) == '0' && sb.length() > 0)
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

	@Override
	public int compareTo(HyperInteger number2) {
		if (this.sign > number2.sign) return 1;
		if (this.sign < number2.sign) return -1;

		if (this.digits.length < number2.digits.length) return -1;

		if (this.digits.length > number2.digits.length) return 1;

		for (int i = 0; i < this.digits.length; i++) {
			if (this.digits[i] > number2.digits[i])
				if (this.sign == 1) return 1;
				else return -1;
			if (this.digits[i] < number2.digits[i])
				if (this.sign == 1) return -1;
				else return 1;
		}

		return 0;
	}

	public HyperInteger abs() {
		return new HyperInteger(this.toString(), 1);
	}

	public HyperInteger subArray(HyperInteger arr, int start, int end) {
		StringBuilder s = new StringBuilder();
		for (int i = start; i < end; i++) s.append(arr.digits[i]);
		return new HyperInteger(s.toString(), arr.sign);
	}
	public HyperInteger subArray(HyperInteger arr, int start, int end, int prepend) {
		StringBuilder s = new StringBuilder(prepend);
		for (int i = start; i < end; i++) s.append(arr.digits[i]);
		return new HyperInteger(s.toString(), arr.sign);
	}
}