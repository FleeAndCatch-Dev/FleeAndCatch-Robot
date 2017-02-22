package flee_and_catch.robot.robot;

import com.google.gson.Gson;
import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.SynchronizationCommand;
import flee_and_catch.robot.communication.command.SynchronizationCommandType;
import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.Speed;
import flee_and_catch.robot.communication.command.device.robot.Steering;
import flee_and_catch.robot.configuration.ThreadConfig;
import flee_and_catch.robot.view.ViewController;

public final class RobotController {

	private static Robot robot;
	private static Steering steering;
	private static boolean accept;

	private static Thread steeringThread;
	private static int updateCounter;

	public static void intitComponents(){
		steeringThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					controlRobot();
				} catch (InterruptedException e) {
					ViewController.showErrorScreen("215");
				}
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
					if(direction == Direction.Left || direction == Direction.Right)
						getRobot().rotate(direction);
					else
						getRobot().forward();
					
					//Process speed:
					if(speed == Speed.Faster)
						getRobot().increaseSpeed();
					else if(speed == Speed.Slower)
						getRobot().decreaseSpeed();
					
					//Set Steering to processed:
					setSteering(null);
					
					if(updateCounter >= 3){
						//Create synchronization object:
						SynchronizationCommand sync = new SynchronizationCommand(CommandType.Synchronization.toString(),SynchronizationCommandType.CurrentRobot.toString(), Client.getClientIdentification(), robot.getJSONRobot());					
						Gson gson = new Gson();
						Client.sendCmd(gson.toJson(sync));
						updateCounter = 0;
					}					
					updateCounter++;
				}
				
			} catch (Exception e) {

			} finally {
				//Wait:
				Thread.sleep(ThreadConfig.STEERING_SLEEP);
			}
		}
	}

	public static void changeActive(boolean pState) {
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
}
