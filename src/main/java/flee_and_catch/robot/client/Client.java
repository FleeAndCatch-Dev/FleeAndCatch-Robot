package flee_and_catch.robot.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	private Socket socket;
	private DataOutputStream outputStream;
	private Thread listenerThread;
	
	/**
	 * 
	 */
	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public void connect() {
		try {
			socket = new Socket(Default.address, Default.port);
			listenerThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listen();
				}
			});
			listenerThread.start();
		}
		catch(IOException ex) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 
	 * @param pAdress
	 * @param pPort
	 */
	public void connect(String pAdress, int pPort) {
		try {
			socket = new Socket(pAdress, pPort);
			listenerThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listen();
				}
			});
			listenerThread.start();
		}
		catch(IOException ex) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 
	 */
	private void listen() {
		String data;
		char[] value;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new DataOutputStream(socket.getOutputStream());
			while(true) {
				value = new char[4];
				reader.read(value);
				int length = 0;
				for(int i = 0; i < value.length; i++) {
					length += (int) (value[i] * Math.pow(2, (value.length - (i + 1))));
				}
				
				value = new char[length];
				int result = reader.read(value);
				
				data = new String(value);
				System.out.println(value);
			}
		}
		catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	public void sendCommand(String pCommand) {
		try {
			outputStream.writeInt(pCommand.length());
			outputStream.flush();
			outputStream.writeBytes(pCommand);
			outputStream.flush();
		}
		catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	public void close() {
		try {
			socket.close();
		}
		catch (IOException e) {
			// TODO: handle exception
		}
	}
}
