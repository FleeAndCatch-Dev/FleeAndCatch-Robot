//### Program.java #########################################################################################################################

package flee_and_catch.robot;

import flee_and_catch.robot.communication.Client;
import lejos.hardware.Button;

//### IMPORTS ##############################################################################################################################
/*import flee_and_catch.robot.localisation.PlayingField;
import flee_and_catch.robot.robot.Robot;
import flee_and_catch.robot.robot.RobotController;
import flee_and_catch.robot.view.ViewController;*/


/* Program [Class]: Main class of the program *//**
 * 
 * @author Manuel Bothner
 * @author Simon Lang
 *
 */
public class Program {
	
//### PUBLIC METHODS #######################################################################################################################
	
	public static void main(String[] args) {
		
		try {
			Client.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Button.ESCAPE.waitForPressAndRelease();
		
		try {
			Client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*Configuration.applyConfigurations();
		
		ViewController viewController = new ViewController();
		
		//Get selected robot from the user:
		Robot robot = viewController.getSelectedRobot();
		
		//Get information about the field from the user over the app: (NOT IMPLEMENTED)
		PlayingField field = new PlayingField(1000, 1000);
		
		//Initialize a robot-controller with the selected robot and the playing field:
		RobotController robotController = new RobotController(robot, field);
		
		//Controller drives robot ...*/

	}

//##########################################################################################################################################
}
//### EOF ##################################################################################################################################
