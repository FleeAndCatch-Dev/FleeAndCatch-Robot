package flee_and_catch.robot.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONException;
import org.json.JSONObject;

import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.ConnectionCommand;
import flee_and_catch.robot.communication.command.ConnectionCommandType;
import flee_and_catch.robot.communication.command.component.IdentificationType;
import flee_and_catch.robot.communication.command.component.RobotType;
import flee_and_catch.robot.communication.command.device.Device;
import flee_and_catch.robot.communication.command.device.robot.Robot;
import flee_and_catch.robot.communication.command.identification.ClientIdentification;
import flee_and_catch.robot.configuration.CommunicationConfig;

public final class Client {
	
	private static boolean connected;
	private static ClientIdentification identification;
	private static Device device;
	private static Socket socket;
	private static BufferedReader bufferedReader;
	private static DataOutputStream outputStream;
	
	private static Lock idLock = new ReentrantLock();
	private static Lock deviceLock = new ReentrantLock();
	
	/**
	 * <h1>Connect</h1>
	 * Open a connection to the server. 
	 * 
	 * @throws Exception
	 *  * @author ThunderSL94
	 */
	public static void connect(IdentificationType pType, RobotType pSubtype) throws Exception{
		if(!connected){
			identification = new ClientIdentification(0, pType.toString(), CommunicationConfig.BACKEND_ADDRESS, CommunicationConfig.BACKEND_PORT);
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
			identification = new ClientIdentification(0, pType.toString(), pAddress, CommunicationConfig.BACKEND_PORT);
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
			identification = new ClientIdentification(0, pType.toString(), pAddress, pPort);
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
		
		ConnectionCommand command = new ConnectionCommand(CommandType.Connection.toString(), ConnectionCommandType.Connect.toString(), identification, device);
        sendCmd(command.getCommand());
		
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
			ConnectionCommand command = new ConnectionCommand(CommandType.Connection.toString(), ConnectionCommandType.Disconnect.toString(), identification, device);
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

	public static Device getDevice() {
		deviceLock.lock();
		Device temp = new Robot((Robot) device);
		deviceLock.unlock();
		return temp;
	}

	public static void setDevice(Device pDevice) {
		deviceLock.lock();
		device = pDevice;
		deviceLock.unlock();
	}

	public static ClientIdentification getClientIdentification() {
		idLock.lock();
		ClientIdentification temp = new ClientIdentification(identification);
		idLock.unlock();
		return temp;
	}
}
