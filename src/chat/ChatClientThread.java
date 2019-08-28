package chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientThread extends Thread {
	private BufferedReader bufferedReader;
	
	public ChatClientThread(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				String data = bufferedReader.readLine();
				
				System.out.println("<< " + data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
