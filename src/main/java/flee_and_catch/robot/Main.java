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
		RobotController rc = new RobotController(null, null);
		
		//Run a test:
		//rc.testRun1();
		//rc.runRandom();
	}

//##########################################################################################################################################	
}
//### EOF ##################################################################################################################################