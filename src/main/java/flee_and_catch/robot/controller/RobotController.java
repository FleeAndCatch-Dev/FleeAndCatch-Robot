//### RobotController.java #################################################################################################################

package flee_and_catch.robot.controller;

//### IMPORTS ##############################################################################################################################
import flee_and_catch.robot.localisation.Direction;
import flee_and_catch.robot.robot.DefaultRobot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

/* RobotController [Class]: */
public class RobotController {
	
//### ATTRIBUTES ###########################################################################################################################

//### CONSTRUCTORS #########################################################################################################################
	
	/* RobotController [Constructor]: *//**
	 * 
	 */
	public RobotController() {
		
	}
	
//### METHODS ##############################################################################################################################
	
	/* runRandom [Method]: Method that let the robot move randomly *//**
	 * 
	 */
 	public void runRandom() {
		
	}
	
	/* testRun [Method]: Method to test some stuff *//**
	 * 
	 * @throws InterruptedException
	 */
	public void testRun() throws InterruptedException {
		
		LCD.drawString("testRun() - Start", 0, 0);
		
		DefaultRobot robot = new DefaultRobot();		//Initialize a new Robot!
		robot.setSpeed(80.0f);							//Set speed to 20 mm/sec!
		
		//Print information:
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot move forward:
		robot.move(325.0f);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot rotate:
		robot.rotate(Direction.LEFT, 90);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot move forward:
		robot.move(288.0f);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot rotate:
		robot.rotate(Direction.RIGHT, 30);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot move forward:
		robot.move(170.0f);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot rotate:
		robot.rotate(Direction.LEFT, 165);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot move forward:
		robot.move(533.0f);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot rotate:
		robot.rotate(Direction.LEFT, 93);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot move forward:
		robot.move(415.0f);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot rotate:
		robot.rotate(Direction.RIGHT, 117);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		//Let the robot move forward:
		robot.move(309.0f);
		LCD.drawString("x: " + robot.getPosition().getX(), 0, 1);
		LCD.drawString("y: " + robot.getPosition().getY(), 0, 2);
		LCD.drawString("o: " + robot.getPosition().getOrientation(), 0, 3);
		LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
		
		LCD.drawString("testRun() - Finished", 0, 0);
		
		/*Wait for any key to be pressed*/ 
		LCD.drawString("Press btn to exit!", 0, 7); 
		Button.waitForAnyPress(); 
	}

//##########################################################################################################################################
}
//### EOF ##################################################################################################################################