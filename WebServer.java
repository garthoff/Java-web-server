import java.io.*;
import java.net.*;

public class WebServer {
	
	public static void main(String args[]) {
		WebServer webServer = new WebServer();
		webServer.start();
	}
	
	protected void start() {
		ServerSocket serverSocket;
		int port = 8080;
		
		System.out.println("(press ctrl-c to exit)");
		try {
			// create the main server socket
			serverSocket = new ServerSocket(port);
		
			for (;;){
				ClientHandler t1 = new ClientHandler(serverSocket);
				t1.start();
			}
		
		
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return;
		}
		
	}
	
	
}
