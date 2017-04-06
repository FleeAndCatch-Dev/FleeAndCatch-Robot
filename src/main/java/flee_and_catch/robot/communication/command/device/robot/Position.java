//### Position.java ########################################################################################################################

package flee_and_catch.robot.communication.command.device.robot;

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
		this.x = ((double) ((int) (x * 100))) / 100;
		this.y = ((double) ((int) (y * 100))) / 100;
		this.orientation = ((double) ((int) (orientation * 100))) / 100;
	}
	
	public Position(Position position) {
		this.x = ((double) ((int) (position.getX() * 100))) / 100;
		this.y = ((double) ((int) (position.getY() * 100))) / 100;
		this.orientation = ((double) ((int) (position.getOrientation() * 100))) / 100;
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
	public void setOrientation(double orientation) {
		this.orientation = ((double) ((int) (orientation * 100))) / 100;
	}
	
//### METHODS ##############################################################################################################################

	
	
	/* calculateNewOrientation [Method]: Method that calculates the new orientation based of a rotation angle *//**
	 * New orientation is not set!!!
	 * @param angle
	 */
	public double calculateNewOrientation(float angle) {
		
		double tempOrientation = (this.orientation + angle) % 360;
		if(this.orientation < 0) 
			tempOrientation += 360;
		
		return tempOrientation;
	}
	
	/* calculateNewPosition [Method]: Method that calculates a new position based of a covered longitudinal distance*//**
	 * 
	 */
	public Position calculateNewPosition(float longitudinalDistance) {
		
		Position tempPosition = new Position(this);
		
		//Convert the orientation from degree in radian:
		double orientationRad = this.orientation * (Math.PI / 180);
		
		//Calculate the distance that the robot moved in x-/y-direction:
		float distX = (float) Math.cos(orientationRad) * longitudinalDistance;
		float distY = (float) Math.sin(orientationRad) * longitudinalDistance;
		
		//Add the moved distance to position:
		tempPosition.setX(this.getX() + distX);
		tempPosition.setY(this.getY() + distY);
		
		return tempPosition;
	}
	
//##########################################################################################################################################	
}
//### EOF ##################################################################################################################################