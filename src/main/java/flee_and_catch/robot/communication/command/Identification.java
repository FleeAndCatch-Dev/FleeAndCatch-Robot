package flee_and_catch.robot.communication.command;

import org.json.JSONException;
import org.json.JSONObject;

public class Identification {
	private int id;
	private String address;
	private int port;
	private String type;
	private String subtype;
	
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
	public Identification(int pId, String pAddress, int pPort, String pType, String pSubType){
		this.id = pId;
		this.address = pAddress;
		this.port = pPort;
		this.type = pType;
		this.subtype = pSubType;
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
		jsonIdentification.put("address", address);
		jsonIdentification.put("port", port);
		jsonIdentification.put("type", type);
		jsonIdentification.put("subtype", subtype);
		
		return jsonIdentification;
	}
	
	public int getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}
}
