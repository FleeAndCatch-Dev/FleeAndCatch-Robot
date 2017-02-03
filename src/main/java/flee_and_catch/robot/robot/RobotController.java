package flee_and_catch.robot.robot;

import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.Speed;
import flee_and_catch.robot.communication.command.device.robot.Steering;
import flee_and_catch.robot.configuration.ThreadConfig;

public final class RobotController {

	private static Robot robot;
	private static Steering steering;
	private static boolean accept;

	private static Thread steeringThread;
	private static Thread synchronizeThread;

	public static void intitComponents(){
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

	private static void controlRobot() throws InterruptedException {
		while(getRobot().isActive()) {
			
			try {
				
				//Should the steering should be processed and their is a steering to process:
				if(getSteering() != null && getSteering().getSpeed() != null && getSteering().getDirection() != null && accept) {
					
					//Convert the direction and the speed to enums:
					Direction direction = Direction.valueOf(getSteering().getDirection());
					Speed speed = Speed.valueOf(getSteering().getSpeed());
					
					//Process direction:
					getRobot().rotate(direction);
					
					//Process speed:
					getRobot().changeSpeed(speed);
					
					//Set Steering to processed:
					setSteering(null);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				//Wait:
				Thread.sleep(ThreadConfig.STEERING_SLEEP);
			}
		}
	}
	private static void synchronize() {
		// TODO Auto-generated method stub
		// TODO send update to backend
	}

	public static void changeActive(boolean pState){
		robot.setActive(pState);
		Client.setDevice(robot.getJSONRobot());
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

	public static boolean isAccept() {
		return accept;
	}
	public static void setAccept(boolean accept) {
		RobotController.accept = accept;
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
