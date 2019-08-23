package Thread;

public class MultiThreadex {

	public static void main(String[] args) {
		Thread t1 = new DigitThread();
		Thread t2 = new AlphaThread();
		
		t1.start();
		t2.start();
		

	}

}
