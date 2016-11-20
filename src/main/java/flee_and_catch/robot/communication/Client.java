package flee_and_catch.robot.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import com.sun.xml.internal.ws.util.StringUtils;

import flee_and_catch.robot.communication.exceptions.ConnectServer;
import flee_and_catch.robot.communication.exceptions.ParseCommand;
import flee_and_catch.robot.communication.json.JSONException;
import flee_and_catch.robot.communication.json.JSONObject;
import sun.awt.CharsetString;
import sun.nio.cs.StandardCharsets;
import sun.security.util.UntrustedCertificates;

public class Client {
	
	private Socket socket;
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
					}
				}
			});
			listenerThread.start();
			return;
		}
		throw new ConnectServer();
	}
	
	private void listen() throws IOException, NumberFormatException, ParseCommand, JSONException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		
		Interpreter interpreter = new Interpreter(this);
		this.id = Integer.valueOf(interpreter.interpret(receiveCmd(reader), outputStream));
		this.opened = true;
		
		while(opened){
			String command = receiveCmd(reader);
			interpreter.interpret(command, outputStream);
		}
	}
	
	private String receiveCmd(BufferedReader pBufferedReader) throws IOException{
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
	
	public void sendCmd(DataOutputStream pOutputStream, String pCommand) throws IOException, JSONException{
		checkCmd(pCommand);
		
		byte[] size = new byte[4];
		int rest = pCommand.length();
		for(int i=0; i<size.length; i++){
			size[size.length - (i + 1)] = (byte) (rest / Math.pow(128, size.length - (i + 1)));
			rest = (int) (rest % Math.pow(128, size.length - (i + 1)));
		}

		pOutputStream.write(size);
		pOutputStream.flush();
		
		pOutputStream.write(pCommand.getBytes());
		pOutputStream.flush();
	}
	
	public void close() throws IOException{
		this.opened = false;
		socket.close();
	}
	
	private void checkCmd(String pCommand) throws JSONException{
		JSONObject jsonObject = new JSONObject(pCommand);
	}
	
	public boolean isOpened() {
		return opened;
	}
	
	public int getId() {
		return id;
	}
}
