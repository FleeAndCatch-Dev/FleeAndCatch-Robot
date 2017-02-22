package flee_and_catch.robot.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.ConnectionCommand;
import flee_and_catch.robot.communication.command.ConnectionCommandType;
import flee_and_catch.robot.communication.command.component.IdentificationType;
import flee_and_catch.robot.communication.command.component.RobotType;
import flee_and_catch.robot.communication.command.device.Device;
import flee_and_catch.robot.communication.command.identification.ClientIdentification;
import flee_and_catch.robot.configuration.CommunicationConfig;
import flee_and_catch.robot.view.ViewController;

public final class Client {
	
	private static boolean connected;
	private static ClientIdentification identification;
	private static Device device;
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
	public static void connect(IdentificationType pType, RobotType pSubtype){
		if(!connected){
			identification = new ClientIdentification(0, pType.toString(), CommunicationConfig.BACKEND_ADDRESS, CommunicationConfig.BACKEND_PORT);
			startConnection();
			return;
		}
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
	public static void connect(IdentificationType pType, RobotType pSubtype, String pAddress){
		if(!connected){
			identification = new ClientIdentification(0, pType.toString(), pAddress, CommunicationConfig.BACKEND_PORT);
			startConnection();
			return;
		}
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
	public static void connect(IdentificationType pType, RobotType pSubtype, String pAddress, int pPort){
		if(!connected){
			identification = new ClientIdentification(0, pType.toString(), pAddress, pPort);
			startConnection();
			return;
		}
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
	private static void startConnection() {
		Thread connectionThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<30;i++){
					if(connected)
						return;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						ViewController.showErrorScreen("207");
					}
				}
				ViewController.showErrorScreen("206");
			}
		});
		connectionThread.start();
		
		try {
			socket = new Socket(identification.getAddress(), identification.getPort());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			ViewController.showErrorScreen("202");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ViewController.showErrorScreen("203");
		}
		try {
			socket.setTcpNoDelay(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			ViewController.showErrorScreen("204");
		}
		Thread listenerThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					listen();
				} catch (Exception e) {
					ViewController.showErrorScreen("205");
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
	 * @throws IOException 
	 */
	private static void listen() throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new DataOutputStream(socket.getOutputStream());		
		connected = true;
		
		Gson gson = new Gson();
		ConnectionCommand command = new ConnectionCommand(CommandType.Connection.toString(), ConnectionCommandType.Connect.toString(), identification, device);
        sendCmd(gson.toJson(command));
		
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
	private static String receiveCmd() {
		char[] value = new char[4];
		try{
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
			close();
			return null;
		}
		catch (Exception e) {
			ViewController.showErrorScreen("205");
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
	public static void sendCmd(String pCommand){
		if(connected){
			checkCmd(pCommand);
			
			byte[] size = new byte[4];
			int rest = pCommand.length();
			for(int i=0; i<size.length; i++){
				size[size.length - (i + 1)] = (byte) (rest / Math.pow(128, size.length - (i + 1)));
				rest = (int) (rest % Math.pow(128, size.length - (i + 1)));
			}

			try {
				outputStream.write(size);
				outputStream.flush();
			
				outputStream.write(pCommand.getBytes());
				outputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ViewController.showErrorScreen("208");
			}
			return;
		}
	}
	
	/**
	 * <h1>Disconnect</h1>
	 * Disconnect the client from server and close all resources.
	 * 
	 * @throws IOException
	 * 
	 * @author ThunderSL94
	 */
	public static void disconnect() {
		try {
			bufferedReader.close();
			outputStream.close();
			connected = false;
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ViewController.showErrorScreen("209");
		}
	}
	
	/**
	 * <h1>Close</h1>
	 * Send a close command to the server for closing the session.
	 * 
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
	public static void close() {
		if(connected){
			Gson gson = new Gson();
			ConnectionCommand command = new ConnectionCommand(CommandType.Connection.toString(), ConnectionCommandType.Disconnect.toString(), identification, device);
			sendCmd(gson.toJson(command));
			return;
		}
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
		return device;
	}

	public static void setDevice(Device pDevice) {
		device = pDevice;
	}

	public static ClientIdentification getClientIdentification() {
		return identification;
	}
}
