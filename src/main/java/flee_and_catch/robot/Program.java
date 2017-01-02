//### Program.java #########################################################################################################################

package flee_and_catch.robot;

import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.command.component.IdentificationType;
import flee_and_catch.robot.communication.command.component.RobotType;
import flee_and_catch.robot.communication.command.device.Device;
//### IMPORTS ##############################################################################################################################
import flee_and_catch.robot.localisation.PlayingField;
import flee_and_catch.robot.robot.Robot;
import flee_and_catch.robot.robot.RobotController;
import flee_and_catch.robot.view.ViewController;


/* Program [Class]: Main class of the program *//**
 * 
 * @author Manuel Bothner
 * @author Simon Lang
 *
 */
public class Program {
	
//### PUBLIC METHODS #######################################################################################################################
	
	public static void main(String[] args) {
		
		//Apply configurations:
		Configuration.applyConfigurations();
		
		//Init view controller:
		ViewController viewController = new ViewController();
		
		//Get selected robot from the user:
		Robot robot = viewController.getSelectedRobot();
		
		//Tell client the robot:
		Client.setDevice((Device)robot.getJSONRobot());
		
		//Backend connection:
		try {
			
			//Init connection to backend:
			
			//Client.connect(IdentificationType.Typ.Robot.toString(), RobotType.valueOf(robot.getType()).toString());
			//Connect client as type robot and subtype of the robot (e.g. three-wheel-drive):
			Client.connect(IdentificationType.Robot, RobotType.valueOf(robot.getIdentification().getSubtype())); //set identification
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		//TODO: Get position of the robot!!!
		//TODO: Get dimensions of the playing field!!!
		
		//Get information about the field from the user over the app: (NOT IMPLEMENTED)
		PlayingField field = new PlayingField(1000, 1000);
		
		//Initialize a robot-controller with the selected robot and the playing field:
		new RobotController(robot, field);
		
		//Controller drives robot ...*/

	}

//##########################################################################################################################################
}
//### EOF ##################################################################################################################################
