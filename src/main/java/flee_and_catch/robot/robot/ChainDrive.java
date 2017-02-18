package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;

public class ChainDrive implements Robot {

	@Override
	public float getLongitudinalDistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetLongitudinalDistance() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increaseSpeed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decreaseSpeed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveForward(float distance) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBackward(float distance) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(float angle) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(Direction direct, float angle) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public flee_and_catch.robot.communication.command.device.robot.Robot getJSONRobot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RobotIdentification getIdentification() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setActive(boolean active) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMoving() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSpeed(float speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTotalDistance() {
		// TODO Auto-generated method stub
		return 0;
	}
}
