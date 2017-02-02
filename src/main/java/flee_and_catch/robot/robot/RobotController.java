package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.Speed;
import flee_and_catch.robot.communication.command.device.robot.Steering;
import flee_and_catch.robot.configuration.ThreadConfig;

public final class RobotController {

	private static Robot robot;
	private static Steering steering;

	private static Thread steeringThread;
	private static Thread synchronizeThread;

	private RobotController(){
		steeringThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					controlRobot();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		synchronizeThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronize();
			}
		});
	}

	private void controlRobot() throws InterruptedException {
		while(robot.isActive()) {
			
			try {
				
				//Should the steering should be processed and their is a steering to process:
				if(steering != null && steering.getSpeed() != null && steering.getDirection() != null) {
					
					//Convert the direction and the speed to enums:
					Direction direction = Direction.valueOf(steering.getDirection());
					Speed speed = Speed.valueOf(steering.getSpeed());
					
					//Process direction:
					robot.rotate(direction);
					
					//Process speed:
					robot.changeSpeed(speed);
					
					//Set Steering to processed:
					steering = null;
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				//Wait:
				Thread.sleep(ThreadConfig.STEERING_SLEEP);
			}
		}
	}
	private void synchronize() {
		// TODO Auto-generated method stub
		// TODO send update to backend
	}

	public static Robot getRobot() {
		return robot;
	}
	public static void setRobot(Robot robot) {
		RobotController.robot = robot;
	}

	public static Steering getSteering() {
		return steering;
	}
	public static void setSteering(Steering steering) {
		RobotController.steering = steering;
	}

	public static Thread getSteeringThread() {
		return steeringThread;
	}
	public static void setSteeringThread(Thread steeringThread) {
		RobotController.steeringThread = steeringThread;
	}

	public static Thread getSynchronizeThread() {
		return synchronizeThread;
	}
	public static void setSynchronizeThread(Thread synchronizeThread) {
		RobotController.synchronizeThread = synchronizeThread;
	}
	
	
}
