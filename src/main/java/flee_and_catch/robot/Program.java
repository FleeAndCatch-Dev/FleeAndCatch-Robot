package flee_and_catch.robot;

import java.io.IOException;
import java.net.UnknownHostException;

import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.exceptions.ConnectServer;
import lejos.hardware.Button;

public class Program {

	public static void main(String[] args) {
		
		Client client = new Client();
		
		try {
			client.connect();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectServer e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Button.ESCAPE.waitForPressAndRelease();
		
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
