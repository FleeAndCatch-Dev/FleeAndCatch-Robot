package flee_and_catch.robot.communication;

import java.util.Objects;
import org.json.JSONObject;
import com.google.gson.Gson;
import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.connection.Client;
import flee_and_catch.robot.communication.command.connection.Connection;
import flee_and_catch.robot.communication.command.connection.ConnectionType;

public final class Interpreter {

	private static Gson gson = new Gson();
	
	public static void parse(String pCommand) throws Exception {
		JSONObject jsonCommand = new JSONObject(pCommand);
		if(!Objects.equals((String) jsonCommand.get("apiid"), "@@fleeandcatch@@"))
			throw new Exception("Wrong apiid of json command");		
		CommandType.Type id = CommandType.Type.valueOf((String) jsonCommand.get("id"));
		
		switch(id){
			case Connection:
				connection(jsonCommand);
				return;
			default:
				throw new Exception("Argument out of range");
		}			
	}

	private static void connection(JSONObject pCommand) throws Exception {
		if(pCommand == null) throw new NullPointerException();
		ConnectionType.Type type = ConnectionType.Type.valueOf((String) pCommand.get("type"));
		Connection command = gson.fromJson(pCommand.toString(), Connection.class);
		
		switch(type){
			case SetId:
				flee_and_catch.robot.communication.Client.setId(command.getClient().getId());
				return;
			case GetType:
				Connection cmd = new Connection(CommandType.Type.Connection.toString(), ConnectionType.Type.SetType.toString(), new Client(flee_and_catch.robot.communication.Client.getId(), flee_and_catch.robot.communication.Client.getType(), flee_and_catch.robot.communication.Client.getSubtype()));
				flee_and_catch.robot.communication.Client.sendCmd(cmd.GetCommand());
				return;
			case Disconnect:
				flee_and_catch.robot.communication.Client.disconnect();
				return;
			default:
				throw new Exception("Argument out of range");
		}
	}
}
