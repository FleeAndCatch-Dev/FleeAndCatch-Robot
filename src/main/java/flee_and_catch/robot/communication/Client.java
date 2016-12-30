package flee_and_catch.robot.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import flee_and_catch.robot.Configuration;
import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.Connection;
import flee_and_catch.robot.communication.command.ConnectionType;
import flee_and_catch.robot.communication.identification.ClientIdentification;
import flee_and_catch.robot.component.IdentificationType;
import flee_and_catch.robot.component.RobotType;
import flee_and_catch.robot.robot.Robot;

public final class Client {
	
	private static boolean connected;
	private static ClientIdentification identification;
	private static Robot robot;
	private static Socket socket;
	private static BufferedReader bufferedReader;
	private static DataOutputStream outputStream;
	
	/**
	 * <h1>Connect</h1>
	 * Open a connection to the server. 
	 * 
	 * @throws Exception
	 *  * @author ThunderSL94
	 */
	public static void connect(IdentificationType pType, RobotType pSubtype) throws Exception{
		if(!connected){
			identification = new ClientIdentification(0, Configuration.address, Configuration.port, pType.toString());
			startConnection();
			return;
		}
		throw new Exception("Connection to server exist");
	}
	
	/**
	 * <h1>Connect</h1>
	 * Open a connection to the server. 
	 * 
	 * @param pAddress Ip address for communication.
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
	public static void connect(IdentificationType pType, RobotType pSubtype, String pAddress) throws Exception{
		if(!connected){
			identification = new ClientIdentification(0, pAddress, Configuration.port, pType.toString());
			startConnection();
			return;
		}
		throw new Exception("Connection to server exist");
	}
	
	/**
	 * <h1>Connect</h1>
	 * Open a connection to the server. 
	 * 
	 * @param pAddress Ip address for communication.
	 * @param pPort port for communication.
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
	public static void connect(IdentificationType pType, RobotType pSubtype, String pAddress, int pPort) throws Exception{
		if(!connected){
			identification = new ClientIdentification(0, pAddress, pPort, pType.toString());
			startConnection();
			return;
		}
		throw new Exception("Connection to server exist");
	}
	
	/**
	 * <h1>Start connection</h1>
	 * Start connection to the server an opens it in a new thread.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 * 
	 * @author ThunderSL94
	 */
	private static void startConnection() throws UnknownHostException, IOException{
		socket = new Socket(identification.getAddress(), identification.getPort());
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

	/**
	 * <h1>Listen</h1>
	 * Listen to the socket for new commands.
	 * 
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
	private static void listen() throws Exception {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new DataOutputStream(socket.getOutputStream());
		
		connected = true;
		while(connected){
			Interpreter.parse(receiveCmd());
		}
	}
	
	/**
	 * <h1>Receive command</h1>
	 * Receive json command as string from the server.
	 * 
	 * @return Command as string.
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
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

	/**
	 * <h1>Send command</h1>
	 * Send json command to server.
	 * 
	 * @param pCommand Command as json string.
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
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
	
	/**
	 * <h1>Disconnect</h1>
	 * Disconnect the client from server and close all resources.
	 * 
	 * @throws IOException
	 * 
	 * @author ThunderSL94
	 */
	public static void disconnect() throws IOException{
		bufferedReader.close();
		outputStream.close();
		connected = false;
		socket.close();
	}
	
	/**
	 * <h1>Close</h1>
	 * Send a close command to the server for closing the session.
	 * 
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
	public static void close() throws Exception {
		if(connected){
			Connection command = new Connection(CommandType.Connection.toString(), ConnectionType.Disconnect.toString(), identification);
			sendCmd(command.getCommand());
			return;
		}
		throw new Exception("There is no connection to the server");
	}
	
	/**
	 * <h1>Check command</h1>
	 * Check string of json syntax.
	 * 
	 * @param pCommand
	 * @return
	 * @throws JSONException
	 * 
	 * @author ThunderSL94
	 */
	private static JSONObject checkCmd(String pCommand) throws JSONException {
		return new JSONObject(pCommand);
	}

	public static boolean isConnected() {
		return connected;
	}

	public static Robot getRobot() {
		return robot;
	}

	public static void setRobot(Robot robot) {
		Client.robot = robot;
	}

	public static ClientIdentification getClientIdentification() {
		return identification;
	}
}
