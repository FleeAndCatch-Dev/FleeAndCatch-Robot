package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;

public interface Robot {
	
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
	 * 
	 */
	void forward();
	/* move [Method]: Method to let the robot move backward *//**
	 * 
	 */
	void backward();
	
	/* move [Method]: Method to let the robot move a distance forward *//**
	 * 
	 * @param distance
	 * @throws InterruptedException
	*/
	void moveForward(float distance) throws InterruptedException;
	/* move [Method]: Method to let the robot move a distance forward *//**
	 * 
	 * @param distance
	 * @throws InterruptedException
	*/
	void moveBackward(float distance) throws InterruptedException;
	/* stop [Method]: Method to stop the robot moving *//**
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

	float getSpeed();
	void setSpeed(float speed);

	float getUltrasonicDistance();
	float getTotalDistance();
}
