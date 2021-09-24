public class HyperInteger implements Comparable<HyperInteger> {
	int sign;
	byte[] digits;
	
	public HyperInteger(String number) {
		parseString(number);
	}

	public HyperInteger(String number, int sign) {
		parseString(number);
		this.sign = sign;
	}

	private void parseString(String number) {
		setValue(number);
	}

	public void setValue(String number) {
		int cursor = 0;
		int numDigits;

		int sign = 1;
		int negative = number.lastIndexOf('-');
		int positive = number.lastIndexOf('+');
		if (negative >= 0) {
			sign = -1;
			cursor = 1;
		} else if (positive >= 0) {
			cursor = 1;
		}

		numDigits = number.length() - cursor;
		this.sign = sign;

		byte[] num = new byte[numDigits];
		for (int i = cursor; i < number.length(); i++) {
			num[i - cursor] = Byte.parseByte(String.valueOf(number.charAt(i)));
		}

		this.digits = num;
	}

	public HyperInteger add(HyperInteger number2) {
		if (this.sign >= 0 && number2.sign >= 0)
			return new HyperInteger(add(digits, number2.digits));
		if (this.sign < 0 && number2.sign < 0)
			return new HyperInteger(add(digits, number2.digits), -1);
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

	public HyperInteger substract(HyperInteger number2) {
		if (this.sign >= 0 && number2.sign >= 0) {
			return substract(this, number2);
		}
		/*
		if (this.sign >= 0 && number2.sign < 0 || this.sign < 0 && number2.sign >= 0)
			return new HyperInteger(substract(digits, number2.digits));
		if (this.sign < 0 && number2.sign < 0)
			return new HyperInteger(add(digits, number2.digits), -1);

		 */
		return null;
	}

	private HyperInteger substract(HyperInteger number1, HyperInteger number2) {
		HyperInteger diff = null;

		if (number1.compareTo(number2) < 0) {
			swap(number1, number2);
			diff = new HyperInteger(substract(number1.digits, number2.digits));
			diff.sign = -1;
			swap(number1, number2);
		}

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
			} else {
				carry = 0;
			}

			diff.append(localDiff);
		} while (carry > 0 || i < number1.length || j < number2.length);

		reverse(number1);
		reverse(number2);
		diff.reverse();
		stripLeadingZeros(diff);

		return diff.toString();
	}

	private void swap(HyperInteger number1, HyperInteger number2) {
		byte[] tmp = number1.digits;
		number1.digits = number2.digits;
		number2.digits = tmp;
	}

	private void stripLeadingZeros(StringBuilder diff) {
		while (diff.charAt(0) == '0') {
			diff.deleteCharAt(0);
		}
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
		if (sign < 0) {
			sb.insert(0, '-');
		}
		for (byte b : digits) {
			sb.append(b);
		}
		return sb.toString();
	}

	@Override
	public int compareTo(HyperInteger number2) {
		if (this.sign > number2.sign) return 1;
		if (this.sign == number2.sign) return 0;
		if (this.sign < number2.sign) return -1;

		if (this.digits.length < number2.digits.length) return -1;

		if (this.digits.length > number2.digits.length) return 1;

		int i = this.digits.length - 1;
		while (i >= 0) {
			if (this.digits[i] > number2.digits[i]) return 1;
			if (this.digits[i] < number2.digits[i]) return -1;
			i--;
		}

		return 0;
	}
}