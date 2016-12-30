package flee_and_catch.robot.communication.identification;

import org.json.JSONException;
import org.json.JSONObject;

import flee_and_catch.robot.component.IdentificationType;

public final class Identification {
	
	private ClientIdentification clientId;
	private RobotIdentification robotId;
	
	/**
	 * <h1>Constructor</h1>
	 * Create new client object for json command.
	 * 
	 * @param pId Id as connection id.
	 * @param pType Type as client type.
	 * @param pSubType Subtype as client subtype.
	 * 
	 * @author ThunderSL94
	 */
	public Identification(ClientIdentification ci, RobotIdentification ri){
		this.clientId = ci;
		this.robotId = ri;
	}
	
	/**
	 * <h1>Get identification</h1>
	 * Get identification as json object.
	 * 
	 * @return Identification as json object.
	 * @throws JSONException
	 * 
	 * @author ThunderSL94
	 */
	public JSONObject getJSONObject(){
		JSONObject jsonIdentification = new JSONObject();
		jsonIdentification.put("id", this.clientId.getId());
		jsonIdentification.put("address", this.clientId.getAddress());
		jsonIdentification.put("port", this.clientId.getPort());
		jsonIdentification.put("type", this.clientId.getType());
		jsonIdentification.put("subtype", this.robotId.getSubtype());
		jsonIdentification.put("role", this.robotId.getRole());
		
		return jsonIdentification;
	}
	
	public ClientIdentification getClientIdentification() {
		return this.clientId;
	}
	
	public RobotIdentification getRobotIdentification() {
		return this.robotId;
	}
}
