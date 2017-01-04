package flee_and_catch.robot.communication;

import java.util.Objects;

import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.Connection;
import flee_and_catch.robot.communication.command.ConnectionType;
import flee_and_catch.robot.communication.command.Control;
import flee_and_catch.robot.communication.command.ControlType;
import flee_and_catch.robot.communication.command.device.Device;
import flee_and_catch.robot.communication.command.device.DeviceAdapter;
import flee_and_catch.robot.robot.Robot;
import flee_and_catch.robot.robot.RobotController;
import flee_and_catch.robot.view.ViewController;

public final class Interpreter {

	private static Gson gson = new Gson();
	private static RobotController robotController;
	private static ViewController viewController;
	
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
			case Control:
				control(jsonCommand);
				return;
			default:
				throw new Exception("Argument out of range");
		}			
	}
	
	
	private static void control(JSONObject pCommand) throws Exception {
		
		if(pCommand == null) throw new NullPointerException();
		
		ControlType type = ControlType.valueOf((String) pCommand.get("type"));
		Control command = gson.fromJson(pCommand.toString(), Control.class);
		
		switch(type){
			case Begin:
				Interpreter.robotController.setRobotActive(command.getRobot().isActive());
				return;
			case End:
				Interpreter.robotController.setRobotActive(command.getRobot().isActive());
				return;
			case Start:
				//TODO: Add functionality!
				return;
			case Stop:
				//TODO: Add functionality!
				return;
			case Control:
				Interpreter.robotController.controlRobot(command.getSteering());
				Interpreter.viewController.showStatus("Control");
				return;
			default:
				throw new Exception("Argument out of range");
		}
	}
	
	public static void setRobotController(RobotController robotController) {
		Interpreter.robotController = robotController;
	}

	public static void setViewController(ViewController viewController) {
		Interpreter.viewController = viewController;
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
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Device.class, new DeviceAdapter());
		builder.setPrettyPrinting();
		Gson localgson = builder.create();
		
		Connection command = localgson.fromJson(pCommand.toString(), Connection.class);
		
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
