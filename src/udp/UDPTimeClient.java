package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPTimeClient {
	private static final String SERVER_IP = "192.168.1.46";
	
	public static void main(String[] args) {
		Scanner sc = null;
		DatagramSocket socket = null;
		
		try {
			//1. 키보드 연결
			sc = new Scanner(System.in);
			while(true) {
			//2. 소켓 생성
				socket = new DatagramSocket();
			
			//3. 사용자 입력 받기
				System.out.print(">> ");
				String message = sc.nextLine();
				
				if(message.equals("exit")) {
					break;
				}
				
				//4. 메시지 전송
				byte[] send_data = message.getBytes("UTF-8");
				DatagramPacket send_packet = new DatagramPacket(send_data, send_data.length, new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));
				socket.send(send_packet);
				
				//5. 메시지 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket); //블로킹
				
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				message = new String(data, 0, length, "UTF-8");
				System.out.println("<< " + message);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(sc != null) {
				sc.close();
			}
			
			if(socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
	}

}
