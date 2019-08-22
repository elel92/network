package echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static String SERVER_IP = "192.168.1.46";
	private static int SERVER_PORT = 8000;
	
	public static void main(String[] args) {
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			socket = new Socket();
			
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(inetSocketAddress);
			System.out.println("[서버] 연결됨 from  " + SERVER_IP + ":"+ socket.getLocalPort());
			
			while(true) {
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				System.out.print(">> ");
				String data = sc.nextLine();
				os.write(data.getBytes("UTF-8"));
				byte[] buffer = new byte[256];
				int readByteCount = is.read(buffer);
				
				if(data.equals("exit")) {
					readByteCount = -1;
				}
				
				if(readByteCount == -1) {
					System.out.println("[서버] 클라이언트로 부터 연결끊김");
					return;
				}
				
				data = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.println("<< " + data);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
