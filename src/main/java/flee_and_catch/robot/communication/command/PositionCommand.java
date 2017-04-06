package flee_and_catch.robot.communication.command;

import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.communication.command.device.robot.Robot;
import flee_and_catch.robot.communication.command.identification.ClientIdentification;

public class PositionCommand extends Command {
	private Robot robot;
	private Position position;
	private double speed;
	
	/**
	 * <h1>Constructor</h1>
	 * Create new connection object for json command.
	 * 
	 * @param pId
	 * @param pType
	 * @param pClient
	 * 
	 * @author ThunderSL94
	 */
	public PositionCommand(String pId, String pType, ClientIdentification pIdentification, Robot pRobot, Position pPosition, double pSpeed) {
		super(pId, pType, pIdentification);
		this.position = pPosition;
		this.speed = pSpeed;
		this.robot = pRobot;
	}

	public Robot getRobot() {
		return robot;
	}

	public Position getPosition() {
		return position;
	}

	public double getSpeed() {
		return speed;
	}
}
