package flee_and_catch.robot.communication;

import java.io.IOException;
import com.sun.org.apache.bcel.internal.util.Objects;

import flee_and_catch.robot.communication.exceptions.ConnectServer;
import flee_and_catch.robot.communication.exceptions.ParseCommand;
import flee_and_catch.robot.communication.json.JSONException;
import flee_and_catch.robot.communication.json.JSONObject;

public class Interpreter {

	private Client client;
	
	public Interpreter(Client pClient){
		this.client = pClient;
	}
	
	public String interpret(String pCommand) throws ParseCommand, JSONException, IOException, ConnectServer{
		JSONObject jsonObject = new JSONObject(pCommand);
		if(Objects.equals((String) jsonObject.get("apiid"), "@@fleeandcatch@@")){
			char[] typeArray = ((String) jsonObject.get("type")).toCharArray();
			String typeCmd = String.valueOf(typeArray, 0, 3);
			if(Objects.equals("Get", typeCmd)){
				String id = Character.toLowerCase(((String) jsonObject.get("id")).toCharArray()[0]) + ((String) jsonObject.get("id")).substring(1);
				if(Objects.equals("client", id)){				
					String type = Character.toLowerCase(String.valueOf(typeArray, 3, typeArray.length - 3).toCharArray()[0]) + String.valueOf(typeArray, 3, typeArray.length - 3).substring(1);
					if(Objects.equals("type", type)){
						client.sendCmd("{\"id\":\"Client\",\"type\":\"SetType\",\"apiid\":\"@@fleeandcatch@@\",\"errorhandling\":\"ignoreerrors\",\"client\":{\"id\":" + String.valueOf(client.getId()) + ",\"type\":\"ThreeWheelDriveRobot\"}}");
						return null;
					}
				}
			}
			else if(Objects.equals("Set", typeCmd)){
				String id = Character.toLowerCase(((String) jsonObject.get("id")).toCharArray()[0]) + ((String) jsonObject.get("id")).substring(1);
				if(Objects.equals("client", id)){
					jsonObject = (JSONObject) jsonObject.get(id);					
					String type = Character.toLowerCase(String.valueOf(typeArray, 3, typeArray.length - 3).toCharArray()[0]) + String.valueOf(typeArray, 3, typeArray.length - 3).substring(1);
					if(Objects.equals("id", type)){
						return String.valueOf(jsonObject.get(type));
					}
				}	
			}
			else if(Objects.equals("Disconnect", new String(typeArray))){
				String id = Character.toLowerCase(((String) jsonObject.get("id")).toCharArray()[0]) + ((String) jsonObject.get("id")).substring(1);
				if(Objects.equals("client", id)){
					client.disconnect();
					return null;
				}
			}
		}		
		throw new ParseCommand();
	}
}
