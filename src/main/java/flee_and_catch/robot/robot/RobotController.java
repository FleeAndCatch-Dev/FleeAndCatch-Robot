package flee_and_catch.robot.robot;

import com.google.gson.Gson;
import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.SynchronizationCommand;
import flee_and_catch.robot.communication.command.SynchronizationCommandType;
import flee_and_catch.robot.communication.command.component.Direction;
import flee_and_catch.robot.communication.command.component.Speed;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.device.robot.Steering;
import flee_and_catch.robot.configuration.ThreadConfig;
import flee_and_catch.robot.view.ViewController;
import lejos.remote.rcx.RCXAbstractPort;

public final class RobotController {

	private static Robot robot;
	private static Steering steering;
	private static Position destination;
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
					ViewController.showErrorScreen("217");
				}
			}
		});
		synchronizeThread = new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					synchronize();
				} catch (Exception e) {
					ViewController.showErrorScreen("217");
				}
			}
		});
	}

	private static void controlRobot() throws InterruptedException {
		
		while(robot.isActive()) {
			
			try {
				
				//Should the steering should be processed and their is a steering to process:
				if(steering != null && steering.getSpeed() != null && steering.getDirection() != null && accept) {

					//Convert the direction and the speed to enums:
					Direction direction = Direction.valueOf(getSteering().getDirection());
					Speed speed = Speed.valueOf(getSteering().getSpeed());
					
					//Process direction:
					if(direction == Direction.Left || direction == Direction.Right)
						robot.rotate(direction);
					else
						robot.forward();
					
					//Process speed:
					if(speed == Speed.Faster)
						robot.increaseSpeed();
					else if(speed == Speed.Slower)
						robot.decreaseSpeed();
					
					//Set Steering to processed:
					setSteering(null);
				}
				else if(destination != null && accept) {
					
					double rx = Math.abs(robot.getPosition().getX());
					double ry = Math.abs(robot.getPosition().getY());
					double dx = Math.abs(destination.getX());
					double dy = Math.abs(destination.getY());
					
					if(rx >= dx - 1.0 && rx <= dx + 1.0 && ry >= dy - 1.0 && ry <= dy + 1.0) {
						robot.stop();
						destination = null;
					}
					robot.driveTo(destination);
				}
				
			} catch (Exception e) {

			} finally {
				//Wait:
				Thread.sleep(ThreadConfig.STEERING_SLEEP);
			}
		}
	}
	
	private static void synchronize() throws Exception {
		while(getRobot().isActive()) {
			//Calculate the speed
			
			//Calculate the current position			
			
			//Show position and speed, other variables
			
			//Create synchronization object:
			SynchronizationCommand sync = new SynchronizationCommand(CommandType.Synchronization.toString(),SynchronizationCommandType.CurrentRobot.toString(), Client.getClientIdentification(), robot.getJSONRobot());
			
			try {
				Gson gson = new Gson();
				Client.sendCmd(gson.toJson(sync));
				Thread.sleep(ThreadConfig.SYNCHRONIZATION_SLEEP);				
			} 
			catch (Exception e) {	
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
	
	public static void setDestination(Position destination) {
		RobotController.destination = destination;
	}
	
	public static Position getDestination() {
		return RobotController.destination;
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
	
	public static Thread getSynchronizeThread() {
		return synchronizeThread;
	}

}
