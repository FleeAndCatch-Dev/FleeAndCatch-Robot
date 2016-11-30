package flee_and_catch.robot.communication.command.connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	private int id;
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
	public Client(int pId, String pType, String pSubType){
		this.id = pId;
		this.type = pType;
		this.subtype = pSubType;
	}
	
	/**
	 * <h1>Get client</h1>
	 * Get client as json object.
	 * 
	 * @return Client as json object.
	 * @throws JSONException
	 * 
	 * @author ThunderSL94
	 */
	public JSONObject GetClient() throws JSONException{
		JSONObject jsonclient = new JSONObject();
		jsonclient.put("id", id);
		jsonclient.put("type", type);
		jsonclient.put("subtype", subtype);
		
		return jsonclient;
	}
	
	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}
}
