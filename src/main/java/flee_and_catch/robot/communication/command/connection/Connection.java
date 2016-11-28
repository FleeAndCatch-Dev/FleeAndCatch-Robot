package flee_and_catch.robot.communication.command.connection;

import org.json.JSONException;
import org.json.JSONObject;

import flee_and_catch.robot.communication.command.Command;

public class Connection extends Command {
private Client client;
	
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
	public Connection(String pId, String pType, Client pClient){
		super(pId, pType);
		this.client = pClient;
	}
	
	/**
	 * <h1>Get command</h1>
	 * Get command as json string.
	 * 
	 * @author ThunderSL94
	 */
	public String GetCommand() throws JSONException{
		JSONObject command = new JSONObject();
		command.put("id", id);
		command.put("type", type);
		command.put("apiid", apiid);
		command.put("errorhandling", errorhandling);
		command.put("client", client.GetClient());
		
		return command.toString();
	}

	public Client getClient() {
		return client;
	}
}
