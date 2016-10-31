//### DefaultRobot.java ####################################################################################################################

/*** Descriptions: ********************************************************************************
 * 
 **************************************************************************************************/

package flee_and_catch.robot.robot;

//### IMPORTS ##############################################################################################################################
import flee_and_catch.robot.localisation.Position;
import flee_and_catch.robot.localisation.Direction;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

/* */
public class DefaultRobot {

//### CONSTANTS ############################################################################################################################
	
	//Distance that a wheel move by a rotation of one degree:
	private static final float DISTANCE_DEGREE =   0.476389f;		//In millimeter!
	//Distance between the both (midpoints of the) wheels (original: 123.000000f):
	private static final float DIAMETER_WHEELS = 120.000000f;		//In millimeter!
	
//### ATTRIBUTES ###########################################################################################################################
	
	//Represents the position and the orientation of the robot:
	private Position pos;
	//Represents the Speed of the speed of the robot:
	private float speed;
	
//### COMPONENTS ###########################################################################################################################
	
	//Motor that driving the right wheel:
	private EV3MediumRegulatedMotor motorRight;
	//Motor that driving the left wheel:
	private EV3MediumRegulatedMotor motorLeft;
	
	
//### CONSTRUCTORS #########################################################################################################################
	
	/* DefaultRobot [Constructor]: *//**
	 * 
	 */
	public DefaultRobot() {
		
		//Initialize the attributes:
		this.pos = new Position();			//x=0, y=0, orientation=0°!
		//Initialize the robot components:
		this.initComponents();
		
	}
	
//### INITIAL METHODS ######################################################################################################################

	private void initComponents() {
		
		//Initial motors with standard ports:
		this.motorRight = new EV3MediumRegulatedMotor(MotorPort.C);
		this.motorLeft  = new EV3MediumRegulatedMotor(MotorPort.B);
		
		//Set default values for speed (degrees/sec):
		this.motorRight.setSpeed(180);
		this.motorLeft.setSpeed(180);
		
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
		return degrees * DefaultRobot.DISTANCE_DEGREE;
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
		
	}
	
	/* stopWithoutDeterminePosition [Method]: Method that stop the robot moving without determine the new position *//**
	 * 
	 * necessary when robot rotates on the spot!
	 */
	private void stopWithoutDeterminePosition() {
		this.motorRight.stop();
		this.motorLeft.stop();
	}
	
//### GETTER/SETTER ########################################################################################################################
	
	/* getPosition [Method]: Returns the current position and orientation of the robot as a Position object *//**
	 * 
	 * @return
	 */
	public Position getPosition() {
		return this.pos;
	}
	
	/* getSpeed [Method]: Returns the current speed of the robot in millimeter per second *//**
	 * 
	 * @return
	 */
	public float getSpeed() {
		return this.speed;
	}
	
	/* setSpeed [Method]: Method to set the speed of the robot in millimeter per second *//**
	 * 
	 */
	public void setSpeed(float speed) {
		
		//Set speed attribute:
		this.speed = speed;
		
		//Convert speed in milli/sec to degrees/sec:
		float degrees = speed / DefaultRobot.DISTANCE_DEGREE;
		//Set the rotation speed (degrees/sec) of the both motors:
		this.motorRight.setSpeed(degrees);
		this.motorLeft.setSpeed(degrees);
		
	}
	
//### PUBLIC METHODS #######################################################################################################################
	
	/* isMoving [Method]: Method that tells whether the robot is moving *//**
	 * 
	 * @return
	 */
	public boolean isMoving() {
		
		return (this.motorRight.isMoving() || this.motorLeft.isMoving());
	
	}
	
	/* stop [Method]: Method to stop the robot moving *//**
	 * 
	 */
	public void stop() {
		
		//Stop the robot:
		this.stopWithoutDeterminePosition();
		//Determine the (new) current position:
		this.determinePosition();
		
	}
	
	/* move [Method]: Method to let the robot move forward *//**
	 * 
	 */
	public void move() {
		
		this.motorRight.forward();
		this.motorLeft.forward();
	
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
		float circular_sektor = (float) (DefaultRobot.DIAMETER_WHEELS * Math.PI * (angle / 360));
		//Calculate the time in seconds that the motors must move:
		float sec = circular_sektor / this.getSpeed();
		
		//Start motors:
		switch(direct) {
			case LEFT:
				this.motorRight.forward();
				this.motorLeft.backward();
				break;
			case RIGHT:
				this.motorRight.backward();
				this.motorLeft.forward();
				break;
			default:
				//Error!
				break;
		}
		
		Thread.sleep((long) (sec * 1000));
		
		//Stop motors:
		this.stop();
	}
	
//##########################################################################################################################################
}
//### EOF ##################################################################################################################################