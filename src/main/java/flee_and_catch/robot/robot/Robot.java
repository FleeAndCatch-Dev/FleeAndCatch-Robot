//### Robot.java ###########################################################################################################################

package flee_and_catch.robot.robot;

//### IMPORTS ##############################################################################################################################
import flee_and_catch.robot.localisation.Position;

/* Robot [Interface]: Interface that defines all necessary functions that a robot must implement *//**
 * 
 * @author Manuel Bothner
 *
 */
public interface Robot {
	
//### GETTER/SETTER ########################################################################################################################
	
	String getType();
	
	Position getPosition();
	
	
	float getSpeed();
	
	
	boolean isMoving();
	
	
	void setSpeed(float speed);

	
//### PUBLIC METHODS #######################################################################################################################
		
	void rotate(float angle) throws InterruptedException;

//##########################################################################################################################################
}
//### EOF ##################################################################################################################################