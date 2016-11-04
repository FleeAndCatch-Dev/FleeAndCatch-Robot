//### Main.java ############################################################################################################################

package flee_and_catch.robot;

import flee_and_catch.robot.client.Client;
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
		client.connect();
		
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