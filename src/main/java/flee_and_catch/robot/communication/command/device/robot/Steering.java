package flee_and_catch.robot.communication.command.device.robot;

import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.Speed;;

public class Steering {
	private String direction;
	private String speed;
	
	public Steering(String pDirection, String pSpeed){
		this.direction = Direction.valueOf(pDirection).toString();
		this.speed = Speed.valueOf(pSpeed).toString();	
	}
	
	public Steering(Steering steering) {
		this.direction = steering.getDirection();
		this.speed = steering.getSpeed();
	}

	public String getDirection() {
		return direction;
	}

	public String getSpeed() {
		return speed;
	}
}
