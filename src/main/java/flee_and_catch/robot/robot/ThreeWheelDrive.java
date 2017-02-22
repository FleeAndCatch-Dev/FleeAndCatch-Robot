package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.IdentificationType;
import flee_and_catch.robot.communication.command.component.RoleType;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;
import flee_and_catch.robot.configuration.RobotConfig;
import flee_and_catch.robot.configuration.ThreeWheelDriveConfig;
import flee_and_catch.robot.robot.sensor.Gyro;
import flee_and_catch.robot.robot.sensor.Ultrasonic;
import flee_and_catch.robot.view.ViewController;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

public class ThreeWheelDrive implements Robot {

	private RobotIdentification identification;
	private boolean active;
	private Position position;
	private double totalDistance;
	private double speedDistance;
	private float speed;
	private Status status;
	
	//Motor that driving the right wheel:
	private EV3MediumRegulatedMotor motorRight;
	//Motor that driving the left wheel:
	private EV3MediumRegulatedMotor motorLeft;
	
	private Ultrasonic ultrasonic;
	private Gyro gyro;
	
	private long clock;
	private float saveSpeed;
	
	public ThreeWheelDrive(String pSubtype){
		this.identification = new RobotIdentification(-1, IdentificationType.Robot.toString(), pSubtype, RoleType.Undefined.toString());
		this.active = false;
		this.position = new Position();	
		this.totalDistance = 0;
		this.speedDistance = 0;
		this.status = Status.Waiting;
	}
	
//### INITIAL METHODS ######################################################################################################################
	
	/* initComponents [Method]: Method that initialize the components (motors, sensors, etc.) of the robot *//**
	 * 
	 */
	@Override
	public void initComponents() {
			
		try{
			//Initial motors with standard ports:
			this.motorRight = new EV3MediumRegulatedMotor(ThreeWheelDriveConfig.PORT_MOTOR_RIGHT);
			this.motorLeft  = new EV3MediumRegulatedMotor(ThreeWheelDriveConfig.PORT_MOTOR_LEFT);
			
			//Set default values for speed (degrees/sec):
			this.motorRight.setSpeed(this.speed / RobotConfig.DISTANCE_DEGREE);
			this.motorLeft.setSpeed(this.speed / RobotConfig.DISTANCE_DEGREE);
			
			//Reset the turn counter of the motors:
			this.motorRight.resetTachoCount();
			this.motorLeft.resetTachoCount();
		
			ultrasonic = new Ultrasonic(ThreeWheelDriveConfig.PORT_ULTRASONIC);
			gyro = new Gyro(ThreeWheelDriveConfig.PORT_GYRO);
		}
		catch (Exception e) {
			ViewController.showErrorScreen("201");
		}
	}
	
	@Override
	public void checkStatus(Status status) {		
			
		if(status == Status.MovingForward) {
			if(this.status == Status.RotateLeft || this.status == Status.RotateRight) {
				this.position.setOrientation(this.position.calculateNewOrientation(getAngle()));
			}
			else if(this.status == Status.MovingBackward) {
				this.position = new Position(this.position.calculateNewPosition(this.getLongitudinalDistance()));
			}
		}
		else if(status == Status.MovingBackward) {
			if(this.status == Status.RotateLeft || this.status == Status.RotateRight) {
				this.position.setOrientation(this.position.calculateNewOrientation(getAngle()));
			}
			else if(this.status == Status.MovingForward) {
				this.position = new Position(this.position.calculateNewPosition(this.getLongitudinalDistance()));
			}
		}
		else if(status == Status.RotateLeft) {
			if(this.status == Status.MovingForward || this.status == Status.MovingBackward) {
				this.position = new Position(this.position.calculateNewPosition(this.getLongitudinalDistance()));
			}
			else if(this.status == Status.RotateRight) {
				this.position.setOrientation(this.position.calculateNewOrientation(getAngle()));
			}
		}
		else if(status == Status.RotateRight) {
			if(this.status == Status.MovingForward || this.status == Status.MovingBackward) {
				this.position = new Position(this.position.calculateNewPosition(this.getLongitudinalDistance()));
			}
			else if(this.status == Status.RotateLeft) {
				this.position.setOrientation(this.position.calculateNewOrientation(getAngle()));
			}
		}
		else if(status == Status.Waiting) {
			if(this.status == Status.MovingForward || this.status == Status.MovingBackward) {
				this.position = new Position(this.position.calculateNewPosition(this.getLongitudinalDistance()));
			}
			else if(this.status == Status.RotateLeft || this.status == Status.RotateRight) {
				this.position.setOrientation(this.position.calculateNewOrientation(getAngle()));
			}
		}
		this.status = status;
	}
		
//### PUBLIC METHODS ########################################################################################################################
	
	/* getLongitudinalDistance [Method]: Returns the longitudinal distance that the robot moved in millimeter *//**
	* 
	* @return
	*/
	@Override
	public float getLongitudinalDistance() {
		//Calculate the average of both rotation counters:
		float degrees = (this.motorRight.getTachoCount() + this.motorLeft.getTachoCount()) / 2;
		//Multiply the number of rotated degrees with the millimeter that are covered per degree:
		float distance = degrees * RobotConfig.DISTANCE_DEGREE;
		totalDistance = totalDistance + distance;
		resetLongitudinalDistance();
		
		return distance;
	}
	
	/* getLongitudinalDistance [Method]: Returns the longitudinal distance that the robot moved in millimeter *//**
	* 
	* @return
	*/
	@Override
	public float getAngle() {
		//Calculate the average of both rotation counters:
		float degrees = (this.motorRight.getTachoCount() + this.motorLeft.getTachoCount()) / 2;		
		float angle = (float) ((degrees * RobotConfig.DISTANCE_DEGREE) * (180 / Math.PI));
		resetLongitudinalDistance();
		
		return angle;
	}
		
	/* resetLongitudinalDistance [Method]: Method that resets the rotation counters of the motors *//**
	 * 
	 */
	@Override
	public void resetLongitudinalDistance() {				
		//Reset the rotation counters of the motors:			
		this.motorRight.resetTachoCount();
		this.motorLeft.resetTachoCount();				
	}
	
	@Override
	public void increaseSpeed() {
		this.setSpeed(this.getSpeed() + 5.0f);
	}

	@Override
	public void decreaseSpeed() {
		this.setSpeed(this.getSpeed() - 5.0f);
	}

	@Override
	public void forward() {
		this.checkStatus(Status.MovingForward);
		motorLeft.forward();
		motorRight.forward();		
	}

	@Override
	public void backward() {
		this.checkStatus(Status.MovingBackward);
		motorLeft.backward();
		motorRight.backward();
	}

	@Override
	public void moveForward(float distance) throws InterruptedException {
		
		this.checkStatus(Status.MovingForward);
		//Calculate the time in seconds that the motors must move:
		float sec = distance / this.getSpeed();
					
		//Let robot move forward:
		this.forward();
					
		//Wait time to move the distance:
		Thread.sleep((long) (sec * RobotConfig.ADJUST_SEC_MOVE * 1000));
					
		//Stop motors:
		this.stop();
	}
	
	@Override
	public void moveBackward(float distance) throws InterruptedException {
		
		this.checkStatus(Status.MovingBackward);
		//Calculate the time in seconds that the motors must move:
		float sec = distance / this.getSpeed();
					
		//Let robot move forward:
		this.backward();
					
		//Wait time to move the distance:
		Thread.sleep((long) (sec * RobotConfig.ADJUST_SEC_MOVE * 1000));
					
		//Stop motors:
		this.stop();
	}

	@Override
	public void stop() {			
		motorLeft.stop();
		motorRight.stop();
		this.setSpeed(0);
		this.checkStatus(Status.Waiting);
	}

	@Override
	public void rotate(Direction direction) {
		float saveSpeed = getSpeed();							
		
		if(saveSpeed > RobotConfig.ROTATION_SPEED)
			this.setSpeed(getSpeed() / 3);
		if(saveSpeed < RobotConfig.ROTATION_SPEED){
			this.setSpeed(RobotConfig.ROTATION_SPEED);
			this.setSpeed(RobotConfig.ROTATION_SPEED);
		}			
		
		//Start motors:
		switch(direction) {
			case Left:
				//Activate motors:
				this.checkStatus(Status.RotateLeft);
				this.motorRight.forward();
				this.motorLeft.backward();
				break;
			case Right:
				//Activate motors:
				this.checkStatus(Status.RotateRight);
				this.motorRight.backward();
				this.motorLeft.forward();
				break;
			default:
				//Exception
				break;
		}
		
		if(saveSpeed > RobotConfig.ROTATION_SPEED)		
			this.setSpeed(saveSpeed);
	}

	@Override
	public void rotate(float angle) throws InterruptedException {
		if(angle < 0) {
			angle *= -1;
			this.checkStatus(Status.RotateRight);
			this.rotate(Direction.Right, angle);
		}
		else {
			this.checkStatus(Status.RotateLeft);
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
				this.checkStatus(Status.RotateLeft);
				//Set new robot orientation:
				this.position.calculateNewOrientation(angle);
				//Activate motors:
				this.motorRight.forward();
				this.motorLeft.backward();
				break;
			case Right:
				this.checkStatus(Status.RotateRight);
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
		this.stop();
	}
	
	@Override
	public flee_and_catch.robot.communication.command.device.robot.Robot getJSONRobot() {
		return new flee_and_catch.robot.communication.command.device.robot.Robot(this.identification, this.active, this.getPosition(), getRealSpeed(), getUltrasonicDistance(), getGyroAngle());
	}
	
//### GETTER/SETTER ########################################################################################################################
	
	@Override
	public RobotIdentification getIdentification() {
		return identification;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean isMoving() {
		return (this.motorRight.isMoving() || this.motorLeft.isMoving());
	}

	@Override
	public Position getPosition() {
		//Duplicate the saved (old) position of the robot:
		Position curPos = new Position(this.position);
		
		//Calculate the current position of the robot:
		if(status == Status.MovingBackward || status == Status.MovingForward){
			//Calculate the average of both rotation counters:
			float degrees = (this.motorRight.getTachoCount() + this.motorLeft.getTachoCount()) / 2;
			//Multiply the number of rotated degrees with the millimeter that are covered per degree:
			float distance = degrees * RobotConfig.DISTANCE_DEGREE;
			curPos.calculateNewPosition(distance);		}
		else if(status == Status.RotateLeft || status == Status.RotateRight){
			float degrees = (this.motorRight.getTachoCount() + this.motorLeft.getTachoCount()) / 2;		
			float angle = (float) ((degrees * RobotConfig.DISTANCE_DEGREE) * (180 / Math.PI));
			curPos.calculateNewOrientation(angle);
		}
		
		//Return the current position of the robot:*/
		return curPos;
	}

	@Override
	public float getRealSpeed() {
		
		if(status == Status.MovingForward || status == Status.MovingBackward){
			//calculate the real speed by the sensors
			long time = System.currentTimeMillis() - this.clock;
			this.clock = System.currentTimeMillis();
				
			double tempDistance = this.getTotalDistance();
			
			float realSpeed = (float) (((tempDistance - this.speedDistance) * 10) / time);
			this.speedDistance = tempDistance;
			this.saveSpeed = realSpeed;
		}		
			
		return saveSpeed;
	}
	
	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(float speed) {
		//Convert speed from milli/sec to degrees/sec:
		float degrees = speed / RobotConfig.DISTANCE_DEGREE;
					
		//Check if the speed is not negative:
		if(speed < 0.0f) {
			//Set the rotation speed (degrees/sec) of the both motors to zero:
			this.motorRight.setSpeed(0.0f);
			this.motorLeft.setSpeed(0.0f);
			//Set speed attribute:
			speed = 0.0f;
		}
		//Check if the speed is not to high:
		else if(degrees > RobotConfig.MAX_SPEED_MOTOR) {
			//Set the rotation speed (degrees/sec) of the both motors to maximum:
			this.motorRight.setSpeed(RobotConfig.MAX_SPEED_MOTOR);
			this.motorLeft.setSpeed(RobotConfig.MAX_SPEED_MOTOR);
			//Set speed attribute:
			speed = RobotConfig.MAX_SPEED_MOTOR * RobotConfig.DISTANCE_DEGREE;
		}
		else {
			//Set the rotation speed (degrees/sec) of the both motors:
			this.motorRight.setSpeed(degrees);
			this.motorLeft.setSpeed(degrees);
		}
		this.speed = speed;
	}

	/**Get the ultrasonic distance into meter
	 * @throws Exception 
	 * 
	 */
	public float getUltrasonicDistance() {
		if(!ultrasonic.isEnable())
			return -1;			//Ultrasonic isn't enable
		else
			return ultrasonic.getDistance();
	}
	
	public float getGyroAngle(){
		return gyro.getAngle();
	}
	public void resetGyro(){
		gyro.reset();
	}
	
	@Override
	public double getTotalDistance() {

		//Calculate the average of both rotation counters:
		float degrees = (this.motorRight.getTachoCount() + this.motorLeft.getTachoCount()) / 2;
		//Multiply the number of rotated degrees with the millimeter that are covered per degree:
		double distance = degrees * RobotConfig.DISTANCE_DEGREE;

		return totalDistance + distance;
	}
}
