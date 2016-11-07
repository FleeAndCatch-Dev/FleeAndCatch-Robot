//### Main.java ############################################################################################################################

package flee_and_catch.robot;

//### IMPORTS ##############################################################################################################################
import flee_and_catch.robot.controller.RobotController;

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
	public static void main(String[] args) throws InterruptedException {
		
		//Create a controller for a (default robot):
		RobotController rc = new RobotController(null, null);
		
		//Run a test:
		//rc.testRun1();
		//rc.runRandom();
		
	}

//##########################################################################################################################################	
}
//### EOF ##################################################################################################################################