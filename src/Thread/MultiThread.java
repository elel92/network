package Thread;

public class MultiThread {
	public static void main(String[] args) {
		Thread digit = new DigitThread();
		
		digit.start();
		
		for(char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
