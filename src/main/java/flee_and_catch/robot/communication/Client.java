package flee_and_catch.robot.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import flee_and_catch.robot.communication.exceptions.ConnectServer;
import flee_and_catch.robot.communication.exceptions.ParseCommand;
import json.JSONException;
import json.JSONObject;



public class Client {
	
	private Socket socket;
	private BufferedReader reader;
	private DataOutputStream outputStream;
	private boolean opened;
	private int id;
	
	public Client(){
		this.opened = false;
	}
	
	public void connect() throws UnknownHostException, IOException, ConnectServer{
		if(!opened){
			this.socket = new Socket(Default.address, Default.port);
			Thread listenerThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						listen();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseCommand e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ConnectServer e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			listenerThread.start();
			return;
		}
		throw new ConnectServer();
	}	
	public void connect(String pAddress) throws UnknownHostException, IOException, ConnectServer{
		if(!opened){
			this.socket = new Socket(pAddress, Default.port);
			Thread listenerThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						listen();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseCommand e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ConnectServer e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			listenerThread.start();
			return;
		}
		throw new ConnectServer();
	}
	public void connect(String pAddress, int pPort) throws UnknownHostException, IOException, ConnectServer{
		if(!opened){
			this.socket = new Socket(pAddress, pPort);
			Thread listenerThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						listen();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseCommand e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ConnectServer e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			listenerThread.start();
			return;
		}
		throw new ConnectServer();
	}
	
	private void listen() throws IOException, NumberFormatException, ParseCommand, JSONException, ConnectServer{
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new DataOutputStream(socket.getOutputStream());
		
		Interpreter interpreter = new Interpreter(this);
		this.id = Integer.valueOf(interpreter.interpret(receiveCmd(reader)));
		this.opened = true;
		
		while(opened){
			String command = receiveCmd(reader);
			interpreter.interpret(command);
		}
	}
	
	private String receiveCmd(BufferedReader pBufferedReader) throws IOException, JSONException, ConnectServer{
		char[] value = new char[4];
		int result = pBufferedReader.read(value);	
		
		if(result >= 0) {
			int length = 0;
			for(int i = 0; i < value.length; i++) {
				length += (int) (value[i] * Math.pow(128, i));
			}
			
			value = new char[length];
			for( int i=0; i<value.length; i++) {
				char[] tmp = new char[1];
				pBufferedReader.read(tmp, 0, 1);
				value[i] = tmp[0];
			}
			
			return new String(value);
		}
		else {
			close();
			return null;
		}
	}
	
	public void sendCmd(String pCommand) throws IOException, JSONException, ConnectServer{
		if(opened){
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
		throw new ConnectServer();
	}
	
	public void disconnect() throws IOException{
		this.reader.close();
		this.outputStream.close();
		this.opened = false;
		this.socket.close();
	}
	
	public void close() throws IOException, JSONException, ConnectServer{
		if(this.opened){
			sendCmd("{\"id\":\"Client\",\"type\":\"Disconnect\",\"apiid\":\"@@fleeandcatch@@\",\"errorhandling\":\"ignoreerrors\",\"client\":{\"id\":" + id + ",\"type\":\"" + "ThreeWheelRobot" + "\"}}");
			return;
		}
		throw new ConnectServer();
	}
	
	private JSONObject checkCmd(String pCommand) throws JSONException{
		JSONObject jsonObject = new JSONObject(pCommand);
		return jsonObject;
	}
	
	public boolean isOpened() {
		return opened;
	}
	
	public int getId() {
		return id;
	}
}
