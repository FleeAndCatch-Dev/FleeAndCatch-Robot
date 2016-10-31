//### Position.java ########################################################################################################################

package flee_and_catch.robot.localisation;

//### IMPORTS ##############################################################################################################################

public class Position {
	
//### CONSTANTS ############################################################################################################################

//### ATTRIBUTES ###########################################################################################################################
	
	private double x;				//X-Coordinate of the Position!
	private double y;				//Y-Coordinate of the Position!
	private double orientation;		//Angle that represents the orientation!
	
//### CONSTRUCTORS #########################################################################################################################

	public Position() {
		this.x = 0.0;
		this.y = 0.0;
		this.orientation = 0.0;
	}


//### INITIAL METHODS ######################################################################################################################

//### GETTER/SETTER ########################################################################################################################

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
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
		
		double orientationRad = this.orientation * (Math.PI / 180);
		float distX = (float) Math.cos(orientationRad) * longitudinalDistance;
		float distY = (float) Math.sin(orientationRad) * longitudinalDistance;
		
		this.x += distX;
		this.y += distY;
	}
	
	
	
	public String toStringLCD() {
		
		return this.x + ", " + this.y + ", " + this.orientation;
		
	}
	
//##########################################################################################################################################	
}
//### EOF ##################################################################################################################################