public class HyperInteger {
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
}