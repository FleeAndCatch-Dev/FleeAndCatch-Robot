//### Robot.java ###########################################################################################################################

package flee_and_catch.robot.robot;

import org.json.JSONObject;

import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;

/* Robot [Interface]: Interface that defines all necessary functions that a robot must implement *//**
 * 
 * @author Manuel Bothner
 *
 */
public interface Robot {
	
//### GETTER/SETTER ########################################################################################################################
	
	RobotIdentification getIdentification();
	
	boolean isActive();
	
	Position getPosition();
	
	float getSpeed();

	boolean isMoving();
	
	void setSpeed(float speed);
	
	void setActive(boolean active);
	
	flee_and_catch.robot.communication.command.device.robot.Robot getJSONRobot();

//### PUBLIC METHODS #######################################################################################################################
		
	void rotate(float angle) throws InterruptedException;

	
//	public Robot(Identification pIdentification, Position pPosition, double pSpeed){
//		this.identification = pIdentification;
//		this.position = pPosition;
//		this.speed = pSpeed;
//	}
	
//### STATIC METHODS (REALIZED BY AN INNER CLASS) ##########################################################################################
	
	class Static implements Robot {

		public static JSONObject getJSONObject(Robot robot){
			JSONObject jsonRobot = new JSONObject();
			jsonRobot.put("identification", robot.getIdentification().getJSONObject());
			jsonRobot.put("position", robot.getPosition().getJSONObject());
			jsonRobot.put("speed", robot.getSpeed());
			
			return jsonRobot;
		}
		
		//###  
		
		@Override
		public RobotIdentification getIdentification() {
		// TODO Auto-generated method stub
		return null;
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
		public boolean isMoving() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
		public void setSpeed(float speed) {
		// TODO Auto-generated method stub
		
	}

		@Override
		public void rotate(float angle) throws InterruptedException {
		// TODO Auto-generated method stub
		
		}

		@Override
		public boolean isActive() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public flee_and_catch.robot.communication.command.device.robot.Robot getJSONRobot() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setActive(boolean active) {
			// TODO Auto-generated method stub
			
		}


	}
	
//##########################################################################################################################################
}
//### EOF ##################################################################################################################################