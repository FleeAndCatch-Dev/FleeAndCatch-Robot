package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.device.robot.Direction;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;

public interface Robot {
	
	
	void initComponents();
	
	/* getLongitudinalDistance [Method]: Returns the longitudinal distance that the robot moved *//**
	* 
	* @return
	*/
	float getLongitudinalDistance();
	
	/* resetLongitudinalDistance [Method]: Method that resets the rotation counters of the motors *//**
	 * 
	 */
	void resetLongitudinalDistance();
	
	void increaseSpeed();
	void decreaseSpeed();
	
	/* move [Method]: Method to let the robot move forward *//**
	 * @throws Exception 
	 * 
	 */
	void forward();
	/* move [Method]: Method to let the robot move backward *//**
	 * @throws Exception 
	 * 
	 */
	void backward();
	
	/* move [Method]: Method to let the robot move a distance forward *//**
	 * 
	 * @param distance
	 * @throws InterruptedException
	 * @throws Exception 
	*/
	void moveForward(float distance) throws InterruptedException;
	/* move [Method]: Method to let the robot move a distance forward *//**
	 * 
	 * @param distance
	 * @throws InterruptedException
	 * @throws Exception 
	*/
	void moveBackward(float distance) throws InterruptedException;
	/* stop [Method]: Method to stop the robot moving *//**
	 * @throws Exception 
	 * 
	 */
	void stop();
	
	void rotate(Direction direction);
	void rotate(float angle) throws InterruptedException;
	void rotate(Direction direct, float angle) throws InterruptedException;
	
	public abstract flee_and_catch.robot.communication.command.device.robot.Robot getJSONRobot();
	
//### GETTER/SETTER ########################################################################################################################
	
	RobotIdentification getIdentification();
	boolean isActive();
	void setActive(boolean active);
	
	/* isMoving [Method]: Method that tells whether the robot is moving *//**
	 * 
	 * @return
	 */
	boolean isMoving();

	Position getPosition();

	/**Get the real speed, calculated by the sensors in cm/s
	 * 
	 * @return
	 */
	float getRealSpeed();
	/**Get the speed which the user ist setting
	 * 
	 * @return
	 */
	float getSpeed();
	void setSpeed(float speed);
	
	double getTotalDistance();
	void checkStatus(Status status);
	float getAngle();
}
