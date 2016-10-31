
package flee_and_catch.robot.controller;

import flee_and_catch.robot.localisation.Direction;
import flee_and_catch.robot.robot.DefaultRobot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

//### IMPORTS ##############################################################################################################################

public class RobotController {
//### CONSTANTS ############################################################################################################################

//### ATTRIBUTES ###########################################################################################################################

//### COMPONENTS ###########################################################################################################################

//### CONSTRUCTORS #########################################################################################################################
	
	public RobotController() {
		
	}
	
//### INITIAL METHODS ######################################################################################################################

//### INNER CLASSES ########################################################################################################################

//### GETTER/SETTER ########################################################################################################################

//### METHODS ##############################################################################################################################
	
	public void testRun() throws InterruptedException {
		
		LCD.drawString("testRun() - Start", 0, 0);
		
		DefaultRobot robot = new DefaultRobot();
		
		LCD.drawString(robot.getPosition().toStringLCD(), 0, 1);
		
		robot.setSpeed(60.0f);		//Set speed to 20 mm/sec!
		robot.move(40.0f);				//Let the robot move forward!
		//Thread.sleep(1000);
		
		//robot.rotate(Direction.RIGHT, 270.0f);
		
		LCD.drawString(robot.getPosition().toStringLCD(), 0, 1);
		
		LCD.drawString("testRun() - Finished", 0, 0);
		
		/*Wait for any key to be pressed*/ 
		LCD.drawString("Press btn to exit!", 0, 7); 
		Button.waitForAnyPress(); 
	}
}
//### EOF ##################################################################################################################################