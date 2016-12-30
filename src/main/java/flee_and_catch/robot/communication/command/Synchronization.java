package flee_and_catch.robot.communication.command;

import org.json.JSONObject;

import flee_and_catch.robot.communication.identification.Identification;
import flee_and_catch.robot.robot.Robot;

public class Synchronization extends Command {

	private Robot robot;
	
	public Synchronization(String pId, String pType, Identification pIdentification, Robot pRobot) {
		super(pId, pType, pIdentification);
		robot = pRobot;
	}

	@Override
	public String getCommand() {
		JSONObject command = new JSONObject();
		command.put("id", id);
		command.put("type", type);
		command.put("apiid", apiid);
		command.put("errorhandling", errorhandling);
		command.put("identification", identification.getJSONObject());
		command.put("robot", Robot.Static.getJSONObject(robot));
		
		return command.toString();
	}

	public Robot getRobot() {
		return robot;
	}
}