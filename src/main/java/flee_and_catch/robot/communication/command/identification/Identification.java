package flee_and_catch.robot.communication.command.identification;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Identification {
	protected int id;
	protected String type;
	
	public abstract JSONObject getJSONObject() throws JSONException;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
}
