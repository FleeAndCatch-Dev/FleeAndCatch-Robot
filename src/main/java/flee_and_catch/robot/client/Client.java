package flee_and_catch.robot.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.freedesktop.dbus.test.test;

import com.sun.org.apache.bcel.internal.util.Objects;

import flee_and_catch.robot.client.json.JSONException;
import flee_and_catch.robot.client.json.JSONObject;

public class Client {

	private int id;
	private Socket socket;
	private DataOutputStream outputStream;
	private Thread listenerThread;
	private boolean opened;
	
	/**
	 * <h1>Constructor</h1>
	 * Create an object of the class client.
	 * 
	 * @author ThunderSL94
	 */
	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * <h1>Connect to server</h1>
	 * Connect to the server with the default address and port.
	 * 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * 
	 * @author ThunderSL94
	 */
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(Default.address, Default.port);
		listenerThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					listen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		listenerThread.start();
	}
	
	/**
	 * <h1>Connect to server</h1>
	 * Connect to the server with the given address and port.
	 * 
	 * @param pAdress Address of the server
	 * @param pPort Port of the server
	 * 
	 * @throws IOException 
	 * @throws UnknownHostException
	 * 
	 * @author ThunderSL94
	 */
	public void connect(String pAdress, int pPort) throws UnknownHostException, IOException {
		socket = new Socket(pAdress, pPort);
		listenerThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					listen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		listenerThread.start();
	}
	
	/**
	 * <h1>Listen at socket</h1>
	 * Listen at the socket and receive commands.
	 * 
	 * @throws IOException 
	 * @throws JSONException 
	 * 
	 * @author ThunderSL94
	 */
	private void listen() throws IOException, JSONException {
		String data = null;
		char[] value = null;
		opened = true;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new DataOutputStream(socket.getOutputStream());
		
		while(opened) {
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
			
			JSONObject jsonObject = new JSONObject(data);
			
			if(Objects.equals(jsonObject.getString("id"), "Connection")) {
				if(Objects.equals(jsonObject.getString("type"), "SetId")) {
					jsonObject = jsonObject.getJSONObject("client");
					id = jsonObject.getInt("id");
				}
				else if(Objects.equals(jsonObject.getString("type"), "GetType")) {
					String jsonString = "{\"id\":\"Connection\",\"type\":\"SetType\",\"apiid\":\"@@fleeandcatch@@\",\"errorhandling\":\"ignoreerrors\",\"client\":{\"id\":" + id + ",\"type\":" + 1 + "}}";
					sendCommand(jsonString);
				}
			}
		}
		
		reader.close();
		outputStream.close();
	}
	
	/**
	 * <h1>Send command</h1>
	 * Send JSON command to the server
	 * 
	 * @param pCommand JSON command as string
	 * 
	 * @throws IOException
	 * 
	 * @author ThunderSL94
	 */
	public void sendCommand(String pCommand) throws IOException {
		/*byte[] value = new byte[4];
		int rest = 0;
		int quotient = pCommand.length();
		
		for(int i=0; i<value.length; i++) {
			rest = (int) (quotient % (Math.pow(256, value.length - (i + 1))));
			quotient = (int) (quotient / (Math.pow(256, value.length - (i + 1))));		
			
			value[value.length - (i + 1)] = (byte) rest;
		}*/
		
		outputStream.write(pCommand.length());
		outputStream.flush();
		
		outputStream.writeBytes(pCommand);
		outputStream.flush();
	}
	
	/**
	 * <h1>Close socket</h1>
	 * Close the socket of the client
	 * 
	 * @throws IOException
	 * 
	 * @author ThunderSL94
	 */
	public void close() throws IOException {
		opened = false;
		socket.close();
	}

	
	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public int getId() {
		return id;
	}

	public Socket getSocket() {
		return socket;
	}

	public DataOutputStream getOutputStream() {
		return outputStream;
	}

	public Thread getListenerThread() {
		return listenerThread;
	}
}
