package flee_and_catch.robot.communication.command;

import java.util.ArrayList;
import flee_and_catch.robot.communication.command.device.robot.Robot;
import flee_and_catch.robot.communication.command.identification.ClientIdentification;

public class SynchronizationCommand extends Command {

	private ArrayList<Robot> robots;
	
	public SynchronizationCommand(String pId, String pType, ClientIdentification pIdentification, Robot pRobot) {
		super(pId, pType, pIdentification);
		this.robots = new ArrayList<Robot>();
		this.robots.add(pRobot);
	}

	public ArrayList<Robot> getRobots() {
		return robots;
	}
}