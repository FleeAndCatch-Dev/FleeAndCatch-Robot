package flee_and_catch.robot.communication;

import java.util.Objects;
import org.json.JSONObject;
import com.google.gson.Gson;

import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.Connection;
import flee_and_catch.robot.communication.command.ConnectionType;

public final class Interpreter {

	private static Gson gson = new Gson();
	
	/**
	 * <h1>Parse command</h1>
	 * Parse json command and run it, when the parsing is correct. Sort it in different id.
	 * 
	 * @param pCommand Command as json command.
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
	public static void parse(String pCommand) throws Exception {
		JSONObject jsonCommand = new JSONObject(pCommand);
		if(!Objects.equals((String) jsonCommand.get("apiid"), "@@fleeandcatch@@"))
			throw new Exception("Wrong apiid of json command");		
		CommandType id = CommandType.valueOf((String) jsonCommand.get("id"));
		
		switch(id){
			case Connection:
				connection(jsonCommand);
				return;
			default:
				throw new Exception("Argument out of range");
		}			
	}

	/**
	 * <h1>Connection</h1>
	 * Parse connection command.
	 * 
	 * @param pCommand Command as json object.
	 * @throws Exception
	 * 
	 * ThunderSL94
	 */
	private static void connection(JSONObject pCommand) throws Exception {
		if(pCommand == null) throw new NullPointerException();
		ConnectionType type = ConnectionType.valueOf((String) pCommand.get("type"));
		Connection command = gson.fromJson(pCommand.toString(), Connection.class);
		
		switch(type){
			case Connect:
				Client.getClientIdentification().setId(command.getIdentification().getId());
				return;
			case Disconnect:
				Client.disconnect();
				return;
			default:
				throw new Exception("Argument out of range");
		}
	}
}
