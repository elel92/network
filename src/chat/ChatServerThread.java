package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	private List<Writer> listWriters;
	
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	private void doJoin(String nickname, PrintWriter writer) {
		this.nickname = nickname;
		String data = nickname + "님이 참여하였습니다.";
		broadcast(data);
		
		addWriter(writer);
		
		writer.println("join:ok");
		writer.flush();
	}
	
	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}
	
	private void broadcast(String data) {
		synchronized (listWriters) {
			for(Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
	
	@Override
	public void run() {
		try {
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream(), StandardCharsets.UTF_8 ) );
			PrintWriter printWriter = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8 ), true );
			
			while(true) {
				String request = bufferedReader.readLine();
				
				if(request == null) {
					ChatServer.log("클라이언트로부터 연결 끊김");
					break;
				}
				
				String[] tokens = request.split(":");
				
				if(tokens[0].equals("join")) {
					doJoin(tokens[1], printWriter);
				} else if(tokens[0].equals("message")) {
					doMessage(tokens[1]);
				} else if(tokens[0].equals("quit")) {
					doQuit(printWriter);
				} else {
					ChatServer.log("에러 : 알수 없는 요청" + tokens[0] + ")");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doMessage(String message) {
		broadcast(message);
	}
	
	private void doQuit(PrintWriter writer) {
		removeWriter(writer);
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}

	private void removeWriter(PrintWriter writer) {
		listWriters.remove(writer);
	}
}
