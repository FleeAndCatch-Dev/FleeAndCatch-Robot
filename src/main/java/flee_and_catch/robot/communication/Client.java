package flee_and_catch.robot.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.connection.Connection;
import flee_and_catch.robot.communication.command.connection.ConnectionType;

public final class Client {
	
	private static Socket socket;
	private static BufferedReader bufferedReader;
	private static DataOutputStream outputStream;
	private static String address;
	private static int port;
	private static boolean connected;
	private static int id;
	private static String type = Type.Robot.toString();
	private static String subtype = "ThreeWheelDrive";
	
	public static void connect() throws Exception{
		if(!connected){
			address = Default.address;
			port = Default.port;
			startConnection();
			return;
		}
		throw new Exception("Connection to server exist");
	}
	
	public static void connect(String pAddress) throws Exception{
		if(!connected){
			address = pAddress;
			port = Default.port;
			startConnection();
			return;
		}
		throw new Exception("Connection to server exist");
	}
	
	public static void connect(String pAddress, int pPort) throws Exception{
		if(!connected){
			address = pAddress;
			port = pPort;
			startConnection();
			return;
		}
		throw new Exception("Connection to server exist");
	}
	
	private static void startConnection() throws UnknownHostException, IOException{
		socket = new Socket(address, port);
		Thread listenerThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					listen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		listenerThread.start();
	}

	private static void listen() throws Exception {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new DataOutputStream(socket.getOutputStream());
		
		connected = true;
		while(connected){
			Interpreter.parse(receiveCmd());
		}
	}
	
	private static String receiveCmd() throws Exception{
		char[] value = new char[4];
		int result = bufferedReader.read(value);	
		
		if(result >= 0) {
			int length = 0;
			for(int i = 0; i < value.length; i++) {
				length += (int) (value[i] * Math.pow(128, i));
			}
			
			value = new char[length];
			for( int i=0; i<value.length; i++) {
				char[] tmp = new char[1];
				bufferedReader.read(tmp, 0, 1);
				value[i] = tmp[0];
			}
			
			return new String(value);
		}
		else {
			close();
			return null;
		}
	}

	public static void sendCmd(String pCommand) throws Exception{
		if(connected){
			checkCmd(pCommand);
			
			byte[] size = new byte[4];
			int rest = pCommand.length();
			for(int i=0; i<size.length; i++){
				size[size.length - (i + 1)] = (byte) (rest / Math.pow(128, size.length - (i + 1)));
				rest = (int) (rest % Math.pow(128, size.length - (i + 1)));
			}

			outputStream.write(size);
			outputStream.flush();
			
			outputStream.write(pCommand.getBytes());
			outputStream.flush();
			return;
		}
		throw new Exception("Send of new command failed");
	}
	
	public static void disconnect() throws IOException{
		bufferedReader.close();
		outputStream.close();
		connected = false;
		socket.close();
	}
	
	public static void close() throws Exception {
		if(connected){
			Connection command = new Connection(CommandType.Type.Connection.toString(), ConnectionType.Type.Disconnect.toString(), new flee_and_catch.robot.communication.command.connection.Client(id, type, subtype));
			sendCmd(command.GetCommand());
			return;
		}
		throw new Exception("There is no connection to the server");
	}
	
	private static JSONObject checkCmd(String pCommand) throws JSONException {
		return new JSONObject(pCommand);
	}

	public static boolean isConnected() {
		return connected;
	}

	public static int getId() {
		return id;
	}
	public static void setId(int id) {
		Client.id = id;
	}

	public static String getType() {
		return type;
	}

	public static String getSubtype() {
		return subtype;
	}
}
