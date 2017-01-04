//### RobotController.java #################################################################################################################

package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.RobotType;
import flee_and_catch.robot.communication.command.component.Speed;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.device.robot.Steering;
import flee_and_catch.robot.communication.command.component.Direction;
//### IMPORTS ##############################################################################################################################
import flee_and_catch.robot.localisation.PlayingField;
import flee_and_catch.robot.robot.ThreeWheelDrive;
import flee_and_catch.robot.threads.SynchronizationThread;
import flee_and_catch.robot.robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

/* RobotController [Class]: */
public class RobotController {
	
//### ATTRIBUTES ###########################################################################################################################
	
	//Represents the robot that is controlled:
	private Robot robot;
	//Represents the playing field in that the robot moves:
	private PlayingField field;
	//Thread that sends the robot data to the backend:
	Thread syncThread;
	
	private boolean acceptSterring;
	
	private Direction direction = null;
	
//### CONSTRUCTORS #########################################################################################################################
	
	/* RobotController [Constructor]: Initialize the controller with a robot and a playing field *//**
	 * 
	 */
	public RobotController(Robot robot, PlayingField field) {
		
		this.robot = robot;
		this.field = field;
		this.syncThread = new Thread(new SynchronizationThread(this.robot));
		this.acceptSterring = true;
		
		//Start thread for data sending to backend:
		//syncThread.start();

	}
	
//### METHODS ##############################################################################################################################
	
	public void controlRobot(Steering steering) {
		
		long start = 0;
		long end = 0;
		
		//Handle speed command:
		switch(Speed.valueOf(steering.getSpeed())) {
			case Faster:
				this.robot.increaseSpeed();
				break;
			case Slower:
				this.robot.decreaseSpeed();
				break;
			default:
				break;
		}
		
		switch(Direction.valueOf(steering.getDirection())) {
			case Right:
				if(this.direction != Direction.valueOf(steering.getDirection())) {
					this.robot.rotate(Direction.valueOf(steering.getDirection()));
					this.direction = Direction.valueOf(steering.getDirection());
				}
				break;
			case Left:
				if(this.direction != Direction.valueOf(steering.getDirection())) {
					this.robot.rotate(Direction.valueOf(steering.getDirection()));
					this.direction = Direction.valueOf(steering.getDirection());
				}
				break;
			case StraightOn:
				start = System.currentTimeMillis();
				this.robot.move();
				end = System.currentTimeMillis() - start;
				this.direction = Direction.valueOf(steering.getDirection());
				break;
		}
		
		
	}
	
	public void setRobotActive(boolean active) {
		this.robot.setActive(active);
	}
	
	/* runRandomEasy [Method]: Method that let the robot move randomly in an easy way *//**
	 * 
	 */
	public void runRandomEasy() {
		
		LCD.drawString("runRandomEasy() - Start", 0, 0);
	
	}
	
	/* runRandom [Method]: Method that let the robot move randomly *//**
	 * @throws InterruptedException 
	 * 
	 */
 	public void runRandom() throws InterruptedException {
		
 		LCD.drawString("runRandom() - Start", 0, 0);
 		
 		Position posStart = new Position(575, 550, 0);
 		
 		//Position: x = 0mm, y = 0mm, orientation = 0°; speed: 80mm/s:
		ThreeWheelDrive robot = new ThreeWheelDrive(RobotType.ThreeWheelDrive.toString());		//Initialize a new Robot!
		
		//Size: width=2000mm, height=2000mm
		PlayingField field = new PlayingField(1150, 1100);				//Initialize a new PlayingField!
 		
		//Minimal distance that the robot must move before turn:
		float minDistance = 100.0f;		//10cm
		//Maximal distance that the robot can move before turn:
		float maxDistance = 400.0f;		//40cm
		//Probability for turn:
		float probability = 15.0f;		//25%
		//Minimal time between two shifts in direction:
		float time        = 500.0f;	 	//500ms
		//Start angle:
		float startAngle  = -150.0f;	//-150°
		//End angle:
		float endAngle    = 150.0f;		//150°
		
		//Helping variables:
		int counter = 0;
		//Save the last totalDistance value:
		float savedDistance = 0.0f;
		
		//Start robot to move:
		robot.move();
		
		//Run robot until it has a covered a specific distance:
 		while(robot.getTotalDistance() < 3500) {
 			
 			Position pos = robot.getPosition();
 			
 			//Check if robot currently out of the playing field:
 			if(!field.isIn(pos)) {
 				
 				//Make a turn:
 				robot.rotate(Direction.Left, 180);		//180° turn!
 				//Buffer so that the robot get back in the field until the next check:
 				robot.move(50);							//Move 50mm forward!
 				//Let the robot still move:
 				robot.move();
 				
 			}
 			//Is the robot in the field and check time is reached:
 			else if(counter >= time / 25) {
 				
 				float distance = robot.getTotalDistance() - savedDistance;
 				
 				//If a turn is possible:
 				if(distance >= minDistance && distance <= maxDistance) {
 					
 					int rand = (int)(Math.random() * 100) + 1;
 					
 					if(rand <= probability) {
 						
 						int angle = (int) ((int)(Math.random() * (endAngle - startAngle)) + startAngle);	//Angle between 30° and 150°
 						robot.rotate(angle);
 						savedDistance = robot.getTotalDistance();
 						robot.move();
 					}
 				}
 				//If a turn is required:
 				else if(distance > maxDistance) {
 					
 					int angle = (int) ((int)(Math.random() * (endAngle - startAngle)) + startAngle);	//Angle between 30° and 150°
					robot.rotate(angle);
					robot.move();
					savedDistance = robot.getTotalDistance();
 				}
 				//If no turn is allowed:
 				//... do nothing!
 				counter = 0;
 			}
 			
 			counter++;
 			
 			//Print information:
 			LCD.drawString("x: " + pos.getX(), 0, 1);
 			LCD.drawString("y: " + pos.getY(), 0, 2);
 			LCD.drawString("o: " + pos.getOrientation(), 0, 3);
 			LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
 			
 			//Wait 25ms:
 			Thread.sleep(25);
 		}
 		
 		robot.stop();
 		
		/*Wait for any key to be pressed*/ 
		LCD.drawString("Press btn to exit!", 0, 7); 
		Button.waitForAnyPress(); 
	}

	/* testRun1 [Method]: Temporary method to test some stuff *//**
	 * 
	 * @throws InterruptedException
	 */
	public void testRun1() throws InterruptedException {
		
		LCD.drawString("testRun() - Start", 0, 0);
		
		ThreeWheelDrive robot = new ThreeWheelDrive(RobotType.ThreeWheelDrive.toString());		//Initialize a new Robot!
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
		robot.rotate(Direction.Left, 90);
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
		robot.rotate(Direction.Right, 30);
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
		robot.rotate(Direction.Left, 165);
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
		robot.rotate(Direction.Left, 93);
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
		robot.rotate(Direction.Right, 117);
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

	/* testRun2 [Method]: Temporary method to test some stuff *//**
	 * 
	 * @throws InterruptedException
	 */
	public void testRun2() throws InterruptedException {
		
 		LCD.drawString("testRun2() - Start", 0, 0);
 		
 		//Position: x = 0mm, y = 0mm, orientation = 0°; speed: 80mm/s:
		ThreeWheelDrive robot = new ThreeWheelDrive(RobotType.ThreeWheelDrive.toString());		//Initialize a new Robot!
		
		//Size: width=2000mm, height=2000mm
		//PlayingField field = new PlayingField(2000, 2000);					//Initialize a new PlayingField!
		
		robot.move();
		
		//Run robot until it has a covered a specific distance:
 		while(robot.getTotalDistance() < 2000) {
 			
 			Position pos = robot.getPosition();
 			
			//Print information:
			LCD.drawString("x: " + pos.getX(), 0, 1);
			LCD.drawString("y: " + pos.getY(), 0, 2);
			LCD.drawString("o: " + pos.getOrientation(), 0, 3);
			LCD.drawString("d: " + robot.getTotalDistance(), 0, 4);
			
			//Wait 25ms:
			Thread.sleep(25);
		
 		}
		
 		robot.stop();
 		
		LCD.drawString("testRun2() - Finished", 0, 0);
		
		/*Wait for any key to be pressed*/ 
		LCD.drawString("Press btn to exit!", 0, 7); 
		Button.waitForAnyPress(); 
		
		
	}
	
//##########################################################################################################################################
}
//### EOF ##################################################################################################################################