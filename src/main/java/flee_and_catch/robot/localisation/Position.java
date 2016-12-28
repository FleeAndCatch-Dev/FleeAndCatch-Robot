//### Position.java ########################################################################################################################

package flee_and_catch.robot.localisation;

import org.json.JSONObject;

//### IMPORTS ##############################################################################################################################

/* Position [Class]: Represents a Position and the orientation of a robot *//**
 * 
 * @author Manuel Bothner
 *
 */
public class Position {
	
//### CONSTANTS ############################################################################################################################

//### ATTRIBUTES ###########################################################################################################################
	
	private double x;				//X-Coordinate of the Position!
	private double y;				//Y-Coordinate of the Position!
	private double orientation;		//Angle that represents the orientation (0-359°)!
	
//### CONSTRUCTORS #########################################################################################################################

	/* Position [Constructor]: Initialize with x=0, y=0, orientation=0 *//**
	 * 
	 */
	public Position() {
		this.x = 0.0;
		this.y = 0.0;
		this.orientation = 0.0;
	}

	/* Position [Constructor]: Constructor for individual initialization *//**
	 * 
	 * @param x
	 * @param y
	 * @param orientation
	 */
	public Position(double x, double y, double orientation) {
		this.x = x;
		this.y = y;
		this.setOrientation(orientation);
	}
	
//### GETTER/SETTER ########################################################################################################################

	/* getX [Method]: Returns the x-coordinate of the position *//**
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/* getY [Method]: Returns the y-coordinate of the position */
	public double getY() {
		return y;
	}
	
	/* getOrientation [Method]: Returns the orientation */
	public double getOrientation() {
		return orientation;
	}
	
	/* setX [Method]: Method to set the x-coordination of the position *//**
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/* setY [Method]: Method to set the y-coordination of the position *//**
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/* setOrientation [Method]: Method to set the orientation *//**
	 * 
	 * @param orientation
	 */
	public void setOrientation(double angle) {
		angle = angle % 360;
		if(angle < 0) { angle += 360; }
		this.orientation = angle;
	}
	
//### METHODS ##############################################################################################################################

	/* calculateNewOrientation [Method]: Method that calculates the new orientation based of a rotation angle *//**
	 * 
	 * @param angle
	 */
	public void calculateNewOrientation(float angle) {
		
		this.orientation = (this.orientation + angle) % 360;
		if(this.orientation < 0) { this.orientation += 360; }
		
	}
	
	/* calculateNewPosition [Method]: Method that calculates a new position based of a covered longitudinal distance*//**
	 * 
	 */
	public void calculateNewPosition(float longitudinalDistance) {
		
		//Convert the orientation from degree in radian:
		double orientationRad = this.orientation * (Math.PI / 180);
		
		//Calculate the distance that the robot moved in x-/y-direction:
		float distX = (float) Math.cos(orientationRad) * longitudinalDistance;
		float distY = (float) Math.sin(orientationRad) * longitudinalDistance;
		
		//Add the moved distance to position:
		this.x += distX;
		this.y += distY;
	}

	/* toStringLCD [Method]: Returns the attribute values in a compact string format for the robot LCD */
	public String toStringLCD() {
		
		return this.x + ", " + this.y + ", " + this.orientation;
		
	}
	
	public JSONObject getJSONObject(){
		JSONObject jsonPosition = new JSONObject();
		jsonPosition.put("x", x);
		jsonPosition.put("y", y);
		jsonPosition.put("orientation", orientation);
		
		return jsonPosition;
	}
	
//##########################################################################################################################################	
}
//### EOF ##################################################################################################################################