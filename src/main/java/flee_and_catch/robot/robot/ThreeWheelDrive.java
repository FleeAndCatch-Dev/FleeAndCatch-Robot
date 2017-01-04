//### DefaultRobot.java ####################################################################################################################

/*** Descriptions: ********************************************************************************
 * 
 **************************************************************************************************/

package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.IdentificationType;
import flee_and_catch.robot.communication.command.component.RoleType;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;

import org.jfree.util.Rotation;

import flee_and_catch.robot.communication.command.component.Direction;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

/* DefaultRobot [Class]: Class that represents the default LEGO Mindstorms EV3 robot *//**
 * 
 * @author Manuel Bothner
 *
 */
public class ThreeWheelDrive implements Robot {

//### CONSTANTS ############################################################################################################################
	
	//Distance that a wheel move by a rotation of one degree (original: 0.476389f):
	private static final float DISTANCE_DEGREE =   0.476389f;		//In millimeter!
	//Distance between the both (midpoints of the) wheels (original: 123.000000f):
	private static final float DIAMETER_WHEELS = 120.000000f;		//In millimeter!
	
	//Adjustment parameter of the motor runtime for moving forward: 
	private static final float ADJUST_SEC_MOVE =   1.00000f;	
	//Adjustment parameter of the motor runtime for rotation on spot:
	private static final float ADJUST_SEC_ROTATE = 1.00000f;
	
	//Represents the maximum speed of a motor in degrees per second:
	private static final float MAX_SPEED_MOTOR = 1440.0f;
	
//### ATTRIBUTES ###########################################################################################################################
	
	//Represents an object to identify the robot:
	private RobotIdentification identification;
	//Saves if the robot is controlled bei an app:
	private boolean active;
	//Represents the position and the orientation of the robot:
	private Position pos;
	//Represents the Speed of the speed of the robot:
	private float speed;
	
	//
	private float rotationSpeed;
	
	//Represents the total distance that the robot covered:
	private float totalDistance;
	
//### COMPONENTS ###########################################################################################################################
	
	//Motor that driving the right wheel:
	private EV3MediumRegulatedMotor motorRight;
	//Motor that driving the left wheel:
	private EV3MediumRegulatedMotor motorLeft;
	
//### CONSTRUCTORS #########################################################################################################################
	
	/* DefaultRobot [Constructor]: Initialize the position x=0, y=0, orientation=0 and the speed 50mm/s *//**
	 * 
	 */
	public ThreeWheelDrive(String pSubtype) {
		
		//Initialize the attributes:
		this.pos = new Position();			//x=0, y=0, orientation=0°!
		this.speed = 50.0f;					//Set speed to 50mm/s!
		this.rotationSpeed = 50.0f;
		this.totalDistance = 0.0f;
		this.identification = new RobotIdentification(-1, IdentificationType.Robot.toString(), pSubtype, RoleType.Undefined.toString());
		this.active = false;
		
		//Initialize the robot components:
		this.initComponents();
		
	}
	
//### INITIAL METHODS ######################################################################################################################
	
	/* initComponents [Method]: Method that initialize the components (motors, sensors, etc.) of the robot *//**
	 * 
	 */
	private void initComponents() {
		
		//Initial motors with standard ports:
		this.motorRight = new EV3MediumRegulatedMotor(MotorPort.C);
		this.motorLeft  = new EV3MediumRegulatedMotor(MotorPort.B);
		
		//Set default values for speed (degrees/sec):
		this.motorRight.setSpeed(this.speed / ThreeWheelDrive.DISTANCE_DEGREE);
		this.motorLeft.setSpeed(this.speed / ThreeWheelDrive.DISTANCE_DEGREE);
		
		//Reset the turn counter of the motors:
		this.motorRight.resetTachoCount();
		this.motorLeft.resetTachoCount();
		
	}
	
//### PRIVATE METHODS ######################################################################################################################
	
	/* getLongitudinalDistance [Method]: Returns the longitudinal distance that the robot moved *//**
	 * 
	 * @return
	 */
	private float getLongitudinalDistance() {
		
		//Calculate the average of both rotation counters:
		float degrees = (this.motorRight.getTachoCount() + this.motorLeft.getTachoCount()) / 2;
		//Multiply the number of rotated degrees with the millimeter that are covered per degree:
		return degrees * ThreeWheelDrive.DISTANCE_DEGREE;
		
	}
	
	/* resetLongitudinalDistance [Method]: Method that resets the rotation counters of the motors *//**
	 * 
	 */
	private void resetLongitudinalDistance() {
		
		//Reset the rotation counters of the motors:
		this.motorRight.resetTachoCount();
		this.motorLeft.resetTachoCount();
		
	}
	
	/* determinePosition [Method]: Method that determine the new position of the robot after moving *//**
	 * 
	 */
	private void determinePosition() {
		
		//Add current longitudinal distance to the total distance:
		this.totalDistance += this.getLongitudinalDistance();
		
		//Calculate the new position of the robot:
		this.pos.calculateNewPosition(this.getLongitudinalDistance());
		
		//Reset the rotation counters:
		this.resetLongitudinalDistance();
	}
	
	/* stopWithoutDeterminePosition [Method]: Method that stop the robot moving without determine the new position *//**
	 * 
	 * necessary when robot rotates on the spot!
	 */
	private void stopWithoutDeterminePosition() {
		
		//Stop motors:
		this.motorRight.stop();
		this.motorLeft.stop();
		//Reset the detected rotation of the rotation counter:
		this.resetLongitudinalDistance();
	}
	
//### GETTER/SETTER ########################################################################################################################
	
	@Override
	public RobotIdentification getIdentification() {
		return this.identification;
	}

	@Override
	public boolean isActive(){
		return active;
	}
	
	/* isMoving [Method]: Method that tells whether the robot is moving *//**
	 * 
	 * @return
	 */
	public boolean isMoving() {
		
		return (this.motorRight.isMoving() || this.motorLeft.isMoving());
	
	}

	/* getPosition [Method]: Returns the current position and orientation of the robot as a Position object *//**
	 * 
	 * @return
	 */
	public Position getPosition() {
		
		//Duplicate the saved (old) position of the robot:
		Position curPos = new Position();
		curPos.setX(this.pos.getX());
		curPos.setY(this.pos.getY());
		curPos.setOrientation(this.pos.getOrientation());
		//Calculate the current position of the robot:
		curPos.calculateNewPosition(this.getLongitudinalDistance());
		//Return the current position of the robot:
		return curPos;
		
	}
	
	/* getSpeed [Method]: Returns the current speed of the robot in millimeter per second *//**
	 * 
	 * @return
	 */
	public float getSpeed() {
		return this.speed;
	}
	
	/* getTotalDistance [Method]: Returns the total distance that the robot moved *//**
	 * 
	 * @return
	 */
	public float getTotalDistance() {
		//Current total distance = (old) saved total distance + current longitudinal distance:
		return this.totalDistance + this.getLongitudinalDistance();
	}
	
	@Override
	public flee_and_catch.robot.communication.command.device.robot.Robot getJSONRobot() {
		// TODO Auto-generated method stub
		return new flee_and_catch.robot.communication.command.device.robot.Robot(this.identification, this.active, this.getPosition(), this.speed);
	}
	
	//--- Setter -------------------------------------------------------------------------------------------------------
	
	/* setSpeed [Method]: Method to set the speed of the robot in millimeter per second *//**
	 * 
	 */
	public void setSpeed(float speed) {
				
		//Convert speed from milli/sec to degrees/sec:
		float degrees = speed / ThreeWheelDrive.DISTANCE_DEGREE;
		
		//Check if the speed is not negative:
		if(speed < 0.0f) {
			//Set the rotation speed (degrees/sec) of the both motors to zero:
			this.motorRight.setSpeed(0.0f);
			this.motorLeft.setSpeed(0.0f);
			//Set speed attribute:
			this.speed = 0.0f;
		}
		//Check if the speed is not to high:
		else if(degrees > ThreeWheelDrive.MAX_SPEED_MOTOR) {
			//Set the rotation speed (degrees/sec) of the both motors to maximum:
			this.motorRight.setSpeed(ThreeWheelDrive.MAX_SPEED_MOTOR);
			this.motorLeft.setSpeed(ThreeWheelDrive.MAX_SPEED_MOTOR);
			//Set speed attribute:
			this.speed = ThreeWheelDrive.MAX_SPEED_MOTOR * ThreeWheelDrive.DISTANCE_DEGREE;
		}
		else {
			//Set the rotation speed (degrees/sec) of the both motors:
			this.motorRight.setSpeed(degrees);
			this.motorLeft.setSpeed(degrees);
			//Set speed attribute:
			this.speed = speed;
		}

	}
	
	/* setActive [Method]: Method to set if the robot is controlled or not *//**
	 * 	
	 */
	@Override
	public void setActive(boolean active) {
		this.active = active;
		
	}
	
//### PUBLIC METHODS #######################################################################################################################
	
	public void increaseSpeed() {
		this.setSpeed(this.getSpeed() + 10.0f);
	}
	
	public void decreaseSpeed() {
		this.setSpeed(this.getSpeed() - 10.0f);
	}
	
	/* stop [Method]: Method to stop the robot moving *//**
	 * 
	 */
	public void stop() {
		
		//Stop the robot:
		this.motorRight.stop();
		this.motorLeft.stop();
		//Determine the (new) current position:
		this.determinePosition();
		
	}
	
	/* move [Method]: Method to let the robot move forward *//**
	 * 
	 */
	public void move() {
		
		this.setSpeed(this.speed);
		
		this.motorRight.forward();
		this.motorLeft.forward();
	
	}
	
	
	@Override
	public void rotate(Direction direction) {
		
		//First stop the robot moving (Depends on the moving concept):
		//if(this.isMoving()) { this.stop(); }
		
		this.setSpeed(rotationSpeed);
		
		//Start motors:
		switch(direction) {
			case Left:
				//Activate motors:
				this.motorRight.forward();
				this.motorLeft.backward();
				break;
			case Right:
				//Activate motors:
				this.motorRight.backward();
				this.motorLeft.forward();
				break;
			default:
				//Error!
				break;
		}
		
		//TODO: Calculate Position:
		
	}

	
	//--- Temporarily effects ------------------------------------------------------------------------------------------
	
	/* move [Method]: Method to let the robot move a distance forward *//**
	 * 
	 * @param distance
	 * @throws InterruptedException
	 */
 	public void move(float distance) throws InterruptedException {
		
		//Calculate the time in seconds that the motors must move:
		float sec = distance / this.getSpeed();
		
		//Let robot move forward:
		this.move();
		
		//Wait time to move the distance:
		Thread.sleep((long) (sec * ThreeWheelDrive.ADJUST_SEC_MOVE * 1000));
		
		//Stop motors:
		this.stop();
		
	}
	
	/* rotate [Method]: Method to rotate the robot around this vertical axis *//**
	 * 
	 * @param direct
	 * @param angle
	 * @throws InterruptedException
	 */
	public void rotate(Direction direct, float angle) throws InterruptedException {
		
		//Notice: This works only if both motors have the same speed!
		
		//First stop the robot moving (Depends on the moving concept):
		if(this.isMoving()) { this.stop(); }
		
		//Calculate the length of the circular sector:
		float circular_sektor = (float) (ThreeWheelDrive.DIAMETER_WHEELS * Math.PI * (angle / 360));
		//Calculate the time in seconds that the motors must move:
		float sec = circular_sektor / this.getSpeed();
		
		//Start motors:
		switch(direct) {
			case Left:
				//Set new robot orientation:
				this.pos.calculateNewOrientation(angle);
				//Activate motors:
				this.motorRight.forward();
				this.motorLeft.backward();
				break;
			case Right:
				//Set new robot orientation:
				this.pos.calculateNewOrientation(-angle);
				//Activate motors:
				this.motorRight.backward();
				this.motorLeft.forward();
				break;
			default:
				//Error!
				break;
		}
		
		Thread.sleep((long) (sec * ThreeWheelDrive.ADJUST_SEC_ROTATE * 1000));
		
		//Stop motors:
		this.stopWithoutDeterminePosition();
	}
	
	/* rotate [Method]: Method to rotate the robot around this vertical axis *//**
	 * 
	 * @param angle
	 * @throws InterruptedException 
	 */
	public void rotate(float angle) throws InterruptedException {
		
		if(angle < 0) {
			angle *= -1;
			this.rotate(Direction.Right, angle);
		}
		else {
			this.rotate(Direction.Left, angle);
		}
		
	}




//##########################################################################################################################################
}
//### EOF ##################################################################################################################################