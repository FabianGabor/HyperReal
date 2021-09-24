public class Main {

	public static void main(String[] args) {
		HyperInteger hyperInteger1 = new HyperInteger(String.valueOf(1));
		HyperInteger hyperInteger2 = new HyperInteger(String.valueOf(-10));
		HyperInteger sum = hyperInteger1.add(hyperInteger2);
		HyperInteger sub = hyperInteger1.substract(hyperInteger2);

		System.out.println("hyperInteger1 = " + hyperInteger1);
		System.out.println("hyperInteger2 = " + hyperInteger2);
		System.out.println("sum = " + sum);

		System.out.println("hyperInteger1 = " + hyperInteger1);
		System.out.println("hyperInteger2 = " + hyperInteger2);
		System.out.println(hyperInteger1 + " - " + hyperInteger2 + " = " + sub);
	}
}