package flee_and_catch.robot.communication.command.identification;

import org.json.JSONException;
import org.json.JSONObject;

import flee_and_catch.robot.communication.command.component.IdentificationType;

public class ClientIdentification extends Identification {

	private String address;
	private int port;
	
	public ClientIdentification(int pId, String pType, String pAddress, int pPort){
		this.id = pId;
		this.type = IdentificationType.valueOf(pType).toString();
		this.address = pAddress;
		this.port = pPort;
	}
	
	public ClientIdentification(ClientIdentification pClientIdentification){
		this.id = pClientIdentification.getId();
		this.type = pClientIdentification.getType();
		this.address = pClientIdentification.getAddress();
		this.port = pClientIdentification.getPort();
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
	public JSONObject getJSONObject() throws JSONException{
		JSONObject jsonIdentification = new JSONObject();
		jsonIdentification.put("id", id);
		jsonIdentification.put("type", type);
		jsonIdentification.put("address", address);
		jsonIdentification.put("port", port);
		
		return jsonIdentification;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}
}
