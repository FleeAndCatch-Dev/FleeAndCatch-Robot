//### Program.java #########################################################################################################################

package flee_and_catch.robot;

import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.command.Identification;
import flee_and_catch.robot.component.IdentificationType;

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
		
		//Backend connection:
		try {
			
			//Init connection to backend:
			
			//Client.connect(IdentificationType.Typ.Robot.toString(), RobotType.valueOf(robot.getType()).toString());
			//Connect client as type robot and subtype of the robot (e.g. three-wheel-drive):
			Client.connect(IdentificationType.Robot.toString(), robot.getType());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Get robot identification from client-module:
		Identification id = new Identification(Client.getId(), Client.getAddress(), Client.getPort(), Client.getType(), Client.getSubtype());
		//Save identification in the client class!!!???
		
		
		//TODO: Get position of the robot!!!
		//TODO: Get dimensions of the playing field!!!
		
		//Get information about the field from the user over the app: (NOT IMPLEMENTED)
		PlayingField field = new PlayingField(1000, 1000);
		
		//Initialize a robot-controller with the selected robot and the playing field:
		RobotController robotController = new RobotController(robot, field);
		
		//Controller drives robot ...*/

	}

//##########################################################################################################################################
}
//### EOF ##################################################################################################################################
