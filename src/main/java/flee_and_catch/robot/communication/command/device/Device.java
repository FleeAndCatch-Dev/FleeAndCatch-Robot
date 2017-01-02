package flee_and_catch.robot.communication.command.device;

import org.json.JSONException;
import org.json.JSONObject;

public interface Device {
	JSONObject getJSONObject() throws JSONException;
}
