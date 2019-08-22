package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		try {
			while(true) {
				Scanner sc = new Scanner(System.in);
				
				System.out.print("> ");
				String dns = sc.nextLine();
				
				if(dns.equals("exit")) {
					break;
				}
				
				InetAddress[] inetAddresses = InetAddress.getAllByName(dns);
				
				for(InetAddress inetAddress : inetAddresses) {
					System.out.println(dns + " : " +inetAddress.getHostAddress());
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
