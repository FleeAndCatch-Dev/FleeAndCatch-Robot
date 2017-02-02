package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.IdentificationType;
import flee_and_catch.robot.communication.command.component.RobotType;
import flee_and_catch.robot.communication.command.component.RoleType;
import flee_and_catch.robot.communication.command.identification.RobotIdentification;
import flee_and_catch.robot.configuration.RobotConfig;

public class ThreeWheelDrive extends Robot {

	public ThreeWheelDrive(){
		super();
		this.identification = new RobotIdentification(0, IdentificationType.Robot.toString(), RobotType.ThreeWheelDrive.toString(), RoleType.Undefined.toString());
	}

	@Override
	public void rotate(Direction direction) {
		//First stop the robot moving (Depends on the moving concept):
		//if(this.isMoving()) { this.stop(); }
				
		this.setSpeed(RobotConfig.ROTATION_SPEED);
				
		//Start motors:
		switch(direction) {
			case Left:
				//Activate motors:
				this.motorRight.forward();
				this.motorLeft.backward();
				break;
			case Right:
				//Activate motors:
				this.motorRight.backward();
				this.motorLeft.forward();
				break;
			case StraightOn:
				this.motorRight.forward();
				this.motorLeft.forward();
			default:
				//Exception
				break;
		}
				
		//TODO: Calculate Position:
	}

	@Override
	public void rotate(float angle) throws InterruptedException {
		if(angle < 0) {
			angle *= -1;
			this.rotate(Direction.Right, angle);
		}
		else {
			this.rotate(Direction.Left, angle);
		}
	}

	@Override
	public void rotate(Direction direct, float angle) throws InterruptedException {
		//Notice: This works only if both motors have the same speed!
		
		//First stop the robot moving (Depends on the moving concept):
		if(this.isMoving()) { this.stop(); }
		
		//Calculate the length of the circular sector:
		float circular_sektor = (float) (RobotConfig.DIAMETER_WHEELS * Math.PI * (angle / 360));
		//Calculate the time in seconds that the motors must move:
		float sec = circular_sektor / this.getSpeed();
		
		//Start motors:
		switch(direct) {
			case Left:
				//Set new robot orientation:
				this.position.calculateNewOrientation(angle);
				//Activate motors:
				this.motorRight.forward();
				this.motorLeft.backward();
				break;
			case Right:
				//Set new robot orientation:
				this.position.calculateNewOrientation(-angle);
				//Activate motors:
				this.motorRight.backward();
				this.motorLeft.forward();
				break;
			default:
				//Error!
				break;
		}
		
		Thread.sleep((long) (sec * RobotConfig.ADJUST_SEC_ROTATE * 1000));
		
		//Stop motors:
		this.stopWithoutDeterminePosition();		
	}

	@Override
	public flee_and_catch.robot.communication.command.device.robot.Robot getJSONRobot() {
		return new flee_and_catch.robot.communication.command.device.robot.Robot(this.identification, this.active, this.getPosition(), this.speed);
	}
}
