//### Robots.java ##########################################################################################################################

package flee_and_catch.robot.component;

import flee_and_catch.robot.robot.Robot;
import flee_and_catch.robot.robot.ThreeWheelDriveRobot;

//### IMPORTS ##############################################################################################################################

public enum RobotType {

//### ENUM ELEMENTS ########################################################################################################################
	
	//Here are all available robots are defined with their name and class:
	ThreeWheelDrive 	("ThreeWheelDrive", new ThreeWheelDriveRobot("ThreeWheelDrive")),
	FourWheelDrive  	("FourWheelDrive", null),
	ChainDrive 			("ChainDrive", null),
	Test				("Test", null);
	
//### ATTRIBUTES ###########################################################################################################################

	private String name;	//Name (shown on LCD etc.) of the robot!
	private Robot robot;	//Represents the real class of the robot!

//### CONSTRUCTORS #########################################################################################################################
	
	private RobotType(String name, Robot robot) {
		this.name = name;
		this.robot = robot;
	}
	
//### GETTER ###############################################################################################################################
	
	public String getName() {
		return this.name;
	}
	
	public Robot getRobot() {
		return this.robot;
	}
	
//##########################################################################################################################################	
}
//### EOF ##################################################################################################################################