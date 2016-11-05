//### Main.java ############################################################################################################################

package flee_and_catch.robot;

import java.io.IOException;
import java.net.UnknownHostException;

import flee_and_catch.robot.client.Client;
import flee_and_catch.robot.client.json.JSONObject;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.internal.ev3.EV3Key;
import lejos.internal.ev3.EV3Keys;
//### IMPORTS ##############################################################################################################################
//import flee_and_catch.robot.controller.RobotController;

/* Main [Class]: Main class of the application *//**
 * 
 * @author Manuel Bothner
 *
 */
public class Main {

//### METHODS ##############################################################################################################################
	
	/* main [Method]: Start point of the application *//**
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {
		/*
		//Create a controller for a (default robot):
		RobotController rc = new RobotController();
		//Run a test:
		rc.testRun();
		*/
		
		Client client = new Client();
		try {
			client.connect();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
		
		//try {		
		//JSONObject jsonObject = new JSONObject();
		/*jsonObject.
		jsonObject.append("type", "robot");
		jsonObject.append("id", null);
		String data = jsonObject.toString();
		
		client.sendCommand(data);*/
		/*}
		catch (JSONException e) {
		// TODO: handle exception
		}*/
		
		/*try {		
			JSONObject jsonObject = new JSONObject();
			jsonObject.append("type", "robot");
			jsonObject.append("id", null);
			String data = jsonObject.toString();
			
			client.sendCommand(data);
		}
		catch (JSONException e) {
			// TODO: handle exception
		}*/
	}

//##########################################################################################################################################	
}
//### EOF ##################################################################################################################################