//### Program.java #########################################################################################################################

package flee_and_catch.robot;

import com.google.gson.Gson;

import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.ExceptionCommand;
import flee_and_catch.robot.communication.command.ExceptionCommandType;
import flee_and_catch.robot.communication.command.component.IdentificationType;
import flee_and_catch.robot.configuration.SoundConfig;
import flee_and_catch.robot.robot.Robot;
import flee_and_catch.robot.robot.RobotController;
import flee_and_catch.robot.view.ViewController;
import flee_and_catch.robot.communication.command.component.RobotType;

//### IMPORTS ##############################################################################################################################


/* Program [Class]: Main class of the program *//**
 * 
 * @author Manuel Bothner
 * @author Simon Lang
 *
 */
public class Program {
	
//### PUBLIC METHODS #######################################################################################################################
	
	public static void main(String[] args) {

		SoundConfig.applyConfigurations();
		
		//Show a start screen for welcome
		ViewController.showStartScreen();
		//Get a robot of the user
		Robot robot = ViewController.getSelectedRobot();
		//Set the choosen robot to the controller
		RobotController.setRobot(robot);
		
		Client.setDevice(robot.getJSONRobot());
		//Backend connection:
		try {
			//Connect client as type robot and subtype of the robot (e.g. three-wheel-drive):
			Client.connect(IdentificationType.Robot, RobotType.valueOf(robot.getIdentification().getSubtype()));
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Locate the field and generate an object of thet
		//TODO
		//TODO: Get position of the robot!!!
		//TODO: Get dimensions of the playing field!!!
		
		ViewController.showExit();
		
		//Tide up
		if(RobotController.getRobot().isActive()){
			Gson gson = new Gson();
			ExceptionCommand cmd = new ExceptionCommand(CommandType.Exception.toString(), ExceptionCommandType.UnhandeldDisconnection.toString(), Client.getClientIdentification(), new flee_and_catch.robot.communication.command.exception.Exception(ExceptionCommandType.UnhandeldDisconnection.toString(), "Devie is disconnecting", Client.getDevice()));
			try {
				Client.sendCmd(gson.toJson(cmd));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RobotController.changeActive(false);
		}
		
		try {
			Client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//##########################################################################################################################################
}
//### EOF ##################################################################################################################################
