package flee_and_catch.robot.communication.command;

import org.json.JSONObject;

public class Position {
	private double x;
	private double y;
	private double orientation;
	
	public Position(double pX, double pY, double pOrientation){
		x = pX;
		y = pY;
		orientation = pOrientation;
	}
	
	public JSONObject getJSONObject(){
		JSONObject jsonPosition = new JSONObject();
		jsonPosition.put("x", x);
		jsonPosition.put("y", y);
		jsonPosition.put("orientation", orientation);
		
		return jsonPosition;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getOrientation() {
		return orientation;
	}
	
	
}