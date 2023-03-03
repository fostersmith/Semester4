package duckJousting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DuckJoustingClient {
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream out;
	
	public DuckJoustingClient(String address, int port) {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");
			
			input = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch(IOException e) {e.printStackTrace();}
		
	}
	
	public static void main(String[] args) {
		new DuckJoustingClient("127.0.0.1", 5000);
	}
}
