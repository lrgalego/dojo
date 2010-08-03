import static org.junit.Assert.assertEquals;

import org.junit.Test;
// 0,1,2,3,4,5,6
// 0,1,1,2,3,5,8

public class FibonacciTest {

	@Test
	public void fibonacciOfZeroIsZero() {
		assertEquals(0, Fibonacci.of(0));
	}

	@Test
	public void fibonacciOfOneIsOne() {
		assertEquals(1, Fibonacci.of(1));
	}

	@Test
	public void fibonacciOfTwoIsOne() {
		assertEquals(1, Fibonacci.of(2));
	}

	@Test
	public void fibonacciOfFiveIsFive() {
		assertEquals(5, Fibonacci.of(5));
	}
	
	@Test
	public void fibonacciOfTirtyIs832040() throws Exception {
		System.out.println(Fibonacci.of(30));
	}

}

class Fibonacci {
	public static long of(int n) {
		if (n < 2) {
			return n;
		}
		long nMinus1 = 1;
		long nMinus2 = 0;
		for (int i = 1; i < n; i++) {
			long temp = nMinus1 + nMinus2;
			nMinus2 = nMinus1;
			nMinus1 = temp;
		}
		return nMinus1;
	}
}
