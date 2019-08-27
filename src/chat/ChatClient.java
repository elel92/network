package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static String SERVER_IP = "192.168.1.46";
	private static int SERVER_PORT = 9000;
	
	public static void main(String[] args) {
		Scanner sc = null;
		Socket socket = null;
		
		try {
			sc = new Scanner(System.in);
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			System.out.print("닉네임 >> ");
			String nickname = sc.nextLine();
			pw.println("join:"+nickname);
			pw.flush();
			
			new ChatClientThread(br).start();
			
			while(true) {
				System.out.print(">> ");
				String input = sc.nextLine();
				
				if(input.equals("quit")) {
					
					System.out.println("클라이언트로부터 연결 끊김");
					
					pw.println("quit");
					break;
				}
				
				pw.println("message:"+input);
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(sc != null) {
				sc.close();
			}
		}
	}
}
