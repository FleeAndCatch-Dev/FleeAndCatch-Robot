package flee_and_catch.robot.communication.command;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import flee_and_catch.robot.communication.command.device.robot.Robot;
import flee_and_catch.robot.communication.command.identification.ClientIdentification;

public class SynchronizationCommand extends Command {

	private ArrayList<Robot> robots;
	
	public SynchronizationCommand(String pId, String pType, ClientIdentification pIdentification, Robot pRobot) {
		super(pId, pType, pIdentification);
		this.robots = new ArrayList<Robot>();
		this.robots.add(pRobot);
	}

	@Override
	public String getCommand() {
		JSONArray robotarray = new JSONArray();
		for(int i=0; i<robots.size(); i++){
			robotarray.put(robots.get(0));
		}
		
		JSONObject command = new JSONObject();
		command.put("id", id);
		command.put("type", type);
		command.put("apiid", apiid);
		command.put("errorhandling", errorhandling);
		command.put("identification", identification.getJSONObject());
		command.put("robot", robots);
		
		return command.toString();
	}

	public ArrayList<Robot> getRobots() {
		return robots;
	}
}