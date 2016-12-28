package flee_and_catch.robot.communication.command;

import org.json.JSONObject;

public class Synchronization extends Command {
	private Identification identification;
	private Robot robot;
	
	public Synchronization(String pId, String pType, Identification pIdentification, Robot pRobot) {
		super(pId, pType);
		identification = pIdentification;
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
		command.put("robot", robot.getJSONObject());
		
		return command.toString();
	}

	public Identification getIdentification() {
		return identification;
	}

	public Robot getRobot() {
		return robot;
	}
}