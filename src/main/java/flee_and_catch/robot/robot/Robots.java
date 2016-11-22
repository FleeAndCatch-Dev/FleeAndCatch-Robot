//### Robots.java ##########################################################################################################################

package flee_and_catch.robot.robot;

//### IMPORTS ##############################################################################################################################

public enum Robots {

//### ENUM ELEMENTS ########################################################################################################################
	
	//Here are all available robots are defined with their name and class:
	THREEWHEEL 	("3-Wheel", new ThreeWheelDriveRobot()),
	FOURWHEEL  	("4-Wheel", new ThreeWheelDriveRobot()),
	CHAINDRIVE 	("Chain-Drive", new ThreeWheelDriveRobot()),
	TEST 		("TEST", new ThreeWheelDriveRobot()),
	LALA 		("LALA", new ThreeWheelDriveRobot()),
	TINKIWIKI 	("TINKIWINKI", new ThreeWheelDriveRobot()),
	DIPSI 		("DIPSI", new ThreeWheelDriveRobot()),
	PO 			("PO", new ThreeWheelDriveRobot()),
	NEWROBOT 	("New Robot", new ThreeWheelDriveRobot());
	
//### ATTRIBUTES ###########################################################################################################################

	private String name;	//Name (shown on LCD etc.) of the robot!
	private Robot robot;	//Represents the real class of the robot!

//### CONSTRUCTORS #########################################################################################################################
	
	private Robots(String name, Robot robot) {
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