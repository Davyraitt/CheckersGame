package menuLayout;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private String hostname;
	private int port;
	private boolean isConnected = true;
	
	
	public Client(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	public void connect(String nickName) {
		System.out.println("Connecting to server: " + this.hostname + " on port " + this.port);
		
		
		try {
			Socket socket = new Socket(this.hostname, this.port);
			
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			out.writeUTF(nickName);
			
			System.out.println("You are now connected as " + nickName);
			
			//boolean isRunning = true;
			
			Thread readSocketThread = new Thread( () -> {
				receiveDataFromSocket(in);
			});
			
			readSocketThread.start();
			
			
			isConnected = false;
			socket.close();
			try {
				readSocketThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void receiveDataFromSocket(DataInputStream in) {
		String received = "";
		while (isConnected) {
			
			try {
				received = in.readUTF();
				System.out.println(received);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void writeStringToSocket(Socket socket, String text) {
		
		try {
			socket.getOutputStream().write(text.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
