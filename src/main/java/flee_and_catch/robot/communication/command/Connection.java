package flee_and_catch.robot.communication.command;

import org.json.JSONException;
import org.json.JSONObject;

import flee_and_catch.robot.communication.command.device.Device;
import flee_and_catch.robot.communication.command.identification.ClientIdentification;

public class Connection extends Command {
	private Device device;
	
	/**
	 * <h1>Constructor</h1>
	 * Create new connection object for json command.
	 * 
	 * @param pId Id as command type.
	 * @param pType Type as connection type.
	 * @param pClient Client object.
	 * 
	 * @author ThunderSL94
	 */
	public Connection(String pId, String pType, ClientIdentification pIdentification, Device pDevice){
		super(pId, pType, pIdentification);
		this.device = pDevice;
	}
	
	/**
	 * <h1>Get command</h1>
	 * Get command as json string.
	 * 
	 * @author ThunderSL94
	 */
	public String getCommand() throws JSONException{
		JSONObject command = new JSONObject();
		command.put("id", id);
		command.put("type", type);
		command.put("apiid", apiid);
		command.put("errorhandling", errorhandling);
		command.put("identification", identification.getJSONObject());
		command.put("device", device.getJSONObject());
		
		return command.toString();
	}

	public Device getDevice() {
		return device;
	}
}
