package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.Speed;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;

public interface Robot {
	
	void increaseSpeed();
	void decreaseSpeed();
	
	/* stop [Method]: Method to stop the robot moving *//**
	 * 
	 */
	void stop();
	
	/* move [Method]: Method to let the robot move forward *//**
	 * 
	 */
	void move();
	
	/* move [Method]: Method to let the robot move a distance forward *//**
	 * 
	 * @param distance
	 * @throws InterruptedException
	*/
	void move(float distance) throws InterruptedException;
	
	void rotate(Direction direction);
	void rotate(float angle) throws InterruptedException;
	void rotate(Direction direct, float angle) throws InterruptedException;

	void changeSpeed(Speed speed);
	
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

	float getTotalDistance();
}
