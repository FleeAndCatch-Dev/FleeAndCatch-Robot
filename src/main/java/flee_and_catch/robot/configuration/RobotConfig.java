package flee_and_catch.robot.configuration;

public class RobotConfig {

	//Distance that a wheel move by a rotation of one degree (original: 0.476389f):
	public static final float DISTANCE_DEGREE =   0.476389f;		//In millimeter!
	//Distance between the both (midpoints of the) wheels (original: 123.000000f):
	public static final float DIAMETER_WHEELS = 120.000000f;		//In millimeter!
		
	//Adjustment parameter of the motor runtime for moving forward: 
	public static final float ADJUST_SEC_MOVE =   1.00000f;	
	//Adjustment parameter of the motor runtime for rotation on spot:
	public static final float ADJUST_SEC_ROTATE = 1.00000f;
		
	//Represents the maximum speed of a motor in degrees per second:
	public static final float MAX_SPEED_MOTOR = 1440.0f;
	
	//Represents the rotation speed of a motor in degrees per second:
	public static final float ROTATION_SPEED = 50.0f;
}
