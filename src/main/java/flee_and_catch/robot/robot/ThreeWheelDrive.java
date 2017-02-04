package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.IdentificationType;
import flee_and_catch.robot.communication.command.component.RobotType;
import flee_and_catch.robot.communication.command.component.RoleType;
import flee_and_catch.robot.communication.command.component.Speed;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;
import flee_and_catch.robot.configuration.RobotConfig;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class ThreeWheelDrive implements Robot {

	protected RobotIdentification identification;
	protected boolean active;
	protected Position position;
	protected float speed;
	
	//Represents the total distance that the robot covered:
	protected float totalDistance;
	
	//Motor that driving the right wheel:
	protected EV3MediumRegulatedMotor motorRight;
	//Motor that driving the left wheel:
	protected EV3MediumRegulatedMotor motorLeft;
	
	public ThreeWheelDrive(){
		this.identification = new RobotIdentification(0, IdentificationType.Robot.toString(), RobotType.ThreeWheelDrive.toString(), RoleType.Undefined.toString());
		this.active = false;
		this.position = new Position();
		this.speed = 0;
		
		totalDistance = 0;
		
		initComponents();
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
		this.motorRight.setSpeed(this.speed / RobotConfig.DISTANCE_DEGREE);
		this.motorLeft.setSpeed(this.speed / RobotConfig.DISTANCE_DEGREE);
		
		//Reset the turn counter of the motors:
		this.motorRight.resetTachoCount();
		this.motorLeft.resetTachoCount();
		
	}

//### PRIVATE METHODS ######################################################################################################################
	
	/* getLongitudinalDistance [Method]: Returns the longitudinal distance that the robot moved *//**
	* 
	* @return
	*/
	protected float getLongitudinalDistance() {
				
		//Calculate the average of both rotation counters:
		float degrees = (this.motorRight.getTachoCount() + this.motorLeft.getTachoCount()) / 2;
		//Multiply the number of rotated degrees with the millimeter that are covered per degree:
		return degrees * RobotConfig.DISTANCE_DEGREE;
				
	}
			
	/* resetLongitudinalDistance [Method]: Method that resets the rotation counters of the motors *//**
	 * 
	 */
	protected void resetLongitudinalDistance() {
				
		//Reset the rotation counters of the motors:
		this.motorRight.resetTachoCount();
		this.motorLeft.resetTachoCount();
				
	}
			
	/* determinePosition [Method]: Method that determine the new position of the robot after moving *//**
	 * 
	 */
	protected void determinePosition() {
				
		//Add current longitudinal distance to the total distance:
		this.totalDistance += this.getLongitudinalDistance();
				
		//Calculate the new position of the robot:
		this.position.calculateNewPosition(this.getLongitudinalDistance());
				
		//Reset the rotation counters:
		this.resetLongitudinalDistance();
	}
			
	/* stopWithoutDeterminePosition [Method]: Method that stop the robot moving without determine the new position *//**
	 * 
	 * necessary when robot rotates on the spot!
	 */
	protected void stopWithoutDeterminePosition() {
				
		//Stop motors:
		this.motorRight.stop();
		this.motorLeft.stop();
		//Reset the detected rotation of the rotation counter:
		this.resetLongitudinalDistance();
	}
	
//### PUBLIC METHODS #######################################################################################################################
	
	public void increaseSpeed(){
		this.setSpeed(this.getSpeed() + 10.0f);
	}
	public void decreaseSpeed(){
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
		Thread.sleep((long) (sec * RobotConfig.ADJUST_SEC_MOVE * 1000));
			
		//Stop motors:
		this.stop();
			
	}
	
	@Override
	public void rotate(Direction direction) {
		//First stop the robot moving (Depends on the moving concept):
		//if(this.isMoving()) { this.stop(); }
				
		this.setSpeed(RobotConfig.ROTATION_SPEED);				
				
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
			case StraightOn:
				this.motorRight.forward();
				this.motorLeft.forward();
			default:
				//Exception
				break;
		}
				
		//TODO: Calculate Position:
	}

	@Override
	public void rotate(float angle) throws InterruptedException {
		if(angle < 0) {
			angle *= -1;
			this.rotate(Direction.Right, angle);
		}
		else {
			this.rotate(Direction.Left, angle);
		}
	}

	@Override
	public void rotate(Direction direct, float angle) throws InterruptedException {
		//Notice: This works only if both motors have the same speed!
		
		//First stop the robot moving (Depends on the moving concept):
		if(this.isMoving()) { this.stop(); }
		
		//Calculate the length of the circular sector:
		float circular_sektor = (float) (RobotConfig.DIAMETER_WHEELS * Math.PI * (angle / 360));
		//Calculate the time in seconds that the motors must move:
		float sec = circular_sektor / this.getSpeed();
		
		//Start motors:
		switch(direct) {
			case Left:
				//Set new robot orientation:
				this.position.calculateNewOrientation(angle);
				//Activate motors:
				this.motorRight.forward();
				this.motorLeft.backward();
				break;
			case Right:
				//Set new robot orientation:
				this.position.calculateNewOrientation(-angle);
				//Activate motors:
				this.motorRight.backward();
				this.motorLeft.forward();
				break;
			default:
				//Error!
				break;
		}
		
		Thread.sleep((long) (sec * RobotConfig.ADJUST_SEC_ROTATE * 1000));
		
		//Stop motors:
		this.stopWithoutDeterminePosition();		
	}
	
	public void changeSpeed(Speed speed) {
		switch(speed) {
			case Faster:
				this.increaseSpeed();
				break;
			case Slower:
				this.decreaseSpeed();
				break;
			case Equal:
				break;
			default:
				break;
		}
	}

	@Override
	public flee_and_catch.robot.communication.command.device.robot.Robot getJSONRobot() {
		return new flee_and_catch.robot.communication.command.device.robot.Robot(this.identification, this.active, this.getPosition(), this.speed);
	}
	
//### GETTER/SETTER ########################################################################################################################
	
	public RobotIdentification getIdentification() {
		return identification;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/* isMoving [Method]: Method that tells whether the robot is moving *//**
	 * 
	 * @return
	 */
	public boolean isMoving() {	
		return (this.motorRight.isMoving() || this.motorLeft.isMoving());
	}

	public Position getPosition() {
		//Duplicate the saved (old) position of the robot:
		Position curPos = new Position();
		curPos.setX(this.position.getX());
		curPos.setY(this.position.getY());
		curPos.setOrientation(this.position.getOrientation());
		//Calculate the current position of the robot:
		curPos.calculateNewPosition(this.getLongitudinalDistance());
		//Return the current position of the robot:
		return curPos;
	}

	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		//Convert speed from milli/sec to degrees/sec:
		float degrees = speed / RobotConfig.DISTANCE_DEGREE;
			
		//Check if the speed is not negative:
		if(speed < 0.0f) {
			//Set the rotation speed (degrees/sec) of the both motors to zero:
			this.motorRight.setSpeed(0.0f);
			this.motorLeft.setSpeed(0.0f);
			//Set speed attribute:
			this.speed = 0.0f;
		}
		//Check if the speed is not to high:
		else if(degrees > RobotConfig.MAX_SPEED_MOTOR) {
			//Set the rotation speed (degrees/sec) of the both motors to maximum:
			this.motorRight.setSpeed(RobotConfig.MAX_SPEED_MOTOR);
			this.motorLeft.setSpeed(RobotConfig.MAX_SPEED_MOTOR);
			//Set speed attribute:
			this.speed = RobotConfig.MAX_SPEED_MOTOR * RobotConfig.DISTANCE_DEGREE;
		}
		else {
			//Set the rotation speed (degrees/sec) of the both motors:
			this.motorRight.setSpeed(degrees);
			this.motorLeft.setSpeed(degrees);
			//Set speed attribute:
			this.speed = speed;
		}
	}

	public float getTotalDistance() {
		//Current total distance = (old) saved total distance + current longitudinal distance:
		return totalDistance + getLongitudinalDistance();
	}
}
