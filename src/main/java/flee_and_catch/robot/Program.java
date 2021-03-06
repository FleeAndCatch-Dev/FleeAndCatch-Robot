//### Program.java #########################################################################################################################

package flee_and_catch.robot;

import com.google.gson.Gson;

import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.ExceptionCommand;
import flee_and_catch.robot.communication.command.ExceptionCommandType;
import flee_and_catch.robot.configuration.SoundConfig;
import flee_and_catch.robot.robot.Robot;
import flee_and_catch.robot.robot.RobotController;
import flee_and_catch.robot.robot.RobotType;
import flee_and_catch.robot.view.ViewController;
import lejos.hardware.Button;
import flee_and_catch.robot.communication.command.identification.IdentificationType;

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
		//Init the robot types
		RobotType.values();
		//Show a start screen for welcome
		ViewController.showStartScreen();
		//Get a robot of the user
		Robot robot = ViewController.getSelectedRobot();
		//SHows the initialization screen
		ViewController.showInitScreen();
		//Init the sensors of the robot
		robot.initComponents();
		//Set the choosen robot to the controller
		RobotController.setRobot(robot);
		
		Client.setDevice(robot.getJSONRobot());
		//Backend connection:
		
		//Connect client as type robot and subtype of the robot (e.g. three-wheel-drive):
		Client.connect(IdentificationType.Robot, RobotType.valueOf(robot.getIdentification().getSubtype()));					
		
		//Locate the field and generate an object of thet
		//TODO
		//TODO: Get position of the robot!!!
		//TODO: Get dimensions of the playing field!!!
		
		//while(RobotController.get)
		Button.ESCAPE.waitForPressAndRelease();
		
		//Tide up
		if(RobotController.getRobot().isActive()){
			RobotController.getRobot().stop();
			RobotController.changeActive(false);
			
			Gson gson = new Gson();
			ExceptionCommand cmd = new ExceptionCommand(CommandType.Exception.toString(), ExceptionCommandType.UnhandeldDisconnection.toString(), Client.getClientIdentification(), new flee_and_catch.robot.communication.command.exception.Exception(ExceptionCommandType.UnhandeldDisconnection.toString(), "Devie is disconnecting", Client.getDevice()));
			Client.sendCmd(gson.toJson(cmd));
		}
		
		Client.close();
	}

//##########################################################################################################################################
}
//### EOF ##################################################################################################################################
