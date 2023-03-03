package duckJousting;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DuckJoustingServer {
	private Socket p1Sock, p2Sock;
	private ServerSocket server;
	private DataInputStream in1, in2;
	private DataOutputStream out1, out2;
	
	boolean w1Pressed, a1Pressed, d1Pressed, w2Pressed, a2Pressed, d2Pressed;
	
	// determines the buttons pressed by player 1 by accepting input from the client in the format "w_on" until the client sends "Over"
	private Thread input1 = new Thread() {
		@Override
		public void run() {
			try {
				String line = "a_off";
				while(!line.equals("Over")) {
					System.out.println(line);
					char key = line.charAt(0);
					String type = line.split("_")[0];
					if(type.equals("on")) {
						switch(key){
						case 'w':
							w1Pressed = true;
							break;
						case 'a':
							a1Pressed = true;
							break;
						case 'd':
							d1Pressed = true;
							break;
						}
					} else if(type.equals("off")) {
						switch(key){
						case 'w':
							w1Pressed = false;
							break;
						case 'a':
							a1Pressed = false;
							break;
						case 'd':
							d1Pressed = false;
							break;			
						}
					}
					line = in1.readUTF();
				}	
			} catch(IOException e) {
				quit("An IO Exception occurred for player 1");
			}
			quit("Player 1 quit");
		}
	};
	

	// determines the buttons pressed by player 2 by accepting input from the client in the format "w_on" until the client sends "Over"
	private Thread input2 = new Thread() {
		@Override
		public void run() {
			try {
				String line = "a_off";
				while(!line.equals("Over")) {
					char key = line.charAt(0);
					String type = line.split("_")[0];
					if(type.equals("on")) {
						switch(key){
						case 'w':
							w1Pressed = true;
							break;
						case 'a':
							a1Pressed = true;
							break;
						case 'd':
							d1Pressed = true;
							break;
						}
					} else if(type.equals("off")) {
						switch(key){
						case 'w':
							w1Pressed = false;
							break;
						case 'a':
							a1Pressed = false;
							break;
						case 'd':
							d1Pressed = false;
							break;			
						}
					}
					line = in1.readUTF();
				}	
			} catch(IOException e) {
				quit("An IO Exception occurred for player 1");
			}
			quit("Player 2 quit");
		}
	};

	public synchronized void quit(String msg) {
	}
	
	
	
	public DuckJoustingServer(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");
			
			System.out.println("Waiting for client 1...");
			p1Sock = server.accept();
			System.out.println("Client 1 connected!");
			
			in1 = new DataInputStream(new BufferedInputStream(p1Sock.getInputStream()));
			out1 = new DataOutputStream(new BufferedOutputStream(p1Sock.getOutputStream()));
			
			input1.start();
			
			System.out.println("Waiting for client 2...");
			p2Sock = server.accept();
			System.out.println("Client 2 connected!");

			in2 = new DataInputStream(new BufferedInputStream(p2Sock.getInputStream()));
			out2 = new DataOutputStream(new BufferedOutputStream(p2Sock.getOutputStream()));
			
			input2.start();
			
			
		} catch(IOException e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		new DuckJoustingServer(5000);
	}
}
