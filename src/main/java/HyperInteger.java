import java.util.ArrayList;

public class HyperInteger {
	int sign;
	byte[] digits;

	private HyperInteger() {
	}

	public HyperInteger(String number) {
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